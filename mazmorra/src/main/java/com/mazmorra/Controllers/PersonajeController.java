package com.mazmorra.Controllers;

import java.io.IOException;
import com.mazmorra.App;
import javafx.fxml.FXML;

public class PersonajeController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}