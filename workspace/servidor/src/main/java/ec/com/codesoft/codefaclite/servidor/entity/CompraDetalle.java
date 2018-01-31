/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "COMPRA_DETALLE")
public class CompraDetalle {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(name = "PRODUCTO_ID")
    //private Long productoId;
    @Column(name = "CANTIDAD")
    private BigDecimal cantidad;
    @Column(name = "PRECIO_UNITARIO")
    private BigDecimal precioUnitario;
    @Column(name = "DESCUENTO")
    private BigDecimal descuento;
    @Column(name = "VALOR_ICE")
    private BigDecimal valorIce;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "TOTAL")
    private BigDecimal total;
    @Column(name = "IVA")
    private BigDecimal iva;
    
    @JoinColumn(name="COMPRA_ID")
    @ManyToOne(optional = false)
    private Compra compra;
    
    @JoinColumn(name = "PRODUCTO_PROVEEDOR_ID")
    @ManyToOne
    private ProductoProveedor productoProveedor;

    public CompraDetalle() {
    }
    
    public Long getId() {
        return id;
    }



    public BigDecimal getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public BigDecimal getValorIce() {
        return valorIce;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public void setCantidad(BigDecimal cantidad) {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public ProductoProveedor getProductoProveedor() {
        return productoProveedor;
    }

    public void setProductoProveedor(ProductoProveedor productoProveedor) {
        this.productoProveedor = productoProveedor;
    }
    
    
    /**
     * Metodos adicionales
     */
    /*
    public BigDecimal getIva()
    {
        return total.multiply(producto.getIva().getPorcentaje());
    }*/
    
    
    
}
