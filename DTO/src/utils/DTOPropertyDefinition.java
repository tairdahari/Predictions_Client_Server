package utils;

import prediction.defenition.property.api.IPropertyDefinition;
import prediction.defenition.property.api.ePropertyType;
import prediction.defenition.property.impl.FloatPropertyDefinition;
import prediction.defenition.property.impl.IntegerPropertyDefinition;

public class DTOPropertyDefinition {
    private final String name;
    private final String propertyType;
    private String from;
    private String to;
    private final Boolean isInitRandom;
    private final String value;

    public DTOPropertyDefinition(IPropertyDefinition value) {
        this.name = value.getName();
        this.propertyType = generateType(value.getType());
        this.isInitRandom = value.getRandomInitial();
        this.value = value.generateValue().toString();

        if( value instanceof IntegerPropertyDefinition) {
            this.from = ((IntegerPropertyDefinition) value).getRange().getFrom().toString();
            this.to = ((IntegerPropertyDefinition) value).getRange().getTo().toString();
        } else if (value instanceof FloatPropertyDefinition) {
            this.from = ((FloatPropertyDefinition) value).getRange().getFrom().toString();
            this.to = ((FloatPropertyDefinition) value).getRange().getTo().toString();
        }
    }

    private String generateType(ePropertyType type) {
        String currType;

        if(type.equals(ePropertyType.DECIMAL))
            currType = "decimal";
        else if (type.equals(ePropertyType.FLOAT) )
            currType = "float";
        else if (type.equals(ePropertyType.STRING))
            currType = "string";
        else
            currType = "boolean";
        return currType;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return propertyType;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
    public Boolean getInitRandom() {
        return isInitRandom;
    }

    public String getValue() {
        return value;
    }
}