/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.test;

import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import static ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha.obtenerDistanciaDias;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TestFechas {

    public static void main(String args[]) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try {
            // Crear dos fechas de ejemplo
            Date fechaMenor = formato.parse("10/10/2023 09:43:52");
            Date fechaMayor = formato.parse("20/10/2023 12:00:00");

            // Llamar al método obtenerDistanciaDias
            int diferenciaDias = obtenerDistanciaDias(fechaMenor, fechaMayor);
            

            // Mostrar el resultado
            System.out.println("Diferencia en días: " + diferenciaDias);
            
            int diferenciaHoras = UtilidadesFecha.obtenerDistanciaHorasDate(fechaMenor, fechaMayor);
            System.out.println("Diferencia en horas: " + diferenciaHoras);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
