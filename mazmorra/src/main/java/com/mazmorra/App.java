package com.mazmorra;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @SuppressWarnings("exports")
    @Override
    public void start(Stage stage) throws IOException {
        // Obtiene la instancia del SceneManager
        SceneManager sm = SceneManager.getInstance();
        
        // Inicializa el SceneManager con el stage y una ruta de estilos
        sm.init(stage);
        
        // Configura las escenas con identificadores y tamaños
        sm.setScene(SceneID.INICIO, "inicio");
        sm.setScene(SceneID.PERSONAJE, "personaje");
        //sm.setScene(SceneID.JUEGO, "juego");
        
        // Carga la escena principal
        sm.loadScene(SceneID.INICIO);
    }

    
    

    public static void main(String[] args) {
        launch();
    }

}