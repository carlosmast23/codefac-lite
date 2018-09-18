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
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.inventario.busqueda.CompraBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.busqueda.ProveedorBusquedaDialogo;
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
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.JXComboBox;

/**
 *
 * @author Carlos
 */
public class IngresoInventarioModel extends IngresoInventarioPanel {

    private static final Integer COLUMNA_BODEGA = 0;
    private static final Integer COLUMNA_FECHA = COLUMNA_BODEGA+1;
    private static final Integer COLUMNA_CANTIDAD = COLUMNA_FECHA+1;
    private static final Integer COLUMNA_DESCRIPCION = COLUMNA_CANTIDAD+1;
    private static final Integer COLUMNA_COSTO_UNITARIO = COLUMNA_DESCRIPCION+1;
    private static final Integer COLUMNA_COSTO_TOTAL = COLUMNA_COSTO_UNITARIO+1;
    private static final Integer COLUMNA_GARANTIA = COLUMNA_COSTO_TOTAL+1;
    private static final Integer COLUMNA_CODIGO_UNITARIO = COLUMNA_GARANTIA+1;
    private static final Integer COLUMNA_OBSERVACION_KARDEX = COLUMNA_CODIGO_UNITARIO+1;
    
    /**
     * Referencia de la compra que se carga para agregar al inventario
     */
    private Compra compraInventario;
    
    /**
     * Listado de todos los detalles de los kardex que se crearons
     */
    private Map<KardexDetalle,CompraDetalle> detallesKardex;
    
    private JComboBox<Bodega> cmbBodegaSeleccion;
    
    private JComboBox<EnumSiNo> cmbSiNo;
    
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        agregarListenerBotones();
        agregarListenerCombos();
        agregarListenerDatos();
        agregarPopUps();
        valoresIniciales();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        setearValoresKardex();
        if(validarItems())
        {
            try {
                KardexServiceIf servicioKardex=ServiceFactory.getFactory().getKardexServiceIf();
                Bodega bodega= (Bodega) getCmbBodega().getSelectedItem();
                servicioKardex.ingresarInventario(detallesKardex,bodega);
                DialogoCodefac.mensaje("Correcto","Los producto fueron agregados correctamente al kardex",DialogoCodefac.MENSAJE_CORRECTO);
            //} 
            //catch (ServicioCodefacException ex) 
            //{
            //    DialogoCodefac.mensaje("Incorrecto","No se pudo agregar los productos al inventario",DialogoCodefac.MENSAJE_INCORRECTO);
            //    Logger.getLogger(IngresoInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
            //    throw new ExcepcionCodefacLite("Cancelar grabar");
            } catch (RemoteException ex) {
                Logger.getLogger(IngresoInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
                throw new ExcepcionCodefacLite("Cancelar grabar");
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(IngresoInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            throw new ExcepcionCodefacLite("Error al validar");
        }
        
    }
    
    /**
     * Validar los items del kardex que este ingresado el codigo especifico
     * @return 
     */
    private boolean validarItems()
    {
        boolean validado=true;
        
        List<CompraDetalle> detalles= compraInventario.getDetalles();
        
        for (Map.Entry<KardexDetalle, CompraDetalle> entry : detallesKardex.entrySet()) {
            KardexDetalle key = entry.getKey();
            CompraDetalle value = entry.getValue();
            
            //Verificar que existan detalles
            if(key.getDetallesEspecificos()!=null)
            {
                for(KardexItemEspecifico item: key.getDetallesEspecificos())
                {
                    //Verificar que los detalles ingresados tengan un codigo especifico del prodcto para contralar el inventario
                    if(item.getCodigoEspecifico().equals(""))
                    {
                        DialogoCodefac.mensaje("Error Validación","El producto "+value.getProductoProveedor().getProducto().getNombre()+" requiere un código individual",DialogoCodefac.MENSAJE_ADVERTENCIA);                    
                        validado=false;
                        break;
                    }
                }
            }            
        }

        return validado;        
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
        limpiarVariables();
        limpiarVentana();
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

    private void agregarListenerBotones() {
        getBtnBuscarCompraPendiente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiar();
                CompraBusquedaDialogo buscarBusquedaDialogo = new CompraBusquedaDialogo();
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                compraInventario = (Compra) buscarDialogo.getResultado();
                if (compraInventario != null) {
                    String preimpreso=compraInventario.getPreimpreso();
                    String proveedor =compraInventario.getProveedor().getRazonSocial();
                    getTxtCompraSeleccionada().setText(preimpreso+" - "+proveedor);
                    cargarKardexDetalleCompra();
                    actualizarTablaComprasPendientes();
                }
                
            }
        });
        
    }
    
    private void cargarKardexDetalleCompra()
    {
         List<CompraDetalle> detalles=compraInventario.getDetalles();
        for (CompraDetalle detalle : detalles) {
            KardexDetalle kardexDetalle = new KardexDetalle();
            kardexDetalle.setCantidad(detalle.getCantidad());
            kardexDetalle.setCodigoTipoDocumento(compraInventario.getCodigoTipoDocumento());
            kardexDetalle.setReferenciaDocumentoId(compraInventario.getId());
            kardexDetalle.setPrecioUnitario(detalle.getPrecioUnitario());
            kardexDetalle.setPrecioTotal(detalle.getTotal());
                                    
            if (detalle.getProductoProveedor().getProducto().getGarantiaEnum().equals(EnumSiNo.SI)) {
                for (int i = 0; i < detalle.getCantidad(); i++) {
                    KardexItemEspecifico item=new KardexItemEspecifico();
                    item.setCodigoEspecifico("");
                    item.setEstado("a");
                    item.setObservaciones("");
                    kardexDetalle.addDetalle(item);
                }
            }
            
            detallesKardex.put(kardexDetalle, detalle);


        }
        
    }
    
    private void actualizarTablaComprasPendientes()
    {
        String titulo[]={"Bodega","Fecha","Cantidad","Descripcion","Costo Unitario","Costo Total","garantia","Codigo Unitario","Observacion Kardex"};
        
        Class clases[] = {
            Bodega.class,
            Date.class,
            String.class,
            String.class,
            String.class,
            String.class,
            String.class,
            String.class,
            String.class};

        //DefaultTableModel modelTable=new DefaultTableModel(titulo,0);
        DefaultTableModel modelTable=UtilidadesTablas.crearModeloTabla(titulo,clases,new Boolean[]{true,false,false,false,false,false,false});
        
        for (Map.Entry<KardexDetalle, CompraDetalle> entry : detallesKardex.entrySet()) 
        {
            KardexDetalle kardexDetalle = entry.getKey();
            CompraDetalle compraDetalle = entry.getValue();
            
            List<KardexItemEspecifico> detallesItem=kardexDetalle.getDetallesEspecificos();
            if(detallesItem==null)
            {
                Vector<Object> vector = new Vector<Object>();
                vector.add(null);
                vector.add(getCmbFechaIngreso().getDate());
                vector.add(compraDetalle.getCantidad() + "");
                vector.add(compraDetalle.getDescripcion() + "");
                vector.add(compraDetalle.getPrecioUnitario() + "");
                vector.add(compraDetalle.getTotal() + "");
                vector.add(compraDetalle.getProductoProveedor().getProducto().getGarantiaEnum().getNombre() + "");
                vector.add("");
                vector.add("");
                modelTable.addRow(vector);                
            }
            else
            {                
                for (int i = 0; i <detallesItem.size() ; i++) {
                    Vector<Object> vector = new Vector<Object>();
                    vector.add(null);
                    vector.add(getCmbFechaIngreso().getDate());
                    vector.add("1");
                    vector.add(compraDetalle.getDescripcion() + "");
                    vector.add(compraDetalle.getPrecioUnitario() + "");
                    vector.add(compraDetalle.getPrecioUnitario() + "");
                    vector.add(compraDetalle.getProductoProveedor().getProducto().getGarantiaEnum().getNombre() + "");
                    vector.add("");
                    vector.add("");
                    modelTable.addRow(vector);
                    
                }
                
            }
        }
        
        getTblTblCompra().setModel(modelTable);
        //Bloquear columnas que no se pueden editar
        UtilidadesTablas.cambiarTipoColumna(getTblTblCompra(),COLUMNA_BODEGA,cmbBodegaSeleccion);
        //UtilidadesTablas.cambiarTipoColumna(getTblTblCompra(), COLUMNA_GARANTIA,);
        //UtilidadesTablas.cambiarTipoColumna(getTblTblCompra(),COLUMNA_CANTIDAD,new JTextField());
        Map<Integer,Integer> mapTamaniosTabla=new HashMap<Integer,Integer>();
        mapTamaniosTabla.put(COLUMNA_CANTIDAD, 20);
        mapTamaniosTabla.put(COLUMNA_FECHA, 30);
        mapTamaniosTabla.put(COLUMNA_GARANTIA, 10);
        
        UtilidadesTablas.definirTamanioColumnasPorMap(getTblTblCompra(),mapTamaniosTabla);
        
    }

    private void agregarListenerCombos() {
        getCmbBodega().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(getCmbBodega().getSelectedIndex()>=0)
                {
                    Bodega bodegaDefecto=(Bodega) getCmbBodega().getSelectedItem();
                    DefaultTableModel modeloTabla=(DefaultTableModel) getTblTblCompra().getModel();
                    
                    for (int i = 0; i <modeloTabla.getRowCount(); i++) {
                        modeloTabla.setValueAt(bodegaDefecto, i,COLUMNA_BODEGA);
                    }
                    
                }
            }
        });
    }

    private void valoresIniciales() {
        try {            
            //crear el combo box de las opciones de seleccion de la tabla
            cmbBodegaSeleccion = new JComboBox<Bodega>();
            
            getCmbBodega().removeAllItems();
            BodegaServiceIf servicioBodega=ServiceFactory.getFactory().getBodegaServiceIf();
            List<Bodega> bodegas=servicioBodega.obtenerTodos();
            for (Bodega bodega : bodegas) {
                getCmbBodega().addItem(bodega);
                cmbBodegaSeleccion.addItem(bodega); //Combo para la seleccion de la tabla
            }
            
            //No seleccionar ninguna opcion del combo box por defecto
            crearCabeceraComboBox();
            
            /**
             * Crear los valores del combo del enum de si o no para ver si quieren manejar garantia
             */
            cmbSiNo=new JComboBox<EnumSiNo>();
            cmbSiNo.addItem(EnumSiNo.NO);
            cmbSiNo.addItem(EnumSiNo.SI);
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(IngresoInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

    /**
     * Metodo que limpia las variables internas
     */
    private void limpiarVariables() {
        this.detallesKardex=new HashMap<KardexDetalle,CompraDetalle>();
        compraInventario=null;
    }

    /**
     * Setea los valores de la tabla al Kardex
     */
    private void setearValoresKardex() {
        int filaTabla=0;
         for (Map.Entry<KardexDetalle, CompraDetalle> entry : detallesKardex.entrySet()) 
        {
            KardexDetalle kardexDetalle = entry.getKey();
            CompraDetalle compraDetalle = entry.getValue();
            
            List<KardexItemEspecifico> detallesItem=kardexDetalle.getDetallesEspecificos();
            if(detallesItem==null)
            {
                //TODO: Verificar si por algun motivo se puede modificar los datos normales de la compra detalles
                String codigoUnitario=getTblTblCompra().getModel().getValueAt(filaTabla,COLUMNA_CODIGO_UNITARIO).toString();
                String observacionKardex=getTblTblCompra().getModel().getValueAt(filaTabla,COLUMNA_OBSERVACION_KARDEX).toString();                            
                filaTabla++;
            }
            else
            {                
                for (int i = 0; i <detallesItem.size() ; i++) {
                    
                    //TODO: Verificar si por algun motivo se puede modificar los datos normales de la compra detalles
                    String codigoUnitario = getTblTblCompra().getModel().getValueAt(filaTabla, COLUMNA_CODIGO_UNITARIO).toString();
                    String observacionKardex = getTblTblCompra().getModel().getValueAt(filaTabla, COLUMNA_OBSERVACION_KARDEX).toString();  
                    KardexItemEspecifico kardexEspecifico= detallesItem.get(i);
                    kardexEspecifico.setCodigoEspecifico(codigoUnitario);
                    kardexEspecifico.setObservaciones(observacionKardex);
                    filaTabla++;                                        
                }
                
            }
        }
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void limpiarVentana() {
        getCmbFechaIngreso().setDate(new Date());
        crearCabeceraComboBox();
        //getCmbBodega().setSelectedIndex(-1);
        getTxtCompraSeleccionada().setText("");
        getTblTblCompra().setModel(new DefaultTableModel());
    }
    
    private void crearCabeceraComboBox()
    {
        UtilidadesComboBox.crearCabeceraComboBox(getCmbBodega(),"Seleccione una bodega :");        
    }

    private void agregarPopUps() {
        JPopupMenu jpopMenu=new JPopupMenu();
        JMenuItem menuItem=new JMenuItem("Cambiar Fecha");
        
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada=getTblTblCompra().getSelectedRow();
                
                if(filaSeleccionada>=0)
                {
                    DefaultTableModel modeloTabla=(DefaultTableModel) getTblTblCompra().getModel();
                    DialogoFecha dialogoFecha = new DialogoFecha();
                    dialogoFecha.setVisible(true);
                    
                    if (dialogoFecha.getFechaResultado()!=null) 
                    {
                        //System.out.println(COLUMNA_FECHA+"<---");
                        modeloTabla.setValueAt(dialogoFecha.getFechaResultado(),filaSeleccionada,COLUMNA_FECHA);
                        //System.out.println("seteado fecha nueva");
                    }
                }
                else
                {                
                    DialogoCodefac.mensaje("Sin seleccion","Seleccione una fila para editar",DialogoCodefac.MENSAJE_ADVERTENCIA);
                }               
            }
        });
        
        jpopMenu.add(menuItem);
        getTblTblCompra().setComponentPopupMenu(jpopMenu);
        
        
    }

    private void agregarListenerDatos() {
        getCmbFechaIngreso().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel modeloTabla=(DefaultTableModel) getTblTblCompra().getModel();
                Date fechaDefecto=getCmbFechaIngreso().getDate();
                for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                    modeloTabla.setValueAt(fechaDefecto,i,COLUMNA_FECHA);
                }
            }
        });
    }
    
}

