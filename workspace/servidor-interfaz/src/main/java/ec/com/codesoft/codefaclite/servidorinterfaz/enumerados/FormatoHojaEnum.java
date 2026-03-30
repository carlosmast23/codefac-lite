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
public enum FormatoHojaEnum {
    A6("A6"),
    A5("A5"),
    A4("A4"),
    @Deprecated
    A3("A3"),
    TICKET("TICKET");
    
    private String letra;    
    private Boolean pos58;

    private FormatoHojaEnum(String letra) {
        this.letra = letra;
        this.pos58=false;
    }

    public String getLetra() {
        return letra;
    }

    public Boolean getPos58() {
        return pos58;
    }

    public void setPos58(Boolean pos58) {
        this.pos58 = pos58;
    }
    
    
    
    public static FormatoHojaEnum buscarPorLetra(String letra)
    {
        for (FormatoHojaEnum formato : FormatoHojaEnum.values()) {
            if(formato.getLetra().equals(letra))
            {
                return formato;
            }
        }
        
        return null;
    }
    
}
