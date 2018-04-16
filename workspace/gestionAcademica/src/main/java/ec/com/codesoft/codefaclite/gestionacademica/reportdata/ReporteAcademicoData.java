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
public class ReporteAcademicoData implements ExcelDatosInterface
{

    private String cedulaEstudiante;
    private String nombresEstudiante;
    private String apellidosEstudiante;
    private String emailEstudiante;
    private String telefonoEstudiante;
    private String representanteEstudiante;
    private String nivelAcademicoEstudiante;

       public ReporteAcademicoData(String cedulaEstudiante, String nombresEstudiante, String apellidosEstudiante, String emailEstudiante, String telefonoEstudiante, String representanteEstudiante, String nivelAcademicoEstudiante) {
        this.cedulaEstudiante = cedulaEstudiante;
        this.nombresEstudiante = nombresEstudiante;
        this.apellidosEstudiante = apellidosEstudiante;
        this.emailEstudiante = emailEstudiante;
        this.telefonoEstudiante = telefonoEstudiante;
        this.representanteEstudiante = representanteEstudiante;
        this.nivelAcademicoEstudiante = nivelAcademicoEstudiante;
    }

    public String getCedulaEstudiante() {
        return cedulaEstudiante;
    }

    public void setCedulaEstudiante(String cedulaEstudiante) {
        this.cedulaEstudiante = cedulaEstudiante;
    }

    public String getNombresEstudiante() {
        return nombresEstudiante;
    }

    public void setNombresEstudiante(String nombresEstudiante) {
        this.nombresEstudiante = nombresEstudiante;
    }

    public String getApellidosEstudiante() {
        return apellidosEstudiante;
    }

    public void setApellidosEstudiante(String apellidosEstudiante) {
        this.apellidosEstudiante = apellidosEstudiante;
    }

    public String getEmailEstudiante() {
        return emailEstudiante;
    }

    public void setEmailEstudiante(String emailEstudiante) {
        this.emailEstudiante = emailEstudiante;
    }

    public String getTelefonoEstudiante() {
        return telefonoEstudiante;
    }

    public void setTelefonoEstudiante(String telefonoEstudiante) {
        this.telefonoEstudiante = telefonoEstudiante;
    }

    public String getRepresentanteEstudiante() {
        return representanteEstudiante;
    }

    public void setRepresentanteEstudiante(String representanteEstudiante) {
        this.representanteEstudiante = representanteEstudiante;
    }

    public String getNivelAcademicoEstudiante() {
        return nivelAcademicoEstudiante;
    }

    public void setNivelAcademicoEstudiante(String nivelAcademicoEstudiante) {
        this.nivelAcademicoEstudiante = nivelAcademicoEstudiante;
    }

    @Override
    public List<TipoDato> getDatos() {
        List<TipoDato> datos = new ArrayList<>();
        datos.add(new TipoDato(this.cedulaEstudiante, Excel.TipoDataEnum.TEXTO));
        datos.add(new TipoDato(this.nombresEstudiante, Excel.TipoDataEnum.TEXTO));
        datos.add(new TipoDato(this.apellidosEstudiante, Excel.TipoDataEnum.TEXTO));
        datos.add(new TipoDato(this.emailEstudiante, Excel.TipoDataEnum.TEXTO));
        datos.add(new TipoDato(this.telefonoEstudiante, Excel.TipoDataEnum.TEXTO));
        datos.add(new TipoDato(this.representanteEstudiante, Excel.TipoDataEnum.TEXTO));
        return datos;
    }
    
    

}
