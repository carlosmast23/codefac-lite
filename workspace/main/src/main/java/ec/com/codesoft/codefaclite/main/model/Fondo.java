/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import static ec.com.codesoft.codefaclite.main.model.Fondo2.toBufferedImage;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

/**
 *
 * @author Carlos
 */
public class Fondo implements Border
{
    private BufferedImage image;

    /**
     * Datos de contacto de soporte a dibujar debajo del logo (ya no van quemados en la imagen de fondo,
     * ver DatosContactoSoporte). Si vienen null no se dibuja nada, para no romper otros usos de esta clase
     * (ej. fondo personalizado de clientes PRO en GeneralPanelModel.establecerImagenFondo).
     */
    private String telefonoSoporte;
    private String correoSoporte;
    private String webSoporte;

    public Fondo(BufferedImage image) {
        this.image = image;
    }

    public Fondo(Image image) {
        this.image = toBufferedImage(image);
    }

    public Fondo(Image image,String correoSoporte,String telefonoSoporte,String webSoporte) {
        this.image = toBufferedImage(image);
        this.correoSoporte = correoSoporte;
        this.telefonoSoporte = telefonoSoporte;
        this.webSoporte = webSoporte;
    }
    /*
    public Fondo(Image image) {
        this.image = toBufferedImage(image);
    }*/
    
    /**
 * Converts a given Image into a BufferedImage
 *
 * @param img The Image to be converted
 * @return The converted BufferedImage
 */
public static BufferedImage toBufferedImage(Image img)
{
    if (img instanceof BufferedImage)
    {
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
    

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        //System.out.println(width+" - "+height);
        /*BufferedImage imgResize=toBufferedImage(new ImageIcon(image.getScaledInstance((int)(height*1.6666666d),height,Image.SCALE_SMOOTH)).getImage());
        int x0=x+(width-imgResize.getWidth())/2;
        int y0=x+(height-imgResize.getHeight())/2;
        g.drawImage(imgResize,x0,y0,null);*/
        BufferedImage imgResize=toBufferedImage(new ImageIcon(image.getScaledInstance(width, height,0)).getImage());
        //int x0=x+(width-imgResize.getWidth())/2;
        //int y0=x+(height-imgResize.getHeight())/2;
        g.drawImage(imgResize,x,y,null);

        dibujarDatosContacto(g,x,y,width,height);
    }

    /**
     * Dibuja telefono/correo/web de soporte centrados cerca de la parte inferior del fondo, en vez de llevarlos
     * quemados en la imagen (ver DatosContactoSoporte para de donde salen estos valores).
     */
    private void dibujarDatosContacto(Graphics g,int x,int y,int width,int height)
    {
        if(correoSoporte==null && telefonoSoporte==null && webSoporte==null)
        {
            return;
        }

        StringBuilder texto=new StringBuilder();
        if(correoSoporte!=null && !correoSoporte.isEmpty())
        {
            texto.append(correoSoporte);
        }
        if(telefonoSoporte!=null && !telefonoSoporte.isEmpty())
        {
            if(texto.length()>0) texto.append("   |   ");
            texto.append(telefonoSoporte);
        }
        if(webSoporte!=null && !webSoporte.isEmpty())
        {
            if(texto.length()>0) texto.append("   |   ");
            texto.append(webSoporte);
        }

        if(texto.length()==0)
        {
            return;
        }

        Graphics2D g2=(Graphics2D) g.create();
        try
        {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int tamanioFuente=Math.max(14, Math.min(22, (int)(height*0.026)));
            g2.setFont(new Font("Arial", Font.PLAIN, tamanioFuente));
            g2.setColor(new Color(60, 80, 110));

            String linea=texto.toString();
            java.awt.FontMetrics fm=g2.getFontMetrics();
            int anchoTexto=fm.stringWidth(linea);
            int xTexto=x+(width-anchoTexto)/2;
            int yTexto=y+height-Math.max(18,(int)(height*0.05));

            //Linea divisoria delgada arriba del texto, igual que tenia el footer original quemado en la imagen
            int anchoLinea=(int)(width*0.7);
            int xLinea=x+(width-anchoLinea)/2;
            int yLinea=yTexto-tamanioFuente-12;
            g2.setColor(new Color(220, 225, 232));
            g2.drawLine(xLinea, yLinea, xLinea+anchoLinea, yLinea);

            g2.setColor(new Color(60, 80, 110));
            g2.drawString(linea, xTexto, yTexto);
        }
        finally
        {
            g2.dispose();
        }
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
    
}
