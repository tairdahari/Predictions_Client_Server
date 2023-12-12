package subComponents.detailsScreen.rightScreen.ruleDetails.condition.single;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import utils.DTOActions.DTOSingleConditionAction;
public class SingleConditionDetailsController {

    @FXML private Label mainEntityName;
    @FXML private Label secondEntityName;
    @FXML private Label property;
    @FXML private Label operator;
    @FXML private Label value;
    @FXML private Label thisCount;
    @FXML private Label elseCount;



    public void SetData(DTOSingleConditionAction singleCondition) {
        elseCount.setText(singleCondition.getElseActionAmount().toString());
        thisCount.setText(singleCondition.getThenActionAmount().toString());
        value.setText(singleCondition.getValue());
        operator.setText(singleCondition.getOperator());
        property.setText(singleCondition.getProperty());
        mainEntityName.setText(singleCondition.getMainEntityName());

        if(singleCondition.getSecondaryEntityName() == null) {
            secondEntityName.setText("None");
        }
        else {
            secondEntityName.setText(singleCondition.getSecondaryEntityName());
        }
    }
}
