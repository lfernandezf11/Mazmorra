package com.mazmorra;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Gestor centralizado de escenas para la aplicación JavaFX.
 * 
 * Implementa un patrón Singleton y permite registrar, cargar y eliminar escenas asociadas a archivos FXML.
 * Utiliza el valor de Enum {@code SceneID} como identificador único de cada escena.
 * 
 * También proporciona acceso a los controladores cargados para interacción con lógica de negocio o suscripción a eventos.
 * 
 * @author Miguel González Seguro
 * @author Lucía Fernández Florencio
 */
public class SceneManager {
    /** Instancia única del SceneManager (patrón Singleton). */
    private static SceneManager instance;

    /** Ventana principal de la aplicación. */
    private Stage stage; 

    /** Mapa de escenas indexado por su identificador {@code SceneID}. */
    private HashMap<SceneID, Scene> scenes; 


    /**
     * Constructor privado del gestor de escenas, que inicializa el mapa de escenas vacío.
     * Impide que la clase pueda instanciarse más de una vez.
     */
    private SceneManager() {
        scenes = new HashMap<>();
    }


    /**
     * Método estático para obtener la instancia única de SceneManager (patrón Singleton).
     * 
     * @return la instancia única de SceneManager.
     */
    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    /**
     * Inicializa el SceneManager con el Stage principal.
     * 
     * @param stage  la ventana principal de la aplicación, donde se mostrarán las escenas.
     */
    @SuppressWarnings("exports") // El Stage es exportado desde App al gestor de escenas, por eso hay que suprimir los avisos de exportación.
    public void init(Stage stage) {
        this.stage = stage;
    }

    /**
     * Carga una escena desde un archivo FXML y la registra bajo un identificador único.
     * La escena también se asocia con la hoja de estilo definida previamente.
     * 
     * @param sceneID el identificador único de la escena.
     * @param fxml el nombre del archivo FXML que define la vista de la escena.
     */
    public void setScene(SceneID sceneID, String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/" + fxml + ".fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            // Aplica la hoja de estilos CSS global
            scene.getStylesheets().add(App.class.getResource("/com/mazmorra/styles/styles.css").toExternalForm());

            // Guarda el FXMLLoader en la escena para recuperar el controlador después
            scene.setUserData(fxmlLoader);
            scenes.put(sceneID, scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina una escena previamente almacenada usando su identificador.
     * 
     * @param sceneID el identificador único de la escena que se desea eliminar.
     */
    public void removeScene(SceneID sceneID) {
        scenes.remove(sceneID); 
    }

    /**
     * Carga y muestra una escena previamente almacenada en el SceneManager.
     * 
     * @param sceneID el identificador único de la escena que se desea cargar.
     */
    public void loadScene(SceneID sceneID) {
        if (scenes.containsKey(sceneID)) {
            stage.setScene(scenes.get(sceneID)); // Establece la escena en la ventana principal
            stage.show(); // Muestra la ventana con la nueva escena
        }
    }


    /**
     * Recupera el controlador asociado a una escena cargada para poder acceder a sus métodos sin necesidad de volver a crearla.
     * 
     * Es útil para registrar eventos después de cargar una escena.
     * 
     * @param sceneID identificador único de la escena.
     * @return instancia del controlador de la escena cargada, o null si no existe.
     */
    public Object getController(SceneID sceneID) {
        if (scenes.containsKey(sceneID)) {
            Scene scene = scenes.get(sceneID);
            FXMLLoader loader = (FXMLLoader) scene.getUserData(); // Recuperar el loader
            return loader.getController();
        }
        return null;
    }
}