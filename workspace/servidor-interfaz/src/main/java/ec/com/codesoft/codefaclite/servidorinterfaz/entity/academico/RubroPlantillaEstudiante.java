/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "RUBROS_NIVEL")
public class RubroPlantillaEstudiante implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    private RubroPlantilla rubroPlantilla;
    
    private EstudianteInscrito estudianteInscrito;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RubroPlantilla getRubroPlantilla() {
        return rubroPlantilla;
    }

    public void setRubroPlantilla(RubroPlantilla rubroPlantilla) {
        this.rubroPlantilla = rubroPlantilla;
    }

    public EstudianteInscrito getEstudianteInscrito() {
        return estudianteInscrito;
    }

    public void setEstudianteInscrito(EstudianteInscrito estudianteInscrito) {
        this.estudianteInscrito = estudianteInscrito;
    }
    
    
    
}
