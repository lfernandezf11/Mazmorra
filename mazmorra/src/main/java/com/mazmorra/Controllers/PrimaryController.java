package com.mazmorra.Controllers;

import java.io.IOException;
import com.mazmorra.App;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
