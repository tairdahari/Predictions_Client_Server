package prediction.defenition.entity.manager;

import prediction.defenition.entity.EntityDefinitionImpl;
import prediction.defenition.entity.IEntityDefinition;
import xmlJavaFXReader.schema.generated.PRDEntities;
import xmlJavaFXReader.schema.generated.PRDEntity;

import java.util.LinkedHashMap;
import java.util.Map;

public class EntityDefinitionManager implements IEntityDefinitionManager {
    private final Map<String, IEntityDefinition> entities = new LinkedHashMap<>();

    public EntityDefinitionManager(PRDEntities prdEntities) {
        for (PRDEntity oneEntity: prdEntities.getPRDEntity()) {
            EntityDefinitionImpl entity = new EntityDefinitionImpl(oneEntity);
            entities.put(entity.getEntityName(), entity);
        }
    }

    @Override
    public void addEntity(IEntityDefinition entity) {
        entities.put(entity.getEntityName(), entity);
    }

    @Override
    public IEntityDefinition getEntity(String entityName) {
        for (Map.Entry<String, IEntityDefinition> entityDefinitionEntry: this.entities.entrySet()) {
            if (entityDefinitionEntry.getKey().equals(entityName)) {
                return entities.get(entityName);
            }
        }
        throw new IllegalArgumentException("Invalid Entity name: " + entityName + ". " + "This Entity doesn't exists.");
    }

    @Override
    public Map<String, IEntityDefinition> getEntities() {
        return entities;
    }
}