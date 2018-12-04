/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.io.Serializable;
import java.util.Objects;
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
 * @author Carlos
 */
@Entity
@Table(name = "PUNTO_EMISION")
public class PuntoEmision implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "PUNTO_EMISION")
    private Integer puntoEmision;
    
    @Column(name = "TIPO_FACTURACION")
    private String tipoFacturacion;
    
    @Column(name = "ESTADO")
    private String  estado;
    
    @Column(name = "SECUENCIAL_FACTURA")
    private Integer secuencialFactura;
    
    @Column(name = "SECUENCIAL_NOTA_CREDITO")
    private Integer secuencialNotaCredito;
    
    @Column(name = "SECUENCIAL_NOTA_DEBITO")
    private Integer secuencialNotaDebito;
    
    @Column(name = "SECUENCIAL_GUIA_REMISION")
    private Integer secuencialGuiaRemision;
    
    @Column(name = "SECUENCIAL_RETENCIONES")
    private Integer secuencialRetenciones;
    
    @Column(name = "SECUENCIAL_NOTA_VENTA")
    private Integer secuencialNotaVenta;
    
    @JoinColumn(name = "SUCURSAL_ID")
    private Sucursal sucursal;

    public PuntoEmision() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPuntoEmision() {
        return puntoEmision;
    }

    public void setPuntoEmision(Integer puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    public String getTipoFacturacion() {
        return tipoFacturacion;
    }

    public void setTipoFacturacion(String tipoFacturacion) {
        this.tipoFacturacion = tipoFacturacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getSecuencialFactura() {
        return secuencialFactura;
    }

    public void setSecuencialFactura(Integer secuencialFactura) {
        this.secuencialFactura = secuencialFactura;
    }

    public Integer getSecuencialNotaCredito() {
        return secuencialNotaCredito;
    }

    public void setSecuencialNotaCredito(Integer secuencialNotaCredito) {
        this.secuencialNotaCredito = secuencialNotaCredito;
    }

    public Integer getSecuencialNotaDebito() {
        return secuencialNotaDebito;
    }

    public void setSecuencialNotaDebito(Integer secuencialNotaDebito) {
        this.secuencialNotaDebito = secuencialNotaDebito;
    }

    public Integer getSecuencialGuiaRemision() {
        return secuencialGuiaRemision;
    }

    public void setSecuencialGuiaRemision(Integer secuencialGuiaRemision) {
        this.secuencialGuiaRemision = secuencialGuiaRemision;
    }

    public Integer getSecuencialRetenciones() {
        return secuencialRetenciones;
    }

    public void setSecuencialRetenciones(Integer secuencialRetenciones) {
        this.secuencialRetenciones = secuencialRetenciones;
    }

    public Integer getSecuencialNotaVenta() {
        return secuencialNotaVenta;
    }

    public void setSecuencialNotaVenta(Integer secuencialNotaVenta) {
        this.secuencialNotaVenta = secuencialNotaVenta;
    }

    @Override
    public String toString() {
        return puntoEmisionFormatoTexto();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        final PuntoEmision other = (PuntoEmision) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

   
    
    
    /**
     * Metodos Personalizados
     */
    
    public String puntoEmisionFormatoTexto()
    {
        return UtilidadesTextos.llenarCarateresIzquierda(puntoEmision.toString(),3,"0");
    }
    
    public GeneralEnumEstado getEstadoEnum()
    {
        return GeneralEnumEstado.getEnum(estado);
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }
    
    public ComprobanteEntity.TipoEmisionEnum getTipoFacturacionEnum()
    {
        return ComprobanteEntity.TipoEmisionEnum.getEnumByEstado(tipoFacturacion);
    }
    

    
    
    
}
