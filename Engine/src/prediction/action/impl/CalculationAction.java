package prediction.action.impl;

import prediction.action.api.AbstractAction;
import prediction.action.api.eActionType;
import prediction.defenition.entity.IEntityDefinition;
import prediction.defenition.entity.ISecondaryEntity;
import prediction.expression.ExpressionHandlerImpl;

public abstract class CalculationAction extends AbstractAction {
    private final String resultProperty;
    private ExpressionHandlerImpl expressionHandler1;
    private ExpressionHandlerImpl expressionHandler2;
    public CalculationAction
            (eActionType actionType, IEntityDefinition entityDefinition, ISecondaryEntity secondaryEntity, String resultProperty, String arg1 , String arg2) {
        super(actionType, entityDefinition, secondaryEntity);
        this.resultProperty = resultProperty;
        this.expressionHandler1 = new ExpressionHandlerImpl(entityDefinition,arg1, "NUMERIC");
        this.expressionHandler2 = new ExpressionHandlerImpl(entityDefinition, arg2, "NUMERIC");
    }

    public ExpressionHandlerImpl getExpressionHandler1() {
        return expressionHandler1;
    }

    public void setExpressionHandler1(ExpressionHandlerImpl newExpression) {
        this.expressionHandler1 = newExpression;
    }

    public void setExpressionHandler2(ExpressionHandlerImpl newExpression) {
        this.expressionHandler2 = newExpression;
    }

    public ExpressionHandlerImpl getExpressionHandler2() {
        return expressionHandler2;
    }

    public String getResultProperty() {
        return resultProperty;
    }
}