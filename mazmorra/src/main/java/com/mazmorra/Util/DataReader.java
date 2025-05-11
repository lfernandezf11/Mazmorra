package com.mazmorra.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mazmorra.TipoJugador;
import com.mazmorra.Model.Enemigo;

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


    public static List<Enemigo> leerJsonEnemigos(String rutaJson) {
        List<Enemigo> enemigos = new ArrayList<>(); 
        
        try (BufferedReader br = new BufferedReader(new FileReader(rutaJson))) {
            //Lee el archivo Json como un String.
            StringBuilder jsonBuilder = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                jsonBuilder.append(linea.trim());
            }
            String textoJson = jsonBuilder.toString();

          
            // Elimina los corchetes iniciales y finales (aunque asumimos su existencia, se comprueba).
            if (textoJson.startsWith("["))
                textoJson = textoJson.substring(1);
            if (textoJson.endsWith("]"))
                textoJson = textoJson.substring(0, textoJson.length() - 1);

            // Divide los objetos por "},{", asumiendo que no hay llaves en los valores, y después las elimina.
            String[] enemigosArray = textoJson.split("\\},\\{");
            for (String enemigo : enemigosArray) {
                enemigo = enemigo.replace("{", "").replace("}", "");

                // Divide el array de enemigos por campos clave:valor e inicializa su HashMap. 
                String[] campos = enemigo.split(",");
                Map<String, Object> mapEnemigo = new HashMap<>(); //Object porque almacena valores int y String.

                // Divide cada campo en clave y valor, almacenando sólo los valores no nulos.
                for (String campo : campos) {
                    String[] claveValor = campo.split(":", 2); //Divide para generar un máximo de dos campos
                    if (claveValor.length < 2)
                        continue;
                    String clave = claveValor[0].trim().replaceAll("^\"|\"$", "").toUpperCase(); //Elimina comillas iniciales y finales.
                    String valor = claveValor[1].trim().replaceAll("^\"|\"$", "");

                    // Comprueba si el valor es un dígito y lo parsea. Convierte el tipo en enum.
                    if (valor.matches("\\d+")) {
                        mapEnemigo.put(clave, Integer.parseInt(valor));
                    } else {
                        mapEnemigo.put(clave, valor.toUpperCase());
                    }         
                }
            // Asigna a cada atributo de Enemigo el valor correspondiente a su clave en el map.
            String nombre = mapEnemigo.get("NOMBRE").toString();
            int ataque = (int) mapEnemigo.get("ATAQUE");
            int defensa = (int) mapEnemigo.get("DEFENSA");
            int vida = (int) mapEnemigo.get("VIDA");
            int velocidad = (int) mapEnemigo.get("VELOCIDAD");
            String rutaImagen = mapEnemigo.get("RUTAIMAGEN").toString();
            TipoJugador tipo = TipoJugador.valueOf(mapEnemigo.get("NOMBRE").toString());
            int percepcion = (int) mapEnemigo.get("PERCEPCION");
                
            enemigos.add(new Enemigo(nombre, ataque, defensa, vida, tipo, rutaImagen, percepcion));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  return enemigos;
    } 
}
