package prediction.execution.simulations;

import prediction.execution.simulationExecutionDetails.ISimulationDetails;

import java.util.List;

public interface SimulationExecutionManager {

    List<ISimulationDetails> getAllSimulations();

    void setAllSimulations(List<ISimulationDetails> allSimulations);
}
