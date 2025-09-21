package lk.ijse.elitedrivingschool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent load = FXMLLoader.load((getClass().getResource("/view/student.fxml")));
        stage.setScene(new Scene(load));
        stage.setTitle("Center");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}