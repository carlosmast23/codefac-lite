/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
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
@Table(name = "DESCUENTO_PRODUCTO_DETALLE")
public class DescuentoProductoDetalle implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID")
    private Long  id;
    
    @JoinColumn (name = "DESCUENTO_ID")
    private Descuento descuento;
    
    @JoinColumn (name = "PRODUCTO_ID")
    private Producto producto;

    public DescuentoProductoDetalle() 
    {
        
    }

    public DescuentoProductoDetalle(Descuento descuento, Producto producto) {
        this.descuento = descuento;
        this.producto = producto;
    }
    
    
    
    ///////////////////////////////////////////////////////////////////
    ///                     GET AND SET
    //////////////////////////////////////////////////////////////////

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Descuento getDescuento() {
        return descuento;
    }

    public void setDescuento(Descuento descuento) {
        this.descuento = descuento;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    
    
}
