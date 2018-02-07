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
public enum TipoFacturacionEnumEstado {
    ELECTRONICA("e","Electrónica"),
    NORMAL("m","Manual");

    private TipoFacturacionEnumEstado(String letra, String nombre) {
        this.letra = letra;
        this.nombre = nombre;
    }
    
    private String letra;
    private String nombre;

    public String getLetra() {
        return letra;
    }

    public String getNombre() {
        return nombre;
    }
    
    public static TipoFacturacionEnumEstado getEnumByEstado(String estado)
    {

        for (TipoFacturacionEnumEstado enumerador : TipoFacturacionEnumEstado.values())
        {
            if(enumerador.letra.equals(estado))
            {
                return enumerador;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
}
