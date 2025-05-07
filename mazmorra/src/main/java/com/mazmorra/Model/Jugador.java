package com.mazmorra.Model;

import java.util.ArrayList;
import java.util.Objects;
import com.mazmorra.TipoJugador;
import com.mazmorra.Interfaces.Observer;

/**
 * Representa al objeto de tipo Personaje que actúa como Jugador.
 */
public class Jugador extends Personaje {
    /** Lista de observadores suscritos a cambios en el jugador. */
    private ArrayList<Observer> observers = new ArrayList<>();
    private int puntosRestantes = 10; // Ahora es de instancia, no estático

    /**
     * Constructor parametrizado que genera el Personaje de tipo Jugador.
     */
    public Jugador(String nombre, int ataque, int defensa, int vida, String rutaImagen, TipoJugador tipo, int puntosRestantes) {
        super(nombre, ataque, defensa, vida, tipo, rutaImagen);
        this.puntosRestantes = puntosRestantes;
    }

    public TipoJugador getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoJugador tipo) {
        this.tipo = tipo;
        this.velocidad = calcularVelocidad(tipo); // Actualiza velocidad si cambia el tipo
        notifyObservers();
    }

    public int getPuntosRestantes() {
        return puntosRestantes;
    }

    public void setPuntosRestantes(int puntosRestantes) {
        this.puntosRestantes = puntosRestantes;
        notifyObservers();
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
        notifyObservers();
    }

    @Override
    public void setAtaque(int ataque) {
        this.ataque = ataque;
        notifyObservers();
    }

    @Override
    public void setDefensa(int defensa) {
        this.defensa = defensa;
        notifyObservers();
    }

    @Override
    public void setVida(int vida) {
        this.vida = vida;
        notifyObservers();
    }

    // Métodos Observer
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        observers.forEach(Observer::onChange);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jugador)) return false;
        Jugador jugador = (Jugador) o;
        return ataque == jugador.ataque &&
               defensa == jugador.defensa &&
               vida == jugador.vida &&
               velocidad == jugador.velocidad &&
               Objects.equals(nombre, jugador.nombre) &&
               tipo == jugador.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, tipo, ataque, defensa, vida, velocidad);
    }

    @Override
    public String toString() {
        return super.toString().replace("}", "") +
                ", tipo=" + tipo +
                ", puntosRestantes=" + puntosRestantes +
                '}';
    }

    // Métodos para manipular puntos
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