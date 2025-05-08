package com.mazmorra.Model;


/**
  * Almacena cada entidad capaz de interactuar con el Mapa junto con su posición en los ejes horizontal columna vertical.
  * Habilita el registro columna actualización del posicionamiento de los personajes durante la partida.
  *
  * @author Miguel González Seguro
  * @author Lucía Fernández Florencio
  * 
  */
public class Entidad {
    private Object entidad ; 
    private int fila;
    private int columna;

    /*Constructor parametrizado */
    public Entidad(Object entidad, int fila, int columna) {
        this.entidad = entidad;
        this.fila = fila;
        this.columna = columna;
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

    public int getfila() {
        return fila;
    }

    public void setfila(int fila) {
        this.fila = fila;
    }

    public int getcolumna() {
        return columna;
    }

    public void setcolumna(int columna) {
        this.columna = columna;
    }

    // public Image getImagen() {
    //     if (entidad instanceof Personaje) {
    //         return ((Personaje) entidad).getRutaImagen();
    //     } else if (entidad instanceof Enemigo) {
    //         return ((Enemigo) entidad).getRutaImagen();
    //     }
    //     // Agrega más instanceof según los tipos que necesites
    //     return null;
    // }
}

