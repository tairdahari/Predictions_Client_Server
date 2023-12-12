package prediction.users;


import prediction.clientRequest.ClientRequest;
import prediction.execution.simulationExecutionDetails.ISimulationDetails;
import utils.DTOClientChosenSimulation;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String userName;
    private Map<Integer, ClientRequest> allClientRequests;
    private Map<Integer, ISimulationDetails> allClientSimulations;//TODO

    public User(String name) {
        this.userName = name;
        this.allClientRequests = new HashMap<>();
        this.allClientSimulations = new HashMap<>();
    }

    public void addRequest(ClientRequest clientRequest, Integer serialNumber) {
        allClientRequests.put(serialNumber, clientRequest);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setRequestStatus(String serialNumber, String status) {
        allClientRequests.get(serialNumber).setRequestStatus(status);

    }

    public String getUserName() {
        return userName;
    }

    public Map<String, DTOClientChosenSimulation> getAllClientRequestsDto() {
        Map<String, DTOClientChosenSimulation> allRequestsDto= new HashMap<>();

        for (Map.Entry<Integer, ClientRequest> clientChosenSimulationEntry :allClientRequests.entrySet()) {
            DTOClientChosenSimulation dtoClientChosenSimulation = new DTOClientChosenSimulation(clientChosenSimulationEntry.getValue());
            allRequestsDto.put(String.valueOf(clientChosenSimulationEntry.getKey()),dtoClientChosenSimulation);
        }
        return allRequestsDto;
    }

    public Map<Integer, ISimulationDetails> getAllClientSimulations() {
        return allClientSimulations;
    }

    public void setAllClientSimulations(Map<Integer, ISimulationDetails> allClientSimulations) {
        this.allClientSimulations = allClientSimulations;
    }
}
