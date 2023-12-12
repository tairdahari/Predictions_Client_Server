package clientSubComponents.detailsScreen.rightScreen.ruleDetails.kill;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import utils.DTOActions.DTOKillAction;

public class KillDetailsController {

    @FXML private Label mainEntityName;
    @FXML private Label secondEntityName;

    public void SetData(DTOKillAction kill) {
        mainEntityName.setText(kill.getMainEntityName());

        if(kill.getSecondaryEntityName() == null) {
            secondEntityName.setText("None");
        }
        else {
            secondEntityName.setText(kill.getSecondaryEntityName());
        }
    }

}
