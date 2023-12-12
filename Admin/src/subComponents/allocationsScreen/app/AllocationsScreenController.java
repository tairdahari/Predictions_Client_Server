package subComponents.allocationsScreen.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import subComponents.mainScreen.body.BodyController;
import util.Constants;
import util.http.HttpClientUtil;
import utils.DTOActionDetails;
import utils.DTOActionDetailsDeserializer;
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
    private TableColumn<DTOClientChosenSimulation, ComboBox> requestStatus;

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

        runningSimulationsNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(0).asObject().asString());
        endedSimulationNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(0).asObject().asString());

        requestStatus.setCellValueFactory(param -> {
                    DTOClientChosenSimulation item = param.getValue();
                    ComboBox<String> statusComboBox = new ComboBox<>(FXCollections.observableArrayList("Approved", "Declined"));

                    statusComboBox.setValue(item.getRequestStatus());

                    statusComboBox.setOnAction(event -> {
                        handleApprovedRequest(item, statusComboBox.getValue());
                    });

            return new SimpleObjectProperty<>(statusComboBox);
        });

        allClientsRequests.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                handleRowClick(newSelection);
            }
        });


    }

    private void handleApprovedRequest(DTOClientChosenSimulation clientChosenSimulation, String selectedStatus) {
        //maybe sending a message if the admin sure about this approval if he does so disable the line.

        String finalUrl = HttpUrl
                .parse(Constants.REQUEST_STATUS)
                .newBuilder()
                .addQueryParameter("serialNumber",clientChosenSimulation.getSerialNumber())
                .addQueryParameter("clientName",clientChosenSimulation.getClientName() )
                .addQueryParameter("status",selectedStatus)
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


                        //Map<String, DTOSimulationDefinition> allFiles = gson.fromJson(responseData, new TypeToken<Map<String, DTOSimulationDefinition>>(){}.getType());

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

    private void handleRowClick(DTOClientChosenSimulation newSelection) {
        clientChosenSimulation = newSelection;
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
            for (DTOClientChosenSimulation newRequest : allRequests.values()) {
                int index = -1;
                for (int i = 0; i < allClientsRequests.getItems().size(); i++) {
                    if (newRequest.equals(allClientsRequests.getItems().get(i))) {
                        index = i;
                        break;
                    }
                }

                if (index != -1) {
                    allClientsRequests.getItems().set(index, newRequest);
                } else {
                    allClientsRequests.getItems().add(newRequest);
                }
            }

            allClientsRequests.getItems().removeIf(clientChosenSimulation -> !allRequests.containsValue(clientChosenSimulation));
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
}