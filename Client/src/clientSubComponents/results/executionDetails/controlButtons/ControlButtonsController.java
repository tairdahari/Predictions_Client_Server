package clientSubComponents.results.executionDetails.controlButtons;//package subComponents.results.executionDetails.controlButtons;

import clientSubComponents.results.executionDetails.ExecutionDetailsController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import util.Constants;
import util.http.HttpClientUtil;

import java.io.IOException;

public class ControlButtonsController {

    @FXML private Button pauseButton;
    @FXML private Button resumeButton;
    @FXML private Button stopButton;
    private ExecutionDetailsController executionDetailsController;
    public void  setMainController(ExecutionDetailsController executionDetailsController) {
        this.executionDetailsController = executionDetailsController;
    }

    @FXML
    void pauseButtonAction(ActionEvent event) {
        String simulationName = executionDetailsController.getResultsScreenController().getMainBodyComponentController().getSimulationName();
        String serialNumber = executionDetailsController.getResultsScreenController().getMainBodyComponentController().getSimulationSerialNumber();

        String finalUrl = HttpUrl.parse(Constants.PAUSE_SIMULATION)
                .newBuilder()
                .addQueryParameter("simulationName", simulationName)
                .addQueryParameter("serialNumber", serialNumber)
                .build()
                .toString();

        RequestBody requestBody = RequestBody.create("", MediaType.get("application/json"));
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
                        Platform.runLater(() -> {
                        });
                    } else {
                    }
                } finally {
                    response.close();
                }
            }
        });
    }




    @FXML
    void resumeButtonAction(ActionEvent event) {
        String simulationName = executionDetailsController.getResultsScreenController().getMainBodyComponentController().getSimulationName();
        String serialNumber = executionDetailsController.getResultsScreenController().getMainBodyComponentController().getSimulationSerialNumber();

        String finalUrl = HttpUrl.parse(Constants.RESUME_SIMULATION)
                .newBuilder()
                .addQueryParameter("simulationName", simulationName)
                .addQueryParameter("serialNumber", serialNumber)
                .build()
                .toString();

        RequestBody requestBody = RequestBody.create("", MediaType.get("application/json"));
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
                        Platform.runLater(() -> {
                        });
                    } else {
                    }
                } finally {
                    response.close();
                }
            }
        });
    }
    @FXML
    void stopButtonAction(ActionEvent event) throws IOException {
        String simulationName = executionDetailsController.getResultsScreenController().getMainBodyComponentController().getSimulationName();
        String serialNumber = executionDetailsController.getResultsScreenController().getMainBodyComponentController().getSimulationSerialNumber();

        String finalUrl = HttpUrl.parse(Constants.STOP_SIMULATION)
                .newBuilder()
                .addQueryParameter("simulationName", simulationName)
                .addQueryParameter("serialNumber", serialNumber)
                .build()
                .toString();

        RequestBody requestBody = RequestBody.create("", MediaType.get("application/json"));
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
                        Platform.runLater(() -> {
                        });
                    } else {
                    }
                } finally {
                    response.close();
                }
            }
        });
        setControlButtonsVisible();
        executionDetailsController.loadResultFxml(simulationName);
    }

    private void setControlButtonsVisible() {
        stopButton.setVisible(false);
        pauseButton.setVisible(false);
        resumeButton.setVisible(false);
    }

//    public void setCurrId(String id) {
//        serialNumber = id;
//    }

    public void handleFailure(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error In The Server");
        alert.setContentText(errorMessage);
        alert.setWidth(300);
        alert.show();
    }
}