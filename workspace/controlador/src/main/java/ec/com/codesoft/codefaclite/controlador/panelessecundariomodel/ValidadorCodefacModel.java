/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.panelessecundariomodel;

import ec.com.codesoft.codefaclite.controlador.panel.ValidadorCodefacPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class ValidadorCodefacModel extends ValidadorCodefacPanel{

    @Override
    public String getNombrePanel() {
        return ValidadorCodefacModel.PANEL_VALIDACION;
    }

    @Override
    public void actualizar(Object obj) {
        DefaultTableModel model=(DefaultTableModel) obj;
        getTblDatosValidar().setModel(model);
        getTblDatosValidar().repaint();
    }
    
}
