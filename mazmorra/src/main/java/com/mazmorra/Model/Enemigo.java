package com.mazmorra.Model;

public class Enemigo {
    private String nombre;
    private int ataque;
    private int vida;
    private int defensa;
    private int velocidad;
    private int percepcion;

    public Enemigo(String nombre, int ataque, int vida, int defensa, int velocidad, int percepcion) {
        this.nombre = nombre;
        this.ataque = ataque;
        this.vida = vida;
        this.defensa = defensa;
        this.velocidad = velocidad;
        this.percepcion = percepcion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAtaque() {
        return this.ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getVida() {
        return this.vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getDefensa() {
        return this.defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getVelocidad() {
        return this.velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getPercepcion() {
        return this.percepcion;
    }

    public void setPercepcion(int percepcion) {
        this.percepcion = percepcion;
    }

    @Override
    public String toString() {
        return "{" +
                " nombre='" + getNombre() + "'" +
                ", ataque='" + getAtaque() + "'" +
                ", vida='" + getVida() + "'" +
                ", defensa='" + getDefensa() + "'" +
                ", velocidad='" + getVelocidad() + "'" +
                ", percepcion='" + getPercepcion() + "'" +
                "}";
    }

}