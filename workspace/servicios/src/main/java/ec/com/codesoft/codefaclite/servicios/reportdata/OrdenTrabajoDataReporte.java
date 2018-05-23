/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servicios.reportdata;

import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.controlador.excel.TipoDato;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class OrdenTrabajoDataReporte implements ExcelDatosInterface
{
    private String id;
    private String descripciond;
    private String notas;
    private String fechaEntrega;
    private String estadod;
    private String tipoOrdenTrabajo;
    private String prioridad;
    private String departamento;
    private String persona;

    public OrdenTrabajoDataReporte() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripciond() {
        return descripciond;
    }

    public void setDescripciond(String descripciond) {
        this.descripciond = descripciond;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getTipoOrdenTrabajo() {
        return tipoOrdenTrabajo;
    }

    public void setTipoOrdenTrabajo(String tipoOrdenTrabajo) {
        this.tipoOrdenTrabajo = tipoOrdenTrabajo;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public String getEstadod() {
        return estadod;
    }

    public void setEstadod(String estadod) {
        this.estadod = estadod;
    }
     
    @Override
    public List<TipoDato> getDatos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
