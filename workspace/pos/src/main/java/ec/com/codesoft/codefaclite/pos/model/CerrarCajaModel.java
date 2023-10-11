/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.pos.model;

import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.core.swing.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.controlador.vista.pos.CajaSesionModelControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.pos.reportdata.VentaReporteData;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.IngresoCaja;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaSessionEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ConfiguracionImpresoraEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import org.apache.commons.net.ntp.TimeStamp;

/**
 *
 * @author Desarrollo
 */
public class CerrarCajaModel extends CajaSessionModel
{
    //private CajaSession cajaSessionA = null;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        //super.iniciar();

        getPnlCierreCaja().setVisible(true);
        getPnlCierreCajaOpciones().setVisible(true);
        super.iniciar();
        
        /**
         * TODO: Solucion temporal, lo correcto seria tener una variable general del formulario para saber que ya se termino el proceso de carga o para llevar una bandera para saber que se termino de cargar
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    agregarListenerRecuperarDatos();
                } catch (InterruptedException ex) {
                    Logger.getLogger(CerrarCajaModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
        
    }
    
    private void agregarListenerRecuperarDatos()
    {
        addInternalFrameListener(new InternalFrameListener() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
                
            }

            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                
            }

            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                
            }

            @Override
            public void internalFrameIconified(InternalFrameEvent e) {
                
            }

            @Override
            public void internalFrameDeiconified(InternalFrameEvent e) {
                
            }

            @Override
            public void internalFrameActivated(InternalFrameEvent e) {
                System.out.println("estado formulario: "+estadoFormulario);
                controlador.limpiar();
            }

            @Override
            public void internalFrameDeactivated(InternalFrameEvent e) {
                
            }
        });
    }
    
 
   
    /*private void cargarDatos()
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
    } */   
    
    
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
    
    //TODO: Optimizar esta parte para poner en otro lado más general por ejemplo el contralador
    public static void generarReporteCaja(CajaSession cajaSession,InterfazComunicacionPanel panelPadre)
    {
        
        if(cajaSession.getCaja()==null)
        {
            return;
        }
        
        Map<String,Object> parametros = new HashMap<String,Object>();
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //CajaSession cajaSession=getControlador().getCajaSession();
        String fechaAperturaStr= "";
        if(cajaSession.getFechaHoraApertura()!=null)
        {
            fechaAperturaStr= format.format(cajaSession.getFechaHoraApertura());
        }
        
        String fechaCierreStr="";
        if(cajaSession.getFechaHoraCierre()!=null)
        {
            fechaCierreStr=format.format(cajaSession.getFechaHoraCierre());
        }
        
        BigDecimal totalVentas =cajaSession.calcularValorCierreTeorico();
        if(cajaSession.getValorCierre()!=null)
        {
            totalVentas=cajaSession.getValorCierre();
        }
        //Cargar los datos de los parametros
        
        parametros.put("caja", cajaSession.getCaja().getNombre());
        parametros.put("usuario", cajaSession.getUsuario().getNick());
        parametros.put("fecha_apertura", fechaAperturaStr);
        parametros.put("fecha_cierre", fechaCierreStr);
        parametros.put("valor_apertura", cajaSession.getValorApertura()+"");
        parametros.put("valor_cierre_teorico", totalVentas+"");
        parametros.put("valor_ciere_practico", cajaSession.getValorCierreReal()+"");
        parametros.put("observacion", cajaSession.getObservacionCierreCaja());
        
        parametros.put("1ctv", cajaSession.getMoneda1ctv()+"");
        parametros.put("5ctv", cajaSession.getMoneda5ctv()+"");
        parametros.put("10ctv", cajaSession.getMoneda10ctv()+"");
        parametros.put("25ctv", cajaSession.getMoneda25ctv()+"");
        parametros.put("50ctv", cajaSession.getMoneda50ctv()+"");
        parametros.put("100ctv", cajaSession.getMoneda1dolar()+"");
        
        parametros.put("1usd", cajaSession.getBillete1()+"");
        parametros.put("5usd", cajaSession.getBillete5()+"");
        parametros.put("10usd", cajaSession.getBillete10()+"");
        parametros.put("20usd", cajaSession.getBillete20()+"");
        parametros.put("50usd", cajaSession.getBillete50()+"");
        parametros.put("100usd", cajaSession.getBillete100()+"");
        
        
        //Cargar los datos de los detalles
        List<VentaReporteData> detalleData=new ArrayList<VentaReporteData>();
        
        List<IngresoCaja> ingresoCajaList=cajaSession.getIngresosCaja();
        
         SimpleDateFormat dateFormatHora = new SimpleDateFormat("HH:mm");
        
        if(ingresoCajaList!=null)
        {
            for (IngresoCaja ingresoCaja : ingresoCajaList) 
            {
                if(ingresoCaja.getFactura()!=null)
                {
                    Timestamp fechaIngreso=ingresoCaja.getFactura().getFechaCreacion();

                    for (FormaPago formaPago : ingresoCaja.getFactura().getFormaPagos()) 
                    {
                        String estadoNombre=ingresoCaja.getFactura().getEstadoEnum().getNombre();
                        //En el caso que la nota de credito este anulada en el reporte si aparece pero con estado anulado

                        if(Factura.EstadoNotaCreditoEnum.ANULADO_TOTAL.equals(ingresoCaja.getFactura().getEstadoNotaCreditoEnum()))
                        {
                            estadoNombre=ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getNombre();
                        }

                        VentaReporteData reporteData = new VentaReporteData(                            
                                ingresoCaja.getFactura().getSecuencial() + "",
                                ingresoCaja.getFactura().getIdentificacion(),
                                ingresoCaja.getFactura().getRazonSocial(),
                                formaPago.getTotal() + "",
                                estadoNombre,
                                formaPago.getSriFormaPago().getAlias(),
                                dateFormatHora.format(fechaIngreso),
                                "+"
                        );

                        detalleData.add(reporteData);
                    }
                }
                else if(ingresoCaja.getCompra()!=null)
                {
                    //Tomar en cuenta para el calculo siempre y cuando no este eliminado
                    if(!ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado().equals(ingresoCaja.getCompra().getEstado()))
                    {
                        Timestamp fechaIngreso=ingresoCaja.getCompra().getFechaCreacion();
                        VentaReporteData compraData = new VentaReporteData(                            
                                    ingresoCaja.getCompra().getSecuencial() + "",
                                    ingresoCaja.getCompra().getIdentificacion(),
                                    ingresoCaja.getCompra().getRazonSocial(),
                                    ingresoCaja.getCompra().getTotal()+"",
                                    ingresoCaja.getCompra().getEstado(),
                                    "Efectivo",
                                    dateFormatHora.format(fechaIngreso),
                                    "-"
                            );
                        detalleData.add(compraData);
                    }
                }
                
            }
        }
        
        UtilidadesLista.ordenarLista(detalleData,new Comparator<VentaReporteData>() {
            @Override
            public int compare(VentaReporteData o1, VentaReporteData o2) {
                return o1.getFormaPago().compareTo(o2.getFormaPago());
            }
        });
        
        String[] opciones = {"A4", "Ticket", "Cancelar"};
        int opcionSeleccionada = DialogoCodefac.dialogoPreguntaPersonalizada("Reporte", "Por favor seleccione el tipo de reporte?", DialogoCodefac.MENSAJE_CORRECTO, opciones);
        switch (opcionSeleccionada) {
            case 0: 
                imprimirFormatoA4(detalleData,parametros,panelPadre);
                break;

            case 1:
                imprimirFormatoTicket(parametros, detalleData, panelPadre);
                break;
        }
        
        
    }
    
    private static void imprimirFormatoTicket(Map<String,Object> parametros,List<VentaReporteData> detalleData,InterfazComunicacionPanel panelPadre)
    {
        String nombreReporte="cierreCajaTicket.jrxml";
        ReporteCodefac.generarReporteInternalFramePlantilla(RecursoCodefac.JASPER_POS,nombreReporte, parametros, detalleData, panelPadre, "Cierre Caja", OrientacionReporteEnum.VERTICAL,FormatoHojaEnum.TICKET,ConfiguracionImpresoraEnum.NINGUNA);        
    }
    
    private static void imprimirFormatoA4(List<VentaReporteData> detalleData,Map<String,Object> parametros,InterfazComunicacionPanel panelPadre)
    {
        DialogoCodefac.dialogoReporteOpciones(new ReporteDialogListener() {
            @Override
            public void excel() {
                try {
                    Excel excel = new Excel();
                    String nombreCabeceras[] = {"Secuencial", "Identificación", "Cliente", "Total"};
                    excel.gestionarIngresoInformacionExcel(nombreCabeceras, detalleData);
                    excel.abrirDocumento();
                } catch (Exception exc) {
                    exc.printStackTrace();
                    DialogoCodefac.mensaje("Error", "El archivo Excel se encuentra abierto", DialogoCodefac.MENSAJE_INCORRECTO);
                }
            }

            @Override
            public void pdf() {
                InputStream path = RecursoCodefac.JASPER_POS.getResourceInputStream("reporteCierreCaja.jrxml");
                ReporteCodefac.generarReporteInternalFramePlantilla(path, parametros, detalleData, panelPadre, "Cierre Caja");
            }
        });
    }
    
    @Override
    public void generarReporte()
    {
        generarReporteCaja(getControlador().getCajaSession(), panelPadre);
    
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
