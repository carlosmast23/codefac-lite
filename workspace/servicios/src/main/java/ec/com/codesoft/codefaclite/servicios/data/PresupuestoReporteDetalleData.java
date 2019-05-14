/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servicios.data;

import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.controlador.excel.TipoDato;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class PresupuestoReporteDetalleData extends PresupuestoReporteData{
    
    private String proveedor;
    private String producto;
    private BigDecimal precioCompra;
    private BigDecimal descuentoCompra;
    private BigDecimal cantidadCompra;
    private BigDecimal totalCompra;
    
    

    public PresupuestoReporteDetalleData(PresupuestoReporteData presupuestoReporteData) {
        setCodigo(presupuestoReporteData.getCodigo());
        setIdentificacion(presupuestoReporteData.getIdentificacion());
        setNombres(presupuestoReporteData.getNombres());
        setDescripcion(presupuestoReporteData.getDescripcion());
        setFecha(presupuestoReporteData.getFecha());
        setEstado(presupuestoReporteData.getEstado());
        setOrdenTrabajo(presupuestoReporteData.getOrdenTrabajo());
        setTotal(presupuestoReporteData.getTotal());
        setCompras(presupuestoReporteData.getCompras());
        setProduccionInterna(presupuestoReporteData.getProduccionInterna());
        setUtilidad(presupuestoReporteData.getUtilidad());
    }
    
    

    public PresupuestoReporteDetalleData() {
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public BigDecimal getDescuentoCompra() {
        return descuentoCompra;
    }

    public void setDescuentoCompra(BigDecimal descuentoCompra) {
        this.descuentoCompra = descuentoCompra;
    }

    public BigDecimal getCantidadCompra() {
        return cantidadCompra;
    }

    public void setCantidadCompra(BigDecimal cantidadCompra) {
        this.cantidadCompra = cantidadCompra;
    }

    public BigDecimal getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(BigDecimal totalCompra) {
        this.totalCompra = totalCompra;
    }
    
    

    @Override
    public List<TipoDato> getDatos() {
        List<TipoDato> tiposDatos = new ArrayList<TipoDato>();
        
        //Datos Adicionales
        tiposDatos.add(new TipoDato(this.getProducto(),Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.getProveedor(),Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.getPrecioCompra(), Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.getDescuentoCompra(), Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.getCantidadCompra(), Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.getTotalCompra(),Excel.TipoDataEnum.NUMERO));
        
        
        tiposDatos.add(new TipoDato(this.getCodigo(),Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.getOrdenTrabajo(),Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.getIdentificacion(), Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.getFecha(), Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.getEstado(), Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.getNombres(),Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.getDescripcion(), Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.getTotal(), Excel.TipoDataEnum.NUMERO));        
        tiposDatos.add(new TipoDato(this.getCompras(), Excel.TipoDataEnum.NUMERO));        
        tiposDatos.add(new TipoDato(this.getProduccionInterna(), Excel.TipoDataEnum.NUMERO));        
        tiposDatos.add(new TipoDato(this.getUtilidad(), Excel.TipoDataEnum.NUMERO));        
        
        
        return tiposDatos;
    }
    
    
}
