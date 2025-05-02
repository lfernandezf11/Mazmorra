package com.mazmorra.Controllers;

import com.mazmorra.SceneID;
import com.mazmorra.SceneManager;
import com.mazmorra.Interfaces.Observer;
import com.mazmorra.Model.Personaje;
import com.mazmorra.Model.Proveedor;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class PersonajeController implements Observer {

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

    private Personaje personajeUno;

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
        imagenMago.setOnMouseClicked(e -> mostrarStats());
        imagenGuerrero.setOnMouseClicked(e -> mostrarStats());
        imagenElfo.setOnMouseClicked(e -> mostrarStats());

        // Obtiene la instancia del Proveedor
        Proveedor proveedor = Proveedor.getInstance();

        // Inicialización con 5 puntos en vida
        personajeUno = new Personaje("", 0, 0, 5, 0);
        proveedor.setPersonaje(personajeUno); // Guarda en el Proveedor
        personajeUno.suscribe(this);

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
            personajeUno.incrementarVida();
            Proveedor.getInstance().setPersonaje(personajeUno); // Actualiza el singleton
        });

        restVida.setOnAction(e -> {
            personajeUno.decrementarVida();
            Proveedor.getInstance().setPersonaje(personajeUno);
        });

        addAtaque.setOnAction(e -> {
            personajeUno.incrementarAtaque();
            Proveedor.getInstance().setPersonaje(personajeUno); // Actualiza el singleton
        });
        restAtaque.setOnAction(e -> {
            personajeUno.decrementarAtaque();
            Proveedor.getInstance().setPersonaje(personajeUno);
        });

        addDefensa.setOnAction(e -> {
            personajeUno.incrementarDefensa();
            Proveedor.getInstance().setPersonaje(personajeUno); // Actualiza el singleton
        });
        restDefensa.setOnAction(e -> {
            personajeUno.decrementarDefensa();
            Proveedor.getInstance().setPersonaje(personajeUno);
        });

        addVelocidad.setOnAction(e -> {
            personajeUno.incrementarVelocidad();
            Proveedor.getInstance().setPersonaje(personajeUno);
        });

        restVelocidad.setOnAction(e -> {
            personajeUno.decrementarVelocidad();
            Proveedor.getInstance().setPersonaje(personajeUno);
        });

        iniciarJuego.setOnAction(e -> {
            // Guarda cambios finales antes de cambiar
            personajeUno.setNombre(introNombre.getText());
            Proveedor.getInstance().setPersonaje(personajeUno);
            SceneManager.getInstance().loadScene(SceneID.JUEGO);
        });
    }

    @Override
    public void onChange() {
        actualizarPersonaje();
    }

    private void actualizarPersonaje() {
        // Sincronización completa de valores
        puntosVida.setText(String.valueOf(personajeUno.getVida()));
        puntosAtaque.setText(String.valueOf(personajeUno.getAtaque()));
        puntosDefensa.setText(String.valueOf(personajeUno.getDefensa()));
        puntosVelocidad.setText(String.valueOf(personajeUno.getVelocidad()));
        PuntosRestantes.setText(String.valueOf(personajeUno.getPuntosRestantes()));

        // Gestión de estados de botones (-)
        restVida.setDisable(personajeUno.getVida() <= 0);
        restAtaque.setDisable(personajeUno.getAtaque() <= 0);
        restDefensa.setDisable(personajeUno.getDefensa() <= 0);
        restVelocidad.setDisable(personajeUno.getVelocidad() <= 0);

        // Gestión de estados de botones (+)
        boolean sinPuntos = personajeUno.getPuntosRestantes() <= 0;
        addVida.setDisable(sinPuntos);
        addAtaque.setDisable(sinPuntos);
        addDefensa.setDisable(sinPuntos);
        addVelocidad.setDisable(sinPuntos);
    }
}