/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.reportData;


import ec.com.codesoft.codefaclite.servidorinterfaz.controller.Excel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.TipoDato;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.result.AbstractResult;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DellWin10
 */
public class UtilidadResult extends AbstractResult implements ExcelDatosInterface{
    
    public Long facturaDetalleId;
    
    private String secuencial;
    
    public Date fechaEmision;
    
    private String razonSocial;
    
    private String nombreProducto;
            
    public String identificacion;
    
    public Long facturaId;
    
    public BigDecimal subtotal;
    
    public BigDecimal costo;
    
    public BigDecimal utilidad;
    
    private String fechaEmisionStr;
    
    private String vendedor;
    private String referente;
    private String colaborador;
    
    private Long vendedorComision;
    private Long referenteComision;
    private Long colaboradorComision;
    

    
    @Override
    public void constructor(Object[] dato) {
        int indice=0;
        facturaDetalleId=(Long) dato[indice++];
        secuencial=(String) dato[indice++];
        fechaEmision=(Date) dato[indice++];
        razonSocial=(String) dato[indice++];
        identificacion=(String)dato[indice++];
        nombreProducto=(String)dato[indice++];
        
        vendedor=(String)dato[indice++];
        vendedorComision=(Long)dato[indice++];
        
        referente=(String)dato[indice++];
        referenteComision=(Long)dato[indice++];
        
        colaborador=(String)dato[indice++];
        colaboradorComision=(Long)dato[indice++];
        
        facturaId=(Long) dato[indice++];
        subtotal=(BigDecimal) dato[indice++];
        costo=(BigDecimal) dato[indice++];
        utilidad=(BigDecimal) dato[indice++];
        
        //Setear la fecha de emision
        fechaEmisionStr=ParametrosSistemaCodefac.FORMATO_ESTANDAR_FECHA.format(fechaEmision);
        
        if(costo==null)
        {
            costo=BigDecimal.ZERO;
        }
        
        if(utilidad==null)
        {
            utilidad=BigDecimal.ZERO;
        }
        
        subtotal=subtotal.setScale(2, RoundingMode.HALF_UP);
        costo=costo.setScale(2, RoundingMode.HALF_UP);
        utilidad=utilidad.setScale(2, RoundingMode.HALF_UP);
    }

    public String getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(String secuencial) {
        this.secuencial = secuencial;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public BigDecimal getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(BigDecimal utilidad) {
        this.utilidad = utilidad;
    }

    public String getFechaEmisionStr() {
        return fechaEmisionStr;
    }

    public void setFechaEmisionStr(String fechaEmisionStr) {
        this.fechaEmisionStr = fechaEmisionStr;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Long getFacturaDetalleId() {
        return facturaDetalleId;
    }

    public void setFacturaDetalleId(Long facturaDetalleId) {
        this.facturaDetalleId = facturaDetalleId;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getReferente() {
        return referente;
    }

    public void setReferente(String referente) {
        this.referente = referente;
    }

    public String getColaborador() {
        return colaborador;
    }

    public void setColaborador(String colaborador) {
        this.colaborador = colaborador;
    }

    public Long getVendedorComision() {
        return vendedorComision;
    }

    public void setVendedorComision(Long vendedorComision) {
        this.vendedorComision = vendedorComision;
    }

    public Long getReferenteComision() {
        return referenteComision;
    }

    public void setReferenteComision(Long referenteComision) {
        this.referenteComision = referenteComision;
    }

    public Long getColaboradorComision() {
        return colaboradorComision;
    }

    public void setColaboradorComision(Long colaboradorComision) {
        this.colaboradorComision = colaboradorComision;
    }


    
    
    
    

    @Override
    public List<TipoDato> getDatos() {
         List<TipoDato> tiposDatos = new ArrayList<>();
        tiposDatos.add(new TipoDato("",Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.secuencial,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.fechaEmisionStr,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.razonSocial,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.identificacion,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.nombreProducto,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.vendedor,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.vendedorComision,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.referente,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.referenteComision,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.colaborador,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.colaboradorComision,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.subtotal,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.costo,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.utilidad,Excel.TipoDataEnum.NUMERO));
        
        
        return tiposDatos;
        
    }
    
    
    

    
}

