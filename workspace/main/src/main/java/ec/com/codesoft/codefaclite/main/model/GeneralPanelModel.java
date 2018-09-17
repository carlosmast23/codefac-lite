/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;



import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import com.jtattoo.plaf.aero.AeroLookAndFeel;
import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;
import com.jtattoo.plaf.fast.FastLookAndFeel;
import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.jtattoo.plaf.mint.MintLookAndFeel;
import com.jtattoo.plaf.smart.SmartLookAndFeel;
import com.jtattoo.plaf.texture.TextureLookAndFeel;
import com.seaglasslookandfeel.SeaGlassLookAndFeel;
import ec.com.codesoft.codefaclite.configuraciones.model.CalculadoraModel;
import ec.com.codesoft.codefaclite.configuraciones.model.ComprobantesConfiguracionModel;
import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.PanelSecundarioAbstract;
import ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.PanelSecundarioListener;
import ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.ValidadorCodefacModel;
import ec.com.codesoft.codefaclite.corecodefaclite.ayuda.AyudaCodefacAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.util.CampoBuscarAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.util.CursorPorDefectoAnotacion;
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
import ec.com.codesoft.codefaclite.main.archivos.ArchivoConfiguracionesCodefac;
import ec.com.codesoft.codefaclite.main.init.Main;
import ec.com.codesoft.codefaclite.main.interfaces.BusquedaCodefacInterface;
import ec.com.codesoft.codefaclite.main.license.Licencia;
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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PermisoVentana;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CategoriaMenuEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EstiloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.FuncionesSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ModoSistemaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UtilidadesServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.imagen.UtilidadImagen;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadVarios;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.JDBCType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.SplitPaneUI;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.text.JTextComponent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;

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
    
    /**
     * Varible que almacena la ip del servidor para setear en la pantalla
     */
    public String ipServidor; //Todo: agrupar estos datos de mejor maneras
    
    /**
     * Map que me permite tener grabadas las pantallas abiertas
     */
    private Map<GeneralPanelInterface,JMenuItem> mapPantallaAbiertas;
   
    public GeneralPanelModel() 
    {
        getjPanelSeleccion().setVisible(false);//Asumo que cuando se abre por primera vez la pantalla esta oculta
        mapPantallaAbiertas=new HashMap<GeneralPanelInterface, JMenuItem>();
    }
    
    public void iniciarComponentesGenerales()
    {
        iniciarComponentesPantalla();
        iniciarComponentes();        
        agregarListenerBotonesDefecto();
        agregarListenerBotones();
        agregarListenerSplit();
        agregarListenerFrame();
        agregarListenerGraphics();
        agregarListenerItemMenu();
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
                eventoCerrarSistema();
            }

            @Override
            public void windowClosed(WindowEvent e){ }

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        //TODO: Falta implementar la funcionalidad para estos botones
        JPopupMenu popup = new JPopupMenu();
        popup.add(new JMenuItem("Nuevo"));
        popup.add(new JMenuItem("Actualizar"));
        //popup.add(new JMenuItem("tres"));
        
        getjDesktopPane1().setComponentPopupMenu(popup);
    }
    
    private void eventoCerrarSistema()
    {
        String[] opciones = {"Salir", "Cambiar usuario", "Cancelar"};
        int opcionSeleccionada = DialogoCodefac.dialogoPreguntaPersonalizada("Alerta", "Porfavor seleccione una opción?", DialogoCodefac.MENSAJE_ADVERTENCIA, opciones);
        switch (opcionSeleccionada) {
            case 0: //opcion de salir
                grabarDatosSalir();

                //Solo detener la publicidad cuando exista
                if (hiloPublicidadCodefac != null) {
                    hiloPublicidadCodefac.hiloPublicidad = false;
                }
                dispose();
                System.exit(0);
                break;

            case 1: //opcion cambiar de usuario
                cerrarTodasPantallas();
                setVisible(false);
                Usuario usuario = Main.cargarLoginUsuario();
                sessionCodefac.setUsuario(usuario);
                sessionCodefac.setPerfiles(Main.obtenerPerfilesUsuario(usuario));
                setVentanasMenuList(null);
                setearEtiquetasPantallaPrincipal();
                setVisible(true);
                break;

            case 2:
                break;
        }
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
            } catch (ServicioCodefacException ex) {
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
        final String PAGINA_DEFECTO=ParametrosSistemaCodefac.PAGINA_DEFECTO_AYUDA;
        
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
                url=PAGINA_DEFECTO;
            }
        }
        catch (UnsupportedOperationException exception) {
            System.out.println("metodo no implementado");
            url=PAGINA_DEFECTO;
            //return ; //Si no esta implementado la ayuda no abre nada
        }
        
        
        PanelSecundarioAbstract panelSecundario = panelesSecundariosMap.get(PanelSecundarioAbstract.PANEL_AYUDA);
        JPanel jpanel = (JPanel) panelSecundario;
        
        int ancho=getjPanelSeleccion().getWidth()-1;
        int alto=getjPanelSeleccion().getHeight()-1;

        if(browser!=null && url!=null && browser.getUrl()!=null)
        {
            //Verificar si la url cargada no es la misma cargo la nueva url
            if(!browser.getUrl().equals(url))
            {
                browser = new SwingBrowser();
                browser.loadURL(url);
                browser.setBounds(1, 1,ancho,alto);
            }            
            jpanel.removeAll();
            jpanel.add(browser);
        }
        else //Si no existe creado el recurso browser que se muestra en la ayuda la creo desde 0
        {
            browser = new SwingBrowser();
            
            browser.loadURL(url);
            browser.setBounds(1, 1, ancho, alto);
            jpanel.removeAll();
            jpanel.add(browser);            

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
                            //Este artificio se realiza porque cuando se reutilizaba un referencia de la pantalla generaba problemas con los dialogos7
                            ventana= (ControladorCodefacInterface) menuControlador.createNewInstance();
                            ventana.reconstruirPantalla(); //Metodo adicional que construye las pantallas laterales
                            agregarListenerMenu(ventana,menuControlador.isMaximizado());                    
                        }                        
                        else
                        {
                            try {
                                if (ventana.isIcon()) {
                                    ventana.setIcon(false);
                                } else {
                                    ventana.setSelected(true);
                                }
                                
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
                    boolean respuesta=true;
                    if(!frameInterface.salirSinGrabar())
                    {
                        respuesta=DialogoCodefac.dialogoPregunta("Advertencia","Existen datos ingresados , está seguro que desea limpiar la ventana?",DialogoCodefac.MENSAJE_ADVERTENCIA);
                    }
                    
                    //Solo si la respuesta es grabar ejecuta el metodo
                    if(respuesta)
                    {
                        frameInterface.nuevo();
                    }
                    else
                    {
                        return ; //cancelar la operacion si el usuario escoge no
                    }
                    
                }
                catch(UnsupportedOperationException exception)
                {
                    System.out.println("metodo no implementado"); 
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, exception);
                } catch (ExcepcionCodefacLite ex) {
                    //Cancela el ciclo de vida normal si manda una excecion
                    System.out.println(ex.getMessage()); 
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                } catch (RemoteException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    FuncionesSistemaCodefac.servidorConexionPerdida();
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
                        try {
                            DialogInterfacePanel interfaz = (DialogInterfacePanel) frame;
                            Object resultado = interfaz.getResult();
                            frame.dispose();
                            mostrarPanelSecundario(false);
                            //Setear el focus al formulario que abrio el dialogo
                            frameInterface.formOwnerFocus.moveToFront();
                            frameInterface.formOwnerFocus.setSelected(true);
                            
                            frameInterface.formOwner.updateInterface(resultado);
                            

                            
                        } catch (ExcepcionCodefacLite ex) {
                            System.out.println("Error al grabar en modo dialogo");
                            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (PropertyVetoException ex) {
                            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
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
                                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (RemoteException ex) {
                                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                                FuncionesSistemaCodefac.servidorConexionPerdida();
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
                                 //ex.printStackTrace();
                                LOG.log(Level.WARNING,ex.getMessage());
                                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (RemoteException ex) {
                                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                                FuncionesSistemaCodefac.servidorConexionPerdida();
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
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    System.err.println("Metodo no implementado boton editar");
                    //Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    //getjButton4().setEnabled(false);
                }
                               
            }
        });
        
        
        
        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame frame= getjDesktopPane1().getSelectedFrame();
                ControladorCodefacInterface frameInterface=(ControladorCodefacInterface) frame;

                listenerBuscar(frame,new BusquedaCodefacInterface() {
                    @Override
                    public void buscar() throws ExcepcionCodefacLite {
                        
                        try {
                            //Si existe implementado el metodo buscar por defecto se ejecuta este metodo
                            frameInterface.buscar();
                        } catch (UnsupportedOperationException ex) {
                            //Este metodo se ejecuta si no existe implementacion del metodo buscar
                            ejectutarDialogoBusqueda(frameInterface.obtenerDialogoBusqueda(),true,frameInterface,false);
                        }catch (ExcepcionCodefacLite ex) {
                            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                            throw ex;
                        } catch (RemoteException ex) {
                            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                            FuncionesSistemaCodefac.servidorConexionPerdida();
                        }
                    }
                });

                               
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
                } catch (RemoteException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    FuncionesSistemaCodefac.servidorConexionPerdida();
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
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    System.err.println(ex.getMessage());
                } catch (RemoteException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    FuncionesSistemaCodefac.servidorConexionPerdida();
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
                } catch (ExcepcionCodefacLite ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RemoteException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    FuncionesSistemaCodefac.servidorConexionPerdida();
                }
                               
            }
        });
        
        getBtnAyuda().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    if(!getjPanelSeleccion().isVisible())//Si no esta visible el panel lo muestra
                    {
                        cargarAyuda();
                        mostrarPanelSecundario(true, PanelSecundarioAbstract.PANEL_AYUDA);
                    }
                    else //Si esta vosible el panel lo oculta
                    {
                        cargarAyuda();
                        mostrarPanelSecundario(false, PanelSecundarioAbstract.PANEL_AYUDA);
                    }

                    
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
    
    /**
     * Listener que ejecuta el dialogo para buscar datos
     * @param frame
     * @param busquedaInterface
     * @param validacionDatosIngresados variable para saber si quiere validar si existen datos 
     */
    private void listenerBuscar(JInternalFrame frame,BusquedaCodefacInterface busquedaInterface)
    {        
        ControladorCodefacInterface frameInterface=(ControladorCodefacInterface) frame;
        
        //Variable para devolver al estado original si cancela o lanza una excepcion la busqueda        
        String estadoFomularioTemp=frameInterface.estadoFormulario;
            try
                {
                    /**
                     * Si ciclo de vida esta desactivado controlo manualmente el
                     * ciclo de vida
                     */
                    if (!frameInterface.cicloVida) {
                       busquedaInterface.buscar();
                        return;
                    }

                    
                    frameInterface.estadoFormulario= ControladorCodefacInterface.ESTADO_EDITAR;                   
                    busquedaInterface.buscar();
                    
                    
                    limpiarCamposValidacion(frameInterface);
                    mostrarPanelSecundario(false);
                    String tituloOriginal = getTituloOriginal(frame.getTitle());
                    frame.setTitle(tituloOriginal + " [Editar]");

                }
                catch (ExcepcionCodefacLite ex) 
                {
                    frameInterface.estadoFormulario=estadoFomularioTemp; //Regresa al estado original si se lanza alguna excepcion
                    LOG.log(Level.WARNING,ex.getMessage());
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    //Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    /**
     * 
     * @param dialogModel
     * @param frameInterface
     * @param cargarDirecto //si esta en tru y existe un solo dato carga directamente sin confirmacion del usuario
     * @throws ExcepcionCodefacLite 
     */
    private void ejectutarDialogoBusqueda(BuscarDialogoModel dialogModel,boolean  validacionDatosIngresados,GeneralPanelInterface frameInterface,boolean cargarDirecto) throws ExcepcionCodefacLite
    {   
        //Si no existe datos no muestro nada
        if (dialogModel.getTamanioConsulta() == 0) {
            DialogoCodefac.mensaje("Info", "No existe resultados en la busqueda", DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("No existen datos en la consulta para abrir el dialogo de busqueda");
        } 
        
       
        //Si desea cargar directo , lo hace cuando existe un solo resultado
        if(cargarDirecto)
        {
            if(dialogModel.getTamanioConsulta() == 1)
            {
                Object resultado=dialogModel.obtenerResultadoLista(0); //obtiene el primer resultado para cargar en la pantalla
                
                //Preguntar si desea cargar los datos perdiendo los datos ingresados
                if(preguntarCargarDatosBuscar(validacionDatosIngresados,frameInterface))
                {
                    frameInterface.cargarDatosPantalla(resultado);
                    
                }
                return;
            }
        }

        //Si no carga directo el dato se abre la pantalla normal de seleccion para el usuario
        dialogModel.setVisible(true);
        Object resultado=dialogModel.getResultado();                
        if(resultado!=null)
        {
            //Preguntar si desea cargar los datos perdiendo los datos ingresados
            if(preguntarCargarDatosBuscar(validacionDatosIngresados,frameInterface))
            {
                frameInterface.cargarDatosPantalla(resultado);
            }
            else
            {
                throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar, no desea cargar los datos");
            }
        }
        else
        {
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar, no selecciono ningun dato");
        }
    }
    
    private boolean preguntarCargarDatosBuscar(boolean  validacionDatosIngresados,GeneralPanelInterface frameInterface)
    {
        //Solo ejecutar si requiere validacion de campos ingresados
        if(validacionDatosIngresados)
        {
            if (!frameInterface.salirSinGrabar()) {
                boolean respuesta = DialogoCodefac.dialogoPregunta("Advertencia", "Existen datos ingresados , está seguro que desea cargar de todos modos?", DialogoCodefac.MENSAJE_ADVERTENCIA);
                return respuesta;
            }
        }
        return true;
    }
    
    /**
     * TODO: optimizar este codigo porque existe 2 repetidos con diferentes parametros
     * @param opcion 
     */
    private void mostrarPanelSecundario(boolean  opcion)
    {
        if(opcion)
        {
            //Valores para mostrar en la pantalla secundaria
            PROPORCION_HORIZONTAL = PROPORCION_HORIZONTAL_DEFAULT;
            PROPORCION_VERTICAL = PROPORCION_VERTICAL_DEFAULT;
            getjPanelSeleccion().setVisible(opcion); //Vuelve visible el componente secundario
        }
        else
        {
            PROPORCION_HORIZONTAL = PROPORCION_HORIZONTAL_INICIAL;
            PROPORCION_VERTICAL = 0.9999999999999999d;
            getjPanelSeleccion().setVisible(opcion); //Vuelve invisible el componente secundario

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
            getjPanelSeleccion().setVisible(opcion); //Vuelve visible el componente secundario
        }
        else
        {
            PROPORCION_HORIZONTAL = PROPORCION_HORIZONTAL_INICIAL; //proporcion que oculta en  la pantalla
            PROPORCION_VERTICAL = 0.9999999999999999d; //Proporcion que oculta en la pantall
            getjPanelSeleccion().setVisible(opcion); //Vuelve invisible el componente secundario

        }
        
        getjSplitPanel().setDividerLocation(PROPORCION_HORIZONTAL);
        getjSplitPanelVerticalSecundario().setDividerLocation(PROPORCION_VERTICAL);
        getjPanelSeleccion().setSelectedComponent(panelesSecundariosMap.get(panelSecundario)); //Seleccionar el tab del panel secundario seleccionado

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
            //Anular el metodo de cierre automatico para controlar manualmente
            panel.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
            /**
             * Agregar variables de session a la pantalla
             */
           
            panel.estadoFormulario= ControladorCodefacInterface.ESTADO_GRABAR;
            panel.panelPadre=generalPanelModel;
            panel.session=sessionCodefac;
            //panel.reconstruirPantalla(); //Metodo adicional que construye las pantallas laterales
            
            try
            {
                panel.iniciar();//Metodo que se ejecuta despues de construir el objeto
            }
            catch(java.lang.UnsupportedOperationException uoe)
            {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, uoe);
                System.err.println("Metodo no implementado");
            } catch (ExcepcionCodefacLite ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Cancelado metodo iniciar");
                return;
            } catch (RemoteException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                FuncionesSistemaCodefac.servidorConexionPerdida();
            }
            
            panel.addInternalFrameListener(listenerFrame);
            String tituloOriginal=getTituloOriginal(panel.getTitle());
            panel.setTitle(tituloOriginal+" [Nuevo]");
            getjDesktopPane1().add(panel);
            
            panel.setMaximum(maximisado);
            panel.show();
            getBtnNuevo().requestFocus();
            agregarValidadores(panel); //Agregar validadores para los campos
            agregarAyudas(panel);
            
            try
            {                
                panel.limpiar();
                panel.nuevo();
            }catch(java.lang.UnsupportedOperationException uoe)
            {
                
            } catch (ExcepcionCodefacLite ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                FuncionesSistemaCodefac.servidorConexionPerdida();
            }
            
            /**
             * Mostrar la pantalla centrada cuando no se muestra maximisado
             */
            if(!maximisado)
            {
                Dimension desktopSize =getjDesktopPane1().getSize(); //tamanio del escritorio
                Dimension jInternalFrameSize = panel.getPreferredSize(); //tamanio de la ventana
                double ancho=(double)(desktopSize.width - jInternalFrameSize.width) /(double) 2;
                double alto=(double)(desktopSize.height - jInternalFrameSize.height) /(double) 2;
                //double alto=(double)(desktopSize.height) /(double) 2;
                panel.setLocation((int)ancho,(int)alto);
            }
            
            
            panel.consola=new ConsolaGeneral();
            mostrarConsola(panel.consola,true);
            
            //Agregar ventana al combo de ventanas abiertas
            agregarVentanaAbierta(panel);
            

                        
        } catch (PropertyVetoException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo para agregar en un metodo todas las ventanas abiertas
     * @param panel 
     */
    private void agregarVentanaAbierta(GeneralPanelInterface panel)
    {
        JMenuItem jmenuItem=mapPantallaAbiertas.get(panel);
        if(jmenuItem==null)
        {
            jmenuItem=new JMenuItem(panel.toString());
            //jmenuItem.setFont(new Font("Arial", Font.BOLD, 13));
            seleccionarVentanaActivaMenu();
            jmenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (panel != null) {
                        try {
                            if (panel.isIcon()) {
                                panel.setIcon(false);
                            }

                            panel.setSelected(true);
                            seleccionarVentanaActivaMenu();
                        } catch (PropertyVetoException ex) {
                            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            
            mapPantallaAbiertas.put(panel, jmenuItem);
        }
        
        getjMenuVentanasActivas().add(jmenuItem);
        
    }
    
    /**
     * 
     * Metodo que permite poner check en la ventana que este seleccionada
     */
    private void seleccionarVentanaActivaMenu()
    {
        GeneralPanelInterface panel= getPanelActivo();
        
        for (Map.Entry<GeneralPanelInterface, JMenuItem> entry : mapPantallaAbiertas.entrySet()) {
            GeneralPanelInterface key = entry.getKey();
            JMenuItem value = entry.getValue();            
            
            if(panel.equals(key))
            {
                value.setFont(new Font("Arial", Font.BOLD, 13));      
            }
            else
            {
                value.setFont(new Font("Arial", Font.PLAIN, 13));
            }
            
        }
    }
    
    /**
     * Metodo para quitar del menu alguna ventana abierta
     */
    private void quitarVentanaAbierta(GeneralPanelInterface panel)
    {
        JMenuItem jmenuItem=mapPantallaAbiertas.get(panel);
        if(jmenuItem!=null)
        {
            //Eliminar la referencia del map
            mapPantallaAbiertas.remove(panel);
            //Eliminar la referencia del menu
            getjMenuVentanasActivas().remove(jmenuItem);
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
    
    //Metodo que permite agregar un metodo para la pantalla de busqueda directo desde el formulario
    //TODO: Ver si se puede optimizar de mejor manera con los otros validadores porque es muchas repeticiones
    private void agregarListenerBusqueda(ControladorCodefacInterface panel)
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
    //TODO: optimizar para que exista un solo meotodo para correr las validaciones para que no exista tantos recorridos de gana
    private void limpiarCamposValidacion(ControladorCodefacInterface panel)
    {
       ConsolaGeneral consola=new ConsolaGeneral();
       
       Class classVentana=panel.getClass();
        Method[] metodos=classVentana.getMethods();
        for (Method metodo : metodos) {
            
            //Si no es metodo de tipo get ya no valida
            if(metodo.getName().indexOf("get")!=0)
            {
                continue;
            }
            
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
            
            CursorPorDefectoAnotacion anotacionCursor=metodo.getAnnotation(CursorPorDefectoAnotacion.class);
            if(anotacionCursor!=null)
            {
                try {
                    JTextComponent componente=(JTextComponent) metodo.invoke(panel);
                    componente.requestFocus(); //Seteo el focus del campo por defecto
                    
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
    
    
    

    
    private Vector<String> validarComponente(ValidacionCodefacAnotacion validacion,JTextComponent componente,GeneralPanelInterface panel)
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
       PanelSecundarioAbstract panel=panelesSecundariosMap.get(PanelSecundarioAbstract.PANEL_VALIDACION);
       //Esta funcion se encarga de enviar el modelo a la tabla para mostrar los datos
       panel.actualizar(consola.getModeloTabla());
       
       JTable tablaValidacion=(JTable) panel.getPropertyByNombre(ValidadorCodefacModel.PROPIEDAD_TABLA);       
       tablaValidacion.addMouseListener(new MouseListener() {           
           @Override
           public void mouseClicked(MouseEvent e) {
               int filaSeleccionado=tablaValidacion.getSelectedRow();
               if(filaSeleccionado>=0)
               {
                   ControladorCodefacInterface frameInterface=(ControladorCodefacInterface) getPanelActivo();
                   frameInterface.consola.seleccionarFila(filaSeleccionado);
               }
           }

           @Override
           public void mousePressed(MouseEvent e) {}
           @Override
           public void mouseReleased(MouseEvent e) {}
           @Override
           public void mouseEntered(MouseEvent e) {}
           @Override
           public void mouseExited(MouseEvent e) {}
       });
               
       if(consola.getModeloTabla().getRowCount()>0)
       {
           mostrarPanelSecundario(true,PanelSecundarioAbstract.PANEL_VALIDACION);
       }
       else
       {
           mostrarPanelSecundario(false);
       }
      
    }
    
    private void agregarValidadores(GeneralPanelInterface panel)
    {
       Class classVentana=panel.getClass();
        Method[] metodos=classVentana.getMethods();
        for (Method metodo : metodos) {
            //solo hacer validaciones para metodos que empicen con la palabra get
            if(metodo.getName().indexOf("get")!=0)
            {
                continue;
            }
            
            ValidacionCodefacAnotacion validacion=metodo.getAnnotation(ValidacionCodefacAnotacion.class);
            if(validacion!=null)
            {
                if(validacion.grupo().equals(ValidacionCodefacAnotacion.GRUPO_FORMULARIO))
                {                    
                    try {
                        JTextComponent componente=(JTextComponent) metodo.invoke(panel);
                        
                        //Poner un borde doble solo cuando el campo es requerido para el usuario
                        if(validacion.requerido())
                        {                            
                            componente.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, new Color(122, 138, 153)));                                                    
                        }
                        
                        componente.addFocusListener(new FocusListener() {
                            @Override
                            public void focusGained(FocusEvent e) {

                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                //Verifica que no existen datos ingresados porque no debe validar nada
                                //Optimizar este codigo porque este metodo es para otra funcionalidad mas complicada
                                String letra=componente.getText();
                                if(panel.salirSinGrabar() && componente.getText().equals(""))
                                {
                                    return;
                                }

                                //Este codigo se pone porque despues de cambiar de pantalla se ejecuta el evento de focus de la anterior
                                //y eso me genera problemas cuando quiero manejar los eventos de las jinternalFrame
                                //TODO: Revisar este codigo
                                if(!panel.equals(getPanelActivo()))
                                {
                                    //System.out.println("no validar porque cambio de pantalla");
                                    return;
                                }
                                //TODO: Revisar esta solucion porque es muy rebuscada
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
            
            CursorPorDefectoAnotacion anotacionCursorDefecto=metodo.getAnnotation(CursorPorDefectoAnotacion.class);
            if(anotacionCursorDefecto!=null)
            {
                try {
                    JTextComponent componente=(JTextComponent) metodo.invoke(panel);
                    componente.requestFocus(); //Setear con el puntero ese campo
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            
            //Buscar Anotaciones para la etiqueta de busqueda por campo
            CampoBuscarAnotacion buscarAnotacion=metodo.getAnnotation(CampoBuscarAnotacion.class);
            if(buscarAnotacion!=null)
            {
                try {
                    JTextComponent componente=(JTextComponent) metodo.invoke(panel);
                    ImageIcon icon = new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("find2-ico.png"));
                    componente.setBorder(BorderFactory.createMatteBorder(0, 16, 0, 0, icon));
                    componente.addKeyListener(new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {}

                        @Override
                        public void keyPressed(KeyEvent e) {
                            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                                JInternalFrame frame = getjDesktopPane1().getSelectedFrame();
                                
                                listenerBuscar(frame,new BusquedaCodefacInterface() {
                                    @Override
                                    public void buscar() throws ExcepcionCodefacLite {

                                        try
                                        {
                                            BuscarDialogoModel dialogBuscar=panel.obtenerDialogoBusqueda();
                                            //Ejecutar la pantalla de dialogo solo si hay algo de texto
                                            if (!componente.getText().equals("")) {
                                                try {
                                                    dialogBuscar.getTxtBuscar().setText(componente.getText());
                                                    dialogBuscar.ejecutarConsulta();
                                                    //Verifico que exista coincidencias con el dato ingreso o cancelo la busquda
                                                     ejectutarDialogoBusqueda(dialogBuscar,false,panel,true);                                                   
                                                    
                                                } catch (ExcepcionCodefacLite ex) {
                                                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                                                    throw ex;
                                                }
                                            } else {
                                                throw new ExcepcionCodefacLite("No existen datos en los campos para abrir el dialogo de busqueda");
                                            }
                                        }
                                        catch(UnsupportedOperationException ex) {
                                            //si no existe implentada la funcion no se muestra nada
                                            LOG.log(Level.INFO,"No se puede mostrar la busqueda desde un campo porque no esta implementado el dialogo de busqueda");
                                        }

                                    }
                                });
                
                            }
                        }

                        @Override
                        public void keyReleased(KeyEvent e) {}
                    });
                }
                //Verificar tambien para ver si implementa el etiqueta de busqueda por  campo
                catch (IllegalAccessException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
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
                            Boolean respuesta=true;
                            GeneralPanelInterface panelCerrando=(GeneralPanelInterface) e.getInternalFrame();                            
                            if(!panelCerrando.salirSinGrabar())
                            {
                                respuesta=DialogoCodefac.dialogoPregunta("Advertencia","Existen datos ingresados , está seguro que desea cerrar la ventana?",DialogoCodefac.MENSAJE_ADVERTENCIA);
                            }
                            
                            //Solo cerrar si la respuesta es si
                            if(respuesta)
                            {                                
                                ControladorCodefacInterface panel = (ControladorCodefacInterface) getjDesktopPane1().getSelectedFrame();
                                panel.formularioCerrando = true;
                                cargarAyuda();
                                mostrarPanelSecundario(false);
                                e.getInternalFrame().dispose();
                                quitarVentanaAbierta(panelCerrando); //
                            }                                                        
                        }

                        @Override
                        public void internalFrameClosed(InternalFrameEvent e) {
                            
                            //if (verificarTodasPantallasMinimizadas(e.getInternalFrame())) {
                                habilitarBotones(false);
                            //}
                            //Seleccionar la siguiente ventana por defecto
                            JInternalFrame[] ventanas=getjDesktopPane1().getAllFrames();
                            for (JInternalFrame ventana : ventanas) {
                                    try {
                                        if(!ventana.isIcon())
                                            ventana.setSelected(true);
                                    } catch (PropertyVetoException ex) {
                                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                            }
                            
                            mostrarPanelSecundario(false);
                        }

                        @Override
                        public void internalFrameIconified(InternalFrameEvent e) {
                            //Veifico si ya no existen pantallas para establecer el foco entonces desahabilito los botones
                            ControladorCodefacInterface controlador=(ControladorCodefacInterface) e.getInternalFrame();
                            controlador.sinAcciones=true; //Este artificio utilizo para que cuando minimice no se ejecuten los validadores
                            
                            if (verificarTodasPantallasMinimizadas(e.getInternalFrame())) {
                                habilitarBotones(false);
                                
                            }
                            mostrarPanelSecundario(false);

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
                                }
                                else
                                    System.out.println("consola null");
                            }else
                                System.out.println("panel null");

                            //s
                           
                        }
                        
                        //Evento cuando se desactiva las pantallas
                        @Override
                        public void internalFrameDeactivated(InternalFrameEvent e) {}
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
        habilitarBotones(false);//Descativo todas las ventanas para luego activar segun los permisos
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
                    //No validar para el boton de ayuda porque siempre tiene que estar activo
                    if(key!=ControladorCodefacInterface.BOTON_AYUDA)
                    {
                        Boolean permisoBotonUsuario = permisoBotonesRoles(frameInterface, key);
                        if (!permisoBotonUsuario) {
                            boton.setEnabled(false); //Descivar si no tiene permisos
                        }
                    }
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
                    if ((permisoVentana.getPermisoGrabar().equals("s") || permisoVentana.getPermisoEditar().equals("s")) && ControladorCodefacInterface.BOTON_NUEVO == boton) 
                        return true;                    
                    
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
            Image fondoImg=new javax.swing.ImageIcon(getClass().getResource("/img/general/fondoGeneral.png")).getImage();
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
            widgetVirtualMall.setVisible(false); //TODO: Por el momento dejo desactivado este componente porque no estamos usando
            
            /***
             * Agregar el widget de Ventas diarias
             */
            mapBuscar = new HashMap<>();
            mapBuscar.put("nombre", "WidgetVentasDiarias");
            //int xVd=servicio.obtenerPorMap(mapBuscar).get(0).x;
            //int yVd=servicio.obtenerPorMap(mapBuscar).get(0).y;
            
            int xVd=100;
            int yVd=100;
            
            widgetVentasDiarias = new VentasDiariasModel(getjDesktopPane1());
            widgetVentasDiarias.panelPadre=this;
            widgetVentasDiarias.setPreferredSize(new Dimension(xVd,yVd));
            widgetVentasDiarias.setBounds(xVd,xVd,250,330);
            
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
        this.getJMenuBar().add(getjMenuVentanasActivas());
        //actualizarMenuCodefac();
        agregarListenerMenu();
    }
    
    private boolean isModuloPermitido(ModuloCodefacEnum moduloVerificar)
    {
        List<ModuloCodefacEnum> modulosPermitidos =sessionCodefac.getModulos();
        for (ModuloCodefacEnum modulosPermitido : modulosPermitidos) {
            if(modulosPermitido.equals(moduloVerificar))
            {
                return true;
            }
        }
        //Si no encuentra ninguna coincidencia manda false
        return false;
    
    }
    
    private List<JMenu> construirMenuDinamico()
    {
        List<JMenu> menus=new ArrayList<JMenu>();
        
        for (ModuloCodefacEnum moduloSistema : ModuloCodefacEnum.values()) {
            System.out.println("MOdulo prueba:"+moduloSistema.getNombre());
        }
        
                
        for (ModuloCodefacEnum moduloSistema : ModuloCodefacEnum.values()) {
            
                if(moduloSistema.equals(ModuloCodefacEnum.GESTIONA_ACADEMICA))
                {
                    System.out.println("MODO CONSTRUYENDO:"+moduloSistema.getNombre());
                }
                
                JMenu menuModulo = new JMenu(moduloSistema.getNombre());
                menuModulo.setIcon(moduloSistema.getIcono());
                menuModulo.setFont(new Font("Arial",2,15));
                boolean existenCategorias=false;
                
                for (CategoriaMenuEnum categoriaEnum : CategoriaMenuEnum.values()) {
                    JMenu menuCategoria=new JMenu(categoriaEnum.getNombre());
                    menuCategoria.setIcon(categoriaEnum.getIcono());
                    menuCategoria.setFont(new Font("Arial", 0, 13));
                    
                    boolean existenMenuItem=false;
                    for (VentanaEnum menuControlador : ventanasMenuList)  //Todo: Analizar para que la variables ventanasMenuList pueda setera cada vez que busco las pantalla que pertence al menu y se vayagn quitando de la lista para acelerar el proceso
                    {
                        //Si la ventana no pertecene a la categoria no hago mas validaciones
                        if(!menuControlador.getCategoriaMenu().equals(categoriaEnum))
                        {
                            continue; //salta a la siguiente vuelta
                        }
                        
                        //Verificacion cuando es un modulo habilitado
                        boolean agregarAlMenu=false;
                        
                        //Si esta en modo de desarrollo carga todos las opciones de los menus
                        if(ParametrosSistemaCodefac.MODO.equals(ModoSistemaEnum.DESARROLLO))
                        {
                            if (menuControlador.getModulo().equals(moduloSistema)) 
                            {
                                agregarAlMenu=true;
                            }
                        }
                        else //Si esta en modo de produccion hago las validaciones normales
                        {                        
                            //Validacion de las ventanas cuando el usuario es gratis
                            if(sessionCodefac.getTipoLicenciaEnum().equals(TipoLicenciaEnum.GRATIS))
                            {
                                //Si el tipo de licencia de la pantala es gratis le activo solo las pantallas disponibles para esta modalidad
                                if (menuControlador.getModulo().equals(moduloSistema)) 
                                {                                                                
                                    //El acceso es el mismo para cualquier usuario gratis y para el administrador
                                    if(menuControlador.getTipoLicenciaEnum().equals(TipoLicenciaEnum.GRATIS)) 
                                    {
                                        agregarAlMenu = true;
                                    }

                                }
                            }
                            else //Validacion para usuarios premiun
                            {                        
                                if(isModuloPermitido(moduloSistema))
                                {
                                    if(menuControlador.getModulo().equals(moduloSistema))
                                    {                                    
                                        if(verificarMenuUsuario(menuControlador) || sessionCodefac.getUsuario().isRoot)
                                        {
                                            agregarAlMenu=true;
                                        }

                                      }
                                }
                                else //Verificacion cuando no es un modulo habilitado
                                {
                                    //Solo agregar otras ventanas de otros modulos si el menu pertenece al modulo actual
                                    //Nota: sin esta linea pueden aparecer varios enlaces a esta ventana desde otros menus de modulos
                                    if (menuControlador.getModulo().equals(moduloSistema)) {
                                        //Verifica si es super usuario carga todos los modulos


                                        //Verifica si la pantalla adicional deberia agregarse porque esta depende de otra que si se cargo el modulo
                                        if (menuControlador.verificarPermisoModuloAdicional(sessionCodefac.getModulos())) 
                                        {
                                            //Verifica si el usuario tienes permisos para esa pantalla o son son super usuarios
                                            if(verificarMenuUsuario(menuControlador) || sessionCodefac.getUsuario().isRoot)
                                            {
                                                agregarAlMenu = true;
                                            }
                                        } 


                                    }


                                }

                            }
                        }
                        
                        //Esta pantalla filtra que solo se agregue si pertenece al modulo y a la submenu corecto
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
                            
                            //Agregar atajo de teclado si existe
                            if(menuControlador.getTeclaAtajo()!=null)
                            {
                                menuVentana.setAccelerator(KeyStroke.getKeyStroke(menuControlador.getTeclaAtajo(),InputEvent.ALT_MASK));
                            }
                            
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
    public Map<String, Object> mapReportePlantilla(OrientacionReporteEnum orientacionEnum,FormatoHojaEnum formatoReporte) {
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
            
           if(sessionCodefac.getTipoLicenciaEnum().equals(TipoLicenciaEnum.GRATIS))
            {
                inputStream=RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream("sin_imagen.jpg");
            }
            else
            {
                RemoteInputStream remoteInputStream = service.getResourceInputStreamByFile(DirectorioCodefac.IMAGENES, nombreImagen);
                //verifica que existe una imagen
                if (remoteInputStream != null) {
                    inputStream = RemoteInputStreamClient.wrap(remoteInputStream);
                } 
                else //Si no existe 
                {
                    inputStream = RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream("sin_imagen.jpg");
                }            
            }

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
            
            String nombreReporteEncabezado="";
            String nombreReportePiePagina="";
            
            switch(formatoReporte)
            {
                case A5:
                    switch (orientacionEnum) {
                        case HORIZONTAL:
                            break;

                        case VERTICAL:
                            nombreReporteEncabezado = "encabezadoA5.jrxml";
                            nombreReportePiePagina = "pie_paginaA5.jrxml";
                            break;
                    }
                    break;
                    
                case A4:
                    switch (orientacionEnum) {
                        case HORIZONTAL:
                            nombreReporteEncabezado = "encabezado_horizontal.jrxml";
                            nombreReportePiePagina = "pie_pagina_horizontal.jrxml";
                            break;

                        case VERTICAL:
                            nombreReporteEncabezado = "encabezado.jrxml";
                            nombreReportePiePagina = "pie_pagina.jrxml";
                            break;
                    }
                    break;
                
            }
            
            inputStream = RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER,nombreReporteEncabezado));
            JasperReport reportCabecera = JasperCompileManager.compileReport(inputStream);
            
            parametros.put("pl_url_cabecera",reportCabecera);
            
            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER,nombreReportePiePagina));
            JasperReport reportPiePagina = JasperCompileManager.compileReport(inputStream);
            
            parametros.put("pl_url_piepagina",reportPiePagina);
            //System.out.println(parametros.get("SUBREPORT_DIR"));            
        } catch (RemoteException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
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
    public void crearDialogoCodefac(ObserverUpdateInterface panel,VentanaEnum ventanEnum,boolean maximizado,GeneralPanelInterface panelPadre)
    {
        crearDialogoVentana(ventanEnum.getClase(), panel, maximizado,null,panelPadre);    
    }
    
    @Override
    public void crearDialogoCodefac(ObserverUpdateInterface panel,VentanaEnum ventanEnum,boolean maximizado,Object[] parametrosPostConstructor,GeneralPanelInterface panelPadre)
    {
        crearDialogoVentana(ventanEnum.getClase(), panel, maximizado,parametrosPostConstructor,panelPadre);    
    }

    @Override
    public void crearDialogoCodefac(ObserverUpdateInterface panel,String namePanel, boolean maximizado,GeneralPanelInterface panelPadre) {
        
        Class clase=buscarPanelDialog(namePanel);
        crearDialogoVentana(clase, panel, maximizado,null,panelPadre);
                
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
    
    private void crearDialogoVentana(Class clase,ObserverUpdateInterface panel,boolean maximizado,Object[] parametrosPostConstructor,GeneralPanelInterface panelPadre)
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
                    ventana.formOwnerFocus=panelPadre;
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
                eventoCerrarSistema();

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
                //String tipoLicencia=WebServiceCodefac.getTipoLicencia(usuarioLicencia);
                Licencia licenciaInternet=new Licencia();
                licenciaInternet.cargarLicenciaOnline(usuarioLicencia);
                
                ValidacionLicenciaCodefac validacionLicencia = new ValidacionLicenciaCodefac(sessionCodefac.getParametrosCodefac().get(ParametroCodefac.DIRECTORIO_RECURSOS).getValor());
                
                //TODO: Analizar la opcion para comparar tambien el numero de usuario y modulos para generar una nueva licencia
                                
                if(validacionLicencia.getLicencia().compararOtraLicencia(licenciaInternet))
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
                        ec.com.codesoft.codefaclite.main.init.Main.iniciarComponentes();
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
    
    /**
     * Verifica si todas las pantallas estan minimizadas
     * @return 
     */
    private Boolean verificarTodasPantallasMinimizadas(JInternalFrame internalFrameActual)
    {
       
        JInternalFrame[] ventanas = getjDesktopPane1().getAllFrames();
        for (JInternalFrame ventana : ventanas) {

            if(!ventana.equals(internalFrameActual))
            {
                
                if(!ventana.isIcon())
                {
                    return  false;
                }
            }
        }
        return true;
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
        
        //Cargar los temas en el menu
        for(EstiloCodefacEnum estiloEnum : EstiloCodefacEnum.values())
        {
            JMenuItem menuItemTema=new JMenuItem(estiloEnum.getNombre());
            menuItemTema.setFont(new Font("Arial", Font.PLAIN, 13));   
            menuItemTema.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    seleccionarTema(estiloEnum.getNombre());
                    //DialogoCodefac.mensaje("Correcto","Tema modificado correctamente", DialogoCodefac.MENSAJE_CORRECTO);
                    //Guardar en el arcivo de configuraciones
                    try {
                        ArchivoConfiguracionesCodefac.getInstance().agregarCampo(ArchivoConfiguracionesCodefac.CAMPO_TEMA, estiloEnum.getNombre());
                        ArchivoConfiguracionesCodefac.getInstance().guardar();
                        
                    } catch (IOException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            getJmenuTemas().add(menuItemTema);            
        }
                
    }
    
    /**
     * Metodo que permite establecer el nuevo tema para cambiar la apariencia
     */
    private void seleccionarTema(String nombreTema)
    {
        EstiloCodefacEnum estiloCodefacEnum=EstiloCodefacEnum.findByNombre(nombreTema);
        try {
            Properties props = new Properties();
            props.put("logoString", "Codefac");
            
            TextureLookAndFeel.setCurrentTheme(new Properties(props));
            AeroLookAndFeel.setCurrentTheme(new Properties(props));
            //SeaGlassLookAndFeel.setCurrentTheme(props);
            McWinLookAndFeel.setCurrentTheme(new Properties(props));
            MintLookAndFeel.setCurrentTheme(new Properties(props));
            GraphiteLookAndFeel.setCurrentTheme(new Properties(props));
            FastLookAndFeel.setCurrentTheme(new Properties(props));
            AluminiumLookAndFeel.setCurrentTheme(new Properties(props));
            AeroLookAndFeel.setCurrentTheme(new Properties(props));
            AcrylLookAndFeel.setCurrentTheme(new Properties(props));
            SmartLookAndFeel.setCurrentTheme(new Properties(props));
            
            UIManager.setLookAndFeel(estiloCodefacEnum.getClassName());

            SwingUtilities.updateComponentTreeUI(this);
            repaint();
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

    private void iniciarComponentesPantalla() {
        String anioActualStr=UtilidadesFecha.obtenerAnioStr(UtilidadesFecha.getFechaHoy());
        getLblPiePagina().setText("Todos los derechos reservador por @Codesoft "+anioActualStr);
    }

    private void agregarListenerItemMenu() {
        
        getItemActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getBtnActualizar().doClick();
            }
        });
        
        getItemAyuda().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getBtnAyuda().doClick();
            }
        });
        
        getItemBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getBtnBuscar().doClick();
            }
        });
        
        getItemEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getBtnEliminar().doClick();
            }
        });
        
        getItemGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getBtnGuardar().doClick();
            }
        });
        
        getItemImprimir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getBtnImprimir().doClick();
            }
        });

        getItemNuevo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getBtnNuevo().doClick();
            }
        });
    }
    
    public void setearEtiquetasPantallaPrincipal()
    {
        try {
            getLblNombreEmpresa().setText(" Empresa: " + ((sessionCodefac.getEmpresa() != null) ? sessionCodefac.getEmpresa().getNombreLegal() : "Sin asignar") + " | Usuario: " + sessionCodefac.getUsuario().getNick());
            
            //Obtener el tipo de licencia para imprimir en la pantalla inicio
            UtilidadesServiceIf utilidadesService = ServiceFactory.getFactory().getUtilidadesServiceIf();
            TipoLicenciaEnum tipoLicenciaEnum = utilidadesService.getTipoLicencia();
            getLblTextoSecundario().setText("Servidor IP: " + ipServidor + " | Licencia: " + tipoLicenciaEnum.getNombre() + " | Versión: " + ParametrosSistemaCodefac.VERSION);
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