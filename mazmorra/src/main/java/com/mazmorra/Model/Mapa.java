package com.mazmorra.Model;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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
    private static String paredPath = "./../../../../resources/com/mazmorra/Images/pared.png"; //Guardamos las rutas en una variable, por legibilidad.
    private static String sueloPath = "./../../../../resources/com/mazmorra/Images/suelo.png";
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
     * @param gridPane estructura de tipo GridPane donde se dispondrán las imágenes. 
     * @param anchoAnchor espacio disponible en el PaneAnchor que ancla el GridPane en la escena.
     */
    public void generarTablero(GridPane gridPaneJuego, double anchoAnchor) throws Exception{
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
                    actualizarTamCelda(gridPaneJuego, mapaMatriz.length, anchoAnchor); //Establece el tamaño de las celdas en función del tamaño del mapa.
                }
        }
    }

    /**
     * Calcula el tamaño de cada celda del tablero en función de las dimensiones obtenidas del txt Mapa.
     * @param gridPane estructura de tipo GridPane donde se dispondrán las imágenes de tipo suelo y pared. 
     * @param size tamaño del mapa, suponiendo que es un cuadrado perfecto.
     * @param anchoAnchor ancho disponible en la estructura AnchorPane para distribuir las celdas.
     */
    private void actualizarTamCelda(GridPane gridPaneJuego, int size, double anchoAnchor){
         {
            double tamCelda = anchoAnchor / size; // size = filas = columnas
        
            for (Node node : gridPaneJuego.getChildren()) {
                if (node instanceof ImageView) {
                    ((ImageView) node).setFitWidth(tamCelda);
                    ((ImageView) node).setFitHeight(tamCelda);
                }
            }
        }
    }
}

