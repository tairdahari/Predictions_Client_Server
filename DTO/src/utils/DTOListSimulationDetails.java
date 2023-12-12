package utils;

import prediction.execution.simulationExecutionDetails.ISimulationDetails;

import java.util.ArrayList;
import java.util.List;

public class DTOListSimulationDetails {
    private final List<DTOSimulationDetails> dtoSimulationDetailsList;

    public DTOListSimulationDetails(List<ISimulationDetails> allSimulations) {
        this.dtoSimulationDetailsList = new ArrayList<>();
        for(ISimulationDetails simulationDetails : allSimulations) {
            DTOSimulationDetails dtoSimulationDetails = new DTOSimulationDetails(simulationDetails, simulationDetails.getContext().getInProgress());
            this.dtoSimulationDetailsList.add(dtoSimulationDetails);
        }
    }

    public List<DTOSimulationDetails> getDtoSimulationDetailsList() {
        return dtoSimulationDetailsList;
    }

}
