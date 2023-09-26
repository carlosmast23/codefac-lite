/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.reportData;

import java.io.Serializable;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ActividadPresupuestoData implements Serializable{
    
    private String nombreEmpleado;
    private String usuario;
    private String codigoOrdenTrabajo;
    private String tarea;
    private String servicio;
    private String fecha;
    private String placa;
    private String terminado;

    public ActividadPresupuestoData() {
    }
    
    

    public ActividadPresupuestoData(String nombreEmpleado, String usuario, String codigoOrdenTrabajo) {
        this.nombreEmpleado = nombreEmpleado;
        this.usuario = usuario;
        this.codigoOrdenTrabajo = codigoOrdenTrabajo;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCodigoOrdenTrabajo() {
        return codigoOrdenTrabajo;
    }

    public void setCodigoOrdenTrabajo(String codigoOrdenTrabajo) {
        this.codigoOrdenTrabajo = codigoOrdenTrabajo;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTerminado() {
        return terminado;
    }

    public void setTerminado(String terminado) {
        this.terminado = terminado;
    }
    
    
    
    
}
