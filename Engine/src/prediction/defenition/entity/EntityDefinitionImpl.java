package prediction.defenition.entity;

import prediction.defenition.property.Range;
import prediction.defenition.property.api.IPropertyDefinition;
import prediction.defenition.property.impl.BooleanPropertyDefinition;
import prediction.defenition.property.impl.FloatPropertyDefinition;
import prediction.defenition.property.impl.IntegerPropertyDefinition;
import prediction.defenition.property.impl.StringPropertyDefinition;
import prediction.defenition.valueGenerator.api.ValueGeneratorFactory;
import xmlJavaFXReader.schema.generated.PRDEntity;
import xmlJavaFXReader.schema.generated.PRDProperty;

import java.util.*;

public class EntityDefinitionImpl implements IEntityDefinition {
    private final String entityName;
    private Integer numOfPopulation;
    private final Map<String, IPropertyDefinition> entityProperties;
    public EntityDefinitionImpl(PRDEntity prdEntity) {
        this.entityName = prdEntity.getName();
        this.entityProperties = new LinkedHashMap<>();

        for (PRDProperty oneProperty : prdEntity.getPRDProperties().getPRDProperty()) {
            ckPropertyExist(oneProperty);
            IPropertyDefinition property = createPropertyDefinition(oneProperty);
            entityProperties.put(oneProperty.getPRDName(), property);
        }
    }
    private void ckPropertyExist(PRDProperty oneProperty) {
        for(Map.Entry<String, IPropertyDefinition> propertyDefinitionEntry : this.entityProperties.entrySet()) {
            if(propertyDefinitionEntry.getKey().equals(oneProperty.getPRDName())) {
                throw new IllegalArgumentException("Invalid property name: " + oneProperty.getPRDName() + "." + "A property with the same name already exists");
            }
        }
    }
    private IPropertyDefinition createPropertyDefinition(PRDProperty prdProperty) {
        Boolean isRandom = prdProperty.getPRDValue().isRandomInitialize();

        switch (prdProperty.getType()) {
            case "boolean":
                return new BooleanPropertyDefinition(prdProperty.getPRDName(), (isRandom ? ValueGeneratorFactory.createRandomBoolean(): ValueGeneratorFactory.createFixed(Boolean.valueOf(prdProperty.getPRDValue().getInit()))) , isRandom);
            case "decimal":
                return new IntegerPropertyDefinition(prdProperty.getPRDName(), (isRandom ? ValueGeneratorFactory.createRandomInteger((int) prdProperty.getPRDRange().getFrom(), (int) prdProperty.getPRDRange().getTo()) : ValueGeneratorFactory.createFixed(Integer.valueOf(prdProperty.getPRDValue().getInit()))),isRandom , new Range((int)prdProperty.getPRDRange().getFrom(), (int)prdProperty.getPRDRange().getTo()));
            case "float":
                return new FloatPropertyDefinition(prdProperty.getPRDName(), (isRandom ? ValueGeneratorFactory.createRandomFloat((float) prdProperty.getPRDRange().getFrom(), (float) prdProperty.getPRDRange().getTo()) : ValueGeneratorFactory.createFixed(Float.valueOf(prdProperty.getPRDValue().getInit()))), isRandom,  new Range((float)prdProperty.getPRDRange().getFrom(), (float)prdProperty.getPRDRange().getTo()));
            case "string":
                return new StringPropertyDefinition(prdProperty.getPRDName(),(isRandom ? ValueGeneratorFactory.createRandomString(): ValueGeneratorFactory.createFixed(prdProperty.getPRDValue().getInit())), isRandom);
            default:
                throw new IllegalArgumentException("Invalid property type: " + prdProperty.getType());
        }
    }
    @Override
    public String getEntityName() {
        return entityName;
    }
    @Override
    public Integer getNumOfPopulation() {
        return numOfPopulation;
    }
    @Override
    public Map<String, IPropertyDefinition> getEntityProperties() {
        return entityProperties;
    }
    @Override
    public IPropertyDefinition getPropertyByName(String name) {
        if (!entityProperties.containsKey(name)) {
            throw new IllegalArgumentException("for entity of type " + this.entityName + " has no property named " + name);
        }

        return entityProperties.get(name);
    }

    @Override
    public void setNumOfPopulation(Integer population) {
        this.numOfPopulation = population;
    }

}