package prediction.defenition.property.impl;

import prediction.defenition.property.api.AbstractPropertyDefinition;
import prediction.defenition.property.api.ePropertyType;
import prediction.defenition.valueGenerator.api.ValueGenerator;

public class StringPropertyDefinition extends AbstractPropertyDefinition<String> {
    public StringPropertyDefinition(String propertyName, ValueGenerator<String> valueGenerator, Boolean isRandom) {
        super(propertyName, ePropertyType.STRING, valueGenerator,isRandom );
    }

    @Override
    public ePropertyType getType() {
        return ePropertyType.STRING;
    }
}
