/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.ejemplo.utilidades.imagen;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadImagen {
    public static InputStream castImputStreamForReport(InputStream input) {
        
        try {
            BufferedImage image = ImageIO.read(input); 
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            return is;
            //BufferedInputStream bufferedIn = new BufferedInputStream(input);
            //ObjectInputStream ois=new ObjectInputStream(input);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UtilidadImagen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UtilidadImagen.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                input.close();
            } catch (IOException ex) {
                Logger.getLogger(UtilidadImagen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
        

    }
}
