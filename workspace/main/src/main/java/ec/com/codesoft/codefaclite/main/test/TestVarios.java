/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.test;

import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSistema;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TestVarios {
    
    public static void main(String args[])
    {
        System.out.println(UtilidadesSistema.generarClaveSoporte());
        BigDecimal.ONE.setScale(2, RoundingMode.HALF_UP);
    }
    
}
