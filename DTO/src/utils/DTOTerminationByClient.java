package utils;

import prediction.Termination.ITermination;

public class DTOTerminationByClient {
    private final String terminationName;
    private final Integer count;

    public DTOTerminationByClient(String terminationName, String count) {
        this.terminationName = terminationName;
        this.count = Integer.valueOf(count);
    }
    public String getTerminationName() {
        return terminationName;
    }

    public Integer getCount() {
        return count;
    }
}
