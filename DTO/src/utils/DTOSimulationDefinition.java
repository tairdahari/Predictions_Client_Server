package utils;

import prediction.Termination.ITermination;
import prediction.World;
import prediction.defenition.entity.EntityDefinitionImpl;
import prediction.defenition.entity.IEntityDefinition;
import prediction.defenition.property.api.IPropertyDefinition;
import prediction.rule.IRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DTOSimulationDefinition {
    private final List<DTOEntityDefinition> dtoEntityDefinition;
    private final List<DTORuleDefinition> dtoRulesDefinition;
    //private final List<DTOTerminationDefinition> dtoTerminationDefinition;
    private final List<DTOPropertyDefinition> dtoEnvironmentVariables;
    private final DTOGridDefinition dtoGridDefinition;

    public DTOSimulationDefinition(World world) {
        this.dtoEntityDefinition = new ArrayList<>();
        this.dtoRulesDefinition = new ArrayList<>();
        //this.dtoTerminationDefinition = new ArrayList<>();
        this.dtoEnvironmentVariables = new ArrayList<>();
        this.dtoGridDefinition = new DTOGridDefinition(world.getWorldGrid());

        //entities
        for (Map.Entry<String , IEntityDefinition> entityDefinition : world.getEntityManager().getEntities().entrySet()) {
            dtoEntityDefinition.add(new DTOEntityDefinition(entityDefinition.getValue()));
        }
        //rules
        for (Map.Entry<String , IRule> ruleDefinition : world.getRuleManager().getRules().entrySet()) {
            dtoRulesDefinition.add(new DTORuleDefinition(ruleDefinition.getValue()));
        }
        //termination
//        for (Object ob : world.getTerminationManager().getSimulationTerminationConditions()) {
//            if (ob instanceof ITermination) {
//                ITermination termination = (ITermination) ob;
//                dtoTerminationDefinition.add(new DTOTerminationDefinition(termination));
//            }
//        }
        //environment
        for (Map.Entry<String, IPropertyDefinition> envPropertyDefinitionEntry : world.getEnvVariableManager().getEnvVariables().entrySet()) {
            dtoEnvironmentVariables.add(new DTOPropertyDefinition(envPropertyDefinitionEntry.getValue()));
        }
    }

    public List<DTOEntityDefinition> getDtoEntityDefinition() {
        return dtoEntityDefinition;
    }
    public DTOEntityDefinition getDtoEntityDefinitionByName(String entityName) {
        for( DTOEntityDefinition entityDefinition : this.getDtoEntityDefinition()) {
            if(entityDefinition.getEntityName().equals(entityName)) {
                return entityDefinition;
            }
        }
        throw new IllegalArgumentException("There is no Entity with this name.");
    }

    public List<DTORuleDefinition> getDtoRulesDefinition() {
        return dtoRulesDefinition;
    }

//    public List<DTOTerminationDefinition> getDtoTerminationDefinition() {
//        return dtoTerminationDefinition;
//    }

    public List<DTOPropertyDefinition> getDtoEnvironmentVariables() {
        return dtoEnvironmentVariables;
    }

    public DTOGridDefinition getDtoGridDefinition() {
        return dtoGridDefinition;
    }

    public Object getDtoEnvironmentByName(String value) {
        for( DTOPropertyDefinition propertyDefinition : this.getDtoEnvironmentVariables()) {
            if(propertyDefinition.getName().equals(value)) {
                return propertyDefinition;
            }
        }
        throw new IllegalArgumentException("There is no Environment with this name.");
    }

    public Object getDtoRuleDefinitionByName(String value) {
        for( DTORuleDefinition ruleDefinition : this.getDtoRulesDefinition()) {
            if(ruleDefinition.getName().equals(value)) {
                return ruleDefinition;
            }
        }
        throw new IllegalArgumentException("There is no rule with this name.");
    }

//    public Object getDtoTerminationDefinitionByType(String value) {
//        for( DTOTerminationDefinition terminationDefinition : this.getDtoTerminationDefinition()) {
//            if(terminationDefinition.getTerminationName().equals(value)) {
//                return terminationDefinition;
//            }
//        }
//        throw new IllegalArgumentException("There is no termination with this name.");
//    }
}