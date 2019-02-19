package ec.com.codesoft.codefaclite.compra.model;

import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.compra.panel.RetencionReportePanel;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ReporteRetencionesData;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ValorRetencionesData;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ControladorReporteRetencion;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RetencionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionIvaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionRentaServiceIf;
import static ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha.fechaInicioMes;
import static ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha.formatDate;
import static ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha.hoy;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author CodesoftDesarrollo
 */
public class RetencionReporteModel extends RetencionReportePanel {

    private ControladorReporteRetencion controladorReporte;
    //private List<ReporteRetencionesData> dataReporte;
    
    /**
     * Carga los tipos de retencion definida por el SRI
     */
    private Map<String,SriRetencion> mapTipoRetencion;
    
    private Persona proveedor = null;
    SriRetencionIva sriRetencionIva = null;
    SriRetencionRenta sriRetencionRenta = null;

    Map<String, Object> parameters = new HashMap<String, Object>();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private List<RetencionDetalle> dataretencion;
    Date fechaInicio = null;
    Date fechaFin = null;
    String fechainicio = "";
    String fechafin = "";
    //boolean banderaTipo = true, 
    boolean banderaIva = true, banderaRenta = true;

    private static final Logger LOG = Logger.getLogger(RetencionReporteModel.class.getName());

    public RetencionReporteModel() {
        
    }
    

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        variablesIniciales();
        listenerCheckBox();
        listenerBotones();
        super.validacionDatosIngresados=false;
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

    @Override
    public void imprimir() {
            
            DialogoCodefac.dialogoReporteOpciones( new ReporteDialogListener() {
                @Override
                public void excel() {
                    try{
                        /*Excel excel = new Excel();
                        String nombreCabeceras[] = {"Clave de Acceso","Preimpreso","Proveedor","Fecha","Estado","Tipo","Compra", "Base Imponible","Porcentaje", "Código", "Valor"};
                        excel.gestionarIngresoInformacionExcel(nombreCabeceras, dataReporte);
                        excel.abrirDocumento();*/
                        controladorReporte.obtenerReporteExcel();
                    }
                    catch(Exception exc)
                    {
                        exc.printStackTrace();
                        DialogoCodefac.mensaje("Error","El archivo Excel se encuentra abierto",DialogoCodefac.MENSAJE_INCORRECTO);
                    }  
                }

                @Override
                public void pdf() {
                    controladorReporte.obtenerReportePdf(panelPadre);
                    //ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, dataReporte, panelPadre, "Reporte Retenciones ");                    
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
        

        getDateFechaInicio().setDate(fechaInicioMes(hoy()));
        getDateFechaFin().setDate(hoy());

        getChkTodos().setSelected(true);
        if (getChkTodos().isSelected()) {
            proveedor = null;
            getTxtProveedor().setText("...");
            getBtnBuscarProveedor().setEnabled(false);
        }

        

        getChkTodosIva().setSelected(true);
        if (getChkTodosIva().isSelected()) {
            sriRetencionIva = null;
            banderaIva = true;
            getCmbRetencionIva().setEnabled(false);
        }

        
        getChkTodosRenta().setSelected(true);
        if (getChkTodosRenta().isSelected()) {
            sriRetencionRenta = null;
            banderaRenta = true;
            getCmbRetencionRenta().setEnabled(false);
        }

        
        getChkTodosTipo().setSelected(true);
        if (getChkTodosTipo().isSelected()) {
            //banderaTipo = true;
            getCmbTipo().setEnabled(false);
        }
    }

//    @Override
    public String getNombre() {
        return "Reporte Retenciones";
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
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void variablesIniciales() {
        
        //==================// Cargar los estados disponibles //
        getCmbEstado().removeAllItems();
        for (ComprobanteEntity.ComprobanteEnumEstado comprobanteEstado : ComprobanteEntity.ComprobanteEnumEstado.values()) {
            getCmbEstado().addItem(comprobanteEstado);
        }
        
        //===================//Agregar los tipos de retencion Iva//==========================//
        getCmbRetencionIva().removeAllItems();
        SriRetencionIvaServiceIf sriRetencionIvaService = ServiceFactory.getFactory().getSriRetencionIvaServiceIf();
        try {
            List<SriRetencionIva> tipoRetencionesIva = sriRetencionIvaService.obtenerTodosOrdenadoPorCodigo();
            for (SriRetencionIva tipoRetencione : tipoRetencionesIva) {
                getCmbRetencionIva().addItem(tipoRetencione);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //==================/Agregar los tipos de retencion Renta/===================//
        getCmbRetencionRenta().removeAllItems();
        SriRetencionRentaServiceIf sriRetencionRentaService = ServiceFactory.getFactory().getSriRetencionRentaServiceIf();
        try {
            List<SriRetencionRenta> tipoRetencionesRenta = sriRetencionRentaService.obtenerTodosOrdenadoPorCodigo();
            for (SriRetencionRenta sriRetencionRenta : tipoRetencionesRenta) {
                getCmbRetencionRenta().addItem(sriRetencionRenta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //================/ CARGAR LOS TIPOS DE RETENCIONES PARA CLASIFICAR /=============//
        try {
            getCmbTipo().removeAllItems();
            //mapTipoRetencion=new HashMap<String,SriRetencion>();
            List<SriRetencion> retenecionesTipo=ServiceFactory.getFactory().getSriRetencionServiceIf().obtenerTodos();
            for (SriRetencion sriRetencion : retenecionesTipo) {
                //mapTipoRetencion.put(sriRetencion.getCodigo(),sriRetencion);
                getCmbTipo().addItem(sriRetencion);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(RetencionReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //============/ Agregar los valores de tipo de retenciones que puede manejas /==============//
        
    }

    private void listenerCheckBox() {
        getChkTodos().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    proveedor = null;
                    getTxtProveedor().setText("...");
                    getBtnBuscarProveedor().setEnabled(false);
                } else {
                    getBtnBuscarProveedor().setEnabled(true);
                }
            }
        });
        
        getChkTodosIva().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    sriRetencionIva = null;
                    banderaIva = true;
                    getCmbRetencionIva().setEnabled(false);
                } else {
                    banderaIva = false;
                    getCmbRetencionIva().setEnabled(true);
                }
            }
        });
        
        getChkTodosRenta().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    sriRetencionRenta = null;
                    banderaRenta = true;
                    getCmbRetencionRenta().setEnabled(false);
                } else {
                    banderaRenta = false;
                    getCmbRetencionRenta().setEnabled(true);
                }
            }
        });
        
        getChkTodosTipo().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    //banderaTipo = true;
                    getCmbTipo().setEnabled(false);
                } else {
                    //banderaTipo = false;
                    getCmbTipo().setEnabled(true);
                }
            }
        });
    }
    
    
    private void construirTablaTotalesRenta()
    {       
        Vector<String> titulo3 = new Vector<>();
        titulo3.add("Código");
        titulo3.add("Valor");
        DefaultTableModel modeloTablaRetRenta = new DefaultTableModel(titulo3, 0);
        //List<Object[]> dataRetencionCodigoRenta = fs.obtenerRetencionesCodigo(proveedor, fechaInicio, fechaFin, sriRetencionIva, sriRetencionRenta, "1");
        for (Map.Entry<String, BigDecimal> entry : controladorReporte.getMapSumatoriaRetencionRenta().entrySet()) {
            String key = entry.getKey();
            BigDecimal value = entry.getValue();
            
            Vector<String> fila = new Vector<String>();            
            fila.add(key);
            fila.add(value.toString());
            
            modeloTablaRetRenta.addRow(fila);
        }
            
        getTblRetRenta().setModel(modeloTablaRetRenta);

    }
    
    private void construirTablaTotalesIva()
    {
        
        Vector<String> titulo2 = new Vector<>();
        titulo2.add("Código");
        titulo2.add("Valor");
        DefaultTableModel modeloTablaRetIva = new DefaultTableModel(titulo2, 0);
        //List<Object[]> dataRetencionCodigo = fs.obtenerRetencionesCodigo(proveedor, fechaInicio, fechaFin, sriRetencionIva, sriRetencionRenta, "2");
        for (Map.Entry<String, BigDecimal> entry : controladorReporte.getMapSumatoriaRetencionIva().entrySet()) {
            String key = entry.getKey();
            BigDecimal value = entry.getValue();
            
            Vector<String> fila = new Vector<String>();            
            fila.add(key);
            fila.add(value.toString());
            modeloTablaRetIva.addRow(fila);
            
        }
        getTblRetIva().setModel(modeloTablaRetIva);
    }
    
    private void construirTablaRetenciones()
    {
        Vector<String> titulo = new Vector<>();
        titulo.add("Preimpreso");
        titulo.add("Proveedor");
        titulo.add("Fecha");
        titulo.add("Estado");
        titulo.add("Tipo");
        titulo.add("Compra Preimpreso");
        titulo.add("Base imponble");
        titulo.add("Porcentaje");
        titulo.add("Código");
        titulo.add("Valor");
        
        DefaultTableModel modeloTablaRetenciones = new DefaultTableModel(titulo, 0);
        
        for (ReporteRetencionesData data : controladorReporte.getDataReporte()) {
            Vector<String> fila = new Vector<String>();
            fila.add(data.getPreimpresoRetencion());
            fila.add(data.getProveedor());
            fila.add(data.getFecha());
            fila.add(data.getEstado());
            fila.add(data.getTipo());
            fila.add(data.getPreimpresoRetencionCompra());            
            fila.add(data.getBaseRetencion());
            fila.add(data.getPorcentajeRetencion());
            fila.add(data.getCodigoRetencion());
            fila.add(data.getValorRetencion());

            modeloTablaRetenciones.addRow(fila);
        }
        /*
        for (RetencionDetalle retencion : dataretencion) {
            Vector<String> fila = new Vector<String>();
            fila.add(retencion.getRetencion().getPreimpreso());
            fila.add(retencion.getRetencion().getRazonSocial());
            fila.add(retencion.getRetencion().getFechaEmision().toString());
            fila.add(retencion.getRetencion().getEstadoEnum().getNombre());
            fila.add(mapTipoRetencion.get(retencion.getCodigoSri()).getNombre());
            fila.add(retencion.getRetencion().getPreimpresoDocumentoFormato());
            fila.add(retencion.getBaseImponible().toString());
            fila.add(retencion.getPorcentajeRetener().toString() + " %");
            fila.add(retencion.getCodigoRetencionSri());
            fila.add(retencion.getValorRetenido().toString());

            modeloTablaRetenciones.addRow(fila);

        }*/
        
        getTblRetenciones().setModel(modeloTablaRetenciones);
        //UtilidadesTablas.cambiarTamanioColumnas(getTblRetenciones(),new Integer[]{100});
        UtilidadesTablas.definirTamanioColumnas(getTblRetenciones(),new Integer[]{130});
        
    }

    private void listenerBotones() {
        
        getBtnLimpiarFechaInicial().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDateFechaInicio().setDate(null);
            }
        });
        
        getBtnLimpiarFechaFinal().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDateFechaFin().setDate(null);
            }
        });
        
        getBtnBuscarProveedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProveedorBusquedaDialogo proveedorBusquedaDialogo = new ProveedorBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(proveedorBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                proveedor = ((PersonaEstablecimiento) buscarDialogoModel.getResultado()).getPersona();
                if (proveedor != null) {
                    String identificacion = proveedor.getIdentificacion();
                    String nombre = proveedor.getRazonSocial();
                    getTxtProveedor().setText(identificacion + " - " + nombre);
                }
            }
        });

        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getDateFechaInicio().getDate() != null) {
                    fechaInicio = new Date(getDateFechaInicio().getDate().getTime());
                } else {
                    fechaInicio = null;
                }
                if (getDateFechaFin().getDate() != null) {
                    fechaFin = new Date(getDateFechaFin().getDate().getTime());
                } else {
                    fechaFin = null;
                }
                if (banderaIva == false) {
                    sriRetencionIva = (SriRetencionIva) getCmbRetencionIva().getSelectedItem();
                }
                if (banderaRenta == false) {
                    sriRetencionRenta = (SriRetencionRenta) getCmbRetencionRenta().getSelectedItem();
                }
                SriRetencion sriRetencion = null;
                if (getChkTodosTipo().isSelected() == true) {
                    sriRetencion = null;
                } else {
                    sriRetencion = (SriRetencion) getCmbTipo().getSelectedItem();
                }
                
                //ControladorReporteRetencion controladorReporte=new ControladorReporteRetencion(proveedor, fechaInicio, fechaFin, sriRetencionIva, sriRetencionRenta, sriRetencion,estadoEnum);
                //RetencionServiceIf fs = ServiceFactory.getFactory().getRetencionServiceIf();
                
                ComprobanteEntity.ComprobanteEnumEstado estadoEnum=(ComprobanteEntity.ComprobanteEnumEstado) getCmbEstado().getSelectedItem();
                controladorReporte=new ControladorReporteRetencion(proveedor, fechaInicio, fechaFin, sriRetencionIva, sriRetencionRenta, sriRetencion,estadoEnum);
                controladorReporte.generarReporte();
                construirTablaRetenciones();
                construirTablaTotalesIva();
                construirTablaTotalesRenta();

            }
        });
    }

}
