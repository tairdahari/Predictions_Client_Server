package utils;

import java.util.Map;

public class DTOAdminRequestsManager {
    private Map<Integer, DTOClientChosenSimulation> allRequests;

    public DTOAdminRequestsManager(Map<Integer, DTOClientChosenSimulation> allRequests) {
        this.allRequests = allRequests;
    }

    public Map<Integer, DTOClientChosenSimulation> getAllRequests() {
        return allRequests;
    }
}
