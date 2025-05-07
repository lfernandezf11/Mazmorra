package com.mazmorra.Controllers;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
    private Label vidaCthulu;
    @FXML
    private Label ataqueCthulu;
    @FXML
    private Label velocidadCthulu;
    @FXML
    private Label vidaMino;
    @FXML
    private Label ataqueMino;
    @FXML
    private Label velocidadMino;

    @FXML
    private StackPane stackPaneJuego;
    @FXML
    private GridPane gridPaneJuego;

    private Jugador jugador;
    private Mapa mapa;

    @FXML
    public void initialize() {

        // Obtiene el jugador e inserta sus stats en la escena
        System.out.println("INICIALIZANDO JUEGO CONTROLLER");
        jugador = Proveedor.getInstance().getJugador();
        System.out.println("Jugador en JuegoController: " + jugador);
        if (jugador != null) {
            jugador.subscribe(this);
            actualizarStats();
        } else {
            System.out.println("Jugador es null en JuegoController");
        }

        // PONER LA RUTA BIEN, IMPLEMENTAR EN LA RUTA BASE
        Proveedor.getInstance()
                .cargarEnemigosDesdeJson("mazmorra/src/main/resources/com/mazmorra/Enemigos/enemigo1.json");

        List<Personaje> personajes = Proveedor.getInstance().getListaDePersonajesIncluyendoJugador();
        mostrarpersonajesPorVelocidad(personajes);

        try {
            List<Map<String, Object>> enemigos = DataReader
                    .leerJson("mazmorra/src/main/resources/com/mazmorra/Enemigos/enemigo1.json");
            for (Map<String, Object> enemigo : enemigos) {
                String nombre = ((String) enemigo.get("nombre")).toUpperCase();
                int vida = (int) enemigo.get("vida");
                int ataque = (int) enemigo.get("ataque");
                int velocidad = (int) enemigo.get("velocidad");

                switch (nombre) {
                    case "CTHULU":
                        vidaCthulu.setText(String.valueOf(vida));
                        ataqueCthulu.setText(String.valueOf(ataque));
                        velocidadCthulu.setText(String.valueOf(velocidad));
                        break;
                    case "MINOTAURO":
                        vidaMino.setText(String.valueOf(vida));
                        ataqueMino.setText(String.valueOf(ataque));
                        velocidadMino.setText(String.valueOf(velocidad));
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo enemigos: " + e.getMessage());
        }

        cargarMapa();
        cargarTablero();
    }

    @Override
    public void onChange() {
        actualizarStats(); // Actualiza la UI cuando hay un cambio en el jugador
    }

    private void actualizarStats() {
        System.out.println("Actualizando stats en JuegoController");
        if (jugador != null) {
            System.out.println("Vida del jugador en JuegoController: " + jugador.getVida());
            vidaJugador.setText(String.valueOf(jugador.getVida()));
        } else {
            System.out.println("Jugador es null en actualizarStats()");
        }
        if (jugador != null) {
            nombreJugador.setText(jugador.getNombre());
            vidaJugador.setText(String.valueOf(jugador.getVida()));
            ataqueJugador.setText(String.valueOf(jugador.getAtaque()));
            defensaJugador.setText(String.valueOf(jugador.getDefensa()));
            velocidadJugador.setText(String.valueOf(jugador.getVelocidad()));
            if (jugador.getImagen() != null) {
                imagenJugador.setImage(jugador.getImagen());
                imagenJugador.setFitWidth(32);
                imagenJugador.setFitHeight(32);
            }
        }
    }

    private void mostrarpersonajesPorVelocidad(List<Personaje> personajes) {
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