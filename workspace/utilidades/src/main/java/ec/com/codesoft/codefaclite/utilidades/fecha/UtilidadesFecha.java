/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.fecha;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Carlos
 */
public class UtilidadesFecha {
    
    public static java.util.Date getPrimerDiaMes(int anio, int mes)
    {
        /*
        int anio = obtenerAnio(anio);
        int mes = obtenerMes(fecha);*/
        Calendar calendario = Calendar.getInstance();
        calendario.set(anio,mes, 1);
        return calendario.getTime();
        //int ultimoDiaMes = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    
    public static java.util.Date getUltimoDiaMes(int anio, int mes)
    {
        /*
        int anio = obtenerAnio(fecha);
        int mes = obtenerMes(fecha);*/
        Calendar calendario = Calendar.getInstance();
        calendario.set(anio,mes, 1);
        calendario.set(anio, mes, calendario.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendario.getTime();
        //int ultimoDiaMes = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    
    
    public static java.util.Date getFechaNTP() throws Exception
    {
        //Generamos un objeto de la clase ObtenerFecha. 
 
        ObtenerFecha objFecha=new ObtenerFecha(); 
        //Generamos otro objeto de la clase SimpleDateFormat para darle formato a la fecha 
 
        return objFecha.getNTPDate(); 
    }
   
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
 * etodo que obtiene dias de dias
 * @param fechaMenor pendiente
 * @param fechaMayor nada
 * @return dias
 */
    public static int obtenerDistanciaDias(java.util.Date fechaMenor, java.util.Date fechaMayor) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); //todo: Ver si estos dates format se hace un formato global
        try {
            //Formatear sin tiempo la fecha para evitar problemas en los calculos por las horas
            fechaMenor=formato.parse(formato.format(fechaMenor)); //Revisar si no hay problema porque estoy quitando 
            fechaMayor=formato.parse(formato.format(fechaMayor)); //Revisar si no hay problema porque estoy quitando 
        } catch (ParseException ex) {
            Logger.getLogger(UtilidadesFecha.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        int dias = (int) ((fechaMayor.getTime() - fechaMenor.getTime()) / 86400000);
        return dias;
    }

    public static String formatDate(java.util.Date fecha, String formato) {
        //yyyy MMMMM dd
        //yyyy-MM-dd
        DateFormat dateFormat = new SimpleDateFormat(formato);
        return dateFormat.format(fecha);
    }
    
    public static String obtenerAnioStr(Date date) {

        if (null == date) {

            return "0";

        } else {

            String formato = "yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
            return dateFormat.format(date);

        }

    }
    
    public static int obtenerAnio(Date date) {

        return Integer.parseInt(obtenerAnioStr(date));

    }
    
    public static String obtenerMesStr(Date date) {

        if (null == date) {

            return "0";

        } else {

            String formato = "MM";
            SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
            return dateFormat.format(date);

        }

    }

    public static int obtenerMes(Date date) {
        return Integer.parseInt(obtenerMesStr(date));
    }
    

    public static String obtenerDiaStr(Date date) {

        if (null == date) {

            return "0";

        } else {

            String formato = "dd";
            SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
            return dateFormat.format(date);

        }

    }

    public static int obtenerDia(Date date) {
        return Integer.parseInt(obtenerDiaStr(date));

    }
    
    public static String formatoDiaMesAño(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }
    
    public static java.util.Date fechaProxima(java.util.Date date, int numero, String e)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch(e)
        {
            case "Dia":
                calendar.add(Calendar.DAY_OF_YEAR, numero);
                break;
            case "Mes":
                calendar.add(Calendar.MONTH, numero);
                break;
        };
        return calendar.getTime();
    }
    
    /**
     * @author: Carlos Sánchez 
     * @param date
     * @param dias
     * @return 
     */
    public static java.util.Date sumarDiasFecha(java.util.Date date, int dias)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,dias);
        return calendar.getTime();
    }

    public static java.sql.Date stringFormatXMLGregorianCalendarToDate(String fechaFormat)
    {
        if(fechaFormat==null || fechaFormat.isEmpty())
        {
            return null;
        }
        
        try {
            XMLGregorianCalendar result = DatatypeFactory.newInstance().newXMLGregorianCalendar(fechaFormat);
            java.sql.Date fechaAutorizacion = new java.sql.Date(result.toGregorianCalendar().getTime().getTime());
            return fechaAutorizacion;
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(UtilidadesFecha.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    

}
