/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.cartera.model;

import ec.com.codesoft.codefaclite.cartera.panel.MovimientoCarteraPanel;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteBusquedaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraCruce;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class MovimientoCarteraModel extends MovimientoCarteraPanel{
    
    /**
     * Referencia para guardar el cliente o proveedor para hacer el filtro para el movimiento de cartera
     */
    private Persona personaFiltro;
    
    
    /**
     * Map que agrupa la cartera por cliente y sus lista de cruces
     */
    private Map<Cartera,List<CarteraCruce>> mapMovimientoCartera;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        listerBotones();
        valoresInicialesPantalla();
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

    private void valoresInicialesPantalla() {
        getCmbTipoCartera().removeAllItems();
        for (Cartera.TipoCarteraEnum carteraEnum : Cartera.TipoCarteraEnum.values()) {
            getCmbTipoCartera().addItem(carteraEnum);
        }
    }

    private void listerBotones() {
        getBtnBuscarPersona().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnListenerBuscarCliente();
            }
        });
        
        
        getBtnConsultar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listenerBotonConsultar();
            }
        });
    }
    
    private void listenerBotonConsultar()
    {
        try {
            List<CarteraCruce> cruces=ServiceFactory.getFactory().getCarteraServiceIf().consultarMovimientoCartera(personaFiltro);
            mapMovimientoCartera=convertirCrucesEnMap(cruces);
            construirTablaMovimientoCartera();
            
        } catch (RemoteException ex) {
            Logger.getLogger(MovimientoCarteraModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void construirTablaMovimientoCartera()
    {
        String[] titulo={"identificación","Nombres","Documento","Preimpreso","Debe","Haber"};
        DefaultTableModel modeloTabla=new DefaultTableModel(titulo,0);
        
        for (Map.Entry<Cartera, List<CarteraCruce>> entry : mapMovimientoCartera.entrySet()) {
            Cartera cartera = entry.getKey();
            List<CarteraCruce> cruces = entry.getValue();
            
            String[] filaTitulo={cartera.getPersona().getIdentificacion(),cartera.getPersona().getNombreSimple(),"Factura",cartera.getPreimpreso(),cartera.getTotal().toString(),""};
            modeloTabla.addRow(filaTitulo);
            
            for (CarteraCruce cruceTmp : cruces) {
                String[] filaCruce={"","","Abono","","",cruceTmp.getValor().toString(),""};
                modeloTabla.addRow(filaCruce);
            }
            
            //Saldos y valores cancelado
            String[] filaSaldo={"","","","Debe","0",cartera.getTotal().subtract(cartera.getSaldo()).toString()};
            modeloTabla.addRow(filaSaldo);
            String[] filaHaber={"","","","Haber","0",cartera.getSaldo().toString()};
            modeloTabla.addRow(filaHaber);
            
            //Espacio en blanco para manejar un formato bonito
            modeloTabla.addRow(new String[]{});
            modeloTabla.addRow(new String[]{});
            
        }
        
                
        getTblDatos().setModel(modeloTabla);
    
    }
    
    private Map<Cartera,List<CarteraCruce>> convertirCrucesEnMap(List<CarteraCruce> cruces)
    {
        Map<Cartera,List<CarteraCruce>> mapMovimientoCartera=new HashMap<Cartera,List<CarteraCruce>>();
        
        for (CarteraCruce cruce : cruces) {
            
            List<CarteraCruce> crucesTmp=mapMovimientoCartera.get(cruce.getCarteraAfectada());
            if(crucesTmp==null)
            {
                //Cuando no existe en el map cree el array e agrego al map
                crucesTmp=new ArrayList<CarteraCruce>();
                crucesTmp.add(cruce);
                mapMovimientoCartera.put(cruce.getCarteraAfectada(),crucesTmp);
            }
            else
            {
                //Si ya existe la lista de cruces creada solo agrego 
                crucesTmp.add(cruce);                
            }                        
        }
        return mapMovimientoCartera;
    }
    
    private void btnListenerBuscarCliente() {
        ClienteBusquedaDialogo clienteBusquedaDialogo = new ClienteBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        //factura.setCliente((Persona) buscarDialogoModel.getResultado());
        Persona personaTemp=(Persona) buscarDialogoModel.getResultado();
        if(personaTemp!=null)
        {
            personaFiltro=personaTemp;
            getTxtPersonaFiltro().setText(personaFiltro.toString());
        }
       
    }
    
    
}
