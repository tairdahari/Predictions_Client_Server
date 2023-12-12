package prediction.clientRequest;

import prediction.users.User;
import prediction.users.UserManager;
import utils.DTOClientChosenSimulation;
import utils.DTOClientRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestsManager {
    private Map<Integer, ClientRequest> allRequests;
    private Integer requestSerialNumber = 1;
    private UserManager userManager;


    public RequestsManager(UserManager userManager) {
        this.userManager = userManager;
        this.allRequests = new HashMap<>();
    }
    public User getUserByName(String userName) {
        return userManager.getUsers().get(userName);
    }
    public Map<Integer, ClientRequest> getAllRequestsInSystem() {
        return allRequests;
    }

    public Map<Integer, ClientRequest> getAllRequests() {
        return allRequests;
    }

    public Integer getRequestSerialNumber() {
        return requestSerialNumber;
    }

    public void setRequestSerialNumber(Integer requestSerialNumber) {
        this.requestSerialNumber = requestSerialNumber;
    }

    public void addRequest(DTOClientRequest dtoClientRequest) {
        ClientRequest clientRequest = new ClientRequest(requestSerialNumber, getUserByName(dtoClientRequest.getClientName()), dtoClientRequest.getSimulationName(), dtoClientRequest.getNumOfSimulationsToRun(), dtoClientRequest.getTermination());
        allRequests.put(requestSerialNumber, clientRequest);
        addRequestToCurrentClient(requestSerialNumber, clientRequest);
        requestSerialNumber++;
    }

    private void addRequestToCurrentClient(Integer requestSerialNumber, ClientRequest clientRequest) {
        clientRequest.getUser().addRequest(clientRequest, requestSerialNumber);
    }

    public Map<String, DTOClientChosenSimulation> getDtoAllRequests() {
        Map<String, DTOClientChosenSimulation> allRequestsDto= new HashMap<>();

        for (Map.Entry<Integer, ClientRequest> clientChosenSimulationEntry :allRequests.entrySet()) {
            DTOClientChosenSimulation dtoClientChosenSimulation = new DTOClientChosenSimulation(clientChosenSimulationEntry.getValue());
            allRequestsDto.put(String.valueOf(clientChosenSimulationEntry.getKey()),dtoClientChosenSimulation);
        }
        return allRequestsDto;
    }
}