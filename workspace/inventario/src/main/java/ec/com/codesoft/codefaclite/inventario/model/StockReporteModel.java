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
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.inventario.busqueda.CategoriaProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.data.StockMinimoData;
import ec.com.codesoft.codefaclite.inventario.panel.StockMinimoPanel;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.InputStream;
import java.math.BigDecimal;
 ;
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
 */
public class StockReporteModel extends StockMinimoPanel{
    
    private List<Object[]> listaStock;
    private List<StockMinimoData> listaData;
    
    protected CategoriaProducto categoriaProducto;
    

    @Override
    public void iniciar() throws ExcepcionCodefacLite   {
        listenerBotones();
        listenerCheckBox();
        valoresIniciales();
        setTitle("Reporte Stock");
    }
    
    private void valoresIniciales() {
        try {                       
            getCmbBodega().removeAllItems();
            BodegaServiceIf servicioBodega = ServiceFactory.getFactory().getBodegaServiceIf();
            List<Bodega> bodegas = servicioBodega.obtenerActivosPorEmpresa(session.getEmpresa());
            UtilidadesComboBox.llenarComboBox(getCmbBodega(), bodegas);
            //for (Bodega bodega : bodegas) {
            //    getCmbBodega().addItem(bodega);                
            //}
            //Por defecto aparece desctiva para que buque todas las categorias
            getChkTodasCategoria().setSelected(true);
            getBtnBuscarCategoria().setEnabled(false);
        }catch (ServicioCodefacException ex) {
            Logger.getLogger(StockMinimoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite   {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite   {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite   {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite   {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite   {
        
        InputStream path = RecursoCodefac.JASPER_INVENTARIO.getResourceInputStream("stockMinimo.jrxml");
        
        DialogoCodefac.dialogoReporteOpciones( new ReporteDialogListener() {
                @Override
                public void excel() {
                    try{
                        Excel excel = new Excel();
                        String nombreCabeceras[] = {"Código", "Producto","Categoria","Ubicación", "Stock", "Cantidad Min","Costo"};
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
                    ReporteCodefac.generarReporteInternalFramePlantilla(path,new HashMap(), listaData, panelPadre, "Reporte Stock");
                    //dispose();
                    //setVisible(false);
                }
            });
        
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite   {
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
                try {
                    Bodega bodegaSeleccionada=(Bodega) getCmbBodega().getSelectedItem();
                    
                    
                    listaStock=ServiceFactory.getFactory().getKardexServiceIf().consultarStock(bodegaSeleccionada,categoriaProducto);
                    
                    listaData=new ArrayList<StockMinimoData>();
                    
                     for (Object[] objeto : listaStock) 
                     {
                        Producto producto = (Producto) objeto[0];
                        Integer cantidad = (Integer) objeto[1];
                        BigDecimal costoPromedio=(BigDecimal)objeto[2];
                        //Kardex kardexTemp = (Kardex) objeto[2];
                        
                        /*if(producto==null)
                        {
                            System.err.println("Error con un producto nulo en kardeId="+kardexTemp.getId());
                            continue;
                        }*/
                        
                        StockMinimoData data=new StockMinimoData();
                        
                        
                        
                        String codigoPersonalizado="Sin Código";
                        if(producto.getCodigoPersonalizado()!=null)
                        {
                            codigoPersonalizado=producto.getCodigoPersonalizado();
                        }
                        
                        data.setCodigo(codigoPersonalizado);
                        data.setProducto(producto.getNombre());
                        data.setStock(cantidad.toString());
                        data.setCategoria((producto.getCatalogoProducto().getCategoriaProducto()!=null)?producto.getCatalogoProducto().getCategoriaProducto().getNombre():"");
                        data.setUbicacion(producto.getUbicacion());
                        data.setCantidadMinima(producto.getCantidadMinima().toString());
                        data.setCosto(costoPromedio.toString());
                        
                        listaData.add(data);                        
                    }
                     
                     construirTabla();
                    

                    
                } catch (Exception ex) {
                    Logger.getLogger(StockReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
    }
    
    
    private void construirTabla()
    {
        String[] titulo={
            "Código",
            "Producto",
            "Categoria",
            "Ubicación",
            "Stock",
            "Cantidad Minima",
            "Costo"
        };
        
        DefaultTableModel modeloTabla=new DefaultTableModel(titulo,0);
        for (StockMinimoData stockMinimo : listaData) {
            
            String[] datos=
            {
                stockMinimo.getCodigo(),
                stockMinimo.getProducto(),
                stockMinimo.getCategoria(),
                stockMinimo.getUbicacion(),
                stockMinimo.getStock(),
                stockMinimo.getCantidadMinima(),
                stockMinimo.getCosto()
            };
            
            modeloTabla.addRow(datos);
        }
        
        getTblDato().setModel(modeloTabla);        
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
    
}
