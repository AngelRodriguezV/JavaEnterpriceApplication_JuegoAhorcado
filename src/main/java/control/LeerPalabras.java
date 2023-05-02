/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 *
 * @author veneg
 */
public class LeerPalabras {
    
    public static ArrayList<String> leer(File ruta) {
        ArrayList<String> datos = new ArrayList<String>();
        try {
            CSVReader reader = new CSVReader(new FileReader(ruta));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                for (String data : nextLine) {
                    datos.add(data);
                }
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        return datos;
    }
    
}
