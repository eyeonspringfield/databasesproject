package org.personal.markcs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            Statement stmt;
            ResultSet rs;

            // hatarozzuk meg az adatbazis elerhetoseget
            String url = "jdbc:mysql://localhost:3306/ingatlanok";

            // hozzuk letre a kapcsolatot
            // mas esetben a ket ures string jelentene a felhasznalonevet es a jelszot
            Connection con = DriverManager.getConnection(url, "root", "");

            // irassuk ki a kapcsolodasi adatokat
            System.out.println("URL: " + url);
            System.out.println("Connection: " + con);

            // hozzuk letre a Statement objektumot
            stmt = con.createStatement();

            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            // hajtsunk vegre egy egyszeru lekerdezest
            rs = stmt.executeQuery("SELECT * from felhasznalo");

            // irassuk ki az eredmenyeket
            System.out.println("Display all results:");
            while(rs.next()){
                String id = rs.getString("Azonosító");
                String vnev = rs.getString("Név");
                String megye = rs.getString("Lakcím");
                System.out.println(id + " | " + vnev + " | "+ megye);
            }

            // zarjuk a kapcsolatot
            con.close();

            // hiba eseten irassuk ki a stack-et
        }catch( Exception e ) {
            e.printStackTrace();
        }
        scene = new Scene(loadFXML("primary"), 960, 540);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}