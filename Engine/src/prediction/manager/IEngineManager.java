package prediction.manager;

import prediction.clientRequest.RequestsManager;
import prediction.users.UserManager;
import prediction.worldManager.IWorldManager;
import utils.DTOAllFiles;
import utils.DTOListSimulationDetails;
import utils.DTOQueue;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

public interface IEngineManager {
    DTOAllFiles getDTOFilesList();
    Integer getThreadPoolSizeByUser();
    void setThreadPoolSizeByUser(Integer threadPoolSizeByUser);

    ThreadPoolExecutor getThreadPoolExecutor();

    DTOQueue getDtoQueue();

    UserManager getUserManager();

    void removeUserFromTheUserManager(String userName);

    Map<String, IWorldManager> getAllFilesInTheSystem();

    IWorldManager getWorldFromFilesById(String id);

    IWorldManager createWorld(String fileName);

    RequestsManager getRequestsManager();

    void setRequestsManager(RequestsManager requestsManager);

    DTOListSimulationDetails getAllEndedSimulations();
}
