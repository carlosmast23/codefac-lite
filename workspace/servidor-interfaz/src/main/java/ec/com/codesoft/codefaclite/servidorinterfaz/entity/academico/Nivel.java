/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "NIVEL")
@XmlRootElement
public class Nivel {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIVEL_ID")
    private Long idNivel;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "ORDEN")
    private Integer orden;
    private Nivel nivelAnterior;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    private String estado;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(Long idNivel) {
        this.idNivel = idNivel;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Nivel getNivelAnterior() {
        return nivelAnterior;
    }

    public void setNivelAnterior(Nivel nivelAnterior) {
        this.nivelAnterior = nivelAnterior;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
