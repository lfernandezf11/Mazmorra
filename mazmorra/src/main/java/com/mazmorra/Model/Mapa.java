package com.mazmorra.Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Mapa {
    private static String paredPath = "./../../../../resources/com/mazmorra/Images/pared.png";
    private static String sueloPath = "./../../../../resources/com/mazmorra/Images/suelo.png";
    private int[][] mapaMatriz; 

    public Mapa(int[][] mapaMatriz) {
        this.mapaMatriz = mapaMatriz;
    }

    public void generarTablero(GridPane gridPane) {

        for (int i = 0; i < mapaMatriz.length; i++) {
            for (int j = 0; j < mapaMatriz[i].length; j++) {
                int celda = mapaMatriz[i][j];
                ImageView imageView = new ImageView();

                if (celda == 0) {
                    imageView.setImage(new Image(paredPath));
                } else {
                    imageView.setImage(new Image(sueloPath));
                }

                gridPane.add(imageView, j, i); // GridPane: (NodeChild, int columna, int fila): j se tiene que especificar antes que i.

                actualizarTamCelda(gridPane, mapaMatriz.length, 10);
            }
        }
    

    }


    private void actualizarTamCelda(GridPane gridPane, int size, double anchoAnchor){
         {
            double tamañoCelda = anchoDisponible / size; // size = filas = columnas
        
            for (Node node : gridPane.getChildren()) {
                if (node instanceof ImageView) {
                    ((ImageView) node).setFitWidth(tamañoCelda);
                    ((ImageView) node).setFitHeight(tamañoCelda);
                }
            }
        }
    }



}



/**Crea el grid a partir de los datos del tjt, recogidos desde DataReader.  */