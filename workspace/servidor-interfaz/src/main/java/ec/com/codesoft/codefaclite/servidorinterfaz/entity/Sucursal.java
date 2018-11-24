/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Carlos
 */
public class Sucursal implements Serializable{
    
    private Long id;
    private String nombre;
    private String tipo;
    private String direcccion;
    private String telefono;
    private String email;    
    /**
     * Este es el codigo del sri para saber el numero de sucursal para emitir la factura
     */
    private String codigoSucursal; 
    
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

    public String getCodigoSucursal() {
        return codigoSucursal;
    }

    public void setCodigoSucursal(String codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    //////////////////////////////////////////////////////////
    //------------------> METODOS PERSONALIZADOS 
    //////////////////////////////////////////////////////////
    public TipoSucursalEnum getTipoSucursalEnum()
    {
        return TipoSucursalEnum.getEnum(tipo);
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
        
    }
}
