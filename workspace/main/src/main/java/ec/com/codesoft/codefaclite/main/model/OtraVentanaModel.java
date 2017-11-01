/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.main.panel.Formulario2Ejemplo;
import ec.com.codesoft.codefaclite.main.panel.FormularioEjemploForm;
import ec.com.codesoft.codefaclite.main.panel.NewJFormDesignerForm;
import ec.com.codesoft.codefaclite.main.panel.NewJFormDesignerForm1;
import ec.com.codesoft.codefaclite.main.panel.NewJFormDesignerForm2;
import ec.com.codesoft.codefaclite.main.panel.OtraVentana;
import ec.com.codesoft.codefaclite.main.test.UsuarioModeloBusqueda;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class OtraVentanaModel extends Formulario2Ejemplo{

    @Override
    public void grabar() throws UnsupportedOperationException {
        //JOptionPane.showMessageDialog(null,"grabar ...");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        UsuarioModeloBusqueda modelo=new UsuarioModeloBusqueda();
        
        BuscarDialogoModel dialogoForm=new BuscarDialogoModel(modelo);
        dialogoForm.setVisible(true);
        Persona persona =(Persona) dialogoForm.getResultado();
        
        System.out.println(persona.getCedula()+"-"+persona.getNombre());
    }

    @Override
    public void editar() {
        JOptionPane.showMessageDialog(null,"editar ...");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() {
        JOptionPane.showMessageDialog(null,"eliminar ...");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos=new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_GRABAR,true);
        permisos.put(GeneralPanelInterface.BOTON_EDITAR,true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR,true);
        return permisos;
    }
    
}
