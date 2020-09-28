/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.data;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TransferenciaReporteData {
    private String producto;
    private String bodegaDestino;
    private String bodegaOrigen;
    private String fechaIngreso;
    private String cantidad;
    private String empresa;
    private String tipoTransaccion;

    public TransferenciaReporteData(String producto, String bodegaDestino,String bodegaOrigen, String fechaIngreso, String cantidad, String empresa,String tipoTransaccion) {
        this.producto = producto;
        this.bodegaDestino = bodegaDestino;
        this.bodegaOrigen=bodegaOrigen;
        this.fechaIngreso = fechaIngreso;
        this.cantidad = cantidad;
        this.empresa = empresa;
        this.tipoTransaccion=tipoTransaccion;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getBodegaDestino() {
        return bodegaDestino;
    }

    public void setBodegaDestino(String bodegaDestino) {
        this.bodegaDestino = bodegaDestino;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getBodegaOrigen() {
        return bodegaOrigen;
    }

    public void setBodegaOrigen(String bodegaOrigen) {
        this.bodegaOrigen = bodegaOrigen;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }
    
    
    
}
