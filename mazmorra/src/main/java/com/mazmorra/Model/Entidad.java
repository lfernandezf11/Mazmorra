package com.mazmorra.Model;

import javafx.scene.image.Image;

/**
  * Almacena cada entidad capaz de interactuar con el Mapa junto con su posición en los ejes horizontal y vertical.
  * Habilita el registro y actualización del posicionamiento de los personajes durante la partida.
  *
  * @author Miguel González Seguro
  * @author Lucía Fernández Florencio
  * 
  */
public class Entidad {
    private Object entidad; 
    private int x;
    private int y;

    /*Constructor parametrizado */
    public Entidad(Object entidad, int x, int y) {
        this.entidad = entidad;
        this.x = x;
        this.y = y;
    }

    /**
     * Devuelve la entidad asociada a un objeto del mapa.
     * 
     * @return el objeto del juego que representa la entidad 'Personaje' o 'Enemigo'
     * 
     */
    public Object getentidad() {
        return entidad;
    }

    public void setentidad(Object entidad) {
        this.entidad = entidad;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImagen() {
        if (entidad instanceof Personaje) {
            return ((Personaje) entidad).getImagen();
        } else if (entidad instanceof Enemigo) {
            return ((Enemigo) entidad).getImagen();
        }
        // Agrega más instanceof según los tipos que necesites
        return null;
    }
}

