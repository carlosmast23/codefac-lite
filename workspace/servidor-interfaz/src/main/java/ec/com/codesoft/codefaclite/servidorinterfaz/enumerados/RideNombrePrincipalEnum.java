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
public enum RideNombrePrincipalEnum implements ParametroUtilidades.ComparadorInterface<RideNombrePrincipalEnum>
{    
    RAZON_SOCIAL("Razón Social","r"),
    NOMBRE_LEGAL("Nombre Legal","l");

    private RideNombrePrincipalEnum(String nombre, String letra) 
    {
        this.nombre = nombre;
        this.letra = letra;
    }
    
    private String nombre;
    private String letra;

    public String getNombre() {
        return nombre;
    }

    public String getLetra() {
        return letra;
    }
    
    public static RideNombrePrincipalEnum buscarPorLetra(String letra)
    {
        for (RideNombrePrincipalEnum value : RideNombrePrincipalEnum.values()) {
            if(value.getLetra().equals(letra))
            {
                return value;
            }
        }
        return null;
    }

    @Override
    public RideNombrePrincipalEnum consultarParametro(String nombreParametro) 
    {
        return RideNombrePrincipalEnum.buscarPorLetra(nombreParametro);
    }
    
    
    
}
