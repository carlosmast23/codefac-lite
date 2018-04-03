/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.other;

import java.util.List;
import java.util.Map;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class NotificacionDeudaImprimir 
{
    private String periodo;
    private String curso;
    private String nombres;
    private String representante;
    private String nota;
    private String total;
    
    //private Map<String,Object> parametrosPorAlumno;
    
    private List<EstudianteDeudaData> deudas;

    public NotificacionDeudaImprimir(List<EstudianteDeudaData> deudas) 
    {
        //this.parametrosPorAlumno = parametrosPorAlumno;
        this.deudas = deudas;
    }
    
    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
   
//    public Map<String, Object> getParametrosPorAlumno()
//    {
//        return parametrosPorAlumno;
//    }
//
//    public void setParametrosPorAlumno(Map<String, Object> parametrosPorAlumno) 
//    {
//        this.parametrosPorAlumno = parametrosPorAlumno;
//    }

    public List<EstudianteDeudaData> getDeudas() {
        return deudas;
    }

    public void setDeudas(List<EstudianteDeudaData> deudas) {
        this.deudas = deudas;
    }
}
