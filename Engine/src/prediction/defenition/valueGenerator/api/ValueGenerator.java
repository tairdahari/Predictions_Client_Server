package prediction.defenition.valueGenerator.api;

import java.io.Serializable;

public interface ValueGenerator<T> extends Serializable {
    T generateValue();
}