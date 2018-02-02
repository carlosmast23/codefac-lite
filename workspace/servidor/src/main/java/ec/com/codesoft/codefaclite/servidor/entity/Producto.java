/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.entity;

import ec.com.codesoft.codefaclite.servidor.entity.enumerados.EnumSiNo;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
@Entity
@Table(name = "PRODUCTO")
@XmlRootElement
public class Producto implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column (name = "ID_PRODUCTO")
    private Long  idProducto;
    @Column(name = "CODIGO_PERSONALIZADO")
    private String codigoPersonalizado;
    @Column(name = "CODIGO_EAN")
    private String codigoEAN;
    @Column(name = "CODIGO_UPC")
    private String codigoUPC;
    
    @Column(name = "TIPO_PRODUCTO")
    private String tipoProducto;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "VALOR_UNITARIO")
    private BigDecimal valorUnitario;
    @Column(name = "ESTADO")
    private String estado;
    
    
    @Column(name = "UBICACION")
    private String ubicacion;
    
    @Column(name = "GARANTIA")
    private String garantia;
    
    @Column(name = "CANTIDAD_MINIMA")
    private Integer cantidadMinima;
    
    @Column(name = "PRECIO_DISTRIBUIDOR")
    private BigDecimal precioDistribuidor;
    
    @Column(name = "PRECIO_TARJETA")
    private BigDecimal precioTarjeta;
    
    @Column(name = "STOCK_INICIAL")
    private Long stockInicial;
    
    @Column(name = "MARCA")
    private String marca;
    
    @Column(name = "IMAGEN")
    private String imagen;
    
    @Column(name = "CATEGORIA")
    private String categoria;
    
    @Column(name = "CARACTERISTICAS")
    private String caracteristicas;
    
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    
    @JoinColumn(name = "IVA_ID")
    @ManyToOne
    private ImpuestoDetalle iva;
    
    @JoinColumn(name = "ICE_ID")
    @ManyToOne
    private ImpuestoDetalle ice;
    
    @JoinColumn(name = "IRBPNR_ID")
    @ManyToOne
    private ImpuestoDetalle irbpnr;

    public Producto() {
    }
    
    

    public ImpuestoDetalle getIva() {
        return iva;
    }

    public void setIva(ImpuestoDetalle iva) {
        this.iva = iva;
    }

    public ImpuestoDetalle getIce() {
        return ice;
    }

    public void setIce(ImpuestoDetalle ice) {
        this.ice = ice;
    }

    public static final Producto getDEFECTO() {
        Producto p = new Producto();
        return new Producto();
    }

    public ImpuestoDetalle getIrbpnr() {
        return irbpnr;
    }

    public void setIrbpnr(ImpuestoDetalle irbpnr) {
        this.irbpnr = irbpnr;
    }

    
    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    
    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodigoPersonalizado() {
        return codigoPersonalizado;
    }

    public void setCodigoPersonalizado(String codigoPersonalizado) {
        this.codigoPersonalizado = codigoPersonalizado;
    }

    public String getCodigoEAN() {
        return codigoEAN;
    }

    public void setCodigoEAN(String codigoEAN) {
        this.codigoEAN = codigoEAN;
    }

    public String getCodigoUPC() {
        return codigoUPC;
    }

    public void setCodigoUPC(String codigoUPC) {
        this.codigoUPC = codigoUPC;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getGarantia() {
        return garantia;
    }

    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    public Integer getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(Integer cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    public BigDecimal getPrecioDistribuidor() {
        return precioDistribuidor;
    }

    public void setPrecioDistribuidor(BigDecimal precioDistribuidor) {
        this.precioDistribuidor = precioDistribuidor;
    }

    public BigDecimal getPrecioTarjeta() {
        return precioTarjeta;
    }

    public void setPrecioTarjeta(BigDecimal precioTarjeta) {
        this.precioTarjeta = precioTarjeta;
    }

    public Long getStockInicial() {
        return stockInicial;
    }

    public void setStockInicial(Long stockInicial) {
        this.stockInicial = stockInicial;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    /**
     * Metodos personalizados
     */
    
    public EnumSiNo getGarantiaEnum()
    {
        return EnumSiNo.getEnumByLetra(garantia);
    }
    
    
    
    
    
}
