/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.ejemplo.utilidades.fecha;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Carlos
 */
public class UtilidadesFecha {

    public static java.sql.Date getFechaHoy() {
        java.util.Date fechaHoy = new java.util.Date();
        return new java.sql.Date(fechaHoy.getTime());
    }

    public static java.util.Date fechaFinMes(java.util.Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
    }

    public static java.util.Date fechaInicioMes(java.util.Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));

        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
    }

    public static java.util.Date hoy() {
        java.util.Date fecha = new java.util.Date();
        return fecha;
    }

    /**
     * Obtiene el numero de dias entre 2 fechas dadas
     *
     * @param fechaMenor
     * @param fechaMayor
     * @return
     */
    public static int obtenerDistanciaDias(java.util.Date fechaMenor, java.util.Date fechaMayor) {
        int dias = (int) ((fechaMayor.getTime() - fechaMenor.getTime()) / 86400000);
        return dias;
    }

    public static String formatDate(java.util.Date fecha, String formato) {
        //yyyy MMMMM dd
        //yyyy-MM-dd
        DateFormat dateFormat = new SimpleDateFormat(formato);
        return dateFormat.format(fecha);
    }
}
