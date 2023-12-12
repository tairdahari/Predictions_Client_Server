package utils.DTOActions;

import utils.DTOActionDetails;

public class DTOReplaceAction extends DTOActionDetails {
    private String nameOfEntityToKill;
    private String nameOfEntityToCreate;
    private String mode;
    private final String secondaryEntityName;


    public DTOReplaceAction(String name, String mainEntityName, String secondaryEntityName, String mode) {
        super(name, mainEntityName, null);
        this.secondaryEntityName = secondaryEntityName;
        this.nameOfEntityToKill = mainEntityName;
        this.nameOfEntityToCreate =secondaryEntityName;
        this.mode = mode;
    }


    public String getNameOfEntityToKill() {
        return nameOfEntityToKill;
    }

    public String getNameOfEntityToCreate() {
        return nameOfEntityToCreate;
    }

    public String getMode() {
        return mode;
    }
}
