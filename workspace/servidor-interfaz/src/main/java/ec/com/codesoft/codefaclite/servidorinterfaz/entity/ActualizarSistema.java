/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "ACTUALIZAR_SISTEMA")
public class ActualizarSistema implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @Column(name = "NOMBRE_METODO")
    private String nombreMetodo;
    
    @Column(name = "VERSION")
    private String version;
    
    @Column(name = "CAMBIO_ACTUALIZADO")
    private String cambioActualizado;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "FECHA_EJECUCION")
    private Date fechaEjecucion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombreMetodo() {
        return nombreMetodo;
    }

    public void setNombreMetodo(String nombreMetodo) {
        this.nombreMetodo = nombreMetodo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCambioActualizado() {
        return cambioActualizado;
    }

    public void setCambioActualizado(String cambioActualizado) {
        this.cambioActualizado = cambioActualizado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public EnumSiNo getCambioActualizadoEnum() {
        return EnumSiNo.getEnumByLetra(cambioActualizado);
    }

    public void setCambioActualizadoEnum(EnumSiNo cambioActualizado) {
        this.cambioActualizado = cambioActualizado.getLetra();
    }

    public Date getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(Date fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final ActualizarSistema other = (ActualizarSistema) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
}
