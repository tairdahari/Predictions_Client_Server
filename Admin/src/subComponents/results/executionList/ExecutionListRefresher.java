package subComponents.results.executionList;

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
import utils.DTOListSimulationDetails;

import java.io.IOException;
import java.util.TimerTask;
import java.util.function.Consumer;

public class ExecutionListRefresher extends TimerTask {
    private final Consumer<DTOListSimulationDetails> listSimulationDetailsConsumer;
    //private final String userName;
    //private final String id;

    public ExecutionListRefresher( Consumer<DTOListSimulationDetails> dtoListSimulationDetailsConsumer) {
        this.listSimulationDetailsConsumer = dtoListSimulationDetailsConsumer;
        //this.userName = userName;
        //this.id = id;
    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(Constants.LIST_ENDED_SIMULATIONS_EXECUTION)
                .newBuilder()
                //.addQueryParameter("userName", userName)
                //.addQueryParameter("id", id)
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
                try{
                    if (response.isSuccessful()) {
                        DTOListSimulationDetails allSimulationsExecution = new Gson().fromJson(response.body().string(), DTOListSimulationDetails.class);
                        listSimulationDetailsConsumer.accept(allSimulationsExecution);
                    }}finally {
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
