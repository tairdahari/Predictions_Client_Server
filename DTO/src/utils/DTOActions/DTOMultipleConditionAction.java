package utils.DTOActions;

import utils.DTOActionDetails;

public class DTOMultipleConditionAction extends DTOActionDetails {
    private String numOfActions;
    private final Integer thenActionAmount;
    private final Integer elseActionAmount;
    private final String logic;
    private final Integer numOfConditions;


    public DTOMultipleConditionAction(String name, String mainEntityName, String secondaryEntityName, String logic, Integer thenActionAmount, Integer elseActionAmount, Integer numOfConditions) {
        super(name, mainEntityName, secondaryEntityName);
        this.logic = logic;
        this.numOfConditions = numOfConditions;
        this.thenActionAmount = thenActionAmount;
        this.elseActionAmount = elseActionAmount;
    }

    public String getLogic() {
        return logic;
    }

    public Integer getNumOfConditions() {
        return numOfConditions;
    }

    public Integer getThenActionAmount() {
        return thenActionAmount;
    }

    public Integer getElseActionAmount() {
        return elseActionAmount;
    }
}
