module org.personal.markcs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires jbcrypt;
    requires jdk.jshell;

    opens org.personal.markcs to javafx.fxml;
    exports org.personal.markcs;
    opens org.personal.markcs.Model to javafx.fxml;
    exports org.personal.markcs.Model;
    opens org.personal.markcs.Control to javafx.fxml;
    exports org.personal.markcs.Control;
}
