/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FacturaComprobante;
import java.io.File;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadesComprobantes {

    public static Map<String,String> decodificarArchivoBase64Offline(String nombreArchivo, String numeroAutorizacion, String fechaAutorizacion) {
        try {
            File archivo = new File(nombreArchivo);
            if (archivo.exists()) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document documento = builder.parse(archivo);
                NodeList listaNodos = documento.getElementsByTagName("*");
                String comprobante = null;
                String numeroAutorizacionComprobante = "";
                String fechaAutorizacionComprobante = "";
                String estadoComprobante = "";
                for (int i = 0; i < listaNodos.getLength(); i++) {
                    Element elemento = (Element) listaNodos.item(i);
                    if (elemento.getNodeName().equals("comprobante")) {
                        comprobante = elemento.getChildNodes().item(0).getNodeValue();
                    }
                    if (elemento.getNodeName().equals("estado")) {
                        estadoComprobante = elemento.getChildNodes().item(0).getNodeValue();
                    }
                    if ((numeroAutorizacion == null)
                            && (fechaAutorizacion == null)) {
                        if (elemento.getNodeName().equals("numeroAutorizacion")) {
                            numeroAutorizacionComprobante = elemento.getChildNodes().item(0).getNodeValue();
                        }
                        if (elemento.getNodeName().equals("fechaAutorizacion")) {
                            fechaAutorizacionComprobante = fechaAutorizacionComprobante + elemento.getChildNodes().item(0).getNodeValue();
                        }
                    }
                }
                if ((fechaAutorizacion == null) && (numeroAutorizacion == null)) {
                    fechaAutorizacion = fechaAutorizacionComprobante;
                    numeroAutorizacion = numeroAutorizacionComprobante;
                }
                if ("AUTORIZADO".equalsIgnoreCase(estadoComprobante)) {
                    Map<String,String> map=new HashMap<String,String>();
                    map.put("comprobante", comprobante);
                    map.put("numeroAutorizacion", numeroAutorizacion);
                    map.put("fechaAutorizacion", fechaAutorizacion);
                    //generarReporteComprobante(comprobante, numeroAutorizacion, fechaAutorizacion);
                    return map;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 
    /*
    private static void generarReporteComprobante(String documento, String numeroAutorizacion, String fechaAutorizacion) {
        if (documento != null) {
            //String rutaReporte = ServerConfigurationManager.getAppSetting("RUTA_REPORTES");
            File comprobanteElectronicoXML = Utilitarios.crearArchivoDesdeCadena(rutaReporte + numeroAutorizacion + ".xml", documento);
            //ComprobanteElectronico comprobante = null;

            try {
                //String tipoComprobante = obtenerTipoComprobante(documento);
                JAXBContext jaxbContext = JAXBContext.newInstance(FacturaComprobante.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                FacturaComprobante facturaComprobante = (FacturaComprobante) jaxbUnmarshaller.unmarshal(file);

                generarReporte(comprobante, reporteComprobante, numeroAutorizacion, fechaAutorizacion, tipoIva);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
*/

    public static void generarReporteJasper(String pathReporte, Map parametros, Collection datos, String pathGrabar) {
        try {
            JasperReport report = JasperCompileManager.compileReport(pathReporte);
            JRBeanCollectionDataSource dataReport = new JRBeanCollectionDataSource(datos);
            JasperPrint print = JasperFillManager.fillReport(report, parametros, dataReport);
            JasperExportManager.exportReportToPdfFile(print, pathGrabar);
            //JasperViewer.viewReport(print,false);
        } catch (JRException ex) {
            Logger.getLogger(UtilidadesComprobantes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

}
