/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author CodesoftDesarrollo 1
 */
@Entity
@Table(name = "PRESUPUESTO_DETALLE")
@XmlRootElement
public class PresupuestoDetalle implements Serializable
{   
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)        
    private Long id; 
    
    @Column(name = "PRECIO_COMPRA")
    private BigDecimal precioCompra;
            
    @Column(name = "DESCUENTO_COMPRA")
    private BigDecimal descuentoCompra;
    
    @Column(name = "PRECIO_VENTA")
    private BigDecimal precioVenta;

    @Column(name = "DESCUENTO_VENTA")
    private BigDecimal descuentoVenta;
    
    @Column(name = "CANTIDAD")
    private BigDecimal cantidad;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @Column(name = "RESERVADO")
    private String reservado;
    
    /**
     * Variable usada para agrupar detalles que va a permanecer a una misma orden de compra
     */
    @Column(name = "NUMERO_ORDEN_COMPRA")
    private Integer numeroOrdenCompra;
    
    @JoinColumn(name = "PROVEEDOR_ID")
    @ManyToOne
    private Persona persona;
    
    @JoinColumn(name = "PRODUCTO_ID")
    @ManyToOne
    private Producto producto;    
    
    @JoinColumn(name = "BODEGA_ID")
    @ManyToOne
    private Bodega bodega;

    @JoinColumn(name = "PRESUPUESTO_ID")
    @ManyToOne    
    private Presupuesto presupuesto;
      
    @JoinColumn(name = "PRODUCTO_PROVEEDOR_ID")
    @ManyToOne
    private ProductoProveedor productoProveedor;
    
    @Column(name = "KARDEX_ID")
    private Long kardexId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public BigDecimal getDescuentoCompra() {
        return descuentoCompra;
    }

    public void setDescuentoCompra(BigDecimal descuentoCompra) {
        this.descuentoCompra = descuentoCompra;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getDescuentoVenta() {
        return descuentoVenta;
    }

    public void setDescuentoVenta(BigDecimal descuentoVenta) {
        this.descuentoVenta = descuentoVenta;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }

    public Integer getNumeroOrdenCompra() {
        return numeroOrdenCompra;
    }

    public void setNumeroOrdenCompra(Integer numeroOrdenCompra) {
        this.numeroOrdenCompra = numeroOrdenCompra;
    }

    public ProductoProveedor getProductoProveedor() {
        return productoProveedor;
    }

    public void setProductoProveedor(ProductoProveedor productoProveedor) {
        this.productoProveedor = productoProveedor;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public String getReservado() {
        return reservado;
    }

    public void setReservado(String reservado) {
        this.reservado = reservado;
    }
    
    public EnumSiNo getReservadoEnum() 
    {
        return EnumSiNo.getEnumByLetra(reservado);
    }

    public void setReservadoEnum(EnumSiNo reservadoEnum) 
    {
        this.reservado = reservadoEnum.getLetra();
    }

    public Long getKardexId() {
        return kardexId;
    }

    public void setKardexId(Long kardexId) {
        this.kardexId = kardexId;
    }

    public Kardex getKardex()
    {
        if(kardexId!=null)
        {
            try {
                return ServiceFactory.getFactory().getKardexServiceIf().buscarPorId(kardexId);
            } catch (RemoteException ex) {
                Logger.getLogger(DetalleFacturaNotaCeditoAbstract.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return null;
    }
    
    
    public void setKardex(Kardex kardex)
    {
        this.kardexId=kardex.getId();
    }
    
    /**
     * ==========================> METODOS PERSONALIZADOS <====================
     */
    
    /**
     * Devuelve el valor de la cantidad por el valor unitario
     * @return 
     */
    public BigDecimal calcularSubtotalCompra()
    {
        return cantidad.multiply(precioCompra);
    }
    
    /**
     * Devuelve el valor del subtotal - descuento
     * @return 
     */
    public BigDecimal calcularTotalCompra()
    {
        return calcularSubtotalCompra().subtract(descuentoCompra);
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final PresupuestoDetalle other = (PresupuestoDetalle) obj;
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        
        return true;
    }
}
