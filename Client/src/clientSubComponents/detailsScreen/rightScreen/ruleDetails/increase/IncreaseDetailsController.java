package clientSubComponents.detailsScreen.rightScreen.ruleDetails.increase;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import utils.DTOActions.DTOIncreaseAction;
import utils.DTORuleDefinition;

public class IncreaseDetailsController {
    @FXML private Label by;

    @FXML private Label mainEntityName;

    @FXML private Label property;

    @FXML private Label secondEntityName;

    public void SetData(DTOIncreaseAction increase) {
        by.setText(increase.getBy());
        property.setText(increase.getProperty());
        mainEntityName.setText(increase.getMainEntityName());

        if(increase.getSecondaryEntityName() == null) {
            secondEntityName.setText("None");
        }
        else {
            secondEntityName.setText(increase.getSecondaryEntityName());
        }
    }

}
