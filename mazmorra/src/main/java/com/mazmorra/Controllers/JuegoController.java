package com.mazmorra.Controllers;

import java.io.IOException;

import com.mazmorra.Interfaces.Observer;
import com.mazmorra.Model.Mapa;
import com.mazmorra.Model.Personaje;
import com.mazmorra.Model.Proveedor;
import com.mazmorra.Util.DataReader;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class JuegoController implements Observer {
    
    @FXML
    private Label vidaJugador;
    @FXML
    private Label ataqueJugador;
    @FXML
    private Label defensaJugador;
    @FXML
    private Label nombreJugador;
    @FXML
    private Label velocidadJugador;
    @FXML
    private ImageView imagenJugador;
    
    @FXML
    private StackPane stackPaneJuego; 
    @FXML
    private GridPane gridPaneJuego; 

    private Personaje personaje;
    private Mapa mapa; 

    @FXML
    public void initialize() {
        
        // Obtiene el personaje e inserta sus stats en la escena
        personaje = Proveedor.getInstance().getPersonaje();
        personaje.suscribe(this);
        actualizarStats();

        // Carga el mapa (como trabaja con archivos, hay que capturar errores IO)
        String archivo = "mapa_15x15_prueba.txt";
        String ruta = "src/main/resources/com/mazmorra/Tablero/" + archivo;
        try {
            int[][] mapaMatriz = DataReader.leerMapa(ruta); 
            mapa = new Mapa(mapaMatriz);
        } catch (IOException e) {
            System.err.println("Error en la lectura del archivo.\n" + e.getMessage()); 
        }

        /*Añade un listener nativo de Java Fx para detectar cambios (valor observable, valor antiguo, valor nuevo) en la propiedad 'ancho'.
          Si usásemos el stackPaneJuego.getWidht, no podríamos controlar la validez del ancho.*/
        stackPaneJuego.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 0 && mapa != null) { //Cuando se produce un cambio y el valor es válido (>0), se genera el Tablero. 
                mapa.generarTablero(gridPaneJuego, stackPaneJuego);
            }
        });
        
    }

    @Override
    public void onChange() {
        actualizarStats();
    }

    private void actualizarStats() {
        nombreJugador.setText(personaje.getNombre());
        vidaJugador.setText(String.valueOf(personaje.getVida()));
        ataqueJugador.setText(String.valueOf(personaje.getAtaque()));
        defensaJugador.setText(String.valueOf(personaje.getDefensa()));
        velocidadJugador.setText(String.valueOf(personaje.getVelocidad()));
        if (personaje.getImagen() != null) {
            imagenJugador.setImage(personaje.getImagen());
            imagenJugador.setFitWidth(32);
            imagenJugador.setFitHeight(32);
        }
    }
}