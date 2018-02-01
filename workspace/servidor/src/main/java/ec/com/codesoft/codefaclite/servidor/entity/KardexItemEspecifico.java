/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.entity;

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
@Table(name = "KARDEX_DETALLE")
public class KardexItemEspecifico {
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "CODIGO_ESPECIFICO")
    private String codigoEspecifico;
    
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    
    @Column(name = "ID")
    private String estado;

    @JoinColumn(name = "KARDEX_DETALLE_ID")
    @ManyToOne
    private KardexDetalle kardexDetalle;

    public KardexItemEspecifico() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoEspecifico() {
        return codigoEspecifico;
    }

    public void setCodigoEspecifico(String codigoEspecifico) {
        this.codigoEspecifico = codigoEspecifico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public KardexDetalle getKardexDetalle() {
        return kardexDetalle;
    }

    public void setKardexDetalle(KardexDetalle kardexDetalle) {
        this.kardexDetalle = kardexDetalle;
    }

    

}
