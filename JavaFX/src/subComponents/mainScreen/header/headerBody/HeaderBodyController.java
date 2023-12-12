//package subComponents.mainScreen.header.headerBody;
//
//import javafx.application.Platform;
//import javafx.beans.property.SimpleBooleanProperty;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.Node;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.stage.FileChooser;
//import javafx.stage.Stage;
//import prediction.engineManager.IEngineManager;
//import subComponents.mainScreen.header.HeaderController;
//import utils.DTOQueue;
//
//import java.io.File;
//
//public class HeaderBodyController {
//    private IEngineManager engineManager;
//    private HeaderController mainHeaderController;
//    @FXML
//    private Label statusFileXML;
//    @FXML
//    private Label filePath;
//    @FXML
//    private Button loadFileButton;
//    @FXML
//    private Label workingThreads;
//    @FXML
//    private Label completedSimulations;
//    @FXML
//    private Label queueSize;
//    private SimpleBooleanProperty loadFileButtonProperty;
//    private SimpleBooleanProperty isFileCorrectProperty;
//    private Thread thread;
//    public HeaderBodyController() {
//        loadFileButtonProperty = new SimpleBooleanProperty(false);
//        isFileCorrectProperty = new SimpleBooleanProperty(false);
//    }
//
//    public void setMainController(HeaderController mainController) {
//        this.mainHeaderController = mainController;
//    }
//
//    public void setSystemEngine(IEngineManager engineManager) {
//        this.engineManager = engineManager;
//    }
//    @FXML
//    void loadFileButtonAction(ActionEvent event) {
//        while (true) {
//            engineManager.initialSystem();
//            FileChooser fileChooser = new FileChooser();
//            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
//            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            File selectedFile = fileChooser.showOpenDialog(stage);
//
//            if (selectedFile != null) {
//                String absolutePath = selectedFile.getAbsolutePath();
//                boolean isXMLFile = absolutePath.toLowerCase().endsWith(".xml");
//                loadFileButtonProperty.set(true);
//
//                if (!isXMLFile || !checkFileValidity(selectedFile)) {
//                    continue;
//                }
//
//                // File is valid, proceed with your logic for a valid file
//                filePath.setText(selectedFile.getPath());
//                filePath.setVisible(true);
//                statusFileXML.setText("File is correct");
//                statusFileXML.setVisible(true);
//                isFileCorrectProperty.set(true);
//                mainHeaderController.updatePushTabButtons();
//                loadFileButtonProperty.set(false);
//
//                mainHeaderController.getMainController().getBodyComponentController().setDetails();
//                break;
//            } else {
//                break;
//            }
//        }
//
//        // The following code should be outside the while loop
//        Thread thread = new Thread(() -> {
//            int SLEEP_TIME = 200;
//            while (true) {
//                Platform.runLater(() -> {
//                    queueUpdate();
//                });
//                try {
//                    Thread.sleep(SLEEP_TIME);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//
//            }
//        });
//        thread.setDaemon(true); // Make the thread a daemon thread so it doesn't block application exit
//        thread.start();
//    }
//
//
//
//    private boolean checkFileValidity(File selectedFile) {
//        try {
//            engineManager.readingSystemInformationFromFileJavaFX(selectedFile);
//            return true; // File is valid
//        } catch (RuntimeException e) {
//            statusFileXML.setText("Invalid file");
//            statusFileXML.setVisible(true);
//            loadErrorFxml(e);
//            throw new IllegalArgumentException(e);
//        }
//    }
//
//    private void loadErrorFxml(RuntimeException e) {
//        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
//        errorAlert.setTitle("Error Alert");
//        errorAlert.setHeaderText("File Error");
//        errorAlert.setContentText(String.valueOf(e));
//        errorAlert.showAndWait();
//    }
//
//    public SimpleBooleanProperty getLoadFileButtonProperty() {
//        return this.loadFileButtonProperty;
//    }
//
//    public SimpleBooleanProperty getIsFileCorrectProperty() {
//        return this.isFileCorrectProperty;
//    }
//
//    public Button getLoadFileButton() {
//        return loadFileButton;
//    }
//
//    public void queueUpdate() {
//        DTOQueue dtoQueue = engineManager.getDtoQueue();
//        queueSize.setText(dtoQueue.getQueueSize().toString());
//        workingThreads.setText(dtoQueue.getWaiting().toString());
//        completedSimulations.setText(dtoQueue.getFinished().toString());
//    }
//}