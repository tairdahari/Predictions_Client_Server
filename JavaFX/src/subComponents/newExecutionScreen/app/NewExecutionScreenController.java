//package subComponents.newExecutionScreen.app;
//
//import javafx.application.Platform;
//import javafx.event.ActionEvent;
//import javafx.event.Event;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.control.Button;
//
//import prediction.engineManager.IEngineManager;
//import subComponents.mainScreen.body.BodyController;
//import subComponents.newExecutionScreen.environmentDetails.EnvironmentDetailsController;
//import subComponents.newExecutionScreen.environmentDetails.oneEnv.OneEnvironmentController;
//import subComponents.newExecutionScreen.populationDetails.PopulationDetailsController;
//import subComponents.newExecutionScreen.populationDetails.entityCount.EntityCountController;
//import utils.DTOReRun;
//
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.Consumer;
//
//public class NewExecutionScreenController {
//    private BodyController mainBodyComponentController;
//    private IEngineManager engineManager;
//    private List<Consumer<String>> valueSelectionListeners = new ArrayList<>();
//    private EnvironmentDetailsController environmentDetailsController;
//    private PopulationDetailsController populationDetailsController;
//    private List<EntityCountController> entityCountControllers = new ArrayList<>();
//    private List<OneEnvironmentController> environmentControllers = new ArrayList<>();
//    private Event event;
//    private boolean animation = true;
//    private Thread thread;
//
//    @FXML
//    private BorderPane newExecutionScreenComponent;
//    @FXML private HBox detailsHbox;
//    @FXML private Button startButton;
//
//    @FXML
//    public void initialize(){
//    }
//
//    public void setMainController(BodyController mainBodyController) {
//        this.mainBodyComponentController = mainBodyController;
//    }
//
//    public void setEngineManager(IEngineManager engineManager) {
//        this.engineManager = engineManager;
//    }
//    public void addValueSelectionListener(Consumer<String> listener) {
//        valueSelectionListeners.add(listener);
//    }
//    public void notifyValueSelectionListeners(String selectedValue) {
//        for (Consumer<String> listener : valueSelectionListeners) {
//            listener.accept(selectedValue);
//        }
//    }
//
//    public void setPopulation() {
//        try {
//            detailsHbox.getChildren().clear();
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/subComponents/newExecutionScreen/populationDetails/populationDetails.fxml"));
//            Parent populationDetailsUI = fxmlLoader.load();
//            PopulationDetailsController populationDetailsController = fxmlLoader.getController();
//            populationDetailsController.setNewExecutionScreenController(this);
//            populationDetailsController.setMaxCountLabel(engineManager.getSimulationDefinition().getDtoGridDefinition().getSize());
//            this.populationDetailsController = populationDetailsController;
//            populationDetailsController.setEntities(engineManager.getSimulationDefinition().getDtoEntityDefinition());
//
//            // Add populationDetailsUI to detailsHbox
//            detailsHbox.getChildren().add(populationDetailsUI);
//
//            // Apply the fade-in animation after adding to detailsHbox
//            if (animation) {
//                populationDetailsController.applyFadeInAnimation();
//                //startAnimations(populationDetailsController);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private void startAnimations(PopulationDetailsController populationDetailsController) {
//        //TODO THREAD
//        thread = new Thread(() -> {
//            int SLEEP_TIME = 200;
//            while(true) {
//                Platform.runLater( ()-> {
//                    populationDetailsController.applyFadeInAnimation();
//                });
//                try {
//                    Thread.sleep(SLEEP_TIME);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
//        thread.setDaemon(true);
//        thread.start();
//    }
//
//
//    public void setEnvironment() {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/subComponents/newExecutionScreen/environmentDetails/environmentDetails.fxml"));
//            Parent envDetailsUI = fxmlLoader.load();
//            EnvironmentDetailsController environmentDetailsController = fxmlLoader.getController();
//            this.environmentDetailsController = environmentDetailsController;
//            environmentDetailsController.setNewExecutionScreenController(this);
//            environmentDetailsController.setListView(engineManager.getSimulationDefinition().getDtoEnvironmentVariables());
//            detailsHbox.getChildren().add(envDetailsUI);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void startButtonAction(ActionEvent actionEvent) {
//        List<Integer> populationList = populationDetailsController.initListPopulation(engineManager);
//
//        engineManager.initPopulation(populationList);
//        List<Object> userEnvVarChoices = environmentDetailsController.getListValues(engineManager);
//        engineManager.initialization(userEnvVarChoices);
//        engineManager.start(populationList,userEnvVarChoices);
//        mainBodyComponentController.startClicked();
//    }
//
//    @FXML
//    void clearButtonAction(ActionEvent event) {
//        if (populationDetailsController != null) {
//            populationDetailsController.clear();
//        }
//
//        if (environmentDetailsController != null) {
//            environmentDetailsController.clear();
//        }
//    }
//
//
//    public void OnReRun(String id) {
//        DTOReRun dtoReRun = engineManager.reRun(Integer.parseInt(id));
//        int i = 0;
//
//        for (Integer integer : dtoReRun.getPopulationList()) {
//            entityCountControllers.get(i).reRun(integer);
//            i++;
//        }
//
//        i=0;
//
//        for (Object object : dtoReRun.getUserEnvVarChoices()) {
//            environmentControllers.get(i).reRun(object);
//            i++;
//        }
//    }
//
//    public void addEntityToList(EntityCountController entityCountController)
//    {
//        entityCountControllers.add(entityCountController);
//    }
//
//    public void addEnvToList(OneEnvironmentController environmentController)
//    {
//        environmentControllers.add(environmentController);
//    }
//
//    public void clearEntityList()
//    {
//        entityCountControllers.clear();
//    }
//
//    public void clearEnvList() {
//        environmentControllers.clear();
//    }
//
//    public void fadeAnimation() {
//        animation = true;
//        //populationDetailsController.applyFadeInAnimation();
//    }
//
//    public void stopAnimation() {
//        animation = false;
//    }
//}