package lk.ijse.elitedrivingschool.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ManageCourseFromController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colFee;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableView<?> tblCourse;

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
