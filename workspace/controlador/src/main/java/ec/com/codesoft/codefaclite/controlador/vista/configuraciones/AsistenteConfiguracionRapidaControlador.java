/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.configuraciones;

import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import ec.com.codesoft.codefaclite.controlador.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class AsistenteConfiguracionRapidaControlador extends ModelControladorAbstract<AsistenteConfiguracionRapidaControlador.CommonIf, AsistenteConfiguracionRapidaControlador.SwingIf, AsistenteConfiguracionRapidaControlador.WebIf> implements VistaCodefacIf {

    /**
     * Varible general que identifica cuando tab tiene la ventana //TODO: Para
     * hacer parametrizable seria bueno obtener este dato desde el mismo
     * componente
     */
    private static final Integer TOTAL_COUNT_TAB = 4;
    /**
     * Corregir problema que generar error si no estan iniciadas las variables
     * en la parte susperior en la parte de MVC
     */
    private Integer pestañaActivaTab;

    private Boolean botonTerminarHabilitar;
    
    private File fileEmpresaLogo;

    private List<ParametroCodefac> listParametroCodefac;
    
    private Empresa empresa;
    private Sucursal sucursal;
    private PuntoEmision puntoEmision;
    private Usuario usuario;

    public AsistenteConfiguracionRapidaControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, CommonIf interfaz, TipoVista tipoVista) {
        super(mensajeVista, session, interfaz, tipoVista);
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        iniciarVariables();
        System.out.println("Iniciar");
    }

    private void iniciarVariables() {
        pestañaActivaTab = 0;
        botonTerminarHabilitar = false;
        
        listParametroCodefac=new ArrayList<ParametroCodefac>();

        empresa=new Empresa();
        empresa.setContribuyenteRegimenMicroempresasBool(true);
        empresa.setObligadoLlevarContabilidadBool(false);        
        sucursal=new Sucursal();
        

        //sucursal.get
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {

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
        //empresa = new Empresa();
        //empresa.setIdentificacion("1724218951");
        //empresa.setObligadoLlevarContabilidadBool(true);
        //empresa=new Empresa("1717171717");
        //System.out.println("Limpiar");
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

    public void listenerBtnAvanzarPantalla() {
        //Habilitar el boton de terminar
        if (pestañaActivaTab == TOTAL_COUNT_TAB - 1) {
            botonTerminarHabilitar = true;
        }

        if (pestañaActivaTab < TOTAL_COUNT_TAB - 1) {
            pestañaActivaTab++;
            getInterfaz().activarPestañaActiva(pestañaActivaTab);
        }

    }

    public void listenerBtnAtrasPantalla() {
        if (pestañaActivaTab > 0) {
            pestañaActivaTab--;
            getInterfaz().activarPestañaActiva(pestañaActivaTab);
        }

    }

    public void listenerBtnTerminar() {
        //TODO: Revisar para mejorar esta parte para que en la parte derecha le aparesca cuales son los campos faltantes
        if (getInterfaz().ejecutarValidadoresVista()) {
            try {
                sucursal.setEmpresa(empresa);
                
                empresa=ServiceFactory.getFactory().getEmpresaServiceIf().grabarConfiguracionInicial(empresa, sucursal, puntoEmision, usuario, listParametroCodefac);
                //Subo los archivos despues de grabar por que primero necesitaba el path donde van a estar los recursos
                subirArchivosServidor();
                
                mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);

            } catch (ServicioCodefacException ex) {
                Logger.getLogger(AsistenteConfiguracionRapidaControlador.class.getName()).log(Level.SEVERE, null, ex);
                mostrarMensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ERROR));
            } catch (RemoteException ex) {
                Logger.getLogger(AsistenteConfiguracionRapidaControlador.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            mostrarMensaje(new CodefacMsj("Faltan campos requeridos por llenar", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
        }

    }
    
    private void subirArchivosServidor()
    {
        if(fileEmpresaLogo!=null)
        {
            try {
                SimpleRemoteInputStream istream = new SimpleRemoteInputStream(
                        new FileInputStream(fileEmpresaLogo));
                
                ParametroCodefac parametroEmpresa=ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.DIRECTORIO_RECURSOS,empresa);
                String pathServidor=parametroEmpresa.getValor();
                ServiceFactory.getFactory().getRecursosServiceIf().uploadFileServer(
                        pathServidor,
                        DirectorioCodefac.IMAGENES, 
                        istream,
                        fileEmpresaLogo.getName()
                );
                                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AsistenteConfiguracionRapidaControlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(AsistenteConfiguracionRapidaControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void listenerBtnBuscarLogoEmpresa()
    {
        File file=getInterfaz().buscarFileLogoEmpresa();
        if(file!=null)
        {
            //Grabo el nombre de la imagen
            empresa.setImagenLogoPath(file.getName());
            fileEmpresaLogo=file;
            
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public PuntoEmision getPuntoEmision() {
        return puntoEmision;
    }

    public void setPuntoEmision(PuntoEmision puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    ///////////////////////////////////////////////////////////////////////////
    //             METODOS QUE CONTIENEN INTERFACES PARA LA IMPLEMTACION
    ///////////////////////////////////////////////////////////////////////////
    public interface CommonIf {

        //TODO: Implementacion de todas las interfaces comunes
        public void activarPestañaActiva(Integer indiceTab);

        public Boolean ejecutarValidadoresVista();
        
        public File buscarFileLogoEmpresa();
    }

    public interface SwingIf extends CommonIf {
        //TODO: Implementacion de las interfaces solo necesarias para Swing
    }

    public interface WebIf extends CommonIf {
        //TODO: Implementacion de las interafaces solo para la web
    }

}
