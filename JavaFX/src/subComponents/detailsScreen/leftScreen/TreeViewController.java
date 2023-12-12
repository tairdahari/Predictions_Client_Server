//package subComponents.detailsScreen.leftScreen;
//
//import javafx.beans.property.SimpleObjectProperty;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.control.TreeItem;
//import javafx.scene.control.TreeView;
//import javafx.scene.layout.AnchorPane;
//import prediction.engineManager.EngineManager;
//import prediction.engineManager.IEngineManager;
//import subComponents.detailsScreen.app.DetailsScreenController;
//import subComponents.detailsScreen.rightScreen.entityDetails.EntityDetailsController;
//import subComponents.detailsScreen.rightScreen.environmentDetails.EnvironmentDetailsController;
//import subComponents.detailsScreen.rightScreen.gridDetails.GridDetailsController;
//import subComponents.detailsScreen.rightScreen.ruleDetails.RuleDetailsController;
//import subComponents.detailsScreen.rightScreen.terminationDetails.TerminationDetailsController;
//import utils.*;
//
//import java.io.IOException;
//import java.util.List;
//
//public class TreeViewController {
//    @FXML
//    private TreeView<String> treeViewForDetailsScreenComponent;
//    public DetailsScreenController detailsScreenComponentController;
//    private IEngineManager engineManager  = new EngineManager();
//    private SimpleObjectProperty<TreeItem<String>> selectedItem;
//    public TreeViewController() {
//        this.selectedItem = new SimpleObjectProperty<>();
//    }
//
//    private void clearTreeView() {
//        treeViewForDetailsScreenComponent.getRoot().getChildren().clear();
//        treeViewForDetailsScreenComponent.setRoot(null);
//    }
//    public void initialTreeView() {
//        TreeItem<String> rootItem = new TreeItem<>("world");
//        this.treeViewForDetailsScreenComponent.setRoot(rootItem);
//        DTOSimulationDefinition dtoSimulationDefinition = engineManager.getSimulationDefinition();
//        insertDataToTreeView(rootItem, dtoSimulationDefinition);
//        //rootItem.setExpanded(true);
//    }
//    public void setSystemEngine(IEngineManager engineManager) {
//        this.engineManager = engineManager;
//    }
//    public void setMainController(DetailsScreenController detailsScreenController) {
//        this.detailsScreenComponentController = detailsScreenController;
//    }
//    private void insertDataToTreeView(TreeItem<String> rootItem, DTOSimulationDefinition dtoSimulationDefinition) {
//        List<String> worldBranches = this.engineManager.getWorld().getWorldProperties();
//
//        for (String property : worldBranches) {
//            TreeItem<String> propertyBranch = new TreeItem<>(property);
//            rootItem.getChildren().add(propertyBranch);
//
//            if ("Entities".equals(property)) {
//                List<DTOEntityDefinition> entities = dtoSimulationDefinition.getDtoEntityDefinition();
//                for (DTOEntityDefinition entityDefinition : entities) {
//                    TreeItem<String> entityNode = new TreeItem<>(entityDefinition.getEntityName());
//                    propertyBranch.getChildren().add(entityNode);
//                }
//            } else if ("Environments".equals(property)) {
//                List<DTOPropertyDefinition> environmentVariables = dtoSimulationDefinition.getDtoEnvironmentVariables();
//                for (DTOPropertyDefinition envDefinition : environmentVariables) {
//                    TreeItem<String> envNode = new TreeItem<>(envDefinition.getName());
//                    propertyBranch.getChildren().add(envNode);
//                }
//            } else if ("Rules".equals(property)) {
//                List<DTORuleDefinition> rules = dtoSimulationDefinition.getDtoRulesDefinition();
//                for (DTORuleDefinition ruleDefinition : rules) {
//                    TreeItem<String> ruleNode = new TreeItem<>(ruleDefinition.getName());
//                    propertyBranch.getChildren().add(ruleNode);
//
////                    ruleNode.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
////                        showDetails(item);
////                    });
//                }
//            } else if ("Termination".equals(property)) {
//                List<DTOTerminationDefinition> terminations = dtoSimulationDefinition.getDtoTerminationDefinition();
//                for (DTOTerminationDefinition terminationDefinition : terminations) {
//                    TreeItem<String> terminationNode = new TreeItem<>(terminationDefinition.getTerminationName());
//                    propertyBranch.getChildren().add(terminationNode);
//
////                    terminationNode.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
////                        showDetails(item);
////                    });
//                }
//            }
//
//        }
//        //rootItem.setExpanded(true);
//    }
//    public void selectedItem() throws IOException {
//        detailsScreenComponentController.getvBoxComponent().getChildren().clear();
//
//        TreeItem<String> item = treeViewForDetailsScreenComponent.getSelectionModel().getSelectedItem();
//
//        if (item != null) {
//            switch (item.getParent().getValue()) {
//                case "Entities":
//                    loadAndDisplayDetails(item, "Entities", "/subComponents/detailsScreen/rightScreen/entityDetails/entityDetails.fxml");
//                    break;
//                case "Environments":
//                    loadAndDisplayDetails(item, "Environments", "/subComponents/detailsScreen/rightScreen/environmentDetails/environmentDetails.fxml");
//                    break;
//                case "Rules":
//                    loadAndDisplayDetails(item, "Rules", "/subComponents/detailsScreen/rightScreen/ruleDetails/RulesDetails.fxml");
//                    break;
//                case "Termination":
//                    loadAndDisplayDetails(item, "Termination", "/subComponents/detailsScreen/rightScreen/terminationDetails/terminationDetails.fxml");
//                default:
//                    break;
//            }
//
//            if(item.getValue().equals("Grid")) {
//                loadAndDisplayDetails(item, "Grid", "/subComponents/detailsScreen/rightScreen/gridDetails/gridDetails.fxml");
//            }
//        }
//    }
//
//
//    private void loadAndDisplayDetails(TreeItem<String> selectedItem, String type, String fxmlPath) throws IOException {
//       // if (selectedItem != null && selectedItem.getParent().getValue().equals(type)) {
//            Object selectedDefinition = engineManager. getCurrentDtoDefinition(type, selectedItem.getValue());
//
//            if (selectedDefinition != null) {
//                displayDetails(selectedDefinition, fxmlPath);
//            }
//    }
//    private void displayDetails(Object selectedDefinition, String fxmlPath) {
//        try {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
//            ScrollPane detailsScrollPane = fxmlLoader.load();
//
//            AnchorPane detailsPane = (AnchorPane) detailsScrollPane.getContent();
//            if(fxmlLoader.getController() instanceof EntityDetailsController) {
//                ((EntityDetailsController) fxmlLoader.getController()).updateDetails(selectedDefinition);
//            } else if (fxmlLoader.getController() instanceof EnvironmentDetailsController) {
//                ((EnvironmentDetailsController) fxmlLoader.getController()).updateDetails(selectedDefinition);
//            } else if (fxmlLoader.getController() instanceof RuleDetailsController) {
//                ((RuleDetailsController) fxmlLoader.getController()).updateDetails(selectedDefinition);
//            } else if (fxmlLoader.getController() instanceof GridDetailsController) {
//                ((GridDetailsController) fxmlLoader.getController()).updateDetails(selectedDefinition);
//            } else if (fxmlLoader.getController() instanceof TerminationDetailsController) {
//                ((TerminationDetailsController) fxmlLoader.getController()).updateDetails(selectedDefinition);
//            }
//
//            detailsScreenComponentController.getvBoxComponent().getChildren().clear();
//            detailsScreenComponentController.getvBoxComponent().getChildren().add(detailsPane);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
