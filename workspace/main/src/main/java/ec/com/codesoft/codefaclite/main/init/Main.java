/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.init;


import ec.com.codesoft.codefaclite.main.model.GeneralPanelModel;
import ec.com.codesoft.codefaclite.main.panel.LoginForm;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import static java.awt.Frame.MAXIMIZED_BOTH;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class Main {
    public static void main(String[] args) {
        
        GeneralPanelModel panel=new GeneralPanelModel();
        panel.setVisible(true);
        panel.setExtendedState(MAXIMIZED_BOTH);
        
        //JOptionPane.showMessageDialog(null,"jemplo ");
        /*
        UsuarioModeloBusqueda modelo=new UsuarioModeloBusqueda();
        
        BuscarDialogoModel dialogoForm=new BuscarDialogoModel(modelo);
        dialogoForm.setVisible(true);
        Persona persona =(Persona) dialogoForm.getResultado();
        
        System.out.println(persona.getCedula()+"-"+persona.getNombre());
        */
        
    }
}
