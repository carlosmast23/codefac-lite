/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public enum OrdenTrabajoEnumEstado 
{
     /**
     * Identificador para 
     */
    RECIBIDO("R","Recibido"),
    /**
     * Identificador para proveedores
     */
    PRESUPUESTADO("P","Presupuestado"),
    /**
     * Identificador para cuando son clientes y proveedores
     */
    ANULADO("A","Anulado");

    private OrdenTrabajoEnumEstado(String letra, String nombre) {
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
    
    public static OrdenTrabajoEnumEstado getEnum(String letra)
    {

        for (OrdenTrabajoEnumEstado enumerador : OrdenTrabajoEnumEstado.values())
        {
            if(enumerador.letra.equals(letra))
            {
                return enumerador;
            }
        }
        return null;
    }
   
}
