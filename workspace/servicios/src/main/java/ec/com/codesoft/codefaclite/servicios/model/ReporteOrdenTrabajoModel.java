/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servicios.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servicios.data.OrdenTrabajoData;
import ec.com.codesoft.codefaclite.servicios.panel.ReporteOrdenTrabajoPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenTrabajoServiceIf;
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
    private List<OrdenTrabajo> listaResultado;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
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
                //Si no existen datos no continu el proceso
        if(listaResultado==null)
        {
            DialogoCodefac.mensaje("Advertencia","No existen datos para mostrar",DialogoCodefac.MENSAJE_ADVERTENCIA);
            return;
        }
        
        List<OrdenTrabajoData> listData=new ArrayList<OrdenTrabajoData>() ;
        
        for (OrdenTrabajo ordenTrabajo : listaResultado) 
        {
            OrdenTrabajoData ordenTrabajoData=new OrdenTrabajoData();
            ordenTrabajoData.setCliente(ordenTrabajo.getCliente().getNombresCompletos());
            ordenTrabajoData.setCodigo(ordenTrabajo.getId().toString());
            ordenTrabajoData.setDetalleStr(ordenTrabajo.getDetalleString());
            ordenTrabajoData.setEstado(ordenTrabajo.getEstado());
            ordenTrabajoData.setFechaIngreso(ordenTrabajo.getFechaIngreso().toString());
            ordenTrabajoData.setIdentificacion(ordenTrabajo.getCliente().getIdentificacion());     
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
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    

    private void listenerBotones() {
        getBtnConsultar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    OrdenTrabajoServiceIf serviceOrdenTrabajo=ServiceFactory.getFactory().getOrdenTrabajoServiceIf();
                    Date fechaInicial=getCmbFechaInicial().getDate();
                    Date fechaDateFinal=getCmbFechaFinal().getDate();
                    listaResultado=serviceOrdenTrabajo.consultarReporte(fechaInicial, fechaDateFinal);
                    generarTabla();
                } catch (RemoteException ex) {
                    Logger.getLogger(ReporteOrdenTrabajoModel.class.getName()).log(Level.SEVERE, null, ex);
                    DialogoCodefac.mensaje("Error","No existe comunicación con el servidor",DialogoCodefac.MENSAJE_INCORRECTO);
                }
            }
        });
    }
    
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
        
    }
    
}
