package utils;

import java.util.Map;

public class DTOAllFiles {
    private Map<String, DTOSimulationDefinition> allFiles;

    public DTOAllFiles(Map<String, DTOSimulationDefinition> allFiles) {
        this.allFiles = allFiles;
    }

    public Map<String, DTOSimulationDefinition> getAllFiles() {
        return allFiles;
    }
}
