/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    
    private Persona destinatorio;
    private String razonSocial;
    private String direccionDestino;
    private String motivoTranslado;
    private String ruta;
    private String codDucumentoSustento;
    private String preimpreso;
    private String autorizacionNumero;
    private Date fechaEmision;
    
    private Long referenciaDocumentoId;
    
    private List<DetalleProductoGuiaRemision> detallesProductos;
    
    private GuiaRemision guiaRemision;

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

    public Long getReferenciaDocumentoId() {
        return referenciaDocumentoId;
    }

    public void setReferenciaDocumentoId(Long referenciaDocumentoId) {
        this.referenciaDocumentoId = referenciaDocumentoId;
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
