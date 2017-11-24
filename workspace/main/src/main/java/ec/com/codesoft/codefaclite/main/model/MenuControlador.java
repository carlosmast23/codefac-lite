/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Carlos
 */
public class MenuControlador 
{
   private JMenuItem menuItem;
   private Class ventana;
   private boolean maximizado;

    public MenuControlador(JMenuItem menuItem, Class ventana) {
        this.menuItem = menuItem;
        this.ventana = ventana;
        this.maximizado=true;
    }

    public MenuControlador(JMenuItem menuItem, Class ventana, boolean maximizado) {
        this.menuItem = menuItem;
        this.ventana = ventana;
        this.maximizado = maximizado;
    }
    
    

    public JMenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(JMenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public Class getVentana() {
        return ventana;
    }

    public void setVentana(Class ventana) {
        this.ventana = ventana;
    }

    public boolean isMaximizado() {
        return maximizado;
    }

    public void setMaximizado(boolean maximizado) {
        this.maximizado = maximizado;
    }

    
   
   
}
