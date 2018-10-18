/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlType(propOrder = {"tipoIDInformante","idInformante","razonSocial","anio","mes","numEstabRuc","totalVentas","codigoOperativo","ventas"})
@XmlRootElement(name = "iva")
public class AtsJaxb implements Serializable
{
    private String tipoIDInformante;
    private String idInformante;
    private String razonSocial;
    private Integer anio;
    private String mes;
    private String numEstabRuc;
    private BigDecimal totalVentas;
    private String codigoOperativo;    
    private List<VentaAts> ventas;

    public AtsJaxb() {
    }

    @XmlElementWrapper(name = "ventas")
    @XmlElement(name = "detalleVentas")
    public List<VentaAts> getVentas() {
        return ventas;
    }

    @XmlElement(name = "TipoIDInformante")
    public String getTipoIDInformante() {
        return tipoIDInformante;
    }

    @XmlElement(name = "IdInformante")
    public String getIdInformante() {
        return idInformante;
    }

    @XmlElement(name = "razonSocial")
    public String getRazonSocial() {
        return razonSocial;
    }

    @XmlElement(name = "Anio")
    public Integer getAnio() {
        return anio;
    }

    @XmlElement(name = "Mes")
    public String getMes() {
        return mes;
    }

    @XmlElement(name = "numEstabRuc")
    public String getNumEstabRuc() {
        return numEstabRuc;
    }

    @XmlElement(name = "totalVentas")
    public BigDecimal getTotalVentas() {
        return totalVentas;
    }

    @XmlElement(name = "codigoOperativo")
    public String getCodigoOperativo() {
        return codigoOperativo;
    }

    public void setTipoIDInformante(String tipoIDInformante) {
        this.tipoIDInformante = tipoIDInformante;
    }

    public void setIdInformante(String idInformante) {
        this.idInformante = idInformante;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public void setNumEstabRuc(String numEstabRuc) {
        this.numEstabRuc = numEstabRuc;
    }

    public void setTotalVentas(BigDecimal totalVentas) {
        this.totalVentas = totalVentas;
    }

    public void setCodigoOperativo(String codigoOperativo) {
        this.codigoOperativo = codigoOperativo;
    }

    public void setVentas(List<VentaAts> ventas) {
        this.ventas = ventas;
    }
    
    /**
     *=========================> METODOS PERSONALIZADOS <====================
     */
    /**
     * Metodo que calcula el tot
     */
    public void calcularTotalVentas()
    {
        this.totalVentas=BigDecimal.ZERO;
        if(ventas!=null)
        {
            for (VentaAts venta : ventas) {
                this.totalVentas=this.totalVentas.add(venta.getBaseImponible());
            }
        }
    }
    
    
    
}
