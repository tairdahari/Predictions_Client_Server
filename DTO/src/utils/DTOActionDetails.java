package utils;

public abstract class DTOActionDetails {
    private final String actionName;
    private final String mainEntityName;
    private String secondaryEntityName;

    public DTOActionDetails(String actionName, String mainEntityName, String secondaryEntityName) {
        this.actionName = actionName;
        this.mainEntityName = mainEntityName;
        if(secondaryEntityName != null)
            this.secondaryEntityName = secondaryEntityName;
    }

    public String getActionName() {
        return actionName;
    }

    public String getMainEntityName() {
        return mainEntityName;
    }

    public String getSecondaryEntityName() {
        return secondaryEntityName;
    }
}
