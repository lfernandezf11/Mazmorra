package com.mazmorra.Model;

import java.util.ArrayList;
import java.util.List;

public class Proveedor {

    private static Proveedor instance;
    private Jugador jugador;
    private List<Enemigo> enemigos;

    public static Proveedor getInstance() {
        if (instance == null) {
            instance = new Proveedor();
        }
        return instance;
    }
    
    private Proveedor(){
        this.jugador = new Jugador("", 0, 0, 0, 0, null, null, 0);
        this.enemigos = new ArrayList<>();
    }

    public Jugador getJugador(){
        return this.jugador;
    }

    public void setJugador(Jugador jugador){
        this.jugador = jugador;
    }
}
