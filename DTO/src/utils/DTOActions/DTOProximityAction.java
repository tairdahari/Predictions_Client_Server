package utils.DTOActions;

import utils.DTOActionDetails;

public class DTOProximityAction extends DTOActionDetails {
    private String sourceEntity;
    private String targetEntity;
    private String depth;
    private String numOfActions;


    public DTOProximityAction(String name, String mainEntityName, String secondaryEntityName, String depth, Integer numOfActions) {
        super(name, mainEntityName, null);
        this.sourceEntity = mainEntityName;
        this.targetEntity = secondaryEntityName;
        this.depth = depth;
        this.numOfActions = numOfActions.toString();
    }

    public String getSourceEntity() {
        return sourceEntity;
    }

    public String getTargetEntity() {
        return targetEntity;
    }

    public String getDepth() {
        return depth;
    }

    public String getNumOfActions() {
        return numOfActions;
    }
}
