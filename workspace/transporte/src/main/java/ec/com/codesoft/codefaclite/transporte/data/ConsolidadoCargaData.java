/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.transporte.data;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DetalleProductoGuiaRemision;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.internal.sessions.factories.SessionsFactory;
/**
 *
 * @author CARLOS_CODESOFT
 */
public class ConsolidadoCargaData {
    
    private String codigoInterno;
    private String codigoAdicional;
    private String descripcion;
    private BigDecimal cantidad;
    private BigDecimal total;
    
    
    public ConsolidadoCargaData(DetalleProductoGuiaRemision data)
    {
        this.codigoInterno=consultarCodigoInternoProducto(data.getReferenciaId());
        this.codigoAdicional=data.getCodigoAdicional();
        this.descripcion=data.getDescripcion();
        this.cantidad=data.getCantidad();
        this.total=BigDecimal.ZERO;
    }
    
    /**
     * TODO: Metodo temporal por que como son muchas consultas de debe hacer desde el servidor
     */
    private String consultarCodigoInternoProducto(Long referenciaId)
    {
        try {
            FacturaDetalle facturaDetalle= ServiceFactory.getFactory().getFacturaDetalleServiceIf().buscarPorId(referenciaId);
            //TODO: Solo queda hecho para el caso que asumiendo es inventario pero tocaria hacer por tipo de documento
            Producto producto= ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(facturaDetalle.getReferenciaId());
            if(producto!=null)
            {
                return producto.getCodigoPersonalizado();
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(ConsolidadoCargaData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
         
    }

    public ConsolidadoCargaData(String codigoInterno, String codigoAdicional, String descripcion, BigDecimal cantidad) {
        this.codigoInterno = codigoInterno;
        this.codigoAdicional = codigoAdicional;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public String getCodigoAdicional() {
        return codigoAdicional;
    }

    public void setCodigoAdicional(String codigoAdicional) {
        this.codigoAdicional = codigoAdicional;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    

    ///////////////////////////////////////////////////////////////////////////
    //                  METODOS PERSONALAZIDOS                             ////
    ///////////////////////////////////////////////////////////////////////////
    
    public void agregarCantidad(BigDecimal cantidad)
    {
        this.cantidad=this.cantidad.add(cantidad);
    }
    
    
}
