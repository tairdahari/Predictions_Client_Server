package prediction.defenition.property.impl;

import prediction.defenition.property.Range;
import prediction.defenition.property.api.AbstractPropertyDefinition;
import prediction.defenition.property.api.ePropertyType;
import prediction.defenition.valueGenerator.api.ValueGenerator;

public class IntegerPropertyDefinition extends AbstractPropertyDefinition<Integer> {
    private final Range range;
 public IntegerPropertyDefinition(String propertyName, ValueGenerator<Integer> valueGenerator, Boolean isRandom, Range range){
     super(propertyName, ePropertyType.DECIMAL , valueGenerator,isRandom);
     this.range =range;
 }
    @Override
    public ePropertyType getType() {
        return ePropertyType.DECIMAL;
    }

    public Range getRange() {
     return range;
    }
}