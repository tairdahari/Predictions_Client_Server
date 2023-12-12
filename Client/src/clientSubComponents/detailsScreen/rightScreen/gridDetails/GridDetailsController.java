package clientSubComponents.detailsScreen.rightScreen.gridDetails;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import clientSubComponents.detailsScreen.leftScreen.DetailsController;
import utils.DTOGridDefinition;


public class GridDetailsController implements DetailsController {
    @FXML private Label rowsComponent;
    @FXML private Label colsComponent;
    @FXML private Label rowsShowComponent;
    @FXML private Label colsShowComponent;

    @FXML
    public void initialize() {
        rowsShowComponent.setText("");
        colsShowComponent.setText("");
    }


    @Override
    public void updateDetails(Object gridDefinition) {
        if(gridDefinition instanceof DTOGridDefinition) {
            rowsShowComponent.setText(((DTOGridDefinition) gridDefinition).getRows());
            colsShowComponent.setText(((DTOGridDefinition) gridDefinition).getCols());

            rowsComponent.setVisible(true);
            rowsShowComponent.setVisible(true);
            colsComponent.setVisible(true);
            colsShowComponent.setVisible(true);
        }
    }
}
