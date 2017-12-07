/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.entity;

import ec.com.codesoft.ejemplo.utilidades.texto.UtilidadesTextos;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
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
@Table(name = "FACTURA")
public class Factura {    
    public static final String ESTADO_FACTURADO="F";
    public static final String ESTADO_ANULADO="A";
    public static final String ESTADO_PENDIENTE_FACTURA_ELECTRONICA="P";
    
    
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
    private Date fechaFactura;
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

    @JoinColumn(name = "CLIENTE_ID")
    @ManyToOne    
    private Persona cliente;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factura")
    private List<FacturaDetalle> detalles;

    public Factura() {
    }
    
    public Long getId() {
        return id;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public Long getTipoClienteId() {
        return tipoClienteId;
    }
    
    public Integer getSecuencial() {
        return secuencial;
    }

    public String getPuntoEstablecimiento() {
        return puntoEstablecimiento;
    }
    
    public String getPuntoEmision() {
        return puntoEmision;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public BigDecimal getSubtotalSinImpuesto() {
        return subtotalSinImpuesto;
    }

    public BigDecimal getSubtotalDoce() {
        return subtotalDoce;
    }

    public BigDecimal getSubtotalCero() {
        return subtotalCero;
    }

    public BigDecimal getValorIvaDoce() {
        return valorIvaDoce;
        
    }
    
    public BigDecimal getValorIvaCero() {
        return valorIvaCero;
    }

    public Long getIvaSriId() {
        return ivaSriId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getEstado() {
        return estado;
    }

    public Persona getCliente() {
        return cliente;
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

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
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

    public void setCliente(Persona cliente) {
        this.cliente = cliente;
    }

    public List<FacturaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<FacturaDetalle> detalles) {
        this.detalles = detalles;
    }
    
    /**
     * Informacion adicional
     */
    public void addDetalle(FacturaDetalle detalle)
    {
        if(this.detalles==null)
        {
            this.detalles=new ArrayList<FacturaDetalle>();
        }
        detalle.setFactura(this);
        this.detalles.add(detalle);
        
    }
    
    public String getPreimpreso()
    {
       return UtilidadesTextos.llenarCarateresIzquierda(puntoEmision,3,"0")+"-"+UtilidadesTextos.llenarCarateresIzquierda(puntoEstablecimiento,3,"0")+"-"+UtilidadesTextos.llenarCarateresIzquierda(secuencial+"",9,"0");
    }
    
    
}
