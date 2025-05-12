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

/**
 * Gestiona la construcción gráfica del tablero de juego a partir de una matriz
 * de números enteros proveniente del DataReader.
 * <p>
 * Las imágenes correspondientes a "suelo" y "pared" son enrutadas de forma
 * estática desde Resources. <br>
 * La matriz se dibuja programáticamente mediante la creación de un elemento de
 * tipo Grid, <br>
 * que posteriormente conecta con la escena a través de un AnchorPane.
 * </p>
 * 
 * @author Miguel González Seguro
 * @author Lucía Fernández Florencio
 * 
 */

public class Mapa {
    Random random = new Random();
    private static String paredPath = "/com/mazmorra/Images/pared.png"; // La ruta relativa de img prescinde de
                                                                        // src/main/resources, porque se recupera desde
                                                                        // el classpath (desde target/classes)
    private static String sueloPath = "/com/mazmorra/Images/suelo.png";
    private int[][] mapaMatriz; // Matriz de datos procedente de DataReader.java
    private List<Personaje> personajes = Proveedor.getInstance().getListaDePersonajesIncluyendoJugador();
    private Jugador jugador = Proveedor.getInstance().getJugador();
    private List<Enemigo> enemigos = Proveedor.getInstance().getListaEnemigos();

    private Set<String> posicionesOcupadas = new HashSet<>();
    // POSICION DEL JUGADOR
    private int posicionX;
    private int posicionY;

    private boolean personajesGenerados = false;

    /* Constructor parametrizado */
    public Mapa(int[][] mapaMatriz) {
        this.mapaMatriz = mapaMatriz;
    }

    /**
     * Recupera la información de la matriz del mapa.
     * 
     * @return la matriz del mapa.
     */
    public int[][] getMapaMatriz() {
        return mapaMatriz;
    }

    /**
     * Traslada la disposición de las celdas de tipo suelo y techo a formato gráfico
     * en Scene Builder.
     * 
     * @param gridPaneJuego estructura de tipo grid donde se dispondrán las
     *                      imágenes.
     * @param stackPane     nodo padre de gridPaneJuego, que determina su tamaño.
     * 
     */
    public void generarTablero(GridPane gridPaneJuego, StackPane stackPaneJuego) {
        resetearGridPane(gridPaneJuego); // Limpia el tablero antes de cada regeneración.
        if (resetearGridPane(gridPaneJuego) != null) {
            addConstraints(gridPaneJuego);
        }
        double anchoStack = stackPaneJuego.getWidth();

        for (int i = 0; i < mapaMatriz.length; i++) {
            for (int j = 0; j < mapaMatriz[i].length; j++) {
                int celda = mapaMatriz[i][j];
                ImageView imageView = new ImageView(); // Crea una vista gráfica por cada celda.

                if (celda == 0) {
                    imageView.setImage(new Image(getClass().getResource(sueloPath).toExternalForm()));
                    // Obtiene el recurso dentro de la ruta de clase, y convierte la referencia a
                    // una URL completa que JavaFX pueda interpretar.
                } else {
                    imageView.setImage(new Image(getClass().getResource(paredPath).toExternalForm()));
                }
                gridPaneJuego.add(imageView, j, i); // GridPane: (NodeChild, int columna, int fila): j se tiene que
                                                    // especificar antes que i.
                actualizarTamCelda(gridPaneJuego, mapaMatriz.length, anchoStack);
            }
        }
    }

    public void generarPosicionesIniciales() {
        if (personajesGenerados)
            return;

        int posicionX, posicionY;
        for (Personaje personaje : personajes) {
            do {
                posicionX = random.nextInt(mapaMatriz[0].length);
                posicionY = random.nextInt(mapaMatriz.length);
            } while (mapaMatriz[posicionY][posicionX] != 0 || posicionesOcupadas.contains(posicionX + "," + posicionY));

            posicionesOcupadas.add(posicionX + "," + posicionY);
            this.posicionX = posicionX;
            this.posicionY = posicionY;
            personaje.setPosicion(posicionX, posicionY);
        }

        personajesGenerados = true;
    }

    public void dibujarPersonajes(GridPane gridPanePersonajes) {
        resetearGridPane(gridPanePersonajes);
        addConstraints(gridPanePersonajes);

        posicionesOcupadas.clear();
        posicionesOcupadas.add(jugador.getColumna() + "," + jugador.getFila());
        for (Enemigo enemigo : enemigos) {
            posicionesOcupadas.add(enemigo.getPosicionX() + "," + enemigo.getPosicionY());
        }

        // Dibuja el jugador en la posición actual

        ImageView entidadJugador = new ImageView();
        String url = Proveedor.getInstance().getJugador().getRutaImagen();
        entidadJugador.setImage(new Image(getClass().getResource(url).toExternalForm()));
        entidadJugador.setFitWidth(32);
        entidadJugador.setFitHeight(32);
        gridPanePersonajes.add(entidadJugador, jugador.getColumna(), jugador.getFila());

        System.out.println("🎯 Posiciones ocupadas:");
        for (String pos : posicionesOcupadas) {
            System.out.println(" - " + pos);
        }
        // Dibuja los enemigos en sus posiciones guardadas (aquí solo si tienes
        // posiciones guardadas)
        List<Enemigo> enemigos = Proveedor.getInstance().getListaEnemigos();
        for (Enemigo enemigo : enemigos) {
            int enemigoX = enemigo.getPosicionX(); // Debes tener estos métodos si quieres mover enemigos
            int enemigoY = enemigo.getPosicionY();
            ImageView enemigoView = new ImageView();
            String urlEnemigo = enemigo.getRutaImagen();
            enemigoView.setImage(new Image(getClass().getResource(urlEnemigo).toExternalForm()));
            enemigoView.setFitWidth(32);
            enemigoView.setFitHeight(32);
            gridPanePersonajes.add(enemigoView, enemigoX, enemigoY);
        }
    }

    // METODO MOVER JUGADOR
    public boolean moverJugador(int dx, int dy, GridPane gridPanePersonajes, StackPane stackPaneJuego) {
        int nuevoX = jugador.getColumna() + dx;
        int nuevoY = jugador.getFila() + dy;

        // Fuera de los límites
        if (nuevoX < 0 || nuevoX >= mapaMatriz[0].length || nuevoY < 0 || nuevoY >= mapaMatriz.length) {
            return false;
        }

        // Es una pared
        if (mapaMatriz[nuevoY][nuevoX] != 0) {
            return false;
        }

        // Hay otro personaje (enemigo)
        String posStr = nuevoX + "," + nuevoY;
        if (posicionesOcupadas.contains(posStr)) {
            return false;
        }

        // Movimiento válido
        posicionesOcupadas.remove(jugador.getColumna() + "," + jugador.getFila());
        jugador.setPosicion(nuevoX, nuevoY);
        posicionesOcupadas.add(posStr);

        dibujarPersonajes(gridPanePersonajes);
        return true;
    }

    public boolean moverEnemigo(Enemigo enemigo, GridPane gridPanePersonajes, StackPane stackPaneJuego) {
        int x = enemigo.getPosicionX();
        int y = enemigo.getPosicionY();
        int[][] direcciones = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
        List<int[]> posiblesMovimientos = new java.util.ArrayList<>();
        int jugadorX = Proveedor.getInstance().getJugador().getColumna();
        int jugadorY = Proveedor.getInstance().getJugador().getFila();

        List<Enemigo> enemigos = Proveedor.getInstance().getListaEnemigos();
        java.util.Set<String> posicionesOcupadas = new java.util.HashSet<>();
        for (Enemigo e : enemigos) {
            if (e != enemigo) {
                posicionesOcupadas.add(e.getPosicionX() + "," + e.getPosicionY());
            }
        }

        for (int[] dir : direcciones) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            String posStr = nx + "," + ny;
            if (nx >= 0 && nx < mapaMatriz[0].length &&
                    ny >= 0 && ny < mapaMatriz.length &&
                    mapaMatriz[ny][nx] == 0) {
                // Permite moverse a la casilla del jugador para atacar
                if (!posicionesOcupadas.contains(posStr)) {
                    posiblesMovimientos.add(new int[] { nx, ny });
                }
            }
        }

        if (!posiblesMovimientos.isEmpty()) {
            int[] movimiento = posiblesMovimientos.get(random.nextInt(posiblesMovimientos.size()));
            if (movimiento[0] == jugadorX && movimiento[1] == jugadorY) {
                // Aquí puedes poner la lógica de ataque al jugador
                System.out.println("¡El enemigo ataca al jugador!");
                // No muevas al enemigo a la casilla del jugador
            } else {
                enemigo.setPosicion(movimiento[0], movimiento[1]);
            }
            dibujarPersonajes(gridPanePersonajes);
            return true;
        }
        dibujarPersonajes(gridPanePersonajes); // Redibuja aunque no se mueva
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
     * Añade las restricciones en las columnas y filas del GridPane sobre el que se
     * trabaja.
     * 
     * @param gridPaneJuego el GridPane del tablero de juego.
     * 
     */
    private void addConstraints(GridPane gridPane) {
        for (int i = 0; i < mapaMatriz.length; i++) { // Asume que el tablero es cuadrado.
            ColumnConstraints cc = new ColumnConstraints();
            RowConstraints rc = new RowConstraints();

            cc.setPercentWidth(100.0 / mapaMatriz.length);// Establece que el grid tiene que ocupar todo el espacio
                                                          // disponible.
            rc.setPercentHeight(100.0 / mapaMatriz.length);

            gridPane.getColumnConstraints().add(cc);// Añade las restricciones al grid.
            gridPane.getRowConstraints().add(rc);
        }
    }

    /**
     * Calcula el tamaño de cada celda del tablero en función de las dimensiones
     * obtenidas de la matriz.
     * 
     * @param gridPane   estructura de tipo GridPane donde se dispondrán las
     *                   imágenes de tipo suelo y pared.
     * @param size       tamaño del mapa, suponiendo que es un cuadrado perfecto.
     * @param anchoStack ancho disponible en la estructura AnchorPane para
     *                   distribuir las celdas.
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
