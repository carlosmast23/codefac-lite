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

    private String nivelAcademicoEstudiante;
    private String cedulaEstudiante;
    private String estudiante;
    private String rubro;
    private String valor;

    public ReporteDeudasEstudianteData(String nivelAcademicoEstudiante, String cedulaEstudiante, String estudiante, String rubro, String valor) {
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
    
    
}
