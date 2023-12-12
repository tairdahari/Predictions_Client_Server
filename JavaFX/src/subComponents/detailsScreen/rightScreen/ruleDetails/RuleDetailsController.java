package subComponents.detailsScreen.rightScreen.ruleDetails;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import subComponents.detailsScreen.leftScreen.DetailsController;
import subComponents.detailsScreen.rightScreen.ruleDetails.calculation.CalculationDetailsController;
import subComponents.detailsScreen.rightScreen.ruleDetails.condition.multiple.MultipleConditionDetailsController;
import subComponents.detailsScreen.rightScreen.ruleDetails.condition.single.SingleConditionDetailsController;
import subComponents.detailsScreen.rightScreen.ruleDetails.decrease.DecreaseDetailsController;
import subComponents.detailsScreen.rightScreen.ruleDetails.increase.IncreaseDetailsController;
import subComponents.detailsScreen.rightScreen.ruleDetails.kill.KillDetailsController;
import subComponents.detailsScreen.rightScreen.ruleDetails.proximity.ProximityDetailsController;
import subComponents.detailsScreen.rightScreen.ruleDetails.replace.ReplaceDetailsController;
import subComponents.detailsScreen.rightScreen.ruleDetails.set.SetDetailsController;
import utils.DTOActionDetails;
import utils.DTOActions.*;
import utils.DTOEntityDefinition;
import utils.DTORuleDefinition;
import java.io.IOException;
import java.util.EventListener;
import java.util.List;


public class RuleDetailsController implements DetailsController {

    @FXML private Pane actionDetails;
    @FXML private Label numberOfActions;
    @FXML private Label probability;
    @FXML private Label ruleName;
    @FXML private Label ticks;
    @FXML private TreeView<String> actionTreeView = new TreeView<>();

    private List<DTOActionDetails> actionDTOList;

    @Override
    public void updateDetails(Object ruleDefinition) throws IOException {
        if(ruleDefinition instanceof DTORuleDefinition) {
            numberOfActions.setText(((DTORuleDefinition) ruleDefinition).getNumOfActions().toString());
            probability.setText(((DTORuleDefinition) ruleDefinition).getProbability().toString());
            ruleName.setText(((DTORuleDefinition) ruleDefinition).getName());
            ticks.setText(((DTORuleDefinition) ruleDefinition).getTicks().toString());
            setActionTreeView(((DTORuleDefinition) ruleDefinition).getListDetailsAction());
        }

    }

    private void setActionTreeView(List<DTOActionDetails> actionTypes) {
        TreeItem<String> root = new TreeItem<>("Select Action");
        for (DTOActionDetails actionType : actionTypes) {
            root.getChildren().add(new TreeItem<>(actionType.getActionName()));
        }
        actionTreeView.setRoot(root);
        actionDTOList = actionTypes;
    }

    @FXML
    private void SelectActionClicked(ActionEvent event) {
        TreeItem<String> selectedItem = actionTreeView.getSelectionModel().getSelectedItem();
        int i = 0;
        for(TreeItem<String> treeItem : actionTreeView.getRoot().getChildren()) {
            if(treeItem.equals(selectedItem)) {
                break;
            }
            i++;
        }
        DTOActionDetails actionDTO = actionDTOList.get(i);
        setActionDetailsPain(actionDTO);
    }

    private void setActionDetailsPain(DTOActionDetails action) {
        switch (action.getActionName()) {
            case "INCREASE":
                createIncreaseAction((DTOIncreaseAction)action);
                break;
            case "DECREASE":
                createDecreaseAction((DTODecreaseAction)action);
                break;
            case "CALCULATION":
                createCalculationAction((DTOCalculationAction) action);
                break;
            case "CONDITION":
                createConditionAction(action);
                break;
            case "KILL":
                createKillAction((DTOKillAction)action);
                break;
            case "SET":
                createSetAction((DTOSetAction)action);
                break;
            case "PROXIMITY":
                createProximityAction((DTOProximityAction)action);
                break;
            case "REPLACE":
                createReplaceAction((DTOReplaceAction)action);
                break;
        }
    }

    private void createConditionAction(DTOActionDetails action) {
        if(action instanceof DTOMultipleConditionAction) {
            createMulConAction((DTOMultipleConditionAction)action);
        }else {
            createSinConAction((DTOSingleConditionAction)action);
        }
    }

    private void createIncreaseAction(DTOIncreaseAction action) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/subComponents/detailsScreen/rightScreen/ruleDetails/increase/IncreaseData.fxml"));
            Parent increaseContent = loader.load();
            IncreaseDetailsController increaseDataController = loader.getController();

            increaseDataController.SetData(action);
            actionDetails.getChildren().clear();
            actionDetails.getChildren().add(increaseContent);

        } catch (IOException e) {
        }
    }

    private void createDecreaseAction(DTODecreaseAction action) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/subComponents/detailsScreen/rightScreen/ruleDetails/decrease/DecreaseData.fxml"));
            Parent decreaseContent = loader.load();
            DecreaseDetailsController decreaseDataController = loader.getController();

            decreaseDataController.SetData(action);
            actionDetails.getChildren().clear();
            actionDetails.getChildren().add(decreaseContent);

        } catch (IOException e) {
        }
    }

    private void createCalculationAction(DTOCalculationAction action) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/subComponents/detailsScreen/rightScreen/ruleDetails/calculation/CalculationData.fxml"));
            Parent calculationContent = loader.load();
            CalculationDetailsController calculationDataController = loader.getController();

            calculationDataController.SetData(action);
            actionDetails.getChildren().clear();
            actionDetails.getChildren().add(calculationContent);

        } catch (IOException e) {
        }
    }

    private void createMulConAction(DTOMultipleConditionAction action) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/subComponents/detailsScreen/rightScreen/ruleDetails/condition/multiple/MultipleConditionData.fxml"));
            Parent multipleConditionContent = loader.load();
            MultipleConditionDetailsController multipleConditionDataController = loader.getController();

            multipleConditionDataController.SetData(action);
            actionDetails.getChildren().clear();
            actionDetails.getChildren().add(multipleConditionContent);

        } catch (IOException e) {
        }
    }

    private void createSinConAction(DTOSingleConditionAction action) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/subComponents/detailsScreen/rightScreen/ruleDetails/condition/single/SingleConditionData.fxml"));
            Parent singleConditionContent = loader.load();
            SingleConditionDetailsController singleConditionDataController = loader.getController();

            singleConditionDataController.SetData(action);
            actionDetails.getChildren().clear();
            actionDetails.getChildren().add(singleConditionContent);

        } catch (IOException e) {
        }
    }

    private void createKillAction(DTOKillAction action) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/subComponents/detailsScreen/rightScreen/ruleDetails/kill/KillData.fxml"));
            Parent killContent = loader.load();
            KillDetailsController killDataController = loader.getController();

            killDataController.SetData(action);
            actionDetails.getChildren().clear();
            actionDetails.getChildren().add(killContent);

        } catch (IOException e) {
        }
    }

    private void createSetAction(DTOSetAction action) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/subComponents/detailsScreen/rightScreen/ruleDetails/set/SetData.fxml"));
            Parent setContent = loader.load();
            SetDetailsController setDataController = loader.getController();

            setDataController.SetData(action);
            actionDetails.getChildren().clear();
            actionDetails.getChildren().add(setContent);

        } catch (IOException e) {
        }
    }

    private void createProximityAction(DTOProximityAction action) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/subComponents/detailsScreen/rightScreen/ruleDetails/proximity/ProximityData.fxml"));
            Parent proximityContent = loader.load();
            ProximityDetailsController proximityDataController = loader.getController();

            proximityDataController.SetData(action);
            actionDetails.getChildren().clear();
            actionDetails.getChildren().add(proximityContent);

        } catch (IOException e) {
        }
    }

    private void createReplaceAction(DTOReplaceAction action) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/subComponents/detailsScreen/rightScreen/ruleDetails/replace/ReplaceData.fxml"));
            Parent replaceContent = loader.load();
            ReplaceDetailsController replaceDataController = loader.getController();

            replaceDataController.SetData(action);
            actionDetails.getChildren().clear();
            actionDetails.getChildren().add(replaceContent);

        } catch (IOException e) {
        }
    }


}