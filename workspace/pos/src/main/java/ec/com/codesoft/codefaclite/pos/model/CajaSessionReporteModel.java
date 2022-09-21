/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.pos.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.pos.panel.CajaSessionReportePanel;
import ec.com.codesoft.codefaclite.pos.reportdata.CajaSessionReporteData;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import static ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha.fechaInicioMes;
import static ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha.hoy;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Robert
 */
public class CajaSessionReporteModel extends CajaSessionReportePanel 
{
    
    private DefaultTableModel modeloTablaCajasSession;

    private List<CajaSessionReporteData> dataReporte;

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        iniciarValores();
        listenerCombos();
        listenerBotones();
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

    public void generarConsulta() {
        try {
            Caja caja = (Caja) getCmbCaja().getSelectedItem();
            Usuario usuario = (Usuario) getCmbUsuario().getSelectedItem();
            java.sql.Date fechaInicial = (getDateFechaInicio().getDate() != null) ? new java.sql.Date(getDateFechaInicio().getDate().getTime()) : null;
            java.sql.Date fechaFinal = (getDateFechaFin().getDate() != null) ? new java.sql.Date(getDateFechaFin().getDate().getTime()) : null;
            
            List<CajaSession> cajasSession = (List<CajaSession>) ServiceFactory.getFactory().getCajaSesionServiceIf().obtenerCajaSessionPorCajaUsuarioYFecha(caja, usuario, fechaInicial, fechaFinal);
            
            dataReporte = new ArrayList<>();
            if(cajasSession != null && !cajasSession.isEmpty()){
                cajasSession.forEach(cs -> 
                {
                    dataReporte.add(
                            new CajaSessionReporteData(
                                    cs.getCaja().getNombre(),
                                    cs.getUsuario().getNick(),
                                    cs.getCaja().getSucursal().getNombre(),
                                    cs.getCaja().getPuntoEmision().getPuntoEmision().toString(),
                                    cs.getFechaHoraApertura().toString(),
                                    cs.getFechaHoraCierre().toString(),
                                    cs.getValorApertura().toString(),
                                    cs.getValorCierre().toString()
                            ));
                });
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(CajaSessionReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void imprimir() {
        
        InputStream path = RecursoCodefac.JASPER_POS.getResourceInputStream("reportePlantilla.jrxml");
        Map parameters = new HashMap();
        
        Caja caja = (Caja) getCmbCaja().getSelectedItem();
        Usuario usuario = (Usuario) getCmbUsuario().getSelectedItem();
       
        if (caja != null) {
            parameters.put("caja", caja.getNombre());
        } else {
            parameters.put("caja", "TODOS");
        }
        if (usuario != null) {
            parameters.put("usuario", usuario.getNick());
        } else {
            parameters.put("usuario", "TODOS");
        }
               
        DialogoCodefac.dialogoReporteOpciones(new ReporteDialogListener() 
        {
            @Override
            public void excel() 
            {
                try {
                    Excel excel = new Excel();
                    String[] nombreCabeceras = {" Caja ", " Usuario ", " Sucursal ", " Punto Emisión ", " Apertura caja ", " Cierre caja ", " Valor apertura ", "Valor cierre "};
                    excel.gestionarIngresoInformacionExcel(nombreCabeceras, dataReporte);
                    excel.abrirDocumento();
                } catch (IOException | IllegalArgumentException | IllegalAccessException ex) {
                    Logger.getLogger(CajaSessionReporteData.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void pdf() 
            {
                ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, dataReporte, panelPadre, "Sesiones de caja");
            }
        });
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

//    @Override
    public String getNombre() {
        return "Sessiones caja";
    }

    @Override
    public String getURLAyuda() {
        return "https://docs.google.com/document/d/e/2PACX-1vRxHiHd5vpEu1In25BKtCXigpl4m1phGAZwNR7Rh2Jm-Xqe7ffQpivlYJsMAWHFBS0BOnYxj4dpUi7H/pub?embedded=true#h.dc7l4ak532l6";
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     private void iniciarValores() {
        try {
           
            List<Caja> cajas = ServiceFactory.getFactory().getCajaServiceIf().buscarCajasPorSucursal(session);
            List<Usuario> usuarios = ServiceFactory.getFactory().getCajaPermisoServiceIf().buscarUsuariosPorSucursalYLigadosACaja(session.getSucursal(), cajas.get(0));
            
            UtilidadesComboBox.llenarComboBox(getCmbCaja(), cajas);
            UtilidadesComboBox.llenarComboBox(getCmbUsuario(), usuarios);
            
            modeloTablaCajasSession = new DefaultTableModel();
            getTblCajasSession().setModel(modeloTablaCajasSession);
            
            getDateFechaInicio().setDate(fechaInicioMes(hoy()));
            getDateFechaFin().setDate(hoy());
            
        } catch (RemoteException ex) {
            Logger.getLogger(CajaSessionReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void listenerCombos()
    {
        getCmbCaja().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try {
                    cargarUsuariosPorCajaEnCombo();
                } catch (RemoteException ex) {
                    Logger.getLogger(CajaSessionReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });        
    }
    
    private void cargarUsuariosPorCajaEnCombo() throws RemoteException
    {
        Caja caja = (Caja) getCmbCaja().getSelectedItem();
        List<Usuario> usuarios = ServiceFactory.getFactory().getCajaPermisoServiceIf().buscarUsuariosPorSucursalYLigadosACaja(session.getSucursal(), caja);
        UtilidadesComboBox.llenarComboBox(getCmbUsuario(), usuarios);
    }
    
    private void listenerBotones() 
    {
        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                
                Vector<String> titulo = new Vector<>();
                titulo.add("Caja");
                titulo.add("Usuario");
                titulo.add("Sucursal");
                titulo.add("Punto Emisión");
                titulo.add("Fecha Apertura");
                titulo.add("Fecha Cierre");
                titulo.add("Valor Apertura");
                titulo.add("Valor Cierre");
                
                modeloTablaCajasSession = new DefaultTableModel(titulo, 0);
                
                generarConsulta();
                
                dataReporte.forEach(csrd -> 
                {
                    Vector<String> fila = new Vector<>();
                    
                    fila.add(csrd.getNombreCaja());
                    fila.add(csrd.getNombreUsuario());
                    fila.add(csrd.getNombreSucursal());
                    fila.add(csrd.getNombrePuntoEmision());
                    fila.add(csrd.getFechaHoraAperturaCaja());
                    fila.add(csrd.getFechaHoraCierreCaja());
                    fila.add(csrd.getValorAperturaCaja());
                    fila.add(csrd.getValorCierreCaja());
                    
                    modeloTablaCajasSession.addRow(fila);
                });
                
                getTblCajasSession().setModel(modeloTablaCajasSession);

            }
        });
        
        getBtnLimpiarFechaInicio().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDateFechaInicio().setDate(null);
            }
        });

        getBtnLimpiarFechaFin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDateFechaFin().setDate(null);
            }
        });
    }
    
    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
