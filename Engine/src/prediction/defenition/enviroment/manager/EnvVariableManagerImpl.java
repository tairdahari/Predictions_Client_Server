package prediction.defenition.enviroment.manager;

import prediction.defenition.property.Range;
import prediction.defenition.property.api.IPropertyDefinition;
import prediction.defenition.property.impl.BooleanPropertyDefinition;
import prediction.defenition.property.impl.FloatPropertyDefinition;
import prediction.defenition.property.impl.IntegerPropertyDefinition;
import prediction.defenition.property.impl.StringPropertyDefinition;
import prediction.defenition.valueGenerator.api.ValueGeneratorFactory;
import prediction.execution.instance.enviroment.api.IActiveEnvironment;
import prediction.execution.instance.enviroment.impl.ActiveEnvironmentImpl;
import xmlJavaFXReader.schema.generated.PRDEnvProperty;
import xmlJavaFXReader.schema.generated.PRDEnvironment;

import java.util.LinkedHashMap;
import java.util.Map;

public class EnvVariableManagerImpl implements IEnvVariablesManager {
    private final Map<String, IPropertyDefinition> propNameToPropDefinition;
    public EnvVariableManagerImpl(PRDEnvironment prdEvironment) {
        this.propNameToPropDefinition = new LinkedHashMap<>();

        for (PRDEnvProperty oneProperty : prdEvironment.getPRDEnvProperty()) {
            ckPropertyExist(oneProperty);
            IPropertyDefinition property = createEnvPropertyDefinition(oneProperty);
            propNameToPropDefinition.put(oneProperty.getPRDName(), property);
        }
    }
    private IPropertyDefinition createEnvPropertyDefinition(PRDEnvProperty oneEnvProperty) {

        switch (oneEnvProperty.getType()) {
            case "boolean":
                return new BooleanPropertyDefinition(oneEnvProperty.getPRDName(), ValueGeneratorFactory.createRandomBoolean(),true);
            case "decimal":
                return new IntegerPropertyDefinition(oneEnvProperty.getPRDName() , ValueGeneratorFactory.createRandomInteger((int)oneEnvProperty.getPRDRange().getFrom(), (int)oneEnvProperty.getPRDRange().getTo()),
                        true, new Range(oneEnvProperty.getPRDRange().getFrom(), oneEnvProperty.getPRDRange().getTo()));
            case "float":
                return new FloatPropertyDefinition(oneEnvProperty.getPRDName(),  ValueGeneratorFactory.createRandomFloat((float)oneEnvProperty.getPRDRange().getFrom(), (float)oneEnvProperty.getPRDRange().getTo()), true, new Range(oneEnvProperty.getPRDRange().getFrom(), oneEnvProperty.getPRDRange().getTo())); //TODO DAHUF!!
//                case "float":
//                return new FloatPropertyDefinition(oneEnvProperty.getPRDName(),  ValueGeneratorFactory.createRandomFloat((float)oneEnvProperty.getPRDRange().getFrom(), (float)oneEnvProperty.getPRDRange().getTo()),
//                        true, new Range(oneEnvProperty.getPRDRange().getFrom(), oneEnvProperty.getPRDRange().getTo()));
            case "string":
                return new StringPropertyDefinition(oneEnvProperty.getPRDName(), ValueGeneratorFactory.createRandomString(),true);
            default:
                throw new IllegalArgumentException("Invalid property type: " + oneEnvProperty.getType());
        }
    }
    private void ckPropertyExist(PRDEnvProperty oneProperty) {
        for(Map.Entry<String, IPropertyDefinition> propertyDefinitionEntry : this.getEnvVariables().entrySet()) {
            if(propertyDefinitionEntry.getKey().equals(oneProperty.getPRDName())) {
                throw new IllegalArgumentException("Invalid environment property name: " + oneProperty.getPRDName() + "." + " A environment with the same name already exists");
            }
        }
    }
    @Override
    public IActiveEnvironment createActiveEnvironment() {
        return new ActiveEnvironmentImpl();
    }

    @Override
    public Map<String, IPropertyDefinition> getEnvVariables() {
        return propNameToPropDefinition;
    }
}