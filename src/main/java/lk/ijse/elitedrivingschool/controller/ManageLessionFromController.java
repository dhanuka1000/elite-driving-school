package lk.ijse.elitedrivingschool.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.elitedrivingschool.bo.custom.BOFactory;
import lk.ijse.elitedrivingschool.bo.custom.LessonBO;
import lk.ijse.elitedrivingschool.dto.LessionDTO;
import lk.ijse.elitedrivingschool.dto.tm.LessionTm;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

public class ManageLessionFromController {

    public TextField txtTime;
    public TableColumn colTime;
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<LessionTm, String> colDate;

    @FXML
    private TableColumn<LessionTm, String> colDuration;

    @FXML
    private TableColumn<LessionTm, String> colId;

    @FXML
    private TableColumn<LessionTm, String> colLocation;

    @FXML
    private TableColumn<LessionTm, String> colStatus;

    @FXML
    private TableView<LessionTm> tblLesson;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtLocation;

    @FXML
    private TextField txtStatus;

    private LessonBO lessonBO = (LessonBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.LESSON);

    private static final Pattern ID_PATTERN = Pattern.compile("^L\\d{3}$");
    private static final Pattern DURATION_PATTERN = Pattern.compile("^\\d+\\s*(hours?|hrs?)$", Pattern.CASE_INSENSITIVE);
    private static final Pattern STATUS_PATTERN = Pattern.compile("^(Scheduled|Completed|Cancelled)$", Pattern.CASE_INSENSITIVE);

    public void initialize() {
        setCellValueFactory();
        loadTableData();
        loadNextId();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("lessionId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadTableData() {
        try {
            ObservableList<LessionTm> obList = FXCollections.observableArrayList();
            for (LessionDTO dto : lessonBO.getAllLessons()) {
                obList.add(new LessionTm(
                        dto.getLessionId(),
                        dto.getDate(),
                        dto.getDuration(),
                        dto.getLocation(),
                        dto.getStatus(),
                        dto.getInstructorId()
                ));
            }
            tblLesson.setItems(obList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load lessons: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private void loadNextId() {
        try {
            txtId.setText(lessonBO.getNextId());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate ID: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private boolean validateFields() {
        if (!ID_PATTERN.matcher(txtId.getText()).matches()) {
            showError("Invalid Lesson ID format. Should be L followed by 3 digits (e.g., L001)");
            return false;
        }
        if (!DURATION_PATTERN.matcher(txtDuration.getText()).matches()) {
            showError("Invalid duration format. Example: '2 hours' or '1.5 hrs'");
            return false;
        }
        if (!STATUS_PATTERN.matcher(txtStatus.getText()).matches()) {
            showError("Status must be: Scheduled, Completed, or Cancelled");
            return false;
        }
        if (txtLocation.getText().trim().isEmpty()) {
            showError("Location cannot be empty");
            return false;
        }
        return true;
    }

    private void showError(String message) {
        new Alert(Alert.AlertType.ERROR, message).show();
    }

    private void showSuccess(String message) {
        new Alert(Alert.AlertType.INFORMATION, message).show();
    }

    private void clearFields() {
        txtDate.clear();
        txtDuration.clear();
        txtLocation.clear();
        txtStatus.clear();
        loadNextId();
    }

    @FXML
    void addPatientBtn(ActionEvent event) {
        if (!validateFields()) return;

        try {
            LessionDTO dto = new LessionDTO(
                    txtId.getText(),
                    LocalDateTime.parse(txtDate.getText()),
                    txtDuration.getText(),
                    txtLocation.getText(),
                    txtStatus.getText(),
                    null // instructorId would be set from another control
            );

            if (lessonBO.saveLessons(dto)) {
                showSuccess("Lesson saved successfully!");
                clearFields();
                loadTableData();
            } else {
                showError("Failed to save lesson");
            }
        } catch (Exception e) {
            showError("Error saving lesson: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void deletePatientBtn(ActionEvent event) {
        String lessonId = txtId.getText();
        if (lessonId.isEmpty()) {
            showError("Please select a lesson to delete");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete lesson " + lessonId + "?");
        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                if (lessonBO.deleteLessons(lessonId)) {
                    showSuccess("Lesson deleted successfully!");
                    clearFields();
                    loadTableData();
                } else {
                    showError("Failed to delete lesson");
                }
            } catch (Exception e) {
                showError("Error deleting lesson: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    void updatePatientBtn(ActionEvent event) {
        if (!validateFields()) return;

        try {
            LessionDTO dto = new LessionDTO(
                    txtId.getText(),
                    LocalDateTime.parse(txtDate.getText()),
                    txtDuration.getText(),
                    txtLocation.getText(),
                    txtStatus.getText(),
                    null // instructorId would be set from another control
            );

            if (lessonBO.updateLessons(dto)) {
                showSuccess("Lesson updated successfully!");
                clearFields();
                loadTableData();
            } else {
                showError("Failed to update lesson");
            }
        } catch (Exception e) {
            showError("Error updating lesson: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void onTableClick(javafx.scene.input.MouseEvent event) {
        LessionTm selected = tblLesson.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtId.setText(selected.getLessionId());
            txtDate.setText(selected.getDate().toString());
            txtDuration.setText(selected.getDuration());
            txtLocation.setText(selected.getLocation());
            txtStatus.setText(selected.getStatus());
        }
    }
}