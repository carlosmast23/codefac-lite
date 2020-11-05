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
 * @author Carlos
 */
public class KardexData implements ExcelDatosInterface{
    private String documento;
    private String preimpreso;
    private String proveedor;
    private String fecha;
    
    private String ingreso_cantidad;
    private String ingreso_precio;
    private String ingreso_total;
    
    private String egreso_cantidad;
    private String egreso_precio;
    private String egreso_total;
    
    private String saldo_cantidad;
    private String saldo_precio;
    private String saldo_total;
    
    private String fechaDocumento;
    private String documentoTransaccion;

    public KardexData() {
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getPreimpreso() {
        return preimpreso;
    }

    public void setPreimpreso(String preimpreso) {
        this.preimpreso = preimpreso;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIngreso_cantidad() {
        return ingreso_cantidad;
    }

    public void setIngreso_cantidad(String ingreso_cantidad) {
        this.ingreso_cantidad = ingreso_cantidad;
    }

    public String getIngreso_precio() {
        return ingreso_precio;
    }

    public void setIngreso_precio(String ingreso_precio) {
        this.ingreso_precio = ingreso_precio;
    }

    public String getIngreso_total() {
        return ingreso_total;
    }

    public void setIngreso_total(String ingreso_total) {
        this.ingreso_total = ingreso_total;
    }

    public String getEgreso_cantidad() {
        return egreso_cantidad;
    }

    public void setEgreso_cantidad(String egreso_cantidad) {
        this.egreso_cantidad = egreso_cantidad;
    }

    public String getEgreso_precio() {
        return egreso_precio;
    }

    public void setEgreso_precio(String egreso_precio) {
        this.egreso_precio = egreso_precio;
    }

    public String getEgreso_total() {
        return egreso_total;
    }

    public void setEgreso_total(String egreso_total) {
        this.egreso_total = egreso_total;
    }

    public String getSaldo_cantidad() {
        return saldo_cantidad;
    }

    public void setSaldo_cantidad(String saldo_cantidad) {
        this.saldo_cantidad = saldo_cantidad;
    }

    public String getSaldo_precio() {
        return saldo_precio;
    }

    public void setSaldo_precio(String saldo_precio) {
        this.saldo_precio = saldo_precio;
    }

    public String getSaldo_total() {
        return saldo_total;
    }

    public void setSaldo_total(String saldo_total) {
        this.saldo_total = saldo_total;
    }

    public String getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(String fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getDocumentoTransaccion() {
        return documentoTransaccion;
    }

    public void setDocumentoTransaccion(String documentoTransaccion) {
        this.documentoTransaccion = documentoTransaccion;
    }
    
    
    

    @Override
    public List<TipoDato> getDatos() {
        List<TipoDato> tiposDatos = new ArrayList<TipoDato>();        
        tiposDatos.add(new TipoDato(this.fecha, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.documento,Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.preimpreso, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.fechaDocumento, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.proveedor,Excel.TipoDataEnum.TEXTO));
        
        tiposDatos.add(new TipoDato(this.ingreso_cantidad, Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.ingreso_precio,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.ingreso_total,Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.egreso_cantidad, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.egreso_precio,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.egreso_total, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.saldo_cantidad, Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.saldo_precio,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.saldo_total,Excel.TipoDataEnum.TEXTO));    
        
        return tiposDatos;
    }
    
    
    
    
    
    
}
