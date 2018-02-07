/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.init;


import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jtattoo.plaf.AbstractLookAndFeel;
import com.seaglasslookandfeel.SeaGlassLookAndFeel;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import com.sun.xml.internal.ws.client.ClientTransportException;
import ec.com.codesoft.codefaclite.configuraciones.model.CalculadoraModel;
import ec.com.codesoft.codefaclite.configuraciones.model.ComprobantesConfiguracionModel;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.AyudaCodefacModel;
import ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.PanelSecundarioAbstract;
import ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.ValidadorCodefacModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.crm.model.ClienteModel;
import ec.com.codesoft.codefaclite.crm.model.ClienteReporte;
import ec.com.codesoft.codefaclite.crm.model.CompraModel;
import ec.com.codesoft.codefaclite.crm.model.EmpresaModel;
import ec.com.codesoft.codefaclite.crm.model.ProductoModel;
import ec.com.codesoft.codefaclite.crm.model.ProductoReporte;
import ec.com.codesoft.codefaclite.facturacion.model.FacturaDisenioModel;
import ec.com.codesoft.codefaclite.facturacion.model.FacturaReporteModel;
import ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel;
import ec.com.codesoft.codefaclite.facturacion.model.NotaCreditoModel;
import ec.com.codesoft.codefaclite.facturacion.model.UtilidadComprobanteModel;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturacionPanel;
import ec.com.codesoft.codefaclite.inventario.model.AsociarProductoProveedorModel;
import ec.com.codesoft.codefaclite.inventario.model.BodegaModel;
import ec.com.codesoft.codefaclite.inventario.model.CategoriaProductoModel;
import ec.com.codesoft.codefaclite.inventario.model.IngresoInventarioModel;
import ec.com.codesoft.codefaclite.inventario.model.InventarioEnsambleModel;
import ec.com.codesoft.codefaclite.inventario.model.KardexModel;
import ec.com.codesoft.codefaclite.main.license.ValidacionLicenciaCodefac;
import ec.com.codesoft.codefaclite.main.license.excepcion.NoExisteLicenciaException;
import ec.com.codesoft.codefaclite.main.license.excepcion.ValidacionLicenciaExcepcion;
import ec.com.codesoft.codefaclite.main.model.GeneralPanelModel;
import ec.com.codesoft.codefaclite.main.model.HiloPublicidadCodefac;
import ec.com.codesoft.codefaclite.main.model.LoginModel;
import ec.com.codesoft.codefaclite.main.model.MenuControlador;
import ec.com.codesoft.codefaclite.main.model.SplashScreenModel;
import ec.com.codesoft.codefaclite.main.model.ValidarLicenciaModel;
import ec.com.codesoft.codefaclite.main.panel.ValidarLicenciaDialog;
import ec.com.codesoft.codefaclite.main.panel.publicidad.Publicidad;
import ec.com.codesoft.codefaclite.main.session.SessionCodefac;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.PersistenciaDuplicadaException;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.service.EmpresaService;
import ec.com.codesoft.codefaclite.servidor.service.ParametroCodefacService;
import ec.com.codesoft.codefaclite.servidor.service.PerfilServicio;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesServidor;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ParametroCodefacServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ServiceController;
import ec.com.codesoft.codefaclite.ws.codefac.test.service.WebServiceCodefac;
import ec.com.codesoft.ejemplo.utilidades.fecha.UtilidadesFecha;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Carlos
 */
public class Main {
    
    
    public static void main(String[] args) {      
        iniciarComponentes();
    }
    
    public static void iniciarComponentes()
    {
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
        SplashScreenModel splashScren=new SplashScreenModel();
        splashScren.agregarPorcentaje(40,"Cargando base de datos");
        splashScren.agregarPorcentaje(60,"Cargando datos session");
        splashScren.agregarPorcentaje(80,"Creando controlador codefac");
        splashScren.agregarPorcentaje(100,"Cargando ventanas");
        splashScren.setVisible(true);
        splashScren.iniciar();

        //DialogoCodefac.mensaje("uno mas","otro mas",DialogoCodefac.MENSAJE_CORRECTO);
        
        componentesIniciales();
        splashScren.siguiente();
        
       
                /**
         * Crear la session y cargar otro datos de la empresa
         */
        SessionCodefac session=new SessionCodefac();        
        EmpresaService empresaService = new EmpresaService();
        List<Empresa> empresaList=empresaService.obtenerTodos();
        
        if(empresaList!=null && empresaList.size()>0)
            session.setEmpresa(empresaList.get(0));
        
        //session.setParametrosCodefac(getParametros());
        splashScren.siguiente();
        
         /**
         * Seteando la session de los datos a utilizar en el aplicativo
         */
        GeneralPanelModel panel=new GeneralPanelModel();
        panel.setSessionCodefac(session);
        splashScren.siguiente();
        
        /**
         * Añadir menus y ventanas a la aplicacion principal
         */        
        panel.setVentanasMenuList(agregarMenuVentana(panel));
        panel.setPanelesSecundarios(agregarPanelesSecundarios());
        panel.agregarPanelesSecundarios();
        /**
         * Establecer propiedades del formulario principal
         */
        panel.setIconImage(new javax.swing.ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("logoCodefac-ico.png")).getImage()); // NOI18N        
        panel.setExtendedState(MAXIMIZED_BOTH);
        splashScren.siguiente();
        splashScren.termino();
        
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /**
         * Realizar Analisis para verificar si existe la licencia instalada
         */
        if(!comprobarLicencia())
        {
            System.exit(0);
        }
        else 
        {
            try {
                //Buscar el tipo de licencia paa setear en el sistema
                ParametroCodefacServiceIf servicio = ServiceController.getController().getParametroCodefacServiceIf();
                String pathBase = servicio.getParametroByNombre(ParametroCodefac.DIRECTORIO_RECURSOS).valor;
                ValidacionLicenciaCodefac validacion = new ValidacionLicenciaCodefac(pathBase);
                TipoLicenciaEnum tipoLicencia = validacion.getLicencia().getTipoLicenciaEnum();
                
                //Esta validacion es solo para usuario premium para cuando no paguen y tengamos que disminuir la licencia
                if(!TipoLicenciaEnum.GRATIS.equals(tipoLicencia))
                {
                    validacionCodefacOnline(validacion);
                    validacion = new ValidacionLicenciaCodefac(pathBase);
                    tipoLicencia = validacion.getLicencia().getTipoLicenciaEnum();
                }
                
                //Setear Variables de sesion
                session.setTipoLicenciaEnum(tipoLicencia);
                session.setUsuarioLicencia(validacion.obtenerLicencia().getProperty(ValidacionLicenciaCodefac.USUARIO));
            } catch (RemoteException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

        
        /**
         * Si el usuario devuuelto es incorrecto terminar el aplicativo
         */
        LoginModel loginModel=new LoginModel();
        loginModel.setVisible(true);
        Usuario usuarioLogin=loginModel.getUsuarioLogin();
        if(usuarioLogin==null)
        {
            System.out.println("aplicacion terminada");
            return ;
        }
        
        session.setUsuario(usuarioLogin);
        session.setPerfiles(obtenerPerfilesUsuario(usuarioLogin));
        
        /**
         * Agregando Hilo de Publicidad si es usuario Gratuito
         */
        if(session.getTipoLicenciaEnum().equals(TipoLicenciaEnum.GRATIS))
        {
            HiloPublicidadCodefac hiloPublicidad=new HiloPublicidadCodefac();
            hiloPublicidad.setPublicidades(obtenerPublicidades());
            hiloPublicidad.start();
            panel.setHiloPublicidadCodefac(hiloPublicidad);
        }
        panel.iniciarComponentesGenerales();
        panel.setVisible(true);
        
        
    }
    
    public static void validacionCodefacOnline(ValidacionLicenciaCodefac validacion)
    {
        try {
            ParametroCodefacServiceIf servicio = ServiceController.getController().getParametroCodefacServiceIf();
            /**
             * Verificar si la licencia actual es la misma que tiene el servidor
             */
            ParametroCodefac parametroFechaValidacion = servicio.getParametroByNombre(ParametroCodefac.ULTIMA_FECHA_VALIDACION);
            if (parametroFechaValidacion != null) {
                String fechaStr = parametroFechaValidacion.getValor();
                if (!fechaStr.equals("")) {
                    try {
                        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
                        Date fechaUltimaRevision = formato.parse(fechaStr);
                        int dias = UtilidadesFecha.obtenerDistanciaDias(fechaUltimaRevision, UtilidadesFecha.hoy());
                        
                        //Validacion para evitar que cambien fechas del sistema o que corrompan la fecha
                        if(dias<0)
                        {
                            DialogoCodefac.mensaje("Error", "No se puede validar su licencia ,inconsistencia con las fechas", DialogoCodefac.MENSAJE_INCORRECTO);
                            System.exit(0);
                            
                        }
                        
                        //Revisar la licencia cada despues de 15 dias con un rango maximo de 30 dias 
                        if (dias > 15 && dias < 30) {
                            if (verificarLicenciaOnline(validacion)) {
                                grabarFechaRevision(parametroFechaValidacion,false);
                            }
                        }

                        //Si execde los 30 dias sin validar por internet ya no permite el acceso
                        if (dias >= 30) {
                            if (verificarLicenciaOnline(validacion)) {
                                grabarFechaRevision(parametroFechaValidacion,false);
                            } else {
                                //Si no se logro validar la licencia durante 30 dias ya no se abre el software
                                DialogoCodefac.mensaje("Error", "No se puede validar su licencia , verifique su conexión a internet", DialogoCodefac.MENSAJE_INCORRECTO);
                                System.exit(0);
                            }
                        }

                    } catch (ParseException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    if(verificarLicenciaOnline(validacion)) //no se pone en un if, porque esta controlado en el metodo si no existe salir
                    {
                        grabarFechaRevision(parametroFechaValidacion,false);                    
                    }
                    else
                    {
                        //Si no se logro validar la licencia por primera vez  no se abre el software
                        DialogoCodefac.mensaje("Error", "No se puede validar su licencia , verifique su conexión a internet", DialogoCodefac.MENSAJE_INCORRECTO);
                        System.exit(0);
                        
                    }
                }

            }
            else //cuando no se tiene registro de la fecha de validacion
            {
                if (verificarLicenciaOnline(validacion)) 
                {
                    grabarFechaRevision(parametroFechaValidacion,true);
                }
                else
                {
                    //Si no se logro validar la licencia por primera vez  no se abre el software
                    DialogoCodefac.mensaje("Error", "No se puede validar su licencia , verifique su conexión a internet", DialogoCodefac.MENSAJE_INCORRECTO);
                    System.exit(0);
                }
            
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void grabarFechaRevision(ParametroCodefac parametroFechaValidacion,boolean crear)
    {
        if(crear)
        {
            parametroFechaValidacion=new ParametroCodefac();
            parametroFechaValidacion.setNombre(ParametroCodefac.ULTIMA_FECHA_VALIDACION);
        }
        
        ParametroCodefacServiceIf servicio=ServiceController.getController().getParametroCodefacServiceIf();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaHoy = UtilidadesFecha.getFechaHoy();
        parametroFechaValidacion.setValor(format.format(fechaHoy));
        if(crear)
            servicio.grabar(parametroFechaValidacion);
        else
            servicio.editar(parametroFechaValidacion);
    }
    
    /**
     * Verifica que la licencia que esta en el computador sea la misma que la del servidor
     * @return 
     */
    public static boolean verificarLicenciaOnline(ValidacionLicenciaCodefac validacion) throws ClientTransportException
    {
        try
        {
            String usuario=validacion.obtenerLicencia().getProperty(ValidacionLicenciaCodefac.USUARIO);
            String licencia = WebServiceCodefac.getLicencia(usuario);
            String tipoLicencia = WebServiceCodefac.getTipoLicencia(usuario);

            String tipoLicenciaPc=validacion.obtenerLicencia().getProperty(ValidacionLicenciaCodefac.TIPO_LICENCIA);
            String licenciaPc=validacion.obtenerLicencia().getProperty(ValidacionLicenciaCodefac.LICENCIA);


            if(licencia.equals(licenciaPc) && tipoLicencia.equals(TipoLicenciaEnum.getEnumByNombre(tipoLicenciaPc).getLetra()))
            {             
                return true;
            }
            else
            {
                //cuando la licencia es incorrecta se vuelve a descargar
                validacion.crearLicencia(usuario,tipoLicencia);
                if(validacion.validar())
                {
                    return true;
                }                
            }
        }
        catch(com.sun.xml.internal.ws.client.ClientTransportException cte)
        {
            return false;
        } catch (ValidacionLicenciaExcepcion ex) {
            return false;
        } catch (NoExisteLicenciaException ex) {
            return false;
        }
        return false;
    }
    
    public static Map<String,ParametroCodefac> getParametros()
    {
        Map<String,ParametroCodefac> parametros=new HashMap<String,ParametroCodefac>();
        ParametroCodefac param=new ParametroCodefac();
        
        param.id=1;
        param.nombre=ParametroCodefac.NOMBRE_FIRMA_ELECTRONICA;
        param.valor="carlos_alfonso_sanchez_coyago.p12";        
        parametros.put(param.nombre,param);
        
        param=new ParametroCodefac();
        param.id=2;
        param.nombre=ParametroCodefac.CLAVE_FIRMA_ELECTRONICA;
        param.valor="Code17bwbtj";        
        parametros.put(param.nombre,param);
        
        
        param=new ParametroCodefac();
        param.id=3;
        param.nombre=ParametroCodefac.DIRECTORIO_RECURSOS;
        param.valor="E:/FacturacionOffline";        
        parametros.put(param.nombre,param);
        
        param=new ParametroCodefac();
        param.id=4;
        param.nombre=ParametroCodefac.MODO_FACTURACION;
        param.valor="pruebas";        
        parametros.put(param.nombre,param);
        
        param=new ParametroCodefac();
        param.id=5;
        param.nombre=ParametroCodefac.SRI_WS_RECEPCION;
        param.valor="https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";        
        parametros.put(param.nombre,param);

        param=new ParametroCodefac();
        param.id=6;
        param.nombre=ParametroCodefac.SRI_WS_RECEPCION_PRUEBA;
        param.valor="https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";        
        parametros.put(param.nombre,param);

        param=new ParametroCodefac();
        param.id=7;
        param.nombre=ParametroCodefac.SRI_WS_AUTORIZACION;
        param.valor="https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";        
        parametros.put(param.nombre,param);

        param=new ParametroCodefac();
        param.id=8;
        param.nombre=ParametroCodefac.SRI_WS_AUTORIZACION_PRUEBA;
        param.valor="https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";        
        parametros.put(param.nombre,param);
        
        param=new ParametroCodefac();
        param.id=9;
        param.nombre=ParametroCodefac.SECUENCIAL_FACTURA;
        param.valor="1";        
        parametros.put(param.nombre,param);
        
        param=new ParametroCodefac();
        param.id=10;
        param.nombre=ParametroCodefac.SECUENCIAL_GUIA_REMISION;
        param.valor="1";        
        parametros.put(param.nombre,param);
        
        param=new ParametroCodefac();
        param.id=11;
        param.nombre=ParametroCodefac.SECUENCIAL_NOTA_CREDITO;
        param.valor="1";        
        parametros.put(param.nombre,param);
        
        param=new ParametroCodefac();
        param.id=12;
        param.nombre=ParametroCodefac.SECUENCIAL_NOTA_DEBITO;
        param.valor="1";        
        parametros.put(param.nombre,param);
        
        param=new ParametroCodefac();
        param.id=13;
        param.nombre=ParametroCodefac.SECUENCIAL_RETENCION;
        param.valor="1";        
        parametros.put(param.nombre,param);
        
        return parametros;
    }
    
    /**
     * Metodo donde se van a ligar las ventanas con los menus correspondientes
     * @param panel
     * @return                                             
     */
    public static List<MenuControlador> agregarMenuVentana(GeneralPanelModel panel)
    {
        List<MenuControlador> ventanas=new ArrayList<MenuControlador>();
        ventanas.add(new MenuControlador(panel.getjMenuCliente(),ClienteModel.class));
        ventanas.add(new MenuControlador(panel.getjMenuProducto(),ProductoModel.class));
        ventanas.add(new MenuControlador(panel.getjMenuFactura(),FacturacionModel.class));
        ventanas.add(new MenuControlador(panel.getjMenuEmisor(),EmpresaModel.class));
        ventanas.add(new MenuControlador(panel.getjMenuComprobanteConfig(),ComprobantesConfiguracionModel.class));
        ventanas.add(new MenuControlador(panel.getjMenuCalculadora(),CalculadoraModel.class,false));
        ventanas.add(new MenuControlador(panel.getjMenuItemUtilidades(),UtilidadComprobanteModel.class,true));
        ventanas.add(new MenuControlador(panel.getjMenuItemNotaCredito(),NotaCreditoModel.class,true));
        ventanas.add(new MenuControlador(panel.getjMenuItemFacturaReporte(),FacturaReporteModel.class,true));
        ventanas.add(new MenuControlador(panel.getjMenuItemReporteCliente(),ClienteReporte.class,true));
        ventanas.add(new MenuControlador(panel.getjMenuItemReporteProducto(),ProductoReporte.class,true));
        ventanas.add(new MenuControlador(panel.getjMenuItemDisenador(),FacturaDisenioModel.class,true));
        ventanas.add(new MenuControlador(panel.getjMenuCompra(),CompraModel.class,true));
        ventanas.add(new MenuControlador(panel.getjMenuItemAsociarProducto(),AsociarProductoProveedorModel.class,true));
        ventanas.add(new MenuControlador(panel.getMenuBodega(),BodegaModel.class,true));
        ventanas.add(new MenuControlador(panel.getMenuCatProducto(),CategoriaProductoModel.class,true));
        ventanas.add(new MenuControlador(panel.getjMenuItemIngresarInventario(),IngresoInventarioModel.class,true));
        ventanas.add(new MenuControlador(panel.getjMenuItemKardex(),KardexModel.class,false));
        ventanas.add(new MenuControlador(panel.getjMenuItemInventarioEnsamble(),InventarioEnsambleModel.class,false));
        
        return ventanas;
    
    }
    
    public static Map<String,PanelSecundarioAbstract> agregarPanelesSecundarios()
    {
        Map<String,PanelSecundarioAbstract> paneles=new HashMap<String,PanelSecundarioAbstract>();
        paneles.put(PanelSecundarioAbstract.PANEL_AYUDA,new AyudaCodefacModel() );
        paneles.put(PanelSecundarioAbstract.PANEL_MONITOR,MonitorComprobanteModel.getInstance());
        paneles.put(PanelSecundarioAbstract.PANEL_VALIDACION,new ValidadorCodefacModel());
        return paneles;
    }
    
    public static List<Publicidad> obtenerPublicidades()
    {
        List<Publicidad> publicidades=new ArrayList<Publicidad>();
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("angelicaPerfumes.png"),"https://www.facebook.com/avonbellezacosmeticos/",3,"Dale click a la imagen para mas información"));
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("anunciateConNosotros.png"),"https://www.facebook.com/codefac.ec/",6,"Dale click a la imagen para mas información"));
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("desarrolloSoftware.png"),"https://www.facebook.com/codesoft.ec/",3,"Dale click a la imagen para mas información"));
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("publicidadCodesoft.png"),"https://www.facebook.com/codesoft.ec/",3,"Dale click a la imagen para mas información"));
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("publicidadPaquete.png"),"https://www.facebook.com/codefac.ec/",10,"Dale click a la imagen para mas información"));
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("publicidadVirtualMall.png"),"https://www.facebook.com/vmquito/",5,"Dale click a la imagen para mas información"));
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("tol2dox.png"),"https://www.facebook.com/toldos.max.5",2,"Dale click a la imagen para mas información"));
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("virtuallMallMensajeria.png"),"https://www.facebook.com/vmquito/",4,"Dale click a la imagen para mas información"));
        return publicidades;
    }
    
    
 
    public static void componentesIniciales()
    {
        try {
            AbstractFacade.cargarEntityManager();
        } catch (PersistenceException e) {
            try {
                System.out.println(e.getMessage());
                UtilidadesServidor.crearBaseDatos();
                //JOptionPane.showMessageDialog(null,"Creada base de datos");
                AbstractFacade.cargarEntityManager();
            } catch (PersistenceException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PersistenciaDuplicadaException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (PersistenciaDuplicadaException ex) {
            DialogoCodefac.mensaje("Error",ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            System.exit(0);//Salir si existe otra instancia abierta
        }
        
    }
    
    private static boolean comprobarLicencia()
    {
        try {
            ParametroCodefacServiceIf servicio=ServiceController.getController().getParametroCodefacServiceIf();
            String pathBase=servicio.getParametroByNombre(ParametroCodefac.DIRECTORIO_RECURSOS).valor;
            ValidacionLicenciaCodefac validacion = new ValidacionLicenciaCodefac();
            validacion.setPath(pathBase);
            
            if (validacion.verificarExisteLicencia())
            {
                try {
                    if(validacion.validar())
                    {
                        return true;
                    }
                    else
                    {//Si la licencia es incorrecta abre el dialogo de verificacion
                        DialogoCodefac.mensaje("Error","La licencia es incorrecta para esta maquina",DialogoCodefac.MENSAJE_INCORRECTO);
                        return false;
                    }
                } catch (ValidacionLicenciaExcepcion ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoExisteLicenciaException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else //Cuando no existe la licencia
            {
                //Crear un dialogo si no existe la licencia
                ValidarLicenciaModel licenciaDialog = new ValidarLicenciaModel(null, true,false);
                licenciaDialog.setValidacionLicenciaCodefac(validacion);
                if(validacion.verificarConexionInternet())
                {
                    licenciaDialog.setVisible(true);
                    if(licenciaDialog.licenciaCreada)
                    {
                        return comprobarLicencia(); //volver a verificar la licencia
                    }
                    else
                    {
                        return false;
                    }
                }
                else
                {
                    DialogoCodefac.mensaje("Error","Para activar su producto conéctese a Internet",DialogoCodefac.MENSAJE_INCORRECTO);
                    return false;
                }
            }
            return false;
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private static List<Perfil> obtenerPerfilesUsuario(Usuario usuario)
    {
        PerfilServicio servicio=new PerfilServicio();
        return servicio.obtenerPerfilesPorUsuario(usuario);
    }
}

