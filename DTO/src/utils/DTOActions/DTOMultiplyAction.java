package utils.DTOActions;

import utils.DTOActionDetails;

public class DTOMultiplyAction extends DTOActionDetails {
    private final String arg1;
    private final String arg2;
    private final String resultProp;
    private final String calcType;


    public DTOMultiplyAction(String calcType, String name, String mainEntityName, String arg1, String arg2, String resultProp) {
        super(name, mainEntityName, null);
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.resultProp = resultProp;
        this.calcType = calcType;
    }

    public String getArg1() {
        return arg1;
    }

    public String getArg2() {
        return arg2;
    }


    public String getResultProp() {
        return resultProp;
    }

    public String getCalcType() {
        return calcType;
    }
}
