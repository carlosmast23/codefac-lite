/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ClienteEnumEstado;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "PRODUCTO_PROVEEDOR")
public class ProductoProveedor implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column (name = "ID")
    private Long  id;
    
    @Column (name = "ESTADO")
    private String estado;
    
    @Column (name = "DESCRIPCION")
    private String descripcion;
    
    @Column (name = "CON_IVA")
    private String conIva;
    
    @Column (name = "COSTO_ACTUAL")
    private BigDecimal costo;
    
    @JoinColumn(name = "PRODUCTO_ID")
    private Producto producto;
    
    @JoinColumn(name = "PROVEEDOR_ID")
    private Persona proveedor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getConIva() {
        return conIva;
    }

    public void setConIva(String conIva) {
        this.conIva = conIva;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Persona getProveedor() {
        return proveedor;
    }

    public void setProveedor(Persona proveedor) {
        this.proveedor = proveedor;
    }
     
    
}
