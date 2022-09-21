/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.transporte.model;

import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.controlador.vista.transporte.GuiaRemisionLoteControlador;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ITableBindingAddData;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RutaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.transporte.panel.GuiaRemisionLotePanel;
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
public class GuiaRemisionLoteModel extends GuiaRemisionLotePanel implements ControladorVistaIf,GuiaRemisionLoteControlador.SwingIf{

    private GuiaRemisionLoteControlador controlador;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        controlador=new GuiaRemisionLoteControlador(DialogoCodefac.intefaceMensaje, session, this, ModelControladorAbstract.TipoVista.ESCRITORIO);
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
        return "https://docs.google.com/document/d/e/2PACX-1vRxHiHd5vpEu1In25BKtCXigpl4m1phGAZwNR7Rh2Jm-Xqe7ffQpivlYJsMAWHFBS0BOnYxj4dpUi7H/pub?embedded=true#h.l42dko1b5qfq";
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
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public ModelControladorAbstract getControladorVista() {
        return controlador;
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
            "Total"};
        
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
                }
        );
        
        getTblDatos().setModel(modelo);
        UtilidadesTablas.definirTamanioColumnas(getTblDatos(),new Integer[]{0});
    }
    
    public ITableBindingAddData getTableBindingAddData()
    {
        return new ITableBindingAddData<Factura>() {
            @Override
            public Object[] addData(Factura value) {
                String nombreComercial=(value.getSucursal().getNombreComercial()!=null)?value.getSucursal().getNombreComercial():"";
                String vendedor=(value.getVendedor()!=null)?value.getVendedor().getNombresCompletos():"";
                return new Object[]{
                    value,
                    value.getPreimpreso(),
                    value.getFechaEmision(),
                    value.getIdentificacion(),
                    value.getRazonSocial(),
                    nombreComercial,
                    vendedor,
                    "",//Ruta                    
                    value.getCodigoDocumentoEnum().getNombre(),
                    value.getTotal()
                };
            }

            @Override
            public void setData(Factura objetoOriginal, Object objetoModificado, Integer columnaModificada) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    };
    
    ////////////////////////////////////////////////////////////////////////////
    ///                         GET AND SET
    ////////////////////////////////////////////////////////////////////////////

    public GuiaRemisionLoteControlador getControlador() {
        return controlador;
    }

    public void setControlador(GuiaRemisionLoteControlador controlador) {
        this.controlador = controlador;
    }

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
    
    
}
