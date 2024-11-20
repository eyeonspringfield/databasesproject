package org.personal.markcs.Control;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import org.personal.markcs.App;
import org.personal.markcs.DAO.UserDaoImpl;
import org.personal.markcs.Model.Session;
import org.personal.markcs.Model.User;

public class ListController {
    @FXML
    private Label usernameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label dobLabel;
    @FXML
    private Label taxIdLabel;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private Label mothersNameLabel;

    private UserDaoImpl dao = new UserDaoImpl();
    @FXML
    private void initialize() {
        User user = dao.getUserByName(Session.getName());

        if(!Session.isAuthenticated() || user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Érvénytelen azonosító!");
            alert.showAndWait();
            return;
        }

        usernameLabel.setText(user.getName());
        addressLabel.setText(user.getAddress());
        dobLabel.setText(user.getBirthdate().toString());
        taxIdLabel.setText(String.valueOf(user.getTaxID()));
        phoneNumberLabel.setText(user.getPhone());
        mothersNameLabel.setText(user.getMothersMaidenName());

        //TODO tableview initialization
    }

    @FXML
    private void logout() throws IOException {
        if(!Session.isAuthenticated()) {
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

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}