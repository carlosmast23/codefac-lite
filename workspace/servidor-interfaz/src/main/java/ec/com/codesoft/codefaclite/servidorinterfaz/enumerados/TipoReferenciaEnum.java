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

    private TipoReferenciaEnum(String codigo) {
        this.codigo = codigo;
    }

    private String codigo;

    public static TipoReferenciaEnum getFindByTipoReferencia(String codigo)
    {
        for (TipoReferenciaEnum tipoReferenciaEnum : TipoReferenciaEnum.values()) {
            if(tipoReferenciaEnum.codigo.equals(codigo))
            {
                return tipoReferenciaEnum;
            }
        }
        
        return null;
    }

    public String getCodigo() {
        return codigo;
    }

    
    
    
    
}
