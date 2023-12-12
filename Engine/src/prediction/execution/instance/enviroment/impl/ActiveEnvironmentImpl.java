package prediction.execution.instance.enviroment.impl;

import prediction.execution.instance.enviroment.api.IActiveEnvironment;
import prediction.execution.instance.property.IPropertyInstance;

import java.util.LinkedHashMap;
import java.util.Map;

public class ActiveEnvironmentImpl implements IActiveEnvironment {
    private final Map<String, IPropertyInstance> envVariables;
    public ActiveEnvironmentImpl() {
        envVariables = new LinkedHashMap<>();
    }

    @Override
    public IPropertyInstance getProperty(String name) {
        if (!envVariables.containsKey(name)) {
            throw new IllegalArgumentException("Can't find env variable with name " + name);
        }
        return envVariables.get(name);
    }

    @Override
    public void addPropertyInstance(IPropertyInstance propertyInstance) {
        envVariables.put(propertyInstance.getPropertyDefinition().getName(), propertyInstance);
    }

    @Override
    public Map<String, IPropertyInstance> getEnvVariablesList() {
        return this.envVariables;
    }
}