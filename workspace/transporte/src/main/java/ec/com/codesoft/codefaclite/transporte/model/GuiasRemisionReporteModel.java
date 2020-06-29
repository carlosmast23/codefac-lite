/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.transporte.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.TransportistaBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ControladorReporteGuiaRemision;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Transportista;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.GuiaRemisionServiceIf;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.GuiaTransporteData;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.transporte.panel.GuiasRemisionReportePanel;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
 ;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author CodesoftDesarrollo
 */
public class GuiasRemisionReporteModel extends GuiasRemisionReportePanel
{
    private Persona persona;
    private Producto producto;
    private Transportista transportista;
    //private List<GuiaRemision> listaConsulta;
    private ControladorReporteGuiaRemision controladorReporte;
    
    //private Date fechaInicial;
    //private Date fechaFinal;

    @Override
    public void iniciar() throws ExcepcionCodefacLite   {
        getDateFechaInicio().setDate(UtilidadesFecha.fechaInicioMes(UtilidadesFecha.hoy()));
        getDateFechaFin().setDate(UtilidadesFecha.hoy());
        
        for(ComprobanteEntity.ComprobanteEnumEstado enumerador : ComprobanteEntity.ComprobanteEnumEstado.values())
        {
            getCmbEstado().addItem(enumerador);
        }
        
        getChkTodosCliente().setSelected(true);
        if(getChkTodosCliente().isSelected())
        {
            persona = null;
            getTxtCliente().setText("");
            getBtnBuscarCliente().setEnabled(false);
        }
        
        getChkTodosTransportista().setSelected(true);
        if(getChkTodosTransportista().isSelected())
        {
            transportista = null;
            getTxtTransportista().setText("");
            getBtnBuscarTransportista().setEnabled(false);
        }
        
        getChkTodosProductos().setSelected(true);
        if(getChkTodosProductos().isSelected())
        {
            producto = null;
            getTxtProducto().setText("");
            getBtnBuscarProducto().setEnabled(false);
        }
        
        listenerBotones();
        listenerCheck();
        listenerFechas();
        listenerTablas();
        
        this.validacionDatosIngresados=false; //Desactivar validaciones automaticas en el formulario
        
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite   {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite   {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite   {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite   {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite   {
        imprimirReporte();
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite   {
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
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
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
    
    public void listenerBotones()
    {
        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarConsulta();
            }
        });
        
        getBtnBuscarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo(session.getEmpresa());
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                Producto productoTmp = (Producto) buscarDialogoModel.getResultado();
                
                if (productoTmp != null) {
                    getTxtProducto().setText(productoTmp.getNombre());
                    producto=productoTmp;
                }
            }
        });
        
        getBtnBuscarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteEstablecimientoBusquedaDialogo clienteBusquedaDialogo = new ClienteEstablecimientoBusquedaDialogo(session);
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                persona = ((PersonaEstablecimiento) buscarDialogoModel.getResultado()).getPersona();
                if (persona != null) 
                {
                    setearDatosCliente();
                }
            }
        });
        
        getBtnBuscarTransportista().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransportistaBusquedaDialogo transportistaBusquedaDialogo = new TransportistaBusquedaDialogo(session.getEmpresa());
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(transportistaBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                transportista = (Transportista) buscarDialogoModel.getResultado();
                if(transportista != null)
                {
                    setearDatosTransportista();
                }
                
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
    
    public void ejecutarConsulta()
    {
        try {
            GuiaRemisionServiceIf guiaRemisionServiceIf=ServiceFactory.getFactory().getGuiaRemisionServiceIf();
            Date fechaInicial =(getDateFechaInicio().getDate()!=null)?new java.sql.Date(getDateFechaInicio().getDate().getTime()):null;
            Date fechaFinal =(getDateFechaFin().getDate()!=null)?new java.sql.Date(getDateFechaFin().getDate().getTime()):null;
            ComprobanteEntity.ComprobanteEnumEstado estado=(ComprobanteEntity.ComprobanteEnumEstado) getCmbEstado().getSelectedItem();
            
            String codigoProducto=(producto!=null)?producto.getNombre():null;
            controladorReporte=new ControladorReporteGuiaRemision(
                    fechaInicial, 
                    fechaFinal, 
                    estado,
                    transportista,
                    persona,
                    codigoProducto,
                    session.getEmpresa());
            
            controladorReporte.generarReporte();
            //listaConsulta=guiaRemisionServiceIf.obtenerConsulta(fechaInicial,fechaFinal,estado);
            mostrarReporteTabla();
            //imprimirReporte();
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(GuiasRemisionReporteModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error", ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
        }
    }
    
    private void imprimirReporte()
    {
                
        if(controladorReporte.getListaConsulta()!=null)
        {
            DialogoCodefac.dialogoReporteOpciones(new ReporteDialogListener() {
                @Override
                public void excel() {
                    try {
                        /*Excel excel = new Excel();
                        String nombreCabeceras[] = getCabeceraReporteExcel();
                        excel.gestionarIngresoInformacionExcel(nombreCabeceras, controladorReporte.getListReporte());
                        excel.abrirDocumento();*/
                        controladorReporte.obtenerReporteExcel();
                    } catch (IOException ex) {
                        Logger.getLogger(GuiasRemisionReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(GuiasRemisionReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(GuiasRemisionReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                @Override
                public void pdf() {
                    try {
                        /*InputStream path = RecursoCodefac.JASPER_TRANSPORTE.getResourceInputStream("reporte_guiaRemision.jrxml");
                        Map parameters = new HashMap();
                        ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, listReporte, panelPadre, "Reporte Guía Remisión ", OrientacionReporteEnum.HORIZONTAL);*/
                        if(getChkReporteAgrupado().isSelected())
                        {                            
                            controladorReporte.obtenerReporteAgrupadoArchivoPdf(panelPadre);
                        }
                        else
                        {
                            controladorReporte.obtenerReportePdf(panelPadre);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(GuiasRemisionReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(GuiasRemisionReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(GuiasRemisionReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            });
        
        }
    }
    
    private String[] getCabeceraReporte()
    {
        return new String[]{"Preimpreso","Transportista","Identificación","Estado","FechaInicio","FechaFin","Dir Partida","Facturas","Placa","#Items"};
    }
    
    private void mostrarReporteTabla()
    {
        String[] titulos=getCabeceraReporte();
        
        if(controladorReporte.getListaConsulta()!=null)
        {
            DefaultTableModel modeloTabla=new DefaultTableModel(titulos,0);
            
            for (GuiaRemision guiaRemision : controladorReporte.getListaConsulta()) 
            {
                String[] fila={
                    guiaRemision.getPreimpreso(),
                    guiaRemision.getRazonSocial(),
                    guiaRemision.getIdentificacion(),
                    guiaRemision.getEstadoEnum().getNombre(),
                    guiaRemision.getFechaIniciaTransporte().toString(),
                    guiaRemision.getFechaFinTransporte().toString(),
                    guiaRemision.getDireccionPartida(),
                    UtilidadesLista.castListToString(guiaRemision.obtenerFacturasTransportadas(),","),
                    guiaRemision.getPlaca(),
                    guiaRemision.obtenerTotalItems().toString(),
                    
                };
                
                modeloTabla.addRow(fila);
            }
            
            getTblDocumentos().setModel(modeloTabla);
        
        }
        
    
    }
   
    public void listenerCheck()
    {   
        getChkTodosCliente().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    persona = null;
                    getTxtCliente().setText("...");
                    getBtnBuscarCliente().setEnabled(false);
                } else {
                    getBtnBuscarCliente().setEnabled(true);
                }
            }
        });
        
        getChkTodosTransportista().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED)
                {
                    transportista = null;
                    getTxtTransportista().setText("...");
                    getBtnBuscarTransportista().setEnabled(false);
                } else {
                    getBtnBuscarTransportista().setEnabled(true);
                }   
            }
        });
        
        getChkTodosProductos().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED)
                {
                    producto = null;
                    getTxtProducto().setText("");
                    getBtnBuscarProducto().setEnabled(false);
                } else {
                    getBtnBuscarProducto().setEnabled(true);
                }
            }
        });
    }
    
    public void listenerFechas()
    {
        
    }
    
    public void setearDatosCliente()
    {
        getTxtCliente().setText("" + persona.getIdentificacion()+ " - " + persona.getNombresCompletos());
    }
    
    public void setearDatosTransportista()
    {
        getTxtTransportista().setText(" " + transportista.getIdentificacion()+ " - " + transportista.getApellidos() +" "+ transportista.getNombres());
    }
    
    private void listenerTablas() {
        JPopupMenu jpopMenuItem=new JPopupMenu();
        JMenuItem itemRide= new JMenuItem("RIDE");
        itemRide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada=getTblDocumentos().getSelectedRow();
                if(filaSeleccionada>=0)
                {
                    panelPadre.cambiarCursorEspera();
                    
                    try {
                        String claveAcceso=controladorReporte.getListReporte().get(filaSeleccionada).getClaveAcceso();//                    String claveAcceso = this.factura.getClaveAcceso();
                        byte[] byteReporte = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(claveAcceso,session.getEmpresa());
                        JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(byteReporte);
                        panelPadre.crearReportePantalla(jasperPrint,controladorReporte.getListReporte().get(filaSeleccionada).getPreimpreso());
                    }catch (IOException ex) {
                        Logger.getLogger(GuiasRemisionReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(GuiasRemisionReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    panelPadre.cambiarCursorNormal();
                    
                }
            }
        });
        jpopMenuItem.add(itemRide);
                
        getTblDocumentos().setComponentPopupMenu(jpopMenuItem);
    }
    
  
    
}
