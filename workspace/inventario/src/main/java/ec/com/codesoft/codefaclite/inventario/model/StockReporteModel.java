/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.TallerMecanicoInventarioBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.CategoriaProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.PresentacionPrecioData;
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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoPresentacionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SegmentoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TipoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoUbicacionEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoStockEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.orden.KardexOrdenarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesImpuestos;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSistema;
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
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author Carlos
 */
public class StockReporteModel extends StockMinimoPanel{
    
    private static final int COLUMNA_STOCK=8;
    
    private List<Object[]> listaStock;
    private List<StockMinimoData> listaData;
    private Bodega bodegaSeleccionada;
    
    protected CategoriaProducto categoriaProducto;
    

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        listenerBotones();
        listenerCheckBox();
        listenerJPopupMenu();
        valoresIniciales();
        ocultarComponentes();
        setTitle("Reporte Stock");
    }
    
    public void ocultarComponentes()
    {
    
    }
    
    private void listenerJPopupMenu()
    {
        JPopupMenu jpopMenuItem=new JPopupMenu();
        JMenuItem itemMenu= new JMenuItem("Eliminar Kardex");
        new CodefacMsj("", CodefacMsj.TipoMensajeEnum.ADVERTENCIA);
        itemMenu.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                int filaSeleccionada=getTblDato().getSelectedRow();
                if(filaSeleccionada>=0)
                {
                    //Preguntar si desea eliminar el kardex que ingrese la clave de soporte
                    String claveIngresada = DialogoCodefac.mensajeTextoIngreso(MensajeCodefacSistema.IngresoInformacion.INGRESO_CLAVE_CODEFAC);
                    if (!UtilidadesSistema.verificarClaveSoporte(claveIngresada)) 
                    {
                        DialogoCodefac.mensaje(MensajeCodefacSistema.IngresoInformacion.MENSAJE_CLAVE_INCORRECTA);
                        return;
                    }
                    
                    //Eliminar los KARDEX POR EL ID de la tabla seleccionada
                    try {                        
                        StockMinimoData stockMinimoData=(StockMinimoData) getTblDato().getValueAt(filaSeleccionada,0);
                        ServiceFactory.getFactory().getKardexServiceIf().eliminarPorId(stockMinimoData.getKardexId());
                        DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.ELIMINADO_CORRECTAMENTE);
                        
                        generarConsulta();
                    } catch (RemoteException ex) {
                        Logger.getLogger(StockReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(StockReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                        DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                    }
                    
                    
                }
                
            }
        });
        
        jpopMenuItem.add(itemMenu);
        getTblDato().setComponentPopupMenu(jpopMenuItem);
        
    }
    
    //TODO: Unificar el funcionamiento con la pantalla de Stock minimo por que son bastante similares
    public void valoresIniciales() {
        try {                       
            
            UtilidadesComboBox.llenarComboBox(getCmbTipoStock(),TipoStockEnum.values());
            UtilidadesComboBox.llenarComboBox(getCmbUbicacion(),TipoUbicacionEnum.values());
            UtilidadesComboBox.llenarComboBox(getCmbOrdenar(), KardexOrdenarEnum.values());
            UtilidadesComboBox.llenarComboBox(getCmbTipoReporte(),TipoReporteStockEnum.values());
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
            
            //Cargar los datos de las presentaciones
            getCmbMostrarPresentaciones().removeAllItems();
            getCmbMostrarPresentaciones().addItem(EnumSiNo.NO);
            getCmbMostrarPresentaciones().addItem(EnumSiNo.SI);
            
            //Cargar los datos para saber si se debe incluir el iva en el reporte
            getCmbIncluirIva().removeAllItems();            
            getCmbIncluirIva().addItem(EnumSiNo.SI);
            getCmbIncluirIva().addItem(EnumSiNo.NO);
            
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
        
        try {
            Map<Long,BigDecimal> datosModificados= StockMinimoData.obtenerKardexModificadoStock(listaData);
            
            if(datosModificados.size()==0)
            {
                throw new ExcepcionCodefacLite("No existen datos para modificar el Stock");
            }
            
            Boolean continuar=DialogoCodefac.dialogoPregunta(new CodefacMsj("Está seguro de que quiere actualizar el STOCK de "+datosModificados.size()+" productos ?", CodefacMsj.TipoMensajeEnum.CORRECTO));
            if(!continuar)
            {
                throw new ExcepcionCodefacLite("Proceso cancelado ...");
            }
            
            ServiceFactory.getFactory().getKardexServiceIf().actualizarKardexLote(datosModificados,session.getUsuario());
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(StockReporteModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ERROR));            
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
        
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
                        //String nombreCabeceras[] = {"Código","Codigo2","Lote","Bodega","Producto","Marca","Categoria","Aplicación","Ubicación","Iva","Stock","Pvp1" ,"Cantidad Min","Costo","´Último Costo","Utilidad"};
                        excel.gestionarIngresoInformacionExcel(StockMinimoData.NOMBRE_CABECERA_EXCEL,listaData);
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
                    
                    JasperReport reportDatosAdicionales= obtenerSubReporte("stockGarantiaDetalle.jrxml");
                    JasperReport reportPresentacion= obtenerSubReporte("detallePresentaciones.jrxml");
                    Map<String,Object> mapParametros=new HashMap<String,Object>();
                    mapParametros.put("pl_detalle_item",reportDatosAdicionales);
                    mapParametros.put("pl_presentacion_list",reportPresentacion);
                    TipoReporteStockEnum tipoReporteStockEnum=(TipoReporteStockEnum) getCmbTipoReporte().getSelectedItem();
                    ReporteCodefac.generarReporteInternalFramePlantilla(RecursoCodefac.JASPER_INVENTARIO,tipoReporteStockEnum.getNombreReporte(), mapParametros, listaData, panelPadre, "Reporte Stock", OrientacionReporteEnum.HORIZONTAL, FormatoHojaEnum.A4);
                    //ReporteCodefac.generarReporteInternalFramePlantilla(path,mapParametros, listaData, panelPadre, "Reporte Stock");
                    //dispose();
                    //setVisible(false);
                }
            });
        
    }
    
    private JasperReport obtenerSubReporte(String nombreReporte)
    {
        BigDecimal b1=BigDecimal.ONE;
        b1.multiply(b1);
        InputStream inputStream = null;
        try {
            RecursosServiceIf service = ServiceFactory.getFactory().getRecursosServiceIf();
            inputStream = RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER_INVENTARIO, nombreReporte));
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
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
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
            generarConsulta();
        }
    };
    
    private void generarConsulta()
    {
        try {
            Bodega bodegaSeleccionada = (Bodega) getCmbBodega().getSelectedItem();

            if (getChkTodasBodega().isSelected()) {
                bodegaSeleccionada = null;
            }

            TipoProducto tipoSeleccionada = (TipoProducto) getCmbTipo().getSelectedItem();
            if (getChkTodosTipo().isSelected()) {
                tipoSeleccionada = null;
            }

            SegmentoProducto segmentoProducto = (SegmentoProducto) getCmbSegmento().getSelectedItem();
            if (getChkTodosSegmento().isSelected()) {
                segmentoProducto = null;
            }

            String nombreProducto = getTxtNombreProducto().getText();
            if (UtilidadesTextos.verificarNullOVacio(nombreProducto)) {
                //Si no tiene nada ingresado envio un null
                nombreProducto = null;
            } else {
                //Si quiere poner parte de un nombre pongo los porcentajes para que pueda buscar en cualquier lugar
                nombreProducto = "%" + nombreProducto + "%";
            }

            String codigoProducto = getTxtCodigoProducto().getText();

            if (UtilidadesTextos.verificarNullOVacio(codigoProducto)) {
                codigoProducto = null;
            }

            KardexOrdenarEnum ordenarEnum = (KardexOrdenarEnum) getCmbOrdenar().getSelectedItem();
            TipoStockEnum tipoStockEnum = (TipoStockEnum) getCmbTipoStock().getSelectedItem();
            TipoUbicacionEnum tipoUbicacionEnum = (TipoUbicacionEnum) getCmbUbicacion().getSelectedItem();
            listaStock = ServiceFactory.getFactory().getKardexServiceIf().consultarStock(bodegaSeleccionada, nombreProducto, codigoProducto, categoriaProducto, tipoSeleccionada, segmentoProducto, session.getEmpresa(), ordenarEnum, tipoStockEnum, tipoUbicacionEnum);

            listaData = new ArrayList<StockMinimoData>();

            for (Object[] objeto : listaStock) {
                Producto producto = (Producto) objeto[0];
                BigDecimal cantidad = (BigDecimal) objeto[1];
                BigDecimal costoPromedio = (BigDecimal) objeto[2];
                Bodega bodega = (Bodega) objeto[3];
                Lote lote = (Lote) objeto[4];
                BigDecimal ultimoCosto = (BigDecimal) objeto[5];
                BigDecimal reserva = (BigDecimal) objeto[6];
                Long kardexId = (Long) objeto[7];

                if (reserva == null) {
                    reserva = BigDecimal.ZERO;
                }

                //Aumentar el valor del iva en los precios, costos y utilidades
                EnumSiNo incluyeIvaEnum = (EnumSiNo) getCmbIncluirIva().getSelectedItem();

                BigDecimal valorUnitario = producto.getValorUnitario();
                if (incluyeIvaEnum != null && incluyeIvaEnum.equals(EnumSiNo.SI)) {
                    //Obtener el valor unitario pero con iva
                    valorUnitario = producto.getValorUnitarioConIva();

                    //Verificar si el costo le tengo que poner con iva
                    costoPromedio = UtilidadesImpuestos.agregarValorIva(producto.getTarifaIva(), costoPromedio);
                    costoPromedio.setScale(2, RoundingMode.HALF_UP);

                    //Agregar el iva al último costo
                    ultimoCosto = UtilidadesImpuestos.agregarValorIva(producto.getTarifaIva(), ultimoCosto);
                    ultimoCosto = ultimoCosto.setScale(2, RoundingMode.HALF_UP);

                }

                //Kardex kardexTemp = (Kardex) objeto[2];
                /*if(producto==null)
                        {
                            System.err.println("Error con un producto nulo en kardeId="+kardexTemp.getId());
                            continue;
                        }*/
                StockMinimoData data = new StockMinimoData();

                String codigoPersonalizado = "Sin Código";
                if (producto.getCodigoPersonalizado() != null) {
                    codigoPersonalizado = producto.getCodigoPersonalizado();
                }
                //System.out.println(producto.getNombre());
                data.setKardexId(kardexId);
                data.setCodigo(codigoPersonalizado);
                data.setCodigo2((producto.getCodigoUPC() != null) ? producto.getCodigoUPC() : "");
                data.setProducto(producto.getNombre());
                data.setStock(cantidad.setScale(obtenerCantidadDecimales(), RoundingMode.HALF_UP) + "");
                data.setReserva(reserva.setScale(obtenerCantidadDecimales(), RoundingMode.HALF_UP));

                data.setCategoria((producto.getCatalogoProducto().getCategoriaProducto() != null) ? producto.getCatalogoProducto().getCategoriaProducto().getNombre() : "");
                data.setUbicacion(producto.getUbicacion());
                if (producto.getCantidadMinima() != null) {
                    data.setCantidadMinima(producto.getCantidadMinima().toString());
                } else {
                    data.setCantidadMinima("0");
                }
                data.setCosto(costoPromedio.toString());

                if (ultimoCosto != null) {
                    data.setUltimoCosto(ultimoCosto.setScale(2, RoundingMode.HALF_UP) + "");

                }
                data.setBodega(bodega.getNombre());
                data.setLote((lote != null) ? lote.getCodigo() : "");
                //data.setPvp1(producto.getValorUnitario().setScale(2, RoundingMode.HALF_UP));
                //data.setUtilidad1(producto.getValorUnitario().subtract(costoPromedio).setScale(2,RoundingMode.HALF_UP));
                data.setPvp1(valorUnitario.setScale(2, RoundingMode.HALF_UP));
                data.setUtilidad1(valorUnitario.subtract(costoPromedio).setScale(2, RoundingMode.HALF_UP));

                data.setAplicacion(producto.getAplicacionProducto());
                data.setTipo(producto.getTipoProducto());
                data.setSegmento(producto.getSegmentoProducto());
                data.setMarca((producto.getMarcaProducto() != null) ? producto.getMarcaProducto().getNombre() : "");
                data.setIvaPorcentaje(producto.getCatalogoProducto().getIva().getTarifa());

                //Agregar los detalles adicional cuando el producto tiene garantia
                if (producto.getGarantiaEnum().equals(EnumSiNo.SI) && getCmbMostrarDetalle().getSelectedItem().equals(EnumSiNo.SI)) {

                    List<KardexItemEspecifico> kardeItemEspecificoList = ServiceFactory.getFactory().getItemEspecificoServiceIf().obtenerItemsEspecificosPorProducto(producto);
                    for (KardexItemEspecifico kardexItemEspecifico : kardeItemEspecificoList) {
                        StockUnicoData stockUnicoData = new StockUnicoData(kardexItemEspecifico.getCodigoEspecifico());
                        data.agregarDetalle(stockUnicoData);
                    }

                }

                //Agregar las presentaciones en el reporte
                if (getCmbMostrarPresentaciones().getSelectedItem().equals(EnumSiNo.SI)) {
                    List<ProductoPresentacionDetalle> resultadoList = producto.getPresentacionList();
                    for (ProductoPresentacionDetalle resultado : resultadoList) {
                        if (resultado.getPresentacionProducto() != null) {
                            PresentacionPrecioData presentacionData = new PresentacionPrecioData();
                            presentacionData.setNombre(resultado.getPresentacionProducto().getNombre());
                            presentacionData.setCosto(resultado.getCantidad().multiply(ultimoCosto).setScale(2, RoundingMode.HALF_UP) + "");
                            presentacionData.setPvp(resultado.getCantidad().multiply(valorUnitario).setScale(2, RoundingMode.HALF_UP) + "");
                            presentacionData.setStock(cantidad.divide(resultado.getCantidad(), 2, RoundingMode.HALF_UP) + "");
                            data.agregarPresentacion(presentacionData);
                        }
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
    
    private Integer obtenerCantidadDecimales()
    {
        //Por defecto si no tiene un valor redondea al numero de decimales
        Integer decimalesCantidadRedondear = null;
        decimalesCantidadRedondear = ParametroUtilidades.obtenerValorBaseDatos(session.getEmpresa(), ParametroCodefac.NUMERO_DECIMAL_PRODUCTO, new ParametroUtilidades.ComparadorInterface() {
            @Override
            public Object consultarParametro(String nombreParametro) {
                return Integer.parseInt(nombreParametro);
            }
        });
        
        if(decimalesCantidadRedondear==null)
        {
            decimalesCantidadRedondear=2;
        }
        
        return decimalesCantidadRedondear;
    }
    
    
    public void construirTabla(List<StockMinimoData> listaData)
    {
        String[] titulo={
            "",
            "Código",
            "Lote",
            "Bodega",
            "Producto",
            "Marca",
            "Categoria",
            "Ubicación",
            "Stock",
            "Reserva",
            "Pvp1",
            "Cantidad Minima",
            "Último Costo",
            "Costo Promedio",
            "Utilidad"
        };
        
        DefaultTableModel modeloTabla=new DefaultTableModel(titulo,0);
        for (StockMinimoData stockMinimo : listaData) {
            
            Object[] datos=
            {
                stockMinimo,
                stockMinimo.getCodigo(),
                stockMinimo.getLote(),
                stockMinimo.getBodega(),
                stockMinimo.getProducto(),
                stockMinimo.getMarca(),
                stockMinimo.getCategoria(),
                stockMinimo.getUbicacion(),
                stockMinimo.getStock(),
                stockMinimo.getReserva()+"",
                stockMinimo.getPvp1()+"",
                stockMinimo.getCantidadMinima(),
                stockMinimo.getUltimoCosto()+"",
                stockMinimo.getCosto(),
                stockMinimo.getUtilidad1()+""
            };
            
            modeloTabla.addRow(datos);
        }
        
        getTblDato().setModel(modeloTabla);       
        
        UtilidadesTablas.ocultarColumna(getTblDato(),0);
        
        modeloTabla.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {                
                int columnaModificada = e.getColumn();
                int filaModificada = e.getFirstRow();

                if (filaModificada < 0 || columnaModificada < 0) //Si no existe ninguna fila seleccionada no ejecuta ninguna accion 
                    return;
                
                Object datoModificado=modeloTabla.getValueAt(filaModificada, columnaModificada);
                
                if(columnaModificada==COLUMNA_STOCK)
                {
                    StockMinimoData objeto=(StockMinimoData) modeloTabla.getValueAt(filaModificada,0);
                    try
                    {
                        BigDecimal stockNumero=new BigDecimal(datoModificado+"");
                        objeto.setStock(stockNumero+"");
                        objeto.setActualizarStockTmp(true);
                    }
                    catch(NumberFormatException  nfe)
                    {
                        Logger.getLogger(GestionInventarioModel.class.getName()).log(Level.INFO,"Erro al ingresar el stock con el formato del numero");
                    }
                }
                
                
            }
        });
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
    
    public enum TipoReporteStockEnum
    {
        DETALLADO("stockMinimo.jrxml"),
        SIMPLE("stockMinimoModelo2.jrxml");
        
        private String nombreReporte;

        private TipoReporteStockEnum(String nombreReporte) {
            this.nombreReporte = nombreReporte;
        }

        public String getNombreReporte() {
            return nombreReporte;
        }
        
        
    }
    
}
