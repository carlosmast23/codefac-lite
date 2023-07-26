/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.test;

import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.sql.Time;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TestFechas {
    public static void main(String args[])
    {
        Time time1 = Time.valueOf("11:00:00");
        Time time2 = Time.valueOf("13:00:00");
        
        System.out.println(UtilidadesFecha.obtenerDistanciaHoras(time1, time2));
    }
    
}
