/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author CARLOS_CODESOFT
 */
@Entity
@Table(name = "MANTENIMIENTO_INFORME_DETALLE")
public class MantenimientoInformeDetalle implements Serializable{
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "CODIGO_TIPO_NO_CONFORMIDAD")
    private String codigoTipoNoConformidad;
    
    @Column(name = "PARTE_VEHICULO")
    private String parteVehiculo;
    
    @JoinColumn(name = "MANTENIMIENTO_TAREA_DETALLE_ID")
    private MantenimientoTareaDetalle mantenimientoTareaDetalle;

    public MantenimientoInformeDetalle() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoTipoNoConformidad() {
        return codigoTipoNoConformidad;
    }

    public void setCodigoTipoNoConformidad(String codigoTipoNoConformidad) {
        this.codigoTipoNoConformidad = codigoTipoNoConformidad;
    }

    public String getParteVehiculo() {
        return parteVehiculo;
    }

    public void setParteVehiculo(String parteVehiculo) {
        this.parteVehiculo = parteVehiculo;
    }

    public MantenimientoTareaDetalle getMantenimientoTareaDetalle() {
        return mantenimientoTareaDetalle;
    }

    public void setMantenimientoTareaDetalle(MantenimientoTareaDetalle mantenimientoTareaDetalle) {
        this.mantenimientoTareaDetalle = mantenimientoTareaDetalle;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final MantenimientoInformeDetalle other = (MantenimientoInformeDetalle) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    

}
