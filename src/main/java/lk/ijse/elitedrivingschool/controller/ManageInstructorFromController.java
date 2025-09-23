package lk.ijse.elitedrivingschool.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.elitedrivingschool.bo.custom.BOFactory;
import lk.ijse.elitedrivingschool.bo.custom.InstructorBO;
import lk.ijse.elitedrivingschool.dto.InstructorDTO;
import lk.ijse.elitedrivingschool.dto.tm.InstructorTM;

import java.util.Optional;
import java.util.regex.Pattern;

public class ManageInstructorFromController {

    private InstructorBO instructorBO = (InstructorBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.INSTRUCTOR);
    private static final Pattern ID_PATTERN = Pattern.compile("^L\\d{3}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z\\s]{3,50}$");

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<InstructorTM, String> colEmail;

    @FXML
    private TableColumn<InstructorTM, String> colId;

    @FXML
    private TableColumn<InstructorTM, String> colName;

    @FXML
    private TableColumn<InstructorTM, String> colPhone;

    @FXML
    private TableColumn<InstructorTM, String> colSpecialization;

    @FXML
    private TableView<InstructorTM> tblInsructor;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtSpecialization;

    public void initialize() {
        setCellValueFactory();
        resetPage();
    }

    private void resetPage() {

        try {
            loadTableData();
            loadNextId();

            btnAdd.setDisable(false);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            txtEmail.setText("");
            txtName.setText("");
            txtPhone.setText("");
            txtSpecialization.setText("");

        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please try again").show();
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
    }

    private void loadTableData() {
        try {
            ObservableList<InstructorTM> obList = FXCollections.observableArrayList();
            for (InstructorDTO dto : instructorBO.getAllInstructors()) {
                obList.add(new InstructorTM(
                        dto.getInstructorId(),
                        dto.getFullName(),
                        dto.getEmail(),
                        dto.getPhone(),
                        dto.getSpecialization()
                ));
            }
            tblInsructor.setItems(obList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load instructor: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private void loadNextId() {
        try {
            txtId.setText(instructorBO.getNextId());
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

        String name = txtName.getText().trim();
        if (name.isEmpty()) {
            showError("Name cannot be empty");
            return false;
        }
        if (!NAME_PATTERN.matcher(name).matches()) {
            showError("Invalid name format. Name should contain only letters and spaces (3-50 characters)");
            return false;
        }
        return true;
    }

    private boolean validateAllFields() {
        return  validateName();
    }

    @FXML
    void onTableClick(javafx.scene.input.MouseEvent event) {
        InstructorTM selected = tblInsructor.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtId.setText(selected.getInstructorId());
            txtName.setText(selected.getFullName());
            txtEmail.setText(selected.getEmail());
            txtPhone.setText(selected.getPhone());
            txtSpecialization.setText(selected.getSpecialization());

            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void addPatientBtn(ActionEvent event) {

        if (!validateAllFields()) return;

        try {
            InstructorDTO dto = new InstructorDTO(
                    txtId.getText(),
                    txtName.getText(),
                    txtEmail.getText(),
                    txtPhone.getText(),
                    txtSpecialization.getText()
            );
            if (instructorBO.saveInstructors(dto)){
                showSuccess("Instructor saved successfully!");
                resetPage();
            }
            else {
                showError("Something went wrong, please try again");
            }
        }
        catch (Exception e) {
            showError("Error saving Instructor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void deletePatientBtn(ActionEvent event) {

        String id = txtId.getText().trim();
        if(id.isEmpty()) {
            showError("ID cannot be empty");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete instructor " + id + "?");
        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                if (instructorBO.deleteInstructors(id)) {
                    showSuccess("Instructor deleted successfully!");
                    resetPage();
                } else {
                    showError("Failed to delete instructor");
                }
            } catch (Exception e) {
                showError("Error deleting instructor: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    void updatePatientBtn(ActionEvent event) {

        try {
            if (!validateAllFields()) return;

            InstructorDTO dto = new InstructorDTO(
                    txtId.getText(),
                    txtName.getText(),
                    txtEmail.getText(),
                    txtPhone.getText(),
                    txtSpecialization.getText()
            );

            if (instructorBO.updateInstructors(dto)){
                showSuccess("Instructor updated successfully!");
                resetPage();
            }
            else {
                showError("Something went wrong, please try again");
            }
        }
        catch (Exception e) {
            showError("Error saving Instructor: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
