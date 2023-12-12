package utils;

import java.util.Map;

public class DTOPropertyHistogram {

    private Map<String, Integer> values;

    private String averageConsistency;

    public DTOPropertyHistogram(Map<String, Integer> values) {
        this.values = values;
    }

    public Map<String, Integer> getValues() {
        return values;
    }

    public String getAverageConsistency() {
        return averageConsistency;
    }

    public void setAverageConsistency(Double averageConsistency) {
        this.averageConsistency = averageConsistency.toString();
    }
}
