/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.data;

import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.controlador.excel.TipoDato;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TransferenciaReporteData implements ExcelDatosInterface{
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

    @Override
    public List<TipoDato> getDatos() {
        List<TipoDato> tiposDatos = new ArrayList<TipoDato>();
        
        tiposDatos.add(new TipoDato(this.producto,Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.tipoTransaccion, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.bodegaDestino,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.bodegaOrigen, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.empresa, Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.fechaIngreso,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.cantidad,Excel.TipoDataEnum.NUMERO));
        
        return tiposDatos;
    }
    
    
    
}
