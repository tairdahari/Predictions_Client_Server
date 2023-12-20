package clientSubComponents.results.executionDetails;//package subComponents.results.executionDetails;

import clientSubComponents.results.app.ResultsScreenController;
import clientSubComponents.results.executionDetails.controlButtons.ControlButtonsController;
import clientSubComponents.results.executionResult.ExecutionResultController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import prediction.execution.runner.eSimulationState;
import util.Constants;
import util.http.HttpClientUtil;
import utils.*;

import java.io.IOException;
import java.util.List;

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

    public ResultsScreenController getResultsScreenController() {
        return resultsScreenController;
    }
    public void displaySimulationDetailsThread(DTOSimulationDetails newSelection, String simulationName) throws IOException {
        String finalUrl = HttpUrl
                .parse(Constants.SIMULATION_DETAILS)
                .newBuilder()
                .addQueryParameter("simulationId", newSelection.getId())
                .addQueryParameter("id", simulationName)
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                    handleFailure(e.getMessage());
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        selectedSimulation = newSelection;
                        if (selectedSimulation != null) {
                            Boolean stop = false;
                            eSimulationState simulationState = eSimulationState.RUNNING;

                            if (selectedSimulation.getInProgress().equals(eSimulationState.RUNNING) ||
                                    selectedSimulation.getInProgress().equals(eSimulationState.PAUSED)) {
                                // Load control buttons on the JavaFX Application Thread
                                Platform.runLater(() -> {
                                    loadControlButtons();
                                });
                            } else {
                                try {
                                    // Add your code for the 'else' section here
                                    loadResultFxml(simulationName );
                                    loadErrorFxml(simulationName);
                                } catch (IOException e) {
                                    // Handle any exceptions
                                }
                            }

                            while (!stop && !simulationState.equals(eSimulationState.STOPPED)) {
                                try {
                                    Gson gson = new GsonBuilder()
                                            .registerTypeAdapter(DTOActionDetails.class, new DTOActionDetailsDeserializer())
                                            .create();
                                    DTOSimulationDetails dtoSimulationDetails = gson.fromJson(responseData, DTOSimulationDetails.class);

                                    // Update UI on the JavaFX Application Thread
                                    Platform.runLater(() -> {
                                        setEntitiesTable(dtoSimulationDetails.getDtoEntityListExecution());
                                        runTimeLabel.setText(dtoSimulationDetails.getCurrTime());
                                        tickLabel.setText(dtoSimulationDetails.getCurrTick());
                                    });

                                    simulationState = dtoSimulationDetails.getInProgress();
                                } catch (NumberFormatException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                } finally {
                    response.close();
                }
            }


        });
    }


    public void handleFailure(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error In The Server");
        alert.setContentText(errorMessage);
        alert.setWidth(300);
        alert.show();
    }

    private void loadErrorFxml(String simulationName) throws IOException { //ERROR_MESSAGE

        // Serialize dtoExecutionListsDataMember to JSON
        Gson gson = new Gson();
        String dtoJson = gson.toJson(selectedSimulation);

        // Build the URL
//        String finalUrl = HttpUrl
//                .parse(Constants.ERROR_MESSAGE)
//                .toString();  // No need to add query parameters here

        HttpUrl.Builder urlBuilder = HttpUrl
                .parse(Constants.ERROR_MESSAGE)
                .newBuilder();

        // Add the "id" query parameter
        urlBuilder.addQueryParameter("id", simulationName);

        String finalUrl = urlBuilder.build().toString();


        // Create an HTTP request with the JSON payload
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json"), dtoJson);
        Request request = new Request.Builder()
                .url(finalUrl)
                .post(requestBody)
                .build();

        // Send the request asynchronously
        HttpClientUtil.runAsyncPost(finalUrl, requestBody, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                    handleFailure(e.getMessage());
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();

                        String errorForDisplay = new Gson().fromJson(responseData, String.class);

                        Platform.runLater(() -> {
                            if( errorForDisplay !=  null) {
                                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                                errorAlert.setTitle("Error Alert");
                                errorAlert.setHeaderText("Simulation Exception");
                                errorAlert.setContentText(errorForDisplay);
                                errorAlert.showAndWait();
                            }                        });
                    } else {
                        // Handle non-successful response if needed
                    }
                } finally {
                    // If there are any cleanup or resource release tasks, you can put them here
                    response.close();
                }
            }
        });
    }

    public void loadResultFxml(String simulationName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/clientSubComponents/results/executionResult/executionResult.fxml"));
        Parent executionResultContent = fxmlLoader.load();
        ExecutionResultController resultController = fxmlLoader.getController();
        resultController.setSerialNumber(this.resultsScreenController.getMainBodyComponentController().getSimulationSerialNumber());
        resultController.setSimulationName(this.resultsScreenController.getMainBodyComponentController().getSimulationName());
        String finalUrl = HttpUrl
                .parse(Constants.SIMULATION_DEFINITION)
                .newBuilder()
                .addQueryParameter("id", simulationName)
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                    handleFailure(e.getMessage());
                });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        Gson gson = new GsonBuilder()
                                .registerTypeAdapter(DTOActionDetails.class, new DTOActionDetailsDeserializer())
                                .create();
                        DTOSimulationDefinition dtoSimulationDefinition = gson.fromJson(responseData, DTOSimulationDefinition.class);

                        Platform.runLater(() -> {
                            resultController.initTree(dtoSimulationDefinition.getDtoEntityDefinition());
                            resultsScreenController.getExecutionResultsBox().getChildren().clear();
                            resultsScreenController.getExecutionResultsBox().getChildren().add(executionResultContent);
                        });
                    }
                } finally {
                    // If there are any cleanup or resource release tasks, you can put them here
                    response.close();
                }
            }
        });
    }

    private void loadControlButtons() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/clientSubComponents/results/executionDetails/controlButtons/controlButtons.fxml"));
            Parent controlButtonsContent = fxmlLoader.load();
            ControlButtonsController controller = fxmlLoader.getController();
            //controller.setCurrId(selectedSimulation.getId());
            //controller.setEngineManager(engineManager);
            controller.setMainController(this);
            System.out.println("hykhjhkhkj");

            buttonsVBox.getChildren().add(controlButtonsContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setEntitiesTable(List<DTOEntityExecution> dtoEntityListExecution) {
        ObservableList<DTOEntityExecution> observableList = FXCollections.observableArrayList(dtoEntityListExecution);
        entitiesTable.setItems(observableList);
    }
    @FXML
    void reRunButtonAction(ActionEvent event) {
        resultsScreenController.isRerunClicked(selectedSimulation.getId());
    }
}