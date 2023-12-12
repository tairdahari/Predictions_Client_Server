package prediction.defenition.entity;

import prediction.defenition.property.api.IPropertyDefinition;

import java.io.Serializable;
import java.util.Map;

public interface IEntityDefinition extends Serializable {
    String getEntityName();
    Integer getNumOfPopulation();
    Map<String, IPropertyDefinition> getEntityProperties();
    IPropertyDefinition getPropertyByName(String name);
    void setNumOfPopulation(Integer population);
}