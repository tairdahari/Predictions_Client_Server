package prediction.function.api;

import prediction.execution.context.IContext;
import prediction.execution.instance.entity.IEntityInstance;

import java.io.Serializable;

public interface IFunction extends Serializable {
    String getArgumentName();
    Object execute(Object argObject, IContext context);
}