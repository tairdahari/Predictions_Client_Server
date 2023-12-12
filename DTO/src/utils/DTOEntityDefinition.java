package utils;

import prediction.defenition.entity.EntityDefinitionImpl;
import prediction.defenition.entity.IEntityDefinition;
import prediction.defenition.property.api.IPropertyDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DTOEntityDefinition {
    private final String entityName;
    private int numOfPopulation;
    private final List<DTOPropertyDefinition> entityProperties;

    public DTOEntityDefinition(IEntityDefinition entityDefinition) {

        this.entityName = entityDefinition.getEntityName();
        //this.numOfPopulation = entityDefinition.getNumOfPopulation();
        this.entityProperties = new ArrayList<>();
        initListProperty(entityDefinition.getEntityProperties());
    }

    private void initListProperty(Map<String, IPropertyDefinition> entityProperties) {
        for (Map.Entry<String , IPropertyDefinition> propertyDefinition : entityProperties.entrySet()) {
            this.entityProperties.add(new DTOPropertyDefinition(propertyDefinition.getValue()));
        }
    }

    public String getEntityName() {
        return entityName;
    }

    public Integer getNumOfPopulation() {
        return numOfPopulation;
    }

    public List<DTOPropertyDefinition> getEntityProperties() {
        return entityProperties;
    }

}