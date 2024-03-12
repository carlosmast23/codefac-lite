/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.result;

import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 *
 * @author DellWin10
 */
public class UtilidadResult extends AbstractResult{
    
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

    
    @Override
    public void constructor(Object[] dato) {
        facturaDetalleId=(Long) dato[0];
        secuencial=(String) dato[1];
        fechaEmision=(Date) dato[2];
        razonSocial=(String) dato[3];
        identificacion=(String)dato[4];
        nombreProducto=(String)dato[5];
        facturaId=(Long) dato[6];
        subtotal=(BigDecimal) dato[7];
        costo=(BigDecimal) dato[8];
        utilidad=(BigDecimal) dato[9];
        
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

    public Long getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Long facturaId) {
        this.facturaId = facturaId;
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
    
    
    

    
}

