/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.info;

import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Clase que contiene los parametros generales del sistema 
 * @author Carlos
 */
public abstract class ParametrosSistemaCodefac {
    /**
     * Version actual del sistema
     */
    public static final String VERSION="1.2.3";
    /**
     * El modo de configuracion del sistema
     */
    public static final ModoSistemaEnum MODO=ModoSistemaEnum.DESARROLLO;
    
    /**
     * 
     */
    public static final Image iconoSistema=new javax.swing.ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("logoCodefac-ico.png")).getImage();
    /**
     * Icono del sistema 
     */
    //public static fin  new javax.swing.ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("logoCodefac-ico.png")).getImage()
}
