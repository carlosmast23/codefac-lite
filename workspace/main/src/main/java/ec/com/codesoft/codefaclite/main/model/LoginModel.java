/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.main.panel.LoginFormDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidor.service.UsuarioServicio;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ServiceController;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UsuarioServicioIf;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class LoginModel extends LoginFormDialog{

    UsuarioServicioIf usuarioServicio;
    Usuario usuario;

    public LoginModel() {
        super(null,true);
        initListenerBotones();
        this.usuarioServicio=ServiceController.getController().getUsuarioServicioIf();
        
        Image fondoImg = new javax.swing.ImageIcon(getClass().getResource("/img/general/fondoInicial.jpg")).getImage();
        getPanelPrincipal().setBorder(new Fondo2(fondoImg));
    }

    private void initListenerBotones() {
        
        getBtnSalir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        getBtnIngresar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clave=new String(getTxtClave().getPassword());
                if(!getTxtUsuario().getText().equals("") && !clave.equals(""))
                {
                    try {
                        usuario=usuarioServicio.login(getTxtUsuario().getText(),clave);
                        if(usuario!=null)
                        {
                            //JOptionPane.showMessageDialog(null,"Usuario correcto");
                            dispose();
                        }
                        else
                        {
                            DialogoCodefac.mensaje("Error Login","Datos Incorrectos",DialogoCodefac.MENSAJE_INCORRECTO);
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                else
                {
                    DialogoCodefac.mensaje("Advertencia Login","Ingrese todos los campos",DialogoCodefac.MENSAJE_ADVERTENCIA);
                }
            }
        });
    }
    
    public Usuario getUsuarioLogin()
    {
        return usuario;
    }
    
}
