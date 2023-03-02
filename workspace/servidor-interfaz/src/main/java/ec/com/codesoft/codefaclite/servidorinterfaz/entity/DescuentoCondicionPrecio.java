/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "DESCUENTO_CONDICION_PRECIO")
public class DescuentoCondicionPrecio implements Serializable{
    
    private static Long SECUENCIAL_TMP=-11l;
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    
    @JoinColumn (name = "DESCUENTO_ID")
    private Descuento descuento;
    
    @Column (name = "NUMERO_PRECIO")
    private Integer numeroPrecio;
    
    @Column (name = "PORCENTAJE_DESCUENTO")
    private BigDecimal porcentajeDescuento;

    public DescuentoCondicionPrecio() {
        this.id=SECUENCIAL_TMP--;
    }

    public DescuentoCondicionPrecio(Descuento descuento) {
        this.descuento = descuento;
        this.id=SECUENCIAL_TMP--;
    }
    

    public Descuento getDescuento() {
        return descuento;
    }

    public void setDescuento(Descuento descuento) {
        this.descuento = descuento;
    }

    public Integer getNumeroPrecio() {
        return numeroPrecio;
    }

    public void setNumeroPrecio(Integer numeroPrecio) {
        this.numeroPrecio = numeroPrecio;
    }

    public BigDecimal getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(BigDecimal porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final DescuentoCondicionPrecio other = (DescuentoCondicionPrecio) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
