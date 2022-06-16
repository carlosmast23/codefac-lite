/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.varios;

import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadesCodigos {
    
    /**
     * Generar formato de los codigos
     * @param prefijo
     * @param numerador
     * @return 
     */
    public static String generarFormatoCodigo(String prefijo,Integer numerador,Integer tamanioCodigo)
    {
        String numeroConFormato=UtilidadesTextos.llenarCarateresIzquierda(numerador.toString(),tamanioCodigo,"0");
        return prefijo+numeroConFormato;
        //return prefijo
    }
    
    public static String generarPrefijo(String codigoEmpresa,String codigoSucursal,String codigoDocumento,String separador)
    {
        return codigoEmpresa+separador+codigoSucursal+separador+codigoDocumento+separador;
    }
    
    /**
     * Genera un codigo unico alfanumerico muy grande
     * @return 
     */
    public static String generarCodigoUnicoUUID()
    {
        return UUID.randomUUID().toString().replace("-","");
    }
    
    /**
     * TODO: Para que sea exacto este metodo deberia ejecutarse en el servidor, y parece que en este momento se va a generar pero desde el cliente
     * @return 
     */
    public static String generarCodigoConTiempo()
    {
        java.util.Date fechaActual= UtilidadesFecha.getFechaHoraHoy();
        //String anio=UtilidadesFecha.obtenerAnioStr(UtilidadesFecha.castDateUtilToSql(fechaActual));
        //String mes=UtilidadesFecha.obtenerMesStr(UtilidadesFecha.castDateUtilToSql(fechaActual));
        //String dia=UtilidadesFecha.obtenerDiaStr(UtilidadesFecha.castDateUtilToSql(fechaActual));
        
        SimpleDateFormat sdf=new SimpleDateFormat("yyMMddHHmmSSS");
        return sdf.format(fechaActual);
    }
}
