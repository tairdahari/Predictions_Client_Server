package clientSubComponents.results.executionList;//package subComponents.results.executionList;

import clientSubComponents.results.app.ResultsScreenController;
import clientSubComponents.results.executionDetails.ExecutionDetailsController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import utils.DTOListSimulationDetails;
import utils.DTOSimulationDetails;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import static util.Constants.REFRESH_RATE;


public class ExecutionListController implements Initializable, Closeable {
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
    private Thread thread;
    private Timer timer;
    private TimerTask dataRefresher;
    private String simulationName;
    private volatile boolean updating = true; // Use a flag to control updating
    public ExecutionListController() {
    }

    public void setMainController(ResultsScreenController resultsScreenController) {
        this.resultsScreenController = resultsScreenController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
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
    private void updateListExecution(DTOListSimulationDetails allSimulationsExecution) {
        Platform.runLater(() -> {
            updateList(allSimulationsExecution);
        });
    }
    public void refresherExecutionList(String id) {
        dataRefresher = new ExecutionListRefresher(
                resultsScreenController.getMainBodyComponentController().getMainController().currentUserNameProperty().getValue(),
                this::updateListExecution
        , id);
        timer = new Timer();
        timer.schedule(dataRefresher, REFRESH_RATE, REFRESH_RATE);
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/clientSubComponents/results/executionDetails/executionDetails.fxml"));
            Parent executionDetailsContent = fxmlLoader.load();
            ExecutionDetailsController controller = fxmlLoader.getController();
            controller.setMainController(resultsScreenController);
            //controller.setSimulationName(simulationName);
            controller.displaySimulationDetailsThread(newSelection, simulationName);
            resultsScreenController.getExecutionDetailsBox().getChildren().add(executionDetailsContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateList(DTOListSimulationDetails allSimulationsExecution) {
        ObservableList<DTOSimulationDetails> observableList = FXCollections.observableArrayList(allSimulationsExecution.getDtoSimulationDetailsList());
        simulationsTable.setItems(observableList);
    }
    @Override
    public void close() {
        if (dataRefresher != null && timer != null) {
            dataRefresher.cancel();
            timer.cancel();
        }
    }

    public void setSimulationName(String id) {
        simulationName = id;
    }
}
