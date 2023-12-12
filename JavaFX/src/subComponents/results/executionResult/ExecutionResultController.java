//package subComponents.results.executionResult;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.chart.*;
//import javafx.scene.control.*;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import prediction.worldManager.WorldManager;
//import prediction.worldManager.IWorldManager;
//import utils.*;
//
//import java.util.List;
//import java.util.Map;
//
//public class ExecutionResultController {
//
//    @FXML
//    private HBox average;
//
//    @FXML
//    private Label averageLabel;
//
//    @FXML
//    private HBox consistency;
//
//    @FXML
//    private Label consistencyLabel;
//
//    @FXML
//    private GridPane histogramHolder;
//    @FXML
//    private BarChart<String, Number> barChart;
//
//    @FXML
//    private TreeView<String> treeView;
//    private IWorldManager engineManager  = new WorldManager();
//    @FXML
//    private LineChart<String, Number> entityQuantityGraph;
//
//
//    @FXML
//    public void initialize() {
//    }
//
//    private void setEntitiesAndProperties(TreeItem<String> rootItem, List<DTOEntityDefinition> dtoEntityDefinition) {
//        for (DTOEntityDefinition oneEntity: dtoEntityDefinition) {
//            TreeItem<String> entityBranch = new TreeItem<>(oneEntity.getEntityName());
//            rootItem.getChildren().add(entityBranch);
//
//            List<DTOPropertyDefinition> properties = oneEntity.getEntityProperties();
//            for (DTOPropertyDefinition oneProperty: properties) {
//                TreeItem<String> propertyNode = new TreeItem<>(oneProperty.getName());
//                entityBranch.getChildren().add(propertyNode);
//            }
//        }
//    }
//    //            Double averageConsistency = calculateAverageConsistency(histogramData.getValues());
////            if (averageConsistency != null) {
////                consistencyLabel.setText(averageConsistency.toString());
////                engineManager.getHistogram(selectedProperty).setAverageConsistency(averageConsistency);
////            } else {
////                consistencyLabel.setText("");
////                engineManager.getHistogram(selectedProperty).setAverageConsistency(0.0);
////            }
//    public void selectedItem() {
//        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
//
//        if (item != null && item.isLeaf()) {
//            String selectedProperty = item.getValue();
//            DTOPropertyHistogram histogramData = engineManager.getHistogram(selectedProperty);
//
//            barChart.getData().clear();
//            barChart.setAnimated(true);
//            XYChart.Series<String, Number> series = new XYChart.Series<>();
//
//            for (Map.Entry<String, Integer> entry : histogramData.getValues().entrySet()) {
//                String propertyValue = entry.getKey();
//                int frequency = entry.getValue();
//                series.getData().add(new XYChart.Data<>(propertyValue, frequency));
//            }
//
//            barChart.getData().add(series);
//            barChart.setAnimated(false);
//
//            try {
//                float consistency = calculateConsistency(selectedProperty);
//                String formattedValue = String.format("%.2f", consistency);
//                consistencyLabel.setText(formattedValue);
//            } catch (IllegalArgumentException e) {
//                consistencyLabel.setText("");
//            }
//
//            try {
//                Double averageValue = calculateAverageValue(histogramData.getValues());
//                averageLabel.setText(averageValue.toString());
//            } catch (NumberFormatException e) {
//                averageLabel.setText("");
//            }
//        }
//    }
//    private float calculateConsistency(String propertyName) {
//        return engineManager.getPropertyConsistency(propertyName);
//    }
//
//    private Double calculateAverageValue(Map<String, Integer> histogramData) {
//        int totalFrequency = 0;
//        int totalValue = 0;
//
//        for (Map.Entry<String, Integer> entry : histogramData.entrySet()) {
//            String propertyValue = entry.getKey();
//            int frequency = entry.getValue();
//
//            // Parse the property value as a Double (assuming it's a numerical property)
//            Double numericValue = Double.parseDouble(propertyValue);
//
//            totalFrequency += frequency;
//            totalValue += frequency * numericValue;
//        }
//
//        // Calculate the average value
//        if (totalFrequency > 0) {
//            return totalValue / (double) totalFrequency;
//        } else {
//            return 0.0; // Default value when no data is available
//        }
//    }
//
//    @FXML
//    void handleLoadButton(ActionEvent event) {
//        displayEntityQuantityGraph(engineManager.getContext().getEntityQuantities());
//    }
//
//    public void displayEntityQuantityGraph(Map<Integer, Integer> entityQuantities) {
//        entityQuantityGraph.getData().clear();
//        entityQuantityGraph.setAnimated(true);
//
//        XYChart.Series<String, Number> series = new XYChart.Series<>();
//
//        for (Map.Entry<Integer, Integer> entry : entityQuantities.entrySet()) {
//            Integer tick = entry.getKey();
//            Integer quantity = entry.getValue();
//
//            XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(tick.toString(), quantity);
//
//            series.getData().add(dataPoint);
//        }
//
//        entityQuantityGraph.getData().add(series);
//        entityQuantityGraph.setAnimated(false);
//    }
//    public void initTree(List<DTOEntityDefinition> dtoEntityDefinition) {
//        TreeItem<String> rootItem = new TreeItem<String>("Entities");
//        treeView.setRoot(rootItem);
//        setEntitiesAndProperties(rootItem, dtoEntityDefinition);
//    }
//    public void setEngineManager(IWorldManager engineManager) {
//        this.engineManager = engineManager;
//    }
//}
