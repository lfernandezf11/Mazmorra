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
            jugador.setImagen("/com/mazmorra/Images/maga/magaAbajo.png");
            mostrarStats();
        });

        imagenGuerrero.setOnMouseClicked(e -> {
            jugador.setImagen("/com/mazmorra/Images/guerrero/guerreroAbajo.png");
            mostrarStats();
        });

        imagenElfo.setOnMouseClicked(e -> {
            jugador.setImagen("/com/mazmorra/Images/arquero/arqueroAbajo.png");
            mostrarStats();
        });

        // Obtiene la instancia del Proveedor
        Proveedor proveedor = Proveedor.getInstance();

        // Inicialización con 5 puntos en vida
        jugador = new Jugador("", 0, 0, 5, null, TipoJugador.MAGO, 0);
        proveedor.setJugador(jugador); // Guarda en el Proveedor
        jugador.subscribe(this);

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
            Proveedor.getInstance().setJugador(jugador); // Actualiza el singleton
        });

        restVida.setOnAction(e -> {
            jugador.decrementarVida();
            Proveedor.getInstance().setJugador(jugador);
        });

        addAtaque.setOnAction(e -> {
            jugador.incrementarAtaque();
            Proveedor.getInstance().setJugador(jugador); // Actualiza el singleton
        });
        restAtaque.setOnAction(e -> {
            jugador.decrementarAtaque();
            Proveedor.getInstance().setJugador(jugador);
        });

        addDefensa.setOnAction(e -> {
            jugador.incrementarDefensa();
            Proveedor.getInstance().setJugador(jugador); // Actualiza el singleton
        });
        restDefensa.setOnAction(e -> {
            jugador.decrementarDefensa();
            Proveedor.getInstance().setJugador(jugador);
        });

        addVelocidad.setOnAction(e -> {
            jugador.incrementarVelocidad();
            Proveedor.getInstance().setJugador(jugador);
        });

        restVelocidad.setOnAction(e -> {
            jugador.decrementarVelocidad();
            Proveedor.getInstance().setJugador(jugador);
        });

        iniciarJuego.setOnAction(e -> {
            // Guarda cambios finales antes de cambiar
            jugador.setNombre(introNombre.getText());
            Proveedor.getInstance().setJugador(jugador);
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