package com.mazmorra.Controllers;

import com.mazmorra.SceneID;
import com.mazmorra.SceneManager;
import com.mazmorra.TipoJugador;
import com.mazmorra.Interfaces.Observer;
import com.mazmorra.Model.Jugador;
import com.mazmorra.Model.Proveedor;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Controlador para la escena de selección y configuración del jugador.
 * 
 * Permite al usuario introducir su nombre, elegir un tipo de personaje 
 * (MAGO, GUERRERO, ARQUERO) y distribuir una cantidad predeterminada de
 * puntos iniciales entre las estadísticas de vida, ataque, defensa y velocidad
 * antes de iniciar la partida.
 * 
 * Implementa la interfaz {@link Observer} para plasmar en la escena los cambios en la
 * entidad Jugador de forma dinámica.
 * 
 * @author Miguel González Seguro
 * @author Lucía Fernández Florencio
 */

public class JugadorController implements Observer {
    //Etiquetas de atributos
    @FXML
    private Label Vida;
    @FXML
    private Label Ataque;
    @FXML
    private Label Defensa;
    @FXML
    private Label Velocidad;
    
    //Etiquetas de puntos asignados a cada atributo
    @FXML
    private Label pDisponible;
    @FXML
    private Label puntosVida;
    @FXML
    private Label puntosAtaque;
    @FXML
    private Label puntosDefensa;
    @FXML
    private Label puntosVelocidad;
    @FXML
    private Label PuntosRestantes;

    // Campo de texto para introducir el nombre
    @FXML
    private TextField introNombre;

    // Selección del tipo de jugador
    @FXML
    private ImageView imagenMago;
    @FXML
    private ImageView imagenGuerrero;
    @FXML
    private ImageView imagenElfo;

    // Botones para modificar estadísticas
    @FXML
    private Button addVida;
    @FXML
    private Button restVida;
    @FXML
    private Button addAtaque;
    @FXML
    private Button restAtaque;
    @FXML
    private Button addDefensa;
    @FXML
    private Button restDefensa;
    @FXML
    private Button addVelocidad;
    @FXML
    private Button restVelocidad;
    
    // Botones para confirmar
    @FXML
    private Button iniciarJuego;
    @FXML
    private Button introUno;

    /** Instancia del jugador actual */
    private Jugador jugador;

    /**
     * Método de inicialización automática llamado por JavaFX.
     * Configura el estado inicial de los componentes visuales, suscriptores del modelo, y los eventos de los botones.
     * 
     * Los controles permanecen ocultas hasta que el usuario introduce su nombre. 
     */
    @FXML
    public void initialize() {
        // Oculta los componentes hasta la introducción del nombre
        ocultarComponentesIniciales();

        // Hace visibles las imágenes de cada tipo de jugador cuando el nombre no está vacío. 
        introUno.setOnAction(e -> {
            if (!introNombre.getText().isEmpty()) {
                // Mostrar las imágenes
                imagenMago.setVisible(true);
                imagenGuerrero.setVisible(true);
                imagenElfo.setVisible(true);
            }
        });

        /* Configura los ImageView para que sean clickables y genera el objeto Jugador sólo cuando se ha seleccionado el tipo.
           Si lo hiciésemos antes, el Jugador se iniciaría con el valor TipoJugador null, con lo que no se generaría correctamente
           ni sus datos podrían pasar entre pantallas.*/
        imagenMago.setOnMouseClicked(e -> {
            crearJugador(TipoJugador.MAGO, "/com/mazmorra/Images/magaAbajo.png");
        });
           
        imagenGuerrero.setOnMouseClicked(e -> {
            crearJugador(TipoJugador.GUERRERO, "/com/mazmorra/Images/guerreroAbajo.png");
        });
           
        imagenElfo.setOnMouseClicked(e -> {
            crearJugador(TipoJugador.ARQUERO, "/com/mazmorra/Images/arqueroAbajo.png");       
        });
            
    }


     /**
     * Oculta todos los componentes de edición de estadísticas, botones y etiquetas.
     * Se utiliza al iniciar la escena para que la configuración sólo sea posible tras la
     * introducción del nombre.
     * 
     */
    private void ocultarComponentesIniciales() {
        puntosVida.setVisible(false);
        puntosAtaque.setVisible(false);
        puntosDefensa.setVisible(false);
        puntosVelocidad.setVisible(false);
        PuntosRestantes.setVisible(false);
        addVida.setVisible(false);
        restVida.setVisible(false);
        addAtaque.setVisible(false);
        restAtaque.setVisible(false);
        addDefensa.setVisible(false);
        restDefensa.setVisible(false);
        addVelocidad.setVisible(false);
        restVelocidad.setVisible(false);
        iniciarJuego.setVisible(false);
        imagenMago.setVisible(false);
        imagenGuerrero.setVisible(false);
        imagenElfo.setVisible(false);
        pDisponible.setVisible(false);
        Ataque.setVisible(false);
        Defensa.setVisible(false);
        Vida.setVisible(false);
        Velocidad.setVisible(false);
    }
    
    
    /**
     * Crea el objeto Jugador tras seleccionar el tipo de personaje y lo registra en el proveedor,
     * desde el cual se recuperarán sus datos. 
     * También suscribe al controlador como observador y actualiza la interfaz con la llamada a los métodos
     * indicados para ello.
     *
     * @param tipo el tipo de jugador seleccionado (MAGO, GUERRERO, ARQUERO).
     * @param rutaImagen la ruta al recurso gráfico correspondiente al tipo.
     */
    private void crearJugador(TipoJugador tipo, String rutaImagen) {
        jugador = new Jugador(introNombre.getText(), 0, 0, 5, rutaImagen, tipo);
        jugador.subscribe(this);
        Proveedor.getInstance().setJugador(jugador); 
        mostrarStats();
        configurarBotones();
        actualizarPersonaje();
    }
        
    /**
     * Muestra en la interfaz todos los controles (etiquetas y botones) asociados a la modificación de las estadísticas.
     */
    private void mostrarStats() {
        puntosVida.setVisible(true);
        puntosAtaque.setVisible(true);
        puntosDefensa.setVisible(true);
        puntosVelocidad.setVisible(true);
        PuntosRestantes.setVisible(true);
        addVida.setVisible(true);
        restVida.setVisible(true);
        addAtaque.setVisible(true);
        restAtaque.setVisible(true);
        addDefensa.setVisible(true);
        restDefensa.setVisible(true);
        addVelocidad.setVisible(true);
        restVelocidad.setVisible(true);
        iniciarJuego.setVisible(true);
        Ataque.setVisible(true);
        Defensa.setVisible(true);
        Velocidad.setVisible(true);
        Vida.setVisible(true);
        pDisponible.setVisible(true);
    }


    /**
     * Configura las acciones de los botones para modificar las estadísticas del jugador,
     * a través de los métodos propios de {@link Jugador}.
     * 
     * IniciarJuego gestiona la acción de iniciar el juego una vez completada la configuración.
     */
    private void configurarBotones() {
        // Configuración usando métodos del modelo
        addVida.setOnAction(e -> {
            jugador.incrementarVida();
        });

        restVida.setOnAction(e -> {
            jugador.decrementarVida();
        });

        addAtaque.setOnAction(e -> {
            jugador.incrementarAtaque();
        });
        restAtaque.setOnAction(e -> {
            jugador.decrementarAtaque();
        });

        addDefensa.setOnAction(e -> {
            jugador.incrementarDefensa();
        });
        restDefensa.setOnAction(e -> {
            jugador.decrementarDefensa();
        });

        addVelocidad.setOnAction(e -> {
            jugador.incrementarVelocidad();
        });

        restVelocidad.setOnAction(e -> {
            jugador.decrementarVelocidad();
        });

        iniciarJuego.setOnAction(e -> {
            // Actualiza los valores del Jugador.
            jugador.setNombre(introNombre.getText());
            
            Proveedor.getInstance().setJugador(jugador);
            // Carga y muestra la escena de juego en este momento
            SceneManager.getInstance().setScene(SceneID.JUEGO, "juego");
            SceneManager.getInstance().loadScene(SceneID.JUEGO);
        });
    }


    /**
     * Método de la interfaz {@link Observer} que se ejecuta cada vez que cambia el modelo del Jugador.
     */
    @Override
    public void onChange() {
        actualizarPersonaje();
    }


    /**
     * Sincroniza los valores actuales del jugador con los las etiquetas visuales de la escena.
     * También habilita o deshabilita los botones según el número de puntos restantes.
     */
    private void actualizarPersonaje() {
        // Sincronización completa de valores
        puntosVida.setText(String.valueOf(jugador.getVida()));
        puntosAtaque.setText(String.valueOf(jugador.getAtaque()));
        puntosDefensa.setText(String.valueOf(jugador.getDefensa()));
        puntosVelocidad.setText(String.valueOf(jugador.getVelocidad()));
        PuntosRestantes.setText(String.valueOf(jugador.getPuntosRestantes()));

        // Gestión de estados de botones (-)
        restVida.setDisable(jugador.getVida() <= 0);
        restAtaque.setDisable(jugador.getAtaque() <= 0);
        restDefensa.setDisable(jugador.getDefensa() <= 0);
        restVelocidad.setDisable(jugador.getVelocidad() <= 0);

        // Gestión de estados de botones (+)
        boolean sinPuntos = jugador.getPuntosRestantes() <= 0;
        addVida.setDisable(sinPuntos);
        addAtaque.setDisable(sinPuntos);
        addDefensa.setDisable(sinPuntos);
        addVelocidad.setDisable(sinPuntos);
    }
}