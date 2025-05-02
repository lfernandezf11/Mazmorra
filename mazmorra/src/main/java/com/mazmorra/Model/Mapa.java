package com.mazmorra.Model;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

 /**
  * Gestiona la construcción gráfica del tablero de juego a partir de una matriz de números enteros proveniente del DataReader.
  * <p>
  * Las imágenes correspondientes a "suelo" y "pared" son enrutadas de forma estática desde Resources. <br>
  * La matriz se dibuja programáticamente mediante la creación de un elemento de tipo Grid, <br>
  * que posteriormente conecta con la escena a través de un AnchorPane.
  * </p>
  * 
  * @author Miguel González Seguro
  * @author Lucía Fernández Florencio
  * 
  */

public class Mapa {
    private static String paredPath = "mazmorra/src/main/resources/com/mazmorra/Images/pared.png"; //Guardamos las rutas en una variable, por legibilidad.
    private static String sueloPath = "mazmorra/src/main/resources/com/mazmorra/Images/pared.png";
    private int[][] mapaMatriz; //Matriz de datos procedente de DataReader.java

    /*Constructor parametrizado */
    public Mapa(int[][] mapaMatriz) {
        this.mapaMatriz = mapaMatriz;
    }

    /**
     * Recupera la información de la matriz del mapa.
     * @return la matriz del mapa.
     */
    public int[][] getMapaMatriz() {
        return mapaMatriz;
    }
    
    /**
     * Traslada la disposición de las celdas de tipo suelo y techo a formato gráfico en Scene Builder.
     * 
     * @param gridPaneJuego estructura de tipo grid donde se dispondrán las imágenes. 
     * @param stackPane nodo padre de gridPaneJuego, que determina su tamaño.
     * 
     */
    public void generarTablero(GridPane gridPaneJuego, StackPane stackPaneJuego){
        resetearGridPane(gridPaneJuego); //Limpia el tablero antes de cada regeneración.
        if(resetearGridPane(gridPaneJuego) != null) {
            addConstraints(gridPaneJuego);
        }
        double anchoStack = stackPaneJuego.getWidth();

        for (int i = 0; i < mapaMatriz.length; i++) { 
            for (int j = 0; j < mapaMatriz[i].length; j++) {
                int celda = mapaMatriz[i][j];
                ImageView imageView = new ImageView(); //Crea una vista gráfica por cada celda.
    
                if (celda == 0) {
                    imageView.setImage(new Image(paredPath));
                } else {
                    imageView.setImage(new Image(sueloPath));
                }
                gridPaneJuego.add(imageView, j, i); // GridPane: (NodeChild, int columna, int fila): j se tiene que especificar antes que i.
                actualizarTamCelda(gridPaneJuego, mapaMatriz.length, anchoStack); //Establece el tamaño de las celdas en función del tamaño del mapa.
            }
        }
    }

    /**
     * Limpia el contenido y las restricciones del GridPane.
     * 
     * @param gridPaneJuego el GridPane que va a ser limpiado.
     * @return el GridPane sin contenido.
     * 
     */
    private GridPane resetearGridPane(GridPane gridPaneJuego){
        gridPaneJuego.getChildren().clear();
        gridPaneJuego.getColumnConstraints().clear();
        gridPaneJuego.getRowConstraints().clear();

        return gridPaneJuego;
    }
    
    /**
     * Añade las restricciones en las columnas y filas del GridPane sobre el que se trabaja.
     * 
     * @param gridPaneJuego el GridPane del tablero de juego.
     * 
     */
    private void addConstraints(GridPane gridPaneJuego){
        for (int i = 0; i < mapaMatriz.length; i++) { //Asume que el tablero es cuadrado.
            ColumnConstraints cc = new ColumnConstraints(); 
            RowConstraints rc = new RowConstraints();
            
            cc.setPercentWidth(100.0 / mapaMatriz.length);//Establece que el grid tiene que ocupar todo el espacio disponible.
            rc.setPercentHeight(100.0 / mapaMatriz.length);
            
            gridPaneJuego.getColumnConstraints().add(cc);//Añade las restricciones al grid.
            gridPaneJuego.getRowConstraints().add(rc);
        }
    }
    
    /**
     * Calcula el tamaño de cada celda del tablero en función de las dimensiones obtenidas del txt Mapa.
     * 
     * @param gridPane estructura de tipo GridPane donde se dispondrán las imágenes de tipo suelo y pared. 
     * @param size tamaño del mapa, suponiendo que es un cuadrado perfecto.
     * @param anchoStack ancho disponible en la estructura AnchorPane para distribuir las celdas.
     * 
     */
    private void actualizarTamCelda(GridPane gridPaneJuego, int size, double anchoStack){
         
            double tamCelda = anchoStack / size; // size = mapaMatriz.length = columnas
        
            for (Node node : gridPaneJuego.getChildren()) {
                if (node instanceof ImageView) {
                    ((ImageView) node).setFitWidth(tamCelda);
                    ((ImageView) node).setFitHeight(tamCelda);
                }
            }
        }
    }





