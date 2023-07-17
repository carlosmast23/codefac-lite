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
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
