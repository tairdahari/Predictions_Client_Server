package subComponents.allocationsScreen.app;

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
public class AllocationTableRefresher extends TimerTask {
    private final Consumer<Map<String, DTOClientChosenSimulation>> mapAllRequestsConsumer;
    //private final String userName;
    //private final String id;

    public AllocationTableRefresher(Consumer<Map<String, DTOClientChosenSimulation>> mapAllRequestsConsumer) {
        this.mapAllRequestsConsumer = mapAllRequestsConsumer;
        //this.userName = userName;
       // this.id = id;
    }

    @Override
    public void run() {

        String finalUrl = HttpUrl
                .parse(Constants.ALL_REQUESTS)
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
                        Platform.runLater(() -> {
                            Map<String, DTOClientChosenSimulation> allRequests = new Gson().fromJson(responseData, new TypeToken<Map<String, DTOClientChosenSimulation>>(){}.getType());
                            mapAllRequestsConsumer.accept(allRequests);
                        });
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