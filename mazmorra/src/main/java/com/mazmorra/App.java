package com.mazmorra;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @SuppressWarnings("exports")
    @Override
    public void start(Stage stage) throws IOException {
        // Obtiene la instancia del SceneManager
        SceneManager sm = SceneManager.getInstance();

        // Inicializa el SceneManager con el stage y una ruta de estilos
        sm.init(stage);

        // Carga la escena INICIO en el mapa del SceneManager
        sm.setScene(SceneID.INICIO, "inicio"); // <-- ¡ESTO ES IMPRESCINDIBLE!

        // Carga la escena principal
        sm.loadScene(SceneID.INICIO);
    }

    public static void main(String[] args) {
        launch();
    }
}