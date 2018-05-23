/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;



import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.configuraciones.model.CalculadoraModel;
import ec.com.codesoft.codefaclite.configuraciones.model.ComprobantesConfiguracionModel;
import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteListener;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.PanelSecundarioAbstract;
import ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.PanelSecundarioListener;
import ec.com.codesoft.codefaclite.corecodefaclite.ayuda.AyudaCodefacAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.util.LimpiarAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ConsolaGeneral;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ValidacionCodefacAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.validacionPersonalizadaAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazPostConstructPanel;
import ec.com.codesoft.codefaclite.crm.model.ClienteModel;
import ec.com.codesoft.codefaclite.crm.model.ProductoModel;
import ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.main.init.Main;
import ec.com.codesoft.codefaclite.main.license.ValidacionLicenciaCodefac;
import ec.com.codesoft.codefaclite.main.panel.GeneralPanelForm;
import ec.com.codesoft.codefaclite.main.panel.WidgetVentasDiarias;
import ec.com.codesoft.codefaclite.main.session.SessionCodefac;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.AccesoDirecto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AccesoDirectoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ParametroCodefacServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceControllerServer;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PermisoVentana;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CategoriaMenuEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.ws.codefac.test.service.WebServiceCodefac;
import ec.com.codesoft.codefaclite.utilidades.imagen.UtilidadImagen;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadVarios;
import es.mityc.firmaJava.ocsp.config.ServidorOcsp;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.ToolTipManager;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.SplitPaneUI;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledEditorKit;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;
import org.jfree.util.Log;
/**
 *
 * @author Carlos
 */
public class GeneralPanelModel extends GeneralPanelForm implements InterfazComunicacionPanel{

    private static final Logger LOG = Logger.getLogger(GeneralPanelModel.class.getName());
    
    private GeneralPanelModel generalPanelModel=this;
    private ControladorVista controladorVista;
    private ControladorCodefacInterface panelActual;
    private SwingBrowser browser ;
    private SwingBrowser browserPublicidad ;
    private List<VentanaEnum> ventanasMenuList;
    private SessionCodefac sessionCodefac;
    private Map<String,PanelSecundarioAbstract> panelesSecundariosMap;
    
    private static double PROPORCION_HORIZONTAL=0.75d;
    private static double PROPORCION_VERTICAL=0.7d;
    
    private static double PROPORCION_HORIZONTAL_DEFAULT=0.75d;
    private static double PROPORCION_VERTICAL_DEFAULT=0.7d;
    
    private static double PROPORCION_HORIZONTAL_MIN=0.95d;
    private static double PROPORCION_VERTICAL_MIN=0.95d;
    
    
    private static double PROPORCION_HORIZONTAL_INICIAL=0.999999999d;
    private static double PROPORCION_VERTICAL_INICIAL=0.7d;
    
    /**
     * Variable que va a controlar cada que tiempo se va a mostrar publicidad
     */
    private HiloPublicidadCodefac hiloPublicidadCodefac;
    
    /**
     * Referencia al widget de Virtuall Mall para poder trabajar con este objeto
     */
    private WidgetVirtualMallModelo widgetVirtualMall;
    private WidgetVentasDiarias widgetVentasDiarias;

    public GeneralPanelModel() 
    {
    
        
    }
    
    public void iniciarComponentesGenerales()
    {
        iniciarComponentes();        
        agregarListenerBotonesDefecto();
        agregarListenerBotones();
        agregarListenerSplit();
        agregarListenerFrame();
        agregarListenerGraphics();
        cargarDatosAdicionales();
               
        habilitarBotones(false);  
        
    }
    
    public void agregarPanelesSecundarios()
    {
        for (Map.Entry<String, PanelSecundarioAbstract> entry : panelesSecundariosMap.entrySet()) {
            String key = entry.getKey();
            PanelSecundarioAbstract value = entry.getValue();
            getjPanelSeleccion().addTab(value.getNombrePanel(), (Component) value);
            value.addListenerPanelSecundario(new PanelSecundarioListener() {
                @Override
                public void mostrar() {
                    mostrarPanelSecundario(true,key);
                }
            });
        }
    }
        
    private void agregarListenerFrame()
    {
        
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                
            }

            @Override
            public void windowClosing(WindowEvent e) {
                /*Boolean respuesta=DialogoCodefac.dialogoPregunta("Alerta","Estas seguro que deseas salir?",DialogoCodefac.MENSAJE_ADVERTENCIA);
                
                if(respuesta)
                {                    
                    grabarDatosSalir();
                    
                    //Solo detener la publicidad cuando exista
                    if(hiloPublicidadCodefac!=null)
                        hiloPublicidadCodefac.hiloPublicidad=false;                    
                    
                    //AbstractFacade.entityManager.close();
                    dispose();                    
                    System.exit(0);
                    
                }*/
                
                String[] opciones={"Salir","Cambiar usuario","Cancelar"};
                int opcionSeleccionada=DialogoCodefac.dialogoPreguntaPersonalizada("Alerta","Porfavor seleccione una opción?",DialogoCodefac.MENSAJE_ADVERTENCIA,opciones);
                switch(opcionSeleccionada)
                {
                    case 0: //opcion de salir
                        grabarDatosSalir();

                        //Solo detener la publicidad cuando exista
                        if (hiloPublicidadCodefac != null) {
                            hiloPublicidadCodefac.hiloPublicidad = false;
                        }
                        dispose();
                        System.exit(0);
                        break;
                        
                    case 1:
                        cerrarTodasPantallas();
                        setVisible(false);
                        Usuario usuario=Main.cargarLoginUsuario();
                        sessionCodefac.setUsuario(usuario);
                        sessionCodefac.setPerfiles(Main.obtenerPerfilesUsuario(usuario));
                        setVentanasMenuList(null);
                        setVisible(true);
                        break;
                        
                    case 2:
                        break;
                }
                
                
                
                
            }

            @Override
            public void windowClosed(WindowEvent e) {
                
            }

            @Override
            public void windowIconified(WindowEvent e) {
                
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                
                
            }
        });
    }
    
    private void cerrarTodasPantallas()
    {
        JInternalFrame[] ventanas = getjDesktopPane1().getAllFrames();
        for (JInternalFrame ventana : ventanas) {
            ventana.dispose();

        }

    }
    
    private void grabarDatosSalir()
    {
        ParametroCodefacServiceIf servicio=ServiceFactory.getFactory().getParametroCodefacServiceIf();
        //Grabar el celular si es distinto de vacio
        if(!widgetVirtualMall.getTxtCelular().equals(""))
        {
            try {
                ParametroCodefac parametro=servicio.getParametroByNombre(ParametroCodefac.CELULAR_VIRTUAL_MALL);
                //Si no existe el parametro la crea en la base de datos
                if(parametro==null)
                {
                    parametro=new ParametroCodefac();
                    parametro.setNombre(ParametroCodefac.CELULAR_VIRTUAL_MALL);
                    parametro.setValor(widgetVirtualMall.getTxtCelular().getText());
                    servicio.grabar(parametro);
                }
                else
                {
                    parametro.setValor(widgetVirtualMall.getTxtCelular().getText());
                    servicio.editar(parametro);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    private void agregarListenerGraphics()
    {
                
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                establecerImagenFondo();
            }
        
        });
    }
    
    public void establecerImagenFondo()
    {
        Image fondoImg=null;
        fondoImg = new javax.swing.ImageIcon(getClass().getResource("/img/general/fondoGeneral.png")).getImage();
        if(sessionCodefac!=null)
        {
            //Solo establecer fondos personalizados para usuarios pro
            if(sessionCodefac.getTipoLicenciaEnum().equals(TipoLicenciaEnum.PRO))
            {
                try {
                    ParametroCodefacServiceIf servicio=ServiceFactory.getFactory().getParametroCodefacServiceIf();
                    Map<String,ParametroCodefac> map=servicio.getParametrosMap();
                    if(map!=null)
                    {
                        ParametroCodefac parametroCodefac=map.get(ParametroCodefac.IMAGEN_FONDO);
                        if(parametroCodefac!=null)
                        {
                            String valor=parametroCodefac.getValor();
                            if(!valor.equals(""))
                            {
                                
                                InputStream inputStream=RemoteInputStreamClient.wrap(ServiceFactory.getFactory().getRecursosServiceIf().getResourceInputStreamByFile(DirectorioCodefac.IMAGENES,valor));
                                //String pathImagen=map.get(ParametroCodefac.DIRECTORIO_RECURSOS).valor + "/" + DirectorioCodefac.IMAGENES.getNombre() + "/"+valor;
                                BufferedImage bufferImage= ImageIO.read(inputStream);
                                fondoImg = bufferImage;
                            }
                        }
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        getjDesktopPane1().setBorder(new Fondo(fondoImg));

        getjSplitPanel().setDividerLocation(PROPORCION_HORIZONTAL);
        getjSplitPanelVerticalSecundario().setDividerLocation(PROPORCION_VERTICAL);
        
    }
    
    private void agregarListenerSplit()
    {
        SplitPaneUI spui = this.getjSplitPanel().getUI();
        if (spui instanceof BasicSplitPaneUI) {
          // Setting a mouse listener directly on split pane does not work, because no events are being received.
          ((BasicSplitPaneUI) spui).getDivider().addMouseListener(new MouseAdapter() {
              @Override
              public void mouseReleased(MouseEvent e) {
                  //System.out.println("mouseReleasedE");
                  int division=getjSplitPanel().getDividerLocation();
                  int ancho=getWidth();
                  PROPORCION_HORIZONTAL=(double)division/(double)ancho;
                  //System.out.println("division:"+division+"ancho:"+ancho+"p1:"+PROPORCION_HORIZONTAL+">"+PROPORCION_HORIZONTAL_MIN);
                  //if(PROPORCION_HORIZONTAL>PROPORCION_HORIZONTAL_MIN)
                 //{
                      //PROPORCION_HORIZONTAL=PROPORCION_HORIZONTAL_INICIAL;
                  //}
                  getjSplitPanel().setDividerLocation(PROPORCION_HORIZONTAL);
                  
                  //System.out.println("division: "+division+"ancho: "+ancho+"proporcionos: "+proporcion);
              }
             
            });
        }
        
        SplitPaneUI spui2 = this.getjSplitPanelVerticalSecundario().getUI();
        if (spui2 instanceof BasicSplitPaneUI) {
          // Setting a mouse listener directly on split pane does not work, because no events are being received.
          ((BasicSplitPaneUI) spui2).getDivider().addMouseListener(new MouseAdapter() {
              @Override
              public void mouseReleased(MouseEvent e) {
                  //System.out.println("mouseReleasedE");
                  /*
                  int division=getjSplitPanelVerticalSecundario().getDividerLocation();
                  int ancho=getHeight();
                  PROPORCION_VERTICAL=(double)division/(double)ancho;
                  if(PROPORCION_VERTICAL>PROPORCION_VERTICAL_MIN)
                  {
                      PROPORCION_VERTICAL=PROPORCION_VERTICAL_INICIAL;
                  }
                  getjSplitPanel().setDividerLocation(PROPORCION_VERTICAL);
                  */
                  //System.out.println("division: "+division+"ancho: "+ancho+"proporcionos: "+proporcion);
              }             
            });
        }
        
    
    }
    
    
    private void cargarAyuda()
    {
        ControladorCodefacInterface panel=(ControladorCodefacInterface) getjDesktopPane1().getSelectedFrame();
        String url="";
        try
        {
            if(panel!=null)
            {
                url=panel.getURLAyuda();
            }
            else
            {
                url="http://www.cf.codesoft-ec.com/ayuda";
            }
        }
        catch (UnsupportedOperationException exception) {
            System.out.println("metodo no implementado");
            return ;
        }
        
        
        PanelSecundarioAbstract panelSecundario = panelesSecundariosMap.get(PanelSecundarioAbstract.PANEL_AYUDA);
        JPanel jpanel = (JPanel) panelSecundario;
        int ancho=getjPanelSeleccion().getWidth()-1;
        int alto=getjPanelSeleccion().getHeight()-1;

        if(browser!=null && panel!=null)
        {
            //Verifacar si la url cargada es la misma no volver a cargar
            if(!browser.getUrl().equals(panel.getURLAyuda()))
            {
                browser = new SwingBrowser();
                browser.loadURL(panel.getURLAyuda());
                browser.setBounds(1, 1,ancho,alto);
                jpanel.removeAll();
                jpanel.add(browser);
            }
            else
            {
                jpanel.removeAll();
                jpanel.add(browser);
            }
        }
        else
        {
            browser = new SwingBrowser();
            if(panel!=null)
            {
                browser.loadURL(panel.getURLAyuda());
                browser.setBounds(1, 1,ancho,alto);
                jpanel.removeAll();
                jpanel.add(browser);
            }
            else
            {
                //Pagina por defecto cuando no existe una ayuda especifica
                browser.loadURL("http://www.cf.codesoft-ec.com/ayuda");
                browser.setBounds(1, 1,ancho,alto);
                jpanel.removeAll();
                jpanel.add(browser);
            }
        }
        //getjSplitPanelVerticalSecundario().setLeftComponent(getJPanelContenidoAuxiliar());
            
    }
    
    /**
     * Carga toda la ayuda por defecto sin importar el panel
     */
    private void cargarAyudaTodo()
    {

        PanelSecundarioAbstract panelSecundario = panelesSecundariosMap.get(PanelSecundarioAbstract.PANEL_AYUDA);
        JPanel jpanel = (JPanel) panelSecundario;
        int ancho=getjPanelSeleccion().getWidth()-1;
        int alto=getjPanelSeleccion().getHeight()-1;

        if(browser!=null)
        {
            //Verifacar si la url cargada es la misma no volver a cargar
            if(!browser.getUrl().equals("http://www.cf.codesoft-ec.com/ayuda"))
            {
                browser = new SwingBrowser();
                browser.loadURL("http://www.cf.codesoft-ec.com/ayuda");
                browser.setBounds(1, 1,ancho,alto);
                jpanel.removeAll();
                jpanel.add(browser);
            }
            else
            {
                jpanel.removeAll();
                jpanel.add(browser);
            }
        }
        else
        {
            browser = new SwingBrowser();
            browser.loadURL("http://www.cf.codesoft-ec.com/ayuda");
            browser.setBounds(1, 1,ancho,alto);
            jpanel.removeAll();
            jpanel.add(browser);
        }
        //getjSplitPanelVerticalSecundario().setLeftComponent(getJPanelContenidoAuxiliar());
            
    }
    
    private void cargarPublicidad()
    {
            browserPublicidad = new SwingBrowser();
            browserPublicidad.loadURL("http://www.vm.codesoft-ec.com/general/publicidad/b");
            browserPublicidad.setBounds(1, 1, getjPanelPublicidadContenido().getWidth() - 1, getjPanelPublicidadContenido().getHeight() - 1);
            getjPanelPublicidadContenido().removeAll();
            getjPanelPublicidadContenido().add(browserPublicidad);            
            //PROPORCION_VERTICAL=PROPORCION_VERTICAL_DEFAULT;
            //getjSplitPanelVerticalSecundario().setDividerLocation(PROPORCION_VERTICAL);
    }
    
    
    private void agregarListenerMenu()
    {
        for (VentanaEnum menuControlador : ventanasMenuList) {
            
            if(menuControlador.getJmenuItem()==null)
                continue; //Si no tiene asiganod un jmenu item continua al siguiente menu
            
            menuControlador.getJmenuItem().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                        ControladorCodefacInterface ventana= (ControladorCodefacInterface) menuControlador.getInstance();
                        if(!verificarPantallaCargada(ventana))
                        {
                            //Este artificio se realiza porque cuando se reutilizaba un referencia de la pantalla generaba problemas con los dialogos
                            ventana= (ControladorCodefacInterface) menuControlador.createNewInstance();
                            agregarListenerMenu(ventana,menuControlador.isMaximizado());                    
                        }                        
                        else
                        {
                            try {
                                ventana.setSelected(true);
                            } catch (PropertyVetoException ex) {
                                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        
                }
            });
        }
        

    }
    
    /**
     * Verifica si la pantalla ya esta cargada en el escritorio
     * @return 
     */
    private boolean verificarPantallaCargada(ControladorCodefacInterface ventana)
    {
         JInternalFrame[] ventanas=getjDesktopPane1().getAllFrames();
         for (JInternalFrame ventanTemp : ventanas) {
            if(ventanTemp.equals(ventana))
            {
                return true;
            }
        }
         return false;
    }
    
    private String getTituloOriginal(String titulo)
    {
        int inicio=titulo.indexOf("[");
        if(inicio<0)
            return titulo;
        else
            return titulo.substring(0,inicio-1);
    }
    
    private void agregarListenerBotones()
    {
        getBtnHome().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               minimizarTodasVentanas();
            }
        });
        
        getBtnNuevo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame frame= getjDesktopPane1().getSelectedFrame();
                ControladorCodefacInterface frameInterface=(ControladorCodefacInterface) frame;
                
                /**
                 * Si ciclo de vida esta desactivado controlo manualmente el ciclo de vida
                 */
                if(!frameInterface.cicloVida)
                {
                    frameInterface.limpiar();
                    return;
                }
                
                try
                {
                    frameInterface.nuevo();
                }
                catch(UnsupportedOperationException exception)
                {
                    System.out.println("metodo no implementado"); 
                } catch (ExcepcionCodefacLite ex) {
                    //Cancela el ciclo de vida normal si manda una excecion
                    ex.printStackTrace();
                    return;
                }
                
                String tituloOriginal=getTituloOriginal(frame.getTitle());
                frame.setTitle(tituloOriginal+" [Nuevo]");
                frameInterface.estadoFormulario= ControladorCodefacInterface.ESTADO_GRABAR;
                limpiarAnotaciones(frameInterface);
                
                
                try
                {
                    frameInterface.limpiar();
                }
                catch(UnsupportedOperationException exception)
                {
                    System.out.println("metodo no implementado"); 
                }
                    
                limpiarCamposValidacion(frameInterface);
                frameInterface.consola=new ConsolaGeneral();
                mostrarConsola(frameInterface.consola,true);
            }
        });
        
        getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame frame = getjDesktopPane1().getSelectedFrame();
                ControladorCodefacInterface frameInterface = (ControladorCodefacInterface) frame;
                /**
                 * Verificar si el proceso es normal o la ventan funciona como dialogo
                 */
                if(frameInterface.modoDialogo)
                {
                    if(validarFormulario(frameInterface,ValidacionCodefacAnotacion.GRUPO_FORMULARIO))
                    {
                        DialogInterfacePanel interfaz = (DialogInterfacePanel) frame;
                        Object resultado = interfaz.getResult();
                        frameInterface.formOwner.updateInterface(resultado);
                        frame.dispose();
                        mostrarPanelSecundario(false);
                    }
                    else
                    {
                        mostrarPanelSecundario(true);
                    }
                    return;
                    
                }
                
                
                try
                {
                    boolean procesoTerminado=false;
                    
                    if(frameInterface.estadoFormulario.equals(ControladorCodefacInterface.ESTADO_GRABAR))
                    {
                        
                        if(validarFormulario(frameInterface,ValidacionCodefacAnotacion.GRUPO_FORMULARIO))
                        {
                            try {
                                frameInterface.grabar();
                                procesoTerminado=true;
                            } catch (ExcepcionCodefacLite ex) {
                                //ex.printStackTrace();
                                System.err.println(ex.getMessage());
                                //JOptionPane.showMessageDialog(null,ex.getMessage());
                            }
                            
                        }
                        else
                        {
                            mostrarConsola(frameInterface.consola,true);
                            //JOptionPane.showMessageDialog(null,"Error de validacion Nuevo");
                        }
                    }
                    else
                    {
                        if(validarFormulario(frameInterface,ValidacionCodefacAnotacion.GRUPO_FORMULARIO))
                        {
                            try {
                                frameInterface.editar();
                                procesoTerminado=true;
                            } catch (ExcepcionCodefacLite ex) {
                                 ex.printStackTrace();
                                //JOptionPane.showMessageDialog(null,ex.getMessage());
                            }
                            
                        }
                        else
                        {
                            mostrarConsola(frameInterface.consola,true);
                            //JOptionPane.showMessageDialog(null,"Error de validacion Editar");
                        }
                    
                    }

                    /**
                     * Si ciclo de vida esta desactivado controlo manualmente el
                     * ciclo de vida
                     */
                    if (!frameInterface.cicloVida) {
                        //frameInterface.limpiar();
                        return;
                    }
                    
                    if(procesoTerminado)
                    {
                        String tituloOriginal=getTituloOriginal(frame.getTitle());
                        frame.setTitle(tituloOriginal+" [Nuevo]");
                        frameInterface.estadoFormulario=ControladorCodefacInterface.ESTADO_GRABAR;
                        limpiarAnotaciones(frameInterface);
                        frameInterface.limpiar();
                        limpiarCamposValidacion(frameInterface);
                    }
                }
                catch (UnsupportedOperationException ex) {
                    System.err.println("Metodo no implementado boton editar");
                    //Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    //getjButton4().setEnabled(false);
                }
                               
            }
        });
        
        
        
        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try
                {
                    JInternalFrame frame= getjDesktopPane1().getSelectedFrame();
                    ControladorCodefacInterface frameInterface=(ControladorCodefacInterface) frame;
                    
                    /**
                     * Si ciclo de vida esta desactivado controlo manualmente el
                     * ciclo de vida
                     */
                    if (!frameInterface.cicloVida) {
                        try {
                            frameInterface.buscar();
                        } catch (ExcepcionCodefacLite ex) {
                            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        return;
                    }
                    
                   
                    try {
                        frameInterface.buscar();
                        frameInterface.estadoFormulario= ControladorCodefacInterface.ESTADO_EDITAR;
                        limpiarCamposValidacion(frameInterface);
                        mostrarPanelSecundario(false);
                        
                        String tituloOriginal = getTituloOriginal(frame.getTitle());
                        frame.setTitle(tituloOriginal + " [Editar]");

                    } catch (ExcepcionCodefacLite ex) {
                        //ex.printStackTrace();
                        System.out.println("Cancelado metodo buscar");
                        //JOptionPane.showMessageDialog(null,ex.getMessage());
                    }

                }
                catch (UnsupportedOperationException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    //getjButton4().setEnabled(false);
                }
                               
            }
        });
        
        getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try
                {
                    JInternalFrame frame= getjDesktopPane1().getSelectedFrame();
                    ControladorCodefacInterface frameInterface=(ControladorCodefacInterface) frame;
                    
                    /**
                     * Si ciclo de vida esta desactivado controlo manualmente el
                     * ciclo de vida
                     */
                    if (!frameInterface.cicloVida) {
                        frameInterface.actualizar();
                        return;
                    }
                    
                    frameInterface.actualizar();
                    limpiarCamposValidacion(frameInterface);
                }
                catch (UnsupportedOperationException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    //getjButton4().setEnabled(false);
                } catch (ExcepcionCodefacLite ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                               
            }
        });
        
        getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try
                {
                    JInternalFrame frame= getjDesktopPane1().getSelectedFrame();
                    ControladorCodefacInterface frameInterface=(ControladorCodefacInterface) frame;
                    
                    /**
                     * Si ciclo de vida esta desactivado controlo manualmente el
                     * ciclo de vida
                     */
                    if (!frameInterface.cicloVida) {
                        frameInterface.eliminar();
                        return;
                    }
                    
                    String tituloOriginal=getTituloOriginal(frame.getTitle());                    
                    frameInterface.eliminar();
                    frameInterface.estadoFormulario= ControladorCodefacInterface.ESTADO_GRABAR;
                    limpiarAnotaciones(frameInterface);
                    frameInterface.limpiar();
                    limpiarCamposValidacion(frameInterface);
                    
                    frameInterface.consola=new ConsolaGeneral();
                    mostrarConsola(frameInterface.consola,true);
                    frame.setTitle(tituloOriginal+" [Nuevo]");
                }
                catch (UnsupportedOperationException ex) {
                    System.err.println("Metodo no implementado");
                   // Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    //getjButton4().setEnabled(false);
                } catch (ExcepcionCodefacLite ex) {
                    //Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    System.err.println(ex.getMessage());
                }
                               
            }
        });
        
        getBtnImprimir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try
                {
                    JInternalFrame frame= getjDesktopPane1().getSelectedFrame();
                    ControladorCodefacInterface frameInterface=(ControladorCodefacInterface) frame;
                    
                    /**
                     * Si ciclo de vida esta desactivado controlo manualmente el
                     * ciclo de vida
                     */
                    if (!frameInterface.cicloVida) {
                        frameInterface.imprimir();
                        return;
                    }
                    
                    frameInterface.imprimir();
                    limpiarCamposValidacion(frameInterface);
                }
                catch (UnsupportedOperationException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    //getjButton4().setEnabled(false);
                }
                               
            }
        });
        
        getBtnAyuda().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cargarAyuda();
                    mostrarPanelSecundario(true,PanelSecundarioAbstract.PANEL_AYUDA);
                    
                }
            });
        
        
         
         getBtnSalirPantallaPublicidad().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getjSplitPanelVerticalSecundario().setDividerLocation(0.9999999999d);
                    PROPORCION_VERTICAL=0.9999999999d;
                    //browser=null;
                }
            });
        
    }   
    
    private void mostrarPanelSecundario(boolean  opcion)
    {
        if(opcion)
        {
            //Valores para mostrar en la pantalla secundaria
            PROPORCION_HORIZONTAL = PROPORCION_HORIZONTAL_DEFAULT;
            PROPORCION_VERTICAL = PROPORCION_VERTICAL_DEFAULT;
        }
        else
        {
            PROPORCION_HORIZONTAL = PROPORCION_HORIZONTAL_INICIAL;
            PROPORCION_VERTICAL = 0.9999999999999999d;

        }
        
        getjSplitPanel().setDividerLocation(PROPORCION_HORIZONTAL);
        getjSplitPanelVerticalSecundario().setDividerLocation(PROPORCION_VERTICAL);


    }
    
    private void mostrarPanelSecundario(boolean  opcion,String panelSecundario)
    {
        if(opcion)
        {
            //Valores para mostrar en la pantalla secundaria
            PROPORCION_HORIZONTAL = PROPORCION_HORIZONTAL_DEFAULT;
            PROPORCION_VERTICAL = PROPORCION_VERTICAL_DEFAULT;
        }
        else
        {
            PROPORCION_HORIZONTAL = PROPORCION_HORIZONTAL_INICIAL;
            PROPORCION_VERTICAL = 0.9999999999999999d;

        }
        
        getjSplitPanel().setDividerLocation(PROPORCION_HORIZONTAL);
        getjSplitPanelVerticalSecundario().setDividerLocation(PROPORCION_VERTICAL);
        getjPanelSeleccion().setSelectedComponent(panelesSecundariosMap.get(panelSecundario));

    }
    private boolean verificarPermisosVentana(List<String> permisosVentana,List<Perfil> rolesUsuario)
    {
        //Si la ventana no tiene permisos o no esta implementado puede acceder
        if(permisosVentana==null)
            return true;
        
        for (Perfil rolUsuario : rolesUsuario) {
            for (String permisoVentana : permisosVentana) {
                if(rolUsuario.getNombre().equals(permisoVentana))
                {
                    return true;
                }
            }
        }
        return false;
    
    }
    
    private void agregarListenerMenu(ControladorCodefacInterface panel,boolean maximisado)
    {
        try {
           
            /**
             * Agregar variables de session a la pantalla
             */
            //ParametroCodefacServiceIf servicio=ServiceFactory.getFactory().getParametroCodefacServiceIf();
            //sessionCodefac.setParametrosCodefac(servicio.getParametrosMap());
            
            panel.estadoFormulario= ControladorCodefacInterface.ESTADO_GRABAR;
            panel.panelPadre=generalPanelModel;
            panel.session=sessionCodefac;
            
            try
            {
                panel.iniciar();//Metodo que se ejecuta despues de construir el objeto
            }
            catch(java.lang.UnsupportedOperationException uoe)
            {
                System.err.println("Metodo no implementado");
            } catch (ExcepcionCodefacLite ex) {
                System.err.println("Cancelado metodo iniciar");
                return;
            }
            

            
            panel.addInternalFrameListener(listenerFrame);
            String tituloOriginal=getTituloOriginal(panel.getTitle());
            panel.setTitle(tituloOriginal+" [Nuevo]");
            getjDesktopPane1().add(panel);
            
            panel.setMaximum(maximisado);
            panel.show();
            getBtnNuevo().requestFocus();
            agregarValidadores(panel);
            agregarAyudas(panel);
            
            try
            {
                panel.limpiar();
            }catch(java.lang.UnsupportedOperationException uoe)
            {
            }
            
            /**
             * Mostrar la pantalla centrada cuando no se muestra maximisado
             */
            if(!maximisado)
            {
                Dimension desktopSize = getSize();
                Dimension jInternalFrameSize = panel.getSize();
                panel.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                        (desktopSize.height - jInternalFrameSize.height) / 2);
            }
            
            
            panel.consola=new ConsolaGeneral();
            mostrarConsola(panel.consola,true);
            

                        
        } catch (PropertyVetoException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void agregarAyudas(ControladorCodefacInterface panel)
    {
        Class classVentana=panel.getClass();
        Method[] metodos=classVentana.getMethods();
        for (Method metodo : metodos) {
            AyudaCodefacAnotacion validacion=metodo.getAnnotation(AyudaCodefacAnotacion.class);
            //System.out.println(metodo.getName());
            if(validacion!=null)
            {
                try {
                    JTextComponent componente=(JTextComponent) metodo.invoke(panel);
                    InputStream input=RecursoCodefac.AYUDA.getResourceInputStream(validacion.recurso());
                    String htmlText=UtilidadVarios.getStringHtmltoUrl(input);
                    File file = new File(RecursoCodefac.AYUDA.getResourceURL(validacion.recurso()).getPath());
                    //File file = new File(getClass().getResource("/pagina/ayudaHtml.html").toURI());
                    
                    String path="file:"+file.getParentFile().toURI().getPath();
                    htmlText=htmlText.replace("[recurso]",path);
                    ToolTipManager.sharedInstance().setDismissDelay(100000);
                    componente.setToolTipText(htmlText);

                } catch (IllegalAccessException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    private void limpiarAnotaciones(ControladorCodefacInterface panel)
    {
       boolean validado=true;
       
       Class classVentana=panel.getClass();
        Method[] metodos=classVentana.getMethods();
        for (Method metodo : metodos) {
            LimpiarAnotacion validacion=metodo.getAnnotation(LimpiarAnotacion.class);

            if(validacion!=null)
            {
                validado=false;
                try {
                    JTextComponent componente=(JTextComponent) metodo.invoke(panel);
                    componente.setText("");
                    
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
    
    private void limpiarCamposValidacion(ControladorCodefacInterface panel)
    {
       ConsolaGeneral consola=new ConsolaGeneral();
       
       Class classVentana=panel.getClass();
        Method[] metodos=classVentana.getMethods();
        for (Method metodo : metodos) {
            LimpiarAnotacion validacion=metodo.getAnnotation(LimpiarAnotacion.class);
            //System.out.println(metodo.getName());
            if(validacion!=null)
            {
                try {
                    JTextComponent componente=(JTextComponent) metodo.invoke(panel);
                    componente.setBackground(Color.WHITE);
                    
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
    
    private boolean validarFormulario(ControladorCodefacInterface panel,String grupo)
    {
       //Volver a crear los errores pendientes
       panel.consola=new ConsolaGeneral();
       boolean validado=true;       
       Class classVentana=panel.getClass();
        Method[] metodos=classVentana.getMethods();
        for (Method metodo : metodos) {
            ValidacionCodefacAnotacion validacion=metodo.getAnnotation(ValidacionCodefacAnotacion.class);
            //System.out.println(metodo.getName());
            if(validacion!=null)
            {
                if(validacion.grupo().equals(grupo))
                {
                    try {
                        JTextComponent componente=(JTextComponent) metodo.invoke(panel);
                        Vector<String> errores=validarComponente(validacion,componente,panel);

                        if(errores.size()>0)
                        {
                            //Si Existe errores pinto de colo amarillo
                            componente.setBackground(new Color(255,255,102));
                        }
                        else
                        {
                            //Si no existe error pinto de blanco
                            componente.setBackground(Color.white);
                        }

                        for (String error : errores) {
                            panel.consola.agregarDatos(validacion.nombre(),error,componente);
                            validado=false;
                        }



                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return validado;
    }
    
    //TODO optimizar metodo porque existen varios con codigo simila
    private boolean validarFormulario(ControladorCodefacInterface panel,String grupo,String nombreComponente)
    {
       //Volver a crear los errores pendientes
       panel.consola=new ConsolaGeneral();
       boolean validado=true;       
       Class classVentana=panel.getClass();
        Method[] metodos=classVentana.getMethods();
        for (Method metodo : metodos) {
            ValidacionCodefacAnotacion validacion=metodo.getAnnotation(ValidacionCodefacAnotacion.class);
            //System.out.println(metodo.getName());
            if(validacion!=null)
            {
                if(validacion.grupo().equals(grupo))
                {
                    if(validacion.nombre().equals(nombreComponente))
                    {
                        try {
                            JTextComponent componente = (JTextComponent) metodo.invoke(panel);
                            Vector<String> errores = validarComponente(validacion, componente, panel);

                            if (errores.size() > 0) {
                                //Si Existe errores pinto de colo amarillo
                                componente.setBackground(new Color(255, 255, 102));
                            } else {
                                //Si no existe error pinto de blanco
                                componente.setBackground(Color.white);
                            }

                            for (String error : errores) {
                                panel.consola.agregarDatos(validacion.nombre(), error, componente);
                                validado = false;
                            }

                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvocationTargetException ex) {
                            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    
                    }
                    
                }
            }
        }
        return validado;
    }
    
    
    

    
    private Vector<String> validarComponente(ValidacionCodefacAnotacion validacion,JTextComponent componente,ControladorCodefacInterface panel)
    {
        Vector<String> validar=new Vector<String>();
        if(validacion.requerido())
        {
            if(componente.getText().equals(""))
            {
                validar.add("campo requerido");
                
            }
        }
        else
        {
            //Si el campo no es requerido y no tiene valor no hago ninguna otra validacion
            if (componente.getText().equals("")) {
                return validar;
            }
        }
        
        if(componente.getText().length()<validacion.min())
        {
            validar.add("tamaño min requerido");
        }
        
        if(componente.getText().length()>validacion.max())
        {
            validar.add("tamaño max requerido");
        }
        
        if(!validacion.expresionRegular().equals("")){
            if(!Pattern.matches(validacion.expresionRegular(),componente.getText()))
            {
                if(!validacion.expresionRegularMensaje().equals(""))
                {
                    validar.add(validacion.expresionRegularMensaje());
                }
                else
                {
                    validar.add("expresion regular fallo");
                }
            }
        }
        
        String[] personalizados = validacion.personalizado();
        for (String personalizado : personalizados) {
            if (!personalizado.equals("")) {
                Method[] metodosValidar = panel.getClass().getMethods();
                for (Method method : metodosValidar) {
                    ///System.out.println(method.getName());
                    validacionPersonalizadaAnotacion validacionPersonalizada = method.getAnnotation(validacionPersonalizadaAnotacion.class);

                    if (validacionPersonalizada != null) {
                        if (personalizado.equals(method.getName())) {
                            try {
                                boolean resultado = (boolean) method.invoke(panel);
                                if (!resultado) {
                                    validar.add(validacionPersonalizada.errorTitulo());
                                }
                            } catch (IllegalAccessException ex) {
                                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalArgumentException ex) {
                                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InvocationTargetException ex) {
                                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        }
        

        
        return validar;
    }
    
    private void mostrarConsola(ConsolaGeneral consola,Boolean actualizarVista)
    {
        /*
       getjTablaConsola().addMouseListener(new MouseListener() {
           @Override
           public void mouseClicked(MouseEvent e) {
               //int fila=getjTablaConsola().getSelectedRow();
               //consola.seleccionarFila(fila);
               
           }
           @Override
           public void mousePressed(MouseEvent e) {
               
           }
           @Override
           public void mouseReleased(MouseEvent e) {
               
           }
           @Override
           public void mouseEntered(MouseEvent e) {
               
           }
           @Override
           public void mouseExited(MouseEvent e) {
               
           }
       });
       */
       PanelSecundarioAbstract panel=panelesSecundariosMap.get(PanelSecundarioAbstract.PANEL_VALIDACION);
       panel.actualizar(consola.getModeloTabla());
       //getJPanelContenidoAuxiliar().removeAll();
       //getJPanelContenidoAuxiliar().add(getJPanelConsola());
       //if(!actualizarVista)
        //   return;
               
       if(consola.getModeloTabla().getRowCount()>0)
       {
           mostrarPanelSecundario(true,PanelSecundarioAbstract.PANEL_VALIDACION);
       }
       else
       {
           mostrarPanelSecundario(false);
       }
      
    }
    
    private void agregarValidadores(ControladorCodefacInterface panel)
    {
       Class classVentana=panel.getClass();
        Method[] metodos=classVentana.getMethods();
        for (Method metodo : metodos) {
            ValidacionCodefacAnotacion validacion=metodo.getAnnotation(ValidacionCodefacAnotacion.class);
            if(validacion!=null)
            {
                if(validacion.grupo().equals(ValidacionCodefacAnotacion.GRUPO_FORMULARIO))
                {                    
                    try {
                        JTextComponent componente=(JTextComponent) metodo.invoke(panel);
                        componente.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, new Color(122, 138, 153)));
                        componente.addFocusListener(new FocusListener() {
                            @Override
                            public void focusGained(FocusEvent e) {

                            }

                            @Override
                            public void focusLost(FocusEvent e) {
                                System.out.println("focusLost");

                                //Este codigo se pone porque despues de cambiar de pantalla se ejecuta el evento de focus de la anterior
                                //y eso me genera problemas cuando quiero manejar los eventos de las jinternalFrame
                                if(!panel.equals(getPanelActivo()))
                                {
                                    //System.out.println("no validar porque cambio de pantalla");
                                    return;
                                }

                                if (panel.sinAcciones) {
                                    panel.sinAcciones = false;
                                    return;
                                }

                                if(panel.formularioCerrando)
                                {
                                    return;
                                }


                                Vector<String> errores = validarComponente(validacion, componente, panel);
                                panel.consola.quitarDato(componente);
                                for (String error : errores) {
                                    panel.consola.agregarDatos(validacion.nombre(), error, componente);
                                }

                                if(errores.size()>0)
                                {
                                    componente.setBackground(new Color(255,255,102));
                                    mostrarConsola(panel.consola,true);                                
                                }
                                else
                                {
                                    panel.consola.quitarDato(componente);
                                    componente.setBackground(Color.white);
                                    mostrarConsola(panel.consola,true);   

                                }

                            }
                        });

                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
    
    InternalFrameListener listenerFrame=new InternalFrameListener() {
                        @Override
                        public void internalFrameOpened(InternalFrameEvent e) {
                        }

                        @Override
                        public void internalFrameClosing(InternalFrameEvent e) {
                            // System.out.println("internalFrameClosing");
                            ControladorCodefacInterface panel=(ControladorCodefacInterface) getjDesktopPane1().getSelectedFrame();
                            panel.formularioCerrando=true;
                            cargarAyuda();                            
                            mostrarPanelSecundario(false);
                            
                        }

                        @Override
                        public void internalFrameClosed(InternalFrameEvent e) {
                               //System.err.println("internalFrameClosed");
                        }

                        @Override
                        public void internalFrameIconified(InternalFrameEvent e) {
                            //mostrarPanelSecundario(false);
                        }

                        @Override
                        public void internalFrameDeiconified(InternalFrameEvent e) {
                            //JOptionPane.showMessageDialog(null,"internalFrameDeiconified");
                            habilitarConfiguracioneBotones();
                            //mostrarPanelSecundario(true);
                        }

                        @Override
                        public void internalFrameActivated(InternalFrameEvent e) {
                            //JOptionPane.showMessageDialog(null,"internalFrameActivated");
                            habilitarConfiguracioneBotones();
                            
                            System.out.println("pantalla activida");
                            //mostrarPanelSecundario(true,PanelSecundarioAbstract.PANEL_MONITOR);
                            ControladorCodefacInterface panel = getPanelActivo();
                            if (panel != null) {
                                System.out.println("Panel Activo: "+panel.getTitle());
                                if (panel.consola != null) {
                                    mostrarConsola(getPanelActivo().consola, false);
                                    System.out.println(getPanelActivo().consola.getModeloTabla().getRowCount());
                                    //revalidate();
                                    //repaint();
                                    //revalidate();
                                    //repaint();
                                    System.out.println("consola seteado a otra pantalla");

                                }
                                else
                                    System.out.println("consola null");
                            }else
                                System.out.println("panel null");

                            //s
                           
                        }

                        @Override
                        public void internalFrameDeactivated(InternalFrameEvent e) {
                             habilitarBotones(false);
                             //System.err.println("internalFrameDeactivated");
                            //habilitarConfiguracioneBotones();
                            //JOptionPane.showMessageDialog(null,"internalFrameDeactivated");
                        }
     };
    
        
    
    private void habilitarBotones(Boolean opcion)
    {
        getBtnActualizar().setEnabled(opcion);
        //getBtnAyuda().setEnabled(opcion); //Siempre debe estar habilitado
        getBtnBuscar().setEnabled(opcion);
        getBtnEliminar().setEnabled(opcion);
        getBtnGuardar().setEnabled(opcion);
        getBtnImprimir().setEnabled(opcion);
        getBtnNuevo().setEnabled(opcion);
    }
    
    private ControladorCodefacInterface getPanelActivo()
    {
        JInternalFrame frame = getjDesktopPane1().getSelectedFrame();
        ControladorCodefacInterface frameInterface = (ControladorCodefacInterface) frame;
        return frameInterface;
    }
 
    /**
     * Habilita solo los botones disponibles para la pantalla
     */
    private void habilitarConfiguracioneBotones()
    {
        JInternalFrame frame = getjDesktopPane1().getSelectedFrame();
        ControladorCodefacInterface frameInterface = (ControladorCodefacInterface) frame;
        
        try
        {
            Map<Integer, Boolean> mapPermisos = frameInterface.permisosFormulario();
            for (Map.Entry<Integer, Boolean> entry : mapPermisos.entrySet()) {
                Integer key = entry.getKey();
                Boolean value = entry.getValue();
                JButton boton=null;

                switch (key) {
                    case ControladorCodefacInterface.BOTON_GRABAR:
                        boton=getBtnGuardar();
                        break;

                    case ControladorCodefacInterface.BOTON_ELIMINAR:
                        boton=getBtnEliminar();
                        break;

                    case ControladorCodefacInterface.BOTON_IMPRIMIR:
                        boton=getBtnImprimir();
                        break;

                    case ControladorCodefacInterface.BOTON_AYUDA:
                        boton=getBtnAyuda();
                        break;

                    case ControladorCodefacInterface.BOTON_NUEVO:
                        boton=getBtnNuevo();
                        break;

                    case ControladorCodefacInterface.BOTON_REFRESCAR:
                        boton=getBtnActualizar();
                        break;

                    case ControladorCodefacInterface.BOTON_BUSCAR:
                        boton=getBtnBuscar();
                        break;
                }
                
                boton.setEnabled(value);
                
                //Si es un super usuario no debe hacer mas validaciones de los botonew
                if(sessionCodefac.getUsuario().getNick().equals(Usuario.SUPER_USUARIO))
                {
                    continue; //continua con la siguiete validacion sin verificar permisos por roles de usuario
                }
                //Adicional de validar si la pantalla tiene disponible la opcion verificar si el usuario tiene permisos para los botones
                if(value) //Validar solo si el valor es positivo porque solo debo poder desactivar opciones
                {
                    Boolean permisoBotonUsuario=permisoBotonesRoles(frameInterface,key);
                    if(!permisoBotonUsuario)
                        boton.setEnabled(false); //Descivar si no tiene permisos
                }
                
                
            }
        }
        catch(java.lang.UnsupportedOperationException uoe)
        {
            //Si no esta implementado el metodo poner todos los botones en falso
            habilitarBotones(false);
        }
    }
    
    /**
     * Verificar si el usuario tiene permisos para activar el boton del menu
     * @return 
     */
    private boolean permisoBotonesRoles(ControladorCodefacInterface frame,Integer boton )
    {
        String claseNombre=frame.getClass().getName();
        List<Perfil> perfiles= sessionCodefac.getPerfiles();
        for (Perfil perfil : perfiles) {
            for (PermisoVentana permisoVentana : perfil.getVentanasPermisos()) {
                if(permisoVentana.getVentanaEnum().getClaseNombre().equals(claseNombre))
                {
                    if(permisoVentana.getPermisoBuscar().equals("s") && ControladorCodefacInterface.BOTON_BUSCAR==boton)
                        return true;
                    
                    if(permisoVentana.getPermisoEditar().equals("s") && ControladorCodefacInterface.BOTON_GRABAR==boton)
                        return true;
                    
                    if(permisoVentana.getPermisoEliminar().equals("s") && ControladorCodefacInterface.BOTON_ELIMINAR==boton)
                        return true;
                    
                    if(permisoVentana.getPermisoGrabar().equals("s") && ControladorCodefacInterface.BOTON_GRABAR==boton)
                        return true;
                    
                    if(permisoVentana.getPermisoImprimir().equals("s") && ControladorCodefacInterface.BOTON_IMPRIMIR==boton)
                        return true;                    
                }
            }
        }
        
        return false;
    }
    
    private void iniciarComponentes()
    {
        try {
            controladorVista=new ControladorVista();
            
            //Cargar configuraciones de los divisores
            PROPORCION_HORIZONTAL=PROPORCION_HORIZONTAL_INICIAL;
            PROPORCION_VERTICAL=PROPORCION_VERTICAL_INICIAL;
            
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            
            getjSplitPanel().setDividerLocation(PROPORCION_HORIZONTAL);
            getjSplitPanelVerticalSecundario().setDividerLocation(PROPORCION_VERTICAL);
            
            //Cargar el fondo de la pantalla
            Image fondoImg=new javax.swing.ImageIcon(getClass().getResource("/img.general/fondoGeneral.png")).getImage();
            getjDesktopPane1().setBorder(new Fondo(fondoImg));
            
            //Setear el segundo componente de la ventana auxliar
            getjSplitPanel().setRightComponent(getJpanelAuxiliar());
            
            //Cargar el componente de publicidad para que siempre exista
            cargarPublicidad();
            URL url=null;
            Map<String,Object> mapBuscar;
            AccesoDirectoServiceIf servicio=ServiceFactory.getFactory().getAccesoDirectoServiceIf();
            
            /***
             * Agregar el widget de virtualMall
             */
            mapBuscar = new HashMap<>();
            mapBuscar.put("nombre","WidgetVirtualMall");
            int x=servicio.obtenerPorMap(mapBuscar).get(0).x;
            int y=servicio.obtenerPorMap(mapBuscar).get(0).y;
            
            widgetVirtualMall=new WidgetVirtualMallModelo(getjDesktopPane1());
            widgetVirtualMall.setPreferredSize(new Dimension(x,y));
            widgetVirtualMall.setBounds(x,y,260, 400);
            widgetVirtualMall.addListenerIcono(new IconoInterfaz() {
                @Override
                public void doubleClick() {
                }
                
                @Override
                public void grabarNuevaPosicion(Point nuevaPosicion) {
                    try {
                        Map<String, Object> mapBuscar;
                        AccesoDirectoServiceIf servicio = ServiceFactory.getFactory().getAccesoDirectoServiceIf();
                        
                        mapBuscar = new HashMap<>();
                        mapBuscar.put("nombre","WidgetVirtualMall");
                        AccesoDirecto acceso=servicio.obtenerPorMap(mapBuscar).get(0);
                        acceso.setX((int)nuevaPosicion.getX());
                        acceso.setY((int)nuevaPosicion.getY());
                        servicio.editar(acceso);
                    } catch (RemoteException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            getjDesktopPane1().add(widgetVirtualMall);
            widgetVirtualMall.setVisible(true);
            
            /***
             * Agregar el widget de Ventas diarias
             */
            mapBuscar = new HashMap<>();
            mapBuscar.put("nombre", "WidgetVentasDiarias");
            int xVd=servicio.obtenerPorMap(mapBuscar).get(0).x;
            int yVd=servicio.obtenerPorMap(mapBuscar).get(0).y;
            
            widgetVentasDiarias = new VentasDiariasModel(getjDesktopPane1());
            widgetVentasDiarias.panelPadre=this;
            widgetVentasDiarias.setPreferredSize(new Dimension(xVd,yVd));
            widgetVentasDiarias.setBounds(xVd,xVd,220,330);
            
            widgetVentasDiarias.addListenerIcono(new IconoInterfaz() {
                @Override
                public void doubleClick() {
                }
                
                @Override
                public void grabarNuevaPosicion(Point nuevaPosicion) {
                    try {
                        Map<String, Object> mapBuscar;
                        AccesoDirectoServiceIf servicio = ServiceFactory.getFactory().getAccesoDirectoServiceIf();
                        mapBuscar = new HashMap<>();
                        mapBuscar.put("nombre","WidgetVentasDiarias");
                        AccesoDirecto acceso=servicio.obtenerPorMap(mapBuscar).get(0);
                        acceso.setX((int)nuevaPosicion.getX());
                        acceso.setY((int)nuevaPosicion.getY());
                        servicio.editar(acceso);
                    } catch (RemoteException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            //
            getjDesktopPane1().add(widgetVentasDiarias);
            widgetVentasDiarias.setVisible(true);
            /***
             * fin widget Ventas diarias
             */
            mapBuscar = new HashMap<>();
            mapBuscar.put("nombre", FacturacionModel.class.getName());
            url=RecursoCodefac.IMAGENES_ACCESO_DIRECTO.getResourceURL("factura.png");
            IconoPanel iconoFactura=new IconoPanel("Factura",url,getjDesktopPane1(),servicio.obtenerPorMap(mapBuscar).get(0).x,servicio.obtenerPorMap(mapBuscar).get(0).y);
            iconoFactura.addListenerIcono(new ListenerIcono(FacturacionModel.class,true));
            getjDesktopPane1().add(iconoFactura);
            
            
            mapBuscar=new HashMap<>();
            mapBuscar.put("nombre",ProductoModel.class.getName());
            url=RecursoCodefac.IMAGENES_ACCESO_DIRECTO.getResourceURL("producto.png");
            IconoPanel iconoPanel=new IconoPanel("Producto",url,getjDesktopPane1(),servicio.obtenerPorMap(mapBuscar).get(0).x,servicio.obtenerPorMap(mapBuscar).get(0).y);
            iconoPanel.addListenerIcono(new ListenerIcono(ProductoModel.class, true));
            getjDesktopPane1().add(iconoPanel);
            
            
            mapBuscar = new HashMap<>();
            mapBuscar.put("nombre", CalculadoraModel.class.getName());
            url=RecursoCodefac.IMAGENES_ACCESO_DIRECTO.getResourceURL("calculadora.png");
            IconoPanel iconoCalcu=new IconoPanel("Calculadora",url,getjDesktopPane1(),servicio.obtenerPorMap(mapBuscar).get(0).x,servicio.obtenerPorMap(mapBuscar).get(0).y);
            iconoCalcu.addListenerIcono(new ListenerIcono(CalculadoraModel.class,false));
            getjDesktopPane1().add(iconoCalcu);
            
            
            
            mapBuscar = new HashMap<>();
            mapBuscar.put("nombre", ClienteModel.class.getName());
            url=RecursoCodefac.IMAGENES_ACCESO_DIRECTO.getResourceURL("cliente.png");
            IconoPanel iconoCliente=new IconoPanel("Cliente",url,getjDesktopPane1(),servicio.obtenerPorMap(mapBuscar).get(0).x,servicio.obtenerPorMap(mapBuscar).get(0).y);
            iconoCliente.addListenerIcono(new ListenerIcono(ClienteModel.class,true));
            getjDesktopPane1().add(iconoCliente);
            
            
            mapBuscar = new HashMap<>();
            mapBuscar.put("nombre", ComprobantesConfiguracionModel.class.getName());
            url=RecursoCodefac.IMAGENES_ACCESO_DIRECTO.getResourceURL("configuracion.png");
            IconoPanel iconoConfig=new IconoPanel("Configurar",url,getjDesktopPane1(),servicio.obtenerPorMap(mapBuscar).get(0).x,servicio.obtenerPorMap(mapBuscar).get(0).y);
            iconoConfig.addListenerIcono(new ListenerIcono(ComprobantesConfiguracionModel.class,true));
            getjDesktopPane1().add(iconoConfig);
        } catch (RemoteException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void seleccionaPanel(ControladorCodefacInterface panelInterface)
    {
        
        this.panelActual=panelInterface;
        this.controladorVista.agregarVista(panelInterface);
    }
    
    public void agregarReportePantalla()
    {
        try {
            // Se construye el panel que ira dentro del JInternalFrame
            JPanel p = new JPanel();
            p.setLayout(new FlowLayout());
            p.add(new JLabel("JPanel para el repote jasper"));
            p.add(new JTextField(10));
            
            // Se construye el JInternalFrame
            JInternalFrame internal = new JInternalFrame("Un Internal Frame");
            internal.add(p);
            getjDesktopPane1().add(internal);
            internal.setMaximum(true);
            internal.show();
        } catch (PropertyVetoException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void crearReportePantalla(JasperPrint jasperPrint,String nombrePantalla) {
        JRViewer viewer=new JRViewer(jasperPrint);
        viewer.setZoomRatio(0.6f);
        JInternalFrame internal = new JInternalFrame("Un Internal Frame");
        internal.setClosable(true);
        internal.setIconifiable(true);
        internal.setMaximizable(true);
        internal.setResizable(true);
        internal.setTitle(nombrePantalla);
        
        //internal.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/report-ico.png"))); // NOI18N
        internal.setFrameIcon(new javax.swing.ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("report-ico.png"))); // NOI18N
        try {
            if (internal.isIcon()) {
                internal.setIcon(false);
            } else if (internal.isMaximum()) {
                internal.setMaximum(false);
            }
        } catch(PropertyVetoException e) {
            return;
        }
        Dimension pantallaPrincipal=getjDesktopPane1().getSize();
        Dimension pantallaReporte=new Dimension((int)((double)pantallaPrincipal.width/(double)2),(int)((double)pantallaPrincipal.height*3/(double)4));
        //internal.setPreferredSize(pantallaReporte);
        internal.setSize(pantallaReporte);
        internal.validate();
        internal.add(viewer);
        getjDesktopPane1().add(internal);
        internal.show();
    }

    /**
     * Metodo para poder abrir una ventana desde otra ventana
     * @param panel 
     */
    @Override
    public void crearVentanaCodefac(GeneralPanelInterface panel,boolean maximizado) {
        agregarListenerMenu((ControladorCodefacInterface) panel,maximizado);
    }
    
    public List<VentanaEnum> getVentanasMenuList() {
        return ventanasMenuList;
    }

    public void setVentanasMenuList(List<VentanaEnum> ventanasMenuList) {
        this.ventanasMenuList=VentanaEnum.getListValues();
        
        this.getJMenuBar().removeAll();
        List<JMenu> menus=construirMenuDinamico();
        this.getJMenuBar().add(getjMenuInicio());
        this.getJMenuBar().add(getjMenuUtilidades());
        
        for (JMenu menu : menus) {
            this.getJMenuBar().add(menu);
        }
        this.getJMenuBar().add(getjMenuAyuda());
        //actualizarMenuCodefac();
        agregarListenerMenu();
    }
    
    private boolean isModuloPermitido(ModuloCodefacEnum moduloVerificar)
    {
        Map<ModuloCodefacEnum, Boolean> modulosPermitidos =sessionCodefac.getModulosMap();
        for (Map.Entry<ModuloCodefacEnum, Boolean> entry : modulosPermitidos.entrySet()) {
            ModuloCodefacEnum key = entry.getKey();
            Boolean value = entry.getValue();
            
            if(value)
            {
                if(moduloVerificar.equals(key))
                {
                    return true;
                }
            }
            
        }
        return false;
    
    }
    
    private List<JMenu> construirMenuDinamico()
    {
        List<JMenu> menus=new ArrayList<JMenu>();
        
                
        for (ModuloCodefacEnum moduloSistema : ModuloCodefacEnum.values()) {

                JMenu menuModulo = new JMenu(moduloSistema.getNombre());
                menuModulo.setIcon(moduloSistema.getIcono());
                menuModulo.setFont(new Font("Arial",2,15));
                boolean existenCategorias=false;
                for (CategoriaMenuEnum categoriaEnum : CategoriaMenuEnum.values()) {
                    JMenu menuCategoria=new JMenu(categoriaEnum.getNombre());
                    menuCategoria.setIcon(categoriaEnum.getIcono());
                    menuCategoria.setFont(new Font("Arial", 0, 13));
                    
                    boolean existenMenuItem=false;
                    for (VentanaEnum menuControlador : ventanasMenuList) {
                        
                        //Verificacion cuando es un modulo habilitado
                        boolean agregarAlMenu=false;
                        if(isModuloPermitido(moduloSistema))
                        {
                            if(menuControlador.getModulo().equals(moduloSistema))
                            {
                                //Verifica si es super usuario carga todos los modulos
                                if(sessionCodefac.getUsuario().getNick().equals(Usuario.SUPER_USUARIO))
                                {
                                    agregarAlMenu=true;
                                    
                                }
                                else
                                {                                
                                    if(verificarMenuUsuario(menuControlador))
                                    {
                                        agregarAlMenu=true;
                                    }
                                }
                            }
                        }
                        else //Verificacion cuando no es un modulo habilitado
                        {
                            //Solo agregar otras ventanas de otros modulos si el menu pertenece al modulo actual
                            //Nota: sin esta linea pueden aparecer varios enlaces a esta ventana desde otros menus de modulos
                            if (menuControlador.getModulo().equals(moduloSistema)) {
                                //Verifica si es super usuario carga todos los modulos
                                if (sessionCodefac.getUsuario().getNick().equals(Usuario.SUPER_USUARIO)) 
                                {
                                    agregarAlMenu = true;
                                } 
                                else 
                                    if (menuControlador.verificarPermisoModuloAdicional(sessionCodefac.getModulosMap())) {
                                    if (verificarMenuUsuario(menuControlador)) {
                                        agregarAlMenu = true;
                                    }
                                }
                            }

                        
                        }
                        
                        
                        if (menuControlador.getCategoriaMenu().equals(categoriaEnum)&& agregarAlMenu ) {
                            existenMenuItem = true;
                            String nombreVentana = "Sin nombre";
                            try {
                                LOG.log(Level.INFO,moduloSistema.getNombre()+":"+categoriaEnum.getNombre()+"->"+menuControlador.getNombre());
                                nombreVentana =menuControlador.getNombre();
                            } catch (java.lang.UnsupportedOperationException uoe) {
                                LOG.log(Level.WARNING,menuControlador.getClass().getSimpleName() + ": Ventana sin implementar nombre");
                            }

                            JMenuItem menuVentana = new JMenuItem(nombreVentana);
                            menuVentana.setFont(new Font("Arial", 0, 13));
                            menuCategoria.add(menuVentana);

                            menuControlador.setJmenuItem(menuVentana);
                        }
                        
                    }
                    
                    if(existenMenuItem)
                    {
                        menuModulo.add(menuCategoria);
                        existenCategorias=true;
                    }
                    
                } 
                if(existenCategorias)
                {
                    menus.add(menuModulo);
                }
            //}
            
            
        }
        return menus;
    }
    
    /*
    Metodo que permite verificar si el usuario tiene permiso para el menu seleccionado
    */
    private boolean verificarMenuUsuario(VentanaEnum ventanaEnum)
    {
        List<Perfil> perfiles=sessionCodefac.getPerfiles();
        for (Perfil perfil : perfiles) {
            //Verificar si tiene permisos dentro de cada perfil asignado al usuario
            for (PermisoVentana permisoVentana : perfil.getVentanasPermisos()) {
                if(permisoVentana.getVentanaEnum().equals(ventanaEnum))
                {
                    return true;
                }
            }
 
        }
        return false;
    }
    
    /**
     * Permite actualizar los menus disponibles segun los modulos que tengan permisos
     */
    /*
    private void actualizarMenuCodefac()
    {
        for (MenuControlador menuControlador : ventanasMenuList) {
            
            
            if (!menuControlador.verificarPermisoModulo(sessionCodefac.getModulosMap())) //Verifica si la pantalla tiene permisos para los modulos cargados en
            {
                //Si no tiene permise solo oculto el menu para que no acceda
                menuControlador.getMenuItem().setVisible(false);

            }
            else
            {
                menuControlador.getMenuItem().setVisible(true);
            }
        }
        
    }*/

    @Override
    public Map<String, Object> mapReportePlantilla() {
        InputStream inputStream = null;

        SimpleDateFormat formateador = new SimpleDateFormat("d MMM yyyy HH:mm:ss");
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("pl_fecha_hora", formateador.format(new Date()));
        parametros.put("pl_usuario", sessionCodefac.getUsuario().getNick());
        parametros.put("pl_direccion", sessionCodefac.getEmpresa().getDireccion());
        parametros.put("pl_nombre_empresa", sessionCodefac.getEmpresa().getNombreLegal());
        parametros.put("pl_telefonos", sessionCodefac.getEmpresa().getTelefonos());
        parametros.put("pl_celular", sessionCodefac.getEmpresa().getCelular());
        parametros.put("pl_facebook", sessionCodefac.getEmpresa().getFacebook());
        parametros.put("pl_adicional", sessionCodefac.getEmpresa().getAdicional());
        
        try {    
            RecursosServiceIf service= ServiceFactory.getFactory().getRecursosServiceIf();
            String nombreImagen=sessionCodefac.getEmpresa().getImagenLogoPath();
            //service.getResourceInputStream(RecursoCodefac.AYUDA, file);
            
            //inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.IMAGENES_GENERAL, "codesoft-logo.png"));
            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStreamByFile(DirectorioCodefac.IMAGENES, nombreImagen));
            //inputStream = (InputStream) UtilidadesRmi.deserializar(service.getResourceInputStream(RecursoCodefac.IMAGENES_GENERAL, "codefac-logotipo.png"));
            //parametros.put("pl_url_img1",UtilidadImagen.castInputStreamToImage(inputStream));
            parametros.put("pl_url_img1",UtilidadImagen.castInputStreamToImage(inputStream));
            
            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.IMAGENES_REDES_SOCIALES, "facebook.png"));
            parametros.put("pl_img_facebook",UtilidadImagen.castInputStreamToImage(inputStream));
            
            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.IMAGENES_REDES_SOCIALES, "whatsapp.png"));
            parametros.put("pl_img_whatsapp",UtilidadImagen.castInputStreamToImage(inputStream));
            
            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.IMAGENES_REDES_SOCIALES, "telefono.png"));
            parametros.put("pl_img_telefono",UtilidadImagen.castInputStreamToImage(inputStream));
            
            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.IMAGENES_GENERAL, "codefac-logotipo.png"));
            //parametros.put("pl_img_logo_pie",UtilidadImagen.castInputStreamToImage(inputStream));
            parametros.put("pl_img_logo_pie",null);
            
            /*
            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.IMAGENES_GENERAL, "codefac-logotipo.png"));
            parametros.put("pl_url_img1_url",UtilidadImagen.castInputStreamToImage(inputStream));
            
            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.IMAGENES_REDES_SOCIALES, "facebook.png"));
            parametros.put("pl_img_facebook_url",UtilidadImagen.castInputStreamToImage(inputStream));
            
            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.IMAGENES_REDES_SOCIALES, "whatsapp.png"));
            parametros.put("pl_img_whatsapp_url",UtilidadImagen.castInputStreamToImage(inputStream));
            
            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.IMAGENES_REDES_SOCIALES, "telefono.png"));
            parametros.put("pl_img_telefono_url",UtilidadImagen.castInputStreamToImage(inputStream));
            
            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.IMAGENES_GENERAL, "codesoft-logo.png"));
            parametros.put("pl_img_logo_pie_url",UtilidadImagen.castInputStreamToImage(inputStream));
            */
            
            inputStream = RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER, "encabezado.jrxml"));
            JasperReport reportCabecera = JasperCompileManager.compileReport(inputStream);
            
            parametros.put("pl_url_cabecera",reportCabecera);
            
            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER, "pie_pagina.jrxml"));
            JasperReport reportPiePagina = JasperCompileManager.compileReport(inputStream);
            
            parametros.put("pl_url_piepagina",reportPiePagina);
            //System.out.println(parametros.get("SUBREPORT_DIR"));            
        } catch (RemoteException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parametros;
    }

    public SessionCodefac getSessionCodefac() {
        return sessionCodefac;
    }

    public void setSessionCodefac(SessionCodefac sessionCodefac) {
        this.sessionCodefac = sessionCodefac;
    }
    
     @Override
    public void crearDialogoCodefac(ObserverUpdateInterface panel,VentanaEnum ventanEnum,boolean maximizado)
    {
        crearDialogoVentana(ventanEnum.getClase(), panel, maximizado,null);    
    }
    
    @Override
    public void crearDialogoCodefac(ObserverUpdateInterface panel,VentanaEnum ventanEnum,boolean maximizado,Object[] parametrosPostConstructor)
    {
        crearDialogoVentana(ventanEnum.getClase(), panel, maximizado,parametrosPostConstructor);    
    }

    @Override
    public void crearDialogoCodefac(ObserverUpdateInterface panel,String namePanel, boolean maximizado) {
        
        Class clase=buscarPanelDialog(namePanel);
        crearDialogoVentana(clase, panel, maximizado,null);
                
    }
    
    public void crearVentanaCodefac(VentanaEnum ventanEnum,boolean maximizado,Object[] parametrosPostConstructor)
    {
        ControladorCodefacInterface ventana=(ControladorCodefacInterface) ventanEnum.getInstance();
        agregarListenerMenu(ventana,maximizado);
        
        //Validacion para verificar si implementa la interfaz del postcostructod
        if (ventana instanceof InterfazPostConstructPanel) {
            ((InterfazPostConstructPanel) ventana).postConstructorExterno(parametrosPostConstructor);
        }
    }   
    
    private void crearDialogoVentana(Class clase,ObserverUpdateInterface panel,boolean maximizado,Object[] parametrosPostConstructor)
    {
        if(clase!=null)
        {
            try {
                Constructor constructor=clase.getConstructor();
                Object ventanaGeneral=constructor.newInstance();
                ControladorCodefacInterface ventana=(ControladorCodefacInterface) ventanaGeneral;
                //Verificar si el objeto implementa el metodo para comportarse como dialogo
                if(ventana instanceof  DialogInterfacePanel)
                {
                    ventana.modoDialogo=true;
                    ventana.formOwner=panel;
                    agregarListenerMenu(ventana,maximizado);
                    habilitarBotones(false);
                    getBtnGuardar().setEnabled(true);
                    
                    //Validacion para verificar si implementa la interfaz del postcostructod
                    if (ventana instanceof InterfazPostConstructPanel) {
                        ((InterfazPostConstructPanel) ventana).postConstructorExterno(parametrosPostConstructor);
                    }
                }
                else
                {
                    System.err.println("La clase que desea abrir no implementa la interfaz DialogInterfacePanel");
                }
                
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Buscar los panes que se pueden usar como dialogos
     */
    private Class buscarPanelDialog(String nombre)
    {
        for (VentanaEnum menuControlador : ventanasMenuList) {
            Class<GeneralPanelInterface> clase=menuControlador.getClase();
            if(clase.getName().equals(nombre))
            {
                 return menuControlador.getClase();
            }
        }
        return null;
    }


    public Map<String,PanelSecundarioAbstract> getPanelesSecundarios() {
        return panelesSecundariosMap;
    }

    public void setPanelesSecundarios(Map<String,PanelSecundarioAbstract> panelesSecundariosMap) {
        this.panelesSecundariosMap = panelesSecundariosMap;
    }

    @Override
    public boolean validarPorGrupo(String nombre) {
        ControladorCodefacInterface panel=getPanelActivo();
        return validarFormulario(panel,nombre);
    }

    public HiloPublicidadCodefac getHiloPublicidadCodefac() {
        return hiloPublicidadCodefac;
    }

    public void setHiloPublicidadCodefac(HiloPublicidadCodefac hiloPublicidadCodefac) {
        this.hiloPublicidadCodefac = hiloPublicidadCodefac;
    }

    private void agregarListenerBotonesDefecto() {
        getjMenuItemSalir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Boolean respuesta=DialogoCodefac.dialogoPregunta("Alerta","Estas seguro que deseas salir?",DialogoCodefac.MENSAJE_ADVERTENCIA);
                String[] opciones={"Salir","Cambiar usuario","Cancelar"};
                int opcionSeleccionada=DialogoCodefac.dialogoPreguntaPersonalizada("Alerta","Porfavor seleccione una opción?",DialogoCodefac.MENSAJE_ADVERTENCIA,opciones);
                switch(opcionSeleccionada)
                {
                    case 0: //opcion de salir
                        hiloPublicidadCodefac.hiloPublicidad = false;
                        dispose();
                        break;
                        
                    case 1:
                        break;
                        
                    case 2:
                        break;
                }

            }
        });
        
        getjMenuItemContenido().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarAyudaTodo();
                mostrarPanelSecundario(true,PanelSecundarioAbstract.PANEL_AYUDA);
            }
        });
        
        getjMenuItemAcerca().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                AcercaModel.getInstance().setUsuario(sessionCodefac.getUsuarioLicencia());
                AcercaModel.getInstance().setLicencia(sessionCodefac.getTipoLicenciaEnum().getNombre());
                AcercaModel.getInstance().setVisible(true);
            }
        });
        
        /*getjMenuItemMonitor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MonitorComprobanteModel.getInstance().mostrar();
            }
        });*/
        
        getjMenuItemInicio().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               minimizarTodasVentanas();
            }
        });
        
        getjMenuItemActualizarLicencia().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuarioLicencia=sessionCodefac.getUsuarioLicencia();
                String tipoLicencia=WebServiceCodefac.getTipoLicencia(usuarioLicencia);
                //TODO: Analizar la opcion para comparar tambien el numero de usuario y modulos para generar una nueva licencia
                
                if(sessionCodefac.getTipoLicenciaEnum().getLetra().equals(tipoLicencia))
                {
                    DialogoCodefac.mensaje("Advertencia","No necesita actualizar su licencia \n Si desea contratar una nueva licencia visite nuestra página Web", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    return;
                }
                
                
                boolean respuesta=DialogoCodefac.dialogoPregunta("Confirmar","Para actualizar la licencia debe cerrar todas las ventas , desea continuar?",DialogoCodefac.MENSAJE_ADVERTENCIA);
                if(respuesta)
                {
                    try {
                        dispose();
                        ParametroCodefacServiceIf servicio=ServiceFactory.getFactory().getParametroCodefacServiceIf();
                        String pathBase = servicio.getParametroByNombre(ParametroCodefac.DIRECTORIO_RECURSOS).valor;
                        ValidacionLicenciaCodefac validacion = new ValidacionLicenciaCodefac();
                        validacion.setPath(pathBase);
                        ValidarLicenciaModel dialogo=new ValidarLicenciaModel(null,true,true);
                        dialogo.validacionLicenciaCodefac=validacion;
                        dialogo.setVisible(true);
                    } catch (RemoteException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
            }
                
        });
        
    }
    
    private void minimizarTodasVentanas()
    {
        JInternalFrame[] ventanas = getjDesktopPane1().getAllFrames();
        for (JInternalFrame ventana : ventanas) {
            try {
                ventana.setIcon(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        getjDesktopPane1().revalidate();
        getjDesktopPane1().repaint();
        
    }

    @Override
    public boolean validarPorGrupo(String nombre, String componente) {
        ControladorCodefacInterface panel=getPanelActivo();
        return validarFormulario(panel,nombre,componente);
    }

    private void cargarDatosAdicionales() {
        
        try {
            /**
             * Setear el parametro del celular si ya fue ingresado alguna vez
             */
            ParametroCodefacServiceIf servicio=ServiceFactory.getFactory().getParametroCodefacServiceIf();
            ParametroCodefac parametro=servicio.getParametroByNombre(ParametroCodefac.CELULAR_VIRTUAL_MALL);
            if(parametro!=null)
            {
                widgetVirtualMall.getTxtCelular().setText(parametro.getValor());
            }
        } catch (RemoteException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
                
    }
    
    public class ListenerIcono implements IconoInterfaz 
    {
        private Class ventanaClase;
        private Boolean maximizado;

        public ListenerIcono(Class ventanaClase, Boolean maximizado) {
            this.ventanaClase = ventanaClase;
            this.maximizado = maximizado;
        }

        
        @Override
        public void doubleClick() {
            try {
                System.out.println("Ejemplo:"+ventanaClase.getName());
                Constructor contructor = ventanaClase.getConstructor();
                ControladorCodefacInterface ventana = (ControladorCodefacInterface) contructor.newInstance();
                agregarListenerMenu(ventana,maximizado);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void grabarNuevaPosicion(Point nuevaPosicion) {
            try {
                Map<String, Object> mapBuscar;
                AccesoDirectoServiceIf servicio = ServiceFactory.getFactory().getAccesoDirectoServiceIf();
                
                mapBuscar = new HashMap<>();
                mapBuscar.put("nombre", ventanaClase.getName());
                AccesoDirecto acceso=servicio.obtenerPorMap(mapBuscar).get(0);
                acceso.setX((int)nuevaPosicion.getX());
                acceso.setY((int)nuevaPosicion.getY());
                servicio.editar(acceso);
            } catch (RemoteException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    

   
}