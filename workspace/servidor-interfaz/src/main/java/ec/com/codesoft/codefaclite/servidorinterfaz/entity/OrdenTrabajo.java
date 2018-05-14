/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author CodesoftDesarrollo 1
 */
@Entity
@Table(name = "ORDEN_TRABAJO")
public class OrdenTrabajo implements Serializable
{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODIGO")
    private String codigo;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @Column(name = "FECHA_INGRESO")
    private Date fechaIngreso;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ordenTrabajo",fetch = FetchType.EAGER)
    private List<OrdenTrabajoDetalle> detalles;

    public List<OrdenTrabajoDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<OrdenTrabajoDetalle> detalles) {
        this.detalles = detalles;
    }
    
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
    
    
}
