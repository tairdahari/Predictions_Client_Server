package subComponents.managmentScreen.app;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import subComponents.mainScreen.body.BodyController;
import util.Constants;
import util.http.HttpClientUtil;
import utils.DTOFileUpload;
import utils.DTOQueue;

import java.io.IOException;
import java.util.List;

public class ManagementScreenController {

    @FXML
    private Label completedSimulations;

    @FXML
    private Label queueSize;

    @FXML
    private Label workingThreads;

    @FXML
    private Button setCountButton;

    @FXML
    private Spinner<Integer> threadCountSpinner;

    @FXML
    private ListView<String> simulationsList;
    @FXML
    public void initialize() {
        setSpinners();
    }
    private BodyController mainBodyComponentController;
    public ListView<String> getSimulationsList() {
        return simulationsList;
    }
    public void queueUpdate(DTOQueue dtoQueue) {
        queueSize.setText(dtoQueue.getQueueSize().toString());
        workingThreads.setText(dtoQueue.getWaiting().toString());
        completedSimulations.setText(dtoQueue.getFinished().toString());
    }

    public void setMainController(BodyController bodyController) {
        mainBodyComponentController = bodyController;
    }

    public void setFilesList(List<DTOFileUpload> dtoFileUploadList) {

        for (DTOFileUpload file: dtoFileUploadList) {
            simulationsList.getItems().add(file.getFileName());
        }

//        simulationsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
//                @Override
//                public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
//
//                    selectedEnv = simulationsList.getSelectionModel().getSelectedItem();
//                    setOneEnvDetails(getDtoEnvByName(selectedEnv, environments));
//                }
//            });
//
//        }

    }
    public void handleFailure(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error In The Server");
        alert.setContentText(errorMessage);
        alert.setWidth(300);
        alert.show();
    }
    private void setSpinners() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE);
        valueFactory.setValue(0);
        threadCountSpinner.setValueFactory(valueFactory);
    }

    public void clearList() {
        ObservableList<String> simulations = simulationsList.getItems();
        simulations.clear();
    }

    public void setCount(ActionEvent actionEvent) {
        String finalUrl = HttpUrl
                .parse(Constants.THREADPOOL_SIZE)
                .newBuilder()
                .addQueryParameter("threadPoolSizeByUser", threadCountSpinner.getValue().toString())
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
                        String responseData = response.body().string();
                        Gson gson = new Gson();
                        Platform.runLater(() -> {
                        });
                    }
                } finally {
                    response.close();
                }
            }
        });
    }

    public BodyController getMainBodyComponentController() {
        return mainBodyComponentController;
    }
}
