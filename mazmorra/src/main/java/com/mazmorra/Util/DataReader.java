package com.mazmorra.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mazmorra.Model.Mapa;

public class DataReader {

    public static int[][] leerMapa(String path) throws IOException {
        List<String> tuplas = new ArrayList<>();

        // Leer líneas del archivo
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    tuplas.add(linea.trim());
                }
            }
        }

        int tam = tuplas.size(); // Asumimos que el mapa es cuadrado
        int[][] matrizMapa = new int[tam][tam];

        for (int i = 0; i < tam; i++) {
            String tupla = tuplas.get(i);
            if (tupla.length() != tam) {
                throw new IllegalArgumentException("Las proporciones del mapa no son correctas");
            }

            for (int j = 0; j < tam; j++) {
                String c = "" + tupla.charAt(j);
                if (c.toUpperCase().equals('S')) {
                    matrizMapa[i][j] = 0;
                } else if (c.toUpperCase().equals('P')) {
                    matrizMapa[i][j] = 1;
                } else {
                    throw new IllegalArgumentException("Carácter inválido en la coordenada " + matrizMapa[i][j]);
                }
            }
        }

        return matrizMapa;
    }
}

    
  
    // List <Enemigo> leerEnemigos(String path);

