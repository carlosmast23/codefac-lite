/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.SignoEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Robert
 */
@Entity
@Table(name = "INGRESO_CAJA")
@XmlRootElement
public class IngresoCaja implements Serializable
{
    /*
    * Atributos
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "VALOR")
    private BigDecimal valor;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "SECUENCIAL")
    private String secuencial;
    
    /*
     * Constructor
     */
    public IngresoCaja() {
    }
    /*
    * Foreing Key
    */
    //@JoinColumn(name = "CAJA_SESSION_ID")
    //@ManyToOne
    //private CajaSession cajaSession;
    @Column(name = "CAJA_SESSION_ID") 
    private Long cajaSessionId;
    
    @JoinColumn(name = "FACTURA_ID")
    @ManyToOne
    private Factura factura;
    
    @JoinColumn(name = "COMPRA_ID")
    @ManyToOne
    private Compra compra;
    
    //@JoinColumn(name = "CARTERA_ID")
    //@ManyToOne
    //private Cartera cartera;
    
    @Column(name = "CARTERA_ID") 
    private Long carteraId;
    
    @JoinColumn(name = "SRI_FORMA_PAGO_ID")
    @ManyToOne
    private SriFormaPago formaPago;
    
    @Column(name = "SIGNO")    
    private Integer signoIngreso;
    
    @Column(name = "FECHA_HORA")    
    private java.util.Date fechaHora;
    
    
    
    /*
    * Get and Set
    */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Deprecated
    public CajaSession getCajaSession() throws RemoteException {
        return ServiceFactory.getFactory().getCajaSesionServiceIf().buscarPorId(cajaSessionId);
        //return cajaSession;
    }

    public void setCajaSession(CajaSession cajaSession) {
        if(cajaSession!=null)
        {
            this.cajaSessionId = cajaSession.getId();
        }
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Integer getSignoIngreso() {
        return signoIngreso;
    }

    public void setSignoIngreso(Integer signoIngreso) {
        this.signoIngreso = signoIngreso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
    public SignoEnum getSignoIngresoEnum() {
        return SignoEnum.consultarPorValor(signoIngreso);
    }

    public void setSignoIngresoEnum(SignoEnum signoIngresoEnum) {
        this.signoIngreso = signoIngresoEnum.getValor();
    }

    //TODO: Metodo de forma temporal hasta poder encontrar una mejor manera de consultar
    @Deprecated    
    public Cartera getCartera() {
        Cartera cartera=null;
        try {
            cartera=ServiceFactory.getFactory().getCarteraServiceIf().buscarPorId(carteraId);
        } catch (RemoteException ex) {
            Logger.getLogger(IngresoCaja.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cartera;
    }

    //TODO: Solución temporal
    @Deprecated
    public void setCartera(Cartera cartera) {
        this.carteraId = cartera.getId();
    }
    

    public SriFormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(SriFormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public String getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(String secuencial) {
        this.secuencial = secuencial;
    }
    
    public BigDecimal obtenerValorConSigno()
    {
        return valor.multiply(getSignoIngresoEnum().getValorBigDecimal());
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Long getCarteraId() {
        return carteraId;
    }

    public void setCarteraId(Long carteraId) {
        this.carteraId = carteraId;
    }

    public Long getCajaSessionId() {
        return cajaSessionId;
    }

    public void setCajaSessionId(Long cajaSessionId) {
        this.cajaSessionId = cajaSessionId;
    }
    
    
    
    
   
    
    
    /*
    * Equals
    */

    @Override
    public int hashCode() {
        int hash = 3;
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
        final IngresoCaja other = (IngresoCaja) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
