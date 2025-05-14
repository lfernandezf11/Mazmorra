package com.mazmorra.Controllers;

import com.mazmorra.SceneID;
import com.mazmorra.SceneManager;
import com.mazmorra.TipoJugador;
import com.mazmorra.Model.Jugador;
import com.mazmorra.Model.Proveedor;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controlador para la escena de victoria del jugador.
 * 
 * Permite al usuario elegir entre cerrar la aplicación o reiniciar el juego
 * volviendo a la pantalla de inicio, a través de botones.
 * 
 * @author Miguel González Seguro
 * @author Lucía Fernández Florencio
 */
public class YouWinController {

    /** Botón para cerrar la aplicación*/
    @FXML
    private Button salir;

    /** Botón para reiniciar la partida desde la pantalla de inicio. */
    @FXML
    private Button jugarDeNuevo;

    /** Espacio para reflejar la imagen del personaje ganador */
    @FXML 
    private ImageView imagenGanador;

    // Instancia del modelo
    private Jugador jugador;

    /** Rutas para las imágenes del jugador correspondiente */
    private static String pathGuerrero = "/com/mazmorra/Images/guerrero_hd.png";
    private static String pathArquero = "/com/mazmorra/Images/arquero_hd.png";
    private static String pathMaga = "/com/mazmorra/Images/maga_hd.png";


    /**
     * Inicializa los eventos de los botones de la escena de victoria.
     * 
     * - JugarDeNuevo: vuelve a cargar la escena de inicio.
     * - Salir: cierra completamente la aplicación.
     */
    @FXML
    public void initialize() {
        cargarImagenGanador();

        jugarDeNuevo.setOnAction(event -> {
            SceneManager.getInstance().setScene(SceneID.INICIO, "inicio");
            SceneManager.getInstance().loadScene(SceneID.INICIO);
        });

        salir.setOnAction(event -> {
            System.exit(0);
        });
    }


    /**
     * Carga y muestra la imagen del personaje que ha ganado, recuperando el nombre de la instancia del jugador
     * y asociando a cada tipo una imagen con mayor calidad. 
     */
    private void cargarImagenGanador() {
        jugador = Proveedor.getInstance().getJugador();
        if (jugador.getTipoJugador() != null)
            if (jugador.getTipoJugador().equals(TipoJugador.ARQUERO)) {
                Image imagen = new Image(getClass().getResource(pathArquero).toExternalForm());
                imagenGanador.setImage(imagen);
            } else if (jugador.getTipoJugador().equals(TipoJugador.GUERRERO)){
                Image imagen = new Image(getClass().getResource(pathGuerrero).toExternalForm());
                imagenGanador.setImage(imagen);
            } else {
                Image imagen = new Image(getClass().getResource(pathMaga).toExternalForm());
                imagenGanador.setImage(imagen);
            }
    }
    
}

