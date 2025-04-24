package com.mazmorra.Controllers;

import java.io.IOException;
import com.mazmorra.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class InicioController {

    @FXML
    private Button cambiarAPersonajeBoton;

    @FXML
    private void cambiarAPersonaje() throws IOException {
        App.setRoot("personaje");
    }

}
