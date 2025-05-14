package com.mazmorra;

/**
 * Enumeración que define los distintos tipos de personajes disponibles en el juego.
 * 
 * Se utiliza para distinguir entre personajes controlados por el jugador y enemigos.
 * 
 * Los valores se dividen en dos grupos:
 * <ul>
 *   <li>Jugador: ARQUERO, GUERRERO, MAGO</li>
 *   <li>Enemigo: CICLOPE, CTHULU, MINOTAURO</li>
 * </ul>
 * 
 * Este tipo se usa en la clase {@link Personaje} para determinar el valor de la velocidad 
 * mediante Personaje.calcularVelocidad().
 * 
 * @author Miguel González Seguro
 * @author Lucía Fernández Florencio
 */
public enum TipoJugador {
    ARQUERO, GUERRERO, MAGO, 
    CICLOPE, CTHULU, MINOTAURO
}