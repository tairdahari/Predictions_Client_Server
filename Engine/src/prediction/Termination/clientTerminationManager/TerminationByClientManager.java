package prediction.Termination.clientTerminationManager;

import prediction.Termination.BySeconds;
import prediction.Termination.ByTicks;
import prediction.Termination.ByUser;
import utils.DTOTerminationByClient;

import java.util.ArrayList;
import java.util.List;

public class TerminationByClientManager implements ITerminationByClientManager{
    private final List<Object> simulationTerminationConditions;
    public TerminationByClientManager(List<DTOTerminationByClient> termination) {
        this.simulationTerminationConditions = new ArrayList<>();
        for (DTOTerminationByClient dtoTerminationByClient : termination) {
            if (dtoTerminationByClient.getTerminationName().equals("byTicks")) {
                simulationTerminationConditions.add(new ByTicks(dtoTerminationByClient.getCount()));
            } else if (dtoTerminationByClient.getTerminationName().equals("bySeconds")) {
                simulationTerminationConditions.add(new BySeconds(dtoTerminationByClient.getCount()));
            } else if (dtoTerminationByClient.getTerminationName().equals("byUser")) {
                simulationTerminationConditions.add(new ByUser(0));
            }
        }
    }

    @Override
    public List<Object> getSimulationTerminationConditions() {
        return simulationTerminationConditions;
    }
}
