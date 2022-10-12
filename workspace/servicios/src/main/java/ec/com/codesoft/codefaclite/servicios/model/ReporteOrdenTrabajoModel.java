/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servicios.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servicios.data.OrdenTrabajoData;
import ec.com.codesoft.codefaclite.servicios.panel.ReporteOrdenTrabajoPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenTrabajoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class ReporteOrdenTrabajoModel extends ReporteOrdenTrabajoPanel{
    
    /**
     * Variable para guardar la lista de resultados de la consulta cuando existe consultas
     */
    private List<OrdenTrabajoDetalle> listaResultado;
    /**
     * Referencia para buscar el empleado por el cual buscar
     */
    private Empleado empleado;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        listenerCheck();
        listenerBotones();
        valoresIniciales();
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
                //Si no existen datos no continu el proceso
        if(listaResultado==null)
        {
            DialogoCodefac.mensaje("Advertencia","No existen datos para mostrar",DialogoCodefac.MENSAJE_ADVERTENCIA);
            return;
        }
        
        List<OrdenTrabajoData> listData=new ArrayList<OrdenTrabajoData>() ;
        
        for (OrdenTrabajoDetalle ordenTrabajoDetalle : listaResultado) 
        {
            OrdenTrabajoData ordenTrabajoData=new OrdenTrabajoData();
            ordenTrabajoData.setCliente(ordenTrabajoDetalle.getOrdenTrabajo().getCliente().getNombresCompletos());
            ordenTrabajoData.setCodigo(ordenTrabajoDetalle.getOrdenTrabajo().getId().toString());
            ordenTrabajoData.setDetalleStr(ordenTrabajoDetalle.getTitulo());
            ordenTrabajoData.setEstado(ordenTrabajoDetalle.getEstadoEnum().getNombre());
            ordenTrabajoData.setFechaIngreso(ordenTrabajoDetalle.getOrdenTrabajo().getFechaIngreso().toString());
            ordenTrabajoData.setIdentificacion(ordenTrabajoDetalle.getOrdenTrabajo().getCliente().getIdentificacion());     
            ordenTrabajoData.setEmpleado((ordenTrabajoDetalle.getOrdenTrabajo().getUsuario()!=null)?ordenTrabajoDetalle.getOrdenTrabajo().getUsuario().getNick():"");
            listData.add(ordenTrabajoData);
        }
        
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("fechaInicial", (getCmbFechaInicial().getDate()!=null)?getCmbFechaInicial().getDate().toString():"");
        parameters.put("fechaFinal", (getCmbFechaFinal().getDate()!=null)?getCmbFechaFinal().getDate().toString():"");
        
        DialogoCodefac.dialogoReporteOpciones(new ReporteDialogListener() {
            @Override
            public void excel() {
                
            }

            @Override
            public void pdf() {
                InputStream path = RecursoCodefac.JASPER_SERVICIO.getResourceInputStream("ordentTrabajoReporte.jrxml");
                ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, listData, panelPadre, "Reporte Ordenes Trabajo ", OrientacionReporteEnum.HORIZONTAL);
            }
        });
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
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true); //Habilito el boton de buscar
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    

    private void listenerBotones() {
        
        getBtnBuscarEmpleado().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmpleadoBusquedaDialogo empleadosBusquedaDialogo = new EmpleadoBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(empleadosBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                Empleado empleadoTemp = (Empleado) buscarDialogoModel.getResultado();
                if (empleadoTemp != null) {
                    empleado = empleadoTemp;
                    getTxtEmpleado().setText(empleado.toString());
                }
            }
        });
        
        getBtnConsultar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    OrdenTrabajoServiceIf serviceOrdenTrabajo=ServiceFactory.getFactory().getOrdenTrabajoServiceIf();
                    Date fechaInicial=getCmbFechaInicial().getDate();
                    Date fechaDateFinal=getCmbFechaFinal().getDate();
                    Departamento departamento=(Departamento) getCmbDepartamentos().getSelectedItem();
                    OrdenTrabajoDetalle.EstadoEnum estado=(OrdenTrabajoDetalle.EstadoEnum) getCmbEstado().getSelectedItem();
                    listaResultado=serviceOrdenTrabajo.consultarReporte(fechaInicial, fechaDateFinal,departamento,empleado,estado);
                    imprimir();
                } catch (RemoteException ex) {
                    Logger.getLogger(ReporteOrdenTrabajoModel.class.getName()).log(Level.SEVERE, null, ex);
                    DialogoCodefac.mensaje("Error","No existe comunicación con el servidor",DialogoCodefac.MENSAJE_INCORRECTO);
                } catch (ExcepcionCodefacLite ex) {
                    Logger.getLogger(ReporteOrdenTrabajoModel.class.getName()).log(Level.SEVERE, null, ex);
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
        
        getBtnHoy().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getCmbFechaInicial().setDate(UtilidadesFecha.getFechaHoy());
            }
        });
    }
    
    
    
    /*
    private void generarTabla()
    {
        //Si no existen datos en la tabla no se genera nada
        if(listaResultado==null)
        {
            return;
        }
        
        String[] tituloTabla={"Código","Identificación","Cliente","Fecha Ingreso,Estado,Detalles"};
        DefaultTableModel modeloTabla=new DefaultTableModel(tituloTabla,0);
        
        for (OrdenTrabajo ordenTrabajo : listaResultado) {
            modeloTabla.addRow(new String[]{ordenTrabajo.getId().toString(),
            ordenTrabajo.getCliente().getIdentificacion(),
            ordenTrabajo.getCliente().getNombresCompletos(),
            ordenTrabajo.getFechaIngreso().toString(),
            ordenTrabajo.getDetalleString()});
        }
        
        getTblDatos().setModel(modeloTabla);
        
    }*/

    private void valoresIniciales() {
        
        try {
            List<Departamento> departamentos= ServiceFactory.getFactory().getDepartamentoServiceIf().obtenerTodos(); //TODO: Filtrar solo departamentos activos
            getCmbDepartamentos().removeAllItems();
            for (Departamento departamento : departamentos)
            {                
                getCmbDepartamentos().addItem(departamento);
            }
            
            OrdenTrabajoDetalle.EstadoEnum[] estados=OrdenTrabajoDetalle.EstadoEnum.values();
            getCmbEstado().removeAllItems();
            for (OrdenTrabajoDetalle.EstadoEnum estado : estados) {
                getCmbEstado().addItem(estado);
            }
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(ReporteOrdenTrabajoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        //Valores iniciales
        //getChkDepartamentoTodos().setSelected(true);
        //getChkEmpleadoTodos().setSelected(true);
        getChkEstadoTodos().doClick();
        getChkDepartamentoTodos().doClick();
        getChkEmpleadoTodos().doClick();
    }

    private void listenerCheck() {
        getChkEmpleadoTodos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getChkEmpleadoTodos().isSelected())
                {
                    empleado=null;
                    getTxtEmpleado().setText("");
                    getBtnBuscarEmpleado().setEnabled(false);
                }
                else
                {
                    getBtnBuscarEmpleado().setEnabled(true);
                }
            }
        });
        
        getChkDepartamentoTodos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getChkDepartamentoTodos().isSelected())
                {
                    getCmbDepartamentos().setSelectedItem(null);
                    getCmbDepartamentos().setEnabled(false);
                }
                else
                {
                    getCmbDepartamentos().setEnabled(true);
                }
            }
        });
        
        getChkEstadoTodos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getChkEstadoTodos().isSelected())
                {
                    getCmbEstado().setSelectedItem(null);
                    getCmbEstado().setEnabled(false);
                }
                else
                {
                    getCmbEstado().setEnabled(true);
                }
            }
        });
        
    }
    
}
