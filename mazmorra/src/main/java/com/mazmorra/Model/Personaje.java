package com.mazmorra.Model;

import com.mazmorra.TipoJugador;

/**
 * Clase abstracta que representa un personaje genérico del juego.
 * 
 * Centraliza los atributos comunes de las clases Jugador y Enemigo, las cuales
 * heredan de esta clase.
 * 
 * -La posición del personaje se establece y gestiona a través de ObjetoMapa.
 * -Los observers son implementados en las clases instanciables hijas: Jugador y
 * Enemigo.
 * 
 */
public abstract class Personaje {
    /*
     * Atributos comunes a 'Jugador' y 'Enemigo' : estadísticas de combate, nombre e
     * imagen gráfica asociada.
     */
    protected String nombre;
    protected int ataque;
    protected int defensa;
    protected int vida;
    protected int velocidad;
    protected TipoJugador tipo;
    protected String rutaImagen;
    // protected int columna;
    // protected int fila;

    /*
     * En lugar de almacenar una Image asociada al Personaje, almacenamos la ruta
     * del recurso
     * y después generamos la imagen desde el controlador del juego, encapsulando
     * funcionalidades
     * para depender menos de JavaFx.
     */

    private static int vidaInicial = 5;

    /**
     * Constructor parametrizado que genera un Personaje no tipado del juego.
     *
     * @param nombre     Nombre del personaje.
     * @param ataque     Nivel de ataque.
     * @param defensa    Nivel de defensa.
     * @param vida       Puntos de vida inicial.
     * @param tipo       Tipo de personaje (enum TipoJugador).
     * @param rutaImagen Ruta del archivo de imagen asociado al personaje.
     */
    protected Personaje(String nombre, int ataque, int defensa, int vida, TipoJugador tipo, String rutaImagen) {
        this.nombre = nombre;
        this.ataque = ataque;
        this.defensa = defensa;
        this.vida = vidaInicial;
        this.tipo = tipo;
        this.velocidad = calcularVelocidad(tipo); // Asignación correcta de velocidad
        this.rutaImagen = rutaImagen;
    }

    /* GETTERS Y SETTERS */

    /**
     * Obtiene el nombre del personaje.
     * 
     * @return nombre del personaje.
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

    // public int getColumna() {
    //     return columna;
    // }

    // public int getFila() {
    //     return fila;
    // }

    // public void setColumna(int columna) {
    //     this.columna = columna;
    // }

    // public void setFila(int fila) {
    //     this.fila = fila;
    // }

    /* RESTO DE MÉTODOS */
    /**
     * Asigna al Jugador un valor de velocidad de tipo estático, dependiente del
     * tipo de jugador seleccionado (arquero, guerrero o mago).
     * 
     * @param tipo tipo de Jugador seleccionado, definido por el enum
     *             {@code TipoJugador}.
     * @return el valor de la velocidad correspondiente a este tipo de jugador.
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
                return 5;
        }
    }

    /**
     * Devuelve una cadena que contiene cada atributo del personaje junto con su
     * valor.
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
                "}";
    }
}