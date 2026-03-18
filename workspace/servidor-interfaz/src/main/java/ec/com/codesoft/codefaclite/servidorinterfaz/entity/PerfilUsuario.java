/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Session;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "PERFIL_USUARIO")
public class PerfilUsuario implements Serializable {
    
    private static final long serialVersionUID = 1L;
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column (name = "ID")
    private Long  id;
    
    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;
    
    //@JoinColumn(name = "USUARIO_ID")
    //@ManyToOne    
    //private Usuario usuario;
    @Column(name = "USUARIO_ID")
    private Long  usuarioId;

    @JoinColumn(name = "PERFIL_ID")
    //@ManyToOne        
    private Perfil perfil;
    //@Column(name = "PERFIL_ID")
    //private Long  perfilId;

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
        try {
            return ServiceFactory.getFactory().getUsuarioServicioIf().buscarPorId(usuarioId);
        } catch (RemoteException ex) {
            Logger.getLogger(PerfilUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void setUsuario(Usuario usuario) {
        if(usuario!=null)
        {
            this.usuarioId = usuario.getId();
        }
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final PerfilUsuario other = (PerfilUsuario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
