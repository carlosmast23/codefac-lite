/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.varios;

import java.math.BigDecimal;

/**
 *
 * @author CARLOS_CODESOFT
 */
public abstract class UtilidadesNumeros {
    
    public static int numeroDecimales(String numeroStr)
    {
        BigDecimal numero=new BigDecimal(numeroStr);
        String[] decimales= numero.toString().split("\\.");
        if(decimales.length>1)
        {
            int cantidadDecimales=decimales[1].length();
            return cantidadDecimales;
        }
        
        return 0;
    }
    
    
}
