package prediction.expression;

import prediction.action.api.eExpressionType;
import prediction.execution.context.IContext;
import prediction.execution.instance.entity.IEntityInstance;

import java.io.Serializable;

public interface IExpressionHandler extends Serializable {
    Boolean isSystemFunction(String expression);
    Object evaluateSystemFunction(String expression ,IContext context);
    Boolean isProperty(String expression);
    Object evaluateProperty(String expression, IContext context);
    Object evaluateNumberExpression(String expression);
    eExpressionType getExpressionType(String expression, String typeExpression);

    IEntityInstance getCurrEntity(IContext context);

    boolean isFloat();
}
