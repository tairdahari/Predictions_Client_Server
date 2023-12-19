package clientSubComponents.requestScreen;

import clientSubComponents.mainScreen.body.BodyController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import util.Constants;
import util.http.HttpClientUtil;
import utils.*;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;

import static util.Constants.REFRESH_RATE;

public class RequestScreenController implements Closeable {
    private BodyController mainController;
    @FXML
    private TableView<DTOClientChosenSimulation> allClientsRequests;

    @FXML
    private Spinner<Integer> amountToRunSpinner;

    @FXML
    private TableColumn<DTOClientChosenSimulation, String> requestStatus;

    @FXML
    private Button executeButton;

    @FXML
    private TableColumn<DTOClientChosenSimulation, String> amountToRun;

    @FXML
    private Spinner<Integer> seconds;

    @FXML
    private TableColumn<DTOClientChosenSimulation, String> serialNumber;

    @FXML
    private TableColumn<DTOClientChosenSimulation, String> simulationName;

    @FXML
    private TableColumn<DTOClientChosenSimulation, String> endedSimulationNumber;

    @FXML
    private TableColumn<DTOClientChosenSimulation, String> runningSimulationsNumber;

    @FXML
    private Button submitButton;
    @FXML
    private Label secondsLabel;
    @FXML
    private Label ticksLabel;

    @FXML
    private ComboBox<String> terminationWay;

    @FXML
    private Spinner<Integer> ticks;

    @FXML
    private ComboBox<String> userChoiceSimulationComboBox;
    private DTOClientChosenSimulation chosenDtoClientRequest;
    private TimerTask dataRefresher;
    private Timer timer;

    @FXML
    public void initialize() {
        serialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        simulationName.setCellValueFactory(new PropertyValueFactory<>("simulationName"));
        amountToRun.setCellValueFactory(new PropertyValueFactory<>("amountToRun"));
        requestStatus.setCellValueFactory(new PropertyValueFactory<>("requestStatus"));
        runningSimulationsNumber.setCellValueFactory(new PropertyValueFactory<>("runningSimulationsNumber"));
        endedSimulationNumber.setCellValueFactory(new PropertyValueFactory<>("endedSimulationNumber"));

        runningSimulationsNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(0).asObject().asString());
        endedSimulationNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(0).asObject().asString());
       // requestStatus.setCellValueFactory(cellData -> new SimpleStringProperty("Waiting"));
        setSpinners();

        allClientsRequests.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                handleRowClick(newSelection);
            }
        });
        terminationWay.setOnAction(event -> {
            String selectedOption = (String) terminationWay.getValue();
            if (selectedOption != null) {
                handleTerminationChoice(selectedOption);
            }
        });
    }

    private void setSpinners() {
        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE);
        valueFactory1.setValue(0);
        ticks.setValueFactory(valueFactory1);

        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE);

        valueFactory2.setValue(0);
        seconds.setValueFactory(valueFactory2);

        SpinnerValueFactory<Integer> valueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE);

        valueFactory3.setValue(0);
        amountToRunSpinner.setValueFactory(valueFactory3);

    }

    private void handleTerminationChoice(String terminationSelect) {
        if(terminationSelect.equals("By ticks/seconds")){
            updateTerminationButtons();
        }
    }

    private void updateTerminationButtons() {
        ticksLabel.setDisable(false);
        secondsLabel.setDisable(false);
        ticks.setDisable(false);
        seconds.setDisable(false);
    }

    public void setSimulationsInSystem() {
        ObservableList<String> terminationItems = FXCollections.observableArrayList("By User", "By ticks/seconds");
        terminationWay.setItems(terminationItems);
        String finalUrl = HttpUrl
                    .parse(Constants.LIST_WORLDS)
                    .newBuilder()
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


                            Map<String, DTOSimulationDefinition> allFiles = gson.fromJson(responseData, new TypeToken<Map<String, DTOSimulationDefinition>>(){}.getType());

                            Platform.runLater(() -> {

                                List<String> allFilesInSystem = createList(allFiles);
                                ObservableList<String> simulations = FXCollections.observableArrayList(allFilesInSystem);
                                userChoiceSimulationComboBox.setItems(simulations);
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

    private List<String> createList(Map<String, DTOSimulationDefinition> allFiles) {
        List<String> allFilesList = new ArrayList<>();
        for (Map.Entry<String, DTOSimulationDefinition> file : allFiles.entrySet()) {
            allFilesList.add(file.getKey());
        }
        return allFilesList;
    }

    private void handleRowClick(DTOClientChosenSimulation newSelection) {
        chosenDtoClientRequest = newSelection;
    }

    @FXML
    void submitButtonClicked(ActionEvent event){
        if(isOkRequest()) {
            sendRequest();
        }
        fillClientRequestsTable();
    }

    private void fillClientRequestsTable() {
        dataRefresher = new RequestScreenRefresher(
                this::setAllRequestsTable,
                mainController.getMainController().currentUserNameProperty().getValue());
        timer = new Timer();
        timer.schedule(dataRefresher, REFRESH_RATE, REFRESH_RATE);

    }
    private void setAllRequestsTable(Map<String, DTOClientChosenSimulation> allRequests) {
        Platform.runLater(() -> {
            ObservableList<DTOClientChosenSimulation> observableList = FXCollections.observableArrayList(allRequests.values());
            allClientsRequests.setItems(observableList);
        });

    }
    private void sendRequest() {
        DTOClientRequest dtoClientRequest = createDtoRequest();

        String finalUrl = HttpUrl
                .parse(Constants.CLIENT_REQUEST)
                .newBuilder()
                .build()
                .toString();

        Gson gson= new Gson();
        String clientRequest = gson.toJson(dtoClientRequest);
        RequestBody body = RequestBody.create(clientRequest.getBytes());
        HttpClientUtil.runAsyncPost(finalUrl, body, new Callback(){
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
                            //System.out.println("sara hamalca");
                        });
                    }} finally {
                    response.close();
                }
            }
        });
    }

    private void errorMessage(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Failed Request");
        alert.setContentText(errorMessage);
        alert.setWidth(300);
        alert.show();
    }

    private boolean isOkRequest() {
        int EMPTY = 0;
        boolean isOk = true;

        if (userChoiceSimulationComboBox.getValue() == null) {
            isOk = false;
            errorMessage("Please select a simulation.");
        }

        if (terminationWay.getValue() == null) {
            isOk = false;
            errorMessage("Please select a termination way.");
        } else {
            if( terminationWay.getValue().equals("By ticks/seconds")) {
                // You need to check the values of ticks and seconds separately.
                Integer ticksValue = ticks.getValue();
                Integer secondsValue = seconds.getValue();

                if (ticksValue == EMPTY && secondsValue == EMPTY) {
                    isOk = false;
                    errorMessage("Please specify either ticks or seconds, or both.");
                }
            }
        }

        return isOk;
    }

    @FXML
    void executeButtonClicked(ActionEvent event) {
        if(chosenDtoClientRequest != null) {
            setNewExecutionSimulationName(chosenDtoClientRequest.getSimulationName());
            mainController.getNewExecutionScreenComponentController().getDetailsHbox().getChildren().clear();

            mainController.openTabNewExecution();
            mainController.setSimulationSerialNumber(chosenDtoClientRequest.getSerialNumber());
            mainController.setSimulationName(chosenDtoClientRequest.getSimulationName());
            mainController.newExecutionButtonAction(event);

        }
    }
    public void setNewExecutionSimulationName(String simulationName) {
        mainController.getNewExecutionScreenComponentController().setSimulationName(simulationName);
        mainController.getNewExecutionScreenComponentController().setSerialNumber(chosenDtoClientRequest.getSerialNumber());
    }

//    private void loadNewExecutionScreen(String simulationName) {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/clientSubComponents/newExecutionScreen/app/newExecutionScreen.fxml"));
//            Parent executionDetailsContent = fxmlLoader.load();
//            NewExecutionScreenController controller = fxmlLoader.getController();
//            controller.setSimulationName(simulationName);
//            controller.setPopulation();
//            controller.setEnvironment();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }



    public void setMainController(BodyController bodyController) {
        mainController = bodyController;
    }

    public DTOClientRequest createDtoRequest() {
        List<DTOTerminationByClient> terminationList = new ArrayList<>();
        if(terminationWay.getValue().equals("By User")) {
            terminationList.add(new DTOTerminationByClient("byUser", "0"));
        }
        else {
            if(ticks.getValue() != 0) {
                terminationList.add(new DTOTerminationByClient("byTicks", ticks.getValue().toString()));
            }
            if(seconds.getValue() != 0) {
                terminationList.add(new DTOTerminationByClient("bySeconds", seconds.getValue().toString()));
            }
        }
        return new DTOClientRequest(mainController.getMainController().currentUserNameProperty().getValue(), userChoiceSimulationComboBox.getValue(),amountToRunSpinner.getValue().toString(),terminationList);
    }
    @Override
    public void close() {
        if (dataRefresher != null && timer != null) {
            dataRefresher.cancel();
            timer.cancel();
        }
    }
}
