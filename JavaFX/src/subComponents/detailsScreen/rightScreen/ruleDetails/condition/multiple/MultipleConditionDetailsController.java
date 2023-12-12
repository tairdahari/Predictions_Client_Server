package subComponents.detailsScreen.rightScreen.ruleDetails.condition.multiple;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import utils.DTOActions.DTOMultipleConditionAction;

public class MultipleConditionDetailsController {
    @FXML private Label mainEntityName;

    @FXML private Label secondEntityName;
    @FXML private Label logical;
    @FXML private Label thisCount;
    @FXML private Label elseCount;

    public void SetData(DTOMultipleConditionAction multipleCondition) {
        elseCount.setText(multipleCondition.getElseActionAmount().toString());
        thisCount.setText(multipleCondition.getThenActionAmount().toString());
        logical.setText(multipleCondition.getLogic());
        mainEntityName.setText(multipleCondition.getMainEntityName());

        if(multipleCondition.getSecondaryEntityName() == null) {
            secondEntityName.setText("None");
        }
        else {
            secondEntityName.setText(multipleCondition.getSecondaryEntityName());
        }
    }

}
