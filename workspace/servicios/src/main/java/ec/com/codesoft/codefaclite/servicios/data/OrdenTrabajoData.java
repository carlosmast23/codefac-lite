/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servicios.data;

/**
 *
 * @author Carlos
 */
public class OrdenTrabajoData {
    private String codigo;
    private String identificacion;
    private String cliente;
    private String fechaIngreso;
    private String estado;
    private String detalleStr;
    private String empleado;
    private String objetoMantenimiento;

    public OrdenTrabajoData() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDetalleStr() {
        return detalleStr;
    }

    public void setDetalleStr(String detalleStr) {
        this.detalleStr = detalleStr;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getObjetoMantenimiento() {
        return objetoMantenimiento;
    }

    public void setObjetoMantenimiento(String objetoMantenimiento) {
        this.objetoMantenimiento = objetoMantenimiento;
    }
    
    
    
    
}
