package clientSubComponents.detailsScreen.rightScreen.ruleDetails.set;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import utils.DTOActions.DTOSetAction;

public class SetDetailsController {


    @FXML private Label mainEntityName;
    @FXML private Label secondEntityName;
    @FXML private Label property;
    @FXML private Label value;

    public void SetData(DTOSetAction set) {
        value.setText(set.getValue());
        property.setText(set.getProperty());
        mainEntityName.setText(set.getMainEntityName());

        if(set.getSecondaryEntityName() == null) {
            secondEntityName.setText("None");
        }
        else {
            secondEntityName.setText(set.getSecondaryEntityName());
        }
    }
}
