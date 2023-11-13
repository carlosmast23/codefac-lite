/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import java.util.Objects;
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
@Table(name = "DESCUENTO_PRODUCTO_DETALLE")
public class DescuentoProductoDetalle implements Serializable
{
    public static Long INDICE_TMP=-1l;
    
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
        this.id=INDICE_TMP--;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final DescuentoProductoDetalle other = (DescuentoProductoDetalle) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }


    
    
    
    
}
