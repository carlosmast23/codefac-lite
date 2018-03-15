/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "cartera")
public class Cartera implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    /**
     * Este campo me permite identificar a quien corresponde el cliente o proveedor
     * dependiendo el documento
     */
    @JoinColumn(name = "PERSONA_ID")
    private Persona persona;
    
    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;
    
    @Column(name = "TOTAL")
    private BigDecimal total;
    
    @Column(name = "SALDO")
    private BigDecimal saldo;
    
    /**
     * Referencia que me permite grabar el id  de donde viene el documento y en conjunto con el
     * documento puedo saber a donde consultar
     */
    @Column(name = "REFERENCIA_ID")
    private Long referenciaID;
    
    /**
     * Codigo del documento para poder enlazar posteriormente con la referencia si necesito algun dato adicional
     */
    @Column(name = "CODIGO_DOCUMENTO")
    private String codigoDocumento;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cartera", fetch = FetchType.EAGER)
    private List<CarteraDetalle> detalles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Long getReferenciaID() {
        return referenciaID;
    }

    public void setReferenciaID(Long referenciaID) {
        this.referenciaID = referenciaID;
    }

    public String getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(String codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public void setDetalles(List<CarteraDetalle> detalles) {
        this.detalles = detalles;
    }

    public List<CarteraDetalle> getDetalles() {
        return detalles;
    }
    
    //Metodos personalizados
    
    public void addDetalle(CarteraDetalle carteraDetalle)
    {
        if(detalles==null)
        {
            detalles=new ArrayList<CarteraDetalle>();        
        }
        
        carteraDetalle.setCartera(this);
        detalles.add(carteraDetalle);
    }
    
    
    
    
}
