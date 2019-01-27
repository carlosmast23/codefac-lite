/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.panel.UtilidadEnvioReportesPanel;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoReporteEnum;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.swing.JComponent;

/**
 *
 * @author Carlos
 */
public class UtilidadEnvioReportesModel  extends UtilidadEnvioReportesPanel{

    private Empleado empleadoSeleccionado;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        cargarValoresIniciales();
        listenerBotones();
        validacionDatosIngresados=false;
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

    private void cargarValoresIniciales() {
        for (ComprobanteEntity.ComprobanteEnumEstado objeto : ComprobanteEntity.ComprobanteEnumEstado.values()) {
            getCmbEstadoGuiaRemision().addItem(objeto);
            getCmbEstadoNotaCredito().addItem(objeto);
            getCmbEstadoRetenciones().addItem(objeto);
            getCmbEstadoVentas().addItem(objeto);
        } 
        
        for (FormatoReporteEnum valor : FormatoReporteEnum.values()) {
            getCmbFormatoGuiaRemision().addItem(valor);
            getCmbFormatoNotaCredito().addItem(valor);
            getCmbFormatoRetenciones().addItem(valor);
            getCmbFormatoVentas().addItem(valor);
        }
    }

    private void listenerBotones() {
        getBtnBuscarEmpleado().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmpleadoBusquedaDialogo busquedaDialog = new EmpleadoBusquedaDialogo();
                //busquedaDialog.setTipoEnum(Departamento.TipoEnum.Ventas);
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busquedaDialog);
                buscarDialogoModel.setVisible(true);
                Empleado empleadoTmp = (Empleado) buscarDialogoModel.getResultado();
                if (empleadoTmp != null) {
                    empleadoSeleccionado=empleadoTmp;
                    getTxtEmpleadoDatos().setText(empleadoTmp.toString());
                }
            }
        });
    }
    
}
