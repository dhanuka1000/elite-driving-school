package lk.ijse.elitedrivingschool.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.elitedrivingschool.bo.custom.BOFactory;
import lk.ijse.elitedrivingschool.bo.custom.LessonBO;
import lk.ijse.elitedrivingschool.bo.custom.StudentBO;
import lk.ijse.elitedrivingschool.dto.StudentDTO;
import lk.ijse.elitedrivingschool.dto.tm.StudentTM;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class ManageStudentFromController {

    public Button btnLesson;
    public Button btnUser;

    StudentBO studentBO =(StudentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.STUDENT);
    LessonBO lessonBO = (LessonBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.LESSON);

    @FXML
    private AnchorPane ancChild;

    @FXML
    private AnchorPane ancParent;

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
    private ComboBox<String> cmbLessonId;

    @FXML
    private TableColumn<StudentTM, String> colAddress;

    @FXML
    private TableColumn<StudentTM, LocalDate> colBirthday;

    @FXML
    private TableColumn<StudentTM, String> colContact;

    @FXML
    private TableColumn<StudentTM, String> colEmail;

    @FXML
    private TableColumn<StudentTM, String> colLessonId;

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
    private TextField txtStudentId;

    @FXML
    private TextField txtStudentName;

    @FXML
    private TextField txtdob;

    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z\\s]{3,50}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(?:0|94|\\+94)?(?:7(0|1|2|5|6|7|8)\\d{7})$");
    private static final Pattern ADDRESS_PATTERN = Pattern.compile("^[A-Za-z0-9\\s.,-]{5,100}$");
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
    private static final Pattern STUDENT_ID_PATTERN = Pattern.compile("^S\\d{3,}$");

    private boolean validateName() {
        String name = txtStudentName.getText().trim();
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

    private boolean validateEmail() {
        String email = txtEmail.getText().trim();
        if (email.isEmpty()) {
            showError("Email cannot be empty");
            return false;
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            showError("Invalid email format. Please enter a valid email address");
            return false;
        }
        return true;
    }

    private boolean validatePhone() {
        String phone = txtContact.getText().trim();
        if (phone.isEmpty()) {
            showError("Phone number cannot be empty");
            return false;
        }
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            showError("Invalid phone number format. Please enter a valid Sri Lankan phone number");
            return false;
        }
        return true;
    }

    private boolean validateAddress() {
        String address = txtAddress.getText().trim();
        if (address.isEmpty()) {
            showError("Address cannot be empty");
            return false;
        }
        if (!ADDRESS_PATTERN.matcher(address).matches()) {
            showError("Invalid address format. Address should be 5-100 characters long");
            return false;
        }
        return true;
    }

    private boolean validateAllFields() {
        return  validateName() &&
                validateEmail() &&
                validatePhone() &&
                validateAddress();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Operation Successful");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void initialize() {

        setCellValueFactory();
        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please try again").show();
        }
    }

    private void loadLessonIds() {
        try {
            List<String> lessonIds = lessonBO.getAllLessonIds();
            cmbLessonId.getItems().clear();
            cmbLessonId.getItems().addAll(lessonIds);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load lesson IDs").show();
            e.printStackTrace();
        }
    }

    private void resetPage() {

        try {
            loadTableData();
            loadNextId();
            loadLessonIds();

            //btnAddPatient.setDisable(false);
            btnUpdatePatient.setDisable(true);
            btnDeletePatient.setDisable(true);

            txtAddress.setText("");
            txtdob.setText("");
            txtContact.setText("");
            txtEmail.setText("");
            txtStudentName.setText("");
            cmbLessonId.getSelectionModel().clearSelection();

        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please try again").show();
        }
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {

        tblStudent.getItems().clear();
        List<StudentDTO> allStudents = studentBO.getAllStudents();
        for (StudentDTO dto : allStudents) {
            tblStudent.getItems().add(new StudentTM(
                    dto.getStudentId(),
                    dto.getFullName(),
                    dto.getDob(),
                    dto.getPhone(),
                    dto.getEmail(),
                    dto.getAddress(),
                    dto.getLesson()
            ));
        }
    }

    private void loadNextId() throws SQLException {

        String nextId = studentBO.getNextId();
        txtStudentId.setText(nextId);
    }

    private void setCellValueFactory() {

        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colBirthday.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colLessonId.setCellValueFactory(new PropertyValueFactory<>("lesson"));
    }

    public void onClickTable(MouseEvent mouseEvent) {

        StudentTM selectedItem = tblStudent.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            txtStudentId.setText(selectedItem.getStudentId());
            txtStudentName.setText(selectedItem.getFullName());
            txtEmail.setText(selectedItem.getEmail());
            txtContact.setText(selectedItem.getPhone());
            txtdob.setText(String.valueOf(selectedItem.getDob()));
            txtAddress.setText(selectedItem.getAddress());
            cmbLessonId.setValue(String.valueOf(selectedItem.getLesson()));

            //btnAddPatient.setDisable(true);
            btnUpdatePatient.setDisable(false);
            btnDeletePatient.setDisable(false);
        }
    }

    @FXML
    void addPatientBtn(ActionEvent event) {

        if (!validateAllFields()) {
            return;
        }

        String studentId = txtStudentId.getText().trim();
        String name = txtStudentName.getText().trim();
        String dob = txtdob.getText().trim();
        String contact = txtContact.getText().trim();
        String email = txtEmail.getText().trim();
        String address = txtAddress.getText().trim();
        String lessonId = cmbLessonId.getSelectionModel().getSelectedItem();

        try {
            boolean isSaved = studentBO.saveStudents(new StudentDTO(studentId, name, email, contact, dob, address, lessonId));

            if (isSaved) {
                showSuccess("Student added successfully!");
                resetPage();
            } else {
                showError("Failed to add student");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to save the student: " + e.getMessage());
        }
    }

    @FXML
    void updatePatientBtn(ActionEvent event) {

        if (!validateAllFields()) {
            return;
        }

        String studentId = txtStudentId.getText().trim();
        String name = txtStudentName.getText().trim();
        String dob = txtdob.getText();
        String contact = txtContact.getText().trim();
        String email = txtEmail.getText().trim();
        String address = txtAddress.getText().trim();
        String lessonId = cmbLessonId.getValue();

        try {
            boolean isUpdated = studentBO.updateStudents(new StudentDTO(studentId, name, email,  contact, dob, address, lessonId));

            if (isUpdated) {
                showSuccess("Student updated successfully!");
                resetPage();
            } else {
                showError("Failed to update student");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Failed to update the student: " + e.getMessage());
        }
    }

    @FXML
    void deletePatientBtn(ActionEvent event) {

        if (!validateAllFields()) {
            return;
        }

        String studentId = txtStudentId.getText().trim();

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Deletion");
        confirmation.setHeaderText("Delete Student");
        confirmation.setContentText("Are you sure you want to delete student " + studentId + "?");

        if (confirmation.showAndWait().get() == ButtonType.OK) {
            try {
                boolean isDeleted = studentBO.deleteStudents(studentId);

                if (isDeleted) {
                    showSuccess("Student deleted successfully!");
                    resetPage();
                } else {
                    showError("Failed to delete student");
                }

            } catch (Exception e) {
                e.printStackTrace();
                showError("Failed to delete the student: " + e.getMessage());
            }
        }
    }

    @FXML
    void btnCourseIn(MouseEvent event) {

        mouseEnter(btnCourse);
    }

    @FXML
    void btnCourseOut(MouseEvent event) {

        mouseExit(btnCourse);
    }

    @FXML
    void btnEnrolmentIn(MouseEvent event) {

        mouseEnter(btnEnrolment);
    }

    @FXML
    void btnEnrolmentOut(MouseEvent event) {

        mouseExit(btnEnrolment);
    }

    @FXML
    void btnInstructorIn(MouseEvent event) {

        mouseEnter(btnInstructor);
    }

    @FXML
    void btnInstructorOut(MouseEvent event) {

        mouseExit(btnInstructor);
    }

    @FXML
    void btnPaymentIn(MouseEvent event) {

        mouseEnter(btnPayment);
    }

    @FXML
    void btnPaymentOut(MouseEvent event) {

        mouseExit(btnPayment);
    }

    @FXML
    void btnStudentIn(MouseEvent event) {

        mouseEnter(btnStudent);
    }

    @FXML
    void btnStudentOut(MouseEvent event) {

        mouseExit(btnStudent);
    }

    private void navigateTo(String path) {
        try {
            ancChild.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/" + path));
            anchorPane.prefWidthProperty().bind(ancChild.widthProperty());
            anchorPane.prefHeightProperty().bind(ancChild.heightProperty());
            ancChild.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page not found").show();
            e.printStackTrace();
        }
    }

    private void studentNavigationTo(String path) {
        try {
            ancParent.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/" + path));
            anchorPane.prefWidthProperty().bind(ancParent.widthProperty());
            anchorPane.prefHeightProperty().bind(ancParent.heightProperty());
            ancParent.getChildren().add(anchorPane);
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Page not found").show();
            e.printStackTrace();
        }
    }

    public void mouseEnter(Button btn){

        btn.setStyle(
                "-fx-background-color: #dfe4ea;" +
                        "-fx-background-radius: 15;" +
                        "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;"
        );
    }

    public void mouseExit(Button btn){

        btn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-background-radius: 15;" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-weight: normal;"
        );
    }

    @FXML
    void goCourseBtn(ActionEvent event) {

        navigateTo("view/course.fxml");
    }

    @FXML
    void goEnrolmentBtn(ActionEvent event) {

        navigateTo("view/enrolment.fxml");
    }

    @FXML
    void goInstructorBtn(ActionEvent event) {

        navigateTo("view/instructor.fxml");
    }

    @FXML
    void goStudentbtn(ActionEvent event) {

        studentNavigationTo("view/student.fxml");
    }

    public void goPaymentBtn(ActionEvent actionEvent) {

        navigateTo("view/pyment.fxml");
    }

    public void goLessonBtn(ActionEvent actionEvent) {

        navigateTo("view/lesson.fxml");
    }

    public void btnLessontIn(MouseEvent mouseEvent) {

        mouseEnter(btnLesson);
    }

    public void btnLessonOut(MouseEvent mouseEvent) {

        mouseExit(btnLesson);
    }

    public void goUserBtn(ActionEvent actionEvent) {
        navigateTo("view/user.fxml");
    }

    public void btnUserIn(MouseEvent mouseEvent) {
        mouseEnter(btnUser);
    }

    public void btnUserOut(MouseEvent mouseEvent) {
        mouseExit(btnUser);
    }
}
