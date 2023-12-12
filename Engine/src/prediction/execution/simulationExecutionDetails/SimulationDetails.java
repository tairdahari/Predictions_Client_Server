package prediction.execution.simulationExecutionDetails;

import prediction.execution.context.IContext;

import java.time.LocalDateTime;

public class SimulationDetails implements  ISimulationDetails{
    private LocalDateTime startSimulationDateTime;
    private LocalDateTime endSimulationDateTime;
    private final int id;
    private final IContext context;


    public SimulationDetails(LocalDateTime simulationDateTime, int id, IContext context) {
        this.startSimulationDateTime = simulationDateTime;
        this.id = id;
        this.context = context;
    }
    @Override
    public LocalDateTime getStartSimulationDateTime() {
        return startSimulationDateTime;
    }
    @Override
    public Integer getId() {
        return id;
    }
    @Override
    public IContext getContext() {
        return context;
    }

    @Override
    public void setStartSimulationDateTime(LocalDateTime currentDateTime) {
        startSimulationDateTime= currentDateTime;
    }

    @Override
    public void setEndSimulationDateTime(LocalDateTime currentDateTime) {
        endSimulationDateTime= currentDateTime;
    }

    @Override
    public LocalDateTime getEndSimulationDateTime() {
        return endSimulationDateTime;
    }
}
