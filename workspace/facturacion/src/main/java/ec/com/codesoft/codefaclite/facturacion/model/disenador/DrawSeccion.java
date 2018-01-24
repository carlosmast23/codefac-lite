/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model.disenador;

import ec.com.codesoft.codefaclite.servidor.entity.BandaComprobante;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Rectangle2D;

/**
 *
 * @author Carlos
 */
public class DrawSeccion implements DrawInterface{
    
    public BandaComprobante seccionEntity;
    
    private List<DrawComponente> componentes;

    public DrawSeccion(BandaComprobante seccionEntity) {
        this.seccionEntity=seccionEntity;
        this.componentes=new ArrayList<DrawComponente>();
    }

    @Override
    public void dibujar(Graphics g, Point desplazamiento,DrawDocumento documento) {
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(desplazamiento.x,desplazamiento.y,documento.documentoEntity.getAncho(),seccionEntity.getAlto());
        //g.drawString(nombre, (desplazamiento.x+documento.ancho)*3/4, desplazamiento.y+alto+50);
        Rectangle rectangle=new Rectangle(desplazamiento.x,desplazamiento.y,documento.documentoEntity.getAncho(),seccionEntity.getAlto());
        g.setColor(new Color(220,220,220));
        drawCenteredString(g, seccionEntity.getTitulo(),rectangle,new Font("Arial", Font.PLAIN,30));
        
        /**
         * Dibujar Componentes
         */
        
        for (DrawComponente componente : componentes) {
            componente.dibujar(g,desplazamiento, documento);
        }
        
    }
    
    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    public void agregarComponente(DrawComponente componente)
    {
        this.componentes.add(componente);
    }
    
}
