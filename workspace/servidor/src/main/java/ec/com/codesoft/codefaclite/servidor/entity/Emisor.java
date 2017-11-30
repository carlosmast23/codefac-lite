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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "EMISOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Emisor.findAll", query = "SELECT e FROM Emisor e"),
    @NamedQuery(name = "Emisor.findByRuc", query = "SELECT e FROM Emisor e WHERE e.ruc = :ruc"),
    @NamedQuery(name = "Emisor.findByNomComercial", query = "SELECT e FROM Emisor e WHERE e.nomComercial = :nomComercial")})
public class Emisor implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column (name = "ID_CLIENTE")
    private Integer idCliente;
    @Column (name = "RAZON_SOCIAL")
    private String razonSocial;
    @Column (name = "RUC")
    private String ruc;
    @Column (name = "NOM_COMERCIAL")
    private String nomComercial;
    @Column (name = "DIR_ESTABLECIMIENTO")
    private String dirEstablecimiento;
    @Column (name = "COD_ESTABLECIMIENTO")
    private String codEstablecimiento;
    @Column (name = "RESOLUSION")
    private String resolucion;
    @Column (name = "CONTRIBUYENTE_ESPECIAL")
    private String contribuyenteEspecial;
    @Column (name = "LLEVA_CONTABILIDAD")
    private String llevaContabilidad;
    @Column (name = "LOGO_IMAGEN")
    private String logoImagen;
    @Column (name = "TIPO_EMISION")
    private String tipoEmision;
    @Column (name = "TIEMPO_ESPERA")
    private String tiempoEspera;
    @Column (name = "CLAVE_INTERNA")
    private String claveInterna;
    @Column (name = "DIRECCION_MATRIZ")
    private String direccionMatriz;
    @Column (name = "ACTIVIDAD_COMERCIAL")
    private String actividadComercial;

    public String getActividadComercial() {
        return actividadComercial;
    }

    public void setActividadComercial(String actividadComercial) {
        this.actividadComercial = actividadComercial;
    }
    
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNomComercial() {
        return nomComercial;
    }

    public void setNomComercial(String nomComercial) {
        this.nomComercial = nomComercial;
    }

    public String getDirEstablecimiento() {
        return dirEstablecimiento;
    }

    public void setDirEstablecimiento(String dirEstablecimiento) {
        this.dirEstablecimiento = dirEstablecimiento;
    }

    public String getCodEstablecimiento() {
        return codEstablecimiento;
    }

    public void setCodEstablecimiento(String codEstablecimiento) {
        this.codEstablecimiento = codEstablecimiento;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public String getContribuyenteEspecial() {
        return contribuyenteEspecial;
    }

    public void setContribuyenteEspecial(String contribuyenteEspecial) {
        this.contribuyenteEspecial = contribuyenteEspecial;
    }

    public String getLlevaContabilidad() {
        return llevaContabilidad;
    }

    public void setLlevaContabilidad(String llevaContabilidad) {
        this.llevaContabilidad = llevaContabilidad;
    }

    public String getLogoImagen() {
        return logoImagen;
    }

    public void setLogoImagen(String logoImagen) {
        this.logoImagen = logoImagen;
    }

    public String getTipoEmision() {
        return tipoEmision;
    }

    public void setTipoEmision(String tipoEmision) {
        this.tipoEmision = tipoEmision;
    }

    public String getTiempoEspera() {
        return tiempoEspera;
    }

    public void setTiempoEspera(String tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

    public String getClaveInterna() {
        return claveInterna;
    }

    public void setClaveInterna(String claveInterna) {
        this.claveInterna = claveInterna;
    }

    public String getDireccionMatriz() {
        return direccionMatriz;
    }

    public void setDireccionMatriz(String direccionMatriz) {
        this.direccionMatriz = direccionMatriz;
    }    
   
}
