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
               
        try
        {
            if(tipoLicencia.equals(TipoLicenciaEnum.GRATIS.getNombre()))
            {

                //Validacion cuando el usuario si esta registrado con licencia gratis 
                this.tipoLicenciaEnum=TipoLicenciaEnum.GRATIS;
                return BCrypt.checkpw(usuario + ":" + mac + ":" + TipoLicenciaEnum.GRATIS.getLetra()+":"+cantidadUsuarios, licencia);
                                
            }
            else
            {
                if(tipoLicencia.equals(TipoLicenciaEnum.PRO.getNombre()))
                {
                    this.tipoLicenciaEnum=TipoLicenciaEnum.PRO;
                    return BCrypt.checkpw(usuario+":"+mac+":"+TipoLicenciaEnum.PRO.getLetra()+":"+cantidadUsuarios,licencia);
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

    public TipoLicenciaEnum getTipoLicenciaEnum() {
        String usuario=propiedades.getProperty(PROPIEDAD_USUARIO);
        String licencia=propiedades.getProperty(PROPIEDAD_LICENCIA);
        String tipoLicencia=propiedades.getProperty(PROPIEDAD_TIPO_LICENCIA);
        
        return TipoLicenciaEnum.getEnumByNombre(tipoLicencia);
        
    }

    
}
