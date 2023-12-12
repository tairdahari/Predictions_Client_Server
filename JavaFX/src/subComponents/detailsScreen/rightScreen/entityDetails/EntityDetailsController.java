//package subComponents.detailsScreen.rightScreen.entityDetails;
//
//import javafx.fxml.FXML;
//import javafx.scene.layout.GridPane;
//import prediction.engineManager.IEngineManager;
//import subComponents.detailsScreen.app.DetailsScreenController;
//import subComponents.detailsScreen.leftScreen.DetailsController;
//import subComponents.mainScreen.body.BodyController;
//import utils.DTOEntityDefinition;
//import utils.DTOPropertyDefinition;
//
//import javafx.scene.control.Label;
//public class EntityDetailsController implements DetailsController {
//
//    @FXML
//    private Label entityNameComponent;
//    @FXML
//    private Label propertiesComponent;
//    @FXML
//    private Label entityNameShowComponent;
//    @FXML
//    private Label propertiesShowComponent;
//
//    @FXML
//    public void initialize() {
//        entityNameShowComponent.setText("");
//        propertiesShowComponent.setText("");
//    }
//    @Override
//    public void updateDetails(Object entityDefinition) {
//        if(entityDefinition instanceof DTOEntityDefinition) {
//            entityNameShowComponent.setText(((DTOEntityDefinition) entityDefinition).getEntityName());
//
//            StringBuilder propertyDetails = new StringBuilder();
//
//            for (DTOPropertyDefinition property: ((DTOEntityDefinition) entityDefinition).getEntityProperties()) {
//                propertyDetails.append(property.getName()).append(": ").append(property.getValue()).append("\n");
//            }
//
//            propertiesShowComponent.setText(propertyDetails.toString());
//
//            entityNameComponent.setVisible(true);
//            propertiesComponent.setVisible(true);
//            entityNameShowComponent.setVisible(true);
//            propertiesShowComponent.setVisible(true);
//        }
//    }
//}
