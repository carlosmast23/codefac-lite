/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.report;

import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Carlos
 */
public class ReporteCodefac {
    public static void generarReporte(String pathReporte,Map parametros,Collection datos)
    {
        try {
            JasperReport report =JasperCompileManager.compileReport(pathReporte);
            JRBeanCollectionDataSource dataReport= new JRBeanCollectionDataSource(datos);
            JasperPrint print =JasperFillManager.fillReport(report, parametros,dataReport);
            JasperViewer.viewReport(print,false);
        } catch (JRException ex) {
            Logger.getLogger(ReporteCodefac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void generarReporte(String pathReporte,Map parametros)
    {
        try {
            JasperReport report =JasperCompileManager.compileReport(pathReporte);            
            JasperPrint print =JasperFillManager.fillReport(report, parametros,new JREmptyDataSource());
            JasperViewer.viewReport(print,false);
        } catch (JRException ex) {
            Logger.getLogger(ReporteCodefac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void generarReportePlantilla(String pathReporte,Map parametros)
    {
        try {
            JasperReport report =JasperCompileManager.compileReport(pathReporte);            
            JasperPrint print =JasperFillManager.fillReport(report, parametros,new JREmptyDataSource());
            JasperViewer.viewReport(print,false);
        } catch (JRException ex) {
            Logger.getLogger(ReporteCodefac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void generarReporteInternalFrame(String pathReporte,Map parametros,Collection datos,InterfazComunicacionPanel panelPadre,String tituloReporte)
    {
        try {
            JasperReport report =JasperCompileManager.compileReport(pathReporte);
            JRBeanCollectionDataSource dataReport= new JRBeanCollectionDataSource(datos);
            JasperPrint print =JasperFillManager.fillReport(report, parametros,dataReport);
            //JasperViewer.viewReport(print,false);
            panelPadre.crearReportePantalla(print,tituloReporte);
        } catch (JRException ex) {
            Logger.getLogger(ReporteCodefac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void generarReporteInternalFrame(InputStream pathReporte,Map parametros,Collection datos,InterfazComunicacionPanel panelPadre,String tituloReporte)
    {
        try {
            JasperReport report =JasperCompileManager.compileReport(pathReporte);
            JRBeanCollectionDataSource dataReport= new JRBeanCollectionDataSource(datos);
            JasperPrint print =JasperFillManager.fillReport(report, parametros,dataReport);
            //JasperViewer.viewReport(print,false);
            panelPadre.crearReportePantalla(print,tituloReporte);
        } catch (JRException ex) {
            Logger.getLogger(ReporteCodefac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Map<String,Object> agregarMapPlantilla(Map<String,Object> parametros,String tituloReporte,InterfazComunicacionPanel panelPadre)
    {
            Map<String,Object> mapCompleto=new HashMap<String,Object>(panelPadre.mapReportePlantilla(OrientacionReporteEnum.VERTICAL,FormatoHojaEnum.A4));            
            //Agregado parametros adicionales
            for (Map.Entry<String, Object> entry : parametros.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                mapCompleto.put(key, value);
            }
            
            mapCompleto.put("pl_titulo",tituloReporte);
            //mapCompleto.putAll(parametros);
            return mapCompleto;        
    }
    
    public static void generarReporteInternalFramePlantilla(InputStream pathReporte,Map<String,Object> parametros,Collection datos,InterfazComunicacionPanel panelPadre,String tituloReporte)
    {
        generarReporte(pathReporte,parametros,datos,panelPadre,tituloReporte,OrientacionReporteEnum.VERTICAL,FormatoHojaEnum.A4);
    }
    
    public static void generarReporteInternalFramePlantilla(InputStream pathReporte, Map<String, Object> parametros, Collection datos, InterfazComunicacionPanel panelPadre, String tituloReporte, OrientacionReporteEnum orientacionEnum) {
        generarReporte(pathReporte,parametros,datos,panelPadre,tituloReporte,orientacionEnum,FormatoHojaEnum.A4);
    }
    
    public static void generarReporteInternalFramePlantilla(InputStream pathReporte, Map<String, Object> parametros, Collection datos, InterfazComunicacionPanel panelPadre, String tituloReporte, OrientacionReporteEnum orientacionEnum,FormatoHojaEnum formatoReporte) {
        generarReporte(pathReporte, parametros, datos, panelPadre, tituloReporte, orientacionEnum,formatoReporte);
    }
    
    public static void generarReporteInternalFramePlantillaArchivo(InputStream pathReporte, Map<String, Object> parametros, Collection datos, InterfazComunicacionPanel panelPadre, String tituloReporte, OrientacionReporteEnum orientacionEnum,FormatoHojaEnum formatoReporte,String path) {
        try {
            JasperPrint jasperPrint=construirReporte(pathReporte, parametros, datos, panelPadre, tituloReporte, orientacionEnum, formatoReporte);
            JasperExportManager.exportReportToPdfFile(jasperPrint, path);
        } catch (JRException ex) {
            Logger.getLogger(ReporteCodefac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    private static void generarReporte(InputStream pathReporte,Map<String,Object> parametros,Collection datos,InterfazComunicacionPanel panelPadre,String tituloReporte,OrientacionReporteEnum orientacionEnum,FormatoHojaEnum formatoReporte)
    {
        JasperPrint jasperPrint=construirReporte(pathReporte, parametros, datos, panelPadre, tituloReporte, orientacionEnum, formatoReporte);
        panelPadre.crearReportePantalla(jasperPrint,tituloReporte);
    } 
    
    private static JasperPrint construirReporte(InputStream pathReporte,Map<String,Object> parametros,Collection datos,InterfazComunicacionPanel panelPadre,String tituloReporte,OrientacionReporteEnum orientacionEnum,FormatoHojaEnum formatoReporte)
    {
        try {
            Map<String,Object> mapCompleto=new HashMap<String,Object>(panelPadre.mapReportePlantilla(orientacionEnum,formatoReporte));
            
            //Agregado parametros adicionales
            for (Map.Entry<String, Object> entry : parametros.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                mapCompleto.put(key, value);
            }
            
            mapCompleto.put("pl_titulo",tituloReporte);
            //mapCompleto.putAll(parametros);
            JasperReport report =JasperCompileManager.compileReport(pathReporte);
            JRBeanCollectionDataSource dataReport= new JRBeanCollectionDataSource(datos);
            JasperPrint print =JasperFillManager.fillReport(report, mapCompleto,dataReport);
            return print;
        } catch (JRException ex) {
            Logger.getLogger(ReporteCodefac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
