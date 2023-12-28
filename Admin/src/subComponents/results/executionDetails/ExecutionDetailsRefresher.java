package subComponents.results.executionDetails;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.Constants;
import util.http.HttpClientUtil;
import utils.DTOSimulationDetails;

import java.io.IOException;
import java.util.TimerTask;
import java.util.function.Consumer;

public class ExecutionDetailsRefresher extends TimerTask {
    private final String simulationName;
    private final String serialNumber;
    private final Consumer<DTOSimulationDetails> detailsConsumer;

    public ExecutionDetailsRefresher(Consumer<DTOSimulationDetails> detailsConsumer, String id, String simulationName) {
        this.simulationName = simulationName;
        this.serialNumber = id;
        this.detailsConsumer = detailsConsumer;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(Constants.SIMULATION_DETAILS)
                .newBuilder()
                .addQueryParameter("simulationId", serialNumber)
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
                        DTOSimulationDetails dtoSimulationDetails = new Gson().fromJson(response.body().string(), DTOSimulationDetails.class);
                        Platform.runLater(() -> {
                            detailsConsumer.accept(dtoSimulationDetails);
                        });                    }
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
}
