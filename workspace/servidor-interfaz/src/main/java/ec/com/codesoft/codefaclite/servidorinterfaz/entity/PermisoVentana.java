/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import java.io.Serializable;
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
@Table(name = "PERMISO_VENTANA")
public class PermisoVentana implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID")    
    private Long id;

    @Column (name = "NOMBRE_CLASE")     
    private String nombreClase;
    
    @Column (name = "PERMISO_GRABAR") 
    private String permisoGrabar;
    
    @Column(name = "PERMISO_ELIMINAR")
    private String permisoEliminar;
    
    @Column(name = "PERMISO_IMPRIMIR")
    private String permisoImprimir;
    
    @Column (name = "PERMISO_EDITAR")     
    private String permisoEditar;
    
    
    @Column (name = "PERMISO_BUSCAR") 
    private String permisoBuscar;
        
    @JoinColumn(name="PERFIL_ID")
    @ManyToOne(optional = false)    
    private Perfil perfil;

    public PermisoVentana() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreClase() {
        return nombreClase;
    }

    public void setNombreClase(String nombreClase) {
        this.nombreClase = nombreClase;
    }

    public String getPermisoGrabar() {
        return permisoGrabar;
    }

    public void setPermisoGrabar(String permisoGrabar) {
        this.permisoGrabar = permisoGrabar;
    }

    public String getPermisoEliminar() {
        return permisoEliminar;
    }

    public void setPermisoEliminar(String permisoEliminar) {
        this.permisoEliminar = permisoEliminar;
    }

    public String getPermisoImprimir() {
        return permisoImprimir;
    }

    public void setPermisoImprimir(String permisoImprimir) {
        this.permisoImprimir = permisoImprimir;
    }

    public String getPermisoEditar() {
        return permisoEditar;
    }

    public void setPermisoEditar(String permisoEditar) {
        this.permisoEditar = permisoEditar;
    }

    public String getPermisoBuscar() {
        return permisoBuscar;
    }

    public void setPermisoBuscar(String permisoBuscar) {
        this.permisoBuscar = permisoBuscar;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    
    //Metodo que obtiene el enum atravez del codigo
    public VentanaEnum getVentanaEnum()
    {
        return VentanaEnum.buscarPorCodigo(nombreClase);
    }
    
    
    
}
