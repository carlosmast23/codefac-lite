/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.pos.model;

import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.controlador.vista.pos.CajaSesionModelControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.pos.reportdata.VentaReporteData;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.IngresoCaja;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaSessionEnum;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Desarrollo
 */
public class CerrarCajaModel extends CajaSessionModel
{
    //private CajaSession cajaSessionA = null;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        //super.iniciar(); //To change body of generated methods, choose Tools | Templates.
        //super.cicloVida=false;
        //super.validacionDatosIngresados=false;
        //cajaSessionA = null;
        //limpiar();
        //getjTextValorApertura().setEnabled(false);
        //getjCmbCajaPermiso().removeAllItems();
        //getjComboBoxEstadoCierre().removeAllItems();
        //cargarDatos();
        //addListenerCombo();
        getPnlCierreCaja().setVisible(true);
        getPnlCierreCajaOpciones().setVisible(true);
        //set
        super.iniciar();
    }
    
    
    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        //super.cicloVida=false;
        //super.validacionDatosIngresados = false;
        
        /*if(ca != null)
        {
            try {
                ServiceFactory.getFactory().getCajaSesionServiceIf().editar(cajaSessionA);
                //Mensaje
                DialogoCodefac.mensaje("Correcto","La caja se ha cerrado", DialogoCodefac.MENSAJE_CORRECTO);              
                this.iniciar();
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(CerrarCajaModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } */      
    }

    @Override
    public void limpiar() {
        /*getjTextFechaApertura().setText("");
        getjTextFechaCierre().setText("");
        getjTextHoraApertura().setText("");
        getjTextHoraCierre().setText("");
        getjTextValorApertura().setText("");
        getjTextValorCierre().setText("");*/
    }
   
    private void cargarDatos()
    {
        List<Caja> cajas = new ArrayList<>();
        try {
            List<CajaSession> cajasSession = ServiceFactory.getFactory().getCajaSesionServiceIf().obtenerCajaSessionPorUsuarioYSucursal(this.session.getUsuario(), this.session.getSucursal());
            if(cajasSession!=null && cajasSession.size()>0)
            {
                cajasSession.forEach(cajaSession -> {
                    cajas.add(cajaSession.getCaja());
                });
            }            
        } catch (RemoteException ex) {
            Logger.getLogger(CerrarCajaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        UtilidadesComboBox.llenarComboBox(getjCmbCajaPermiso(), cajas);
        UtilidadesComboBox.llenarComboBox(getjComboBoxEstadoCierre(), CajaSessionEnum.values()); 
    }
    
    /*public void addListenerCombo(){
        getjCmbCajaPermiso().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Caja caja = (Caja) getjCmbCajaPermiso().getSelectedItem();
                cajaSessionA=controlador.buscarSessionDesdeCaja(caja);
                cargarDatosPantalla(cajaSessionA);
                
                /*if(caja != null)
                {
                    try {
                        List<CajaSession> cajasSession = ServiceFactory.getFactory().getCajaSesionServiceIf().obtenerCajaSessionPorUsuarioYSucursal(session.getUsuario(), session.getSucursal());
                        if(cajasSession.size()>0)
                        {
                            cajasSession.forEach(cajaSession ->{
                                if(cajaSession.getCaja().equals(caja)){
                                    cajaSessionA = cajaSession;
                                    cargarDatosPantalla(cajaSessionA);
                                }
                            });
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(CerrarCajaModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }*/
    
    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public CajaSesionModelControlador.TipoProcesoCajaEnum getTipoProcesoEnum() {
        return CajaSesionModelControlador.TipoProcesoCajaEnum.CIERRE_CAJA; //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void generarReporte()
    {
        InputStream path = RecursoCodefac.JASPER_POS.getResourceInputStream("reporteCierreCaja.jrxml");
        Map<String,Object> parametros = new HashMap<String,Object>();
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CajaSession cajaSession=getControlador().getCajaSession();
        String fechaAperturaStr= format.format(cajaSession.getFechaHoraApertura());
        String fechaCierreStr= format.format(cajaSession.getFechaHoraCierre());
        
        //Cargar los datos de los parametros
        
        parametros.put("caja", cajaSession.getCaja().getNombre());
        parametros.put("usuario", cajaSession.getUsuario().getNick());
        parametros.put("fecha_apertura", fechaAperturaStr);
        parametros.put("fecha_cierre", fechaCierreStr);
        parametros.put("valor_apertura", cajaSession.getValorApertura()+"");
        parametros.put("valor_cierre_teorico", cajaSession.getValorCierre()+"");
        parametros.put("valor_ciere_practico", cajaSession.getValorCierreReal()+"");
        parametros.put("observacion", cajaSession.getObservacionCierreCaja());
        
        
        //Cargar los datos de los detalles
        List<VentaReporteData> detalleData=new ArrayList<VentaReporteData>();
        for (IngresoCaja ingresoCaja : cajaSession.getIngresosCaja()) 
        {
            VentaReporteData reporteData=new VentaReporteData(
                    ingresoCaja.getFactura().getSecuencial()+"", 
                    ingresoCaja.getFactura().getIdentificacion(), 
                    ingresoCaja.getFactura().getRazonSocial(), 
                    ingresoCaja.getFactura().getTotal()+"");
            
            detalleData.add(reporteData);            
        }
        
        
        DialogoCodefac.dialogoReporteOpciones( new ReporteDialogListener() {
                @Override
                public void excel() {
                    try{
                        Excel excel = new Excel();
                        String nombreCabeceras[] = {"Secuencial", "Identificación","Cliente", "Total"};
                        excel.gestionarIngresoInformacionExcel(nombreCabeceras, detalleData);
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
                    ReporteCodefac.generarReporteInternalFramePlantilla(path, parametros, detalleData, panelPadre,"Cierre Caja");
                }
            });
        
        //Llenar datos para el reporte
    
    }

    @Override
    public CajaSessionEnum getFiltroDialogoEnum() {
        return CajaSessionEnum.FINALIZADO;
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        generarReporte();
    }
    
    
    
}
