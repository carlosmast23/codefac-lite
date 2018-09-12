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
    
    private Map<String, ParametroCodefac> parametros;
    
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
            service.editarParametros(parametros);
            DialogoCodefac.mensaje("Actualizado datos", "Los parametros fueron actualizados correctamente", DialogoCodefac.MENSAJE_CORRECTO);
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
        
    }

    private void cargarDatos() {
        try {
            ParametroCodefacServiceIf service=ServiceFactory.getFactory().getParametroCodefacServiceIf();
            parametros = service.getParametrosMap();
            
            ParametroCodefac parametroTipoDocumento= parametros.get(ParametroCodefac.DEFECTO_TIPO_DOCUMENTO_FACTURA);
            TipoDocumentoEnum tipoDocumentoEnum=TipoDocumentoEnum.obtenerTipoDocumentoPorCodigo(parametroTipoDocumento.getValor());            
            getCmbTipoDocumento().setSelectedItem(tipoDocumentoEnum);
            
            //Cargar el documento de la compra
            ParametroCodefac parametroTipoDocumentoCompra= parametros.get(ParametroCodefac.DEFECTO_TIPO_DOCUMENTO_COMPRA);
            TipoDocumentoEnum tipoDocumentoCompraEnum=TipoDocumentoEnum.obtenerTipoDocumentoPorCodigo(parametroTipoDocumentoCompra.getValor());            
            getCmbTipoDocumentoCompra().setSelectedItem(tipoDocumentoCompraEnum);
            
            //Cargar el documento de la compra
            ParametroCodefac parametroOrdenCompraObservacion= parametros.get(ParametroCodefac.ORDEN_TRABAJO_OBSERVACIONES);
            getTxtOrdenTrabajoReporte().setText((parametroOrdenCompraObservacion!=null)?parametroOrdenCompraObservacion.getValor():"");
            
            //Cargar datos del tipo de reporte de las ordenes de trabajo
            ParametroCodefac parametroFormtaOrdenTrabajo= parametros.get(ParametroCodefac.FORMATO_ORDEN_TRABAJO);
            getCmbFormatoHojas().setSelectedItem((parametroFormtaOrdenTrabajo!=null)?parametroFormtaOrdenTrabajo.getValor():null);
            
            ParametroCodefac parametroActivarCarteras = parametros.get(ParametroCodefac.ACTIVAR_CARTERA);
            EnumSiNo enumSiNo=EnumSiNo.getEnumByLetra((parametroActivarCarteras!=null)?parametroActivarCarteras.getValor():null);
            getCmbActivarModuloCartera().setSelectedItem(enumSiNo);
            //getTxtOrdenTrabajoReporte().setText((parametroFormtaOrdenTrabajo!=null)?parametroFormtaOrdenTrabajo.getValor():"");
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(ConfiguracionDefectoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setearVariable() {
        TipoDocumentoEnum tipoDocumento=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();
        parametros.get(ParametroCodefac.DEFECTO_TIPO_DOCUMENTO_FACTURA).setValor(tipoDocumento.getCodigo());
        
        TipoDocumentoEnum tipoDocumentoCompra=(TipoDocumentoEnum) getCmbTipoDocumentoCompra().getSelectedItem();
        parametros.get(ParametroCodefac.DEFECTO_TIPO_DOCUMENTO_COMPRA).setValor(tipoDocumentoCompra.getCodigo());
        
        //Agregar detalle para la orden de trabajo
        agregarParametro(ParametroCodefac.ORDEN_TRABAJO_OBSERVACIONES,getTxtOrdenTrabajoReporte().getText());
        
        EnumSiNo enumSiNo=(EnumSiNo) getCmbActivarModuloCartera().getSelectedItem();
        agregarParametro(ParametroCodefac.ACTIVAR_CARTERA,enumSiNo.getLetra());
        /*ParametroCodefac parametroCodefac=parametros.get(ParametroCodefac.ORDEN_TRABAJO_OBSERVACIONES);
        if(parametroCodefac==null)
        {
            parametroCodefac=new ParametroCodefac();
            parametroCodefac.setNombre(ParametroCodefac.ORDEN_TRABAJO_OBSERVACIONES);
            parametroCodefac.setValor(getTxtOrdenTrabajoReporte().getText());
            parametros.put(ParametroCodefac.ORDEN_TRABAJO_OBSERVACIONES, parametroCodefac);

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
        
    }
    
    private void agregarParametro(String nombreParametro,String valor)
    {
        ParametroCodefac parametroCodefac = parametros.get(nombreParametro);
        if (parametroCodefac == null) {
            parametroCodefac = new ParametroCodefac();
            parametroCodefac.setNombre(nombreParametro);
            parametroCodefac.setValor(valor);
            parametros.put(nombreParametro, parametroCodefac);

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
