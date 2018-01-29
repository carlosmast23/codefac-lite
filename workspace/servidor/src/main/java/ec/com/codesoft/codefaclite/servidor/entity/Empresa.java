/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */

@Entity
@Table(name = "EMPRESA")
public class Empresa implements Serializable{
    
    public static final String NO_LLEVA_CONTABILIDAD="NO";
    public static final String SI_LLEVA_CONTABILIDAD="SI";
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    public long id;
    @Column (name = "TELEFONOS")
    private String telefonos;
    @Column (name = "RAZON_SOCIAL")
    private String razonSocial;
    @Column (name = "NOMBRE_LEGAL")
    private String nombreLegal;
    @Column (name = "DIRECCION")
    private String direccion;
    @Column (name = "IDENTIFICACION")
    private String identificacion;
    @Column (name = "OBLIGADO_LLEVAR_CONTABILIDAD")
    private String obligadoLlevarContabilidad;
    @Column (name = "CONTRIBUYENTE_ESPECIAL")
    private String contribuyenteEspecial;
    @Column (name = "LOGO_IMAGEN_PATH")
    private String imagenLogoPath;

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

     public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

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
    
    
    
    
    
}
