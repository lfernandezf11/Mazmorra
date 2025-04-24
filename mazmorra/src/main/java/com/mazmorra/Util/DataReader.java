package com.mazmorra.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    public static int[][] leerMapaDesdeArchivo(String rutaArchivo) throws IOException {
        List<String> lineas = new ArrayList<>();

        // Leer líneas del archivo
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    lineas.add(linea.trim());
                }
            }
        }

        int tamaño = lineas.size(); // Asumimos que el mapa es cuadrado
        int[][] matriz = new int[tamaño][tamaño];

        for (int i = 0; i < tamaño; i++) {
            String fila = lineas.get(i);
            if (fila.length() != tamaño) {
                throw new IllegalArgumentException("El mapa no es cuadrado: fila " + i + " tiene longitud " + fila.length());
            }

            for (int j = 0; j < tamaño; j++) {
                char c = fila.charAt(j);
                if (c == 'S') {
                    matriz[i][j] = 0;
                } else if (c == 'P') {
                    matriz[i][j] = 1;
                } else {
                    throw new IllegalArgumentException("Carácter no reconocido: '" + c + "' en (" + i + ", " + j + ")");
                }
            }
        }

        return matriz;
    }
}

    
    // int [][] leerMapa(String path);
    // List <Enemigo> leerEnemigos(String path);

