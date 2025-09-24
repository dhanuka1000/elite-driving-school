package lk.ijse.elitedrivingschool.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.elitedrivingschool.bo.custom.BOFactory;
import lk.ijse.elitedrivingschool.bo.custom.PaymentBO;
import lk.ijse.elitedrivingschool.bo.custom.StudentBO;
import lk.ijse.elitedrivingschool.dto.PaymentDTO;
import lk.ijse.elitedrivingschool.dto.StudentDTO;
import lk.ijse.elitedrivingschool.dto.tm.PaymentTM;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ManagePaymentFromController {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z\\s]{3,50}$");
    public TextField txtStudentId;
    PaymentBO paymentBO =(PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PAYMENT);
    StudentBO studentBO =(StudentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.STUDENT);

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

//    @FXML
//    private ComboBox<String> cmbId;

    @FXML
    private TableColumn<PaymentTM, String> colAmount;

    @FXML
    private TableColumn<PaymentTM, String> colDate;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentId;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentMethod;

    @FXML
    private TableColumn<PaymentTM, String> colStatus;

    @FXML
    private TableColumn<PaymentTM, String> colStudentId;

    @FXML
    private TableView<PaymentTM> tblCourse;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtPaymentMethod;

    @FXML
    private TextField txtStatus;

    private void loadStudentIds() {
        try {
            txtStudentId.setText(studentBO.getNextId());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load student IDs").show();
            e.printStackTrace();
        }
    }

    public void initialize() {
        setCellValueFactory();
        resetPage();
    }

    private void resetPage() {

        try {
            loadTableData();
            loadNextId();
            loadStudentIds();

            btnAdd.setDisable(false);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            txtAmount.setText("");
            txtDate.setText("");
            txtPaymentMethod.setText("");
            txtStatus.setText("");
//            txtStudentId.setText("");

        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please try again").show();
        }
    }

    private void setCellValueFactory() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
    }

    private void loadTableData() {
        try {
            ObservableList<PaymentTM> obList = FXCollections.observableArrayList();
            for (PaymentDTO dto : paymentBO.getAllPayments()) {
                obList.add(new PaymentTM(
                        dto.getPaymentId(),
                        dto.getAmount(),
                        dto.getPaymentDate(),
                        dto.getPaymentMethod(),
                        dto.getStatus(),
                        dto.getStudentId()
                ));
            }
            tblCourse.setItems(obList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load Payment: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private void loadNextId() {
        try {
            txtId.setText(paymentBO.getNextId());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate ID: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        new Alert(Alert.AlertType.ERROR, message).show();
    }

    private void showSuccess(String message) {
        new Alert(Alert.AlertType.INFORMATION, message).show();
    }

    private boolean validateName() {

        String name = txtPaymentMethod.getText().trim();
        if (name.isEmpty()) {
            showError("Name cannot be empty");
            return false;
        }
        if (!NAME_PATTERN.matcher(name).matches()) {
            showError("Invalid method format.");
            return false;
        }
        return true;
    }

    private boolean validateAllFields() {
        return  validateName();
    }

    @FXML
    void onTableClick(javafx.scene.input.MouseEvent event) {
        PaymentTM selected = tblCourse.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtId.setText(selected.getPaymentId());
            txtAmount.setText(selected.getAmount().toString());
            txtDate.setText(String.valueOf(selected.getPaymentDate()));
            txtPaymentMethod.setText(selected.getPaymentMethod());
            txtStatus.setText(selected.getStatus());
            txtStudentId.setText(selected.getStudentId());

            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void addPatientBtn(ActionEvent event) {

        if (!validateAllFields()) return;

        try {
            PaymentDTO dto = new PaymentDTO(
                    txtId.getText(),
                    txtAmount.getText(),
                    txtDate.getText(),
                    txtPaymentMethod.getText(),
                    txtStatus.getText(),
                    txtStudentId.getText()
            );

            if (paymentBO.savePayments(dto)) {
                showSuccess("Payment saved! Student auto-created if new.");
                loadTableData();
            } else {
                showError("Failed to save Payment");
            }
        } catch (Exception e) {
            showError("Error saving Payment: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void deletePatientBtn(ActionEvent event) {

        String paymentId = txtId.getText();
        if (paymentId.isEmpty()) {
            showError("Please select a Payment to delete");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete Payment " + paymentId + "?");
        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                if (paymentBO.deletePayments(paymentId)) {
                    showSuccess("Payment deleted successfully!");
                    resetPage();
                } else {
                    showError("Failed to delete Payment");
                }
            } catch (Exception e) {
                showError("Error deleting Payment: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    void updatePatientBtn(ActionEvent event) {

        if (!validateAllFields()) return;

        try {
            PaymentDTO dto = new PaymentDTO(
                    txtId.getText(),
                    txtAmount.getText(),
                    txtDate.getText(),
                    txtPaymentMethod.getText(),
                    txtStatus.getText(),
                    txtStudentId.getText()
            );

            if (paymentBO.updatePayments(dto)) {
                showSuccess("Payment updated successfully!");
                resetPage();
            } else {
                showError("Failed to update Payment");
            }
        } catch (Exception e) {
            showError("Error updating Payment: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
