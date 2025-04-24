package com.mazmorra.Controllers;

import com.mazmorra.SceneID;
import com.mazmorra.SceneManager;
import com.mazmorra.Interfaces.Observer;
import com.mazmorra.Model.Personaje;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PersonajeController implements Observer {

    protected int puntosDisponibles = 5;
    protected int vida = 5;
    protected int ataque = 0;
    protected int defensa = 0;
    protected int velocidad = 0;

    @FXML
    private Label puntosVida;
    @FXML
    private Label puntosAtaque;
    @FXML
    private Label puntosDefensa;
    @FXML
    private Label puntosVelocidad;
    @FXML
    private Label puntosRestantes;
    @FXML
    private Button iniciarJuego;
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

    private Personaje personajeUno;

    @FXML
    public void initialize() {
        personajeUno = new Personaje(ataque, defensa, vida, velocidad);
        personajeUno.suscribe(this);
        onChange();

        iniciarJuego.setOnAction(event -> {
            SceneManager.getInstance().loadScene(SceneID.JUEGO);
        });

        addVida.setOnAction(event -> {
            personajeUno.setVida(personajeUno.getVida()+1);
            puntosVida.setText(String.valueOf(personajeUno.getVida()));
            personajeUno.setPuntosRestantes(personajeUno.getPuntosRestantes()-1);
            puntosRestantes.setText(String.valueOf(personajeUno.getPuntosRestantes()));
        });

        restVida.setOnAction(event -> {
            personajeUno.setVida(personajeUno.getVida()-1);
            puntosVida.setText(String.valueOf(personajeUno.getVida()));
            personajeUno.setPuntosRestantes(personajeUno.getPuntosRestantes()+1);
            puntosRestantes.setText(String.valueOf(personajeUno.getPuntosRestantes()));
        });

        addAtaque.setOnAction(event -> {
            personajeUno.setAtaque(personajeUno.getAtaque() + 1);
            personajeUno.setPuntosRestantes(personajeUno.getPuntosRestantes() - 1);
            
            personajeUno.notifyObservers();
        });

        restAtaque.setOnAction(event -> {
            personajeUno.setAtaque(personajeUno.getAtaque()-1);
            puntosAtaque.setText(String.valueOf(personajeUno.getAtaque()));
            personajeUno.setPuntosRestantes(personajeUno.getPuntosRestantes()+1);
            puntosRestantes.setText(String.valueOf(personajeUno.getPuntosRestantes()));
        });

        addDefensa.setOnAction(event -> {
            personajeUno.setDefensa(personajeUno.getDefensa()+1);
            puntosDefensa.setText(String.valueOf(personajeUno.getDefensa()));
            personajeUno.setPuntosRestantes(personajeUno.getPuntosRestantes()-1);
            puntosRestantes.setText(String.valueOf(personajeUno.getPuntosRestantes()));
        });

        restDefensa.setOnAction(event -> {
            personajeUno.setDefensa(personajeUno.getDefensa()-1);
            puntosDefensa.setText(String.valueOf(personajeUno.getDefensa()));
            personajeUno.setPuntosRestantes(personajeUno.getPuntosRestantes()+1);
            puntosRestantes.setText(String.valueOf(personajeUno.getPuntosRestantes()));
        });

        addVelocidad.setOnAction(event -> {
            personajeUno.setVelocidad(personajeUno.getVelocidad()+1);
            puntosVelocidad.setText(String.valueOf(personajeUno.getVelocidad()));
            personajeUno.setPuntosRestantes(personajeUno.getPuntosRestantes()-1);
            puntosRestantes.setText(String.valueOf(personajeUno.getPuntosRestantes()));
        });

        restVelocidad.setOnAction(event -> {
            personajeUno.setVelocidad(personajeUno.getVelocidad()-1);
            puntosVelocidad.setText(String.valueOf(personajeUno.getVelocidad()));
            personajeUno.setPuntosRestantes(personajeUno.getPuntosRestantes()+1);
            puntosRestantes.setText(String.valueOf(personajeUno.getPuntosRestantes()));
        });
    }

    @Override
    public void onChange() {
        personajeUno.setVida(personajeUno.getVida());
    }
}