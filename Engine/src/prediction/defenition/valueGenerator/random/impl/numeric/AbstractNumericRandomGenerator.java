package prediction.defenition.valueGenerator.random.impl.numeric;

import prediction.defenition.valueGenerator.random.api.AbstractRandomValueGenerator;

public abstract class AbstractNumericRandomGenerator<T> extends AbstractRandomValueGenerator<T> {

    protected final T from;
    protected final T to;

    protected AbstractNumericRandomGenerator(T from, T to) {
        this.from = from;
        this.to = to;
    }

    public T getTFrom() {
        return from;
    }

    public T getTTo() {
        return to;
    }
}
