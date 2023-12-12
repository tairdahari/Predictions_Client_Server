package prediction.admin;

import java.util.Map;
import prediction.clientRequest.ClientRequest;

public class AdminRequestsManager {
    private Map<Integer, ClientRequest> allRequests;

    public AdminRequestsManager(Map<Integer, ClientRequest> allRequests) {
        this.allRequests = allRequests;
    }

    public Map<Integer, ClientRequest> getAllRequests() {
        return allRequests;
    }

    public void setAllRequests(Map<Integer, ClientRequest> allRequests) {
        this.allRequests = allRequests;
    }
}
