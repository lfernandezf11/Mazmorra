package com.mazmorra.Model;

import java.util.ArrayList;
import java.util.Objects;
import com.mazmorra.TipoJugador;
import com.mazmorra.Interfaces.Observer;

/**
 * Clase que representa al objeto de tipo Personaje que actúa como Jugador.
 * Hereda de Personaje y añade puntos restantes como atributo específico. 
 * 
 * @author Miguel González Seguro
 * @author Lucía Fernández Florencio
 * 
 */


public class Jugador extends Personaje {
    
    /** Lista de observadores suscritos a cambios en el jugador. */
    private ArrayList<Observer> observers = new ArrayList<>();

    /** Puntos a distribuir entre los atributos del jugador. */
    private int puntosRestantes;
    
    /** Puntos a distribuir entre los atributos del jugador, en el momento de su creación. Por defecto, 10. */
    private static int puntosRestantesIniciales = 10;

    /**
     * Constructor que crea un nuevo jugador con los parámetros especificados.
     * Hereda los atributos de la superclase Personaje, e implementa puntosRestantes. 
     *
     * @param nombre            el nombre del jugador.
     * @param ataque            el valor de ataque del jugador.
     * @param defensa           el valor de defensa del jugador.
     * @param vida              los puntos de vida del jugador.
     * @param tipo              el tipo de jugador, definido en los valores del Enum TipoJugador: mago, guerrero o arquero.
     * @param rutaImagen        la ruta del archivo de imagen asociado al jugador.
     */
    public Jugador(String nombre, int ataque, int defensa, int vida, String rutaImagen, TipoJugador tipo) {
        super(nombre, ataque, defensa, vida, tipo, rutaImagen);
        this.puntosRestantes = puntosRestantesIniciales;
    }

      
    /**
     * Devuelve los puntos restantes que puede distribuir el jugador entre sus atributos.
     *
     * @return los puntos restantes a distribuir.
     */
    public int getPuntosRestantes() {
        return puntosRestantes;
    }

    /**
     * Establece los puntos restantes que puede distribuir el jugador entre sus atributos
     *
     * @param puntosRestantes el nuevo valor de puntos restantes.
     */
    public void setPuntosRestantes(int puntosRestantes) {
        this.puntosRestantes = puntosRestantes;
        notifyObservers();
    }


    /** SETTERS PARA LOS ATRIBUTOS DEL PERSONAJE
     * Es necesario sobreescribir los setters para los atributos heredados puesto que, en el caso del Jugador,
     * cada cambio ha de ser notificado a los observadores. 
     */
    
    /**
     * Establece el nombre del jugador y notifica a los observadores.
     *
     * @param nombre el nuevo nombre del jugador.
     */
    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
        notifyObservers();
    }

    /**
     * Establece el ataque del jugador y notifica a los observadores.
     *
     * @param ataque el nuevo valor de ataque.
     */
    @Override
    public void setAtaque(int ataque) {
        this.ataque = ataque;
        notifyObservers();
    }

    /**
     * Establece la defensa del jugador y notifica a los observadores.
     *
     * @param defensa el nuevo valor de defensa.
     */
    @Override
    public void setDefensa(int defensa) {
        this.defensa = defensa;
        notifyObservers();
    }

    /**
     * Establece la vida del jugador y notifica a los observadores.
     *
     * @param vida el nuevo valor de vida.
     */
    @Override
    public void setVida(int vida) {
        this.vida = vida;
        notifyObservers();
    }

    /**
     * Establece el tipo del jugador, actualiza la velocidad y notifica a los observadores.
     *
     * @param tipo el nuevo tipo de jugador.
     */
    @Override
    public void setTipoJugador(TipoJugador tipo) {
        this.tipo = tipo;
        this.velocidad = calcularVelocidad(tipo); // Actualiza velocidad si cambia el tipo
        notifyObservers();
    }

    /**
     * Suscribe un observador a los cambios del jugador.
     *
     * @param observer el observador a suscribir.
     */
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    /**
     * Elimina la suscripción de un observador.
     *
     * @param observer el observador a eliminar.
     */
    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifica a todos los observadores suscritos de que el estado del jugador ha cambiado.
     */
    public void notifyObservers() {
        observers.forEach(Observer::onChange);
    }

    /**
     * Compara este jugador con otro objeto para determinar si son iguales.
     *
     * @param o el objeto con el que comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
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

    /**
     * Devuelve el código hash de este jugador.
     *
     * @return el código hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nombre, tipo, ataque, defensa, vida, velocidad);
    }

    /**
     * Devuelve una representación en texto del jugador, incluyendo sus atributos y puntos restantes.
     *
     * @return Cadena de texto con la información del jugador.
     */
    @Override
    public String toString() {
        return super.toString().replace("}", "") +
                ", tipo=" + tipo +
                ", puntosRestantes=" + puntosRestantes +
                '}';
    }


    /** MÉTODOS PARA MODIFICAR LOS ATRIBUTOS DEL JUGADOR */
    /**
     * Incrementa el valor de ataque en 1 si hay puntos restantes, y notifica a los observadores.
     */
    public void incrementarAtaque() {
        if(puntosRestantes > 0) {
            ataque++;
            puntosRestantes--;
            notifyObservers(); 
        }
    }

    /**
     * Decrementa el valor de ataque en 1 si es mayor que 0, y notifica a los observadores.
     */
    public void decrementarAtaque() {
        if(ataque > 0) {
            ataque--;
            puntosRestantes++;
            notifyObservers();
        }
    }

    /**
     * Incrementa el valor de defensa en 1 si hay puntos restantes, y notifica a los observadores.
     */
    public void incrementarDefensa() {
        if(puntosRestantes > 0) {
            defensa++;
            puntosRestantes--;
            notifyObservers(); 
        }
    }

    /**
     * Decrementa el valor de defensa en 1 si es mayor que 0, y notifica a los observadores.
     */
    public void decrementarDefensa() {
        if(defensa > 0) {
            defensa--;
            puntosRestantes++;
            notifyObservers();
        }
    }

    /**
     * Incrementa el valor de vida en 1 si hay puntos restantes, y notifica a los observadores.
     */
    public void incrementarVida() {
        if(puntosRestantes > 0) {
            vida++;
            puntosRestantes--;
            notifyObservers(); 
        }
    }

    /**
     * Decrementa el valor de vida en 1 si es mayor que 0, y notifica a los observadores.
     */
    public void decrementarVida() {
        if(vida > 0) {
            vida--;
            puntosRestantes++;
            notifyObservers();
        }
    }

    /**
     * Incrementa el valor de velocidad en 1 si hay puntos restantes y notifica a los observadores.
     */
    public void incrementarVelocidad() {
        if(puntosRestantes > 0) {
            velocidad++;
            puntosRestantes--;
            notifyObservers(); 
        }
    }

    /**
     * Decrementa el valor de velocidad en 1 si es mayor que 0, y notifica a los observadores.
     */
    public void decrementarVelocidad() {
        if(velocidad > 0) {
            velocidad--;
            puntosRestantes++;
            notifyObservers();
        }
    }
}