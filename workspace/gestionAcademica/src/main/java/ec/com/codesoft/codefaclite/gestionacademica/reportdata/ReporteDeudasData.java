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
public class ReporteDeudasData implements ExcelDatosInterface , Cloneable {

    private String nivelAcademicoEstudiante;
    private String cedulaEstudiante;
    private String estudiante;
    private String rubro;
    private String tipo;
    private String valor;
    private Integer nivel;
    
    //Variable que me va a seguir exclusivamente para el REPORTE AGRUPADO
    private String abono;

    public ReporteDeudasData(String nivelAcademicoEstudiante, String cedulaEstudiante, String estudiante, String rubro, String valor,TipoRubroEnum tipoRubroEnum) {
        this.nivelAcademicoEstudiante = nivelAcademicoEstudiante;
        this.cedulaEstudiante = cedulaEstudiante;
        this.estudiante = estudiante;
        this.rubro = rubro;
        this.valor = valor;
        this.tipo=tipoRubroEnum.nombre;
        this.abono="0";
    }
    
    public ReporteDeudasData(String nivelAcademicoEstudiante, String cedulaEstudiante, String estudiante, String rubro,TipoRubroEnum tipoRubroEnum) {
        this.nivelAcademicoEstudiante = nivelAcademicoEstudiante;
        this.cedulaEstudiante = cedulaEstudiante;
        this.estudiante = estudiante;
        this.rubro = rubro;
        this.valor = "0";
        this.tipo=tipoRubroEnum.nombre;
        this.abono="0";
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
    
    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public TipoRubroEnum getTipoEnum() {
        return TipoRubroEnum.findByNombre(tipo);
    }

    public void setTipoEnum(TipoRubroEnum tipoEnum) {
        this.tipo = tipoEnum.nombre;
    }

    public String getAbono() {
        return abono;
    }

    public void setAbono(String abono) {
        this.abono = abono;
    }
    
    
        
    public void sumarValor(String valorStr)
    {
        this.valor=new BigDecimal(this.valor).add(new BigDecimal(valorStr)).toString();
    }
    
    public void sumarAbono(String abono)
    {
        this.abono=new BigDecimal(this.abono).add(new BigDecimal(abono)).toString();
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    public enum TipoRubroEnum
    {
        DEUDA("Deuda"),
        ABONO("Abono"),
        ;
        
        private String nombre;

        private TipoRubroEnum(String nombre) {
            this.nombre = nombre;
        }
        
        public static TipoRubroEnum findByNombre(String nombre)
        {
            for (TipoRubroEnum value : TipoRubroEnum.values()) {
                if(value.nombre.equals(nombre))
                {
                    return value;
                }
            }
            return null;
        }
        
    }
   
    
    
}
