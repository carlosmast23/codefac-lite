/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "IMPUESTO_DETALLE")
@XmlRootElement
public class ImpuestoDetalle implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID_IMPUESTO_DETALLE")
    private Integer idImpuestoDetalle;
    
    @Column (name= "CODIGO")
    private Integer codigo;
    
    @Column (name= "NOMBRE")
    private String nombre;
    
    @Column (name= "PORCENTAJE")
    private BigDecimal porcentaje;
    
    @Column (name = "TARIFA")
    private Integer tarifa;
    
    @Column (name= "DESCRIPCION")
    private String descripcion;
    
    @Column (name= "FECHA_INICIO")
    private Date fechaInicio;
    
    @Column (name= "FECHA_FIN")
    private Date fechaFin;
        
    //@ManyToOne
    @JoinColumn(name="ID_IMPUESTO")
    @ManyToOne(optional = false)
    private Impuesto impuesto;
    
    
    public Integer getIdImpuestoDetalle() {
        return idImpuestoDetalle;
    }

    public void setIdImpuestoDetalle(Integer idImpuestoDetalle) {
        this.idImpuestoDetalle = idImpuestoDetalle;
    }

    public Impuesto getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Impuesto impuesto) {
        this.impuesto = impuesto;
    }

    

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    
    public Integer getTarifa() {
        return tarifa;
    }

    public void setTarifa(Integer tarifa) {
        this.tarifa = tarifa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String toString() {
        return codigo+" - "+nombre;
    }
    
    
    
    
}
