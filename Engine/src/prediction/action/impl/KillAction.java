package prediction.action.impl;

import prediction.action.api.AbstractAction;
import prediction.action.api.eActionType;
import prediction.defenition.entity.IEntityDefinition;
import prediction.defenition.entity.ISecondaryEntity;
import prediction.execution.context.IContext;
import prediction.execution.instance.entity.IEntityInstance;
import prediction.execution.instance.property.IPropertyInstance;

public class KillAction extends AbstractAction {
    public KillAction(IEntityDefinition entityDefinition, ISecondaryEntity secondaryEntity) {
        super(eActionType.KILL, entityDefinition, secondaryEntity);
    }

    @Override
    public void execute(IContext context) {
        IEntityInstance entityInstance = getCurrEntity(context);
        if(entityInstance == null)
            return;

        context.removeEntity(entityInstance);
    }
}