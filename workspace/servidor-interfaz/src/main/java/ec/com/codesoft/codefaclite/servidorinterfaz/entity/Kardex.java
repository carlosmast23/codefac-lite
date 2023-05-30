package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.SignoEnum;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesImpuestos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "KARDEX")
public class Kardex implements Serializable,Cloneable {
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;
    
    @Column(name = "FECHA_MODIFICACION")
    private Date fechaModificacion;
    
    @Column(name = "PRECIO_PROMEDIO")
    private BigDecimal costoPromedio;
    
    /**
     * Almacena el ultimo valor de costo utilizado 
     */
    @Column(name = "PRECIO_ULTIMO")
    private BigDecimal precioUltimo;
    
    /**
     * TODO: Esta de revisar esta variable porque no tengo clara la utilidad,
     * Talvez al restar entre precios de ingresos y comprar me puede estar dando
     * la utilidad pero toca analizar
     */
    @Column(name = "PRECIO_TOTAL")
    private BigDecimal precioTotal;
    
    /**
     * Cantidad disponible del producto en esa bodega
     */
    @Column(name = "STOCK")
    private BigDecimal stock;
    
    /**
     * Variable que me permite almacenar stock reservado cuando se crean
     * ensambles
     */
    @Column(name = "RESERVA")            
    private BigDecimal reserva;
    
    @JoinColumn(name = "BODEGA_ID")
    @ManyToOne  
    private Bodega bodega;

    @JoinColumn(name = "PRODUCTO_ID")
    @ManyToOne     
    private Producto producto;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @JoinColumn(name = "LOTE_ID")
    @ManyToOne
    private Lote lote;    
    
    //Variable solo de paso para grabar los detalles que luego necesito para grabar o hacer algun tema temporal
    @Transient
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "kardex" ,fetch = FetchType.EAGER )
    private List<KardexDetalle> detallesKardex;

    public Kardex() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public BigDecimal getCostoPromedio() {
        return costoPromedio;
    }

    public void setCostoPromedio(BigDecimal costoPromedio) {
        this.costoPromedio = costoPromedio;
    }

    public BigDecimal getPrecioUltimo() {
        return precioUltimo;
    }

    public void setPrecioUltimo(BigDecimal precioUltimo) {
        this.precioUltimo = precioUltimo;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public Bodega getBodega() {
        return bodega;
    }

    public void setBodega(Bodega bodega) {
        this.bodega = bodega;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Deprecated
    public List<KardexDetalle> getDetallesKardexPersistencia() {
        try {
            //return detallesKardex;
            return ServiceFactory.getFactory().getKardexDetalleServiceIf().consultarPorKardex(this);
        } catch (RemoteException ex) {
            Logger.getLogger(Kardex.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(Kardex.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<KardexDetalle>();
    }

    /*public void setDetallesKardex(List<KardexDetalle> detallesKardex) {
        this.detallesKardex = detallesKardex;
    }*/

    public BigDecimal getReserva() {
        return reserva;
    }

    public void setReserva(BigDecimal reserva) {
        this.reserva = reserva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public GeneralEnumEstado getEstadoEnum() {
        return GeneralEnumEstado.getEnum(estado);
    }

    public void setEstadoEnum(GeneralEnumEstado estadoEnum) {
        this.estado = estadoEnum.getEstado();
    }
    
    
    
         /**
     * Formas de pago adicional
     */
    public void addDetalleKardex(KardexDetalle kardexDetalle)
    {
        if(this.detallesKardex==null)
        {
            this.detallesKardex=new ArrayList<KardexDetalle>();
        }
        kardexDetalle.setKardex(this);
        this.detallesKardex.add(kardexDetalle);
        
    }
    
    public void calcularPrecioTotal()
    {
        this.precioTotal=costoPromedio.multiply(stock);
    }
    
    /**
     * @deprecated 
     * @return 
     */
    public BigDecimal calcularPrecioPromedio()
    {
        //if(stock==0)
        if(stock.compareTo(BigDecimal.ZERO)==0)
        {
            return BigDecimal.ZERO;
        }
        else
        {
            return this.precioTotal.divide(this.stock,2,BigDecimal.ROUND_HALF_UP);
        }
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }
    
    public BigDecimal getPrecioUltimoConIva() {
        BigDecimal tarifa=getProducto().getTarifaIva();
        return UtilidadesImpuestos.agregarValorIva(tarifa,precioUltimo);
    }
    
    //TODO:Unificar los 2 metodos
    public BigDecimal obtenerTotalCajas()
    {
        ProductoPresentacionDetalle empaque=producto.buscarProductoPorNombrePresentacionLocal(PresentacionProducto.CAJA_PRESENTACION);
        if(empaque!=null)
        {
            BigDecimal totalCajas=stock.divide(empaque.getCantidad(),0,RoundingMode.HALF_UP);    
            return totalCajas;
        }
        return BigDecimal.ZERO;
    }
    
    //TODO:Unificar los 2 metodos
    public BigDecimal obtenerTotalUnidadesSueltas()
    {
        ProductoPresentacionDetalle empaque=producto.buscarProductoPorNombrePresentacionLocal(PresentacionProducto.CAJA_PRESENTACION);
        if(empaque!=null)
        {
            BigDecimal unidadesSueltas=stock.remainder(empaque.getCantidad());
            return unidadesSueltas;
        }
        return BigDecimal.ZERO;
    }
    
    public BigDecimal obtenerCantidadPorCaja()
    {
        ProductoPresentacionDetalle empaque=producto.buscarProductoPorNombrePresentacionLocal(PresentacionProducto.CAJA_PRESENTACION);
        if(empaque!=null)
        {
            return empaque.getCantidad();
        } 
        return BigDecimal.ZERO;
    }
    
    public BigDecimal recalcularStock()
    {
        BigDecimal stock=BigDecimal.ZERO;
        List<KardexDetalle> detallesKardex=getDetallesKardexPersistencia();
        if(detallesKardex!=null)
        {
            for (KardexDetalle kardexDetalle: detallesKardex) 
            {                
                Integer signo=kardexDetalle.getSigno();
                if(signo==null)
                {
                    signo=kardexDetalle.getCodigoTipoDocumentoEnum().getSignoInventarioNumero();
                }
                
                //System.out.println(signo+" > "+kardexDetalle.getCantidad());
                stock=stock.add(kardexDetalle.getCantidad().multiply(new BigDecimal(signo)));
            }
        }
        return stock;
    }
    
    public void procesarReserva(BigDecimal cantidadReserva,SignoEnum sigEnum)
    {
            BigDecimal stockActual=BigDecimal.ZERO;
            if(stock!=null)
            {
                stockActual=stock;
            }
            
            BigDecimal reservaActual=BigDecimal.ZERO;
            if(reserva!=null)
            {
                reservaActual=reserva;
            }
            
            if(sigEnum.equals(SignoEnum.POSITIVO))
            {
                stock=stockActual.subtract(cantidadReserva);
                reserva=reservaActual.add(cantidadReserva);
            }
            else if(sigEnum.equals(SignoEnum.NEGATIVO))
            {
                stock=stockActual.add(cantidadReserva);
                reserva=reservaActual.subtract(cantidadReserva);
            }
            
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final Kardex other = (Kardex) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
    
}
