/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ArqueoCajaEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    * Foreing Key
    */
    @JoinColumn(name = "CAJA_SESSION_ID")
    @ManyToOne
    private CajaSession cajaSession;
    
    @JoinColumn(name = "USUARIO_ID")
    @ManyToOne
    private Usuario usuario;
    
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

    public ArqueoCajaEnum getEstadoEnum() {
        return ArqueoCajaEnum.getEnum(estado);
    }

    public void setEstadoEnum(ArqueoCajaEnum estadoEnum) {
        if(estadoEnum==null)
        {
            this.estado=null;
        }
        else
        {
            this.estado = estadoEnum.getEstado();
        }
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public CajaSession getCajaSession() {
        return cajaSession;
    }

    public void setCajaSession(CajaSession cajaSession) {
        this.cajaSession = cajaSession;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
