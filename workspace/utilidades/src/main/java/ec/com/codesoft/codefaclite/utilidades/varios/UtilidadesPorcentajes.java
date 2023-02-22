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
    

    public static BigDecimal calcularPorcentajeDosValores(BigDecimal valorComparar, BigDecimal valorTotal) 
    {
        if(valorTotal.compareTo(BigDecimal.ZERO)<=0)
        {
            //throw new Exception("Error de division para zero");
            return null;
        }
        return valorComparar.divide(valorTotal,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
    }
    
    /**
     * Metodo que permite agregar un valor y un porcentaje y calcular la utilidad
     * si el porcentaje de utilidad es cero por defcto retorno cero
     * @param valor
     * @param porcentajeUtilidad
     * @return 
     */
    public static BigDecimal agregarUtilidadPrecio(BigDecimal valor,BigDecimal porcentajeUtilidad,BigDecimal valorDefecto)
    {
        if(porcentajeUtilidad.compareTo(BigDecimal.ZERO)<=0)
        {
            if(valorDefecto!=null)
            {
                return valorDefecto;
            }
            return BigDecimal.ZERO;
        }
        
        BigDecimal porcentaje= porcentajeUtilidad.divide(new BigDecimal("100"),4,RoundingMode.HALF_UP).add(BigDecimal.ONE);
        
        return valor.multiply(porcentaje);       
        
    }
    
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
    
    /**
     * Metodo que me permite calcular un porcentaje y agregar al valor original
     * @porcentajeNumero estevalor debe ser entre 0 y 100 de preferencia
     * @return 
     */
    public static BigDecimal calcularValorIncluidoPorcentaje(BigDecimal valor, BigDecimal porcentajeNumero)
    {
        BigDecimal porcentajeDecimal=porcentajeNumero.divide(UtilidadBigDecimal.CIEN,2,BigDecimal.ROUND_HALF_UP);
        porcentajeDecimal=porcentajeDecimal.add(BigDecimal.ONE);
        
        return valor.multiply(porcentajeDecimal).setScale(2,BigDecimal.ROUND_HALF_UP);
    }
}
