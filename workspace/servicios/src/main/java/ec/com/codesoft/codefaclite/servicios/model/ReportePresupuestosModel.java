/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servicios.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteFacturacionBusqueda;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servicios.data.PresupuestoData;
import ec.com.codesoft.codefaclite.servicios.panel.ReportePresupuestosPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class ReportePresupuestosModel extends ReportePresupuestosPanel {

    private Persona cliente;
    private List<PresupuestoData> presupuestosData;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        listenerBotones();
        listenerCheckBox();
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
        InputStream path = RecursoCodefac.JASPER_SERVICIO.getResourceInputStream("presupuestosReporte.jrxml");

        DialogoCodefac.dialogoReporteOpciones(new ReporteDialogListener() {
            @Override
            public void excel() {
                try {
                    Excel excel = new Excel();
                    excel.gestionarIngresoInformacionExcel(getTituloTablaPantalla(), presupuestosData);
                    excel.abrirDocumento();
                } catch (Exception exc) {
                    exc.printStackTrace();
                    DialogoCodefac.mensaje("Error", "Error al general el archivo excel : \n" + exc.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
                }
            }

            @Override
            public void pdf() {
                ReporteCodefac.generarReporteInternalFramePlantilla(path, new HashMap(), presupuestosData, panelPadre, "Reporte Presupuestos");
            }
        });
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        getCmbEstado().removeAllItems();
        for (Presupuesto.EstadoEnum estadoEnum : Presupuesto.EstadoEnum.values()) {
            getCmbEstado().addItem(estadoEnum);
        }
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true); //Habilito el boton de buscar
        return permisos;
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

    private void listenerBotones() {

        getBtnBuscarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteFacturacionBusqueda clienteBusquedaDialogo = new ClienteFacturacionBusqueda();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                cliente = ((PersonaEstablecimiento) buscarDialogoModel.getResultado()).getPersona();
                if (cliente != null) {
                    setearValoresCliente();
                }
            }

        });

        getBtnLimpiarFechaInicial().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCmbFechaInicial().setDate(null);
            }
        });

        getBtnLimpiarFechaFinal().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCmbFechaFinal().setDate(null);
            }
        });

        getBtnConsultar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Presupuesto.EstadoEnum estadoEnum=(getCmbEstado().isEnabled())?(Presupuesto.EstadoEnum) getCmbEstado().getSelectedItem():null;
                    List<Presupuesto> presupuestos = ServiceFactory.getFactory().getPresupuestoServiceIf().consultarPresupuestos(
                            getCmbFechaInicial().getDate(),
                            getCmbFechaFinal().getDate(),
                            cliente,
                            estadoEnum);

                    construirDataReporte(presupuestos);
                    mostrarDatosTabla();
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(ReportePresupuestosModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RemoteException ex) {
                    Logger.getLogger(ReportePresupuestosModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void setearValoresCliente() {
        getTxtCliente().setText(cliente.toString());
    }

    private void construirDataReporte(List<Presupuesto> lista) {
        presupuestosData = new ArrayList<PresupuestoData>();
        for (Presupuesto presupuesto : lista) {
            PresupuestoData presupuestoData = new PresupuestoData();
            presupuestoData.setCodigo(presupuesto.getCodigo());
            presupuestoData.setFecha(presupuesto.getFechaPresupuesto().toString());
            presupuestoData.setEstado(presupuesto.getEstadoEnum().getNombre());
            presupuestoData.setDescripcion(presupuesto.getDescripcion());
            presupuestoData.setIdentificacion(presupuesto.getPersona().getIdentificacion());
            presupuestoData.setNombres(presupuesto.getPersona().getNombres());
            presupuestoData.setTotal(presupuesto.getTotalCompra());
            presupuestosData.add(presupuestoData);
        }

    }

    private String[] getTituloTablaPantalla() {
        String[] titulo = {"Código", "Identificación", "Fecha", "Estado", "Nombres", "Descripción", "Total"};
        return titulo;
    }

    private void mostrarDatosTabla() {

        String[] titulo = getTituloTablaPantalla();
        DefaultTableModel modeloTabla = new DefaultTableModel(titulo, 0);

        if (presupuestosData != null) {
            for (PresupuestoData presupuestoData : presupuestosData) {
                modeloTabla.addRow(new String[]{
                    presupuestoData.getCodigo(),
                    presupuestoData.getIdentificacion(),
                    presupuestoData.getFecha(),
                    presupuestoData.getEstado(),
                    presupuestoData.getNombres(),
                    presupuestoData.getDescripcion(),
                    presupuestoData.getTotal().toString(),});

            }
        }

        getTblDatos().setModel(modeloTabla);
    }

    private void listenerCheckBox() {
        getChkTodosClientes().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    cliente = null;
                    getTxtCliente().setText("");                    
                    getBtnBuscarCliente().setEnabled(false);
                } else {
                    getBtnBuscarCliente().setEnabled(true);
                }
            }
        });
        
        getChkTodosEstados().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected                    
                    getCmbEstado().setEnabled(false);
                } else {
                    getCmbEstado().setEnabled(true);
                }
            }
        });
    }

}
