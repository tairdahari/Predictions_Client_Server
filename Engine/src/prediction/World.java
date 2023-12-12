package prediction;

import prediction.Termination.manager.TerminationManagerDefinitionImpl;
import prediction.defenition.grid.GridDefinition;
import prediction.defenition.entity.manager.EntityDefinitionManager;
import prediction.defenition.enviroment.manager.EnvVariableManagerImpl;
import prediction.rule.manager.RuleDefinitionManagerImpl;
import xmlJavaFXReader.schema.generated.PRDWorld;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class World implements Serializable {
    private final EntityDefinitionManager entityManager;
    private final EnvVariableManagerImpl envVariableManager;
    private final RuleDefinitionManagerImpl ruleManager;
    //private final TerminationManagerDefinitionImpl terminationManager;
    private GridDefinition worldGrid;
    private List<String> worldProperties;

    public World (PRDWorld world)
    {
        entityManager =new EntityDefinitionManager(world.getPRDEntities());
        envVariableManager =new EnvVariableManagerImpl(world.getPRDEnvironment());
        ruleManager =new RuleDefinitionManagerImpl(world.getPRDRules(), entityManager);
        //terminationManager =new TerminationManagerDefinitionImpl(world.getPRDTermination());
        this.worldProperties = insertProperties();
        this.worldGrid = new GridDefinition(world.getPRDGrid());
    }

    private List<String> insertProperties() {
        List<String> worldProperties= new ArrayList<>();

        worldProperties.add("Entities");
        worldProperties.add("Environments");
        worldProperties.add("Rules");
        worldProperties.add("Grid");
        worldProperties.add("Termination");

        return worldProperties;
    }

    public EntityDefinitionManager getEntityManager() {
        return entityManager;
    }

    public RuleDefinitionManagerImpl getRuleManager() {
        return ruleManager;
    }

    public EnvVariableManagerImpl getEnvVariableManager() {
        return envVariableManager;
    }

    public TerminationManagerDefinitionImpl getTerminationManager() {
        //return terminationManager;
        return null;
    }

    public List<String> getWorldProperties() {
        return worldProperties;
    }
    public GridDefinition getWorldGrid() {
        return worldGrid;
    }
}