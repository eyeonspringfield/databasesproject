package org.personal.markcs.Control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;
import org.personal.markcs.App;
import org.personal.markcs.DAO.UserDaoImpl;
import org.personal.markcs.Model.User;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class RegisterController {
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField password;
    @FXML
    private TextField taxId;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField motherName;
    @FXML
    private DatePicker birthDate;
    @FXML
    private TextField postalCode;
    @FXML
    private TextField settlement;
    @FXML
    private TextField streetHouseNumber;
    @FXML
    private CheckBox confirmationOfValidity;

    UserDaoImpl dao = new UserDaoImpl();

    public void registerUser(ActionEvent actionEvent) throws IOException {
        if(firstName.getText().isEmpty() || lastName.getText().isEmpty() || taxId.getText().isEmpty() ||
                phoneNumber.getText().isEmpty() || motherName.getText().isEmpty() || birthDate.getValue() == null ||
                postalCode.getText().isEmpty() || settlement.getText().isEmpty() || streetHouseNumber.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Regisztráció sikertelen!");
            alert.setHeaderText(null);
            alert.setContentText("Sikertelen volt a regisztráció! Nem lehet üresen hagyni beviteli mezőt!");
            alert.showAndWait();
            return;
        }
        if (!confirmationOfValidity.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Regisztráció sikertelen!");
            alert.setHeaderText(null);
            alert.setContentText("Sikertelen volt a regisztráció, mert nem nyilatkozott az adatok valódiságáról!");
            alert.showAndWait();
            return;
        }

        //egyéb ellenőrzések amikhez most nincs kedvem

        User user = new User(
                firstName.getText() + lastName.getText(),
                BCrypt.hashpw(password.getText(), BCrypt.gensalt()),
                Integer.parseInt(taxId.getText()),
                postalCode.getText() + " " + settlement.getText() + " " + streetHouseNumber.getText(),
                phoneNumber.getText(),
                birthDate.getValue(),
                motherName.getText(),
                true,
                LocalDate.now(),
                "owner"
        );

        boolean success = dao.addUser(user);

        if(success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Sikeres regisztráció");
            alert.setContentText("Sikeres regisztráció!");
            App.setRoot("primary");
        }
    }
}
