package utils.DTOActions;

import utils.DTOActionDetails;

public class DTOIncreaseAction extends DTOActionDetails {

    private String property;
    private String by;


    public DTOIncreaseAction(String name, String mainEntityName, String property, String by) {
        super(name, mainEntityName, null);
        this.property = property;
        this.by = by;
    }

    public String getProperty() {
        return property;
    }

    public String getBy() {
        return by;
    }
}
