/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.cartera.model;

import ec.com.codesoft.codefaclite.cartera.panel.CuentasPorCobarReportePanel;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraServiceIf;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DesarrolloSoftware
 */
public class CuentasPorCobrarReporteModel extends CuentasPorCobarReportePanel
{
    private Persona persona;
    private boolean banderaTodos;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        addListenerBotones();
        addListenerCheck();
        initDatosTabla();
        getDateFechaInicio().setDate(new java.util.Date());
        getDateFechaFin().setDate(new java.util.Date());
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
    
    public void addListenerBotones()
    {
        getBtnBuscarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ClienteBusquedaDialogo buscarBusquedaDialogo = new ClienteBusquedaDialogo();
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                Persona personaTemp = (Persona) buscarDialogo.getResultado();
                if(personaTemp != null)
                {
                   persona = personaTemp;
                   getTxtCliente().setText(persona.getIdentificacion() + " - " + persona.getNombreSimple() );
                }
            }
        });
        
        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                try {
                    CarteraServiceIf carteraServiceIf = ServiceFactory.getFactory().getCarteraServiceIf();
                    if(banderaTodos)
                    {
                        List<Cartera> carteras = carteraServiceIf.listaCarteraSaldoCero(persona, new Date(getDateFechaInicio().getDate().getTime()), new Date(getDateFechaFin().getDate().getTime()));
                    }else{
                        List<Cartera> carteras = carteraServiceIf.listaCarteraSaldoCero(persona, new Date(getDateFechaInicio().getDate().getTime()), new Date(getDateFechaFin().getDate().getTime()));
                    }
                    List<Cartera> carteras = carteraServiceIf.listaCarteraSaldoCero(persona, new Date(getDateFechaInicio().getDate().getTime()), new Date(getDateFechaFin().getDate().getTime()));
                    mostrarDatosTabla(carteras);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(CuentasPorCobrarReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RemoteException ex) {
                    Logger.getLogger(CuentasPorCobrarReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            }
        });
    }
    
    public void addListenerCheck()
    {
//        if (getChkTodosNiveles().isSelected()) {
//            banderaNiveles = true;
//            getCmbNivelAcademico().setEnabled(false);
//        }

        getCheckTodos().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    banderaTodos = true;
                    getBtnBuscarCliente().setEnabled(false);
                    getTxtCliente().setEnabled(false);
                    persona = null;
                    getTxtCliente().setText("");
                    
                } else {
                    banderaTodos = false;
                    getBtnBuscarCliente().setEnabled(true);
                    getTxtCliente().setEnabled(true);
                }
            }
        });
    }
    
    public void initDatosTabla()
    {
        DefaultTableModel modeloTablaDetallesPresupuesto = UtilidadesTablas.crearModeloTabla(new String[]{"Preimpreso","Persona","Saldo"}, new Class[]{String.class,String.class,String.class});
        getTableCuentasPorCobrar().setModel(modeloTablaDetallesPresupuesto);
    }
    
     private void mostrarDatosTabla(List<Cartera> carteras)
    {
        
        String[] titulo={"Preimpreso","Persona","Saldo"};
        DefaultTableModel defaultTableModel = new DefaultTableModel(titulo,0);
        BigDecimal saldos = new BigDecimal(BigInteger.ZERO);
        for (Cartera cartera : carteras) {
            Vector<String> fila=new Vector<String>();
            fila.add(cartera.getPreimpreso()+"");
            fila.add(cartera.getPersona().getNombreSimple()+"");
            fila.add(cartera.getSaldo()+"");
            saldos = saldos.add(cartera.getSaldo());
            defaultTableModel.addRow(fila);
        }
        getLblTotal().setText(""+saldos);
        getTableCuentasPorCobrar().setModel(defaultTableModel);
        
    }
    
    
}
