package lk.ijse.elitedrivingschool.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ManagePaymentFromController {

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<?> cmbId;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableColumn<?, ?> colPaymentMethod;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colStudentId;

    @FXML
    private TableView<?> tblCourse;

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
