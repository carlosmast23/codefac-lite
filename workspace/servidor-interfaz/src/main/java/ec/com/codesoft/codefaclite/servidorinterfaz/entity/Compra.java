/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.compra.OrdenCompra;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "COMPRA")
public class Compra implements Serializable {    
    //public static final String ESTADO_FACTURADO="F";
    //public static final String ESTADO_ANULADO="A";
    //public static final String ESTADO_PENDIENTE_FACTURA_ELECTRONICA="P";
    
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "CLAVE_ACCESO")
    private String claveAcceso;
    @Column(name = "EMPRESA_ID")
    private Long empresaId;
    @Column(name = "TIPO_IDENTIFICACION_ID")
    private Long tipoClienteId;
    @Column(name = "SECUENCIAL")
    private Integer secuencial;
    @Column(name = "PUNTO_ESTABLECIMIENTO")
    private String puntoEstablecimiento;
    @Column(name = "PUNTO_EMISION")
    private String puntoEmision;
    @Column(name = "FECHA_FACTURA")
    private Date fechaFactura;
    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;
    
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
    @Column(name = "USUARIO_ID")
    private Long usuarioId;
    @Column(name = "ESTADO")
    private String estado;
   
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    @Column(name = "IDENTIFICACION")
    private String identificacion;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "TIPO_FACTURACION")
    private String tipoFacturacion;
    @Column(name = "CODIGO_DOCUMENTO")
    private String codigoDocumento;
    @Column(name ="CODIGO_TIPO_DOCUMENTO")
    private String codigoTipoDocumento;
    
    @Column(name ="INVENTARIO_INGRESO")
    private String inventarioIngreso;
    
    @Column(name ="OBSERVACION")
    private String observacion;
    
    @Column(name ="ESTADO_RETENCION")
    private String estadoRetencion;
    
    @Column(name ="AUTORIZACION")
    private String autorizacion;
    
    @JoinColumn(name ="ORDEN_COMPRA_ID")
    private OrdenCompra ordenCompra;
    
    @JoinColumn(name = "PROVEEDOR_ID")
    @ManyToOne    
    private Persona proveedor;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "compra",fetch = FetchType.EAGER)
    private List<CompraDetalle> detalles;
    
    @Column(name ="TIPO_IDENTIFICACION_CODIGO_SRI")
    private String tipoIdentificacionCodigoSri;
    

    public Compra() {
        this.descuentoImpuestos=BigDecimal.ZERO;
        this.descuentoSinImpuestos=BigDecimal.ZERO;
        
    }
    
    public Long getId() {
        return id;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public Long getTipoClienteId() {
        return tipoClienteId;
    }
    
    public Integer getSecuencial() {
        return secuencial;
    }
    
    public String getSecuencialFormat() {
        return UtilidadesTextos.llenarCarateresIzquierda(secuencial.toString(),9,"0");
    }

    public String getPuntoEstablecimiento() {
        return puntoEstablecimiento;
    }
    
    public String getPuntoEstablecimientoFormat() {
        return UtilidadesTextos.llenarCarateresIzquierda(puntoEstablecimiento,3,"0");
    }
    
    public String getPuntoEmision() {
        return puntoEmision;
    }
    
    public String getPuntoEmisionFormat() {
        return UtilidadesTextos.llenarCarateresIzquierda(puntoEmision, 3, "0");
    }
    

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }


    public Long getIvaSriId() {
        return ivaSriId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public String getEstado() {
        return estado;
    }

    public String getTipoIdentificacionCodigoSri() {
        return tipoIdentificacionCodigoSri;
    }

    
    

    public void setId(Long id) {
        this.id = id;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public void setTipoClienteId(Long tipoClienteId) {
        this.tipoClienteId = tipoClienteId;
    }

    public void setSecuencial(Integer secuencial) {
        this.secuencial = secuencial;
    }

    public void setPuntoEstablecimiento(String puntoEstablecimiento) {
        this.puntoEstablecimiento = puntoEstablecimiento;
    }

    public void setPuntoEmision(String puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


    public void setIvaSriId(Long ivaSriId) {
        this.ivaSriId = ivaSriId;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    public List<CompraDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<CompraDetalle> detalles) {
        this.detalles = detalles;
    }
    
    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public Persona getProveedor() {
        return proveedor;
    }

    public void setProveedor(Persona proveedor) {
        this.proveedor = proveedor;
    }

    public String getCodigoTipoDocumento() {
        return codigoTipoDocumento;
    }

    public void setCodigoTipoDocumento(String codigoTipoDocumento) {
        this.codigoTipoDocumento = codigoTipoDocumento;
    }

    public String getInventarioIngreso() {
        return inventarioIngreso;
    }

    public void setInventarioIngreso(String inventarioIngreso) {
        this.inventarioIngreso = inventarioIngreso;
    }

    public String getEstadoRetencion() {
        return estadoRetencion;
    }

    public void setEstadoRetencion(String estadoRetencion) {
        this.estadoRetencion = estadoRetencion;
    }

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public void setTipoIdentificacionCodigoSri(String tipoIdentificacionCodigoSri) {
        this.tipoIdentificacionCodigoSri = tipoIdentificacionCodigoSri;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }
    
    
    
    public GeneralEnumEstado getEstadoEnum()
    {
        return GeneralEnumEstado.getEnum(estado);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.id);
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
        final Compra other = (Compra) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
   
    /**
     * Informacion adicional
     */
    public void calcularTotales()
    {
        this.subtotalImpuestos=BigDecimal.ZERO;
        this.subtotalSinImpuestos=BigDecimal.ZERO;
        this.iva=BigDecimal.ZERO;
        
        if(detalles!=null)
        {
            //FacturaDetalle fd;
            //fd.getReferenciaId()
            for (CompraDetalle detalle : detalles) {
                if(detalle.getProductoProveedor().getProducto().getCatalogoProducto().getIva().getPorcentaje().compareTo(BigDecimal.ZERO)==0)
                {   //Sumar los subtotales con valor 0
                    subtotalSinImpuestos=subtotalSinImpuestos.add(detalle.getTotal());                    
                }
                else
                { //Sumar los subtotales con valor 12
                    subtotalImpuestos=subtotalImpuestos.add(detalle.getTotal());
                    iva=iva.add(detalle.getIva());
                }
            }
            
            //Setear la escala del iva y del valor total
            iva=iva.setScale(2,ParametrosSistemaCodefac.REDONDEO_POR_DEFECTO);
            
            total=subtotalImpuestos.add(subtotalSinImpuestos).add(iva); //calcular el total de los valores
            
            //redondeo despues de redondear el iva para tener un valor coherente con la sumatoria de los valores redondeados
            total=total.setScale(2,ParametrosSistemaCodefac.REDONDEO_POR_DEFECTO);
        }
    }
    
    
    public void addDetalle(CompraDetalle detalle)
    {
        if(this.detalles==null)
        {
            this.detalles=new ArrayList<CompraDetalle>();
        }
        detalle.setCompra(this);
        this.detalles.add(detalle);
        
    }
    
    public EnumSiNo getInventarioIngresoEnum()
    {
        return EnumSiNo.getEnumByLetra(inventarioIngreso);
    }
    

    public String getPreimpreso()
    {
       if(puntoEstablecimiento==null || puntoEmision==null || secuencial==null)
       {
           return "";
       }
       else
       {
        return UtilidadesTextos.llenarCarateresIzquierda(puntoEstablecimiento,3,"0")+"-"+UtilidadesTextos.llenarCarateresIzquierda(puntoEmision,3,"0")+"-"+UtilidadesTextos.llenarCarateresIzquierda(secuencial+"",9,"0");
       }
    }
    
    public RetencionEnumCompras getEstadoRetencionEnum()
    {
        return RetencionEnumCompras.getEnum(estadoRetencion);
    }
    
    public enum RetencionEnumCompras
    {
        /**
         * Estado para saber si un documento ya fue enviada la retencion
         */
        EMITIDO("E", "Emitido"), 
        /**
         * Estado que indica que el documento tiene pendiente enviar la retencion
         */
        NO_EMITIDO("N", "No emitido"), 
        /**
         * Estado que 
         */
        SIN_CONTABILIDAD("S","Sin contabilidad");
        
        private final String estado;
        private final String nombre;

        private RetencionEnumCompras(String estado, String nombre)
        {
            this.estado = estado;
            this.nombre = nombre;
        }
        
        public String getEstado()
        {
            return this.estado;
        }
        
        public String getNombre()
        {
            return this.nombre;
        }
        
        public static RetencionEnumCompras getEnum(String estado) {

            for (RetencionEnumCompras enumerador : RetencionEnumCompras.values()) {
                if (enumerador.getEstado().equals(estado)) {
                    return enumerador;
                }
            }
            return null;
        }
    }    
}
