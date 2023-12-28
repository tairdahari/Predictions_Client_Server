package subComponents.mainScreen.header.headerBody;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import subComponents.mainScreen.header.HeaderController;
import util.Constants;
import util.http.HttpClientUtil;
import utils.DTOFileUpload;
import utils.DTOQueue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static util.Constants.REFRESH_RATE;

public class HeaderBodyController {
    //private IEngineManager engineManager;
    private HeaderController mainHeaderController;
    @FXML
    private Label statusFileXML;
    @FXML
    private Label filePath;
    @FXML
    private Button loadFileButton;
    private final List<DTOFileUpload> filesListDto = new ArrayList<>();
    private SimpleBooleanProperty loadFileButtonProperty;
    private SimpleBooleanProperty isFileCorrectProperty;
    private Thread thread;
    private boolean isFirstFile = true;
    private Timer timer;
    private TimerTask queueRefresher;

    public HeaderBodyController() {
        loadFileButtonProperty = new SimpleBooleanProperty(false);
        isFileCorrectProperty = new SimpleBooleanProperty(false);
    }
    public void setMainController(HeaderController mainController) {
        this.mainHeaderController = mainController;
    }

    public List<DTOFileUpload> getFilesListDto() {
        return filesListDto;
    }

    @FXML
    void loadFileButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile == null) {
            return;
        }
        Pair<String, RequestBody> urlAndBody = buildFinalUrlAndBody(isFirstFile, selectedFile);
        String finalUrl = urlAndBody.getKey();
        RequestBody body = urlAndBody.getValue();

        HttpClientUtil.runAsyncPost(finalUrl, body, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                    statusFileXML.setText(e.getMessage());
                    statusFileXML.setVisible(true);
                    filePath.setText("");
                    isFileCorrectProperty.set(false);
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try{
                    if (response.isSuccessful()) {
                        String res = response.body().string();
                        DTOFileUpload dtoFileUpload = new Gson().fromJson(res, DTOFileUpload.class);
                        Platform.runLater(() -> {
                            String isValid = dtoFileUpload.getIsValid();
                            String errorMessage = dtoFileUpload.getErrorMessage();

                            String absolutePath = selectedFile.getAbsolutePath();
                            boolean isXMLFile = absolutePath.toLowerCase().endsWith(".xml");
                            loadFileButtonProperty.set(true);

                            if (!isXMLFile || !checkFileValidity(selectedFile)) {
                                return;
                            }

                            if (isValid.equals("true")) {
                                if(!dtoFileUploadExist(dtoFileUpload)) {
                                    filesListDto.add(dtoFileUpload);
                                }
                                filePath.setText(selectedFile.getPath());
                                filePath.setVisible(true);
                                statusFileXML.setText("File is correct");
                                statusFileXML.setVisible(true);
                                isFileCorrectProperty.set(true);
                                mainHeaderController.updatePushTabButtons();
                                loadFileButtonProperty.set(false);
                                mainHeaderController.getMainController().getBodyComponentController().updateButtons();
                            } else {
                                filePath.setText(selectedFile.getPath());
                                isFileCorrectProperty.set(false);
                                statusFileXML.setText(errorMessage);
                                statusFileXML.setVisible(true);
                            }
                        });
                        refresherQueue();

                    }}finally {
                    response.close();
                }
            }
        });
    }

//    public void updateQueue() {
//        String finalUrl = HttpUrl
//                .parse(Constants.QUEUE_DATA)
//                .newBuilder()
//                .build()
//                .toString();
//
//        HttpClientUtil.runAsync(finalUrl, new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                Platform.runLater(() -> {
//                    handleFailure(e.getMessage());
//                });
//            }
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                try {
//                    if (response.isSuccessful()) {
//                        String responseData = response.body().string();
//                        Gson gson = new Gson();
//                        Platform.runLater(() -> {
//                            DTOQueue dtoQueue = gson.fromJson(responseData, DTOQueue.class);
//                            mainHeaderController.getMainController().getBodyComponentController().getManagementScreenComponentController().queueUpdate(dtoQueue);
//                        });
//                    }
//                } finally {
//                    response.close();
//                }
//            }
//        });
//    }

    private boolean dtoFileUploadExist(DTOFileUpload dtoFileUpload) {
         for(DTOFileUpload file :filesListDto) {
             if(file.getFileName().equals(dtoFileUpload.getFileName())) {
                 return true;
             }
         }
         return false;
    }
    public void handleFailure(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error In The Server");
        alert.setContentText(errorMessage);
        alert.setWidth(300);
        alert.show();
    }

    private Pair<String, RequestBody> buildFinalUrlAndBody(boolean isFirstFile, File selectedFile) {
        String finalUrl;
        if (isFirstFile) {
            finalUrl = HttpUrl
                    .parse(Constants.LOAD_FILE)
                    .newBuilder()
                    .addQueryParameter("isFirstUpload", "true")
                    .build()
                    .toString();
            isFirstFile = false;
        }
        else {
            finalUrl = HttpUrl
                    .parse(Constants.LOAD_FILE)
                    .newBuilder()
                    .addQueryParameter("isFirstUpload", "false")
                    .build()
                    .toString();
        }

        RequestBody body =
                new MultipartBody.Builder()
                        .addFormDataPart("xmlFile", selectedFile.getName(), RequestBody.create(selectedFile, MediaType.parse("text/plain")))
                        .build();
        return new Pair<>(finalUrl, body);
    }

    private boolean checkFileValidity(File selectedFile) {
        try {
            //engineManager.readingSystemInformationFromFileJavaFX(selectedFile);
            return true; // File is valid
        } catch (RuntimeException e) {
            statusFileXML.setText("Invalid file");
            statusFileXML.setVisible(true);
            loadErrorFxml(e);
            throw new IllegalArgumentException(e);
        }
    }

    private void loadErrorFxml(RuntimeException e) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error Alert");
        errorAlert.setHeaderText("File Error");
        errorAlert.setContentText(String.valueOf(e));
        errorAlert.showAndWait();
    }

    public SimpleBooleanProperty getLoadFileButtonProperty() {
        return this.loadFileButtonProperty;
    }

    public SimpleBooleanProperty getIsFileCorrectProperty() {
        return this.isFileCorrectProperty;
    }

    public Button getLoadFileButton() {
        return loadFileButton;
    }

    public void refresherQueue() {
        queueRefresher = new QueueRefresher(
                this::updateQueueData
                );
        timer = new Timer();
        timer.schedule(queueRefresher, REFRESH_RATE, REFRESH_RATE);
    }
    private void updateQueueData(DTOQueue dtoQueue) {
        Platform.runLater(() -> {
            mainHeaderController.getMainController().getBodyComponentController().getManagementScreenComponentController().queueUpdate(dtoQueue);
        });
    }

}