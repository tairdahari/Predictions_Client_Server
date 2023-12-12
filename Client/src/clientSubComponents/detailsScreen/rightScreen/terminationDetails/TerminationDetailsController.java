package clientSubComponents.detailsScreen.rightScreen.terminationDetails;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import clientSubComponents.detailsScreen.leftScreen.DetailsController;
import utils.DTOGridDefinition;
import utils.DTOTerminationDefinition;

public class TerminationDetailsController implements DetailsController {
    @FXML private Label countLabel;
    @FXML private Label countLabelMain;


    @Override
    public void updateDetails(Object terminationDefinition) {
        if(terminationDefinition instanceof DTOTerminationDefinition) {
            if(((DTOTerminationDefinition) terminationDefinition).getTerminationName().equals("BY_USER")) {
                countLabelMain.setVisible(false);
            } else {
                countLabelMain.setVisible(true);
                countLabel.setText(((DTOTerminationDefinition) terminationDefinition).getCount().toString());
            }
        }
    }
}
