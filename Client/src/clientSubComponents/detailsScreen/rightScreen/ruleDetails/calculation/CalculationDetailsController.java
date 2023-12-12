package clientSubComponents.detailsScreen.rightScreen.ruleDetails.calculation;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import utils.DTOActions.DTOCalculationAction;
import utils.DTOActions.DTODivideAction;
import utils.DTOActions.DTOMultiplyAction;

public class CalculationDetailsController {


    @FXML private Label actionName;
    @FXML private Label mainEntityName;
    @FXML private Label secondEntityName;
    @FXML private Label resultProp;
    @FXML private Label arg1;
    @FXML private Label arg2;

    public void SetData(DTODivideAction calculation) {
        actionName.setText(calculation.getCalcType());
        resultProp.setText(calculation.getResultProp());
        arg1.setText(calculation.getArg1());
        arg2.setText(calculation.getArg2());
        mainEntityName.setText(calculation.getMainEntityName());

        if (calculation.getSecondaryEntityName() == null) {
            secondEntityName.setText("None");
        } else {
            secondEntityName.setText(calculation.getSecondaryEntityName());
        }

    }

    public void SetData(DTOMultiplyAction calculation) {
        actionName.setText(calculation.getCalcType());
        resultProp.setText(calculation.getResultProp());
        arg1.setText(calculation.getArg1());
        arg2.setText(calculation.getArg2());
        mainEntityName.setText(calculation.getMainEntityName());

        if(calculation.getSecondaryEntityName() == null) {
            secondEntityName.setText("None");
        }
        else {
            secondEntityName.setText(calculation.getSecondaryEntityName());
        }
    }
}
