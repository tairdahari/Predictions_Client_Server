package prediction.execution.instance.property;

import prediction.defenition.property.api.IPropertyDefinition;
import prediction.execution.context.IContext;
import prediction.expression.ExpressionHandlerImpl;

import java.util.HashMap;
import java.util.Map;

public class PropertyInstanceImpl implements IPropertyInstance{
    private final IPropertyDefinition propertyDefinition;
    private Object value;
    private Map<Object, Float> valueToLastChangeTick = new HashMap<>();


    public PropertyInstanceImpl(IPropertyDefinition propertyDefinition, Object value) {
        this.propertyDefinition = propertyDefinition;
        this.value = value;
    }

    @Override
    public IPropertyDefinition getPropertyDefinition() {
        return propertyDefinition;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void updateValue(Object val, IContext context) {
        if(val instanceof PropertyInstanceImpl)
            this.value =((PropertyInstanceImpl) val).getValue();
        else
            this.value = val;
    }

    @Override
    public float getTicksSinceLastChange(Object val, int currentTick) {
        Float lastChangeTick = valueToLastChangeTick.get(val);

        if (lastChangeTick == null) {
            // Value hasn't changed yet
            return currentTick; // Assume it has changed at the beginning of the simulation
        } else {
            return currentTick - lastChangeTick;
        }
    }
}