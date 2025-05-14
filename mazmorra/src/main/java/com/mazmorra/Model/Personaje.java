package com.mazmorra.Model;

import com.mazmorra.TipoJugador;

/**
 * Clase abstracta que representa un personaje genérico del juego.
 * 
 * Centraliza los atributos comunes de las clases Jugador y Enemigo, las cuales heredan de esta clase.
 * 
 * -La posición del personaje se establece y gestiona a través de la clase Mapa y el Controlador del Juego.
 * -Los observers son implementados en las clases instanciables hijas: Jugador y Enemigo.
 * 
 * @author Miguel González Seguro
 * @author Lucía Fernández Florencio
 * 
 */


public abstract class Personaje {
    /**
     * Atributos comunes a 'Jugador' y 'Enemigo' : estadísticas de combate, nombre,
     * imagen gráfica asociada y posición en la matriz del tablero de juego.
     */
    protected String nombre;
    protected int ataque;
    protected int defensa;
    protected int vida;
    protected int velocidad;
    protected TipoJugador tipo;
    protected String rutaImagen;
    /*
     * En lugar de almacenar una Image asociada al Personaje, almacenamos la ruta del recurso y después generamos 
     * la imagen desde el controlador del juego, encapsulando funcionalidades para depender menos de JavaFx.
     */
    
    /** Coordenada X (columna) que ocupa el personaje. */
    protected int columna;
    
    /** Coordenada Y (fila) que ocupa el personaje. */
    protected int fila;

    
  

    /**
     * Constructor parametrizado que genera un Personaje no tipado del juego.
     *
     * @param nombre     Nombre del personaje.
     * @param ataque     el valor de ataque del personaje.
     * @param defensa    el valor de defensa del personaje.
     * @param vida       los puntos de vida del personaje.
     * @param tipo       el tipo de personaje, definido en los valores del Enum TipoJugador.
     * @param rutaImagen la ruta del archivo de imagen asociado al personaje.
     */
    protected Personaje(String nombre, int ataque, int defensa, int vida, TipoJugador tipo, String rutaImagen) {
        this.nombre = nombre;
        this.ataque = ataque;
        this.defensa = defensa;
        this.vida = vida;
        this.tipo = tipo;
        this.velocidad = calcularVelocidad(tipo); // Asignación correcta de velocidad
        this.rutaImagen = rutaImagen;
    }

    /* GETTERS Y SETTERS */

    /**
     * Obtiene el nombre del personaje.
     * 
     * @return el nombre del personaje.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del personaje.
     * 
     * @param nombre el nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el ataque del personaje.
     * 
     * @return el valor de ataque actual.
     */
    public int getAtaque() {
        return ataque;
    }

    /**
     * Establece el valor de ataque del personaje.
     * 
     * @param ataque el nuevo valor de ataque.
     */
    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    /**
     * Obtiene la defensa del personaje.
     * 
     * @return el valor de defensa actual.
     */
    public int getDefensa() {
        return defensa;
    }

    /**
     * Establece el valor de defensa del personaje.
     * 
     * @param nombre el nuevo valor de defensa.
     */
    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    /**
     * Obtiene el nivel de vida del personaje.
     * 
     * @return el número de vidas actual.
     */
    public int getVida() {
        return vida;
    }

    /**
     * Establece el valor de vida del personaje.
     * 
     * @param nombre el nuevo número de vidas.
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * Obtiene la velocidad del personaje, un valor constante dependiente del tipo
     * de personaje.
     * 
     * @return el valor de velocidad.
     */
    public int getVelocidad() {
        return velocidad;
    }

    /*
     * setVelocidad no es necesario, puesto que ésta se establece en función del
     * tipo de personaje
     * a través del método calcularvelocidad().
     */

    /**
     * Obtiene el tipo de jugador asociado al personaje:
     * - Arquero, Mago o Guerrero para el Jugador.
     * - Cíclope, Chthulu o Minotauro para el Enemigo.
     * 
     * @return el tipo de jugador.
     */
    public TipoJugador getTipoJugador() {
        return tipo;
    }

    /**
     * Establece el tipo de jugador asociado al personaje:
     * - Arquero, Mago o Guerrero para el Jugador.
     * - Cíclope, Chthulu o Minotauro para el Enemigo.
     * 
     * @param tipo el tipo de jugador.
     */
    public void setTipoJugador(TipoJugador tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la ruta del archivo de imagen asociado al personaje.
     * 
     * @return la ruta del archivo de imagen.
     */
    public String getRutaImagen() {
        return rutaImagen;
    }

    /**
     * Establece la ruta del archivo de imagen asociado al personaje.
     * 
     * @param el nombre la ruta del archivo de imagen.
     */
    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    /**
     * Obtiene la posición del personaje en el eje horizonal.
     * 
     * @return la posición de columna.
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Establece la posición del personaje en el eje horizonal.
     * 
     * @param columna la nueva posición de columna.
     */
    public void setColumna(int columna) {
       this.columna = columna;
    }

    
    /**
     * Obtiene la posición del personaje en el eje vertical.
     * 
     * @return la posición de fila.
     */
    public int getFila() {
        return fila;
    }

    /**
     * Establece la posición del personaje en el eje vertical.
     * 
     * @param y la nueva posición de fila.
     */
    public void setFila(int fila) {
       this.fila = fila;
    }

    

    /* RESTO DE MÉTODOS */
    /**
     * Establece la posición del personaje en el mapa.
     * 
     * @param columna la nueva posición en el eje horizontal.
     * @param fila la nueva posición en el eje vertical.
     */
    public void setPosicion(int columna, int fila) {
        setColumna(columna);
        setFila(fila);
    }
    
    
    /**
     * Asigna al personaje un valor de velocidad de tipo estático, dependiente del
     * tipo de personaje definido en los valores del enum {@code TipoJugador}
     * (arquero, guerrero o mago para el jugador; cíclope, minotauro o cthulu para el enemigo).
     * 
     * @param tipo  el tipo de personaje seleccionado, definido por el enum {@code TipoJugador}.
     *             
     * @return el valor de la velocidad correspondiente a este tipo de personaje.
     */
    public static int calcularVelocidad(TipoJugador tipo) {
        if (tipo == null) {
            return 5; // Valor por defecto si el tipo no está definido
        }
        switch (tipo) {
            case ARQUERO:
                return 7;
            case MAGO:
                return 5;
            case GUERRERO:
                return 4;
            case CICLOPE:
                return 6;
            case CTHULU:
                return 4;
            case MINOTAURO:
                return 5;
            default:
                return 5; // Valor por defecto si el tipo de personaje no existe. 
        }
    }

    /**
     * Devuelve una representación en texto del personaje, que contiene cada atributo junto con su valor.
     * 
     * @return la cadena de texto con la información del personaje.
     */
    @Override
    public String toString() {
        return "Personaje { " +
                "nombre='" + getNombre() + "'" +
                ", ataque='" + getAtaque() + "'" +
                ", defensa='" + getDefensa() + "'" +
                ", vida='" + getVida() + "'" +
                ", velocidad='" + getVelocidad() + "'" +
                ", tipo='" + getTipoJugador() + "'" +
                ", ruta imagen='" + getRutaImagen() + "'" +
                ", posición en x='" + getColumna() + "'" +
                ", posición en y='" + getFila() + "'" +
                "}";
    }
}