/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaPedidoLoteModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.controlador.vista.transporte.GuiaRemisionLoteControlador;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ITableBindingAddData;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.facturacion.callback.ClienteFacturaLoteImplComprobante;
import ec.com.codesoft.codefaclite.facturacion.interfaz.InterfaceCallbakClient;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturaPedidoLotePanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobanteLote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.rmi.RemoteException;
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
public class FacturaPedidoLoteModel extends FacturaPedidoLotePanel implements ControladorVistaIf,FacturaPedidoLoteModelControlador.SwingIf {

    private FacturaPedidoLoteModel formThis=this;
    
    private FacturaPedidoLoteModelControlador controlador;

    public FacturaPedidoLoteModel() {
        /**
         * Desactivo el ciclo de vida para controlar manualmente
         */
        //super.cicloVida = false;
        //super.validacionDatosIngresados = false;
    }
    
    
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        this.controlador=new FacturaPedidoLoteModelControlador(DialogoCodefac.intefaceMensaje, session,this, ModelControladorAbstract.TipoVista.ESCRITORIO);
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

    @Override
    public ModelControladorAbstract getControladorVista() {
        return controlador;
    }

    public FacturaPedidoLoteModelControlador getControlador() {
        return controlador;
    }

    public void setControlador(FacturaPedidoLoteModelControlador controlador) {
        this.controlador = controlador;
    }
    
    public void crearModeloTabla()
    {   
        String titulo[]=new String[]{
            "Objeto",
            "Seleccion",
            "Preimpreso",
            "Fecha",
            "Identificación",
            "Razón Social",
            "Nombre Legal",
            "Vendedor",
            "Ruta",
            "Documento",
            "Total",
            "Crédito",
            "Dias"};
        
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
                    Object.class,
                    Object.class,
                    Object.class,
                    Object.class,
                    Boolean.class,
                    Integer.class,
                }
        );
        
        getTblDatos().setModel(modelo);
        UtilidadesTablas.definirTamanioColumnas(getTblDatos(),new Integer[]{0});
    }
    
    public ITableBindingAddData getTableBindingAddData()
    {
        return new ITableBindingAddData<FacturaPedidoLoteModelControlador.FacturaDataTable>() {
            @Override
            public Object[] addData(FacturaPedidoLoteModelControlador.FacturaDataTable valueTmp) {
                Factura value=valueTmp.factura;
                String nombreComercial=(value.getSucursal().getNombreComercial()!=null)?value.getSucursal().getNombreComercial():"";
                String vendedor=(value.getVendedor()!=null)?value.getVendedor().getNombresCompletos():"";
                return new Object[]{
                    valueTmp,
                    value.getPreimpreso(),
                    value.getFechaEmision(),
                    value.getIdentificacion(),
                    value.getRazonSocial(),
                    nombreComercial,
                    vendedor,
                    "",//Ruta                    
                    value.getCodigoDocumentoEnum().getNombre(),
                    value.getTotal(),
                    valueTmp.credito,
                    valueTmp.dias
                };
            }
        };
    };

    @Override
    public void abrirGuiaRemision(GuiaRemision guiaRemision) {
        Object[] parametros=new Object[]{
            guiaRemision,
            false,
            true
        };
        panelPadre.crearVentanaCodefac(VentanaEnum.GUIA_REMISION,true, parametros);
    }

    @Override
    public void cerrarPantalla() {
        //dispose();
    }

    @Override
    public ClienteInterfaceComprobanteLote getInterfaceCallBack() {
        ClienteInterfaceComprobanteLote cic =null;
        try {
            cic = new ClienteFacturaLoteImplComprobante(this,new InterfaceCallbakClient() {
                @Override
                public void terminoProceso() {
                    formThis.estadoNormal();
                    //getCmbCarpetaComprobante().setSelectedIndex(getCmbCarpetaComprobante().getSelectedIndex()); //Vuelve a cargar los comprobantes
                }
            });
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaPedidoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cic;
    }

    

}
