package prediction.function.impl;

import prediction.execution.context.IContext;
import prediction.execution.instance.entity.IEntityInstance;
import prediction.execution.instance.property.IPropertyInstance;
import prediction.function.api.AbstractFunction;

public class TicksFunction extends AbstractFunction {
    public TicksFunction(String functionName, String argumentName) {
        super(functionName, argumentName);
    }

    @Override
    public Object execute(Object argObject, IContext context) {
        String[] parts = argObject.toString().split("\\.");
        String entityName = parts[0];
        String propertyName = parts[1];


        IPropertyInstance propertyInstance =context.getEntityInstanceManager().getEntityInstanceByName(entityName).getPropertyByName(propertyName);
        if (propertyInstance == null) {
            throw new IllegalArgumentException("Property not found: " + propertyName);
        }
        int currentTick = context.getCurrTick();
        Object propertyValue = propertyInstance.getValue();
        float ticksSinceLastChange = propertyInstance.getTicksSinceLastChange(propertyValue, currentTick);

        return ticksSinceLastChange;
    }
}
