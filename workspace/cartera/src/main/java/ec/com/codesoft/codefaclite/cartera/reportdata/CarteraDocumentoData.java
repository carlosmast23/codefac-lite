/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.cartera.reportdata;

import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.controlador.excel.TipoDato;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class CarteraDocumentoData implements ExcelDatosInterface {
    private String codigo;
    private String descripcion;
    private String valor;
    private String saldo;
    private String preimpreso;
    private String fechaEmision;
    private String persona;
    private String documento;

    public CarteraDocumentoData(String codigo, String descripcion, String valor, String saldo, String preimpreso, String fechaEmision, String persona, String documento) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.valor = valor;
        this.saldo = saldo;
        this.preimpreso = preimpreso;
        this.fechaEmision = fechaEmision;
        this.persona = persona;
        this.documento = documento;
    }

    

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getPreimpreso() {
        return preimpreso;
    }

    public void setPreimpreso(String preimpreso) {
        this.preimpreso = preimpreso;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    @Override
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
        return tiposDatos; 
    }

    public static final String[] TITULO_REPORTE={
        "Código",
        "Documento",
        "Cliente/Proveedor",
        "Descripción",
        "Fecha",
        "Preimpreso",
        "Valor",
        "Saldo"
    };
    
    
    
}
