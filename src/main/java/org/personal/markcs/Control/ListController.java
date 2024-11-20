package org.personal.markcs.Control;

import java.io.IOException;
import javafx.fxml.FXML;
import org.personal.markcs.App;

public class ListController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}