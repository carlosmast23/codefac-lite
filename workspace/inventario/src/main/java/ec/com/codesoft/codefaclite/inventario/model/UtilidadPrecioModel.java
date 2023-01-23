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
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazPostConstructPanel;
import ec.com.codesoft.codefaclite.inventario.panel.UtilidadPrecioPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.ProductoPrecioDataTable;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesPorcentajes;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class UtilidadPrecioModel extends UtilidadPrecioPanel implements DialogInterfacePanel,InterfazPostConstructPanel, ControladorVistaIf,UtilidadPrecioModelControlador.SwingIf{

    
    private UtilidadPrecioModelControlador controlador;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException 
    {
        this.controlador = new UtilidadPrecioModelControlador(DialogoCodefac.intefaceMensaje, session,this, UtilidadPrecioModelControlador.TipoVista.ESCRITORIO);
        listenerTablas();
        crearModeloTabla();
    }
    
    private void listenerCargarPreciosOriginal(Producto producto)
    {
        DefaultTableModel tableModel = UtilidadesTablas.crearModeloTabla(new String[]{"Precio", "Valor"}, new Class[]{String.class, String.class});
        List<Producto.PrecioVenta> precioList= producto.obtenerPreciosVenta();
        
        for (Producto.PrecioVenta precioVenta : precioList) 
        {
            Object[] fila={precioVenta.alias,precioVenta.precio+""};
            tableModel.addRow(fila);
        }
        
        getTblPreciosOriginal().setModel(tableModel);
    }
    
    private void listenerTablas()
    {
        getTblProductos().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                
                int filaSeleccionada=getTblProductos().getSelectedRow();
                if(filaSeleccionada>=0)
                {
                    ProductoPrecioDataTable productoData=(ProductoPrecioDataTable) getTblProductos().getValueAt(filaSeleccionada,0);
                    listenerCargarPreciosOriginal(productoData.producto);
                }
            }
        });        
        
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
            "Pvp1",
            "Pvp2",
            "Pvp3",
            "Pvp4",
            "Pvp5",
            "Pvp6"
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
        

        UtilidadesTablas.definirTamanioColumnas(getTblProductos(),new Integer[]{0,50,250,600,80,80});
    }
    
    
    public ITableBindingAddData getTableBindingAddData()
    {
        return new ITableBindingAddData<ProductoPrecioDataTable>() {
            @Override
            public Object[] addData(ProductoPrecioDataTable valueTmp) {
                Producto producto=valueTmp.producto;
                String codigo=producto.getCodigoPersonalizado();
                String nombreProducto=producto.getNombre();
                               
                valueTmp.recalcularValoresDesdePorcentajes(valueTmp.costoUltimo);
                                                
                return new Object[]{
                    valueTmp,
                    codigo,
                    nombreProducto,
                    valueTmp.costoUltimo,
                    valueTmp.costoPromedio,
                    valueTmp.pvp1,
                    valueTmp.pvp2,
                    valueTmp.pvp3,
                    valueTmp.pvp4,
                    valueTmp.pvp5,
                    valueTmp.pvp6,
                };
            }

            @Override
            public void setData(ProductoPrecioDataTable objetoOriginal, Object objetoModificado, Integer columnaModificada) {
                final int COLUMNA_OBJETO=0;
                final int COLUMNA_PVP1_PORCENTAJE=6;
                final int COLUMNA_PVP2_PORCENTAJE=7;
                final int COLUMNA_PVP3_PORCENTAJE=8;
                final int COLUMNA_PVP4_PORCENTAJE=9;
                final int COLUMNA_PVP5_PORCENTAJE=10;
                final int COLUMNA_PVP6_PORCENTAJE=11;
                
                BigDecimal valorModificado=null;
                
                try
                {
                    valorModificado=new BigDecimal(objetoModificado+"");
                }
                catch(NumberFormatException nfe)
                {
                    nfe.printStackTrace();
                }
                
                
                switch (columnaModificada) {
                    case COLUMNA_OBJETO:
                        break;

                    case COLUMNA_PVP1_PORCENTAJE:
                        objetoOriginal.pvp1=valorModificado;
                        break;

                    case COLUMNA_PVP2_PORCENTAJE:
                        objetoOriginal.pvp2=valorModificado;
                        break;
                        
                    case COLUMNA_PVP3_PORCENTAJE:
                        objetoOriginal.pvp3=valorModificado;
                        break;

                    case COLUMNA_PVP4_PORCENTAJE:
                        objetoOriginal.pvp4=valorModificado;
                        break;
                        
                    case COLUMNA_PVP5_PORCENTAJE:
                        objetoOriginal.pvp5=valorModificado;
                        break;
                        
                    case COLUMNA_PVP6_PORCENTAJE:
                        objetoOriginal.pvp6=valorModificado;
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

    @Override
    public void postConstructorExterno(Object[] parametros) {
        if(parametros==null)
        {
            return ;
        }
        
        if(parametros.length>0)
        {
            List<Producto> productoListTmp=(List<Producto>) parametros[0];
            this.controlador.castListDataTable(productoListTmp);
            actualizarBindingCompontValues();
        }
    }

    @Override
    public Object getResult() throws ExcepcionCodefacLite {
        try {
            this.controlador.grabar();
            //Todo: Terminar logica para terminr de retornar los datos para grabar            
        } catch (RemoteException ex) {
            Logger.getLogger(UtilidadPrecioModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
