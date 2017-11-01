/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.main.panel.OtraVentana;
import ec.com.codesoft.codefaclite.main.panel.OtraVentana2;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class OtraVentanaModel2 extends OtraVentana2{

    @Override
    public void grabar() throws UnsupportedOperationException {
        JOptionPane.showMessageDialog(null,"grabar 2...");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() {
        JOptionPane.showMessageDialog(null,"editar 2...");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() {
        JOptionPane.showMessageDialog(null,"eliminar 2...");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos=new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_GRABAR,false);
        permisos.put(GeneralPanelInterface.BOTON_EDITAR,true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR,true);
        return permisos;
    }
    
}
