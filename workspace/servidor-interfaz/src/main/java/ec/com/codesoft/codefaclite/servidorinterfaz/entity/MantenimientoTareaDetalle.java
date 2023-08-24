/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.EstadoEntidadIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.sql.Timestamp;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author CARLOS_CODESOFT
 */

@Entity
@Table(name = "MANTENIMIENTO_TAREA_DETALLE")
public class MantenimientoTareaDetalle extends EntityAbstract<MantenimientoTareaDetalle.EstadoEnum>
{
    
    @JoinColumn(name = "OPERADOR_ID")
    private Empleado operador;
    
    @Column(name = "FECHA_INICIO")
    private Timestamp fechaInicio;
    
    @Column(name = "FECHA_FIN")
    private Timestamp fechaFin;
    
    @Column(name = "OBSERVACION")
    private String observacion;
    
    @JoinColumn(name = "MANTENIMIENTO_ID")
    private Mantenimiento mantenimiento;
    
    //private Tarea
    
    @JoinColumn(name = "TAREA_MANTENIMIENTO_ID")
    private TareaMantenimiento tarea;

    public MantenimientoTareaDetalle() {
    }

    public Empleado getOperador() {
        return operador;
    }

    public void setOperador(Empleado operador) {
        this.operador = operador;
    }

    public Timestamp getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Timestamp getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }

    public MantenimientoTareaDetalle(Mantenimiento mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

    public TareaMantenimiento getTarea() {
        return tarea;
    }

    public void setTarea(TareaMantenimiento tarea) {
        this.tarea = tarea;
    }

    public Mantenimiento getMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(Mantenimiento mantenimiento) {
        this.mantenimiento = mantenimiento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
    
    
    
    
    public enum EstadoEnum implements EstadoEntidadIf
    {
        GENERADO("G","Generado"),
        INICIADO("I","Iniciado"),
        FINALIZADO("F","Finalizado");

        private EstadoEnum(String letra, String nombre) {
            this.letra = letra;
            this.nombre = nombre;
        }
        
        private String letra;
        private String nombre;

        public String getLetra() {
            return letra;
        }

        public String getNombre() {
            return nombre;
        }
        
        public static EstadoEnum getEnum(String letra) {
            for (EstadoEnum enumerador : EstadoEnum.values()) {
                if (enumerador.letra.equals(letra)) {
                    return enumerador;
                }
            }
            return null;
        }
    }

    
}
