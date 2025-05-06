package com.mazmorra.Model;

import javafx.scene.image.Image;

/**
 * Clase abstracta que representa un personaje genérico del juego.
 * 
 * Centraliza los atributos comunes de las clases Jugador y Enemigo, las cuales heredan de esta clase.
 * 
 * -La posición del personaje se establece y gestiona a través de ObjetoMapa.
 * -Los observers son implementados en las clases instanciables hijas: Jugador y Enemigo.
 * 
 */
public abstract class Personaje {
    /*Atributos comunes a 'Jugador' y 'Enemigo' : estadísticas de combate, nombre e imagen gráfica asociada. */
    protected String nombre;
    protected int ataque; 
    protected int defensa;
    protected int vida;
    protected int velocidad;
    protected Image imagen;

    
    /**
     * Constructor parametrizado que genera un Personaje no tipado del juego.
     *
     * @param nombre     Nombre del personaje.
     * @param ataque     Nivel de ataque.
     * @param defensa    Nivel de defensa.
     * @param vida       Puntos de vida.
     * @param velocidad  Nivel de velocidad.
     * @param rutaImagen Ruta del archivo de imagen asociado al personaje.
     */
    public Personaje(String nombre, int ataque, int defensa, int vida, int velocidad, String rutaImagen) {
        this.nombre = nombre;
        this.ataque = ataque;
        this.defensa = defensa;
        this.vida = vida;
        this.velocidad = velocidad;
        setImagen(rutaImagen); // inicializa imagen e imageView
    }

  
    /*GETTERS Y SETTERS */

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
     * @param nombre el nuevo nombre del personaje.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el valor de ataque del personaje.
     *
     * @return el valor de ataque.
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
     * Obtiene el valor de defensa del personaje.
     *
     * @return el valor de defensa.
     */
    public int getDefensa() {
        return defensa;
    }

    /**
     * Establece el valor de defensa del personaje.
     *
     * @param defensa el nuevo valor de defensa.
     */
    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    /**
     * Obtiene los puntos de vida del personaje.
     *
     * @return la cantidad de vida.
     */
    public int getVida() {
        return vida;
    }

    /**
     * Establece los puntos de vida del personaje.
     *
     * @param vida la nueva cantidad de vida.
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * Obtiene la velocidad del personaje.
     *
     * @return el valor de velocidad.
     */
    public int getVelocidad() {
        return velocidad;
    }

    /**
     * Establece la velocidad del personaje.
     *
     * @param velocidad el nuevo valor de velocidad.
     */
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    /**
     * Establece la imagen gráfica del personaje a partir de una ruta.
     *
     * @param rutaImagen la ruta relativa a la imagen del personaje.
     */
    public static void setImagen(String rutaImagen) {
        this.imagen = new Image(getClass().getResource(rutaImagen).toExternalForm());
    }

    /**
     * Obtiene la imagen del personaje.
     *
     * @return el objeto Image del personaje.
     */
    public Image getImagen() {
        return imagen;
    }

    
    /**
     * Obtiene los valores de los atributos del personaje.
     * 
     * @return un String con las características del personaje.
     * 
     */
     @Override
    public String toString() {
        return "Personaje { " +
            " nombre='" + getNombre() + "'" +
            ", ataque='" + getAtaque() + "'" +
            ", defensa='" + getDefensa() + "'" +
            ", vida='" + getVida() + "'" +
            ", velocidad='" + getVelocidad() + "'" +
            "}";
    }

}
