/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.inventario.model;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.inventario.panel.StockMinimoPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class StockMinimoModel extends StockMinimoPanel{
    
    private List<Object[]> listaStock;
    //private 

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        valoresIniciales();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void valoresIniciales() {
        try {
                       
            getCmbBodega().removeAllItems();
            BodegaServiceIf servicioBodega = ServiceFactory.getFactory().getBodegaServiceIf();
            List<Bodega> bodegas = servicioBodega.obtenerTodos();
            for (Bodega bodega : bodegas) {
                getCmbBodega().addItem(bodega);                
            }
        } catch (RemoteException ex) {
            Logger.getLogger(GestionInventarioModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listenerBotones() {
        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    listaStock=ServiceFactory.getFactory().getKardexServiceIf().consultarStockMinimo();
                    construirTabla();
                } catch (RemoteException ex) {
                    Logger.getLogger(StockMinimoModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private void construirTabla()
    {
        String[] titulo={
            "Código",
            "Producto",
            "Stock",
            "Cantidad Minima"
        };
        
        DefaultTableModel modeloTabla=new DefaultTableModel(titulo,0);
        for (Object[] objeto : listaStock) {
            Producto producto=(Producto) objeto[0];
            Integer cantidad=(Integer) objeto[1];
            
            String[] datos=
            {
                producto.getCodigoPersonalizado().toString(),
                producto.getNombre(),
                cantidad.toString(),
                producto.getCantidadMinima().toString()
                
            };
            
            modeloTabla.addRow(datos);
            
        }
        getTblDato().setModel(modeloTabla);
        
    }
    
}
