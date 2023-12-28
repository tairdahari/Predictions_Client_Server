package subComponents.mainScreen.header.headerBody;

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
import utils.DTOQueue;

import java.io.IOException;
import java.util.TimerTask;
import java.util.function.Consumer;

public class QueueRefresher extends TimerTask {
    private final Consumer<DTOQueue> dtoQueueConsumer;

    public QueueRefresher(Consumer<DTOQueue> dtoQueueConsumer) {
        this.dtoQueueConsumer = dtoQueueConsumer;

    }

    @Override
    public void run() {
        String finalUrl = HttpUrl
                .parse(Constants.QUEUE_DATA)
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
                String res = response.body().string();
                try {
                    if (response.isSuccessful()) {
                        Platform.runLater(() -> {
                            DTOQueue dtoQueue = null;
                            try {
                                dtoQueue = new Gson().fromJson(res, DTOQueue.class);
                                dtoQueueConsumer.accept(dtoQueue);
                            } catch (Exception e) {
                                System.out.println("asldjlsufhsfhlisjfh");
                            }
                        });
                    }
                } finally {
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
}
