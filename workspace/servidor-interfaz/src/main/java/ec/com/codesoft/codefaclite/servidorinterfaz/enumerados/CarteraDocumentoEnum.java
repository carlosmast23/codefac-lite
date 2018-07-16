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
public enum CarteraDocumentoEnum {

    ABONO(CarteraEnum.COMPROBANTE_INGRESO,"Abono","ABO"),
    RETENCION(CarteraEnum.COMPROBANTE_INGRESO,"Retención","RET");

    private CarteraDocumentoEnum(CarteraEnum carteraEnum, String nombre, String codigo) {
        this.carteraEnum = carteraEnum;
        this.nombre = nombre;
        this.codigo = codigo;
    }
    
    private CarteraEnum carteraEnum;
    private String nombre;
    private String codigo;

    @Override
    public String toString() {
        return nombre;
    }
    
    


    public static List<CarteraDocumentoEnum>  buscarPorTipoCartera(CarteraEnum cartera)
    {
        List<CarteraDocumentoEnum> resultados=new ArrayList<CarteraDocumentoEnum>();
        for (CarteraDocumentoEnum carteraDocumentoEnum : CarteraDocumentoEnum.values()) 
        {
            if(carteraDocumentoEnum.carteraEnum.equals(cartera))
            {
                resultados.add(carteraDocumentoEnum);
            }
        }
        return resultados;
    }

    
}
