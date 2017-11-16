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
@Table(name = "FACTURA_DETALLE")
public class FacturaDetalle {
    private Long id;
    private Long productoId;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal descuento;
    private BigDecimal valorIce;

    public FacturaDetalle() {
    }
    
    @Id
    @Column(name = "ID")    
    public Long getId() {
        return id;
    }

    @Column(name = "PRODUCTO_ID")    
    public Long getProductoId() {
        return productoId;
    }

    @Column(name = "CANTIDAD")    
    public Integer getCantidad() {
        return cantidad;
    }

    @Column(name = "PRECIO_UNITARIO")   
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    @Column(name = "DESCUENTO")       
    public BigDecimal getDescuento() {
        return descuento;
    }

    @Column(name = "VALOR_ICE")           
    public BigDecimal getValorIce() {
        return valorIce;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public void setValorIce(BigDecimal valorIce) {
        this.valorIce = valorIce;
    }
    
}
