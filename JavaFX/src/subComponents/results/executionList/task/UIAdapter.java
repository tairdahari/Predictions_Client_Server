package subComponents.results.executionList.task;

import javafx.application.Platform;
import utils.DTOSimulationDetails;

import java.util.function.Consumer;

public class UIAdapter {
    private final Consumer<DTOSimulationDetails> simulationExecution;

    public UIAdapter(Consumer<DTOSimulationDetails> simulationExecution) {
        this.simulationExecution = simulationExecution;
    }
    
    public void update(DTOSimulationDetails simulationDetails) {
        Platform.runLater(
                () -> {
                    simulationExecution.accept(simulationDetails);
                }
        );
    }
}
