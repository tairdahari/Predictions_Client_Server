package prediction.function.impl;

import prediction.execution.context.IContext;
import prediction.expression.ExpressionHandlerImpl;
import prediction.expression.IExpressionHandler;
import prediction.function.api.AbstractFunction;

import static prediction.action.impl.MultiplyAction.convertToFloat;

public class PercentFunction extends AbstractFunction {
    private ExpressionHandlerImpl expressionHandler1;
    private ExpressionHandlerImpl expressionHandler2;

    public PercentFunction(String argumentName) {
        super("percent", argumentName);
    }

    @Override
    public Object execute(Object argObject, IContext context) {
        try {
            String args = argObject.toString();
            String[] argArray = args.split(",");

            if (argArray.length != 2) {
                throw new IllegalArgumentException("Percent function requires 2 arguments separated by a comma");
            }

            String expression1 = argArray[0].trim();
            String expression2 = argArray[1].trim();

            this.expressionHandler1 = new ExpressionHandlerImpl(null, expression1, "NUMERIC");
            this.expressionHandler2 = new ExpressionHandlerImpl(null, expression2, "NUMERIC");


            Object value1 = expressionHandler1.getValueExpression(context);
            Object value2 = expressionHandler2.getValueExpression(context);

            if(value1 instanceof AbstractFunction) {
                value1 = ((AbstractFunction) value1).execute(((AbstractFunction) value1).getArgumentName(), context);
            }
            if (value2 instanceof  AbstractFunction) {
                value2 = ((AbstractFunction) value2).execute(((AbstractFunction) value2).getArgumentName(), context);
            }
            value1 = convertToFloat(value1);
            value2 = convertToFloat(value2);


            float wholePart = (float) value1;
            float percentage = (float) value2;

            if (percentage < 0 || percentage > 100) {
                throw new IllegalArgumentException("Percentage must be between 0 and 100");
            }

            return (wholePart * percentage) / 100.0;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while evaluating percent function: " + e.getMessage());
        }
    }

}
