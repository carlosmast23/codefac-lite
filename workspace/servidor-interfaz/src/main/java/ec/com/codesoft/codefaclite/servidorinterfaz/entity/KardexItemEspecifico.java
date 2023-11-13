/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "KARDEX_ITEM_ESPECIFICO")
public class KardexItemEspecifico implements Serializable {
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "CODIGO_ESPECIFICO")
    private String codigoEspecifico;
    
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    
    @Column(name = "ESTADO")
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
    
    public GeneralEnumEstado getEstadoEnum() {
        return GeneralEnumEstado.getEnum(estado);
    }

    public void setEstadoEnum(GeneralEnumEstado estadoEnum) {
        this.estado = estadoEnum.getEstado();
    }

    public KardexDetalle getKardexDetalle() {
        return kardexDetalle;
    }

    public void setKardexDetalle(KardexDetalle kardexDetalle) {
        this.kardexDetalle = kardexDetalle;
    }


    

}
