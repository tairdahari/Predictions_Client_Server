//package subComponents.newExecutionScreen.environmentDetails.oneEnv;
//
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import subComponents.newExecutionScreen.environmentDetails.EnvironmentDetailsController;
//import utils.DTOPropertyDefinition;
//
//import java.util.LinkedList;
//import java.util.List;
//
//public class OneEnvironmentController {
//    @FXML private Label envName;
//    @FXML private Label envType;
//    @FXML private Label envNewValue;
//    @FXML private Label envNameLabel;
//    @FXML private Label envTypeLabel;
//    @FXML private Spinner<Double> rangeSpinner;
//    private SpinnerValueFactory<Double> spinnerValueFactory;
//    @FXML private ComboBox<Boolean> booleanComboBox;
//    @FXML private TextField stringTextField;
//    private List<EnvironmentListener> environmentListeners = new LinkedList<>();
//    private Double currValue = new Double(10F);
//    private EnvironmentDetailsController environmentDetailsController;
//
//    private float from;
//    private float to;
//
//    @FXML
//    public void initialize() {
//        setLabelsVisible(false);
//        ObservableList<Boolean> booleanItems = FXCollections.observableArrayList(true, false);
//        booleanComboBox.setItems(booleanItems);
//        rangeSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
//            onChange(oldValue, newValue);
//
//        });
//
//    }
//    public Label getEnvTypeLabel() {
//        return envTypeLabel;
//    }
//
//    public Spinner<Double> getRangeSpinner() {
//        return rangeSpinner;
//    }
//    public float getFrom() {
//        return from;
//    }
//
//    public float getTo() {
//        return to;
//    }
//
//    public void setLabelsText(DTOPropertyDefinition dtoEnvDefinition) {
//        envNameLabel.setText(dtoEnvDefinition.getName());
//        envTypeLabel.setText(dtoEnvDefinition.getType());
//        envNewValue.setVisible(true);
//
//        if (dtoEnvDefinition.getType().equals("float")) {
//            rangeSpinner.setVisible(true);
//            this.from = Float.parseFloat(dtoEnvDefinition.getFrom());
//            this.to = Float.parseFloat(dtoEnvDefinition.getTo());
//            SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(from,to , currValue);
//            rangeSpinner.setValueFactory(valueFactory);
//        } else if (dtoEnvDefinition.getType().equals("boolean")) {
//            ObservableList<Boolean> booleanItems = FXCollections.observableArrayList(true, false);
//            booleanComboBox.setItems(booleanItems);
//            booleanComboBox.setVisible(true);
//            booleanComboBox.setVisibleRowCount(2); // Set the visible row count to 2 for dropdown
//        } else if (dtoEnvDefinition.getType().equals("string")) {
//            stringTextField.setVisible(true);
//        }
//    }
//    public void setEnvNewValueVisible(boolean visible) {
//        envNewValue.setVisible(visible);
//    }
//    public void setLabelsVisible(boolean show) {
//        envName.setVisible(show);
//        envType.setVisible(show);
//        envNameLabel.setVisible(show);
//        envTypeLabel.setVisible(show);
//        envNewValue.setVisible(false);
//        rangeSpinner.setVisible(false);
//        booleanComboBox.setVisible(false);
//        stringTextField.setVisible(false);
//
//    }
//
//    public ComboBox<Boolean> getBooleanComboBox() {
//        return booleanComboBox;
//    }
//
//    public TextField getStringTextField() {
//        return stringTextField;
//    }
//
//    public void onChange(Double oldVal, Double newVal) {
//        spinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory((double) from, (double) to, newVal);
//        rangeSpinner.setValueFactory(spinnerValueFactory);
//        this.currValue = newVal;
//    }
//
//    public void setEnvDetailsController(EnvironmentDetailsController environmentDetailsController) {
//        this.environmentDetailsController = environmentDetailsController;
//    }
//
//    public void reRun(Object object) {
//
//        if (object instanceof Float) {
//            spinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, Double.parseDouble(object.toString()));
//            rangeSpinner.setValueFactory(spinnerValueFactory);
//        } else if (object instanceof Boolean) {
//            booleanComboBox.getItems().clear();
//            Boolean value = (Boolean) object;
//            booleanComboBox.getSelectionModel().select(value);
//        } else if (object instanceof String) {
//            String value = (String) object;
//            stringTextField.setText(value);
//        }
//    }
//}