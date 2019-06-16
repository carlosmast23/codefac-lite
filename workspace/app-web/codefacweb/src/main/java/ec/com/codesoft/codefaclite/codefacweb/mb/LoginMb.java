/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb;

import ec.com.codesoft.codefaclite.codefacweb.core.SessionMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.MensajeMb;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PerfilServiceIf;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Carlos
 */
@ManagedBean
@RequestScoped
public class LoginMb implements Serializable {

    private String nick;
    private String clave;
    private Sucursal sucursalSeleccionada;
    private Empresa empresaSeleccionada;

    private List<Sucursal> sucursales;
    private List<Empresa> empresas;

    @ManagedProperty(value = "#{sessionMb}")
    private SessionMb sessionMb;

    @PostConstruct
    public void init() {
        iniciarDatos();
    }

    public String login() {
        try {
            System.out.println("Login Empresa:" + empresaSeleccionada);
            System.out.println("Login Sucursal:" + sucursalSeleccionada);
            Usuario usuario = ServiceFactory.getFactory().getUsuarioServicioIf().login(nick, clave);
            if (usuario != null) {
                construirSession(usuario, empresaSeleccionada, sucursalSeleccionada);
                return "proforma";
            } else {
                MensajeMb.mostrarMensaje("Error", "Credenciales incorrectass", FacesMessage.SEVERITY_ERROR);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(LoginMb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "login.xhtml";
    }

    private void construirSession(Usuario usuario, Empresa empresa, Sucursal sucursal) {
        try {
            SessionCodefac session = ServiceFactory.getFactory().getUtilidadesServiceIf().getSessionPreConstruido();

            session.setEmpresa(empresa);

            session.setUsuario(usuario);
            session.setPerfiles(obtenerPerfilesUsuario(usuario));
            session.setSucursal(sucursal);//Todo:Seteado por defecto
            session.setMatriz(sucursal);//TODO: Falta buscar la matriz de esa sucursal
            sessionMb.setSession(session);

        } catch (RemoteException ex) {
            Logger.getLogger(LoginMb.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private Sucursal obtenerMatriz(Sucursal sucursal) {
        //Sucursal sucursal = (Sucursal) getCmbSucursal().getSelectedItem();
        Sucursal matriz = null;
        if (sucursal != null) {
            try {
                matriz = ServiceFactory.getFactory().getSucursalServiceIf().obtenerMatrizPorSucursal(sucursal.getEmpresa());
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(LoginMb.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(LoginMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return matriz;
    }

    public static List<Perfil> obtenerPerfilesUsuario(Usuario usuario) {
        try {
            PerfilServiceIf servicio = ServiceFactory.getFactory().getPerfilServicioIf();
            return servicio.obtenerPerfilesPorUsuario(usuario);
        } catch (RemoteException ex) {
            Logger.getLogger(LoginMb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public SessionMb getSessionMb() {
        return sessionMb;
    }

    public void setSessionMb(SessionMb sessionMb) {
        this.sessionMb = sessionMb;
    }

    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    private void iniciarDatos() {

        try {
            empresas = ServiceFactory.getFactory().getEmpresaServiceIf().obtenerTodos();
            if (empresas.size() > 0) //Selecciono por defecto la primera empresa y carga la sucursal
            {
                empresaSeleccionada = empresas.get(0);
                cargarSucursales(empresaSeleccionada);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(LoginMb.class.getName()).log(Level.SEVERE, null, ex);
        }
        //sucursales=ServiceFactory.getFactory().getSucursalServiceIf().consultarActivosPorEmpresa(empresa);
    }

    public void cargarSucursalesEvento(SelectEvent event) {
        //System.out.println("ejecuntando evento seleccionar");
        cargarSucursales(empresaSeleccionada);
    }

    private void cargarSucursales(Empresa empresa) {
        try {
            System.err.println(empresaSeleccionada.getId());
            sucursales = ServiceFactory.getFactory().getSucursalServiceIf().consultarActivosPorEmpresa(empresaSeleccionada);
            System.out.println("Datos encontrados" + sucursales.size());
        } catch (RemoteException ex) {
            Logger.getLogger(LoginMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(LoginMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Sucursal getSucursalSeleccionada() {
        return sucursalSeleccionada;
    }

    public void setSucursalSeleccionada(Sucursal sucursalSeleccionada) {
        this.sucursalSeleccionada = sucursalSeleccionada;
    }

    public Empresa getEmpresaSeleccionada() {
        return empresaSeleccionada;
    }

    public void setEmpresaSeleccionada(Empresa empresaSeleccionada) {
        this.empresaSeleccionada = empresaSeleccionada;
    }

}
