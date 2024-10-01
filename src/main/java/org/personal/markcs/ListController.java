package org.personal.markcs;

import java.io.IOException;
import javafx.fxml.FXML;

public class ListController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}