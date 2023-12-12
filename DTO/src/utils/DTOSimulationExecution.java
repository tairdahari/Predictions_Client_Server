package utils;

import prediction.execution.context.IContext;
import prediction.execution.instance.entity.IEntityInstance;
import prediction.execution.instance.property.IPropertyInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DTOSimulationExecution {
    private final List<DTOPropertyExecution> dtoEnvironmentVariables;
    private final List<DTOEntityExecution> dtoEntityListExecution;
    public DTOSimulationExecution(IContext context) {
        this.dtoEnvironmentVariables = new ArrayList<>();
        this.dtoEntityListExecution = new ArrayList<>();
        for (Map.Entry<String, IPropertyInstance> envPropertyInstanceEntry : context.getActiveEnvironment().getEnvVariablesList().entrySet()) {
            dtoEnvironmentVariables.add(new DTOPropertyExecution(envPropertyInstanceEntry.getKey(), envPropertyInstanceEntry.getValue().getValue().toString()));
        }

        for(IEntityInstance entityInstance: context.getEntityInstanceManager().getInstances()) {
            Integer entityCount = context.getEntityInstanceManager().getEntityQuantityByName(entityInstance.getEntityDefinition().getEntityName());
            dtoEntityListExecution.add(new DTOEntityExecution(entityInstance.getEntityDefinition().getEntityName(), entityCount.toString(), null));
        }
    }

    public List<DTOPropertyExecution> getDtoEnvironmentVariables() {
        return dtoEnvironmentVariables;
    }

    public List<DTOEntityExecution> getDtoEntityListExecution() {
        return dtoEntityListExecution;
    }
}
