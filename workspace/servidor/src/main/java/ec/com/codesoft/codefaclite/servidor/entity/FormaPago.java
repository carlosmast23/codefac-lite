/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "FORMA_PAGO_FACTURA")
public class FormaPago {
    
    private Long id;
    private Long formaPagoId;
    private BigDecimal total;
    private Integer plazo;
    private String unidadTiempo;

    public FormaPago() {
    }
    
    
    @Id
    @Column(name = "ID")        
    public Long getId() {
        return id;
    }
    
    @Column(name = "FORMA_PAGO_ID")  
    public Long getFormaPagoId() {
        return formaPagoId;
    }

    @Column(name = "TOTAL")      
    public BigDecimal getTotal() {
        return total;
    }

    @Column(name = "PLAZO")      
    public Integer getPlazo() {
        return plazo;
    }

    @Column(name = "UNIDAD_TIEMPO")      
    public String getUnidadTiempo() {
        return unidadTiempo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFormaPagoId(Long formaPagoId) {
        this.formaPagoId = formaPagoId;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
    }

    public void setUnidadTiempo(String unidadTiempo) {
        this.unidadTiempo = unidadTiempo;
    }

    
}
