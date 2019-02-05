/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.panel.UtilidadEnvioReportesPanel;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ControladorReporteFactura;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ControladorReporteGuiaRemision;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ControladorReporteRetencion;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.DocumentosConsultarEnum;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.CorreoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoReporteEnum;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class UtilidadEnvioReportesModel  extends UtilidadEnvioReportesPanel{

    private Empleado empleadoSeleccionado;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        cargarValoresIniciales();
        listenerBotones();
        validacionDatosIngresados=false;
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void cargarValoresIniciales() {
        
        //Cargar por defecto el primer dia del mes actual
        Date primerDiaMes=UtilidadesFecha.fechaInicioMes(new Date());
        getCmbFechaInicial().setDate(primerDiaMes);
        
        getCmbFechaFinal().setDate(UtilidadesFecha.getFechaHoy());
        
        for (ComprobanteEntity.ComprobanteEnumEstado objeto : ComprobanteEntity.ComprobanteEnumEstado.values()) {
            getCmbTipoEstadoReporte().addItem(objeto);
        } 
        
        //for (FormatoReporteEnum valor : FormatoReporteEnum.values()) {
        //    getCmbFormatoReporte().addItem(valor);
        //}
    }

    private void listenerBotones() {
        getBtnBuscarEmpleado().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                EmpleadoBusquedaDialogo busquedaDialog = new EmpleadoBusquedaDialogo();
                //busquedaDialog.setTipoEnum(Departamento.TipoEnum.Ventas);
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busquedaDialog);
                buscarDialogoModel.setVisible(true);
                Empleado empleadoTmp = (Empleado) buscarDialogoModel.getResultado();
                if (empleadoTmp != null) {
                    empleadoSeleccionado=empleadoTmp;
                    getTxtEmpleadoDatos().setText(empleadoTmp.toString());
                }
            }
            
        });
        
        
        getBtnEnviarCorreo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, String> archivosAdjuntos=new HashMap<String, String>();
                //FormatoReporteEnum formatoReporteEnum=(FormatoReporteEnum) getCmbFormatoReporte().getSelectedItem();
                Boolean formatoPdf=getChkPdf().isSelected();
                Boolean formatoExcel=getChkExcel().isSelected();
                                                
                if(!validacionCampos())
                {
                    return;
                }
                panelPadre.cambiarCursorEspera();
                
                if(getChkVentas().isSelected())
                {
                    if(formatoPdf)
                        generarReporteFacturasYNotaCredito(DocumentosConsultarEnum.VENTAS,FormatoReporteEnum.PDF, archivosAdjuntos);
                    
                    if(formatoExcel)
                        generarReporteFacturasYNotaCredito(DocumentosConsultarEnum.VENTAS,FormatoReporteEnum.EXCEL, archivosAdjuntos);
                }
                
                if(getChkNotaCredito().isSelected())
                {
                    if(formatoPdf)
                        generarReporteFacturasYNotaCredito(DocumentosConsultarEnum.NOTA_CREDITO,FormatoReporteEnum.PDF, archivosAdjuntos);
                    
                    if(formatoExcel)
                        generarReporteFacturasYNotaCredito(DocumentosConsultarEnum.NOTA_CREDITO,FormatoReporteEnum.EXCEL, archivosAdjuntos);
                }
                
                if(getChkRetencion().isSelected())
                {
                    if(formatoPdf)
                        generarReporteRetenciones(FormatoReporteEnum.PDF,archivosAdjuntos);
                    
                    if(formatoExcel)
                        generarReporteRetenciones(FormatoReporteEnum.EXCEL,archivosAdjuntos);
                }
                
                if (getChkGuiaRemision().isSelected()) {
                    
                    if(formatoPdf)
                        generarReporteGuiaRemision(FormatoReporteEnum.PDF, archivosAdjuntos);
                    
                    if(formatoExcel)
                        generarReporteGuiaRemision(FormatoReporteEnum.EXCEL, archivosAdjuntos);
                    
                }

                
                
                /**
                 * Metodo final para enviar los correos
                 */
               
                CorreoCodefac correoCodefac = new CorreoCodefac() {
                    @Override
                    public String getMensaje() {
                        return "Los archivos de los reportes estan adjuntos en el mensaje.";
                    }

                    @Override
                    public String getTitulo() {
                       return "Reportes Generados Codefac";
                    }

                    @Override
                    public Map<String, String> getPathFiles() {                        
                        return archivosAdjuntos;
                    }

                    @Override
                    public List<String> getDestinatorios() {
                        ArrayList<String> correos=new ArrayList<String>();
                        correos.add(empleadoSeleccionado.getCorreoElectronico());
                        return correos;
                    }
                };
                
                try {
                    correoCodefac.enviarCorreo();
                    panelPadre.cambiarCursorNormal();
                    DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.PROCESO_CORRECTO);
                } catch (CorreoCodefac.ExcepcionCorreoCodefac ex) {
                    Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
    }
    
    private void generarReporteGuiaRemision(FormatoReporteEnum formatoReporteEnum,Map<String, String> archivosAdjuntos)
    {
        ControladorReporteGuiaRemision controladorReporte=new ControladorReporteGuiaRemision(
                new java.sql.Date(getCmbFechaInicial().getDate().getTime()), 
                new java.sql.Date(getCmbFechaFinal().getDate().getTime()), 
                (ComprobanteEntity.ComprobanteEnumEstado) getCmbTipoEstadoReporte().getSelectedItem());
        controladorReporte.generarReporte();
        
        File archivoReporte = null;
        if (formatoReporteEnum.EXCEL.equals(formatoReporteEnum)) {
            
            try {
                archivoReporte = controladorReporte.obtenerReporteArchivoExcel();
            } catch (IOException ex) {
                Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else if (formatoReporteEnum.PDF.equals(formatoReporteEnum)) {
            try {
                archivoReporte = controladorReporte.obtenerReporteArchivoPdf(panelPadre);
            } catch (IOException ex) {
                Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        archivosAdjuntos.put("reporteGuiaRemision."+ formatoReporteEnum.getExtension(), archivoReporte.getPath());
        
        
    }
    
    private void generarReporteRetenciones(FormatoReporteEnum formatoReporteEnum,Map<String, String> archivosAdjuntos)
    {
        ControladorReporteRetencion controladorReporte = new ControladorReporteRetencion(
                null,
                new java.sql.Date(getCmbFechaInicial().getDate().getTime()),
                new java.sql.Date(getCmbFechaFinal().getDate().getTime()),
                null,
                null,
                null,
                (ComprobanteEntity.ComprobanteEnumEstado) getCmbTipoEstadoReporte().getSelectedItem());
        controladorReporte.generarReporte();
        
        File archivoReporte = null;
        if (formatoReporteEnum.EXCEL.equals(formatoReporteEnum)) {
            
            try {
                archivoReporte = controladorReporte.obtenerArchivoReporteExcel();
            } catch (IOException ex) {
                Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else if (formatoReporteEnum.PDF.equals(formatoReporteEnum)) {
            archivoReporte = controladorReporte.obtenerArchivoReportePdf(panelPadre);
        }
        
        archivosAdjuntos.put("reporteRetencion."+ formatoReporteEnum.getExtension(), archivoReporte.getPath());
        
    }
    
    private void generarReporteFacturasYNotaCredito(DocumentosConsultarEnum documentoEnum,FormatoReporteEnum formatoReporteEnum,Map<String, String> archivosAdjuntos)
    {
        //DocumentosConsultarEnum documentoEnum = null;
        String tituloReporte = "";
                    
        if (documentoEnum.equals(DocumentosConsultarEnum.VENTAS)) {
            //documentoEnum = DocumentosConsultarEnum.VENTAS;
            tituloReporte = "FacturasReporte";
        } else if (documentoEnum.equals(DocumentosConsultarEnum.NOTA_CREDITO)) {
            //documentoEnum = DocumentosConsultarEnum.NOTA_CREDITO;
            tituloReporte = "NotaCreditoReporte";
        }

        ControladorReporteFactura controlador = new ControladorReporteFactura(
                null,
                new java.sql.Date(getCmbFechaInicial().getDate().getTime()),
                new java.sql.Date(getCmbFechaFinal().getDate().getTime()),
                (ComprobanteEntity.ComprobanteEnumEstado) getCmbTipoEstadoReporte().getSelectedItem(),
                false,
                null,
                false,
                true,
                documentoEnum);

        controlador.generarReporte();

        File archivoReporte = null;
        if (formatoReporteEnum.EXCEL.equals(formatoReporteEnum)) {
            archivoReporte = controlador.obtenerArchivoReporteExcel();
        } else if (formatoReporteEnum.PDF.equals(formatoReporteEnum)) {
            archivoReporte = controlador.obtenerArchivoReportePdf(panelPadre);
        }
        archivosAdjuntos.put(tituloReporte+"."+ formatoReporteEnum.getExtension(), archivoReporte.getPath());
    }
    
    private boolean validacionCampos() {
        if(empleadoSeleccionado==null)
        {
            DialogoCodefac.mensaje("Advertencia","Seleccione un empleado para enviar los reportes",DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }
        
        return true;
    }
    
}
