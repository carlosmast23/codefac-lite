/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.license;

import ec.com.codesoft.codefaclite.main.session.SessionCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.mail.Session;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Carlos
 */
public class ValidacionLicenciaCodefac {
    private static final String NOMBRE_LICENCIA="licence.codefac";
    
    private Licencia licencia;
    private SessionCodefac sesion;
    private String path;

    public void validar() {
        if(verificarExisteLicencia())
        {
            Properties p = obtenerLicencia();
            licencia=new Licencia(p);
            
            if(licencia.validarLicencia())
            {
                System.out.println("Licencia correcta");
            }
            else
            {
                System.out.println("Licencia incorrecta");
            }
            
        }
        else
        {

        }
            
            
        if (verificarConexionInternet()) {
            System.out.println("si existe comunicacion con el servidor");
        } else {
            System.out.println("no existe comunicacion con el servidor");
            return;
        }

    }

    /**
     * Verificar si existe el usuario propietario en codesoft
     *
     * @return
     */
    public boolean verificarExisteUsuario() {
        //sesion.getParametrosCodefac().get(ParametroCodefac.CORREO_USUARIO)
        return true;
    }

    public boolean verificarExisteLicencia() {
        File af = new File(path+NOMBRE_LICENCIA);
        if(af.exists())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    private Properties obtenerLicencia()
    {
        try {
            Properties p = new Properties();
            p.load(new FileReader(path+NOMBRE_LICENCIA));
            return p;
        } catch (IOException ex) {
            Logger.getLogger(ValidacionLicenciaCodefac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void aprobacionUsuario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean verificarConexionInternet() {
        try {

            URL ruta = new URL("http://www.google.es");
            URLConnection rutaC = ruta.openConnection();
            rutaC.connect();
            return true;
        } catch (Exception e) {

            return false;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    
}
