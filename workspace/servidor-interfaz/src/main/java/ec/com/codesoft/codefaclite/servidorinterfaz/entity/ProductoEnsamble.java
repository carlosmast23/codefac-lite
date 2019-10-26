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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "PRODUCTO_ENSAMBLE")
public class ProductoEnsamble implements Serializable{
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    
    /**
     * Producto que es parte del ensamble
     */
    @JoinColumn(name = "COMPONENTE_ENSAMBLE")
    @ManyToOne
    private Producto componenteEnsamble;    
    
    /**
     * Producto padre que es el ensamble
     */
    @JoinColumn(name = "PRODUCTO_ENSAMBLE")
    @ManyToOne
    private Producto productoEnsamble;

    public ProductoEnsamble() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getComponenteEnsamble() {
        return componenteEnsamble;
    }

    public void setComponenteEnsamble(Producto componenteEnsamble) {
        this.componenteEnsamble = componenteEnsamble;
    }

    public Producto getProductoEnsamble() {
        return productoEnsamble;
    }

    public void setProductoEnsamble(Producto productoEnsamble) {
        this.productoEnsamble = productoEnsamble;
    }
    
    public enum EnsambleAccionEnum {
        /**
         * Enumerador que identifica que fue creado por un usuario
         */
        AGREGAR,
        /**
         * Enumerador que identifica que fue construido durante una venta
         */
        CONSTRUIR_FACTURA,
        /**
         * Enumardo que indica que se quito el ensamble por un usuario
         */
        QUITAR
    }
    
}
