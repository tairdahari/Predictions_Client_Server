package utils.DTOActions;

import utils.DTOActionDetails;

public class DTOConditionAction extends DTOActionDetails {
    private String numOfActions;
    private final Integer thenActionAmount;
    private final Integer elseActionAmount;


    public DTOConditionAction(String name, String mainEntityName, String secondaryEntityName, Integer thenActionAmount, Integer elseActionAmount) {
        super(name, mainEntityName, secondaryEntityName);
        this.elseActionAmount = elseActionAmount;
        this.thenActionAmount = thenActionAmount;
    }

    public Integer getElseActionAmount() {
        return elseActionAmount;
    }

    public Integer getThenActionAmount() {
        return thenActionAmount;
    }
}
