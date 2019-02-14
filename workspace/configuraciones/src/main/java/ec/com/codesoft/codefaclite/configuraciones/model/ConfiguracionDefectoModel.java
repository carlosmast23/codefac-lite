/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.panel.ConfiguracionDefectoPanel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ParametroCodefacServiceIf;
import java.rmi.RemoteException;
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
public class ConfiguracionDefectoModel extends ConfiguracionDefectoPanel{
    
    private Map<String, ParametroCodefac> parametrosTodos;
    private List<ParametroCodefac> parametrosEditar;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        iniciarVariables();
        cargarDatos();
        /**
         * Desactivo el ciclo de vida para controlar manualmente
         */
        super.cicloVida = false;
        super.validacionDatosIngresados=false;
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            setearVariable();
            ParametroCodefacServiceIf service=ServiceFactory.getFactory().getParametroCodefacServiceIf();
            service.editarParametros(parametrosEditar);
            DialogoCodefac.mensaje("Actualizado datos", "Los parametros fueron actualizados correctamente", DialogoCodefac.MENSAJE_CORRECTO);
            //Cargar nuevamente lo datos de la base para tener persistente la informacion
            cargarDatos();
            
        } catch (RemoteException ex) {
            Logger.getLogger(ConfiguracionDefectoModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error","No se pueden grabar los parametros",DialogoCodefac.MENSAJE_INCORRECTO);
        }
        
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
    public String getNombre() {
        return "Configuraciones por Defecto";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void iniciarVariables() {
                
        //Agregar los tipos de documentos disponibles
        getCmbTipoDocumento().removeAllItems();
        List<TipoDocumentoEnum> tipoDocumentos= TipoDocumentoEnum.obtenerTipoDocumentoPorModulo(ModuloCodefacEnum.FACTURACION);
        for (TipoDocumentoEnum tipoDocumento : tipoDocumentos) {
            getCmbTipoDocumento().addItem(tipoDocumento);
        }
        
        //Agregar los tipos de documentos disponibles para las compras
        getCmbTipoDocumentoCompra().removeAllItems();
        List<TipoDocumentoEnum> tipoDocumentosCompra= TipoDocumentoEnum.obtenerTipoDocumentoPorModulo(ModuloCodefacEnum.COMPRA);
        for (TipoDocumentoEnum tipoDocumento : tipoDocumentosCompra) {
            getCmbTipoDocumentoCompra().addItem(tipoDocumento);
        }
        
        //Agregar los datos del combo de tipo formato de hoja de las ordenes de trabajo
        FormatoHojaEnum[] formatos = FormatoHojaEnum.values();
        for (FormatoHojaEnum formato : formatos) {
            getCmbFormatoHojas().addItem(formato);
        }
        
        //Agregar las opcion para esocger si o no en activar modulo cartera
        getCmbActivarModuloCartera().removeAllItems();
        getCmbActivarModuloCartera().addItem(EnumSiNo.NO);
        getCmbActivarModuloCartera().addItem(EnumSiNo.SI);
        
        //Agregar las opcion para esocger si o no en activar los comprobantes de venta
        getCmbActivarComprobanteVenta().removeAllItems();
        getCmbActivarComprobanteVenta().addItem(EnumSiNo.NO);
        getCmbActivarComprobanteVenta().addItem(EnumSiNo.SI);
        
        //Agregar las opcion para esocger si o no en activar los comprobantes de venta
        getCmbActivarReporteSimpleGuiaRemision().removeAllItems();
        getCmbActivarReporteSimpleGuiaRemision().addItem(EnumSiNo.NO);
        
                //Agregar las opcion para esocger si o no en activar los comprobantes de venta
        getCmbCargarProductoIvaFactura().removeAllItems();
        getCmbCargarProductoIvaFactura().addItem(EnumSiNo.NO);
        getCmbCargarProductoIvaFactura().addItem(EnumSiNo.SI);
        
    }

    private void cargarDatos() {
        try {
            ParametroCodefacServiceIf service=ServiceFactory.getFactory().getParametroCodefacServiceIf();
            parametrosTodos = service.getParametrosMap();
            
            ParametroCodefac parametroTipoDocumento= parametrosTodos.get(ParametroCodefac.DEFECTO_TIPO_DOCUMENTO_FACTURA);
            TipoDocumentoEnum tipoDocumentoEnum=TipoDocumentoEnum.obtenerTipoDocumentoPorCodigo(parametroTipoDocumento.getValor());            
            getCmbTipoDocumento().setSelectedItem(tipoDocumentoEnum);
            
            //Cargar el documento de la compra
            ParametroCodefac parametroTipoDocumentoCompra= parametrosTodos.get(ParametroCodefac.DEFECTO_TIPO_DOCUMENTO_COMPRA);
            TipoDocumentoEnum tipoDocumentoCompraEnum=TipoDocumentoEnum.obtenerTipoDocumentoPorCodigo(parametroTipoDocumentoCompra.getValor());            
            getCmbTipoDocumentoCompra().setSelectedItem(tipoDocumentoCompraEnum);
            
            //Cargar el documento de la compra
            ParametroCodefac parametroOrdenCompraObservacion= parametrosTodos.get(ParametroCodefac.ORDEN_TRABAJO_OBSERVACIONES);
            getTxtOrdenTrabajoReporte().setText((parametroOrdenCompraObservacion!=null)?parametroOrdenCompraObservacion.getValor():"");
            
            //Cargar datos del tipo de reporte de las ordenes de trabajo
            ParametroCodefac parametroFormtaOrdenTrabajo= parametrosTodos.get(ParametroCodefac.FORMATO_ORDEN_TRABAJO);
            getCmbFormatoHojas().setSelectedItem((parametroFormtaOrdenTrabajo!=null)?parametroFormtaOrdenTrabajo.getValor():null);
            
            ParametroCodefac parametroActivarCarteras = parametrosTodos.get(ParametroCodefac.ACTIVAR_CARTERA);
            EnumSiNo enumSiNo=EnumSiNo.getEnumByLetra((parametroActivarCarteras!=null)?parametroActivarCarteras.getValor():null);
            getCmbActivarModuloCartera().setSelectedItem(enumSiNo);
            //getTxtOrdenTrabajoReporte().setText((parametroFormtaOrdenTrabajo!=null)?parametroFormtaOrdenTrabajo.getValor():"");
            
            ParametroCodefac parametroComprobanteVenta= parametrosTodos.get(ParametroCodefac.COMPROBANTE_VENTA_ACTIVAR);
            enumSiNo=EnumSiNo.getEnumByLetra((parametroComprobanteVenta!=null)?parametroComprobanteVenta.getValor():null);
            if (parametroComprobanteVenta != null) {
                getCmbActivarComprobanteVenta().setSelectedItem(enumSiNo);
            } else {
                getCmbActivarComprobanteVenta().setSelectedItem(EnumSiNo.NO);
            }
            
            
            ParametroCodefac parametroComprobanteGuiaRemision= parametrosTodos.get(ParametroCodefac.COMPROBANTE_GUIA_REMISION_ACTIVAR);
            enumSiNo=EnumSiNo.getEnumByLetra((parametroComprobanteGuiaRemision!=null)?parametroComprobanteGuiaRemision.getValor():null);
            if(parametroComprobanteGuiaRemision!=null)
                getCmbActivarReporteSimpleGuiaRemision().setSelectedItem(enumSiNo);
            else
                getCmbActivarReporteSimpleGuiaRemision().setSelectedItem(EnumSiNo.NO);
            
            
            ParametroCodefac parametroCargarProductoIvaFactura= parametrosTodos.get(ParametroCodefac.CARGAR_PRODUCTO_IVA_FACTURA);
            enumSiNo=EnumSiNo.getEnumByLetra((parametroCargarProductoIvaFactura!=null)?parametroCargarProductoIvaFactura.getValor():null);
            if(parametroCargarProductoIvaFactura!=null)
                getCmbCargarProductoIvaFactura().setSelectedItem(enumSiNo);
            else
                getCmbCargarProductoIvaFactura().setSelectedItem(EnumSiNo.NO);
            
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(ConfiguracionDefectoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setearVariable() {
        parametrosEditar=new ArrayList<ParametroCodefac>();        
                
        TipoDocumentoEnum tipoDocumento=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        parametrosTodos.get(ParametroCodefac.DEFECTO_TIPO_DOCUMENTO_FACTURA).setValor(tipoDocumento.getCodigo());
        parametrosEditar.add(parametrosTodos.get(ParametroCodefac.DEFECTO_TIPO_DOCUMENTO_FACTURA));
        
        TipoDocumentoEnum tipoDocumentoCompra=(TipoDocumentoEnum) getCmbTipoDocumentoCompra().getSelectedItem();
        parametrosTodos.get(ParametroCodefac.DEFECTO_TIPO_DOCUMENTO_COMPRA).setValor(tipoDocumentoCompra.getCodigo());
        parametrosEditar.add(parametrosTodos.get(ParametroCodefac.DEFECTO_TIPO_DOCUMENTO_COMPRA));
        
        //Agregar detalle para la orden de trabajo
        agregarParametro(ParametroCodefac.ORDEN_TRABAJO_OBSERVACIONES,getTxtOrdenTrabajoReporte().getText());
        parametrosEditar.add(parametrosTodos.get(ParametroCodefac.ORDEN_TRABAJO_OBSERVACIONES));
        
        
        EnumSiNo enumSiNo=(EnumSiNo) getCmbActivarModuloCartera().getSelectedItem();
        agregarParametro(ParametroCodefac.ACTIVAR_CARTERA,enumSiNo.getLetra());
        parametrosEditar.add(parametrosTodos.get(ParametroCodefac.ACTIVAR_CARTERA));
        
        
        enumSiNo = (EnumSiNo) getCmbActivarComprobanteVenta().getSelectedItem();
        agregarParametro(ParametroCodefac.COMPROBANTE_VENTA_ACTIVAR, (enumSiNo!=null)?enumSiNo.getLetra():null);
        parametrosEditar.add(parametrosTodos.get(ParametroCodefac.COMPROBANTE_VENTA_ACTIVAR));
        
        enumSiNo = (EnumSiNo) getCmbActivarReporteSimpleGuiaRemision().getSelectedItem();
        agregarParametro(ParametroCodefac.COMPROBANTE_GUIA_REMISION_ACTIVAR, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        parametrosEditar.add(parametrosTodos.get(ParametroCodefac.COMPROBANTE_GUIA_REMISION_ACTIVAR));
        
        enumSiNo = (EnumSiNo) getCmbCargarProductoIvaFactura().getSelectedItem();
        agregarParametro(ParametroCodefac.CARGAR_PRODUCTO_IVA_FACTURA, (enumSiNo != null) ? enumSiNo.getLetra() : null);
        parametrosEditar.add(parametrosTodos.get(ParametroCodefac.CARGAR_PRODUCTO_IVA_FACTURA));
        /*ParametroCodefac parametroCodefac=parametrosTodos.get(ParametroCodefac.ORDEN_TRABAJO_OBSERVACIONES);
        if(parametroCodefac==null)
        {
            parametroCodefac=new ParametroCodefac();
            parametroCodefac.setNombre(ParametroCodefac.ORDEN_TRABAJO_OBSERVACIONES);
            parametroCodefac.setValor(getTxtOrdenTrabajoReporte().getText());
            parametrosTodos.put(ParametroCodefac.ORDEN_TRABAJO_OBSERVACIONES, parametroCodefac);

        }
        else
        {
            parametroCodefac.setValor(getTxtOrdenTrabajoReporte().getText());
        }*/
        
        
        //Agregar tipo de hoja para el reporte de la orden de trabajo
        FormatoHojaEnum formatoHojaEnum=(FormatoHojaEnum) getCmbFormatoHojas().getSelectedItem();
        if(formatoHojaEnum==null)
        {
            formatoHojaEnum=FormatoHojaEnum.A4;
        }
        
        agregarParametro(ParametroCodefac.FORMATO_ORDEN_TRABAJO,formatoHojaEnum.getLetra());
        parametrosEditar.add(parametrosTodos.get(ParametroCodefac.FORMATO_ORDEN_TRABAJO));
        
    }
    
    private void agregarParametro(String nombreParametro,String valor)
    {
        ParametroCodefac parametroCodefac = parametrosTodos.get(nombreParametro);
        if (parametroCodefac == null) {
            parametroCodefac = new ParametroCodefac();
            parametroCodefac.setNombre(nombreParametro);
            parametroCodefac.setValor(valor);
            parametrosTodos.put(nombreParametro, parametroCodefac);

        } else {
            parametroCodefac.setValor(valor);
        }
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
