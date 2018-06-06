/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.main.license.Licencia;
import ec.com.codesoft.codefaclite.main.license.ValidacionLicenciaCodefac;
import ec.com.codesoft.codefaclite.main.license.excepcion.NoExisteLicenciaException;
import ec.com.codesoft.codefaclite.main.license.excepcion.ValidacionLicenciaExcepcion;
import ec.com.codesoft.codefaclite.main.panel.ValidarLicenciaDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.service.UsuarioServicio;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PerfilUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UsuarioServicioIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.ws.codefac.test.service.WebServiceCodefac;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ActualizarlicenciaRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ActualizarlicenciaResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ComprobarRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ComprobarResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.DevolverlicenciaRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.DevolverlicenciaResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ObtenerlicenciaRequestType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.ObtenerlicenciaResponseType;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.SOAPServer;
import ec.com.codesoft.codefaclite.ws.codefac.webservice.SOAPServerPortType;
import es.mityc.firmaJava.libreria.utilidades.UtilidadFechas;
import java.awt.Desktop;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import sun.applet.Main;

/**
 *
 * @author Carlos
 */
public class ValidarLicenciaModel extends ValidarLicenciaDialog{
    
    public Boolean licenciaCreada;
    public Boolean actualizaLicencia;
    
    public ValidarLicenciaModel(Frame parent, boolean modal,Boolean actualizarLicencia) {
        super(parent, modal);
        addListener();
        iniciarComponentes();
        this.licenciaCreada=false;
        this.actualizaLicencia=actualizarLicencia;
    }

    private void addListener() {
        addListenerButtons();
    }
    
    private void crearLicencia()
    {
        String usuarioTxt=getTxtUsuarioVerificar().getText();
        //Crea la nueva licencia con el usuario
        Licencia licencia=new Licencia();
        licencia.cargarLicenciaOnline(usuarioTxt);
        
        Properties propiedad = validacionLicenciaCodefac.crearLicenciaMaquina(licencia);

        
        //Actualizar la nueva licencia en el servidor
        WebServiceCodefac.actualizarLicencia(getTxtUsuarioVerificar().getText(), propiedad.getProperty(Licencia.PROPIEDAD_LICENCIA));
                
    }
    

    private void addListenerButtons() {
        getBtnSalirRegistro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        getBtnSalirRegistro1().addActionListener(new ActionListener() {
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
                crearLicencia();
                
                //Verificar que no exista el usuario admin
                if(getTxtUsuarioRegistrar().getText().equals("admin") || getTxtUsuarioRegistrar().getText().equals("root"))
                {
                    DialogoCodefac.mensaje("Error","No se puede crear un usuario con la palabra admin o root",DialogoCodefac.MENSAJE_INCORRECTO);
                    return;
                }
                
                
                //Genera un nuevo usuario con los datos ingresados                
                UsuarioServicioIf servicio=ServiceFactory.getFactory().getUsuarioServicioIf();
                Usuario usuario=new Usuario();
                String clave=new String(getTxtClaveRegistrar().getPassword());
                usuario.setClave(clave);
                usuario.setNick(getTxtUsuarioRegistrar().getText());    
                usuario.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                                
                try {
                    
                    servicio.grabarUsuario(usuario,Perfil.PERFIL_GRATIS);
                    
                } catch (ServicioCodefacException ex) {
                    DialogoCodefac.mensaje("Error", ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
                    return;
                } catch (RemoteException ex) {
                    Logger.getLogger(ValidarLicenciaModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                licenciaCreada=true;
                
                DialogoCodefac.mensaje("Felicidades","La licencia fue creada correctamente",DialogoCodefac.MENSAJE_CORRECTO);
                dispose();
                
            }
        });
        
        getBtnVerificar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(WebServiceCodefac.verificarCredenciales(getTxtUsuarioVerificar().getText(),new String(getTxtClaveVerificar().getPassword())))
                { 
                    //Verificar si existe la licencia para solo descargar
                    String licencia=WebServiceCodefac.getLicencia(getTxtUsuarioVerificar().getText());
                    
                    String tipoLicencia=WebServiceCodefac.getTipoLicencia(getTxtUsuarioVerificar().getText());
                    Integer cantidadUsuarios=WebServiceCodefac.getCantidadClientes(getTxtUsuarioVerificar().getText());
                    
                    /*
                    String moduloInventario = WebServiceCodefac.getModuloCodefac(getTxtUsuarioVerificar().getText(),ModuloCodefacEnum.INVENTARIO.getLetra());
                    String moduloGestionAcademica = WebServiceCodefac.getModuloCodefac(getTxtUsuarioVerificar().getText(),ModuloCodefacEnum.GESTIONA_ACADEMICA.getLetra());
                    String moduloFacturacion = WebServiceCodefac.getModuloCodefac(getTxtUsuarioVerificar().getText(),ModuloCodefacEnum.FACTURACION.getLetra());
                    String moduloCRM = WebServiceCodefac.getModuloCodefac(getTxtUsuarioVerificar().getText(),ModuloCodefacEnum.CRM.getLetra());
                    
                    //Agregar a una lista solo los modulos activos
                    
                    List<String> modulosActivos = new ArrayList<String>();
                    if (moduloInventario != null && moduloInventario.equals("s")) {
                        modulosActivos.add(moduloInventario);
                    }

                    if (moduloGestionAcademica != null && moduloGestionAcademica.equals("s")) {
                        modulosActivos.add(moduloGestionAcademica);
                    }

                    if (moduloFacturacion != null && moduloFacturacion.equals("s")) {
                        modulosActivos.add(moduloFacturacion);
                    }

                    if (moduloCRM != null && moduloCRM.equals("s")) {
                        modulosActivos.add(moduloCRM);
                    }*/
                    
                    //No hace verificaciones porque esta accion solo es accesible desde la pantalla de menu
                    //y se supone que ya esta validando la licencia anterior
                    if(actualizaLicencia)
                    {
                        getjTabbedPane1().setEnabledAt(0, false);
                        getjTabbedPane1().setEnabledAt(1, false);
                        getjTabbedPane1().setEnabledAt(2, true);
                        getjTabbedPane1().setSelectedIndex(2);
                        TipoLicenciaEnum licenciaEnum= TipoLicenciaEnum.getEnumByLetra(tipoLicencia);
                        getLblTipoLicenciaActualizar().setText(licenciaEnum.getNombre());
                        getLblNumeroMaquinasActualizar().setText(cantidadUsuarios+"");
                        getLblNumeroUsuariosActualizar().setText(licenciaEnum.getNumeroUsuarios());
                        return; //dtener la ejecucion
                        
                    }
                    
                    //Si la licencia existe
                    if(!licencia.equals("fail"))
                    {             
                        //Si existe en el servidor la licencia solo vuelve a descargar
                        Licencia licenciaDescargada=new Licencia();
                        licenciaDescargada.setUsuario(getTxtUsuarioVerificar().getText());
                        licenciaDescargada.setLicencia(licencia);
                        licenciaDescargada.setTipoLicenciaEnum(TipoLicenciaEnum.getEnumByLetra(tipoLicencia));
                        licenciaDescargada.setCantidadClientes(cantidadUsuarios);
                        licenciaDescargada.cargarModulosOnline();
                        
                        validacionLicenciaCodefac.crearLicenciaDescargada(licenciaDescargada);
                        
                        licenciaCreada=true;
                        DialogoCodefac.mensaje("Adverencia","La licencia ya esta registrada y fue descargada",DialogoCodefac.MENSAJE_ADVERTENCIA);
                        dispose();
                        return ;//Detener la ejecucion
                    }
                    
                    
                    getjTabbedPane1().setEnabledAt(0, false);
                    getjTabbedPane1().setEnabledAt(1, true);
                    getjTabbedPane1().setEnabledAt(2, false);
                    getjTabbedPane1().setSelectedIndex(1);
                    
                    TipoLicenciaEnum licenciaEnum = TipoLicenciaEnum.getEnumByLetra(tipoLicencia);
                    //Actualizar los labes para actualizar o para crear
                    getLblTipoLicenciaActualizar().setText(licenciaEnum.getNombre());
                    getLblNumeroMaquinasActualizar().setText(cantidadUsuarios+"");
                    getLblNumeroUsuariosActualizar().setText(licenciaEnum.getNumeroUsuarios());
                    
                    //TODO: Verificar si es necesario que esta seteando los datos tanto para registro y para crear
                    getLblTipoLicenciaRegistro().setText(licenciaEnum.getNombre());
                    getLblNumeroMaquinaRegistro().setText(cantidadUsuarios+"");
                    getLblNumeroUsuarioRegistro().setText(licenciaEnum.getNumeroUsuarios());
                    
                    
                    
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
        
        getBtnActualizarLicencia().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean problemaLicenciaAnterior=false; 
                try {
                    if(!validacionLicenciaCodefac.validar())
                    {
                        problemaLicenciaAnterior=true;                        
                    }
                } catch (ValidacionLicenciaExcepcion ex) {
                    problemaLicenciaAnterior=true;
                    Logger.getLogger(ValidarLicenciaModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoExisteLicenciaException ex) {
                    problemaLicenciaAnterior=true;
                    Logger.getLogger(ValidarLicenciaModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //Mostrar mensaje cuando exista algun problema con la licencia anterior
                if(problemaLicenciaAnterior)
                {
                    DialogoCodefac.mensaje("Advertencia","Existe problemas con su licencia anterior, Comuníquese con soporte técnico para resolver este problema",DialogoCodefac.MENSAJE_ADVERTENCIA);
                }
                else
                {
                    
                    //Crear la nueva licencia con los datos de esta maquina
                    crearLicencia();
                    DialogoCodefac.mensaje("Felicidades", "Su licencia fue actualizada correctamente", DialogoCodefac.MENSAJE_CORRECTO);
                    dispose();
                    ec.com.codesoft.codefaclite.main.init.Main.iniciarComponentes();
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
    
    
    
    public static void main(String[] args)
    {
        ValidarLicenciaModel validar=new ValidarLicenciaModel(null,true,true);
        validar.setVisible(true);
    }

    private void iniciarComponentes() {
        getjTabbedPane1().setEnabledAt(0, true);
        getjTabbedPane1().setEnabledAt(1, false);
        getjTabbedPane1().setEnabledAt(2, false);
    }
    
    
    
    
}
