/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.reportdata;

/**
 *
 * @author CodesoftDesarrollo
 */
public class ReporteDeudasEstudianteData {

    private String rubro;
    private String valor;
    private String periodo;
    private String nivel;

    public ReporteDeudasEstudianteData(String rubro, String valor, String periodo, String nivel) {
        this.rubro = rubro;
        this.valor = valor;
        this.periodo = periodo;
        this.nivel = nivel;
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

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
    
    

}
