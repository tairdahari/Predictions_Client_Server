package subComponents.managmentScreen.app;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import subComponents.mainScreen.body.BodyController;
import utils.DTOFileUpload;

import java.util.List;

public class ManagementScreenController {

    @FXML
    private Label completedSimulations;

    @FXML
    private Label queueSize;

    @FXML
    private Label workingThreads;

    @FXML
    private Button setCountButton;

    @FXML
    private Spinner<Integer> threadCountSpinner;

    @FXML
    private ListView<String> simulationsList;
    @FXML
    public void initialize() {
        setSpinners();
    }

    private BodyController mainBodyComponentController;
    public ListView<String> getSimulationsList() {
        return simulationsList;
    }
    public void queueUpdate() {
//        DTOQueue dtoQueue = engineManager.getDtoQueue();
//        queueSize.setText(dtoQueue.getQueueSize().toString());
//        workingThreads.setText(dtoQueue.getWaiting().toString());
//        completedSimulations.setText(dtoQueue.getFinished().toString());
    }

    public void setMainController(BodyController bodyController) {
        mainBodyComponentController = bodyController;
    }

    public void setFilesList(List<DTOFileUpload> dtoFileUploadList) {

        for (DTOFileUpload file: dtoFileUploadList) {
            simulationsList.getItems().add(file.getFileName());
        }

//        simulationsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
//                @Override
//                public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
//
//                    selectedEnv = simulationsList.getSelectionModel().getSelectedItem();
//                    setOneEnvDetails(getDtoEnvByName(selectedEnv, environments));
//                }
//            });
//
//        }

    }
    private void setSpinners() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE);
        valueFactory.setValue(0);
        threadCountSpinner.setValueFactory(valueFactory);
    }

    public void clearList() {
        ObservableList<String> simulations = simulationsList.getItems();
        simulations.clear();
    }

    public void setCount(ActionEvent actionEvent) {

    }
}
