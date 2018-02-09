/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.ejemplo.utilidades.texto.UtilidadesTextos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Factura implements Serializable {    
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
    
    /**
     * Valor del descuento de los productos que cobran iva
     */
    @Column(name = "DESCUENTO_IVA")
    private BigDecimal descuentoImpuestos;
    /**
     * Valor del descuento de los productos que no cobran iva
     */    
    @Column(name = "DESCUENTO_IVA_CERO")
    private BigDecimal descuentoSinImpuestos;
    
    
    @Column(name = "SUBTOTAL_IVA")
    private BigDecimal subtotalImpuestos;
    @Column(name = "SUBTOTAL_IVA_CERO")
    private BigDecimal subtotalSinImpuestos;
    
    /**
     * Valor del iva cobrado
     */
    @Column(name = "IVA")
    private BigDecimal iva;

    @Column(name = "IVA_SRI_ID")
    private Long ivaSriId;
    @Column(name = "TOTAL")
    private BigDecimal total;
    @Column(name = "USUARIO_ID")
    private Long usuarioId;
    @Column(name = "ESTADO")
    private String estado;
   
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    @Column(name = "IDENTIFICACION")
    private String identificacion;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "TIPO_FACTURACION")
    private String tipoFacturacion;
    @Column(name = "CODIGO_DOCUMENTO")
    private String codigoDocumento;
    
    @JoinColumn(name = "CLIENTE_ID")
    @ManyToOne    
    private Persona cliente;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factura", fetch = FetchType.EAGER)
    private List<FacturaDetalle> detalles;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factura", fetch = FetchType.EAGER)
    private List<FormaPago> formaPagos;

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public BigDecimal getDescuentoImpuestos() {
        return descuentoImpuestos;
    }

    public void setDescuentoImpuestos(BigDecimal descuentoImpuestos) {
        this.descuentoImpuestos = descuentoImpuestos;
    }

    public BigDecimal getDescuentoSinImpuestos() {
        return descuentoSinImpuestos;
    }

    public void setDescuentoSinImpuestos(BigDecimal descuentoSinImpuestos) {
        this.descuentoSinImpuestos = descuentoSinImpuestos;
    }

    public BigDecimal getSubtotalImpuestos() {
        return subtotalImpuestos;
    }

    public void setSubtotalImpuestos(BigDecimal subtotalImpuestos) {
        this.subtotalImpuestos = subtotalImpuestos;
    }

    public BigDecimal getSubtotalSinImpuestos() {
        return subtotalSinImpuestos;
    }

    public void setSubtotalSinImpuestos(BigDecimal subtotalSinImpuestos) {
        this.subtotalSinImpuestos = subtotalSinImpuestos;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public List<FormaPago> getFormaPagos() {
        return formaPagos;
    }

    public void setFormaPagos(List<FormaPago> formaPagos) {
        this.formaPagos = formaPagos;
    }

    public String getTipoFacturacion() {
        return tipoFacturacion;
    }

    public void setTipoFacturacion(String tipoFacturacion) {
        this.tipoFacturacion = tipoFacturacion;
    }

    public String getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(String codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
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
    
     /**
     * Formas de pago adicional
     */
    public void addFormaPago(FormaPago formaPago)
    {
        if(this.formaPagos==null)
        {
            this.formaPagos=new ArrayList<FormaPago>();
        }
        formaPago.setFactura(this);
        this.formaPagos.add(formaPago);
        
    }
    
    public String getPreimpreso()
    {
       return UtilidadesTextos.llenarCarateresIzquierda(puntoEmision,3,"0")+"-"+UtilidadesTextos.llenarCarateresIzquierda(puntoEstablecimiento,3,"0")+"-"+UtilidadesTextos.llenarCarateresIzquierda(secuencial+"",9,"0");
    }
    
    
}
