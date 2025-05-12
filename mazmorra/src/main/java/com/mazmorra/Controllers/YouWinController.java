package com.mazmorra.Controllers;

import com.mazmorra.SceneID;
import com.mazmorra.SceneManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class YouWinController {
    @FXML
    private Button salir;
    @FXML
    private Button jugarDeNuevo;

    @FXML
    public void initialize() {
        jugarDeNuevo.setOnAction(event -> {
            SceneManager.getInstance().setScene(SceneID.INICIO, "inicio");
            SceneManager.getInstance().loadScene(SceneID.INICIO);
        });

        salir.setOnAction(event -> {
            // Carga la escena del personaje (puede estar precargada si quieres)
            System.exit(0);
        });
    }
}
