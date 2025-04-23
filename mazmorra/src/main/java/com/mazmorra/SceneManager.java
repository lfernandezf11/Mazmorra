package com.mazmorra;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    private static SceneManager instance;

    private Stage stage; // Ventana "exportada" de App
    private HashMap<String, Scene> scenes; // Mapa para almacenar las escenas por nombre

    /*Constructor privado de SceneManager. 
      Inicializa la estructura.
     */
    private SceneManager() {
        scenes = new HashMap<>();
    }

    /*Instancia única de SceneManager. Estática. */
    public static SceneManager getInstance(){
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }
    
    /*Llama al Stage stage exportado desde la App para indicar que es la ventana que usamos con un this.stage.) */
    @SuppressWarnings("exports")
    public void init(Stage stage){
        this.stage = stage;
    }

    //CREATE Y DELETE
    /*Genera una nueva escena: 
     * Carga en un loader el fxml
     * Establece la ruta padre (Parent) cargando ese louder.
     * Crea una escena en la ruta, con tamaño definido.
     * Almacena la escena en la instancia única SceneManager, utilizando como id único su nombre.
     */
    public void setScene(String scene_name, String fxml, int width, int height){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views" + fxml + ".fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, width, height); 
            scenes.put(scene_name, scene); 
        } catch (IOException e) {
            e.printStackTrace(); // Error al cargar el FXML
        }
    }

    /*Elimina una escena por nombre */
    public void removeScene(String scene_name){
        scenes.remove(scene_name); // Elimina la escena del mapa
    }

    /* Busca una escena por nombre y la carga (la "enseña") en la ventana principal (App sm.loadScene) de existir ésta */
    public void loadScene(String scene_name) throws Exception {
        String name_limpio = scene_name.trim().toLowerCase();
        if (scenes.containsKey(name_limpio)){
            stage.setScene(scenes.get(name_limpio)); 
            stage.show(); 
        } else{
            throw new Exception();
        }
    }
}
