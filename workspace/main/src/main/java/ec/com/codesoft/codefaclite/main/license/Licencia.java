/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.license;

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
    
    
    private String obtenerMac()
    {
        NetworkInterface a;
        String linea;
        try {
            a = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            byte[] mac = a.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            System.out.println(sb.toString());
            return sb.toString();

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return "";
    }
    
    public boolean validarLicencia()
    {
        return BCrypt.checkpw(propiedades.getProperty(PROPIEDAD_USUARIO)+":"+mac,propiedades.getProperty(PROPIEDAD_LICENCIA));
    }
    
    
}
