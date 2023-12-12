package utils;

import prediction.defenition.entity.IEntityDefinition;
import prediction.execution.instance.entity.IEntityInstance;
import prediction.execution.runner.eSimulationState;
import prediction.execution.simulationExecutionDetails.ISimulationDetails;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DTOSimulationDetails {
    private final String startTime;
    private final String id;
    private eSimulationState inProgress;
    private String endTime = null;
    private final String currTick;
    private final String currTime;
//    private String averageConsistency;

    private final List<DTOEntityExecution> dtoEntityListExecution;


    public DTOSimulationDetails(ISimulationDetails simulationDetails, eSimulationState inProgress) {
        this.inProgress = setProgress(inProgress);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm:ss");
        this.startTime = simulationDetails.getStartSimulationDateTime().format(formatter);
        if(simulationDetails.getEndSimulationDateTime() != null) {
            this.endTime = simulationDetails.getEndSimulationDateTime().format(formatter);
        }
        this.id = simulationDetails.getId().toString();
        this.currTime = simulationDetails.getContext().getCurrTime().toString();
        this.currTick = simulationDetails.getContext().getCurrTick().toString();

        Map<String, Integer> entityMap = new HashMap<>();

        List<IEntityInstance> entityList = simulationDetails.getContext().getEntityInstanceManager().getInstances();

        for (Map.Entry<String, IEntityDefinition> entity : simulationDetails.getContext().getWorld().getEntityManager().getEntities().entrySet()) {
            entityMap.put(entity.getKey(),0);
        }
        for (IEntityInstance entity : entityList) {
            entityMap.put(entity.getEntityDefinition().getEntityName(), entityMap.getOrDefault(entity.getEntityDefinition().getEntityName(), 0) + 1);
        }

        this.dtoEntityListExecution = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : entityMap.entrySet()) {
            String entityName = entry.getKey();
            Integer quantity = entry.getValue();
            DTOEntityExecution dtoEntityExecution = new DTOEntityExecution(entityName, quantity.toString(), null);
            dtoEntityListExecution.add(dtoEntityExecution);
        }
    }

    private eSimulationState setProgress(eSimulationState inProgress) {
        return this.inProgress= inProgress;
    }

    public String getId() {
        return id;
    }

    public String getCurrTick() {
        return currTick;
    }

    public List<DTOEntityExecution> getDtoEntityListExecution() {
        return dtoEntityListExecution;
    }

    public String getCurrTime() {
        return currTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public eSimulationState getInProgress() {
        return inProgress;
    }

    public String getEndTime() {
        return endTime;
    }

//    public String getAverageConsistency() {
//        return averageConsistency;
//    }
//
//    public void setAverageConsistency(Double averageConsistency) {
//        this.averageConsistency = averageConsistency.toString();
//    }

}