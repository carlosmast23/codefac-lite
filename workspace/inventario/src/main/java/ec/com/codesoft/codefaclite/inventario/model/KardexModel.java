/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.inventario.busqueda.CompraBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.inventario.panel.KardexPanel;
import ec.com.codesoft.codefaclite.servidor.entity.Bodega;
import ec.com.codesoft.codefaclite.servidor.entity.Compra;
import ec.com.codesoft.codefaclite.servidor.entity.Producto;
import ec.com.codesoft.codefaclite.servidor.service.BodegaService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class KardexModel extends KardexPanel{
    
    /**
     * Referencia del producto para consultar el kardex
     */
    private Producto productoSeleccionado;

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        agregarListernerBotones();
        valoresIniciales();
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

    private void agregarListernerBotones() {
        getBtnProductoBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoBusquedaDialogo buscarBusquedaDialogo = new ProductoBusquedaDialogo();
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                productoSeleccionado = (Producto) buscarDialogo.getResultado();
                if (productoSeleccionado != null) {
                    getTxtProducto().setText(productoSeleccionado.getNombre());
                }
            }
        });
    }
    
    private void valoresIniciales() {
        getCmbBodega().removeAllItems();
        BodegaService servicioBodega = new BodegaService();
        List<Bodega> bodegas = servicioBodega.obtenerTodos();
        for (Bodega bodega : bodegas) {
            getCmbBodega().addItem(bodega);
        }
        
    }

    
}
