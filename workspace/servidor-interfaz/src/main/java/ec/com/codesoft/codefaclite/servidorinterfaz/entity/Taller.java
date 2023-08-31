/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author CARLOS_CODESOFT
 */
@Entity
@Table(name = "TALLER")
public class Taller extends EntityAbstract<GeneralEnumEstado> {
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taller", fetch = FetchType.EAGER)
    private List<TallerTarea> tallerTareaList; 

    public Taller() {
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<TallerTarea> getTallerTareaList() {
        return tallerTareaList;
    }

    public void setTallerTareaList(List<TallerTarea> tallerTareaList) {
        this.tallerTareaList = tallerTareaList;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
    
}
