package lk.ijse.elitedrivingschool.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.elitedrivingschool.bo.custom.BOFactory;
import lk.ijse.elitedrivingschool.bo.custom.EnrolmentBO;
import lk.ijse.elitedrivingschool.dto.EnrolmentDTO;
import lk.ijse.elitedrivingschool.dto.tm.EnrolmentTM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.regex.Pattern;

public class ManageEnrolmentFromController {

    private EnrolmentBO enrolmentBO = (EnrolmentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ENROLMENT);
    private static final Pattern ID_PATTERN = Pattern.compile("^L\\d{3}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z\\s]{3,50}$");

    @FXML
    private ListView<String> listCourseId;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbStudentId;

    @FXML
    private TableColumn<EnrolmentTM, String> colCourseIds;

    @FXML
    private TableColumn<EnrolmentTM, String> colEnrolmentDate;

    @FXML
    private TableColumn<EnrolmentTM, String> colEnrolmentId;

    @FXML
    private TableColumn<EnrolmentTM, String> colStudentId;

    @FXML
    private TableColumn<EnrolmentTM, String> colUpfrontDate;

    @FXML
    private TableView<EnrolmentTM> tblEnrolment;

    @FXML
    private TextField txtCourseId;

    @FXML
    private TextField txtEnrolmentDate;

    @FXML
    private TextField txtEnrolmentId;

    @FXML
    private TextField txtUpfrontDate;

    public void initialize() {
        setCellValueFactory();
        resetPage();
        loadStudentIds();

        cmbStudentId.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.isEmpty()) {
                loadCoursesByStudent(newVal);
            }
        });
    }

    private void loadStudentIds() {
        try {
            cmbStudentId.getItems().clear();
            cmbStudentId.getItems().addAll(enrolmentBO.getAllStudentIds());
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load student IDs").show();
        }
    }

    private void loadCoursesByStudent(String studentId) {
        try {
            listCourseId.getItems().clear();
            var courseIds = enrolmentBO.getCourseIdsByStudent(studentId);
            listCourseId.getItems().addAll(courseIds);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to load courses for student");
        }
    }

    private void resetPage() {

        try {
            loadTableData();
            loadNextId();

            btnAdd.setDisable(false);
            btnDelete.setDisable(true);
            btnUpdate.setDisable(true);

            cmbStudentId.setValue("");
            //txtCourseId.setText("");
            txtEnrolmentDate.setText("");
            txtUpfrontDate.setText("");

        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please try again").show();
        }
    }

    private void setCellValueFactory() {

        colEnrolmentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colCourseIds.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colEnrolmentDate.setCellValueFactory(new PropertyValueFactory<>("enrolmentDate"));
        colUpfrontDate.setCellValueFactory(new PropertyValueFactory<>("upfrontPaid"));
    }

    private void loadTableData() {
        try {
            ObservableList<EnrolmentTM> obList = FXCollections.observableArrayList();
            for (EnrolmentDTO dto : enrolmentBO.getAllEnrolment()) {
                obList.add(new EnrolmentTM(
                        dto.getId(),
                        dto.getStudentId(),
                        dto.getCourseIds(),
                        dto.getEnrolmentDate(),
                        dto.getUpfrontPaid()
                ));
            }
            tblEnrolment.setItems(obList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load enrolment: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private void loadNextId() {
        try {
            txtEnrolmentId.setText(enrolmentBO.getNextId());
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


    @FXML
    void addPatientBtn(ActionEvent event) {

        try {
            ObservableList<String> selectedCourses = listCourseId.getSelectionModel().getSelectedItems();
            if (selectedCourses.isEmpty()) {
                showError("Please select at least one course");
                return;
            }

            EnrolmentDTO dto = new EnrolmentDTO(
                    txtEnrolmentId.getText(),
                    Collections.singletonList(cmbStudentId.getValue()),
                    new ArrayList<>(selectedCourses),
                    txtEnrolmentDate.getText(),
                    txtUpfrontDate.getText()
            );

            if (enrolmentBO.saveEnrolment(dto)) {
                showSuccess("Enrolment saved successfully!");
                resetPage();
            } else {
                showError("Failed to save enrolment");
            }
        } catch (Exception e) {
            showError("Error saving enrolment: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void deletePatientBtn(ActionEvent event) {

        String id = txtEnrolmentId.getText();
        if (id.isEmpty()) {
            showError("Please select an enrolment to delete");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete enrolment " + id + "?");
        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                if (enrolmentBO.deleteEnrolment(id)) {
                    showSuccess("Enrolment deleted successfully!");
                    resetPage();
                } else {
                    showError("Failed to delete enrolment");
                }
            } catch (Exception e) {
                showError("Error deleting enrolment: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    void onMouseClicked(MouseEvent event) {

        EnrolmentTM selected = tblEnrolment.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtEnrolmentId.setText(selected.getId());
            cmbStudentId.setValue(String.valueOf(selected.getStudentId()));
            txtEnrolmentDate.setText(selected.getEnrolmentDate());
            txtUpfrontDate.setText(selected.getUpfrontPaid());

            listCourseId.getSelectionModel().clearSelection();
            String[] courses = selected.getCourseId().split(",");
            for (String c : courses) {
                listCourseId.getSelectionModel().select(c.trim());
            }

            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }

    }

    @FXML
    void updatePatientBtn(ActionEvent event) {

        try {
            ObservableList<String> selectedCourses = listCourseId.getSelectionModel().getSelectedItems();
            if (selectedCourses.isEmpty()) {
                showError("Please select at least one course");
                return;
            }

            EnrolmentDTO dto = new EnrolmentDTO(
                    txtEnrolmentId.getText(),
                    Collections.singletonList(cmbStudentId.getValue()),
                    new ArrayList<>(selectedCourses),
                    txtEnrolmentDate.getText(),
                    txtUpfrontDate.getText()
            );

            if (enrolmentBO.updateEnrolment(dto)) {
                showSuccess("Enrolment updated successfully!");
                resetPage();
            } else {
                showError("Failed to update enrolment");
            }
        } catch (Exception e) {
            showError("Error updating enrolment: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
