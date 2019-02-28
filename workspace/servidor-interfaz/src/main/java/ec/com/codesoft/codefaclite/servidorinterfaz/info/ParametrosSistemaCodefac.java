/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.info;

import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import java.awt.Image;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.ImageIcon;

/**
 * Clase que contiene los parametros generales del sistema 
 * @author Carlos
 */
public abstract class ParametrosSistemaCodefac {
    /**
     * Version actual del sistema
     */
    public static final String VERSION="1.2.7.8.1";
    /**
     * El modo de configuracion del sistema
     */
    public static final ModoSistemaEnum MODO=ModoSistemaEnum.PRODUCCION;

    /**
     * Puerto por defecto para configurar las conexiones en red no es final porque puedo modificar al momento de leer el archivo de configuraciones
     */
    public static  int PUERTO_COMUNICACION_RED=1099;
    /**
     * Nombre de la base de datosv1
     */
    public static final String NOMBRE_BASE_DATOS="Derby2.DB";
    
    /**
     * Icono por defecto para usar en todo el sistema
     */
    public static final Image iconoSistema=new javax.swing.ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("logoCodefac-ico.png")).getImage();

    /**
     * Cadena que contiene la clave para encriptar o desencriptar los datos en codefac
     */
    public static final String LLAVE_ENCRIPTAR="codesoft23codefac01"; //Todo: Analizar para obtener esta clave de un ddl en c++ para que no puedan ver con ingeniera inversa, Advertencia: no cambiar la clave porque datos encriptados con este clave ya no se podrian desencriptar
    
    /**
     * Pagina que se carga por defecto cuando carga las ayudas
     */
    public static final String PAGINA_DEFECTO_AYUDA="http://www.cf.codesoft-ec.com/ayuda";
    
    /**
     * Puerto por el que van a aceptar conexiones para el app movil de Codefac
     */
    public static  Integer PUERTO_APP_MOVIL_SMS=9999;
    
    public static final String CODIGO_TELEFONO_ECUADOR="+593";
    
    public static final Integer LIMITE_CARACTERES_SMS=160;
    
    /**
     * 
     */
    public static final String CARPETA_DATOS_TEMPORALES="tmp";
    
    
    public static final String LINK_CONSULTAS_SRI="https://declaraciones.sri.gob.ec/tuportal-internet/accederAplicacion.jspa?redireccion=60&idGrupo=58";
     
    public static final String LINK_ANULACIONES_SRI="https://declaraciones.sri.gob.ec/tuportal-internet/accederAplicacion.jspa?redireccion=61&idGrupo=58";
    
    /**
     * Variable que sirve para establecer el redondeo por defecto de todo el sistam
     */
    public static final RoundingMode REDONDEO_POR_DEFECTO=RoundingMode.HALF_UP;
    public abstract class MensajesSistemaCodefac
    {
        public static final String MENSAJE_PIE_PAGINA_GRATIS="Reporte generado con Codefac versión gratuita, descargada en www.cf.codesoft-ec.com";
    }
}
