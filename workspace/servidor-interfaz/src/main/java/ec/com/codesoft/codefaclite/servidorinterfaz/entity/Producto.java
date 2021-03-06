/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "PRODUCTO")
@XmlRootElement
public class Producto implements Serializable, Comparable<Producto> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PRODUCTO")
    private Long idProducto;
    @Column(name = "CODIGO_PERSONALIZADO")
    private String codigoPersonalizado;
    @Column(name = "CODIGO_EAN")
    private String codigoEAN;
    @Column(name = "CODIGO_UPC")
    private String codigoUPC;

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

    @Column(name = "CARACTERISTICAS")
    private String caracteristicas;

    @Column(name = "OBSERVACIONES")
    private String observaciones;
    
    @Column(name = "TIPO_PRODUCTO_COD")
    private String tipoProductoCodigo;
    
    @Column(name = "MANEJAR_INVENTARIO")
    private String manejarInventario;
    
    @Column(name = "GENERAR_CODIGO_BARRAS")
    private String generarCodigoBarras;
    
    @Column(name = "TRANSPORTAR_EN_GUIA_REMISION")
    private String transportarEnGuiaRemision;
    
    @JoinColumn(name = "EMPRESA_ID")
    private Empresa empresa;
    
    @JoinColumn(name = "CATALOGO_PRODUCTO_ID")
    @ManyToOne    
    private CatalogoProducto catalogoProducto;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoEnsamble",fetch = FetchType.EAGER)
    private List<ProductoEnsamble> detallesEnsamble;
    
    public Producto() {
    }


    public static final Producto getDEFECTO() {
        Producto p = new Producto();
        return new Producto();
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
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
    
    public GeneralEnumEstado getEstadoEnum() {
        return GeneralEnumEstado.getEnum(estado);
    }


    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public void setEstadoEnum(GeneralEnumEstado estadoEnum) {
        this.estado = estadoEnum.getEstado();
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

    public List<ProductoEnsamble> getDetallesEnsamble() {
        return detallesEnsamble;
    }

    public void setDetallesEnsamble(List<ProductoEnsamble> detallesEnsamble) {
        this.detallesEnsamble = detallesEnsamble;
    }

    public CatalogoProducto getCatalogoProducto() {
        return catalogoProducto;
    }

    public void setCatalogoProducto(CatalogoProducto catalogoProducto) {
        this.catalogoProducto = catalogoProducto;
    }

    public String getTipoProductoCodigo() {
        return tipoProductoCodigo;
    }

    public void setTipoProductoCodigo(String tipoProductoCodigo) {
        this.tipoProductoCodigo = tipoProductoCodigo;
    }

    public String getManejarInventario() {
        return manejarInventario;
    }

    public void setManejarInventario(String manejarInventario) {
        this.manejarInventario = manejarInventario;
    }
    
    public EnumSiNo getManejarInventarioEnum() {
        return EnumSiNo.getEnumByLetra(manejarInventario);
    }

    public void setManejarInventarioEnum(EnumSiNo manejarInventarioEnum) {
        this.manejarInventario = manejarInventarioEnum.getLetra();
    }
    

    public String getGenerarCodigoBarras() {
        return generarCodigoBarras;
    }
    
    public EnumSiNo getGenerarCodigoBarrasEnum() {
        return EnumSiNo.getEnumByLetra(generarCodigoBarras);
    }

    public void setGenerarCodigoBarras(EnumSiNo generarCodigoBarrasEnum) {
        this.generarCodigoBarras = generarCodigoBarrasEnum.getLetra();
    }

    public String getTransportarEnGuiaRemision() {
        return transportarEnGuiaRemision;
    }

    public void setTransportarEnGuiaRemision(String transportarEnGuiaRemision) {
        this.transportarEnGuiaRemision = transportarEnGuiaRemision;
    }
    
    public EnumSiNo getTransportarEnGuiaRemisionEnum() {
        if(transportarEnGuiaRemision==null)
        {
            return EnumSiNo.SI; //Por defecto si no existe valor asumo que es si
        }
        return EnumSiNo.getEnumByLetra(transportarEnGuiaRemision);
    }

    public void setTransportarEnGuiaRemisionEnum(EnumSiNo transportarEnGuiaRemisionEnum) {
        this.transportarEnGuiaRemision = transportarEnGuiaRemisionEnum.getLetra();
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    
    
    
    
    public List<PrecioVenta> obtenerPreciosVenta()
    {
        List<PrecioVenta> valores=new ArrayList<PrecioVenta>();
       
        if(valorUnitario!=null && valorUnitario.compareTo(BigDecimal.ZERO)!=0)
        {
            PrecioVenta precioVenta=new PrecioVenta();
            precioVenta.alias=PrecioVenta.PV1;
            precioVenta.precio=valorUnitario;
            valores.add(precioVenta);
        }
        
        if(precioDistribuidor!=null && precioDistribuidor.compareTo(BigDecimal.ZERO)!=0)
        {
            PrecioVenta precioVenta=new PrecioVenta();
            precioVenta.alias=PrecioVenta.PV2;
            precioVenta.precio=precioDistribuidor;
            valores.add(precioVenta);
        }
        
        if(precioTarjeta!=null && precioTarjeta.compareTo(BigDecimal.ZERO)!=0)
        {
            PrecioVenta precioVenta=new PrecioVenta();
            precioVenta.alias=PrecioVenta.PV3;
            precioVenta.precio=precioTarjeta;
            valores.add(precioVenta);
        }
        
        return valores;
    }
    
    
        

    /**
     * Metodos personalizados
     */
    public EnumSiNo getGarantiaEnum() {
        return EnumSiNo.getEnumByLetra(garantia);
    }
    
    /*public EnumSiNo getManejarInventarioEnum()
    {
        return EnumSiNo.getEnumByLetra(manejarInventario);
    }*/

    /**
     * Agregar producto al ensamble
     *
     * @param detalle
     */
    public void addProductoEnsamble(ProductoEnsamble detalle) {
        if (this.detallesEnsamble == null) {
            this.detallesEnsamble = new ArrayList<ProductoEnsamble>();
        }
        detalle.setProductoEnsamble(this);
        this.detallesEnsamble.add(detalle);

    }
    
    public TipoProductoEnum getTipoProductoEnum()
    {
        return TipoProductoEnum.getEnumByLetra(tipoProductoCodigo);
    }   

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int compareTo(Producto p) {
      return this.getIdProducto().compareTo(p.getIdProducto());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.idProducto);
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
        final Producto other = (Producto) obj;
        if (!Objects.equals(this.idProducto, other.idProducto)) {
            return false;
        }
        return true;
    }
    
    public static class PrecioVenta {
        public static final String PV1="pv1";
        public static final String PV2="pv2";
        public static final String PV3="pv3";
        
        public String alias;
        public BigDecimal precio;

        @Override
        public String toString() {
            return alias;
        }
        
        

    }
    
    
}
