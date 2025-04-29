package com.mazmorra.Controllers;

import com.mazmorra.SceneID;
import com.mazmorra.SceneManager;
import com.mazmorra.Interfaces.Observer;
import com.mazmorra.Model.Personaje;
import com.mazmorra.Model.Proveedor;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PersonajeController implements Observer {

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

    private Personaje personajeUno;

    @FXML
    public void initialize() {

        // Obtiene la instancia del Proveedor
        Proveedor proveedor = Proveedor.getInstance();

        // Inicialización con 5 puntos en vida
        personajeUno = new Personaje(0, 0, 5, 0);
        proveedor.setPersonaje(personajeUno); // Guarda en el Proveedor
        personajeUno.suscribe(this);

        configurarBotones();
        actualizarPersonaje();
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