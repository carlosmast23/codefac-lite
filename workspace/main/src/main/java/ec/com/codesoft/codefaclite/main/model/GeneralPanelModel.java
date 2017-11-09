/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;



import ec.com.codesoft.codefaclite.corecodefaclite.ayuda.AyudaCodefacAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.util.LimpiarAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ConsolaGeneral;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ValidacionCodefacAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.validacionPersonalizadaAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.crm.model.ClienteModel;
import ec.com.codesoft.codefaclite.main.panel.GeneralPanelForm;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
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
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private GeneralPanelInterface panelActual;
    private SwingBrowser browser ;
    private SwingBrowser browserPublicidad ;
    DefaultListModel modelo;
    
    private static double PROPORCION_HORIZONTAL=0.75d;
    private static double PROPORCION_VERTICAL=0.7d;
    
    private static double PROPORCION_HORIZONTAL_DEFAULT=0.75d;
    private static double PROPORCION_VERTICAL_DEFAULT=0.7d;
    
    private static double PROPORCION_HORIZONTAL_MIN=0.95d;
    private static double PROPORCION_VERTICAL_MIN=0.95d;
    
    
    private static double PROPORCION_HORIZONTAL_INICIAL=0.999999999d;
    private static double PROPORCION_VERTICAL_INICIAL=0.7d;

    public GeneralPanelModel() 
    {
        iniciarComponentes();
        agregarListenerBotones();
        agregarListenerMenu();
        agregarListenerSplit();
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
        GeneralPanelInterface panel=(GeneralPanelInterface) getjDesktopPane1().getSelectedFrame();
        if(browser!=null)
        {
            //Verifacar si la url cargada es la misma no volver a cargar
            if(!browser.getUrl().equals(panel.getURLAyuda()))
            {
                browser = new SwingBrowser();
                browser.loadURL(panel.getURLAyuda());
                browser.setBounds(1, 1, getJPanelContenidoAuxiliar().getWidth() - 1, getJPanelContenidoAuxiliar().getHeight() - 1);
                getJPanelContenidoAuxiliar().removeAll();
                getJPanelContenidoAuxiliar().add(browser);
            }
            else
            {
                getJPanelContenidoAuxiliar().removeAll();
                getJPanelContenidoAuxiliar().add(browser);
            }
        }
        else
        {
            browser = new SwingBrowser();
            if(panel!=null)
            {
                browser.loadURL(panel.getURLAyuda());
                browser.setBounds(1, 1, getJPanelContenidoAuxiliar().getWidth() - 1, getJPanelContenidoAuxiliar().getHeight() - 1);
                getJPanelContenidoAuxiliar().removeAll();
                getJPanelContenidoAuxiliar().add(browser);
            }
            else
            {
                browser.loadURL("https://www.google.com.ec/");
                browser.setBounds(1, 1, getJPanelContenidoAuxiliar().getWidth() - 1, getJPanelContenidoAuxiliar().getHeight() - 1);
                getJPanelContenidoAuxiliar().removeAll();
                getJPanelContenidoAuxiliar().add(browser);
            }
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
        /*
        getjDesktopPane1().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                //Redimensionar tamaño a las proporciones de la pantalla
                //super.componentResized(e);                
                Image fondoImg=new javax.swing.ImageIcon(getClass().getResource("/img.general/fondoGeneral.png")).getImage();
                getjDesktopPane1().setBorder(new Fondo(fondoImg));
                
        }       
            
    });*/
        
        getjMenuItem1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        
       getjMenuItem1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        getjMenuCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarListenerMenu(new ClienteModel(),true);                
            }
        });
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
                String tituloOriginal=getTituloOriginal(frame.getTitle());
                frame.setTitle(tituloOriginal+" [Nuevo]");
                GeneralPanelInterface frameInterface=(GeneralPanelInterface) frame;
                frameInterface.estadoFormulario= GeneralPanelInterface.ESTADO_GRABAR;
                limpiarAnotaciones(frameInterface);
                frameInterface.limpiar();
                limpiarCamposValidacion(frameInterface);
                frameInterface.consola=new ConsolaGeneral();
                mostrarConsola(frameInterface.consola);
            }
        });
        
        getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try
                {
                    boolean procesoTerminado=false;
                    JInternalFrame frame= getjDesktopPane1().getSelectedFrame();
                    GeneralPanelInterface frameInterface=(GeneralPanelInterface) frame;
                    
                    if(frameInterface.estadoFormulario.equals(GeneralPanelInterface.ESTADO_GRABAR))
                    {
                        
                        if(validarFormulario(frameInterface))
                        {
                            try {
                                frameInterface.grabar();
                                procesoTerminado=true;
                            } catch (ExcepcionCodefacLite ex) {
                                JOptionPane.showMessageDialog(null,ex.getMessage());
                            }
                            
                        }
                        else
                        {
                            mostrarConsola(frameInterface.consola);
                            JOptionPane.showMessageDialog(null,"Error de validacion Nuevo");
                        }
                    }
                    else
                    {
                        if(validarFormulario(frameInterface))
                        {
                            try {
                                frameInterface.editar();
                                procesoTerminado=true;
                            } catch (ExcepcionCodefacLite ex) {
                                JOptionPane.showMessageDialog(null,ex.getMessage());
                            }
                            
                        }
                        else
                        {
                            mostrarConsola(frameInterface.consola);
                            JOptionPane.showMessageDialog(null,"Error de validacion Editar");
                        }
                    
                    }
                    
                    if(procesoTerminado)
                    {
                        String tituloOriginal=getTituloOriginal(frame.getTitle());
                        frame.setTitle(tituloOriginal+" [Nuevo]");
                        frameInterface.estadoFormulario=GeneralPanelInterface.ESTADO_GRABAR;
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
                    GeneralPanelInterface frameInterface=(GeneralPanelInterface) frame;
                    String tituloOriginal = getTituloOriginal(frame.getTitle());
                    frame.setTitle(tituloOriginal + " [Editar]"); 
                    
                    try {
                        frameInterface.buscar();
                        frameInterface.estadoFormulario= GeneralPanelInterface.ESTADO_EDITAR;
                        limpiarCamposValidacion(frameInterface);
                        mostrarPanelSecundario(false);
                    } catch (ExcepcionCodefacLite ex) {
                        JOptionPane.showMessageDialog(null,ex.getMessage());
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
                    GeneralPanelInterface frameInterface=(GeneralPanelInterface) frame;
                    frameInterface.actualizar();
                    limpiarCamposValidacion(frameInterface);
                }
                catch (UnsupportedOperationException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    //getjButton4().setEnabled(false);
                }
                               
            }
        });
        
        getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try
                {
                    JInternalFrame frame= getjDesktopPane1().getSelectedFrame();
                    GeneralPanelInterface frameInterface=(GeneralPanelInterface) frame;
                    String tituloOriginal=getTituloOriginal(frame.getTitle());
                    frame.setTitle(tituloOriginal+" [Nuevo]");
                    frameInterface.eliminar();
                    frameInterface.estadoFormulario= GeneralPanelInterface.ESTADO_GRABAR;
                    limpiarAnotaciones(frameInterface);
                    frameInterface.limpiar();
                    limpiarCamposValidacion(frameInterface);
                    
                    frameInterface.consola=new ConsolaGeneral();
                    mostrarConsola(frameInterface.consola);
                }
                catch (UnsupportedOperationException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    //getjButton4().setEnabled(false);
                }
                               
            }
        });
        
        getBtnImprimir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try
                {
                    JInternalFrame frame= getjDesktopPane1().getSelectedFrame();
                    GeneralPanelInterface frameInterface=(GeneralPanelInterface) frame;
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
                    mostrarPanelSecundario(true);
                    
                }
            });
        
         getBtnSalirPantallAuxiliar().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getjSplitPanel().setDividerLocation(PROPORCION_HORIZONTAL_INICIAL);
                    PROPORCION_HORIZONTAL=PROPORCION_HORIZONTAL_INICIAL;
                    //browser=null;
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
    
    private void agregarListenerMenu(GeneralPanelInterface panel,boolean maximisado)
    {
        try {
            panel.panelPadre=generalPanelModel;
            panel.addInternalFrameListener(listenerFrame);
            String tituloOriginal=getTituloOriginal(panel.getTitle());
            panel.setTitle(tituloOriginal+" [Nuevo]");
            getjDesktopPane1().add(panel);
            panel.setMaximum(maximisado);
            panel.show();
            getBtnNuevo().requestFocus();
            agregarValidadores(panel);
            agregarAyudas(panel);
            
            panel.estadoFormulario= GeneralPanelInterface.ESTADO_GRABAR;
            
            panel.consola=new ConsolaGeneral();
            mostrarConsola(panel.consola);
            
                        
        } catch (PropertyVetoException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void agregarAyudas(GeneralPanelInterface panel)
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
                    
                    InputStreamReader reader=new InputStreamReader(input);
                    BufferedReader br = new BufferedReader(reader);
                    String line = null;
                    String htmlText="";
                    while ( (line = br.readLine()) != null)
                    {
                        htmlText+=line;
                    }
                    
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
                } catch (IOException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    private void limpiarAnotaciones(GeneralPanelInterface panel)
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
    
    private void limpiarCamposValidacion(GeneralPanelInterface panel)
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
    
    private boolean validarFormulario(GeneralPanelInterface panel)
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
                
                try {
                    JTextComponent componente=(JTextComponent) metodo.invoke(panel);
                    Vector<String> errores=validarComponente(validacion,componente,panel);
                    
                    if(errores.size()>0)
                    {
                        //Si Existe errores pinto de colo amarillo
                        componente.setBackground(new Color(255,255,102));
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
    
    private void mostrarConsola(ConsolaGeneral consola)
    {
        
       getjTablaConsola().addMouseListener(new MouseListener() {
           @Override
           public void mouseClicked(MouseEvent e) {
               int fila=getjTablaConsola().getSelectedRow();
               consola.seleccionarFila(fila);
               
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
       
       getjTablaConsola().setModel(consola.getModeloTabla());
       getJPanelContenidoAuxiliar().removeAll();
       getJPanelContenidoAuxiliar().add(getJPanelConsola());
       
       if(consola.getModeloTabla().getRowCount()>0)
       {
           mostrarPanelSecundario(true);
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
            ValidacionCodefacAnotacion validacion=metodo.getAnnotation(ValidacionCodefacAnotacion.class);
            if(validacion!=null)
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
                                mostrarConsola(panel.consola);                                
                            }
                            else
                            {
                                panel.consola.quitarDato(componente);
                                componente.setBackground(Color.white);
                                mostrarConsola(panel.consola);   
                                
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
    
    
    InternalFrameListener listenerFrame=new InternalFrameListener() {
                        @Override
                        public void internalFrameOpened(InternalFrameEvent e) {
                        }

                        @Override
                        public void internalFrameClosing(InternalFrameEvent e) {
                            // System.out.println("internalFrameClosing");
                            GeneralPanelInterface panel=(GeneralPanelInterface) getjDesktopPane1().getSelectedFrame();
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
 
    private void habilitarConfiguracioneBotones()
    {
        JInternalFrame frame = getjDesktopPane1().getSelectedFrame();
        GeneralPanelInterface frameInterface = (GeneralPanelInterface) frame;
        Map<Integer, Boolean> mapPermisos = frameInterface.permisosFormulario();
        for (Map.Entry<Integer, Boolean> entry : mapPermisos.entrySet()) {
            Integer key = entry.getKey();
            Boolean value = entry.getValue();

            switch (key) {
                case GeneralPanelInterface.BOTON_GRABAR:
                    getBtnGuardar().setEnabled(value);
                    break;

                case GeneralPanelInterface.BOTON_ELIMINAR:
                    getBtnEliminar().setEnabled(value);
                    break;
                    
                case GeneralPanelInterface.BOTON_IMPRIMIR:
                    getBtnImprimir().setEnabled(value);
                    break;
                    
                case GeneralPanelInterface.BOTON_AYUDA:
                    getBtnAyuda().setEnabled(value);
                    break;
                    
                case GeneralPanelInterface.BOTON_NUEVO:
                    getBtnNuevo().setEnabled(value);
                    break;
                    
                case GeneralPanelInterface.BOTON_REFRESCAR:
                    getBtnActualizar().setEnabled(value);
                    break;
                
                case GeneralPanelInterface.BOTON_BUSCAR:
                    getBtnBuscar().setEnabled(value);
                    break;
            }

        }
    }
    
    private void iniciarComponentes()
    {
        controladorVista=new ControladorVista();
        
        //Cargar configuraciones de los divisores
        PROPORCION_HORIZONTAL=PROPORCION_HORIZONTAL_INICIAL;
        PROPORCION_VERTICAL=PROPORCION_VERTICAL_INICIAL;
        
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
    
    private void seleccionaPanel(GeneralPanelInterface panelInterface)
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
        agregarListenerMenu(panel,maximizado);
    }
    
   
}
    

