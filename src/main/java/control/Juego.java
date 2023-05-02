/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package control;

import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.event.ActionEvent;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author veneg
 */
@Named(value = "juego")
@SessionScoped
public class Juego implements Serializable {
    
    private String nombre;
    private int intentos;
    private String opcionDificultad;
    
    private final String abesedario = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String[] dificultad = {"FACIL", "DIFICIL"};
    
    private ArrayList<String> palabras;
    private ArrayList<Character> letrasDisponibles;
    private ArrayList<Character> letrasFallidas;
    private ArrayList<Character> letrasCorrectas;
    private ArrayList<Character> palabraSecreta;
    private String palabra;
    
    public Juego() {
        opcionDificultad = "FACIL";
        reiniciar();
    }
    
    public void reiniciar() {
        letrasDisponibles = new ArrayList<Character>();
        letrasFallidas = new ArrayList<Character>();
        letrasCorrectas = new ArrayList<Character>();
        intentos = 5;
        iniciarLetras();
    }
    
    public void palabraAleatoria(){
        URL url = Thread.currentThread().getContextClassLoader().getResource("Datos/palabras_" + opcionDificultad + ".csv");
        Path path = null;
        System.out.print("Palabras "+opcionDificultad);
        try {
            path = Paths.get(url.toURI());
        } catch (URISyntaxException ex) {
            Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
        }
        palabras = LeerPalabras.leer(path.toFile());
        System.out.print("Palabras "+palabras.toString());
        Random ran = new Random();
        palabra = palabras.get(ran.nextInt(palabras.size()));
        palabra = palabra.toUpperCase();
        palabraSecreta = new ArrayList<Character>(Collections.nCopies(palabra.length(), '_'));
    }
    
    private void iniciarLetras() {
        char[] letras = abesedario.toCharArray();
        for (int i = 0; i < letras.length; i++) {
            letrasDisponibles.add(letras[i]);
        }
    }
    
    public void actualizar(ActionEvent event) {
        // Verificamos si acerto la letra seleccionada
        boolean acerto = false;
        Character letra = event.getComponent().getAttributes().get("value").toString().charAt(0);
        for (int i = 0; i < palabra.length(); i++) {
            if (palabra.charAt(i) == letra) {
                letrasCorrectas.add(letra);
                acerto = true;
                break;
            }
        }
        // Eliminamos la letra de las letras que se pueden seleccionar
        letrasDisponibles.remove(letra);
        // Sino acerto agregamos la letra a la lista de letras fallidas
        if (acerto == false) {
            letrasFallidas.add(letra);
            intentos--;
        }
        // Actualizamos el arreglo
        if (letrasCorrectas.isEmpty() == false && acerto == true) {
            for (int i = 0; i < palabra.length(); i++) {
                if (letrasCorrectas.contains(palabra.charAt(i))) {
                    palabraSecreta.set(i, palabra.charAt(i));
                }
            }
        }
    }
    
    public boolean asertoPalabra() {
        for (int i = 0; i < palabra.length(); i++) {
            if (letrasCorrectas.contains(palabra.charAt(i))) {
                continue;
            }
            else {
                return false;
            }
        }
        return true;
    }
    
    public boolean hayIntentos() {
        return intentos != 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Character> getLetrasDisponibles() {
        return letrasDisponibles;
    }

    public ArrayList<Character> getLetrasFallidas() {
        return letrasFallidas;
    }

    public ArrayList<Character> getLetrasCorrectas() {
        return letrasCorrectas;
    }

    public String getPalabra() {
        return palabra;
    }

    public ArrayList<Character> getPalabraSecreta() {
        return palabraSecreta;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public String[] getDificultad() {
        return dificultad;
    }

    public String getOpcionDificultad() {
        return opcionDificultad;
    }

    public void setOpcionDificultad(String opcionDificultad) {
        this.opcionDificultad = opcionDificultad;
    }
    
    public ArrayList<Character> getPalabraSecreta2() {
        ArrayList<Character> ps = new ArrayList<Character>();
        char[] c = palabra.toCharArray();
        for (int i = 0; i < c.length; i++) {
            ps.add(c[i]);
        }
        return ps;
    }
}
