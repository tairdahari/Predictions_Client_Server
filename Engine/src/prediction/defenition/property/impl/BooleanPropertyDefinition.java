package prediction.defenition.property.impl;

import prediction.defenition.property.api.AbstractPropertyDefinition;
import prediction.defenition.property.api.ePropertyType;
import prediction.defenition.valueGenerator.api.ValueGenerator;

public class BooleanPropertyDefinition extends AbstractPropertyDefinition<Boolean> {

    public BooleanPropertyDefinition(String name, ValueGenerator<Boolean> valueGenerator, Boolean isRandom) {
        super(name, ePropertyType.BOOLEAN, valueGenerator ,isRandom);
    }
    @Override
    public ePropertyType getType() {
        return ePropertyType.BOOLEAN;
    }
}