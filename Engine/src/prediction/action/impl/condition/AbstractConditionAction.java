package prediction.action.impl.condition;

import prediction.action.api.AbstractAction;
import prediction.action.api.IAction;
import prediction.action.api.eActionType;
import prediction.defenition.entity.IEntityDefinition;
import prediction.defenition.entity.ISecondaryEntity;
import prediction.execution.context.IContext;

import java.util.List;

public abstract class AbstractConditionAction extends AbstractAction {
    private  List<IAction> thenSection;
    private  List<IAction>  elseSection;

    public AbstractConditionAction(IEntityDefinition entityDefinition, List<IAction> thenSection, List<IAction> elseSection, ISecondaryEntity secondaryEntityDefinition) {
        super(eActionType.CONDITION, entityDefinition, secondaryEntityDefinition);
        this.thenSection = thenSection;
        this.elseSection = elseSection;
    }

    public List<IAction> getThenSection() {
        return thenSection;
    }

    public List<IAction> getElseSection() {
        return elseSection;
    }

    public AbstractConditionAction(IEntityDefinition entityDefinition) {
        super(eActionType.CONDITION, entityDefinition, null);
    }

    @Override
    public void execute(IContext context) {
        if (checkCondition(context)) {
            executeActions(thenSection, context);
        } else {
            executeActions(elseSection, context);
        }
    }
    public abstract boolean checkCondition(IContext context);
    private void executeActions(List<IAction> actions, IContext context) {
        for (IAction action : actions) {
            action.execute(context);
        }
    }

}