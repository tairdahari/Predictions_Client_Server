package subComponents.results.executionDetails;//package subComponents.results.executionDetails;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import subComponents.results.app.ResultsScreenController;
import subComponents.results.executionError.ExecutionErrorController;
import subComponents.results.executionResult.ExecutionResultController;
import utils.DTOEntityExecution;
import utils.DTOSimulationDetails;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ExecutionDetailsController {
    @FXML
    private VBox buttonsVBox;

    @FXML
    private TableView<DTOEntityExecution> entitiesTable;

    @FXML
    private TableColumn<DTOEntityExecution, String> entityName;

    @FXML
    private TableColumn<DTOEntityExecution, String> quantity;

    @FXML
    private Label runTimeLabel;

    @FXML
    private Label tickLabel;

    @FXML
    private Button reRunButton;

    private DTOSimulationDetails selectedSimulation;

    private ResultsScreenController resultsScreenController;

    private Thread thread;

    @FXML
    public void initialize() {
        entityName.setCellValueFactory(new PropertyValueFactory<DTOEntityExecution, String>("entityName"));
        quantity.setCellValueFactory(new PropertyValueFactory<DTOEntityExecution, String>("quantity"));
    }
    public void setMainController(ResultsScreenController resultsScreenController) {
        this.resultsScreenController = resultsScreenController;
    }

//    public void displaySimulationDetailsThread(DTOSimulationDetails newSelection, IEngineManager engineManager) throws IOException {
//        selectedSimulation = newSelection;
//        if (selectedSimulation != null) {
//            thread = new Thread(() -> {
//                Boolean stop = false;
//                int SLEEP_TIME = 200;
//                eSimulationState simulationState = eSimulationState.RUNNING;
//
//                while (!stop && !simulationState.equals(eSimulationState.STOPPED)) {
//                    try {
//                        DTOSimulationDetails simulationDetails = engineManager.getDtoSimulationDetailsByID(Integer.parseInt(selectedSimulation.getId()));
//                        Platform.runLater(() -> {
//                            setEntitiesTable(simulationDetails.getDtoEntityListExecution());
//                            runTimeLabel.setText(simulationDetails.getCurrTime());
//                            tickLabel.setText(simulationDetails.getCurrTick());
//                        });
//                        simulationState = simulationDetails.getInProgress();
//                        Thread.sleep(SLEEP_TIME);
//                    } catch (NumberFormatException | InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            });
//
//            if (selectedSimulation.getInProgress().equals(eSimulationState.RUNNING) ||
//                    selectedSimulation.getInProgress().equals(eSimulationState.PAUSED)) {
//                String string = null;
//            }
//                //loadControlButtons(engineManager); //TODO load control buttons
//            else {
//                try {
//                    loadResultFxml(engineManager);
//                    loadErrorFxml(engineManager);
//                } catch (IOException e) {
//
//                }
//            }
//            thread.setDaemon(true);
//            thread.start();
//        }
//    }

//    private void loadErrorFxml(IEngineManager engineManager) throws IOException {
//        String errorForDisplay = engineManager.getErrorMessage(selectedSimulation);
//        if( errorForDisplay !=  null) {
//            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
//            errorAlert.setTitle("Error Alert");
//            errorAlert.setHeaderText("Simulation Exception");
//            errorAlert.setContentText(errorForDisplay);
//            errorAlert.showAndWait();
//        }
//    }
//
//    public void loadResultFxml(IEngineManager engineManager) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/subComponents/results/executionResult/executionResult.fxml"));
//        Parent executionResultContent = fxmlLoader.load();
//        ExecutionResultController resultController = fxmlLoader.getController();
//        resultController.setEngineManager(engineManager);
//        resultController.initTree(engineManager.getSimulationDefinition().getDtoEntityDefinition());
//        resultsScreenController.getExecutionResultsBox().getChildren().clear();
//        resultsScreenController.getExecutionResultsBox().getChildren().add(executionResultContent);
//
//    }

//    private void loadControlButtons(IEngineManager engineManager) {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/subComponents/results/executionDetails/controlButtons/controlButtons.fxml"));
//            Parent controlButtonsContent = fxmlLoader.load();
//            ControlButtonsController controller = fxmlLoader.getController();
//            controller.setCurrId(selectedSimulation.getId());
//            controller.setEngineManager(engineManager);
//            controller.setMainController(this);
//            buttonsVBox.getChildren().add(controlButtonsContent);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private void setEntitiesTable(List<DTOEntityExecution> dtoEntityListExecution) {
        ObservableList<DTOEntityExecution> observableList = FXCollections.observableArrayList(dtoEntityListExecution);
        entitiesTable.setItems(observableList);
    }
    @FXML
    void reRunButtonAction(ActionEvent event) {
        //resultsScreenController..isRerunClicked(selectedSimulation.getId());
    }

}

