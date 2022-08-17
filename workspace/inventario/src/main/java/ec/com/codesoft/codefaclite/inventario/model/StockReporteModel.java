/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.inventario.busqueda.CategoriaProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.StockMinimoData;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.StockUnicoData;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.inventario.panel.StockMinimoPanel;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexItemEspecifico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SegmentoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TipoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author Carlos
 */
public class StockReporteModel extends StockMinimoPanel{
    
    private List<Object[]> listaStock;
    private List<StockMinimoData> listaData;
    private Bodega bodegaSeleccionada;
    
    protected CategoriaProducto categoriaProducto;
    

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        listenerBotones();
        listenerCheckBox();
        valoresIniciales();
        ocultarComponentes();
        setTitle("Reporte Stock");
    }
    
    public void ocultarComponentes()
    {
    
    }
    
    private void valoresIniciales() {
        try {                       
            
            //Obtener bodega por defecto seleccionada
            bodegaSeleccionada=ServiceFactory.getFactory().getBodegaServiceIf().obtenerUnicaBodegaPorSucursal(session.getSucursal());
            //getCmbBodega().removeAllItems();
            BodegaServiceIf servicioBodega = ServiceFactory.getFactory().getBodegaServiceIf();
            List<Bodega> bodegas = servicioBodega.obtenerActivosPorEmpresa(session.getEmpresa());
            UtilidadesComboBox.llenarComboBox(getCmbBodega(), bodegas);
            getCmbBodega().setSelectedItem(bodegaSeleccionada);
            //for (Bodega bodega : bodegas) {
            //    getCmbBodega().addItem(bodega);                
            //}
            //Por defecto aparece desctiva para que buque todas las categorias
            getChkTodasCategoria().setSelected(true);
            getBtnBuscarCategoria().setEnabled(false);
            
            //cargar los tipos de productos
            List<TipoProducto> tipoProductoList=ServiceFactory.getFactory().getTipoProductoServiceIf().obtenerActivosPorEmpresa(session.getEmpresa());
            UtilidadesComboBox.llenarComboBox(getCmbTipo(), tipoProductoList);
            
            //cargar los segmentos de los productos
            List<SegmentoProducto> segmentoProductoList=ServiceFactory.getFactory().getSegmentoProductoServiceIf().obtenerActivosPorEmpresa(session.getEmpresa());
            UtilidadesComboBox.llenarComboBox(getCmbSegmento(), segmentoProductoList);
            
            //Cargar los datos del enum de los detalles
            getCmbMostrarDetalle().removeAllItems();
            getCmbMostrarDetalle().addItem(EnumSiNo.NO);
            getCmbMostrarDetalle().addItem(EnumSiNo.SI);
            
        } catch (RemoteException ex) {
            Logger.getLogger(GestionInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(StockMinimoModel.class.getName()).log(Level.SEVERE, null, ex);
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
        
        //InputStream path = RecursoCodefac.JASPER_INVENTARIO.getResourceInputStream("stockMinimo.jrxml");
        
        DialogoCodefac.dialogoReporteOpciones( new ReporteDialogListener() {
                @Override
                public void excel() {
                    try{
                        Excel excel = new Excel();
                        String nombreCabeceras[] = {"Código","Lote","Bodega","Producto","Categoria","Ubicación", "Stock","Pvp1" ,"Cantidad Min","Costo","Utilidad"};
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
                    
                    JasperReport reportDatosAdicionales= obtenerSubReporte();
                    Map<String,Object> mapParametros=new HashMap<String,Object>();
                    mapParametros.put("pl_detalle_item",reportDatosAdicionales);
                    ReporteCodefac.generarReporteInternalFramePlantilla(RecursoCodefac.JASPER_INVENTARIO,"stockMinimo.jrxml", mapParametros, listaData, panelPadre, "Reporte Stock", OrientacionReporteEnum.HORIZONTAL, FormatoHojaEnum.A4);
                    //ReporteCodefac.generarReporteInternalFramePlantilla(path,mapParametros, listaData, panelPadre, "Reporte Stock");
                    //dispose();
                    //setVisible(false);
                }
            });
        
    }
    
    private JasperReport obtenerSubReporte()
    {
        InputStream inputStream = null;
        try {
            RecursosServiceIf service = ServiceFactory.getFactory().getRecursosServiceIf();
            inputStream = RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER_INVENTARIO, "stockGarantiaDetalle.jrxml"));
            JasperReport reportDatosAdicionales = JasperCompileManager.compileReport(inputStream);
            return reportDatosAdicionales;
        } catch (RemoteException ex) {
            Logger.getLogger(StockReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StockReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(StockReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(StockReporteModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
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
        
        getBtnBuscar().addActionListener(listenerBuscarReporte);
    }
    
    private ActionListener listenerBuscarReporte=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                    Bodega bodegaSeleccionada=(Bodega) getCmbBodega().getSelectedItem();             
                    
                    if(getChkTodasBodega().isSelected())
                    {
                        bodegaSeleccionada=null;
                    }
                    
                    TipoProducto tipoSeleccionada=(TipoProducto) getCmbTipo().getSelectedItem();
                    if(getChkTodosTipo().isSelected())
                    {
                        tipoSeleccionada=null;
                    }
                    
                    SegmentoProducto segmentoProducto=(SegmentoProducto) getCmbSegmento().getSelectedItem();
                    if(getChkTodosSegmento().isSelected())
                    {
                        segmentoProducto=null;
                    }
                    
                    String nombreProducto=getTxtNombreProducto().getText();
                    if(UtilidadesTextos.verificarNullOVacio(nombreProducto))
                    {
                        //Si no tiene nada ingresado envio un null
                        nombreProducto=null;
                    }
                    else
                    {
                        //Si quiere poner parte de un nombre pongo los porcentajes para que pueda buscar en cualquier lugar
                        nombreProducto="%"+nombreProducto+"%";
                    }
                    
                    listaStock=ServiceFactory.getFactory().getKardexServiceIf().consultarStock(bodegaSeleccionada,nombreProducto,categoriaProducto,tipoSeleccionada,segmentoProducto,session.getEmpresa());
                    
                    listaData=new ArrayList<StockMinimoData>();
                    
                     for (Object[] objeto : listaStock) 
                     {
                        Producto producto = (Producto) objeto[0];
                        BigDecimal cantidad = (BigDecimal) objeto[1];
                        BigDecimal costoPromedio=(BigDecimal)objeto[2];
                        Bodega bodega=(Bodega)objeto[3];
                        Lote lote=(Lote)objeto[4];
                        
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
                        //System.out.println(producto.getNombre());
                        data.setCodigo(codigoPersonalizado);
                        data.setProducto(producto.getNombre());
                        data.setStock(cantidad.setScale(obtenerCantidadDecimales(), RoundingMode.HALF_UP)+"");
                        
                        data.setCategoria((producto.getCatalogoProducto().getCategoriaProducto()!=null)?producto.getCatalogoProducto().getCategoriaProducto().getNombre():"");
                        data.setUbicacion(producto.getUbicacion());
                        if(producto.getCantidadMinima()!=null)
                        {
                            data.setCantidadMinima(producto.getCantidadMinima().toString());
                        }
                        else
                        {
                            data.setCantidadMinima("0");
                        }
                        data.setCosto(costoPromedio.toString());
                        data.setBodega(bodega.getNombre());
                        data.setLote((lote!=null)?lote.getCodigo():"");
                        data.setPvp1(producto.getValorUnitario().setScale(2, RoundingMode.HALF_UP));
                        data.setUtilidad1(producto.getValorUnitario().subtract(costoPromedio).setScale(2,RoundingMode.HALF_UP));
                        
                        data.setAplicacion(producto.getAplicacionProducto());
                        data.setTipo(producto.getTipoProducto());
                        data.setSegmento(producto.getSegmentoProducto());
                        data.setMarca((producto.getMarcaProducto()!=null)?producto.getMarcaProducto().getNombre():"");
                        
                        //Agregar los detalles adicional cuando el producto tiene garantia
                        if(producto.getGarantiaEnum().equals(EnumSiNo.SI) && getCmbMostrarDetalle().getSelectedItem().equals(EnumSiNo.SI))
                        {
                            
                            List<KardexItemEspecifico> kardeItemEspecificoList=ServiceFactory.getFactory().getItemEspecificoServiceIf().obtenerItemsEspecificosPorProducto(producto);
                            for (KardexItemEspecifico kardexItemEspecifico : kardeItemEspecificoList) {
                                StockUnicoData stockUnicoData=new StockUnicoData(kardexItemEspecifico.getCodigoEspecifico());                                
                                data.agregarDetalle(stockUnicoData);
                            }
                                    
                        }
                        
                        listaData.add(data);                        
                    }
                     
                     construirTabla(listaData);
                    

                    
                } catch (RemoteException ex) {
                    Logger.getLogger(StockReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                Logger.getLogger(StockReporteModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
    private Integer obtenerCantidadDecimales()
    {
        //Por defecto si no tiene un valor redondea al numero de decimales
        Integer decimalesCantidadRedondear = null;
        try {
            decimalesCantidadRedondear = ParametroUtilidades.obtenerValorBaseDatos(session.getEmpresa(), ParametroCodefac.NUMERO_DECIMAL_PRODUCTO, new ParametroUtilidades.ComparadorInterface() {
                @Override
                public Object consultarParametro(String nombreParametro) {
                    return Integer.parseInt(nombreParametro);
                }
            });
        } catch (RemoteException ex) {
            Logger.getLogger(KardexModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(decimalesCantidadRedondear==null)
        {
            decimalesCantidadRedondear=2;
        }
        
        return decimalesCantidadRedondear;
    }
    
    
    public void construirTabla(List<StockMinimoData> listaData)
    {
        String[] titulo={
            "Código",
            "Lote",
            "Bodega",
            "Producto",
            "Categoria",
            "Ubicación",
            "Stock",
            "Pvp1",
            "Cantidad Minima",
            "Costo",
            "Utilidad"
        };
        
        DefaultTableModel modeloTabla=new DefaultTableModel(titulo,0);
        for (StockMinimoData stockMinimo : listaData) {
            
            String[] datos=
            {
                stockMinimo.getCodigo(),
                stockMinimo.getLote(),
                stockMinimo.getBodega(),
                stockMinimo.getProducto(),
                stockMinimo.getCategoria(),
                stockMinimo.getUbicacion(),
                stockMinimo.getStock(),
                stockMinimo.getPvp1()+"",
                stockMinimo.getCantidadMinima(),
                stockMinimo.getCosto(),
                stockMinimo.getUtilidad1()+""
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
