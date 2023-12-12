package prediction.action.api;

import prediction.defenition.entity.IEntityDefinition;
import prediction.defenition.entity.ISecondaryEntity;
import prediction.execution.context.IContext;
import prediction.execution.instance.entity.IEntityInstance;

import java.io.Serializable;

public interface IAction extends Serializable {
    void execute(IContext context);
    eActionType getActionType();
    IEntityDefinition getPrimaryEntity();
    ISecondaryEntity getSecondaryEntityDefinition();

    IEntityInstance getCurrEntity(IContext context);
}