/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util;

import es.mityc.firmaJava.libreria.utilidades.UtilidadTratarNodo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;

/**
 *
 * @author Carlos
 */
public abstract class ComprobantesElectronicosUtil {
    
    public static void generarArchivoXml(StringWriter xml,String path)
    {
        File file = new File(path);
        //crear toda la ruta si no existe
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            //file.mkdir();
        }

        FileWriter fw = null;
        try {       
            //file.createNewFile();
            fw = new FileWriter(file);
            fw.write(xml.toString());
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(ComprobantesElectronicosUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(ComprobantesElectronicosUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void generarArchivoXml(String xml,String path)
    {
        File file = new File(path);
        //crear toda la ruta si no existe
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            //file.mkdir();
        }

        FileWriter fw = null;
        try {       
            //file.createNewFile();
            fw = new FileWriter(file);
            fw.write(xml);
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(ComprobantesElectronicosUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(ComprobantesElectronicosUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void generarArchivoXml(Document documento, String path)
            throws Exception {
        File file = new File(path);
        //crear toda la ruta si no existe
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            //file.mkdir();
        }
        
        
        FileOutputStream flujoSalida = null;
        try {
            try {
                flujoSalida = new FileOutputStream(path);
                UtilidadTratarNodo.saveDocumentToOutputStream(documento,
                        flujoSalida, true);
            } finally {
                flujoSalida.close();
            }
        } catch (FileNotFoundException e) {
            throw new Exception("Error al salvar el documento");
        }
    }
    
    public static boolean eliminarArchivo(String rutaArchivo) {
        File archivo = new File(rutaArchivo);

        if (archivo.exists() && archivo.canWrite() && !archivo.isDirectory()) {
            archivo.delete();
        }

        return false;
    }
    
    public static String getPathXml(String path,String carpeta)
    {
        return path+"/"+carpeta;
    }
    
    public static byte[] archivoToByte(File file) throws IOException {
        byte[] buffer = new byte[(int) file.length()];
        InputStream ios = null;
        try {
            ios = new FileInputStream(file);
            if (ios.read(buffer) == -1) {
                throw new IOException(
                        "EOF reached while trying to read the whole file");
            }
            return buffer;
        } finally {
            try {
                if (ios != null) {
                    ios.close();
                }
            } catch (IOException e) {

            }
        }
    }
    
    public static String dateToString(Date fecha)
    {
        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
        return format.format(fecha);
    }
    
    public static String dateToString(Timestamp fecha)
    {
        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yyyy");
        return format.format(fecha);
    }
    
    
}
