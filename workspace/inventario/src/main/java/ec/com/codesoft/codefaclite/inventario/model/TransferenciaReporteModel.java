/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ITableBindingAddData;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.inventario.data.TransferenciaReporteData;
import ec.com.codesoft.codefaclite.inventario.panel.TransferenciasReportePanel;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RutaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.TransferenciaBodegaRespuesta;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TransferenciaReporteModel extends TransferenciasReportePanel{
    
    private java.util.Date fechaInicial;
    private java.util.Date fechaFinal;
    private List<Bodega> bodegaDestinoList;
        
    private Bodega bodegaDestinoSeleccionado;
    
    private List<TransferenciaReporteData> datosReporte;
    private TransferenciaReporteData datoSeleccionado;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        crearModeloTabla();
        datosIniciales();
    }
    
    private void datosIniciales()
    {
        try {
            bodegaDestinoList = ServiceFactory.getFactory().getBodegaServiceIf().obtenerTodosActivos();
            //bodegaDestinoList.add(0,null);
            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(TransferenciaReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(TransferenciaReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        InputStream path = RecursoCodefac.JASPER_INVENTARIO.getResourceInputStream("transferenciaReporte.jrxml");
        Map parameters = new HashMap();
        
        DialogoCodefac.dialogoReporteOpciones( new ReporteDialogListener() {
                @Override
                public void excel() {
                    try{
                        Excel excel = new Excel();
                        String nombreCabeceras[] = (String[]) obtenerCabeceraDatos().toArray(new String[0]);
                        excel.gestionarIngresoInformacionExcel(nombreCabeceras, datosReporte);
                        excel.abrirDocumento();
                    }
                    catch(Exception exc)
                    {
                        exc.printStackTrace();
                        DialogoCodefac.mensaje("Error","El archivo Excel se encuentra abierto",DialogoCodefac.MENSAJE_INCORRECTO);
                    }  
                }

                @Override
                public void pdf() {
                    ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, datosReporte, panelPadre,"Transferencia de Bodegas");
                    //dispose();
                    //setVisible(false);
                }
            });
        
        //ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, datosReporte, panelPadre, "Transferencia de Inventarios");
        
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
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, false);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, false);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, false);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, false);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }
    
    public List<String> obtenerCabeceraDatos()
    {

        return new ArrayList<String>()
        {
            {
                add("Producto");
                add("Tipo");
                add("Bodega Origen");
                add("Bodega Destino");
                add("Empresa");
                add("Fecha");
                add("Cantidad");
                
            }
        };
    }
    
    public void crearModeloTabla()
    {   
        List<String> cabecera=obtenerCabeceraDatos();
        cabecera.add(0,"");
        String titulo[]=(String[]) cabecera.toArray(new String[0]);
        DefaultTableModel modelo=UtilidadesTablas.crearModeloTabla(titulo, new Class[]{Object.class,Object.class,Object.class,Object.class,Object.class,Object.class,Object.class,Object.class});
        getTblDatos().setModel(modelo);
        UtilidadesTablas.definirTamanioColumnas(getTblDatos(),new Integer[]{0});
    }
    
    public ITableBindingAddData getTableBindingAddData()
    {
        return new ITableBindingAddData<TransferenciaReporteData>() {
            @Override
            public Object[] addData(TransferenciaReporteData value) {
                return new Object[]{
                    value,
                    value.getProducto(),
                    value.getTipoTransaccion(),
                    value.getBodegaOrigen(),
                    value.getBodegaDestino(),
                    value.getEmpresa(),
                    value.getFechaIngreso(),
                    value.getCantidad(),                    
                };
            }

            @Override
            public void setData(TransferenciaReporteData objetoOriginal, Object objetoModificado, Integer columnaModificada) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    };
    
    public void btnListenerConsulta()
    {
        try {
            System.out.println("Boton consultar ...");
            //ServiceFactory.getFactory().getBodegaServiceIf().obtenerUnicaBodegaPorSucursal
            List<TransferenciaBodegaRespuesta> respuestaList=ServiceFactory.getFactory().getKardexServiceIf().consultarMovimientosTransferencia(fechaInicial,fechaFinal,bodegaDestinoSeleccionado);
            cargarDatosModelReporte(respuestaList);
            
            //ServiceFactory.getFactory().getBodegaServiceIf().grabar("");
            //ServiceFactory.getFactory().getKardexServiceIf().consultarStock(null,null);
        } catch (RemoteException ex) {
            Logger.getLogger(TransferenciaReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(TransferenciaReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargarDatosModelReporte(List<TransferenciaBodegaRespuesta> respuestaList)
    {
        datosReporte=new ArrayList<TransferenciaReporteData>();
        
        for (TransferenciaBodegaRespuesta respuesta : respuestaList) 
        {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            
            String bodegaDestino="Sin referencia";
            String empresaNombre="";
            
            
            if(respuesta.kardexDetalleBodegaDestinoTransferencia!=null)
            {
                bodegaDestino=respuesta.kardexDetalleBodegaDestinoTransferencia.getKardex().getBodega().getNombre();
                empresaNombre=respuesta.kardexDetalleBodegaDestinoTransferencia.getKardex().getBodega().getEmpresa().getNombreLegal();
            }
            
            TransferenciaReporteData reportData=new TransferenciaReporteData(
                    respuesta.kardexDetalle.getKardex().getProducto().getNombre(),                     
                    bodegaDestino, 
                    respuesta.kardexDetalle.getKardex().getBodega().getNombre(),
                    simpleDateFormat.format(respuesta.kardexDetalle.getFechaIngreso()), 
                    respuesta.kardexDetalle.getCantidad()+"", 
                    empresaNombre,
                    respuesta.kardexDetalle.getCodigoTipoDocumentoEnum().getNombre(),
                    respuesta.kardexDetalle.getKardex().getPrecioUltimoConIva().multiply(respuesta.kardexDetalle.getCantidad())+""
            );  
            
            datosReporte.add(reportData);
            
        }
        
        
    }
    
    ////////////////////////////////////////////////////////////////////////////
    ///                              GET AND SET
    ////////////////////////////////////////////////////////////////////////////

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public List<Bodega> getBodegaDestinoList() {
        return bodegaDestinoList;
    }

    public void setBodegaDestinoList(List<Bodega> bodegaDestinoList) {
        this.bodegaDestinoList = bodegaDestinoList;
    }

    public Bodega getBodegaDestinoSeleccionado() {
        return bodegaDestinoSeleccionado;
    }

    public void setBodegaDestinoSeleccionado(Bodega bodegaDestinoSeleccionado) {
        this.bodegaDestinoSeleccionado = bodegaDestinoSeleccionado;
    }
    

    public List<TransferenciaReporteData> getDatosReporte() {
        return datosReporte;
    }

    public void setDatosReporte(List<TransferenciaReporteData> datosReporte) {
        this.datosReporte = datosReporte;
    }

    public TransferenciaReporteData getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(TransferenciaReporteData datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }
    
    
    
}
