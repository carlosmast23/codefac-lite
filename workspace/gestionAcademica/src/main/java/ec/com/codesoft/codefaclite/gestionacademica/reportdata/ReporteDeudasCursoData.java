/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.reportdata;

import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.controlador.excel.TipoDato;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo
 */
public class ReporteDeudasCursoData implements ExcelDatosInterface {
    private String nivel;
    private String rubro;
    private BigDecimal abono;
    private BigDecimal deuda;

    public ReporteDeudasCursoData(String nivel, String rubro, BigDecimal abono, BigDecimal deuda) {
        this.nivel = nivel;
        this.rubro = rubro;
        this.abono = abono;
        this.deuda = deuda;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public BigDecimal getAbono() {
        return abono;
    }

    public void setAbono(BigDecimal abono) {
        this.abono = abono;
    }

    public BigDecimal getDeuda() {
        return deuda;
    }

    public void setDeuda(BigDecimal deuda) {
        this.deuda = deuda;
    }

    @Override
    public List<TipoDato> getDatos() {
        
        List<TipoDato> tiposDatos = new ArrayList<TipoDato>();
        
        tiposDatos.add(new TipoDato(this.rubro,Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.nivel, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.abono, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.deuda, Excel.TipoDataEnum.NUMERO));        

        return tiposDatos;
    }

    public enum TipoReporteEnum
    {
        DETALLADO("reporte_deudas_curso.jrxml"),
        RESUMIDO("reporte_deudas_curso_resumido.jrxml");
        
        private TipoReporteEnum(String nombreReporte) {
            this.nombreReporte = nombreReporte;
        }
        private String nombreReporte;

        public String getNombreReporte() {
            return nombreReporte;
        }
        
        
    }
   
    
    
}
