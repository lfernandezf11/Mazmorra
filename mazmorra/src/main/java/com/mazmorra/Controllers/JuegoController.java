package com.mazmorra.Controllers;

import com.mazmorra.Interfaces.Observer;
import com.mazmorra.Model.Personaje;
import com.mazmorra.Model.Proveedor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class JuegoController implements Observer {

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

    private Personaje personaje;

    @FXML
    public void initialize() {
        // Obtener instancia del personaje
        personaje = Proveedor.getInstance().getPersonaje();

        // Suscribirse a cambios
        personaje.suscribe(this);
        actualizarStats();
    }

    @Override
    public void onChange() {
        actualizarStats();
    }

    private void actualizarStats() {
        vidaJugador.setText(String.valueOf(personaje.getVida()));
        ataqueJugador.setText(String.valueOf(personaje.getAtaque()));
        defensaJugador.setText(String.valueOf(personaje.getDefensa()));
        velocidadJugador.setText(String.valueOf(personaje.getVelocidad()));
    }
}