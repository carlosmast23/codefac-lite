/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos;

import java.io.Serializable;
import java.sql.Time;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.Fetch;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Robert
 */
@Entity
@Table(name = "TURNO")
@XmlRootElement
public class Turno implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "HORA_INICIAL")
    private Time horaInicial;
    
    @Column(name = "HORA_FINAL")
    private Time horaFinal;
    
    @Column(name = "CANTIDAD_DIAS")
    private Integer cantidadDias;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "turno", fetch = FetchType.EAGER)
    private List<TurnoAsignado> turnoAsignadoList;

    public Turno() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Time getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(Time horaInicial) {
        this.horaInicial = horaInicial;
    }

    public Time getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Time horaFinal) {
        this.horaFinal = horaFinal;
    }

    public List<TurnoAsignado> getTurnoAsignadoList() {
        return turnoAsignadoList;
    }

    public void setTurnoAsignadoList(List<TurnoAsignado> turnoAsignadoList) {
        this.turnoAsignadoList = turnoAsignadoList;
    }

    public Integer getCantidadDias() {
        return cantidadDias;
    }

    public void setCantidadDias(Integer cantidadDias) {
        this.cantidadDias = cantidadDias;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.id);
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
        final Turno other = (Turno) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Turno{" + "nombre=" + nombre + ", horaInicial=" + horaInicial + ", horaFinal=" + horaFinal + '}';
    }

}
