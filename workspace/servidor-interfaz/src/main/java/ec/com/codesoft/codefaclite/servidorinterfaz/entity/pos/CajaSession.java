/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaSessionEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.SignoEnum;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Robert
 */
@Entity
@Table(name = "CAJA_SESSION")
@XmlRootElement
public class CajaSession implements Serializable
{
    /*
    * Atributos
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_HORA_APERTURA")
    private Timestamp fechaHoraApertura;
    @Column(name = "FECHA_HORA_CIERRE")
    private Timestamp fechaHoraCierre;
    
    @Column(name = "VALOR_APERTURA")
    private BigDecimal valorApertura;
    @Column(name = "VALOR_CIERRE")
    private BigDecimal valorCierre;
    @Column(name = "VALOR_CIERRE_REAL")
    private BigDecimal valorCierreReal;
    @Column(name = "ESTADO_CIERRE_CAJA")
    private String estadoCierreCaja;
    @Column(name = "OBSERVACION_CIERRE")
    private String observacionCierreCaja;
    
    //Datos adicionales para grabar las monedas
    @Column(name = "MONEDA_1CTV")
    private Integer moneda1ctv;
    @Column(name = "MONEDA_5CTV")
    private Integer moneda5ctv;    
    @Column(name = "MONEDA_10CTV")
    private Integer moneda10ctv;
    @Column(name = "MONEDA_25CTV")
    private Integer moneda25ctv;
    @Column(name = "MONEDA_50CTV")
    private Integer moneda50ctv;
    @Column(name = "MONEDA_1USD")
    private Integer moneda1dolar;
    
    @Column(name = "BILLETE_1USD")
    private Integer billete1;
    @Column(name = "BILLETE_5USD")
    private Integer billete5;
    @Column(name = "BILLETE_10USD")
    private Integer billete10;
    @Column(name = "BILLETE_20USD")
    private Integer billete20;
    @Column(name = "BILLETE_50USD")
    private Integer billete50;
    @Column(name = "BILLETE_100USD")
    private Integer billete100;
    
    
    
    /*
    * Foreign Key
    */
    @JoinColumn(name = "CAJA_ID")
    @ManyToOne
    private Caja caja;
    
    @JoinColumn(name = "USUARIO_ID")
    @ManyToOne
    private Usuario usuario;
    
    /*@JoinColumn(name = "VENTA_ID")
    @ManyToOne
    private Venta venta;*/
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cajaSession", fetch = FetchType.EAGER)
    private List<ArqueoCaja> arqueosCaja;
    
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "cajaSession", fetch = FetchType.EAGER)
    //private List<IngresoCaja> ingresosCaja;
    /*
    * Constructor
    */
    public CajaSession() {
    }
    
    /*
    * Get and Set
    */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getFechaHoraApertura() {
        return fechaHoraApertura;
    }

    public void setFechaHoraApertura(Timestamp fechaHoraApertura) {
        this.fechaHoraApertura = fechaHoraApertura;
    }

    public Timestamp getFechaHoraCierre() {
        return fechaHoraCierre;
    }

    public void setFechaHoraCierre(Timestamp fechaHoraCierre) {
        this.fechaHoraCierre = fechaHoraCierre;
    }

    public BigDecimal getValorApertura() {
        return valorApertura;
    }

    public void setValorApertura(BigDecimal valorApertura) {
        this.valorApertura = valorApertura;
    }

    public BigDecimal getValorCierre() {
        return valorCierre;
    }

    public void setValorCierre(BigDecimal valorCierre) {
        this.valorCierre = valorCierre;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getValorCierreReal() {
        return valorCierreReal;
    }

    public void setValorCierreReal(BigDecimal valorCierreReal) {
        this.valorCierreReal = valorCierreReal;
    }

    public String getObservacionCierreCaja() {
        return observacionCierreCaja;
    }

    public void setObservacionCierreCaja(String observacionCierreCaja) {
        this.observacionCierreCaja = observacionCierreCaja;
    }

    
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getMoneda1ctv() {
        return moneda1ctv;
    }

    public void setMoneda1ctv(Integer moneda1ctv) {
        this.moneda1ctv = moneda1ctv;
    }

    public Integer getMoneda5ctv() {
        return moneda5ctv;
    }

    public void setMoneda5ctv(Integer moneda5ctv) {
        this.moneda5ctv = moneda5ctv;
    }

    public Integer getMoneda10ctv() {
        return moneda10ctv;
    }

    public void setMoneda10ctv(Integer moneda10ctv) {
        this.moneda10ctv = moneda10ctv;
    }
    
    
    public Integer getMoneda25ctv() {
        return moneda25ctv;
    }

    public void setMoneda25ctv(Integer moneda25ctv) {
        this.moneda25ctv = moneda25ctv;
    }

    public Integer getMoneda50ctv() {
        return moneda50ctv;
    }

    public void setMoneda50ctv(Integer moneda50ctv) {
        this.moneda50ctv = moneda50ctv;
    }

    public Integer getMoneda1dolar() {
        return moneda1dolar;
    }

    public void setMoneda1dolar(Integer moneda1dolar) {
        this.moneda1dolar = moneda1dolar;
    }

    public Integer getBillete1() {
        return billete1;
    }

    public void setBillete1(Integer billete1) {
        this.billete1 = billete1;
    }

    public Integer getBillete5() {
        return billete5;
    }

    public void setBillete5(Integer billete5) {
        this.billete5 = billete5;
    }

    public Integer getBillete10() {
        return billete10;
    }

    public void setBillete10(Integer billete10) {
        this.billete10 = billete10;
    }

    public Integer getBillete20() {
        return billete20;
    }

    public void setBillete20(Integer billete20) {
        this.billete20 = billete20;
    }

    public Integer getBillete50() {
        return billete50;
    }

    public void setBillete50(Integer billete50) {
        this.billete50 = billete50;
    }

    public Integer getBillete100() {
        return billete100;
    }

    public void setBillete100(Integer billete100) {
        this.billete100 = billete100;
    }

    
    
    
    
    public CajaEnum getEstadoEnum(){
        return CajaEnum.getEnum(estado);
    }
    
    public void setEstadoEnum(CajaEnum estadoCajaEnum){
        if(estadoCajaEnum == null)
        {
            this.estado = null;
        }
        else
        {
            this.estado = estadoCajaEnum.getEstado();
        }
    }
            
     public String getEstadoCierreCaja() {
        return estadoCierreCaja;
    }

    public void setEstadoCierreCaja(String estadoCierreCaja) {
        this.estadoCierreCaja = estadoCierreCaja;
    }
    
    public CajaSessionEnum getEstadoSessionEnum()
    {
        return CajaSessionEnum.getEnum(estadoCierreCaja);
    }
    
    public void setEstadoSessionEnum(CajaSessionEnum estadoCajaSessionEnum)
    {
        if(estadoCajaSessionEnum == null)
        {
            estadoCierreCaja = null;
        }
        else
        {
            estadoCierreCaja = estadoCajaSessionEnum.getEstado();
        }
    }

    public List<ArqueoCaja> getArqueosCaja() {
        return arqueosCaja;
    }

    public void setArqueosCaja(List<ArqueoCaja> arqueosCaja) {
        this.arqueosCaja = arqueosCaja;
    }

    @Deprecated //TODO: Las entidades no deberian hacer consulta a la base de datos, pero resulta usar de esta forma más practico    
    public List<IngresoCaja> getIngresosCaja() 
    {
        List<IngresoCaja> resultadoList=new ArrayList<IngresoCaja>();
        try {
            resultadoList=ServiceFactory.getFactory().getIngresoCajaServiceIf().consultarPorCajaSession(this);
            
            //return ingresosCaja;            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(CajaSession.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(CajaSession.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultadoList;
    }

    /*public void setIngresosCaja(List<IngresoCaja> ingresosCaja) {
        this.ingresosCaja = ingresosCaja;
    }
    
    public void addIngresoCaja(IngresoCaja ingresoCaja){
        if(this.ingresosCaja == null)
        {
            this.ingresosCaja = new ArrayList<>();
        }
        
        ingresoCaja.setCajaSession(this);
        this.ingresosCaja.add(ingresoCaja);
    }*/
    
    /*
    * Equals
    */

    @Override
    public int hashCode() {
        int hash = 7;
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
        final CajaSession other = (CajaSession) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return caja.getNombre()+" - "+usuario.getNick();
    }
    
    /**
     * Metodos Personalizados
     */
    public BigDecimal calcularValorCierreTeorico()
    {
        BigDecimal totalVentas = BigDecimal.ZERO;
        totalVentas = getValorApertura();

        if (totalVentas == null) {
            totalVentas = BigDecimal.ZERO;
        }

        //Todo: mejorar esta parte 
        //List<IngresoCaja> ingresoCajaList=new ArrayList<IngresoCaja>();
        List<IngresoCaja> ingresoCajaList=getIngresosCaja();
        
        
        if(ingresoCajaList!=null)
        {
            for (IngresoCaja ingresoCaja : ingresoCajaList) 
            {
                if(ingresoCaja.getFactura()!=null)
                {
                    if(!ingresoCaja.getFactura().getEstadoEnum().equals(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO) && !ingresoCaja.getFactura().getEstadoEnum().equals(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO_SRI) )
                    {
                        //Verificar si fue anulado con NOTA DE CREDITO para igual no tomar en cuenta por el momento
                        //if(!Factura.EstadoNotaCreditoEnum.ANULADO_TOTAL.equals(ingresoCaja.getFactura().getEstadoNotaCreditoEnum()))
                        //{
                            for (FormaPago formaPago : ingresoCaja.getFactura().getFormaPagos()) 
                            {
                                //Solo agregar las formas de pago en efectivo
                                if(formaPago.getSriFormaPago().getAlias().equals(SriFormaPago.ALIAS_FORMA_PAGO_EFECTIVO))
                                {
                                    //System.out.println("Efectivo :"+ingre);
                                    if(ingresoCaja.getSignoIngresoEnum().equals(SignoEnum.POSITIVO))
                                    {
                                        totalVentas = totalVentas.add(formaPago.getTotal());
                                    }
                                    else
                                    {
                                        totalVentas = totalVentas.subtract(formaPago.getTotal());
                                    }
                                }                        
                            }
                        //}

                    }
                }
                else if(ingresoCaja.getCompra()!=null) //En el caso de compras debe restar el valor
                {
                    if(!ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado().equals(ingresoCaja.getCompra().getEstado()))
                    {
                        //TODO: Por el momento no considera forma de pago, solo asumo que fue en efectivo
                        totalVentas=totalVentas.subtract(ingresoCaja.getCompra().getTotal());
                    }
                    
                }
                else if(ingresoCaja.getCartera()!=null)
                {
                    if(ingresoCaja.getCartera().getEstadoEnum().equals(ingresoCaja.getCartera().getEstadoEnum()))
                    {
                        
                    }
                }
            }
        }
        
        return totalVentas;
    }
}
