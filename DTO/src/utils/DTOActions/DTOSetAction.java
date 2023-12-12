package utils.DTOActions;

import utils.DTOActionDetails;

public class DTOSetAction extends DTOActionDetails {
    private String value;
    private String property;

    public DTOSetAction(String name, String mainEntityName, String value, String property) {
        super(name, mainEntityName, null);
        this.value = value;
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public String getProperty() {
        return property;
    }
}
