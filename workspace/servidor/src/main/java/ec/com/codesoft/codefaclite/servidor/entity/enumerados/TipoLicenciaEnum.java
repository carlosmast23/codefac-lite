/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.entity.enumerados;

import ec.com.codesoft.codefaclite.servidor.entity.enumerados.ClienteEnumEstado;

/**
 *
 * @author Carlos
 */
public enum TipoLicenciaEnum {
    /**
     * Licencia gratuita por defecto para los usuario gratuitos
     */
    GRATIS("f","GRATIS"),
    /**
     * Licencia premiun para usuario de pago
     */
    PRO("p","PREMIUM");
    
    private String letra;
    private String nombre;

    private TipoLicenciaEnum(String letra, String nombre) {
        this.letra = letra;
        this.nombre = nombre;
    }
    
    public static TipoLicenciaEnum getEnumByLetra(String letra)
    {

        for (TipoLicenciaEnum enumerador : TipoLicenciaEnum.values())
        {
            if(enumerador.letra.equals(letra))
            {
                return enumerador;
            }
        }
        return null;
    }
    
    public static TipoLicenciaEnum getEnumByNombre(String nombre)
    {

        for (TipoLicenciaEnum enumerador : TipoLicenciaEnum.values())
        {
            if(enumerador.nombre.equals(nombre))
            {
                return enumerador;
            }
        }
        return null;
    }

    public String getLetra() {
        return letra;
    }

    public String getNombre() {
        return nombre;
    }
    
    
    
    
    
}
