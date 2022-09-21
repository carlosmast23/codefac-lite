/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;

/**
 *
 * @author CARLOS_CODESOFT
 */
public enum TipoNegocioEnum implements ParametroUtilidades.ComparadorInterface<TipoNegocioEnum> {    
    SIMPLE("s","simple"),
    TALLER_AUTOMOTRIZ("t","taller automotriz"),
    FARMACIA("f","farmacia");    
    
    private String letra;
    private String nombre;    
    

    private TipoNegocioEnum(String letra, String nombre) {
        this.letra = letra;
        this.nombre = nombre;
    }

    public String getLetra() {
        return letra;
    }

    public String getNombre() {
        return nombre;
    }
    
    public static TipoNegocioEnum getEnum(String letra) 
    {
        for (TipoNegocioEnum enumerador : TipoNegocioEnum.values()) {
            if (enumerador.letra.equals(letra)) {
                return enumerador;
            }
        }
        return null;
    }

    @Override
    public TipoNegocioEnum consultarParametro(String nombreParametro) {
        return TipoNegocioEnum.getEnum(nombreParametro);
    }
    
    
}
