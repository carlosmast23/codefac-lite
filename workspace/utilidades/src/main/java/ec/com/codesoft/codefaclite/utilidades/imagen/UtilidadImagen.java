/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.imagen;

import java.awt.Graphics2D;
import java.awt.Image;
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
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import org.imgscalr.Scalr;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadImagen {
    public static InputStream castImputStreamForReport(InputStream input) {
        
        try {
            BufferedImage image = ImageIO.read(input); 
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            return is;
            
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
    
    public static Image castInputStreamToImage(InputStream inputStream)
    {
        try {
            return ImageIO.read(inputStream);
        } catch (IOException ex) {
            Logger.getLogger(UtilidadImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static ImageIcon castInputStreamToImageIcon(InputStream inputStream)
    {
        Image imageLogo = UtilidadImagen.castInputStreamToImage(inputStream);
        ImageIcon imageIcon=new ImageIcon(imageLogo);
        return imageIcon;
    }
            
    public static ImageInputStream castBufferImputStream(InputStream input) {
        
        try {
            ImageInputStream iis=ImageIO.createImageInputStream(input);
        } catch (IOException ex) {
            Logger.getLogger(UtilidadImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Image convertirOutPutStreamToImage(ByteArrayOutputStream bos) {
       
        try {
            byte [] data = bos.toByteArray();
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            BufferedImage bImage2 = ImageIO.read(bis);
            return bImage2;
        } catch (IOException ex) {
            Logger.getLogger(UtilidadImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    
    /**
     * Metodo que me permite redimensionar una imagen
     * @param imagenOriginal
     * @param ancho
     * @param alto
     * @return
     * @throws Exception 
     */
    public static BufferedImage resizeImage(BufferedImage imagenOriginal, int ancho, int alto) throws Exception 
    {
        return Scalr.resize(imagenOriginal, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, ancho, alto, Scalr.OP_ANTIALIAS);
    }
    
    public static BufferedImage castInputStreamToBufferedImage(InputStream inputStream)
    {
        try {
            BufferedImage imBuff = ImageIO.read(inputStream);
            return imBuff;
        } catch (IOException ex) {
            Logger.getLogger(UtilidadImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static BufferedImage resizeImage(Image imagenOriginal, int ancho, int alto) 
    {
        try
        {
            BufferedImage bufferedImage=toBufferedImage(imagenOriginal);
            return Scalr.resize(bufferedImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, ancho, alto, Scalr.OP_ANTIALIAS);
        } 
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return  null;
        
    }
    
    public static BufferedImage resizeImage(InputStream inputStream, int ancho, int alto) 
    {
        try
        {
            BufferedImage bufferedImage=castInputStreamToBufferedImage(inputStream);
            return Scalr.resize(bufferedImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, ancho, alto, Scalr.OP_ANTIALIAS);
        } 
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return  null;
        
    }
    
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
    
    /**
     * Verifica si la cadena ingresada es una imagen
     * @param text
     * @return 
     */
    public static boolean verificarTextoEsImagen(String text) {
        String[] imageExtensions = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};
        for (String extension : imageExtensions) {
            if (text.toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

}
