package lk.ijse.elitedrivingschool.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ManageLearningFromController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<?> cmbCourseId;

    @FXML
    private ComboBox<?> cmbInstructorId;

    @FXML
    private TableColumn<?, ?> colCourseId;

    @FXML
    private TableColumn<?, ?> colInstructorId;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableView<?> tblLearning;

    @FXML
    private TextField txtStatus;

    @FXML
    void addPatientBtn(ActionEvent event) {

    }

    @FXML
    void deletePatientBtn(ActionEvent event) {

    }

    @FXML
    void updatePatientBtn(ActionEvent event) {

    }

}
