package com.mazmorra.Model;

import com.mazmorra.TipoJugador;

public class Enemigo extends Personaje {

    private int percepcion;
    private int posicionX;
    private int posicionY;

    public Enemigo(String nombre, int ataque, int defensa, int vida, TipoJugador tipo, String rutaImagen,
            int percepcion) {
        super(nombre, ataque, defensa, vida, tipo, rutaImagen);
        this.percepcion = percepcion;
    }

    public int getPercepcion() {
        return percepcion;
    }

    public void setPercepcion(int percepcion) {
        this.percepcion = percepcion;
    }

    @Override
    public String toString() {
        return super.toString().replace("}", "") +
                ", percepcion='" + percepcion + "'" +
                "}";
    }

    public int getPosicionX() {
        return posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicion(int x, int y) {
        this.posicionX = x;
        this.posicionY = y;
    }
}