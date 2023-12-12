package prediction.execution.simulationExecutionDetails;

import prediction.execution.context.IContext;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface ISimulationDetails extends Serializable {

    LocalDateTime getStartSimulationDateTime();
    Integer getId();
    IContext getContext();

    void setStartSimulationDateTime(LocalDateTime currentDateTime);
    void setEndSimulationDateTime(LocalDateTime currentDateTime);
    LocalDateTime getEndSimulationDateTime();

}