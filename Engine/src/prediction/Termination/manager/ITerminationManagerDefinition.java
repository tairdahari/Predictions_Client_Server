package prediction.Termination.manager;

import java.io.Serializable;
import java.util.List;

public interface ITerminationManagerDefinition extends Serializable {
    List<Object> getSimulationTerminationConditions();
}