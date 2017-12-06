/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.panel;


import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

/**
 *
 * @author Carlos
 */
public class GeneralPanelForm extends javax.swing.JFrame  {

    /**
     * Creates new form PanelGeneralForm
     */
    public GeneralPanelForm() {
        initComponents();
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JpanelAuxiliar = new javax.swing.JPanel();
        jSplitPanelVerticalSecundario = new javax.swing.JSplitPane();
        JPanelPublicidad = new javax.swing.JPanel();
        jPanelPublicidadContenido = new javax.swing.JPanel();
        jPanelPublicidadBorde = new javax.swing.JPanel();
        btnSalirPantallaPublicidad = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        JPanelAuxiliarInterno = new javax.swing.JPanel();
        jPanelSeleccion = new javax.swing.JTabbedPane();
        JPanelMenu = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnAyuda = new javax.swing.JButton();
        JPanelPiePagina = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jSplitPanel = new javax.swing.JSplitPane();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuInicio = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        menuConfiguracion = new javax.swing.JMenu();
        jMenuComprobanteConfig = new javax.swing.JMenuItem();
        menuCrm = new javax.swing.JMenu();
        jMenuCliente = new javax.swing.JMenuItem();
        jMenuProducto = new javax.swing.JMenuItem();
        jMenuEmisor = new javax.swing.JMenuItem();
        menuFacturacion = new javax.swing.JMenu();
        jMenuFactura = new javax.swing.JMenuItem();
        jMenuItemMonitor = new javax.swing.JMenuItem();
        jMenuItemNotaCredito = new javax.swing.JMenuItem();
        jMenuUtilidades = new javax.swing.JMenu();
        jMenuCalculadora = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemUtilidades = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();

        JpanelAuxiliar.setLayout(new javax.swing.BoxLayout(JpanelAuxiliar, javax.swing.BoxLayout.LINE_AXIS));

        jSplitPanelVerticalSecundario.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        JPanelPublicidad.setBackground(new java.awt.Color(255, 255, 255));
        JPanelPublicidad.setLayout(new java.awt.BorderLayout());

        jPanelPublicidadContenido.setLayout(new javax.swing.BoxLayout(jPanelPublicidadContenido, javax.swing.BoxLayout.LINE_AXIS));
        JPanelPublicidad.add(jPanelPublicidadContenido, java.awt.BorderLayout.CENTER);

        jPanelPublicidadBorde.setBackground(new java.awt.Color(62, 93, 162));
        jPanelPublicidadBorde.setLayout(new java.awt.BorderLayout());

        btnSalirPantallaPublicidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/salir-ico.png"))); // NOI18N
        btnSalirPantallaPublicidad.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnSalirPantallaPublicidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirPantallaPublicidadActionPerformed(evt);
            }
        });
        jPanelPublicidadBorde.add(btnSalirPantallaPublicidad, java.awt.BorderLayout.LINE_END);

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Publicidad");
        jPanelPublicidadBorde.add(jLabel3, java.awt.BorderLayout.CENTER);

        JPanelPublicidad.add(jPanelPublicidadBorde, java.awt.BorderLayout.PAGE_START);

        jSplitPanelVerticalSecundario.setBottomComponent(JPanelPublicidad);

        JPanelAuxiliarInterno.setLayout(new java.awt.BorderLayout());
        JPanelAuxiliarInterno.add(jPanelSeleccion, java.awt.BorderLayout.CENTER);

        jSplitPanelVerticalSecundario.setLeftComponent(JPanelAuxiliarInterno);

        JpanelAuxiliar.add(jSplitPanelVerticalSecundario);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(800, 600));

        JPanelMenu.setBackground(new java.awt.Color(255, 255, 255));

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/nuevo-icono.png"))); // NOI18N
        btnNuevo.setToolTipText("Nuevo");
        btnNuevo.setMargin(new java.awt.Insets(0, 14, 0, 14));
        JPanelMenu.add(btnNuevo);

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/save-icon.png"))); // NOI18N
        btnGuardar.setToolTipText("Grabar");
        btnGuardar.setMargin(new java.awt.Insets(0, 14, 0, 14));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        JPanelMenu.add(btnGuardar);

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/delete-icon.png"))); // NOI18N
        btnEliminar.setToolTipText("Eliminar");
        btnEliminar.setMargin(new java.awt.Insets(0, 14, 0, 14));
        JPanelMenu.add(btnEliminar);

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find-icon.png"))); // NOI18N
        btnBuscar.setToolTipText("Buscar");
        btnBuscar.setMargin(new java.awt.Insets(0, 14, 0, 14));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        JPanelMenu.add(btnBuscar);

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/refresh-icon.png"))); // NOI18N
        btnActualizar.setToolTipText("Refrescar");
        btnActualizar.setMargin(new java.awt.Insets(0, 14, 0, 14));
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        JPanelMenu.add(btnActualizar);

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/print-icon.png"))); // NOI18N
        btnImprimir.setToolTipText("Imprimir");
        btnImprimir.setMargin(new java.awt.Insets(0, 14, 0, 14));
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        JPanelMenu.add(btnImprimir);

        btnAyuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/help_icon.png"))); // NOI18N
        btnAyuda.setToolTipText("Ayuda");
        btnAyuda.setMargin(new java.awt.Insets(0, 14, 0, 14));
        JPanelMenu.add(btnAyuda);

        getContentPane().add(JPanelMenu, java.awt.BorderLayout.PAGE_START);

        JPanelPiePagina.setBackground(new java.awt.Color(62, 93, 162));
        JPanelPiePagina.setForeground(new java.awt.Color(62, 93, 162));
        JPanelPiePagina.setToolTipText("");

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img.general/letraCodefac.png"))); // NOI18N
        jLabel1.setText("Todos los derechos reservador por @Codesoft 2017");
        JPanelPiePagina.add(jLabel1);

        getContentPane().add(JPanelPiePagina, java.awt.BorderLayout.PAGE_END);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());
        getContentPane().add(jPanel3, java.awt.BorderLayout.LINE_START);

        jSplitPanel.setDividerLocation(800);
        jSplitPanel.setDividerSize(3);
        jSplitPanel.setDoubleBuffered(true);
        jSplitPanel.setLeftComponent(jDesktopPane1);

        getContentPane().add(jSplitPanel, java.awt.BorderLayout.CENTER);

        menuInicio.setText("Inicio");

        jMenuItem1.setText("Iinicio");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        menuInicio.add(jMenuItem1);

        jMenuItem2.setText("Salir");
        menuInicio.add(jMenuItem2);

        jMenuBar1.add(menuInicio);

        menuConfiguracion.setText("Configuraciones");

        jMenuComprobanteConfig.setText("Comprobante");
        jMenuComprobanteConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuComprobanteConfigActionPerformed(evt);
            }
        });
        menuConfiguracion.add(jMenuComprobanteConfig);

        jMenuBar1.add(menuConfiguracion);

        menuCrm.setText("Crm");

        jMenuCliente.setText("Clientes");
        jMenuCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuClienteActionPerformed(evt);
            }
        });
        menuCrm.add(jMenuCliente);

        jMenuProducto.setText("Productos");
        menuCrm.add(jMenuProducto);

        jMenuEmisor.setText("Emisor");
        menuCrm.add(jMenuEmisor);

        jMenuBar1.add(menuCrm);

        menuFacturacion.setText("Facturacion");

        jMenuFactura.setText("Factura");
        menuFacturacion.add(jMenuFactura);

        jMenuItemMonitor.setText("Monitor");
        menuFacturacion.add(jMenuItemMonitor);

        jMenuItemNotaCredito.setText("Nota Credito");
        menuFacturacion.add(jMenuItemNotaCredito);

        jMenuBar1.add(menuFacturacion);

        jMenuUtilidades.setText("Utilidades");

        jMenuCalculadora.setText("Calculadora");
        jMenuCalculadora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuCalculadoraActionPerformed(evt);
            }
        });
        jMenuUtilidades.add(jMenuCalculadora);

        jMenuBar1.add(jMenuUtilidades);

        jMenu1.setText("Comprobantes");

        jMenuItemUtilidades.setText("Utilidades");
        jMenu1.add(jMenuItemUtilidades);

        jMenuBar1.add(jMenu1);

        jMenu8.setText("Ayuda");
        jMenuBar1.add(jMenu8);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        //ClassLoader classLoader = RecursoCodefac.class.getClassLoader();
        //System.out.println(classLoader.getResource("img/iconos/save-icon.png").getFile());
        //btnBuscar.setIcon(new ImageIcon(classLoader.getResource("img/iconos/save-icon.png").getFile()));
       
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        //AyudaForm ayuda=new AyudaForm();
       
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void jMenuClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuClienteActionPerformed
       
    }//GEN-LAST:event_jMenuClienteActionPerformed

    private void jMenuComprobanteConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuComprobanteConfigActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuComprobanteConfigActionPerformed

    private void jMenuCalculadoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuCalculadoraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuCalculadoraActionPerformed

    private void btnSalirPantallaPublicidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirPantallaPublicidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalirPantallaPublicidadActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GeneralPanelForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GeneralPanelForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GeneralPanelForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GeneralPanelForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GeneralPanelForm().setVisible(true);
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanelAuxiliarInterno;
    private javax.swing.JPanel JPanelMenu;
    private javax.swing.JPanel JPanelPiePagina;
    private javax.swing.JPanel JPanelPublicidad;
    private javax.swing.JPanel JpanelAuxiliar;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAyuda;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalirPantallaPublicidad;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuCalculadora;
    private javax.swing.JMenuItem jMenuCliente;
    private javax.swing.JMenuItem jMenuComprobanteConfig;
    private javax.swing.JMenuItem jMenuEmisor;
    private javax.swing.JMenuItem jMenuFactura;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItemMonitor;
    private javax.swing.JMenuItem jMenuItemNotaCredito;
    private javax.swing.JMenuItem jMenuItemUtilidades;
    private javax.swing.JMenuItem jMenuProducto;
    private javax.swing.JMenu jMenuUtilidades;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelPublicidadBorde;
    private javax.swing.JPanel jPanelPublicidadContenido;
    private javax.swing.JTabbedPane jPanelSeleccion;
    private javax.swing.JSplitPane jSplitPanel;
    private javax.swing.JSplitPane jSplitPanelVerticalSecundario;
    private javax.swing.JMenu menuConfiguracion;
    private javax.swing.JMenu menuCrm;
    private javax.swing.JMenu menuFacturacion;
    private javax.swing.JMenu menuInicio;
    // End of variables declaration//GEN-END:variables

    public JMenuItem getjMenuItem1() {
        return jMenuItem1;
    }

    public void setjMenuItem1(JMenuItem jMenuItem1) {
        this.jMenuItem1 = jMenuItem1;
    }

    public JMenuItem getjMenuItem2() {
        return jMenuItem2;
    }

    public void setjMenuItem2(JMenuItem jMenuItem2) {
        this.jMenuItem2 = jMenuItem2;
    }



    public JDesktopPane getjDesktopPane1() {
        return jDesktopPane1;
    }

    public void setjDesktopPane1(JDesktopPane jDesktopPane1) {
        this.jDesktopPane1 = jDesktopPane1;
    }

    public JButton getBtnActualizar() {
        return btnActualizar;
    }

    public JMenuItem getjMenuCliente() {
        return jMenuCliente;
    }

    public void setjMenuCliente(JMenuItem jMenuCliente) {
        this.jMenuCliente = jMenuCliente;
    }

    public JMenuItem getjMenuProducto() {
        return jMenuProducto;
    }

    public void setjMenuProducto(JMenuItem jMenuProducto) {
        this.jMenuProducto = jMenuProducto;
    }
    
    public void setBtnActualizar(JButton btnActualizar) {
        this.btnActualizar = btnActualizar;
    }

    public JButton getBtnAyuda() {
        return btnAyuda;
    }

    public void setBtnAyuda(JButton btnAyuda) {
        this.btnAyuda = btnAyuda;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public void setBtnBuscar(JButton btnBuscar) {
        this.btnBuscar = btnBuscar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public void setBtnEliminar(JButton btnEliminar) {
        this.btnEliminar = btnEliminar;
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public void setBtnGuardar(JButton btnGuardar) {
        this.btnGuardar = btnGuardar;
    }

    public JButton getBtnImprimir() {
        return btnImprimir;
    }

    public void setBtnImprimir(JButton btnImprimir) {
        this.btnImprimir = btnImprimir;
    }

    public JSplitPane getjSplitPanel() {
        return jSplitPanel;
    }

    public void setjSplitPanel(JSplitPane jSplitPanel) {
        this.jSplitPanel = jSplitPanel;
    }

    public JPanel getJpanelAuxiliar() {
        return JpanelAuxiliar;
    }

    public void setJpanelAuxiliar(JPanel JpanelAuxiliar) {
        this.JpanelAuxiliar = JpanelAuxiliar;
    }


    public JSplitPane getjSplitPanelVerticalSecundario() {
        return jSplitPanelVerticalSecundario;
    }

    public void setjSplitPanelVerticalSecundario(JSplitPane jSplitPanelVerticalSecundario) {
        this.jSplitPanelVerticalSecundario = jSplitPanelVerticalSecundario;
    }

    public JPanel getJPanelPublicidad() {
        return JPanelPublicidad;
    }

    public void setJPanelPublicidad(JPanel JPanelPublicidad) {
        this.JPanelPublicidad = JPanelPublicidad;
    }

    public JButton getBtnSalirPantallaPublicidad() {
        return btnSalirPantallaPublicidad;
    }

    public void setBtnSalirPantallaPublicidad(JButton btnSalirPantallaPublicidad) {
        this.btnSalirPantallaPublicidad = btnSalirPantallaPublicidad;
    }

    public JPanel getjPanelPublicidadContenido() {
        return jPanelPublicidadContenido;
    }

    public void setjPanelPublicidadContenido(JPanel jPanelPublicidadContenido) {
        this.jPanelPublicidadContenido = jPanelPublicidadContenido;
    }


    public JButton getBtnNuevo() {
        return btnNuevo;
    }

    public void setBtnNuevo(JButton btnNuevo) {
        this.btnNuevo = btnNuevo;
    }

    public JMenuItem getjMenuFactura() {
        return jMenuFactura;
    }

    public void setjMenuFactura(JMenuItem jMenuFactura) {
        this.jMenuFactura = jMenuFactura;
    }

    public JMenuItem getjMenuEmisor() {
        return jMenuEmisor;
    }

    public void setjMenuEmisor(JMenuItem jMenuEmisor) {
        this.jMenuEmisor = jMenuEmisor;
    }

    public JMenuItem getjMenuComprobanteConfig() {
        return jMenuComprobanteConfig;
    }

    public void setjMenuComprobanteConfig(JMenuItem jMenuComprobanteConfig) {
        this.jMenuComprobanteConfig = jMenuComprobanteConfig;
    }

    public JMenuItem getjMenuCalculadora() {
        return jMenuCalculadora;
    }

    public void setjMenuCalculadora(JMenuItem jMenuCalculadora) {
        this.jMenuCalculadora = jMenuCalculadora;
    }

    public JMenuItem getjMenuItemMonitor() {
        return jMenuItemMonitor;
    }

    public JTabbedPane getjPanelSeleccion() {
        return jPanelSeleccion;
    }

    public JMenuItem getjMenuItemUtilidades() {
        return jMenuItemUtilidades;
    }

    public void setjMenuItemUtilidades(JMenuItem jMenuItemUtilidades) {
        this.jMenuItemUtilidades = jMenuItemUtilidades;
    }

    public JMenuItem getjMenuItemNotaCredito() {
        return jMenuItemNotaCredito;
    }
    
    
        
}
