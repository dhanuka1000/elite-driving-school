package lk.ijse.elitedrivingschool.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.elitedrivingschool.bo.custom.BOFactory;
import lk.ijse.elitedrivingschool.bo.custom.UserBO;
import lk.ijse.elitedrivingschool.dto.UserDTO;
import lk.ijse.elitedrivingschool.dto.tm.UserTM;

import java.util.Optional;
import java.util.regex.Pattern;

public class UserController {

    private UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.USER);
    private static final Pattern ID_PATTERN = Pattern.compile("^L\\d{3}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z\\s]{3,50}$");

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<UserTM, String> colName;

    @FXML
    private TableColumn<UserTM, String> colPassword;

    @FXML
    private TableColumn<UserTM, String> colRole;

    @FXML
    private TableColumn<UserTM, String> colUserId;

    @FXML
    private TableView<UserTM> tblUser;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtRole;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUserName;

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

            txtUserName.setText("");
            txtPassword.setText("");
            txtRole.setText("");

        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong, please try again").show();
        }
    }

    private void setCellValueFactory() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

    private void loadTableData() {
        try {
            ObservableList<UserTM> obList = FXCollections.observableArrayList();
            for (UserDTO dto : userBO.getAllUsers()) {
                obList.add(new UserTM(
                        dto.getUserId(),
                        dto.getUserName(),
                        dto.getPassword(),
                        dto.getRole()
                ));
            }
            tblUser.setItems(obList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load user: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private void loadNextId() {
        try {
            txtUserId.setText(userBO.getNextId());
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

        String name = txtUserName.getText().trim();
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
        UserTM selected = tblUser.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtUserId.setText(selected.getUserId());
            txtUserName.setText(selected.getUserName());
            txtPassword.setText(selected.getPassword());
            txtRole.setText(selected.getRole());

            btnAdd.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    void addPatientBtn(ActionEvent event) {

        if (!validateAllFields()) return;

        try {
            UserDTO dto = new UserDTO(
                    txtUserId.getText(),
                    txtUserName.getText(),
                    txtPassword.getText(),
                    txtRole.getText()
            );

            if (userBO.saveUsers(dto)) {
                showSuccess("User saved successfully!");
                loadTableData();
            } else {
                showError("Failed to save user");
            }
        } catch (Exception e) {
            showError("Error saving user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void deletePatientBtn(ActionEvent event) {

        String userId = txtUserId.getText();
        if (userId.isEmpty()) {
            showError("Please select a user to delete");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete user " + userId + "?");
        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                if (userBO.deleteUsers(userId)) {
                    showSuccess("User deleted successfully!");
                    resetPage();
                } else {
                    showError("Failed to delete user");
                }
            } catch (Exception e) {
                showError("Error deleting user: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @FXML
    void updatePatientBtn(ActionEvent event) {

        if (!validateAllFields()) return;

        try {
            UserDTO dto = new UserDTO(
                    txtUserId.getText(),
                    txtUserName.getText(),
                    txtPassword.getText(),
                    txtRole.getText()
            );

            if (userBO.updateUsers(dto)) {
                showSuccess("User updated successfully!");
                resetPage();
            } else {
                showError("Failed to update user");
            }
        } catch (Exception e) {
            showError("Error updating user: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
