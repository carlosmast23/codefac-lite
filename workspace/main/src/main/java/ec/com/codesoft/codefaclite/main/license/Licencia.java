/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.license;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.ejemplo.utilidades.varios.UtilidadVarios;
import java.io.FileWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Properties;

/**
 *
 * @author Carlos
 */
public class Licencia {
    
    public static final String PROPIEDAD_USUARIO="usuario";
    public static final String PROPIEDAD_LICENCIA="licencia";
    public static final String PROPIEDAD_TIPO_LICENCIA="tipo";
    //TODO: Verificar estas variables porque se repiten 2 veces en el modulo de verificacion licencia
    public static final String PROPIEDAD_CANTIDAD_CLIENTES="clientes";
    
    //Modulos adicionales que puede tener la licencia
    public static String MODULO_INVENTARIO="modulo_inventario";
    public static String MODULO_GESTION_ACADEMICA="modulo_gestion_academica";
    public static String MODULO_FACTURACION="modulo_facturacion";
    public static String MODULO_CRM="modulo_facturacion";

    
    private String mac;
    private Properties propiedades;
    private TipoLicenciaEnum tipoLicenciaEnum;

    public Licencia(Properties propiedades) {
        this.propiedades = propiedades;
        this.mac=obtenerMac();
        this.tipoLicenciaEnum=TipoLicenciaEnum.getEnumByNombre(propiedades.getProperty(PROPIEDAD_TIPO_LICENCIA));        
    }
    
    
    public String obtenerMac()
    {
        //UtilidadVarios.obtenerMac();
        return UtilidadVarios.obtenerMacSinInternet();
    }
    
    public boolean validarLicencia()
    {
        String usuario=propiedades.getProperty(PROPIEDAD_USUARIO);
        String licencia=propiedades.getProperty(PROPIEDAD_LICENCIA);        
        String tipoLicencia=propiedades.getProperty(PROPIEDAD_TIPO_LICENCIA);
        String cantidadUsuarios=propiedades.getProperty(PROPIEDAD_CANTIDAD_CLIENTES);
        String modulosStr=getModulosStr(); //Obtiene los valores de los modulos activos con snsnsnssn para validar la licencia
               
        try
        {
            if(tipoLicencia.equals(TipoLicenciaEnum.GRATIS.getNombre()))
            {

                //Validacion cuando el usuario si esta registrado con licencia gratis 
                this.tipoLicenciaEnum=TipoLicenciaEnum.GRATIS;
                return BCrypt.checkpw(usuario + ":" + mac + ":" + TipoLicenciaEnum.GRATIS.getLetra()+":"+cantidadUsuarios+":"+modulosStr, licencia);
                                
            }
            else
            {
                if(tipoLicencia.equals(TipoLicenciaEnum.PRO.getNombre()))
                {
                    this.tipoLicenciaEnum=TipoLicenciaEnum.PRO;
                    return BCrypt.checkpw(usuario+":"+mac+":"+TipoLicenciaEnum.PRO.getLetra()+":"+cantidadUsuarios+":"+modulosStr,licencia);
                }
            }
            
            //Validacion por defecto cuando no existe tipo licencia
            return false;
        }
        catch(java.lang.IllegalArgumentException iae)
        {
            return false;
        }

    }
    
    /**
     * Devuelve una cadena construida con los modulos para verificar la licencia
     * @return 
     */
    private String getModulosStr()
    {
        String modulosStr="";
        
        modulosStr+=getRespuestaModulo(propiedades.getProperty(MODULO_CRM));
        modulosStr+=getRespuestaModulo(propiedades.getProperty(MODULO_FACTURACION));
        modulosStr+=getRespuestaModulo(propiedades.getProperty(MODULO_GESTION_ACADEMICA));
        modulosStr+=getRespuestaModulo(propiedades.getProperty(MODULO_INVENTARIO));
        return modulosStr;
    }
    
    private String getRespuestaModulo(String modulo)
    {
        String valorPropiedad= propiedades.getProperty(modulo);
        
        String respuesta="n";
        if(valorPropiedad!=null && valorPropiedad.equals("si"))
        {
            respuesta="s";
        }
        return respuesta;
    }

    public TipoLicenciaEnum getTipoLicenciaEnum() {
        //String usuario=propiedades.getProperty(PROPIEDAD_USUARIO);
        //String licencia=propiedades.getProperty(PROPIEDAD_LICENCIA);
        String tipoLicencia=propiedades.getProperty(PROPIEDAD_TIPO_LICENCIA);
        
        return TipoLicenciaEnum.getEnumByNombre(tipoLicencia);
        
    }

    public Properties getPropiedades() {
        return propiedades;
    }
    
    

    
}
