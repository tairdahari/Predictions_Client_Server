package prediction.action.impl;

import prediction.action.api.eActionType;
import prediction.defenition.entity.IEntityDefinition;
import prediction.defenition.entity.ISecondaryEntity;
import prediction.defenition.property.api.ePropertyType;
import prediction.execution.context.IContext;
import prediction.execution.instance.entity.IEntityInstance;
import prediction.execution.instance.property.IPropertyInstance;
import prediction.function.api.AbstractFunction;

import static prediction.action.impl.IncreaseAction.verifyNumericPropertyTYpe;

public class MultiplyAction extends CalculationAction {

    public MultiplyAction(eActionType actionType, IEntityDefinition entityDefinition, ISecondaryEntity secondaryEntity, String resultProperty, String arg1, String arg2) {
        super(actionType, entityDefinition,secondaryEntity, resultProperty, arg1, arg2);
    }

    @Override
    public void execute(IContext context) {
        Object arg1 = super.getExpressionHandler1().getValueExpression(context);
        Object arg2 = super.getExpressionHandler2().getValueExpression(context);

        IEntityInstance entityInstance = getCurrEntity(context);
        if(entityInstance == null)
            return;

        IPropertyInstance propertyInstance = entityInstance.getPropertyByName(getResultProperty());

        if (!verifyNumericPropertyTYpe(propertyInstance)) {
            throw new IllegalArgumentException("multiply action can't operate on a none number property [" + super.getResultProperty());
        }
        if(arg1 instanceof AbstractFunction) {
            arg1 = ((AbstractFunction) arg1).execute(((AbstractFunction) arg1).getArgumentName(), context);
        }
        if (arg2 instanceof  AbstractFunction) {
            arg2 = ((AbstractFunction) arg2).execute(((AbstractFunction) arg2).getArgumentName(), context);
        }

        if(super.getExpressionHandler1().isFloat()||
                super.getExpressionHandler2().isFloat()) {
            if(propertyInstance.getPropertyDefinition().getType().equals(ePropertyType.FLOAT)) {
                multiplyArguments(context, propertyInstance, arg1, arg2);
            }
            else {
                throw new IllegalArgumentException("Multiplication not allowed for decimal result variable [" + super.getResultProperty());
            }
        } else {
            multiplyArguments(context, propertyInstance, arg1, arg2);
        }
    }

    public void multiplyArguments(IContext context, IPropertyInstance propertyInstance, Object arg1, Object arg2) {
        Object newValue;

        if (arg1 instanceof Integer && arg2 instanceof Integer) {
            int result = (int) arg1 * (int) arg2;
            newValue = context.ckNewValueInRange(result, propertyInstance);
            propertyInstance.updateValue(newValue, context);
        } else if (arg1 instanceof Float || arg2 instanceof Float) {
            float result = convertToFloat(arg1) * convertToFloat(arg2);
            newValue = context.ckNewValueInRange(result, propertyInstance);
            propertyInstance.updateValue(newValue, context);
        }
    }

    public static float convertToFloat(Object value) {
        if (value instanceof Float ) {
            return (float) value;
        } else if (value instanceof Integer) {
            return (float) (int) value;
        } else {
            try {
                return Float.parseFloat(value.toString());
            } catch (Exception e) {
                throw new IllegalArgumentException("Cannot convert argument to float: " + value.getClass().getSimpleName());
            }
        }
    }
}