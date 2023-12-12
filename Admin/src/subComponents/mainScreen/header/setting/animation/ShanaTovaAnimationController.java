package subComponents.mainScreen.header.setting.animation;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ShanaTovaAnimationController {

    @FXML
    private ImageView shanaTovaImage;
    private TranslateTransition translate;

    @FXML
    public void initialize() {
        translate = new TranslateTransition();
        translate.setNode(shanaTovaImage);
        translate.setDuration(Duration.millis(1000));
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setByX(500);
        translate.setByY(-250);
        translate.setAutoReverse(true);
    }

    public void shanaTovaAnimation() {
        setImage();
        translate = new TranslateTransition();
        translate.setNode(shanaTovaImage);
        translate.setDuration(Duration.millis(1000));
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setByX(500);
        translate.setByY(-250);
        translate.setAutoReverse(true);
        translate.play(); // Start the animation when called
    }

    public void setImage() {
        this.shanaTovaImage = new ImageView("subComponents/mainScreen/header/setting/image/shanaTova.jpg");
        shanaTovaImage.setImage(shanaTovaImage.getImage());
    }
}

