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

    //Aquí no se puede inicializar un Jugador parametrizado, puesto que el constructor intentaría 
    //obtener el tipo para calcular la velocidad antes de haber sido asignado.
    private Proveedor() { 
        this.enemigos = new ArrayList<>();
    }

    public Jugador getJugador() {
        return this.jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public List<Enemigo> getListaEnemigos() {
        return enemigos;
    }

    public List<Personaje> getListaDePersonajesIncluyendoJugador() {
        List<Personaje> lista = new ArrayList<>();
        if (jugador != null) {
            lista.add(jugador);
        }
    
        if (enemigos != null && !enemigos.isEmpty()) {
            lista.addAll(enemigos);
        }
        return lista;
    }

    public void setEnemigos(List<Enemigo> enemigos) {
        this.enemigos = enemigos;
        
    }

}