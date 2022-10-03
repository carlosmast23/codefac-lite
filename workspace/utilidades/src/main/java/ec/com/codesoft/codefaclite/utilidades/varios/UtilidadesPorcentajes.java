/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.varios;

import ec.com.codesoft.codefaclite.utilidades.validadores.UtilidadBigDecimal;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class UtilidadesPorcentajes {
  
    public static final BigDecimal CIEN=new BigDecimal("100");
    public static final RoundingMode MODO_REDONDEO=RoundingMode.HALF_UP;
    
    /**
     * Metodo que permite calcular el valor menos el descuento
     * Formula:
     * vt=valor total
     * d=descuento
     * vn=vt*(1-d/100)
     * @param valorTotal
     * @param porcentajeDescuentoEntero
     * @return 
     */
    public static BigDecimal calcularValorMenosDescuento(BigDecimal valorTotal,BigDecimal porcentajeDescuentoEntero)
    {
        
        return valorTotal.multiply(BigDecimal.ONE.subtract(porcentajeDescuentoEntero.divide(CIEN,2,MODO_REDONDEO)));
    }
    
    /**
     * Metodo que permite reconstruir el valor original antes de aplicar un descuento
     * FORMULA:
     * vn=valor neto (valor incluido el descuento)
     * d=descuento
     * vt=(vn*100)/(100-d)
     * @param valorConDescuento
     * @param porcentajeDescuentoEntero
     * @return 
     */
    public static BigDecimal construirValorAntesDeDescuento(BigDecimal valorConDescuento,BigDecimal porcentajeDescuentoEntero)
    {
        if(porcentajeDescuentoEntero.compareTo(CIEN)>=0)
        {
            porcentajeDescuentoEntero=new BigDecimal("99.999");
        }
        
        return valorConDescuento.multiply(CIEN).divide(CIEN.subtract(porcentajeDescuentoEntero),2,MODO_REDONDEO);
    }
    
    /**
     * Metodo que permite calcular el porcentaje de un valor asumiendo que me pasa un valor entero sobre el 100 por ciento
     * @param porcentajeEntero
     * @param valor
     * @return 
     */
    public static BigDecimal calcularPorcentaje(BigDecimal porcentajeEntero,BigDecimal valor)
    {
        BigDecimal porcentajeDecimal=porcentajeEntero.divide(UtilidadBigDecimal.CIEN,2,BigDecimal.ROUND_HALF_UP);
        return valor.multiply(porcentajeDecimal);
    }
}
