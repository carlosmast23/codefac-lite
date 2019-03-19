/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional.CampoDefectoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
public class Factura extends ComprobanteEntity implements Serializable {

    private static final long serialVersionUID = -1238278914412853684L;
    
    
    //public static final String ESTADO_FACTURADO="F";
    //public static final String ESTADO_ANULADO="A";
    //public static final String ESTADO_PENDIENTE_FACTURA_ELECTRONICA="P";
    
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(name = "TIPO_IDENTIFICACION_ID")
    //private Long tipoClienteId;
   
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

    //@Column(name = "IVA_SRI_ID")
    @JoinColumn(name = "IVA_SRI_ID")
    private ImpuestoDetalle ivaSriId;
    
    @Column(name = "TOTAL")
    private BigDecimal total;
  
    @Column(name = "ESTADO_NOTA_CREDITO")
    private String estadoNotaCredito;
    
    @Column(name = "TIPO_IDENTIFICACION_CODIGO_SRI")
    private String tipoIdentificacionCodigoSri;
    
    @Column(name = "ESTADO_ENVIADO_GUIA_REMISION")
    private String estadoEnviadoGuiaRemision;
    
    @Column(name = "FECHA_VENCIMIENTO_FACTURA")
    protected Date fechaVencimiento;
    
    @JoinColumn(name = "CLIENTE_ID")
    @ManyToOne    
    private Persona cliente;
    
    @JoinColumn(name = "REFERIDO_ID")
    @ManyToOne    
    private Persona referido;
    
    @JoinColumn(name = "TIPO_IDENTIFICACION_ID")
    private SriIdentificacion sriIdentificacion;
    
    @JoinColumn(name = "VENDEDOR_ID")
    private Empleado vendedor;
    
    /**
     * Se refiere a la sucucursal del cliente
     */
    @JoinColumn(name = "SUCURSAL_ID")
    private PersonaEstablecimiento sucursal;
        
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



    public ImpuestoDetalle getIvaSriId() {
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



    public void setIvaSriId(ImpuestoDetalle ivaSriId) {
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

    public Persona getReferido() {
        return referido;
    }

    public void setReferido(Persona referido) {
        this.referido = referido;
    }

    public SriIdentificacion getSriIdentificacion() {
        return sriIdentificacion;
    }

    public void setSriIdentificacion(SriIdentificacion sriIdentificacion) {
        this.sriIdentificacion = sriIdentificacion;
    }

    public String getTipoIdentificacionCodigoSri() {
        return tipoIdentificacionCodigoSri;
    }

    public void setTipoIdentificacionCodigoSri(String tipoIdentificacionCodigoSri) {
        this.tipoIdentificacionCodigoSri = tipoIdentificacionCodigoSri;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getEstadoEnviadoGuiaRemision() {
        return estadoEnviadoGuiaRemision;
    }

    public void setEstadoEnviadoGuiaRemision(String estadoEnviadoGuiaRemision) {
        this.estadoEnviadoGuiaRemision = estadoEnviadoGuiaRemision;
    }
    
    public EnumSiNo getEstadoEnviadoGuiaRemisionEnum() {
        return EnumSiNo.getEnumByLetra(estadoEnviadoGuiaRemision);
    }

    public void setEstadoEnviadoGuiaRemisionEnum(EnumSiNo estadoEnviadoGuiaRemisionEnum) {
        this.estadoEnviadoGuiaRemision = estadoEnviadoGuiaRemisionEnum.getLetra();
    }

    public PersonaEstablecimiento getSucursal() {
        return sucursal;
    }

    public void setSucursal(PersonaEstablecimiento sucursal) {
        this.sucursal = sucursal;
    }

    
    
    
    /**
     * Informacion adicional
     */
    
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
    
    public BigDecimal getTotalFormasPago()
    {
        BigDecimal totalFormasPago = BigDecimal.ZERO;
        int res;
        if (formaPagos!= null) {
            for (FormaPago fp : formaPagos) {
                totalFormasPago = totalFormasPago.add(fp.getTotal());
            }
        }
        return totalFormasPago;
        //totalFormasPago = totalFormasPago.add(valorTotalFormaDePago);
    
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
    
    public void addDatosAdicionalCorreo(String correo,FacturaAdicional.Tipo tipoCorreo,FacturaAdicional.CampoDefectoEnum campoDefecto)
    {
        FacturaAdicional facturaAdicional=new FacturaAdicional();
        facturaAdicional.setCampo(campoDefecto.getNombre());
        facturaAdicional.setFactura(this);
        facturaAdicional.setTipo(tipoCorreo.getLetra());
        facturaAdicional.setValor(correo);
        
        if (this.datosAdicionales == null) {
            this.datosAdicionales = new ArrayList<FacturaAdicional>();
        }
        
        //Buscar si existe un correo anterior para nombrar de forma secuencial
        Integer numeroMaximo=0;
        for (FacturaAdicional datoAdicional : datosAdicionales) {            
            if(datoAdicional.getTipo().equals(tipoCorreo.getLetra()))
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
            facturaAdicional.setCampo(campoDefecto.getNombre()+" "+facturaAdicional.getNumero());
        }

        this.datosAdicionales.add(facturaAdicional);
    
    }
    
    public FacturaAdicional obtenerDatoAdicionalPorCampo(CampoDefectoEnum campo)
    {
        if(this.datosAdicionales!=null)
        {
            for (FacturaAdicional facturaAdicional : datosAdicionales) {
                if(facturaAdicional.getCampo().equals(campo.getNombre()))
                {
                    return facturaAdicional;
                }
            }
        }
        return null;
    }
    
    /**
     * Elimina datos adicionales de la factura como correos o codigos de enlace de los documentos
     */
    public void eliminarTodosDatosAdicionales()
    {
        if(this.datosAdicionales!=null)
        {
            this.datosAdicionales.clear();
        }
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
   
    public void calcularTotalesDesdeDetalles()
    {
        //Solo calcular si la variables de detalles fue creada
        if(detalles==null || detalles.size()==0)
        {
            this.total=BigDecimal.ZERO;
            this.descuentoSinImpuestos=BigDecimal.ZERO;
            this.descuentoImpuestos=BigDecimal.ZERO;
            this.subtotalSinImpuestos=BigDecimal.ZERO;            
            this.subtotalSinImpuestos=BigDecimal.ZERO;
            this.iva=BigDecimal.ZERO;
            return;
        }
        
        BigDecimal total=BigDecimal.ZERO; //total de la factura
        
        BigDecimal subTotalSinImpuestos=BigDecimal.ZERO;//Sin el descuento
        BigDecimal subTotalConImpuestos=BigDecimal.ZERO;//Sin los descuentos
        
        BigDecimal descuentoSinImpuestos=BigDecimal.ZERO; //
        BigDecimal descuentoConImpuestos=BigDecimal.ZERO; //
        
        BigDecimal impuestoIva=BigDecimal.ZERO; //
        
        BigDecimal ivaDecimal=BigDecimal.ZERO; //Todo: Variable donde se almacena el iva de uno de los detalles (pero si tuviera varias ivas distintos de 0 , se generaria poroblemas)
        
        for (FacturaDetalle detalle : detalles) {
            //Sumar los subtotales
            if(detalle.getIvaPorcentaje().equals(0))
            {
                subTotalSinImpuestos=subTotalSinImpuestos.add(detalle.getPrecioUnitario().multiply(detalle.getCantidad()));
                descuentoSinImpuestos=descuentoSinImpuestos.add(detalle.getDescuento());
            }
            else
            {                
                subTotalConImpuestos=subTotalConImpuestos.add(detalle.getPrecioUnitario().multiply(detalle.getCantidad()));
                descuentoConImpuestos=descuentoConImpuestos.add(detalle.getDescuento());
                
                ivaDecimal=new BigDecimal(detalle.getIvaPorcentaje().toString()).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP);
                impuestoIva=subTotalConImpuestos.subtract(descuentoConImpuestos).multiply(ivaDecimal);
            }
           
            
        }
        
        //Calcula el total de los totales
        total=subTotalSinImpuestos.subtract(descuentoSinImpuestos)
                .add(subTotalConImpuestos.subtract(descuentoConImpuestos))
                .add(impuestoIva);
        
       
        /**
         * Recalcular los valores partiendo del total para redondear con 2 cifras y que los valores cuadren
         */
        //total=total.setScale(2,BigDecimal.ROUND_HALF_UP);
        this.total=total.setScale(2,BigDecimal.ROUND_HALF_UP); //valor final con 2 decimales
        
        this.descuentoSinImpuestos=descuentoSinImpuestos.setScale(2,BigDecimal.ROUND_HALF_UP); //Este valor no se mueve porque debe ser fijo de 2 decimales segun el sri
        this.subtotalSinImpuestos=subTotalSinImpuestos.setScale(2,BigDecimal.ROUND_HALF_UP);// Este valor se redondea y tampoco ya no se mueve porque no interfiere con el iva donde se descuadra //TODO: PERO REVISAR ESTA AFIRMACION
        
        //---------------- CALCULOS PARA LOS VALORES CON IMPUESTAS QUE ES LA PARTE DONDE GENERAN PROBLEMAS ------------------------///
        BigDecimal ivaDecimalTmp=ivaDecimal.add(BigDecimal.ONE); //1.12 por ejemplo
        
        //Valor total solo de los valores que tienen impuestos
        BigDecimal totalConImpuestos=this.total.subtract(this.subtotalSinImpuestos).add(this.descuentoSinImpuestos); //esto ya tiene 2 decimales no debo redondear
        
        //Estos valores seteo directo porque solo pueden tener 2 decimales en los calculos y no deberia cambiar porque generarian confusion
        this.descuentoImpuestos = descuentoConImpuestos.setScale(2,BigDecimal.ROUND_HALF_UP);
        
        //Calculo el subtotal ya restado el descuento diviendo para 1.12 por ejemplo
        BigDecimal subtotalMenosImpuestos=totalConImpuestos.divide(ivaDecimalTmp,2,BigDecimal.ROUND_HALF_UP);
        
        //Al subtotal menos impuesto le sumo el descuento y ya tengo el subtotal original
        this.subtotalImpuestos=subtotalMenosImpuestos.add(this.descuentoImpuestos);
        
        //Calcular el iva de la resta del del total -subtotal
        this.iva=totalConImpuestos.subtract(subtotalMenosImpuestos);
 
    
    }

    public Empleado getVendedor() {
        return vendedor;
    }

    public void setVendedor(Empleado vendedor) {
        this.vendedor = vendedor;
    }
    
    
    
    /**
     * Metodo que permite saber si en la factura fueron ingresados correos
     * @return 
     */
    public boolean verificarExistenCorreosIngresados()
    {
        if(datosAdicionales!=null)
        {
            for (FacturaAdicional datosAdicional : datosAdicionales) {
                if(datosAdicional.getTipo().equals(FacturaAdicional.Tipo.TIPO_CORREO.getLetra()))
                {
                    if(datosAdicional.getValor()!=null && !datosAdicional.getValor().isEmpty())
                    {
                        return true;
                    }
                }
            }
        }
        return false;
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
    
    public GeneralEnumEstado getEnumEstadoProforma()
    {
        return GeneralEnumEstado.getEnum(estado);
    }
            
    
}
