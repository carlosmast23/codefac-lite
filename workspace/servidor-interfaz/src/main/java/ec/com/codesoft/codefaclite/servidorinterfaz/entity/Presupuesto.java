/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FechaFormatoEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author CodesoftDesarrollo 1
 */
@Entity
@Table(name = "PRESUPUESTO")
@XmlRootElement
public class Presupuesto implements Serializable
{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "EMPRESA_ID")
    private Long empresaId;
    
    @Column(name = "USUARIO_ID")
    private Long usuarioId;
    
    @Column(name = "CODIGO")
    private String codigo;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    
    @Column(name = "ESTADO")
    private String estado;
    
    //Todo: Cambiar el nombre por fecha creacion , para saber que es la fecha cuando se creo
    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;
    
    @Column(name = "FECHA_PRESUPUESTO")
    private Date fechaPresupuesto;    
    
    /**
     * Fecha que establece hasta cuando es valido el presupuesto
     */
    @Column(name = "FECHA_VALIDEZ")
    private Date fechaValidez;
    
    @Column(name = "DESCUENTO_COMPRA")
    private BigDecimal descuentoCompra;

    @Column(name = "DESCUENTO_VENTA")
    private BigDecimal descuentoVenta;

    @Column(name = "TOTAL_COMPRA")
    private BigDecimal totalCompra;

    @Column(name = "TOTAL_VENTA")
    private BigDecimal totalVenta;            
            
    
    @JoinColumn(name = "ORDEN_TRABAJO_DETALLE_ID")
    @OneToOne
    private OrdenTrabajoDetalle ordenTrabajoDetalle;
    
    @JoinColumn(name = "CLIENTE_ID")
    @ManyToOne
    private Persona persona;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "presupuesto", fetch = FetchType.EAGER)
    private List<PresupuestoDetalle>  presupuestoDetalles;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescipcion() {
        return descripcion;
    }

    public void setDescipcion(String descipcion) {
        this.descripcion = descipcion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    
    
    public OrdenTrabajoDetalle getOrdenTrabajoDetalle() {
        return ordenTrabajoDetalle;
    }

    public void setOrdenTrabajoDetalle(OrdenTrabajoDetalle ordenTrabajoDetalle) {
        this.ordenTrabajoDetalle = ordenTrabajoDetalle;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<PresupuestoDetalle> getPresupuestoDetalles() {
        return presupuestoDetalles;
    }

    public void setPresupuestoDetalles(List<PresupuestoDetalle> presupuestoDetalles) {
        this.presupuestoDetalles = presupuestoDetalles;
    }

    public Date getFechaPresupuesto() {
        return fechaPresupuesto;
    }

    public void setFechaPresupuesto(Date fechaPresupuesto) {
        this.fechaPresupuesto = fechaPresupuesto;
    }

    public Date getFechaValidez() {
        return fechaValidez;
    }

    public void setFechaValidez(Date fechaValidez) {
        this.fechaValidez = fechaValidez;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public BigDecimal getDescuentoCompra() {
        return descuentoCompra;
    }

    public void setDescuentoCompra(BigDecimal descuentoCompra) {
        this.descuentoCompra = descuentoCompra;
    }

    public BigDecimal getDescuentoVenta() {
        return descuentoVenta;
    }

    public void setDescuentoVenta(BigDecimal descuentoVenta) {
        this.descuentoVenta = descuentoVenta;
    }

    public BigDecimal getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(BigDecimal totalCompra) {
        this.totalCompra = totalCompra;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }
    
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.codigo);
        hash = 47 * hash + Objects.hashCode(this.descripcion);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Presupuesto other = (Presupuesto) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }    
}
