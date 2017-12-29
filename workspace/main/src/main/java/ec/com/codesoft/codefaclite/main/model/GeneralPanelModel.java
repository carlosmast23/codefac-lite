/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;



import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.comprobantes.ComprobanteElectronicoAbstract;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteListener;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
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
import ec.com.codesoft.codefaclite.crm.model.ClienteModel;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.main.panel.GeneralPanelForm;
import ec.com.codesoft.codefaclite.main.session.SessionCodefac;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidor.service.ParametroCodefacService;
import ec.com.codesoft.ejemplo.utilidades.imagen.UtilidadImagen;
import ec.com.codesoft.ejemplo.utilidades.varios.UtilidadVarios;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Panel;
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
import java.text.SimpleDateFormat;
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
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
/**
 *
 * @author Carlos
 */
public class GeneralPanelModel extends GeneralPanelForm implements InterfazComunicacionPanel{
    private GeneralPanelModel generalPanelModel=this;
    private ControladorVista controladorVista;
    private ControladorCodefacInterface panelActual;
    private SwingBrowser browser ;
    private SwingBrowser browserPublicidad ;
    private List<MenuControlador> ventanasMenuList;
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


    public GeneralPanelModel() 
    {
        iniciarComponentes();
        agregarListenerBotonesDefecto();
        agregarListenerBotones();
        agregarListenerSplit();
        agregarListenerFrame();
        agregarListenerGraphics();
               
        habilitarBotones(false);
        
        

        /*
        getjDesktopPane1().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                //System.out.println("resi");
                //revalidate();
                //repaint();
                //getjSplitPanel().getDividerLocation();
               // getjSplitPanel().setDividerLocation(DIVISION_GENERAL);
               // getjSplitPanelVerticalSecundario().setDividerLocation(DIVISION_SECUNDARIA);                
            }
            
        });*/
        
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
                Boolean respuesta=DialogoCodefac.dialogoPregunta("Alerta","Estas seguro que deseas salir?",DialogoCodefac.MENSAJE_ADVERTENCIA);
                if(respuesta)
                {
                    hiloPublicidadCodefac.hiloPublicidad=false;
                    dispose();
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
    
    private void agregarListenerGraphics()
    {
                
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Image fondoImg=new javax.swing.ImageIcon(getClass().getResource("/img.general/fondoGeneral.png")).getImage();
                getjDesktopPane1().setBorder(new Fondo(fondoImg));
                
                getjSplitPanel().setDividerLocation(PROPORCION_HORIZONTAL);
                getjSplitPanelVerticalSecundario().setDividerLocation(PROPORCION_VERTICAL);
            }
           
            
        
        });
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
                  System.out.println("division:"+division+"ancho:"+ancho+"p1:"+PROPORCION_HORIZONTAL+">"+PROPORCION_HORIZONTAL_MIN);
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
            url=panel.getURLAyuda();
        }
        catch (UnsupportedOperationException exception) {
            System.out.println("metodo no implementado");
            return ;
        }
        
        
        PanelSecundarioAbstract panelSecundario = panelesSecundariosMap.get(PanelSecundarioAbstract.PANEL_AYUDA);
        JPanel jpanel = (JPanel) panelSecundario;
        int ancho=getjPanelSeleccion().getWidth()-1;
        int alto=getjPanelSeleccion().getHeight()-1;

        if(browser!=null)
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
                browser.loadURL("https://www.google.com.ec/");
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
        for (MenuControlador menuControlador : ventanasMenuList) {
            menuControlador.getMenuItem().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Constructor contructor=menuControlador.getVentana().getConstructor();
                        ControladorCodefacInterface ventana= (ControladorCodefacInterface) contructor.newInstance();
                        agregarListenerMenu(ventana,menuControlador.isMaximizado());
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
            });
        }
        

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
                    DialogInterfacePanel interfaz = (DialogInterfacePanel) frame;
                    Object resultado = interfaz.getResult();
                    frameInterface.formOwner.updateInterface(resultado);
                    frame.dispose();
                    mostrarPanelSecundario(false);
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
                                ex.printStackTrace();
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
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
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
                    
                    String tituloOriginal = getTituloOriginal(frame.getTitle());
                    frame.setTitle(tituloOriginal + " [Editar]"); 
                    
                    try {
                        frameInterface.buscar();
                        frameInterface.estadoFormulario= ControladorCodefacInterface.ESTADO_EDITAR;
                        limpiarCamposValidacion(frameInterface);
                        mostrarPanelSecundario(false);
                    } catch (ExcepcionCodefacLite ex) {
                        ex.printStackTrace();
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
                    frame.setTitle(tituloOriginal+" [Nuevo]");
                    frameInterface.eliminar();
                    frameInterface.estadoFormulario= ControladorCodefacInterface.ESTADO_GRABAR;
                    limpiarAnotaciones(frameInterface);
                    frameInterface.limpiar();
                    limpiarCamposValidacion(frameInterface);
                    
                    frameInterface.consola=new ConsolaGeneral();
                    mostrarConsola(frameInterface.consola,true);
                }
                catch (UnsupportedOperationException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    //getjButton4().setEnabled(false);
                } catch (ExcepcionCodefacLite ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
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
    
    private void agregarListenerMenu(ControladorCodefacInterface panel,boolean maximisado)
    {
        try {
                                    /**
             * Agregar variables de session a la pantalla
             */
            ParametroCodefacService servicio=new ParametroCodefacService();
            sessionCodefac.setParametrosCodefac(servicio.getParametrosMap());
            
            
            panel.panelPadre=generalPanelModel;
            panel.session=sessionCodefac;

            
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
            
            panel.estadoFormulario= ControladorCodefacInterface.ESTADO_GRABAR;
            
            panel.consola=new ConsolaGeneral();
            mostrarConsola(panel.consola,true);
            
            try
            {
                panel.iniciar();//Metodo que se ejecuta despues de construir el objeto
            }
            catch(java.lang.UnsupportedOperationException uoe)
            {}
            
                        
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
                    File file = new File(RecursoCodefac.AYUDA.getResourcePath(validacion.recurso()));
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
                validar.add("expresion regular fallo");
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
        getBtnAyuda().setEnabled(opcion);
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

                switch (key) {
                    case ControladorCodefacInterface.BOTON_GRABAR:
                        getBtnGuardar().setEnabled(value);
                        break;

                    case ControladorCodefacInterface.BOTON_ELIMINAR:
                        getBtnEliminar().setEnabled(value);
                        break;

                    case ControladorCodefacInterface.BOTON_IMPRIMIR:
                        getBtnImprimir().setEnabled(value);
                        break;

                    case ControladorCodefacInterface.BOTON_AYUDA:
                        getBtnAyuda().setEnabled(value);
                        break;

                    case ControladorCodefacInterface.BOTON_NUEVO:
                        getBtnNuevo().setEnabled(value);
                        break;

                    case ControladorCodefacInterface.BOTON_REFRESCAR:
                        getBtnActualizar().setEnabled(value);
                        break;

                    case ControladorCodefacInterface.BOTON_BUSCAR:
                        getBtnBuscar().setEnabled(value);
                        break;
                }

            }
        }
        catch(java.lang.UnsupportedOperationException uoe)
        {
            //Si no esta implementado el metodo poner todos los botones en falso
            habilitarBotones(false);
        }
    }
    
    private void iniciarComponentes()
    {
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

    public List<MenuControlador> getVentanasMenuList() {
        return ventanasMenuList;
    }

    public void setVentanasMenuList(List<MenuControlador> ventanasMenuList) {
        this.ventanasMenuList = ventanasMenuList;
        agregarListenerMenu();
    }

    @Override
    public Map<String, Object> mapReportePlantilla() {
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        Map<String,Object> parametros=new HashMap<String,Object>();
        parametros.put("pl_fecha_hora",formateador.format(new Date()));
        parametros.put("pl_usuario",sessionCodefac.getUsuario().getNick());
        parametros.put("pl_direccion",sessionCodefac.getEmpresa().getDireccion());
        parametros.put("pl_nombre_empresa",sessionCodefac.getEmpresa().getNombreLegal());
        parametros.put("pl_telefonos",sessionCodefac.getEmpresa().getTelefonos());
        
        parametros.put("pl_url_img1",(RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream("codefac-logotipo.png")));
        parametros.put("pl_img_facebook",(RecursoCodefac.IMAGENES_REDES_SOCIALES.getResourceInputStream("facebook.png")));
        parametros.put("pl_img_whatsapp",(RecursoCodefac.IMAGENES_REDES_SOCIALES.getResourceInputStream("whatsapp.png")));
        parametros.put("pl_img_telefono",(RecursoCodefac.IMAGENES_REDES_SOCIALES.getResourceInputStream("telefono.png")));
        parametros.put("pl_img_logo_pie",(RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream("codesoft-logo.png")));
        
        parametros.put("pl_url_img1_url",(RecursoCodefac.IMAGENES_GENERAL.getResourcePath("codefac-logotipo.png")));
        parametros.put("pl_img_facebook_url",(RecursoCodefac.IMAGENES_REDES_SOCIALES.getResourcePath("facebook.png")));
        parametros.put("pl_img_whatsapp_url",(RecursoCodefac.IMAGENES_REDES_SOCIALES.getResourcePath("whatsapp.png")));
        parametros.put("pl_img_telefono_url",(RecursoCodefac.IMAGENES_REDES_SOCIALES.getResourcePath("telefono.png")));
        parametros.put("pl_img_logo_pie_url",(RecursoCodefac.IMAGENES_GENERAL.getResourcePath("codesoft-logo.png")));
        
        parametros.put("pl_url_cabecera",RecursoCodefac.JASPER.getResourcePath("encabezado.jasper"));
        parametros.put("pl_url_piepagina",RecursoCodefac.JASPER.getResourcePath("pie_pagina.jasper"));
        
        //System.out.println(parametros.get("SUBREPORT_DIR"));
        return parametros;
    }

    public SessionCodefac getSessionCodefac() {
        return sessionCodefac;
    }

    public void setSessionCodefac(SessionCodefac sessionCodefac) {
        this.sessionCodefac = sessionCodefac;
    }

    @Override
    public void crearDialogoCodefac(ObserverUpdateInterface panel,String namePanel, boolean maximizado) {
        
        Class clase=buscarPanelDialog(namePanel);
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
        for (MenuControlador menuControlador : ventanasMenuList) {
            if(menuControlador.getVentana().getName().equals(nombre))
            {
                 return menuControlador.getVentana();
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
                Boolean respuesta=DialogoCodefac.dialogoPregunta("Alerta","Estas seguro que deseas salir?",DialogoCodefac.MENSAJE_ADVERTENCIA);
                if(respuesta)
                {
                    hiloPublicidadCodefac.hiloPublicidad=false;
                    dispose();
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
                AcercaModel.getInstance().setVisible(true);
            }
        });
    }
   
}