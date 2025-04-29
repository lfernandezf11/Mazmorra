package com.mazmorra.Controllers;

import com.mazmorra.Interfaces.Observer;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;;

public class JuegoController implements Observer {

    @FXML
    private GridPane gridPaneJuego;

    @FXML
    private AnchorPane anchorPaneJuego;


   /*initialize()
    Mapa mapa = new Mapa(mapaMatriz);
mapa.generarTablero(gridPaneJuego, anchorPaneJuego.getWidth());*/

    @Override
    public void onChange() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onChange'");
    }

   
    
}
