package clientSubComponents.login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class LoginMain extends Application {
    public static void main(String[] args) {
        Thread.currentThread().setName("main");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/clientSubComponents/login/loginComponent.fxml");
        fxmlLoader.setLocation(url);
        Parent root = fxmlLoader.load(url.openStream());

        // Get the AppController instance from the FXMLLoader
        LoginController loginController = fxmlLoader.getController();

        // Pass the primaryStage to the AppController
        loginController.setPrimaryStage(primaryStage);

        Scene scene = new Scene(root, 395, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}