package com.mazmorra.Model;
import java.util.ArrayList;
import java.util.Objects;
import com.mazmorra.TipoPersonaje;
import com.mazmorra.Interfaces.Observer;

public class Personaje  {
    private ArrayList<Observer> observers = new ArrayList<>();
    protected TipoPersonaje tipo;
    protected int ataque; 
    protected int defensa;
    protected int vida;
    protected int velocidad;

    public Personaje(int ataque, int defensa, int vida, int velocidad, TipoPersonaje tipo) {
        this.tipo = tipo;
        this.ataque = ataque;
        this.defensa = defensa;
        this.vida = vida;
        this.velocidad = velocidad;
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
    }

    public int getAtaque() {
        return this.ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return this.defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getVida() {
        return this.vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getVelocidad() {
        return this.velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public Personaje tipo(TipoPersonaje tipo) {
        setTipo(tipo);
        return this;
    }

    public Personaje ataque(int ataque) {
        setAtaque(ataque);
        return this;
    }

    public Personaje defensa(int defensa) {
        setDefensa(defensa);
        return this;
    }

    public Personaje vida(int vida) {
        setVida(vida);
        return this;
    }

    public Personaje velocidad(int velocidad) {
        setVelocidad(velocidad);
        return this;
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
    

}
