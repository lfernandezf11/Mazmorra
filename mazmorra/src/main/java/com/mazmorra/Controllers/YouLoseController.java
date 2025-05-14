package com.mazmorra.Controllers;

import com.mazmorra.SceneID;
import com.mazmorra.SceneManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controlador para la escena de derrota del jugador.
 * 
 * Permite al usuario elegir entre cerrar la aplicación o reiniciar el juego
 * volviendo a la pantalla de inicio, a través de botones.
 * 
 * @author Miguel González Seguro
 * @author Lucía Fernández Florencio
 */
public class YouLoseController {

    /** Botón para cerrar la aplicación. */
    @FXML
    private Button salir;

    /** Botón para reiniciar la partida desde la pantalla de inicio. */
    @FXML
    private Button reintentar;

    /**
     * Inicializa los eventos de los botones de la escena de derrota.
     * 
     * - Reintentar: vuelve a cargar la escena de inicio.
     * - Salir: cierra completamente la aplicación.
     */
    @FXML
    public void initialize() {
        reintentar.setOnAction(event -> {
            SceneManager.getInstance().setScene(SceneID.INICIO, "inicio");
            SceneManager.getInstance().loadScene(SceneID.INICIO);
        });

        salir.setOnAction(event -> {
            System.exit(0);
        });
    }
}
