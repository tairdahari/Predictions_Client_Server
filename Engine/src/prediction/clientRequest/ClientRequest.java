package prediction.clientRequest;

import prediction.Termination.clientTerminationManager.TerminationByClientManager;
import prediction.users.User;
import utils.DTOTerminationByClient;

import java.util.List;

public class ClientRequest {
    private final User user;
    //private IWorldManager worldManager;
    private final String simulationName;
    private final Integer numOfSimulationsToRun;
    private final TerminationByClientManager terminationManager;
    private Integer serialNumber;
    private String requestStatus;
    private Integer runningSimulationsNumber = 0;
    private Integer endedSimulationNumber = 0;
    private boolean stopBoolean = false;


    public ClientRequest(Integer requestSerialNumber, User user, String simulationName, String numOfSimulationsToRun, List<DTOTerminationByClient> termination) {
        this.serialNumber = requestSerialNumber;
        this.user = user;
        this.simulationName = simulationName;
        this.numOfSimulationsToRun = Integer.valueOf(numOfSimulationsToRun);
        this.terminationManager = new TerminationByClientManager(termination);
    }

    public User getUser() {
        return user;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public Integer getNumOfSimulationsToRun() {
        return numOfSimulationsToRun;
    }

    public TerminationByClientManager getTerminationManager() {
        return terminationManager;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatusLogic(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Integer getRunningSimulationsNumber() {
        return runningSimulationsNumber;
    }

    public void setRunningSimulationsNumber() {
        this.runningSimulationsNumber++;
    }

    public Integer getEndedSimulationNumber() {
        return endedSimulationNumber;
    }

    public void setEndedSimulationNumber() {
        this.endedSimulationNumber++;
        this.runningSimulationsNumber--;
    }

    public boolean isStopBoolean() {
        return stopBoolean;
    }

    public void setStopBoolean(boolean stopBoolean) {
        this.stopBoolean = stopBoolean;
    }
}
