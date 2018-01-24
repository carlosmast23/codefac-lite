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
    
    /**
     * Variable para saber si el componente no esta seleccionado
     */
    private boolean seleccionado;

    public DrawComponente(ComponenteComprobanteFisico componenteEntity) {
        this.componenteEntity=componenteEntity;
        this.seleccionado=false;
    }
    
    
    
    @Override
    public void dibujar(Graphics g, Point desplazamiento, DrawDocumento documento) {
        
        //Si el componente esta oculto entonces no se muestra en pantalla
        if(componenteEntity.getOculto().equals("s"))
        {
            return;
        }
        
        g.setFont(new Font("Arial", Font.PLAIN,12));
        
        //Si el componente esta selccionado cambio el color de fondo
        if(seleccionado)
        {
            g.setColor(new Color(255, 255, 0, 100));
            g.fillRect(componenteEntity.getX()+desplazamiento.x, componenteEntity.getY()+desplazamiento.y,componenteEntity.getAncho(),componenteEntity.getAlto());
        }
        
        int tipoLetra=Font.PLAIN;
        if(componenteEntity.getNegrita().equals("s"))
        {
            tipoLetra=Font.BOLD;
        }
            
            
        Font font = new Font("Arial",tipoLetra,componenteEntity.getTamanioLetra());
        g.setFont(font);
        
        int desfazY=componenteEntity.getAlto()-componenteEntity.getTamanioLetra();
        desfazY=(int)((double)desfazY/(double)2);
        
        g.setColor(Color.BLACK);
        g.drawString(componenteEntity.getNombre(), componenteEntity.getX()+desplazamiento.x+5, componenteEntity.getY()+desplazamiento.y+componenteEntity.getTamanioLetra()+desfazY);
        
        g.setColor(Color.green);
        g.drawRect(componenteEntity.getX()+desplazamiento.x, componenteEntity.getY()+desplazamiento.y,componenteEntity.getAncho(),componenteEntity.getAlto());
        
        
        
    }

    public ComponenteComprobanteFisico getComponenteEntity() {
        return componenteEntity;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }
    
    
    
}
