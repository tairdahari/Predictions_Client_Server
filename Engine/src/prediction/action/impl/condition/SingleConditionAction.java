package prediction.action.impl.condition;

import prediction.action.api.IAction;
import prediction.action.api.eExpressionType;
import prediction.defenition.entity.IEntityDefinition;
import prediction.defenition.entity.ISecondaryEntity;
import prediction.execution.context.IContext;
import prediction.expression.ExpressionHandlerImpl;
import prediction.function.api.AbstractFunction;

import java.util.List;

public class SingleConditionAction extends AbstractConditionAction {
    private final String operator;
    private String value;
    private String propertyExpression;
    private ExpressionHandlerImpl expressionHandler;
    private ExpressionHandlerImpl expressionPropertyHandler;
    public SingleConditionAction(IEntityDefinition entityDefinition, String propertyExpression, String operator, String value,
                                 List<IAction> thenSection, List<IAction> elseSection, ISecondaryEntity secondaryEntity) {
        super(entityDefinition, thenSection, elseSection, secondaryEntity);
        this.propertyExpression = propertyExpression;
        this.operator = operator;
        this.value = value;
        this.expressionHandler = new ExpressionHandlerImpl(entityDefinition, value, "STRING");
        this.expressionPropertyHandler = new ExpressionHandlerImpl(entityDefinition, propertyExpression, "STRING");
    }
    public SingleConditionAction(IEntityDefinition entityDefinition, String propertyExpression, String operator, String value) {
        super(entityDefinition);
        this.propertyExpression = propertyExpression;
        this.operator = operator;
        this.value = value;
        this.expressionHandler = new ExpressionHandlerImpl(entityDefinition, value, "STRING");
        this.expressionPropertyHandler = new ExpressionHandlerImpl(entityDefinition, propertyExpression, "STRING");

    }
    public ExpressionHandlerImpl getPropertyExpression() {
            return expressionPropertyHandler;
    }
    public  String getValue() {
        return value;
    }
    public String getOperator() {
        return operator;
    }
    public ExpressionHandlerImpl getExpressionHandler() {
        return expressionHandler;
    }
    @Override
    public boolean checkCondition(IContext context) {
        eExpressionType expressionType = expressionPropertyHandler.getExpressionType(propertyExpression, null);
        Object propertyValue = expressionPropertyHandler.getValueExpression(context);

        Object evaluatedObject;

        if (propertyValue == null) {
            return false;
        }
        if (expressionPropertyHandler.getValueExpression(context) instanceof AbstractFunction) {
            propertyValue = ((AbstractFunction) expressionPropertyHandler.getValueExpression(context)).execute(((AbstractFunction) expressionPropertyHandler.getValueExpression(context)).getArgumentName(), context);
        }

        if (expressionHandler.getValueExpression(context) instanceof AbstractFunction) {
            evaluatedObject = ((AbstractFunction) expressionHandler.getValueExpression(context)).execute(((AbstractFunction) expressionHandler.getValueExpression(context)).getArgumentName(), context);
            this.expressionHandler = new ExpressionHandlerImpl(super.getPrimaryEntity(), evaluatedObject.toString(),expressionType.toString());
            this.value = this.expressionHandler.getValueExpression(context).toString();
        }

        if ("=".equals(operator)) {
            return propertyValue.toString().equals(value);
        } else if ("!=".equals(operator)) {
            return !propertyValue.toString().equals(value);
        } else if ("bt".equals(operator)) {
            try {
                float floatValue = Float.parseFloat(value);
                return (Float) propertyValue > floatValue;
            } catch (NumberFormatException e) {
                return false; // Value cannot be parsed as a float
            }
        } else if ("lt".equals(operator)) {
            try {
                float floatValue = Float.parseFloat(value);
                return (Float) propertyValue < floatValue;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }
}