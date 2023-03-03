/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.CategoriaProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.StockMinimoData;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.inventario.panel.StockMinimoPanel;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.orden.KardexOrdenarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 * TODO: Analizar la posibilidad de unir con la pantalla de Reporte Stcok porque son demasiadas similar
 */
public class StockMinimoModel extends StockMinimoPanel{
    
    private List<Object[]> listaStock;
    private List<StockMinimoData> listaData;
    protected CategoriaProducto categoriaProducto;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        valoresIniciales();
        listenerCheckBox();
        listenerBotones();
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
        
        //InputStream path = RecursoCodefac.JASPER_INVENTARIO.getResourceInputStream("stockMinimoConProveedor.jrxml");
        
        DialogoCodefac.dialogoReporteOpciones( new ReporteDialogListener() {
                @Override
                public void excel() {
                    try{
                        Excel excel = new Excel();
                        String nombreCabeceras[] = {"Código", "Producto","Categoria", "Stock", "Cantidad Min"};
                        excel.gestionarIngresoInformacionExcel(nombreCabeceras,listaData);
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
                    //ReporteCodefac.generarReporteInternalFramePlantilla(path,new HashMap(), listaData, panelPadre, "Reporte Stock Minimo ");
                    ReporteCodefac.generarReporteInternalFramePlantilla(RecursoCodefac.JASPER_INVENTARIO,"stockMinimoConProveedor.jrxml",new HashMap(), listaData, panelPadre, "Reporte Stock", OrientacionReporteEnum.HORIZONTAL, FormatoHojaEnum.A4);
                    //dispose();
                    //setVisible(false);
                }
            });
        
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
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
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

    public void valoresIniciales() {
        try {                       
            getCmbBodega().removeAllItems();
            BodegaServiceIf servicioBodega = ServiceFactory.getFactory().getBodegaServiceIf();
            List<Bodega> bodegas = servicioBodega.obtenerActivosPorEmpresa(session.getEmpresa());
            UtilidadesComboBox.llenarComboBox(getCmbBodega(), bodegas);
            //for (Bodega bodega : bodegas) {
            //    getCmbBodega().addItem(bodega);                
            //}            
           UtilidadesComboBox.llenarComboBox(getCmbOrdenar(), KardexOrdenarEnum.values());
            
            getChkTodasCategoria().setSelected(true);
            getBtnBuscarCategoria().setEnabled(false);
        } catch (RemoteException ex) {
            Logger.getLogger(GestionInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(StockMinimoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listenerBotones() {
         getBtnBuscarCategoria().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CategoriaProductoBusquedaDialogo catProdBusquedaDialogo = new CategoriaProductoBusquedaDialogo(session.getEmpresa());
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(catProdBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                categoriaProducto = (CategoriaProducto) buscarDialogoModel.getResultado();
                cargarCategoriaPantalla();
            }
        });
        
        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listenerBotonBuscar();
            }
        });
    }
    
    public void listenerBotonBuscar()
    {
        try {
            Bodega bodegaSeleccionada = (Bodega) getCmbBodega().getSelectedItem();
            if(getChkTodasBodega().isSelected())
            {
                bodegaSeleccionada=null;
            }
            KardexOrdenarEnum ordenarEnum=(KardexOrdenarEnum) getCmbOrdenar().getSelectedItem();            
            listaStock = ServiceFactory.getFactory().getKardexServiceIf().consultarStockMinimo(bodegaSeleccionada, categoriaProducto, session.getEmpresa(),ordenarEnum);
            listaData = new ArrayList<StockMinimoData>();

            for (Object[] objeto : listaStock) 
            {
                Producto producto = (Producto) objeto[0];
                BigDecimal cantidad = (BigDecimal) objeto[1];
                Kardex kardex=null;
                //Kardex kardex=(Kardex) objeto[2];
                //Lote lote=(Lote)objeto[4];

                StockMinimoData data = new StockMinimoData();

                data.setCodigo(producto.getCodigoPersonalizado().toString());
                data.setProducto(producto.getNombre());
                data.setStock(cantidad.toString());
                data.setCategoria((producto.getCatalogoProducto().getCategoriaProducto() != null) ? producto.getCatalogoProducto().getCategoriaProducto().getNombre() : "");
                data.setCantidadMinima(producto.getCantidadMinima().toString());
                data.setLote(""); //Por el momento voy dejar que salga un stock minimo por todos los lotes
                //Ver si este dato luego se debe poner
                data.setBodega("");
                if(kardex!=null)
                {
                    data.setReserva(kardex.getReserva());
                }
                
                //TODO: Optimizar este proceso para obtener toda la informacipon directamente del servidor
                List<ProductoProveedor> productoProveedorList= ServiceFactory.getFactory().getProductoProveedorServiceIf().buscarPorProductoActivo(producto);
                if(productoProveedorList.size()>0)
                {
                    ProductoProveedor productoProveedor=productoProveedorList.get(0);
                    data.setRucProveedor(productoProveedor.getProveedor().getIdentificacion());
                    data.setNombreProveedor(productoProveedor.getProveedor().getRazonSocial());
                }else
                {
                    data.setRucProveedor("");
                    data.setNombreProveedor("");
                
                }

                listaData.add(data);

            }

            construirTabla();
        } catch (RemoteException ex) {
            Logger.getLogger(StockMinimoModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(StockMinimoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void listenerCheckBox() {
        getChkTodasCategoria().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//accion cuando se selecciona todos
                    categoriaProducto = null;
                    cargarCategoriaPantalla();
                    getBtnBuscarCategoria().setEnabled(false);
                } else {
                    getBtnBuscarCategoria().setEnabled(true);
                }
            }
        });
    
    }
    
    private void cargarCategoriaPantalla()
    {
        if(categoriaProducto!=null)
        {
            getTxtCategoria().setText(categoriaProducto.getDescripcion());
        }
        else
        {
            getTxtCategoria().setText("");
        }
    }
    
    private void construirTabla()
    {
        String[] titulo={
            "Código",
            "Producto",
            "Categoria",
            "Stock",
            "Cantidad Minima"
        };
        
        DefaultTableModel modeloTabla=new DefaultTableModel(titulo,0);
        for (StockMinimoData stockMinimo : listaData) {
            
            String[] datos=
            {
                stockMinimo.getCodigo(),
                stockMinimo.getProducto(),
                stockMinimo.getCategoria(),
                stockMinimo.getStock(),
                stockMinimo.getCantidadMinima(),
                
            };
            
            modeloTabla.addRow(datos);
        }
        
        getTblDato().setModel(modeloTabla);
        
    }
    
}
