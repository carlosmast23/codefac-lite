/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.file;

import java.io.File;

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
}
