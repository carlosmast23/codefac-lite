/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.configuraciones;

import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class AsistenteConfiguracionRapidaControlador extends ModelControladorAbstract<AsistenteConfiguracionRapidaControlador.CommonIf, AsistenteConfiguracionRapidaControlador.SwingIf,AsistenteConfiguracionRapidaControlador.WebIf> implements  VistaCodefacIf{
    /**
     * Varible general que identifica cuando tab tiene la ventana
     * //TODO: Para hacer parametrizable seria bueno obtener este dato desde el mismo componente
     */
    private static final Integer TOTAL_COUNT_TAB=4;
    /**
     * Corregir problema que generar error si no estan iniciadas las variables en la parte susperior en la parte de MVC
     */
    private Integer pestañaActivaTab=0;
    
    private Boolean botonTerminarHabilitar=false;

    public AsistenteConfiguracionRapidaControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, CommonIf interfaz, TipoVista tipoVista) {
        super(mensajeVista, session, interfaz, tipoVista);
    }

    
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        iniciarVariables();
    }
    
    private void iniciarVariables()
    {
        pestañaActivaTab=0;
        botonTerminarHabilitar=false;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void listenerBtnAvanzarPantalla()
    {
        //Habilitar el boton de terminar
        if(pestañaActivaTab==TOTAL_COUNT_TAB-1)
        {
            botonTerminarHabilitar=true;
        }
        
        
        if(pestañaActivaTab<TOTAL_COUNT_TAB-1)
        {
            pestañaActivaTab++;
            getInterfaz().activarPestañaActiva(pestañaActivaTab);
        }
        
    }
    
    public void listenerBtnAtrasPantalla()
    {        
        if(pestañaActivaTab>0)
        {
            pestañaActivaTab--;
            getInterfaz().activarPestañaActiva(pestañaActivaTab);
        }
        
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //                  METODOS GET AND SET
    ///////////////////////////////////////////////////////////////////////////

    public Boolean getBotonTerminarHabilitar() {
        return botonTerminarHabilitar;
    }

    public void setBotonTerminarHabilitar(Boolean botonTerminarHabilitar) {
        this.botonTerminarHabilitar = botonTerminarHabilitar;
    }
    
    
    ///////////////////////////////////////////////////////////////////////////
    //             METODOS QUE CONTIENEN INTERFACES PARA LA IMPLEMTACION
    ///////////////////////////////////////////////////////////////////////////
    public interface CommonIf {
        //TODO: Implementacion de todas las interfaces comunes
        public void activarPestañaActiva(Integer indiceTab);
    }

    public interface SwingIf extends CommonIf {
        //TODO: Implementacion de las interfaces solo necesarias para Swing
    }

    public interface WebIf extends CommonIf {
        //TODO: Implementacion de las interafaces solo para la web
    }
    
}
