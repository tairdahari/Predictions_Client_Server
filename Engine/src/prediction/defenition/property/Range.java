package prediction.defenition.property;

import java.io.Serializable;

public class Range implements Serializable {
    private final Double from;
    private final Double to;
    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }
    public Double getFrom() {
        return from;
    }
    public Double getTo() {
        return to;
    }
}
