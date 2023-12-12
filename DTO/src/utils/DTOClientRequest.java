package utils;

import java.util.List;

public class DTOClientRequest {
    private final String clientName;
    private final String simulationName;
    private final String numOfSimulationsToRun;
    private final List<DTOTerminationByClient> termination;


    public DTOClientRequest(String clientName, String simulationName, String numOfSimulationsToRun, List<DTOTerminationByClient> termination) {
        this.clientName = clientName;
        this.simulationName = simulationName;
        this.numOfSimulationsToRun = numOfSimulationsToRun;
        this.termination = termination;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public String getNumOfSimulationsToRun() {
        return numOfSimulationsToRun;
    }

    public List<DTOTerminationByClient> getTermination() {
        return termination;
    }

    public String getClientName() {
        return clientName;
    }
}
