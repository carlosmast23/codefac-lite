/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

/**
 *
 * @author Carlos
 */
public enum CategoriaMenuEnum {
    GESTIONAR("Gestionar"),
    PROCESOS("Procesos"),
    REPORTES("Reportes");
    
    private String nombre;

    private CategoriaMenuEnum(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
    
}
