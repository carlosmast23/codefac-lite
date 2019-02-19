/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal.TipoSucursalEnum;
import java.io.Serializable;
import javax.persistence.Basic;
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
@Table(name = "PERSONA_ESTABLECIMIENTO")
public class PersonaEstablecimiento implements  Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "CODIGO_SUCURSAL")
    private String codigoSucursal;
    
    @Column(name = "NOMBRE_COMERCIAL")
    private String nombreComercial;
    
    @Column(name = "DIRECCION")
    private String direccion;
    
    @Column(name = "TELEFONO_CONVENCIONAL")
    private String telefonoConvencional;
    
    @Column(name = "EXTENSION_TELEFONO")
    private String extensionTelefono;
    
    @Column(name = "TELEFONO_CELULAR")
    private String telefonoCelular;
    
    @Column(name = "CORREO_ELECTRONICO")
    private String correoElectronico;

    @Column(name = "TIPO_SUCURSAL")
    private String tipoSucursal;
    
    @JoinColumn(name ="PERSONA_ID")
    private Persona persona;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoSucursal() {
        return codigoSucursal;
    }

    public void setCodigoSucursal(String codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonoConvencional() {
        return telefonoConvencional;
    }

    public void setTelefonoConvencional(String telefonoConvencional) {
        this.telefonoConvencional = telefonoConvencional;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getTipoSucursal() {
        return tipoSucursal;
    }

    public void setTipoSucursal(String tipoSucursal) {
        this.tipoSucursal = tipoSucursal;
    }
    
    public TipoSucursalEnum getTipoSucursalEnum() {
        return TipoSucursalEnum.getEnum(tipoSucursal);
    }

    public void setTipoSucursalEnum(TipoSucursalEnum tipoSucursalEnum) {
        this.tipoSucursal = tipoSucursalEnum.getCodigo();
    }

    public String getExtensionTelefono() {
        return extensionTelefono;
    }

    public void setExtensionTelefono(String extensionTelefono) {
        this.extensionTelefono = extensionTelefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
    
    
    
}
