/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.util;

import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @author home
 */
public abstract class ArchivoConfiguracion {
    private PropertiesConfiguration archivoPropiedades;
    
    public abstract String getNombreArchivo();
    //public abstract 

    public ArchivoConfiguracion() {
        cargarConfiguracionesIniciales();
    }

    
    public void cargarConfiguracionesIniciales()
    {
    
        try {
            //Verifica que existe el archivo o finalmente me da creando para evitar errores la primera vez
            UtilidadesArchivos.verificarExiteArchivoOCrear(getNombreArchivo());

            archivoPropiedades = new PropertiesConfiguration(getNombreArchivo());
            //propiedadesIniciales.load(new FileReader(NOMBRE_ARCHIVO_CONFIGURACION));
            //propiedadesIniciales.load(new FileReader(NOMBRE_ARCHIVO_CONFIGURACION));

        } catch (ConfigurationException ex) {
            Logger.getLogger(ArchivoConfiguracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void guardar() throws IOException {
        try 
        {
            archivoPropiedades.save();
            //propiedadesIniciales.store(new FileWriter(ArchivoConfiguracionesCodefac.NOMBRE_ARCHIVO_CONFIGURACION), "");
        } catch (ConfigurationException ex) {
            Logger.getLogger(ArchivoConfiguracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void agregarCampoEnum(CampoIf campoIf,String valor)
    {
        agregarCampo(campoIf.getNombreCampo(), valor);
    }
    
    public void agregarCampo(String nombreCampo, String valor) {
        archivoPropiedades.setProperty(nombreCampo, valor);

    }
    
    public String obtenerValorEnum(CampoIf nombreCampoIf)
    {
        return obtenerValor(nombreCampoIf.getNombreCampo());
    }
    
    private String obtenerValor(String nombreCampo)
    {
        return (archivoPropiedades.getString(nombreCampo)!=null)?archivoPropiedades.getString(nombreCampo).toString():null;
        
    }
    
    public HashMap<String, String> obtenerTodosLosValores()  {

        // Crea una instancia de PropertiesConfiguration y carga el archivo de propiedades
        //PropertiesConfiguration config = new PropertiesConfiguration(fileName);
        //config.load();

        // Crea un HashMap para almacenar las claves y valores de las propiedades
        HashMap<String, String> map = new HashMap<>();

        // Itera sobre todas las claves y agrega las claves y valores al HashMap
        Iterator<String> keys = archivoPropiedades.getKeys();
        while (keys.hasNext()) {
            String key = keys.next();
            String value = archivoPropiedades.getString(key);
            map.put(key, value);
        }

        // Devuelve el HashMap con las claves y valores de las propiedades
        return map;
    }
    
    public void grabarConfiguracion(String propiedad, String valor) {
        try {
            //PropertiesConfiguration propiedadesIniciales = ArchivoConfiguracionesCodefac.getInstance().getPropiedadesIniciales();
            archivoPropiedades.setProperty(propiedad, valor);
            guardar();
        } catch (IOException ex) {
            Logger.getLogger(ArchivoConfiguracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public interface CampoIf
    {
        public String getNombreCampo();
    }
    
    //public static ArchivoConfiguracion GetInstance()
    //{
        
    //}

}
