//package subComponents.newExecutionScreen.populationDetails;
//
//import javafx.animation.FadeTransition;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.control.Label;
//import javafx.scene.layout.VBox;
//import javafx.util.Duration;
//import prediction.engineManager.IEngineManager;
//import subComponents.newExecutionScreen.app.NewExecutionScreenController;
//import subComponents.newExecutionScreen.populationDetails.entityCount.EntityCountController;
//import subComponents.newExecutionScreen.populationDetails.entityCount.PopulationCountListener;
//import utils.DTOEntityDefinition;
//
//
//import java.io.IOException;
//import java.util.*;
//
//public class PopulationDetailsController {
//    @FXML private VBox populationVboxComponent;
//    @FXML private Label currentCountLabel;
//    @FXML private Label maxCountLabel;
//    private List<PopulationCountListener> populationCountListeners = new LinkedList<>();
//    private Map<String,EntityCountController> entityCountControllers =  new LinkedHashMap<>();
//
//    private NewExecutionScreenController newExecutionScreenController;
//
//    @FXML
//    public void initialize() {
//        currentCountLabel.setText("0");
//        Map<String,EntityCountController> entityCountControllers = new LinkedHashMap<>();
//    }
//    public void applyFadeInAnimation() {
//        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(5), populationVboxComponent);
//        fadeTransition.setFromValue(0.0); // Start with opacity 0
//        fadeTransition.setToValue(1.0);   // End with opacity 1
//        fadeTransition.play();
//    }
//    public void setMaxCountLabel(Integer maxSize) {
//        maxCountLabel.setText(maxSize.toString());}
//    public void setEntities(List<DTOEntityDefinition> entities) {
//        newExecutionScreenController.clearEntityList();
//        for(DTOEntityDefinition entityDefinition : entities) {
//            try {
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/subComponents/newExecutionScreen/populationDetails/entityCount/entityCount.fxml"));
//                Parent populationLoaderContent = fxmlLoader.load();
//                EntityCountController controller = fxmlLoader.getController();
//
//                controller.setMaxSize(Integer.parseInt(maxCountLabel.getText()));
//                populationVboxComponent.getChildren().add(populationLoaderContent);
//                controller.updateDetails(entityDefinition);
//                controller.setPopulationDetailsController(this);
//                entityCountControllers.put(entityDefinition.getEntityName(), controller);
//                newExecutionScreenController.addEntityToList(controller);
//                populationCountListeners.add(controller);
//
//            } catch (IOException e) {
//                //throw new RuntimeException(e);
//            }
//        }
//    }
//
//    public void currentCounterChanged(int oldVal, int newVal) {
//        Integer oldCurrentValue = Integer.parseInt(currentCountLabel.getText());
//        Integer tempVal = oldCurrentValue;
//        tempVal = tempVal - oldVal + newVal;
//        currentCountLabel.setText(tempVal.toString());
//
//        for (PopulationCountListener populationCountListeners : populationCountListeners) {
//            populationCountListeners.onChange(oldCurrentValue, tempVal);
//        }
//    }
//
//    public List<Integer> initListPopulation(IEngineManager engineManager) {
//        List<Integer> populationCounters = new ArrayList<>();
//        int listItr = 0;
//        for(Map.Entry<String , EntityCountController> entityCountControllerEntry : entityCountControllers.entrySet()) {
//            populationCounters.add(entityCountControllerEntry.getValue().getPopulationCounter().getValue());
//            listItr++;
//        }
//        return populationCounters;
//    }
//
//    public void setNewExecutionScreenController(NewExecutionScreenController newExecutionScreenController) {
//        this.newExecutionScreenController = newExecutionScreenController;
//    }
//
//    public void clear() {
//        for (EntityCountController controller : entityCountControllers.values()) {
//            controller.getPopulationCounter().getValueFactory().setValue(0);
//        }
//        currentCountLabel.setText("0");
//    }
//}
