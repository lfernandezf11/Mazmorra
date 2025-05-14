package com.mazmorra.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase singleton que actúa como proveedor centralizado de datos del modelo.
 * 
 * Se encarga de almacenar y proporcionar acceso al objeto Jugador
 * y a la lista de Enemigo, permitiendo su reutilización entre controladores.
 * 
 * Esta clase evita la duplicación de instancias y facilita el acceso global
 * al estado del juego mediante el patrón Singleton. La instancia de Proveedor es única para todo el programa.
 * 
 * - No se inicializa directamente el Jugador, ya que necesita el tipo para calcular la velocidad.
 * - Se recomienda setear los valores desde la vista inicial del juego.
 */
public class Proveedor {

    /** Instancia única de la clase Proveedor (Singleton). */
    private static Proveedor instance;

    /** Jugador principal del juego. */
    private Jugador jugador;

    /** Lista de enemigos activos en el tablero. */
    private List<Enemigo> enemigos;

    /**
     * Devuelve la instancia única del proveedor.
     * Si no existe, la crea.
     * 
     * @return la instancia única de {@code Proveedor}.
     */
    public static Proveedor getInstance() {
        if (instance == null) {
            instance = new Proveedor();
        }
        return instance;
    }

    /**
     * Constructor privado para cumplir el patrón Singleton.
     * Se inicializa la lista de enemigos como vacía.
     */
    private Proveedor() { 
        this.enemigos = new ArrayList<>();
        /*Aquí no se puede inicializar un Jugador parametrizado, puesto que el constructor intentaría 
        obtener el tipo para calcular la velocidad antes de haber sido asignado.*/
    }

    /**
     * Devuelve el jugador actual.
     * 
     * @return el objeto Jugador almacenado.
     */
    public Jugador getJugador() {
        return this.jugador;
    }


    /**
     * Establece el jugador seleccionado.
     * 
     * @param jugador objeto de tipo Jugador generado.
     */
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }


    /**
     * Devuelve la lista de enemigos activos en el juego.
     * 
     * @return la lista de objetos Enemigo.
     */
    public List<Enemigo> getListaEnemigos() {
        return enemigos;
    }

    /**
     * Devuelve una lista combinada con el jugador y los enemigos,
     * con objeto de poder visualizarlos o acceder a sus atributos comunes de forma agrupada.
     * 
     * @return lista de objetos de tipo Personaje, incluyendo al jugador y enemigos.
     */
     public List<Personaje> getListaDePersonajesIncluyendoJugador() {
        /* Pese a que Personaje es una clase abstracta, puede definir el tipo de datos para una estructura de datos.
        La lista contiene instancias de las clases hijas (Jugador y Enemigo), agrupadas bajo la superclase.
         */
        List<Personaje> lista = new ArrayList<>();
        if (jugador != null) {
            lista.add(jugador);
        }
    
        if (enemigos != null && !enemigos.isEmpty()) {
            lista.addAll(enemigos);
        }
        return lista;
    }


    /**
     * Establece la lista de enemigos actuales.
     * 
     * @param enemigos lista de objetos Enemigo.
     */
    public void setEnemigos(List<Enemigo> enemigos) {
        this.enemigos = enemigos;
    }

}
