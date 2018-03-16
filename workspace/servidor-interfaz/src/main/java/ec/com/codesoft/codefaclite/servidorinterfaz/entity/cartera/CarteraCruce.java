/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "CARTERA_CRUCE")
public class CarteraCruce implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "FECHA_CRUCE")
    private Date fechaCruce;
    
    @Column(name = "VALOR")
    private BigDecimal valor;
    
    @JoinColumn(name = "CARTERA_AFECTA_ID")
    private Cartera carteraAfectada;
    
    @JoinColumn(name = "CARTERA_DETALLE_ID")
    private CarteraDetalle carteraDetalle;
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaCruce() {
        return fechaCruce;
    }

    public void setFechaCruce(Date fechaCruce) {
        this.fechaCruce = fechaCruce;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Cartera getCarteraAfectada() {
        return carteraAfectada;
    }

    public void setCarteraAfectada(Cartera carteraAfectada) {
        this.carteraAfectada = carteraAfectada;
    }

    public CarteraDetalle getCarteraDetalle() {
        return carteraDetalle;
    }

    public void setCarteraDetalle(CarteraDetalle carteraDetalle) {
        this.carteraDetalle = carteraDetalle;
    }
    
    
}
