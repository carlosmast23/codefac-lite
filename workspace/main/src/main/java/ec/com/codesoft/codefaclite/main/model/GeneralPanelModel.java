/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;



import ec.com.codesoft.codefaclite.corecodefaclite.ayuda.AyudaCodefacAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.util.LimpiarAnotacion;
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

    public GeneralPanelModel() 
    {
        iniciarComponentes();
        agregarListener();
        agregarListenerMenu();
        habilitarBotones(false);
        //getjSplitPanel().setRightComponent(getJpanelAuxiliar());
               
        Component componente=getjSplitPanel().getLeftComponent();
        getjSplitPanel().setLeftComponent(getJpanelAuxiliar());
        getjSplitPanel().setRightComponent(componente);
        getjSplitPanel().setDividerLocation(0.0d);
        
        //Image IMG=new ImageIcon(getClass().getResource("/imagenes/casalibertad.jpg")).getImage();
        //BufferedImage image=ImageIO.read(IMG);
        Image fondoImg=new javax.swing.ImageIcon(getClass().getResource("/img.general/fondoGeneral.png")).getImage();
        getjDesktopPane1().setBorder(new Fondo(fondoImg));
        
        getjDesktopPane1().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                revalidate();
                repaint();
            }
        });
        
    }
    
    private void cargarAyuda()
    {
            browser = new SwingBrowser();
            GeneralPanelInterface panel=(GeneralPanelInterface) getjDesktopPane1().getSelectedFrame();
            browser.loadURL(panel.getURLAyuda());
            //browser.loadURL("https://es.wikipedia.org/wiki/Vlad%C3%ADmir_Makanin");
            System.out.println("creando panel:"+getJPanelContenidoAuxiliar().getWidth()+":"+getJPanelContenidoAuxiliar().getHeight());
            browser.setBounds(1, 1, getJPanelContenidoAuxiliar().getWidth() - 1, getJPanelContenidoAuxiliar().getHeight() - 1);
            getJPanelContenidoAuxiliar().removeAll();
            getJPanelContenidoAuxiliar().add(browser);
            getjSplitPanel().setDividerLocation(0.25);
            //getjSplitPanelVerticalSecundario().setDividerLocation(0.75);
    }
    
    private void cargarPublicidad()
    {
            browserPublicidad = new SwingBrowser();
            //browserPublicidad.loadURL(panel.getURLAyuda());
            browserPublicidad.loadURL("http://www.vm.codesoft-ec.com/general/publicidad/b");
            //System.out.println("creando panel:"+getJPanelPublicidadContenidoContenido().getWidth()+":"+getJPanelPublicidadContenido().getHeight());
            browserPublicidad.setBounds(1, 1, getjPanelPublicidadContenido().getWidth() - 1, getjPanelPublicidadContenido().getHeight() - 1);
            getjPanelPublicidadContenido().removeAll();
            getjPanelPublicidadContenido().add(browserPublicidad);
            //getjSplitPanel().setDividerLocation(0.75);
            getjSplitPanelVerticalSecundario().setDividerLocation(0.70);
    }
  
    private void agregarListenerMenu()
    {
        getjDesktopPane1().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                super.componentResized(e);
                System.out.println("Resizing");
                Image fondoImg=new javax.swing.ImageIcon(getClass().getResource("/img.general/fondoGeneral.png")).getImage();
                getjDesktopPane1().setBorder(new Fondo(fondoImg));
        }       
            
    });
        
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
                agregarListenerMenu(new ClienteModel());
                
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
    
    private void agregarListener()
    {
        getBtnNuevo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame frame= getjDesktopPane1().getSelectedFrame();
                String tituloOriginal=getTituloOriginal(frame.getTitle());
                frame.setTitle(tituloOriginal+" [Nuevo]");
                GeneralPanelInterface frameInterface=(GeneralPanelInterface) frame;
                frameInterface.estadoFormulario= GeneralPanelInterface.ESTADO_GRABAR;
                limpiar(frameInterface);
                limpiarCamposValidacion(frameInterface);
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
                            frameInterface.grabar();
                            procesoTerminado=true;
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"Error de validacion Nuevo");
                        }
                    }
                    else
                    {
                        if(validarFormulario(frameInterface))
                        {
                            frameInterface.editar();
                            procesoTerminado=true;
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"Error de validacion Editar");
                        }
                    
                    }
                    
                    if(procesoTerminado)
                    {
                        String tituloOriginal=getTituloOriginal(frame.getTitle());
                        frame.setTitle(tituloOriginal+" [Nuevo]");
                        frameInterface.estadoFormulario=GeneralPanelInterface.ESTADO_GRABAR;
                        limpiar(frameInterface);
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
                    frameInterface.buscar();
                    frameInterface.estadoFormulario= GeneralPanelInterface.ESTADO_EDITAR;
                    limpiarCamposValidacion(frameInterface);
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
                    limpiar(frameInterface);
                    limpiarCamposValidacion(frameInterface);
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
                    /*
                    String pagina = "http://www.google.com";
                    JEditorPane pane = new JEditorPane();
                    pane.setSize(400,400);
                    JScrollPane scroll = new JScrollPane(pane);
                    scroll.setSize(400,400);
                    JPanel panelPagina=new JPanel();
                    panelPagina.add(scroll);
                    panelPagina.setSize(400, 400);
                    generalPanelModel.add(panelPagina,java.awt.BorderLayout.LINE_END);
                    generalPanelModel.invalidate();
                    generalPanelModel.validate();
                    generalPanelModel.repaint();
                    */
                    cargarAyuda();
                    cargarPublicidad();
                    //limpiarCamposValidacion(frameInterface);
                }
            });
        
         getjSplitPanel().addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if(browser!=null)
                {
                    //System.out.println("redimensionando panel:"+getJPanelContenidoAuxiliar().getWidth()+":"+getJPanelContenidoAuxiliar().getHeight());
                    browser.setBounds(1, 1, getJPanelContenidoAuxiliar().getWidth() - 1, getJPanelContenidoAuxiliar().getHeight() - 1);
                }
            }
        });
        
         getBtnSalirPantallAuxiliar().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getjSplitPanel().setDividerLocation(0d);
                    browser=null;
                }
            });
         
         getBtnSalirPantallaPublicidad().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getjSplitPanelVerticalSecundario().setDividerLocation(0.999999999999d);
                    browser=null;
                }
            });
        
    }   
    
    private void agregarListenerMenu(GeneralPanelInterface panel)
    {
        try {
            panel.estadoFormulario= GeneralPanelInterface.ESTADO_GRABAR;
            panel.panelPadre=generalPanelModel;
            panel.addInternalFrameListener(listenerFrame);
            String tituloOriginal=getTituloOriginal(panel.getTitle());
            panel.setTitle(tituloOriginal+" [Nuevo]");
            getjDesktopPane1().add(panel);
            panel.setMaximum(true);
            panel.show();
            agregarValidadores(panel);
            agregarAyudas(panel);
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
                        System.out.println(line);
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
    
    private void limpiar(GeneralPanelInterface panel)
    {
       ConsolaGeneral consola=new ConsolaGeneral();
       boolean validado=true;
       
       Class classVentana=panel.getClass();
        Method[] metodos=classVentana.getMethods();
        for (Method metodo : metodos) {
            LimpiarAnotacion validacion=metodo.getAnnotation(LimpiarAnotacion.class);
            //System.out.println(metodo.getName());
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
       ConsolaGeneral consola=new ConsolaGeneral();
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
                    componente.setBackground(new Color(255,255,102));
                    Vector<String> errores=validar(validacion,componente);
                    for (String error : errores) {
                        consola.agregarDatos(validacion.nombre(),error,componente);
                        validado=false;
                    }
                    
                    //Validacion personalizada
                    String[] personalizados=validacion.personalizado();
                    for (String personalizado : personalizados) {
                        if(!personalizado.equals(""))
                        {
                            Method[] metodosValidar=classVentana.getMethods();
                            for (Method method : metodosValidar) 
                            {
                                ///System.out.println(method.getName());
                                validacionPersonalizadaAnotacion validacionPersonalizada=method.getAnnotation(validacionPersonalizadaAnotacion.class);

                                if(validacionPersonalizada!=null)
                                {
                                    if(personalizado.equals(method.getName()))
                                    {
                                        boolean resultado=(boolean) method.invoke(panel);                                        
                                        if(!resultado)
                                        {
                                            consola.agregarDatos(validacion.nombre(),validacionPersonalizada.errorTitulo(),componente);
                                            validado=false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                                        
                    
                    
                    
                    //JTextField
                    
                    
                    
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        if(!validado)
        {
            mostrarConsola(consola);
        }
        
        return validado;
    }
    
    private Vector<String> validar(ValidacionCodefacAnotacion validacion,JTextComponent componente)
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
       getjSplitPanel().setDividerLocation(0.25d);
       
    }
    
    private void agregarValidadores(GeneralPanelInterface panel)
    {
       Class classVentana=panel.getClass();
        Method[] metodos=classVentana.getMethods();
        for (Method metodo : metodos) {
            ValidacionCodefacAnotacion validacion=metodo.getAnnotation(ValidacionCodefacAnotacion.class);
            //System.out.println(metodo.getName());
            if(validacion!=null)
            {
                try {
                    JTextComponent componente=(JTextComponent) metodo.invoke(panel);
                    componente.setBorder(BorderFactory.createMatteBorder(
                                    1, 5, 1, 1, new Color(122, 138, 153)));

                    componente.addFocusListener(new FocusListener() {
                        @Override
                        public void focusGained(FocusEvent e) {
                            
                        }

                        @Override
                        public void focusLost(FocusEvent e) {
                            if(componente.getText().equals(""))
                            {
                                //componente.setText("ingrese datos");
                                //componente.requestFocus();
                                componente.setBackground(new Color(255,255,102));
                                //omponent[] componentes=componente.getComponents();
                                //Container c=componente.getParent();
                                //JLabel myLabel = new JLabel("Team 1");
                                //c.add(myLabel);
                                //for (Component componente1 : componentes) {
                                //    System.out.println(componente1);
                                //}
                                
                            }
                            else
                            {
                                componente.setBackground(Color.WHITE);
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

                        }

                        @Override
                        public void internalFrameClosed(InternalFrameEvent e) {
                            //habilitarBotones(false);
                        }

                        @Override
                        public void internalFrameIconified(InternalFrameEvent e) {

                        }

                        @Override
                        public void internalFrameDeiconified(InternalFrameEvent e) {
                            //JOptionPane.showMessageDialog(null,"internalFrameDeiconified");
                            habilitarConfiguracioneBotones();
                        }

                        @Override
                        public void internalFrameActivated(InternalFrameEvent e) {
                            //JOptionPane.showMessageDialog(null,"internalFrameActivated");
                            habilitarConfiguracioneBotones();
                           
                        }

                        @Override
                        public void internalFrameDeactivated(InternalFrameEvent e) {
                             habilitarBotones(false);
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
    }
    
    private void seleccionaPanel(GeneralPanelInterface panelInterface)
    {
        
        this.panelActual=panelInterface;
        this.controladorVista.agregarVista(panelInterface);
        
        //JPanel panel=(JPanel)panelInterface;
       // panel.setPreferredSize(getjPanel4().getSize());
        /*
        this.getjPanel4().removeAll();
        this.getjPanel4().add(panel,BorderLayout.BEFORE_FIRST_LINE);
        this.getjPanel4().revalidate();
        this.getjPanel4().repaint();
        
        actualizarVistasActivas();*/
    }
    
    private void mostrarVista(GeneralPanelInterface panelInterface)
    {
        //JPanel panel=(JPanel)panelInterface;
        //panel.setPreferredSize(getjPanel4().getSize());
        
        /**
        this.getjPanel4().removeAll();
        this.getjPanel4().add(panel,BorderLayout.BEFORE_FIRST_LINE);
        this.getjPanel4().revalidate();
        this.getjPanel4().repaint();
    }
    
    private void actualizarVistasActivas()
    {
        DefaultListModel modelo = new DefaultListModel();
        for (GeneralPanelInterface panel :controladorVista.getListaPaneles() ) {
            modelo.addElement(panel.getNombre());
            
        }
        getjList2().setModel(modelo);
        //getjList2().setCellRenderer(new CountryRenderer());
        /*getjList2().setCellRenderer(new DefaultListCellRenderer() {
            @Override
        public Component getListCellRendererComponent(JList<? extends Country> list, Country country, int index,
            boolean isSelected, boolean cellHasFocus) {

            String code = country.getCode();
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/" + code + ".png"));

            setIcon(imageIcon);
            setText(country.getName());

            return this;
        }
        });*/

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
    
   
}
    

