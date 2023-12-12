package prediction.defenition.property.api;

import java.io.Serializable;

public interface IPropertyDefinition extends Serializable {
    String getName();
    ePropertyType getType();
    Object generateValue();
    Boolean getRandomInitial();
}
