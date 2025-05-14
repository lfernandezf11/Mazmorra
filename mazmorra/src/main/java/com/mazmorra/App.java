package com.mazmorra;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal de la aplicación JavaFX.
 * 
 * Inicializa y lanza el juego configurando la escena de inicio mediante el SceneManager.
 * App extiende de javafx Application, y es el punto de entrada del programa.
 * 
 * @author Miguel González Seguro
 * @author Lucía Fernández Florencio
 */
public class App extends Application {

    /**
     * Método que se ejecuta automáticamente al iniciar la aplicación JavaFX.
     * 
     * Inicializa la instancia del SceneManager junto con el Stage principal,
     * y establece la escena de inicio como pantalla inicial del juego.
     *
     * @param stage el escenario proporcionado por JavaFX (motivo por el cual no es declarado en el código).
     * @throws IOException si ocurre un error al cargar alguna de las escenas.
     */
    @SuppressWarnings("exports")
    @Override
    public void start(Stage stage) throws IOException {
        // Obtiene la instancia del SceneManager (singleton)
        SceneManager sm = SceneManager.getInstance();

        // Inicializa el SceneManager con el stage principal
        sm.init(stage);

        // Registra la escena de inicio en el gestor de escenas
        sm.setScene(SceneID.INICIO, "inicio"); /* ¡Importante! Cuando ejecutamos App, todas las escenas contenidas en la clase cargan
                                                       si están instauradas, es decir, si se ha llevado a cabo el setScene en App. 
                                                       Aunque la escena no se muestre, su initialize ya ha sido ejecutado, con lo que su estado
                                                       es el mismo al pasar de pantalla. 
                                                       Hay que hacer el setScene justo antes del loadScene.
                                                       */

        // Carga y muestra la escena de inicio. 
        sm.loadScene(SceneID.INICIO);
    }

    /**
     * Método principal que lanza la aplicación.
     * 
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        launch();
    }
}