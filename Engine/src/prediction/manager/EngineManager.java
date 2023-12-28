package prediction.manager;

import prediction.clientRequest.RequestsManager;
import prediction.execution.simulationExecutionDetails.ISimulationDetails;
import prediction.users.UserManager;
import prediction.worldManager.IWorldManager;
import prediction.worldManager.WorldManager;
import utils.DTOAllFiles;
import utils.DTOListSimulationDetails;
import utils.DTOQueue;
import utils.DTOSimulationDefinition;

import java.util.*;
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
        this.threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);//TODO change the sie of threadpool
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

    @Override
    public DTOListSimulationDetails getAllEndedSimulations() {
        List<ISimulationDetails> allEndedSimulations = new ArrayList<>();

        for (Map.Entry<String, IWorldManager> clientChosenSimulationEntry :allFilesInTheSystem.entrySet()) {
            String id = clientChosenSimulationEntry.getKey();

            List<ISimulationDetails> allSimulationsById = getWorldFromFilesById(id).getAllSimulationsExecution();

            for (ISimulationDetails oneSimulation : allSimulationsById) {
                if (oneSimulation.getEndSimulationDateTime() != null) {
                    allEndedSimulations.add(oneSimulation);
                }
            }
        }
        return new DTOListSimulationDetails(allEndedSimulations);
    }

    @Override
    public Integer getThreadPoolSizeByUser() {
        return threadPoolSizeByUser;
    }
    @Override
    public void setThreadPoolSizeByUser(Integer threadPoolSizeByUser) {
        this.threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadPoolSizeByUser);
    }
}
