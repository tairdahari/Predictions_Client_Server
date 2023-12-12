package prediction.defenition.valueGenerator.random.impl.bool;

import prediction.defenition.valueGenerator.random.api.AbstractRandomValueGenerator;

public class RandomBooleanValueGenerator extends AbstractRandomValueGenerator<Boolean> {

    @Override
    public Boolean generateValue() {
        return random.nextDouble() < 0.5;
    }
}
