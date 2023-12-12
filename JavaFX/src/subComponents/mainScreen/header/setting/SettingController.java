//package subComponents.mainScreen.header.setting;
//
//import javafx.animation.FadeTransition;
//import javafx.application.Platform;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.CheckBox;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
//import javafx.scene.Scene;
//import javafx.util.Duration;
//import subComponents.mainScreen.header.HeaderController;
//import subComponents.mainScreen.header.setting.animation.ShanaTovaAnimationController;
//
//public class SettingController {
//
//    private Stage primaryStage;
//    @FXML
//    private CheckBox animationBox;
//    private HeaderController headerController;
//    private ShanaTovaAnimationController animationController;
//    @FXML
//    private AnchorPane optionsTabPane;
//    private Thread thread;
//    private boolean isAnimationRunning = true;
//
//    @FXML
//    void animationBoxChange(ActionEvent event) {
//        thread = new Thread(() -> {
//            int SLEEP_TIME = 1000;
//            while(true) {
//                Platform.runLater( ()-> {
//                    if (animationBox.isSelected()) {
//                        if (!isAnimationRunning) {
//                            headerController.rotateLabelAnimation();
//                            headerController.fadeInAnimation();
//                            isAnimationRunning = true;
//                        }
//                    } else {
//                        if (isAnimationRunning) {
//                            headerController.stopRotateAnimation();
//                            headerController.stopAnimation();
//                            isAnimationRunning = false;
//                        }
//                    }
//                });
//                try {
//                    Thread.sleep(SLEEP_TIME);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//        thread.setDaemon(true);
//        thread.start();
//
//    }
//    public void setAnimationController(ShanaTovaAnimationController animationController) {
//        this.animationController = animationController;
//    }
//    public void setHeaderController( HeaderController headerController) {
//        this.headerController = headerController;
//    }
//    public void setPrimaryStage(Stage primaryStage) {
//        this.primaryStage = primaryStage;
//    }
//
//    @FXML
//    void darkTheme() {
//        applyTheme("#D3D3D3");
//    }
//
//    @FXML
//    void summerTheme() {
//        applyTheme("#F5DEB3");
//    }
//
//    private void applyTheme(String color) {
//        try {
//            Scene scene = primaryStage.getScene();
//            scene.getStylesheets().clear(); // Remove existing stylesheets
//            headerController.getMainController().setColorBackground(color);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
