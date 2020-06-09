/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Robert
 */
@Entity
@Table(name = "ARQUEO_CAJA")
@XmlRootElement
public class ArqueoCaja implements Serializable
{
    /*
    * Atributos
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "FECHA_HORA")
    private Timestamp fechaHora;
    @Column(name = "VALOR_TEORICO")
    private String valorTeorico;
    @Column(name = "VALOR_FISICO")
    private BigDecimal valorFisico;
    @Column(name = "ESTADO")
    private String estado;
    
    /*
    * Constructor
    */

    public ArqueoCaja() {
    }

    /*
     * Get and Set
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getValorTeorico() {
        return valorTeorico;
    }

    public void setValorTeorico(String valorTeorico) {
        this.valorTeorico = valorTeorico;
    }

    public BigDecimal getValorFisico() {
        return valorFisico;
    }

    public void setValorFisico(BigDecimal valorFisico) {
        this.valorFisico = valorFisico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    /*
    * Equals
    */

    @Override
    public int hashCode() {
        int hash = 7;
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
        final ArqueoCaja other = (ArqueoCaja) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
