/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.sri.SriSustentoComprobanteEnum;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesImpuestos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "COMPRA_DETALLE")
public class CompraDetalle extends DetalleFacturaNotaCeditoAbstract implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(name = "PRODUCTO_ID")
    //private Long productoId;
    /*@Column(name = "CANTIDAD")
    private BigDecimal cantidad;*/
    /*@Column(name = "PRECIO_UNITARIO")
    private BigDecimal precioUnitario;*/
    /*@Column(name = "DESCUENTO")
    private BigDecimal descuento;*/
    /*@Column(name = "VALOR_ICE")
    private BigDecimal valorIce;*/
    /*@Column(name = "DESCRIPCION")
    private String descripcion;*/
    /**
     * Todo: Revisar como corregir este problema porque el total se esta grabando el subtotal sin iva y no le modificado porque la compra funciona con esta logica y genera problemas
     */
    /*@Column(name = "TOTAL")
    private BigDecimal total;*/
    /*@Column(name = "IVA")
    private BigDecimal iva;*/
    
    //@Column(name = "PRECIO_UNITARIO")
    //private BigDecimal precioUnitario;
    
    
    @JoinColumn(name="COMPRA_ID")
    @ManyToOne(optional = false)
    private Compra compra;
    
    @JoinColumn(name = "PRODUCTO_PROVEEDOR_ID")
    @ManyToOne
    private ProductoProveedor productoProveedor;
    
    @JoinColumn(name = "SRI_RETENCION_IVA_ID")
    @ManyToOne
    private SriRetencionIva sriRetencionIva;
    
    @JoinColumn(name= "SRI_RETENCION_RENTA_IVA_ID")
    @ManyToOne
    private SriRetencionRenta sriRetencionRenta;
    
    @Column(name="VALOR_RETENCION_IVA")
    private BigDecimal valorSriRetencionIVA;
    
    @Column(name="VALOR_RETENCION_RENTA")
    private BigDecimal valorSriRetencionRenta;
    
    @Column(name = "CODIGO_SUSTENTO_SRI")
    private String codigoSustentoSri;
    
    //@Column(name = "PORCENTAJE_IVA")
    //private Integer porcentajeIva;
    
    /*@JoinColumn(name = "LOTE_ID")
    private Lote lote;*/
        
    /**
     * Este campo me permite guardar cual fue el codigo original del proveedor cuando exista un codigo
     * por el momento no lo hago persistente y solo lo uso para poder enviar informacion a la pantalla de COMPRA XML
     */
    //@Column (name = "CODIGO_PROVEEDOR")
    @Transient
    private String codigoProveedor;
    
    @Column(name = "COSTO_UNITARIO")
    private BigDecimal costoUnitario;
    
    
    public CompraDetalle() {
    }
    
    public Long getId() {
        return id;
    }



    /*public BigDecimal getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public BigDecimal getValorIce() {
        return valorIce;
    }*/

    public void setId(Long id) {
        this.id = id;
    }



    /*public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public void setValorIce(BigDecimal valorIce) {
        this.valorIce = valorIce;
    }

    public String getDescripcion() {
        return descripcion;
    }*/

    /*public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }*/
    
    //TODO: Metodo temporal hasta poder ver como hacer para grabar correctamente
    //Parece que va a tocar grabar todos los decimales y en este punto redondear
    public BigDecimal obtenerIvaCalculado()
    {
        BigDecimal iva=getIva();
        BigDecimal total=getTotal();
        
        if (iva.compareTo(BigDecimal.ZERO) == 0) {
            return iva;
        }
        
        return total.multiply(new BigDecimal("0.12")).setScale(2, RoundingMode.HALF_UP);
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public ProductoProveedor getProductoProveedor() {
        return productoProveedor;
    }

    public void setProductoProveedor(ProductoProveedor productoProveedor) {
        this.productoProveedor = productoProveedor;
    }
    
    /**
     * Metodo personalizados
     */
    
    public BigDecimal getBaseImponibleRenta()
    {
        return getSubtotal().setScale(2,BigDecimal.ROUND_HALF_UP);
    }
    
    
    public BigDecimal getBaseImponibleIva()
    {
        return calcularValorIva().setScale(2,BigDecimal.ROUND_HALF_UP);
    }

    
    /**
     * Metodo que devuelve el subtotal de la cantidad por el precio unitario y menos el descuento
     * @return 
     */
    public BigDecimal getSubtotal()
    {
        BigDecimal cantidad=getCantidad();
        BigDecimal precioUnitario=getPrecioUnitario();
        BigDecimal descuento=getDescuento();
        return new BigDecimal(cantidad+"").multiply(precioUnitario).subtract(descuento);
    }
    
    public void calcularSubtotalSinIva()
    {
        BigDecimal total=getSubtotal();
        setTotal(total);
    }
    /**
     * Calcula el valor del iva 
     * @return 
     */
    public BigDecimal calcularValorIva()
    {
        //Todo: revisar el valor del iva 12 que esta quemado
        //return getSubtotal().multiply( new BigDecimal("0.12"));
        return UtilidadesImpuestos.calcularValorIva(new BigDecimal(getIvaPorcentaje()+""),getSubtotal());
    }
    
    public BigDecimal calcularTotal()
    {
        //Todo: revisar el valor del iva 12 que esta quemado
        //return getSubtotal().multiply(new BigDecimal("1.12"));
        return UtilidadesImpuestos.agregarValorIva(new BigDecimal(getIvaPorcentaje()+""), getSubtotal());
    }

    public SriRetencionIva getSriRetencionIva() {
        return sriRetencionIva;
    }

    public void setSriRetencionIva(SriRetencionIva sriRetencionIva) {
        this.sriRetencionIva = sriRetencionIva;
    }

    public SriRetencionRenta getSriRetencionRenta() {
        return sriRetencionRenta;
    }

    public void setSriRetencionRenta(SriRetencionRenta sriRetencionRenta) {
        this.sriRetencionRenta = sriRetencionRenta;
    }

    public BigDecimal getValorSriRetencionIVA() {
        return valorSriRetencionIVA;
    }

    public void setValorSriRetencionIVA(BigDecimal valorSriRetencionIVA) {
        this.valorSriRetencionIVA = valorSriRetencionIVA;
    }

    public BigDecimal getValorSriRetencionRenta() {
        return valorSriRetencionRenta;
    }

    public void setValorSriRetencionRenta(BigDecimal valorSriRetencionRenta) {
        this.valorSriRetencionRenta = valorSriRetencionRenta;
    }

    public String getCodigoSustentoSri() {
        return codigoSustentoSri;
    }

    public void setCodigoSustentoSri(String codigoSustentoSri) {
        this.codigoSustentoSri = codigoSustentoSri;
    }
    
    public void setCodigoSustentoSriEnum(SriSustentoComprobanteEnum codigoEnum) {
        this.codigoSustentoSri = codigoEnum.getCodigo();
    }
    
    public SriSustentoComprobanteEnum getCodigoSustentoSriEnum() {
        return SriSustentoComprobanteEnum.obtenerPorCodigo(codigoSustentoSri);        
    }

    public String getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(String codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    /*public Integer getPorcentajeIva() {
        return porcentajeIva;
    }

    public void setPorcentajeIva(Integer porcentajeIva) {
        this.porcentajeIva = porcentajeIva;
    }*/

    /*public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }*/

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }


    
    /**
     * Metodos adicionales
     */
    
    public BigDecimal calcularUtilidadRedondeada()
    {
        //TODO: Tomar en cuenta que el calculo se esta tomando en cuenta no que lleva iva y el calculo se lo esta haciendo por unidad
        BigDecimal valorTotal= productoProveedor.getProducto().getValorUnitario().subtract(getPrecioUnitario()).setScale(2, RoundingMode.HALF_UP);
        return valorTotal;
    }
    
    
    /**
     * Verificar si el producto esta cobrando iva 0
     * @return 
     */
    public Boolean isImpuestoIvaCero()
    {
        return getProductoProveedor().getProducto().getCatalogoProducto().getIva().getPorcentaje().compareTo(BigDecimal.ZERO)==0;
    }
    
    /**
     * Obtener el valor del total porque se esta grabando mal en los detalles
     * @return 
     */
    public BigDecimal getTotalCalculado() {
        //TODO modificado por el momento hasta solucionar el tema de grabar correctamente los decimales del iva
        return getSubtotal().add(obtenerIvaCalculado()).setScale(2, RoundingMode.HALF_UP);
        //return getSubtotal().add(iva);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        final CompraDetalle other = (CompraDetalle) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
