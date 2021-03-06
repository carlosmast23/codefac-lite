/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobante.reporte;

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
public class ReporteFacturaData implements ExcelDatosInterface {
    
    public Boolean mostrarReferido=false;

    protected String numeroFactura;
    protected String fechaFactura;
    protected String identificacionCliente;
    protected String razonSocialCliente;
    protected String nombreLegalCliente;
    protected String estadoFactura;
    protected String tipoEmision;
    protected String documento;
    protected String subtotalDoceFactura;
    protected String subtotalCeroFactura;
    protected String descFactura;
    protected String ivaDoceFactura;
    protected String totalFactura;
    protected String valorAfecta;
    protected String referencia;
    protected String totalFinal;
    protected String claveAcceso;
    
    ///campo adicional para filrar por el punto de venta
    protected String puntoEmision;
    
    //============> Campos adicionales para los referidos <===================//
    protected String referido;
    protected String referidoIdentificacion;
    protected String referidoPorcentaje;
    protected String valorComision;
    
    //============> Campos adicionales para el vendedor y fecha maxima de pago <====================//
    protected String fechaMaximaPago;
    protected String vendedor;

    public ReporteFacturaData() {
    }
    
    

    public ReporteFacturaData(String numeroFactura, String fechaFactura, String identificacionCliente, String razonSocialCliente, String nombreLegalCliente, String estadoFactura,String tipoDocumento,String documento, String subtotalDoceFactura, String subtotalCeroFactura, String descFactura, String ivaDoceFactura, String totalFactura,String valorAfecta,String referencia,String totalFinal,String referido,String referidoIdentificacion,String referidoPorcentaje,String valorComision,String claveAcceso,String puntoEmision) {
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
        this.referido=referido;
        this.referidoIdentificacion=referidoIdentificacion;
        this.referidoPorcentaje=referidoPorcentaje;
        this.valorComision=valorComision;
        this.claveAcceso=claveAcceso;
        this.puntoEmision=puntoEmision;
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

    public String getReferido() {
        return referido;
    }

    public void setReferido(String referido) {
        this.referido = referido;
    }

    public String getReferidoIdentificacion() {
        return referidoIdentificacion;
    }

    public void setReferidoIdentificacion(String referidoIdentificacion) {
        this.referidoIdentificacion = referidoIdentificacion;
    }

    public String getReferidoPorcentaje() {
        return referidoPorcentaje;
    }

    public void setReferidoPorcentaje(String referidoPorcentaje) {
        this.referidoPorcentaje = referidoPorcentaje;
    }

    public String getValorComision() {
        return valorComision;
    }

    public void setValorComision(String valorComision) {
        this.valorComision = valorComision;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public String getFechaMaximaPago() {
        return fechaMaximaPago;
    }

    public void setFechaMaximaPago(String fechaMaximaPago) {
        this.fechaMaximaPago = fechaMaximaPago;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getPuntoEmision() {
        return puntoEmision;
    }

    public void setPuntoEmision(String puntoEmision) {
        this.puntoEmision = puntoEmision;
    }
    

    @Override
    public List<TipoDato> getDatos() {
        
        if(mostrarReferido)
            return getDatosReporteComisiones();
        else
            return getDatosReporteFactura();
        
    }

    public List<TipoDato> getDatosReporteFactura()
    {    
        List<TipoDato> tiposDatos = new ArrayList<TipoDato>();

        tiposDatos.add(new TipoDato(this.claveAcceso, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.fechaMaximaPago, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.vendedor, Excel.TipoDataEnum.TEXTO));
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
    
    
    public List<TipoDato> getDatosReporteComisiones()
    {    
        List<TipoDato> tiposDatos = new ArrayList<TipoDato>();        
        tiposDatos.add(new TipoDato(this.referidoIdentificacion, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.referido, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.referidoPorcentaje, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.valorComision, Excel.TipoDataEnum.NUMERO));
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
