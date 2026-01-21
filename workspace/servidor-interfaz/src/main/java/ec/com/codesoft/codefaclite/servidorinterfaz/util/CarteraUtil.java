/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.util;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.math.BigDecimal;

/**
 *
 * @author CARLOS_CODESOFT
 */
public final  class CarteraUtil {
    
    public static void validarTotalySaldo(BigDecimal total,BigDecimal saldo,Class clase) throws ServicioCodefacException
    {
        //por defecto deben enviar 2 valores de total y saldo para poder llenar este dato
        if(total==null || saldo==null)
        {
            if(total.compareTo(saldo)>=0)
            {
                //todo bien OK
            }
            else
            {
                throw new ServicioCodefacException("Módulo de cartera: El Saldo no puede ser mayor al TOTAL");
            }
        }
        else
        {
            throw new ServicioCodefacException("Módulo de cartera: no se pueden enviar valor con NULL");
        }
    }
    
}
