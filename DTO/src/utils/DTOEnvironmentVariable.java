package utils;

import prediction.defenition.property.api.IPropertyDefinition;
import prediction.defenition.property.impl.FloatPropertyDefinition;
import prediction.defenition.property.impl.IntegerPropertyDefinition;

public class DTOEnvironmentVariable {
    private final String name;
    private final String type;
    private String from;
    private String to;
    private String value;

    public DTOEnvironmentVariable(IPropertyDefinition value) {
        this.name = value.getName();
        this.type = value.getType().toString();

        if( value instanceof IntegerPropertyDefinition) {
            this.from = ((IntegerPropertyDefinition) value).getRange().getFrom().toString();
        } else if (value instanceof FloatPropertyDefinition) {
            this.to = ((FloatPropertyDefinition<?>) value).getRange().getTo().toString();
        }
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }
}
