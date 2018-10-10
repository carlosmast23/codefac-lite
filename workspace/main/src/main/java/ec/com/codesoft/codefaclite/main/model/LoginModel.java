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
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.ws.codefac.test.service.WebServiceCodefac;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
    public boolean salirAplicacion=false;
    
    UsuarioServicioIf usuarioServicio;
    Usuario usuario;

    public LoginModel() {
        super(null,true);
        valoresIniciales();
        initListenerBotones();
        this.usuarioServicio=ServiceFactory.getFactory().getUsuarioServicioIf();
        
        Image fondoImg = new javax.swing.ImageIcon(getClass().getResource("/img/general/fondoInicial.jpg")).getImage();
        getPanelPrincipal().setBorder(new Fondo2(fondoImg));
        
        //Setear la versión del sistema
        getLblVersion().setText("Versión:"+ParametrosSistemaCodefac.VERSION+"   ");
        
        addComponentListener(new ComponentListener() { //Artificio para setear en los dialogs el focus
            @Override
            public void componentResized(ComponentEvent e) {}

            @Override
            public void componentMoved(ComponentEvent e) {}

            @Override
            public void componentShown(ComponentEvent e) {
                getTxtUsuario().requestFocus();
            }

            @Override
            public void componentHidden(ComponentEvent e) {}
        });
        
        
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                salirAplicacion=true;
                setVisible(false);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                //salirAplicacion=true;
                //setVisible(false);
            }

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
    }

    private void initListenerBotones() {
        
        getBtnSalir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //dispose();
                salirAplicacion=true;
                setVisible(false);
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
            if(usuarioTxt.toLowerCase().indexOf("root")>=0) //Si contiene la palabra root asumo que es de soporte
            {
                //Consultar el usuario root
                Map<String, Object> mapParametros = new HashMap<String, Object>();
                mapParametros.put("nick", Usuario.SUPER_USUARIO);
                Usuario usuarioRoot = null; //variable para consultar la variable root
                try {
                    usuarioRoot = ServiceFactory.getFactory().getUsuarioServicioIf().obtenerPorMap(mapParametros).get(0);//obtiene el usuario root de la base de datos 
                    usuarioRoot.isRoot = true;
                } catch (RemoteException ex) {
                    Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
                }

                
                if(ParametrosSistemaCodefac.MODO.equals(ModoSistemaEnum.PRODUCCION)) //Cuando esta en modo produccion para el root consulto desde los web services
                {
                    if(WebServiceCodefac.getVerificarSoporte(usuarioTxt, clave))
                    {                            
                            usuario=usuarioRoot;
                            LOG.log(Level.INFO, "Ingresando con el usuario root: "+usuarioTxt);
                            setVisible(false);
                    }
                    else
                    {
                        LOG.log(Level.WARNING, "Error al ingresar con el usuario: " + usuarioTxt);
                        DialogoCodefac.mensaje("Error Login", "Datos Incorrectos para usuario root", DialogoCodefac.MENSAJE_INCORRECTO);
                    }
                }
                else
                {
                    //Seteo directemente una clave independiente de lo que este grabado en la base, ademas como solo es para modo desarrollo no afecta para el desarrollo
                    if(clave.equals("1234")) //Todo: ver si se setea esta variable de forma global aunque no hay necesidad
                    {
                        usuario=usuarioRoot;
                        LOG.log(Level.INFO, "Ingresando con el usuario root en produccion: " + usuarioTxt);
                        setVisible(false);
                    }
                    else
                    {
                        DialogoCodefac.mensaje("Error Login","Datos Incorrectos para root en modo desarrollo",DialogoCodefac.MENSAJE_INCORRECTO);
                    }

                }
                    
            }
            else //Validacion para usuarios normales que no son root
            {            
                try {
                    usuario=usuarioServicio.login(usuarioTxt,clave);
                    if(usuario!=null)
                    {
                        LOG.log(Level.INFO, "Ingresando con el usuario: "+usuarioTxt);
                        setVisible(false);
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

    private void valoresIniciales() {
        String anioActualStr=UtilidadesFecha.obtenerAnioStr(UtilidadesFecha.getFechaHoy());
        getLblPiePagina().setText("Codefac software de facturación electrónica @ Codesoft "+anioActualStr);
    }
    
}
