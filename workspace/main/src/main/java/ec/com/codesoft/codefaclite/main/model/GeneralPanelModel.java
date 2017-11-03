/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;


import com.sun.org.apache.bcel.internal.generic.AALOAD;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.crm.model.ClienteModel;
import ec.com.codesoft.codefaclite.main.panel.GeneralPanelForm;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import java.awt.BorderLayout;
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
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
    DefaultListModel modelo;

    public GeneralPanelModel() 
    {
        iniciarComponentes();
        agregarListener();
        habilitarBotones(false);
        
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
    
    
    
    private void agregarListener()
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
                /*
                try {
                    //seleccionaPanel(new OtraVentana2());
                    OtraVentanaModel ayuda=new OtraVentanaModel();
                    ayuda.addInternalFrameListener(listenerFrame);
                    
                    getjDesktopPane1().add(ayuda);
                    ayuda.setMaximum(true);
                    ayuda.show();
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                */
            }
        });
        
       getjMenuItem1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                try {
                    
                    //seleccionaPanel(new OtraVentana2());
                    OtraVentanaModel ayuda=new OtraVentanaModel();
                    ayuda.addInternalFrameListener(listenerFrame);
                    
                    getjDesktopPane1().add(ayuda);
                    ayuda.setMaximum(true);
                    ayuda.show();
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }*/
                
            }
        });
        getjMenuCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarListenerMenu(new ClienteModel());
                
            }
        });
        
        
        getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try
                {
                    JInternalFrame frame= getjDesktopPane1().getSelectedFrame();
                    GeneralPanelInterface frameInterface=(GeneralPanelInterface) frame;
                    frameInterface.grabar();
                }
                catch (UnsupportedOperationException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    //getjButton4().setEnabled(false);
                }
                               
            }
        });
        
        getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try
                {
                    JInternalFrame frame= getjDesktopPane1().getSelectedFrame();
                    GeneralPanelInterface frameInterface=(GeneralPanelInterface) frame;
                    frameInterface.editar();
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
                    frameInterface.eliminar();
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
                }
                catch (UnsupportedOperationException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    //getjButton4().setEnabled(false);
                }
                               
            }
        });
        
    }   
    
    private void agregarListenerMenu(GeneralPanelInterface panel)
    {
        try {
            panel.panelPadre=generalPanelModel;
            panel.addInternalFrameListener(listenerFrame);
            getjDesktopPane1().add(panel);
            panel.setMaximum(true);
            panel.show();
        } catch (PropertyVetoException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
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
        getBtnEditar().setEnabled(opcion);
        getBtnEliminar().setEnabled(opcion);
        getBtnGuardar().setEnabled(opcion);
        getBtnImprimir().setEnabled(opcion);
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

                case GeneralPanelInterface.BOTON_EDITAR:
                    getBtnEditar().setEnabled(value);
                    break;

                case GeneralPanelInterface.BOTON_ELIMINAR:
                    getBtnEliminar().setEnabled(value);
                    break;
                    
                case GeneralPanelInterface.BOTON_IMPRIMIR:
                    getBtnImprimir().setEnabled(value);
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
    

