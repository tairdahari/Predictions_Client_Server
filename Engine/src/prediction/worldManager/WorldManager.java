package prediction.worldManager;

import prediction.World;
import prediction.action.api.IAction;
import prediction.defenition.entity.IEntityDefinition;
import prediction.defenition.property.api.IPropertyDefinition;
import prediction.execution.context.ContextImpl;
import prediction.execution.context.GridManager;
import prediction.execution.context.IContext;
import prediction.execution.context.SimulationGrid;
import prediction.execution.instance.entity.IEntityInstance;
import prediction.execution.instance.entity.manager.EntityInstanceManagerImpl;
import prediction.execution.instance.entity.manager.IEntityInstanceManager;
import prediction.execution.instance.enviroment.api.IActiveEnvironment;
import prediction.execution.instance.property.PropertyInstanceImpl;
import prediction.execution.runner.SimulationExecutor;
import prediction.execution.runner.eSimulationState;
import prediction.execution.simulationExecutionDetails.ISimulationDetails;
import prediction.execution.simulations.SimulationExecutionManager;
import prediction.execution.simulations.SimulationsExecutionManagerImpl;
import utils.*;
import xmlJavaFXReader.schema.SchemaBasedJAXBMain;
import xmlJavaFXReader.schema.generated.PRDWorld;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

public class WorldManager implements IWorldManager {
    private World world;
    private IContext context;
    private SimulationExecutionManager simulationExecutionManager;
    private int idForSimulation = 1;
    private Map<Integer, SimulationExecutor> simulationExecutors;
    private Map<String, Thread> runningSimulationThreads = new HashMap<>();
    private Integer countEndedSimulations = 0;
    private String fileName;


    public WorldManager(String fileName) {
        simulationExecutionManager = new SimulationsExecutionManagerImpl();
        this.simulationExecutors =  new LinkedHashMap<>();
        this.fileName = fileName;
    }
    @Override
    public void initialSystem() {
        simulationExecutionManager = new SimulationsExecutionManagerImpl();
        this.simulationExecutors =  new LinkedHashMap<>();
        countEndedSimulations = 0;
    }


    @Override
    public IContext getContext()
    {
        return this.context;
    }

//    @Override
//    public void readingSystemInformationFromFileJavaFX(File file) {
//        SchemaBasedJAXBMain schema = new SchemaBasedJAXBMain();
//        PRDWorld world = schema.createWorldFromXMLFile(file);
//        transferringDataFromTheXMLClassesToOurClasses(world);
//
//        this.threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3); //TODO change the sie of threadpool
//    }

    @Override
    public void readingSystemInformationFromFileWeb(InputStream InputStreamFile, boolean isFirstUpload) {
        SchemaBasedJAXBMain schema = new SchemaBasedJAXBMain();
        PRDWorld world = schema.createWorldFromXMLFileWeb(InputStreamFile);
        transferringDataFromTheXMLClassesToOurClasses(world);

    }


    @Override
    public void transferringDataFromTheXMLClassesToOurClasses(PRDWorld world) {
        this.world = new World(world);
    }

    @Override
    public DTOSimulationDefinition getSimulationDefinition() {
        DTOSimulationDefinition dtoSimulationDefinition = new DTOSimulationDefinition(world);
        return dtoSimulationDefinition;
    }

    @Override
    public DTOListSimulationDetails getAllSimulationsExecution() {
        DTOListSimulationDetails dtoListSimulationDetail = new DTOListSimulationDetails(simulationExecutionManager.getAllSimulations());
        return dtoListSimulationDetail;
    }

    @Override
    public void loadFromFile(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(Paths.get(path)))) {
            this.world = (World) in.readObject();
            this.context = (IContext) in.readObject();
            //this.allSimulations = (List<ISimulationExecutionDetails>) in.readObject();
            this.simulationExecutionManager = (SimulationExecutionManager) in.readObject();
        }
    }

    @Override
    public DTOSimulationDetails getDtoSimulationDetailsByID(Integer id) {
        for( ISimulationDetails simulationDetails : simulationExecutionManager.getAllSimulations()) {
            if(simulationDetails.getId() == id)
                return new DTOSimulationDetails(simulationDetails, simulationDetails.getContext().getInProgress());

        }
        return null;
    }
    private boolean isSecondaryEntityExist(IAction action) {
        if(action.getSecondaryEntityDefinition() != null) {
            context.setSecondaryEntityName(action.getSecondaryEntityDefinition().getSecondaryEntityDefinition().getEntityName());
            return true;
        }
        context.setSecondaryEntityName(null);
        return false;
    }

    private boolean isValidEntityForExecute(IEntityInstance entityInstance, IEntityDefinition contextEntity) {
        return entityInstance.getEntityDefinition().getEntityName().equals(contextEntity.getEntityName());
    }


    @Override
    public void initialization(List<Object> userEnvVarChoices) {
        IEntityInstanceManager entityInstanceManager = new EntityInstanceManagerImpl();
        SimulationGrid simulationGrid = new SimulationGrid(world.getWorldGrid().getRows(), world.getWorldGrid().getCols());
        GridManager gridManager = new GridManager(simulationGrid);

        for (Map.Entry<String, IEntityDefinition> entityDefinitionEntry : this.world.getEntityManager().getEntities().entrySet()) {
            for (int j = 0; j < entityDefinitionEntry.getValue().getNumOfPopulation(); j++) {
                entityInstanceManager.create(entityDefinitionEntry.getValue(),gridManager);
            }
        }

        IEntityInstance entityInstance = entityInstanceManager.getInstances().get(0);
        IActiveEnvironment activeEnvironment = this.world.getEnvVariableManager().createActiveEnvironment();

        // create instances of the environment
        int listIter = 0;
        for(Map.Entry<String, IPropertyDefinition> envPropertyDefinitionEntry: this.world.getEnvVariableManager().getEnvVariables().entrySet()) {
            activeEnvironment.addPropertyInstance(new PropertyInstanceImpl(envPropertyDefinitionEntry.getValue(), userEnvVarChoices.get(listIter)));
            listIter++;
        }
        this.context = new ContextImpl(idForSimulation, world, entityInstance, entityInstanceManager, activeEnvironment, gridManager);
        idForSimulation++;
    }

    @Override
    public void saveToFile(String path) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(Paths.get(path)))){
            out.writeObject(this.world);
            out.writeObject(this.context);
            out.writeObject(this.simulationExecutionManager);
        }
    }
    @Override
    public DTOPropertyHistogram getHistogram(String selectedPropertyName) {
        return this.context.getEntityInstanceManager().propertyHistogram(selectedPropertyName);
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public Object getCurrentDtoDefinition(String type, String value) {
        Object returnDto = null;
        switch (type) {
            case "Entities":
                returnDto = getSimulationDefinition().getDtoEntityDefinitionByName(value);
                break;
            case "Environments":
                returnDto = getSimulationDefinition().getDtoEnvironmentByName(value);
                break;
            case "Rules":
                returnDto = getSimulationDefinition().getDtoRuleDefinitionByName(value);
                break;
            case "Grid":
                returnDto = getSimulationDefinition().getDtoGridDefinition();
                break;
//            case "Termination":
//                returnDto = getSimulationDefinition().getDtoTerminationDefinitionByType(value);
            default:
                break;
        }
        return returnDto;
    }

    @Override
    public void initPopulation(List<Integer> populationList) {
        int listItr = 0;
        for (Map.Entry<String, IEntityDefinition> entityDefinitionEntry: world.getEntityManager().getEntities().entrySet()) {
            entityDefinitionEntry.getValue().setNumOfPopulation(populationList.get(listItr));
            listItr++;
        }
    }

    @Override
    public void start(List<Integer> populationList, List<Object> userEnvVarChoices, ThreadPoolExecutor threadPoolExecutor) {
        simulationExecutors.put(context.getSimulationId(), new SimulationExecutor(context, simulationExecutionManager, this));
        threadPoolExecutor.execute(simulationExecutors.get(context.getSimulationId()));
        simulationExecutors.get(context.getSimulationId()).setPopulationList(populationList);
        simulationExecutors.get(context.getSimulationId()).setUserEnvVarChoices(userEnvVarChoices);
    }

    @Override
    public DTOReRun reRun(Integer id)
    {
        SimulationExecutor simulationExecutor = simulationExecutors.get(id);
        DTOReRun dtoReRun = new DTOReRun(simulationExecutor.getPopulationList(), simulationExecutor.getUserEnvVarChoices());
        return dtoReRun;
    }

    @Override
    public float getPropertyConsistency(String propertyName) {
        int currTick = this.context.getCurrTick();
        return this.context.getEntityInstanceManager().propertyConsistency(propertyName, currTick);

    }

    @Override
    public String getErrorMessage(DTOSimulationDetails selectedSimulation) {
        return simulationExecutors.get(Integer.parseInt(selectedSimulation.getId())).getSimulationError();
    }

    @Override
    public void stop(String currSimulationId) {
        simulationExecutors.get(Integer.parseInt(currSimulationId)).stop();
        synchronized (simulationExecutors.get(Integer.parseInt(currSimulationId)))
        {
            simulationExecutors.get(Integer.parseInt(currSimulationId)).notifyAll();
        }
    }
    @Override
    public void pause(String currSimulationId) {
        //simulationExecutors.get(Integer.parseInt(currSimulationId)).pause();
        simulationExecutors.get(Integer.parseInt(currSimulationId)).setSimulationState(eSimulationState.PAUSED);
    }
    @Override
    public void resume(String currSimulationId) {
        simulationExecutors.get(Integer.parseInt(currSimulationId)).setSimulationState(eSimulationState.RUNNING);
        synchronized (simulationExecutors.get(Integer.parseInt(currSimulationId)))
        {
            simulationExecutors.get(Integer.parseInt(currSimulationId)).notifyAll();
        }
    }

    @Override
    public boolean isPaused(String currSimulationId) {
        return simulationExecutors.get(Integer.parseInt(currSimulationId)).isPaused();
    }
    @Override
    public int getCountEndedSimulations() {
        return countEndedSimulations;
    }
    @Override
    public void setCountEndedSimulations(int countEndedSimulations) {
        this.countEndedSimulations = countEndedSimulations;
    }

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public void setRequestManager(DTOClientRequest dtoClientRequest) {
//        ClientRequest clientRequest = new ClientRequest(dtoClientRequest.getClientName(), user, dtoClientRequest.getSimulationName(), dtoClientRequest.getNumOfSimulationsToRun(), dtoClientRequest.getTermination());
//        requestsManager.getAllRequests().put(requestsManager.getRequestSerialNumber(), clientRequest);

    }
}