package com.mazmorra.Model;

import com.mazmorra.TipoJugador;

/**
 * Representa a un enemigo dentro del juego. Hereda de la clase Personaje y añade
 * la percepción como atributo específico.
 * 
 * @author Miguel González Seguro
 * @author Lucía Fernández Florencio
 * 
 */

public class Enemigo extends Personaje {
    
    /** Nivel de percepción del enemigo, determina su capacidad para detectar al jugador.*/
    private int percepcion;
    
    /**
     * Crea un nuevo enemigo con los parámetros especificados.
     *
     * @param nombre      el nombre del enemigo.
     * @param ataque      el valor de ataque del enemigo.
     * @param defensa     el valor de defensa del enemigo.
     * @param vida        los puntos de vida del enemigo.
     * @param tipo        el tipo de enemigo, definido en los valores del Enum TipoJugador: cíclope, minotauro o cthulu.
     * @param rutaImagen  la ruta del archivo de imagen asociado al enemigo.
     * @param percepcion  el nivel de percepción del enemigo, que determina su capacidad para detectar al jugador y moverse hacia él.
     * 
     */
    public Enemigo(String nombre, int ataque, int defensa, int vida, TipoJugador tipo, String rutaImagen, int percepcion) {
        super(nombre, ataque, defensa, vida, tipo, rutaImagen);
        this.percepcion = percepcion;
    }


    /**
     * Devuelve el nivel de percepción del enemigo.
     *
     * @return el nivel de percepción.
     */
    public int getPercepcion() {
        return percepcion;
    }


    /**
     * Establece el nivel de percepción del enemigo.
     *
     * @param percepcion el nuevo valor de percepción.
     */
    public void setPercepcion(int percepcion) {
        this.percepcion = percepcion;
    }


    /**
     * Devuelve una representación en texto del enemigo, incluyendo sus atributos y percepción.
     *
     * @return Cadena de texto con la información del enemigo.
     */
    @Override
    public String toString() {
        return super.toString().replace("}", "") +
                ", percepcion='" + percepcion + "'" +
                "}";
    }

    
    
}
