package prediction.execution.instance.property;

import prediction.defenition.property.api.IPropertyDefinition;
import prediction.execution.context.IContext;

import java.io.Serializable;

public interface IPropertyInstance extends Serializable {
    IPropertyDefinition getPropertyDefinition();
    Object getValue();
    void updateValue(Object val, IContext context);
    float getTicksSinceLastChange(Object val, int currentTick);

}