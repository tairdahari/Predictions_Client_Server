package subComponents.detailsScreen.rightScreen.ruleDetails.proximity;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import utils.DTOActions.DTOProximityAction;

public class ProximityDetailsController {
    @FXML private Label sourceEntityName;

    @FXML private Label targetEntityName;

    @FXML private Label environmentDepth;

    @FXML private Label actionCount;

    public void SetData(DTOProximityAction proximity) {
        sourceEntityName.setText(proximity.getSourceEntity());
        targetEntityName.setText(proximity.getTargetEntity());
        environmentDepth.setText(proximity.getDepth());
        actionCount.setText(proximity.getNumOfActions());

    }
}
