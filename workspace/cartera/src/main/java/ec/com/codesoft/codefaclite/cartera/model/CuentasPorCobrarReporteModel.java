/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.cartera.model;

import ec.com.codesoft.codefaclite.cartera.panel.CuentasPorCobarReportePanel;
import ec.com.codesoft.codefaclite.cartera.reportdata.CuentasPorCobrarData;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.reportes.UtilidadReporteJasper;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CarteraEstadoReporteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CategoriaMenuEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoCategoriaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraServiceIf;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.sql.Date;
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
 * @author DesarrolloSoftware
 */
public class CuentasPorCobrarReporteModel extends CuentasPorCobarReportePanel
{
    private Persona persona;
    private boolean banderaTodos;
    private List<Cartera> carteraResultado;
    
    private TipoReporteJasperEnum tipoReporteJasperSeleccionado;
    private List<TipoReporteJasperEnum> tipoReporteJasperList;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        addListenerBotones();
        addListenerCheck();
        iniciarDatosVariables();
        initDatosTabla();        
        getDateFechaInicio().setDate(null);
        getDateFechaFin().setDate(new java.util.Date());
        UtilidadesComboBox.llenarComboBox(getCmbTipoReporteCartera(),CarteraEstadoReporteEnum.values());
    }
    
    private void iniciarDatosVariables()
    {        
        tipoReporteJasperList=UtilidadesLista.arrayToList(TipoReporteJasperEnum.values());;
        tipoReporteJasperSeleccionado=TipoReporteJasperEnum.NORMAL;
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
    
    private String[] obtenerCabecera()
    {
        String[] nombreCabeceras = {
                            "Código", 
                            "Documento",
                            "Preimpreso",
                            "Fecha",
                            "Identificación",
                            "Razón Social",
                            "Nombre Comercial",
                            "Total",
                            "Saldo",
                        };
        return nombreCabeceras;
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        InputStream path=RecursoCodefac.JASPER_INVENTARIO.JASPER_CARTERA.getResourceInputStream("cuentas_por_cobrar.jrxml");
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        
        if(carteraResultado!=null)
        {
            List<CuentasPorCobrarData> resultadoReporte=CuentasPorCobrarData.castCuentasPorCobrar(carteraResultado);
            
            DialogoCodefac.dialogoReporteOpciones( new ReporteDialogListener() {
                @Override
                public void excel() {
                    try{
                        Excel excel = new Excel();
                        String nombreCabeceras[] = obtenerCabecera();
                        excel.gestionarIngresoInformacionExcel(nombreCabeceras, resultadoReporte);
                        excel.abrirDocumento();
                    }
                    catch(Exception exc)
                    {
                        exc.printStackTrace();
                        DialogoCodefac.mensaje("Error","El archivo Excel se encuentra abierto",DialogoCodefac.MENSAJE_INCORRECTO);
                    }  
                }

                @Override
                public void pdf() {
                    
                    
                    String tituloSecundario=tipoReporteJasperSeleccionado.getNombreReporte();
                    UtilidadReporteJasper.obtenerDatosReporteAgrupado(tipoReporteJasperSeleccionado,resultadoReporte);
                    
                    ReporteCodefac.generarReporteInternalFramePlantilla(path, mapParametros, resultadoReporte, panelPadre,obtenerTituloReporte()+" "+tituloSecundario,OrientacionReporteEnum.HORIZONTAL);
                    //dispose();
                    //setVisible(false);
                }
            });
        }
    }
    
    public String obtenerTituloReporte()
    {
        return "Cuentas Por Cobrar";
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
    
    public void addListenerBotones()
    {
        getBtnBuscarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                ClienteEstablecimientoBusquedaDialogo buscarBusquedaDialogo = new ClienteEstablecimientoBusquedaDialogo(session);
                BuscarDialogoModel buscarDialogo = new BuscarDialogoModel(buscarBusquedaDialogo);
                buscarDialogo.setVisible(true);
                PersonaEstablecimiento personaTemp = (PersonaEstablecimiento) buscarDialogo.getResultado();
                if(personaTemp != null)
                {
                   persona = personaTemp.getPersona();
                   getTxtCliente().setText(persona.getIdentificacion() + " - " + persona.getNombreSimple() );
                }
            }
        });
        
        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
                try {
                    CarteraServiceIf carteraServiceIf = ServiceFactory.getFactory().getCarteraServiceIf();
                    /*if(banderaTodos)
                    {
                        List<Cartera> carteras = carteraServiceIf.listaCarteraSaldoCero(persona, new Date(getDateFechaInicio().getDate().getTime()), new Date(getDateFechaFin().getDate().getTime()));
                    }else{
                        List<Cartera> carteras = carteraServiceIf.listaCarteraSaldoCero(persona, new Date(getDateFechaInicio().getDate().getTime()), new Date(getDateFechaFin().getDate().getTime()));
                    }*/
                    java.sql.Date fechaInicio=null;
                    if(getDateFechaInicio().getDate()!=null)
                    {
                        fechaInicio=new Date(getDateFechaInicio().getDate().getTime());
                    }
                    
                    
                    java.sql.Date fechaFin=null;
                    if(getDateFechaFin().getDate()!=null)
                    {
                        fechaFin=new Date(getDateFechaFin().getDate().getTime());
                    }
                    
                    
                    List<Cartera> carteras = carteraServiceIf.listaCarteraSaldoCero(
                            persona, 
                            null,
                            fechaInicio, 
                            fechaFin,
                            DocumentoCategoriaEnum.COMPROBANTES_VENTA,
                            getTipoCarteraEnum(),
                            Cartera.TipoSaldoCarteraEnum.CON_SALDO,
                            Cartera.TipoOrdenamientoEnum.POR_PREIMPRESO, 
                            (CarteraEstadoReporteEnum) getCmbTipoReporteCartera().getSelectedItem(),
                            session.getSucursal(),
                            null);
                    
                    carteraResultado=carteras;
                    mostrarDatosTabla(carteras);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(CuentasPorCobrarReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RemoteException ex) {
                    Logger.getLogger(CuentasPorCobrarReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            }
        });
    }
    
    public Cartera.TipoCarteraEnum getTipoCarteraEnum()
    {
        return Cartera.TipoCarteraEnum.CLIENTE;
    }

    public TipoReporteJasperEnum getTipoReporteJasperSeleccionado() {
        return tipoReporteJasperSeleccionado;
    }

    public void setTipoReporteJasperSeleccionado(TipoReporteJasperEnum tipoReporteJasperSeleccionado) {
        this.tipoReporteJasperSeleccionado = tipoReporteJasperSeleccionado;
    }

    public List<TipoReporteJasperEnum> getTipoReporteJasperList() {
        return tipoReporteJasperList;
    }

    public void setTipoReporteJasperList(List<TipoReporteJasperEnum> tipoReporteJasperList) {
        this.tipoReporteJasperList = tipoReporteJasperList;
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
        
        String[] titulo=obtenerCabecera();
        DefaultTableModel defaultTableModel = new DefaultTableModel(titulo,0);
        List<CuentasPorCobrarData> resultadoImprimir=CuentasPorCobrarData.castCuentasPorCobrar(carteras);
        BigDecimal saldoPendiente=BigDecimal.ZERO;
        for (CuentasPorCobrarData cuentaPorCobrarData : resultadoImprimir) {
            Vector<String> fila=new Vector<String>();
            fila.add(cuentaPorCobrarData.getCodigo());
            fila.add(cuentaPorCobrarData.getDocumento());
            fila.add(cuentaPorCobrarData.getPreimpreso());
            fila.add(cuentaPorCobrarData.getFechaEmision());
            fila.add(cuentaPorCobrarData.getIdentificacion());
            fila.add(cuentaPorCobrarData.getRazonSocial());
            fila.add(cuentaPorCobrarData.getNombreComercial());            
            fila.add(cuentaPorCobrarData.getTotal());
            fila.add(cuentaPorCobrarData.getSaldo());
            
            saldoPendiente=saldoPendiente.add(new BigDecimal(cuentaPorCobrarData.getSaldo()));
            defaultTableModel.addRow(fila);
        }
        
        getLblTotal().setText(""+saldoPendiente);
        getTableCuentasPorCobrar().setModel(defaultTableModel);
        
    }
     

    
}
