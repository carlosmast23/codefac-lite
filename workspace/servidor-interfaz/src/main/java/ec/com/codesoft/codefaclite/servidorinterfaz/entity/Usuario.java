/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable{
    
    public static final String SUPER_USUARIO="root";
    public static final String CONSUMIDOR_FINAL_NOMBRE="CONSUMIDOR FINAL";
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //@Id
    @Column (name = "NICK")
    private String nick;
    
    @Column (name = "CLAVE")
    private String clave;
    
    @Column (name = "ESTADO")
    private String estado;
    
    @JoinColumn(name = "EMPLEADO_ID")
    @ManyToOne 
    private Empleado empleado;
    
    @JoinColumn(name = "EMPRESA_ID")
    private Empresa empresa;
    
    /**
     * Variable que me sirve solo para saber si el usuario ingresado es root
     */
    @Transient
    public boolean isRoot;
    
    /**
     * Variable que me permite saber si el usuario solo es para configurar la primera vez
     */
    //@Transient
    //public boolean isConfig;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario",fetch = FetchType.EAGER)
    private List<PerfilUsuario> perfilesUsuario;
    

    public Usuario(String nick, String clave) {
        this.nick = nick;
        this.clave = clave;
        this.isRoot=false;
        
    }

    public Usuario() {
        this.isRoot=false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public GeneralEnumEstado getEstadoEnum() 
    {
        return GeneralEnumEstado.getEnum(estado);
    }

    public void setEstadoEnum(GeneralEnumEstado estadoEnum) {
        this.estado = estadoEnum.getEstado();
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    

    public List<PerfilUsuario> getPerfilesUsuario() {
        return perfilesUsuario;
    }

    public void setPerfilesUsuario(List<PerfilUsuario> perfilesUsuario) {
        this.perfilesUsuario = perfilesUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.nick);
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
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.nick, other.nick)) {
            return false;
        }
        return true;
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
