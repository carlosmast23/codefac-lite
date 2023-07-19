/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.validadores;

import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JTextField;

/**
 *
 * @author Carlos
 */
public class UtilidadBigDecimal {
    
    private static final BigDecimal[] VALORES_DEFECTO = {
        new BigDecimal("100"),
        new BigDecimal("1000"),        
    };
    
    public static final BigDecimal CIEN =VALORES_DEFECTO[0];
    
    /**
     * Me permite calcular el porcetaje desde un valor entero
     * @param porcentaje
     * @param valor
     * @return 
     */
    public static BigDecimal calcularValorPorcentaje(BigDecimal porcentaje,BigDecimal valor,Integer decimales)
    {        
        BigDecimal decimalPorcentaje=porcentaje.divide(UtilidadBigDecimal.CIEN,decimales,RoundingMode.HALF_UP);
        return valor.multiply(decimalPorcentaje);        
        
    }
    
    public static BigDecimal redondearDosDecimales(BigDecimal valor)
    {
        return redondear(valor,2);
    }
    
    public static BigDecimal redondearCuatroDecimales(BigDecimal valor)
    {
        return redondear(valor,4);
    }
    
    public static BigDecimal obtenerValorJTextField(JTextField textField)
    {
        try
        {
            return new BigDecimal(textField.getText());
        }
        catch(Exception e)
        {
            
        }
        return BigDecimal.ZERO;
    }
    
    public static BigDecimal redondear(BigDecimal valor, Integer numeroDecimales)
    {
        if(valor==null)
        {
            return BigDecimal.ZERO;
        }
        
        return valor.setScale(numeroDecimales, RoundingMode.HALF_UP);
    }
    
    public static BigDecimal copiar(BigDecimal datoOriginal)
    {
        return datoOriginal.add(BigDecimal.ZERO);
    }
    
}
