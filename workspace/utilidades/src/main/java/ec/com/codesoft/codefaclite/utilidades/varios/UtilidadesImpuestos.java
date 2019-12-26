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
public class UtilidadesImpuestos {
    public static BigDecimal quitarValorIva(BigDecimal ivaDefecto,BigDecimal valor,Integer decimales)
    {        
        ivaDefecto=ivaDefecto.divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP).add(BigDecimal.ONE);            
        return valor.divide(ivaDefecto,decimales,BigDecimal.ROUND_HALF_UP);
    }
}
