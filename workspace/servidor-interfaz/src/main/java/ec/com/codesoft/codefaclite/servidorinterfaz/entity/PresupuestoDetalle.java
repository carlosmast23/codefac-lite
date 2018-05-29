/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author CodesoftDesarrollo 1
 */
@Entity
@Table(name = "PRESUPUESTO_DETALLE")
@XmlRootElement
public class PresupuestoDetalle implements Serializable
{   
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)        
    private Long id; 
    
    @Column(name = "PRECIO_COMPRA")
    private BigDecimal precioCompra;
            
    @Column(name = "DESCUENTO_COMPRA")
    private BigDecimal descuentoCompra;
    
    @Column(name = "PRECIO_VENTA")
    private BigDecimal precioVenta;

    @Column(name = "DESCUENTO_VENTA")
    private BigDecimal descuentoVenta;
    
    @Column(name = "CANTIDAD")
    private BigDecimal cantidad;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @JoinColumn(name = "PROVEEDOR_ID")
    @ManyToOne
    private Persona persona;
    
    @JoinColumn(name = "PRODUCTO_ID")
    @ManyToOne
    private Producto producto;    

    @JoinColumn(name = "PRESUPUESTO_ID")
    @ManyToOne    
    private Presupuesto presupuesto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public BigDecimal getDescuentoCompra() {
        return descuentoCompra;
    }

    public void setDescuentoCompra(BigDecimal descuentoCompra) {
        this.descuentoCompra = descuentoCompra;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getDescuentoVenta() {
        return descuentoVenta;
    }

    public void setDescuentoVenta(BigDecimal descuentoVenta) {
        this.descuentoVenta = descuentoVenta;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
  
}