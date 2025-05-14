package com.mazmorra;

/**
 * Enumeración que representa los identificadores únicos para cada escena del juego.
 * 
 * Estos IDs son utilizados por la instancia única de SceneManager para reeistrar, cambiar y cargar escenas 
 * en función del flujo de la aplicación.
 * 
 * Las escenas definidas son cinco:
 * <ul>
 *   <li>INICIO: Pantalla de inicio del juego.</li>
 *   <li>PERSONAJE: Pantalla de selección y configuración del personaje.</li>
 *   <li>JUEGO: Pantalla principal del juego donde se mueve el personaje sobre el mapa.</li>
 *   <li>YOULOSE: Escena que se muestra cuando el jugador pierde.</li>
 *   <li>YOUWIN: Escena que se muestra cuando el jugador gana.</li>
 * </ul>
 * 
 * @author Miguel González Seguro
 * @author Lucía Fernández Florencio
 */
public enum SceneID {
    INICIO, PERSONAJE, JUEGO, YOULOSE, YOUWIN
}
  