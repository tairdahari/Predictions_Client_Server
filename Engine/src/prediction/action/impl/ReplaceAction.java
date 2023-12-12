package prediction.action.impl;

import prediction.action.api.AbstractAction;
import prediction.action.api.eActionType;
import prediction.defenition.entity.IEntityDefinition;
import prediction.defenition.entity.ISecondaryEntity;
import prediction.defenition.property.api.IPropertyDefinition;
import prediction.execution.context.GridManager;
import prediction.execution.context.IContext;
import prediction.execution.instance.entity.EntityInstanceImpl;
import prediction.execution.instance.entity.IEntityInstance;
import prediction.execution.instance.entity.manager.IEntityInstanceManager;
import prediction.execution.instance.property.IPropertyInstance;
import prediction.execution.instance.property.PropertyInstanceImpl;

import java.util.Map;

public class ReplaceAction extends AbstractAction {
    private final String create;
    private final String mode;

    public ReplaceAction(IEntityDefinition kill, ISecondaryEntity secondaryEntity, String create, String mode) {
        super(eActionType.REPLACE, kill, secondaryEntity);
        this.create = create;
        this.mode = mode;
    }
    public String getCreate() {
        return create;
    }

    public String getMode() {
        return mode;
    }

    @Override
    public void execute(IContext context) {
        IEntityInstance entityToKill = context.getPrimaryEntityInstance();
        IEntityInstanceManager entityInstanceManager = context.getEntityInstanceManager();
        GridManager gridManager = context.getGridManager();
        int id = entityToKill.getId();


        if (entityToKill != null) {
            context.removeEntity(entityToKill);
        } else {
            throw new IllegalArgumentException("Entity to kill does not exist in the context.");
        }

        IEntityInstance newEntityInstance;
        IEntityDefinition entityToCreate = context.getWorld().getEntityManager().getEntity(create);

        //if(ckAvailablePlaceForNewEntity())
        if ("scratch".equalsIgnoreCase(mode)) {
            newEntityInstance = createEntityFromScratch(entityToCreate, id, gridManager);
        } else if ("derived".equalsIgnoreCase(mode)) {
            newEntityInstance = createEntityDerivedFromKilled(entityToKill, entityToCreate, id, gridManager, context);
        } else {
            throw new IllegalArgumentException("Invalid mode value. Mode must be 'scratch' or 'derived'.");
        }

        context.getInstancesForUpdate().add(newEntityInstance);
        //context.getEntityInstanceManager().setCount(context.getEntityInstanceManager().getCount() +1);
    }

    private IEntityInstance createEntityFromScratch(IEntityDefinition entityToCreateDefinition, int id, GridManager gridManager) {
        IEntityInstance newEntityInstance = new EntityInstanceImpl(entityToCreateDefinition, id, gridManager, null, "scratch");

        for (Map.Entry<String, IPropertyDefinition> propertyDefinition: entityToCreateDefinition.getEntityProperties().entrySet()) {
            Object value = propertyDefinition.getValue().generateValue();
            IPropertyInstance newPropertyInstance = new PropertyInstanceImpl(propertyDefinition.getValue(), value);
            newEntityInstance.addPropertyInstance(newPropertyInstance);
        }

        return newEntityInstance;
    }

    private IEntityInstance createEntityDerivedFromKilled(IEntityInstance killedEntity, IEntityDefinition entityToCreateDefinition, Integer id, GridManager gridManager, IContext context) {

        IEntityInstance entityToCreate = new EntityInstanceImpl(entityToCreateDefinition, id, gridManager, killedEntity.getPosition(), "derived");

        for (Map.Entry<String, IPropertyInstance> propertyEntry: killedEntity.getPropertiesDefinition().entrySet()) {
            String propertyName = propertyEntry.getKey();

            if (entityToCreate.getEntityDefinition().getEntityProperties().containsKey(propertyName))
                entityToCreate.getPropertyByName(propertyName).updateValue(propertyEntry.getValue(),context);
            else
                entityToCreate.addPropertyInstance(killedEntity.getPropertyByName(propertyName));
        }
        return entityToCreate;
    }
}

