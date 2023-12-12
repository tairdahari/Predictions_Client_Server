package prediction.function.impl;

import prediction.execution.context.IContext;
import prediction.execution.instance.property.IPropertyInstance;
import prediction.function.api.AbstractFunction;

public class EnvironmentFunction extends AbstractFunction {
    public EnvironmentFunction(String argumentName  ) {
        super( "environment", argumentName);
    }

    @Override
    public Object execute(Object argObject, IContext context) {
        if (!(argObject instanceof String)) {
            throw new IllegalArgumentException("Argument must be a string representing the environment variable name");
        }

        String envVariableName = (String) argObject;
        IPropertyInstance propertyInstance = context.getActiveEnvironment().getProperty(envVariableName);

        return propertyInstance.getValue();
    }
}
