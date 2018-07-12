/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.info;

import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Clase que contiene los parametros generales del sistema 
 * @author Carlos
 */
public abstract class ParametrosSistemaCodefac {
    /**
     * Version actual del sistema
     */
    public static final String VERSION="1.2.5.4";
    /**
     * El modo de configuracion del sistema
     */
    public static final ModoSistemaEnum MODO=ModoSistemaEnum.DESARROLLO;

    /**
     * Puerto por defecto para configurar las conexiones en red
     */
    public static final int PUERTO_COMUNICACION_RED=1099;
    /**
     * Nombre de la base de datos
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
}
