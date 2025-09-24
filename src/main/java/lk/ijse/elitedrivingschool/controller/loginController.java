package lk.ijse.elitedrivingschool.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.elitedrivingschool.bo.custom.BOFactory;
import lk.ijse.elitedrivingschool.bo.custom.UserBO;
import lk.ijse.elitedrivingschool.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.util.Optional;

public class loginController {

    @FXML
    private Button loginBtn;

    @FXML
    private TextField password;

    @FXML
    private TextField userName;

    @FXML
    private AnchorPane ancParent;

    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.USER);

    @FXML
    void btnGoHomePage(ActionEvent event) {

        String usernameInput = userName.getText().trim();
        String passwordInput = password.getText().trim();

        if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Login Error", "Username and password cannot be empty");
            return;
        }

        try {
            Optional<User> optionalUser = userBO.findByUsername(usernameInput);

            if (optionalUser.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Login Error", "User not found");
                return;
            }

            User user = optionalUser.get();

            if (BCrypt.checkpw(passwordInput, user.getPassword())) {

                showAlert(Alert.AlertType.INFORMATION, "Login Success", "Welcome " + user.getUserName());

                switch (user.getRole().toLowerCase()) {
                    case "admin":
                        enableAdminDashboard();
                        break;
                    case "user":
                        enableUserDashboard();
                        break;
                    default:
                        showAlert(Alert.AlertType.ERROR, "Role Error", "Unknown role: " + user.getRole());
                        break;
                }

            } else {
                showAlert(Alert.AlertType.ERROR, "Login Error", "Incorrect password");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", e.getMessage());
        }
    }

    private void enableAdminDashboard() {
        ancParent.setDisable(false);
        System.out.println("Admin logged in - full access granted");
    }

    private void enableUserDashboard() {
        ancParent.setDisable(false);
        System.out.println("User logged in - limited access granted");
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void navigateToDashboard() {
        try {
            ancParent.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/student.fxml"));
            anchorPane.prefWidthProperty().bind(ancParent.widthProperty());
            anchorPane.prefHeightProperty().bind(ancParent.heightProperty());
            ancParent.getChildren().add(anchorPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load dashboard").show();
            e.printStackTrace();
        }
    }
}
