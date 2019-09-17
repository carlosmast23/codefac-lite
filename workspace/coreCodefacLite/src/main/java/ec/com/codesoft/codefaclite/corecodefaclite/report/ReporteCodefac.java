/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.report;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.proxy.ReporteProxy;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.utilidades.imagen.UtilidadImagen;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
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
    
    @Deprecated
    public static void generarReporteInternalFramePlantilla(InputStream pathReporte, Map<String, Object> parametros, Collection datos, InterfazComunicacionPanel panelPadre, String tituloReporte, OrientacionReporteEnum orientacionEnum,FormatoHojaEnum formatoReporte) {
        generarReporte(pathReporte, parametros, datos, panelPadre, tituloReporte, orientacionEnum,formatoReporte);
    }
    
    /**
     * Metodo creado para ver si se compila el reporte y puedo tener un proxy de los reportes y acceder mas rapidos a los mismos
     * @Date 17/09/2019     
     */
    public static void generarReporteInternalFramePlantilla(RecursoCodefac recursoCodefac,String nombre, Map<String, Object> parametros, Collection datos, InterfazComunicacionPanel panelPadre, String tituloReporte, OrientacionReporteEnum orientacionEnum,FormatoHojaEnum formatoReporte) {
        generarReporte(recursoCodefac,nombre, parametros, datos, panelPadre, tituloReporte, orientacionEnum,formatoReporte);
    }
    
    public static void generarReporteInternalFramePlantillaArchivo(InputStream pathReporte, Map<String, Object> parametros, Collection datos, InterfazComunicacionPanel panelPadre, String tituloReporte, OrientacionReporteEnum orientacionEnum,FormatoHojaEnum formatoReporte,String path) {
        try {
            JasperPrint jasperPrint=construirReporte(pathReporte, parametros, datos, panelPadre, tituloReporte, orientacionEnum, formatoReporte);
            JasperExportManager.exportReportToPdfFile(jasperPrint, path);
        } catch (JRException ex) {
            Logger.getLogger(ReporteCodefac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo creado para ver si se compila el reporte y puedo tener un proxy de los reportes y acceder mas rapidos a los mismos
     * @Date 17/09/2019     
     */
    private static void generarReporte(RecursoCodefac recursoCodefac,String nombre,Map<String,Object> parametros,Collection datos,InterfazComunicacionPanel panelPadre,String tituloReporte,OrientacionReporteEnum orientacionEnum,FormatoHojaEnum formatoReporte)
    {
        JasperPrint jasperPrint=construirReporte(recursoCodefac,nombre, parametros, datos, panelPadre, tituloReporte, orientacionEnum, formatoReporte);
        panelPadre.crearReportePantalla(jasperPrint,tituloReporte);
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
    
    /**
     * Metodo creado para ver si se compila el reporte y puedo tener un proxy de los reportes y acceder mas rapidos a los mismos
     * @Date 17/09/2019     
     */
    private static JasperPrint construirReporte(RecursoCodefac recursoCodefac,String nombre,Map<String,Object> parametros,Collection datos,InterfazComunicacionPanel panelPadre,String tituloReporte,OrientacionReporteEnum orientacionEnum,FormatoHojaEnum formatoReporte)
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
            
            /**
             * Agregado reporte para tener un proxy de objetos
             */
            JasperReport report=ReporteProxy.buscar(recursoCodefac, nombre);
            if(report==null)
            {
                InputStream inputStream= recursoCodefac.getResourceInputStream(nombre);
                report =JasperCompileManager.compileReport(inputStream);
                ReporteProxy.agregar(recursoCodefac,nombre,report);
                
            }
            
            JRBeanCollectionDataSource dataReport= new JRBeanCollectionDataSource(datos);
            JasperPrint print =JasperFillManager.fillReport(report, mapCompleto,dataReport);
            return print;
        } catch (JRException ex) {
            Logger.getLogger(ReporteCodefac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Metodo creado para ver si se compila el reporte y puedo tener un proxy de los reportes y acceder mas rapidos a los mismos
     * @Date 17/09/2019     
     */
    public static JasperPrint construirReporte(InputStream pathReporte,Map<String,Object> parametros,Collection datos,SessionCodefac session,String tituloReporte,OrientacionReporteEnum orientacionEnum,FormatoHojaEnum formatoReporte)
    {
        try {
            //mapReportePlantilla(orientacionEnum, formatoReporte, session);
            Map<String,Object> mapCompleto=new HashMap<String,Object>(mapReportePlantilla(orientacionEnum,formatoReporte,session));
            
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
    
    /**
     * TODO: Ver si se puede unir con el metodo que esta en GeneralPanelModel
     * @param orientacionEnum
     * @param formatoReporte
     * @param sessionCodefac
     * @return 
     */
    public static Map<String, Object> mapReportePlantilla(OrientacionReporteEnum orientacionEnum,FormatoHojaEnum formatoReporte,SessionCodefac sessionCodefac) {
        InputStream inputStream = null;
        
        SimpleDateFormat formateador = new SimpleDateFormat("d MMM yyyy HH:mm:ss");
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("pl_fecha_hora", formateador.format(new Date()));
        parametros.put("pl_usuario", sessionCodefac.getUsuario().getNick());
        parametros.put("pl_direccion", sessionCodefac.getSucursal().getDirecccion()); //TODO: Ver si agregar la direccion general de la matriz
        
        String nombreComercial=sessionCodefac.getEmpresa().getNombreLegal();
        if(nombreComercial==null || nombreComercial.trim().isEmpty())
        {
            nombreComercial=sessionCodefac.getEmpresa().getRazonSocial();
        }
        
        parametros.put("pl_nombre_empresa", nombreComercial);
        parametros.put("pl_telefonos", sessionCodefac.getMatriz().getTelefono());
        parametros.put("pl_celular", sessionCodefac.getMatriz().getCelular());
        parametros.put("pl_facebook", sessionCodefac.getEmpresa().getFacebook());
        parametros.put("pl_ruc", sessionCodefac.getEmpresa().getIdentificacion());
        
        /**
         * Agregado valdación cuando no llenen ningun dato que salgo información del sistema cuando el usuario tiene una licencia gratuita
         * @author Carlos Sanchez
         * @fecha 03/11/2018
         */
        if(sessionCodefac.getTipoLicenciaEnum().equals(TipoLicenciaEnum.GRATIS))
        {
            parametros.put("pl_adicional", ParametrosSistemaCodefac.MensajesSistemaCodefac.MENSAJE_PIE_PAGINA_GRATIS);
        }
        else
        {
            parametros.put("pl_adicional", sessionCodefac.getEmpresa().getAdicional());
        }        
        
        
        try {    
            RecursosServiceIf service= ServiceFactory.getFactory().getRecursosServiceIf();
            String nombreImagen=sessionCodefac.getEmpresa().getImagenLogoPath();
            //service.getResourceInputStream(RecursoCodefac.AYUDA, file);
            
           if(sessionCodefac.getTipoLicenciaEnum().equals(TipoLicenciaEnum.GRATIS))
            {
                inputStream=RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream("sin_imagen.jpg");
            }
            else
            {
                RemoteInputStream remoteInputStream = service.getResourceInputStreamByFile(sessionCodefac.getEmpresa(),DirectorioCodefac.IMAGENES, nombreImagen);
                //verifica que existe una imagen
                if (remoteInputStream != null) {
                    inputStream = RemoteInputStreamClient.wrap(remoteInputStream);
                } 
                else //Si no existe 
                {
                    inputStream = RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream("sin_imagen.jpg");
                }            
            }

            parametros.put("pl_url_img1",UtilidadImagen.castInputStreamToImage(inputStream));
            
            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.IMAGENES_REDES_SOCIALES, "facebook.png"));
            parametros.put("pl_img_facebook",UtilidadImagen.castInputStreamToImage(inputStream));
            
            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.IMAGENES_REDES_SOCIALES, "whatsapp.png"));
            parametros.put("pl_img_whatsapp",UtilidadImagen.castInputStreamToImage(inputStream));
            
            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.IMAGENES_REDES_SOCIALES, "telefono.png"));
            parametros.put("pl_img_telefono",UtilidadImagen.castInputStreamToImage(inputStream));
            
            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.IMAGENES_GENERAL, "codefac-logotipo.png"));
            //parametros.put("pl_img_logo_pie",UtilidadImagen.castInputStreamToImage(inputStream));
            parametros.put("pl_img_logo_pie",null);
            
            String nombreReporteEncabezado="";
            String nombreReportePiePagina="";
            
            switch(formatoReporte)
            {
                case TICKET:
                        nombreReporteEncabezado = "encabezadoTicket.jrxml";
                        nombreReportePiePagina = "pie_paginaTicket.jrxml";
                        break;
                
                case A5:
                    switch (orientacionEnum) {
                        case HORIZONTAL:
                            break;

                        case VERTICAL:
                            nombreReporteEncabezado = "encabezadoA5.jrxml";
                            nombreReportePiePagina = "pie_paginaA5.jrxml";
                            break;
                    }
                    break;
                    
                case A4:
                    switch (orientacionEnum) {
                        case HORIZONTAL:
                            nombreReporteEncabezado = "encabezado_horizontal.jrxml";
                            nombreReportePiePagina = "pie_pagina_horizontal.jrxml";
                            break;

                        case VERTICAL:
                            nombreReporteEncabezado = "encabezado.jrxml";
                            nombreReportePiePagina = "pie_pagina.jrxml";
                            break;
                    }
                    break;
                
            }
            
            inputStream = RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER,nombreReporteEncabezado));
            JasperReport reportCabecera = JasperCompileManager.compileReport(inputStream);
            
            parametros.put("pl_url_cabecera",reportCabecera);
            
            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER,nombreReportePiePagina));
            JasperReport reportPiePagina = JasperCompileManager.compileReport(inputStream);
            
            parametros.put("pl_url_piepagina",reportPiePagina);
            //System.out.println(parametros.get("SUBREPORT_DIR"));            
        } catch (RemoteException ex) {
            Logger.getLogger(ReporteCodefac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(ReporteCodefac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReporteCodefac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parametros;
    }
}
