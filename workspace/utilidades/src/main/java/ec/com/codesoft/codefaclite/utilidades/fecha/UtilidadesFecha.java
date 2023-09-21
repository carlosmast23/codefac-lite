/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.fecha;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
        Calendar calendario = Calendar.getInstance();
        calendario.set(anio,mes, 1);
        return calendario.getTime();
    }
    
    public static java.util.Date getUltimoDiaMes(int anio, int mes)
    {
        Calendar calendario = Calendar.getInstance();
        calendario.set(anio,mes, 1);
        calendario.set(anio, mes, calendario.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendario.getTime();
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
    
    public static java.util.Date getFechaHoraHoy() {
        java.util.Date fechaHoy = new java.util.Date();
        return fechaHoy;
    }
    
    public static String getFechaHoraHoyFormat()
    {
        java.util.Date fecha=getFechaHoraHoy();
        SimpleDateFormat formato=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formato.format(fecha);
    }
    
    public static Timestamp getFechaHoyTimeStamp()
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp;
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
        
        if(fechaMenor==null || fechaMayor==null)
        {
            return 0;
        }
        
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); //todo: Ver si estos dates format se hace un formato global
        //Formatear sin tiempo la fecha para evitar problemas en los calculos por las horas
        //fechaMenor=formato.parse(formato.format(fechaMenor)); //Revisar si no hay problema porque estoy quitando
        //fechaMayor=formato.parse(formato.format(fechaMayor)); //Revisar si no hay problema porque estoy quitando
        LocalDate fechaMenorLocalDate = LocalDate.parse(formato.format(fechaMenor), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate fechaMayorLocalDate = LocalDate.parse(formato.format(fechaMayor), DateTimeFormatter.ISO_LOCAL_DATE);
        
        Long dias=ChronoUnit.DAYS.between(fechaMenorLocalDate, fechaMayorLocalDate);
                
        //int dias = (int) ((fechaMayor.getTime() - fechaMenor.getTime()) / 86400000);
        return dias.intValue();
    }
    
    public static int obtenerDistanciaConLaFechaActual(java.util.Date fecha)
    {
        return obtenerDistanciaDias(fecha,getFechaHoy());
    }

    public static String formatDate(java.util.Date fecha, String formato) {
        //yyyy MMMMM dd
        //yyyy-MM-dd
        DateFormat dateFormat = new SimpleDateFormat(formato);
        return dateFormat.format(fecha);
    }
    
    public static String formatDate(java.util.Date fecha, SimpleDateFormat simpleDateFormat) {
        //yyyy MMMMM dd
        //yyyy-MM-dd
        //DateFormat dateFormat = new SimpleDateFormat(formato);
        return simpleDateFormat.format(fecha);
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
    
    /**
     * Obtiene el dia con respecto a la semana
     * @param date
     * @return 
     */
    public static int obtenerDiaSemana(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Integer diaSemana=cal.get(Calendar.DAY_OF_WEEK);
        
        //Corregir problema que devuelve por deecto que el primer dia de la semana es el domingo
        if(diaSemana==1)
        {
            diaSemana=7;
        }else
        {
            diaSemana=diaSemana-1;
        }
        
        return diaSemana;

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
    
    public static java.util.Date restarDiasFecha(java.util.Date date, int dias)
    {
        return sumarDiasFecha(date,-1* dias);
    }
    
    public static java.util.Date sumarTimeAFecha(java.util.Date date, Time time) {
        // Obtiene los valores de horas, minutos y segundos del objeto Time
        int hour = time.getHours();
        int minute = time.getMinutes();
        int second = time.getSeconds();

        // Crea un objeto Calendar para manipular la fecha y hora
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Suma los valores de horas, minutos y segundos al objeto Calendar
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        calendar.add(Calendar.MINUTE, minute);
        calendar.add(Calendar.SECOND, second);

        // Devuelve el nuevo objeto Date con la suma realizada
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
            
    public static java.util.Date sumarAniosFecha(java.util.Date date, int anios)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR,anios);
        return calendar.getTime();
    }
    
    public static java.util.Date sumarMesesFecha(java.util.Date date, int meses)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,meses);
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
    
    public static Date cambiarDiaFecha(Date fecha,Integer dia)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.set(Calendar.DAY_OF_MONTH,dia);

        return new java.sql.Date(calendar.getTime().getTime()); // Devuelve el objeto Date con los nuevos días añadidos
    }
    
    public static java.util.Date castDateSqlToUtil(java.sql.Date fechaSql)
    {
        if(fechaSql==null)
            return null;
            
        return new java.util.Date(fechaSql.getTime());
    }
    
    public static java.util.Date castTimeSqlToUtil(java.sql.Time horaSql)
    {
        if(horaSql==null)
            return null;
            
        return new java.util.Date(horaSql.getTime());
    }
    
    public static java.sql.Date castDateUtilToSql(java.util.Date fechaUtil)
    {
        if(fechaUtil==null)
            return null;
        
        return new java.sql.Date(fechaUtil.getTime());
    }
    
    public static java.util.Date castStringToDate(String fechaStr,SimpleDateFormat simpleDateFormat)
    {
        try {
            return simpleDateFormat.parse(fechaStr);
        } catch (ParseException ex) {
            Logger.getLogger(UtilidadesFecha.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Timestamp castDateSqlToTimeStampSql(Date date){
        if(date == null){
            return null;
        }
        return new Timestamp(date.getTime());
    }
    
    public static Timestamp castDateSqlToTimeStampSql(java.util.Date date){
        if(date == null){
            return null;
        }
        return new Timestamp(date.getTime());
    }
    
    public static Date FechaHoraPorUnion(Date date, Date time){
        //Date tipo SQL
        GregorianCalendar dateCal = new GregorianCalendar();
        GregorianCalendar timeCal = new GregorianCalendar();    
        dateCal.setTime(date);
        timeCal.setTime(time);
        
        int año = dateCal.get(Calendar.YEAR);
        int mes = dateCal.get(Calendar.MONTH);
        int dia = dateCal.get(Calendar.DAY_OF_MONTH);

        int hora = timeCal.get(Calendar.HOUR_OF_DAY);
        int minutos = timeCal.get(Calendar.MINUTE);
        int segundos = timeCal.get(Calendar.SECOND);
        
        GregorianCalendar dateTime = new GregorianCalendar(año, mes, dia, hora, minutos, segundos);
        java.util.Date dateTimeUitl = dateTime.getTime();
        return UtilidadesFecha.castDateUtilToSql(dateTimeUitl);
    }
    
    public static Date getFechaDeTimeStamp(Timestamp date)
    {
        
        GregorianCalendar dateCal = new GregorianCalendar();
        dateCal.setTime(date);
        
        int año = dateCal.get(Calendar.YEAR);
        int mes = dateCal.get(Calendar.MONTH);
        int dia = dateCal.get(Calendar.DAY_OF_MONTH);
        
        GregorianCalendar onlyDate = new GregorianCalendar(año, mes, dia);
        java.util.Date dateUtil = onlyDate.getTime();
        
        return UtilidadesFecha.castDateUtilToSql(dateUtil);
    }
    
    public static Date getHoraDeTimeStamp(Timestamp date)
    {
        GregorianCalendar timeCal = new GregorianCalendar();
        timeCal.setTime(date);
        
        int horas = timeCal.get(Calendar.HOUR_OF_DAY);
        int minutos = timeCal.get(Calendar.MINUTE);
        int segundos = timeCal.get(Calendar.SECOND);
        
        GregorianCalendar onlyTime = new GregorianCalendar(0,0,0,horas,minutos,segundos);
        java.util.Date dateUtil = onlyTime.getTime();
        
        return UtilidadesFecha.castDateUtilToSql(dateUtil);
    }
    
    public static Timestamp castDateToTimeStamp(java.sql.Date fecha)
    {
        return new Timestamp(fecha.getTime());
    }
    
    public static Timestamp castDateToTimeStamp(java.util.Date fecha)
    {
        return new Timestamp(fecha.getTime());
    }
    
    /**
     * Metodo que me permite verificar si una fecha esta dentro de una cantidad de dias previos
     * @return 
     */
    public static Boolean validarfechaDentroDeRango(Timestamp fechaEmision,Integer diasTolerancia)
    {
        int distanciaDias=obtenerDistanciaConLaFechaActual(fechaEmision);
        if(distanciaDias>diasTolerancia)
        {
            return false;
        }
        return true;
    
    }

    public static java.util.Date eliminarHorasFecha(java.util.Date fechaConHoras) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaConHoras);

        // Establece las horas, minutos, segundos y milisegundos a cero
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        // Devuelve la fecha sin horas
        return cal.getTime();
    }

    public static int compararFechaSinImportarHora(java.util.Date fecha1, java.util.Date fecha2)
    {
        fecha1=eliminarHorasFecha(fecha1);
        fecha2=eliminarHorasFecha(fecha2);
        
        return fecha1.compareTo(fecha2);
    }
    
    /**
     * Metodo que puede verificar si una fecha es superior al día actual sin tomar en cuentas las horas
     * @return 
     */
    public static Boolean verificarFechaEsSuperiorAlDiaActual(java.util.Date fecha)
    {        
        if(compararFechaSinImportarHora(fecha,UtilidadesFecha.getFechaHoy())>0)
        {
            return true;
        }
        return false;
    }
    
    public static java.util.Date combinarFechayHora(java.util.Date date, Time time) 
    {
        // Crea un objeto Calendar para manipular la fecha
        Calendar dateCalendar = Calendar.getInstance();
        
        // Establece el calendario de la fecha al objeto "date"
        dateCalendar.setTime(date);
        
        // Obtiene la hora, minutos, segundos y milisegundos de "time"
        /*long timeInMillis = time.getTime();
        int hour = (int) (timeInMillis / 3600000); // 3600000 ms en una hora
        int minute = (int) ((timeInMillis % 3600000) / 60000); // 60000 ms en un minuto
        int second = (int) ((timeInMillis % 60000) / 1000); // 1000 ms en un segundo
        int millisecond = (int) (timeInMillis % 1000);*/
        int hour = time.getHours();
        int minute = time.getMinutes();
        int second = time.getSeconds();
        
        
        // Establece la hora, minutos, segundos y milisegundos en "date"
        dateCalendar.set(Calendar.HOUR_OF_DAY, hour);
        dateCalendar.set(Calendar.MINUTE, minute);
        dateCalendar.set(Calendar.SECOND, second);
        dateCalendar.set(Calendar.MILLISECOND, 0);
        
        // Devuelve el nuevo objeto Date con la fecha y hora combinadas
        return dateCalendar.getTime();
    }

    /**
     * Esta funcion calcula la distancia entre 2 fechas y cuando la fecha 1 es superior a la fecha 2 asume que tiene que ser del siguiente día
     * @param time1
     * @param time2
     * @return 
     */
    public static Time obtenerDistanciaHoras(Time time1, Time time2) 
    {
         long timeInMillis1 = time1.getTime();
        long timeInMillis2 = time2.getTime();
        
        // Si time1 es mayor que time2, asumimos que time2 pertenece al día siguiente
        if (timeInMillis1 > timeInMillis2) {
            // Agregamos 24 horas (en milisegundos) a time2 para completar el día siguiente
            timeInMillis2 += 24 * 60 * 60 * 1000; // 24 horas * 60 minutos * 60 segundos * 1000 milisegundos
        }
        
        // Calcula la diferencia en milisegundos entre los dos tiempos
        long differenceInMillis = Math.abs(timeInMillis1 - timeInMillis2);
        
        // Convierte la diferencia en horas y minutos
        int hours = (int) (differenceInMillis / (1000 * 60 * 60));
        int minutes = (int) ((differenceInMillis / (1000 * 60)) % 60);
        
        // Crea un nuevo objeto Time con la cantidad de horas y minutos calculados
        return new Time(hours, minutes, 0);
    }
    
    public static Time extractTimeFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);

        long millisecondsInTime = hours * 60 * 60 * 1000 + minutes * 60 * 1000 + seconds * 1000;
        return new Time(millisecondsInTime);
    }

    public static Time obtenerTiempoActual() {
        long currentTimeMillis = System.currentTimeMillis();
        return new Time(currentTimeMillis);
    }
    
     public static boolean comparaFechaEntreRango(java.util.Date fecha, java.util.Date fechaInicio, java.util.Date fechFin) {
        // Compara si la fecha a verificar está entre la fecha de inicio y la fecha de fin
        return fecha.compareTo(fechaInicio) >= 0 && fecha.compareTo(fechFin) <= 0;
    }
    
    public static int obtenerAnioActual()
    {
        return obtenerAnio(getFechaHoy());
    }
    
    public static int obtenerMesActual()
    {
        return obtenerMes(getFechaHoy());
    }
    
    public static int obtenerDiaActual()
    {
        return obtenerDia(getFechaHoy());
    }
    
    public static java.util.Date agregarTiempoFinalDia(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.HOUR_OF_DAY, 23);
        calendar.add(Calendar.MINUTE, 59);
        calendar.add(Calendar.SECOND, 59);
        return calendar.getTime();
    }
    
     public static java.util.Date obtenerPrimerDiaDelMes() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1); // Establece el día al primero del mes actual
        return calendar.getTime(); // Convierte Calendar a Date
    }
     
    public static int calcularDiferenciaEnHoras(Timestamp timestampInicial, Timestamp timestampFinal) {
        // Calcula la diferencia en milisegundos
        long diferenciaEnMilisegundos = timestampFinal.getTime() - timestampInicial.getTime();

        // Calcula la diferencia en horas
        int diferenciaEnHoras = (int) (diferenciaEnMilisegundos / (60 * 60 * 1000));

        return diferenciaEnHoras;
    }

}
