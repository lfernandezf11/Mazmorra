package com.mazmorra.Model;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;


/**
 * Gestiona la lógica del escenario de juego y su representación gráfica.
 * <p>
 * Carga el mapa desde una matriz de enteros, genera visualmente el tablero
 * con imágenes asociadas a "suelo", "pared" y "escalera", y gestiona la
 * colocación y movimiento del jugador y los enemigos.
 * </p>
 * <p>
 * Los personajes se ubican al inicio de forma aleatoria sobre celdas válidas (tipo suelo),
 * y cada entidad ocupa una única celda en el tablero.
 * </p>
 * 
 * @author Miguel González Seguro
 * @author Lucía Fernández Florencio
 */
public class Mapa {

    /** Instancia de Random para cálculos aleatorios (movimientos, combate, etc.) */
    Random random = new Random();

    /** Rutas estáticas de los recursos gráficos usados en el mapa */
    private static String paredPath = "/com/mazmorra/Images/pared.png";
    private static String sueloPath = "/com/mazmorra/Images/suelo.png";
    private static String escaleraPath = "/com/mazmorra/Images/escalera.png";

    /** Matriz que representa el escenario de juego */
    private int[][] mapaMatriz;

    /** Listado combinado de personajes (jugador + enemigos) */
    private List<Personaje> personajes = Proveedor.getInstance().getListaDePersonajesIncluyendoJugador();

    /** Referencia directa al jugador */
    private Jugador jugador = Proveedor.getInstance().getJugador();

    /** Lista de enemigos cargada desde archivo */
    private List<Enemigo> enemigos = Proveedor.getInstance().getListaEnemigos();

    /** Set de posiciones ocupadas (en formato "x,y") para evitar solapamientos */
    private Set<String> posicionesOcupadas = new HashSet<>();

    /** Posición actual del jugador (no usada directamente, pero mantenida como referencia) */
    @SuppressWarnings("unused")
    private int posicionX;
    @SuppressWarnings("unused")
    private int posicionY;

    /** Flag de control para evitar generar posiciones múltiples veces */
    private boolean personajesGenerados = false;

    /** Constructor parametrizado */
    public Mapa(int[][] mapaMatriz) {
        this.mapaMatriz = mapaMatriz;
    }

    /**
     * Devuelve la matriz actual del mapa.
     * @return matriz de enteros del mapa
     */
    public int[][] getMapaMatriz() {
        return mapaMatriz;
    }

    /**
     * Genera visualmente el tablero dentro de un GridPane.
     * Carga imágenes según el valor de cada celda.
     *
     * @param gridPaneJuego contenedor gráfico donde se coloca el tablero
     * @param stackPaneJuego nodo padre para obtener dimensiones
     */
    public void generarTablero(GridPane gridPaneJuego, StackPane stackPaneJuego) {
        resetearGridPane(gridPaneJuego);
        if (resetearGridPane(gridPaneJuego) != null) {
            addConstraints(gridPaneJuego);
        }
        double anchoStack = stackPaneJuego.getWidth();

        for (int i = 0; i < mapaMatriz.length; i++) {
            for (int j = 0; j < mapaMatriz[i].length; j++) {
                int celda = mapaMatriz[i][j];
                ImageView imageView = new ImageView();

                if (celda == 0) {
                    imageView.setImage(new Image(getClass().getResource(sueloPath).toExternalForm()));
                } else if (celda == 1) {
                    imageView.setImage(new Image(getClass().getResource(paredPath).toExternalForm()));
                } else {
                    imageView.setImage(new Image(getClass().getResource(escaleraPath).toExternalForm()));
                }
                gridPaneJuego.add(imageView, j, i);
                actualizarTamCelda(gridPaneJuego, mapaMatriz.length, anchoStack);
            }
        }
    }

    /**
     * Genera las posiciones iniciales de todos los personajes (jugador y enemigos),
     * ubicándolos aleatoriamente sobre celdas válidas.
     */
    public void generarPosicionesIniciales() {
        if (personajesGenerados) return;

        int posicionX, posicionY;
        for (Personaje personaje : personajes) {
            do {
                posicionX = random.nextInt(mapaMatriz[0].length);
                posicionY = random.nextInt(mapaMatriz.length);
            } while (mapaMatriz[posicionY][posicionX] != 0 ||
                     posicionesOcupadas.contains(posicionX + "," + posicionY));

            posicionesOcupadas.add(posicionX + "," + posicionY);
            this.posicionX = posicionX;
            this.posicionY = posicionY;
            personaje.setPosicion(posicionX, posicionY);
        }
        personajesGenerados = true;
    }

    /**
     * Dibuja los personajes (jugador + enemigos) sobre el tablero.
     * Se eliminan posiciones anteriores antes de actualizar.
     *
     * @param gridPanePersonajes capa visual para los personajes
     */
    public void dibujarPersonajes(GridPane gridPanePersonajes) {
        resetearGridPane(gridPanePersonajes);
        addConstraints(gridPanePersonajes);

        posicionesOcupadas.clear();
        posicionesOcupadas.add(jugador.getColumna() + "," + jugador.getFila());

        for (Enemigo enemigo : enemigos) {
            posicionesOcupadas.add(enemigo.getColumna() + "," + enemigo.getFila());
        }

        // Dibuja el jugador
        ImageView entidadJugador = new ImageView();
        entidadJugador.setImage(new Image(getClass().getResource(jugador.getRutaImagen()).toExternalForm()));
        entidadJugador.setFitWidth(32);
        entidadJugador.setFitHeight(32);
        gridPanePersonajes.add(entidadJugador, jugador.getColumna(), jugador.getFila());

        // Dibuja los enemigos
        for (Enemigo enemigo : enemigos) {
            ImageView enemigoView = new ImageView();
            enemigoView.setImage(new Image(getClass().getResource(enemigo.getRutaImagen()).toExternalForm()));
            enemigoView.setFitWidth(32);
            enemigoView.setFitHeight(32);
            gridPanePersonajes.add(enemigoView, enemigo.getColumna(), enemigo.getFila());
        }
    }

    /**
     * Lógica de movimiento del jugador. Detecta colisiones y combate.
     *
     * @param dx desplazamiento en X
     * @param dy desplazamiento en Y
     * @param gridPanePersonajes contenedor visual
     * @param stackPaneJuego pane padre
     * @return true si se ha movido, false si ha habido combate o bloqueo
     */
    public boolean moverJugador(int dx, int dy, GridPane gridPanePersonajes, StackPane stackPaneJuego) {
        int nuevoX = jugador.getColumna() + dx;
        int nuevoY = jugador.getFila() + dy;

        /* CONTROL DE CONDICIONES QUE INVALIDAN LA CAPACIDAD DE MOVIMIENTO */
        // Comprueba que el nuevo movimiento no se salga del mapa
        if (nuevoX < 0 || nuevoX >= mapaMatriz[0].length || nuevoY < 0 || nuevoY >= mapaMatriz.length)
            return false;

        // Si es una pared, no se puede mover
        if (mapaMatriz[nuevoY][nuevoX] == 1)
            return false;

        String posStr = nuevoX + "," + nuevoY;
        Enemigo enemigo = getEnemigoEnPosicion(nuevoX, nuevoY);

        // Si hay un enemigo, se realiza un ataque en lugar de moverse
        if (enemigo != null) {
            int danio = Math.max(1, jugador.getAtaque() - enemigo.getDefensa());
            boolean critico = random.nextDouble() < 0.2; // 20% de posibilidades de recibir un ataque crítico (duplica su valor)
            if (critico) danio *= 2;
            enemigo.setVida(enemigo.getVida() - danio);

            // Si el enemigo muere, se elimina del proveedor y, consecuentemente, del tablero de juego.
            if (enemigo.getVida() <= 0) {
                enemigos.remove(enemigo); 
                posicionesOcupadas.remove(posStr);
            }
            // Actualiza la escena, manteniendo la posición durante el combate. 
            dibujarPersonajes(gridPanePersonajes); 
            return false; // No se mueve
        }

        /* CONTROL DE ACCIONES SI EL MOVIMIENTO ES VÁLIDO */
        // Actualiza posición y escena
        posicionesOcupadas.remove(jugador.getColumna() + "," + jugador.getFila());
        jugador.setPosicion(nuevoX, nuevoY);
        posicionesOcupadas.add(posStr);

        dibujarPersonajes(gridPanePersonajes);
        return true;
    }


    /**
     * Devuelve el enemigo que ocupa la posición dada.
     *
     * @param x columna
     * @param y fila
     * @return enemigo encontrado, o null si no hay ninguno
     */
    private Enemigo getEnemigoEnPosicion(int x, int y) {
        for (Enemigo enemigo : enemigos) {
            if (enemigo.getColumna() == x && enemigo.getFila() == y) {
                return enemigo;
            }
        }
        return null;
    }

    /**
     * Comprueba si el jugador se encuentra sobre una escalera.
     *
     * @return true si está en una celda con valor 2 (escalera)
     */
    public boolean estaEnLaEscalera() {
        int x = jugador.getColumna();
        int y = jugador.getFila();
        return y >= 0 && y < mapaMatriz.length &&
               x >= 0 && x < mapaMatriz[y].length &&
               mapaMatriz[y][x] == 2;
    }

    /**
     * Lógica de movimiento de los enemigos: detección de jugador y movimiento inteligente o aleatorio.
     *
     * @param enemigo enemigo a mover
     * @param gridPanePersonajes grid visual
     * @param stackPaneJuego contenedor principal
     * @return true si se ha movido o ha atacado, false si no ha hecho nada
     */
    public boolean moverEnemigo(Enemigo enemigo, GridPane gridPanePersonajes, StackPane stackPaneJuego) {
        int x = enemigo.getColumna();
        int y = enemigo.getFila();

        /* En una matriz, las posiciones se guardan en orden fila, columna, o lo que es lo mismo, (y,x)
         * La columna (x) crece hacia la derecha, y la fila (y) crece hacia abajo. 
         */
        int[][] direcciones = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} }; //Abajo, Derecha, Arriba, Izquierda
        List<int[]> posiblesMovimientos = new ArrayList<>();

        int jugadorX = jugador.getColumna();
        int jugadorY = jugador.getFila();

        // Distancia Manhattan
        int distancia = Math.abs(jugadorX - x) + Math.abs(jugadorY - y); 

        if (enemigo.getVida() <= 0) return false; // El enemigo no actúa si está muerto

        // Calcula qué celdas están ocupadas por otros enemigos
        Set<String> posicionesOcupadas = new HashSet<>();
        for (Enemigo e : enemigos) {
            if (e != enemigo) {
                posicionesOcupadas.add(e.getColumna() + "," + e.getFila());
            }
        }

        // Si el jugador está dentro del rango de percepción
        if (distancia <= enemigo.getPercepcion()) {
            int mejorDistancia = distancia;
            int[] mejorMovimiento = null;

            for (int[] dir : direcciones) {
                int nx = x + dir[0];
                int ny = y + dir[1];
                String posStr = nx + "," + ny;

                // Evalúa movimiento válido y si reduce distancia
                if (nx >= 0 && ny >= 0 && nx < mapaMatriz[0].length && ny < mapaMatriz.length &&
                    mapaMatriz[ny][nx] == 0 && !posicionesOcupadas.contains(posStr)) {

                    int nuevaDistancia = Math.abs(jugadorX - nx) + Math.abs(jugadorY - ny);
                    if (nuevaDistancia < mejorDistancia) {
                        mejorDistancia = nuevaDistancia;
                        mejorMovimiento = new int[] { nx, ny };
                    }

                    // Si el jugador está en la casilla, se prioriza el ataque
                    if (nx == jugadorX && ny == jugadorY) {
                        mejorMovimiento = new int[] { nx, ny };
                        break;
                    }
                }
            }

            if (mejorMovimiento != null) {
                if (mejorMovimiento[0] == jugadorX && mejorMovimiento[1] == jugadorY) {
                    // ⚔️ Ataque enemigo al jugador
                    int danio = Math.max(1, enemigo.getAtaque() - jugador.getDefensa());
                    boolean critico = random.nextDouble() < 0.2;
                    if (critico) danio *= 2;
                    jugador.setVida(jugador.getVida() - danio);
                    if (jugador.getVida() <= 0) {
                        System.out.println("¡El jugador ha sido derrotado!");
                    }
                } else {
                    enemigo.setPosicion(mejorMovimiento[0], mejorMovimiento[1]); // Movimiento hacia el jugador
                }
                dibujarPersonajes(gridPanePersonajes); // Redibuja los personajes
                return true;
            }
        }

        // Movimiento aleatorio si no percibe al jugador
        for (int[] dir : direcciones) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            String posStr = nx + "," + ny;
            if (nx >= 0 && ny >= 0 && nx < mapaMatriz[0].length && ny < mapaMatriz.length &&
                mapaMatriz[ny][nx] == 0 && !posicionesOcupadas.contains(posStr)) {
                posiblesMovimientos.add(new int[] { nx, ny });
            }
        }

        if (!posiblesMovimientos.isEmpty()) {
            int[] movimiento = posiblesMovimientos.get(random.nextInt(posiblesMovimientos.size()));
            enemigo.setPosicion(movimiento[0], movimiento[1]);
            dibujarPersonajes(gridPanePersonajes);
            return true;
        }

        // No se mueve si no hay opciones
        dibujarPersonajes(gridPanePersonajes);
        return false;
    }


    /**
     * Limpia el contenido y las restricciones del GridPane.
     * 
     * @param gridPaneJuego el GridPane que va a ser limpiado.
     * @return el GridPane sin contenido.
     * 
     */
    private GridPane resetearGridPane(GridPane gridPane) {
        gridPane.getChildren().clear();
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();

        return gridPane;
    }


    /**
     * Añade las restricciones en las columnas y filas del GridPane sobre el que se trabaja.
     * 
     * @param gridPaneJuego el GridPane del tablero de juego.
     * 
     */
    private void addConstraints(GridPane gridPane) {
        for (int i = 0; i < mapaMatriz.length; i++) { // Asume que el tablero es cuadrado.
            ColumnConstraints cc = new ColumnConstraints();
            RowConstraints rc = new RowConstraints();

            cc.setPercentWidth(100.0 / mapaMatriz.length);// Establece que el grid tiene que ocupar todo el espacio disponible.
            rc.setPercentHeight(100.0 / mapaMatriz.length);

            gridPane.getColumnConstraints().add(cc);// Añade las restricciones al grid.
            gridPane.getRowConstraints().add(rc);
        }
    }

    /**
     * Calcula el tamaño de cada celda del tablero en función de las dimensiones
     * obtenidas de la matriz.
     * 
     * @param gridPane   estructura de tipo GridPane donde se dispondrán las imágenes de 'suelo', 'pared' y 'escalera'.
     * @param size       tamaño del mapa, suponiendo que es un cuadrado perfecto.
     * @param anchoStack ancho disponible en la estructura AnchorPane para distribuir las celdas.
     * 
     */
    private void actualizarTamCelda(GridPane gridPane, int size, double anchoStack) {

        double tamCelda = anchoStack / size; // size = mapaMatriz.length = columnas

        for (Node node : gridPane.getChildren()) {
            if (node instanceof ImageView) {
                ((ImageView) node).setFitWidth(tamCelda);
                ((ImageView) node).setFitHeight(tamCelda);
            }
        }
    }
}
