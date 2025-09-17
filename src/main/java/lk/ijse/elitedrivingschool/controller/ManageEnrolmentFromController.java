package lk.ijse.elitedrivingschool.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ManageEnrolmentFromController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<?> cmbCourseId;

    @FXML
    private ComboBox<?> cmbStudentId;

    @FXML
    private TableColumn<?, ?> colCourseId;

    @FXML
    private TableColumn<?, ?> colEnrolmentDate;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableColumn<?, ?> colUpfrontDate;

    @FXML
    private TableView<?> tblEnrolment;

    @FXML
    private TextField txtEnrolmentDate;

    @FXML
    private TextField txtUpfrontDate;

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
