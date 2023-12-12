package prediction.defenition.valueGenerator.api;

import prediction.defenition.valueGenerator.fixed.FixedValueGenerator;
import prediction.defenition.valueGenerator.random.impl.RandomStringGenerator;
import prediction.defenition.valueGenerator.random.impl.bool.RandomBooleanValueGenerator;
import prediction.defenition.valueGenerator.random.impl.numeric.RandomFloatGenerator;
import prediction.defenition.valueGenerator.random.impl.numeric.RandomIntegerGenerator;

import java.io.Serializable;

public interface ValueGeneratorFactory extends Serializable {
    static <T> ValueGenerator<T> createFixed(T value) {
        return new FixedValueGenerator<>(value);
    }

    static ValueGenerator<Boolean> createRandomBoolean() {
        return new RandomBooleanValueGenerator();
    }

    static ValueGenerator<Float> createRandomFloat(Float from, Float to) {
        return new RandomFloatGenerator(from, to);
    }

    static ValueGenerator<String> createRandomString() {
        return new RandomStringGenerator();
    }

    static ValueGenerator<Integer> createRandomInteger(Integer from, Integer to) { return new RandomIntegerGenerator(from, to);}
}