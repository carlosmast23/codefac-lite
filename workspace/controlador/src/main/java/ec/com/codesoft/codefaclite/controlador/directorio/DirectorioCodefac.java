/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.directorio;

import ec.com.codesoft.codefaclite.controlador.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.mail.Session;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Carlos
 */
public enum DirectorioCodefac {
    IMAGENES("imagenes"),
    LICENCIA("licencia"),
    COMPROBANTES_PRUEBAS("comprobantes/pruebas"),
    COMPROBANTES_PRODUCCION("comprobantes/produccion"),
    FIRMA("firma"),
    TEMP("temporal");
    
    private String nombre;
    /**
     * Path de la raiz de los directorio a usar en codefac
     */
    //public static String path_raiz;
    
    
    private DirectorioCodefac(String nombre) {
        this.nombre = nombre;
    }
    
    
    public String getDirectorio(SessionCodefacInterface session)
    {
        String path_raiz=session.getParametrosCodefac().get(ParametroCodefac.DIRECTORIO_RECURSOS).getValor();
        return path_raiz+"/"+nombre;
    }
    
    public String getArchivo(SessionCodefacInterface session,String archivo)
    {
        String path_raiz=session.getParametrosCodefac().get(ParametroCodefac.DIRECTORIO_RECURSOS).getValor();
        return path_raiz+"/"+nombre+"/"+archivo;
    }
    
    public BufferedImage getArchivoStream(SessionCodefacInterface session,String archivo)
    {
        InputStream input=null;
        try {
            String path_raiz=session.getParametrosCodefac().get(ParametroCodefac.DIRECTORIO_RECURSOS).getValor();
            File file=new File(path_raiz+"/"+nombre+"/"+archivo);
            input = new FileInputStream(file);
            BufferedImage image = ImageIO.read(file ); 
            return image;
            //BufferedInputStream bufferedIn = new BufferedInputStream(input);
            //ObjectInputStream ois=new ObjectInputStream(input);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DirectorioCodefac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DirectorioCodefac.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                input.close();
            } catch (IOException ex) {
                Logger.getLogger(DirectorioCodefac.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    
    
}
