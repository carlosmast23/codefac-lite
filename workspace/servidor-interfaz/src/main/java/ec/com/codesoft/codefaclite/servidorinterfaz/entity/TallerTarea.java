/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author CARLOS_CODESOFT
 */
@Entity
@Table(name = "TALLER_TAREA")
public class TallerTarea extends EntityAbstract<GeneralEnumEstado>  
{
    @JoinColumn(name = "TALLER_ID")
    private Taller taller;
    
    @JoinColumn(name = "TAREA_MANTENIMIENTO_ID")
    private TareaMantenimiento tareaMantenimiento;

    public TallerTarea() {
    }
    
    public Taller getTaller() {
        return taller;
    }

    public void setTaller(Taller taller) {
        this.taller = taller;
    }

    public TareaMantenimiento getTareaMantenimiento() {
        return tareaMantenimiento;
    }

    public void setTareaMantenimiento(TareaMantenimiento tareaMantenimiento) {
        this.tareaMantenimiento = tareaMantenimiento;
    }

    @Override
    public String toString() {
        return tareaMantenimiento.getNombre();
    }
    
    
    
    
}
