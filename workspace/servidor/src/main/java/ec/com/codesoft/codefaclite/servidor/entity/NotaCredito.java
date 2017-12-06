/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "NOTA_CREDITO")
public class NotaCredito {
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "CLAVE_ACCESO")
    private String claveAcceso;
    @Column(name = "EMPRESA_ID")
    private Long empresaId;
    @Column(name = "TIPO_IDENTIFICACION_ID")
    private Long tipoClienteId;
    @Column(name = "SECUENCIAL")
    private Integer secuencial;
    @Column(name = "PUNTO_ESTABLECIMIENTO")
    private String puntoEstablecimiento;
    @Column(name = "PUNTO_EMISION")
    private String puntoEmision;
    @Column(name = "FECHA_FACTURA")
    private Date fechaNotaCredito;
    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;
    @Column(name = "SUBTOTAL_SIN_IMPUESTOS")
    private BigDecimal subtotalSinImpuesto;
    @Column(name = "SUBTOTAL_DOCE")
    private BigDecimal subtotalDoce;
    @Column(name = "SUBTOTAL_CERO")
    private BigDecimal subtotalCero;
    @Column(name = "VALOR_IVA_DOCE")
    private BigDecimal valorIvaDoce;
    @Column(name = "VALOR_IVA_CERO")
    private BigDecimal valorIvaCero;
    @Column(name = "IVA_SRI_ID")
    private Long ivaSriId;
    @Column(name = "TOTAL")
    private BigDecimal total;
    @Column(name = "USUARIO_ID")
    private Long usuarioId;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "RAZON_MODIFICADO")
    private String razonModificado;
    
    @JoinColumn(name = "FACTURA_ID")
    @ManyToOne    
    private Factura factura;


    @JoinColumn(name = "CLIENTE_ID")
    @ManyToOne    
    private Persona cliente;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "notaCredito")
    private List<NotaCreditoDetalle> detalles;

    public NotaCredito() {
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public Long getTipoClienteId() {
        return tipoClienteId;
    }

    public void setTipoClienteId(Long tipoClienteId) {
        this.tipoClienteId = tipoClienteId;
    }

    public Integer getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(Integer secuencial) {
        this.secuencial = secuencial;
    }

    public String getPuntoEstablecimiento() {
        return puntoEstablecimiento;
    }

    public void setPuntoEstablecimiento(String puntoEstablecimiento) {
        this.puntoEstablecimiento = puntoEstablecimiento;
    }

    public String getPuntoEmision() {
        return puntoEmision;
    }

    public void setPuntoEmision(String puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    public Date getFechaNotaCredito() {
        return fechaNotaCredito;
    }

    public void setFechaNotaCredito(Date fechaNotaCredito) {
        this.fechaNotaCredito = fechaNotaCredito;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getSubtotalSinImpuesto() {
        return subtotalSinImpuesto;
    }

    public void setSubtotalSinImpuesto(BigDecimal subtotalSinImpuesto) {
        this.subtotalSinImpuesto = subtotalSinImpuesto;
    }

    public BigDecimal getSubtotalDoce() {
        return subtotalDoce;
    }

    public void setSubtotalDoce(BigDecimal subtotalDoce) {
        this.subtotalDoce = subtotalDoce;
    }

    public BigDecimal getSubtotalCero() {
        return subtotalCero;
    }

    public void setSubtotalCero(BigDecimal subtotalCero) {
        this.subtotalCero = subtotalCero;
    }

    public BigDecimal getValorIvaDoce() {
        return valorIvaDoce;
    }

    public void setValorIvaDoce(BigDecimal valorIvaDoce) {
        this.valorIvaDoce = valorIvaDoce;
    }

    public BigDecimal getValorIvaCero() {
        return valorIvaCero;
    }

    public void setValorIvaCero(BigDecimal valorIvaCero) {
        this.valorIvaCero = valorIvaCero;
    }

    public Long getIvaSriId() {
        return ivaSriId;
    }

    public void setIvaSriId(Long ivaSriId) {
        this.ivaSriId = ivaSriId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Persona getCliente() {
        return cliente;
    }

    public void setCliente(Persona cliente) {
        this.cliente = cliente;
    }

    public List<NotaCreditoDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<NotaCreditoDetalle> detalles) {
        this.detalles = detalles;
    }

    public String getRazonModificado() {
        return razonModificado;
    }

    public void setRazonModificado(String razonModificado) {
        this.razonModificado = razonModificado;
    }
    
        /**
     * Informacion adicional
     */
    public void addDetalle(NotaCreditoDetalle detalle)
    {
        if(this.detalles==null)
        {
            this.detalles=new ArrayList<NotaCreditoDetalle>();
        }
        detalle.setNotaCredito(this);
        this.detalles.add(detalle);
        
    }
    
    
    
}
