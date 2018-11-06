/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProformaBusqueda;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfaceModelFind;
import ec.com.codesoft.codefaclite.facturacion.reportdata.ComprobanteVentaData;
import ec.com.codesoft.codefaclite.facturacion.reportdata.ProformaDetalleData;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
    }

    @Override
    public void cargarSecuencial() {
        try {
            //Obtener los secuenciales para las proformas
            Integer secuencial = ServiceFactory.getFactory().getFacturacionServiceIf().obtenerSecuencialProformas();
            getLblSecuencial().setText(secuencial.toString());
        } catch (RemoteException ex) {
            Logger.getLogger(ProformaModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public InterfaceModelFind getBusquedaInterface() {
        return new ProformaBusqueda();
    }

    @Override
    protected void setearValoresDefaultFactura() {
        super.setearValoresDefaultFactura(); //To change body of generated methods, choose Tools | Templates.
        factura.setSecuencial(Integer.parseInt(getLblSecuencial().getText()));
        //facrur
        
    }
    
    
    

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            validacionesGrabar(); //Metodo que realiza validaciones previas antes de grabar
            FacturacionServiceIf servicio = ServiceFactory.getFactory().getFacturacionServiceIf();
            setearValoresDefaultFactura();
            factura = servicio.grabarProforma(factura);
            DialogoCodefac.mensaje("Correcto","Proforma generada correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        
        } catch (RemoteException ex) {
            Logger.getLogger(ProformaModel.class.getName()).log(Level.SEVERE, null, ex);
        }        

    }

    @Override
    public void imprimir() 
    {
        if(!estadoFormulario.equals(ESTADO_EDITAR))
        {
            DialogoCodefac.mensaje(MensajeCodefacSistema.Impresiones.IMPRESION_SECCION_INCORRECTA);
            return;
        }
        
        List<ComprobanteVentaData> dataReporte = getDetalleDataReporte(factura);

        //map de los parametros faltantes
        Map<String, Object> mapParametros = getMapParametrosReporte(factura);

        InputStream path = RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS.getResourceInputStream("presupuesto.jrxml");

        ReporteCodefac.generarReporteInternalFramePlantilla(path, mapParametros, dataReporte, this.panelPadre, "Presupuesto", OrientacionReporteEnum.VERTICAL, FormatoHojaEnum.A4);

              
    }
    
    
}
