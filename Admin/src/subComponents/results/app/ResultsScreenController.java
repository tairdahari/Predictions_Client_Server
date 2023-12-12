package subComponents.results.app;//package subComponents.results.app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import subComponents.mainScreen.body.BodyController;
import subComponents.results.executionDetails.ExecutionDetailsController;
import subComponents.results.executionList.ExecutionListController;
import subComponents.results.executionResult.ExecutionResultController;

import java.io.IOException;

public class ResultsScreenController {
    private BodyController mainBodyComponentController;
    private ExecutionListController executionListController;


//    private IEngineManager engineManager;
    @FXML private HBox executionListBox;
    @FXML private BorderPane perSimulationBorder;
    @FXML private HBox executionDetailsBox;
    @FXML private HBox executionResultsBox;

    @FXML public void initialize(){
    }

    public HBox getExecutionResultsBox() {
        return executionResultsBox;
    }


    public void setMainController(BodyController mainBodyController) {
        this.mainBodyComponentController = mainBodyController;
    }

//    public void setEngineManager(IEngineManager engineManager) {
//        this.engineManager = engineManager;
//    }
    public void clearExecutionDetails() {
        executionDetailsBox.getChildren().clear();
    }

    public void clearExecutionResults() {
        executionResultsBox.getChildren().clear();
    }
    public void setExecutionList() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/subComponents/results/executionList/executionList.fxml"));
            Parent executionListContent = fxmlLoader.load();
            ExecutionListController controller = fxmlLoader.getController();
            //controller.updateList(engineManager.getAllSimulationsExecution());
            this.executionListController = controller;
            executionListController.setMainController(this);
            //executionListController.setEngineManager(engineManager);
            executionListController.updateListThread();
            executionListBox.getChildren().clear();
            executionListBox.getChildren().add(executionListContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setExecutionDetails() {
    }

    public void setExecutionResults() {
    }


    public HBox getExecutionDetailsBox() {
        return executionDetailsBox;
    }

//    public void isRerunClicked(String id) {
//        mainBodyComponentController.OnReRunClicked(id);
//    }
}
