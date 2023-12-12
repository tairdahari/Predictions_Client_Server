package utils;

import prediction.action.api.IAction;
import prediction.action.api.eActionType;
import prediction.action.impl.*;
import prediction.action.impl.condition.MultipleConditionAction;
import prediction.action.impl.condition.SingleConditionAction;
import prediction.rule.IRule;
import utils.DTOActions.*;

import java.util.ArrayList;
import java.util.List;

public class DTORuleDefinition {
    private final String name;
    private final Integer ticks;
    private final Double probability;
    private final Integer numOfActions;
    private List<String> actions;

    private List<DTOActionDetails> actionDetails;

    public DTORuleDefinition(IRule value) {
        this.name = value.getName();
        this.ticks= value.getActivation().getTicks();
        this.probability = value.getActivation().getProbability();
        this.numOfActions= value.getActionsToPerform().size();

        initListActions(value.getActionsToPerform());
    }

    private void initListActions(List<IAction> ruleActions) {
        this.actions = new ArrayList<>();
        this.actionDetails = new ArrayList<>();

        for (IAction oneAction : ruleActions) {
            this.actions.add(oneAction.getActionType().name());
            DTOActionDetails action = null;

            switch (oneAction.getActionType().name()) {
                case "INCREASE":
                    IncreaseAction increaseAction = (IncreaseAction)oneAction;
                    action = new DTOIncreaseAction(oneAction.getActionType().name(), oneAction.getPrimaryEntity().getEntityName(),increaseAction.getPropertyName(), increaseAction.getByExpression());
                    break;
                case "DECREASE":
                    DecreaseAction decreaseAction = (DecreaseAction) oneAction;
                    action = new DTODecreaseAction(oneAction.getActionType().name(), oneAction.getPrimaryEntity().getEntityName(),decreaseAction.getPropertyName(), decreaseAction.getByExpression());
                    break;
                case "CALCULATION":
                   // action = new DTOCalculationAction(oneAction.getActionType().name(), oneAction.getContextEntity().getEntityName(), ((CalculationAction) oneAction).getExpressionHandler1().getExpression(), ((CalculationAction) oneAction).getExpressionHandler2().getExpression(), ((CalculationAction) oneAction).getResultProperty());
                    action = initCalcAction(oneAction);
                    break;
                case "CONDITION":
                    action = initConditionAction(oneAction);
                    break;
                case "KILL":
                    action = new DTOKillAction(oneAction.getActionType().name(), oneAction.getPrimaryEntity().getEntityName());
                    break;
                case "PROXIMITY":
                    ProximityAction proximityAction = (ProximityAction)oneAction;
                    action = new DTOProximityAction(oneAction.getActionType().name(), proximityAction.getSourceEntity().getEntityName(), proximityAction.getTargetEntity().getEntityName(), proximityAction.getExpressionHandler().getExpression(), proximityAction.getActions().size());
                    break;
                case "REPLACE":
                    ReplaceAction replaceAction = (ReplaceAction)oneAction;
                    action = new DTOReplaceAction(oneAction.getActionType().name(), oneAction.getPrimaryEntity().getEntityName() ,replaceAction.getCreate(), replaceAction.getMode());
                    break;
                case "SET":
                    SetAction setAction = (SetAction) oneAction;
                    action = new DTOSetAction(oneAction.getActionType().name(), oneAction.getPrimaryEntity().getEntityName(),setAction.getValueToSet(), setAction.getPropertyName());
                    break;
                default:
                    break;
            }

            this.actionDetails.add(action);
        }
    }

    private DTOActionDetails initConditionAction(IAction oneAction) {
        String seconderyEntityName;
        if(oneAction instanceof MultipleConditionAction) {
            if(oneAction.getSecondaryEntityDefinition() == null)
                seconderyEntityName = null;
            else
                seconderyEntityName = ((MultipleConditionAction) oneAction).getSecondaryEntityDefinition().getSecondaryEntityDefinition().getEntityName();
            return new DTOMultipleConditionAction(oneAction.getActionType().name(), oneAction.getPrimaryEntity().getEntityName(),seconderyEntityName ,((MultipleConditionAction) oneAction).getRootLogicalOperator(),((MultipleConditionAction) oneAction).getThenSection().size(), ((MultipleConditionAction) oneAction).getElseSection().size(),  ((MultipleConditionAction) oneAction).getConditionsSize());
        } else if (oneAction instanceof SingleConditionAction) {
            if(oneAction.getSecondaryEntityDefinition() == null)
                seconderyEntityName = null;
            else
                seconderyEntityName = ((SingleConditionAction) oneAction).getSecondaryEntityDefinition().getSecondaryEntityDefinition().getEntityName();
            return new DTOSingleConditionAction(oneAction.getActionType().name(), oneAction.getPrimaryEntity().getEntityName(), seconderyEntityName,((SingleConditionAction)oneAction).getPropertyExpression().getExpression(),((SingleConditionAction)oneAction).getValue(),((SingleConditionAction)oneAction).getOperator(), ((SingleConditionAction) oneAction).getThenSection().size(), ((SingleConditionAction) oneAction).getElseSection().size());
        } throw new IllegalArgumentException("Invalid Condition Action");
    }

    private DTOActionDetails initCalcAction(IAction oneAction) {
        if(oneAction instanceof DivideAction) {
            return new DTODivideAction(eActionType.DIVIDE.name(),oneAction.getActionType().name(), oneAction.getPrimaryEntity().getEntityName(), ((DivideAction) oneAction).getExpressionHandler1().getExpression(), ((DivideAction) oneAction).getExpressionHandler2().getExpression(), ((DivideAction) oneAction).getResultProperty());
        } else if (oneAction instanceof MultiplyAction) {
            return new DTOMultiplyAction(eActionType.MULTIPLY.name(),oneAction.getActionType().name(), oneAction.getPrimaryEntity().getEntityName(), ((MultiplyAction) oneAction).getExpressionHandler1().getExpression(), ((MultiplyAction) oneAction).getExpressionHandler2().getExpression(), ((MultiplyAction) oneAction).getResultProperty());
        }
        throw new IllegalArgumentException("Invalid action type");
    }


    public String getName() {
        return name;
    }

    public Integer getTicks() {
        return ticks;
    }

    public Double getProbability() {
        return probability;
    }

//    public Integer getNumOfActions() {
//        return numOfActions;
//    }

    public List<String> getActions() {
        return actions;
    }

    public List<DTOActionDetails> getListDetailsAction() {
        return actionDetails;
    }

    public Integer getNumOfActions() {
        return numOfActions;
    }
}
