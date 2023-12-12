//package subComponents.newExecutionScreen.populationDetails.entityCount;
//
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.fxml.FXML;
//import javafx.scene.control.Label;
//import javafx.scene.control.Spinner;
//import javafx.scene.control.SpinnerValueFactory;
//import subComponents.newExecutionScreen.populationDetails.PopulationDetailsController;
//import utils.DTOEntityDefinition;
//
//public class EntityCountController implements PopulationCountListener {
//    @FXML
//    private Spinner<Integer> populationCounter;
//    @FXML
//    private Label entityName;
//    private Integer maxSize = 100;
//    private Boolean isChanged;
//    private PopulationDetailsController populationDetailsController;
//    private SpinnerValueFactory<Integer> spinnerValueFactory;
//    private DTOEntityDefinition entityDefinition;
//
//    @FXML
//    public void initialize() {
//        populationCounter.setEditable(true);
//        isChanged = false;
//    }
//
//    public Spinner<Integer> getPopulationCounter() {
//        return populationCounter;
//    }
//
//    @Override
//    public void onChange(int oldVal, int newVal) {
//        if(!isChanged) {
//            maxSize = maxSize + (oldVal - newVal);
//            spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxSize.intValue(), spinnerValueFactory.getValue());
//            populationCounter.setValueFactory(spinnerValueFactory);
//
//        }
//        isChanged = false;
//    }
//
//
//    public void updateDetails(DTOEntityDefinition entityDefinition) {
//        this.entityDefinition = entityDefinition;
//        entityName.setText(entityDefinition.getEntityName());
//    }
//    public void setPopulationDetailsController(PopulationDetailsController populationDetailsController) {
//        this.populationDetailsController = populationDetailsController;
//    }
//
//    public void setMaxSize(Integer newMaxSize) {
//        maxSize = newMaxSize;
//        spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxSize.intValue(), 0);
//        populationCounter.setValueFactory(spinnerValueFactory);
//
//        populationCounter.valueProperty().addListener(new ChangeListener<Integer>() {
//            @Override
//            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
//                isChanged = true;
//                populationDetailsController.currentCounterChanged(oldValue, newValue);
//            }
//        });
//    }
//
//    public void reRun(Integer integer)
//    {
//        spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxSize, integer);
//        populationCounter.setValueFactory(spinnerValueFactory);
//    }
//}
