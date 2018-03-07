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
public enum TipoReferenciaEnum {
    INVENTARIO("INV"),
    ACADEMICO("ACA");

    private TipoReferenciaEnum(String letra) {
        this.letra = letra;
    }

    private String letra;

    public static TipoReferenciaEnum getFindByTipoReferencia(String letra)
    {
        for (TipoReferenciaEnum tipoReferenciaEnum : TipoReferenciaEnum.values()) {
            if(tipoReferenciaEnum.letra.equals(letra))
            {
                return tipoReferenciaEnum;
            }
        }
        
        return null;
    }
    
}
