package prediction.worldManager;

import prediction.Termination.clientTerminationManager.TerminationByClientManager;
import prediction.World;
import prediction.execution.context.IContext;
import utils.*;
import xmlJavaFXReader.schema.generated.PRDWorld;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public interface IWorldManager extends Serializable {
    //void readingSystemInformationFromFileJavaFX(File file);

    void readingSystemInformationFromFileWeb(InputStream InputStreamFile, boolean isFirstUpload);
    //UserManager getUserManager();

    void transferringDataFromTheXMLClassesToOurClasses(PRDWorld world);
    DTOSimulationDefinition getSimulationDefinition();
    DTOListSimulationDetails getAllSimulationsExecution();

    void saveToFile(String var1) throws IOException;
    void loadFromFile(String var1) throws IOException, ClassNotFoundException;
     void initialization(List<Object> userEnvVarChoices, TerminationByClientManager terminationManager);

    DTOSimulationDetails getDtoSimulationDetailsByID(Integer id);

    DTOPropertyHistogram getHistogram(String selectedPropertyName);
    World getWorld();
    Object getCurrentDtoDefinition(String type, String value);
    void initPopulation(List<Integer> populationList);

    void start(List<Integer> populationList, List<Object> userEnvVarChoices, ThreadPoolExecutor threadPoolExecutor);

    void stop(String currSimulationId);

    void pause(String currSimulationId);

    void resume(String currSimulationId);

    boolean isPaused(String currSimulationId);

    void initialSystem();

    //void removeUserFromTheUserManager(String userName);

    IContext getContext();
     DTOReRun reRun(Integer id);

    float getPropertyConsistency(String propertyName);

    String getErrorMessage(DTOSimulationDetails selectedSimulation);

    int getCountEndedSimulations();

    void setCountEndedSimulations(int countEndedSimulations);

    String getFileName();

    void setRequestManager(DTOClientRequest dtoClientRequest);
}