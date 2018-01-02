/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.main.license.Licencia;
import ec.com.codesoft.codefaclite.main.license.ValidacionLicenciaCodefac;
import ec.com.codesoft.codefaclite.main.panel.ValidarLicenciaDialog;
import ec.com.codesoft.codefaclite.servidor.entity.Perfil;
import ec.com.codesoft.codefaclite.servidor.entity.Usuario;
import ec.com.codesoft.codefaclite.servidor.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.service.UsuarioServicio;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ActualizarlicenciaRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ActualizarlicenciaResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ComprobarRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ComprobarResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ObtenerlicenciaRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ObtenerlicenciaResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.SOAPServer;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.SOAPServerPortType;
import java.awt.Desktop;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class ValidarLicenciaModel extends ValidarLicenciaDialog{
    
    public Boolean licenciaCreada;
    
    public ValidarLicenciaModel(Frame parent, boolean modal) {
        super(parent, modal);
        addListener();
        iniciarComponentes();
        this.licenciaCreada=false;
    }

    private void addListener() {
        addListenerButtons();
    }

    private void addListenerButtons() {
        getBtnSalirRegistro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        getBtnSalirVerificar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        getBtnRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                //Crea la nueva licencia con el usuario
                Properties propiedad=validacionLicenciaCodefac.crearLicencia(getTxtUsuarioVerificar().getText());
                
                //Actualizar la licencia en la maquina
                SOAPServer soapServer = new SOAPServer();
                SOAPServerPortType soapServerPort = soapServer.getSOAPServerPort();

                ActualizarlicenciaRequestType parametros = new ActualizarlicenciaRequestType();
                parametros.setEmail(getTxtUsuarioVerificar().getText());
                parametros.setLicencia(propiedad.getProperty(Licencia.PROPIEDAD_LICENCIA));

                ActualizarlicenciaResponseType respuesta = soapServerPort.actualizarlicencia(parametros);
                
                //Verificar que no exista el usuario admin
                if(getTxtUsuarioRegistrar().getText().equals("admin"))
                {
                    DialogoCodefac.mensaje("Error","No se puede crear un usuario con la palabra admin",DialogoCodefac.MENSAJE_INCORRECTO);
                    return;
                }
                
                
                //Genera un nuevo usuario con los datos ingresados
                UsuarioServicio servicio=new UsuarioServicio();
                Usuario usuario=new Usuario();
                String clave=new String(getTxtClaveRegistrar().getPassword());
                usuario.setClave(clave);
                usuario.setNick(getTxtUsuarioRegistrar().getText());                
                try {
                    servicio.grabarUsuario(usuario,Perfil.PERFIl_OPERADOR);
                } catch (ServicioCodefacException ex) {
                    DialogoCodefac.mensaje("Error", ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
                    return;
                }
                
                licenciaCreada=true;
                
                DialogoCodefac.mensaje("Felicidades","La licencia fue creada correctamente",DialogoCodefac.MENSAJE_CORRECTO);
                dispose();
                
            }
        });
        
        getBtnVerificar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(verificarLicencia())
                { 
                    //Verificar si existe la licencia para solo descargar
                    SOAPServer soapServer = new SOAPServer();
                    SOAPServerPortType soapServerPort = soapServer.getSOAPServerPort();

                    ObtenerlicenciaRequestType parametros = new ObtenerlicenciaRequestType();
                    parametros.setEmail(getTxtUsuarioVerificar().getText());
                    ObtenerlicenciaResponseType respuesta = soapServerPort.obtenerlicencia(parametros);
                    
                    if(!respuesta.getReturn().equals("fail"))
                    {
                        //Si existe en el servidor la licencia solo vuelve a descargar
                        validacionLicenciaCodefac.crearLicencia(getTxtUsuarioVerificar().getText(),respuesta.getReturn());
                        licenciaCreada=true;
                        DialogoCodefac.mensaje("Adverencia","La licencia ya esta registrada y fue descargada",DialogoCodefac.MENSAJE_ADVERTENCIA);
                        dispose();
                        return ;//Detener la ejecucion
                    }
                    
                    getjTabbedPane1().setEnabledAt(0, false);
                    getjTabbedPane1().setEnabledAt(1, true);
                    getjTabbedPane1().setSelectedIndex(1);
                    
                    //Setear las variables del usuario y la clave del la pagina web
                    getTxtUsuarioRegistrar().setText(getTxtUsuarioVerificar().getText());
                    getTxtClaveRegistrar().setText(new String(getTxtClaveVerificar().getPassword()));
                    
                    
                }
                else
                {
                    DialogoCodefac.mensaje("Error","El usuario o la clave son incorrectas", DialogoCodefac.MENSAJE_ADVERTENCIA);
                }
            }
        });
        
        getLblRegistro().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop dk = Desktop.getDesktop();
                    dk.browse(new URI("http://www.cf.codesoft-ec.com/general/registro"));
                } catch (IOException ex) {
                    Logger.getLogger(HiloPublicidadCodefac.class.getName()).log(Level.SEVERE, null, ex);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(HiloPublicidadCodefac.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
    }
    
    private boolean verificarLicencia()
    {
        SOAPServer soapServer=new SOAPServer();
        SOAPServerPortType soapServerPort=soapServer.getSOAPServerPort();
        ComprobarRequestType parametros=new ComprobarRequestType();
        parametros.setC(new String(getTxtClaveVerificar().getPassword()));
        parametros.setU(new String(getTxtUsuarioVerificar().getText()));
        ComprobarResponseType respuesta= soapServerPort.comprobar(parametros);
        if("success".equals(respuesta.getReturn()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    
    public static void main(String[] args)
    {
        ValidarLicenciaModel validar=new ValidarLicenciaModel(null,true);
        validar.setVisible(true);
    }

    private void iniciarComponentes() {
        getjTabbedPane1().setEnabledAt(0, true);
        getjTabbedPane1().setEnabledAt(1, false);
    }
    
    
    
    
}
