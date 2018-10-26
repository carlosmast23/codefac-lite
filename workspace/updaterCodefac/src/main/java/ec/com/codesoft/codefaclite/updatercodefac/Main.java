/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.updatercodefac;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
    
    private static final String VERSION_NUEVA_NAME="tmp/codefac.jar";
    private static final  String VERSION_ACTUAL_NAME="codefac.jar";
    public static String PID_PROCESO_CODEFAC="";
    
    public static void main(String[] args) {
        //args=new String[]{"15328"};
        try {
            
            /**
             * ===================> VERIFICAR QUE EL PROCESO ANTERIOR DE CODEFAC ESTE FINALIZADO <===============
             */
            if(args.length>0)
            {
                PID_PROCESO_CODEFAC=args[0];
                
                //Verificar si el proceso anterior esta vivo entonces lo elimino
                if(verificarProcesoVivo())
                {
                    matarProceso();
                    //Verificar si despues de matar el proces sigue vivo por algun motivo entonces lanzo el monitor
                    if(verificarProcesoVivo())
                    {
                        crearMonitor("No se puede eliminar el proceso de Codefac para actualizar la nueva versión");
                        return;
                    }
                }
            }
            else
            {
                crearMonitor("Error al pasar el pid del proceso");
                return;
            }

            /**
             * ===================> PROCEDER A MOVER Y COPIAR LOS ARCHIVOS ACTUALIZADOS SI EL ANTERIOR PROCESO FUE TERMINADO <=============
             */
            // Creo un file de la ubicacion del archivo descargado de la nueva version y de la version funcional
            File aplicacionActualizada = new File(VERSION_NUEVA_NAME);
            File aplicacionNueva = new File(VERSION_ACTUAL_NAME);

            //Muevo el archivo nuevo para actualizar el codefac 
            Files.move(aplicacionActualizada.toPath(), aplicacionNueva.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            //Actualizar las nuevas librerias descargadas
            instalarLibreriasNuevas();

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
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            String problema=ex.getMessage() + " \nCausa:" + errors;
            crearMonitor(problema);
            
        }
    }
    
    public static void crearMonitor(String problema)
    {
        MonitorPanel monitorPanel = new MonitorPanel();

        monitorPanel.getTxtProblema().setText(problema);
        monitorPanel.setVisible(true);
    }
    
    public static void matarProceso() throws IOException
    {
        List<String> comando = Arrays.asList(
                "taskkill ",
                "/pid",
                PID_PROCESO_CODEFAC);
        //Ejecuta el comando como si estuviera directamente ejecutando en el cmd del sistema
        ProcessBuilder pb = new ProcessBuilder(comando);
        Process proceso = pb.start();
        
    }
    
    public static Boolean verificarProcesoVivo() throws IOException
    {
        
        List<String> comando = Arrays.asList(
                "tasklist ",
                "/fi",
                "\"pid eq " + PID_PROCESO_CODEFAC + " \"");
        //Ejecuta el comando como si estuviera directamente ejecutando en el cmd del sistema
        ProcessBuilder pb = new ProcessBuilder(comando);
        Process proceso = pb.start();
        InputStream is = proceso.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String line;
        String cadena = "";
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            cadena += line;
        }

        cadena = cadena.toLowerCase();
        if (cadena.indexOf(PID_PROCESO_CODEFAC) >= 0 && cadena.indexOf("pid") >= 0) {
            System.out.println("Proceso Vivo");
            return true;
        } else {
            System.out.println("Proceso muerto");
            return false;
        }
    }
    
    /**
     * Metodo que se encarga de remplazar las librerias descargadas
     */
    public static void instalarLibreriasNuevas() throws IOException
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
                    File newFile = new File(libreria.getParent(), libreria.getName().replace(".new", ""));
                    Files.move(libreria.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }   
}
