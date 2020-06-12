/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core.components;

/**
 *
 * @author CARLOS_CODESOFT
 */
public interface ComponentBindingIf<T,C> {
    /**
     * Este metodo me permite establecer los valor que van de la VISTA al CONTROLADOR
     */
    public void getAccion(String nombrePropiedadControlador);
    /**
     * Este metodo me permite establecer elos valores que van del CONTROLADOR a la VISTA
     */
    public void setAccion(T value);

    /**
     * Metodo que debe devuelve el nombre de la propiedad de la vista
     * @return 
     */
    public String getNombrePropiedadControlador(C componente);
}
