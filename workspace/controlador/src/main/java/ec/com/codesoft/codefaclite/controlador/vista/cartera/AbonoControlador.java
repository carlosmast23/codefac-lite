/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.cartera;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.CarteraBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.vista.crm.ZonaControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoCategoriaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class AbonoControlador extends ModelControladorAbstract<AbonoControlador.CommonIf,AbonoControlador.SwingIf, AbonoControlador.WebIf> implements VistaCodefacIf{

    private List<Cartera.TipoCarteraEnum> tipoList;
    private Cartera.TipoCarteraEnum tipoCartera;
    private Persona persona;
    private Cartera cartera;
    private BigDecimal valorCruzar;
    private String descripcion;
    
    public AbonoControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, CommonIf interfaz, TipoVista tipoVista) {
        super(mensajeVista, session, interfaz, tipoVista);
    }
    
    /////////// METODOS PERSONALIZADOS /////////////////////
    public void listenerBotonBuscarCliente()
    {
        ClienteEstablecimientoBusquedaDialogo dialogoBusqueda=new ClienteEstablecimientoBusquedaDialogo(session);
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(dialogoBusqueda);
        buscarDialogoModel.setVisible(true);
        PersonaEstablecimiento personaEstablecimientoTmp=(PersonaEstablecimiento) buscarDialogoModel.getResultado();
        Persona personaTemp=personaEstablecimientoTmp.getPersona();
        if(personaTemp!=null)
        {
            persona=personaTemp;
        }
    }
    
    public void listenerBotonBuscarFactura()
    {
        if(tipoCartera!=null)
        {
            CarteraBusqueda carteraBusqueda;
            if (tipoCartera.equals(Cartera.TipoCarteraEnum.CLIENTE)) 
            {
                carteraBusqueda = new CarteraBusqueda(true, false, DocumentoEnum.obtenerPorCategoria(DocumentoCategoriaEnum.COMPROBANTES_VENTA), persona, true);
            } 
            else {
                carteraBusqueda = new CarteraBusqueda(false, true, DocumentoEnum.obtenerPorCategoria(DocumentoCategoriaEnum.COMPROBANTES_VENTA),persona, true);
            }
            
            BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(carteraBusqueda);
                    buscarDialogoModel.setVisible(true);
                    Cartera carteraTmp = (Cartera) buscarDialogoModel.getResultado();
                    if (carteraTmp != null) {
                        cartera=carteraTmp;
                        //cartera.getPreimpreso()
                    }
            
        }
    }
            

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        
        tipoList=UtilidadesLista.arrayToList(Cartera.TipoCarteraEnum.values());
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        try {
            //fixParametros();
            ServiceFactory.getFactory().getCarteraServiceIf().grabarAbono(tipoCartera,cartera,session.getSucursal(),session.getUsuario(), valorCruzar,descripcion);
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ZonaControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(new CodefacMsj(ex.getMessage(),CodefacMsj.TipoMensajeEnum.ERROR));
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }
    
    @Deprecated
    //TODO: Metodo temporal para corregir problemas de la vista que no puede tener objetos vacios pero para la consulta necesito enviar un dato con null en la parte web
    private void fixParametros()
    {
        if(persona.getIdCliente()==null)
        {
            persona=null;
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        
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
        tipoCartera=null;
        persona=new Persona();
        cartera=new Cartera();
        valorCruzar=null;
        descripcion=null;
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
    
        ///////////////////////////////////////////////////////////////////////////
    //             METODOS QUE CONTIENEN INTERFACES PARA LA IMPLEMTACION
    ///////////////////////////////////////////////////////////////////////////
    
    public interface CommonIf
    {
        //TODO: Implementacion de todas las interfaces comunes
    }
    
    public interface SwingIf extends CommonIf
    {
        //TODO: Implementacion de las interfaces solo necesarias para Swing
    }
    
    public interface WebIf extends CommonIf
    {
        //TODO: Implementacion de las interafaces solo para la web
    }
    
    ////////////////// METODOS GET AND SET //////////////////////

    public List<Cartera.TipoCarteraEnum> getTipoList() {
        return tipoList;
    }

    public void setTipoList(List<Cartera.TipoCarteraEnum> tipoList) {
        this.tipoList = tipoList;
    }

    public Cartera.TipoCarteraEnum getTipoCartera() {
        return tipoCartera;
    }

    public void setTipoCartera(Cartera.TipoCarteraEnum tipoCartera) {
        this.tipoCartera = tipoCartera;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Cartera getCartera() {
        return cartera;
    }

    public void setCartera(Cartera cartera) {
        this.cartera = cartera;
    }

    public BigDecimal getValorCruzar() {
        return valorCruzar;
    }

    public void setValorCruzar(BigDecimal valorCruzar) {
        this.valorCruzar = valorCruzar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
    
    
}
