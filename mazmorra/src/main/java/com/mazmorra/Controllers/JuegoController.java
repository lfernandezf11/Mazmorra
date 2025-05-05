package com.mazmorra.Controllers;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import com.mazmorra.Interfaces.Observer;
import com.mazmorra.Model.Jugador;
import com.mazmorra.Model.Mapa;
import com.mazmorra.Model.Personaje;
import com.mazmorra.Model.Proveedor;
import com.mazmorra.Util.DataReader;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class JuegoController implements Observer {
    /*
     * Para acceder a archivos físicos hay que especificar la ruta RELATIVA A LA
     * RAÍZ DEL PROYECTO (src/main/resources[...]).
     * En tiempo de ejecución, src/main/resources se copia en target/classes. El
     * getResources extrae de aquí las imágenes, por eso
     * se puede prescindir de src/main/resources en su ruta relativa.
     */
    private static final String rutaBase = "mazmorra/src/main/resources/com/mazmorra/Tablero/";

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
    private ImageView imagenMasVelocidad;
    @FXML
    private ImageView imagenVelocidadMedia;
    @FXML
    private ImageView imagenMasLento;

    @FXML
    private StackPane stackPaneJuego;
    @FXML
    private GridPane gridPaneJuego;

    private Jugador jugador;
    private Mapa mapa;

    @FXML
    public void initialize() {
        // PONER LA RUTA BIEN!
        Proveedor.getInstance().cargarEnemigosDesdeJson("enemigos.json");

        // Obtiene el jugador e inserta sus stats en la escena
        jugador = Proveedor.getInstance().getJugador();
        jugador.suscribe(this);
        actualizarStats();

        List<Personaje> personajes = Proveedor.getInstance().getListaDePersonajes();
        mostrarPersonajesPorVelocidad(personajes);

        cargarMapa();
        cargarTablero();
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

    private void mostrarPersonajesPorVelocidad(List<Personaje> personajes) {
        personajes.sort(Comparator.comparingInt(Personaje::getVelocidad).reversed());
        if (personajes.size() >= 3) {
            imagenMasVelocidad.setImage(personajes.get(0).getImagen());
            imagenVelocidadMedia.setImage(personajes.get(1).getImagen());
            imagenMasLento.setImage(personajes.get(2).getImagen());
        } else {
            if (personajes.size() > 0)
                imagenMasVelocidad.setImage(personajes.get(0).getImagen());
            if (personajes.size() > 1)
                imagenVelocidadMedia.setImage(personajes.get(1).getImagen());
            if (personajes.size() > 2)
                imagenMasLento.setImage(personajes.get(2).getImagen());
        }
    }

    /**
     * Genera una instancia Mapa en la que establece la correspondencia gráfica
     * entre:
     * - La matriz de datos de tipo Integer generada por el lector .txt (clase
     * DataReader).
     * - El GridPane de la escena.
     * 
     */
    private void cargarMapa() {
        String rutaArchivo = "mapa_15x15_prueba.txt";
        try {
            int[][] mapaMatriz = DataReader.leerMapa(rutaBase + rutaArchivo); // Ruta común declarada como variable
                                                                              // estática de la clase.
            mapa = new Mapa(mapaMatriz);
        } catch (IOException e) { // Como el método trabaja con archivos, hay que capturar errores de
                                  // entrada/salida (IO)
            System.err.println("Error en la lectura del archivo.\n" + e.getMessage());
        }
    }

    /**
     * Asegura que el método initialize del controlador del juego se haya ejecutado
     * antes de generar la interfaz gráfica para el tablero.
     * El objetivo es que las propiedades de los nodos ya estén establecidas antes
     * de extraerlas y operar sobre ellos.
     * 
     */
    private void cargarTablero() {
        /*
         * Clase JavaFX que permite al programa comunicarse y modificar la interfaz
         * gráfica desde otro hilo.
         */
        /*
         * Carga por primera vez el tablero DESPUÉS de que JavaFx haya terminado de
         * cargar, asegurando que se obtienen valores válidos.
         * Si extrajeramos widht directamente, podría devolver 0.
         */
        Platform.runLater(() -> {
            if (stackPaneJuego.getWidth() > 0 && mapa != null) {
                mapa.generarTablero(gridPaneJuego, stackPaneJuego);
            }

            /*
             * Añade un listener al stackPane para detectar cambios en el ancho posteriores
             * a la primera carga del tablero.
             * Da la RESPONSIVIDAD al tamaño de pantalla
             */
            stackPaneJuego.widthProperty().addListener((obs, oldVal, newVal) -> { // (valor observable, valor antiguo,
                                                                                  // valor nuevo).
                if (newVal.doubleValue() > 0 && mapa != null) { // Cuando se produce un cambio y el nuevo valor es
                                                                // válido (>0 y != oldVal), se genera el Tablero.
                    mapa.generarTablero(gridPaneJuego, stackPaneJuego);
                }
            });
        });
    }
}