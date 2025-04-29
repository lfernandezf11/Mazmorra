package com.mazmorra.Controllers;

import com.mazmorra.Interfaces.Observer;
import com.mazmorra.Model.Personaje;
import com.mazmorra.Model.Proveedor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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

    private Personaje personaje;

    @FXML
    public void initialize() {
        // Obtener instancia del personaje
        personaje = Proveedor.getInstance().getPersonaje();

        // Suscribirse a cambios
        personaje.suscribe(this);

        // Mostrar datos iniciales
        actualizarDatos();
    }

    @Override
    public void onChange() {
        actualizarDatos(); // Actualizar UI cuando hay cambios
    }

    private void actualizarDatos() {
        if (personaje != null) {
            vidaJugador.setText("Vida: " + personaje.getVida());
            ataqueJugador.setText("Ataque: " + personaje.getAtaque());
            defensaJugador.setText("Defensa: " + personaje.getDefensa());
        }
    }
}