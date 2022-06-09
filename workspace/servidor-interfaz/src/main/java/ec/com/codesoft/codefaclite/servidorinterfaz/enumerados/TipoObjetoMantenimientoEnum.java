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
public enum TipoObjetoMantenimientoEnum {
    VEHICULO("V","Vehiculo"),
    OTROS("O","Otros"); 
    
    private String letra;
    private String nombre;

    private TipoObjetoMantenimientoEnum(String letra, String nombre) {
        this.letra = letra;
        this.nombre = nombre;
    }
    
    public String getLetra() {
        return letra;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    public static TipoObjetoMantenimientoEnum getEnumByLetra(String letra)
    {

        for (TipoObjetoMantenimientoEnum enumerador : TipoObjetoMantenimientoEnum.values())
        {
            if(enumerador.getLetra().toLowerCase().equals(letra.toLowerCase()))
            {
                return enumerador;
            }
        }
        return null;
    }
    
    
    
}
