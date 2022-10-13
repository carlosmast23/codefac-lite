/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.utilidades;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.PrinterName;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;

/**
 *
 * @author CARLOS_CODESOFT
 */
public abstract class UtilidadesImpresora {
    
    public static void printReportToPrinter(JasperPrint jp,Integer cantidadImpresion,String nombreImpresora) {
        List<String> impresorasList=new ArrayList<String>();
        impresorasList.add(nombreImpresora);
        printReportToPrinter(jp, cantidadImpresion, impresorasList);
    }
    
    public static void printReportToPrinter(JasperPrint jp,Integer cantidadImpresion,List<String> nombreImpresoraList) 
    {
        try {
            // TODO Auto-generated method stub
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            // printRequestAttributeSet.add(MediaSizeName.ISO_A4); //setting page size
            printRequestAttributeSet.add(new Copies(1));
            
            PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
            //PrinterName printerName = new PrinterName("\\\\CODESOFT-OFICIN\\EPSON L3110 Series", null); //gets printer
            for (String nombreImpresora : nombreImpresoraList) 
            {
                PrinterName printerName = new PrinterName(nombreImpresora, null); //gets printer
                printServiceAttributeSet.add(printerName);
            }
            
            JRPrintServiceExporter exporter = new JRPrintServiceExporter();
            
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            
            for (int i = 0; i < cantidadImpresion; i++) 
            {
                exporter.exportReport();
            }
            
        }
        catch (JRException ex) 
        {
            Logger.getLogger(UtilidadesImpresora.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static List<String> obtenerNombreImpresorasDisponibles()
    {
        List<String> impresoraList=new ArrayList<String>();
        PrintService[] printServiceTmp=PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printService : printServiceTmp) 
        {
            //System.out.println(printService.getName());
            impresoraList.add(printService.getName());
        }
        return impresoraList;
    }
    
    
}
