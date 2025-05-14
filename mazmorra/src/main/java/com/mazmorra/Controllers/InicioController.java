package com.mazmorra.Controllers;

import com.mazmorra.SceneManager;
import com.mazmorra.SceneID;

import javafx.fxml.FXML;
import javafx.scene.control.Button;


/**
 * Controlador de la pantalla de inicio del juego.
 * 
 * Gestiona la interacción inicial del usuario e incluye el botón
 * que permite avanzar a la pantalla de construcción de personaje.
 * 
 * 
 * @author Miguel González Seguro
 * @author Lucía Fernández Florencio
 */
public class InicioController {

    /** Botón que permite al usuario pasar a la pantalla de personaje. */
    @FXML
    private Button cambiarAPersonaje;

    /**
     * Inicializa el controlador después de que el archivo FXML haya sido cargado.
     * 
     * Asigna una acción al botón cambiarAPersonaje, de modo que cuando éste es pulsado se carga y ejecuta
     * la escena correspondiente al selector del personaje (jugador).
     */
    @FXML
    public void initialize() {
        cambiarAPersonaje.setOnAction(event -> {
            // Cambia a la escena de selección de personaje.
            SceneManager.getInstance().setScene(SceneID.PERSONAJE, "personaje");
            SceneManager.getInstance().loadScene(SceneID.PERSONAJE);
        });
    }
}

