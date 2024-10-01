module org.personal.markcs {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.personal.markcs to javafx.fxml;
    exports org.personal.markcs;
}
