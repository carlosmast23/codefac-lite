/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "FACTURA")
public class Factura {
    
    private Long id;
    private String claveAcceso;
    private Long empresaId;
    private Long clienteId;
    private Long tipoClienteId;
    private Integer secuencial;
    private String puntoEstablecimiento;
    private String puntoEmision;
    private Timestamp fechaEmision;
    private Date fechaCreacion;
    private BigDecimal subtotalSinImpuesto;
    private BigDecimal subtotalDoce;
    private BigDecimal subtotalCero;
    private BigDecimal valorIvaDoce;
    private BigDecimal valorIvaCero;
    private Long ivaSriId;
    private BigDecimal total;
    private Long usuarioId;
    private String estado;

    public Factura() {
    }
    
    @Id
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    @Column(name = "CLAVE_ACCESO")
    public String getClaveAcceso() {
        return claveAcceso;
    }

    @Column(name = "EMPRESA_ID")
    public Long getEmpresaId() {
        return empresaId;
    }

    @Column(name = "CLIENTE_ID")
    public Long getClienteId() {
        return clienteId;
    }

    @Column(name = "TIPO_IDENTIFICACION_ID")
    public Long getTipoClienteId() {
        return tipoClienteId;
    }
    
    @Column(name = "SECUENCIAL")
    public Integer getSecuencial() {
        return secuencial;
    }

    @Column(name = "PUNTO_ESTABLECIMIENTO")    
    public String getPuntoEstablecimiento() {
        return puntoEstablecimiento;
    }
    
    @Column(name = "PUNTO_EMISION")    
    public String getPuntoEmision() {
        return puntoEmision;
    }

    @Column(name = "FECHA_EMISION")       
    public Timestamp getFechaEmision() {
        return fechaEmision;
    }

    @Column(name = "FECHA_CREACION")       
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    @Column(name = "SUBTOTAL_SIN_IMPUESTOS")          
    public BigDecimal getSubtotalSinImpuesto() {
        return subtotalSinImpuesto;
    }

    @Column(name = "SUBTOTAL_DOCE")        
    public BigDecimal getSubtotalDoce() {
        return subtotalDoce;
    }

    @Column(name = "SUBTOTAL_CERO")        
    public BigDecimal getSubtotalCero() {
        return subtotalCero;
    }

    @Column(name = "VALOR_IVA_DOCE")     
    public BigDecimal getValorIvaDoce() {
        return valorIvaDoce;
        
    }
    
    @Column(name = "VALOR_IVA_CERO")    
    public BigDecimal getValorIvaCero() {
        return valorIvaCero;
    }

    @Column(name = "IVA_SRI_ID")    
    public Long getIvaSriId() {
        return ivaSriId;
    }

    @Column(name = "TOTAL")        
    public BigDecimal getTotal() {
        return total;
    }

    @Column(name = "USUARIO_ID")         
    public Long getUsuarioId() {
        return usuarioId;
    }

    @Column(name = "ESTADO")           
    public String getEstado() {
        return estado;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public void setTipoClienteId(Long tipoClienteId) {
        this.tipoClienteId = tipoClienteId;
    }

    public void setSecuencial(Integer secuencial) {
        this.secuencial = secuencial;
    }

    public void setPuntoEstablecimiento(String puntoEstablecimiento) {
        this.puntoEstablecimiento = puntoEstablecimiento;
    }

    public void setPuntoEmision(String puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    public void setFechaEmision(Timestamp fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setSubtotalSinImpuesto(BigDecimal subtotalSinImpuesto) {
        this.subtotalSinImpuesto = subtotalSinImpuesto;
    }

    public void setSubtotalDoce(BigDecimal subtotalDoce) {
        this.subtotalDoce = subtotalDoce;
    }

    public void setSubtotalCero(BigDecimal subtotalCero) {
        this.subtotalCero = subtotalCero;
    }

    public void setValorIvaDoce(BigDecimal valorIvaDoce) {
        this.valorIvaDoce = valorIvaDoce;
    }

    public void setValorIvaCero(BigDecimal valorIvaCero) {
        this.valorIvaCero = valorIvaCero;
    }

    public void setIvaSriId(Long ivaSriId) {
        this.ivaSriId = ivaSriId;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
