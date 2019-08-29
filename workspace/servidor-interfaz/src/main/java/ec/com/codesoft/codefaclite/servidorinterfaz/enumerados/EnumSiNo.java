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
public enum EnumSiNo {
    NO("no","n",false),
    SI("si","s",true);

    private EnumSiNo(String nombre,String letra,Boolean bool) {
        this.nombre=nombre;
        this.letra = letra;
        this.bool=bool;
    }
    
    private Boolean bool;
    
    private String letra;
    private String nombre;

    public String getLetra() {
        return letra;
    }

    public String getNombre() {
        return nombre;
    }

    public Boolean getBool() {
        return bool;
    }
    
    

    @Override
    public String toString() {
        return nombre;
    }
    
    public static EnumSiNo getEnumByLetra(String letra)
    {

        for (EnumSiNo enumerador : EnumSiNo.values())
        {
            if(enumerador.getLetra().toLowerCase().equals(letra.toLowerCase()))
            {
                return enumerador;
            }
        }
        return null;
    }
    
    public static EnumSiNo getEnumByNombre(String letra)
    {

        for (EnumSiNo enumerador : EnumSiNo.values())
        {
            if(enumerador.getNombre().toLowerCase().equals(letra.toLowerCase()))
            {
                return enumerador;
            }
        }
        return null;
    }
    
    
    public static EnumSiNo getEnumByBoolean(Boolean opcion)
    {
        if(opcion)
            return EnumSiNo.SI;
        else
            return EnumSiNo.NO;
    }
    
}
