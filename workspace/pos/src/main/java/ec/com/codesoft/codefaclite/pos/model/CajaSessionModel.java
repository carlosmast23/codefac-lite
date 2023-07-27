/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.pos.model;

import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.controlador.vista.pos.CajaModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.pos.CajaSesionModelControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;import java.util.Map;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.pos.panel.CajaSessionPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.IngresoCaja;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaSessionEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.hora.UtilidadesHora;
import ec.com.codesoft.codefaclite.utilidades.validadores.UtilidadBigDecimal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

/**
 *
 * @author Robert
 */
//public class CajaSessionModel extends CajaSessionPanel implements CajaSesionModelControlador.Interface
public class CajaSessionModel extends CajaSessionPanel implements ControladorVistaIf, CajaSesionModelControlador.SwingIf
{
    protected CajaSesionModelControlador controlador ;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException 
    {
        controlador = new CajaSesionModelControlador(DialogoCodefac.intefaceMensaje, session, this, ModelControladorAbstract.TipoVista.ESCRITORIO);
        agregarListenerBoton();
        
    }
    
    private void agregarListenerBoton()
    {
        getTxtBillete1().addActionListener(listenerUnidadMonetaria);
        getTxtBillete5().addActionListener(listenerUnidadMonetaria);
        getTxtBillete10().addActionListener(listenerUnidadMonetaria);
        getTxtBillete20().addActionListener(listenerUnidadMonetaria);
        getTxtBillete50().addActionListener(listenerUnidadMonetaria);
        getTxtBillete100().addActionListener(listenerUnidadMonetaria);
        getTxtMoneda1ctv().addActionListener(listenerUnidadMonetaria);
        getTxtMoneda5ctv().addActionListener(listenerUnidadMonetaria);
        getTxtMoneda10ctv().addActionListener(listenerUnidadMonetaria);
        getTxtMoneda25ctv().addActionListener(listenerUnidadMonetaria);
        getTxtMoneda50ctv().addActionListener(listenerUnidadMonetaria);
        getTxtMoneda1Dolar().addActionListener(listenerUnidadMonetaria);     
        
        getTxtBillete1().addFocusListener(focusListenerUnidadMonetaria);
        getTxtBillete5().addFocusListener(focusListenerUnidadMonetaria);
        getTxtBillete10().addFocusListener(focusListenerUnidadMonetaria);
        getTxtBillete20().addFocusListener(focusListenerUnidadMonetaria);
        getTxtBillete50().addFocusListener(focusListenerUnidadMonetaria);
        getTxtBillete100().addFocusListener(focusListenerUnidadMonetaria);
        getTxtMoneda1ctv().addFocusListener(focusListenerUnidadMonetaria);
        getTxtMoneda5ctv().addFocusListener(focusListenerUnidadMonetaria);
        getTxtMoneda10ctv().addFocusListener(focusListenerUnidadMonetaria);
        getTxtMoneda25ctv().addFocusListener(focusListenerUnidadMonetaria);
        getTxtMoneda50ctv().addFocusListener(focusListenerUnidadMonetaria);
        getTxtMoneda1Dolar().addFocusListener(focusListenerUnidadMonetaria);
        
    }
    
    private FocusListener focusListenerUnidadMonetaria=new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {}

        @Override
        public void focusLost(FocusEvent e) {
            calcularValoresConDetalles();
        }
    };
    
    private ActionListener listenerUnidadMonetaria=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            calcularValoresConDetalles();
        }
    };
    
   
    private void calcularValoresConDetalles()
    {
        BigDecimal billete1=UtilidadBigDecimal.obtenerValorJTextField(getTxtBillete1());
        BigDecimal billete5= UtilidadBigDecimal.obtenerValorJTextField(getTxtBillete5()).multiply(new BigDecimal("5"));
        BigDecimal billete10= UtilidadBigDecimal.obtenerValorJTextField(getTxtBillete10()).multiply(new BigDecimal("10"));
        BigDecimal billete20= UtilidadBigDecimal.obtenerValorJTextField(getTxtBillete20()).multiply(new BigDecimal("20"));
        BigDecimal billete50= UtilidadBigDecimal.obtenerValorJTextField(getTxtBillete50()).multiply(new BigDecimal("50"));
        BigDecimal billete100= UtilidadBigDecimal.obtenerValorJTextField(getTxtBillete100()).multiply(new BigDecimal("100"));;
        
        BigDecimal moneda1= UtilidadBigDecimal.obtenerValorJTextField(getTxtMoneda1ctv()).multiply(new BigDecimal("1")).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP);
        BigDecimal moneda5= UtilidadBigDecimal.obtenerValorJTextField(getTxtMoneda5ctv()).multiply(new BigDecimal("5")).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP);
        BigDecimal moneda10= UtilidadBigDecimal.obtenerValorJTextField(getTxtMoneda10ctv()).multiply(new BigDecimal("10")).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP);
        BigDecimal moneda25= UtilidadBigDecimal.obtenerValorJTextField(getTxtMoneda25ctv()).multiply(new BigDecimal("25")).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP);
        BigDecimal moneda50= UtilidadBigDecimal.obtenerValorJTextField(getTxtMoneda50ctv()).multiply(new BigDecimal("50")).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP);
        BigDecimal moneda100= UtilidadBigDecimal.obtenerValorJTextField(getTxtMoneda1Dolar()).multiply(new BigDecimal("100")).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP);        
        
        BigDecimal total=billete1
                .add(billete5)
                .add(billete10)
                .add(billete20)
                .add(billete50)
                .add(billete100)
                .add(moneda1)
                .add(moneda5)
                .add(moneda10)
                .add(moneda25)
                .add(moneda50)
                .add(moneda100);
        
        getjTextValorCierreReal().setText(total+"");
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
       
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        getTxtBillete1().setText("0");
        getTxtBillete10().setText("0");
        getTxtBillete100().setText("0");
        getTxtBillete20().setText("0");
        getTxtBillete5().setText("0");
        getTxtBillete50().setText("0");
        
        getTxtMoneda10ctv().setText("0");
        getTxtMoneda1Dolar().setText("0");
        getTxtMoneda1ctv().setText("0");
        getTxtMoneda25ctv().setText("0");
        getTxtMoneda50ctv().setText("0");
        getTxtMoneda5ctv().setText("0");
        
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) 
    {
        CajaSession cajaSession = (CajaSession)entidad;
        
        String fechaAperturaStr= UtilidadesFecha.formatDate(UtilidadesFecha.castDateSqlToUtil(UtilidadesFecha.getFechaDeTimeStamp(cajaSession.getFechaHoraApertura())), ParametrosSistemaCodefac.FORMATO_ESTANDAR_FECHA_HORA_SIN_SEGUNDOS);
        String HoraAperturaStr= UtilidadesFecha.formatDate(UtilidadesFecha.castDateSqlToUtil(UtilidadesFecha.getFechaDeTimeStamp(cajaSession.getFechaHoraApertura())), ParametrosSistemaCodefac.FORMATO_ESTANDAR_HORA);
        
        getjTextFechaApertura().setText(fechaAperturaStr);
        getjTextHoraApertura().setText(HoraAperturaStr);
        
        getjTextFechaCierre().setText("" + UtilidadesFecha.getFechaHoy());
        getjTextHoraCierre().setText("" + UtilidadesHora.horaActual());
        
        getjTextValorApertura().setText("" + cajaSession.getValorApertura());
        
        getjCmbCajaPermiso().setSelectedItem(cajaSession.getCaja());
        getjComboBoxEstadoCierre().setSelectedItem(cajaSession.getEstadoSessionEnum());
        
        getjCmbCajaPermiso().setSelectedItem(cajaSession.getCaja());
        
        //get
        getjTextValorCierreReal().setText(cajaSession.getValorCierreReal()+"");
        getTxtObservacionesCierreCaja().setText(cajaSession.getObservacionCierreCaja());
       
        if(cajaSession.getIngresosCaja()==null || cajaSession.getIngresosCaja().isEmpty())
        {
            getjTextValorCierreTeorico().setText("" + cajaSession.getValorApertura());
        }
        else
        {    

            BigDecimal totalVentas =cajaSession.calcularValorCierreTeorico();
            
            getjTextValorCierreTeorico().setText("" + totalVentas);
            getjTextValorCierreReal().setText(totalVentas+"");
            
        }
        
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        return this.controlador.obtenerDialogoBusqueda();
    }

    @Override
    public ModelControladorAbstract getControladorVista() {
        return controlador;
    }
    
    /**
     * Get and Setter
     * @return 
     */
    
    public CajaSesionModelControlador getControlador() {
        return controlador;
    }

    public void setControlador(CajaSesionModelControlador controlador) {
        this.controlador = controlador;
    }

    @Override
    public String valorApertura() {
        return getjTextValorApertura().getText();
    }

    @Override
    public CajaSesionModelControlador.TipoProcesoCajaEnum getTipoProcesoEnum() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosVista(CajaSession cajaSession) {
        cargarDatosPantalla(cajaSession);
    }

    @Override
    public void generarReporte() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CajaSessionEnum getFiltroDialogoEnum() {
        return null;
    }
}
