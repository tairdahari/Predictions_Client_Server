package subComponents.results.executionList.task;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import prediction.worldManager.IWorldManager;
import utils.DTOSimulationDetails;

public class ExecutionListTask extends Task<Boolean> {
    private IWorldManager engineManager;
    private final UIAdapter uiAdapter;
    private final SimpleIntegerProperty currentSimulationId;
    private final SimpleBooleanProperty onFinish;
    private SimpleDoubleProperty progress;

    public ExecutionListTask(IWorldManager engineManager, UIAdapter uiAdapter, SimpleIntegerProperty currentSimulationId, SimpleBooleanProperty onFinish) {
        this.engineManager = engineManager;
        this.uiAdapter = uiAdapter;
        this.currentSimulationId = currentSimulationId;
        this.onFinish = onFinish;
        //this.progress = progress;
    }


    @Override
    protected Boolean call() throws Exception {
        int SLEEP_TIME = 100;
        DTOSimulationDetails dtoSimulationDetails = engineManager.getDtoSimulationDetailsByID(currentSimulationId.getValue());
        uiAdapter.update(dtoSimulationDetails);

        while (dtoSimulationDetails.getInProgress().equals("yes")) {
            dtoSimulationDetails = engineManager.getDtoSimulationDetailsByID(currentSimulationId.getValue());

            if (currentSimulationId.getValue().equals(currentSimulationId.toString())) {
                uiAdapter.update(dtoSimulationDetails);
            }
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException ignored) {}
        }
        onFinish.setValue(true);
        return Boolean.TRUE;
    }
}
