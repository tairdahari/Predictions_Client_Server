package prediction.Termination.manager;

import prediction.Termination.BySeconds;
import prediction.Termination.ByTicks;
import prediction.Termination.ByUser;
import xmlJavaFXReader.schema.generated.PRDBySecond;
import xmlJavaFXReader.schema.generated.PRDByTicks;
import xmlJavaFXReader.schema.generated.PRDTermination;

import java.util.ArrayList;
import java.util.List;

public class TerminationManagerDefinitionImpl implements ITerminationManagerDefinition {
    private final List<Object> simulationTerminationConditions;

    public TerminationManagerDefinitionImpl(PRDTermination prdTermination) {
        this.simulationTerminationConditions = new ArrayList<>();
        if(prdTermination.getPRDByUser() != null)
            simulationTerminationConditions.add(new ByUser(0));
        for (Object condition : prdTermination.getPRDBySecondOrPRDByTicks()) {
            if (condition instanceof PRDByTicks) {
                PRDByTicks prdByTicks = (PRDByTicks) condition;
                simulationTerminationConditions.add(new ByTicks(prdByTicks.getCount()));
            } else if (condition instanceof PRDBySecond) {
                PRDBySecond prdBySecond = (PRDBySecond) condition;
                simulationTerminationConditions.add(new BySeconds(prdBySecond.getCount()));
            }
        }
    }

    @Override
    public List<Object> getSimulationTerminationConditions() {
        return simulationTerminationConditions;
    }
}