/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.restaurant;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author CARLOS_CODESOFT
 */
@ManagedBean
@ViewScoped
public class MonitorComandaMb extends GeneralAbstractMb implements Serializable {
    
    private List<Factura> comandaList;
    
    
    
    @PostConstruct
    public void init()
    {
        System.out.println("init ...");
        consultarComandasPendientes();
        
    }
    
    public void consultarComandasPendientes()
    {
        try {
            //Consultar comandas pendientes
            comandaList=ServiceFactory.getFactory().getFacturacionServiceIf().consultarComandaReporte(null, null,null,sessionMb.getSession().getEmpresa(), ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO);
            System.out.println("comandas totales: "+comandaList.size());
        } catch (RemoteException ex) {
            Logger.getLogger(MonitorComandaMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(MonitorComandaMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, UnsupportedOperationException {
        
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        
    }

    @Override
    public String titulo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        //return VentanaEnum.COMANDA_MONITOR.getNombre();
        return "Comanda Monitor";
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        
    }

    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getURLAyuda() {
        return "";
    }

    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void ejemplo()
    {
        System.out.println("ejemplo ...");
    }
    
    ////////////////////////////////////////////////////////////////////
    ///                     GET AND SET METODOS
    ////////////////////////////////////////////////////////////////////

    public List<Factura> getComandaList() {
        return comandaList;
    }

    public void setComandaList(List<Factura> comandaList) {
        this.comandaList = comandaList;
    }
    
    
}
