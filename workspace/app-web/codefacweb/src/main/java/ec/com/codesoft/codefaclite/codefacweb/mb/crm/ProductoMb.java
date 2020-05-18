/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.crm;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.MensajeMb;
import ec.com.codesoft.codefaclite.controlador.vista.crm.ProductoModelControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import java.io.Serializable;
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
public class ProductoMb extends GeneralAbstractMb implements Serializable,ProductoModelControlador.ProductoModelControladorInterface{

    private EnumSiNo[] listaManejaInventario;
    private EnumSiNo[] listaGenerarCodigoBarras;
    private TipoProductoEnum[] listaTipoProducto;
    private List<CategoriaProducto> listaCategoriaProducto;
    private List<ImpuestoDetalle> listaImpuestoDetalleIva;
    private List<ImpuestoDetalle> listaImpuestoDetalleIce;
    private List<ImpuestoDetalle> listaImpuestoDetalleIrbpnr;
    
    private ProductoModelControlador controlador;
    
    @PostConstruct
    private void init()
    {
        controlador=new ProductoModelControlador(MensajeMb.intefaceMensaje,sessionMb.getSession(), this);
        controlador.iniciarCombosBox();
    }
    
    
    @Override
    public void nuevo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, UnsupportedOperationException {
              
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
        return VentanaEnum.PRODUCTO.getNombre();
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public EnumSiNo[] getListaManejaInventario() {
        return listaManejaInventario;
    }

    public void setListaManejaInventario(EnumSiNo[] listaManejaInventario) {
        this.listaManejaInventario = listaManejaInventario;
    }

    public EnumSiNo[] getListaGenerarCodigoBarras() {
        return listaGenerarCodigoBarras;
    }

    public void setListaGenerarCodigoBarras(EnumSiNo[] listaGenerarCodigoBarras) {
        this.listaGenerarCodigoBarras = listaGenerarCodigoBarras;
    }

    public TipoProductoEnum[] getListaTipoProducto() {
        return listaTipoProducto;
    }

    public void setListaTipoProducto(TipoProductoEnum[] listaTipoProducto) {
        this.listaTipoProducto = listaTipoProducto;
    }

    public List<CategoriaProducto> getListaCategoriaProducto() {
        return listaCategoriaProducto;
    }

    public void setListaCategoriaProducto(List<CategoriaProducto> listaCategoriaProducto) {
        this.listaCategoriaProducto = listaCategoriaProducto;
    }

    public List<ImpuestoDetalle> getListaImpuestoDetalleIva() {
        return listaImpuestoDetalleIva;
    }

    public void setListaImpuestoDetalleIva(List<ImpuestoDetalle> listaImpuestoDetalleIva) {
        this.listaImpuestoDetalleIva = listaImpuestoDetalleIva;
    }

    public List<ImpuestoDetalle> getListaImpuestoDetalleIce() {
        return listaImpuestoDetalleIce;
    }

    public void setListaImpuestoDetalleIce(List<ImpuestoDetalle> listaImpuestoDetalleIce) {
        this.listaImpuestoDetalleIce = listaImpuestoDetalleIce;
    }

    public List<ImpuestoDetalle> getListaImpuestoDetalleIrbpnr() {
        return listaImpuestoDetalleIrbpnr;
    }

    public void setListaImpuestoDetalleIrbpnr(List<ImpuestoDetalle> listaImpuestoDetalleIrbpnr) {
        this.listaImpuestoDetalleIrbpnr = listaImpuestoDetalleIrbpnr;
    }
    
    /**
     * ========================================================================
     *              DATOS VARIOS ADICIONALES
     * ========================================================================
     * @param datos 
     */
    
    
    

    public void llenarCmbManejaInventario(EnumSiNo[] datos) {
        this.listaManejaInventario=datos;
    }

    public void llenarCmbGenerarCodigoBarras(EnumSiNo[] datos) {
        this.listaGenerarCodigoBarras=datos;
    }

    public void llenarCmbTipoProducto(TipoProductoEnum[] tipoProductoList) {
        this.listaTipoProducto=tipoProductoList;
    }

    public void llenarCmbCategoriaProducto(List<CategoriaProducto> catProdList) {
        this.listaCategoriaProducto=catProdList;
    }

    public void llenarComboIva(List<ImpuestoDetalle> impuestos) {
        this.listaImpuestoDetalleIva=impuestos;
    }

    public void llenarComboIce(List<ImpuestoDetalle> impuestos) {
        this.listaImpuestoDetalleIce=impuestos;
    }

    public void llenarComboIrbpnr(List<ImpuestoDetalle> impuestos) {
        this.listaImpuestoDetalleIrbpnr=impuestos;
    }

    public void seleccionarComboIva(ImpuestoDetalle impuesto) {
        //TODO: Falta implementar
    }

    public void llenarCmbGarantia(EnumSiNo[] datos) {
        //TODO: Falta implementar
    }
    
}
