/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

/**
 *
 * @author CARLOS_CODESOFT
 */
public enum TipoUbicacionEnum 
{
    TODOS("Todos"),
    CON_UBICACION("Con Ubicación"),
    SIN_UBICACION("Sin Ubicación");

    private String nombre;

    private TipoUbicacionEnum(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
