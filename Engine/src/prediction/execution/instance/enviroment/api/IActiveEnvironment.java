package prediction.execution.instance.enviroment.api;

import prediction.execution.instance.property.IPropertyInstance;

import java.io.Serializable;
import java.util.Map;

public interface IActiveEnvironment extends Serializable {
    IPropertyInstance getProperty(String name);
    void addPropertyInstance(IPropertyInstance propertyInstance);
    Map<String, IPropertyInstance> getEnvVariablesList();
}