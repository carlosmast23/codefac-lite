/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FacturaEnumEstado;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "FACTURA")
public class Factura extends Comprobante implements Serializable {    
    public static final String ESTADO_FACTURADO="F";
    public static final String ESTADO_ANULADO="A";
    public static final String ESTADO_PENDIENTE_FACTURA_ELECTRONICA="P";
    
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TIPO_IDENTIFICACION_ID")
    private Long tipoClienteId;
   
    /**
     * Valor del descuento de los productos que cobran iva
     */
    @Column(name = "DESCUENTO_IVA")
    private BigDecimal descuentoImpuestos;
    /**
     * Valor del descuento de los productos que no cobran iva
     */    
    @Column(name = "DESCUENTO_IVA_CERO")
    private BigDecimal descuentoSinImpuestos;
    
    
    @Column(name = "SUBTOTAL_IVA")
    private BigDecimal subtotalImpuestos;
    
    @Column(name = "SUBTOTAL_IVA_CERO")
    private BigDecimal subtotalSinImpuestos;
    
    /**
     * Valor del iva cobrado
     */
    @Column(name = "IVA")
    private BigDecimal iva;

    @Column(name = "IVA_SRI_ID")
    private Long ivaSriId;
    
    @Column(name = "TOTAL")
    private BigDecimal total;
  
    @Column(name = "TIPO_FACTURACION")
    private String tipoFacturacion;
    
    @Column(name = "CODIGO_DOCUMENTO")
    private String codigoDocumento;
    
    @Column(name = "ESTADO_NOTA_CREDITO")
    private String estadoNotaCredito;
    
    @JoinColumn(name = "CLIENTE_ID")
    @ManyToOne    
    private Persona cliente;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factura",fetch = FetchType.EAGER)
    private List<FacturaDetalle> detalles;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factura",fetch = FetchType.EAGER)
    private List<FormaPago> formaPagos;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factura",fetch = FetchType.EAGER)
    private List<FacturaAdicional> datosAdicionales;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factura", fetch = FetchType.EAGER)
    private List<NotaCredito> notasCredito;

    public Factura() {
    }
    
    
    public Long getId() {
        return id;
    }


    public Long getTipoClienteId() {
        return tipoClienteId;
    }
    

    public Long getIvaSriId() {
        return ivaSriId;
    }

    public BigDecimal getTotal() {
        return total;
    }


    public Persona getCliente() {
        return cliente;
    }
    
    

    public void setId(Long id) {
        this.id = id;
    }



    public void setTipoClienteId(Long tipoClienteId) {
        this.tipoClienteId = tipoClienteId;
    }


    public void setIvaSriId(Long ivaSriId) {
        this.ivaSriId = ivaSriId;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }


    public void setCliente(Persona cliente) {
        this.cliente = cliente;
    }

    public List<FacturaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<FacturaDetalle> detalles) {
        this.detalles = detalles;
    }

    public BigDecimal getDescuentoImpuestos() {
        return descuentoImpuestos;
    }

    public void setDescuentoImpuestos(BigDecimal descuentoImpuestos) {
        this.descuentoImpuestos = descuentoImpuestos;
    }

    public BigDecimal getDescuentoSinImpuestos() {
        return descuentoSinImpuestos;
    }

    public void setDescuentoSinImpuestos(BigDecimal descuentoSinImpuestos) {
        this.descuentoSinImpuestos = descuentoSinImpuestos;
    }

    public BigDecimal getSubtotalImpuestos() {
        return subtotalImpuestos;
    }

    public void setSubtotalImpuestos(BigDecimal subtotalImpuestos) {
        this.subtotalImpuestos = subtotalImpuestos;
    }

    public BigDecimal getSubtotalSinImpuestos() {
        return subtotalSinImpuestos;
    }

    public void setSubtotalSinImpuestos(BigDecimal subtotalSinImpuestos) {
        this.subtotalSinImpuestos = subtotalSinImpuestos;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public List<FormaPago> getFormaPagos() {
        return formaPagos;
    }

    public void setFormaPagos(List<FormaPago> formaPagos) {
        this.formaPagos = formaPagos;
    }

    public String getTipoFacturacion() {
        return tipoFacturacion;
    }

    public void setTipoFacturacion(String tipoFacturacion) {
        this.tipoFacturacion = tipoFacturacion;
    }

    public String getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(String codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public List<FacturaAdicional> getDatosAdicionales() {
        return datosAdicionales;
    }

    public void setDatosAdicionales(List<FacturaAdicional> datosAdicionales) {
        this.datosAdicionales = datosAdicionales;
    }

    public String getEstadoNotaCredito() {
        return estadoNotaCredito;
    }

    public void setEstadoNotaCredito(String estadoNotaCredito) {
        this.estadoNotaCredito = estadoNotaCredito;
    }
    
        
    
    /**
     * Informacion adicional
     */
    
    public FacturaEnumEstado getEstadoEnum()
    {
        return FacturaEnumEstado.getEnum(estado);
    }
    
    public EstadoNotaCreditoEnum getEstadoNotaCreditoEnum()
    {
        return EstadoNotaCreditoEnum.getEnum(estadoNotaCredito);
    }
            
    public void addDetalle(FacturaDetalle detalle)
    {
        if(this.detalles==null)
        {
            this.detalles=new ArrayList<FacturaDetalle>();
        }
        detalle.setFactura(this);
        this.detalles.add(detalle);
        
    }
    
     /**
     * Formas de pago adicional
     */
    public void addFormaPago(FormaPago formaPago)
    {
        if(this.formaPagos==null)
        {
            this.formaPagos=new ArrayList<FormaPago>();
        }
        formaPago.setFactura(this);
        this.formaPagos.add(formaPago);
        
    }
    
    public void addDatoAdicional(String campo, String valor)
    {
        FacturaAdicional dato=new FacturaAdicional();
        dato.setCampo(campo);
        dato.setNumero(0);
        dato.setTipo(FacturaAdicional.Tipo.TIPO_OTRO.getLetra());
        dato.setValor(valor);
        
        addDatoAdicional(dato);
    }
    
    public void addDatoAdicional(FacturaAdicional datoAdicional)
    {
        if(this.datosAdicionales==null)
        {
            this.datosAdicionales=new ArrayList<FacturaAdicional>();
        }
        datoAdicional.setFactura(this);
        this.datosAdicionales.add(datoAdicional);
    }
    
    public void addDatosAdicionalCorreo(String correo)
    {
        FacturaAdicional facturaAdicional=new FacturaAdicional();
        facturaAdicional.setCampo(FacturaAdicional.NOMBRE_CORREO);
        facturaAdicional.setFactura(this);
        facturaAdicional.setTipo(FacturaAdicional.Tipo.TIPO_CORREO.getLetra());
        facturaAdicional.setValor(correo);
        
        if (this.datosAdicionales == null) {
            this.datosAdicionales = new ArrayList<FacturaAdicional>();
        }
        
        //Buscar si existe un correo anterior para nombrar de forma secuencial
        Integer numeroMaximo=0;
        for (FacturaAdicional datoAdicional : datosAdicionales) {            
            if(datoAdicional.getTipo().equals(FacturaAdicional.Tipo.TIPO_CORREO.getLetra()))
            {
                if(datoAdicional.getNumero()>numeroMaximo)
                {
                    numeroMaximo=datoAdicional.getNumero();
                }
            }
        }
        
        facturaAdicional.setNumero(numeroMaximo+1);
        //Modificar el nombre si el correo es mas de 2
        if(facturaAdicional.getNumero()>1)
        {
            facturaAdicional.setCampo(FacturaAdicional.NOMBRE_CORREO+" "+facturaAdicional.getNumero());
        }

        this.datosAdicionales.add(facturaAdicional);
    
    }
    
    public FacturaAdicional obtenerDatoAdicionalPorCampo(String nombre)
    {
        if(this.datosAdicionales!=null)
        {
            for (FacturaAdicional facturaAdicional : datosAdicionales) {
                if(facturaAdicional.getCampo().equals(nombre))
                {
                    return facturaAdicional;
                }
            }
        }
        return null;
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
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
        final Factura other = (Factura) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
   
    
    
    
    public enum EstadoNotaCreditoEnum
    {
        /**
         * Estado por defecto cuando no aplique ninguna nota de credito a la factura
         */
        SIN_ANULAR("N","Sin anular"),
        /**
         * Estado anulado cuando una nota de credito anulo totalmente la factura
         */
        ANULADO_TOTAL("A", "Anulado Total"),
        /**
         * Estado cuando aplica una nota de credito pero no sobre el total de la
         * factura
         */
        ANULADO_PARCIAL("P", "Anulado Parcial");
        
        

        private EstadoNotaCreditoEnum(String estado, String nombre) {
            this.estado = estado;
            this.nombre = nombre;
        }

        private String estado;
        private String nombre;

        public String getEstado() {
            return estado;
        }

        public String getNombre() {
            return nombre;
        }
        
        public static EstadoNotaCreditoEnum getEnum(String estado) {

            for (EstadoNotaCreditoEnum enumerador : EstadoNotaCreditoEnum.values()) {
                if (enumerador.getEstado().equals(estado)) {
                    return enumerador;
                }
            }
            return null;
        }
        
        
    
    }
            
    
}
