/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.main.panel.VentasDiarias;
import javax.swing.JDesktopPane;

/**
 *
 * @author PC
 */
public class VentasDiariasModel extends VentasDiarias
{
    
    public VentasDiariasModel(JDesktopPane parentPanel) {
        super(parentPanel);
        agregarListenerBotones();
        agregarListenerMovimiento();
    }

    private void agregarListenerBotones() {
        
    }

    
    
}
