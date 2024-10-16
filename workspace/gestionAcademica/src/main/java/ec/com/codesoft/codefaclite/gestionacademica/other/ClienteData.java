/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.other;

import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.controlador.excel.TipoDato;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class ClienteData implements ExcelDatosInterface
{
    private String identificacion;
    private String nombresCompletos;
    private String nombresCompletosEstudiante;
    private String nombreLegal;
    private String telefono;
    private String direccion;
    private String email;
    private String curso;

    public ClienteData() {
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombresCompletos() {
        return nombresCompletos;
    }

    public void setNombresCompletos(String nombresCompletos) {
        this.nombresCompletos = nombresCompletos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreLegal() {
        return nombreLegal;
    }

    public void setNombreLegal(String nombreLegal) {
        this.nombreLegal = nombreLegal;
    }

    public String getNombresCompletosEstudiante() {
        return nombresCompletosEstudiante;
    }

    public void setNombresCompletosEstudiante(String nombresCompletosEstudiante) {
        this.nombresCompletosEstudiante = nombresCompletosEstudiante;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }
          
    @Override
    public List<TipoDato> getDatos() {
        List<TipoDato> tiposDatos = new ArrayList<TipoDato>();
        
        tiposDatos.add(new TipoDato(this.identificacion,Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.nombresCompletos, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.nombresCompletosEstudiante,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.telefono,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.direccion, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.email, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.curso, Excel.TipoDataEnum.TEXTO));
        
        return tiposDatos;
    }   

}
