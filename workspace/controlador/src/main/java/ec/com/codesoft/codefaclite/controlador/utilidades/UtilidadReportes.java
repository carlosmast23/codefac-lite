/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.utilidades;

import java.util.List;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class UtilidadReportes {
    public static JasperPrint unificarReportes(List<JasperPrint> jasperList)
    {
        JasperPrint reporteUnido=null;
        for (JasperPrint jasperPrint : jasperList) {
            if (reporteUnido == null) 
            {
                reporteUnido = jasperPrint;
            } else 
            {
                List pages = jasperPrint.getPages();
                for (int j = 0; j < pages.size(); j++) {
                    JRPrintPage nuevasPaginas = (JRPrintPage) pages.get(j);
                    reporteUnido.addPage(nuevasPaginas);
                }
                //reporteUnido.addPage(page);
            }
        }
        return reporteUnido;
    }
    
    public static void visualizarReporteVentanaExterna(JasperPrint jasperPrint)
    {
        JasperViewer.viewReport(jasperPrint,false);
    }
}
