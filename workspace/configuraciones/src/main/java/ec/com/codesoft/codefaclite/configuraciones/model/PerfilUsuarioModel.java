/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.busqueda.PerfilBusquedaDialogo;
import ec.com.codesoft.codefaclite.configuraciones.busqueda.PerfilUsuarioBusquedaDialogo;
import ec.com.codesoft.codefaclite.configuraciones.panel.PerfilUsuarioPanel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PerfilUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UsuarioServicioIf;
import ec.com.codesoft.ejemplo.utilidades.fecha.UtilidadesFecha;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author Carlos
 */
public class PerfilUsuarioModel extends PerfilUsuarioPanel{
    
    private Usuario usuario;
    

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        agregarListener();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            UsuarioServicioIf usuarioServicioIf= ServiceFactory.getFactory().getUsuarioServicioIf();
            setearValoresPantalla();
            usuarioServicioIf.grabar(usuario);
            DialogoCodefac.mensaje("Correcto","El usuario se grabo correctamente",DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error","No se pueden grabar los datos", DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(PerfilUsuarioModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            DialogoCodefac.mensaje("Error","Ocurrio un error con el servidor", DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(PerfilUsuarioModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        try {
            UsuarioServicioIf usuarioServicioIf = ServiceFactory.getFactory().getUsuarioServicioIf();
            setearValoresPantalla();
            usuarioServicioIf.editar(usuario);
            DialogoCodefac.mensaje("Correcto","El usuario se edito correctamente",DialogoCodefac.MENSAJE_CORRECTO);
        } catch (RemoteException ex) {
            DialogoCodefac.mensaje("Error","Ocurrio un error con el servidor", DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(PerfilUsuarioModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        PerfilUsuarioBusquedaDialogo perfilBusquedaDialogo=new PerfilUsuarioBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(perfilBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        Usuario usuarioTemp = (Usuario) buscarDialogoModel.getResultado();

        if (usuarioTemp == null) {
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar");
        }
        else
        {
            this.usuario=usuarioTemp;
        }
        
        getTxtUsuario().setText(usuario.getNick());
        getTxtClave().setText(usuario.getClave());
        cargarListaPerfilesUsuario();
    }

    @Override
    public void limpiar() {
        this.usuario=new Usuario();
        getTxtClave().setText("");
        getTxtClaveRepetir().setText("");
        getTxtUsuario().setText("");
        cargarListaPerfilesUsuario();
    }

    @Override
    public String getNombre() {
        return "Usuario y Roles";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void agregarListener() {
        getBtnAgregarPerfil().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PerfilBusquedaDialogo perfilBusquedaDialogo = new PerfilBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(perfilBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                Perfil perfilTemp = (Perfil) buscarDialogoModel.getResultado();
                
                if(perfilTemp!=null)
                {
                    //Si existe un usuario cargado agrega los detalles
                    if(usuario!=null)
                    {
                        PerfilUsuario perfilUsuario=new PerfilUsuario();
                        perfilUsuario.setFechaCreacion(UtilidadesFecha.getFechaHoy());
                        perfilUsuario.setPerfil(perfilTemp);
                        
                        usuario.addPerfilUsuario(perfilUsuario);
                        cargarListaPerfilesUsuario();
                    }
                }
                
            }
        });
        
        getBtnQuitarPerfil().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila=getjListPerfiles().getSelectedIndex();
                
                if(fila!=-1)
                {
                    usuario.getPerfilesUsuario().remove(fila);
                    cargarListaPerfilesUsuario();
                }
                
            }
        });
    }
    
    private void cargarListaPerfilesUsuario()
    {
        DefaultListModel listaModel=new DefaultListModel();
        
        if(usuario!=null && usuario.getPerfilesUsuario()!=null)
        {
            for (PerfilUsuario perfilUsuario : usuario.getPerfilesUsuario()) 
            {
                listaModel.addElement(perfilUsuario.getPerfil().getNombre());
            }
        }
        
        getjListPerfiles().setModel(listaModel);
    }

    private void setearValoresPantalla() {
        usuario.setNick(getTxtUsuario().getText());
        usuario.setClave(new String(getTxtClave().getPassword()));
    }
    
}
