package prediction.defenition.entity.manager;

import prediction.defenition.entity.IEntityDefinition;

import java.io.Serializable;
import java.util.Map;

public interface IEntityDefinitionManager extends Serializable {
    void addEntity(IEntityDefinition entity);
    IEntityDefinition getEntity(String entityName);
    Map<String,IEntityDefinition> getEntities();
}