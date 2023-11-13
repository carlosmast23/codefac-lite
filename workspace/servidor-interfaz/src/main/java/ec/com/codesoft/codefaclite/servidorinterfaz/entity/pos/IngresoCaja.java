/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.SignoEnum;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "INGRESO_CAJA")
@XmlRootElement
public class IngresoCaja implements Serializable
{
    /*
    * Atributos
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "VALOR")
    private BigDecimal valor;
    
    /*
     * Constructor
     */
    public IngresoCaja() {
    }

    /*
    * Foreing Key
    */
    @JoinColumn(name = "CAJA_SESSION_ID")
    @ManyToOne
    private CajaSession cajaSession;
    
    @JoinColumn(name = "FACTURA_ID")
    @ManyToOne
    private Factura factura;
    
    @JoinColumn(name = "COMPRA_ID")
    @ManyToOne
    private Compra compra;
    
    @Column(name = "SIGNO")    
    private Integer signoIngreso;
    
    /*
    * Get and Set
    */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public CajaSession getCajaSession() {
        return cajaSession;
    }

    public void setCajaSession(CajaSession cajaSession) {
        this.cajaSession = cajaSession;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Integer getSignoIngreso() {
        return signoIngreso;
    }

    public void setSignoIngreso(Integer signoIngreso) {
        this.signoIngreso = signoIngreso;
    }
    
    public SignoEnum getSignoIngresoEnum() {
        return SignoEnum.consultarPorValor(signoIngreso);
    }

    public void setSignoIngresoEnum(SignoEnum signoIngresoEnum) {
        this.signoIngreso = signoIngresoEnum.getValor();
    }
    
    
   
    
    
    /*
    * Equals
    */

    @Override
    public int hashCode() {
        int hash = 3;
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
        final IngresoCaja other = (IngresoCaja) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
