//package subComponents.mainScreen.app;
//
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//import java.net.URL;
//
//public class Main extends Application {
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        primaryStage.setTitle("Predictions");
//
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        URL url = getClass().getResource("app.fxml");
//        fxmlLoader.setLocation(url);
//        Parent root = fxmlLoader.load(); //url.openStream()
//        // Get the AppController instance from the FXMLLoader
//        AppController appController = fxmlLoader.getController();
//        appController.setAnimation();
//
//        // Pass the primaryStage to the AppController
//        appController.setPrimaryStage(primaryStage);
//        Scene scene  = new Scene(root, 1200, 800);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//}