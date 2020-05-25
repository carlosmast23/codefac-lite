/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.facturacion;

import autorizacion.ws.sri.gob.ec.Mensaje;
import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.MensajeMb;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ReenviarComprobanteModelControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author CARLOS_CODESOFT
 */
@ManagedBean
@ViewScoped
public class ReenviarComprobanteMb extends GeneralAbstractMb implements ReenviarComprobanteModelControlador.InterfazModelControlador{

    private ReenviarComprobanteModelControlador controlador;
    private List<ComprobanteElectronico> comprobantesPendientes;
    private List<ComprobanteElectronico> comprobantesPendientesSeleccionados;
    
    @PostConstruct
    public void init()
    {
        controlador=new ReenviarComprobanteModelControlador(MensajeMb.intefaceMensaje,sessionMb.getSession(), this);
        controlador.iniciar();
    }
    
    @Override
    public void nuevo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarBusqueda(Object obj) throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String titulo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        return VentanaEnum.REENVIO_COMPROBANTES.getNombre();
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void cargarVistaTabla(List<ComprobanteElectronico> comprobantes) {
        this.comprobantesPendientes=comprobantes;
    }

    public List<ComprobanteElectronico> getComprobantesPendientes() {
        return comprobantesPendientes;
    }

    public void setComprobantesPendientes(List<ComprobanteElectronico> comprobantesPendientes) {
        this.comprobantesPendientes = comprobantesPendientes;
    }

    public List<ComprobanteElectronico> getComprobantesPendientesSeleccionados() {
        return comprobantesPendientesSeleccionados;
    }

    public void setComprobantesPendientesSeleccionados(List<ComprobanteElectronico> comprobantesPendientesSeleccionados) {
        this.comprobantesPendientesSeleccionados = comprobantesPendientesSeleccionados;
    }

    

    
    
    
}
