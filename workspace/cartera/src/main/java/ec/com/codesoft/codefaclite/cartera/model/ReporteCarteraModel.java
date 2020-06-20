/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.cartera.model;

import ec.com.codesoft.codefaclite.cartera.panel.ReporteCarteraPanel;
import ec.com.codesoft.codefaclite.cartera.reportdata.CarteraDocumentoData;
import ec.com.codesoft.codefaclite.cartera.reportdata.CuentasPorCobrarData;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CarteraEstadoReporteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoCategoriaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoDetalleEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ReporteCarteraModel extends ReporteCarteraPanel {

    private List<CarteraDocumentoData> resultadoData;
    private List<CarteraDocumentoData> resultadoDataDetalle;
    private Persona personaBusqueda;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        setTitle(VentanaEnum.REPORTE_CARTERA.getNombre());
        datosInicialesVista();
        listenerBotones();
        listenerCheckBox();
    
        //Cargar por defecto el boton de tipo de cartera
        listenerCmbTipoCartera();
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
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        
        
        if(resultadoData!=null)
        {
            
            DialogoCodefac.dialogoReporteOpciones( new ReporteDialogListener() {
                @Override
                public void excel() {
                    try{
                        Excel excel = new Excel();
                        String nombreCabeceras[] = CarteraDocumentoData.TITULO_REPORTE;
                        excel.gestionarIngresoInformacionExcel(nombreCabeceras, resultadoData);
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
                    TipoReporteEnum tipoReporteEnum=(TipoReporteEnum) getCmbTipoReporte().getSelectedItem();
                    Cartera.CarteraCategoriaEnum carteraCategoriaEnum =(Cartera.CarteraCategoriaEnum) getCmbDocumentoCategoriaCartera().getSelectedItem();
                    if(TipoReporteEnum.GENERAL.equals(tipoReporteEnum))
                    {
                        InputStream path=RecursoCodefac.JASPER_INVENTARIO.JASPER_CARTERA.getResourceInputStream("reporte_cartera.jrxml");
                        ReporteCodefac.generarReporteInternalFramePlantilla(path, mapParametros, resultadoData, panelPadre,carteraCategoriaEnum.getNombre());
                    }
                    else if(TipoReporteEnum.DETALLADO.equals(tipoReporteEnum))
                    {
                        InputStream path=RecursoCodefac.JASPER_INVENTARIO.JASPER_CARTERA.getResourceInputStream("reporte_cartera_detallado.jrxml");
                        ReporteCodefac.generarReporteInternalFramePlantilla(path, mapParametros, resultadoDataDetalle, panelPadre,carteraCategoriaEnum.getNombre());
                    }
                                        
                }
            });
        }
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

    private void datosInicialesVista() {

        UtilidadesComboBox.llenarComboBox(getCmbTipoCartera(), Cartera.TipoCarteraEnum.values());
        UtilidadesComboBox.llenarComboBox(getCmbTipoSaldo(), Cartera.TipoSaldoCarteraEnum.values());
        UtilidadesComboBox.llenarComboBox(getCmbTipoReporte(),TipoReporteEnum.values());
    }

    private void construirResultadoData(List<Cartera> datos) {
        if (datos != null) {
            resultadoData = new ArrayList<CarteraDocumentoData>();

            for (Cartera dato : datos) {
                resultadoData.add(new CarteraDocumentoData(
                        dato.getCodigo(),
                        dato.obtenerDescripciones(),
                        dato.getTotal().toString(),
                        dato.getSaldo().toString(),
                        dato.getPreimpreso(),
                        dato.getFechaEmision().toString(),
                        dato.getPersona().getRazonSocial(),
                        dato.getCarteraDocumentoEnum().getNombre(),
                        "",
                        "",
                        "",
                        ""
                ));
            }
        }
    }
    
    private void construirResultadoDataDetalle(List<Cartera> datos) {
        if (datos != null) {
            resultadoDataDetalle = new ArrayList<CarteraDocumentoData>();

            for (Cartera dato : datos) 
            {
                for (CarteraDetalle detalle : dato.getDetalles()) 
                {
                    String codigoDetalleDocumento="";
                    String nombreDetalleDocumento="";
                    String valorDetalle=detalle.getTotal().toString();
                    String saldoDetalle=detalle.getSaldo().toString();
                    
                    DocumentoDetalleEnum documentoDetalle=detalle.getCodigoDetalleDocumentoEnum();
                    if(documentoDetalle!=null)
                    {
                        codigoDetalleDocumento=documentoDetalle.getCodigo();
                        nombreDetalleDocumento=documentoDetalle.getNombre();
                    }
                    
                    resultadoDataDetalle.add(new CarteraDocumentoData(
                        dato.getCodigo(),
                        dato.obtenerDescripciones(),
                        dato.getTotal().toString(),
                        dato.getSaldo().toString(),
                        dato.getPreimpreso(),
                        dato.getFechaEmision().toString(),
                        dato.getPersona().getRazonSocial(),
                        dato.getCarteraDocumentoEnum().getNombre(),
                        codigoDetalleDocumento,
                        nombreDetalleDocumento,
                        valorDetalle,
                        saldoDetalle
                    ));
                }
                
            }
        }
        ordenarPorDocumentoDetalle(resultadoDataDetalle);
    }
    
    private void ordenarPorDocumentoDetalle(List<CarteraDocumentoData> detalles)
    {
        Collections.sort(detalles, new Comparator<CarteraDocumentoData>() {
            public int compare(CarteraDocumentoData o1, CarteraDocumentoData o2) {
                // TODO Auto-generated method stub
                
                return o1.getCodigoDetalleDocumento().compareTo(o2.getCodigoDetalleDocumento());
            }
        });
        
    }

    private void mostrarDatosTabla() {
        DefaultTableModel modeloTabla = new DefaultTableModel(new String[]{
            "Código",
            "Documento",
            "Descripción",
            "Nombres Persona",
            "Total",
            "Saldo",
            "Preimpreso",
            "Fecha Emisión",}, 0);

        for (CarteraDocumentoData carteraData : resultadoData) {
            modeloTabla.addRow(new String[]{
                carteraData.getCodigo(),
                carteraData.getDocumento(),
                carteraData.getDescripcion(),
                carteraData.getPersona(),
                carteraData.getValor(),
                carteraData.getSaldo(),
                carteraData.getPreimpreso(),
                carteraData.getFechaEmision()
            });
        }
        getTblDatos().setModel(modeloTabla);

    }
    
    private void btnListenerBuscarCliente() {
        ClienteEstablecimientoBusquedaDialogo clienteBusquedaDialogo= new ClienteEstablecimientoBusquedaDialogo(session);
        //ClienteBusquedaDialogo clienteBusquedaDialogo = new ClienteBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        //factura.setCliente((Persona) buscarDialogoModel.getResultado());
        PersonaEstablecimiento personaEstablecimientoTmp=(PersonaEstablecimiento) buscarDialogoModel.getResultado();
        Persona personaTemp=personaEstablecimientoTmp.getPersona();
        if(personaTemp!=null)
        {
            personaBusqueda=personaTemp;
            getTxtClienteProveedor().setText(personaBusqueda.toString());
        }
       
    }

    private void listenerBotones() {
        
        getBtnBuscarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnListenerBuscarCliente();
            }
        });

        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                java.sql.Date fechaInicio = (getCmbFechaInicio().getDate() != null) ? new java.sql.Date(getCmbFechaInicio().getDate().getTime()) : null;
                java.sql.Date fechaFin = (getCmbFechaFin().getDate() != null) ? new java.sql.Date(getCmbFechaFin().getDate().getTime()) : null;
                
                Cartera.CarteraCategoriaEnum carteraCategoriaEnum = (Cartera.CarteraCategoriaEnum) getCmbDocumentoCategoriaCartera().getSelectedItem();
                Cartera.TipoSaldoCarteraEnum saldoCarteraEnum=(Cartera.TipoSaldoCarteraEnum) getCmbTipoSaldo().getSelectedItem();
                //DocumentoCategoriaEnum.
                try {
                    List<Cartera> resultado = ServiceFactory.getFactory().getCarteraServiceIf().listaCarteraSaldoCero(
                            personaBusqueda,
                            fechaInicio,
                            fechaFin,
                            carteraCategoriaEnum.getDocumentoCategoriaEnum(),
                            (Cartera.TipoCarteraEnum) getCmbTipoCartera().getSelectedItem(),
                            saldoCarteraEnum,
                            Cartera.TipoOrdenamientoEnum.POR_RAZON_SOCIAL,
                            CarteraEstadoReporteEnum.TODO);

                    construirResultadoData(resultado);
                    construirResultadoDataDetalle(resultado);
                    mostrarDatosTabla();

                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(ReporteCarteraModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RemoteException ex) {
                    Logger.getLogger(ReporteCarteraModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        getCmbTipoCartera().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listenerCmbTipoCartera();
            }
        });
    }

    private void listenerCmbTipoCartera() {
        Cartera.TipoCarteraEnum tipoCarteraEnum = (Cartera.TipoCarteraEnum) getCmbTipoCartera().getSelectedItem();
        if (tipoCarteraEnum != null) {
            List<Cartera.CarteraCategoriaEnum> lista = Cartera.CarteraCategoriaEnum.buscarPorTipoCartera(tipoCarteraEnum);
            getCmbDocumentoCategoriaCartera().removeAllItems();
            for (Cartera.CarteraCategoriaEnum carteraDocumentoEnum : lista) {
                getCmbDocumentoCategoriaCartera().addItem(carteraDocumentoEnum);
            }
        }
    }

    private void listenerCheckBox() {
        getChkTodosClientes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listenerChkTodosClientes();
            }
        });
    }
    
    private void listenerChkTodosClientes()
    {
        if(getChkTodosClientes().isSelected())
        {
            getTxtClienteProveedor().setText("");
            getBtnBuscarCliente().setEnabled(false);
            personaBusqueda=null;           
            
        }
        else
        {
            getBtnBuscarCliente().setEnabled(true);            
        }
    }

    public enum TipoReporteEnum
    {
        GENERAL,
        DETALLADO;
    }
}
