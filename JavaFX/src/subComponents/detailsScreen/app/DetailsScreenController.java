//package subComponents.detailsScreen.app;
//
//import javafx.fxml.FXML;
//import javafx.scene.control.TreeView;
//import javafx.scene.image.Image;
//import javafx.scene.layout.VBox;
//import prediction.engineManager.IEngineManager;
//import subComponents.detailsScreen.leftScreen.TreeViewController;
//import subComponents.mainScreen.body.BodyController;
//
//
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.Consumer;
//
//public class DetailsScreenController {
//    //private BodyController mainBodyComponentController;
//    private IEngineManager engineManager;
//    @FXML private VBox vBoxComponent;
//    private List<Consumer<String>> valueSelectionListeners = new ArrayList<>();
//
//    //treeView
//    @FXML private TreeView treeViewForDetailsScreenComponent;
//    @FXML private TreeViewController treeViewForDetailsScreenComponentController;
//
//
//    @FXML
//    public void initialize() {
//        if (treeViewForDetailsScreenComponentController != null ) {//&& entityDetailsComponentController != null && environmentDetailsComponentController != null) {
//            treeViewForDetailsScreenComponentController.setMainController(this);
//
//        }
//    }
//
//    public void setMainController(BodyController mainBodyController) {
//        this.mainBodyComponentController = mainBodyController;
//    }
//
//    public void setEngineManager(IEngineManager engineManager) {
//        this.engineManager = engineManager;
//        treeViewForDetailsScreenComponentController.setSystemEngine(engineManager);
//
//    }
//    public void addValueSelectionListener(Consumer<String> listener) {
//        valueSelectionListeners.add(listener);
//    }
//    public void notifyValueSelectionListeners(String selectedValue) {
//        for (Consumer<String> listener : valueSelectionListeners) {
//            listener.accept(selectedValue);
//        }
//    }
//
//    public TreeViewController getTreeViewForDetailsScreenComponentController() {
//        return treeViewForDetailsScreenComponentController;
//    }
//
//    public VBox getvBoxComponent() {
//        return vBoxComponent;
//    }
//
////    public void setBackground(String imageUrl) {
////        String backgroundImageUrl = "url('" + imageUrl + "')";
////        String backgroundStyle = "-fx-background-image: " + backgroundImageUrl + "; " +
////                "-fx-background-repeat: no-repeat; " +
////                "-fx-background-size: cover;"; // Adjust 'cover' based on your needs
////
////        vBoxComponent.setStyle(backgroundStyle);
////    }
//
//
//}
