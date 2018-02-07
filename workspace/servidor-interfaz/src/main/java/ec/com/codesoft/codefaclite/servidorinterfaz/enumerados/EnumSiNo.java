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
    NO("no","n"),
    SI("si","s");

    private EnumSiNo(String nombre,String letra) {
        this.nombre=nombre;
        this.letra = letra;
    }
    
    private String letra;
    private String nombre;

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
    
    public static EnumSiNo getEnumByLetra(String letra)
    {

        for (EnumSiNo enumerador : EnumSiNo.values())
        {
            if(enumerador.getLetra().equals(letra))
            {
                return enumerador;
            }
        }
        return null;
    }
    
}
