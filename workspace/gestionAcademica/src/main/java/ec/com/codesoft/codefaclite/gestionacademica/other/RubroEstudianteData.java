/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.other;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;

/**
 *
 * @author Carlos
 */
public class RubroEstudianteData {
    private EstudianteInscrito estudianteInscrito;
    private Boolean estadoOriginal;
    private Boolean estadoFinal;

    public RubroEstudianteData(EstudianteInscrito estudianteInscrito, Boolean seleccionado) {
        this.estudianteInscrito = estudianteInscrito;
        this.estadoOriginal = seleccionado;
        this.estadoFinal=seleccionado;
    }

    public void setSeleccionado(Boolean seleccionado) {
        this.estadoFinal=seleccionado;
    }

    public Boolean isDatoModificado() {
        if (!estadoOriginal.equals(estadoFinal)) 
        {
            return true;
        }
        return false;
    }

    public Boolean getEstadoFinal() {
        return estadoFinal;
    }

    public Boolean getEstadoOriginal() {
        return estadoOriginal;
    }

    

    public EstudianteInscrito getEstudianteInscrito() {
        return estudianteInscrito;
    }

    @Override
    public String toString() {
        return estudianteInscrito.getEstudiante().toString();
    }
    
    
    
}
