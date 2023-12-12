package prediction.action.impl;

import prediction.action.api.AbstractAction;
import prediction.action.api.eActionType;
import prediction.defenition.entity.IEntityDefinition;
import prediction.defenition.entity.ISecondaryEntity;
import prediction.execution.context.IContext;
import prediction.execution.instance.entity.IEntityInstance;
import prediction.execution.instance.property.IPropertyInstance;
import prediction.expression.ExpressionHandlerImpl;
import prediction.function.api.AbstractFunction;

public class SetAction extends AbstractAction {
    private final String propertyName;
    private final String valueToSet;
    private ExpressionHandlerImpl expressionHandler;

    public SetAction(IEntityDefinition entityDefinition, ISecondaryEntity secondaryEntity, String property, String valueToSet) {
        super(eActionType.SET, entityDefinition, secondaryEntity);
        this.propertyName = property;
        this.valueToSet = valueToSet;
        this.expressionHandler = new ExpressionHandlerImpl(entityDefinition, valueToSet, entityDefinition.getEntityProperties().get(property).getType().toString());
    }

    @Override
    public void execute(IContext context) {
        Object evaluatedObject;
        IEntityInstance entityInstance = getCurrEntity(context);
        if(entityInstance == null)
            return;

        IPropertyInstance propertyInstance = entityInstance.getPropertyByName(propertyName);

        if( expressionHandler.getValueExpression(context) instanceof AbstractFunction) {
            evaluatedObject =((AbstractFunction) expressionHandler.getValueExpression(context)).execute(((AbstractFunction) expressionHandler.getValueExpression(context)).getArgumentName(), context);
            this.expressionHandler = new ExpressionHandlerImpl(super.getPrimaryEntity(), evaluatedObject.toString(), "NUMERIC");
        }
        evaluatedObject = expressionHandler.getValueExpression(context);
        if(expressionHandler.getExpressionTypeNeeded().equals("NUMERIC"))
            evaluatedObject = context.ckNewValueInRange(expressionHandler.getValueExpression(context), propertyInstance);
        propertyInstance.updateValue(evaluatedObject, context);
    }
    public String getPropertyName() {
        return propertyName;
    }

    public String getValueToSet() {
        return valueToSet;
    }
}