/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.views;

import java.util.Map;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public interface InterfazComunicacionPanel 
{
    public void crearReportePantalla(JasperPrint jasperPrint,String nombrePantalla);
    public void crearVentanaCodefac(GeneralPanelInterface panel,boolean maximizado);
    public Map<String,Object> mapReportePlantilla();
}
