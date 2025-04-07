/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.test;

import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
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
        String respuesta=UtilidadesTextos.encriptarSimplePorSecuencia("1234567890","AUTOCENFLI","Mi número es 5.60");
        System.out.println(respuesta);
    }
    
}
