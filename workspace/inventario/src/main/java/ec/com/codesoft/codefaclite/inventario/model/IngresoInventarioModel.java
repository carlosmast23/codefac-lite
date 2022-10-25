/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.dialogos.DialogoFecha;
import ec.com.codesoft.codefaclite.controlador.dialogos.DialogoFechaPanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.inventario.busqueda.CompraBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.auxiliar.KardexDetalleTmp;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.auxiliar.KardexItemEspecificoTemp;
import ec.com.codesoft.codefaclite.inventario.panel.IngresoInventarioPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexItemEspecifico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoPresentacionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.ProductoConversionPresentacionRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesCodigos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.JXComboBox;

/**
 *
 * @author Carlos
 */
public class IngresoInventarioModel extends IngresoInventarioPanel {

    /**
     * Variable temporal para solo generar los detalles del kardex a generar
     */
    private List<KardexDetalle> detallesKardexFinal;
    /**
     * Listado de todos los detalles de los kardex que se crearon
     */
    private Map<KardexDetalleTmp, CompraDetalle> detallesKardex;
    
    /**
     * Referencia de la compra que se carga para agregar al inventario
     */
    private Compra compraInventario;
    
    /**
     * Combo box que me permite servir de modelo para que puedan seleccionar en las tablas
     */
    private JComboBox<Bodega> cmbBodegaSeleccion;
    
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
      listenerBotones();
      listenerTablas();
      listenerCombos();
      valoresIniciales();
      agregarPopUps();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            int datosNoSeleccionados=generarKardexDetalleSeleccionados();
            
            if(datosNoSeleccionados>0)
            {
                boolean respuesta=DialogoCodefac.dialogoPregunta("Advertencia","Hay datos que no esta seleccionados para el ingreso. \nDesea continuar de todos modos?",DialogoCodefac.MENSAJE_ADVERTENCIA);
                    if(!respuesta)
                    {
                        throw new  ExcepcionCodefacLite("Cancelado por el usuario");
                    }
            }
            
            if (validarKardexGrabar()) //Validación para ver que todos los datos esten ingresados para grabar
            {
                ServiceFactory.getFactory().getKardexServiceIf().ingresarInventario(detallesKardexFinal);
                
                DialogoCodefac.mensaje("Correcto", "El producto fue ingresado correctamente", DialogoCodefac.MENSAJE_CORRECTO);
                verificarActualizarPreciosVenta();
            }
            else
            {
                throw new ExcepcionCodefacLite("Cancelado guardar por el usuario");
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(IngresoInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            ex.printStackTrace();
            DialogoCodefac.mensaje("Error", ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("Cancelado guardar por el usuario");
        }

    }   
    
    /**
     * Metodo que me permite saber si se quieren actualizar los precios de venta por que se han modificado los costos
     */
    private void verificarActualizarPreciosVenta()
    {       
        try {
            //Verificar si tiene permisos para actualizar los costos
            if (ParametroUtilidades.comparar(session.getEmpresa(), ParametroCodefac.ADVERTENCIA_ACTUALIZAR_COSTO, EnumSiNo.SI)) 
            {
                List<Producto> productoList= ServiceFactory.getFactory().getCompraServiceIf().obtenerProductosActualizarPrecios(compraInventario);
                if(productoList.size()>0)
                {
                    Boolean respuesta=DialogoCodefac.dialogoPregunta(new CodefacMsj("La compra tiene cambios en los costos, desea actualizar los precios de venta ?", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                    if(respuesta)
                    {
                        Object[] parametros = {productoList};
                        panelPadre.crearVentanaCodefac(VentanaEnum.UTILIDAD_PRECIO, true, parametros);
                    }
                }
            }
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(IngresoInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(IngresoInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
      getTblCompra().setModel(new DefaultTableModel());
      getTblGarantiaProductos().setModel(new DefaultTableModel());
      getTxtCompraSeleccionada().setText("");
      crearCabeceraComboBox();
      getCmbFechaIngreso().setDate(new Date());
      compraInventario=null;
      detallesKardex=null;
      detallesKardexFinal=null;
      
    }

//    @Override
    public String getNombre() {
        return "Ingreso Inventario";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, false);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, false);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, false);
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

    private void listenerBotones() {
        getBtnBuscarCompraPendiente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                CompraBusquedaDialogo buscarBusquedaDialogo = new CompraBusquedaDialogo(session.getEmpresa());
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                compraInventario = (Compra) buscarDialogo.getResultado();
                if (compraInventario != null) {
                    //Imprimir un formato el campo de texto para saber cual es la compra seleccionada
                    String preimpreso = compraInventario.getPreimpreso();
                    String proveedor = compraInventario.getProveedor().getRazonSocial();
                    getTxtCompraSeleccionada().setText(preimpreso + " - " + proveedor);
                    
                    cargarKardexDetalleCompra();
                    construirTablaDetalleCompra();
                }
            }
        });
        
        getBtnGenerarCodigosAutomaticos().addActionListener(listenerGenerarCodigosAutomaticos);
    }
    
    
    private ActionListener listenerGenerarCodigosAutomaticos=new ActionListener() 
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            int filaModificada=getTblCompra().getSelectedRow();
            DefaultTableModel tablaModelo=(DefaultTableModel) getTblCompra().getModel();
            KardexDetalleTmp kardexDetalle=(KardexDetalleTmp)tablaModelo.getValueAt(filaModificada,ColumnaDetalleCompraEnum.COLUMNA_KARDEX.numero);
            
            for (KardexItemEspecifico detallesEspecifico : kardexDetalle.getDetallesEspecificos()) 
            {
                String codigoUnico=UtilidadesCodigos.generarCodigoUnicoUUID();
                detallesEspecifico.setCodigoEspecifico(codigoUnico);
            }
            
            construirTablaProductosConGarantia(kardexDetalle);
            
        }
    };
    
    /**
     * Crear los detalles de los kardex individuales 
     */
    private void cargarKardexDetalleCompra()
    {
        detallesKardex=new HashMap<KardexDetalleTmp,CompraDetalle>();
         
        List<CompraDetalle> detalles=compraInventario.getDetalles();
        for (CompraDetalle detalle : detalles) {
            KardexDetalleTmp kardexDetalle = new KardexDetalleTmp();
            kardexDetalle.setId(detalle.getId());
            kardexDetalle.setPuntoEstablecimiento(compraInventario.getPuntoEstablecimiento().toString());
            kardexDetalle.setPuntoEmision(compraInventario.getPuntoEmision().toString());
            kardexDetalle.setSecuencial(compraInventario.getSecuencial());
            
            kardexDetalle.setCantidad(detalle.getCantidad()); //TODO: ESTA PARTE SE DEBE MEJORAR PARA QUE EL KARDEX TERMINE CON VALORES DECIMALES
            kardexDetalle.setCodigoTipoDocumento(compraInventario.getCodigoTipoDocumento());
            kardexDetalle.setReferenciaDocumentoId(compraInventario.getId());
            kardexDetalle.setPrecioUnitario(detalle.getPrecioUnitario());
            kardexDetalle.setPrecioTotal(detalle.getTotal());
            kardexDetalle.setFechaIngreso(UtilidadesFecha.getFechaHoyTimeStamp()); //Setear por defecto con la fecha de hoy
            kardexDetalle.setFechaDocumento(UtilidadesFecha.getFechaHoy());
            
            kardexDetalle.seleccion=true;
                                    
            if (detalle.getProductoProveedor().getProducto().getGarantiaEnum().equals(EnumSiNo.SI)) {
                for (int i = 0; i < detalle.getCantidad().intValue(); i++) {
                    KardexItemEspecificoTemp item=new KardexItemEspecificoTemp();
                    item.setCodigoEspecifico("");
                    item.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                    item.setObservaciones("");
                    item.seleccion=true;
                    kardexDetalle.addDetalle(item);
                }
            }
            
            detallesKardex.put(kardexDetalle, detalle);


        }
        
    }
    
    private void construirTablaDetalleCompra()
    {
        Date fechaPorDefecto=getCmbFechaIngreso().getDate();
        
        String titulo[]={"","Ingresar","Bodega","Fecha","Descripcion","Presentación","Cantidad","Costo Unitario","Costo Total","garantia"};        
        Class clases[] = {
            KardexDetalleTmp.class,
            Boolean.class,
            Bodega.class,
            Date.class,
            String.class,
            String.class,
            String.class,
            String.class,
            String.class,
            String.class};
        
        DefaultTableModel modelTable=UtilidadesTablas.crearModeloTabla(titulo,clases,new Boolean[]{false,true,true,false,false,false,false,false,false,false});
        
        //Agregar los detalles a la tabla principal
        if(detallesKardex!=null)
        {
            for (Map.Entry<KardexDetalleTmp, CompraDetalle> entry : detallesKardex.entrySet()) 
            {
                KardexDetalleTmp kardexDetalle = entry.getKey();
                CompraDetalle compraDetalle = entry.getValue();
                Producto producto= compraDetalle.getProductoProveedor().getProducto();
                //producto.buscarProductoEmpaquePrincipal()
                //String presentacionStr=(producto.buscarPresentacionOriginal()!=null)?producto.buscarPresentacionOriginal().getNombre():"";
                String presentacionStr=(producto.buscarPresentacionProducto()!=null)?producto.buscarPresentacionProducto().getNombre():"";

                Object[] fila=
                {
                    kardexDetalle,
                    kardexDetalle.seleccion,
                    kardexDetalle.bodega,
                    kardexDetalle.getFechaIngreso(),
                    compraDetalle.getDescripcion(),
                    presentacionStr,
                    kardexDetalle.getCantidad(),
                    kardexDetalle.getPrecioUnitario(),
                    kardexDetalle.getPrecioTotal(),
                    compraDetalle.getProductoProveedor().getProducto().getGarantiaEnum().getNombre()
                };

                modelTable.addRow(fila);
            }
        }
        
        getTblCompra().setModel(modelTable);
        
        //Ocultar la primera columna
        UtilidadesTablas.ocultarColumna(getTblCompra(),0);
        
        Map<Integer, Integer> mapTamaniosTabla = new HashMap<Integer, Integer>();
        //mapTamaniosTabla.put(ColumnaDetalleCompraEnum.COLUMNA_INGRESAR.numero, 1);
        //mapTamaniosTabla.put(ColumnaDetalleCompraEnum.COLUMNA_CANTIDAD.numero, 1);
        //mapTamaniosTabla.put(ColumnaDetalleCompraEnum.COLUMNA_COSTO_UNITARIO.numero, 1);
        //mapTamaniosTabla.put(ColumnaDetalleCompraEnum.COLUMNA_COSTO_TOTAL.numero, 1);
        //mapTamaniosTabla.put(3, 150);
        //mapTamaniosTabla.put(ColumnaDetalleCompraEnum.COLUMNA_FECHA.numero, 10);
        //mapTamaniosTabla.put(ColumnaDetalleCompraEnum.COLUMNA_GARANTIA.numero, 1);

        
        UtilidadesTablas.definirTamanioColumnasPorMap(getTblCompra(),mapTamaniosTabla);
        
        //Modificado campo para poder agregar las bodegas como combobox en las tablas
        UtilidadesTablas.cambiarTipoColumna(getTblCompra(),ColumnaDetalleCompraEnum.COLUMNA_BODEGA.numero,cmbBodegaSeleccion);
        
        
        //Agregar listener al modelo
        modelTable.addTableModelListener(listenerDetalleCompra);
    }
    
    
    
    private TableModelListener listenerDetalleCompra=new TableModelListener() {
        @Override
        public void tableChanged(TableModelEvent e) {
            
            int filaModificada=e.getFirstRow();
            int columnaModificada=e.getColumn();
            
            DefaultTableModel tablaModelo=(DefaultTableModel) e.getSource();
            ColumnaDetalleCompraEnum enumerador=ColumnaDetalleCompraEnum.getEnum(columnaModificada);
            KardexDetalleTmp kardexDetalle=(KardexDetalleTmp)tablaModelo.getValueAt(filaModificada,ColumnaDetalleCompraEnum.COLUMNA_KARDEX.numero);
            
            CompraDetalle compraDetalle=detallesKardex.get(kardexDetalle);
            
            if(compraDetalle.getProductoProveedor().getProducto().getGarantiaEnum().equals(EnumSiNo.NO))
            {
                switch (enumerador) {
                    case COLUMNA_INGRESAR:
                        boolean seleccion=(boolean) tablaModelo.getValueAt(filaModificada,ColumnaDetalleCompraEnum.COLUMNA_INGRESAR.numero);
                        kardexDetalle.seleccion=seleccion;
                        break;
                        
                    case COLUMNA_BODEGA:
                        Bodega bodega=(Bodega) tablaModelo.getValueAt(filaModificada,ColumnaDetalleCompraEnum.COLUMNA_BODEGA.numero);
                        kardexDetalle.bodega=bodega;                        
                        break;

                    case COLUMNA_FECHA:
                        java.sql.Date fechaModificada=(java.sql.Date) tablaModelo.getValueAt(filaModificada,ColumnaDetalleCompraEnum.COLUMNA_FECHA.numero);
                        kardexDetalle.setFechaIngreso(UtilidadesFecha.castDateSqlToTimeStampSql(fechaModificada));
                        kardexDetalle.setFechaDocumento(UtilidadesFecha.castDateUtilToSql(fechaModificada));
                        break;
                }
                        
            }
            else
            {
                switch (enumerador) {
                    case COLUMNA_INGRESAR:
                        boolean seleccion=(boolean) tablaModelo.getValueAt(filaModificada,ColumnaDetalleCompraEnum.COLUMNA_INGRESAR.numero);
                        cambiarSeleccionItems(kardexDetalle.getDetallesEspecificos(),seleccion);
                        break;
                        
                    case COLUMNA_BODEGA:
                        Bodega bodega=(Bodega) tablaModelo.getValueAt(filaModificada,ColumnaDetalleCompraEnum.COLUMNA_BODEGA.numero);
                        kardexDetalle.bodega=bodega;          
                        construirTablaProductosConGarantia(kardexDetalle);
                        break;
                        
                    case COLUMNA_FECHA:
                        java.sql.Date fechaModificada = (java.sql.Date) tablaModelo.getValueAt(filaModificada, ColumnaDetalleCompraEnum.COLUMNA_FECHA.numero);
                        kardexDetalle.setFechaIngreso(UtilidadesFecha.castDateSqlToTimeStampSql(fechaModificada));
                        kardexDetalle.setFechaDocumento(UtilidadesFecha.castDateUtilToSql(fechaModificada));
                        break;
                }            
            }
            
            construirTablaProductosConGarantia(kardexDetalle);
        }
    };
    
    
    private void cambiarSeleccionItems(List<KardexItemEspecifico> listaItems,boolean seleccion)
    {
        if(listaItems!=null)
        {
            for (KardexItemEspecifico item : listaItems) {
                KardexItemEspecificoTemp itemTemp=(KardexItemEspecificoTemp) item;
                itemTemp.seleccion=seleccion;
            }
        }
    }
    
    private void construirTablaProductosConGarantia(KardexDetalle kardexDetalle)
    {

        String titulo[]={"","Ingresar","Bodega","Observación","CodigoUnitario"};        
        Class clases[] = {
            KardexItemEspecificoTemp.class,
            Boolean.class,
            Bodega.class,
            String.class,
            String.class};
        
        DefaultTableModel modelTable=UtilidadesTablas.crearModeloTabla(titulo,clases,new Boolean[]{false,true,true,true,true});
        
        if(kardexDetalle.getDetallesEspecificos()!=null)
        {
            for (KardexItemEspecifico item : kardexDetalle.getDetallesEspecificos()) {
                KardexItemEspecificoTemp itemTemp=(KardexItemEspecificoTemp) item;
                Object[] fila=
                {
                    itemTemp,
                    itemTemp.seleccion,
                    ((KardexDetalleTmp)item.getKardexDetalle()).bodega,
                    itemTemp.getObservaciones(),
                    itemTemp.getCodigoEspecifico()
                };
                modelTable.addRow(fila);
            }
        }
        
        getTblGarantiaProductos().setModel(modelTable);       
        
        //Ocultar la primera columna
        UtilidadesTablas.ocultarColumna(getTblGarantiaProductos(),0);
        
        //Modificado campo para poder agregar las bodegas como combobox en las tablas
        UtilidadesTablas.cambiarTipoColumna(getTblGarantiaProductos(),ColumnaGarantiaEnum.COLUMNA_BODEGA.numero,cmbBodegaSeleccion);
        
        modelTable.addTableModelListener(listenerGarantiaCompra);
    }
    
        
    private TableModelListener listenerGarantiaCompra=new TableModelListener() {
        @Override
        public void tableChanged(TableModelEvent e) {
            
            int filaModificada=e.getFirstRow();
            int columnaModificada=e.getColumn();
            
            DefaultTableModel tablaModelo=(DefaultTableModel) e.getSource();
            ColumnaGarantiaEnum enumerador=ColumnaGarantiaEnum.getEnum(columnaModificada);
            KardexItemEspecificoTemp kardexItem=(KardexItemEspecificoTemp)tablaModelo.getValueAt(filaModificada,ColumnaGarantiaEnum.COLUMNA_ITEM.numero);
            
            switch(enumerador)
            {
                case COLUMNA_INGRESAR:
                    boolean seleccion=(boolean) tablaModelo.getValueAt(filaModificada,columnaModificada);
                    kardexItem.seleccion=seleccion;
                    //recalcularCantidadKardexDetalle(kardexItem);
                    //System.out.println("seteado ingreso");
                    break;
                
                case COLUMNA_BODEGA:
                    Bodega bodega=(Bodega) tablaModelo.getValueAt(filaModificada,columnaModificada);
                    cambiarEstadoBodegaItemGarantia(kardexItem,bodega);
                    //TODO:falta implementar para guardar el campo bodega
                    break;
                    
                case COLUMNA_CODIGO_UNITARIO:
                    String codigoUnitario= tablaModelo.getValueAt(filaModificada,columnaModificada).toString();
                    kardexItem.setCodigoEspecifico(codigoUnitario);
                    System.out.println("seteado codigo unitario");
                    break;
                    
                case COLUMNA_OBSERVACION:
                    String observacion= tablaModelo.getValueAt(filaModificada,columnaModificada).toString();
                    kardexItem.setObservaciones(observacion);
                    System.out.println("seteado observacion");
                    break;
                
            }
        }
    };
    
    private void recalcularCantidadKardexDetalle(KardexItemEspecificoTemp item)
    {
        KardexDetalle KardexDetalle=item.getKardexDetalle();
        KardexDetalle.recalcularTotal();
    }
    
    
    private void cambiarEstadoBodegaItemGarantia(KardexItemEspecifico kardexItemEspecifico,Bodega bodega)
    {
        CompraDetalle compraDetalleTemp=detallesKardex.get(kardexItemEspecifico.getKardexDetalle());
        
        KardexDetalle kardeDetalleBuscando=null;
        for (Map.Entry<KardexDetalleTmp, CompraDetalle> entry : detallesKardex.entrySet()) 
        {
            KardexDetalleTmp kardexDetalle = entry.getKey();
            CompraDetalle compraDetalle = entry.getValue();
        
            //Buscar si existe una kardex detalle con la bodega y el producto correcto
            if(kardexDetalle.bodega==bodega && compraDetalleTemp.equals(compraDetalle))
            {
                kardeDetalleBuscando=kardexDetalle;
                        break;
            }
        
        }
        
        ////<---------- Agregar el item especifico al nuevo kardex detalle o si no existe lo creo <-------------///
        if(kardeDetalleBuscando!=null)
        {
            //Si pertenece a otro kardex detalle elimino del primero y agrego al segundo
            if(!kardeDetalleBuscando.equals(kardexItemEspecifico.getKardexDetalle()))
            {
               intercambiarItemsKardex(kardexItemEspecifico,kardeDetalleBuscando);
            }
            //Si la referencia del kardex detalle es el mismo no hago nada
        }
        else
        {
            try {
                KardexDetalle nuevoKardexDetalle=(KardexDetalle) kardexItemEspecifico.getKardexDetalle().clone(); //Copiar un nuevo kardex detalle
                nuevoKardexDetalle.setDetallesEspecificos(new ArrayList<KardexItemEspecifico>());
                
                KardexDetalleTmp nuevoKardexDetalleTmp=(KardexDetalleTmp) nuevoKardexDetalle;
                nuevoKardexDetalleTmp.seleccion=true;
                nuevoKardexDetalleTmp.bodega=bodega;
                
                intercambiarItemsKardex(kardexItemEspecifico,nuevoKardexDetalleTmp);
                
                detallesKardex.put(nuevoKardexDetalleTmp,compraDetalleTemp);                
                
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(IngresoInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
            }          
            
        }
        construirTablaDetalleCompra();
    }
    
    private void intercambiarItemsKardex(KardexItemEspecifico kardexItemEspecifico, KardexDetalle kardexDetalleNuevo)
    {
        //Elimino la referencia del padre actual
        KardexDetalle kardexDetalleAntiguo=kardexItemEspecifico.getKardexDetalle();
        kardexDetalleAntiguo.getDetallesEspecificos().remove(kardexItemEspecifico); 

        //Agrego a la nueva referencia al nuevo padre del kardex
        kardexDetalleNuevo.addDetalle(kardexItemEspecifico);
        kardexItemEspecifico.setKardexDetalle(kardexDetalleNuevo);
        
        /***
         * RECALCULAR LOS SALDOS DEL KARDEX ANTIGUO Y EL NUEVO 
         */
        kardexDetalleAntiguo.setCantidad(new BigDecimal(kardexDetalleAntiguo.getDetallesEspecificos().size()));
        kardexDetalleAntiguo.recalcularTotal();
        
        kardexDetalleNuevo.setCantidad(new BigDecimal(kardexDetalleNuevo.getDetallesEspecificos().size()));
        kardexDetalleNuevo.recalcularTotal();
        
        /**
         * VERIFICAR SI SE TIENE QUE ELIMINAR EL DETALLE KARDEX ANTIGUO SI YA NO TIENE ITEMS
         */
        
        if(kardexDetalleAntiguo.getDetallesEspecificos().size()==0)
        {
            detallesKardex.remove(kardexDetalleAntiguo);
        }
        
    }
    

    private void valoresIniciales() {
        getCmbFechaIngreso().setDate(new Date());
        
        try {
            //crear el combo box de las opciones de seleccion de la tabla
            cmbBodegaSeleccion = new JComboBox<Bodega>();

            getCmbBodega().removeAllItems();
            BodegaServiceIf servicioBodega = ServiceFactory.getFactory().getBodegaServiceIf();
            List<Bodega> bodegas = servicioBodega.obtenerActivosPorSucursal(session.getSucursal());
            for (Bodega bodega : bodegas) {
                getCmbBodega().addItem(bodega);
                cmbBodegaSeleccion.addItem(bodega); //Combo para la seleccion de la tabla
            }

            //No seleccionar ninguna opcion del combo box por defecto
            crearCabeceraComboBox();

        } catch (RemoteException ex) {
            Logger.getLogger(IngresoInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(IngresoInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
    private void crearCabeceraComboBox()
    {
        UtilidadesComboBox.crearCabeceraComboBox(getCmbBodega(),"Seleccione una bodega :");        
    }

    private void listenerTablas() {
        getTblCompra().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionado=getTblCompra().getSelectedRow();
                
                if(filaSeleccionado>=0)
                {
                    DefaultTableModel modeloTabla=(DefaultTableModel) getTblCompra().getModel();
                    KardexDetalle kardexDetalle=(KardexDetalle) modeloTabla.getValueAt(filaSeleccionado,ColumnaDetalleCompraEnum.COLUMNA_KARDEX.numero);
                    construirTablaProductosConGarantia(kardexDetalle);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    private void listenerCombos() {
        
        //Seleccionar todos los productos con la bodega por defecto
        
        getCmbBodega().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                //Validacion para evitar que no ingrese cuando no esta seleccionado una bodega
                if(getCmbBodega().getSelectedIndex()<0)
                {
                    return ;
                }
                
                Bodega bodegaSeleccionada=(Bodega) getCmbBodega().getSelectedItem();
                
                
                //Acceder solo si selecciona una bodega
                if(bodegaSeleccionada!=null && detallesKardex!=null)
                {
                    for (Map.Entry<KardexDetalleTmp, CompraDetalle> entry : detallesKardex.entrySet()) {
                        KardexDetalleTmp kardexDetalle = entry.getKey();
                        //CompraDetalle compraDetalle = entry.getValue();
                        kardexDetalle.bodega=bodegaSeleccionada;

                    }
                    construirTablaDetalleCompra();
                
                }
                
               
            }
        });
        
        getCmbFechaIngreso().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date fechaSeleccionada=getCmbFechaIngreso().getDate();
                //Acceder solo si selecciona una bodega
                if(fechaSeleccionada!=null && detallesKardex!=null)
                {
                    for (Map.Entry<KardexDetalleTmp, CompraDetalle> entry : detallesKardex.entrySet()) {
                        KardexDetalleTmp kardexDetalle = entry.getKey();
                        //CompraDetalle compraDetalle = entry.getValue();
                        kardexDetalle.setFechaIngreso(UtilidadesFecha.castDateSqlToTimeStampSql(fechaSeleccionada));
                        kardexDetalle.setFechaDocumento(UtilidadesFecha.castDateUtilToSql(fechaSeleccionada));

                    }
                    construirTablaDetalleCompra();
                
                }
            }
        });
        

    }
    
    private void agregarPopUps() 
    {
        JPopupMenu jpopMenu=new JPopupMenu();
        JMenuItem menuItem=new JMenuItem("Cambiar Fecha");
        
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                                
                int filaSeleccionada=getTblCompra().getSelectedRow();
                
                if(filaSeleccionada>=0)
                {
                    DefaultTableModel modeloTabla=(DefaultTableModel) getTblCompra().getModel();
                    DialogoFecha dialogoFecha = new DialogoFecha();
                    dialogoFecha.setVisible(true);
                    
                    if (dialogoFecha.getFechaResultado()!=null) 
                    {
                        java.sql.Date fechaNueva=new java.sql.Date(dialogoFecha.getFechaResultado().getTime());
                        //KardexDetalle kardexDetalle=(KardexDetalle) modeloTabla.getValueAt(filaSeleccionada,ColumnaDetalleCompraEnum.COLUMNA_KARDEX.numero);
                        //kardexDetalle.setFechaIngreso(fechaNueva);
                        
                        modeloTabla.setValueAt(fechaNueva, filaSeleccionada, ColumnaDetalleCompraEnum.COLUMNA_FECHA.numero);
                        
                    }
                }
                else
                {                
                    DialogoCodefac.mensaje("Sin seleccion","Seleccione una fila para editar",DialogoCodefac.MENSAJE_ADVERTENCIA);
                }               
            }
        });
        
        jpopMenu.add(menuItem);
        getTblCompra().setComponentPopupMenu(jpopMenu);
        
        
    }
    
    /**
     * Funcion que me permite generar solo los detalles del kardex seleccionados
     * @return devuelve el numero de items no seleccionados
     */
    private int generarKardexDetalleSeleccionados() throws ServicioCodefacException, RemoteException
    {
        int datosEliminados=0; //TODO: Optimizar como contar los datos eliminados porque por ejemplo en los productos que tienen garantia solo debe contar una vez por los detalles especificos
        detallesKardexFinal=new ArrayList<KardexDetalle>();
        
        for (Map.Entry<KardexDetalleTmp, CompraDetalle> entry : detallesKardex.entrySet()) {
            KardexDetalleTmp kardexDetalle = entry.getKey();
            CompraDetalle compraDetalle = entry.getValue();
            
            //Solo agregar los detalles seleccionados
            if(kardexDetalle.seleccion)
            {
                //Crear una variable referencial de tipo Kardex para que sepa la bodega y el producto que esta relacionado este producto
                //TODO: Esta no parece ser la mejor manera pero es un artificio que me ahorra mucha programacion adicional
                
                //Buscar el producto para realizar el kardex, si es un empaque buscar el producto original y hacer la respectiva reconstruccion
                Producto producto=compraDetalle.getProductoProveedor().getProducto();
                if(producto.getTipoProductoEnum().equals(TipoProductoEnum.EMPAQUE))
                {
                    /*producto=producto.buscarProductoEmpaquePrincipal();
                    //BigDecimal cantidadPorCaja= producto.obtenerCantidadPorCaja();
                    ProductoPresentacionDetalle detallePresentacion=producto.buscarPresentacionDetalleProducto();
                    BigDecimal cantidadPorCaja=detallePresentacion.getCantidad();
                    
                    if(cantidadPorCaja.compareTo(BigDecimal.ZERO)==0)
                    {
                        throw new ServicioCodefacException("Las cantidades de las conversiones no pueden ser CERO, producto: "+producto.getNombre());
                    }*/
                    ProductoConversionPresentacionRespuesta respuesta  = ServiceFactory.getFactory().getProductoServiceIf().convertirProductoEmpaqueSecundarioEnPrincipal(producto,kardexDetalle.getCantidad(), kardexDetalle.getPrecioUnitario());
                    
                    producto=respuesta.productoPresentacionPrincipal;
                    kardexDetalle.setCantidad(respuesta.cantidad);
                    kardexDetalle.setPrecioUnitario(respuesta.precioUnitario);
                }
                
                
                Kardex kardex=new Kardex();
                kardex.setBodega(kardexDetalle.bodega);
                kardex.setProducto(producto);
                kardex.setLote(compraDetalle.getLote());
                
                KardexDetalle kardexDetalleNuevo=kardexDetalle.obtenerObjetoOriginal();                
                kardexDetalleNuevo.setKardex(kardex);
                //kardexDetalleNuevo.setCodigoTipoDocumentoReferenciaEnum(compraDetalle.getCompra().getCodigoTipoDocumentoEnum());
                
                
                detallesKardexFinal.add(kardexDetalleNuevo);
                
                //Si existen mas items especificos los guardo
                if(kardexDetalle.getDetallesEspecificos()!=null && kardexDetalle.getDetallesEspecificos().size()>0)
                {
                    ArrayList<KardexItemEspecifico> detalleItemsNuevo=new ArrayList<KardexItemEspecifico>();
                    for (KardexItemEspecifico item : kardexDetalle.getDetallesEspecificos()) {
                        KardexItemEspecificoTemp itemTemp=(KardexItemEspecificoTemp) item;
                        
                        if(itemTemp.seleccion)
                        {
                            KardexItemEspecifico itemNuevo=itemTemp.obtenerObjetoOriginal();
                            itemNuevo.setKardexDetalle(kardexDetalleNuevo); //Seteado el objeto nuevo                            
                            detalleItemsNuevo.add(itemNuevo);
                            
                        }
                        else
                        {
                            datosEliminados++;
                        }
                    }
                    
                    kardexDetalleNuevo.setDetallesEspecificos(detalleItemsNuevo);
                    kardexDetalleNuevo.recalcularTotal();
                    
                }
            }
            else
            {
                datosEliminados++;
            }

        }
        return datosEliminados;
    }
    
    /**
     * Validacion para saber si todos los datos necesarios para grabar fueron ingresados
     * @return 
     */
    private boolean validarKardexGrabar()
    {
        if(detallesKardexFinal==null || detallesKardexFinal.size()==0)
        {
            DialogoCodefac.mensaje("Error","No se puede ingresar porque no existen detalles",DialogoCodefac.MENSAJE_INCORRECTO);
            return false;
        }
        
        for (KardexDetalle kardexDetalle : detallesKardexFinal) {
            
            
            if(kardexDetalle.getFechaIngreso()==null)
            {
                DialogoCodefac.mensaje("Error Validar","Ingrese la fecha para el producto "+kardexDetalle.getKardex().getProducto().getNombre(),DialogoCodefac.MENSAJE_INCORRECTO);
                return false;
            }
            
            if(kardexDetalle.getKardex().getBodega()==null)
            {
                DialogoCodefac.mensaje("Error Validar","Ingrese la bodega para el producto "+kardexDetalle.getKardex().getProducto().getNombre(),DialogoCodefac.MENSAJE_INCORRECTO);
                return false;
            }
            
            //Validacion cuando son productos con garantia
            if(kardexDetalle.getDetallesEspecificos()!=null)
            {
                for (KardexItemEspecifico itemEspecifico : kardexDetalle.getDetallesEspecificos()) {
                    
                    
                    if(itemEspecifico.getCodigoEspecifico().equals(""))
                    {
                        DialogoCodefac.mensaje("Error Validacion","Ingrese el codigo del producto especifico "+kardexDetalle.getKardex().getProducto().getNombre(),DialogoCodefac.MENSAJE_INCORRECTO);                
                        return false;
                    }
                    
                    
                }
            }
            
        }
        
        return true;
    }

    
    public enum ColumnaDetalleCompraEnum
    {
        COLUMNA_KARDEX(0),
        COLUMNA_INGRESAR(COLUMNA_KARDEX.numero+1),
        COLUMNA_BODEGA(COLUMNA_INGRESAR.numero+1),
        COLUMNA_FECHA(COLUMNA_BODEGA.numero+1),
        COLUMNA_CANTIDAD(COLUMNA_FECHA.numero+1),
        COLUMNA_DESCRIPCION(COLUMNA_CANTIDAD.numero+1),
        COLUMNA_COSTO_UNITARIO(COLUMNA_DESCRIPCION.numero+1),
        COLUMNA_COSTO_TOTAL(COLUMNA_COSTO_UNITARIO.numero+1),
        COLUMNA_GARANTIA(COLUMNA_COSTO_TOTAL.numero+1)
        ;

        private ColumnaDetalleCompraEnum(int numero) {
            this.numero = numero;
        }       
    
        public int numero;
        
        public static ColumnaDetalleCompraEnum getEnum(int numero)
        {
            for (ColumnaDetalleCompraEnum value : ColumnaDetalleCompraEnum.values()) 
            {
                if(value.numero==numero)
                {
                    return value;
                }
            }
            return null;
        }
    }
    
    public enum ColumnaGarantiaEnum
    {
        COLUMNA_ITEM(0),
        COLUMNA_INGRESAR(COLUMNA_ITEM.numero+1),
        COLUMNA_BODEGA(COLUMNA_INGRESAR.numero+1),
        COLUMNA_OBSERVACION(COLUMNA_BODEGA.numero+1),
        COLUMNA_CODIGO_UNITARIO(COLUMNA_OBSERVACION.numero+1);

        private ColumnaGarantiaEnum(int numero) {
            this.numero = numero;
        }       
    
        public int numero;
        
        public static ColumnaGarantiaEnum getEnum(int numero)
        {
            for (ColumnaGarantiaEnum value : ColumnaGarantiaEnum.values()) 
            {
                if(value.numero==numero)
                {
                    return value;
                }
            }
            return null;
        }
    }
    
        
   
}

