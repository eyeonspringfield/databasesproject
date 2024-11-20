package org.personal.markcs.Control;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.personal.markcs.App;

public class PrimaryController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public void logIn(ActionEvent actionEvent) throws IOException {
        App.setRoot("list");
    }

    public void register(ActionEvent actionEvent) throws IOException {
        App.setRoot("register");
    }
}
