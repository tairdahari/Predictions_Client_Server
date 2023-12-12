package prediction.function.impl;


import com.sun.xml.internal.ws.protocol.soap.MessageCreationException;
import prediction.execution.context.IContext;
import prediction.function.api.AbstractFunction;

import java.util.Random;

public class RandomFunction extends AbstractFunction {

    public RandomFunction(String argumentName) {
        super("random", argumentName);
    }

    @Override
    public Object execute(Object arg, IContext context) {
        try {
            int max = Integer.parseInt(arg.toString());
            if (max <= 0) {
                throw new IllegalArgumentException("Argument must be a positive integer");
            }

            Random random = new Random();

            return random.nextInt(max + 1);
        } catch(MessageCreationException e) {
            throw new IllegalArgumentException("Argument must be an integer");
        }
    }
}
