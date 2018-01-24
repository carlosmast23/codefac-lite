/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model.disenador;

import ec.com.codesoft.codefaclite.servidor.entity.ComponenteComprobanteFisico;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Carlos
 */
public class DrawComponente implements DrawInterface{
    
    public ComponenteComprobanteFisico componenteEntity;
    public static final int TAMANIO_LETRA=15;
    /*
    public int x;
    public int y;
    public int ancho;
    public int alto;
    public String nombre;
    public boolean negrita;*/

    public DrawComponente(ComponenteComprobanteFisico componenteEntity) {
        this.componenteEntity=componenteEntity;
    }
    
    
    
    @Override
    public void dibujar(Graphics g, Point desplazamiento, DrawDocumento documento) {
        g.setFont(new Font("Arial", Font.PLAIN,12));
        g.setColor(Color.BLACK);
        g.drawString(componenteEntity.getNombre(), componenteEntity.getX()+desplazamiento.x, componenteEntity.getY()+desplazamiento.y+TAMANIO_LETRA);
        
        g.setColor(Color.green);
        g.drawRect(componenteEntity.getX()+desplazamiento.x, componenteEntity.getY()+desplazamiento.y,componenteEntity.getAncho(),componenteEntity.getAlto());
        
    }
    
}
