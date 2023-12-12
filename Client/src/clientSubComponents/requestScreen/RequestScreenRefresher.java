package clientSubComponents.requestScreen;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.Constants;
import util.http.HttpClientUtil;
import utils.DTOClientChosenSimulation;

import java.io.IOException;
import java.util.Map;
import java.util.TimerTask;
import java.util.function.Consumer;

public class RequestScreenRefresher extends TimerTask {
    private final Consumer<Map<String, DTOClientChosenSimulation>> mapAllRequestsConsumer;
    private final String userName;

    public RequestScreenRefresher(Consumer<Map<String, DTOClientChosenSimulation>> mapAllRequestsConsumer, String userName) {
        this.mapAllRequestsConsumer = mapAllRequestsConsumer;
        this.userName = userName;
    }

    @Override
    public void run() {

        String finalUrl = HttpUrl
                .parse(Constants.MAP_CLIENT_REQUESTS)
                .newBuilder()
                .addQueryParameter("userName", userName )
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
                        String responseData = response.body().string();

                        Map<String, DTOClientChosenSimulation> allOneClientRequests = new Gson().fromJson(responseData, new TypeToken<Map<String, DTOClientChosenSimulation>>(){}.getType());
                        mapAllRequestsConsumer.accept(allOneClientRequests);
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