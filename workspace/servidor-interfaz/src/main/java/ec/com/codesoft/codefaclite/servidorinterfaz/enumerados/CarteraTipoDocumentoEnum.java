/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public enum CarteraTipoDocumentoEnum {
    ABONO_EFECTIVO(CarteraDocumentoEnum.ABONO,"Abono efectivo","ABE"),
    RETENCION_FUENTE(CarteraDocumentoEnum.RETENCION,"Retención Fuente","RETF"),
    RETENCION_IVA(CarteraDocumentoEnum.RETENCION,"Retención Iva","RETI");

    private CarteraTipoDocumentoEnum(CarteraDocumentoEnum documento, String nombre, String codigo) {
        this.documento = documento;
        this.nombre = nombre;
        this.codigo = codigo;
    }
    
    private CarteraDocumentoEnum documento;
    private String nombre;
    private String codigo;

    @Override
    public String toString() {
        return nombre;
    }
    
    
    public static List<CarteraTipoDocumentoEnum> buscarPorDocumentoCartera(CarteraDocumentoEnum documento)
    {
        List<CarteraTipoDocumentoEnum> resultados=new ArrayList<CarteraTipoDocumentoEnum>();
        for (CarteraTipoDocumentoEnum resultado : CarteraTipoDocumentoEnum.values()) {
            if(resultado.documento.equals(documento))
            resultados.add(resultado);
        }
        return resultados;
    }
    
}
