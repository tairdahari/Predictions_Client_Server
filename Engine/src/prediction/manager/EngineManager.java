package prediction.manager;

import prediction.clientRequest.RequestsManager;
import prediction.users.UserManager;
import prediction.worldManager.IWorldManager;
import prediction.worldManager.WorldManager;
import utils.DTOAllFiles;
import utils.DTOQueue;
import utils.DTOSimulationDefinition;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class EngineManager implements IEngineManager {
    private Map<String, IWorldManager> allFilesInTheSystem = new LinkedHashMap<>();
    private UserManager userManager;
    private RequestsManager requestsManager;
    private ThreadPoolExecutor threadPoolExecutor;
    private Integer threadPoolSizeByUser;

    public EngineManager() {
        this.userManager = new UserManager();
        this.requestsManager = new RequestsManager(userManager);
        this.threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(7);//TODO change the sie of threadpool
    }

    @Override
    public DTOAllFiles getDTOFilesList() {
        Map<String, DTOSimulationDefinition> filesMap = new HashMap<>();

        for (Map.Entry<String, IWorldManager> oneWorld: allFilesInTheSystem.entrySet()) {
            filesMap.put(oneWorld.getKey(), oneWorld.getValue().getSimulationDefinition());
        }

        return new DTOAllFiles(filesMap);
    }
    @Override
    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }
    @Override
    public DTOQueue getDtoQueue() {
        int countEndedSimulationsInTheSystem = 0;

        for(Map.Entry<String, IWorldManager> file : allFilesInTheSystem.entrySet()) {
            countEndedSimulationsInTheSystem += file.getValue().getCountEndedSimulations();
        }
        DTOQueue dtoQueue = new DTOQueue(threadPoolExecutor.getQueue().size(), threadPoolExecutor.getActiveCount(), countEndedSimulationsInTheSystem );
        return dtoQueue;
    }
    @Override
    public UserManager getUserManager() {
        return userManager;
    }
    @Override
    public void removeUserFromTheUserManager(String userName) {
        this.userManager.removeUser(userName);
    }
    @Override
    public Map<String,IWorldManager> getAllFilesInTheSystem() {
        return allFilesInTheSystem;
    }
    @Override
    public IWorldManager getWorldFromFilesById(String id) {
        return allFilesInTheSystem.get(id);
    }

    @Override
    public IWorldManager createWorld(String fileName) {
        return new WorldManager(fileName);
    }
    @Override
    public RequestsManager getRequestsManager() {
        return requestsManager;
    }
    @Override
    public void setRequestsManager(RequestsManager requestsManager) {
        this.requestsManager = requestsManager;
    }

    public Integer getThreadPoolSizeByUser() {
        return threadPoolSizeByUser;
    }

    public void setThreadPoolSizeByUser(Integer threadPoolSizeByUser) {
        this.threadPoolSizeByUser = threadPoolSizeByUser;
    }
}
