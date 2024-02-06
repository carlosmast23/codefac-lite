/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProformaBusqueda;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.dialog.ProcesoSegundoPlano;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ComprobanteVentaData;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaModelControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturacionPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ConfiguracionImpresoraEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import static ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj.ModoMensajeEnum.MENSAJE_INCORRECTO;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public class ProformaModel extends FacturacionModel{

    public ProformaModel() {
        super();
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        super.iniciar(); //To change body of generated methods, choose Tools | Templates.
        valoresIniciales();
        getBtnCargarProforma().setEnabled(false);
        getCmbPuntoEmision().setVisible(false);
        getLblEstablecimiento().setVisible(false);
        getChkReserva().setVisible(true);
        getChkImprimirSinCodigo().setVisible(true);
        
        //Deshabilitiar componentes de comprobantes electronicos
        super.buscarPanelCategoriaLateral(FacturacionPanel.NOMBRE_PANEL_LATERAL_COMP_ELECTRONICOS).setVisible(false);
        super.buscarPanelCategoriaLateral(FacturacionPanel.NOMBRE_PANEL_LATERAL_REENVIO_CORREO_PROFORMA).setVisible(true);
    }
    
    

    private void valoresIniciales() {
        //nombre de la pantalla
        setTitle("Proforma");

        //cargar el documento de proforma
        getCmbDocumento().removeAllItems();
        getCmbDocumento().addItem(DocumentoEnum.PROFORMA);

        //desactivar el panel formas de pago porque no utilizo
        getPanelFormasPago().setVisible(false);    
        getPnlVuelto().setVisible(false);
        getChkEnviarCorreo().setVisible(true);
        getChkEnviarCorreo().setSelected(true);
        
    }

    @Override
    public void cargarSecuencial() {
        try {
            //Obtener los secuenciales para las proformas
            Long secuencial = ServiceFactory.getFactory().getFacturacionServiceIf().obtenerSecuencialProformas(session.getEmpresa(),DocumentoEnum.PROFORMA);
            getLblSecuencial().setText(secuencial.toString());
        } catch (RemoteException ex) {
            Logger.getLogger(ProformaModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public InterfaceModelFind getBusquedaInterface() {
        return new ProformaBusqueda(session.getEmpresa(),true);
    }

    @Override
    protected void setearValoresDefaultFactura(CrudEnum crudEnum) {
        //TODO: Por el momento dejo en esta parte aunque debe estar unificado con la factura
        super.setearValoresDefaultFactura(crudEnum); //To change body of generated methods, choose Tools | Templates.
        //factura.setSecuencial(Integer.parseInt(getLblSecuencial().getText())); //TODO: Revisar que este de tema de setar el secuencial ya lo estoy haciendo desde el servicio
        
    }
    
    public void listenerBtnEnviarCorreoProforma() throws ExcepcionCodefacLite
    {
        if(super.estadoFormulario.equals(ESTADO_GRABAR))
        {
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.ACCION_PERMITIDA_MODULO_EDITAR);
            return;
        }
        
        DialogoCodefac.mostrarDialogoCargando(new ProcesoSegundoPlano() {
            @Override
            public void procesar() {
                try {
                    ServiceFactory.getFactory().getFacturacionServiceIf().enviarCorreoProforma(factura,getChkImprimirSinCodigo().isSelected());
                    DialogoCodefac.mensaje(MensajeCodefacSistema.CorreoElectronico.CORREO_ENVIADO);
                } catch (RemoteException ex) {
                    Logger.getLogger(ProformaModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(ProformaModel.class.getName()).log(Level.SEVERE, null, ex);
                    DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                }
            }

            @Override
            public String getMensaje() {
                return "enviando correo ...";
            }
        });
        
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        
        DialogoCodefac.mostrarDialogoCargando(new ProcesoSegundoPlano() {
            @Override
            public void procesar() throws ExcepcionCodefacLite {
                try {
                    validacionesGrabar(); //Metodo que realiza validaciones previas antes de grabar
                    FacturacionServiceIf servicio = ServiceFactory.getFactory().getFacturacionServiceIf();
                    setearValoresDefaultFactura(CrudEnum.CREAR);
                    //factura.setEstado(GeneralEnumEstado.ACTIVO.getEstado());

                    factura = servicio.grabarProforma(factura,getChkEnviarCorreo().isSelected(),getChkImprimirSinCodigo().isSelected());
                    //DialogoCodefac.mensaje("Correcto", "Proforma generada correctamente", MENSAJE_CORRECTO);
                    imprimirProforma();

                } catch (RemoteException ex) {
                    Logger.getLogger(ProformaModel.class.getName()).log(Level.SEVERE, null, ex);                                        
                    throw new ExcepcionCodefacLite("Error al grabar: "+ex.getMessage());

                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(ProformaModel.class.getName()).log(Level.SEVERE, null, ex);
                    DialogoCodefac.mensaje("Error", ex.getMessage(), MENSAJE_INCORRECTO);
                    throw new ExcepcionCodefacLite("Error al grabar: "+ex.getMessage());
                } catch (ExcepcionCodefacLite ex) {
                    Logger.getLogger(ProformaModel.class.getName()).log(Level.SEVERE, null, ex);
                    throw new ExcepcionCodefacLite("Error al grabar: "+ex.getMessage());
                }
            }

            @Override
            public String getMensaje() {
                return "Procesando proforma";
            }
        });

    }

    @Override
    public void imprimir() 
    {
        try {
            if(!estadoFormulario.equals(ESTADO_EDITAR))
            {
                DialogoCodefac.mensaje(MensajeCodefacSistema.Impresiones.IMPRESION_SECCION_INCORRECTA);
                return;
            }
            //Obtener el valor original de proforma, ya se que modifico y no guardo los datos y desea imprimir.
            FacturacionServiceIf servicio = ServiceFactory.getFactory().getFacturacionServiceIf();
            Factura facturaTemp = servicio.buscarPorId(factura.getId());
            if(facturaTemp != null){
                this.factura = facturaTemp;
                imprimirProforma();
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ProformaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
                      
    }
    
    public void imprimirProforma()
    {
        //Por defecto reporte
        FacturaModelControlador.FormatoReporteEnum formatoReporte=FacturaModelControlador.FormatoReporteEnum.A4;
        
        String formatoImpresionTexto=ParametroUtilidades.obtenerValorParametro(session.getEmpresa(),ParametroCodefac.REPORTE_DEFECTO_PEDIDO);
        if(formatoImpresionTexto!=null)
        {
            FacturaModelControlador.FormatoReporteEnum formatoReporteTmp=FacturaModelControlador.FormatoReporteEnum.findByName(formatoImpresionTexto);
            if(formatoReporteTmp!=null)
            {
                formatoReporte=formatoReporteTmp;
            }
        }
        
        /*List<ComprobanteVentaData> dataReporte = getDetalleDataReporte(factura);

        //map de los parametros faltantes
        Map<String, Object> mapParametros = getMapParametrosReporte(factura);
        
        ReporteCodefac.generarReporteInternalFramePlantilla(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS,"proforma.jrxml",mapParametros, dataReporte, this.panelPadre, "Proforma", OrientacionReporteEnum.VERTICAL, FormatoHojaEnum.A4);*/
        JasperPrint jasperReporte=FacturaModelControlador.getReporteJasperProforma(factura,formatoReporte,getChkImprimirSinCodigo().isSelected());
        
        
        
        ReporteCodefac.generarReporteInternalFrame(jasperReporte, panelPadre, "Proforma "+factura.getSecuencial(), ConfiguracionImpresoraEnum.NINGUNA);
        //ReporteCodefac.generarReporteInternalFramePlantilla(pathReporte, parametros, bindingComponentList, panelPadre, TITLE_PROPERTY, OrientacionReporteEnum.HORIZONTAL);

    }
    

    //@Override
    public List<ComprobanteVentaData> getDetalleDataReporte(Factura facturaProcesando) {
        return FacturaModelControlador.getDetalleDataReporte(facturaProcesando,getChkImprimirSinCodigo().isSelected()); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    //@Override
    /*public Map<String, Object> getMapParametrosReporte(Factura facturaProcesando) {
        Map<String, Object> mapParametros= FacturaModelControlador.getMapParametrosReporte(facturaProcesando); //To change body of generated methods, choose Tools | Templates.
        mapParametros.put("estado",factura.getEnumEstadoProforma().getNombre());        
        //subtotal_cero
        //Datos adicionales para las proformas
        mapParametros.put("secuencial", facturaProcesando.getSecuencial().toString());
        mapParametros.put("cliente_nombres", facturaProcesando.getRazonSocial());
        mapParametros.put("cliente_identificacion", facturaProcesando.getIdentificacion());
        mapParametros.put("fecha_emision", facturaProcesando.getFechaEmision().toString());
        mapParametros.put("subtotal_cero",facturaProcesando.getSubtotalSinImpuestos().toString());
        mapParametros.put("descuento",facturaProcesando.getDescuentoImpuestos().add(facturaProcesando.getDescuentoSinImpuestos()).toString());
        mapParametros.put("iva_porcentaje",session.obtenerIvaActual().toString());
        mapParametros.put("informacionAdicionalList",obtenerDatosAdicionales());

        try {
            RecursosServiceIf service= ServiceFactory.getFactory().getRecursosServiceIf();
            InputStream inputStream = RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS,"datos_adicionalesA4.jrxml"));
            JasperReport reportDatosAdicionales = JasperCompileManager.compileReport(inputStream);
            mapParametros.put("SUBREPORT_INFO_OTRO",reportDatosAdicionales);
            
            inputStream = RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS,"datos_adicionales.jrxml"));
            reportDatosAdicionales = JasperCompileManager.compileReport(inputStream);
            mapParametros.put("SUBREPORT_INFO_ADICIONAL",reportDatosAdicionales);
        } catch (RemoteException ex) {
            Logger.getLogger(ProformaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProformaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(ProformaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //SUBREPORT_INFO_ADICIONAL
        
        // mapParametros.put("estado",facturaProcesando.getEstadoEnum());
        return mapParametros;
    }*/
    
    /*private List<InformacionAdicionalData> obtenerDatosAdicionales()
    {
        List<InformacionAdicionalData> datosAdicionalesData=new ArrayList<InformacionAdicionalData>();
        if(factura.getDatosAdicionales()!=null)
        {          
            for (FacturaAdicional datoAdicional : factura.getDatosAdicionales()) 
            {
                InformacionAdicionalData data=new InformacionAdicionalData();
                data.setNombre(datoAdicional.getCampo());
                data.setValor(datoAdicional.getValor());
                datosAdicionalesData.add(data);
            }
        }
        return datosAdicionalesData;
    }*/

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        try {
            
            if(!estadoFormulario.equals(ESTADO_EDITAR))
            {
                DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.NO_PERMITE_ELIMINAR);
                throw new ExcepcionCodefacLite("Cancelar eliminar");
            }
            
            Boolean confirmar=DialogoCodefac.dialogoPregunta(MensajeCodefacSistema.Preguntas.ELIMINAR_REGISTRO);
            if(!confirmar)
            {
                throw new ExcepcionCodefacLite("Cancelar eliminar");
            }
            
            ServiceFactory.getFactory().getFacturacionServiceIf().eliminarProforma(factura);
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.ELIMINADO_CORRECTAMENTE);
            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ProformaModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error",ex.getMessage(),MENSAJE_INCORRECTO);
            new ExcepcionCodefacLite("Error Eliminar");
        }
        MesEnum.obtenerPorNumero(ERROR).getNombre();
        
    }
    
    
    
    
    /*
    @Override
    public Map<String, Object> getParametroReporte(DocumentoEnum documento) {
        Map<String, Object> mapParametros= super.getParametroReporte(documento); //To change body of generated methods, choose Tools | Templates.
        mapParametros.put("estado",factura.getEnumEstadoProforma().getNombre());
        
        return mapParametros;
    }
    */

    @Override
    public void cargarSecuencialConsulta() {
        getLblSecuencial().setText(factura.getSecuencial().toString());
    }

    @Override
    public void eventoCambiarEstado() {
        getBtnCargarProforma().setEnabled(false);
    }

    @Override
    public void copiar() throws ExcepcionCodefacLite, RemoteException {
        InterfaceModelFind interfaceModelFind= getBusquedaInterface();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(interfaceModelFind);
        buscarDialogoModel.setVisible(true);
        Factura facturaTmp = (Factura) buscarDialogoModel.getResultado();
        
        if(facturaTmp!=null)
        {
            this.factura = facturaTmp;
            cargarFacturaDesdeProforma(factura, DocumentoEnum.PROFORMA,true);
        }
    }
   
    @Override
    public void postConstructorExterno(Object[] parametros) {
        Factura facturaTmp=(Factura) parametros[0];
        facturaTmp.setCodigoDocumentoEnum(DocumentoEnum.PROFORMA);
        //Cuando cargue una factura a proforma le lleno con los datos y solo saco una copia
        //this.factura=facturaTmp;
        //this.cargarTotalesVista();
        cargarDatosPantallaFactura(facturaTmp);
        
    }
    
}
