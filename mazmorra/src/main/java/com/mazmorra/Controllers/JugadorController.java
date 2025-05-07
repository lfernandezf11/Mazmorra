package com.mazmorra.Controllers;

import com.mazmorra.SceneID;
import com.mazmorra.SceneManager;
import com.mazmorra.TipoJugador;
import com.mazmorra.Interfaces.Observer;
import com.mazmorra.Model.Jugador;
import com.mazmorra.Model.Proveedor;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Controlador para la escena de configuración del jugador.
 * 
 * Permite al usuario introducir su nombre, elegir el tipo de personaje (maga,
 * guerrero, arquero),
 * y distribuir 5 puntos iniciales entre las estadísticas de vida, ataque,
 * defensa y velocidad.
 * 
 * Implementa la interfaz Observer para plasmar en la escena los cambios en la
 * entidad Jugador de forma dinámica.
 */

public class JugadorController implements Observer {

    @FXML
    private Label Vida;
    @FXML
    private Label Ataque;
    @FXML
    private Label Defensa;
    @FXML
    private Label Velocidad;
    @FXML
    private Label pDisponible;
    @FXML
    private Label puntosVida;
    @FXML
    private Label puntosAtaque;
    @FXML
    private Label puntosDefensa;
    @FXML
    private Label puntosVelocidad;
    @FXML
    private Label PuntosRestantes;
    @FXML
    private TextField introNombre;
    @FXML
    private ImageView imagenMago;
    @FXML
    private ImageView imagenGuerrero;
    @FXML
    private ImageView imagenElfo;

    @FXML
    private Button addVida;
    @FXML
    private Button restVida;
    @FXML
    private Button addAtaque;
    @FXML
    private Button restAtaque;
    @FXML
    private Button addDefensa;
    @FXML
    private Button restDefensa;
    @FXML
    private Button addVelocidad;
    @FXML
    private Button restVelocidad;
    @FXML
    private Button iniciarJuego;
    @FXML
    private Button introUno;

    private Jugador jugador;

    /**
     * Método de inicialización automática llamado por JavaFX.
     * Configura el estado inicial de los componentes visuales,
     * suscriptores del modelo, y los eventos de los botones.
     */
    @FXML
    public void initialize() {

        puntosVida.setVisible(false);
        puntosAtaque.setVisible(false);
        puntosDefensa.setVisible(false);
        puntosVelocidad.setVisible(false);
        PuntosRestantes.setVisible(false);
        addVida.setVisible(false);
        restVida.setVisible(false);
        addAtaque.setVisible(false);
        restAtaque.setVisible(false);
        addDefensa.setVisible(false);
        restDefensa.setVisible(false);
        addVelocidad.setVisible(false);
        restVelocidad.setVisible(false);
        iniciarJuego.setVisible(false);
        imagenMago.setVisible(false);
        imagenGuerrero.setVisible(false);
        imagenElfo.setVisible(false);
        pDisponible.setVisible(false);
        Ataque.setVisible(false);
        Defensa.setVisible(false);
        Vida.setVisible(false);
        Velocidad.setVisible(false);

        introUno.setOnAction(e -> {
            if (!introNombre.getText().isEmpty()) {
                // Mostrar las imágenes
                imagenMago.setVisible(true);
                imagenGuerrero.setVisible(true);
                imagenElfo.setVisible(true);
            }
        });

        // Configurar los ImageView para que sean "clickables"
        imagenMago.setOnMouseClicked(e -> {
            crearJugador(TipoJugador.MAGO, "/com/mazmorra/Images/magaAbajo.png");
        });
           
        imagenGuerrero.setOnMouseClicked(e -> {
            crearJugador(TipoJugador.GUERRERO, "/com/mazmorra/Images/guerreroAbajo.png");
        });
           
        imagenElfo.setOnMouseClicked(e -> {
            crearJugador(TipoJugador.ARQUERO, "/com/mazmorra/Images/arqueroAbajo.png");       
        });
            
    }

    /**
     * Genera el Jugador una vez seleccionado el tipo de Personaje (mago, arquero, guerrero).
     * @param tipo
     * @param rutaImagen
     */
    private void crearJugador(TipoJugador tipo, String rutaImagen) {
        jugador = new Jugador(
            introNombre.getText(),
            0, 0, 5, rutaImagen, tipo, 10
        );
        jugador.subscribe(this);
        Proveedor.getInstance().setJugador(jugador); // SOLO AQUÍ
        mostrarStats();
        configurarBotones();
        actualizarPersonaje();
    }
          
    private void mostrarStats() {
        puntosVida.setVisible(true);
        puntosAtaque.setVisible(true);
        puntosDefensa.setVisible(true);
        puntosVelocidad.setVisible(true);
        PuntosRestantes.setVisible(true);
        addVida.setVisible(true);
        restVida.setVisible(true);
        addAtaque.setVisible(true);
        restAtaque.setVisible(true);
        addDefensa.setVisible(true);
        restDefensa.setVisible(true);
        addVelocidad.setVisible(true);
        restVelocidad.setVisible(true);
        iniciarJuego.setVisible(true);
        Ataque.setVisible(true);
        Defensa.setVisible(true);
        Velocidad.setVisible(true);
        Vida.setVisible(true);
        pDisponible.setVisible(true);
    }

    private void configurarBotones() {
        // Configuración usando métodos del modelo
        addVida.setOnAction(e -> {
            jugador.incrementarVida();
        });

        restVida.setOnAction(e -> {
            jugador.decrementarVida();
        });

        addAtaque.setOnAction(e -> {
            jugador.incrementarAtaque();
        });
        restAtaque.setOnAction(e -> {
            jugador.decrementarAtaque();
        });

        addDefensa.setOnAction(e -> {
            jugador.incrementarDefensa();
        });
        restDefensa.setOnAction(e -> {
            jugador.decrementarDefensa();
        });

        addVelocidad.setOnAction(e -> {
            jugador.incrementarVelocidad();
        });

        restVelocidad.setOnAction(e -> {
            jugador.decrementarVelocidad();
        });

        iniciarJuego.setOnAction(e -> {
            jugador.setNombre(introNombre.getText());
            Proveedor.getInstance().setJugador(jugador);
            // Carga y muestra la escena de juego en este momento
            SceneManager.getInstance().setScene(SceneID.JUEGO, "juego");
            SceneManager.getInstance().loadScene(SceneID.JUEGO);
        });
    }

    @Override
    public void onChange() {
        actualizarPersonaje();
    }

    private void actualizarPersonaje() {
        // Sincronización completa de valores
        puntosVida.setText(String.valueOf(jugador.getVida()));
        puntosAtaque.setText(String.valueOf(jugador.getAtaque()));
        puntosDefensa.setText(String.valueOf(jugador.getDefensa()));
        puntosVelocidad.setText(String.valueOf(jugador.getVelocidad()));
        PuntosRestantes.setText(String.valueOf(jugador.getPuntosRestantes()));

        // Gestión de estados de botones (-)
        restVida.setDisable(jugador.getVida() <= 0);
        restAtaque.setDisable(jugador.getAtaque() <= 0);
        restDefensa.setDisable(jugador.getDefensa() <= 0);
        restVelocidad.setDisable(jugador.getVelocidad() <= 0);

        // Gestión de estados de botones (+)
        boolean sinPuntos = jugador.getPuntosRestantes() <= 0;
        addVida.setDisable(sinPuntos);
        addAtaque.setDisable(sinPuntos);
        addDefensa.setDisable(sinPuntos);
        addVelocidad.setDisable(sinPuntos);
    }
}