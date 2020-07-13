/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.transporte.data;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DetalleProductoGuiaRemision;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ConsolidadoCargaData {
    
    private String codigoInterno;
    private String codigoAdicional;
    private String descripcion;
    private Integer cantidad;
    
    public ConsolidadoCargaData(DetalleProductoGuiaRemision data)
    {
        this.codigoInterno=data.getCodigoInterno();
        this.codigoAdicional=data.getCodigoAdicional();
        this.descripcion=data.getDescripcion();
        this.cantidad=data.getCantidad();
    }

    public ConsolidadoCargaData(String codigoInterno, String codigoAdicional, String descripcion, Integer cantidad) {
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    ///////////////////////////////////////////////////////////////////////////
    //                  METODOS PERSONALAZIDOS                             ////
    ///////////////////////////////////////////////////////////////////////////
    
    public void agregarCantidad(Integer cantidad)
    {
        this.cantidad+=cantidad;
    }
    
    
}
