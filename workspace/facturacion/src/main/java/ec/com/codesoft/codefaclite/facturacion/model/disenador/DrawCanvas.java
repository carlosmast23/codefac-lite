/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model.disenador;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Carlos
 */
public class DrawCanvas{

    private DrawDocumento documento;

    public DrawCanvas(DrawDocumento documento) {
        this.documento = documento;
    }

    public DrawCanvas() {
    }
    
    

    public void dibujar(Graphics g,Dimension dimension) {
        
        //Solo dibujar si tiene una referencia
        if(this.documento==null)
        {
            System.out.println("cancelado");
            return;
        }
        
        /**
         * Dibujar el fondo principal
         */
        g.setColor(new Color(245, 245, 245));    

        g.fillRect(0, 0,(int)dimension.getWidth(),(int)dimension.getHeight());        
        
        int desfazX=(int) (dimension.getWidth()-this.documento.documentoEntity.getAncho());
        desfazX=(int)((double)desfazX/(double)2);
        this.documento.dibujar(g,new Point(desfazX,50),this.documento);

    }

    public DrawDocumento getDocumento() {
        return documento;
    }

    public void setDocumento(DrawDocumento documento) {
        this.documento = documento;
    }
    
    
    
}
