package utils;

import prediction.Termination.ITermination;
public class DTOTerminationDefinition {
    private final String terminationName;
    private final Integer count;

    public DTOTerminationDefinition(ITermination termination) {
        this.terminationName = String.valueOf(termination.getTerminationName());
        this.count = termination.getCount();
    }
    public String getTerminationName() {
        return terminationName;
    }

    public Integer getCount() {
        return count;
    }
}