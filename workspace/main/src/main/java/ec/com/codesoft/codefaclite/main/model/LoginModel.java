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
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ModoSistemaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UsuarioServicioIf;
import ec.com.codesoft.codefaclite.ws.codefac.test.service.WebServiceCodefac;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class LoginModel extends LoginFormDialog{

    private static final Logger LOG = Logger.getLogger(LoginModel.class.getName());
    
    UsuarioServicioIf usuarioServicio;
    Usuario usuario;

    public LoginModel() {
        super(null,true);
        initListenerBotones();
        this.usuarioServicio=ServiceFactory.getFactory().getUsuarioServicioIf();
        
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
                ingresarSistema();
            }
        });
        
        getTxtClave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarSistema();
            }
        });
        
        getTxtUsuario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarSistema();
            }
        });
    }
    
    public Usuario getUsuarioLogin()
    {
        return usuario;
    }
    
    private void ingresarSistema()
    {
        
        String clave=new String(getTxtClave().getPassword());
        String usuarioTxt=getTxtUsuario().getText();
        if(!usuarioTxt.equals("") && !clave.equals(""))
        {
            /**
             * Validacion para verificar si no es un usuario root es decir para soporte
             */
            if(usuarioTxt.toLowerCase().indexOf("root")>=0 && ParametrosSistemaCodefac.MODO.equals(ModoSistemaEnum.PRODUCCION)) //Si contiene la palabra root asumo que es de soporte
            {
                if(WebServiceCodefac.getVerificarSoporte(usuarioTxt, clave))
                {
                    try {
                        Map<String,Object> mapParametros=new HashMap<String, Object>();
                        mapParametros.put("nick",Usuario.SUPER_USUARIO);
                        
                        Usuario usuarioRoot=ServiceFactory.getFactory().getUsuarioServicioIf().obtenerPorMap(mapParametros).get(0);//obtiene el usuario root de la base de datos 
                        usuarioRoot.isRoot=true;
                        usuario=usuarioRoot;
                        LOG.log(Level.INFO, "Ingresando con el usuario root: "+usuarioTxt);
                        dispose();
                    } catch (RemoteException ex) {
                        Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else
                {
                    LOG.log(Level.WARNING, "Error al ingresar con el usuario: " + usuarioTxt);
                    DialogoCodefac.mensaje("Error Login", "Datos Incorrectos para usuario root", DialogoCodefac.MENSAJE_INCORRECTO);
                }
                    
            }
            else //Validacion para usuarios normales que no son root
            {            
                try {
                    usuario=usuarioServicio.login(usuarioTxt,clave);
                    if(usuario!=null)
                    {
                        LOG.log(Level.INFO, "Ingresando con el usuario: "+usuarioTxt);
                        dispose();
                    }
                    else
                    {
                        LOG.log(Level.WARNING, "Error al ingresar con el usuario: "+usuarioTxt);
                        DialogoCodefac.mensaje("Error Login","Datos Incorrectos",DialogoCodefac.MENSAJE_INCORRECTO);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        else
        {
            DialogoCodefac.mensaje("Advertencia Login","Ingrese todos los campos",DialogoCodefac.MENSAJE_ADVERTENCIA);
        }
    }
    
}
