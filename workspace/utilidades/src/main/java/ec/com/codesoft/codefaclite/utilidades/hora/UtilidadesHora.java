/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.hora;

import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 */
public class UtilidadesHora 
{
    
    public static Time horaActual() 
    {
        java.util.Date hora = new java.util.Date();
        return new Time(hora.getTime());
    }
    
    /*public static aaa()
    {
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    }*/
    
    public static boolean comprobarHoraEnRangoDeTiempoTmp(Time horaInicial, Time horaFinal, Time hora,Integer cantidadDias)
    {
        //Si no existe ningun valor asignado por defecto devuelvo CERO y asumo que se esta haciendo dentro del mismo día
        if(cantidadDias==null)
        {
            cantidadDias=0;
        }
        
        try {
            SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
            
            java.util.Date horaInicialAux=parser.parse(parser.format(horaInicial));
            java.util.Date horaFinalAux=parser.parse(parser.format(horaFinal));
            java.util.Date horaAux=parser.parse(parser.format(hora));
            
            System.out.println(horaAux.after(horaInicialAux));
            System.out.println(horaAux.before(horaFinalAux));
            if(horaAux.after(horaInicialAux) && horaAux.before(horaFinalAux))
            {
                return true;
            }
            return false;
            //java.sql.Date horaInicialAux = UtilidadesFecha.FechaHoraPorUnion(transformarTimeSqlToDateSql(hora), transformarTimeSqlToDateSql(horaInicial));
            //java.sql.Date horaFinalAux = UtilidadesFecha.FechaHoraPorUnion(transformarTimeSqlToDateSql(hora), transformarTimeSqlToDateSql(horaFinal));
            //java.sql.Date horaAux = transformarTimeSqlToDateSql(hora);
            
            //System.out.println("1.- "+horaAux);
            //System.out.println("2.- "+horaInicialAux);
            //System.out.println("3.- "+horaFinalAux);
            
            //return horaAux.after(horaInicialAux) && horaAux.before(horaFinalAux);
        } catch (ParseException ex) {
            Logger.getLogger(UtilidadesHora.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static boolean comprobarHoraEnRangoDeTiempo(Date fechaAperturaCaja,Time horaInicial, Time horaFinal,Integer cantidadDias)
    {
        //Si no existe ningun valor asignado por defecto devuelvo CERO y asumo que se esta haciendo dentro del mismo día
        if(cantidadDias==null)
        {
            cantidadDias=0;
        }
        
        //TODO: Si la cantidad de dias es negativa entonces asumo que siempre debe coger como referencia el dia de apertura el mismo día
        if(cantidadDias<0)
        {
            fechaAperturaCaja=UtilidadesFecha.getFechaHoraHoy();
        }
        else
        {
            Integer distanciaDiasTmp=UtilidadesFecha.obtenerDistanciaDias(fechaAperturaCaja,UtilidadesFecha.getFechaHoy());
            if(distanciaDiasTmp>cantidadDias)
            {
                //Si la distancia de dias es mayor que la permitida ya no hace ninguna validacion
                return false;
            }
            else
            {
                //Caso contrario tomo como referencia la feha del dia de hoy para la comparacion
                fechaAperturaCaja=UtilidadesFecha.getFechaHoraHoy();
            }
        }
        
        //TODO: Verificar si el día de hoy que estamos haciendo la comparacion esta dentro de la cantidad de dias que tiene tolerancia
        
        
        
        
        java.util.Date fechaInicioCaja=UtilidadesFecha.combinarFechayHora(fechaAperturaCaja, horaInicial);
        //Calcular el numero de horas de diferencia entre una fecha y otra
        Time horasDiferencia=UtilidadesFecha.obtenerDistanciaHoras(horaInicial,horaFinal);
                        
        Date fechaFinCaja=UtilidadesFecha.sumarTimeAFecha(fechaInicioCaja, horasDiferencia);
        
        //Comparar si la fecha se encuentra dentro del rango de permisos disponibles
        if(UtilidadesFecha.comparaFechaEntreRango(UtilidadesFecha.getFechaHoy(),fechaInicioCaja,fechaFinCaja))
        {
            return true;
        }
        
        
        
        /*try {
            SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
            
            java.util.Date horaInicialAux=parser.parse(parser.format(horaInicial));
            java.util.Date horaFinalAux=parser.parse(parser.format(horaFinal));
            java.util.Date horaAux=parser.parse(parser.format(hora));
            
            System.out.println(horaAux.after(horaInicialAux));
            System.out.println(horaAux.before(horaFinalAux));
            if(horaAux.after(horaInicialAux) && horaAux.before(horaFinalAux))
            {
                return true;
            }
            return false;
            //java.sql.Date horaInicialAux = UtilidadesFecha.FechaHoraPorUnion(transformarTimeSqlToDateSql(hora), transformarTimeSqlToDateSql(horaInicial));
            //java.sql.Date horaFinalAux = UtilidadesFecha.FechaHoraPorUnion(transformarTimeSqlToDateSql(hora), transformarTimeSqlToDateSql(horaFinal));
            //java.sql.Date horaAux = transformarTimeSqlToDateSql(hora);
            
            //System.out.println("1.- "+horaAux);
            //System.out.println("2.- "+horaInicialAux);
            //System.out.println("3.- "+horaFinalAux);
            
            //return horaAux.after(horaInicialAux) && horaAux.before(horaFinalAux);
        } catch (ParseException ex) {
            Logger.getLogger(UtilidadesHora.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        return false;
    }
    
    public static java.sql.Date transformarTimeSqlToDateSql(Time hora)
    {
        return new java.sql.Date(hora.getTime());
    }
    
}
