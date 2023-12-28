package utils;

import java.util.Map;

public class DTOEntityQuantities {

    private Map<Integer, Integer> dtoEntityQuantities;

    public DTOEntityQuantities(Map<Integer, Integer> entityQuantities) {
        this.dtoEntityQuantities = entityQuantities;
    }

    public Map<Integer, Integer> getDtoEntityQuantities() {
        return dtoEntityQuantities;
    }
}
