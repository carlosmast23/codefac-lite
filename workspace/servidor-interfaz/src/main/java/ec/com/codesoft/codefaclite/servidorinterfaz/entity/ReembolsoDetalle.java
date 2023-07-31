/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import static ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto.NOMBRE_CAMPO_ID;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author CARLOS_CODESOFT
 */

@Entity
@Table(name = "REEMBOLSO_DETALLE")
public class ReembolsoDetalle implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")    
    private Long id;
    
    @JoinColumn(name = "FACTURA_ID")
    private Factura factura;
    
    @JoinColumn(name = "PROVEEDOR_ID")
    private Persona proveedor;
    
    @Column(name = "PUNTO_ESTABLECIMIENTO")
    private Integer puntoEstablecimiento;
    
    @Column(name = "PUNTO_EMISION")
    private Integer puntoEmision;
    
    @Column(name = "SECUENCIAL")
    private Long secuencial;
    
    @Column(name = "FECHA_EMISION") 
    private Date fechaEmision;
    
    @Column(name = "NUMERO_AUTORIZACION") 
    private String numeroAutorizacion;
    
    @Column(name = "DESCRIPCION") 
    private String descripcion;
    
    
    private List<RembolsoImpuestoDetalle> detalleList;

    public ReembolsoDetalle() 
    {
        
    }    
    
    
    public void agregarImpuestoRembolso(RembolsoImpuestoDetalle rembolsoDetalle)
    {
        if(detalleList==null)
        {
            this.detalleList=new ArrayList<RembolsoImpuestoDetalle>();
        }
        
        this.detalleList.add(rembolsoDetalle);
        
    }
    
    public String generarTextoRembolso()
    {
        String mensaje=" Reembolso de la Factura #"+secuencial+" del proveedor "+proveedor.getNombreSimple();
        return mensaje;
    }
    
    // GET AND SET //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Persona getProveedor() {
        return proveedor;
    }

    public void setProveedor(Persona proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getPuntoEstablecimiento() {
        return puntoEstablecimiento;
    }

    public void setPuntoEstablecimiento(Integer puntoEstablecimiento) {
        this.puntoEstablecimiento = puntoEstablecimiento;
    }

    public Integer getPuntoEmision() {
        return puntoEmision;
    }

    public void setPuntoEmision(Integer puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    public Long getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(Long secuencial) {
        this.secuencial = secuencial;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<RembolsoImpuestoDetalle> getDetalleList() {
        return detalleList;
    }

    public void setDetalleList(List<RembolsoImpuestoDetalle> detalleList) {
        this.detalleList = detalleList;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }
    
}
