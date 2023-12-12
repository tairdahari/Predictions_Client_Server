package clientSubComponents.newExecutionScreen.app;//package subComponents.newExecutionScreen.app;

import clientSubComponents.mainScreen.body.BodyController;
import clientSubComponents.newExecutionScreen.environmentDetails.EnvironmentDetailsController;
import clientSubComponents.newExecutionScreen.environmentDetails.oneEnv.OneEnvironmentController;
import clientSubComponents.newExecutionScreen.populationDetails.PopulationDetailsController;
import clientSubComponents.newExecutionScreen.populationDetails.entityCount.EntityCountController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;


import javafx.scene.layout.VBox;
import javafx.util.Pair;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import util.Constants;
import util.http.HttpClientUtil;
import utils.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NewExecutionScreenController {
    private BodyController mainBodyComponentController;
    private List<Consumer<String>> valueSelectionListeners = new ArrayList<>();
    private EnvironmentDetailsController environmentDetailsController;
    private PopulationDetailsController populationDetailsController;
    private List<EntityCountController> entityCountControllers = new ArrayList<>();
    private List<OneEnvironmentController> environmentControllers = new ArrayList<>();
    private Event event;
    private Thread thread;
    private DTOExecutionLists dtoExecutionListsDataMember;
    private String simulationName;

    @FXML
    private BorderPane newExecutionScreenComponent;
    @FXML private HBox detailsHbox;
    @FXML private Button startButton;

    @FXML
    public void initialize(){
    }

    public void setMainController(BodyController mainBodyController) {
        this.mainBodyComponentController = mainBodyController;
    }
    public void addValueSelectionListener(Consumer<String> listener) {
        valueSelectionListeners.add(listener);
    }
    public void notifyValueSelectionListeners(String selectedValue) {
        for (Consumer<String> listener : valueSelectionListeners) {
            listener.accept(selectedValue);
        }
    }

    public void setPopulation() {
        try {
            detailsHbox.getChildren().clear();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/clientSubComponents/newExecutionScreen/populationDetails/populationDetails.fxml"));
            Parent populationDetailsUI = fxmlLoader.load();
            PopulationDetailsController populationDetailsController = fxmlLoader.getController();
            populationDetailsController.setNewExecutionScreenController(this);

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
                                populationDetailsController.setMaxCountLabel(dtoSimulationDefinition.getDtoGridDefinition().getSize());
                                populationDetailsController.setEntities(dtoSimulationDefinition.getDtoEntityDefinition());
                                detailsHbox.getChildren().add(populationDetailsUI);
                            });
                        }
                    } finally {
                        // If there are any cleanup or resource release tasks, you can put them here
                        response.close();
                    }
                }
            });

            this.populationDetailsController = populationDetailsController;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleFailure(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error In The Server");
        alert.setContentText(errorMessage);
        alert.setWidth(300);
        alert.show();
    }

    public void setEnvironment() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/clientSubComponents/newExecutionScreen/environmentDetails/environmentDetails.fxml"));
            Parent envDetailsUI = fxmlLoader.load();
            EnvironmentDetailsController environmentDetailsController = fxmlLoader.getController();
            this.environmentDetailsController = environmentDetailsController;
            environmentDetailsController.setNewExecutionScreenController(this);

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
                                environmentDetailsController.setListView(dtoSimulationDefinition.getDtoEnvironmentVariables());
                                detailsHbox.getChildren().add(envDetailsUI);
                            });
                        }
                    } finally {
                        // If there are any cleanup or resource release tasks, you can put them here
                        response.close();
                    }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startButtonAction(ActionEvent actionEvent) {
        createLists();

    }

    private void startClicked() {
        // Serialize dtoExecutionListsDataMember to JSON
        Gson gson = new Gson();
        String dtoJson = gson.toJson(dtoExecutionListsDataMember);

        // Build the URL
        // No need to add query parameters here

        // Create an HTTP request with the JSON payload
        //RequestBody requestBody = RequestBody.create(MediaType.get("application/json"), dtoJson);
        String id = null; //TODO getID
        Pair<String, RequestBody> urlAndBody = buildFinalUrlAndBody(id);
        String finalUrl = urlAndBody.getKey();
        RequestBody requestBody = urlAndBody.getValue();

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
                        Platform.runLater(() -> {
                            mainBodyComponentController.startClicked();
                        });
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
    private Pair<String, RequestBody> buildFinalUrlAndBody(String id) {
        String finalUrl;
                finalUrl = HttpUrl
                .parse(Constants.START_EXECUTION)
                .toString();


        RequestBody body =
                new MultipartBody.Builder()
                        .addFormDataPart("id", id, RequestBody.create(id, MediaType.parse("text/plain")))
                        .build();
        return new Pair<>(finalUrl, body);
    }


    private void createLists() {
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
                            List<Integer> populationList = populationDetailsController.initListPopulation();
                            List<Object> userEnvVarChoices = environmentDetailsController.getListValues(dtoSimulationDefinition);
                            DTOExecutionLists dtoExecutionLists = new DTOExecutionLists(populationList, userEnvVarChoices);
                            dtoExecutionListsDataMember = dtoExecutionLists;
                            startClicked();
                        });
                    }
                } finally {
                    // If there are any cleanup or resource release tasks, you can put them here
                    response.close();
                }
            }
        });
    }

    @FXML
    void clearButtonAction(ActionEvent event) {
        if (populationDetailsController != null) {
            populationDetailsController.clear();
        }

        if (environmentDetailsController != null) {
            environmentDetailsController.clear();
        }
    }


    public void OnReRun(String id) {
        DTOReRun dtoReRun = null;
        //DTOReRun dtoReRun = engineManager.reRun(Integer.parseInt(id));
        int i = 0;

        for (Integer integer : dtoReRun.getPopulationList()) {
            entityCountControllers.get(i).reRun(integer);
            i++;
        }

        i=0;

        for (Object object : dtoReRun.getUserEnvVarChoices()) {
            environmentControllers.get(i).reRun(object);
            i++;
        }
    }

    public void addEntityToList(EntityCountController entityCountController)
    {
        entityCountControllers.add(entityCountController);
    }

    public void addEnvToList(OneEnvironmentController environmentController)
    {
        environmentControllers.add(environmentController);
    }

    public void clearEntityList()
    {
        entityCountControllers.clear();
    }

    public void clearEnvList() {
        environmentControllers.clear();
    }

    public void setSimulationName(String simulationName) {
        this.simulationName = simulationName;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public HBox getDetailsHbox() {
        return detailsHbox;
    }

}