package com.mazmorra.Model;


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
        this.personaje = new Personaje("", 0, 0, 0, 0);
    }

    public Personaje getPersonaje(){
        return this.personaje;
    }

    public void setPersonaje(Personaje personaje){
        this.personaje = personaje;
    }
}
