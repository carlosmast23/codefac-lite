
import ec.com.codesoft.codefaclite.updatercodefac.Main;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.apache.commons.io.FileUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Carlos
 */
public class MainTest {
    private static final String CARPETA_PROYECTO_CACHE_TOMCAT="C:\\Users\\CARLOS_CODESOFT\\Documents\\github\\codefac-lite\\workspace\\main\\tomcat9\\webapps\\codefac";
    public static void main(String[] args) {
        
        /*try {
        //ClienteParametros.jar
        //JOptionPane.showMessageDialog(null,"Pid:"+args[0]);
        
        List<String> comando = Arrays.asList(
        "javaw",
        "-jar",
        "ClienteParametros.jar",
        "87653");
        //Ejecuta el comando como si estuviera directamente ejecutando en el cmd del sistema
        ProcessBuilder pb = new ProcessBuilder(comando);
        pb.start();
        //System.out.println(args[0]);
        } catch (IOException ex) {
        Logger.getLogger(MainTest.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        //File directorioEliminar=new File(CARPETA_PROYECTO_CACHE_TOMCAT);
        //Paths.get(CARPETA_PROYECTO_CACHE_TOMCAT).toFile()
        //System.out.println(directorioEliminar.getAbsoluteFile());
        //Files.deleteIfExists(Paths.get(CARPETA_PROYECTO_CACHE_TOMCAT));
        
        //FileUtils.deleteDirectory(Paths.get(CARPETA_PROYECTO_CACHE_TOMCAT).toFile());
        //Main.deleteDir(Paths.get(CARPETA_PROYECTO_CACHE_TOMCAT).toFile());
    }
}
