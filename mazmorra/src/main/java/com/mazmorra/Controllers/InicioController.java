package com.mazmorra.Controllers;

import com.mazmorra.SceneManager;
import com.mazmorra.SceneID;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class InicioController {

    @FXML
    private Button cambiarAPersonaje;

    @FXML
    public void initialize() {
        cambiarAPersonaje.setOnAction(event -> {
            // Carga la escena del personaje (puede estar precargada si quieres)
            SceneManager.getInstance().setScene(SceneID.PERSONAJE, "personaje");
            SceneManager.getInstance().loadScene(SceneID.PERSONAJE);
        });
    }
}
