/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable{
    
    public static final String SUPER_USUARIO="root";
    
    @Id
    @Column (name = "NICK")
    private String nick;
    
    @Column (name = "CLAVE")
    private String clave;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario",fetch = FetchType.EAGER)
    private List<PerfilUsuario> perfilesUsuario;
    

    public Usuario(String nick, String clave) {
        this.nick = nick;
        this.clave = clave;
    }

    public Usuario() {
    }
    
    

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public List<PerfilUsuario> getPerfilesUsuario() {
        return perfilesUsuario;
    }

    public void setPerfilesUsuario(List<PerfilUsuario> perfilesUsuario) {
        this.perfilesUsuario = perfilesUsuario;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nick=" + nick + ", clave=" + clave + '}';
    }

    /**
     * Metodo que permite agregar un PerfilUsuario a la lista
     * @param perfilUsuario 
     */
    
    public void addPerfilUsuario(PerfilUsuario perfilUsuario)
    {
        if(this.perfilesUsuario==null)
        {
            this.perfilesUsuario=new ArrayList<PerfilUsuario>();
        }
        perfilUsuario.setUsuario(this);
        
        this.perfilesUsuario.add(perfilUsuario);
        
    }
    
    
}
