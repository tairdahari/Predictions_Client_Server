package prediction.execution.simulations;


import prediction.execution.simulationExecutionDetails.ISimulationDetails;

import java.util.ArrayList;
import java.util.List;

public class SimulationsExecutionManagerImpl implements SimulationExecutionManager {
    private List<ISimulationDetails> allSimulations;

    public SimulationsExecutionManagerImpl() {
        this.allSimulations = new ArrayList<>();
    }
    @Override
    public List<ISimulationDetails> getAllSimulations() {
        return allSimulations;
    }
    @Override
    public void setAllSimulations(List<ISimulationDetails> allSimulations) {
        this.allSimulations = allSimulations;
    }
}