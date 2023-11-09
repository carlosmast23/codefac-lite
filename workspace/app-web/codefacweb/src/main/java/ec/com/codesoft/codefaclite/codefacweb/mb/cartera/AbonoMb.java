/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.cartera;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.MensajeMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.UtilidadesDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.CarteraBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.controlador.vista.cartera.AbonoControlador;
import ec.com.codesoft.codefaclite.controlador.vista.crm.ProductoModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoCategoriaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author CARLOS_CODESOFT
 */
@ManagedBean
@ViewScoped 
public class AbonoMb extends GeneralAbstractMb implements Serializable,AbonoControlador.WebIf,ControladorVistaIf{

    private AbonoControlador controlador;  
     
    @PostConstruct
    private void init()
    {
        controlador=new AbonoControlador(MensajeMb.intefaceMensaje,sessionMb.getSession(), this,ModelControladorAbstract.TipoVista.WEB);
        //controlador.iniciarCombosBox(); 
    }
    
    @Override
    public void nuevo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String titulo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
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
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void seleccionarCliente(SelectEvent event) {
        PersonaEstablecimiento clienteOficina = (PersonaEstablecimiento) event.getObject();
        this.controlador.setPersona(clienteOficina.getPersona());

    }
    
    public void seleccionarFactura(SelectEvent event) {
        Cartera cartera = (Cartera) event.getObject();
        this.controlador.setCartera(cartera);

    }
    
    @Deprecated
    //TODO: Unificar con un solo metodo en el controlador y agregar una interfaz para abrir
    public void abrirDialogoBuscarFactura() 
    {
        Cartera.TipoCarteraEnum tipoCartera=controlador.getTipoCartera();
        Persona persona=controlador.getPersona();
        if(tipoCartera!=null)
        {
            //Cambio temporal para poder manejar el objeto persona como null
            if(persona.getIdCliente()==null)
            {
                persona=null;
            }
            
            CarteraBusqueda carteraBusqueda;
            if (tipoCartera.equals(Cartera.TipoCarteraEnum.CLIENTE)) 
            {
                carteraBusqueda = new CarteraBusqueda(true, false, DocumentoEnum.obtenerPorCategoria(DocumentoCategoriaEnum.COMPROBANTES_VENTA), persona, true);
            } 
            else {
                carteraBusqueda = new CarteraBusqueda(false, true, DocumentoEnum.obtenerPorCategoria(DocumentoCategoriaEnum.COMPROBANTES_VENTA),persona, true);
            }
            carteraBusqueda.setNoFiltrarDocumentos(true);
            
            UtilidadesDialogo.abrirDialogoBusqueda(carteraBusqueda);
            
        }
    }

    public void abrirDialogoBuscarCliente() {
        System.out.println("Abriendo dialogo init");
        ClienteEstablecimientoBusquedaDialogo clienteBusquedaDialogo = new ClienteEstablecimientoBusquedaDialogo(sessionMb.getSession());
        clienteBusquedaDialogo.setPrimeraColumnaNombre(true);
        //abrirDialogoBusqueda(clienteBusquedaDialogo);
        UtilidadesDialogo.abrirDialogoBusqueda(clienteBusquedaDialogo);
        System.out.println("Abriendo dialogo fin");
    }

    @Override
    public ModelControladorAbstract getControladorVista() {
        return controlador;
    }

    public AbonoControlador getControlador() {
        return controlador;
    }

    public void setControlador(AbonoControlador controlador) {
        this.controlador = controlador;
    }
    
    
    
}
