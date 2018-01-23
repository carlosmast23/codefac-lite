/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model.disenador;

import ec.com.codesoft.codefaclite.servidor.entity.ComprobanteFisicoDisenio;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class DrawDocumento implements DrawInterface{
    /**
     * Documento que se va 
     */
    public ComprobanteFisicoDisenio documentoEntity;
    
    private List<DrawSeccion> secciones;

    public DrawDocumento(ComprobanteFisicoDisenio documentoEntity) {
        this.documentoEntity=documentoEntity;
        this.secciones=new ArrayList<DrawSeccion>();
    }

    
    @Override
    public void dibujar(Graphics g,Point desplazamiento,DrawDocumento documento) {
        g.setColor(Color.white);
        g.fillRect(0+desplazamiento.x,0+desplazamiento.y,documentoEntity.getAncho(),documentoEntity.getAlto());
        
        Point desplazamientoTemp=new Point(0,0);
        desplazamientoTemp.x+=desplazamiento.x;
        desplazamientoTemp.y+=desplazamiento.y;
        for (DrawSeccion seccion : secciones) {
            seccion.dibujar(g, desplazamientoTemp, documento);
            desplazamientoTemp.y+=seccion.seccionEntity.getAlto();
        }
    }
    
    public void agregarSeccion(DrawSeccion seccion)
    {
        this.secciones.add(seccion);
    }
   
    
}
