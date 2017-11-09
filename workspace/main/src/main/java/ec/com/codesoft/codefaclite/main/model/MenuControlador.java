/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

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
   private GeneralPanelInterface ventana;

    public MenuControlador(JMenuItem menuItem, GeneralPanelInterface ventana) {
        this.menuItem = menuItem;
        this.ventana = ventana;
    }

    public JMenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(JMenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public GeneralPanelInterface getVentana() {
        return ventana;
    }

    public void setVentana(GeneralPanelInterface ventana) {
        this.ventana = ventana;
    }
   
   
}
