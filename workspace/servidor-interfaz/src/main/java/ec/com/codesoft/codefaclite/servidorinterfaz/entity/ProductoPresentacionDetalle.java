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
@Table(name = "PRODUCTO_PRESENTACION_DETALLE")
public class ProductoPresentacionDetalle implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "CANTIDAD")
    private BigDecimal cantidad;
    
    @JoinColumn(name = "PRESENTACION_PRODUCTO_ID")
    private PresentacionProducto presentacionProducto;
    
    @JoinColumn(name = "PRODUCTO_ORIGINAL_ID")
    private Producto productoOriginal;
    
    @JoinColumn(name = "PRODUCTO_EMPAQUETADO_ID")
    private Producto productoEmpaquetado;
    

    public ProductoPresentacionDetalle() 
    {
        
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProductoOriginal() {
        return productoOriginal;
    }

    public void setProductoOriginal(Producto productoOriginal) {
        this.productoOriginal = productoOriginal;
    }

    public Producto getProductoEmpaquetado() {
        return productoEmpaquetado;
    }

    public void setProductoEmpaquetado(Producto productoEmpaquetado) {
        this.productoEmpaquetado = productoEmpaquetado;
    }

    public PresentacionProducto getPresentacionProducto() {
        return presentacionProducto;
    }

    public void setPresentacionProducto(PresentacionProducto presentacionProducto) {
        this.presentacionProducto = presentacionProducto;
    }

    
    
    
}
