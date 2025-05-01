package com.mazmorra.Model;

import java.util.ArrayList;
import java.util.Objects;

import com.mazmorra.TipoPersonaje;
import com.mazmorra.Interfaces.Observer;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Personaje {
    private ArrayList<Observer> observers = new ArrayList<>();

    protected TipoPersonaje tipo;
    protected String nombre;
    protected int ataque; 
    protected int defensa;
    protected int vida;
    protected int velocidad;
    private static int puntosRestantes = 5;

    private Image imagen;
    private ImageView imageView;

    public Personaje(String nombre, int ataque, int defensa, int vida, int velocidad, TipoPersonaje tipo, String rutaImagen) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.ataque = ataque;
        this.defensa = defensa;
        this.vida = vida;
        this.velocidad = velocidad;
        setImagen(rutaImagen); // inicializa imagen e imageView
    }

    public Personaje(String nombre, int ataque, int defensa, int vida, int velocidad) {
        this.nombre = nombre;
        this.ataque = ataque;
        this.defensa = defensa;
        this.vida = vida;
        this.velocidad = velocidad;
    }

    public void setImagen(String rutaImagen) {
        this.imagen = new Image("file:" + rutaImagen); // o usa getClass().getResource() si es un recurso
        this.imageView = new ImageView(this.imagen);
        this.imageView.setFitWidth(32);
        this.imageView.setFitHeight(32);
    }

    public Image getImagen() {
        return this.imagen;
    }

    public int getPuntosRestantes(){
        return Personaje.puntosRestantes;
    }

    public void setPuntosRestantes(int puntosRestantes){
        Personaje.puntosRestantes = puntosRestantes;
        notifyObservers();
    }

    public ImageView getImageView() {
        return this.imageView;
    }

    public void suscribe(Observer observer){
        observers.add(observer);
    }

    public void unsuscribe(Observer observer){
        observers.remove(observer);
    }

    public ArrayList<Observer> getObservers() {
        return this.observers;
    }

    public void setObservers(ArrayList<Observer> observers) {
        this.observers = observers;
    }

    public void notifyObservers(){
        observers.forEach(x -> x.onChange());
    }

    public TipoPersonaje getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoPersonaje tipo) {
        this.tipo = tipo;
        notifyObservers();
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
        notifyObservers();
    }
    
    public int getAtaque() {
        return this.ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
        notifyObservers();
    }

    public int getDefensa() {
        return this.defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
        notifyObservers();
    }

    public int getVida() {
        return this.vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
        notifyObservers();
    }

    public int getVelocidad() {
        return this.velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
        notifyObservers();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Personaje)) {
            return false;
        }
        Personaje personaje = (Personaje) o;
        return Objects.equals(tipo, personaje.tipo) && ataque == personaje.ataque && defensa == personaje.defensa && vida == personaje.vida && velocidad == personaje.velocidad;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo, ataque, defensa, vida, velocidad);
    }

    @Override
    public String toString() {
        return "{" +
            " tipo='" + getTipo() + "'" +
            ", ataque='" + getAtaque() + "'" +
            ", defensa='" + getDefensa() + "'" +
            ", vida='" + getVida() + "'" +
            ", velocidad='" + getVelocidad() + "'" +
            "}";
    }

    public void incrementarAtaque() {
        if(puntosRestantes > 0) {
            ataque++;
            puntosRestantes--;
            notifyObservers(); 
        }
    }
    
    public void decrementarAtaque() {
        if(ataque > 0) {
            ataque--;
            puntosRestantes++;
            notifyObservers();
        }
    }

    public void incrementarDefensa() {
        if(puntosRestantes > 0) {
            defensa++;
            puntosRestantes--;
            notifyObservers(); 
        }
    }
    
    public void decrementarDefensa() {
        if(defensa > 0) {
            defensa--;
            puntosRestantes++;
            notifyObservers();
        }
    }

    public void incrementarVida() {
        if(puntosRestantes > 0) {
            vida++;
            puntosRestantes--;
            notifyObservers(); 
        }
    }
    
    public void decrementarVida() {
        if(vida > 0) {
            vida--;
            puntosRestantes++;
            notifyObservers();
        }
    }

    public void incrementarVelocidad() {
        if(puntosRestantes > 0) {
            velocidad++;
            puntosRestantes--;
            notifyObservers(); 
        }
    }
    
    public void decrementarVelocidad() {
        if(velocidad > 0) {
            velocidad--;
            puntosRestantes++;
            notifyObservers();
        }
    }
}
