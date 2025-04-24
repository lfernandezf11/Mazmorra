package com.mazmorra.Controllers;

import java.io.IOException;
import com.mazmorra.App;
import com.mazmorra.Model.Personaje;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PersonajeController {
    private Personaje personaje;
    protected int puntosDisponibles = 5;
    protected int vida = 5;
    protected int ataque = 0;
    protected int defensa = 0;
    protected int velocidad = 0;

    @FXML
    private Label vidaLabel;
    @FXML
    private Label ataqueLabel;
    @FXML
    private Label defensaLabel;
    @FXML
    private Label velocidadLabel;
    @FXML
    private Label puntosRestantes;

    @FXML
    public void initialize() {
        actualizarStats(vida, ataque, defensa, velocidad, puntosDisponibles);

    }

    private void actualizarStats(int vida, int ataque, int defensa, int velocidad, int puntosDisponibles) {
        vidaLabel.setText("Vida: " + vida);
        ataqueLabel.setText("Ataque: " + ataque);
        defensaLabel.setText("Defensa: " + defensa);
        velocidadLabel.setText("Velocidad: " + velocidad);
        puntosRestantes.setText("Puntos disponibles: " + puntosDisponibles);
    }

    @FXML
    private void addVida() {
        if (puntosDisponibles > 0) {
            vida++;
            puntosDisponibles--;
            actualizarStats(vida, ataque, defensa, velocidad, puntosDisponibles);
        }
    }

    @FXML
    private void restVida() {
        if (vida > 5) {
            vida--;
            puntosDisponibles++;
            actualizarStats(vida, ataque, defensa, velocidad, puntosDisponibles);
        }
    }

    @FXML
    private void addAtaque() {
        if (puntosDisponibles > 0) {
            ataque++;
            puntosDisponibles--;
            actualizarStats(vida, ataque, defensa, velocidad, puntosDisponibles);
        }
    }

    @FXML
    private void restAtaque() {
        if (ataque > 0) {
            ataque--;
            puntosDisponibles++;
            actualizarStats(vida, ataque, defensa, velocidad, puntosDisponibles);
        }
    }

    @FXML
    private void addDefensa() {
        if (puntosDisponibles > 0) {
            defensa++;
            puntosDisponibles--;
            actualizarStats(vida, ataque, defensa, velocidad, puntosDisponibles);
        }
    }

    @FXML
    private void restDefensa() {
        if (defensa > 0) {
            defensa--;
            puntosDisponibles++;
            actualizarStats(vida, ataque, defensa, velocidad, puntosDisponibles);
        }
    }

    @FXML
    private void addVel() {
        if (puntosDisponibles > 0) {
            velocidad++;
            puntosDisponibles--;
            actualizarStats(vida, ataque, defensa, velocidad, puntosDisponibles);
        }
    }

    @FXML
    private void restVel() {
        if (velocidad > 0) {
            velocidad--;
            puntosDisponibles++;
            actualizarStats(vida, ataque, defensa, velocidad, puntosDisponibles);
        }
    }

}