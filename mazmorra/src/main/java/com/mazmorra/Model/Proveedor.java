package com.mazmorra.Model;

import java.util.ArrayList;
import java.util.List;


/*LUCÍA: AQUÍ, EN PROVEEDOR, REESCRIBIR LOS MÉTODOS PARA LLAMARLOS BIEN DESDE APP. 
 * CREAR UN JSON CON RUTA VÁLIDA.
 */
public class Proveedor {

    private static Proveedor instance;
    private Jugador jugador;
    private List<Enemigo> enemigos;

    public static Proveedor getInstance() {
        if (instance == null) {
            instance = new Proveedor();
        }
        return instance;
    }

    //Aquí no se puede inicializar un Jugador parametrizado, puesto que el constructor intentaría 
    //obtener el tipo para calcular la velocidad antes de haber sido asignado.
    private Proveedor() { 
        this.enemigos = new ArrayList<>();
        this.jugador = new Jugador();
    }

    public Jugador getJugador() {
        return this.jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public List<Enemigo> getListaEnemigos() {
        return enemigos;
    }

    public List<Personaje> getListaDePersonajesIncluyendoJugador() {
        List<Personaje> lista = new ArrayList<>();
        if (jugador != null) {
            lista.add(jugador);
        }
    
        if (enemigos != null && !enemigos.isEmpty()) {
            lista.addAll(enemigos);
        }
        return lista;
    }

    public void cargarEnemigosDesdeJson(String rutaJson) {
        this.enemigos.clear();
        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(rutaJson))) {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line.trim());
            }
            String json = jsonBuilder.toString();

            // Elimina los corchetes iniciales y finales
            if (json.startsWith("["))
                json = json.substring(1);
            if (json.endsWith("]"))
                json = json.substring(0, json.length() - 1);

            // Divide los objetos por "},{" (asume que no hay "},{" en los valores)
            String[] enemigosArray = json.split("\\},\\{");

            for (String enemigoStr : enemigosArray) {
                // Limpia llaves residuales
                enemigoStr = enemigoStr.replace("{", "").replace("}", "");

                // Divide por coma para obtener los pares clave:valor
                String[] campos = enemigoStr.split(",");

                String nombre = "";
                int ataque = 0, vida = 0, defensa = 0, velocidad = 0, percepcion = 0;

                for (String campo : campos) {
                    String[] keyValue = campo.split(":");
                    if (keyValue.length < 2)
                        continue;

                    String key = keyValue[0].replace("\"", "").trim();
                    String value = keyValue[1].replace("\"", "").trim();

                    switch (key) {
                        case "nombre":
                            nombre = value;
                            break;
                        case "ataque":
                            ataque = Integer.parseInt(value);
                            break;
                        case "vida":
                            vida = Integer.parseInt(value);
                            break;
                        case "defensa":
                            defensa = Integer.parseInt(value);
                            break;
                        case "velocidad":
                            velocidad = Integer.parseInt(value);
                            break;
                        case "percepcion":
                            percepcion = Integer.parseInt(value);
                            break;
                    }
                }
                this.enemigos.add(new Enemigo(nombre, ataque, vida, defensa, velocidad, percepcion));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}