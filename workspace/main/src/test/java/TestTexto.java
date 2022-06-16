
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TestTexto {
    public static void main(String[] args) {        
        Date fechaActual= UtilidadesFecha.getFechaHoy();
        SimpleDateFormat sdf=new SimpleDateFormat("yyMMddHHmmSSS");
        System.out.println(sdf.format(fechaActual));
        
        /*fechaActual= UtilidadesFecha.getFechaHoy();
        sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        System.out.println(sdf.format(fechaActual));
        
        fechaActual= UtilidadesFecha.getFechaHoy();
        sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        System.out.println(sdf.format(fechaActual));
        
        fechaActual= UtilidadesFecha.getFechaHoy();
        sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        System.out.println(sdf.format(fechaActual));*/
    } 
}
