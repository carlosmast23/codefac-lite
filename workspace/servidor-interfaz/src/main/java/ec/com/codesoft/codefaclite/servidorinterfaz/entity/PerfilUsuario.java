/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.sql.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "PERFIL_USUARIO")
public class PerfilUsuario {
    
    private static final long serialVersionUID = 1L;
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column (name = "ID")
    private Long  id;
    
    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;
    
    @JoinColumn(name = "NICK")
    @ManyToOne    
    private Usuario usuario;

    @JoinColumn(name = "PERFIL_ID")
    @ManyToOne        
    //@PrimaryKeyJoinColumn (referencedColumnName = "ID" ) 
    private Perfil perfil;

    public PerfilUsuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
    
    
    
}
