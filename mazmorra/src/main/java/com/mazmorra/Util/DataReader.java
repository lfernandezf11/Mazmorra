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

/**
 * Clase utilitaria encargada de la lectura y la carga de datos desde archivos externos.
 * 
 * Proporciona métodos para recibir dos tipos de información:
 * - La distribución del mapa de juego, que es extraida de un archivo .txt.
 * - La configuración de los atributos de los enemigos, que es extraida desde un fichero JSOn. 
 * 
 * @author Miguel González Seguro
 * @author Lucía Fernández Florencio
 */
public class DataReader {

    /**
     * Lee un archivo de texto que representa el mapa del juego.
     * Cada línea contiene caracteres que definen el tipo de celda:
     * - 'S' para suelo (valor 0)
     * - 'P' para pared (valor 1)
     * - 'E' para escalera (valor 2)
     *
     * @param path la ruta del archivo que contiene el mapa.
     * @return una matriz de números enteros representativa del mapa de juego.
     * @throws IOException si ocurre un error al leer el archivo.
     * @throws IllegalArgumentException si las dimensiones del mapa no son cuadradas o si hay caracteres inválidos.
     */
    public static int[][] leerMapa(String path) throws IOException {
        List<String> tuplas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    tuplas.add(linea.trim());
                }
            }
        }

        //Genera una matriz cuadrada con el número de filas del archivo.
        int tam = tuplas.size();
        int[][] matrizMapa = new int[tam][tam];

        //Comprueba que el número de columnas coincide con el número de filas. 
        for (int i = 0; i < tam; i++) {
            String tupla = tuplas.get(i);
            if (tupla.length() != tam) {
                throw new IllegalArgumentException("Las proporciones del mapa no son correctas");
            }

            // Traduce los caracteres del archivo en números enteros.
            for (int j = 0; j < tam; j++) {
                char c = Character.toUpperCase(tupla.charAt(j));
                switch (c) {
                    case 'S':
                        matrizMapa[i][j] = 0;
                        break;
                    case 'P':
                        matrizMapa[i][j] = 1;
                        break;
                    case 'E':
                        matrizMapa[i][j] = 2;
                        break;
                    case 'T':
                        matrizMapa[i][j] = 3;
                        break;
                    default:
                        throw new IllegalArgumentException("Carácter inválido en la coordenada [" + i + "," + j + "]");
                }
            }
        }

        return matrizMapa;
    }

    /**
     * Lee un archivo JSON que contiene la información sobre los enemigos y los almacena en una lista de objetos Enemigo. 
     * 
     * Cada enemigo tiene como atributo: nombre, ataque, defensa, vida, velocidad, rutaImagen y percepcion.
     * 
     * @param rutaJson la ruta del archivo JSON a procesar.
     * @return una lista de enemigos cargados desde el archivo.
     */
    public static List<Enemigo> leerJsonEnemigos(String rutaJson) {
        //Inicializa la lista de Enemigos.
        List<Enemigo> enemigos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaJson))) {
            StringBuilder jsonBuilder = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                jsonBuilder.append(linea.trim());
            }
            // Declara el string donde se almacenará todo el texto del archivo. 
            String textoJson = jsonBuilder.toString();
            // Elimina los corchetes de inicio y fin
            if (textoJson.startsWith("[")) textoJson = textoJson.substring(1);
            if (textoJson.endsWith("]")) textoJson = textoJson.substring(0, textoJson.length() - 1);

            // Genera un array de enemigos y elimina las llaves
            String[] enemigosArray = textoJson.split("\\},\\{");
            for (String enemigo : enemigosArray) {
                enemigo = enemigo.replace("{", "").replace("}", "");
                // Separa los campos de cada enemigo
                String[] campos = enemigo.split(",");
                Map<String, Object> mapEnemigo = new HashMap<>();

                // Separa cada campo en pares clave/valor.
                for (String campo : campos) {
                    String[] claveValor = campo.split(":", 2);
                    if (claveValor.length < 2) continue;

                    //Elimina las comillas iniciales y finales
                    String clave = claveValor[0].trim().replaceAll("^\"|\"$", "").toUpperCase();
                    String valor = claveValor[1].trim().replaceAll("^\"|\"$", "");
                    //Comprueba si el valor es un Integer o un String antes de almacenarlo en el HashMap del enemigo.
                    if (valor.matches("\\d+")) {
                        mapEnemigo.put(clave, Integer.parseInt(valor));
                    } else {
                        mapEnemigo.put(clave, valor.toUpperCase());
                    }
                }

                // Asigna el valor de cada clave al atributo homólogo de Enemigo
                String nombre = mapEnemigo.get("NOMBRE").toString();
                int ataque = (int) mapEnemigo.get("ATAQUE");
                int defensa = (int) mapEnemigo.get("DEFENSA");
                int vida = (int) mapEnemigo.get("VIDA");
                String rutaImagen = mapEnemigo.get("RUTAIMAGEN").toString();
                TipoJugador tipo = TipoJugador.valueOf(nombre.toUpperCase());
                int percepcion = (int) mapEnemigo.get("PERCEPCION");
                
                //Añade el Enemigo a la lista 
                enemigos.add(new Enemigo(nombre, ataque, defensa, vida, tipo, rutaImagen, percepcion));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return enemigos;
    }
}
