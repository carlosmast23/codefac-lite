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
public class PresupuestoReporteData implements ExcelDatosInterface {
    private String codigo;
    private String identificacion;
    private String nombres;
    private String descripcion;
    private String fecha;
    private String estado;
    private String ordenTrabajo;
    private BigDecimal total;

    public PresupuestoReporteData() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(String ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }
    
    
    

    @Override
    public List<TipoDato> getDatos() {
        List<TipoDato> tiposDatos = new ArrayList<TipoDato>();
        
        tiposDatos.add(new TipoDato(this.codigo,Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.ordenTrabajo,Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.identificacion, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.fecha, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.estado, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.nombres,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.descripcion, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.total, Excel.TipoDataEnum.NUMERO));        
        return tiposDatos;
    }
    
    
}
