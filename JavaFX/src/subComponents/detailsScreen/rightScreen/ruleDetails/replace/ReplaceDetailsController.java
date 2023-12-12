package subComponents.detailsScreen.rightScreen.ruleDetails.replace;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import utils.DTOActions.DTOReplaceAction;

public class ReplaceDetailsController {

    @FXML private Label killEntityName;

    @FXML private Label createEntityName;

    @FXML private Label mode;

    public void SetData(DTOReplaceAction replace) {
        mode.setText(replace.getMode());
        createEntityName.setText(replace.getNameOfEntityToCreate());
        killEntityName.setText(replace.getNameOfEntityToKill());
    }
}
