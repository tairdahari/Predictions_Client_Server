package subComponents.results.executionDetails.controlButtons;//package subComponents.results.executionDetails.controlButtons;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import prediction.engineManager.IEngineManager;
//import subComponents.results.executionDetails.ExecutionDetailsController;
//
//import java.io.IOException;
//
//public class ControlButtonsController {
//
//    @FXML private Button pauseButton;
//    @FXML private Button resumeButton;
//    @FXML private Button stopButton;
//    private IEngineManager engineManager;
//    private String currSimulationId;
//    private ExecutionDetailsController executionDetailsController;
//
//    public void setEngineManager(IEngineManager engineManager) {
//        this.engineManager = engineManager;
//    }
//    public void  setMainController(ExecutionDetailsController executionDetailsController) {this.executionDetailsController = executionDetailsController;}
//
//
//    @FXML
//    void pauseButtonAction(ActionEvent event) {
//        //pausedTime = System.currentTimeMillis();
//        engineManager.pause(currSimulationId);
//    }
//
//    @FXML
//    void resumeButtonAction(ActionEvent event) {
//
//        engineManager.resume(currSimulationId);
//
////        while (engineManager.isPaused(currSimulationId)) {
////            try {
////                Thread.sleep(100);
////            } catch (InterruptedException e) {
////                Thread.currentThread().interrupt();
////            }
////        }
//    }
//    @FXML
//    void stopButtonAction(ActionEvent event) throws IOException {
//        engineManager.stop(currSimulationId);
//        setControlButtonsVisible();
//        executionDetailsController.loadResultFxml(engineManager);
//
//    }
//
//    private void setControlButtonsVisible() {
//        stopButton.setVisible(false);
//        pauseButton.setVisible(false);
//        resumeButton.setVisible(false);
//    }
//
//    public void setCurrId(String id) {
//        currSimulationId = id;
//    }
//}