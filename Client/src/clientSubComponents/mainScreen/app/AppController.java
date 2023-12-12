package clientSubComponents.mainScreen.app;


import clientSubComponents.mainScreen.body.BodyController;
import clientSubComponents.mainScreen.header.HeaderController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class AppController {

    @FXML
    private VBox headerComponent;
    @FXML
    private TabPane bodyComponent;
    @FXML private HeaderController headerComponentController;
    @FXML private BodyController bodyComponentController;
    private Stage primaryStage;
    private StringProperty currentUserName;

//    private IEngineManager engineManager = new EngineManager();

    @FXML
    public void initialize() {
        if (headerComponentController != null && bodyComponentController != null) {
            headerComponentController.setMainController(this);
            //headerComponentController.setSystemEngine(engineManager);
            bodyComponentController.setMainController(this);
           // bodyComponentController.setEngineManager(engineManager);
        }
    }

    public AppController() {
        currentUserName = new SimpleStringProperty("");
    }
    public StringProperty currentUserNameProperty() {
        return currentUserName;
    }
    public void updateUserName(String userName) {
        currentUserName.set(userName);
        headerComponentController.getClientName().setText(userName);
    }
    public void setBody(){
        bodyComponentController.setMainController(this);
        //bodyComponentController.setEngineManager(engineManager);
    }
    public void updatePushTabButtons()
    {
        bodyComponentController.updatePushTabButtons();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public HeaderController getHeaderComponentController() {
        return headerComponentController;
    }

    public BodyController getBodyComponentController() {
        return bodyComponentController;
    }

    public void openTabClientDefinition() {
        bodyComponentController.openTabClientDefinition();
    }
}