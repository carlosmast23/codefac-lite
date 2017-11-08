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
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
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

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        JpanelAuxiliar = new javax.swing.JPanel();
        jSplitPanelVerticalSecundario = new javax.swing.JSplitPane();
        JPanelPublicidad = new javax.swing.JPanel();
        jPanelPublicidadContenido = new javax.swing.JPanel();
        jPanelPublicidadBorde = new javax.swing.JPanel();
        btnSalirPantallaPublicidad = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        JPanelAuxiliarInterno = new javax.swing.JPanel();
        JPanelContenidoAuxiliar = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnSalirPantallAuxiliar = new javax.swing.JButton();
        lblTituloContenedorAuxiliar = new javax.swing.JLabel();
        JPanelConsola = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablaConsola = new javax.swing.JTable();
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
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuCliente = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        jMenuItem3.setText("jMenuItem3");

        jMenu5.setText("File");
        jMenuBar3.add(jMenu5);

        jMenu6.setText("Edit");
        jMenuBar3.add(jMenu6);

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

        JPanelContenidoAuxiliar.setBackground(new java.awt.Color(255, 255, 255));
        JPanelContenidoAuxiliar.setLayout(new javax.swing.BoxLayout(JPanelContenidoAuxiliar, javax.swing.BoxLayout.LINE_AXIS));
        JPanelAuxiliarInterno.add(JPanelContenidoAuxiliar, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(62, 93, 162));
        jPanel2.setLayout(new java.awt.BorderLayout());

        btnSalirPantallAuxiliar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/salir-ico.png"))); // NOI18N
        btnSalirPantallAuxiliar.setMargin(new java.awt.Insets(2, 5, 2, 5));
        btnSalirPantallAuxiliar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirPantallAuxiliarActionPerformed(evt);
            }
        });
        jPanel2.add(btnSalirPantallAuxiliar, java.awt.BorderLayout.LINE_END);

        lblTituloContenedorAuxiliar.setBackground(new java.awt.Color(255, 255, 255));
        lblTituloContenedorAuxiliar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTituloContenedorAuxiliar.setForeground(new java.awt.Color(255, 255, 255));
        lblTituloContenedorAuxiliar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTituloContenedorAuxiliar.setText("Pantalla de Ayuda");
        jPanel2.add(lblTituloContenedorAuxiliar, java.awt.BorderLayout.CENTER);

        JPanelAuxiliarInterno.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jSplitPanelVerticalSecundario.setLeftComponent(JPanelAuxiliarInterno);

        JpanelAuxiliar.add(jSplitPanelVerticalSecundario);

        JPanelConsola.setLayout(new java.awt.BorderLayout());

        jTablaConsola.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTablaConsola);

        JPanelConsola.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(800, 600));
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

        jMenu1.setText("Inicio");

        jMenuItem1.setText("Iinicio");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Salir");
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Crm");

        jMenuCliente.setText("Clientes");
        jMenuCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuClienteActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuCliente);

        jMenuBar1.add(jMenu2);

        jMenu7.setText("Facturacion");
        jMenuBar1.add(jMenu7);

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

    private void btnSalirPantallAuxiliarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirPantallAuxiliarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalirPantallAuxiliarActionPerformed

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
    private javax.swing.JPanel JPanelConsola;
    private javax.swing.JPanel JPanelContenidoAuxiliar;
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
    private javax.swing.JButton btnSalirPantallAuxiliar;
    private javax.swing.JButton btnSalirPantallaPublicidad;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuItem jMenuCliente;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelPublicidadBorde;
    private javax.swing.JPanel jPanelPublicidadContenido;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPanel;
    private javax.swing.JSplitPane jSplitPanelVerticalSecundario;
    private javax.swing.JTable jTablaConsola;
    private javax.swing.JLabel lblTituloContenedorAuxiliar;
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

    public JMenuItem getjMenuItem3() {
        return jMenuItem3;
    }

    public void setjMenuItem3(JMenuItem jMenuItem3) {
        this.jMenuItem3 = jMenuItem3;
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

    public JPanel getJPanelContenidoAuxiliar() {
        return JPanelContenidoAuxiliar;
    }

    public void setJPanelContenidoAuxiliar(JPanel JPanelContenidoAuxiliar) {
        this.JPanelContenidoAuxiliar = JPanelContenidoAuxiliar;
    }

    public JButton getBtnSalirPantallAuxiliar() {
        return btnSalirPantallAuxiliar;
    }

    public void setBtnSalirPantallAuxiliar(JButton btnSalirPantallAuxiliar) {
        this.btnSalirPantallAuxiliar = btnSalirPantallAuxiliar;
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

    public JTable getjTablaConsola() {
        return jTablaConsola;
    }

    public void setjTablaConsola(JTable jTablaConsola) {
        this.jTablaConsola = jTablaConsola;
    }

    public JPanel getJPanelConsola() {
        return JPanelConsola;
    }

    public JButton getBtnNuevo() {
        return btnNuevo;
    }

    public void setBtnNuevo(JButton btnNuevo) {
        this.btnNuevo = btnNuevo;
    }
    
    
    
}
