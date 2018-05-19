/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.license;

import ec.com.codesoft.codefaclite.main.license.excepcion.NoExisteLicenciaException;
import ec.com.codesoft.codefaclite.main.license.excepcion.ValidacionLicenciaExcepcion;
import ec.com.codesoft.codefaclite.main.session.SessionCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadVarios;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import javax.mail.Session;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Carlos
 */
public class ValidacionLicenciaCodefac{
    
    private static final String NOMBRE_LICENCIA="/licencia/licence.codefac";
    /*public static final String USUARIO="usuario";
    public static final String LICENCIA="licencia";
    public static final String TIPO_LICENCIA="tipo";
    
    /**
     * Campo que identifica la cantidad de usuarios de clientes permitidas
     */
    //public static final String CANTIDAD_USUARIOS="clientes";
    
    private Licencia licencia;
    private String path;

    public ValidacionLicenciaCodefac(String path) {
        this.path=path;        
        Properties p = obtenerLicencia();
        licencia=new Licencia();
        licencia.cargarLicenciaFisica(p);
    }

    public ValidacionLicenciaCodefac() {
    }

    public boolean validar() throws ValidacionLicenciaExcepcion,NoExisteLicenciaException{

        if (verificarExisteLicencia()) {
            Properties p = obtenerLicencia();
            licencia = new Licencia(p);

            if (licencia.validarLicencia()) {
                return true;
            } else {
                return false;
            }

        } else {
            throw new NoExisteLicenciaException("No existe licencia");
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
    
    public Properties obtenerLicencia()
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

    public boolean verificarConexionInternet() {
        try {
            //Pagina Web de la pagina de Codefac
            URL ruta = new URL("http://www.cf.codesoft-ec.com/");
            URLConnection rutaC = ruta.openConnection();
            rutaC.connect();
            return true;
        } catch (Exception e) {

            return false;
        }
    }
    
    /**
     * Metodo que crea la licencia
     * @return 
     */
    public Properties crearLicenciaMaquina(Licencia licencia)
    {
        FileOutputStream fr=null;
        try {

            //String licencia=usuario+":"+UtilidadVarios.obtenerMac()+":"+tipoLicencia+":"+cantidadUsuarios;            
            String modulosStr=licencia.getModulosStr();
            String licenciaStr=licencia.getUsuario()+":"+UtilidadVarios.obtenerMacSinInternet()+":"+licencia.getTipoLicenciaEnum().getLetra()+":"+licencia.getCantidadClientes()+":"+modulosStr;            
            licenciaStr=BCrypt.hashpw(licenciaStr,BCrypt.gensalt(12));
            Properties prop = new Properties();
            prop.setProperty(Licencia.PROPIEDAD_USUARIO,licencia.getUsuario());
            prop.setProperty(Licencia.PROPIEDAD_LICENCIA,licenciaStr);
            prop.setProperty(Licencia.PROPIEDAD_CANTIDAD_CLIENTES,licencia.getCantidadClientes().toString());
                        
            //setearPropiedadesModulos(prop,licen); //Setea los modulos activos
            
            TipoLicenciaEnum enumTipoLicencia=licencia.getTipoLicenciaEnum();
            prop.setProperty(Licencia.PROPIEDAD_TIPO_LICENCIA,enumTipoLicencia.getNombre());
            
            //Llea en el properties los datos adicionales del modulo
            licencia.llenarPropertiesModulo(prop);
            
            File file=new File(path+NOMBRE_LICENCIA);
            
             //crear toda la ruta si no existe
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                //file.mkdir();
            }
            
            fr = new FileOutputStream(file);
            prop.store(fr,"Properties");
            fr.close();
            return prop;
            //saveProperties(prop);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ValidacionLicenciaCodefac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ValidacionLicenciaCodefac.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(ValidacionLicenciaCodefac.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    
    public Properties crearLicenciaDescargada(Licencia licencia)
    {
        FileOutputStream fr=null;
        try {
            //String licencia=usuario+":"+UtilidadVarios.obtenerMac();            
            //licencia=BCrypt.hashpw(licencia,BCrypt.gensalt(12));
            Properties prop = new Properties();
            prop.setProperty(Licencia.PROPIEDAD_USUARIO,licencia.getUsuario());
            prop.setProperty(Licencia.PROPIEDAD_LICENCIA,licencia.getLicencia());
            prop.setProperty(Licencia.PROPIEDAD_CANTIDAD_CLIENTES,licencia.getCantidadClientes().toString());            
            //setearPropiedadesModulos(prop,modulosActivos); //Setea los modulos activos
            //TipoLicenciaEnum enumTipoLicencia = TipoLicenciaEnum.getEnumByLetra(licencia.getTipoLicenciaEnum());
            prop.setProperty(Licencia.PROPIEDAD_TIPO_LICENCIA,licencia.getTipoLicenciaEnum().getNombre());
            
            licencia.llenarPropertiesModulo(prop);
            File file=new File(path+NOMBRE_LICENCIA);
            
            //crear toda la ruta si no existe
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                //file.mkdir();
            }
            
            fr = new FileOutputStream(file);
            prop.store(fr,"Properties");
            fr.close();
            return prop;
            //saveProperties(prop);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ValidacionLicenciaCodefac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ValidacionLicenciaCodefac.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(ValidacionLicenciaCodefac.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    /*
    private void setearPropiedadesModulos(Properties propiedades,List<String> modulosActivos)
    {
        String[] modulos=Licencia.MODULOS;
        for (String modulo : modulos) {
            if(modulosActivos.contains(modulo))
            {
                propiedades.setProperty(modulo,"si");
            }
            else
            {
                propiedades.setProperty(modulo,"no");
            }
        }
    }*/
   /* 
    static void saveProperties(Properties p)throws IOException
    {
            FileOutputStream fr=new FileOutputStream(file);
            p.store(fr,"Properties");
            fr.close();
            System.out.println("After saving properties:"+p);
    }*/

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Licencia getLicencia() {
        return licencia;
    }
    
    
    
}
