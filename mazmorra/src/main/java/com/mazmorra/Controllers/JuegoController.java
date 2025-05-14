package com.mazmorra.Controllers;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import com.mazmorra.SceneID;
import com.mazmorra.SceneManager;
import com.mazmorra.Interfaces.Observer;
import com.mazmorra.Model.Enemigo;
import com.mazmorra.Model.Jugador;
import com.mazmorra.Model.Mapa;
import com.mazmorra.Model.Personaje;
import com.mazmorra.Model.Proveedor;
import com.mazmorra.Util.DataReader;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * Controlador principal del juego.
 * <p>
 * Se encarga de inicializar la escena, gestionar los turnos entre el jugador y los enemigos,
 * actualizar la interfaz gráfica (stats, imágenes y etiquetas) y controlar el estado general
 * de la partida (victoria, derrota).
 * </p>
 * 
 * <p>
 * Implementa la interfaz Observer para poder actualizar dinámicamente la vista
 * cuando el modelo (jugador) sufre cambios.
 * </p>
 * 
 * <p>
 * Este controlador gestiona las acciones del usuario mediante eventos de teclado y 
 * coordina la lógica de combate por turnos.
 * </p>
 * 
 * @author Miguel González Seguro
 * @author Lucía Fernández Florencio
 */
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
    private Label teTocaTurno;

    @FXML
    private StackPane stackPaneJuego;
    @FXML
    private GridPane gridPaneJuego;
    @FXML
    private GridPane gridPanePersonajes;

    private Jugador jugador = Proveedor.getInstance().getJugador();
    private List<Enemigo> enemigos = Proveedor.getInstance().getListaEnemigos();
    private List<Personaje> personajes;
    private Mapa mapa;
    private int indiceTurnoActual = 0;
    private List<Personaje> personajesPorTurno;
    private boolean juegoTerminado = false;


    /**
     * Inicializa la escena de juego.
     * 
     * <p>
     * Carga los datos del jugador y enemigos, construye el mapa, genera posiciones iniciales,
     * y asigna los eventos de teclado para mover al jugador. También organiza los personajes por velocidad
     * para ejecutar el sistema de turnos.
     * </p>
     */
    @FXML
    public void initialize() {

        // Obtiene el jugador e inserta sus stats en la escena
        if (jugador.getRutaImagen() != null) {
            imagenJugador.setImage(cargarImagenJugador(jugador.getRutaImagen()));
            jugador.subscribe(this); // Suscribe el controlador al jugador para actualizar la UI si cambia
            actualizarStats();
        }

        // Carga los enemigos desde JSON y los almacena en el proveedor
        enemigos = DataReader.leerJsonEnemigos(rutaBase + "/Enemigos/enemigo1.json");
        Proveedor.getInstance().setEnemigos(enemigos);

        // Obtiene la lista de personajes 
        personajes = Proveedor.getInstance().getListaDePersonajesIncluyendoJugador();

        cargarStatsEnemigos(enemigos); // Inicializa la UI de enemigos
        mostrarpersonajesPorVelocidad(personajes); // Muestra las miniaturas según velocidad

        cargarMapa(); // Carga la matriz del mapa desde archivo
        mapa.generarPosicionesIniciales(); // Coloca personajes aleatoriamente
        dibujarTablero(); // Dibuja el tablero visualmente
        mapa.dibujarPersonajes(gridPanePersonajes); // Dibuja personajes sobre el tablero

        stackPaneJuego.setFocusTraversable(true); // Permite que el StackPane reciba eventos de teclado

        // Asigna comportamiento al presionar teclas si es el turno del jugador
        stackPaneJuego.setOnKeyPressed(event -> {
            Personaje actual = personajesPorTurno.get(indiceTurnoActual);
            if (actual instanceof Jugador) {
                switch (event.getCode()) {
                    case UP:
                        mapa.moverJugador(0, -1, gridPanePersonajes, stackPaneJuego);
                        break;
                    case DOWN:
                        mapa.moverJugador(0, 1, gridPanePersonajes, stackPaneJuego);
                        break;
                    case LEFT:
                        mapa.moverJugador(-1, 0, gridPanePersonajes, stackPaneJuego);
                        break;
                    case RIGHT:
                        mapa.moverJugador(1, 0, gridPanePersonajes, stackPaneJuego);
                        break;
                    default:
                        break;
                }
                actualizarStatsTodos(); // Actualiza toda la interfaz tras el movimiento y pasa de turno
                siguienteTurno(); 
                if (mapa.esUnaTrampa()) {
                    jugador.setVida(jugador.getVida()-1);
                }
            }
            stackPaneJuego.requestFocus(); // Mantiene el foco en el StackPane tras pulsar tecla
        });

        stackPaneJuego.requestFocus(); // Asegura que el StackPane tenga el foco inicial

        // Ordena personajes por velocidad para gestionar los turnos
        personajesPorTurno = Proveedor.getInstance().getListaDePersonajesIncluyendoJugador();
        personajesPorTurno.sort(Comparator.comparingInt(Personaje::getVelocidad).reversed());

        indiceTurnoActual = -1; // Inicializa el índice de turno antes del primer avance
        siguienteTurno(); // Comienza el primer turno del combate
        actualizarLabelTurno(); // Muestra mensaje de "¡Tu turno!" si es necesario
    }


    /**
     * Lógica que se ejecuta cuando el jugador (modelo) notifica un cambio.
     * Actualiza los valores mostrados en la interfaz de usuario.
     */
    @Override
    public void onChange() {
        actualizarStats(); // Actualiza la UI cuando hay un cambio en el modelo (Jugador)
    }


    /**
     * Avanza al siguiente personaje en el orden de turnos.
     * 
     * - Si es el turno del jugador, se espera entrada por teclado.
     * - Si es el turno de un enemigo, se lanza un movimiento automático tras un breve delay.
     */
    private void siguienteTurno() {
        if (juegoTerminado) return; // Evita seguir si la partida terminó

        // Elimina personajes muertos de la lista de turnos
        personajesPorTurno.removeIf(personaje -> personaje.getVida() <= 0);

        // Calcula el índice del siguiente personaje en la lista
        indiceTurnoActual = (indiceTurnoActual + 1) % personajesPorTurno.size();
        Personaje actual = personajesPorTurno.get(indiceTurnoActual);

        actualizarLabelTurno(); // Muestra o esconde el cartel de turno

        if (actual instanceof Jugador) {
            stackPaneJuego.requestFocus(); // Espera a que el jugador actúe manualmente
        } else {
            // Activa movimiento enemigo con un pequeño retardo
            PauseTransition pause = new PauseTransition(Duration.seconds(0.1));
            pause.setOnFinished(event -> {
                mapa.moverEnemigo((Enemigo) actual, gridPanePersonajes, stackPaneJuego);
                actualizarStatsTodos(); // Actualiza interfaz tras actuar enemigo
                siguienteTurno(); // Llama al siguiente turno automáticamente
            });
            pause.play(); // Inicia la pausa
        }
    }
    
    
     /**
     * Actualiza la interfaz con los valores actuales del jugador y los enemigos.
     * 
     * También verifica si se ha alcanzado una condición de fin de partida,
     * como la derrota del jugador o la victoria por eliminación o llegada a la escalera.
     */
    private void actualizarStatsTodos() {
        if (juegoTerminado)
            return;

        actualizarStats(); // Refresca stats del jugador
        cargarStatsEnemigos(enemigos); // Refresca stats de enemigos

        // Verifica si el jugador ha sido derrotado
        if (jugador.getVida() <= 0) {
            juegoTerminado = true;
            SceneManager.getInstance().setScene(SceneID.YOULOSE, "youlose");
            SceneManager.getInstance().loadScene(SceneID.YOULOSE);
            return; // Termina ejecución del método
        }

        // Verifica condición de victoria: sin enemigos o en la escalera
        if (personajesPorTurno.stream().noneMatch(p -> p instanceof Enemigo) || mapa.estaEnLaEscalera()) {
            juegoTerminado = true;
            SceneManager.getInstance().setScene(SceneID.YOUWIN, "youwin");
            SceneManager.getInstance().loadScene(SceneID.YOUWIN);
        }
    }


    /**
     * Muestra u oculta el mensaje de "¡Tu turno!" dependiendo del turno actual.
     */
    private void actualizarLabelTurno() {
        Personaje actual = personajesPorTurno.get(indiceTurnoActual);
        if (actual instanceof Jugador) {
            teTocaTurno.setVisible(true);
            teTocaTurno.setText("¡Tu turno!");
        } else {
            teTocaTurno.setVisible(false);
            teTocaTurno.setText("");
        }
    }


    /**
     * Carga una imagen desde la ruta proporcionada.
     *
     * @param rutaImagen ruta del recurso dentro del classpath.
     * @return imagen de tipo Image generada a partir del recurso.
     */
    private Image cargarImagenJugador(String rutaImagen) {
        return new Image(getClass().getResource(rutaImagen).toExternalForm());
    }


    /**
     * Actualiza los valores mostrados en pantalla asociados al jugador:
     * nombre, vida, ataque, defensa, velocidad e imagen.
     */
    private void actualizarStats() {
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

    /*
     * Lo ideal para las stats de enemigos es establecer un grid en la escena y asignar los valores a las
     * celdas, pero esto es un arreglo más fino, para
     * no determinar una a una cada View y cada Label.
     */

    /**
     * Inserta en la interfaz los datos de los enemigos activos.
     * Asigna imágenes y estadísticas en las posiciones correspondientes.
     *
     * @param enemigos lista de enemigos a representar gráficamente.
     */
    private void cargarStatsEnemigos(List<Enemigo> enemigos) {
        List<ImageView> imagenes = List.of(imagenEnemigo1, imagenEnemigo2, imagenEnemigo3);
        List<Label> vidas = List.of(vidaEnemigo1, vidaEnemigo2, vidaEnemigo3);
        List<Label> ataques = List.of(ataqueEnemigo1, ataqueEnemigo2, ataqueEnemigo3);
        List<Label> velocidades = List.of(velocidadEnemigo1, velocidadEnemigo2, velocidadEnemigo3);

        for (int i = 0; i < imagenes.size(); i++) {
            if (i < enemigos.size()) {
                Enemigo enemigo = enemigos.get(i);
                imagenes.get(i).setImage(cargarImagenJugador(enemigo.getRutaImagen()));
                vidas.get(i).setText(String.valueOf(enemigo.getVida()));
                ataques.get(i).setText(String.valueOf(enemigo.getAtaque()));
                velocidades.get(i).setText(String.valueOf(enemigo.getVelocidad()));
                imagenes.get(i).setOpacity(1.0); // Por si antes estaba "tachado"
            } else {
                imagenes.get(i).setImage(null);
                vidas.get(i).setText("");
                ataques.get(i).setText("");
                velocidades.get(i).setText("");
            }
        }
    }


    /**
     * Ordena los personajes por velocidad descendente y actualiza
     * las miniaturas de la sección lateral de la vista, indicando quién es más rápido.
     *
     * @param personajes lista completa de personajes, incluyendo al jugador.
     */
    private void mostrarpersonajesPorVelocidad(List<Personaje> personajes) {
    personajes.sort(Comparator.comparingInt(Personaje::getVelocidad).reversed());

    List<ImageView> imagenesVelocidad = List.of(imagenMasVelocidad, imagenVelocidadMedia, imagenMasLento, imagenMasMasLento);
    List<Label> etiquetasVelocidad = List.of(masVelocidad, velocidadMedia, masLento, masMasLento);

    for (int i = 0; i < imagenesVelocidad.size(); i++) {
        if (i < personajes.size() && personajes.get(i).getRutaImagen() != null) {
            imagenesVelocidad.get(i).setImage(cargarImagenJugador(personajes.get(i).getRutaImagen()));
            etiquetasVelocidad.get(i).setText(String.valueOf(personajes.get(i).getVelocidad()));
            imagenesVelocidad.get(i).setOpacity(1.0); // Por si acaso se oculta visualmente
        } else {
            imagenesVelocidad.get(i).setImage(null);
            etiquetasVelocidad.get(i).setText("");
        }
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
        String carpeta = "/Tablero/";
        try {
            int[][] mapaMatriz = DataReader.leerMapa(rutaBase + carpeta + rutaArchivo); // Ruta común declarada como
                                                                                        // variable estática de la
                                                                                        // clase.
            mapa = new Mapa(mapaMatriz);
        } catch (IOException e) { // Como el método trabaja con archivos, hay que capturar errores de
                                  // entrada/salida (IO)
            System.err.println("Error en la lectura del archivo.\n" + e.getMessage());
        }
    }

    /**
     * Asegura que el método initialize del controlador del juego se haya ejecutado
     * antes de generar la interfaz gráfica para el tablero.
     * 
     * <p>
     * El objetivo es que las propiedades de los nodos ya estén establecidas antes
     * de extraerlas y operar sobre ellos.<br><br>
     * 
     * También añade un listener para que el tablero se regenere dinámicamente
     * si el ancho del contenedor cambia (responsive design).
     * </p>
     */
    private void dibujarTablero() {
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