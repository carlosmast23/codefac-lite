/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.cartera.reportdata;

import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.excel.TipoDato;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class CarteraDataDetalle extends CarteraDocumentoData
{
    public CarteraDataDetalle(String codigo, String descripcion, String valor, String saldo, String preimpreso, String fechaEmision, String persona, String documento, String codigoDetalleDocumento, String nombreDetalleDocumento, String subtotalDetalle, String saldoDetalle) {
        super(codigo, descripcion, valor, saldo, preimpreso, fechaEmision, persona, documento, codigoDetalleDocumento, nombreDetalleDocumento, subtotalDetalle, saldoDetalle);
    }

    
    public List<TipoDato> getDatos() {
        List<TipoDato> tiposDatos = new ArrayList<TipoDato>();
        
        tiposDatos.add(new TipoDato(this.codigo,Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.documento, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.persona,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.descripcion, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.fechaEmision, Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.preimpreso,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.valor,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.saldo,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.codigoDetalleDocumento,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.nombreDetalleDocumento,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.subtotalDetalle,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.saldoDetalle,Excel.TipoDataEnum.NUMERO));
        
        
        return tiposDatos; 
    }    
    
        
    public static final String[] TITULO_REPORTE_DETALLE=
    {
        "Código",
        "Documento",
        "Cliente/Proveedor",
        "Descripción",
        "Fecha",
        "Preimpreso",
        "Valor",
        "Saldo",
        "Código Detalle Doc",
        "Nombre Detalle Doc",
        "Subtotal Detalle",
        "Saldo Detalle"
    };
    
}
