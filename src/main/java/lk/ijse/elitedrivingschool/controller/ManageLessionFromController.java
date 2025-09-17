package lk.ijse.elitedrivingschool.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ManageLessionFromController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDuration;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colLocation;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private TableView<?> tblLesson;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtLocation;

    @FXML
    private TextField txtTime;

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
