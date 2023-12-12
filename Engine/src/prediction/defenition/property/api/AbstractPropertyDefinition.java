package prediction.defenition.property.api;

import prediction.defenition.valueGenerator.api.ValueGenerator;

public abstract class AbstractPropertyDefinition<T> implements IPropertyDefinition {
    private final String name;
    private final ePropertyType propertyType;
    private final ValueGenerator<T> valueGenerator;
    private final Boolean isRandomInitial;
    public AbstractPropertyDefinition(String name, ePropertyType ePropertyType, ValueGenerator<T> valueGenerator , Boolean isRandom) {
        this.name = name;
        this.propertyType = ePropertyType;
        this.valueGenerator= valueGenerator;
        this.isRandomInitial = isRandom;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public ePropertyType getType() {
        return propertyType;
    }
    @Override
    public T generateValue() {
        return valueGenerator.generateValue();
    }
    @Override
    public Boolean getRandomInitial() {
        return isRandomInitial;
    }
}