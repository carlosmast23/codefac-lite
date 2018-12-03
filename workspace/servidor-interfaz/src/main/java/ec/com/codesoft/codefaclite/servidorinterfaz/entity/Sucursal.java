/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "SUCURSAL")
public class Sucursal implements Serializable{
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "TIPO")
    private String tipo;
    
    @Column(name = "DIRECCION")
    private String direcccion;
    
    @Column(name = "TELEFONO")
    private String telefono;
    
    @Column(name = "EMAIL")
    private String email;    
    
    @Column(name = "ESTADO")
    private String estado;    
    /**
     * Este es el codigo del sri para saber el numero de sucursal para emitir la factura
     */
    @Column(name = "COD_SUCURSAL")
    private Integer codigoSucursal; 
    
    @JoinColumn(name ="EMPRESA_ID")
    private Empresa empresa;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDirecccion() {
        return direcccion;
    }

    public void setDirecccion(String direcccion) {
        this.direcccion = direcccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCodigoSucursal() {
        return codigoSucursal;
    }

    public void setCodigoSucursal(Integer codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
    
    //////////////////////////////////////////////////////////
    //------------------> METODOS PERSONALIZADOS 
    //////////////////////////////////////////////////////////
    public TipoSucursalEnum getTipoSucursalEnum()
    {
        return TipoSucursalEnum.getEnum(tipo);
    }
    
    public GeneralEnumEstado getEstadoEnum()
    {
        return GeneralEnumEstado.getEnum(estado);
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Sucursal other = (Sucursal) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
    public enum TipoSucursalEnum
    {
        MATRIZ("Matriz","m"),
        SUCURSAL("Sucursal","s"),;
        
        private String nombre;
        private String codigo;

        private TipoSucursalEnum(String nombre,String codigo) {
            this.nombre = nombre;
            this.codigo=codigo;
        }
        
        public static TipoSucursalEnum getEnum(String codigo )
        {
            for (TipoSucursalEnum object : TipoSucursalEnum.values()) {
                if(object.codigo.equals(codigo))
                {
                    return object;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return nombre;
        }

        public String getCodigo() {
            return codigo;
        }
        
        
        
        
        
    }
}
