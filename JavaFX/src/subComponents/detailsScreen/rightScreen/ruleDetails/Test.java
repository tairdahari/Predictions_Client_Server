package subComponents.detailsScreen.rightScreen.ruleDetails;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Test extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("header");

        Parent load = FXMLLoader.load(getClass().getResource("ruleDetails.fxml"));
        Scene scene = new Scene(load, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(Test.class);
    }

}