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
public enum CarteraDocumentoEnum {

    ABONO(CarteraEnum.COMPROBANTE_INGRESO,"ABONO","ABO"),
    RETENCION(CarteraEnum.COMPROBANTE_INGRESO,"ABONO","RET");

    private CarteraDocumentoEnum(CarteraEnum carteraEnum, String nombre, String codigo) {
        this.carteraEnum = carteraEnum;
        this.nombre = nombre;
        this.codigo = codigo;
    }
    
    private CarteraEnum carteraEnum;
    private String nombre;
    private String codigo;



    
}
