package com.mazmorra.Model;

import com.mazmorra.TipoJugador;

public class Enemigo extends Personaje {

    private int percepcion;

    public Enemigo(String nombre, int ataque, int defensa, int vida, TipoJugador tipo, String rutaImagen, int percepcion) {
        super(nombre, ataque, defensa, vida, tipo, rutaImagen);
        this.percepcion = percepcion;
    }

    public int getPercepcion() { return percepcion; }
    public void setPercepcion(int percepcion) { this.percepcion = percepcion; }

    @Override
    public String toString() {
        return super.toString().replace("}", "") +
               ", percepcion='" + percepcion + "'" +
               "}";
    }
}