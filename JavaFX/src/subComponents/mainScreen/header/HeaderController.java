//package subComponents.mainScreen.header;
//
//import javafx.animation.Interpolator;
//import javafx.animation.RotateTransition;
//import javafx.animation.TranslateTransition;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Label;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.transform.Rotate;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//import prediction.engineManager.IEngineManager;
//import subComponents.mainScreen.app.AppController;
//import subComponents.mainScreen.header.headerBody.HeaderBodyController;
//import subComponents.mainScreen.header.setting.SettingController;
//
//import subComponents.mainScreen.header.setting.animation.ShanaTovaAnimationController;
//
//import java.io.IOException;
//import java.net.URL;
//
//public class HeaderController {
//
//    private IEngineManager engineManager;
//    private AppController mainController;
//    @FXML
//    private HeaderBodyController headerBodyComponentController;
//    @FXML
//    private AnchorPane headerBodyComponent;
//    @FXML
//    private Label titleLabel;
//    private RotateTransition rotateTransition;
//
//    @FXML
//    public void initialize() {
//        headerBodyComponentController.setMainController(this);
//    }
//    public void updatePushTabButtons() {
//        mainController.updatePushTabButtons();
//    }
//    public AppController getMainController() {
//        return mainController;
//    }
//    public void setMainController(AppController mainController) {
//        this.mainController = mainController;
//    }
//    @FXML
//    void colorSetsAction(){
//        openSettingsWindow();
//    }
//    private void openSettingsWindow() {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader();
//            URL url = getClass().getResource("/subComponents/mainScreen/header/setting/setting.fxml");
//            fxmlLoader.setLocation(url);
//            Parent root = fxmlLoader.load();
//            SettingController settingController = fxmlLoader.getController();
//            settingController.setPrimaryStage(mainController.getPrimaryStage());
//            settingController.setHeaderController(this);
//            ShanaTovaAnimationController shanaTovaAnimationController = new ShanaTovaAnimationController();
//            settingController.setAnimationController(shanaTovaAnimationController);
//            Stage settingsStage = new Stage();
//            settingsStage.setTitle("Settings");
//            settingsStage.setScene(new Scene(root, 590, 500));
//            settingsStage.show();
//        } catch (IOException e) {
//            e.printStackTrace(); // Handle the exception as needed
//        }
//    }
//
//    public void setSystemEngine(IEngineManager engineManager) {
//        this.engineManager = engineManager;
//        this.headerBodyComponentController.setSystemEngine(engineManager);
//    }
//    public HeaderBodyController getHeaderBodyComponentController() {
//        return headerBodyComponentController;
//    }
//
//    public void fadeInAnimation() {
//        mainController.getBodyComponentController().fadeAnimation();
//    }
//
//    public void rotateLabelAnimation() {
//        rotateTransition = new RotateTransition(Duration.millis(2500), titleLabel);
//        rotateTransition.setByAngle(360);
//        rotateTransition.setCycleCount(TranslateTransition.INDEFINITE);
//        rotateTransition.setInterpolator(Interpolator.LINEAR);
//        rotateTransition.setAxis(Rotate.Y_AXIS);
//        rotateTransition.setAutoReverse(true); // Automatically reverse when stopped
//        rotateTransition.play();
//    }
//
//    public void stopRotateAnimation() {
//        if(rotateTransition != null) {
//            rotateTransition.stop();
//            titleLabel.setRotate(0);
//        }
//    }
//
//    public void stopAnimation() {
//        mainController.getBodyComponentController().stopAnimation();
//    }
//}
