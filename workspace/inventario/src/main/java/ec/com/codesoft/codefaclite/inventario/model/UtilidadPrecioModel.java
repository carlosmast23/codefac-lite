/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaPedidoLoteModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.controlador.vista.inventario.UtilidadPrecioModelControlador;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ITableBindingAddData;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.inventario.panel.UtilidadPrecioPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.ProductoPrecioDataTable;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class UtilidadPrecioModel extends UtilidadPrecioPanel implements ControladorVistaIf,UtilidadPrecioModelControlador.SwingIf{

    
    private UtilidadPrecioModelControlador controlador;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException 
    {
        this.controlador = new UtilidadPrecioModelControlador(DialogoCodefac.intefaceMensaje, session,this, UtilidadPrecioModelControlador.TipoVista.ESCRITORIO);
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
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, false);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, false);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    public UtilidadPrecioModelControlador getControlador() {
        return controlador;
    }

    public void setControlador(UtilidadPrecioModelControlador controlador) {
        this.controlador = controlador;
    }
    
    
    //////////////////// METODOS ADICIONALES ////////////////////////////
    
    public void crearModeloTabla()
    {   
        String titulo[]=new String[]
        {
            "Objeto",
            "Seleccion",
            "Código",
            "Nombre Producto",
            "Último Costo",
            "Costo Promedio",
            "% pvp1",
            "% pvp2",
            "% pvp3",
            "% pvp4",
            "% pvp5",
            "% pvp6"
        };
        
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
                    Object.class,
                }
        );
        
         getTblProductos().setModel(modelo);
        
        JPopupMenu popup = new JPopupMenu();        
        JMenuItem jMenuItemCambiarDocumento = new JMenuItem("Cambiar Documento"); 
        popup.add(jMenuItemCambiarDocumento);
        //jMenuItemCambiarDocumento.addActionListener(itemListener);
        
        getTblProductos().setComponentPopupMenu(popup);
        

        UtilidadesTablas.definirTamanioColumnas(getTblProductos(),new Integer[]{0});
    }
    
    public ITableBindingAddData getTableBindingAddData()
    {
        return new ITableBindingAddData<ProductoPrecioDataTable>() {
            @Override
            public Object[] addData(ProductoPrecioDataTable valueTmp) {
                Producto producto=valueTmp.producto;
                String codigo=producto.getCodigoPersonalizado();
                String nombreProducto=producto.getNombre();
                                
                                                
                return new Object[]{
                    valueTmp,
                    codigo,
                    nombreProducto,
                    valueTmp.costoUltimo,
                    valueTmp.costoPromedio,
                    valueTmp.porcentajePvp1,
                    valueTmp.porcentajePvp2,
                    valueTmp.porcentajePvp3,
                    valueTmp.porcentajePvp4,
                    valueTmp.porcentajePvp5,
                    valueTmp.porcentajePvp6,
                };
            }

            @Override
            public void setData(ProductoPrecioDataTable objetoOriginal, Object objetoModificado, Integer columnaModificada) {
                final int COLUMNA_OBJETO=0;
                final int COLUMNA_PVP1_PORCENTAJE=3;
                final int COLUMNA_PVP2_PORCENTAJE=4;
                final int COLUMNA_PVP3_PORCENTAJE=5;
                final int COLUMNA_PVP4_PORCENTAJE=6;
                final int COLUMNA_PVP5_PORCENTAJE=7;
                final int COLUMNA_PVP6_PORCENTAJE=8;
                
                
                switch (columnaModificada) {
                    case COLUMNA_OBJETO:
                        break;

                    case COLUMNA_PVP1_PORCENTAJE:
                        objetoOriginal.porcentajePvp1=new BigDecimal(objetoModificado+"");
                        break;

                    case COLUMNA_PVP2_PORCENTAJE:
                        objetoOriginal.porcentajePvp2=new BigDecimal(objetoModificado+"");
                        break;
                        
                    case COLUMNA_PVP3_PORCENTAJE:
                        objetoOriginal.porcentajePvp3=new BigDecimal(objetoModificado+"");
                        break;

                    case COLUMNA_PVP4_PORCENTAJE:
                        objetoOriginal.porcentajePvp4=new BigDecimal(objetoModificado+"");
                        break;
                        
                    case COLUMNA_PVP5_PORCENTAJE:
                        objetoOriginal.porcentajePvp5=new BigDecimal(objetoModificado+"");
                        break;
                        
                    case COLUMNA_PVP6_PORCENTAJE:
                        objetoOriginal.porcentajePvp6=new BigDecimal(objetoModificado+"");
                        break;
                }
                
                
            }
        };
    };

    @Override
    public ModelControladorAbstract getControladorVista() 
    {
        return controlador;
    }

    @Override
    public Boolean pendientesActualizarPrecio() {
        return getChkPendientesActualizar().isSelected();
    }
    
}
