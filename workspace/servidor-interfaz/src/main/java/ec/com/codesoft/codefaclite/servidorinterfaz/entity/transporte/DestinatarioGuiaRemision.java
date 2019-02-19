/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.io.Serializable;
import java.sql.Date;
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
@Table(name = "DESTINATARIO_GUIA_REMISION")
public class DestinatarioGuiaRemision implements Serializable{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "IDENTIFICACION")
    private String identificacion;
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    @Column(name = "DIRECCION_DESTINO")
    private String direccionDestino;
    @Column(name = "MOTIVO_TRANSLADO")
    private String motivoTranslado;
    @Column(name = "RUTA")
    private String ruta;
    @Column(name = "COD_DOCUMENTO_SUSTENTO")
    private String codDucumentoSustento;
    @Column(name = "PREIMPRESO")
    private String preimpreso;
    @Column(name = "AUTORIZACION_NUMERO")
    private String autorizacionNumero;
    @Column(name = "FECHA_EMISION")
    private Date fechaEmision;
    
    //@Column(name = "REFERENCIA_DOCUMENTO_ID")
    //private Long referenciaDocumentoId;
    
    @Column(name = "CODIGO_ESTABLECIMIENTO")
    private String codigoEstablecimiento;
    
    @JoinColumn(name = "SUCURSAL_ID")
    private PersonaEstablecimiento sucursal;
    
    @JoinColumn(name = "GUIA_REMISION_ID")
    @ManyToOne(optional = false)
    private GuiaRemision guiaRemision;
    
    @JoinColumn(name = "PERSONA_ID")
    @ManyToOne(optional = false)
    private Persona destinatorio;
    
    @JoinColumn(name = "REFERENCIA_DOCUMENTO_ID")
    private Factura facturaReferencia;
   
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "destinatario",fetch = FetchType.EAGER)
    private List<DetalleProductoGuiaRemision> detallesProductos;
    

    public DestinatarioGuiaRemision() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Persona getDestinatorio() {
        return destinatorio;
    }

    public void setDestinatorio(Persona destinatorio) {
        this.destinatorio = destinatorio;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccionDestino() {
        return direccionDestino;
    }

    public void setDireccionDestino(String direccionDestino) {
        this.direccionDestino = direccionDestino;
    }

    public String getMotivoTranslado() {
        return motivoTranslado;
    }

    public void setMotivoTranslado(String motivoTranslado) {
        this.motivoTranslado = motivoTranslado;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getCodDucumentoSustento() {
        return codDucumentoSustento;
    }

    public void setCodDucumentoSustento(String codDucumentoSustento) {
        this.codDucumentoSustento = codDucumentoSustento;
    }

    public String getPreimpreso() {
        return preimpreso;
    }

    public void setPreimpreso(String preimpreso) {
        this.preimpreso = preimpreso;
    }

    public String getAutorizacionNumero() {
        return autorizacionNumero;
    }

    public void setAutorizacionNumero(String autorizacionNumero) {
        this.autorizacionNumero = autorizacionNumero;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public List<DetalleProductoGuiaRemision> getDetallesProductos() {
        return detallesProductos;
    }

    public void setDetallesProductos(List<DetalleProductoGuiaRemision> detallesProductos) {
        this.detallesProductos = detallesProductos;
    }

    public GuiaRemision getGuiaRemision() {
        return guiaRemision;
    }

    public void setGuiaRemision(GuiaRemision guiaRemision) {
        this.guiaRemision = guiaRemision;
    }

    //public Long getReferenciaDocumentoId() {
    //    return referenciaDocumentoId;
    //}

    //public void setReferenciaDocumentoId(Long referenciaDocumentoId) {
    //    this.referenciaDocumentoId = referenciaDocumentoId;
    //}

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(String codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }
    
    public void setCodigoEstablecimiento(int codigoEstablecimiento) {
        this.codigoEstablecimiento = UtilidadesTextos.llenarCarateresIzquierda(codigoEstablecimiento+"", 3,"0");
    }

    public Factura getFacturaReferencia() {
        return facturaReferencia;
    }

    public void setFacturaReferencia(Factura facturaReferencia) {
        this.facturaReferencia = facturaReferencia;
    }

    public PersonaEstablecimiento getSucursal() {
        return sucursal;
    }

    public void setSucursal(PersonaEstablecimiento sucursal) {
        this.sucursal = sucursal;
    }

    
    
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.id);
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
        final DestinatarioGuiaRemision other = (DestinatarioGuiaRemision) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    

    public void addProducto(DetalleProductoGuiaRemision detalle) {
        if (this.detallesProductos == null) {
            this.detallesProductos = new ArrayList<DetalleProductoGuiaRemision>();
        }
        detalle.setDestinatario(this);
        this.detallesProductos.add(detalle);
    }

    @Override
    public String toString() {
        return destinatorio.getRazonSocial()+" ("+preimpreso+")";
    }
    
    
    
}
