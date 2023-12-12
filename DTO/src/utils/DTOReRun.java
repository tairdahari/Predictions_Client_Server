package utils;

import java.util.List;

public class DTOReRun {
    private List<Integer> populationList;
    private List<Object> userEnvVarChoices;

    public DTOReRun(List<Integer> populationList, List<Object> userEnvVarChoices) {
        this.populationList = populationList;
        this.userEnvVarChoices = userEnvVarChoices;
    }

    public List<Integer> getPopulationList() {
        return populationList;
    }

    public List<Object> getUserEnvVarChoices() {
        return userEnvVarChoices;
    }
}
