package org.personal.markcs.Control;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;
import org.personal.markcs.App;
import org.personal.markcs.DAO.UserDaoImpl;
import org.personal.markcs.Model.Session;
import org.personal.markcs.Model.User;

import java.io.IOException;

public class EditUserDataController {
    @FXML
    private TextField username;
    @FXML
    private TextField taxId;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField motherName;
    @FXML
    private DatePicker birthDate;
    @FXML
    private TextField address;
    @FXML
    private TextField password;
    @FXML
    private TextField newPassword;
    @FXML
    private TextField newPasswordAgain;
    @FXML
    private CheckBox acceptTerms;

    private UserDaoImpl dao = new UserDaoImpl();
    User user = null;

    @FXML
    private void initialize(){
        user = dao.getUserByName(Session.getName());

        if(!Session.isAuthenticated() || user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Érvénytelen azonosító!");
            alert.showAndWait();
            return;
        }

        username.setText(user.getName());
        taxId.setText(String.valueOf(user.getTaxID()));
        phoneNumber.setText(user.getPhone());
        motherName.setText(user.getMothersMaidenName());
        birthDate.setValue(user.getBirthdate());
        address.setText(user.getAddress());
    }

    @FXML
    private void editUserData() throws IOException {
        if(username.getText().isEmpty() || taxId.getText().isEmpty() || phoneNumber.getText().isEmpty()
                || motherName.getText().isEmpty() || birthDate.getValue() == null || address.getText().isEmpty()
                || password.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Adatmódosítása sikertelen!");
            alert.setHeaderText(null);
            alert.setContentText("Sikertelen volt az adatok módosítása! Nem lehet üresen hagyni beviteli mezőt!");
            alert.showAndWait();
            return;
        }

        if(!acceptTerms.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Adatmódosítás sikertelen!");
            alert.setHeaderText(null);
            alert.setContentText("Sikertelen volt az adatok módosítása, mert nem nyilatkozott az adatok valódiságáról!");
            alert.showAndWait();
            return;
        }

        if(!BCrypt.checkpw(password.getText(), user.getPassword())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Adatmódosítás sikertelen!");
            alert.setHeaderText(null);
            alert.setContentText("Sikertelen volt az adatok módosítása! Helytelen jelenlegi jelszót adott meg!");
            alert.showAndWait();
            return;
        }

        if(newPassword.getText().isEmpty() && newPasswordAgain.getText().isEmpty()) {
            User updatedUser = new User(
                    username.getText(),
                    user.getPassword(),
                    Integer.parseInt(taxId.getText()),
                    address.getText(),
                    phoneNumber.getText(),
                    birthDate.getValue(),
                    motherName.getText(),
                    user.isLoggedIn(),
                    user.getLastLogin(),
                    user.getRole()
            );

            boolean isUpdated = dao.updateUser(updatedUser);
            if(isUpdated){
                App.setRoot("list");
            }
        }

    }

    @FXML
    private void cancelEditUserData() throws IOException {
        App.setRoot("list");
    }
}
