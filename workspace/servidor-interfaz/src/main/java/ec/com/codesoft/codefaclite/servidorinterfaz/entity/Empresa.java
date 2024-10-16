/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
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
@Table(name = "EMPRESA")
public class Empresa implements Serializable {

    public static final String NO_LLEVA_CONTABILIDAD = "NO";
    public static final String SI_LLEVA_CONTABILIDAD = "SI";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    public Long id;
    //@Column(name = "TELEFONOS")
    //private String telefonos;
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    @Column(name = "NOMBRE_LEGAL")
    private String nombreLegal;
    //@Column(name = "DIRECCION")
    //private String direccion;
    @Column(name = "IDENTIFICACION")
    private String identificacion;
    @Column(name = "OBLIGADO_LLEVAR_CONTABILIDAD")
    private String obligadoLlevarContabilidad;
    @Column(name = "CONTRIBUYENTE_ESPECIAL")
    private String contribuyenteEspecial;
    @Column(name = "LOGO_IMAGEN_PATH")
    private String imagenLogoPath;
    //@Column(name = "CELULAR")
    //private String celular;
    @Column(name = "FACEBOOK")
    private String facebook;
    @Column(name = "TEXTO1")
    private String adicional;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresa",fetch = FetchType.EAGER)
    private List<Sucursal> sucursales;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //public String getTelefonos() {
    //    return telefonos;
    //}

    //public void setTelefonos(String telefonos) {
    //    this.telefonos = telefonos;
    //}

    /*
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }*/

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreLegal() {
        return nombreLegal;
    }

    public void setNombreLegal(String nombreLegal) {
        this.nombreLegal = nombreLegal;
    }

    public String getObligadoLlevarContabilidad() {
        return obligadoLlevarContabilidad;
    }
    
    public EnumSiNo getObligadoLlevarContabilidadEnum() {
        return EnumSiNo.getEnumByNombre(obligadoLlevarContabilidad);
    }

    public void setObligadoLlevarContabilidad(String obligadoLlevarContabilidad) {
        this.obligadoLlevarContabilidad = obligadoLlevarContabilidad;
    }

    public String getContribuyenteEspecial() {
        return contribuyenteEspecial;
    }

    public void setContribuyenteEspecial(String contribuyenteEspecial) {
        this.contribuyenteEspecial = contribuyenteEspecial;
    }

    public String getImagenLogoPath() {
        return imagenLogoPath;
    }

    public void setImagenLogoPath(String imagenLogoPath) {
        this.imagenLogoPath = imagenLogoPath;
    }

    /*
    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }*/

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getAdicional() {
        return adicional;
    }

    public void setAdicional(String adicional) {
        this.adicional = adicional;
    }

    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }
    
    

    @Override
    public String toString() {
        String nameString="";
        if(nombreLegal!=null && !nombreLegal.isEmpty())
        {
            nameString=nombreLegal;
        }
        else if(razonSocial!=null && !razonSocial.isEmpty())
        {
            nameString=razonSocial;
        }
        return nameString;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
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
        final Empresa other = (Empresa) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
