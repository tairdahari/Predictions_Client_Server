package clientSubComponents.mainScreen.header;

import clientSubComponents.mainScreen.app.AppController;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class HeaderController {

//    private IEngineManager engineManager;
    private AppController mainController;
    @FXML
    private Label clientName;
    @FXML
    private Label titleLabel;
    private RotateTransition rotateTransition;

    @FXML
    public void initialize() {
    }
    public void updatePushTabButtons() {
        mainController.updatePushTabButtons();
    }
    public AppController getMainController() {
        return mainController;
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }
    @FXML
    void colorSetsAction(){
        openSettingsWindow();
    }
    private void openSettingsWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = getClass().getResource("/subComponents/mainScreen/header/setting/setting.fxml");
            fxmlLoader.setLocation(url);
            Parent root = fxmlLoader.load();
            Stage settingsStage = new Stage();
            settingsStage.setTitle("Settings");
            settingsStage.setScene(new Scene(root, 590, 500));
            settingsStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }

    public Label getClientName() {
        return clientName;
    }

    public void setClientName(Label clientName) {
        this.clientName = clientName;
    }
}