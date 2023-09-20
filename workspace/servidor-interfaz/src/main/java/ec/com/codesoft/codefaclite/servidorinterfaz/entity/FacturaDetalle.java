/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

//import com.sun.imageio.plugins.common.BogusColorSpace;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.validadores.UtilidadBigDecimal;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadVarios;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesImpuestos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Singleton;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "FACTURA_DETALLE")
public class FacturaDetalle extends DetalleFacturaNotaCeditoAbstract implements Cloneable{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(name = "PRODUCTO_ID")
    //private Long productoId;
   



    @JoinColumn(name = "FACTURA_ID")
    @ManyToOne(optional = false)
    private Factura factura;
    
    @JoinColumn(name = "RESPONSABLE_ID")
    private Empleado responsable;


    @Column(name = "PRECIO_SIN_SUBSIDIO")
    private BigDecimal precioSinSubsidio; 
    
    @Column(name = "RESERVADO")
    private String reservado;
    
    @Column(name = "NUMERO_PVP")
    private Integer numeroPvp;
    
    
    public FacturaDetalle() {
        
    }
    
    //TODO: Hacer algunas pruebas para ver que no genere errores
    @Deprecated
    public FacturaDetalle(FacturaDetalle facturaDetalle) 
    {
        /*this.factura=facturaDetalle.factura;
        this.id=facturaDetalle.id;
        this.numeroPvp=facturaDetalle.numeroPvp;
        this.precioSinSubsidio=facturaDetalle.precioSinSubsidio;
        this.reservado=facturaDetalle.reservado;
        this.responsable=facturaDetalle.responsable;        */
        UtilidadVarios.copiarObjetos(facturaDetalle,this);
        //this.factura=facturaDetalle.factura;
    }

    public Long getId() {
        return id;
    }

    

    public void setId(Long id) {
        this.id = id;
    }

    
    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public BigDecimal getPrecioSinSubsidio() {
        return precioSinSubsidio;
    }

    public void setPrecioSinSubsidio(BigDecimal precioSinSubsidio) {
        this.precioSinSubsidio = precioSinSubsidio;
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
        if(reservadoEnum!=null)
        {
            this.reservado = reservadoEnum.getLetra();
        }
    }

    public Empleado getResponsable() {
        return responsable;
    }

    public void setResponsable(Empleado responsable) {
        this.responsable = responsable;
    }

    public Integer getNumeroPvp() {
        return numeroPvp;
    }

    public void setNumeroPvp(Integer numeroPvp) {
        this.numeroPvp = numeroPvp;
    }
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final FacturaDetalle other = (FacturaDetalle) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    /**
     * Esto proceso permite reinvertir los calculos cuando ya se tiene un detalle incluido el iva
     */
    public void invertirCalculoNVIaFactura()
    {
        CatalogoProducto catalogoProducto= this.getCatalogoProducto();
        System.out.println("IVA: "+this.getIvaPorcentaje());
        //Si el producto original tiene IVA y el iva temporal del detalle es CERO entonces proceso a realizar el proceso inverso de cambiar el iva 
        if(this.getIvaPorcentaje()==0 && catalogoProducto.getIva().getTarifa()>0)
        {
            //Guardo el precio anterior
            BigDecimal precioUnitarioTmp=new BigDecimal(this.getPrecioUnitario()+"");
            //Obtengo el nuevo precio sin iva
            this.setPrecioUnitario(UtilidadesImpuestos.quitarValorIva(ParametrosSistemaCodefac.obtenerIvaDefecto(),this.getPrecioUnitario(), 4));
            
            //Seteo el valor del IVA, que en este caso es la resta del valor anterior y el valor nuevo
            this.setIva(precioUnitarioTmp.subtract(this.getPrecioUnitario()));
            
            //Seteo que el producto si debe llevar IVA
            this.setIvaPorcentaje(ParametrosSistemaCodefac.obtenerIvaDefecto().intValue());
            
        }
        
    }

    /**
     * ====================> METODOS PERSONALIZADOS <===================== *
     */
    
    public BigDecimal calcularSubsidio()
    {
        if(precioSinSubsidio!=null)
        {
            return precioSinSubsidio.subtract(getPrecioUnitario()).multiply(getCantidad());
        }
        return BigDecimal.ZERO;
    }
    
    /**
     * Metodos adicionales
     * TODO: Poner un nombre más facil como getProducto o obtenerProducto
     */
    
    public Producto consultarProductoEnlazado()
    {
        TipoDocumentoEnum tipoDocumentoEnum=getTipoDocumentoEnum();
        if(tipoDocumentoEnum!=null && (tipoDocumentoEnum.equals(tipoDocumentoEnum.INVENTARIO) || tipoDocumentoEnum.equals(TipoDocumentoEnum.LIBRE)))
        {
            try {
                return ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(getReferenciaId());
            } catch (RemoteException ex) {
                Logger.getLogger(FacturaDetalle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    
    
    

    /**
     * El total bruto se refiere al subtotal incluido el descuento
     * @return 
     */
    /*public BigDecimal calcularTotalBruto()
    {
        return getCantidad().multiply(getPrecioUnitario()).add(getDescuento());
    }*/
    

    
}
