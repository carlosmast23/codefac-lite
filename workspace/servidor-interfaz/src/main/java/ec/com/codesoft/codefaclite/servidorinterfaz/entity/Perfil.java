/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "PERFIL")
public class Perfil implements Serializable{
    
    public static final String PERFIl_ADMINISTRADOR="ADMIN";
    public static final String PERFIl_OPERADOR="OPERADOR";
    
    private static final long serialVersionUID = 1L;
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID")
    private Long  id;
    
    @Column (name = "NOMBRE")
    private String nombre;
    
    @Column (name = "DESCRIPCION")
    private String descripcion;
    
    @Column (name = "ESTADO")
    private String estado;
    
     @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfil",fetch = FetchType.EAGER)
    private List<PermisoVentana> ventanasPermisos;

    public Perfil() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public List<PermisoVentana> getVentanasPermisos() {
        return ventanasPermisos;
    }

    public void setVentanasPermisos(List<PermisoVentana> ventanasPermisos) {
        this.ventanasPermisos = ventanasPermisos;
    }
    

    public void addPermisoVentana(PermisoVentana permisoVentana)
    {
        if(this.ventanasPermisos==null)
        {
            this.ventanasPermisos=new ArrayList<PermisoVentana>();
        }
        permisoVentana.setPerfil(this);
        this.ventanasPermisos.add(permisoVentana);
        
    }
    
    //Metodos personalizados
    public GeneralEnumEstado getEstadoEnum()
    {
        return GeneralEnumEstado.getEnum(estado);
    }
    
    
    
}
