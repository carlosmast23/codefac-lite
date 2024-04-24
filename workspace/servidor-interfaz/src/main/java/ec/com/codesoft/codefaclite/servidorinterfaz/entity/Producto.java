/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesImpuestos;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import javax.swing.ImageIcon;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = Producto.NOMBRE_TABLA)
@XmlRootElement
public class Producto implements Serializable, Comparable<Producto>,Cloneable {
    
    /**
     * Variable global que me permite saber cual es el código maximo de los productos que acepta la facturación electrónica
     */
    public static final Integer TAMANIO_MAX_CODIGO=25;
    
    public static final String NOMBRE_TABLA="PRODUCTO";
    public static final String NOMBRE_CAMPO_ID="ID_PRODUCTO";
    
    public static final String NOMBRE_PRODUCTO_REEMBOLSO="REEMBOLSO";
    

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = NOMBRE_CAMPO_ID)
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

    //@Column(name = "MARCA")
    //private String marca;

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
    
    @Column(name = "OCULTAR_DETALLE_VENTA")
    private String ocultarDetalleVenta;
    
    @Column(name = "RESPONSABLE_REQUERIDO")
    private String responsableRequerido;
    
    @Column(name = "TRANSPORTAR_EN_GUIA_REMISION")
    private String transportarEnGuiaRemision;
    
    @Column(name = "PRECIO_SIN_SUBSIDIO")
    private BigDecimal precioSinSubsidio;
    
    @JoinColumn(name = "EMPRESA_ID")
    private Empresa empresa;
    
    @JoinColumn(name = "MARCA_PRODUCTO_ID")
    private MarcaProducto marcaProducto;
    
    @Column(name = "PVP_4")
    private BigDecimal pvp4;
        
    @Column(name = "PVP_5")
    private BigDecimal pvp5;
            
    @Column(name = "PVP_6")
    private BigDecimal pvp6;
    
    @JoinColumn(name = "CATALOGO_PRODUCTO_ID")
    //@ManyToOne    
    private CatalogoProducto catalogoProducto;
    
    @Column(name = "APLICACION_PRODUCTO")    
    private String aplicacionProducto;
    
    @JoinColumn(name = "TIPO_ID")
    private TipoProducto tipoProducto;
        
    @JoinColumn(name = "SEGMENTO_ID")    
    private SegmentoProducto segmentoProducto;
    
    @JoinColumn(name = "CASA_COMERCIAL_ID")
    private CasaComercial casaComercial; 
    
    @Column(name = "NOMBRE_GENERICO")    
    private String nombreGenerico;
    
    @Column(name = "ACTUALIZAR_PRECIO")     
    private String actualizarPrecio;
    
    @Column(name = "FECHA_ULTIMA_ACTUALIZACION_PRECIO") 
    private Timestamp fechaUltimaActualizacionPrecio;
    
    @Column(name = "DISPONIBLE_VENTA") 
    private String disponibleVenta;
    
    @Column(name = "DISPONIBLE_COMPRA") 
    private String disponibleCompra;
    
    @Column(name = "REGISTRO_SANITARIO") 
    private String registroSanitario;
    
    @Column(name = "FECHA_CREACION")
    protected Timestamp fechaCreacion;
    
    @Column(name = "FECHA_ULTIMA_EDICION")
    protected Timestamp fechaUltimaEdicion;
    
    /*@JoinColumn(name = "PRESENTACION_ID")
    private PresentacionProducto presentacion;*/

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoEnsamble",fetch = FetchType.EAGER)
    private List<ProductoEnsamble> detallesEnsamble;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto",fetch = FetchType.EAGER)
    private List<ProductoProveedor> productoProveedorList;
    
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "productoOriginal",fetch = FetchType.EAGER)
    @OneToMany(mappedBy = "productoOriginal",fetch = FetchType.EAGER)
    private List<ProductoPresentacionDetalle> presentacionList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto",fetch = FetchType.EAGER)
    private List<ProductoActividad> actividadList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "producto",fetch = FetchType.EAGER)
    private List<ProductoComponenteDetalle> componenteList;
    
    @Transient
    private Path pathFotoTmp;
    
    @Column(name = "COD_PRESENTACION_DEFECTO_VENTA")
    private String codigoPresentacionDefectoVenta;
    
    @Column(name = "COD_PRESENTACION_DEFECTO_COMPRA")
    private String codigoPresentacionDefectoCompra;
    
    @Column(name = "PSICOTROPICO")
    private String psicotropico;
    
        
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

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    

    /*public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }*/

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
        if(manejarInventarioEnum!=null)
            this.manejarInventario = manejarInventarioEnum.getLetra();
        else
            this.manejarInventario=null;
    }
    

    public String getGenerarCodigoBarras() {
        return generarCodigoBarras;
    }
    
    public EnumSiNo getGenerarCodigoBarrasEnum() {
        return EnumSiNo.getEnumByLetra(generarCodigoBarras);
    }

    public void setGenerarCodigoBarrasEnum(EnumSiNo generarCodigoBarrasEnum) {
        if(generarCodigoBarrasEnum==null)
        {
            this.generarCodigoBarras=null;
        }
        else
        {
            this.generarCodigoBarras = generarCodigoBarrasEnum.getLetra();
        }
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

    public String getOcultarDetalleVenta() {
        return ocultarDetalleVenta;
    }

    public void setOcultarDetalleVenta(String ocultarDetalleVenta) {
        this.ocultarDetalleVenta = ocultarDetalleVenta;
    }

    public String getResponsableRequerido() {
        return responsableRequerido;
    }

    public void setResponsableRequerido(String responsableRequerido) {
        this.responsableRequerido = responsableRequerido;
    }
    
    public EnumSiNo getResponsableRequeridoEnum() {
        return EnumSiNo.getEnumByLetra(responsableRequerido);
    }

    public void setResponsableRequeridoEnum(EnumSiNo responsableRequeridoEnum) {
        if(responsableRequeridoEnum!=null)
        {
            this.responsableRequerido=responsableRequeridoEnum.getLetra();
        }
        else
        {
            this.responsableRequerido = null;
        }
        
    }
    
    
    
    
    
    public EnumSiNo getOcultarDetalleVentaEnum() {
        if(ocultarDetalleVenta==null)
        {
            return EnumSiNo.NO; //Por defecto si no existe valor asumo que es NO
        }
        return EnumSiNo.getEnumByLetra(ocultarDetalleVenta);
    }
    
    public void setOcultarDetalleVentaEnum(EnumSiNo enumSiNo) {
        this.ocultarDetalleVenta=enumSiNo.getLetra();
    }

    public BigDecimal getPrecioSinSubsidio() {
        return precioSinSubsidio;
    }

    public void setPrecioSinSubsidio(BigDecimal precioSinSubsidio) {
        this.precioSinSubsidio = precioSinSubsidio;
    }

    public MarcaProducto getMarcaProducto() {
        return marcaProducto;
    }

    public void setMarcaProducto(MarcaProducto marcaProducto) {
        this.marcaProducto = marcaProducto;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public SegmentoProducto getSegmentoProducto() {
        return segmentoProducto;
    }

    public void setSegmentoProducto(SegmentoProducto segmentoProducto) {
        this.segmentoProducto = segmentoProducto;
    }

    public List<ProductoComponenteDetalle> getComponenteList() {
        return componenteList;
    }

    public void setComponenteList(List<ProductoComponenteDetalle> componenteList) {
        this.componenteList = componenteList;
    }

    
    
    
    /**
     *=========================================================================
     *                      METODOS PERSONALIZADOS
     *=========================================================================
     */
    
    public void eliminarPresentacionProducto(ProductoPresentacionDetalle presentacionProducto)
    {
        if(presentacionList!=null)
        {
            presentacionList.remove(presentacionProducto);
        }
    }
    
    /*@Deprecated
    public PresentacionProducto buscarPresentacionOriginal()
    {
        if(presentacionList!=null)
        {
            for (ProductoPresentacionDetalle productoEnsamble : presentacionList) 
            {
                if(productoEnsamble.getTipoEnum()!=null && productoEnsamble.getTipoEnum().equals(ProductoPresentacionDetalle.TipoPresentacionEnum.ORIGINAL))
                {
                    return productoEnsamble.getPresentacionProducto();
                }
            }
        }
        return null;
    }*/
    
    public ProductoPresentacionDetalle buscarPresentacionDetalleProducto()
    {
        try {
            ProductoPresentacionDetalle detalle= ServiceFactory.getFactory().getProductoPresentacionDetalleServiceIf().buscarPorProductoEmpaquetado(this);
            if(detalle!=null)
            {
                return detalle;
            }
            return null;
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
       return null;
    }
    
    public PresentacionProducto buscarPresentacionProducto()
    {
        try {
            ProductoPresentacionDetalle detalle= ServiceFactory.getFactory().getProductoPresentacionDetalleServiceIf().buscarPorProductoEmpaquetado(this);
            if(detalle!=null)
            {
                return detalle.getPresentacionProducto();
            }
            return null;
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
       return null;
    }
    
    public String obtenerNombrePresentacion()
    {
        PresentacionProducto presentacionProducto=buscarPresentacionProducto();
        if(presentacionProducto!=null)
        {
            return presentacionProducto.getNombre();
        }
        return "";
    }
    
    public void agregarPresentacionOriginal(PresentacionProducto presentacionProducto)
    {
        ProductoPresentacionDetalle detalle=new ProductoPresentacionDetalle();
        detalle.setCantidad(BigDecimal.ONE);
        detalle.setTipoEnum(ProductoPresentacionDetalle.TipoPresentacionEnum.ORIGINAL);
        detalle.setPresentacionProducto(presentacionProducto);
        detalle.setProductoOriginal(this);
        detalle.setProductoEmpaquetado(this);
        addPresentacion(detalle);
    }
    
    public BigDecimal obtenerPrecioVentaPorNumero(Integer numeroPvp)
    {
        List<PrecioVenta> precioVentaList= obtenerPreciosVenta();
        
        if(numeroPvp<precioVentaList.size())
        {
            PrecioVenta precioVenta=precioVentaList.get(numeroPvp);        
            return precioVenta.precio;
        }
               
        return BigDecimal.ZERO;        
    }
    
    public BigDecimal obtenerPrecioVentaPorNombre(String nombre)
    {
        List<PrecioVenta> precioVentaList= obtenerPreciosVenta();
        
        if(precioVentaList!=null)
        {
            for (PrecioVenta precioVenta : precioVentaList) 
            {
                if(precioVenta.alias.equals(nombre))
                {
                    return precioVenta.precio;
                }
            }
        }
               
        return BigDecimal.ZERO;        
    }
    
    public BigDecimal buscarPrecioPorNombre(String pvpNombre)
    {
        if(!UtilidadesTextos.verificarNullOVacio(pvpNombre))
        {
            switch(pvpNombre)
            {
                case Producto.PrecioVenta.PV1: return valorUnitario;
                case Producto.PrecioVenta.PV2: return precioDistribuidor;
                case Producto.PrecioVenta.PV3: return precioTarjeta;
                case Producto.PrecioVenta.PV4: return pvp4;
                case Producto.PrecioVenta.PV5: return pvp5;
                case Producto.PrecioVenta.PV6: return pvp6;
            }
        }
        return null;
    }
    
    public List<PrecioVenta> obtenerPreciosVenta()
    {
        List<PrecioVenta> valores=new ArrayList<PrecioVenta>();
       
        if(valorUnitario!=null && valorUnitario.compareTo(BigDecimal.ZERO)!=0)
        {
            PrecioVenta precioVenta=new PrecioVenta();
            precioVenta.alias=PrecioVenta.PV1;
            precioVenta.precio=valorUnitario;
            precioVenta.numero=1;
            valores.add(precioVenta);
        }
        
        if(precioDistribuidor!=null && precioDistribuidor.compareTo(BigDecimal.ZERO)!=0)
        {
            PrecioVenta precioVenta=new PrecioVenta();
            precioVenta.alias=PrecioVenta.PV2;
            precioVenta.precio=precioDistribuidor;
            precioVenta.numero=2;
            valores.add(precioVenta);
        }
        
        if(precioTarjeta!=null && precioTarjeta.compareTo(BigDecimal.ZERO)!=0)
        {
            PrecioVenta precioVenta=new PrecioVenta();
            precioVenta.alias=PrecioVenta.PV3;
            precioVenta.precio=precioTarjeta;
            precioVenta.numero=3;
            valores.add(precioVenta);
        }
        
        if(pvp4!=null && pvp4.compareTo(BigDecimal.ZERO)!=0)
        {
            PrecioVenta precioVenta=new PrecioVenta();
            precioVenta.alias=PrecioVenta.PV4;
            precioVenta.precio=pvp4;
            precioVenta.numero=4;
            valores.add(precioVenta);
        }
        
        if(pvp5!=null && pvp5.compareTo(BigDecimal.ZERO)!=0)
        {
            PrecioVenta precioVenta=new PrecioVenta();
            precioVenta.alias=PrecioVenta.PV5;
            precioVenta.precio=pvp5;
            precioVenta.numero=5;
            valores.add(precioVenta);
        }
        
        if(pvp6!=null && pvp6.compareTo(BigDecimal.ZERO)!=0)
        {
            PrecioVenta precioVenta=new PrecioVenta();
            precioVenta.alias=PrecioVenta.PV6;
            precioVenta.precio=pvp6;
            precioVenta.numero=6;
            valores.add(precioVenta);
        }
        
        return valores;
    }

    public BigDecimal getPvp4() {
        return pvp4;
    }

    public void setPvp4(BigDecimal pvp4) {
        this.pvp4 = pvp4;
    }

    public BigDecimal getPvp5() {
        return pvp5;
    }

    public void setPvp5(BigDecimal pvp5) {
        this.pvp5 = pvp5;
    }

    public BigDecimal getPvp6() {
        return pvp6;
    }

    public void setPvp6(BigDecimal pvp6) {
        this.pvp6 = pvp6;
    }

    public String getAplicacionProducto() {
        return aplicacionProducto;
    }

    public void setAplicacionProducto(String aplicacionProducto) {
        this.aplicacionProducto = aplicacionProducto;
    }

    public CasaComercial getCasaComercial() {
        return casaComercial;
    }

    public void setCasaComercial(CasaComercial casaComercial) {
        this.casaComercial = casaComercial;
    }

    public String getNombreGenerico() {
        return nombreGenerico;
    }

    public void setNombreGenerico(String nombreGenerico) {
        this.nombreGenerico = nombreGenerico;
    }

    public List<ProductoProveedor> getProductoProveedorList() {
        return productoProveedorList;
    }

    public void setProductoProveedorList(List<ProductoProveedor> productoProveedorList) {
        this.productoProveedorList = productoProveedorList;
    }

    /*public PresentacionProducto getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(PresentacionProducto presentacion) {
        this.presentacion = presentacion;
    }*/

    public List<ProductoPresentacionDetalle> getPresentacionList() {
        
        if(presentacionList==null)
        {
            presentacionList=new ArrayList<ProductoPresentacionDetalle>();
        }
        
        return presentacionList;
    }

    public void setPresentacionList(List<ProductoPresentacionDetalle> presentacionList) {
        this.presentacionList = presentacionList;
    }


    public String getActualizarPrecio() {
        return actualizarPrecio;
    }

    public void setActualizarPrecio(String actualizarPrecio) {
        this.actualizarPrecio = actualizarPrecio;
    }
    
   
    public EnumSiNo getActualizarPrecioEnum() 
    {
        return EnumSiNo.valueOf(actualizarPrecio);
    }

    public void setActualizarPrecioEnum(EnumSiNo actualizarPrecioEnum) {
        this.actualizarPrecio = actualizarPrecioEnum.getLetra();
    }


    public Timestamp getFechaUltimaActualizacionPrecio() {
        return fechaUltimaActualizacionPrecio;
    }

    public void setFechaUltimaActualizacionPrecio(Timestamp fechaUltimaActualizacionPrecio) {
        this.fechaUltimaActualizacionPrecio = fechaUltimaActualizacionPrecio;
    }

    public String getDisponibleVenta() {
        return disponibleVenta;
    }

    public void setDisponibleVenta(String disponibleVenta) {
        this.disponibleVenta = disponibleVenta;
    }

    public String getDisponibleCompra() {
        return disponibleCompra;
    }

    public void setDisponibleCompra(String disponibleCompra) {
        this.disponibleCompra = disponibleCompra;
    }

    public EnumSiNo getDisponibleVentaEnum() {
        return EnumSiNo.getEnumByLetra(disponibleVenta);
    }

    public void setDisponibleVentaEnum(EnumSiNo disponibleVentaEnum) {
        this.disponibleVenta = disponibleVentaEnum.getLetra();
    }

    public EnumSiNo getDisponibleCompraEnum() {
        return EnumSiNo.getEnumByLetra(disponibleCompra);
    }

    public void setDisponibleCompraEnum(EnumSiNo disponibleCompraEnum) {
        this.disponibleCompra = disponibleCompraEnum.getLetra();
    }

    public String getRegistroSanitario() {
        return registroSanitario;
    }

    public void setRegistroSanitario(String registroSanitario) {
        this.registroSanitario = registroSanitario;
    }

    public List<ProductoActividad> getActividadList() {
        return actividadList;
    }

    public void setActividadList(List<ProductoActividad> actividadList) {
        this.actividadList = actividadList;
    }

    public Timestamp getFechaUltimaEdicion() {
        return fechaUltimaEdicion;
    }

    public void setFechaUltimaEdicion(Timestamp fechaUltimaEdicion) 
    {
        this.fechaUltimaEdicion = fechaUltimaEdicion;
    }

    public InputStream obtenerImagenProducto()
    {
        RemoteInputStream risImagen = null;
        if (!UtilidadesTextos.verificarNullOVacio(imagen)) {
            try {
                RecursosServiceIf service = ServiceFactory.getFactory().getRecursosServiceIf();
                byte[] imagenSerializada = service.obtenerRecurso(empresa, DirectorioCodefac.IMAGENES, imagen);
                risImagen = (RemoteInputStream) UtilidadesRmi.deserializar(imagenSerializada);
            } catch (IOException ex) {
                Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //ImageIcon imageIcon=null;
        InputStream inputStream = null;
        if (risImagen != null) {
            try {
                inputStream = RemoteInputStreamClient.wrap(risImagen);
                //imageIcon=UtilidadImagen.castInputStreamToImageIcon(inputStream);                
            } catch (IOException ex) {
                Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            inputStream = RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream(ParametrosSistemaCodefac.ComprobantesElectronicos.LOGO_SIN_FOTO);
            //imageIcon=UtilidadImagen.castInputStreamToImageIcon(inputStream);
        }

        return inputStream;
    }
    

    /**
     * Metodos personalizados
     */
    public EnumSiNo getGarantiaEnum() {
        return EnumSiNo.getEnumByLetra(garantia);
    }
    
    public void setGarantiaEnum(EnumSiNo enumSiNo) {
        
        if(enumSiNo!=null)
            this.garantia=enumSiNo.getLetra();
        else
            this.garantia=null;
    }
    
    public void quitarActividad(ProductoActividad productoActividad)
    {
        if(this.actividadList!=null)
        {
            this.actividadList.remove(productoActividad);
        }
    }
    
    public void addActividad(ProductoActividad productoActividad)
    {
        if(this.actividadList==null)
        {
            this.actividadList=new ArrayList<ProductoActividad>();
        }
        
        productoActividad.setProducto(this);
        this.actividadList.add(productoActividad);        
    }
    
    public void addComponente(ProductoComponente detalle)
    {
        if(this.componenteList==null)
        {
            this.componenteList=new ArrayList<ProductoComponenteDetalle>();
        }
        
        ProductoComponenteDetalle detalleTmp=new ProductoComponenteDetalle();
        detalleTmp.setProducto(this);
        detalleTmp.setProductoComponente(detalle);
                
        this.componenteList.add(detalleTmp);
        
    }
    
    public void addProductoProveedor(ProductoProveedor productoProveedor)
    {
        if(this.productoProveedorList==null)
        {
            this.productoProveedorList=new ArrayList<ProductoProveedor>();
        }
        productoProveedor.setProducto(this);
        this.productoProveedorList.add(productoProveedor);
    }
    
    public void addPresentacion(ProductoPresentacionDetalle presentacionDetalle)
    {
        if(this.presentacionList==null)
        {
            this.presentacionList=new ArrayList<ProductoPresentacionDetalle>();
        }
        
        presentacionDetalle.setProductoOriginal(this);
        this.presentacionList.add(presentacionDetalle);
        
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
    
    public void quitarProductoEnsamble(ProductoEnsamble detalle)
    {
        if(this.detallesEnsamble!=null)
        {
            this.detallesEnsamble.remove(detalle);
        }
    }
    
    public TipoProductoEnum getTipoProductoEnum()
    {
        return TipoProductoEnum.getEnumByLetra(tipoProductoCodigo);
    }   
    
    public void setTipoProductoEnum(TipoProductoEnum tipoProductoEnum)
    {
        if(tipoProductoEnum!=null)
            tipoProductoCodigo=tipoProductoEnum.getLetra();
        else
            tipoProductoCodigo=null;
    } 

    public String getPsicotropico() {
        return psicotropico;
    }

    public void setPsicotropico(String psicotropico) {
        this.psicotropico = psicotropico;
    }
    
    public EnumSiNo getPsicotropicoEnum() {
        return EnumSiNo.getEnumByLetra(psicotropico);
    }

    public void setPsicotropicoEnum(EnumSiNo psicotropicoEnum) {
        this.psicotropico = psicotropicoEnum.getLetra();
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

    public void setCantidadMinima(Double stockMinimo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Metodo que depende de la configuracion del iva para poder mostrar el valorUnitario
     * @return 
     */
    public BigDecimal getValorUnitarioDefecto()
    {
        //if(ParametrosSistemaCodefac.mostrarPreciosConIva())
        //{
        BigDecimal valorConIva=getValorUnitarioConIva();
        return valorConIva;
        //}
        //else
        //{
        //    return getValorUnitario();
        //}
    }
    
    public BigDecimal getValorUnitarioDefectoFormat()
    {
        return getValorUnitarioDefecto().setScale(3, RoundingMode.HALF_UP);
    }
    
    public void setValorUnitarioDefectoFormat(BigDecimal valorUnitario)
    {
        getValorUnitarioDefecto().setScale(3, RoundingMode.HALF_UP);
    }
    
    public void setValorUnitarioDefecto(BigDecimal valorUnitario)
    {
        /*if(ParametrosSistemaCodefac.mostrarPreciosConIva())
        {
            return getValorUnitarioConIva();
        } */
        //Asumo que siempre me manda los valores sin iva
        //this.valorUnitario=valorUnitario;        
    }
    
    public BigDecimal getValorUnitarioConIva()
    {
        if(valorUnitario==null || valorUnitario.compareTo(BigDecimal.ZERO)==0)
        {
            return valorUnitario;
        }
        
        //BigDecimal tarifa= new BigDecimal(catalogoProducto.getIva().getTarifa().toString());
        BigDecimal tarifa= getTarifaIva();
        return UtilidadesImpuestos.agregarValorIva(tarifa, valorUnitario);
    }
    
    public BigDecimal getTarifaIva()
    {
        if(catalogoProducto==null || catalogoProducto.getIva()==null)
        {
             Logger.getLogger(Producto.class.getName()).log(Level.WARNING,"Producto con id:"+idProducto+" , nombre="+nombre+", con catalogo id="+catalogoProducto.getId()+" No tiene asignado un CATALOGO");
             //Por defecto devuelve cero
             return BigDecimal.ZERO;
        }
        
        if(catalogoProducto.getIva()==null)
        {
            Logger.getLogger(Producto.class.getName()).log(Level.WARNING,"Producto con id:"+idProducto+" , nombre="+nombre+", con catalogo id="+catalogoProducto.getId()+" Tiene IVA NULL");
        }
        
        if(catalogoProducto.getIva().getTarifa()==null)
        {
            Logger.getLogger(Producto.class.getName()).log(Level.WARNING,"Producto con id:"+idProducto+" , nombre="+nombre+", con catalogo id="+catalogoProducto.getId()+" Tiene TARIFA NULL");
            return catalogoProducto.getIva().getPorcentaje();
        }


        //System.out.println(catalogoProducto.getIva());
        //System.out.println(catalogoProducto.getIva().getTarifa());
        //return new BigDecimal(catalogoProducto.getIva().getTarifa().toString());
        return new BigDecimal(catalogoProducto.getIva().getTarifa()+"");
    }
    
    public BigDecimal getPrecioDistribuidorConIva() {
        
        if(precioDistribuidor==null)
        {
            return BigDecimal.ZERO;
        }
        
        if(precioDistribuidor.compareTo(BigDecimal.ZERO)==0)
        {
            return precioDistribuidor;
        }
        
        
        BigDecimal tarifa= new BigDecimal(catalogoProducto.getIva().getTarifa().toString());
        return UtilidadesImpuestos.agregarValorIva(tarifa, precioDistribuidor);
    }
    
    public BigDecimal getPrecioDistribuidorConIvaFormat() {
        
        return getPrecioDistribuidorConIva().setScale(3, RoundingMode.HALF_UP);
    }
    
    public void setPrecioDistribuidorConIvaFormat(BigDecimal valor) {}
    
    
    public void setPrecioDistribuidorConIva(BigDecimal valor) {}
    
    public BigDecimal getPrecioTarjetaConIva() {

        if (precioTarjeta == null || precioTarjeta.compareTo(BigDecimal.ZERO) == 0) 
        {
            return precioTarjeta;
        }
        
        BigDecimal tarifa= new BigDecimal(catalogoProducto.getIva().getTarifa().toString());
        return UtilidadesImpuestos.agregarValorIva(tarifa, precioTarjeta);
    }
    
    public void setPrecioTarjetaConIva(BigDecimal valor) {};
    
    public BigDecimal getPvp4ConIva() {

        if (pvp4 == null || pvp4.compareTo(BigDecimal.ZERO) == 0) {
            return pvp4;
        }
        
        BigDecimal tarifa = new BigDecimal(catalogoProducto.getIva().getTarifa().toString());
        return UtilidadesImpuestos.agregarValorIva(tarifa, pvp4);
    }
    
    public void setPvp4ConIva(BigDecimal valor) {};
    
    public BigDecimal getPvp5ConIva() {

        if (pvp5 == null || pvp5.compareTo(BigDecimal.ZERO) == 0) {
            return pvp5;
        }
        
        BigDecimal tarifa = new BigDecimal(catalogoProducto.getIva().getTarifa().toString());
        return UtilidadesImpuestos.agregarValorIva(tarifa, pvp5);
    }
    
    public void setPvp5ConIva(BigDecimal valor) {};
    
    public BigDecimal getPvp6ConIva() {

        if (pvp6 == null || pvp6.compareTo(BigDecimal.ZERO) == 0) {
            return pvp6;
        }
        
        BigDecimal tarifa = new BigDecimal(catalogoProducto.getIva().getTarifa().toString());
        return UtilidadesImpuestos.agregarValorIva(tarifa, pvp6);
    }

    public void setPvp6ConIva(BigDecimal valor) {};
    
    public void setValorUnitarioConIva(BigDecimal valorUnitarioConIva)
    {
        //TODO: Falta implementar
    }
    
    public BigDecimal obtenerIvaValorUnitario()
    {
        return getValorUnitarioConIva().subtract(valorUnitario);
    }
    
    public BigDecimal obtenerPrecioPresupuesto(Empresa empresa)
    {
        String numeroPrecio = ParametroUtilidades.obtenerValorParametro(empresa, ParametroCodefac.NUMERO_PRECIO_PRESUPUESTO);
        BigDecimal precio = BigDecimal.ZERO;
        if (numeroPrecio == null) {
            precio = getValorUnitario();
        } else {
            precio = obtenerPrecioVentaPorNombre(numeroPrecio);
        }
        return precio;
    }

    public String getCodigoPresentacionDefectoVenta() {
        return codigoPresentacionDefectoVenta;
    }

    public void setCodigoPresentacionDefectoVenta(String codigoPresentacionDefectoVenta) {
        this.codigoPresentacionDefectoVenta = codigoPresentacionDefectoVenta;
    }

    public String getCodigoPresentacionDefectoCompra() {
        return codigoPresentacionDefectoCompra;
    }

    public void setCodigoPresentacionDefectoCompra(String codigoPresentacionDefectoCompra) {
        this.codigoPresentacionDefectoCompra = codigoPresentacionDefectoCompra;
    }
    
    
    
    /**
     * TODO: Ver alguna forma de evitar consultar a la base de datos especilamente desde el servidor porque genera redundacia y problemas de rendimiento bastante fuerte
     * Esto sirve cuando tiene varias presentaciones y se tiene que buscar el producto principal
     * para hacer esta busqueda se tiene que buscar el que tenga el tipo distinto de Empaque
     * @return 
     */
    @Deprecated
    public Producto buscarProductoEmpaquePrincipal()
    {
        try {
            Producto producto= ServiceFactory.getFactory().getProductoServiceIf().buscarProductoEmpaquePrincipal(this);
            return producto;
        } catch (RemoteException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Metodo que solo va a buscar la presentacion pero desde el mismo producto
     * eso quiere decir que tiene que estar desde el producto principal y no desde un componente
     * @param nombre
     * @return 
     */
    public ProductoPresentacionDetalle buscarProductoPorNombrePresentacionLocal(String nombre)
    {
        if(presentacionList!=null)
        {
            for (ProductoPresentacionDetalle detalle : presentacionList) 
            {
                if(detalle.getPresentacionProducto()!=null)
                {
                    if(detalle.getPresentacionProducto().getNombre().equals(nombre))
                    {
                        return detalle;
                    }
                }
                
            }        
        }
        return null;
    }
    
    public Producto buscarProductoPorPresentacionPvpDefecto(Empresa empresas)
    {
        PresentacionProducto presentacionDefecto;
        try {
            presentacionDefecto = ServiceFactory.getFactory().getPresentacionProductoServiceIf().buscarPorNombre(empresa,this.getCodigoPresentacionDefectoVenta());
            if(presentacionDefecto!=null)
            {
                return buscarProductoPorPresentacion(presentacionDefecto);
            }
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public Producto buscarProductoPorPresentacion(PresentacionProducto presentacion)
    {
        try {
            //Si la presentacion buscada es del mismo producto envio los mismos datos
            /*if(this.presentacion.equals(presentacion))
            {
            return this;
            }
            
            if(presentacionList!=null) 
            {
            for (ProductoPresentacionDetalle detalle : presentacionList)
            {
            if(detalle.getPresentacionProducto().equals(presentacion))
            {
            return detalle.getProductoEmpaquetado();
            }
            }
            }*/
            ProductoPresentacionDetalle presentacionDetalle= ServiceFactory.getFactory().getProductoServiceIf().buscarProductoPorPresentacion(presentacion, this);
            if(presentacionDetalle!=null)
            {
                return presentacionDetalle.getProductoEmpaquetado();
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public BigDecimal obtenerCantidadPorCaja()
    {
        ProductoPresentacionDetalle empaque=buscarProductoPorNombrePresentacionLocal(PresentacionProducto.CAJA_PRESENTACION);
        if(empaque!=null)
        {
            return empaque.getCantidad();
        } 
        return BigDecimal.ZERO;
    }
    
    public String obtenerNombreCategoria()
    {
        if(catalogoProducto!=null)
        {
            if(catalogoProducto.getCategoriaProducto()!=null) 
            {
                return catalogoProducto.getCategoriaProducto().getNombre();
            }
        }
        return "";
    }

    
    public Path getPathFotoTmp() {
        return pathFotoTmp;
    }

    public void setPathFotoTmp(Path pathFotoTmp) {
        this.pathFotoTmp = pathFotoTmp;
    }
    
    public Boolean verificarPreciosModificados(Producto productoTmp)
    {
        if(verificarPrecioModificado(this.valorUnitario,productoTmp.valorUnitario))
        {
            return true;
        }
        
        if(verificarPrecioModificado(this.precioDistribuidor,productoTmp.precioDistribuidor))
        {
            return true;
        }
        
        if(verificarPrecioModificado(this.precioTarjeta,productoTmp.precioTarjeta))
        {
            return true;
        }
        
        if(verificarPrecioModificado(this.pvp4,productoTmp.pvp4))
        {
            return true;
        }
        
        if(verificarPrecioModificado(this.pvp5,productoTmp.pvp5))
        {
            return true;
        }
        
        if(verificarPrecioModificado(this.pvp6,productoTmp.pvp6))
        {
            return true;
        }
        
        return false;
        
        
    }
    
    private Boolean verificarPrecioModificado(BigDecimal precioAntiguo, BigDecimal precioNuevo)
    {
        if(precioAntiguo==null && precioNuevo!=null)
        {
            return true;
        }
        
        if(precioNuevo==null && precioAntiguo!=null)
        {
            return true;
        }
        
        if(precioAntiguo!=null && precioNuevo!=null)
        {
            if(precioAntiguo.compareTo(precioNuevo)!=0)
            {
                return true;
            }
        }
        
        return false;
    }
    
    public List<PresentacionProducto> obtenerPresentacionesList()
    {
        try {
            List<PresentacionProducto> presentacionList=ServiceFactory.getFactory().getProductoServiceIf().obtenerPresentacionesProducto(this);
            return presentacionList;
        } catch (RemoteException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<PresentacionProducto>();
    }
    
    /**
     * Optimizar esta funcion para tener en un mismo cache y no hacer varias consultas
     * @param codigo
     * @return 
     */
    public PresentacionProducto buscarPresentacionPorCodigo(String codigo)
    {
        List<PresentacionProducto> presentacionList= obtenerPresentacionesList();
        for (PresentacionProducto presentacion : presentacionList) 
        {
            if(presentacion!=null)
            {
                if(UtilidadesTextos.compararTextosSinSensibilidad(presentacion.getNombre(),codigo))
                {
                    return presentacion;
                }
            }
        }
        return null;
    }

    /**
     * Obtiene una presentacion cualquiera que sea disntinta de la ingresada en el producto
     * @return
     */
    /*public ProductoPresentacionDetalle obtenerProductoPresentacionPorDefecto()
    {
    if(presentacionList!=null)
    {
    for (ProductoPresentacionDetalle detallePresentacion : presentacionList)
    {
    PresentacionProducto presentacionProducto= detallePresentacion.getPresentacionProducto();
    if(presentacionProducto!=null)
    {
    if(!presentacionProducto.equals(presentacion))
    {
    return detallePresentacion;
    }
    }
    }
    }
    return null;
    }*/
    @Override
    protected Producto clone() throws CloneNotSupportedException {
        return (Producto)super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    

    @Deprecated
    // ALERTA: No usar este metodo desde el servidor porque puede causar lentitud en los procesos
    // solo utilizar desde los clientes
    // Este metodo se va a utilizar para obtener el kardex aunque se llame desde un empaque
    public Kardex obtenerKardexOriginal()
    {
        try {
            Kardex kardex=ServiceFactory.getFactory().getKardexServiceIf().buscarKardexPrincipal(this);
            return kardex;
        } catch (RemoteException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    
    
    public static class PrecioVenta implements Serializable{
        public static final String PV1="pv1";
        public static final String PV2="pv2";
        public static final String PV3="pv3";
        public static final String PV4="pv4";
        public static final String PV5="pv5";
        public static final String PV6="pv6";
        
        public String alias;
        public BigDecimal precio;
        public Integer numero;

        @Override
        public String toString() {
            return alias;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public BigDecimal getPrecio() {
            return precio;
        }

        public void setPrecio(BigDecimal precio) {
            this.precio = precio;
        }

        public Integer getNumero() {
            return numero;
        }

        public void setNumero(Integer numero) {
            this.numero = numero;
        }
        
        
        
        public static List<String> getListadoPrecios()
        {
            List<String> precioList=new ArrayList<String>();
            precioList.add(PV1);
            precioList.add(PV2);
            precioList.add(PV3);
            
            precioList.add(PV4);
            precioList.add(PV5);
            precioList.add(PV6);
            return precioList;
            
        }
        
        public static String buscaTextoPorPosicion(Integer numeroPvp)
        {
            if(numeroPvp!=null)
            {
                switch(numeroPvp)
                {
                    case 1:return PrecioVenta.PV1;
                    case 2:return PrecioVenta.PV2;
                    case 3:return PrecioVenta.PV3;
                    case 4:return PrecioVenta.PV4;
                    case 5:return PrecioVenta.PV5;
                    case 6:return PrecioVenta.PV6;
                }
            }
            return null;
        }
        
        public static Integer buscarPosicionPorTexto(String pvp)
        {
            if(!UtilidadesTextos.verificarNullOVacio(pvp))
            {
                switch(pvp)
                {
                    case PrecioVenta.PV1: return 1;
                    case PrecioVenta.PV2: return 2;
                    case PrecioVenta.PV3: return 3;
                    case PrecioVenta.PV4: return 4;
                    case PrecioVenta.PV5: return 5;
                    case PrecioVenta.PV6: return 6;
                }
            }
            return -1;
        }
        
        

    }
    
    
}
