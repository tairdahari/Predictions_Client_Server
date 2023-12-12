package subComponents.results.executionList;//package subComponents.results.executionList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import subComponents.results.app.ResultsScreenController;
import subComponents.results.executionDetails.ExecutionDetailsController;
import utils.DTOListSimulationDetails;
import utils.DTOSimulationDetails;

import java.io.IOException;


public class ExecutionListController {
    @FXML
    private TableView<DTOSimulationDetails> simulationsTable;
    @FXML
    private TableColumn<DTOSimulationDetails, String> id;
    @FXML
    private TableColumn<DTOSimulationDetails, String> inProgress;
    @FXML
    private TableColumn<DTOSimulationDetails, String> endTime;
    @FXML
    private TableColumn<DTOSimulationDetails, String> startTime;
    private ResultsScreenController resultsScreenController;
    private DTOSimulationDetails currentSimulationDetails;
    private DTOListSimulationDetails dtoListSimulationDetails;
//    private IEngineManager engineManager;
   // private final Logic logic = new Logic(this);
    private Thread thread;
    private volatile boolean updating = true; // Use a flag to control updating

    public ExecutionListController() {
    }

    public void setMainController(ResultsScreenController resultsScreenController) {
        this.resultsScreenController = resultsScreenController;
    }

    @FXML
    public void initialize(){
        id.setCellValueFactory(new PropertyValueFactory<DTOSimulationDetails, String>("id"));
        inProgress.setCellValueFactory(new PropertyValueFactory<DTOSimulationDetails, String>("inProgress"));
        startTime.setCellValueFactory(new PropertyValueFactory<DTOSimulationDetails, String>("startTime"));
        endTime.setCellValueFactory(new PropertyValueFactory<DTOSimulationDetails, String>("endTime"));

        simulationsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                handleRowClick(newSelection);
            }
        });
    }
    public void updateListThread() {
        int SLEEP_TIME = 200;

        thread= new Thread(()->{
            while(true) {
                try {
                    if (updating) {
                        dtoListSimulationDetails = null;
                        //dtoListSimulationDetails = engineManager.getAllSimulationsExecution();
                        Platform.runLater(() -> {
                            updateList(dtoListSimulationDetails);
                        });
                    }
                    Thread.sleep(SLEEP_TIME);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.setDaemon(true); // Make the thread a daemon thread so it doesn't block application exit
        thread.start();
    }
    public void pauseUpdating() {
        updating = false; // Call this method when you want to pause updating (e.g., when on the result screen)
    }

    public void resumeUpdating() {
        updating = true; // Call this method when you want to resume updating (e.g., when back to the execution list screen)
    }
    public void handleRowClick(DTOSimulationDetails newSelection) {
        this.currentSimulationDetails = newSelection;
        try {
            resultsScreenController.clearExecutionDetails();
            resultsScreenController.clearExecutionResults();
            resultsScreenController.getExecutionDetailsBox().getChildren().clear();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/subComponents/results/executionDetails/executionDetails.fxml"));
            Parent executionDetailsContent = fxmlLoader.load();
            ExecutionDetailsController controller = fxmlLoader.getController();
            controller.setMainController(resultsScreenController);
            //controller.displaySimulationDetailsThread(newSelection, engineManager);
            resultsScreenController.getExecutionDetailsBox().getChildren().add(executionDetailsContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//    public void setEngineManager(IEngineManager engineManager) {
//        this.engineManager = engineManager;
//    }
    public void updateList(DTOListSimulationDetails allSimulationsExecution) {
        ObservableList<DTOSimulationDetails> observableList = FXCollections.observableArrayList(allSimulationsExecution.getDtoSimulationDetailsList());
        simulationsTable.setItems(observableList);
    }
}
