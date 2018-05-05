/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.ejemplo.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.ejemplo.utilidades.texto.UtilidadesTextos;
import es.mityc.firmaJava.libreria.utilidades.UtilidadFechas;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "RETENCION")
@XmlRootElement
public class Retencion implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "TIPO_IDENTIFICACION_ID")
    private Long tipoClienteId;
    
    @Column(name = "CLAVE_ACCESO")
    private String claveAcceso;
    
    @Column(name = "EMPRESA_ID")
    private Long empresaId;
    
    @Column(name = "SECUENCIAL")
    private Integer secuencial;
    
    @Column(name = "PUNTO_ESTABLECIMIENTO")
    private String puntoEstablecimiento;
    
    @Column(name = "PUNTO_EMISION")
    private String puntoEmision;    
    
    @Column(name = "FECHA_EMISION")
    private Date fechaEmision;
    
    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;
    
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
    @Column(name = "telefono")
    private String telefono;
    

    @JoinColumn(name = "PROVEEDOR_ID")
    @ManyToOne    
    private Persona proveedor;

    @JoinColumn(name = "COMPRA_ID")
    @ManyToOne        
    private Compra compra;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "retencion",fetch = FetchType.EAGER)
    private List<RetencionDetalle> detalles;

    public Retencion() {

    }

    public Integer getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(Integer secuencial) {
        this.secuencial = secuencial;
    }

    public Persona getProveedor() {
        return proveedor;
    }

    public void setProveedor(Persona proveedor) {
        this.proveedor = proveedor;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public List<RetencionDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<RetencionDetalle> detalles) {
        this.detalles = detalles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public String getPuntoEstablecimiento() {
        return puntoEstablecimiento;
    }

    public void setPuntoEstablecimiento(String puntoEstablecimiento) {
        this.puntoEstablecimiento = puntoEstablecimiento;
    }

    public String getPuntoEmision() {
        return puntoEmision;
    }

    public void setPuntoEmision(String puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }
    
    
    
    /**
     *Metodos personalizados
     */
         /**
     * Formas de pago adicional
     */
    public void addDetalle(RetencionDetalle DetalleRetencion)
    {
        if(this.detalles==null)
        {
            this.detalles=new ArrayList<RetencionDetalle>();
        }
        DetalleRetencion.setRetencion(this);
        this.detalles.add(DetalleRetencion);
        
    }
    
    
    public String getPeriodoFiscal()
    {
        return UtilidadesFecha.obtenerMesStr(fechaEmision)+"/"+UtilidadesFecha.obtenerAnioStr(fechaEmision);
    }
    
    public String getPreimpreso() {
        return UtilidadesTextos.llenarCarateresIzquierda(puntoEmision, 3, "0") + "-" + UtilidadesTextos.llenarCarateresIzquierda(puntoEstablecimiento, 3, "0") + "-" + UtilidadesTextos.llenarCarateresIzquierda(secuencial + "", 8, "0");
    }

    
    
}
