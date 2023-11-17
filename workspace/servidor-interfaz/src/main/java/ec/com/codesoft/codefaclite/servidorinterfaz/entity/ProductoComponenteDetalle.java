/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author CARLOS_CODESOFT
 */
@Entity
@Table(name = "PRODUCTO_COMPONENTE_DETALLE")
public class ProductoComponenteDetalle implements Serializable{
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JoinColumn(name = "PRODUCTO_COMPONENTE_ID")
    private ProductoComponente productoComponente;
    
    @JoinColumn(name = "PRODUCTO_ID")
    private Producto producto;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductoComponente getProductoComponente() {
        return productoComponente;
    }

    public void setProductoComponente(ProductoComponente productoComponente) {
        this.productoComponente = productoComponente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return productoComponente.getNombre();
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
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
        final ProductoComponenteDetalle other = (ProductoComponenteDetalle) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
