/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author Carlos
 */
public class ReporteComprobantesElectronicosProxy {
    //public URL pathRecursos;
    //public JasperReport jasperReport;
    
    public Object clave;
    public Object valor;
    
    //public static List<ReporteComprobantesElectronicosProxy> lista=new ArrayList<ReporteComprobantesElectronicosProxy>();
    public static List<ReporteComprobantesElectronicosProxy> lista=new ArrayList<ReporteComprobantesElectronicosProxy>();

    public ReporteComprobantesElectronicosProxy(Object clave) {
        this.clave = clave;
    }

    public ReporteComprobantesElectronicosProxy(Object clave, Object valor) {
        this.clave = clave;
        this.valor = valor;
    }
    
    
    /*public static void agregar(URL pathRecursos,JasperReport jasperReport)
    {
        ReporteComprobantesElectronicosProxy reporte=new ReporteComprobantesElectronicosProxy(pathRecursos, jasperReport);
        lista.add(reporte);
    }*/
    
    public static void agregar(Object clave,Object valor)
    {
        ReporteComprobantesElectronicosProxy reporte=new ReporteComprobantesElectronicosProxy(clave, valor);
        lista.add(reporte);
    }
    
    public static Object obtenerObjeto(Object clave)
    {
        for (ReporteComprobantesElectronicosProxy reporte : lista) 
        {
            if(reporte.clave.equals(clave))
            {
                return reporte.valor;
            }
        }
        return null;
    }
    
    public static JasperReport obtenerReporte(URL pathRecursos)
    {
        for (ReporteComprobantesElectronicosProxy reporte : lista) 
        {
            if(reporte.clave instanceof URL)
            {
                URL pathRecursosTmp=(URL) reporte.clave;
                if(pathRecursosTmp.getPath().equals(pathRecursos.getPath()))
                {
                    return(JasperReport) reporte.valor;
                }
            }
        }
        return null;
    }
    
    
    
    
            
}
