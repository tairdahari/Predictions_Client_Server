package subComponents.results.executionError;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ExecutionErrorController {

    @FXML
    private Label errorLabel;

    public void displayErrorMessage(String errorForDisplay) {
        errorLabel.setText(errorForDisplay);
    }
}
