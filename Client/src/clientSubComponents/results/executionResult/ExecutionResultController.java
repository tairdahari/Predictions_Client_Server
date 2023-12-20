package clientSubComponents.results.executionResult;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.Constants;
import util.http.HttpClientUtil;
import utils.DTOEntityDefinition;
import utils.DTOPropertyDefinition;
import utils.DTOPropertyHistogram;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExecutionResultController {

    @FXML
    private HBox average;

    @FXML
    private Label averageLabel;

    @FXML
    private HBox consistency;

    @FXML
    private Label consistencyLabel;

    @FXML
    private GridPane histogramHolder;
    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private TreeView<String> treeView;
    @FXML
    private LineChart<String, Number> entityQuantityGraph;
    private String serialNumber;
    private String simulationName;
    private DTOPropertyHistogram dtoPropertyHistogram;


    @FXML
    public void initialize() {
    }

    private void setEntitiesAndProperties(TreeItem<String> rootItem, List<DTOEntityDefinition> dtoEntityDefinition) {
        for (DTOEntityDefinition oneEntity: dtoEntityDefinition) {
            TreeItem<String> entityBranch = new TreeItem<>(oneEntity.getEntityName());
            rootItem.getChildren().add(entityBranch);

            List<DTOPropertyDefinition> properties = oneEntity.getEntityProperties();
            for (DTOPropertyDefinition oneProperty: properties) {
                TreeItem<String> propertyNode = new TreeItem<>(oneProperty.getName());
                entityBranch.getChildren().add(propertyNode);
            }
        }
    }

    public void selectedItem() {
        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();

        if (item != null && item.isLeaf()) {
            String selectedProperty = item.getValue();

            String finalUrl = HttpUrl
                    .parse(Constants.SIMULATION_HISTOGRAM)
                    .newBuilder()
                    .addQueryParameter("serialNumber", serialNumber)
                    .addQueryParameter("simulationName", simulationName)
                    .addQueryParameter("selectedProperty", selectedProperty)
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
                            Gson gson = new Gson();
                            DTOPropertyHistogram histogramData = gson.fromJson(responseData, DTOPropertyHistogram.class);

                            getHistogram(histogramData, selectedProperty);
                        }
                    } finally {
                        response.close();
                    }
                }
            });
        }
    }

    private void getHistogram(DTOPropertyHistogram histogramData, String selectedProperty) {
            barChart.getData().clear();
            barChart.setAnimated(true);
            XYChart.Series<String, Number> series = new XYChart.Series<>();

            for (Map.Entry<String, Integer> entry : histogramData.getValues().entrySet()) {
                String propertyValue = entry.getKey();
                int frequency = entry.getValue();
                series.getData().add(new XYChart.Data<>(propertyValue, frequency));
            }

            barChart.getData().add(series);
            barChart.setAnimated(false);

            try {
                float consistency = calculateConsistency(selectedProperty);
                String formattedValue = String.format("%.2f", consistency);
                consistencyLabel.setText(formattedValue);
            } catch (IllegalArgumentException e) {
                consistencyLabel.setText("");
            }

            try {
                Double averageValue = calculateAverageValue(histogramData.getValues());
                averageLabel.setText(averageValue.toString());
            } catch (NumberFormatException e) {
                averageLabel.setText("");
            }
        }


    public void handleFailure(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error In The Server");
        alert.setContentText(errorMessage);
        alert.setWidth(300);
        alert.show();
    }

    private float calculateConsistency(String propertyName) {
        final float[] consistency = new float[1];
        String finalUrl = HttpUrl
                .parse(Constants.CONSISTENCY)
                .newBuilder()
                .addQueryParameter("serialNumber", serialNumber)
                .addQueryParameter("simulationName", simulationName)
                .addQueryParameter("propertyName", propertyName)
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
                        consistency[0] = new Gson().fromJson(response.body().string(), Float.class);
                    }
                } finally {
                    response.close();
                }
            }
        });
        return consistency[0];
    }

    private Double calculateAverageValue(Map<String, Integer> histogramData) {
        int totalFrequency = 0;
        int totalValue = 0;

        for (Map.Entry<String, Integer> entry : histogramData.entrySet()) {
            String propertyValue = entry.getKey();
            int frequency = entry.getValue();

            // Parse the property value as a Double (assuming it's a numerical property)
            Double numericValue = Double.parseDouble(propertyValue);

            totalFrequency += frequency;
            totalValue += frequency * numericValue;
        }

        // Calculate the average value
        if (totalFrequency > 0) {
            return totalValue / (double) totalFrequency;
        } else {
            return 0.0; // Default value when no data is available
        }
    }

    @FXML
    void handleLoadButton(ActionEvent event) {
        //displayEntityQuantityGraph(engineManager.getContext().getEntityQuantities());
    }

    public void displayEntityQuantityGraph(Map<Integer, Integer> entityQuantities) {
        entityQuantityGraph.getData().clear();
        entityQuantityGraph.setAnimated(true);

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (Map.Entry<Integer, Integer> entry : entityQuantities.entrySet()) {
            Integer tick = entry.getKey();
            Integer quantity = entry.getValue();

            XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(tick.toString(), quantity);

            series.getData().add(dataPoint);
        }

        entityQuantityGraph.getData().add(series);
        entityQuantityGraph.setAnimated(false);
    }
    public void initTree(List<DTOEntityDefinition> dtoEntityDefinition) {
        TreeItem<String> rootItem = new TreeItem<String>("Entities");
        treeView.setRoot(rootItem);
        setEntitiesAndProperties(rootItem, dtoEntityDefinition);
    }

    public void setSerialNumber(String simulationSerialNumber) {
        this.serialNumber = simulationSerialNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSimulationName(String simulationName) {
        this.simulationName = simulationName;
    }
}
