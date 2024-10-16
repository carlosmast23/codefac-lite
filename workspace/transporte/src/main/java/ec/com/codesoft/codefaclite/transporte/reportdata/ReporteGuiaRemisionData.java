/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.transporte.reportdata;

import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.controlador.excel.TipoDato;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo
 */
public class ReporteGuiaRemisionData implements ExcelDatosInterface {

    private String numeroFactura;
    private String fechaFactura;
    private String identificacionCliente;
    private String razonSocialCliente;
    private String nombreLegalCliente;
    private String estadoFactura;
    private String tipoEmision;
    private String documento;
    private String subtotalDoceFactura;
    private String subtotalCeroFactura;
    private String descFactura;
    private String ivaDoceFactura;
    private String totalFactura;
    private String valorAfecta;
    private String referencia;
    private String totalFinal;

    public ReporteGuiaRemisionData(String numeroFactura, String fechaFactura, String identificacionCliente, String razonSocialCliente, String nombreLegalCliente, String estadoFactura,String tipoDocumento,String documento, String subtotalDoceFactura, String subtotalCeroFactura, String descFactura, String ivaDoceFactura, String totalFactura,String valorAfecta,String referencia,String totalFinal) {
        this.numeroFactura = numeroFactura;
        this.fechaFactura = fechaFactura;
        this.identificacionCliente = identificacionCliente;
        this.razonSocialCliente = razonSocialCliente;
        this.nombreLegalCliente = nombreLegalCliente;
        this.estadoFactura = estadoFactura;
        this.tipoEmision=tipoDocumento;
        this.documento=documento;
        this.subtotalDoceFactura = subtotalDoceFactura;
        this.subtotalCeroFactura = subtotalCeroFactura;
        this.descFactura = descFactura;
        this.ivaDoceFactura = ivaDoceFactura;
        this.totalFactura = totalFactura;
        this.valorAfecta=valorAfecta;
        this.referencia=referencia;
        this.totalFinal=totalFinal;
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

    public String getDescFactura() {
        return descFactura;
    }

    public void setDescFactura(String descFactura) {
        this.descFactura = descFactura;
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

    public String getTipoEmision() {
        return tipoEmision;
    }

    public void setTipoEmision(String tipoDocumento) {
        this.tipoEmision = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getValorAfecta() {
        return valorAfecta;
    }

    public void setValorAfecta(String valorAfecta) {
        this.valorAfecta = valorAfecta;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getTotalFinal() {
        return totalFinal;
    }

    public void setTotalFinal(String totalFinal) {
        this.totalFinal = totalFinal;
    }

    @Override
    public List<TipoDato> getDatos() {
        List<TipoDato> tiposDatos = new ArrayList<TipoDato>();
        tiposDatos.add(new TipoDato(this.numeroFactura, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.referencia, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.fechaFactura, Excel.TipoDataEnum.FECHA));
        tiposDatos.add(new TipoDato(this.identificacionCliente, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.razonSocialCliente, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.nombreLegalCliente, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.documento, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.estadoFactura, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.tipoEmision, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.subtotalDoceFactura, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.subtotalCeroFactura, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.descFactura, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.ivaDoceFactura, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.valorAfecta, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.totalFinal, Excel.TipoDataEnum.NUMERO));
        return tiposDatos;
    }

    
    
   
    

}
