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
    /**
     * 
     * @param ivaDefecto El valor del iva tiene que ser un numero entero
     * @param valor
     * @param decimales
     * @return 
     */
    public static BigDecimal quitarValorIva(BigDecimal ivaDefecto,BigDecimal valor,Integer decimales)
    {        
        ivaDefecto=ivaDefecto.divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP).add(BigDecimal.ONE);            
        return valor.divide(ivaDefecto,decimales,BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * 
     * @param ivaDefecto El valor del iva tiene que ser un numero entero
     * @param valor
     * @return 
     */
    public static BigDecimal agregarValorIva(BigDecimal ivaDefecto,BigDecimal valor)
    {        
        if(valor==null)
        {
            return BigDecimal.ZERO;
        }
        
        ivaDefecto=ivaDefecto.divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP).add(BigDecimal.ONE);            
        return valor.multiply(ivaDefecto);
    }
    
    /*public static BigDecimal agregarValorIvaDefecto(BigDecimal valor)
    {        
        String tarifaStr=ParametrosSistemaCodefac.IVA_DEFECTO;
        BigDecimal ivaDefecto=new BigDecimal(tarifaStr);
        ivaDefecto=ivaDefecto.divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP).add(BigDecimal.ONE);            
        return valor.multiply(ivaDefecto);
    }*/
    
    public static BigDecimal calcularValorIva(BigDecimal ivaDefecto,BigDecimal valor)
    {
        ivaDefecto=ivaDefecto.divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP);            
        return valor.multiply(ivaDefecto);
    }
}
