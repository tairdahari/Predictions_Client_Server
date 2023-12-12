package clientSubComponents.detailsScreen.leftScreen;

import clientSubComponents.detailsScreen.app.DetailsScreenController;
import clientSubComponents.detailsScreen.rightScreen.entityDetails.EntityDetailsController;
import clientSubComponents.detailsScreen.rightScreen.environmentDetails.EnvironmentDetailsController;
import clientSubComponents.detailsScreen.rightScreen.gridDetails.GridDetailsController;
import clientSubComponents.detailsScreen.rightScreen.ruleDetails.RuleDetailsController;
import clientSubComponents.detailsScreen.rightScreen.terminationDetails.TerminationDetailsController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import util.Constants;
import util.http.HttpClientUtil;
import utils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreeViewController {
    @FXML
    private TreeView<String> treeViewForDetailsScreenComponent;
    public DetailsScreenController detailsScreenComponentController;
    //private IEngineManager engineManager  = new EngineManager();
    private SimpleObjectProperty<TreeItem<String>> selectedItem;
    public TreeViewController() {
        this.selectedItem = new SimpleObjectProperty<>();
    }

    private void clearTreeView() {
        treeViewForDetailsScreenComponent.getRoot().getChildren().clear();
        treeViewForDetailsScreenComponent.setRoot(null);
    }
    public void initialTreeView() {
        TreeItem<String> rootItem = new TreeItem<>("Worlds"); // Create a root node for "Worlds"
        this.treeViewForDetailsScreenComponent.setRoot(rootItem);

        String finalUrl = HttpUrl
                .parse(Constants.LIST_WORLDS) // Adjust this URL to fetch the list of worlds
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                    handleFailure(e.getMessage());
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        Gson gson = new GsonBuilder()
                                .registerTypeAdapter(DTOActionDetails.class, new DTOActionDetailsDeserializer())
                                .create();


                        Map<String, DTOSimulationDefinition> allFiles = gson.fromJson(responseData, new TypeToken<Map<String, DTOSimulationDefinition>>(){}.getType());

                        Platform.runLater(() -> {
                            for (Map.Entry<String, DTOSimulationDefinition> entry : allFiles.entrySet()) {
                                String worldName = entry.getKey();
                                DTOSimulationDefinition dtoSimulationDefinition = entry.getValue();

                                TreeItem<String> worldNode = new TreeItem<>(worldName); // Create a child node for each world
                                rootItem.getChildren().add(worldNode);

                                // Fetch and add child nodes for entities, rules, and environments (similar to your existing code)
                                insertDataToTreeView(worldNode, dtoSimulationDefinition);
                                worldNode.setExpanded(true);
                            }
                        });
                    }
                } finally {
                    // If there are any cleanup or resource release tasks, you can put them here
                    response.close();
                }
            }
        });
    }



    public void handleFailure(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error In The Server");
        alert.setContentText(errorMessage);
        alert.setWidth(300);
        alert.show();
    }
    public void setMainController(DetailsScreenController detailsScreenController) {
        this.detailsScreenComponentController = detailsScreenController;
    }
    private void insertDataToTreeView(TreeItem<String> rootItem, DTOSimulationDefinition dtoSimulationDefinition) {
        List<String> worldBranches = insertProperties();

        for (String property : worldBranches) {
            TreeItem<String> propertyBranch = new TreeItem<>(property);
            rootItem.getChildren().add(propertyBranch);

            if ("Entities".equals(property)) {
                List<DTOEntityDefinition> entities = dtoSimulationDefinition.getDtoEntityDefinition();
                for (DTOEntityDefinition entityDefinition : entities) {
                    TreeItem<String> entityNode = new TreeItem<>(entityDefinition.getEntityName());
                    propertyBranch.getChildren().add(entityNode);
                }
            } else if ("Environments".equals(property)) {
                List<DTOPropertyDefinition> environmentVariables = dtoSimulationDefinition.getDtoEnvironmentVariables();
                for (DTOPropertyDefinition envDefinition : environmentVariables) {
                    TreeItem<String> envNode = new TreeItem<>(envDefinition.getName());
                    propertyBranch.getChildren().add(envNode);
                }
            } else if ("Rules".equals(property)) {
                List<DTORuleDefinition> rules = dtoSimulationDefinition.getDtoRulesDefinition();
                for (DTORuleDefinition ruleDefinition : rules) {
                    TreeItem<String> ruleNode = new TreeItem<>(ruleDefinition.getName());
                    propertyBranch.getChildren().add(ruleNode);
                }
            }
        }
    }

    private List<String> insertProperties() {
        List<String> worldProperties= new ArrayList<>();

        worldProperties.add("Entities");
        worldProperties.add("Environments");
        worldProperties.add("Rules");
        worldProperties.add("Grid");

        return worldProperties;
    }

    public void selectedItem() throws IOException {
        detailsScreenComponentController.getvBoxComponent().getChildren().clear();

        TreeItem<String> item = treeViewForDetailsScreenComponent.getSelectionModel().getSelectedItem();

        if (item != null) {
            switch (item.getParent().getValue()) {
                case "Entities":
                    loadAndDisplayDetails(item.getParent().getParent(), item, "Entities", "/clientSubComponents/detailsScreen/rightScreen/entityDetails/entityDetails.fxml", DTOEntityDefinition.class);
                    break;
                case "Environments":
                    loadAndDisplayDetails(item.getParent().getParent(), item, "Environments", "/clientSubComponents/detailsScreen/rightScreen/environmentDetails/environmentDetails.fxml",DTOPropertyDefinition.class );
                    break;
                case "Rules":
                    loadAndDisplayDetails(item.getParent().getParent(), item, "Rules", "/clientSubComponents/detailsScreen/rightScreen/ruleDetails/RulesDetails.fxml", DTORuleDefinition.class);
                    break;
                default:
                    break;
            }

            if(item.getValue().equals("Grid")) {
                loadAndDisplayDetails(item.getParent(), item, "Grid", "/clientSubComponents/detailsScreen/rightScreen/gridDetails/gridDetails.fxml" , DTOGridDefinition.class);
            }
        }
    }


    private void loadAndDisplayDetails(TreeItem<String> fileName, TreeItem<String> selectedItem, String type, String fxmlPath, Class dtoClass) throws IOException {
        String finalUrl = HttpUrl
                .parse(Constants.INIT_TREE_DETAILS)
                .newBuilder()
                .addQueryParameter("type", type)
                .addQueryParameter("selected", selectedItem.getValue())
                .addQueryParameter("id", fileName.getValue())
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() -> {
                    handleFailure(e.getMessage());
                });
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();

                        Gson gson = new GsonBuilder()
                                .registerTypeAdapter(DTOActionDetails.class, new DTOActionDetailsDeserializer())
                                .create();

                        Object selectedDefinition = gson.fromJson(responseData, dtoClass);

                        Platform.runLater(() -> {
                            if (selectedDefinition != null) {
                                displayDetails(selectedDefinition, fxmlPath);
                            }
                        });
                    }
                } finally {
                    response.close();
                }
            }
        });
    }
    private void displayDetails(Object selectedDefinition, String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            ScrollPane detailsScrollPane = fxmlLoader.load();

            AnchorPane detailsPane = (AnchorPane) detailsScrollPane.getContent();
            if(fxmlLoader.getController() instanceof EntityDetailsController) {
                ((EntityDetailsController) fxmlLoader.getController()).updateDetails(selectedDefinition);
            } else if (fxmlLoader.getController() instanceof EnvironmentDetailsController) {
                ((EnvironmentDetailsController) fxmlLoader.getController()).updateDetails(selectedDefinition);
            } else if (fxmlLoader.getController() instanceof RuleDetailsController) {
                ((RuleDetailsController) fxmlLoader.getController()).updateDetails(selectedDefinition);
            } else if (fxmlLoader.getController() instanceof GridDetailsController) {
                ((GridDetailsController) fxmlLoader.getController()).updateDetails(selectedDefinition);
            } else if (fxmlLoader.getController() instanceof TerminationDetailsController) {
                ((TerminationDetailsController) fxmlLoader.getController()).updateDetails(selectedDefinition);
            }

            detailsScreenComponentController.getvBoxComponent().getChildren().clear();
            detailsScreenComponentController.getvBoxComponent().getChildren().add(detailsPane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
