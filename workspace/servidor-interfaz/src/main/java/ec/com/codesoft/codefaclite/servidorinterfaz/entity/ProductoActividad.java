/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

/**
 *
 * @author CARLOS_CODESOFT
 */
@Entity
@Table(name = "PRODUCTO_ACTIVIDAD")
public class ProductoActividad extends EntityAbstract<GeneralEnumEstado>{
    
    @Column (name = "NOMBRE")
    private String nombre;
    
    @Column (name = "DESCRIPCION")
    private String descripcion;
    
    @Column (name = "ORDEN")
    private Integer orden;
    
    @JoinColumn(name = "PRODUCTO_ID")
    private Producto producto;

    public ProductoActividad() {
    }
    
    
    //Metodos GET AND SET

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
}
