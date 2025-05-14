package com.mazmorra.Interfaces;
/**
 * Interfaz Observer del patrón de diseño observador.
 * 
 * <p>
 * Define el método que debe ser implementado por las clases que quieren ser notificadas
 * cuando se produce un cambio en el modelo (por ejemplo, al modificar atributos del jugador).
 * </p>
 * <p>
 * Se utiliza principalmente para actualizar la vista de forma automática conforme se producen estos cambios. 
 * </p>
 * 
 * @author Miguel González Seguro
 * @author Lucía Fernández Florencio
 */
public interface Observer {

    /**
     * Método que se ejecuta cuando el objeto observado notifica un cambio.
     *
     * Las clases que implementan esta interfaz deben definir qué ocurre cuando se llama a este método.
     */
    public void onChange();
}