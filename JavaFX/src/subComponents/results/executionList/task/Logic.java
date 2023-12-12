//package subComponents.results.executionList.task;
//
//import javafx.beans.property.SimpleBooleanProperty;
//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.concurrent.Task;
//import javafx.scene.control.ProgressBar;
//import prediction.engineManager.IEngineManager;
//import subComponents.results.executionList.ExecutionListController;
//
//import java.util.UUID;
//
//public class Logic {
//    private Task<Boolean> currentRunningTask;
//    private ExecutionListController controller;
//
//    public Logic(ExecutionListController controller) {
//        this.controller = controller;
//    }
//
//    public void fetchData(SimpleIntegerProperty simulationId, UIAdapter uiAdapter, SimpleBooleanProperty onFinish,  IEngineManager engineManager) {
//        currentRunningTask = new ExecutionListTask(engineManager, uiAdapter, simulationId, onFinish);
//        //ProgressBar progressBar = controller.mainFlowsExecutionScreenController.getProgressBar();
//
//        currentRunningTask.progressProperty().addListener((obs, oldProgress, newProgress) -> {
//           //progressBar.setProgress(newProgress.doubleValue());
//        });
//        new Thread(currentRunningTask).start();
//    }
//}
//
////    private void fetchData() {
////        UIAdapter uiAdapter = createUIAdapter();
////
////        logic.fetchData(currentSimulationDetails.getId(), uiAdapter, currentSimulationDetails.getInProgress(), engineManager);
////    }
////    private UIAdapter createUIAdapter() {
////        return new UIAdapter(
////                this::addOneSimulationDTO
////        );
////    }
//
////    private void addOneSimulationDTO(DTOSimulationDetails simulationDetails) {
////        // Assuming you have access to your TableView instance
////        TableView<DTOSimulationDetails> tableView = simulationsTable;
////        // Create a new ObservableList containing the updated data
////        ObservableList<DTOSimulationDetails> updatedData = FXCollections.observableArrayList(
////                tableView.getItems()
////        );
////        // Add the new simulationDetails to the updated data list
////        updatedData.add(simulationDetails);
////
////        // Set the updated data list to the TableView
////        tableView.setItems(updatedData);
////    }
