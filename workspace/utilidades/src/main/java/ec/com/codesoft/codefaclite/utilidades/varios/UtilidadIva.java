/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.varios;

import java.math.BigDecimal;

/**
 *
 * @author Carlos
 */
public class UtilidadIva {
    
    public static BigDecimal calcularValorConIvaIncluido(BigDecimal ivaPorcentaje,BigDecimal valor)
    {
        return ivaPorcentaje.add(BigDecimal.ONE).multiply(valor);
    }
}
