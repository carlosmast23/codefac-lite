/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

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
    
    @JoinColumn(name = "REEMBOLSO_DETALLE_ID")
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
