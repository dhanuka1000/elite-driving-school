package lk.ijse.elitedrivingschool.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.elitedrivingschool.bo.custom.BOFactory;
import lk.ijse.elitedrivingschool.bo.custom.StudentBO;
import lk.ijse.elitedrivingschool.dto.StudentDTO;
import lk.ijse.elitedrivingschool.dto.tm.StudentTM;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Wrong {

    public AnchorPane ancParent;
    public AnchorPane ancChild;
    StudentBO studentBO =(StudentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.STUDENT);

    public ComboBox cmbLessonId;
    public TableColumn colLessonId;

    @FXML
    private Button btnAddPatient;

    @FXML
    private Button btnCourse;

    @FXML
    private Button btnDeletePatient;

    @FXML
    private Button btnEnrolment;

    @FXML
    private Button btnInstructor;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnStudent;

    @FXML
    private Button btnUpdatePatient;

    @FXML
    private TableColumn<StudentTM, String> colAddress;

    @FXML
    private TableColumn<StudentTM, LocalDate> colBirthday;

    @FXML
    private TableColumn<StudentTM, String> colContact;

    @FXML
    private TableColumn<StudentTM, String> colEmail;

    @FXML
    private TableColumn<StudentTM, String> colName;

    @FXML
    private TableColumn<StudentTM, String> colStudentId;

    @FXML
    private TableView<StudentTM> tblStudent;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtRegistrationDate;

    @FXML
    private TextField txtStudentId;

    @FXML
    private TextField txtStudentName;

    @FXML
    private TextField txtdob;

    public void initialize() {

        setCellValueFactory();
        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please try again").show();
        }
    }

    private void resetPage() {

        try {
            loadTableData();
            loadNextId();

            btnAddPatient.setDisable(false);
            btnUpdatePatient.setDisable(true);
            btnDeletePatient.setDisable(true);

            txtAddress.setText("");
            txtdob.setText("");
            txtContact.setText("");
            txtEmail.setText("");
            txtStudentName.setText("");
            cmbLessonId.setValue("");

        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please try again").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {

//        tblStudent.getItems().clear();
//        List<StudentDTO> allStudents = studentBO.getAllStudents();
//        for (StudentDTO dto : allStudents) {
//            tblStudent.getItems().add(new StudentTM(
//                    dto.getStudentId(),
//                    dto.getFullName(),
//                    dto.getDob(),
//                    dto.getPhone(),
//                    dto.getEmail(),
//                    dto.getAddress(),
//                    dto.getLessionId()
//            ));
//        }
    }

    private void loadNextId() throws SQLException {

        String nextId = studentBO.getNextId();
        txtStudentId.setText(nextId);
    }

    private void setCellValueFactory() {

        colStudentId.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("full_name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colBirthday.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colLessonId.setCellValueFactory(new PropertyValueFactory<>("lession_id"));
    }

    @FXML
    void PatientBtnExit(MouseEvent event) {

    }

    @FXML
    void PatientBtnIn(MouseEvent event) {

    }

    @FXML
    void addPatientBtn(ActionEvent event) {

    }

    @FXML
    void appointmentBtnExit(MouseEvent event) {

    }

    @FXML
    void appointmentBtnIn(MouseEvent event) {

    }

    @FXML
    void billinBtnIn(MouseEvent event) {

    }

    @FXML
    void billingBtnExit(MouseEvent event) {

    }

    @FXML
    void deletePatientBtn(ActionEvent event) {

    }

    @FXML
    void goAppoinentbtn(ActionEvent event) {

    }

    @FXML
    void goBillBtn(ActionEvent event) {

    }

    @FXML
    void goItrmBtn(ActionEvent event) {

    }

    @FXML
    void goPatientbtn(ActionEvent event) {

    }

    @FXML
    void goTestRBtn(ActionEvent event) {

    }

    @FXML
    void itemBtnExit(MouseEvent event) {

    }

    @FXML
    void itemBtnIn(MouseEvent event) {

    }

    @FXML
    void testResultBtnExit(MouseEvent event) {

    }

    @FXML
    void testResultBtnIn(MouseEvent event) {

    }

    @FXML
    void updatePatientBtn(ActionEvent event) {

    }

}
