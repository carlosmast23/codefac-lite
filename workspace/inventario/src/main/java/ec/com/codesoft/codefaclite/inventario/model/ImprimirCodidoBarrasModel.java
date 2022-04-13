/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.inventario.data.CodigoBarrasData;
import ec.com.codesoft.codefaclite.inventario.panel.ImprimirCodigoBarrasPanel;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.utilidades.imagen.UtilidadCodigoBarras;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Carlos
 */
public class ImprimirCodidoBarrasModel extends ImprimirCodigoBarrasPanel{
    
    private static final Integer COLUMNA_OBJETO=0;
    private static final Integer COLUMNA_CANTIDAD=3;
    
    private Map<Producto,Integer> mapProductosImprimir;
   
 
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        cargarComboBox();
        listenerBotones();
        listenerPopUps();
    }
    
    private void cargarComboBox()
    {
        getCmbTipoCodigoBarras().removeAllItems();
        getCmbTipoCodigoBarras().addItem(UtilidadCodigoBarras.CodigoBarrasEnum.CODE128);
        getCmbTipoCodigoBarras().addItem(UtilidadCodigoBarras.CodigoBarrasEnum.CODE39);        
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
        
        String formatoImpresion=getCmbFormatoImpresion().getSelectedItem().toString();
        String nombreJasper="codigosBarras.jrxml";
        if(formatoImpresion.equals("A4"))
        {
            nombreJasper="codigosBarras.jrxml";
        }
        else if (formatoImpresion.equals("POS"))
        {
            nombreJasper="codigosBarrasMini.jrxml";
        }
        
        InputStream path = RecursoCodefac.JASPER_INVENTARIO.getResourceInputStream(nombreJasper);
        Map<String,Object> parametros = new HashMap<String,Object>();
        
        ReporteCodefac.generarReporteInternalFramePlantilla(path, parametros, getData(), panelPadre,"Códigos de Barras");
        
    }
    
    private List<CodigoBarrasData> getData()
    {
        UtilidadCodigoBarras.CodigoBarrasEnum codigoEnum = (UtilidadCodigoBarras.CodigoBarrasEnum) getCmbTipoCodigoBarras().getSelectedItem();
        
        String opcionLlevaIva=(String) getCmbPrecioConIva().getSelectedItem();        
               
        List<CodigoBarrasData> listaDatos=new ArrayList<CodigoBarrasData>();
        int dpi=(int) getTxtDpi().getValue();
        
        for (Map.Entry<Producto,Integer> entry : mapProductosImprimir.entrySet()) 
        {
            Producto producto = entry.getKey();
            Integer cantidad = entry.getValue();
            
            Image imagenCodigoBarras= UtilidadCodigoBarras.obtenerImagenCodigoBarras(producto.getCodigoPersonalizado(),dpi,codigoEnum);
            for (int i = 0; i < cantidad; i++) 
            {
                CodigoBarrasData codigoBarraData=new CodigoBarrasData();
                codigoBarraData.setImagen(imagenCodigoBarras);
                codigoBarraData.setCodigo(producto.getCodigoPersonalizado());
                codigoBarraData.setNombre(producto.getNombre());
                
                if (opcionLlevaIva.equals("SI")) 
                {
                    codigoBarraData.setPrecio("$"+producto.obtenerValorUnitarioConIva().setScale(2, RoundingMode.HALF_UP).toString());
                } 
                else if (opcionLlevaIva.equals("NO")) 
                {
                    codigoBarraData.setPrecio("$"+producto.getValorUnitario().setScale(2, RoundingMode.HALF_UP).toString());
                }
                
                listaDatos.add(codigoBarraData);
            }
        }
        return listaDatos; 
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        mapProductosImprimir=new HashMap<Producto,Integer>();
        
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        return permisos;
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

    private void listenerBotones() {
        getBtnAgregarTodos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //Producto p;
                    //p.getGenerarCodigoBarras();
                    /*Map<String,Object> mapParametros=new HashMap<String,Object>();
                    mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
                    mapParametros.put("generarCodigoBarras",EnumSiNo.SI.getLetra());*/
                    
                    Producto productoBuscar=ServiceFactory.getFactory().getProductoServiceIf().buscarGenerarCodigoBarras(EnumSiNo.SI,session.getEmpresa());
                    if (productoBuscar!=null) {
                        agregarProductoMap(productoBuscar);
                    }
                    
                    generarTabla(); //Dibujar los datos en la tabla
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(ImprimirCodidoBarrasModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(ImprimirCodidoBarrasModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        getBtnAgregarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo(session.getEmpresa());
                productoBusquedaDialogo.setGenerarCodigoBarrasEnum(EnumSiNo.SI); //Solo buscar los que tenga activa esta opcion
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                Producto productoTmp = (Producto) buscarDialogoModel.getResultado();
                if(productoTmp!=null)
                {
                    agregarProductoMap(productoTmp);
                    generarTabla();
                }
            }
        });
    }
    
    
    private boolean agregarProductoMap(Producto producto)
    {
        Integer cantidad=mapProductosImprimir.get(producto);
        if(cantidad==null)
        {
            mapProductosImprimir.put(producto,1);
            return true;
        }
        return false;
        
    }
    
    private boolean  editarProductoMap(Producto producto,Integer cantidad)
    {
        mapProductosImprimir.put(producto, cantidad);
        return true;
    }
    
    
    private void generarTabla()
    {
        DefaultTableModel modeloTabla=UtilidadesTablas.crearModeloTabla(
                new String[]{"","Código","Nombre","Cantidad"}, 
                new Class[]{Producto.class,String.class,String.class,Integer.class},
                new Boolean[]{false,false,false,true});
        
        for (Map.Entry<Producto,Integer> entry : mapProductosImprimir.entrySet()) {
            Producto producto = entry.getKey();
            Integer cantidad = entry.getValue();
            
            modeloTabla.addRow(new Object[]{
                producto,
                producto.getCodigoPersonalizado(),
                producto.getNombre(),
                cantidad
            });
        }
        modeloTabla.addTableModelListener(modelListener);
        getTblDatos().setModel(modeloTabla);
        UtilidadesTablas.ocultarColumna(getTblDatos(),COLUMNA_OBJETO);
        
    }
    
    /**
     * Listener que permite editar la cantidad en el modelo cuando se modifique en la tabla
     */
    TableModelListener modelListener=new TableModelListener() {
        @Override
        public void tableChanged(TableModelEvent e) {
            int columna=e.getColumn();
            if(columna==COLUMNA_CANTIDAD)
            {
                int fila=e.getFirstRow();
                TableModel modeloTabla= (TableModel) e.getSource();
                Producto producto= (Producto) modeloTabla.getValueAt(fila,COLUMNA_OBJETO); 
                Integer cantidad=(Integer) modeloTabla.getValueAt(fila,COLUMNA_CANTIDAD);
                
                editarProductoMap(producto,cantidad);
                
            }
            
        }
    };

    private void listenerPopUps() {
        JPopupMenu jpopMenu=new JPopupMenu();
        JMenuItem jmenuItemEliminar=new JMenuItem("Eliminar");
        jmenuItemEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila=getTblDatos().getSelectedRow();
                if(fila>=0)
                {
                    int filasSeleccionadas[]=getTblDatos().getSelectedRows();
                    for (int filaSeleccion : filasSeleccionadas) 
                    {
                        DefaultTableModel modeloTabla= (DefaultTableModel) getTblDatos().getModel();
                        Producto producto= (Producto) modeloTabla.getValueAt(filaSeleccion,COLUMNA_OBJETO);
                        mapProductosImprimir.remove(producto);
                    }
                    generarTabla();
                }
            }
        });
        
        jpopMenu.add(jmenuItemEliminar);        
        getTblDatos().setComponentPopupMenu(jpopMenu);
    }
    
}
