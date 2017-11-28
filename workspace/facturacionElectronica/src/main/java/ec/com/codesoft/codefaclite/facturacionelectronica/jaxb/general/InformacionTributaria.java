/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlType(propOrder = {"ambiente","tipoEmision","razonSocial","nombreComercial","ruc","claveAcceso","codigoDocumento","establecimiento","puntoEmision","secuencial","direcionMatriz"})
public class InformacionTributaria {
    private String ambiente;
    private String tipoEmision;
    private String razonSocial;
    private String nombreComercial;
    private String ruc;
    private String claveAcceso;
    private String codigoDocumento;
    private String establecimiento;
    private String puntoEmision;
    private String secuencial;
    private String direcionMatriz;

    public InformacionTributaria() {
    }
    
    @XmlElement(name="ambiente")
    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    @XmlElement(name="tipoEmision")
    public String getTipoEmision() {
        return tipoEmision;
    }

    public void setTipoEmision(String tipoEmision) {
        this.tipoEmision = tipoEmision;
    }

    @XmlElement(name="razonSocial")
    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    @XmlElement(name="nombreComercial")
    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    @XmlElement(name="ruc")
    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    @XmlElement(name="claveAcceso")
    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    @XmlElement(name="codDoc")
    public String getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(String codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    @XmlElement(name="estab")
    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    @XmlElement(name="ptoEmi")
    public String getPuntoEmision() {
        return puntoEmision;
    }

    public void setPuntoEmision(String puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    @XmlElement(name="secuencial")
    public String getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(String secuencial) {
        this.secuencial = secuencial;
    }

    @XmlElement(name="dirMatriz")
    public String getDirecionMatriz() {
        return direcionMatriz;
    }

    public void setDirecionMatriz(String direcionMatriz) {
        this.direcionMatriz = direcionMatriz;
    }
    
    public String getPreimpreso()
    {
        return this.puntoEmision+"-"+this.establecimiento+"-"+this.secuencial;
    }
    

}
