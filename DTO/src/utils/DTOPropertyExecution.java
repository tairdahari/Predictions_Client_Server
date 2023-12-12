package utils;

public class DTOPropertyExecution {
    private final String propertyInstanceName;
    private final String value;
//    private String averageConsistency;

    public DTOPropertyExecution(String propertyInstanceName, String value) {
        this.propertyInstanceName = propertyInstanceName;
        this.value = value;
    }

    public String getPropertyInstanceName() {
        return propertyInstanceName;
    }

    public String getValue() {
        return value;
    }

//    public String getAverageConsistency() {
//        return averageConsistency;
//    }
//
//    public void setAverageConsistency(Double averageConsistency) {
//        this.averageConsistency = averageConsistency.toString();
//    }

}
