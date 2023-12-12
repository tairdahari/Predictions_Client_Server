package prediction.Termination;

import java.io.Serializable;

public interface ITermination extends Serializable {
    int getCount();
    eTerminationType getTerminationName();
}