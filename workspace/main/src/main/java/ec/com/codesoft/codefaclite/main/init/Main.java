/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.init;

//import com.sun.xml.internal.ws.client.ClientTransportException;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.AyudaCodefacModel;
import ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.PanelSecundarioAbstract;
import ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.ValidadorCodefacModel;
import ec.com.codesoft.codefaclite.controlador.logs.LogControlador;
import ec.com.codesoft.codefaclite.main.archivos.ArchivoConfiguracionesCodefac;
import ec.com.codesoft.codefaclite.servicios.ServidorSMS;
import ec.com.codesoft.codefaclite.main.license.Licencia;
import ec.com.codesoft.codefaclite.main.license.ValidacionLicenciaCodefac;
import ec.com.codesoft.codefaclite.main.license.excepcion.NoExisteLicenciaException;
import ec.com.codesoft.codefaclite.main.license.excepcion.ValidacionLicenciaExcepcion;
import ec.com.codesoft.codefaclite.main.model.ConfiguracionesInicalesModel;
import ec.com.codesoft.codefaclite.main.model.DescargaModel;
import ec.com.codesoft.codefaclite.main.model.GeneralPanelModel;
import ec.com.codesoft.codefaclite.main.model.HiloPublicidadCodefac;
import ec.com.codesoft.codefaclite.main.model.LoginModel;
import ec.com.codesoft.codefaclite.main.model.ModoAplicativoModel;
import ec.com.codesoft.codefaclite.main.model.ServidorMonitorModel;
import ec.com.codesoft.codefaclite.main.model.SplashScreenModel;
import ec.com.codesoft.codefaclite.main.model.ValidarLicenciaModel;
import ec.com.codesoft.codefaclite.main.other.ArchivoDescarga;
import ec.com.codesoft.codefaclite.main.other.BaseDatosCredenciales;
import ec.com.codesoft.codefaclite.main.panel.publicidad.Publicidad;
import ec.com.codesoft.codefaclite.main.session.SessionCodefac;
import ec.com.codesoft.codefaclite.main.test.TestPruebaRMI;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.PersistenciaDuplicadaException;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.service.AccesoDirectoService;
import ec.com.codesoft.codefaclite.servidor.service.BodegaService;
import ec.com.codesoft.codefaclite.servidor.service.CategoriaProductoService;
import ec.com.codesoft.codefaclite.servidor.service.CompraDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.CompraService;
import ec.com.codesoft.codefaclite.servidor.service.ComprobanteFisicoDisenioService;
import ec.com.codesoft.codefaclite.servidor.service.ComprobantesService;
import ec.com.codesoft.codefaclite.servidor.service.DepartamentoService;
import ec.com.codesoft.codefaclite.servidor.service.EmpleadoService;
import ec.com.codesoft.codefaclite.servidor.service.EmpresaService;
import ec.com.codesoft.codefaclite.servidor.service.FacturacionService;
import ec.com.codesoft.codefaclite.servidor.service.ImpuestoDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.ImpuestoService;
import ec.com.codesoft.codefaclite.servidor.service.KardexDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.KardexItemEspecificoService;
import ec.com.codesoft.codefaclite.servidor.service.KardexService;
import ec.com.codesoft.codefaclite.servidor.service.NacionalidadService;
import ec.com.codesoft.codefaclite.servidor.service.NotaCreditoService;
import ec.com.codesoft.codefaclite.servidor.service.OrdenTrabajoDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.OrdenTrabajoService;
import ec.com.codesoft.codefaclite.servidor.service.ParametroCodefacService;
import ec.com.codesoft.codefaclite.servidor.service.PerfilService;
import ec.com.codesoft.codefaclite.servidor.service.PerfilUsuarioService;
import ec.com.codesoft.codefaclite.servidor.service.PermisoVentanaService;
import ec.com.codesoft.codefaclite.servidor.service.PersonaService;
import ec.com.codesoft.codefaclite.servidor.service.PresupuestoDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.PresupuestoService;
import ec.com.codesoft.codefaclite.servidor.service.ProductoEnsambleService;
import ec.com.codesoft.codefaclite.servidor.service.ProductoProveedorService;
import ec.com.codesoft.codefaclite.servidor.service.ProductoService;
import ec.com.codesoft.codefaclite.servidor.service.RecursosService;
import ec.com.codesoft.codefaclite.servidor.service.RetencionService;
import ec.com.codesoft.codefaclite.servidor.service.SmsService;
import ec.com.codesoft.codefaclite.servidor.service.SriFormaPagoService;
import ec.com.codesoft.codefaclite.servidor.service.SriIdentificacionService;
import ec.com.codesoft.codefaclite.servidor.service.SriRetencionIvaService;
import ec.com.codesoft.codefaclite.servidor.service.SriRetencionRentaService;
import ec.com.codesoft.codefaclite.servidor.service.SriRetencionService;
import ec.com.codesoft.codefaclite.servidor.service.SriService;
import ec.com.codesoft.codefaclite.servidor.service.UsuarioServicio;
import ec.com.codesoft.codefaclite.servidor.service.UtilidadesService;
import ec.com.codesoft.codefaclite.servidor.service.cartera.CarteraCruceService;
import ec.com.codesoft.codefaclite.servidor.service.cartera.CarteraDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.cartera.CarteraService;
import ec.com.codesoft.codefaclite.servidor.service.compra.OrdenCompraDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.compra.OrdenCompraService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.AulaService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.CatalogoProductoService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.EstudianteInscritoService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.EstudianteService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.NivelAcademicoService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.NivelService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.PeriodoService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.RubroEstudianteService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.RubroPlantillaEstudianteService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.RubroPlantillaService;
import ec.com.codesoft.codefaclite.servidor.service.gestionAcademica.RubrosNivelService;
import ec.com.codesoft.codefaclite.servidor.service.transporte.DestinatarioGuiaRemisionService;
import ec.com.codesoft.codefaclite.servidor.service.transporte.DetalleProductoGuiaRemisionService;
import ec.com.codesoft.codefaclite.servidor.service.transporte.GuiaRemisionAdicionalService;
import ec.com.codesoft.codefaclite.servidor.service.transporte.GuiaRemisionService;
import ec.com.codesoft.codefaclite.servidor.service.transporte.TransportistaService;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesServidor;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AccesoDirectoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CategoriaProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteFisicoDisenioServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EmpresaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexItemEspecificoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NotaCreditoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ParametroCodefacServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoEnsambleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoProveedorServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceControllerServer;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EstiloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AulaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CatalogoProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.DepartamentoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EmpleadoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteInscritoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NacionalidadServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NivelAcademicoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NivelServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenCompraDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenCompraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenTrabajoDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenTrabajoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PeriodoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PermisoVentanaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RubroEstudianteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RubrosNivelServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriIdentificacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UsuarioServicioIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UtilidadesServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraCruceServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.gestionacademica.RubroPlantillaEstudianteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.gestionacademica.RubroPlantillaServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PerfilServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PerfilUsuarioServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PresupuestoDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PresupuestoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RetencionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SmsServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriFormaPagoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionIvaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionRentaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.DestinatarioGuiaRemisionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.DetalleProductoGuiaRemisionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.GuiaRemisionAdicionalServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.GuiaRemisionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.TransportistaServiceIf;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesEncriptar;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSistema;
import ec.com.codesoft.codefaclite.utilidades.web.UtilidadesWeb;
import java.awt.Font;
import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Carlos
 */
public class Main {
    
    //Variable que me servira para tener un icono en la barra de tareas porque como solo uso dialogos , por ratos no existe ninguna referencia para que el usuario pueda saber si la aplicacion se esta ejecutando
    private static JFrame frameAplicacion=new JFrame();

    private static final Logger LOG = Logger.getLogger(Main.class.getName());
    
    
    /**
     * Variable para saber el modo que inicia el aplicativo
     */
    public static Integer modoAplicativo;

    public static void main(String[] args) {
        //Desabilito para que no se veo nada de la pantalla que contiene el proceso de inicio
        frameAplicacion.setUndecorated(true);
        frameAplicacion.setIconImage(ParametrosSistemaCodefac.iconoSistema);
        frameAplicacion.setVisible(true);
            
        //Configurar diferente tipo de letra para los dialogos
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 14));
        
        
        //Verifica si se esta ejecutando la ultima version o manda a aactualizar
        verificarUltimaVersionCodefac();
        
        configurarLogs();
        /**
         * Configura el archivo para guardar la configuracion inicial en propertys de como va a iniciar el aplicativo
         */
        cargarConfiguracionesIniciales();
        
        /**
         * Carga el tema seleccionado por defecto en aarchivo codefac.ini
         */
        cargarTemaCodefac();
        
        /**
         * Verificar si estan actualizaciones pendientes de una nueva version
         */
        verificarActualizacionBaseDatosVersion();

        /**
         * Seleccionar el modo de inicio de Codefac si no selecciona un modo no
         * le permite acceder a los siguiente funcionalidad
         */
        iniciarModoAplicativo(true);

        /**
         * Funcionalidad complementaria que inicia todos los componentes
         * necesarios
         */
        iniciarComponentes();
    }
    
    private static void configurarLogs()
    {
        LogControlador logControlador=new LogControlador();    
        LOG.log(Level.INFO,"Iniciado configuracion de los logs");
                
    }

    //Lee el archivo de configuraciones de cada computador como por ejemplo
    //para saber la modalidad por defecto que se debe ejcutar el aplicativo
    private static void cargarConfiguracionesIniciales() {
        ArchivoConfiguracionesCodefac.getInstance().cargarConfiguracionesIniciales();
    }
    
    private static void verificarUltimaVersionCodefac()
    {
        String path="http://www.cf.codesoft-ec.com/uploads/versiones/"; //directorio principal desde donde se van a bajar los archivos para actualizar
        //String path="http://localhost/java/recursos/";
        String carpetaDescarga="tmp"; //nombre de la carpeta para almacenar en el directoro TODO: Crear una variable global paa hacer referenca al directorio temporal
        
        String nameUltimaVersion="codefac.jar"; //Nombre del archivo de la nueva version de Codefac para descargar        
        String nameVersionPropiedades="ultimaVersion.codefac"; //Nombe del archivo de las propiedades para comparar si tenemos al ultima versions
        String nameUpdater="updater.jar"; //Nombre del archivo updater que se encarga de hacer la actualizacion
        
        //Descargar el archivo de propiedades de la ultima version vigente
        if(UtilidadesWeb.descargarArchivo(nameVersionPropiedades, path+nameVersionPropiedades, carpetaDescarga))
        {
            LOG.log(Level.INFO,"Descarga archivo de que contiene el número de la última versión");
            Properties propiedadesIniciales = new Properties();
            try {
                propiedadesIniciales.load(new FileReader(carpetaDescarga+"/"+nameVersionPropiedades));
                String ultimaVersion=propiedadesIniciales.getProperty("version");
                //Solo actualizar si la version instalada es menor a la disponible en internet
                if(UtilidadesSistema.compareVersion(ParametrosSistemaCodefac.VERSION,ultimaVersion)==-1)
                {
                    if(!DialogoCodefac.dialogoPregunta("Actualizar Codefac","Existe una nueva versión disponible , desea actualizar ahora?", DialogoCodefac.MENSAJE_CORRECTO))
                    {
                        //Si el usuario no desea actualizar la version se termina la funcion si actualizar
                        return;
                    }
                    
                    //Descargar el archivo updater que es el encargado de instalar la nueva version descargada
                    //el updater debe descargarse en la raiz
                    UtilidadesWeb.descargarArchivo(nameUpdater, path + nameUpdater, "");
                    LOG.log(Level.INFO, "Descargado updater para instalar las actualizaciones");
                    
                    //Descargar la ultima version disponible en el repositorio web
                    List<ArchivoDescarga> archivosDescargar=new ArrayList<ArchivoDescarga>();
                   
                    archivosDescargar.add(new ArchivoDescarga(nameUltimaVersion,path+nameUltimaVersion,carpetaDescarga));
                    archivosDescargar.addAll(buscarLibreriasActualizar(path,carpetaDescarga)); //Obtiene una lista de librerias de descargar para actualizar
                    DescargaModel descargaModel=new DescargaModel(archivosDescargar);
                    descargaModel.empezarDescarga();
                    descargaModel.setVisible(true);
                    
                    //Ejecutar el updater para que se encargue de hacer la actualicacion de la nueva version
                    try {
                        //String carpeta = "";
                        List<String> comando = Arrays.asList("java","-jar","updater.jar");
                        ProcessBuilder pb = new ProcessBuilder()
                                .command(comando);
                        Process p = pb.start();
                        System.exit(0); //Terminar la ejecucion del hilo actual , porque el updater se encargara de lanzar la nueva version

                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        else
        {
            LOG.log(Level.WARNING,"No se puede descargar el archivo que contiene el numero de version");
        }
        
        
    }
    
    private static List<ArchivoDescarga> buscarLibreriasActualizar(String path,String carpetaDescarga)
    {
        final String ARCHIVO_LISTA_LIBRERIAS = "librerias.txt";
        UtilidadesWeb.descargarArchivo(ARCHIVO_LISTA_LIBRERIAS, path + ARCHIVO_LISTA_LIBRERIAS, carpetaDescarga);
        List<String> libreriasOnline=UtilidadesArchivos.leerArchivoPlano(carpetaDescarga + "/" + ARCHIVO_LISTA_LIBRERIAS); //Ontiene un array con todos los nombres de las librias disponibles en linea
        File archivoLibrerias=new File("lib"); //Busca la carpeta de librerias del computador
        
        //Verifica si el directorio existe obtengo una lista de las librerias actuales para comparar
        List<String> listaLibreriasDescargadas=new ArrayList<String>();
        if(archivoLibrerias.exists())
        {
            String[] libreriasActuales=archivoLibrerias.list();
            listaLibreriasDescargadas = new ArrayList<String>(Arrays.asList(libreriasActuales));
        }
        
        //Verifico cuales son las librerias que faltan por descargar
        HashSet<String> conjuntoOnline=new HashSet<String>(libreriasOnline);
        HashSet<String> conjuntoDescargado=new HashSet<String>(listaLibreriasDescargadas);
        conjuntoOnline.removeAll(conjuntoDescargado); //elimino los conjuntos que ya estan descargados y estos son los que faltan descargar
        
        ////Librerias por defecto que siempre se deben actualizar porque son parte de la funcionalidad del sistema
        ////TODO: Estar siempre alerta porque si se aumenta un modulo toca agregar en esta parte
        conjuntoOnline.add("cartera-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("compra-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("configuraciones-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("controlador-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("coreCodefacLite-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("crm-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("facturacion-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("facturacionElectronica-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("gestionAcademica-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("inventario-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("recursos-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("servicios-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("servidor-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("servidor-interfaz-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("utilidades-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("ws-client-iess-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("ws-codefac-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("ws-virtualmall-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("transporte-1.0-SNAPSHOT.jar");
       
        //Crear el map con los datos para descargar
        List<ArchivoDescarga> listLibreriasDescargar=new ArrayList<ArchivoDescarga>();
        for (Iterator<String> iterator = conjuntoOnline.iterator(); iterator.hasNext();) {
            String nombreLibreria = iterator.next();
            listLibreriasDescargar.add(new ArchivoDescarga(nombreLibreria+".new", path+nombreLibreria,"lib"));
        }
        return listLibreriasDescargar;
    }

    /**
     * Verifica si la licencia es correcta en el servidor
     *
     * @param pathBase
     */
    private static void verificarLicencia(String pathBase) {
        /**
         * Realizar Analisis para verificar si existe la licencia instalada
         */
        if (!comprobarLicencia(pathBase)) {
            System.exit(0);
        } else {

            //Buscar el tipo de licencia paa setear en el sistema
            ValidacionLicenciaCodefac validacion = new ValidacionLicenciaCodefac(pathBase);
            TipoLicenciaEnum tipoLicencia = validacion.getLicencia().getTipoLicenciaEnum();

            //Esta validacion es solo para usuario premium para cuando no paguen y tengamos que disminuir la licencia
            if (!TipoLicenciaEnum.GRATIS.equals(tipoLicencia)) {
                validacionCodefacOnline(validacion);
                validacion = new ValidacionLicenciaCodefac(pathBase);
                tipoLicencia = validacion.getLicencia().getTipoLicenciaEnum();
            }

            //Este valor seteo para que sea accesible desde el servidor
            //TODO: Verficar si se puede mejorar esta linea de codigo
            UtilidadesServidor.tipoLicenciaEnum = tipoLicencia;
            UtilidadesServidor.modulosMap = validacion.getLicencia().getModulosSistema();
            UtilidadesServidor.cantidadUsuarios = Integer.parseInt(validacion.obtenerLicencia().getProperty(Licencia.PROPIEDAD_CANTIDAD_CLIENTES));
            UtilidadesServidor.usuarioLicencia = validacion.obtenerLicencia().getProperty(Licencia.PROPIEDAD_USUARIO);

        }
    }

    /**
     * Verifica si existe o selecciona el modo del aplicativo (Cliente,
     * Servidor, Cliente-Servidor)
     *
     * @param configuracionesDefecto elige si desea que esocja las
     * configuraciones guardas o que siempre pregunte el modo de inicio
     */
    public static void iniciarModoAplicativo(Boolean configuracionesDefecto) {
        //Si existen configuraciones iniciales solo las carga
        Properties propiedadesIniciales=ArchivoConfiguracionesCodefac.getInstance().getPropiedadesIniciales();
        if (propiedadesIniciales != null && configuracionesDefecto) {
            String modoAplicativoStr = propiedadesIniciales.getProperty(ArchivoConfiguracionesCodefac.CAMPO_MODO_APLICATIVO);
            if (modoAplicativoStr != null) {
                modoAplicativo = Integer.parseInt(modoAplicativoStr);
                return; //sio existe no continua buscando el modo de aplicativo
            }
        }
        
        /**
         * Seleccionar el modo de inicio de Codefac si no selecciona un modo no
         * le permite acceder a los siguiente funcionalidad
         */
        ModoAplicativoModel dialogAplicativo = new ModoAplicativoModel(frameAplicacion, true);
        dialogAplicativo.setLocationRelativeTo(null);
        dialogAplicativo.setVisible(true);

        //Si no selecciona ninguna opcion salir del aplicativo
        if (dialogAplicativo.getModo() == null) {
            System.exit(0);
        } else {
            try {
                modoAplicativo = dialogAplicativo.getModo();
                propiedadesIniciales.put(ArchivoConfiguracionesCodefac.CAMPO_MODO_APLICATIVO, modoAplicativo + "");
                ArchivoConfiguracionesCodefac.getInstance().guardar();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    
    //Funcion que verifica si se instalo una nueva version y ejecuta los scripts para actualizar la base de datos
    private static void verificarActualizacionBaseDatosVersion()
    {        
        Properties propiedadesIniciales=ArchivoConfiguracionesCodefac.getInstance().getPropiedadesIniciales();
        String versionGrabada=propiedadesIniciales.getProperty(ArchivoConfiguracionesCodefac.CAMPO_VERSION);
        
        if(versionGrabada!=null)
        {
            if(!versionGrabada.equals(""))
            {
                //Solo si existe el dato en el archivo , verifico que el dato almacenado sea diferente y superior a la version ejecutada para actualizar
                if(UtilidadesSistema.compareVersion(versionGrabada,ParametrosSistemaCodefac.VERSION)==-1)
                {
                   
                    try {
                        
                        //Si el usuario inicia el programa en modo cliente no debe hacer esta validacion de actualizar datos
                        String modoAplicativo = propiedadesIniciales.getProperty(ArchivoConfiguracionesCodefac.CAMPO_MODO_APLICATIVO);
                        
                        //Solo actualizar si es un modo servidor , o cliente servidor
                        if (modoAplicativo!=null && !modoAplicativo.equals(ModoAplicativoModel.MODO_CLIENTE.toString())) 
                        {
                            //Obtiene una instancia de un objeto donde puedo interactuar con la base de datos
                            BaseDatosCredenciales credenciales = BaseDatosCredenciales.getInstance();
                            //verificar si existen los datos creados
                            if (credenciales.cargarDatos()) {
                                String usuarioDb = credenciales.getUsuario();
                                String claveDb = credenciales.getClave();
                                //Si falta algun datos del usuario y la clave abro la pantalla de crear credenciales
                                if (usuarioDb != null || claveDb != null) { //TODO: hacer una validacion tambien cuando falte alguno de los datos por algun motivo anormal
                                    AbstractFacade.usuarioDb=usuarioDb;
                                    AbstractFacade.claveDb=claveDb;
                                }
                            }
                            else
                            {
                                DialogoCodefac.mensaje("Alerta","No se puede actualizar la base de datos , error en las credenciales",DialogoCodefac.MENSAJE_ADVERTENCIA);
                            }

                            //TODO: Metodo que ejecuta los scripts para actualizar el sistema
                            UtilidadesServidor.actualizarBaseDatos(versionGrabada);
                        }                        
                        //Actualizo el archivo de propiedades del sistema con la ultima version
                        propiedadesIniciales.put(ArchivoConfiguracionesCodefac.CAMPO_VERSION,ParametrosSistemaCodefac.VERSION);
                        ArchivoConfiguracionesCodefac.getInstance().guardar();
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
        }
        else
        {
            try {
                //Si no hay dato no se actualiza porque asumo que es la primera vez que se usa el sistema
                propiedadesIniciales.put(ArchivoConfiguracionesCodefac.CAMPO_VERSION, ParametrosSistemaCodefac.VERSION);
                ArchivoConfiguracionesCodefac.getInstance().guardar();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    public static void cargarRecursosServidor() {
        try {
            //AbstractFacade.cargarEntityManager();

            Map<Class, Class> mapRecursos = new HashMap<Class, Class>();

            mapRecursos.put(ProductoService.class, ProductoServiceIf.class);
            mapRecursos.put(PersonaService.class, PersonaServiceIf.class);
            mapRecursos.put(AccesoDirectoService.class, AccesoDirectoServiceIf.class);
            mapRecursos.put(BodegaService.class, BodegaServiceIf.class);
            mapRecursos.put(CategoriaProductoService.class, CategoriaProductoServiceIf.class);
            mapRecursos.put(CompraDetalleService.class, CompraDetalleServiceIf.class);
            mapRecursos.put(CompraService.class, CompraServiceIf.class);
            mapRecursos.put(ComprobanteFisicoDisenioService.class, ComprobanteFisicoDisenioServiceIf.class);
            mapRecursos.put(EmpresaService.class, EmpresaServiceIf.class);
            mapRecursos.put(FacturacionService.class, FacturacionServiceIf.class);
            mapRecursos.put(ImpuestoDetalleService.class, ImpuestoDetalleServiceIf.class);
            mapRecursos.put(ImpuestoService.class, ImpuestoServiceIf.class);
            mapRecursos.put(KardexDetalleService.class, KardexDetalleServiceIf.class);
            mapRecursos.put(KardexItemEspecificoService.class, KardexItemEspecificoServiceIf.class);
            mapRecursos.put(KardexService.class, KardexServiceIf.class);
            mapRecursos.put(NotaCreditoService.class, NotaCreditoServiceIf.class);
            mapRecursos.put(ParametroCodefacService.class, ParametroCodefacServiceIf.class);
            mapRecursos.put(PerfilService.class, PerfilServiceIf.class);
            mapRecursos.put(ProductoEnsambleService.class, ProductoEnsambleServiceIf.class);
            mapRecursos.put(ProductoProveedorService.class, ProductoProveedorServiceIf.class);
            mapRecursos.put(SriIdentificacionService.class, SriIdentificacionServiceIf.class);
            mapRecursos.put(SriService.class, SriServiceIf.class);
            mapRecursos.put(UsuarioServicio.class, UsuarioServicioIf.class);
            mapRecursos.put(UtilidadesService.class, UtilidadesServiceIf.class);
            mapRecursos.put(ComprobantesService.class, ComprobanteServiceIf.class);
            mapRecursos.put(RecursosService.class, RecursosServiceIf.class);
            mapRecursos.put(AulaService.class, AulaServiceIf.class);
            mapRecursos.put(EstudianteService.class, EstudianteServiceIf.class);
            mapRecursos.put(NivelService.class, NivelServiceIf.class);
            mapRecursos.put(PeriodoService.class, PeriodoServiceIf.class);
            mapRecursos.put(NivelAcademicoService.class, NivelAcademicoServiceIf.class);
            mapRecursos.put(PermisoVentanaService.class, PermisoVentanaServiceIf.class);
            mapRecursos.put(EstudianteInscritoService.class,EstudianteInscritoServiceIf.class);
            mapRecursos.put(RubrosNivelService.class,RubrosNivelServiceIf.class);
            mapRecursos.put(RubroEstudianteService.class,RubroEstudianteServiceIf.class);
            mapRecursos.put(NacionalidadService.class,NacionalidadServiceIf.class);
            mapRecursos.put(CatalogoProductoService.class,CatalogoProductoServiceIf.class);
            mapRecursos.put(CarteraService.class,CarteraServiceIf.class);
            mapRecursos.put(CarteraDetalleService.class,CarteraDetalleServiceIf.class);
            mapRecursos.put(CarteraCruceService.class, CarteraCruceServiceIf.class);
            mapRecursos.put(RubroPlantillaService.class, RubroPlantillaServiceIf.class);
            mapRecursos.put(RubroPlantillaEstudianteService.class, RubroPlantillaEstudianteServiceIf.class);
            mapRecursos.put(PerfilService.class, PerfilServiceIf.class);
            mapRecursos.put(PerfilUsuarioService.class, PerfilUsuarioServiceIf.class);
            mapRecursos.put(SriFormaPagoService.class, SriFormaPagoServiceIf.class);
            mapRecursos.put(SriRetencionIvaService.class, SriRetencionIvaServiceIf.class);
            mapRecursos.put(SriRetencionRentaService.class, SriRetencionRentaServiceIf.class);
            mapRecursos.put(RetencionService.class, RetencionServiceIf.class);
            mapRecursos.put(SriRetencionService.class,SriRetencionServiceIf.class);
            mapRecursos.put(OrdenCompraService.class,OrdenCompraServiceIf.class);
            mapRecursos.put(OrdenCompraDetalleService.class,OrdenCompraDetalleServiceIf.class);
            mapRecursos.put(DepartamentoService.class,DepartamentoServiceIf.class);
            mapRecursos.put(EmpleadoService.class,EmpleadoServiceIf.class);
            mapRecursos.put(OrdenTrabajoService.class,OrdenTrabajoServiceIf.class);
            mapRecursos.put(OrdenTrabajoDetalleService.class,OrdenTrabajoDetalleServiceIf.class);
            mapRecursos.put(PresupuestoService.class, PresupuestoServiceIf.class);
            mapRecursos.put(PresupuestoDetalleService.class, PresupuestoDetalleServiceIf.class);
            mapRecursos.put(TransportistaService.class, TransportistaServiceIf.class);
            
            mapRecursos.put(DestinatarioGuiaRemisionService.class, DestinatarioGuiaRemisionServiceIf.class);
            mapRecursos.put(DetalleProductoGuiaRemisionService.class, DetalleProductoGuiaRemisionServiceIf.class);
            mapRecursos.put(GuiaRemisionService.class, GuiaRemisionServiceIf.class);
            mapRecursos.put(GuiaRemisionAdicionalService.class, GuiaRemisionAdicionalServiceIf.class);
            mapRecursos.put(SmsService.class, SmsServiceIf.class);
            
            ServiceControllerServer.cargarRecursos(mapRecursos);
            LOG.log(Level.INFO,"Servidor Iniciado");

        } catch (PersistenceException ex) {
            Logger.getLogger(TestPruebaRMI.class.getName()).log(Level.SEVERE, null, ex);
        } //catch (PersistenciaDuplicadaException ex) {
        //    Logger.getLogger(TestPruebaRMI.class.getName()).log(Level.SEVERE, null, ex);
        //}
    }

    public static void cargarRecursosCliente(String ipServidor) {
            ServiceFactory.newController(ipServidor);
    }

    public static void iniciarComponentes() {
        try {
            //Desactivo la pantalla del hilo de ejecuion porque el splashScreen tiene un una pantalla que se muestra en la barra de tareas
            frameAplicacion.setVisible(false);
            
            SplashScreenModel splashScren = new SplashScreenModel();
            splashScren.agregarPorcentaje(40, "Cargando base de datos");
            splashScren.agregarPorcentaje(60, "Cargando datos session");
            splashScren.agregarPorcentaje(80, "Creando controlador codefac");
            splashScren.agregarPorcentaje(100, "Cargando ventanas");
            splashScren.setVisible(true);
            splashScren.iniciar();
            
            String ipServidor ="";
            
            if (modoAplicativo.equals(ModoAplicativoModel.MODO_CLIENTE)) {
                  //Consultar si existe grabado la ip del servidor para cargar por defecto la ultima
                    Properties propiedadesIniciales=ArchivoConfiguracionesCodefac.getInstance().getPropiedadesIniciales();
                    String ipServidorDefecto=propiedadesIniciales.getProperty(ArchivoConfiguracionesCodefac.CAMPO_IP_ULTIMO_ACCESO_SERVIDOR);
                    
                    //Cargar los recursos para funcionar en modo cliente
                    ipServidor = JOptionPane.showInputDialog("Ingresa la Ip del servidor: ",(ipServidorDefecto==null)?"":ipServidorDefecto);
                    cargarRecursosCliente(ipServidor);
                    verificarConexionesPermitidas();
                    
                    //Grabar la ip del ultimo servidor accedido para no ingresar nuevamente el dato
                    propiedadesIniciales.put(ArchivoConfiguracionesCodefac.CAMPO_IP_ULTIMO_ACCESO_SERVIDOR, ipServidor + "");
                    ArchivoConfiguracionesCodefac.getInstance().guardar();
                    
                    LOG.log(Level.INFO, "Modo Cliente Activado");
            }
            else
            {
                /**
                 * Componentes iniciales que utilizo tanto para modo servidor y modo cliente-servidor
                 */
                componentesBaseDatos(false);
                cargarRecursosServidor();
                //Todo: Veriicar este metodo que obtiene la ip del servidor, porque cuando tienen varias interfaces o una virtual puede levantarse el servicio en una IP que no se desea
                ipServidor = InetAddress.getLocalHost().getHostAddress();
                cargarRecursosCliente(ipServidor);
                
                ParametroCodefac parametroDirectorioRecursos = ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.DIRECTORIO_RECURSOS);
                //Si no existe el parametro seteo la ruta por defecto que va a ser el directorio del usuario para no tener problemas de permisos                
                if(parametroDirectorioRecursos==null || parametroDirectorioRecursos.getValor().equals(""))
                {                 
                    String directorioUsuario=System.getProperty("user.home")+"/codefacRecursos";         
                    
                    if(parametroDirectorioRecursos==null)
                    {
                        //Abrir un dialogo para preguntar si desea cambiar de ubicacion de la carpeta de recursos
                        if(DialogoCodefac.dialogoPregunta("Directorio Recursos","Por defecto la carpeta de recursos se creará en el directorio del usuario \n Desea cambiar el directorio por defecto? ",DialogoCodefac.MENSAJE_ADVERTENCIA))
                        {
                            JFileChooser f = new JFileChooser();
                            f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                            f.showSaveDialog(null);
                            if(f.getSelectedFile()!=null)
                            {
                                directorioUsuario=f.getSelectedFile().getPath();
                            }                            
                        }
                        
                        parametroDirectorioRecursos=new ParametroCodefac();
                        parametroDirectorioRecursos.setNombre(ParametroCodefac.DIRECTORIO_RECURSOS);
                        parametroDirectorioRecursos.setValor(directorioUsuario);
                        ServiceFactory.getFactory().getParametroCodefacServiceIf().grabar(parametroDirectorioRecursos);                        
                    }
                    else
                    {
                        parametroDirectorioRecursos.setValor(directorioUsuario);
                        ServiceFactory.getFactory().getParametroCodefacServiceIf().editar(parametroDirectorioRecursos);
                    }
                }
                
                verificarLicencia(parametroDirectorioRecursos.getValor());
                //Cargar el servidor de mensajeria
                ServidorSMS.getInstance().iniciarServidor();
                
                //Seteo el path de los directorio como una referencia global de todo el sistema
                UtilidadesServidor.pathRecursos = parametroDirectorioRecursos.getValor();    
                
                if (modoAplicativo.equals(ModoAplicativoModel.MODO_SERVIDOR)) {
                    //Crear el pantalla que va a manterner encendida la conexion con los clientes
                    ServidorMonitorModel monitor = new ServidorMonitorModel();
                    UtilidadesServidor.monitorUpdate = monitor;
                    monitor.setLocationRelativeTo(null);
                    monitor.setVisible(true);
                    LOG.log(Level.INFO, "Modo Servidor activado");
                }
                else
                {
                    if (modoAplicativo.equals(ModoAplicativoModel.MODO_CLIENTE_SERVIDOR)) {
                        verificarConexionesPermitidas();
                        LOG.log(Level.INFO, "Modo Cliente Servidor Activado");
                    }
                }
   
            }            

            //Si el aplicativo debe iniciar en modo servidor se cierra la pantalla de carga del slashScreen de codefac porque no necesita cargar mas modulos
            if (modoAplicativo.equals(ModoAplicativoModel.MODO_SERVIDOR)) {
                splashScren.termino();
                return;
            }

            splashScren.siguiente();
            /**
             * Crear la session y cargar otro datos de la empresa
             */

            SessionCodefac session = new SessionCodefac();
            if (modoAplicativo.equals(ModoAplicativoModel.MODO_SERVIDOR)) {
                session.setTipoLicenciaEnum(UtilidadesServidor.tipoLicenciaEnum);
            } else {
                UtilidadesServiceIf utilidadesServiceIf= ServiceFactory.getFactory().getUtilidadesServiceIf();
                TipoLicenciaEnum tipoLicencia = utilidadesServiceIf.getTipoLicencia();

                session.setTipoLicenciaEnum(tipoLicencia);
                
                session.setModulos(utilidadesServiceIf.getModulosSistema());                
                
            }

            session.setUsuarioLicencia(UtilidadesServidor.usuarioLicencia);
            EmpresaServiceIf empresaService = ServiceFactory.getFactory().getEmpresaServiceIf();
            List<Empresa> empresaList = empresaService.obtenerTodos();

            if (empresaList != null && empresaList.size() > 0) {
                session.setEmpresa(empresaList.get(0));
            }

            //session.setParametrosCodefac(getParametros());
            splashScren.siguiente();

            /**
             * Seteando la session de los datos a utilizar en el aplicativo
             */
            GeneralPanelModel panel = new GeneralPanelModel();
            panel.setSessionCodefac(session);
            splashScren.siguiente();

            /**
             * Añadir menus y ventanas a la aplicacion principal
             */
            //panel.setVentanasMenuList(agregarMenuVentana(panel));
            //panel.setVentanasMenuList(null);
            panel.setPanelesSecundarios(agregarPanelesSecundarios());
            panel.agregarPanelesSecundarios();
            /**
             * Establecer propiedades del formulario principal
             */
            panel.setIconImage(ParametrosSistemaCodefac.iconoSistema); // NOI18N
            panel.setExtendedState(MAXIMIZED_BOTH);
            splashScren.siguiente();
            splashScren.termino();

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

            /**
             * Si el usuario devuuelto es incorrecto terminar el aplicativo
             */
            Usuario usuarioLogin= cargarLoginUsuario();
            if (usuarioLogin == null) {
                LOG.log(Level.WARNING, "Error en la licencia ");
                return;
            }

            session.setUsuario(usuarioLogin);
            session.setPerfiles(obtenerPerfilesUsuario(usuarioLogin));
            panel.setVentanasMenuList(null);

            /**
             * Agregando Hilo de Publicidad si es usuario Gratuito
             */
            if (session.getTipoLicenciaEnum().equals(TipoLicenciaEnum.GRATIS)) {
                HiloPublicidadCodefac hiloPublicidad = new HiloPublicidadCodefac(panel);
                hiloPublicidad.setPublicidades(obtenerPublicidades());
                hiloPublicidad.start();
                panel.setHiloPublicidadCodefac(hiloPublicidad);
            }
            
           
            panel.ipServidor=ipServidor;
            panel.setearEtiquetasPantallaPrincipal();
            
            panel.iniciarComponentesGenerales();
            //frameAplicacion.dispose(); //Libero el recurso de la pantalla que tiene el icono en la barra de tareas
            panel.setVisible(true);
            

        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static Usuario cargarLoginUsuario() {
        frameAplicacion.setVisible(true); //muestro el hilo de ejcucion porque el login es un dialog que no tiene icono en la barra de tareas
        LoginModel loginModel = new LoginModel();
        loginModel.setVisible(true);
        Usuario usuarioLogin = loginModel.getUsuarioLogin();
        frameAplicacion.dispose();
        loginModel.getTxtUsuario().requestFocus();
        return usuarioLogin;
    }

    /**
     * Verifica si no se exedio el numero de conexiones permitidas
     *
     * @return
     */
    private static void verificarConexionesPermitidas() {
        try {
            Boolean respuesta = ServiceFactory.getFactory().getUtilidadesServiceIf().verificarConexionesServidor();
            if (!respuesta) {
                DialogoCodefac.mensaje("Error", "Excedio el numero de clientes permitidos", DialogoCodefac.MENSAJE_INCORRECTO);
                System.exit(0);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Metodo para cargar los estilos disponibles
     */
    private static void cargarEstilosCodefac() {
        /*
            try {
            UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel"); //El tema es interesante
            //UIManager.setLookAndFeel("net.java.dev.nimbus");
            //UIManager.setLookAndFeel(NimbusLookAndFeel.class.getCanonicalName ());
            //UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
            //UIManager.setLookAndFeel(new PlasticLookAndFeel());
            //UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
            //UIManager.setLookAndFeel(new WindowsLookAndFeel()); //El tema es interesante
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //UIManager.getLookAndFeelDefaults();
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
         */

    }

    public static void validacionCodefacOnline(ValidacionLicenciaCodefac validacion) {

        /**
         * Dias Limite para verificar la licencia en ese periodo de tiempo
         */
        int diasLimiteVerificacion=9;
        /**
         * Numero de dias antes de empezar a verificar la licencia
         */
        int diasToleraciaVerificacion=5;
        
        try {
            ParametroCodefacServiceIf servicio = ServiceFactory.getFactory().getParametroCodefacServiceIf();
            /**
             * Verificar si la licencia actual es la misma que tiene el servidor
             */
            ParametroCodefac parametroFechaValidacion = servicio.getParametroByNombre(ParametroCodefac.ULTIMA_FECHA_VALIDACION);
            if (parametroFechaValidacion != null && !parametroFechaValidacion.getValor().equals("")) {
                //String fechaStr = parametroFechaValidacion.getValor();
                String fechaStr = UtilidadesEncriptar.desencriptar(parametroFechaValidacion.getValor(),ParametrosSistemaCodefac.LLAVE_ENCRIPTAR);
                if (!fechaStr.equals("")) {
                    try {
                        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                        Date fechaUltimaRevision = formato.parse(fechaStr);
                        
                        Date fechaRevisar=UtilidadesFecha.hoy(); //Por feecto compara con la hora del sistema
                        try
                        {
                            fechaRevisar=UtilidadesFecha.getFechaNTP(); //Si no existe internet hace el calculo con la hora del sistema
                        }
                        catch(java.lang.NoSuchMethodError nse)
                        {
                            nse.printStackTrace();
                        } catch (Exception ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                                
                        
                        int dias = UtilidadesFecha.obtenerDistanciaDias(fechaUltimaRevision, fechaRevisar);//Esta fecha 

                        //Validacion para evitar que cambien fechas del sistema o que corrompan la fecha poniendo una fecha superior
                        if (dias < 0) {
                            DialogoCodefac.mensaje("Error", "No se puede validar su licencia ,inconsistencia con las fechas", DialogoCodefac.MENSAJE_INCORRECTO);
                            System.exit(0);

                        }

                        //Revisar la licencia cada 5 dias con un rango maximo de 8 dias 
                        if (dias > diasToleraciaVerificacion && dias < diasLimiteVerificacion) {
                            if (verificarLicenciaOnline(validacion)) {
                                grabarFechaRevision(parametroFechaValidacion, false);
                            }
                            else
                            {
                                DialogoCodefac.mensaje("Advertencia", "Le quedan "+(diasLimiteVerificacion-dias)+" días para verificar su licencia por internet. \n\nCausas: \n - No tiene conexion a internet por varios días \n - Esta usando una versión  ilegal \n\n Si el problema persiste comuníquese con un asesor\n Nota: Si no soluciona el problema pasado la fecha limite el programa yo no funcionara" , dias);
                            }
                        }

                        //Si execede los dias limite sin validar por internet ya no permite el acceso
                        if (dias >= diasLimiteVerificacion) {
                            if (verificarLicenciaOnline(validacion)) {
                                grabarFechaRevision(parametroFechaValidacion, false);
                            } else {
                                //Si no se logro validar la licencia durante 30 dias ya no se abre el software
                                DialogoCodefac.mensaje("Advertencia", "No se puede validar su licencia , verifique su conexión a internet", DialogoCodefac.MENSAJE_ADVERTENCIA);
                                System.exit(0);
                            }
                        }

                    } catch (ParseException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    if (verificarLicenciaOnline(validacion)) //no se pone en un if, porque esta controlado en el metodo si no existe salir
                    {
                        grabarFechaRevision(parametroFechaValidacion, false);
                    } else {
                        //Si no se logro validar la licencia por primera vez  no se abre el software
                        DialogoCodefac.mensaje("Error", "No se puede validar su licencia , verifique su conexión a internet", DialogoCodefac.MENSAJE_INCORRECTO);
                        System.exit(0);

                    }
                }

            } else //cuando no se tiene registro de la fecha de validacion
            {
                if (verificarLicenciaOnline(validacion)) {
                    grabarFechaRevision(parametroFechaValidacion, true);
                } else {
                    //Si no se logro validar la licencia por primera vez  no se abre el software
                    DialogoCodefac.mensaje("Error", "No se puede validar su licencia , verifique su conexión a internet", DialogoCodefac.MENSAJE_INCORRECTO);
                    System.exit(0);
                }

            }
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Funcion que graba la fecha de la ultima revision de la licencia y me
     * permite hacer un control para evitar que el softare funcione sin
     * verificar alteraciones en su licencia o cuando se requiera cambiar el
     * tipo de licencia por ejemplo cuando no realiza los pagos de la licencia
     *
     * @param parametroFechaValidacion
     * @param crear
     */
    private static void grabarFechaRevision(ParametroCodefac parametroFechaValidacion, boolean crear) {
        try {
            if (crear) {
                parametroFechaValidacion = new ParametroCodefac();
                parametroFechaValidacion.setNombre(ParametroCodefac.ULTIMA_FECHA_VALIDACION);
            }

            ParametroCodefacServiceIf servicio = ServiceFactory.getFactory().getParametroCodefacServiceIf();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaHoy = UtilidadesFecha.getFechaNTP();
            String dateStr=format.format(fechaHoy);
            parametroFechaValidacion.setValor(UtilidadesEncriptar.encriptar(dateStr,ParametrosSistemaCodefac.LLAVE_ENCRIPTAR));
            if (crear) {
                servicio.grabar(parametroFechaValidacion);
            } else {
                servicio.editar(parametroFechaValidacion);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Verifica que la licencia que esta en el computador sea la misma que la
     * del servidor
     *
     * @param validacion
     * @return
     */

    public static boolean verificarLicenciaOnline(ValidacionLicenciaCodefac validacion)
    {
        //try
        //{
            
            String usuario=validacion.obtenerLicencia().getProperty(Licencia.PROPIEDAD_USUARIO);
            Licencia licenciaOnline=new Licencia();
            licenciaOnline.cargarLicenciaOnline(usuario);
            
            
            Licencia licenciaFisica=new Licencia();
            licenciaFisica.cargarLicenciaFisica(validacion.obtenerLicencia());


            if(licenciaOnline.compararOtraLicencia(licenciaFisica))
            {
                return true;
            }
            else //Si existe diferencias con la otra licencia lanza el dialogo para actualizar la licencia
            {                
                //validacion.crearLicenciaDescargada(licenciaOnline);a
                //validacion.crearLicencia(usuario,tipoLicencia,cantidadCliente,modulosActivos);
                //if(validacion.validar())
                //{
                //    return true;
                //}
                //Pantalla para actualizar la licencia si existe la licencia
                DialogoCodefac.mensaje("Advertencia","Su licencia esta desactualizada o es incorrecta. \n Porfavor actualice su licencia para continuar", DialogoCodefac.MENSAJE_ADVERTENCIA);
                ValidarLicenciaModel licenciaDialog = new ValidarLicenciaModel(null, true, true);
                licenciaDialog.setValidacionLicenciaCodefac(validacion);
                if (validacion.verificarConexionInternet()) {
                    licenciaDialog.setVisible(true);
                    if (licenciaDialog.licenciaCreada) {
                        return comprobarLicencia(validacion.getPath()); //volver a verificar la licencia
                    } else {
                        return false;
                    }
                } else {
                    DialogoCodefac.mensaje("Error", "Para activar su producto conéctese a Internet", DialogoCodefac.MENSAJE_INCORRECTO);
                    return false;
                }

            }            

        //}
        //catch(com.sun.xml.internal.ws.client.ClientTransportException cte)
        //{
        //    return false;
        //}

    }

    /**
     * Agrega todas las pantallas secundarias que puedo utilizar en la pantalla secuendaria del menu principal
     * @return 
     */
    public static Map<String,PanelSecundarioAbstract> agregarPanelesSecundarios()
    {
        Map<String,PanelSecundarioAbstract> paneles=new HashMap<String,PanelSecundarioAbstract>();
        paneles.put(PanelSecundarioAbstract.PANEL_AYUDA,new AyudaCodefacModel() );
        paneles.put(PanelSecundarioAbstract.PANEL_MONITOR,MonitorComprobanteModel.getInstance());
        paneles.put(PanelSecundarioAbstract.PANEL_VALIDACION,new ValidadorCodefacModel());

        return paneles;
    }

    public static List<Publicidad> obtenerPublicidades() {
        List<Publicidad> publicidades = new ArrayList<Publicidad>();
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("angelicaPerfumes.png"), "https://www.facebook.com/avonbellezacosmeticos/", 3, "Dale click a la imagen para mas información"));
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("anunciateConNosotros.png"), "https://www.facebook.com/codefac.ec/", 6, "Dale click a la imagen para mas información"));
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("desarrolloSoftware.png"), "https://www.facebook.com/codesoft.ec/", 3, "Dale click a la imagen para mas información"));
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("publicidadCodesoft.png"), "https://www.facebook.com/codesoft.ec/", 3, "Dale click a la imagen para mas información"));
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("publicidadPaquete.png"), "https://www.facebook.com/codefac.ec/", 10, "Dale click a la imagen para mas información"));
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("publicidadVirtualMall.png"), "https://www.facebook.com/vmquito/", 5, "Dale click a la imagen para mas información"));
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("tol2dox.png"), "https://www.facebook.com/toldos.max.5", 2, "Dale click a la imagen para mas información"));
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("virtuallMallMensajeria.png"), "https://www.facebook.com/vmquito/", 4, "Dale click a la imagen para mas información"));
        return publicidades;
    }

    /**
     * Verifica y carga el Entity manager
     */
    public static void componentesBaseDatos(Boolean repetirCredenciales) {
        /**
         * Verificar si existen las credenciales de la base de datos o las genero
         */
        boolean ingresarCredenciales=false;
        ConfiguracionesInicalesModel.ModoEnum tipoCredencial=ConfiguracionesInicalesModel.ModoEnum.REGISTRAR; //Por defecto hacemos que sea registrar el dialog
        
        if(!repetirCredenciales) //Solo ingresar si la variables es false , que quiere decir que haga la verificacion normal
        {
            BaseDatosCredenciales credenciales=BaseDatosCredenciales.getInstance();            
            //verificar si existen los datos creados
            if(credenciales.cargarDatos())
            {
                String usuarioDb=credenciales.getUsuario();
                String claveDb=credenciales.getClave();
                //Si falta algun datos del usuario y la clave abro la pantalla de crear credenciales
                if(usuarioDb==null || claveDb==null)
                {
                    ingresarCredenciales=true;
                }

            }
            else
            {
                //Si no existe las credenciales verifico si ya existe la base de datos para ver que pantalla abrir para las crdenciales
                if(UtilidadesArchivos.verificarExiteArchivo("Derby2.DB")) //Todo: Setear en una variable global
                {
                    tipoCredencial=ConfiguracionesInicalesModel.ModoEnum.ACCEDER;
                }                
                ingresarCredenciales=true;
            }
        }
        else //Si pide repetir las credenciales directamente abro el dialogo de las credenciales
        {
            ingresarCredenciales=true;
            tipoCredencial=ConfiguracionesInicalesModel.ModoEnum.ACCEDER;
        }

        //Si no existen o faltan credenciales abro la pantalla para crear
        if (ingresarCredenciales) 
        {
            abrirDialogoCredencialesDB(tipoCredencial);
        }
        
        
        //Obtengo los datos para la base de datos
        BaseDatosCredenciales credenciales = BaseDatosCredenciales.getInstance();
        credenciales.cargarDatos();
        AbstractFacade.usuarioDb = credenciales.getUsuario();
        AbstractFacade.claveDb = credenciales.getClave();
        
        /**
         * Cargar la persistencia del servidor
         */
        try {
            
            AbstractFacade.cargarEntityManager();
        } catch (PersistenceException e) {
            try {
                System.out.println(e.getMessage());
                UtilidadesServidor.crearBaseDatos();
                
                AbstractFacade.cargarEntityManager();
            } catch (PersistenceException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PersistenciaDuplicadaException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                //System.out.println(ex.getErrorCode());
                
                if(ex.getSQLState().equals("08004")) //Este error es cuando las credenciales son incorrectas
                {
                    DialogoCodefac.mensaje("Error base de datos","Las credencias de la base de datos son incorrectas",DialogoCodefac.MENSAJE_ADVERTENCIA);                                    
                    componentesBaseDatos(true);//solicitar nuevamente las credenciales                    
                    //System.exit(0); //Si las credenciales son incorrectas se sale del sistema
                }
                else
                {
                    DialogoCodefac.mensaje("Error al crear la base de datos",ex.getMessage()+"\n Se recomienda eliminar y volver a crear la db",DialogoCodefac.MENSAJE_ADVERTENCIA);                
                }
                
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                //TODO: Mejorar esta parte eliminando la base de datos para evitar que ejecuten con problemas en la base
            }

        } catch (PersistenciaDuplicadaException ex) {
            DialogoCodefac.mensaje("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            System.exit(0);//Salir si existe otra instancia abierta
        }

    }
    
    private static void abrirDialogoCredencialesDB(ConfiguracionesInicalesModel.ModoEnum tipoRegistro)
    {
        ConfiguracionesInicalesModel configuraciones = new ConfiguracionesInicalesModel(tipoRegistro);
        configuraciones.setVisible(true);
        if (!configuraciones.datosGrabados) {
            System.exit(0); //Si no se grabo ningun dato se cierra porque el sistema no puede funcionar sin credenciales
        }
    }

    private static boolean comprobarLicencia(String pathBase) {

        //ParametroCodefacServiceIf servicio=ServiceFactory.getFactory().getParametroCodefacServiceIf();
        //String pathBase=servicio.getParametroByNombre(ParametroCodefac.DIRECTORIO_RECURSOS).valor;
        ValidacionLicenciaCodefac validacion = new ValidacionLicenciaCodefac();
        validacion.setPath(pathBase);
        
        Boolean existeLicencia=false;

        if (validacion.verificarExisteLicencia()) {
            try {
                if (validacion.validar()) {
                    return true;
                } else {//Si la licencia es incorrecta abre el dialogo de verificacion
                    DialogoCodefac.mensaje("Error", "No se puede validar la licencia, Posibles causas:\n - La licencia esta desactualizada \n - El archivo de la licencia fue modificado", DialogoCodefac.MENSAJE_INCORRECTO);
                    existeLicencia=true;
                }
            } catch (ValidacionLicenciaExcepcion ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoExisteLicenciaException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  //Cuando no existe la licencia

        
        
        //Crear un dialogo si no existe la licencia o esta desactualizada o con alguna incoherencia
        ValidarLicenciaModel licenciaDialog = new ValidarLicenciaModel(null, true, existeLicencia);
        licenciaDialog.setValidacionLicenciaCodefac(validacion);
        if (validacion.verificarConexionInternet()) {
            licenciaDialog.setVisible(true);
            if (licenciaDialog.licenciaCreada) {
                return comprobarLicencia(pathBase); //volver a verificar la licencia
            } else {
                return false;
            }
        } else {
            DialogoCodefac.mensaje("Error", "Para activar su producto conéctese a Internet", DialogoCodefac.MENSAJE_INCORRECTO);
            return false;
        }


    }

    public static List<Perfil> obtenerPerfilesUsuario(Usuario usuario) {
        try {
            PerfilServiceIf servicio = ServiceFactory.getFactory().getPerfilServicioIf();
            return servicio.obtenerPerfilesPorUsuario(usuario);
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static void cargarTemaCodefac() {
        ArchivoConfiguracionesCodefac archivo=ArchivoConfiguracionesCodefac.getInstance();
        String tema=archivo.obtenerValor(ArchivoConfiguracionesCodefac.CAMPO_TEMA);
        if(tema==null)
        {
            EstiloCodefacEnum estiloDefecto=EstiloCodefacEnum.GLASS;
            ArchivoConfiguracionesCodefac.getInstance().agregarCampo(ArchivoConfiguracionesCodefac.CAMPO_TEMA,estiloDefecto.getNombre());
            setearEstiloSistema(estiloDefecto);
        }
        else
        {
            setearEstiloSistema(EstiloCodefacEnum.findByNombre(tema));
        }
    }
    
    public static void setearEstiloSistema(EstiloCodefacEnum estiloCodefacEnum)
    {
        try {
            UIManager.setLookAndFeel(estiloCodefacEnum.getClassName());
            //repaint();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
