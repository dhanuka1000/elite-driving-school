package lk.ijse.elitedrivingschool.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.elitedrivingschool.bo.custom.BOFactory;
import lk.ijse.elitedrivingschool.bo.custom.CourseBO;
import lk.ijse.elitedrivingschool.bo.custom.UserBO;
import lk.ijse.elitedrivingschool.dto.CourseDTO;
import lk.ijse.elitedrivingschool.dto.UserDTO;
import lk.ijse.elitedrivingschool.dto.tm.CourseTM;
import lk.ijse.elitedrivingschool.dto.tm.UserTM;

import java.util.Optional;
import java.util.regex.Pattern;

public class ManageCourseFromController {

    private CourseBO courseBO = (CourseBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.COURSE);
    private static final Pattern ID_PATTERN = Pattern.compile("^L\\d{3}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z\\s]{3,50}$");

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<CourseTM, String> colDescription;

    @FXML
    private TableColumn<CourseTM, String> colDuration;

    @FXML
    private TableColumn<CourseTM, String> colFee;

    @FXML
    private TableColumn<CourseTM, String> colId;

    @FXML
    private TableColumn<CourseTM, String> colName;

    @FXML
    private TableView<CourseTM> tblCourse;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtFee;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

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

            txtName.setText("");
            txtDuration.setText("");
            txtFee.setText("");
            txtDuration.setText("");

        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please try again").show();
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void loadTableData() {
        try {
            ObservableList<CourseTM> obList = FXCollections.observableArrayList();
            for (CourseDTO dto : courseBO.getAllCourses()) {
                obList.add(new CourseTM(
                        dto.getCourseId(),
                        dto.getName(),
                        dto.getDuration(),
                        dto.getFee(),
                        dto.getDescription()
                ));
            }
            tblCourse.setItems(obList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load course: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private void loadNextId() {
        try {
            txtId.setText(courseBO.getNextId());
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
        CourseTM selected = tblCourse.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtId.setText(selected.getCourseId());
            txtName.setText(selected.getName());
            txtDuration.setText(selected.getDuration());
            txtFee.setText(String.valueOf(selected.getFee()));
            txtDescription.setText(selected.getDescription());

            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void addPatientBtn(ActionEvent event) {

        if (!validateAllFields()) return;

        try {
            CourseDTO dto = new CourseDTO(
                    txtId.getText(),
                    txtName.getText(),
                    txtDuration.getText(),
                    txtFee.getText(),
                    txtDescription.getText()
            );

            if (courseBO.saveCourse(dto)) {
                showSuccess("Course saved successfully!");
                loadTableData();
            } else {
                showError("Failed to save Course");
            }
        } catch (Exception e) {
            showError("Error saving Course: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void deletePatientBtn(ActionEvent event) {

        String id = txtId.getText();
        if (id.isEmpty()) {
            showError("Please select a course to delete");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete course " + id + "?");
        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                if (courseBO.deleteCourse(id)) {
                    showSuccess("Course deleted successfully!");
                    resetPage();
                } else {
                    showError("Failed to delete course");
                }
            } catch (Exception e) {
                showError("Error deleting course: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    void updatePatientBtn(ActionEvent event) {

        if (!validateAllFields()) return;

        try {
            CourseDTO dto = new CourseDTO(
                    txtId.getText(),
                    txtName.getText(),
                    txtDuration.getText(),
                    txtFee.getText(),
                    txtDescription.getText()
            );

            if (courseBO.updateCourse(dto)) {
                showSuccess("Course updated successfully!");
                resetPage();
            } else {
                showError("Failed to update course");
            }
        } catch (Exception e) {
            showError("Error updating course: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
