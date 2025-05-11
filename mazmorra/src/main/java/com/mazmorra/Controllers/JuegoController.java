package com.mazmorra.Controllers;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.mazmorra.Interfaces.Observer;
import com.mazmorra.Model.Enemigo;
import com.mazmorra.Model.Jugador;
import com.mazmorra.Model.Mapa;
import com.mazmorra.Model.Personaje;
import com.mazmorra.Model.Proveedor;
import com.mazmorra.Util.DataReader;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
    private static final String rutaBase = "mazmorra/src/main/resources/com/mazmorra/";

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
    private ImageView imagenMasMasLento;
    @FXML
    private ImageView imagenEnemigo1;
    @FXML
    private ImageView imagenEnemigo2;
    @FXML
    private ImageView imagenEnemigo3;
    @FXML
    private Label vidaEnemigo1;
    @FXML
    private Label ataqueEnemigo1;
    @FXML
    private Label velocidadEnemigo1;
    @FXML
    private Label vidaEnemigo2;
    @FXML
    private Label ataqueEnemigo2;
    @FXML
    private Label velocidadEnemigo2;
    @FXML
    private Label vidaEnemigo3;
    @FXML
    private Label ataqueEnemigo3;
    @FXML
    private Label velocidadEnemigo3;
    @FXML
    private Label masVelocidad;
    @FXML
    private Label velocidadMedia;
    @FXML
    private Label masLento;
    @FXML
    private Label masMasLento;

    @FXML
    private StackPane stackPaneJuego;
    @FXML
    private GridPane gridPaneJuego;
    @FXML
    private GridPane gridPanePersonajes;

    private Jugador jugador;
    private List<Enemigo> enemigos;
    private Mapa mapa;

    @FXML
    public void initialize() {

        // Obtiene el jugador e inserta sus stats en la escena
        System.out.println("INICIALIZANDO JUEGO CONTROLLER");
        jugador = Proveedor.getInstance().getJugador();
        System.out.println("Jugador en JuegoController: " + jugador);
        if (jugador.getRutaImagen() != null) {
            imagenJugador.setImage(cargarImagenJugador(jugador.getRutaImagen()));
            jugador.subscribe(this);
            actualizarStats();
        }
       
        enemigos = DataReader.leerJsonEnemigos(rutaBase + "/Enemigos/enemigo1.json");
        Proveedor.getInstance().setEnemigos(enemigos);
        System.out.println("📦 Enemigos cargados: " + enemigos.size());
for (Enemigo enemigo : enemigos) {
    System.out.println("🧟 " + enemigo.getNombre() + " - " + enemigo.getRutaImagen());
}

        List<Personaje> personajes = Proveedor.getInstance().getListaDePersonajesIncluyendoJugador();
        mostrarpersonajesPorVelocidad(personajes);


           

        cargarMapa();
        dibujarTablero();

        stackPaneJuego.setFocusTraversable(true);

        stackPaneJuego.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    mapa.moverJugador(0, -1, gridPanePersonajes, stackPaneJuego);
                    stackPaneJuego.requestFocus();
                    break;
                case DOWN:
                    mapa.moverJugador(0, 1, gridPanePersonajes, stackPaneJuego);
                    stackPaneJuego.requestFocus();
                    break;
                case LEFT:
                    mapa.moverJugador(-1, 0, gridPanePersonajes, stackPaneJuego);
                    stackPaneJuego.requestFocus();
                    break;
                case RIGHT:
                    mapa.moverJugador(1, 0, gridPanePersonajes, stackPaneJuego);
                    stackPaneJuego.requestFocus();
                    break;
                default:
                    break;
            }
        });
        stackPaneJuego.requestFocus(); // Para que el stackPane reciba el foco y capture las teclas
    }

    @Override
    public void onChange() {
        actualizarStats(); // Actualiza la UI cuando hay un cambio en el jugador
    }

    private Image cargarImagenJugador(String rutaImagen) {
        return new Image(getClass().getResource(rutaImagen).toExternalForm());

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
            if (jugador.getRutaImagen() != null) {
                imagenJugador.setImage(cargarImagenJugador(jugador.getRutaImagen()));
                imagenJugador.setFitWidth(32);
                imagenJugador.setFitHeight(32);
            }
        }
    }

    private void mostrarpersonajesPorVelocidad(List<Personaje> personajes) {
        personajes.sort(Comparator.comparingInt(Personaje::getVelocidad).reversed());

        if (personajes.size() > 0 && personajes.get(0).getRutaImagen() != null) {
            imagenMasVelocidad.setImage(cargarImagenJugador(personajes.get(0).getRutaImagen()));
            masVelocidad.setText(String.valueOf(personajes.get(0).getVelocidad()));
        }
        if (personajes.size() > 1 && personajes.get(1).getRutaImagen() != null) {
            imagenVelocidadMedia.setImage(cargarImagenJugador(personajes.get(1).getRutaImagen()));
            velocidadMedia.setText(String.valueOf(personajes.get(1).getVelocidad()));
        }
        if (personajes.size() > 2 && personajes.get(2).getRutaImagen() != null) {
            imagenMasLento.setImage(cargarImagenJugador(personajes.get(2).getRutaImagen()));
            masLento.setText(String.valueOf(personajes.get(2).getVelocidad()));
        }
        if (personajes.size() > 3 && personajes.get(3).getRutaImagen() != null) {
            imagenMasMasLento.setImage(cargarImagenJugador(personajes.get(3).getRutaImagen()));
            masMasLento.setText(String.valueOf(personajes.get(3).getVelocidad()));
        }
    }

    /*
     * De momento vamos a trabajar con personaje y dos enemigos, es posible que haya
     * que modificar este trozo
     */

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
        String carpeta = "/Tablero/";
        try {
            int[][] mapaMatriz = DataReader.leerMapa(rutaBase + carpeta + rutaArchivo); // Ruta común declarada como
                                                                                        // variable
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
    private void dibujarTablero() {
        //Ejecuta el primer ciclo de la función para pasar al true el booleano personajesGenerados.
          mapa.dibujarPersonajes(gridPanePersonajes); 
        
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