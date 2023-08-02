/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
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
@Table(name = "REEMBOLSO_IMPUESTO_DETALLE")
public class RembolsoImpuestoDetalle implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")    
    private Long id;
    
    @JoinColumn(name = "IMPUESTO_ID")
    private Impuesto impuesto;
    
    @Column(name = "PORCENTAJE_IVA")
    private Integer porcentajeIva;
    
    @Column(name = "BASE_IMPONIBLE")
    private BigDecimal baseImponible;
    
    @JoinColumn(name = "REEMBOLSO_DETALLE")
    private ReembolsoDetalle rembolsoDetalle;

    public RembolsoImpuestoDetalle() 
    {
        
    }
    
    // --Metodos GET AND SET --    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Impuesto getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Impuesto impuesto) {
        this.impuesto = impuesto;
    }

    public Integer getPorcentajeIva() {
        return porcentajeIva;
    }

    public void setPorcentajeIva(Integer porcentajeIva) {
        this.porcentajeIva = porcentajeIva;
    }

    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }
    
    public ReembolsoDetalle getRembolsoDetalle() {
        return rembolsoDetalle;
    }

    public void setRembolsoDetalle(ReembolsoDetalle rembolsoDetalle) {
        this.rembolsoDetalle = rembolsoDetalle;
    }
    
    
    
}
