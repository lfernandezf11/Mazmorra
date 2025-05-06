package com.mazmorra.Model;

import com.mazmorra.TipoJugador;
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
    protected TipoJugador tipo;
    protected Image imagen;

    /**
     * Constructor parametrizado que genera un Personaje no tipado del juego.
     *
     * @param nombre     Nombre del personaje.
     * @param ataque     Nivel de ataque.
     * @param defensa    Nivel de defensa.
     * @param vida       Puntos de vida.
     * @param tipo       Tipo de personaje (enum TipoJugador).
     * @param rutaImagen Ruta del archivo de imagen asociado al personaje.
     */
    protected Personaje(String nombre, int ataque, int defensa, int vida, TipoJugador tipo, String rutaImagen) {
        this.nombre = nombre;
        this.ataque = ataque;
        this.defensa = defensa;
        this.vida = vida;
        this.tipo = tipo;
        this.velocidad = calcularVelocidad(tipo); // Asignación correcta de velocidad
        setImagen(rutaImagen); // inicializa imagen
    }

    /*GETTERS Y SETTERS */

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getVelocidad() {
        return velocidad;
    }

    /**
     * Asigna al Jugador un valor de velocidad de tipo estático, dependiente del tipo de jugador seleccionado (arquero, guerrero o mago).
     * 
     * @param tipo tipo de Jugador seleccionado, definido por el enum {@code TipoJugador}.
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
     * Establece la imagen gráfica del personaje a partir de una ruta.
     *
     * @param rutaImagen la ruta relativa a la imagen del personaje.
     */
    public void setImagen(String rutaImagen) {
        if (rutaImagen == null || rutaImagen.isEmpty()) {
            System.err.println("Ruta de imagen no especificada para el personaje: " + nombre);
            // Puedes asignar una imagen por defecto aquí si lo deseas
            // this.imagen = new Image(getClass().getResource("/com/mazmorra/Images/por_defecto.png").toExternalForm());
            return;
        }
        var url = getClass().getResource(rutaImagen);
        if (url != null) {
            this.imagen = new Image(url.toExternalForm());
        } else {
            System.err.println("Imagen no encontrada: " + rutaImagen);
            // this.imagen = new Image(getClass().getResource("/com/mazmorra/Images/por_defecto.png").toExternalForm());
        }
    }

    public Image getImagen() {
        return imagen;
    }

    @Override
    public String toString() {
        return "Personaje { " +
            "nombre='" + getNombre() + "'" +
            ", ataque='" + getAtaque() + "'" +
            ", defensa='" + getDefensa() + "'" +
            ", vida='" + getVida() + "'" +
            ", velocidad='" + getVelocidad() + "'" +
            "}";
    }
}