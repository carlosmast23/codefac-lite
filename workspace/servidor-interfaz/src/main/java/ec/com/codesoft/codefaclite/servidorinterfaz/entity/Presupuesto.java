/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FechaFormatoEnum;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
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
    
    @Column(name = "CODIGO")
    private String codigo;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @Column(name = "FECHA_INGRESO")
    private Date fechaIngreso;
    
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

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
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
    
    public void addDetalle(PresupuestoDetalle detalle)
    {
        if(this.presupuestoDetalles == null)
        {
            this.presupuestoDetalles = new ArrayList<>();
        }
        detalle.setPresupuesto(this);
        this.presupuestoDetalles.add(detalle);
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

    public Object getDetalles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
