/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.reportdata;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author CodesoftDesarrollo
 */
public class ReporteFacturaData {

    private String numeroFactura;
    private String fechaFactura;
    private String identificacionCliente;
    private String razonSocialCliente;
    private String nombreLegalCliente;
    private String estadoFactura;
    private String subtotalDoceFactura;
    private String subtotalCeroFactura;
    private String ivaDoceFactura;
    private String totalFactura;

    public ReporteFacturaData(String numeroFactura, String fechaFactura, String identificacionCliente, String razonSocialCliente, String nombreLegalCliente, String estadoFactura, String subtotalDoceFactura, String subtotalCeroFactura, String ivaDoceFactura, String totalFactura) {
        this.numeroFactura = numeroFactura;
        this.fechaFactura = fechaFactura;
        this.identificacionCliente = identificacionCliente;
        this.razonSocialCliente = razonSocialCliente;
        this.nombreLegalCliente = nombreLegalCliente;
        this.estadoFactura = estadoFactura;
        this.subtotalDoceFactura = subtotalDoceFactura;
        this.subtotalCeroFactura = subtotalCeroFactura;
        this.ivaDoceFactura = ivaDoceFactura;
        this.totalFactura = totalFactura;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getIdentificacionCliente() {
        return identificacionCliente;
    }

    public void setIdentificacionCliente(String identificacionCliente) {
        this.identificacionCliente = identificacionCliente;
    }

    public String getRazonSocialCliente() {
        return razonSocialCliente;
    }

    public void setRazonSocialCliente(String razonSocialCliente) {
        this.razonSocialCliente = razonSocialCliente;
    }

    public String getNombreLegalCliente() {
        return nombreLegalCliente;
    }

    public void setNombreLegalCliente(String nombreLegalCliente) {
        this.nombreLegalCliente = nombreLegalCliente;
    }

    public String getEstadoFactura() {
        return estadoFactura;
    }

    public void setEstadoFactura(String estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    public String getSubtotalDoceFactura() {
        return subtotalDoceFactura;
    }

    public void setSubtotalDoceFactura(String subtotalDoceFactura) {
        this.subtotalDoceFactura = subtotalDoceFactura;
    }

    public String getSubtotalCeroFactura() {
        return subtotalCeroFactura;
    }

    public void setSubtotalCeroFactura(String subtotalCeroFactura) {
        this.subtotalCeroFactura = subtotalCeroFactura;
    }

    public String getIvaDoceFactura() {
        return ivaDoceFactura;
    }

    public void setIvaDoceFactura(String ivaDoceFactura) {
        this.ivaDoceFactura = ivaDoceFactura;
    }

    public String getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(String totalFactura) {
        this.totalFactura = totalFactura;
    }

    

}
