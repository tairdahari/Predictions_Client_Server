package clientSubComponents.mainScreen.body;

import clientSubComponents.detailsScreen.app.DetailsScreenController;
import clientSubComponents.mainScreen.app.AppController;
import clientSubComponents.newExecutionScreen.app.NewExecutionScreenController;
import clientSubComponents.requestScreen.RequestScreenController;
import clientSubComponents.results.app.ResultsScreenController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class BodyController {
    private AppController mainController;
//    private IEngineManager engineManager;
    @FXML
    private Tab resultsButton;
    @FXML
    private Tab newExecutionButton;
    @FXML
    private Tab detailsButton;
    @FXML
    private Tab requestsButton;
    @FXML
    private AnchorPane detailsScreenComponent;
    @FXML
    private AnchorPane requestsScreenComponent;
    @FXML
    private DetailsScreenController detailsScreenComponentController;
    @FXML
    private RequestScreenController requestsScreenComponentController;

    @FXML private BorderPane newExecutionScreenComponent;
    @FXML private NewExecutionScreenController newExecutionScreenComponentController;
    @FXML private BorderPane resultsScreenComponent;

    @FXML private ResultsScreenController resultsScreenComponentController;
    @FXML
    private TabPane optionsTabPane;
    private SimpleBooleanProperty resultsButtonProperty;
    private SimpleBooleanProperty newExecutionButtonProperty;
    private SimpleBooleanProperty detailsButtonProperty;
    private SimpleBooleanProperty requestButtonProperty;
    private Event event;
    private Image image;


    public BodyController()
    {
        resultsButtonProperty =  new SimpleBooleanProperty(false);
        newExecutionButtonProperty =  new SimpleBooleanProperty(false);
        requestButtonProperty = new SimpleBooleanProperty(false);
        detailsButtonProperty =  new SimpleBooleanProperty(true);
    }

    @FXML
    public void initialize() {
        if(detailsScreenComponentController != null && newExecutionScreenComponentController != null && resultsScreenComponentController != null && requestsScreenComponentController != null) {

            detailsScreenComponentController.setMainController(this);
            requestsScreenComponentController.setMainController(this);
            newExecutionScreenComponentController.setMainController(this);
            resultsScreenComponentController.setMainController(this);
        }
        setTabsListener();

    }
    public void updatePushTabButtons()
    {
        detailsButton.setDisable(false);
        requestsButton.setDisable(false);
        newExecutionButton.setDisable(false);
        resultsButton.setDisable(false);
    }
    private void setTabsListener() {
        optionsTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab == detailsButton) {
                detailsButtonProperty.set(true);
                requestButtonProperty.set(false);
                newExecutionButtonProperty.set(false);
                resultsButtonProperty.set(false);
            } else if (newTab == requestsButton) {
                detailsButtonProperty.set(false);
                requestButtonProperty.set(true);
                newExecutionButtonProperty.set(false);
                resultsButtonProperty.set(false);
            } else if (newTab == newExecutionButton) {
                detailsButtonProperty.set(false);
                requestButtonProperty.set(false);
                newExecutionButtonProperty.set(true);
                resultsButtonProperty.set(false);
            } else if (newTab == resultsButton) {
                detailsButtonProperty.set(false);
                requestButtonProperty.set(false);
                newExecutionButtonProperty.set(false);
                resultsButtonProperty.set(true);
            }
        });
    }
    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public NewExecutionScreenController getNewExecutionScreenComponentController(){
        return newExecutionScreenComponentController;
    }
    @FXML
    public void resultsButtonAction(Event event) {
        String simulationName = newExecutionScreenComponentController.getSimulationName();
        resultsScreenComponentController.setSimulationName(simulationName);
        resultsScreenComponentController.setExecutionList(simulationName);
        resultsScreenComponentController.clearExecutionDetails();
        resultsScreenComponentController.clearExecutionResults();
    }
    @FXML
    void requestsButtonAction() {
        requestsScreenComponentController.setSimulationsInSystem();
    }

    @FXML
    void detailsButtonAction(Event event) {
        detailsScreenComponentController.getvBoxComponent().getChildren().clear();
        detailsScreenComponentController.getTreeViewForDetailsScreenComponentController().initialTreeView();

    }

    @FXML
    public void newExecutionButtonAction(Event event) {
        newExecutionScreenComponentController.getDetailsHbox().getChildren().clear();
        newExecutionScreenComponentController.setPopulation();
        newExecutionScreenComponentController.setEnvironment();
        //optionsTabPane.getSelectionModel().select(newExecutionButton);
    }

    public SimpleBooleanProperty isDetailsButtonProperty() {
        return resultsButtonProperty;
    }

    public SimpleBooleanProperty isNewExecutionButtonProperty() {
        return detailsButtonProperty;
    }

    public SimpleBooleanProperty isResultsButtonProperty() {
        return newExecutionButtonProperty;
    }

    public void OnReRunClicked(String id) {
        detailsButtonProperty.set(false);
        requestButtonProperty.set(false);
        newExecutionButtonProperty.set(true);
        resultsButtonProperty.set(false);
        newExecutionButtonAction(event);
        optionsTabPane.getSelectionModel().select(newExecutionButton);
        newExecutionScreenComponentController.OnReRun(id);

    }
    public void startClicked() {
        detailsButtonProperty.set(false);
        newExecutionButtonProperty.set(false);
        requestButtonProperty.set(false);
        resultsButtonProperty.set(true);
        resultsButtonAction(event);
        optionsTabPane.getSelectionModel().select(resultsButton);

    }

    public AppController getMainController() {
        return mainController;
    }

    public void openTabClientDefinition() {
        optionsTabPane.getSelectionModel().select(detailsButton);
    }

    public void openTabNewExecution() {
        optionsTabPane.getSelectionModel().select(newExecutionButton);
    }
}