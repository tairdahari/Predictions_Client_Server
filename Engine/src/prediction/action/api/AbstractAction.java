package prediction.action.api;

import prediction.defenition.entity.IEntityDefinition;
import prediction.defenition.entity.ISecondaryEntity;
import prediction.execution.context.IContext;
import prediction.execution.instance.entity.IEntityInstance;

public abstract class AbstractAction implements IAction {
    private final eActionType actionType;

    private final IEntityDefinition entityDefinition;
    private ISecondaryEntity secondaryEntityDefinition;

    protected AbstractAction(eActionType actionType, IEntityDefinition entityDefinition, ISecondaryEntity secondaryEntityDefinition) {
        this.actionType = actionType;
        this.entityDefinition = entityDefinition;

        if (secondaryEntityDefinition != null)
            this.secondaryEntityDefinition = secondaryEntityDefinition;
    }

    @Override
    public eActionType getActionType() {
        return actionType;
    }

    @Override
    public IEntityDefinition getPrimaryEntity() {
        return entityDefinition;
    }
    @Override
    public ISecondaryEntity getSecondaryEntityDefinition() {
        return secondaryEntityDefinition;
    }
    @Override
    public IEntityInstance getCurrEntity(IContext context) {
        if(entityDefinition.getEntityName().equals(context.getPrimaryEntityInstance().getEntityDefinition().getEntityName())){
            return context.getPrimaryEntityInstance();
        } else if (context.getSecondaryEntityInstance() != null) {
            if(entityDefinition.getEntityName().equals(context.getSecondaryEntityInstance().getEntityDefinition().getEntityName()))
                return context.getSecondaryEntityInstance();
            else
                throw new RuntimeException();
        } else if (context.getSecondaryEntityName() != null) {
            return null;
        } else
            throw new RuntimeException();
    }


}