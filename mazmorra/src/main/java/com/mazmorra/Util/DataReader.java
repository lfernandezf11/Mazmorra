package com.mazmorra.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.mazmorra.Model.Enemigo;
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

    public static List<Enemigo> leerEnemigos(String path) throws IOException {
        List<Enemigo> enemigos = new ArrayList<>();
        StringBuilder jsonBuilder = new StringBuilder();

        // Leer todo el archivo en un String
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                jsonBuilder.append(linea.trim());
            }
        }

        String json = jsonBuilder.toString();

        // Eliminar los corchetes exteriores y separar los objetos
        json = json.substring(1, json.length() - 1); // Quita [ y ]
        String[] objetos = json.split("\\},\\{");

        for (int i = 0; i < objetos.length; i++) {
            String obj = objetos[i];
            if (!obj.startsWith("{"))
                obj = "{" + obj;
            if (!obj.endsWith("}"))
                obj = obj + "}";

            Enemigo enemigo = new Enemigo(obj, i, i, i, i, i);

            // Extraer los campos manualmente
            enemigo.setNombre(extraerString(obj, "nombre"));
            enemigo.setAtaque(extraerInt(obj, "ataque"));
            enemigo.setVida(extraerInt(obj, "vida"));
            enemigo.setDefensa(extraerInt(obj, "defensa"));
            enemigo.setVelocidad(extraerInt(obj, "velocidad"));
            enemigo.setPercepcion(extraerInt(obj, "percepcion"));

            enemigos.add(enemigo);
        }

        return enemigos;
    }

    // Métodos auxiliares para extraer campos
    private static String extraerString(String json, String campo) {
        String patron = "\"" + campo + "\"\\s*:\\s*\"([^\"]*)\"";
        return extraerValor(json, patron);
    }

    private static int extraerInt(String json, String campo) {
        String patron = "\"" + campo + "\"\\s*:\\s*(\\d+)";
        String valor = extraerValor(json, patron);
        return Integer.parseInt(valor);
    }

    private static String extraerValor(String json, String patron) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(patron);
        java.util.regex.Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
}
