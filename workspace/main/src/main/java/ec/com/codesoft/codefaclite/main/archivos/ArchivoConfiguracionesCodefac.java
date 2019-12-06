/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.archivos;

import ec.com.codesoft.codefaclite.main.init.Main;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ArchivoConfiguracionesCodefac {

    private static final Logger LOG = Logger.getLogger(ArchivoConfiguracionesCodefac.class.getName());
    
    
       /**
     * Nombre del archivo de configuraciones iniciales
     */
    private static final String NOMBRE_ARCHIVO_CONFIGURACION = "codefac.ini";

    public static final String CAMPO_MODO_APLICATIVO = "modo";
    
    /***
     * Este campo es para los clientes para que tengan grabado la ip del servidor
     */
    public static final String CAMPO_IP_ULTIMO_ACCESO_SERVIDOR="servidorip";
    
    /**
     * Este campo es para saber el ultimo tipo de acceso del cliente si fue remoto o local
     */
    public static final String CAMPO_TIPO_CLIENTE="tipo_cliente";
    
    /**
     * Este campo es paro el servidor para que inicie el aplicativo en la direccion de red ingresada
     */
    public static final String CAMPO_IP_SERVIDOR="ip";
    
    /**
     * Este campo sirve para especificar cuando un servidor acepta conexiones desde una ip publica 
     */
    public static final String CAMPO_IP_PUBLICA_SERVIDOR="ip_publica";
    
    public static final String CAMPO_VERSION="version";
    
    public static final String CAMPO_TEMA="tema";
    
    public static final String CAMPO_PUERTO_SISTEMA="puerto_sistema";
    public static final String CAMPO_PUERTO_SMS="puerto_sms";
    
    /**
     * Este campo especifica si se desea activar o no el servicio web al iniciar la aplicacion
     * Valores validos (si o no)
     */
    public static final String CAMPO_ACTIVAR_SERVICIO_WEB="activar_servicio_web";
    
    /**
     * Campo para especificar de que interfaz en particular esta generando la licencia por sis tiene varias y luego tiene problemas con la licencia
     */
    public static final String CAMPO_INTERFAZ_RED_LICENCIA="interfaz_red_licencia";
    
    private Properties propiedadesIniciales;
    
    private static ArchivoConfiguracionesCodefac instance;
    
    public static ArchivoConfiguracionesCodefac getInstance()
    {
        if(instance==null)
            instance=new ArchivoConfiguracionesCodefac();
        
        return instance;
    }
    

    public ArchivoConfiguracionesCodefac() {
    }
    
    public void agregarCampo(String nombreCampo,String valor)
    {
        propiedadesIniciales.put(nombreCampo, valor);
        
    }
    
    public String obtenerValor(String nombreCampo)
    {
        
        return (propiedadesIniciales.get(nombreCampo)!=null)?propiedadesIniciales.get(nombreCampo).toString():null;
    }
    
    public void cargarConfiguracionesIniciales()
    {
    
         try {
            propiedadesIniciales = new Properties();
            propiedadesIniciales.load(new FileReader(NOMBRE_ARCHIVO_CONFIGURACION));

        } catch (FileNotFoundException ex) {
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            LOG.log(Level.INFO,"Archivo de configuracion inicial no existe");

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void guardar() throws IOException
    {
        propiedadesIniciales.store(new FileWriter(ArchivoConfiguracionesCodefac.NOMBRE_ARCHIVO_CONFIGURACION), "");
    }

    public Properties getPropiedadesIniciales() {
        return propiedadesIniciales;
    }

    
}
