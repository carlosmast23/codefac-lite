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
    private String clienteFactura;
    private String totalFactura;

    public ReporteFacturaData(String numeroFactura, String fechaFactura, String clienteFactura, String totalFactura) {
        this.numeroFactura = numeroFactura;
        this.fechaFactura = fechaFactura;
        this.clienteFactura = clienteFactura;
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

    public String getClienteFactura() {
        return clienteFactura;
    }

    public void setClienteFactura(String clienteFactura) {
        this.clienteFactura = clienteFactura;
    }

    public String getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(String totalFactura) {
        this.totalFactura = totalFactura;
    }
       
}
