/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.cartera.model;

import ec.com.codesoft.codefaclite.cartera.panel.MovimientoCarteraPanel;
import ec.com.codesoft.codefaclite.cartera.reportdata.CruceCarteraData;
import ec.com.codesoft.codefaclite.cartera.reportdata.CuentasPorCobrarData;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteBusquedaDialogo;
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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraCruce;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CarteraEstadoReporteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoCategoriaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraServiceIf;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class MovimientoCarteraModel extends MovimientoCarteraPanel{
    
    /**
     * Referencia para guardar el cliente o proveedor para hacer el filtro para el movimiento de cartera
     */
    private Persona personaFiltro;
    
    
    /**
     * Map que agrupa la cartera por cliente y sus lista de cruces
     */
    //private Map<Cartera,List<CarteraCruce>> mapMovimientoCartera;
    private List<Cartera> carteraResultado;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        listenerCheckBox();
        listerBotones();
        valoresInicialesPantalla();
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
        InputStream path=RecursoCodefac.JASPER_INVENTARIO.JASPER_CARTERA.getResourceInputStream("movimiento_cartera.jrxml");
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        
        List<CruceCarteraData> resultadoReporte=CruceCarteraData.castData(carteraResultado);
        DialogoCodefac.dialogoReporteOpciones(new ReporteDialogListener() {
            @Override
            public void excel() {
                try {
                    Excel excel = new Excel();
                    String nombreCabeceras[] = obtenerCabecera();
                    //excel.gestionarIngresoInformacionExcel(nombreCabeceras, resultadoReporte);
                    //excel.abrirDocumento();
                } catch (Exception exc) {
                    exc.printStackTrace();
                    DialogoCodefac.mensaje("Error", "El archivo Excel se encuentra abierto", DialogoCodefac.MENSAJE_INCORRECTO);
                }
            }

            @Override
            public void pdf() {
                ReporteCodefac.generarReporteInternalFramePlantilla(path, mapParametros, resultadoReporte, panelPadre,"Movimiento Cartera");
                //dispose();
                //setVisible(false);
            }

            
        });
    }
    
    private String[] obtenerCabecera() {
        return new String[]{"","",""};
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

    private void valoresInicialesPantalla() {
        getCmbTipoCartera().removeAllItems();
        for (Cartera.TipoCarteraEnum carteraEnum : Cartera.TipoCarteraEnum.values()) {
            getCmbTipoCartera().addItem(carteraEnum);
        }
        
        getChkTodos().setSelected(true);
    }

    private void listerBotones() {
        getBtnBuscarPersona().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnListenerBuscarCliente();
            }
        });
        
        
        getBtnConsultar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listenerBotonConsultar();
            }
        });
    }
    
    private void listenerBotonConsultar()
    {
        try {
            CarteraServiceIf carteraServiceIf = ServiceFactory.getFactory().getCarteraServiceIf();
            Date fechaInicial=null;
            if(getCmbFechaInicio().getDate()!=null)
            {
                fechaInicial=new Date(getCmbFechaInicio().getDate().getTime());
            }
                 
            Date fechaFinal=null;
            if(getCmbFechaFin().getDate()!=null)
            {
                fechaFinal=new Date(getCmbFechaFin().getDate().getTime());
            }
                        
            //List<CarteraCruce> cruces=ServiceFactory.getFactory().getCarteraServiceIf().consultarMovimientoCartera(personaFiltro);
            carteraResultado= carteraServiceIf.listaCarteraSaldoCero(personaFiltro,fechaInicial, fechaFinal,DocumentoCategoriaEnum.COMPROBANTES_VENTA, (Cartera.TipoCarteraEnum) getCmbTipoCartera().getSelectedItem(),Cartera.TipoSaldoCarteraEnum.TODOS,Cartera.TipoOrdenamientoEnum.POR_FECHA,CarteraEstadoReporteEnum.TODO,session.getSucursal());
            //mapMovimientoCartera=convertirCrucesEnMap(cruces);
            construirTablaMovimientoCartera();
            
        } catch (RemoteException ex) {
            Logger.getLogger(MovimientoCarteraModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(MovimientoCarteraModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void construirTablaMovimientoCartera()
    {
        String[] titulo={"identificación","Nombres","Documento","Preimpreso","Debe","Haber"};
        DefaultTableModel modeloTabla=new DefaultTableModel(titulo,0);
        
        for (Cartera cartera : carteraResultado) {
            List<CarteraCruce> cruces = cartera.getCruces();
            
            String[] filaTitulo={cartera.getPersona().getIdentificacion(),cartera.getPersona().getRazonSocial(),"Factura",cartera.getPreimpreso(),cartera.getTotal().toString(),""};
            modeloTabla.addRow(filaTitulo);
            
            for (CarteraCruce cruceTmp : cruces) {
                String[] filaCruce={"","","Abono","","",cruceTmp.getValor().toString(),""};
                modeloTabla.addRow(filaCruce);
            }
            
            //Saldos y valores cancelado
            String[] filaSaldo={"","","","Debe","0",cartera.getTotal().subtract(cartera.getSaldo()).toString()};
            modeloTabla.addRow(filaSaldo);
            String[] filaHaber={"","","","Haber","0",cartera.getSaldo().toString()};
            modeloTabla.addRow(filaHaber);
            
            //Espacio en blanco para manejar un formato bonito
            modeloTabla.addRow(new String[]{});
            modeloTabla.addRow(new String[]{});
        }
        
        
                
        getTblDatos().setModel(modeloTabla);
    
    }
    
    private Map<Cartera,List<CarteraCruce>> convertirCrucesEnMap(List<CarteraCruce> cruces)
    {
        Map<Cartera,List<CarteraCruce>> mapMovimientoCartera=new HashMap<Cartera,List<CarteraCruce>>();
        
        for (CarteraCruce cruce : cruces) {
            
            List<CarteraCruce> crucesTmp=mapMovimientoCartera.get(cruce.getCarteraAfectada());
            if(crucesTmp==null)
            {
                //Cuando no existe en el map cree el array e agrego al map
                crucesTmp=new ArrayList<CarteraCruce>();
                crucesTmp.add(cruce);
                mapMovimientoCartera.put(cruce.getCarteraAfectada(),crucesTmp);
            }
            else
            {
                //Si ya existe la lista de cruces creada solo agrego 
                crucesTmp.add(cruce);                
            }                        
        }
        return mapMovimientoCartera;
    }
    
    private void btnListenerBuscarCliente() {
        //ClienteBusquedaDialogo clienteBusquedaDialogo = new ClienteBusquedaDialogo();
        ClienteEstablecimientoBusquedaDialogo clienteBusquedaDialogo= new ClienteEstablecimientoBusquedaDialogo(session);
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        //factura.setCliente((Persona) buscarDialogoModel.getResultado());
        PersonaEstablecimiento personaEstablecimientoTmp=(PersonaEstablecimiento) buscarDialogoModel.getResultado();
        Persona personaTemp=(Persona)personaEstablecimientoTmp.getPersona();
        if(personaTemp!=null)
        {
            personaFiltro=personaTemp;
            getTxtPersonaFiltro().setText(personaFiltro.toString());
        }
       
    }

    private void listenerCheckBox() {
        getChkTodos().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    personaFiltro = null;
                    getTxtPersonaFiltro().setText("");
                    //getLblNombreCliente().setText("..");
                    getBtnBuscarPersona().setEnabled(false);
                } else {
                    getBtnBuscarPersona().setEnabled(true);
                }
            }
        });
    }
    
    
}
