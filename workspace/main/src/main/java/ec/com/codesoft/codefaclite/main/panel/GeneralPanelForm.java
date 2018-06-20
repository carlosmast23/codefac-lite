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
import javax.swing.JLabel;
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
        btnHome = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnAyuda = new javax.swing.JButton();
        JPanelPiePagina = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblTextoSecundario = new javax.swing.JLabel();
        lblPiePagina = new javax.swing.JLabel();
        lblNombreEmpresa = new javax.swing.JLabel();
        jSplitPanel = new javax.swing.JSplitPane();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuInicio = new javax.swing.JMenu();
        jMenuItemInicio = new javax.swing.JMenuItem();
        jMenuItemSalir = new javax.swing.JMenuItem();
        jMenuUtilidades = new javax.swing.JMenu();
        jMenuCalculadora = new javax.swing.JMenuItem();
        jMenuAyuda = new javax.swing.JMenu();
        jMenuItemContenido = new javax.swing.JMenuItem();
        jMenuItemAcerca = new javax.swing.JMenuItem();
        jMenuItemActualizarLicencia = new javax.swing.JMenuItem();

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

        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/home.png"))); // NOI18N
        btnHome.setToolTipText("Ir al menu principal");
        btnHome.setMargin(new java.awt.Insets(0, 14, 0, 14));
        JPanelMenu.add(btnHome);

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
        JPanelPiePagina.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(62, 93, 162));

        lblTextoSecundario.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblTextoSecundario.setForeground(new java.awt.Color(255, 255, 255));
        lblTextoSecundario.setText("Licencia:Gratis");
        jPanel1.add(lblTextoSecundario);

        JPanelPiePagina.add(jPanel1, java.awt.BorderLayout.LINE_END);

        lblPiePagina.setForeground(new java.awt.Color(255, 255, 255));
        lblPiePagina.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPiePagina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img.general/letraCodefac.png"))); // NOI18N
        lblPiePagina.setText("Todos los derechos reservador por @Codesoft 2017       ");
        JPanelPiePagina.add(lblPiePagina, java.awt.BorderLayout.CENTER);

        lblNombreEmpresa.setBackground(new java.awt.Color(255, 255, 255));
        lblNombreEmpresa.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblNombreEmpresa.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreEmpresa.setText("   Empresa: Codesoft");
        JPanelPiePagina.add(lblNombreEmpresa, java.awt.BorderLayout.LINE_START);

        getContentPane().add(JPanelPiePagina, java.awt.BorderLayout.PAGE_END);

        jSplitPanel.setDividerLocation(800);
        jSplitPanel.setDividerSize(3);
        jSplitPanel.setDoubleBuffered(true);
        jSplitPanel.setLeftComponent(jDesktopPane1);

        getContentPane().add(jSplitPanel, java.awt.BorderLayout.CENTER);

        jMenuInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/modulos/menu.png"))); // NOI18N
        jMenuInicio.setToolTipText("Principal");

        jMenuItemInicio.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jMenuItemInicio.setText("Iinicio");
        jMenuItemInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemInicioActionPerformed(evt);
            }
        });
        jMenuInicio.add(jMenuItemInicio);

        jMenuItemSalir.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jMenuItemSalir.setText("Salir");
        jMenuInicio.add(jMenuItemSalir);

        jMenuBar1.add(jMenuInicio);

        jMenuUtilidades.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/modulos/utilidades.png"))); // NOI18N
        jMenuUtilidades.setToolTipText("Herramientas");

        jMenuCalculadora.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jMenuCalculadora.setText("Calculadora");
        jMenuCalculadora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuCalculadoraActionPerformed(evt);
            }
        });
        jMenuUtilidades.add(jMenuCalculadora);

        jMenuBar1.add(jMenuUtilidades);

        jMenuAyuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/modulos/ayuda.png"))); // NOI18N
        jMenuAyuda.setText("Ayuda");
        jMenuAyuda.setFont(new java.awt.Font("Arial", 2, 15)); // NOI18N

        jMenuItemContenido.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jMenuItemContenido.setText("Contenido");
        jMenuItemContenido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemContenidoActionPerformed(evt);
            }
        });
        jMenuAyuda.add(jMenuItemContenido);

        jMenuItemAcerca.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jMenuItemAcerca.setText("Acerca");
        jMenuAyuda.add(jMenuItemAcerca);

        jMenuItemActualizarLicencia.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        jMenuItemActualizarLicencia.setText("Actualizar Licencia");
        jMenuItemActualizarLicencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemActualizarLicenciaActionPerformed(evt);
            }
        });
        jMenuAyuda.add(jMenuItemActualizarLicencia);

        jMenuBar1.add(jMenuAyuda);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        //ClassLoader classLoader = RecursoCodefac.class.getClassLoader();
        //System.out.println(classLoader.getResource("img/iconos/save-icon.png").getFile());
        //btnBuscar.setIcon(new ImageIcon(classLoader.getResource("img/iconos/save-icon.png").getFile()));
       
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jMenuItemInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemInicioActionPerformed
        
    }//GEN-LAST:event_jMenuItemInicioActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        //AyudaForm ayuda=new AyudaForm();
       
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void jMenuCalculadoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuCalculadoraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuCalculadoraActionPerformed

    private void btnSalirPantallaPublicidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirPantallaPublicidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalirPantallaPublicidadActionPerformed

    private void jMenuItemContenidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemContenidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemContenidoActionPerformed

    private void jMenuItemActualizarLicenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemActualizarLicenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemActualizarLicenciaActionPerformed


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
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalirPantallaPublicidad;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenuAyuda;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuCalculadora;
    private javax.swing.JMenu jMenuInicio;
    private javax.swing.JMenuItem jMenuItemAcerca;
    private javax.swing.JMenuItem jMenuItemActualizarLicencia;
    private javax.swing.JMenuItem jMenuItemContenido;
    private javax.swing.JMenuItem jMenuItemInicio;
    private javax.swing.JMenuItem jMenuItemSalir;
    private javax.swing.JMenu jMenuUtilidades;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelPublicidadBorde;
    private javax.swing.JPanel jPanelPublicidadContenido;
    private javax.swing.JTabbedPane jPanelSeleccion;
    private javax.swing.JSplitPane jSplitPanel;
    private javax.swing.JSplitPane jSplitPanelVerticalSecundario;
    private javax.swing.JLabel lblNombreEmpresa;
    private javax.swing.JLabel lblPiePagina;
    private javax.swing.JLabel lblTextoSecundario;
    // End of variables declaration//GEN-END:variables

    public JMenuItem getjMenuItem1() {
        return jMenuItemInicio;
    }

    public void setjMenuItem1(JMenuItem jMenuItem1) {
        this.jMenuItemInicio = jMenuItem1;
    }

    public JMenuItem getjMenuItem2() {
        return jMenuItemSalir;
    }

    public void setjMenuItem2(JMenuItem jMenuItem2) {
        this.jMenuItemSalir = jMenuItem2;
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

    public JMenuItem getjMenuCalculadora() {
        return jMenuCalculadora;
    }

    public void setjMenuCalculadora(JMenuItem jMenuCalculadora) {
        this.jMenuCalculadora = jMenuCalculadora;
    }


    public JTabbedPane getjPanelSeleccion() {
        return jPanelSeleccion;
    }


    public JMenuItem getjMenuItemInicio() {
        return jMenuItemInicio;
    }

    public void setjMenuItemInicio(JMenuItem jMenuItemInicio) {
        this.jMenuItemInicio = jMenuItemInicio;
    }

    public JMenuItem getjMenuItemSalir() {
        return jMenuItemSalir;
    }

    public void setjMenuItemSalir(JMenuItem jMenuItemSalir) {
        this.jMenuItemSalir = jMenuItemSalir;
    }

    public JMenuItem getjMenuItemAcerca() {
        return jMenuItemAcerca;
    }

    public void setjMenuItemAcerca(JMenuItem jMenuItemAcerca) {
        this.jMenuItemAcerca = jMenuItemAcerca;
    }

    public JMenuItem getjMenuItemContenido() {
        return jMenuItemContenido;
    }

    public void setjMenuItemContenido(JMenuItem jMenuItemContenido) {
        this.jMenuItemContenido = jMenuItemContenido;
    }

    public JButton getBtnHome() {
        return btnHome;
    }

    public void setBtnHome(JButton btnHome) {
        this.btnHome = btnHome;
    }

    public JMenuItem getjMenuItemActualizarLicencia() {
        return jMenuItemActualizarLicencia;
    }

    public void setjMenuItemActualizarLicencia(JMenuItem jMenuItemActualizarLicencia) {
        this.jMenuItemActualizarLicencia = jMenuItemActualizarLicencia;
    }

    public JMenu getjMenuAyuda() {
        return jMenuAyuda;
    }

    public void setjMenuAyuda(JMenu jMenuAyuda) {
        this.jMenuAyuda = jMenuAyuda;
    }

    public JMenu getjMenuInicio() {
        return jMenuInicio;
    }

    public void setjMenuInicio(JMenu jMenuInicio) {
        this.jMenuInicio = jMenuInicio;
    }

    public JMenu getjMenuUtilidades() {
        return jMenuUtilidades;
    }

    public void setjMenuUtilidades(JMenu jMenuUtilidades) {
        this.jMenuUtilidades = jMenuUtilidades;
    }

    public JLabel getLblNombreEmpresa() {
        return lblNombreEmpresa;
    }

    public void setLblNombreEmpresa(JLabel lblNombreEmpresa) {
        this.lblNombreEmpresa = lblNombreEmpresa;
    }

    public JLabel getLblTextoSecundario() {
        return lblTextoSecundario;
    }

    public void setLblTextoSecundario(JLabel lblTextoSecundario) {
        this.lblTextoSecundario = lblTextoSecundario;
    }

    public JLabel getLblPiePagina() {
        return lblPiePagina;
    }

    public void setLblPiePagina(JLabel lblPiePagina) {
        this.lblPiePagina = lblPiePagina;
    }

    
    
    
}