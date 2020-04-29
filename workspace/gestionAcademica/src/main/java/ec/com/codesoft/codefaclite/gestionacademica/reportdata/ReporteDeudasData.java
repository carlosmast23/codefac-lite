/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.reportdata;

import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.controlador.excel.TipoDato;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo
 */
public class ReporteDeudasData implements ExcelDatosInterface{

    private String nivelAcademicoEstudiante;
    private String cedulaEstudiante;
    private String estudiante;
    private String rubro;
    private String valor;

    public ReporteDeudasData(String nivelAcademicoEstudiante, String cedulaEstudiante, String estudiante, String rubro, String valor) {
        this.nivelAcademicoEstudiante = nivelAcademicoEstudiante;
        this.cedulaEstudiante = cedulaEstudiante;
        this.estudiante = estudiante;
        this.rubro = rubro;
        this.valor = valor;
    }

    
    
    public String getNivelAcademicoEstudiante() {
        return nivelAcademicoEstudiante;
    }

    public void setNivelAcademicoEstudiante(String nivelAcademicoEstudiante) {
        this.nivelAcademicoEstudiante = nivelAcademicoEstudiante;
    }

    public String getCedulaEstudiante() {
        return cedulaEstudiante;
    }

    public void setCedulaEstudiante(String cedulaEstudiante) {
        this.cedulaEstudiante = cedulaEstudiante;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public List<TipoDato> getDatos() {
        List<TipoDato> tiposDatos = new ArrayList<TipoDato>();        
        tiposDatos.add(new TipoDato(this.cedulaEstudiante,Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.estudiante, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.nivelAcademicoEstudiante,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.rubro, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.valor, Excel.TipoDataEnum.NUMERO));        
        return tiposDatos;
    }
    
    public enum TipoReporteEnum
    {
        DETALLADO("reporte_deudas.jrxml"),
        RESUMIDO("reporte_deudas_resumido.jrxml");

        private TipoReporteEnum(String nombreReporte) {
            this.nombreReporte = nombreReporte;
        }
        
        private String nombreReporte;

        public String getNombreReporte() {
            return nombreReporte;
        }
        
        
    }
    
    
}
