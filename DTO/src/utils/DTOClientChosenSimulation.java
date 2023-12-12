package utils;

import prediction.Termination.ByUser;
import prediction.clientRequest.ClientRequest;

import java.util.List;

public class DTOClientChosenSimulation {
    private String serialNumber;
    private String simulationName;
    private String amountToRun;
    private String requestStatus;
    private String runningSimulationsNumber = "0";
    private String endedSimulationNumber = "0";
    private String clientName;
    private String termination;


    public DTOClientChosenSimulation(ClientRequest clientRequest) {
        this.serialNumber = String.valueOf(clientRequest.getSerialNumber());
        this.simulationName = clientRequest.getSimulationName();
        this.amountToRun = String.valueOf(clientRequest.getNumOfSimulationsToRun());
        this.requestStatus = clientRequest.getRequestStatus();
        this.runningSimulationsNumber = String.valueOf(clientRequest.getRunningSimulationsNumber());
        this.endedSimulationNumber = String.valueOf(clientRequest.getEndedSimulationNumber());
        this.clientName = clientRequest.getUser().getUserName();
        this.termination = createTerminationString(clientRequest.getTerminationManager().getSimulationTerminationConditions());
    }
    private String createTerminationString(List<Object> simulationTerminationConditions) {
        for(Object termination : simulationTerminationConditions) {
            if(termination instanceof ByUser) {
                return "User";
            }
        }
        return "Seconds/Ticks";
    }

    public String getSerialNumber() {
        return serialNumber;
    }



    public String getSimulationName() {
        return simulationName;
    }


    public String getAmountToRun() {
        return amountToRun;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public String getRunningSimulationsNumber() {
        return runningSimulationsNumber;
    }


    public String getEndedSimulationNumber() {
        return endedSimulationNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public String getTermination() {
        return termination;
    }
}
