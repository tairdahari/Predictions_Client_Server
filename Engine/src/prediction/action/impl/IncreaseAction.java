package prediction.action.impl;

import prediction.action.api.AbstractAction;
import prediction.action.api.eActionType;
import prediction.defenition.entity.IEntityDefinition;
import prediction.defenition.entity.ISecondaryEntity;
import prediction.defenition.property.api.ePropertyType;
import prediction.execution.context.IContext;
import prediction.execution.instance.entity.IEntityInstance;
import prediction.execution.instance.property.IPropertyInstance;
import prediction.expression.ExpressionHandlerImpl;
import prediction.function.api.AbstractFunction;
public class IncreaseAction extends AbstractAction {
    private final String propertyName;
    private final String byExpression;
    private ExpressionHandlerImpl expressionHandler;

    public IncreaseAction(IEntityDefinition entityDefinition, ISecondaryEntity secondaryEntity, String propertyName, String byExpression) {
        super(eActionType.INCREASE, entityDefinition, secondaryEntity);
        this.propertyName = propertyName;
        this.byExpression= byExpression;
        this.expressionHandler = new ExpressionHandlerImpl(entityDefinition, byExpression, "NUMERIC");
    }

    @Override
    public void execute(IContext context) {
        Object evaluatedObject;
        Object newValue;

        IEntityInstance entityInstance = getCurrEntity(context);
        if(entityInstance == null)
            return;
        IPropertyInstance propertyInstance = entityInstance.getPropertyByName(propertyName);

        if (!verifyNumericPropertyTYpe(propertyInstance)) {
            throw new IllegalArgumentException("increase action can't operate on a none number property [" + propertyName);
        }
        if( expressionHandler.getValueExpression(context) instanceof AbstractFunction) {
             evaluatedObject =((AbstractFunction) expressionHandler.getValueExpression(context)).execute(((AbstractFunction) expressionHandler.getValueExpression(context)).getArgumentName(), context);
            this.expressionHandler = new ExpressionHandlerImpl(super.getPrimaryEntity(), evaluatedObject.toString(), "NUMERIC");
        }

        newValue =  context.ckNewValueInRange((float)propertyInstance.getValue()+(float)expressionHandler.getValueExpression(context), propertyInstance);
        propertyInstance.updateValue(newValue, context);
    }

    public static boolean verifyNumericPropertyTYpe(IPropertyInstance propertyValue) {
        return
                ePropertyType.DECIMAL.equals(propertyValue.getPropertyDefinition().getType()) || ePropertyType.FLOAT.equals(propertyValue.getPropertyDefinition().getType());
    }
    public String getPropertyName() {
        return propertyName;
    }

    public String getByExpression() {
        return byExpression;
    }
}