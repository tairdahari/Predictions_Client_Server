package prediction.function.impl;

import prediction.execution.context.IContext;
import prediction.function.api.AbstractFunction;

public class EvaluateFunction extends AbstractFunction {

    public EvaluateFunction(String argumentName) {
        super("evaluate", argumentName);
    }

    @Override
    public Object execute(Object arg, IContext context) {
        if (arg == null || !(arg instanceof String)) {
            throw new IllegalArgumentException("Argument must be in the format <entity>.<property name>");
        }

        String argString = (String) arg;
        String[] parts = argString.split("\\.");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Argument must be in the format <entity>.<property name>");
        }

        String entityName = parts[0];
        String propertyName = parts[1];

        Object entityValue = context.getEntityInstanceManager().getEntityInstanceByName(entityName).getPropertyByName(propertyName).getValue();

        if (entityValue == null) {
            throw new IllegalArgumentException("Entity or property not found");
        }

        return entityValue;
    }
}
