/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util;

import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import static ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService.CARPETA_AUTORIZADOS;
import static ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService.CARPETA_CONFIGURACION;
import ec.com.codesoft.codefaclite.facturacionelectronica.ServicioSri;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FacturaComprobante;
import ec.com.codesoft.codefaclite.ws.recepcion.Comprobante;
import es.mityc.firmaJava.libreria.utilidades.UtilidadTratarNodo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;

/**
 *
 * @author Carlos
 */
public abstract class ComprobantesElectronicosUtil {

    public static void generarArchivoXml(StringWriter xml, String path) {
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
            //fw.write(xml.toString());
            fw.write(new String(xml.toString().getBytes("UTF-8")));
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

    public static void moverArchivoXml(String pathOrigen, String pathDestino) {
        try {
            Path origenPath = FileSystems.getDefault().getPath(pathOrigen);
            Path destinoPath = FileSystems.getDefault().getPath(pathDestino);
            
            File file = destinoPath.toFile();
            //crear toda la ruta si no existe
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                //file.mkdir();
            }
            
            
            Files.move(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(ComprobantesElectronicosUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void copiarArchivoXml(String pathOrigen, String pathDestino) {
        try {
            Path origenPath = FileSystems.getDefault().getPath(pathOrigen);
            Path destinoPath = FileSystems.getDefault().getPath(pathDestino);

            File file = destinoPath.toFile();
            //crear toda la ruta si no existe
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                //file.mkdir();
            }

            Files.copy(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(ComprobantesElectronicosUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    public static void generarArchivoXml(String xml, String path) {
        try {
            File file = new File(path);
            //crear toda la ruta si no existe
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                //file.mkdir();
            }

            FileUtils.writeStringToFile(file, xml, "UTF-8");
            /*
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
             */
        } catch (IOException ex) {
            Logger.getLogger(ComprobantesElectronicosUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String castDocumentToString(Document doc)
    {
        try {
            DocumentBuilderFactory domFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = domFact.newDocumentBuilder();
            
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ComprobantesElectronicosUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(ComprobantesElectronicosUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(ComprobantesElectronicosUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void generarArchivoXml(Document documento, String path)
            throws Exception {
        /**
         * @Fecha 16/04/2019
         * Cambiado forma de grabar porque generaba problemas con caracteres especiales , tildes eñes comas
         * Todo: Ver si eliminar la clase de UtilidadTratarNodo porque parece que ya no utilizo
         */
        String xml=castDocumentToString(documento);
        generarArchivoXml(xml, path);
        
        /*File file = new File(path);
        //crear toda la ruta si no existe
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            //file.mkdir();
        }

        
        FileOutputStream flujoSalida = null;
        try {
            try {
                flujoSalida = new FileOutputStream(path);
                UtilidadTratarNodo.saveDocumentToOutputStream(documento,flujoSalida, true);
            } finally {
                flujoSalida.close();
            }
        } catch (FileNotFoundException e) {
            throw new Exception("Error al salvar el documento");
        }*/
    }

    public static boolean eliminarArchivo(String rutaArchivo) {
        File archivo = new File(rutaArchivo);
        archivo.delete();
        System.out.println(archivo.exists());
        System.out.println(archivo.canWrite());
        System.out.println(archivo.isDirectory());
         
        if (archivo.exists() && archivo.canWrite() && !archivo.isDirectory()) {
            archivo.delete();
            return true;
        }

        return false;
    }

    public static String getPathXml(String path, String carpeta) {
        return path + "/" + carpeta;
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

    public static String dateToString(Date fecha) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(fecha);
    }

    public static String formatSimpleDate(String fechaStr) {
        fechaStr = fechaStr.replace("/", "");
        return fechaStr;
    }

    public static String dateToString(Timestamp fecha) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(fecha);
    }

    public static File[] getComprobantesByFolder(String pathBase, String carpetaConfiguracion) {
        String pathDirectorio = pathBase + "/" + carpetaConfiguracion;
        File f = new File(pathDirectorio);
        //List<File> autorizaciones=new ArrayList<String>();
        if (f.exists()) { // Directorio existe 
            File[] ficheros = f.listFiles();
            /*
            for (File fichero : ficheros) {
                autorizaciones.add(fichero.getName());
            }*/
            return ficheros;
        }
        return null;
    }
    
    public static List<ComprobanteElectronico> getComprobantesObjectByFolder(String pathBase, String carpetaConfiguracion) {
        List<ComprobanteElectronico> comprobantes=new ArrayList<ComprobanteElectronico>();
        
        String pathDirectorio = pathBase +"/" + carpetaConfiguracion;
        File f = new File(pathDirectorio);
        //List<File> autorizaciones=new ArrayList<String>();
        if (f.exists()) { // Directorio existe 
            File[] ficheros = f.listFiles();
            for (File fichero : ficheros) {
                ComprobanteElectronico comprobante = getComprobanteElectronico(carpetaConfiguracion, fichero);
                if(comprobante!=null)
                    comprobantes.add(comprobante);
            }
            /*
            for (File fichero : ficheros) {
                autorizaciones.add(fichero.getName());
            }*/
            //return comprobantes;
        }
        return comprobantes;
    }
    
    public static ComprobanteElectronico getComprobanteElectronico(String carpetaConfiguracion,File archivo)
    {
        try {
            String nombreRide = archivo.getName();
            if(carpetaConfiguracion.equals(ComprobanteElectronicoService.CARPETA_RIDE))
            {
                nombreRide = nombreRide.split("_")[1]; //obtener solo el nombre de la firma
                nombreRide = nombreRide.replace(".pdf", ".xml");
            }
           
            ClaveAcceso claveAcceso=new ClaveAcceso(nombreRide.replace(".xml",""));
            JAXBContext jaxbContext = JAXBContext.newInstance(claveAcceso.getClassTipoComprobante());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            
            if(carpetaConfiguracion.equals(ComprobanteElectronicoService.CARPETA_AUTORIZADOS))
            {
                Map<String, String> map = UtilidadesComprobantes.decodificarArchivoAutorizado(archivo.getPath());
                StringReader reader = new StringReader(map.get("comprobante"));
                ComprobanteElectronico comprobante = (ComprobanteElectronico) jaxbUnmarshaller.unmarshal(reader);
                return comprobante;
            }
            else
            {
                if(carpetaConfiguracion.equals(ComprobanteElectronicoService.CARPETA_RIDE))
                {                    
                    String nombreXmlAutorizado=archivo.getParentFile().getParent()+"\\"+ComprobanteElectronicoService.CARPETA_AUTORIZADOS+"\\"+nombreRide;
                    Map<String,String> map=UtilidadesComprobantes.decodificarArchivoAutorizado(nombreXmlAutorizado);
                    if(map==null)
                    {
                        System.out.println("No existe xml autorizado para el ride:"+archivo.getName());
                        System.out.println("Xml faltante:"+nombreXmlAutorizado);
                        return null;
                    }
                    else
                    {
                        StringReader reader = new StringReader(map.get("comprobante"));
                        ComprobanteElectronico comprobante = (ComprobanteElectronico) jaxbUnmarshaller.unmarshal(reader);
                        return comprobante;
                    }
                    
                    
                }
                else
                {
                    ComprobanteElectronico comprobante = (ComprobanteElectronico) jaxbUnmarshaller.unmarshal(archivo);
                    return comprobante;
                }
            }
            
        } catch (JAXBException ex) {
            Logger.getLogger(ComprobantesElectronicosUtil.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("=============> Problema con el archivo:"+archivo.getAbsolutePath());
        } catch (Exception ex) {
            Logger.getLogger(ComprobantesElectronicosUtil.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("=============> Problema con el archivo:"+archivo.getAbsolutePath());
        }
        return null;
    }

    public static ComprobanteElectronico obtenerComprobante(String path) {
        try {
            
            JAXBContext jaxbContext = JAXBContext.newInstance(FacturaComprobante.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            Map<String, String> mapComprobante = UtilidadesComprobantes.decodificarArchivoBase64Offline(path, null, null);
            StringReader reader = new StringReader(mapComprobante.get("comprobante"));
            ComprobanteElectronico comprobante = (ComprobanteElectronico) jaxbUnmarshaller.unmarshal(reader);
            return comprobante;
        } catch (JAXBException ex) {
            Logger.getLogger(ComprobantesElectronicosUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ComprobantesElectronicosUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
