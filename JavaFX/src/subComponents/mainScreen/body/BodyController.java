//package subComponents.mainScreen.body;
//
//import javafx.beans.property.SimpleBooleanProperty;
//import javafx.event.Event;
//import javafx.fxml.FXML;
//import javafx.scene.control.Tab;
//import javafx.scene.control.TabPane;
//import javafx.scene.image.Image;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.BorderPane;
//import prediction.engineManager.IEngineManager;
//import subComponents.detailsScreen.app.DetailsScreenController;
//import subComponents.mainScreen.app.AppController;
//import subComponents.newExecutionScreen.app.NewExecutionScreenController;
//import subComponents.results.app.ResultsScreenController;
//
//public class BodyController {
//    private AppController mainController;
//    private IEngineManager engineManager;
//    @FXML
//    private Tab resultsButton;
//    @FXML
//    private Tab newExecutionButton;
//    @FXML
//    private Tab detailsButton;
//    @FXML
//    private AnchorPane detailsScreenComponent;
//    @FXML
//    private DetailsScreenController detailsScreenComponentController;
//
//    @FXML private BorderPane newExecutionScreenComponent;
//    @FXML private NewExecutionScreenController newExecutionScreenComponentController;
//    @FXML private BorderPane resultsScreenComponent;
//
//    @FXML private ResultsScreenController resultsScreenComponentController;
//    @FXML
//    private TabPane optionsTabPane;
//    private SimpleBooleanProperty resultsButtonProperty;
//    private SimpleBooleanProperty newExecutionButtonProperty;
//    private SimpleBooleanProperty detailsButtonProperty;
//    private Event event;
//    private Image image;
//
//
//    public BodyController()
//    {
//        resultsButtonProperty =  new SimpleBooleanProperty(false);
//        newExecutionButtonProperty =  new SimpleBooleanProperty(false);
//        detailsButtonProperty =  new SimpleBooleanProperty(true);
//    }
//
//    @FXML
//    public void initialize() {
//        if(detailsScreenComponentController != null && newExecutionScreenComponentController != null && resultsScreenComponentController != null) {
//
//            detailsScreenComponentController.setMainController(this);
//            newExecutionScreenComponentController.setMainController(this);
//            resultsScreenComponentController.setMainController(this);
//        }
//        setTabsListener();
//
//    }
//    public void updatePushTabButtons()
//    {
//        detailsButton.setDisable(false);
//        newExecutionButton.setDisable(false);
//        resultsButton.setDisable(false);
//    }
//    private void setTabsListener() {
//        optionsTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
//            if (newTab == detailsButton) {
//                detailsButtonProperty.set(true);
//                newExecutionButtonProperty.set(false);
//                resultsButtonProperty.set(false);
//            } else if (newTab == newExecutionButton) {
//                detailsButtonProperty.set(false);
//                newExecutionButtonProperty.set(true);
//                resultsButtonProperty.set(false);
//            } else if (newTab == resultsButton) {
//                detailsButtonProperty.set(false);
//                newExecutionButtonProperty.set(false);
//                resultsButtonProperty.set(true);
//            }
//        });
//    }
//    public void setMainController(AppController mainController) {
//        this.mainController = mainController;
//    }
//
//    public void setEngineManager(IEngineManager engineManager) {
//        this.engineManager = engineManager;
//        detailsScreenComponentController.setEngineManager(engineManager);
//        newExecutionScreenComponentController.setEngineManager(engineManager);
//        resultsScreenComponentController.setEngineManager(engineManager);
//    }
//
//    @FXML
//    void resultsButtonAction(Event event) {
//        resultsScreenComponentController.setExecutionList();
//        resultsScreenComponentController.clearExecutionDetails();
//        resultsScreenComponentController.clearExecutionResults();
//    }
//
//    public void setDetails() {
//        detailsScreenComponentController.getTreeViewForDetailsScreenComponentController().initialTreeView();
//    }
//
//    @FXML
//    void detailsButtonAction(Event event) {
//        detailsScreenComponentController.getvBoxComponent().getChildren().clear();
//
//        if(engineManager.getWorld() != null) {
//            detailsScreenComponentController.getTreeViewForDetailsScreenComponentController().initialTreeView();
//        }
//    }
//
//    @FXML
//    void newExecutionButtonAction(Event event) {
//        newExecutionScreenComponentController.setPopulation();
//        newExecutionScreenComponentController.setEnvironment();
//    }
//
//    public SimpleBooleanProperty isDetailsButtonProperty() {
//        return resultsButtonProperty;
//    }
//
//    public SimpleBooleanProperty isNewExecutionButtonProperty() {
//        return detailsButtonProperty;
//    }
//
//    public SimpleBooleanProperty isResultsButtonProperty() {
//        return newExecutionButtonProperty;
//    }
//
//    public void OnReRunClicked(String id) {
//        detailsButtonProperty.set(false);
//        newExecutionButtonProperty.set(true);
//        resultsButtonProperty.set(false);
//        newExecutionButtonAction(event);
//        optionsTabPane.getSelectionModel().select(newExecutionButton);
//        newExecutionScreenComponentController.OnReRun(id);
//
//    }
//    public void startClicked() {
//        detailsButtonProperty.set(false);
//        newExecutionButtonProperty.set(false);
//        resultsButtonProperty.set(true);
//        resultsButtonAction(event);
//        optionsTabPane.getSelectionModel().select(resultsButton);
//
//    }
//
//    public void fadeAnimation() {
//        this.newExecutionScreenComponentController.fadeAnimation();
//    }
//
//
//
////    private void setComponentBackground(AnchorPane detailsScreenComponent, String imageUrl, double opacity) {
////        String backgroundImageUrl = "url('" + imageUrl + "')";
////        String backgroundStyle = "-fx-background-image: " + backgroundImageUrl + "; " +
////                "-fx-background-repeat: no-repeat; " +
////                "-fx-background-size: cover;"; // Adjust 'cover' based on your needs
////
////        detailsScreenComponent.setStyle(backgroundStyle);
////        detailsScreenComponent.setOpacity(opacity);
////        optionsTabPane.setOpacity(1);
////    }
//
//    public void stopAnimation() {
//        this.newExecutionScreenComponentController.stopAnimation();
//    }
//
//}