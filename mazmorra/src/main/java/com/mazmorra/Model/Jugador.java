package com.mazmorra.Model;

import java.util.ArrayList;
import java.util.Objects;
import com.mazmorra.TipoJugador;
import com.mazmorra.Interfaces.Observer;

/**
 * Representa al objeto de tipo Personaje que actúa como Jugador.
 * 
 * Hereda de {@link Personaje} y añade atributos específicos: 
 * - Puntos que el usuario asigna a las estadísticas de combate del jugador.
 * - Notificación de cambios a observadores suscritos.
 * 
 * @author Miguel González Seguro
 * @author Lucía Fernández Florencio
 * 
 */
public class Jugador extends Personaje {
    /** Lista de observadores suscritos a cambios en el jugador. */
    private ArrayList<Observer> observers = new ArrayList<>();
    private TipoJugador tipo;
    private static int puntosRestantes = 5;

    /**
     * Constructor por defecto que genera un objeto Jugador genérico, inicializando sus atributos.
     */
    public Jugador() {
        super("", 0, 0, 0, "");
        this.tipo = TipoJugador.MAGO;
        Jugador.puntosRestantes = 5; //Llamada estática a puntosRestantes.
    }
    
    /**
     * Constructor parametrizado que genera el Personaje de tipo Jugador y asigna a sus atributos los valores iniciales para la partida.
     * 
     * El atributo velocidad es asignado en función del tipo de jugador.
     *
     * @param nombre          Nombre del personaje.
     * @param ataque          Nivel de ataque.
     * @param defensa         Nivel de defensa.
     * @param vida            Puntos de vida.
     * @param rutaImagen      Ruta del archivo de imagen asociado al personaje.
     * @param tipo            Tipo de jugador elegido (Arquero, guerrero o maga).
     * @param puntosRestantes Cantidad inicial de puntos por repartir, igual para toda instancia Jugador.
     */
    public Jugador(String nombre, int ataque, int defensa, int vida, String rutaImagen, TipoJugador tipo, int puntosRestantes) {
        super(nombre, ataque, defensa, vida, calcularVelocidad(tipo), rutaImagen);
        this.tipo = tipo;
        Jugador.puntosRestantes = puntosRestantes;
    }

    /* GETTINGS PARA LOS ATRIBUTOS DE CLASE, SETTINGS PARA TODOS LOS ATRIBUTOS.*/ 
    
    /** Los setters super() están sobreescritos para implementar la notificación a los observadores suscritos a la clase.
     *  La velocidad del jugador depende de su tipo, con lo que el método calcularVelocidad() sustituye al setter correspondiente.
    */

    /** 
     * Obtiene el tipo de jugador asignado a la entidad.
     * 
     * @return el tipo de jugador.
     */
    public TipoJugador getTipo() {
        return this.tipo;
    }

    /** 
     * Establece el tipo de jugador y notifica a los observadores.
     *
     * @param tipo el tipo seleccionado por el usuario.
     */
    public void setTipo(TipoJugador tipo) {
        this.tipo = tipo;
        notifyObservers();
    }

    /** 
     * Obtiene la cantidad de puntos a distribuir.
     * 
     * @return la cantidad de puntos no distribuidos. 
     */
    public int getPuntosRestantes() {
        return Jugador.puntosRestantes;
    }

    /**
     * Establece la cantidad de puntos restantes y notifica a los observadores.
     *
     * @param puntosRestantes la nueva cantidad de puntos por repartir.
     */
    public void setPuntosRestantes(int puntosRestantes) {
        Jugador.puntosRestantes = puntosRestantes;
        notifyObservers();
    }

    /*SETTERS SOBREESCRITOS */
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
     * Establece el valor de ataque y notifica a los observadores.
     *
     * @param ataque nuevo valor de ataque.
     */
    @Override
    public void setAtaque(int ataque) {
        this.ataque = ataque;
        notifyObservers();
    }

    /**
     * Establece el valor de defensa y notifica a los observadores.
     *
     * @param defensa nuevo valor de defensa.
     */
    @Override
    public void setDefensa(int defensa) {
        this.defensa = defensa;
        notifyObservers();
    }

    /**
     * Establece los puntos de vida y notifica a los observadores.
     *
     * @param vida nueva cantidad de vida.
     */
    @Override
    public void setVida(int vida) {
        this.vida = vida;
        notifyObservers();
    }


    /* GESTIÓN Y MANIPULACIÓN DE OBSERVERS*/
    /**
     * Suscribe un observador a los cambios del jugador.
     *
     * @param observer el observador a suscribir.
     */
    public void suscribe(Observer observer) {
        observers.add(observer);
    }

    /**
     * Elimina un observador suscrito previamente.
     *
     * @param observer el observador a eliminar.
     */
    public void unsuscribe(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifica a todos los observadores registrados que ha habido un cambio.
     */
    public void notifyObservers() {
        observers.forEach(Observer::onChange);
    }

    /* MÉTODO DE ASIGNACIÓN DE VELOCIDAD */
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
            default:
                return 5;
        }
    }
    
   



   
//COMENTAR
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

    @Override
    public int hashCode() {
        return Objects.hash(nombre, tipo, ataque, defensa, vida, velocidad);
    }

    @Override
    public String toString() {
        return super.toString().replace("}", "") +
                ", tipo=" + tipo +
                ", puntosRestantes=" + puntosRestantes +
                '}';
    }


    public void incrementarAtaque() {
        if(puntosRestantes > 0) {
            ataque++;
            puntosRestantes--;
            notifyObservers(); 
        }
    }
    
    public void decrementarAtaque() {
        if(ataque > 0) {
            ataque--;
            puntosRestantes++;
            notifyObservers();
        }
    }

    public void incrementarDefensa() {
        if(puntosRestantes > 0) {
            defensa++;
            puntosRestantes--;
            notifyObservers(); 
        }
    }
    
    public void decrementarDefensa() {
        if(defensa > 0) {
            defensa--;
            puntosRestantes++;
            notifyObservers();
        }
    }

    public void incrementarVida() {
        if(puntosRestantes > 0) {
            vida++;
            puntosRestantes--;
            notifyObservers(); 
        }
    }
    
    public void decrementarVida() {
        if(vida > 0) {
            vida--;
            puntosRestantes++;
            notifyObservers();
        }
    }

    public void incrementarVelocidad() {
        if(puntosRestantes > 0) {
            velocidad++;
            puntosRestantes--;
            notifyObservers(); 
        }
    }
    
    public void decrementarVelocidad() {
        if(velocidad > 0) {
            velocidad--;
            puntosRestantes++;
            notifyObservers();
        }
    }


}