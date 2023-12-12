package prediction.action.impl;

import prediction.action.api.eActionType;
import prediction.defenition.entity.IEntityDefinition;
import prediction.defenition.entity.ISecondaryEntity;
import prediction.execution.context.IContext;
import prediction.execution.instance.entity.IEntityInstance;
import prediction.execution.instance.property.IPropertyInstance;
import prediction.expression.ExpressionHandlerImpl;
import prediction.function.api.AbstractFunction;

import static prediction.action.impl.MultiplyAction.convertToFloat;

public class DivideAction extends CalculationAction {
    public DivideAction
            (eActionType actionType, IEntityDefinition entityDefinition, ISecondaryEntity secondaryEntity, String resultProperty, String arg1, String arg2) {
        super(actionType, entityDefinition, secondaryEntity, resultProperty, arg1, arg2);
    }

    @Override
    public void execute(IContext context) {
        Object evaluatedObject;

        if( super.getExpressionHandler1().getValueExpression(context) instanceof AbstractFunction) {
            evaluatedObject =((AbstractFunction) super.getExpressionHandler1().getValueExpression(context)).execute(((AbstractFunction) super.getExpressionHandler1().getValueExpression(context)).getArgumentName(), context);
            super.setExpressionHandler1(new ExpressionHandlerImpl(super.getPrimaryEntity(), evaluatedObject.toString(), "NUMERIC"));
        }
        if( super.getExpressionHandler2().getValueExpression(context) instanceof AbstractFunction) {
            evaluatedObject =((AbstractFunction) super.getExpressionHandler2().getValueExpression(context)).execute(((AbstractFunction) super.getExpressionHandler2().getValueExpression(context)).getArgumentName(), context);
            super.setExpressionHandler2(new ExpressionHandlerImpl(super.getPrimaryEntity(), evaluatedObject.toString(), "NUMERIC"));
        }

        Object arg1 = super.getExpressionHandler1().getValueExpression(context);
        Object arg2 = super.getExpressionHandler2().getValueExpression(context);

        IEntityInstance entityInstance = getCurrEntity(context);
        if(entityInstance == null)
            return;

        IPropertyInstance propertyInstance = entityInstance.getPropertyByName(getResultProperty());

        divideArguments(context, propertyInstance, arg1, arg2);
    }

    public void divideArguments(IContext context, IPropertyInstance propertyInstance, Object arg1, Object arg2) {
        Object newValue;

        if (arg1 instanceof Integer && arg2 instanceof Integer) {
            int result = (int) arg1 / (int) arg2;
            newValue = context.ckNewValueInRange(result, propertyInstance);
            propertyInstance.updateValue(newValue, context);
        } else if (arg1 instanceof Float || arg2 instanceof Float) {
            float result = convertToFloat(arg1) / convertToFloat(arg2);
            newValue = context.ckNewValueInRange(result, propertyInstance);
            propertyInstance.updateValue(newValue, context);
        }
    }
}