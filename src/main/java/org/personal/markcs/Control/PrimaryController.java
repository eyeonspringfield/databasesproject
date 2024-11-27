package org.personal.markcs.Control;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
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
    @FXML
    private ProgressIndicator progressIndicator;
    UserDaoImpl dao = new UserDaoImpl();

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public void logIn(ActionEvent actionEvent) throws IOException {
        progressIndicator.setVisible(true);
        progressIndicator.setProgress(-1.0);
        AtomicBoolean loginSuccess = new AtomicBoolean(false);

        new Thread(() -> {
            loginSuccess.set(false);

            User user = dao.getUserByName(username.getText());
            if (user != null && BCrypt.checkpw(password.getText(), user.getPassword())) {
                Session.setName(user.getName());
                Session.setIsAuthenticated(true);
                dao.setOnline(Session.getName());

                if (user.getRole().equals("owner")) {
                    Session.setIsAdmin(false);
                    loginSuccess.set(true);
                } else if (user.getRole().equals("admin")) {
                    Session.setIsAdmin(true);
                    loginSuccess.set(true);
                }
            }

            Platform.runLater(() -> {
                if (loginSuccess.get()) {
                    try {
                        if (Session.isAdmin()) {
                            App.setRoot("admin");
                        } else {
                            App.setRoot("list");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    progressIndicator.setVisible(false);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Hibás felhasználónév vagy jelszó!");
                    alert.showAndWait();
                }
            });
        }).start();
    }

    public void register(ActionEvent actionEvent) throws IOException {
        App.setRoot("register");
    }

    @FXML
    private void closeApp() throws IOException {
        Platform.exit();
    }
}
