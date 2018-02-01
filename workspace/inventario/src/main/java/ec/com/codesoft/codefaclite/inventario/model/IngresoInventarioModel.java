/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.inventario.busqueda.CompraBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.panel.IngresoInventarioPanel;
import ec.com.codesoft.codefaclite.servidor.entity.Compra;
import ec.com.codesoft.codefaclite.servidor.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class IngresoInventarioModel extends IngresoInventarioPanel {

    /**
     * Referencia de la compra que se carga para agregar al inventario
     */
    private Compra compraInventario;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        agregarListenerBotones();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                    actualizarTablaComprasPendientes();
                }
            }
        });
    }
    
    
    private void actualizarTablaComprasPendientes()
    {
        String titulo[]={"Cantidad","Descripcion","Costo Unitario","Costo Total","Codigo Unitario"};
        DefaultTableModel modelTable=new DefaultTableModel(titulo,0);
        
        List<CompraDetalle> detalles=compraInventario.getDetalles();
        for (CompraDetalle detalle : detalles) {
            Vector<String> vector=new Vector<String>();
            vector.add(detalle.getCantidad()+"");
            vector.add(detalle.getDescripcion()+"");
            vector.add(detalle.getPrecioUnitario()+"");
            vector.add(detalle.getTotal()+"");
            vector.add("");
            modelTable.addRow(vector);
        }
        getTblTblCompra().setModel(modelTable);
    }
    
}

