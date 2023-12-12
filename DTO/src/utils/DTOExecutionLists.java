package utils;

import java.util.List;

public class DTOExecutionLists {
    private final List<Integer> populationList;
    private final List<Object> envList;

    public DTOExecutionLists(List<Integer> populationList, List<Object> envList) {
        this.populationList = populationList;
        this.envList = envList;
    }

    public List<Integer> getPopulationList() {
        return populationList;
    }

    public List<Object> getEnvList() {
        return envList;
    }
}
