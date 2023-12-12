package clientSubComponents.detailsScreen.rightScreen.ruleDetails.actionDetails;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import utils.DTOActionDetails;


public class ActionDetailsController {
    @FXML
    private Label actionTypeComponent;

    @FXML private Label actionTypeShowComponent;

    @FXML private Label actionPrimaryEntityNameComponent;

    @FXML private Label actionPrimaryEntityNameShowComponent;

    @FXML private Label actionSecondaryEntityNameComponent;

    @FXML private Label actionSecondaryEntityNameShowComponent;

    @FXML
    public void initialize() {
        actionTypeShowComponent.setText("");
        actionPrimaryEntityNameShowComponent.setText("");
        actionSecondaryEntityNameShowComponent.setText("");
    }


    public void updateDetails(Object actionDefinition) {
        if(actionDefinition instanceof DTOActionDetails) {
            actionTypeShowComponent.setText(((DTOActionDetails)actionDefinition).getActionName());
            actionPrimaryEntityNameShowComponent.setText(((DTOActionDetails)actionDefinition).getMainEntityName());
            actionSecondaryEntityNameShowComponent.setText(((DTOActionDetails) actionDefinition).getSecondaryEntityName());

            actionTypeComponent.setVisible(true);
            actionTypeShowComponent.setVisible(true);
            actionPrimaryEntityNameComponent.setVisible(true);
            actionPrimaryEntityNameShowComponent.setVisible(true);
            actionSecondaryEntityNameComponent.setVisible(true);
            actionSecondaryEntityNameShowComponent.setVisible(true);
        }
    }
}