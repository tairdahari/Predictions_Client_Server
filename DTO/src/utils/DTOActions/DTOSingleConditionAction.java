package utils.DTOActions;

import utils.DTOActionDetails;

public class DTOSingleConditionAction extends DTOActionDetails {
    private String numOfActions;
    private final Integer thenActionAmount;
    private final Integer elseActionAmount;
    private final String property;
    private final String value;
    private final String operator;



    public DTOSingleConditionAction(String name, String mainEntityName, String secondaryEntityName, String property,
                              String value, String operator, Integer thenActionAmount, Integer elseActionAmount) {
        super(name, mainEntityName, secondaryEntityName);
        this.property = property;
        this.value = value;
        this.operator = operator;
        this.thenActionAmount = thenActionAmount;
        this.elseActionAmount = elseActionAmount;
    }

    public String getProperty() {
        return property;
    }

    public String getValue() {
        return value;
    }

    public String getOperator() {
        return operator;
    }

    public Integer getThenActionAmount() {
        return thenActionAmount;
    }

    public Integer getElseActionAmount() {
        return elseActionAmount;
    }
}
