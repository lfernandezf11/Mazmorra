package com.mazmorra.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                if (c.toUpperCase().equals("S")) {
                    matrizMapa[i][j] = 0;
                } else if (c.toUpperCase().equals("P")) {
                    matrizMapa[i][j] = 1;
                } else {
                    throw new IllegalArgumentException("Carácter inválido en la coordenada " + matrizMapa[i][j]);
                }
            }
        }

        return matrizMapa;
    }

     public static List<Map<String, Object>> leerJson(String rutaArchivo) throws IOException {
        // Leer todo el archivo en un String
        BufferedReader br = new BufferedReader(new FileReader(rutaArchivo));
        StringBuilder sb = new StringBuilder();
        String linea;
        while ((linea = br.readLine()) != null) {
            sb.append(linea.trim());
        }
        br.close();

        // Limpiar y preparar el texto
        String texto = sb.toString().trim();
        texto = texto.substring(1, texto.length() - 1); // Quitar corchetes [ ]
        String[] elementos = texto.split("\\},\\s*\\{");

        List<Map<String, Object>> lista = new ArrayList<>();

        for (String elem : elementos) {
            elem = elem.trim();
            if (!elem.startsWith("{")) elem = "{" + elem;
            if (!elem.endsWith("}")) elem = elem + "}";
            elem = elem.substring(1, elem.length() - 1); // Quitar llaves { }

            String[] campos = elem.split(",");
            Map<String, Object> dic = new HashMap<>();

            for (String campo : campos) {
                String[] claveValor = campo.split(":");
                String clave = claveValor[0].trim().replace("\"", "");
                String valor = claveValor[1].trim();

                // Detectar si es string o número
                if (valor.startsWith("\"")) {
                    valor = valor.replace("\"", "");
                    dic.put(clave, valor);
                } else {
                    dic.put(clave, Integer.parseInt(valor));
                }
            }
            lista.add(dic);
        }
        return lista;
    }
}
