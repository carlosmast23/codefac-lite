/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.validadores;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Carlos
 */
public class UtilidadBigDecimal {
    
    /**
     * Me permite calcular el porcetaje desde un valor entero
     * @param porcentaje
     * @param valor
     * @return 
     */
    public static BigDecimal calcularValorPorcentaje(BigDecimal porcentaje,BigDecimal valor,Integer decimales)
    {        
        BigDecimal decimalPorcentaje=porcentaje.divide(new  BigDecimal("100"),decimales,RoundingMode.HALF_UP);
        return valor.multiply(decimalPorcentaje);
        
    }
    
}
