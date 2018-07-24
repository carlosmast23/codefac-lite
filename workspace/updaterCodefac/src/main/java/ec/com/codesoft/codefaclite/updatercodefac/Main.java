/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.updatercodefac;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que contrala la logica de copiar la logica de mover el archivo  nuevo codefac.java que esta descargado en la carpeta tmp 
 * y se encarga de remplazar por el archivo de la version funcional y cuando los remplaza para hacer la actualizacion
 * se encarga de abrir la nueva version para que para el usuario sea un proceso transparente la actualizacion de la nueva version
 * @author Carlos
 */
public class Main {
    
    private static String VERSION_NUEVA_NAME="tmp/codefac.jar";
    private static String VERSION_ACTUAL_NAME="codefac.jar";
    
    public static void main(String[] args) {
        try {

            // Creo un file de la ubicacion del archivo descargado de la nueva version y de la version funcional
            File aplicacionActualizada = new File(VERSION_NUEVA_NAME);
            File aplicacionNueva = new File(VERSION_ACTUAL_NAME);

            //Muevo el archivo nuevo para actualizar
            Files.move(aplicacionActualizada.toPath(), aplicacionNueva.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            //Actualizar las nuevas librerias descargadas
            instalarLibreriasNuevas();

            try {
                // Comando para abrir nuevamente el aplicativo java (java -jar codefac.jar)
                List<String> comando = Arrays.asList(
                        "javaw",
                        "-jar",
                        "codefac.jar");
                //Ejecuta el comando como si estuviera directamente ejecutando en el cmd del sistema
                ProcessBuilder pb = new ProcessBuilder(comando);
                //.command(comando)
                //.directory(new File(""));
                Process p = pb.start();

                //Si todo termina correctamente cierra el actualizador 
                System.exit(0);

            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo que se encarga de remplazar las librerias descargadas
     */
    public static void instalarLibreriasNuevas()
    {
        File archivoLibrerias=new File("lib"); //Busca la carpeta de librerias del computador
        
        //Verifica si el directorio existe obtengo una lista de las librerias actuales para comparar
        if(archivoLibrerias.exists())
        {
            //String[] libreriasActuales=archivoLibrerias.list();
            File[] libreriasActuales=archivoLibrerias.listFiles();
            for (File libreria : libreriasActuales) {
                if(libreria.getName().indexOf(".new")>=0) //Si la libreria tiene este formato toca actualizar
                {
                    try {
                        File newFile = new File(libreria.getParent(),libreria.getName().replace(".new",""));
                        Files.move(libreria.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    /*
    public static boolean renombrarArchivo(File file, String nuevoNombre) {
        try {
            File newFile = new File(file.getParent(), nuevoNombre);
            Files.move(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }*/
}
