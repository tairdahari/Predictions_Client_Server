package prediction.defenition.enviroment.manager;

import prediction.defenition.property.api.IPropertyDefinition;
import prediction.execution.instance.enviroment.api.IActiveEnvironment;

import java.io.Serializable;
import java.util.Map;

public interface IEnvVariablesManager extends Serializable {
    IActiveEnvironment createActiveEnvironment();
    Map<String, IPropertyDefinition> getEnvVariables();
}