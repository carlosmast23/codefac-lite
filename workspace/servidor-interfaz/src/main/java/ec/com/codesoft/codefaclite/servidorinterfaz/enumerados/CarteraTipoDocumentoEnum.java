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
public enum CarteraTipoDocumentoEnum {
    ABONO_EFECTIVO(CarteraDocumentoEnum.ABONO,"Abono efectivo","ABE"),
    RETENCION_FUENTE(CarteraDocumentoEnum.RETENCION,"",""),
    RETENCION_IVA(CarteraDocumentoEnum.RETENCION,"","");

    private CarteraTipoDocumentoEnum(CarteraDocumentoEnum documento, String nombre, String codigo) {
        this.documento = documento;
        this.nombre = nombre;
        this.codigo = codigo;
    }
    
    private CarteraDocumentoEnum documento;
    private String nombre;
    private String codigo;
    
}
