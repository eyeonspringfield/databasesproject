package org.personal.markcs.Control;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;
import org.personal.markcs.App;
import org.personal.markcs.DAO.UserDaoImpl;
import org.personal.markcs.Model.Session;
import org.personal.markcs.Model.User;

public class PrimaryController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    UserDaoImpl dao = new UserDaoImpl();

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public void logIn(ActionEvent actionEvent) throws IOException {
        User user = dao.getUserByName(username.getText());
        if(user == null || !BCrypt.checkpw(password.getText(), user.getPassword())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Rossz felhasználónév vagy jelszó!");
            alert.showAndWait();
            return;
        }

        Session.setName(user.getName());
        Session.setIsAuthenticated(true);
        dao.setOnline(Session.getName());
        if(user.getRole().equals("owner")) {
            Session.setIsAdmin(false);
            App.setRoot("list");
        }
        if(user.getRole().equals("admin")) {
            Session.setIsAdmin(true);
            App.setRoot("admin");
        }
    }

    public void register(ActionEvent actionEvent) throws IOException {
        App.setRoot("register");
    }
}
