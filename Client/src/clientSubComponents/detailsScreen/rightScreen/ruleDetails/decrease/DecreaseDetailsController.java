package clientSubComponents.detailsScreen.rightScreen.ruleDetails.decrease;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import utils.DTOActions.DTODecreaseAction;
import utils.DTOActions.DTOIncreaseAction;

public class DecreaseDetailsController {
    @FXML private Label by;

    @FXML private Label mainEntityName;

    @FXML private Label property;

    @FXML private Label secondEntityName;

    public void SetData(DTODecreaseAction decrease) {
        by.setText(decrease.getBy());
        property.setText(decrease.getProperty());
        mainEntityName.setText(decrease.getMainEntityName());

        if(decrease.getSecondaryEntityName() == null) {
            secondEntityName.setText("None");
        }
        else {
            secondEntityName.setText(decrease.getSecondaryEntityName());
        }
    }
}
