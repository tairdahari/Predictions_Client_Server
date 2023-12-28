package subComponents.results.app;//package subComponents.results.app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import subComponents.mainScreen.body.BodyController;
import subComponents.results.executionList.ExecutionListController;

import java.io.IOException;

public class ResultsScreenController {
    private BodyController mainBodyComponentController;
    private ExecutionListController executionListController;
    private String id;
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

    public void clearExecutionDetails() {
        executionDetailsBox.getChildren().clear();
    }

    public void clearExecutionResults() {
        executionResultsBox.getChildren().clear();
    }
    public void setExecutionList(String simulationName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/subComponents/results/executionList/executionList.fxml"));
            Parent executionListContent = fxmlLoader.load();
            ExecutionListController controller = fxmlLoader.getController();
            this.executionListController = controller;
            executionListController.setMainController(this);
            //executionListController.setSimulationName(simulationName);
            //executionListController.setSerialNumber(mainBodyComponentController.getSimulationSerialNumber());
            executionListController.refresherExecutionList();
            executionListBox.getChildren().clear();
            executionListBox.getChildren().add(executionListContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BodyController getMainBodyComponentController() {
        return mainBodyComponentController;
    }

    public HBox getExecutionDetailsBox() {
        return executionDetailsBox;
    }


    public void setSimulationName(String simulationName) {
        id = simulationName;
    }
}