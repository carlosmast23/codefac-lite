/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servicios.model;

import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaPedidoLoteModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.controlador.vista.inventario.MarcaProductoControlador;
import ec.com.codesoft.codefaclite.controlador.vista.inventario.SegmentoProductoControlador;
import ec.com.codesoft.codefaclite.controlador.vista.servicio.ObjetoMantenimientoControlador;
import ec.com.codesoft.codefaclite.controlador.vista.servicio.TareaPendienteOrdenTrabajoControlador;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ITableBindingAddData;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
//import ec.com.codesoft.codefaclite.inventario.panel.MarcaProductoPanel;
//import ec.com.codesoft.codefaclite.inventario.panel.SegmentoProductoPanel;
import ec.com.codesoft.codefaclite.servicios.panel.ObjetoMantenimientoPanel;
import ec.com.codesoft.codefaclite.servicios.panel.TareaPendienteOrdenTrabajoPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobanteLote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ObjetoMantenimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresupuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PresupuestoDetalleActividad;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TareaPendienteOrdenTrabajoModel extends TareaPendienteOrdenTrabajoPanel implements ControladorVistaIf,TareaPendienteOrdenTrabajoControlador.SwingIf{
    
    private TareaPendienteOrdenTrabajoControlador controlador;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        this.controlador=new TareaPendienteOrdenTrabajoControlador(DialogoCodefac.intefaceMensaje, session, this, ModelControladorAbstract.TipoVista.ESCRITORIO);
        crearModeloTabla();
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
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }
    
        
    public void crearModeloTabla()
    {
        String titulo[]=new String[]{
            "Objecto",
            "Finalizar",
            "Objeto Mantenimiento",
            "Código OT",
            "Fecha Ingreso",
            "Detalle",            
            "Descripción",};
        
        DefaultTableModel modelo=UtilidadesTablas.crearModeloTabla(
                titulo, 
                new Class[]{
                    Object.class,
                    Boolean.class,
                    Object.class,
                    Object.class,
                    Object.class,
                    Object.class,
                    Object.class,
                }
        );
        
        getTblDatos().setModel(modelo);
        UtilidadesTablas.definirTamanioColumnas(getTblDatos(),new Integer[]{0});
        
    }
    
    public ITableBindingAddData getTableBindingAddData()
    {        
        return new ITableBindingAddData<PresupuestoDetalleActividad>() {
            @Override
            public Object[] addData(PresupuestoDetalleActividad valueTmp) {
                PresupuestoDetalle presupuestoDetalle=valueTmp.getPresupuestoDetalle();
                String detalle=valueTmp.getProductoActividad().getNombre();
                Boolean terminadoBool=valueTmp.getTerminadoEnum().getBool();
                
                ObjetoMantenimiento objetoMantenimiento=presupuestoDetalle.getPresupuesto().getOrdenTrabajoDetalle().getOrdenTrabajo().getObjetoMantenimiento();
                String objetoMantenimientoStr="";
                if(objetoMantenimiento!=null)
                {
                    objetoMantenimientoStr=objetoMantenimiento.getNombre();
                }
                
                return new Object[]
                {
                    valueTmp,                    
                    objetoMantenimientoStr,
                    presupuestoDetalle.getPresupuesto().getOrdenTrabajoDetalle().getOrdenTrabajo().getId(),
                    presupuestoDetalle.getPresupuesto().getFechaPresupuesto(),
                    detalle,                    
                    "Sin Novedad"
                };
            }

            @Override
            public void setData(PresupuestoDetalleActividad objetoOriginal, Object objetoModificado, Integer columnaModificada) {
                final int COLUMNA_OBJETO=0;
                final int COLUMNA_TERMINAR=1;
                //final int COLUMNA_NOVEDADES=4;
                
                switch (columnaModificada) {
                    case COLUMNA_OBJETO:
                        break;
                    
                    case COLUMNA_TERMINAR:
                        objetoOriginal.setTerminado(EnumSiNo.getEnumByBoolean((Boolean) objetoModificado));
                        break;

                    //case COLUMNA_APROBAR:
                    //    objetoOriginal.aprobar=(Boolean) objetoModificado;
                    //    break;

                    /*case COLUMNA_NOVEDADES:
                        objetoOriginal.novedades=(String) objetoModificado;
                        break;*/

                }
                
                
            }
        };
    };

    @Override
    public ModelControladorAbstract getControladorVista() {
        return controlador;
    }

    public TareaPendienteOrdenTrabajoControlador getControlador() {
        return controlador;
    }

    public void setControlador(TareaPendienteOrdenTrabajoControlador controlador) {
        this.controlador = controlador;
    }

    @Override
    public void abrirGuiaRemision(GuiaRemision guiaRemision) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ClienteInterfaceComprobanteLote getInterfaceCallBack() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void generarNotasVentaInterna(List<Factura> facturas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cerrarPantalla() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
