package clientSubComponents.newExecutionScreen.environmentDetails;//package subComponents.newExecutionScreen.environmentDetails;

import clientSubComponents.newExecutionScreen.app.NewExecutionScreenController;
import clientSubComponents.newExecutionScreen.environmentDetails.oneEnv.OneEnvironmentController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import utils.DTOPropertyDefinition;
import utils.DTOSimulationDefinition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EnvironmentDetailsController {
   @FXML
    private ListView<String> listView;

    @FXML private HBox oneEnvHBox;
    private Map<String, OneEnvironmentController> environmentControllerMap;
    private Map<String, Parent> environmentParentsrMap;
    private String selectedEnv;

    private NewExecutionScreenController newExecutionScreenController;

    @FXML
    public void initialize() {
        this.oneEnvHBox.getChildren().clear();
        this.environmentControllerMap =  new LinkedHashMap<>();
        this.environmentParentsrMap =  new LinkedHashMap<>();
    }

    public HBox getOneEnvHBox() {
        return oneEnvHBox;
    }

    public void setListView(List<DTOPropertyDefinition> environments) {
        newExecutionScreenController.clearEnvList();
        for(DTOPropertyDefinition oneEnv : environments) {
            listView.getItems().add(oneEnv.getName());
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/clientSubComponents/newExecutionScreen/environmentDetails/oneEnv/oneEnvironment.fxml"));
                Parent oneEnvContent = fxmlLoader.load();
                environmentParentsrMap.put(oneEnv.getName(), oneEnvContent);
                OneEnvironmentController controller = fxmlLoader.getController();
                controller.setEnvDetailsController(this);
                environmentControllerMap.put(oneEnv.getName(), controller);
                newExecutionScreenController.addEnvToList(controller);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {

                selectedEnv = listView.getSelectionModel().getSelectedItem();
                setOneEnvDetails(getDtoEnvByName(selectedEnv, environments));
            }
        });

    }

    private void setOneEnvDetails(DTOPropertyDefinition dtoEnvDefinition) {
        OneEnvironmentController oneEnvComponentController = environmentControllerMap.get(dtoEnvDefinition.getName());
        oneEnvComponentController.setLabelsVisible(true);

        // Reset UI components to their default state
        oneEnvComponentController.getRangeSpinner().setVisible(false);
        oneEnvComponentController.getBooleanComboBox().setVisible(false);
        oneEnvComponentController.getStringTextField().setVisible(false);

        if (dtoEnvDefinition.getType().equals("float"))   {
            oneEnvComponentController.setLabelsText(dtoEnvDefinition);
            Spinner<Double> rangeSpinner = oneEnvComponentController.getRangeSpinner();
            rangeSpinner.setVisible(true);
        } else if (dtoEnvDefinition.getType().equals("boolean")) {
            oneEnvComponentController.setLabelsText(dtoEnvDefinition);
            oneEnvComponentController.getBooleanComboBox().setVisible(true);
        } else if (dtoEnvDefinition.getType().equals("string")) {
            oneEnvComponentController.setLabelsText(dtoEnvDefinition);
            oneEnvComponentController.getStringTextField().setVisible(true);
        }
        oneEnvHBox.getChildren().clear();
        oneEnvHBox.getChildren().add(environmentParentsrMap.get(dtoEnvDefinition.getName()));
    }


    private DTOPropertyDefinition getDtoEnvByName(String selectedEnv, List<DTOPropertyDefinition> environments) {
        for(DTOPropertyDefinition oneEnv : environments) {
            if(oneEnv.getName().equals(selectedEnv)) {
                return oneEnv;
            }
        }
        throw new RuntimeException();
    }
    public List<Object> getListValues(DTOSimulationDefinition dtoSimulationDefinition) {
        List<Object> values = new ArrayList<>();
        List<DTOPropertyDefinition> dtoEnvironmentVariables = dtoSimulationDefinition.getDtoEnvironmentVariables();

        for (DTOPropertyDefinition dtoEnvironmentVariable : dtoEnvironmentVariables) {
            values.add(dtoEnvironmentVariable.getValue());
        }
        int listItr = 0;
        for(Map.Entry<String , OneEnvironmentController> environmentControllerEntry : environmentControllerMap.entrySet()) {
            switch (environmentControllerEntry.getValue().getEnvTypeLabel().getText()) {
                case "float":
                    values.set(listItr, new Float(environmentControllerEntry.getValue().getRangeSpinner().getValue()));
                    break;
                case "boolean":
                    values.set(listItr, new Boolean(environmentControllerEntry.getValue().getBooleanComboBox().getValue()));
                    break;
                case "string":
                    values.set(listItr, environmentControllerEntry.getValue().getStringTextField().getText());
                    break;
            }
            listItr++;
        }
        return values;
    }

    public void setNewExecutionScreenController(NewExecutionScreenController newExecutionScreenController) {
        this.newExecutionScreenController = newExecutionScreenController;
    }

    public void clear() {
        for (OneEnvironmentController controller : environmentControllerMap.values()) {
            controller.setLabelsVisible(false);

            // Check if the Spinner exists before resetting
            Spinner<Double> rangeSpinner = controller.getRangeSpinner();
            if (rangeSpinner.getValue() != null) {
                rangeSpinner.setVisible(false);
                rangeSpinner.getValueFactory().setValue((double) controller.getFrom());
            }

            // Check if the ComboBox exists before resetting
            ComboBox<Boolean> booleanComboBox = controller.getBooleanComboBox();
            if (booleanComboBox != null) {
                booleanComboBox.setVisible(false);
                booleanComboBox.getSelectionModel().clearSelection();
            }

            // Check if the TextField exists before resetting
            TextField stringTextField = controller.getStringTextField();
            if (stringTextField != null) {
                stringTextField.setVisible(false);
                stringTextField.setText("");
            }
        }

        // Clear the selected item in the ListView
        listView.getSelectionModel().clearSelection();
    }

}