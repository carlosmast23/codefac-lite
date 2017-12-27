/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.license;

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
    private String mac;
    private Properties propiedades;

    public Licencia(Properties propiedades) {
        this.propiedades = propiedades;
        this.mac=obtenerMac();
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
        try
        {
            return BCrypt.checkpw(usuario+":"+mac,licencia);
        }
        catch(java.lang.IllegalArgumentException iae)
        {
            return false;
        }
    }
    
    
    
}
