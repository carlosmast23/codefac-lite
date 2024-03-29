/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.gestionacademica.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import static ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface.ESTADO_GRABAR;
import ec.com.codesoft.codefaclite.gestionacademica.busqueda.PeriodoBusquedaDialogo;
import ec.com.codesoft.codefaclite.gestionacademica.panel.PeriodoPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PeriodoServiceIf;
import static ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha.fechaInicioMes;
import static ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha.formatDate;
import static ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha.hoy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class PeriodoModel extends PeriodoPanel {

    private Periodo periodo;
    private PeriodoServiceIf periodoService;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date fechaInicio = null;
    Date fechaFin = null;


    public PeriodoModel() {
        periodoService = ServiceFactory.getFactory().getPeriodoServiceIf();
        listaExclusionComponentes.add(getTxtNombre()); //Agrego a la lista de exluciones para evitar que valide cuando existan datos ingresados
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        listenerBotones();
        try {
            getCmbEstado().removeAllItems();
            for (GeneralEnumEstado enumerador : GeneralEnumEstado.values()) {
                getCmbEstado().addItem(enumerador);
            }
            
            getDateFechaInicio().setDate(fechaInicioMes(hoy()));
            getDateFechaFin().setDate(hoy());
            
            getDateFechaInicio().addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if ("date".equals(evt.getPropertyName())) {
                        if(estadoFormulario.equals(ESTADO_GRABAR))
                        {
                            if (getDateFechaInicio().getDate() != null || getDateFechaFin().getDate() != null) {
                                getTxtNombre().setText(formatDate(getDateFechaInicio().getDate(), "yyyy MMMMM") + " - " + formatDate(getDateFechaFin().getDate(), "MMMMM"));
                            }
                        }
                    }
                }
            });
            
            getDateFechaFin().addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if ("date".equals(evt.getPropertyName())) {
                        if(estadoFormulario.equals(ESTADO_GRABAR))
                        {
                            if (getDateFechaInicio().getDate() != null || getDateFechaFin().getDate() != null) {
                                getTxtNombre().setText(formatDate(getDateFechaInicio().getDate(), "yyyy MMMMM") + " - " + formatDate(getDateFechaFin().getDate(), "MMMMM"));
                            }
                        }
                    }
                }
            });
            
            //=============> CARGAR LOS PERIODOS ACTIVOS <==================/
            getCmbPeriodosActivos().removeAllItems();
            List<Periodo> periodos=ServiceFactory.getFactory().getPeriodoServiceIf().obtenerPeriodosSinEliminar();
            for (Periodo periodo : periodos) {
                getCmbPeriodosActivos().addItem(periodo);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(PeriodoModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            setearValoresPeriodo(periodo);
            periodo = periodoService.grabar(periodo);
            DialogoCodefac.mensaje("Datos correctos", "El periodo se guardo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("Error al grabar periodo modelo");
        } catch (RemoteException ex) {
            DialogoCodefac.mensaje("Error", "Error de comunicación con el servidor , periodo", DialogoCodefac.MENSAJE_ADVERTENCIA);
            Logger.getLogger(AulaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setearValoresPeriodo(Periodo periodo) {
        periodo.setNombre(getTxtNombre().getText());
        periodo.setObservaciones(getTxtObservacion().getText());
        periodo.setEstado(((GeneralEnumEstado) getCmbEstado().getSelectedItem()).getEstado());
        if (getDateFechaInicio().getDate() != null) {
            fechaInicio = new Date(getDateFechaInicio().getDate().getTime());
            periodo.setFechaInicio(fechaInicio);
        }
        if (getDateFechaFin().getDate() != null) {
            fechaFin = new Date(getDateFechaFin().getDate().getTime());
            periodo.setFechaFin(fechaFin);
        }

    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        try {
            setearValoresPeriodo(periodo);
            periodoService.editar(periodo);
            DialogoCodefac.mensaje("Datos correctos", "El periodo se edito correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (RemoteException ex) {
            Logger.getLogger(NivelModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(PeriodoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        if (estadoFormulario.equals(GeneralPanelInterface.ESTADO_EDITAR)) {
            try {
                Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Estas seguro que desea eliminar el periodo?", DialogoCodefac.MENSAJE_ADVERTENCIA);
                if (!respuesta) {
                    throw new ExcepcionCodefacLite("Cancelacion periodo");
                }
                try {
                    periodoService.eliminar(periodo);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(PeriodoModel.class.getName()).log(Level.SEVERE, null, ex);

                    DialogoCodefac.mensaje("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
                    throw new ExcepcionCodefacLite(ex.getMessage());
                }
                DialogoCodefac.mensaje("Datos correctos", "El periodo se elimino correctamente", DialogoCodefac.MENSAJE_CORRECTO);
            } catch (RemoteException ex) {
                Logger.getLogger(AulaModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    private void cargarDatos()
    {
        getTxtNombre().setText(periodo.getNombre());
        getTxtObservacion().setText(periodo.getObservaciones());

        getDateFechaInicio().setDate(periodo.getFechaInicio());
        getDateFechaFin().setDate(periodo.getFechaFin());

        getCmbEstado().setSelectedItem(periodo.getEStadoEnum());
    }

    @Override
    public void limpiar() {
        periodo = new Periodo();

        getDateFechaInicio().setDate(fechaInicioMes(hoy()));
        getDateFechaFin().setDate(hoy());

        getCmbEstado().setSelectedIndex(0);

    }

//    @Override
    public String getNombre() {
        return "Periodo";
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

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        PeriodoBusquedaDialogo periodoBusquedaDialogo = new PeriodoBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(periodoBusquedaDialogo);
        return buscarDialogoModel;
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        periodo = (Periodo) entidad;
        cargarDatos();
    }

    private void listenerBotones() {
        getBtnEstablecerPeriodoActivo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Periodo periodoSeleccionado=(Periodo) getCmbPeriodosActivos().getSelectedItem();
                    ServiceFactory.getFactory().getPeriodoServiceIf().setearPeriodoActivo(periodoSeleccionado);
                    DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
                } catch (RemoteException ex) {
                    Logger.getLogger(PeriodoModel.class.getName()).log(Level.SEVERE, null, ex);
                    DialogoCodefac.mensaje(MensajeCodefacSistema.ErrorComunicacion.ERROR_COMUNICACION_SERVIDOR);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(PeriodoModel.class.getName()).log(Level.SEVERE, null, ex);
                    DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
                }
            }
        });
    }

}
