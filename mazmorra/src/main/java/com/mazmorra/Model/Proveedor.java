package com.mazmorra.Model;

import java.util.ArrayList;

public class Proveedor {
    private static Proveedor instance;
    private Personaje personaje;

    public static Proveedor getInstance() {
        if (instance == null) {
            instance = new Proveedor();
        }
        return instance;
    }
    
    private Proveedor(){
        Proveedor personaje = new ArrayList<>();/*????*/
    }

    public Personaje getPersonaje(){
        return this.personaje;
    }

    public void setPerro(Personaje personaje){
        this.personaje = personaje;
    }
}
