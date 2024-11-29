package org.personal.markcs;

import atlantafx.base.theme.PrimerDark;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        scene = new Scene(loadFXML("primary"), 1200, 675);
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        stage.setScene(scene);
        stage.setTitle("IngatlanSzeged adatbáziskezelő");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/favicon-purple.png"))));

        Platform.runLater(stage::show);
    }

    public static void setRoot(String fxml) throws IOException {
        new Thread(() -> {
            try {
                Parent newRoot = loadFXML(fxml);
                Platform.runLater(() -> {
                    scene.setRoot(newRoot);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}