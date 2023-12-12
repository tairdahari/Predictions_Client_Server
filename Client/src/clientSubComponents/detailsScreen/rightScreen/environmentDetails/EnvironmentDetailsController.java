package clientSubComponents.detailsScreen.rightScreen.environmentDetails;//package subComponents.detailsScreen.rightScreen.environmentDetails;

import clientSubComponents.detailsScreen.app.DetailsScreenController;
import clientSubComponents.detailsScreen.leftScreen.DetailsController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import utils.DTOEntityDefinition;
import utils.DTOPropertyDefinition;

public class EnvironmentDetailsController implements DetailsController {
    private DetailsScreenController mainDetailsScreenController;
   // private IEngineManager engineManager;
    @FXML
    private Label environmentTypeComponent;
    @FXML
    private Label environmentTypeShowComponent;
    @FXML
    private Label environmentNameComponent;
    @FXML
    private Label environmentNameShowComponent;
    @FXML
    private Label environmentRangeComponent;
    @FXML
    private Label environmentRangeShowComponent;

    @FXML
    public void initialize() {
        environmentTypeShowComponent.setText("");
        environmentNameShowComponent.setText("");
        environmentRangeShowComponent.setText("");
    }

    @Override
    public void updateDetails(Object environmentDefinition) {
        if (environmentDefinition instanceof DTOPropertyDefinition) {
            environmentTypeShowComponent.setText(((DTOPropertyDefinition) environmentDefinition).getType());
            environmentNameShowComponent.setText(((DTOPropertyDefinition) environmentDefinition).getName());

            String fromValue = ((DTOPropertyDefinition) environmentDefinition).getFrom();

            try {
                if(fromValue != null) {
                    Float.parseFloat(fromValue);
                    environmentRangeShowComponent.setText(fromValue + " - " + ((DTOPropertyDefinition) environmentDefinition).getTo());
                    environmentRangeComponent.setVisible(true);
                    environmentRangeShowComponent.setVisible(true);
                }
                else
                    environmentRangeComponent.setVisible(false);
            } catch (NumberFormatException e) {
                environmentRangeComponent.setVisible(false);
                environmentRangeShowComponent.setVisible(false);
            }

            environmentTypeComponent.setVisible(true);
            environmentTypeShowComponent.setVisible(true);
            environmentNameComponent.setVisible(true);
            environmentNameShowComponent.setVisible(true);
        }
    }

}
