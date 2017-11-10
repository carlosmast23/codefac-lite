/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.main.panel.LoginForm;
import ec.com.codesoft.codefaclite.main.panel.LoginFormDialog;
import ec.com.codesoft.codefaclite.servidor.entity.Usuario;
import ec.com.codesoft.codefaclite.servidor.service.UsuarioServicio;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class LoginModel extends LoginFormDialog{

    UsuarioServicio usuarioServicio;
    Usuario usuario;

    public LoginModel() {
        super(null,true);
        initListenerBotones();
        this.usuarioServicio=new UsuarioServicio();
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
                    usuario=usuarioServicio.login(getTxtUsuario().getText(),clave);
                    if(usuario!=null)
                    {
                        //JOptionPane.showMessageDialog(null,"Usuario correcto");
                        dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Usuario incorrecto");
                    }
                    
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Ingrese todos los campos");
                }
            }
        });
    }
    
    public Usuario getUsuarioLogin()
    {
        return usuario;
    }
    
}
