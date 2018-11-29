/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.compra.reportdata;

import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.controlador.excel.TipoDato;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo
 */
public class ReporteRetencionesData implements ExcelDatosInterface{

    private String preimpresoRetencion;
    private String preimpresoRetencionCompra;
    private String baseRetencion;
    private String porcentajeRetencion;
    private String codigoRetencion;
    private String valorRetencion;

    public ReporteRetencionesData(String preimpresoRetencion, String baseRetencion, String porcentajeRetencion, String codigoRetencion, String valorRetencion,String preimpresoRetencionCompra) {
        this.preimpresoRetencion = preimpresoRetencion;
        this.baseRetencion = baseRetencion;
        this.porcentajeRetencion = porcentajeRetencion;
        this.codigoRetencion = codigoRetencion;
        this.valorRetencion = valorRetencion;
        this.preimpresoRetencionCompra=preimpresoRetencionCompra;
    }

    
    
    public String getPreimpresoRetencion() {
        return preimpresoRetencion;
    }

    public void setPreimpresoRetencion(String preimpresoRetencion) {
        this.preimpresoRetencion = preimpresoRetencion;
    }

    public String getBaseRetencion() {
        return baseRetencion;
    }

    public void setBaseRetencion(String baseRetencion) {
        this.baseRetencion = baseRetencion;
    }

    public String getPorcentajeRetencion() {
        return porcentajeRetencion;
    }

    public void setPorcentajeRetencion(String porcentajeRetencion) {
        this.porcentajeRetencion = porcentajeRetencion;
    }

    public String getCodigoRetencion() {
        return codigoRetencion;
    }

    public void setCodigoRetencion(String codigoRetencion) {
        this.codigoRetencion = codigoRetencion;
    }

    public String getValorRetencion() {
        return valorRetencion;
    }

    public void setValorRetencion(String valorRetencion) {
        this.valorRetencion = valorRetencion;
    }

    public String getPreimpresoRetencionCompra() {
        return preimpresoRetencionCompra;
    }

    public void setPreimpresoRetencionCompra(String preimpresoRetencionCompra) {
        this.preimpresoRetencionCompra = preimpresoRetencionCompra;
    }

    
    
    @Override
    public List<TipoDato> getDatos() {

        List<TipoDato> datos = new ArrayList<>();
        datos.add(new TipoDato(this.preimpresoRetencion, Excel.TipoDataEnum.TEXTO));
        datos.add(new TipoDato(this.preimpresoRetencionCompra, Excel.TipoDataEnum.TEXTO));
        datos.add(new TipoDato(this.baseRetencion, Excel.TipoDataEnum.NUMERO));
        datos.add(new TipoDato(this.porcentajeRetencion, Excel.TipoDataEnum.TEXTO));
        datos.add(new TipoDato(this.codigoRetencion, Excel.TipoDataEnum.TEXTO));
        datos.add(new TipoDato(this.valorRetencion, Excel.TipoDataEnum.NUMERO));
        return datos;
    }


    /*@Override
    public List<TipoDato> getDatos() {
        
    }*/
}
