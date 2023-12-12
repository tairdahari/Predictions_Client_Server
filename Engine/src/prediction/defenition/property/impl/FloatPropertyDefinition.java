package prediction.defenition.property.impl;

import prediction.defenition.property.api.AbstractPropertyDefinition;
import prediction.defenition.property.Range;
import prediction.defenition.property.api.ePropertyType;
import prediction.defenition.valueGenerator.api.ValueGenerator;

public class FloatPropertyDefinition<Float> extends AbstractPropertyDefinition {
    private final Range range;
    public FloatPropertyDefinition(String name, ValueGenerator<Integer> valueGenerator, Boolean isRandom , Range range) {
        super(name, ePropertyType.FLOAT, valueGenerator,isRandom);
        this.range =range;
    }
    @Override
    public ePropertyType getType() {
        return ePropertyType.FLOAT;
    }
    public Range getRange() {
        return range;
    }
}