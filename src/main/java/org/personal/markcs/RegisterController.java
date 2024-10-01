package org.personal.markcs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;

public class RegisterController {

    @FXML
    private CheckBox confirmationOfValidity;

    public void registerUser(ActionEvent actionEvent) {
        if (!confirmationOfValidity.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Regisztráció sikertelen!");
            alert.setHeaderText(null);
            alert.setContentText("Sikertelen volt a regisztráció, mert nem nyilatkozott az adatok valódiságáról!");
            alert.showAndWait();

        }
    }
}
