/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobante.reporte;

import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.GuiaRemisionServiceIf;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ControladorReporteGuiaRemision {
    
    private java.sql.Date fechaInicial;
    private java.sql.Date fechaFinal;
    private ComprobanteEntity.ComprobanteEnumEstado estado;
    private List<GuiaRemision> listaConsulta;
    private List<GuiaTransporteData> listReporte;

    public ControladorReporteGuiaRemision(Date fechaInicial, Date fechaFinal, ComprobanteEntity.ComprobanteEnumEstado estado) {
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.estado = estado;
    }
   
    
    public void generarReporte()
    {
        try {
            GuiaRemisionServiceIf guiaRemisionServiceIf=ServiceFactory.getFactory().getGuiaRemisionServiceIf();
            listaConsulta=guiaRemisionServiceIf.obtenerConsulta(fechaInicial,fechaFinal,estado);
            llenarDatosReporte();
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ControladorReporteGuiaRemision.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorReporteGuiaRemision.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public List<GuiaRemision> getListaConsulta() {
        return listaConsulta;
    }
    
    public void obtenerReporteExcel() throws IOException, FileNotFoundException, IllegalArgumentException, IllegalAccessException
    {
        Excel excel = new Excel();
        String nombreCabeceras[] = getCabeceraReporteExcel();
        excel.gestionarIngresoInformacionExcel(nombreCabeceras, listReporte);
        excel.abrirDocumento();
    }
    
    public File obtenerReporteArchivoExcel() throws IOException, FileNotFoundException, IllegalArgumentException, IllegalAccessException {
        Excel excel = new Excel();
        String nombreCabeceras[] = getCabeceraReporteExcel();
        excel.gestionarIngresoInformacionExcel(nombreCabeceras, listReporte);
        return excel.obtenerArchivo();
    }
    
    public void obtenerReportePdf(InterfazComunicacionPanel panelPadre) throws IOException, FileNotFoundException, IllegalArgumentException, IllegalAccessException {
        InputStream path =obtenerPathReporte();
        Map parameters = new HashMap();
        ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, listReporte, panelPadre, "Reporte Guía Remisión ", OrientacionReporteEnum.HORIZONTAL);
    }
    
    public File obtenerReporteArchivoPdf(InterfazComunicacionPanel panelPadre) throws IOException, FileNotFoundException, IllegalArgumentException, IllegalAccessException {
        //InputStream path = obtenerPathReporte();
        //Map parameters = new HashMap();
        //ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, listReporte, panelPadre, "Reporte Guía Remisión ", OrientacionReporteEnum.HORIZONTAL);
        
        String nombreArchivo=UtilidadesArchivos.generarNombreArchivoUnico("reporte","pdf");
        String pathGrabar="\\tmp\\"+nombreArchivo; //TODO: Camabiar por algun nombre en funcion de la fecha para que se unico y no genere problemas
        
        ReporteCodefac.generarReporteInternalFramePlantillaArchivo(obtenerPathReporte(),new HashMap(),listReporte, panelPadre, "Reporte Guía Remisión", OrientacionReporteEnum.HORIZONTAL,FormatoHojaEnum.A4,pathGrabar);
        File file=new File(pathGrabar);
        if(file.exists())
        {
            return file;
        }
        return null;
    }
    
    public String[] getCabeceraReporteExcel()
    {
        return new String[]{"Clave de Acceso","Preimpreso","Transportista","Identificación","Estado","FechaInicio","FechaFin","Dir Partida","Placa"};
    }
    
    public InputStream obtenerPathReporte()
    {
        return RecursoCodefac.JASPER_TRANSPORTE.getResourceInputStream("reporte_guiaRemision.jrxml");
    }

    private void llenarDatosReporte() {
        listReporte=new ArrayList<GuiaTransporteData>();
        for (GuiaRemision guiaRemision : listaConsulta) {
            GuiaTransporteData data=new GuiaTransporteData();
            data.setDireccionPartida(guiaRemision.getDireccionPartida());
            data.setEstado(guiaRemision.getEstadoEnum().getNombre());
            data.setFechaFin(guiaRemision.getFechaFinTransporte().toString());
            data.setFechaInicio(guiaRemision.getFechaIniciaTransporte().toString());
            data.setIdentififacion((guiaRemision.getIdentificacion()!=null)?guiaRemision.getIdentificacion().toString():"");
            data.setPlaca(guiaRemision.getPlaca());
            data.setPreimpreso(guiaRemision.getPreimpreso());
            data.setTransportista(guiaRemision.getRazonSocial());
            data.setClaveAcceso(guiaRemision.getClaveAcceso());
            listReporte.add(data);
        }
    }

    public List<GuiaTransporteData> getListReporte() {
        return listReporte;
    }
    
    
}
