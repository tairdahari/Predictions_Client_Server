package subComponents.mainScreen.body;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import subComponents.allocationsScreen.app.AllocationsScreenController;
import subComponents.mainScreen.app.AppController;
import subComponents.managmentScreen.app.ManagementScreenController;
import subComponents.results.app.ResultsScreenController;
import utils.DTOFileUpload;

import java.util.List;

public class BodyController {
    private AppController mainController;
    @FXML
    private Tab executionsHistoryButton;
    @FXML
    private Tab allocationsButton;
    @FXML
    private Tab managementButton;
    @FXML private AnchorPane managementScreenComponent;
    @FXML
    private ManagementScreenController managementScreenComponentController;
    @FXML
    private AllocationsScreenController allocationsScreenComponentController;
    @FXML
    private AnchorPane allocationsScreenComponent;
    @FXML
    private ResultsScreenController resultsScreenComponentController;
    @FXML
    private TabPane optionsTabPane;
    private SimpleBooleanProperty executionHistoryButtonProperty;
    private SimpleBooleanProperty allocationsButtonProperty;
    private SimpleBooleanProperty managementButtonProperty;
    private Event event;
    private Image image;
    private String simulationName;
    private String simulationSerialNumber;
    private boolean isFirstAppear = false;


    public BodyController() {
        executionHistoryButtonProperty = new SimpleBooleanProperty(false);
        allocationsButtonProperty = new SimpleBooleanProperty(false);
        managementButtonProperty = new SimpleBooleanProperty(true);
    }

    @FXML
    public void initialize() {
        if (managementScreenComponentController != null && allocationsScreenComponentController != null && resultsScreenComponentController != null) {
            managementScreenComponentController.setMainController(this);
            allocationsScreenComponentController.setMainController(this);
            resultsScreenComponentController.setMainController(this);
        }
        setTabsListener();
    }
    public AppController getMainController() {
        return mainController;
    }
    public void updatePushTabButtons() {
        managementButton.setDisable(false);
        allocationsButton.setDisable(false);
        executionsHistoryButton.setDisable(false);
        managementButtonAction(event);
    }
    public String getSimulationSerialNumber() {
        return simulationSerialNumber;
    }
    public void setSimulationName(String simulationName) {
        this.simulationName = simulationName;
    }

    private void setTabsListener() {
        optionsTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab == managementButton) {
                managementButtonProperty.set(true);
                allocationsButtonProperty.set(false);
                executionHistoryButtonProperty.set(false);
            } else if (newTab == allocationsButton) {
                managementButtonProperty.set(false);
                allocationsButtonProperty.set(true);
                executionHistoryButtonProperty.set(false);
            } else if (newTab == executionsHistoryButton) {
                managementButtonProperty.set(false);
                allocationsButtonProperty.set(false);
                executionHistoryButtonProperty.set(true);
            }
        });
    }

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    @FXML
    void executionsHistoryButtonAction(Event event) {
        //String simulationName = managementScreenComponentController.getMainBodyComponentController().getSimulationName();
        //resultsScreenComponentController.setSimulationName(simulationName);
        resultsScreenComponentController.setExecutionList(simulationName);
        resultsScreenComponentController.clearExecutionDetails();
        resultsScreenComponentController.clearExecutionResults();
    }

    public ManagementScreenController getManagementScreenComponentController() {
        return managementScreenComponentController;
    }

    @FXML
    void managementButtonAction(Event event) {
            List<DTOFileUpload> dtoFileUploadList =mainController.getHeaderComponentController().getHeaderBodyComponentController().getFilesListDto();
            managementScreenComponentController.clearList();
            managementScreenComponentController.setFilesList(dtoFileUploadList);
//        if (isFirstAppear) {
//            List<DTOFileUpload> dtoFileUploadList =mainController.getHeaderComponentController().getHeaderBodyComponentController().getFilesListDto();
//            managementScreenComponentController.setFilesList(dtoFileUploadList);
//        }
//        isFirstAppear =true;
    }



//        if(engineManager.getWorld() != null) {
//            //managementScreenController.getTreeViewForDetailsScreenComponentController().initialTreeView();
//        }

    //
    //
    //
//}

    @FXML
    void allocationsButtonAction(Event event) {
        allocationsScreenComponentController.updateTableThread();
    }


    public void updateButtons() {
        managementButton.setDisable(false);
        allocationsButton.setDisable(false);
        executionsHistoryButton.setDisable(false);
    }

    public String getSimulationName() {
        return simulationName;
    }
}