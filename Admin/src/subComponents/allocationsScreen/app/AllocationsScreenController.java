package subComponents.allocationsScreen.app;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import subComponents.mainScreen.body.BodyController;
import util.Constants;
import util.http.HttpClientUtil;
import utils.DTOClientChosenSimulation;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static util.Constants.REFRESH_RATE;

public class AllocationsScreenController implements Closeable {
    @FXML
    private TableView<DTOClientChosenSimulation> allClientsRequests;

    @FXML
    private TableColumn<DTOClientChosenSimulation, String> amountToRun;

    @FXML
    private TableColumn<DTOClientChosenSimulation, String> clientName;

    @FXML
    private TableColumn<DTOClientChosenSimulation, String> endedSimulationNumber;

    @FXML
    private TableColumn<DTOClientChosenSimulation, String> requestStatus;

    @FXML
    private TableColumn<DTOClientChosenSimulation, String> runningSimulationsNumber;

    @FXML
    private TableColumn<DTOClientChosenSimulation, String> serialNumber;

    @FXML
    private TableColumn<DTOClientChosenSimulation, String> simulationName;

    @FXML
    private TableColumn<DTOClientChosenSimulation, String> termination;
    private Thread thread;
    private BodyController mainController;
    private TimerTask dataRefresher;
    private Timer timer;
    private DTOClientChosenSimulation clientChosenSimulation;
    private final ObservableList<DTOClientChosenSimulation> requestsDataObsList = FXCollections.observableArrayList();


    @FXML
    private void initialize() {
        serialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        simulationName.setCellValueFactory(new PropertyValueFactory<>("simulationName"));
        amountToRun.setCellValueFactory(new PropertyValueFactory<>("amountToRun"));
        requestStatus.setCellValueFactory(new PropertyValueFactory<>("requestStatus"));
        runningSimulationsNumber.setCellValueFactory(new PropertyValueFactory<>("runningSimulationsNumber"));
        endedSimulationNumber.setCellValueFactory(new PropertyValueFactory<>("endedSimulationNumber"));
        clientName.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        termination.setCellValueFactory(new PropertyValueFactory<>("termination"));

        allClientsRequests.setItems(requestsDataObsList);
        allClientsRequests.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        allClientsRequests.getSelectionModel().selectedItemProperty().addListener((obs, oldSelect, newSelect) ->  {
            if(newSelect != null) {
                clientChosenSimulation = newSelect;
            }
        });
    }


    private void handleApprovedRequest(DTOClientChosenSimulation clientChosenSimulation, String selectedStatus) {
        //maybe sending a message if the admin sure about this approval if he does so disable the line.

        String finalUrl = HttpUrl
                .parse(Constants.REQUEST_STATUS)
                .newBuilder()
                .build()
                .toString();

        // Create the request body with parameters
        FormBody requestBody = new FormBody.Builder()
                .add("serialNumber", clientChosenSimulation.getSerialNumber())
                .add("clientName", clientChosenSimulation.getClientName())
                .add("status", selectedStatus)
                .build();

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
                    }
                } finally {
                    // If there are any cleanup or resource release tasks, you can put them here
                    response.close();
                }
            }
        });
    }

    public void handleFailure(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error In The Server");
        alert.setContentText(errorMessage);
        alert.setWidth(300);
        alert.show();
    }

    public void updateTableThread() {
        dataRefresher = new AllocationTableRefresher(
                this::updateTable
                );
        timer = new Timer();
        timer.schedule(dataRefresher, REFRESH_RATE, REFRESH_RATE);
    }

    public void updateTable(Map<String, DTOClientChosenSimulation> allRequests) {
        Platform.runLater(() -> {
            for (DTOClientChosenSimulation updatedRequest : allRequests.values()) {
                boolean found = false;

                for (int i = 0; i < allClientsRequests.getItems().size(); i++) {
                    DTOClientChosenSimulation existingItem = allClientsRequests.getItems().get(i);

                    if (existingItem.getSerialNumber().equals(updatedRequest.getSerialNumber())) {
                        allClientsRequests.getItems().set(i, updatedRequest);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    allClientsRequests.getItems().add(updatedRequest);
                }
            }

            if (clientChosenSimulation != null) {
                allClientsRequests.getSelectionModel().select(clientChosenSimulation);
            }
        });
    }


    public void setMainController(BodyController bodyController) {
        mainController = bodyController;
    }

    @Override
    public void close() {
        if (dataRefresher != null && timer != null) {
            dataRefresher.cancel();
            timer.cancel();
        }
    }

    public void approveClicked(MouseEvent mouseEvent) {
        handleApprovedRequest(allClientsRequests.getSelectionModel().getSelectedItem(), "Approved");
    }

    private void handleStatusRequest(String newStatus) {

    }


    public void declineClicked(MouseEvent mouseEvent) {
        handleApprovedRequest(clientChosenSimulation, "Declined");
    }
}