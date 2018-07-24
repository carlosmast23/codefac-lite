/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class UtilidadesArchivos {
    public static void crearRuta(String path)
    {
        File file = new File(path);
        //crear toda la ruta si no existe
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            //file.mkdir();
        }
    }
    
    public static boolean verificarExiteArchivo(String path)
    {
        File fichero = new File(path);
        
        if (fichero.exists()) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean renombrarArchivo(File file,String nuevoNombre)
    {
        try {
            File newFile = new File(file.getParent(), nuevoNombre);
            Files.move(file.toPath(), newFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(UtilidadesArchivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * Funcion que lee una archivo plano y trasforma los datos en un String
     * @param path
     * @return 
     */
    public static List<String> leerArchivoPlano(String path) {
        FileReader f = null;
        List<String> resultado = new ArrayList<String>();
        try {
            String cadena;
            f = new FileReader(path);
            BufferedReader b = new BufferedReader(f);
            while ((cadena = b.readLine()) != null) 
            {
                resultado.add(cadena);
            }
            b.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UtilidadesArchivos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UtilidadesArchivos.class.getName()).log(Level.SEVERE, null, ex);
        } finally 
        {
            try {
                f.close();
            } catch (IOException ex) {
                Logger.getLogger(UtilidadesArchivos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }
}
