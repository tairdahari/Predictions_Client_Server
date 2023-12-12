//package subComponents.mainScreen.app;
//
//import javafx.fxml.FXML;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.control.TabPane;
//import javafx.scene.control.TreeView;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import prediction.engineManager.EngineManager;
//import prediction.engineManager.IEngineManager;
//import subComponents.detailsScreen.leftScreen.TreeViewController;
//import subComponents.mainScreen.body.BodyController;
//import subComponents.mainScreen.header.HeaderController;
//
//public class AppController {
//
//    @FXML
//    private VBox headerComponent;
//    @FXML
//    private TabPane bodyComponent;
//    @FXML private HeaderController headerComponentController;
//    @FXML private BodyController bodyComponentController;
//    private Stage primaryStage;
//    private IEngineManager engineManager = new EngineManager();
//
//    @FXML
//    public void initialize() {
//        if (headerComponentController != null && bodyComponentController != null) {
//            headerComponentController.setMainController(this);
//            headerComponentController.setSystemEngine(engineManager);
//            bodyComponentController.setMainController(this);
//            bodyComponentController.setEngineManager(engineManager);
//        }
//    }
//    public void setBody(){
//        bodyComponentController.setMainController(this);
//        bodyComponentController.setEngineManager(engineManager);
//    }
//    public void updatePushTabButtons()
//    {
//        bodyComponentController.updatePushTabButtons();
//    }
//
//    public Stage getPrimaryStage() {
//        return primaryStage;
//    }
//
//    public void setPrimaryStage(Stage primaryStage) {
//        this.primaryStage = primaryStage;
//    }
//
//    public HeaderController getHeaderComponentController() {
//        return headerComponentController;
//    }
//
//    public BodyController getBodyComponentController() {
//        return bodyComponentController;
//    }
//
//    public void setAnimation() {
//        headerComponentController.rotateLabelAnimation();
//    }
//    public void setColorBackground(String color) {
//        headerComponent.setStyle("-fx-background-color: " + color + ";");
//        bodyComponent.setStyle("-fx-background-color: " + color + ";");
//    }
//}