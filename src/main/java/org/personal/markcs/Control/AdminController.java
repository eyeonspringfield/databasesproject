package org.personal.markcs.Control;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.personal.markcs.App;
import org.personal.markcs.DAO.UserDaoImpl;
import org.personal.markcs.Model.Session;
import org.personal.markcs.Model.User;

import java.io.IOException;

public class AdminController {
    UserDaoImpl dao = new UserDaoImpl();

    @FXML
    private void initialize() {
        User user = dao.getUserByName(Session.getName());

        if(!Session.isAuthenticated() || !Session.isAdmin() || user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Érvénytelen azonosító!");
            alert.showAndWait();
            return;
        }

        /*usernameLabel.setText(user.getName());
        addressLabel.setText(user.getAddress());
        dobLabel.setText(user.getBirthdate().toString());
        taxIdLabel.setText(String.valueOf(user.getTaxID()));
        phoneNumberLabel.setText(user.getPhone());
        mothersNameLabel.setText(user.getMothersMaidenName());*/

        //TODO tableview initialization
    }

    @FXML
    private void logout() throws IOException {
        if(!Session.isAuthenticated() || !Session.isAdmin()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Érvénytelen azonosító!");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Jóváhagyás szükséges!");
        alert.setHeaderText(null);
        alert.setContentText("Biztosan szeretne kijelentkezni?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            Session.setName(null);
            Session.setIsAuthenticated(false);
            App.setRoot("primary");
        }
    }
}
