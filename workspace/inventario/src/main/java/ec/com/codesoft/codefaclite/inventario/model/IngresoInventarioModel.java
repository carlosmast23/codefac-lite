/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class IngresoInventarioModel extends IngresoInventarioPanel {

    private static final Integer COLUMNA_CANTIDAD=0;
    private static final Integer COLUMNA_DESCRIPCION=1;
    private static final Integer COLUMNA_COSTO_UNITARIO=2;
    private static final Integer COLUMNA_COSTO_TOTAL=3;
    private static final Integer COLUMNA_GARANTIA=4;
    private static final Integer COLUMNA_CODIGO_UNITARIO=5;
    private static final Integer COLUMNA_OBSERVACION_KARDEX=6;
    
    /**
     * Referencia de la compra que se carga para agregar al inventario
     */
    private Compra compraInventario;
    
    /**
     * Listado de todos los detalles de los kardex que se crearons
     */
    private Map<KardexDetalle,CompraDetalle> detallesKardex;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        agregarListenerBotones();
        agregarListenerCombos();
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
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
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
                CompraBusquedaDialogo buscarBusquedaDialogo = new CompraBusquedaDialogo();
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                compraInventario = (Compra) buscarDialogo.getResultado();
                if (compraInventario != null) {
                    String preimpreso=compraInventario.getPreimpreso();
                    String proveedor =compraInventario.getProveedor().getRazonSocial();
                    getTxtCompraSeleccionada().setText(preimpreso+"-"+proveedor);
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
        String titulo[]={"Cantidad","Descripcion","Costo Unitario","Costo Total","garantia","Codigo Unitario","Observacion Kardex"};
        DefaultTableModel modelTable=new DefaultTableModel(titulo,0);
        
        for (Map.Entry<KardexDetalle, CompraDetalle> entry : detallesKardex.entrySet()) 
        {
            KardexDetalle kardexDetalle = entry.getKey();
            CompraDetalle compraDetalle = entry.getValue();
            
            List<KardexItemEspecifico> detallesItem=kardexDetalle.getDetallesEspecificos();
            if(detallesItem==null)
            {
                Vector<String> vector = new Vector<String>();
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
                    Vector<String> vector = new Vector<String>();
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

    }

    private void agregarListenerCombos() {
        //getCmbBodega().addActionListener();
    }

    private void valoresIniciales() {
        try {
            getCmbBodega().removeAllItems();
            BodegaServiceIf servicioBodega=ServiceFactory.getFactory().getBodegaServiceIf();
            List<Bodega> bodegas=servicioBodega.obtenerTodos();
            for (Bodega bodega : bodegas) {
                getCmbBodega().addItem(bodega);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(IngresoInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void limpiarVariables() {
        this.detallesKardex=new HashMap<KardexDetalle,CompraDetalle>();
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
    
}

