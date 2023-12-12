package prediction.expression;

import prediction.action.api.eExpressionType;
import prediction.defenition.entity.IEntityDefinition;
import prediction.defenition.property.api.IPropertyDefinition;
import prediction.execution.context.IContext;
import prediction.execution.instance.entity.IEntityInstance;
import prediction.execution.instance.property.IPropertyInstance;
import prediction.function.impl.*;

import java.util.Map;

public class ExpressionHandlerImpl implements IExpressionHandler {
    private final eExpressionType expressionType;
    private final String expression;
    private final String typeExpressionNeeded;
    private final IEntityDefinition entityDefinition;

    public ExpressionHandlerImpl(IEntityDefinition entityDefinition, String expression, String typeExpression)
    {
        this.entityDefinition = entityDefinition;
        this.expression = expression;
        this.expressionType = getExpressionType(expression, typeExpression);
        this.typeExpressionNeeded = typeExpression;

        if(expressionType.equals(eExpressionType.FREEVALUE)) {
            evaluateFreeVal(expression, typeExpressionNeeded);
        }
    }
    public String getExpression() {
        return expression;
    }

    public String getExpressionTypeNeeded() {
        return typeExpressionNeeded;
    }

    @Override
    public eExpressionType getExpressionType(String expression, String typeExpression) {

        if (isSystemFunction(expression)) {
            return eExpressionType.FUNCTION;
        } else if (isProperty(expression)) {
            return eExpressionType.PROPERTY;
        } else {
            return  eExpressionType.FREEVALUE;
        }
    }

    @Override
    public Boolean isSystemFunction(String expression)
    {
        return expression.contains("(");
    }
    @Override
    public Object evaluateSystemFunction(String expression, IContext context) {

        int openParenIndex = expression.indexOf("(");
        int closeParenIndex = expression.lastIndexOf(")");

        String functionName = expression.substring(0, openParenIndex);
        String argument = expression.substring(openParenIndex + 1, closeParenIndex);
//
//        if ("random".equals(functionName)) {
//            RandomFunction randomFunction = new RandomFunction(argument);
//            return randomFunction.execute(argument, context);
        if("random".equals(functionName))
            return new RandomFunction(argument);
        else if ("environment".equals(functionName))
            return new EnvironmentFunction(argument);
         else if("evaluate".equals(functionName)) {
            return new EvaluateFunction(argument);
        }else if("percent".equals(functionName)) {
            return new PercentFunction(argument);
        } else {
             return new TicksFunction(functionName, argument);
        }
    }

    @Override
    public Boolean isProperty(String expression) {
        for(Map.Entry<String, IPropertyDefinition> propertyDefinitionEntry : entityDefinition.getEntityProperties().entrySet()) {
            if(propertyDefinitionEntry.getKey().equals(expression)) {
                return true;
            }
        }
        return false;
    }



    public Object evaluateFreeVal(String expression, String typeExpression)
    {
        switch (typeExpression) {
            case "NUMERIC":
            case "DECIMAL":
            case "FLOAT":
                return evaluateNumberExpression(expression);
            case "BOOLEAN":
                return evaluateBoolean(expression);
            case "STRING":
                return expression; // Return the expression as is for string
            default:
                throw new IllegalArgumentException("Unknown expression type: " + typeExpression);
        }
    }
    public Object evaluateNumberExpression(String expression) throws NumberFormatException {

        try {
            return Float.parseFloat(expression);
//            if (expression.contains(".")) {
//                return Double.parseDouble(expression);
//            }
//            else {
//                return Integer.parseInt(expression);
//            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid number format: " + expression);
        }
    }

    private Object evaluateBoolean(String expression) {
        if ("true".equalsIgnoreCase(expression)) {
            return true;
        } else if ("false".equalsIgnoreCase(expression)) {
            return false;
        } else {
            throw new IllegalArgumentException("Invalid boolean value: " + expression);
        }
    }
    @Override
    public Object evaluateProperty(String expression, IContext context) {
        IEntityInstance entityInstance = getCurrEntity(context);
        if(entityInstance == null)
            return null;
        IPropertyInstance propertyInstance = entityInstance.getPropertyByName(expression);

        return propertyInstance.getValue();
    }
    @Override
    public IEntityInstance getCurrEntity(IContext context) {
        if(entityDefinition.getEntityName().equals(context.getPrimaryEntityInstance().getEntityDefinition().getEntityName())){
            return context.getPrimaryEntityInstance();
        } else if (context.getSecondaryEntityInstance() != null) {
            if(entityDefinition.getEntityName().equals(context.getSecondaryEntityInstance().getEntityDefinition().getEntityName()))
                return context.getSecondaryEntityInstance();
            else
                throw new RuntimeException();
        } else if (context.getSecondaryEntityName() != null) {
            return null;
        } else
            throw new RuntimeException();
    }

    public Object getValueExpression(IContext context)
    {

        if (expressionType.equals(eExpressionType.FUNCTION)) {
            return evaluateSystemFunction(expression, context);
        } else if (expressionType.equals(eExpressionType.PROPERTY)) {
            return evaluateProperty(expression, context);
        } else {
            return evaluateFreeVal(expression, typeExpressionNeeded);
        }
    }
    @Override
    public boolean isFloat() {
        try {
            Float.parseFloat(this.expression);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}