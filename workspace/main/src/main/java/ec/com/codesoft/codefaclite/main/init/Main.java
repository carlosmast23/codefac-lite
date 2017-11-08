/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.init;


import ec.com.codesoft.codefaclite.main.model.GeneralPanelModel;
import ec.com.codesoft.codefaclite.main.panel.LoginForm;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesServidor;
import static java.awt.Frame.MAXIMIZED_BOTH;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class Main {
    public static void main(String[] args) {
        componentesIniciales();        
        GeneralPanelModel panel=new GeneralPanelModel();
        panel.setIconImage(new javax.swing.ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourcePath("logoCodefac-ico.png")).getImage()); // NOI18N
        
        panel.setVisible(true);
        panel.setExtendedState(MAXIMIZED_BOTH);
        String dato="Identificacion del Exterior";
        System.out.println("----------------->"+dato.length());
        //JOptionPane.showMessageDialog(null,"jemplo ");
        /*
        UsuarioModeloBusqueda modelo=new UsuarioModeloBusqueda();
        
        BuscarDialogoModel dialogoForm=new BuscarDialogoModel(modelo);
        dialogoForm.setVisible(true);
        Persona persona =(Persona) dialogoForm.getResultado();
        
        System.out.println(persona.getCedula()+"-"+persona.getNombre());
        */
        
    }
    
    public static void componentesIniciales()
    {
        try {
            AbstractFacade.cargarEntityManager();
        } catch (Exception e) {
            //e.p
            UtilidadesServidor.crearBaseDatos();
            JOptionPane.showMessageDialog(null,"Creada base de datos");
            AbstractFacade.cargarEntityManager();
            
        }
        
    }
}
