package prediction.function.api;

public abstract class AbstractFunction implements IFunction {

    private final String functionName;
    private final String argumentName;

    public AbstractFunction(String functionName, String argumentName) {
        this.functionName = functionName;
        this.argumentName = argumentName;
    }

    @Override
    public String getArgumentName() {
        return argumentName;
    }
}
