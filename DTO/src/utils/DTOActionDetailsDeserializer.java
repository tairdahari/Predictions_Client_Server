package utils;

import com.google.gson.*;
import utils.DTOActions.*;

import java.lang.reflect.Type;

public class DTOActionDetailsDeserializer implements JsonDeserializer<DTOActionDetails> {
    @Override
    public DTOActionDetails deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
        JsonObject jsonObject = json.getAsJsonObject();
        String actionName = jsonObject.get("actionName").getAsString();


            switch (actionName.toLowerCase()) {
                case "calculation":
                    return calcConvertAccordingType(jsonObject, json, context);
                case "condition":
                    return conditionConvertAccordingType(jsonObject, json, context);
                case "decrease":
                    return context.deserialize(json, DTODecreaseAction.class);
                case "divide":
                    return context.deserialize(json, DTODivideAction.class);
                case "increase":
                    return context.deserialize(json, DTOIncreaseAction.class);
                case "kill":
                    return context.deserialize(json, DTOKillAction.class);
                case "multipleCondition":
                    return context.deserialize(json, DTOMultipleConditionAction.class);
                case "multiply":
                    return context.deserialize(json, DTOMultiplyAction.class);
                case "proximity":
                    return context.deserialize(json, DTOProximityAction.class);
                case "replace":
                    return context.deserialize(json, DTOReplaceAction.class);
                case "set":
                    return context.deserialize(json, DTOSetAction.class);
                case "singleCondition":
                    return context.deserialize(json, DTOSingleConditionAction.class);
                default:
            }
        } catch (JsonParseException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private DTOActionDetails conditionConvertAccordingType(JsonObject jsonObject, JsonElement json, JsonDeserializationContext context) {
        if(jsonObject.get("logic") != null) {
            return context.deserialize(json, DTOMultipleConditionAction.class);
        }
        return context.deserialize(json, DTOSingleConditionAction.class);
    }
    private DTOActionDetails calcConvertAccordingType(JsonObject jsonObject, JsonElement json, JsonDeserializationContext context) {
        if(jsonObject.get("calcType").getAsString().equals("MULTIPLY")) {
            return context.deserialize(json, DTOMultiplyAction.class);
        }
        return context.deserialize(json, DTODivideAction.class);
    }
}

