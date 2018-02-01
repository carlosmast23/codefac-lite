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
import ec.com.codesoft.codefaclite.servidor.entity.Bodega;
import ec.com.codesoft.codefaclite.servidor.entity.Compra;
import ec.com.codesoft.codefaclite.servidor.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidor.service.BodegaService;
import ec.com.codesoft.codefaclite.servidor.service.KardexService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
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
        
        if(validarItmems())
        {
            KardexService servicioKardex=new KardexService();
            //servicioKardex.grabar(gr);
        }
        
    }
    
    
    private boolean validarItmems()
    {
        boolean validado=true;
        
        List<CompraDetalle> detalles= compraInventario.getDetalles();
        
        for (int i = 0; i < detalles.size(); i++) {
            CompraDetalle compraDetalle=detalles.get(i);
            if(compraDetalle.getProductoProveedor().getProducto().getGarantiaEnum().equals(EnumSiNo.SI))
            {
                String codigoUnitario=getTblTblCompra().getModel().getValueAt(i,COLUMNA_CODIGO_UNITARIO).toString();
                if(codigoUnitario==null || codigoUnitario.equals(""))
                {
                    DialogoCodefac.mensaje("Error Validación","El producto "+compraDetalle.getProductoProveedor().getProducto().getNombre()+" requiere el ingreso de un codigo individual",DialogoCodefac.MENSAJE_INCORRECTO);                    
                    validado=false;
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

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            KardexDetalle kardexDetalle=new KardexDetalle();
            kardexDetalle.setCantidad(detalle.getCantidad());
            kardexDetalle.setCodigoDocumento(compraInventario.getCodigoDocumento());
            kardexDetalle.setDocumenoAfectaId(compraInventario.getId());
            kardexDetalle.setPrecioUnitario(detalle.getPrecioUnitario());
            kardexDetalle.setPrecioTotal(detalle.getTotal());
            kardexDetalle.setTipoMovimiento(""); //TODO: Pendiente de grabar
            detallesKardex.put(kardexDetalle, detalle);
        }
        
    }
    
    private void actualizarTablaComprasPendientes()
    {
        String titulo[]={"Cantidad","Descripcion","Costo Unitario","Costo Total","garantia","Codigo Unitario","Observacion Kardex"};
        DefaultTableModel modelTable=new DefaultTableModel(titulo,0);
        
        List<CompraDetalle> detalles=compraInventario.getDetalles();
        for (CompraDetalle detalle : detalles) {
            Vector<String> vector=new Vector<String>();
            vector.add(detalle.getCantidad()+"");
            vector.add(detalle.getDescripcion()+"");
            vector.add(detalle.getPrecioUnitario()+"");
            vector.add(detalle.getTotal()+"");
            vector.add(detalle.getProductoProveedor().getProducto().getGarantiaEnum().getNombre()+"");
            vector.add("");
            vector.add("");
            modelTable.addRow(vector);
        }
        getTblTblCompra().setModel(modelTable);
    }

    private void agregarListenerCombos() {
        //getCmbBodega().addActionListener();
    }

    private void valoresIniciales() {
        getCmbBodega().removeAllItems();
        BodegaService servicioBodega=new BodegaService();
        List<Bodega> bodegas=servicioBodega.obtenerTodos();
        for (Bodega bodega : bodegas) {
            getCmbBodega().addItem(bodega);
        }
    }

    private void limpiarVariables() {
        this.detallesKardex=new HashMap<KardexDetalle,CompraDetalle>();
    }
    
}

