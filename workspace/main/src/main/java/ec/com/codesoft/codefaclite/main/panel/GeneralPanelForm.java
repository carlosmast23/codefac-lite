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
        jLabel1 = new javax.swing.JLabel();
        lblPiePagina = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSplitPanel = new javax.swing.JSplitPane();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuInicio = new javax.swing.JMenu();
        jMenuItemInicio = new javax.swing.JMenuItem();
        jMenuItemSalir = new javax.swing.JMenuItem();
        menuFacturacion = new javax.swing.JMenu();
        jMenuFactura = new javax.swing.JMenuItem();
        jMenuItemMonitor = new javax.swing.JMenuItem();
        jMenuItemNotaCredito = new javax.swing.JMenuItem();
        jMenuItemFacturaReporte = new javax.swing.JMenuItem();
        jMenuItemDisenador = new javax.swing.JMenuItem();
        menuCompras = new javax.swing.JMenu();
        jMenuCompra = new javax.swing.JMenuItem();
        menuInventario = new javax.swing.JMenu();
        jMenuItemAsociarProducto = new javax.swing.JMenuItem();
        jMenuItemIngresarInventario = new javax.swing.JMenuItem();
        menuBodega = new javax.swing.JMenuItem();
        jMenuItemKardex = new javax.swing.JMenuItem();
        jMenuItemInventarioEnsamble = new javax.swing.JMenuItem();
        menuCatProducto = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuCliente = new javax.swing.JMenuItem();
        jMenuItemReporteCliente = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuProducto = new javax.swing.JMenuItem();
        jMenuItemReporteProducto = new javax.swing.JMenuItem();
        menuAcademico = new javax.swing.JMenu();
        menuEstudiantes = new javax.swing.JMenuItem();
        menuAula = new javax.swing.JMenuItem();
        menuNivel = new javax.swing.JMenuItem();
        menuNivelAcademico = new javax.swing.JMenuItem();
        menuRubros = new javax.swing.JMenu();
        menuPeriodos = new javax.swing.JMenu();
        menuConfiguracion = new javax.swing.JMenu();
        jMenuComprobanteConfig = new javax.swing.JMenuItem();
        jMenuEmisor = new javax.swing.JMenuItem();
        jMenuItemUtilidades = new javax.swing.JMenuItem();
        jMenuUtilidades = new javax.swing.JMenu();
        jMenuCalculadora = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
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

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Licencia:Gratis");
        jPanel1.add(jLabel1);

        JPanelPiePagina.add(jPanel1, java.awt.BorderLayout.LINE_END);

        lblPiePagina.setForeground(new java.awt.Color(255, 255, 255));
        lblPiePagina.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPiePagina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img.general/letraCodefac.png"))); // NOI18N
        lblPiePagina.setText("Todos los derechos reservador por @Codesoft 2017       ");
        JPanelPiePagina.add(lblPiePagina, java.awt.BorderLayout.CENTER);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("   Empresa: Codesoft");
        JPanelPiePagina.add(jLabel2, java.awt.BorderLayout.LINE_START);

        getContentPane().add(JPanelPiePagina, java.awt.BorderLayout.PAGE_END);

        jSplitPanel.setDividerLocation(800);
        jSplitPanel.setDividerSize(3);
        jSplitPanel.setDoubleBuffered(true);
        jSplitPanel.setLeftComponent(jDesktopPane1);

        getContentPane().add(jSplitPanel, java.awt.BorderLayout.CENTER);

        menuInicio.setText("Inicio");

        jMenuItemInicio.setText("Iinicio");
        jMenuItemInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemInicioActionPerformed(evt);
            }
        });
        menuInicio.add(jMenuItemInicio);

        jMenuItemSalir.setText("Salir");
        menuInicio.add(jMenuItemSalir);

        jMenuBar1.add(menuInicio);

        menuFacturacion.setText("Facturacion");

        jMenuFactura.setText("Factura");
        menuFacturacion.add(jMenuFactura);

        jMenuItemMonitor.setText("Monitor");
        menuFacturacion.add(jMenuItemMonitor);

        jMenuItemNotaCredito.setText("Nota Credito");
        menuFacturacion.add(jMenuItemNotaCredito);

        jMenuItemFacturaReporte.setText("Reporte");
        jMenuItemFacturaReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFacturaReporteActionPerformed(evt);
            }
        });
        menuFacturacion.add(jMenuItemFacturaReporte);

        jMenuItemDisenador.setText("Diseñador");
        jMenuItemDisenador.setToolTipText("Pantalla para diseñar formatos de facturas fisicas");
        menuFacturacion.add(jMenuItemDisenador);

        jMenuBar1.add(menuFacturacion);

        menuCompras.setText("Compras");

        jMenuCompra.setText("Compra");
        menuCompras.add(jMenuCompra);

        jMenuBar1.add(menuCompras);

        menuInventario.setText("Inventario");

        jMenuItemAsociarProducto.setText("Asociar producto");
        jMenuItemAsociarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAsociarProductoActionPerformed(evt);
            }
        });
        menuInventario.add(jMenuItemAsociarProducto);

        jMenuItemIngresarInventario.setText("Ingresar Inventario");
        menuInventario.add(jMenuItemIngresarInventario);

        menuBodega.setText("Bodega");
        menuBodega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBodegaActionPerformed(evt);
            }
        });
        menuInventario.add(menuBodega);

        jMenuItemKardex.setText("Kardex");
        menuInventario.add(jMenuItemKardex);

        jMenuItemInventarioEnsamble.setText("Inventario Ensamble");
        menuInventario.add(jMenuItemInventarioEnsamble);

        menuCatProducto.setText("Categoria Producto");
        menuInventario.add(menuCatProducto);

        jMenuBar1.add(menuInventario);

        jMenu2.setText("Clientes");

        jMenuCliente.setText("Gestionar");
        jMenuCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuClienteActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuCliente);

        jMenuItemReporteCliente.setText("Reporte");
        jMenu2.add(jMenuItemReporteCliente);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Productos");

        jMenuProducto.setText("Gestionar");
        jMenu3.add(jMenuProducto);

        jMenuItemReporteProducto.setText("Reporte");
        jMenu3.add(jMenuItemReporteProducto);

        jMenuBar1.add(jMenu3);

        menuAcademico.setText("Academico");

        menuEstudiantes.setText("Estudiantes");
        menuAcademico.add(menuEstudiantes);

        menuAula.setText("Aula");
        menuAula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAulaActionPerformed(evt);
            }
        });
        menuAcademico.add(menuAula);

        menuNivel.setText("Nivel");
        menuAcademico.add(menuNivel);

        menuNivelAcademico.setText("Nivel Academico");
        menuAcademico.add(menuNivelAcademico);

        menuRubros.setText("Rubros");
        menuAcademico.add(menuRubros);

        menuPeriodos.setText("Periodos");
        menuAcademico.add(menuPeriodos);

        jMenuBar1.add(menuAcademico);

        menuConfiguracion.setText("Configuraciones");

        jMenuComprobanteConfig.setText("Comprobante");
        jMenuComprobanteConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuComprobanteConfigActionPerformed(evt);
            }
        });
        menuConfiguracion.add(jMenuComprobanteConfig);

        jMenuEmisor.setText("Empresa");
        menuConfiguracion.add(jMenuEmisor);

        jMenuItemUtilidades.setText("Utilidades");
        menuConfiguracion.add(jMenuItemUtilidades);

        jMenuBar1.add(menuConfiguracion);

        jMenuUtilidades.setText("Utilidades");

        jMenuCalculadora.setText("Calculadora");
        jMenuCalculadora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuCalculadoraActionPerformed(evt);
            }
        });
        jMenuUtilidades.add(jMenuCalculadora);

        jMenuBar1.add(jMenuUtilidades);

        jMenu8.setText("Ayuda");

        jMenuItemContenido.setText("Contenido");
        jMenuItemContenido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemContenidoActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItemContenido);

        jMenuItemAcerca.setText("Acerca");
        jMenu8.add(jMenuItemAcerca);

        jMenuItemActualizarLicencia.setText("Actualizar Licencia");
        jMenuItemActualizarLicencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemActualizarLicenciaActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItemActualizarLicencia);

        jMenuBar1.add(jMenu8);

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

    private void jMenuItemFacturaReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemFacturaReporteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemFacturaReporteActionPerformed

    private void jMenuItemContenidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemContenidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemContenidoActionPerformed

    private void jMenuItemActualizarLicenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemActualizarLicenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemActualizarLicenciaActionPerformed

    private void jMenuItemAsociarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAsociarProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemAsociarProductoActionPerformed

    private void menuBodegaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBodegaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuBodegaActionPerformed

    private void menuAulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuAulaActionPerformed

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuCalculadora;
    private javax.swing.JMenuItem jMenuCliente;
    private javax.swing.JMenuItem jMenuCompra;
    private javax.swing.JMenuItem jMenuComprobanteConfig;
    private javax.swing.JMenuItem jMenuEmisor;
    private javax.swing.JMenuItem jMenuFactura;
    private javax.swing.JMenuItem jMenuItemAcerca;
    private javax.swing.JMenuItem jMenuItemActualizarLicencia;
    private javax.swing.JMenuItem jMenuItemAsociarProducto;
    private javax.swing.JMenuItem jMenuItemContenido;
    private javax.swing.JMenuItem jMenuItemDisenador;
    private javax.swing.JMenuItem jMenuItemFacturaReporte;
    private javax.swing.JMenuItem jMenuItemIngresarInventario;
    private javax.swing.JMenuItem jMenuItemInicio;
    private javax.swing.JMenuItem jMenuItemInventarioEnsamble;
    private javax.swing.JMenuItem jMenuItemKardex;
    private javax.swing.JMenuItem jMenuItemMonitor;
    private javax.swing.JMenuItem jMenuItemNotaCredito;
    private javax.swing.JMenuItem jMenuItemReporteCliente;
    private javax.swing.JMenuItem jMenuItemReporteProducto;
    private javax.swing.JMenuItem jMenuItemSalir;
    private javax.swing.JMenuItem jMenuItemUtilidades;
    private javax.swing.JMenuItem jMenuProducto;
    private javax.swing.JMenu jMenuUtilidades;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelPublicidadBorde;
    private javax.swing.JPanel jPanelPublicidadContenido;
    private javax.swing.JTabbedPane jPanelSeleccion;
    private javax.swing.JSplitPane jSplitPanel;
    private javax.swing.JSplitPane jSplitPanelVerticalSecundario;
    private javax.swing.JLabel lblPiePagina;
    private javax.swing.JMenu menuAcademico;
    private javax.swing.JMenuItem menuAula;
    private javax.swing.JMenuItem menuBodega;
    private javax.swing.JMenuItem menuCatProducto;
    private javax.swing.JMenu menuCompras;
    private javax.swing.JMenu menuConfiguracion;
    private javax.swing.JMenuItem menuEstudiantes;
    private javax.swing.JMenu menuFacturacion;
    private javax.swing.JMenu menuInicio;
    private javax.swing.JMenu menuInventario;
    private javax.swing.JMenuItem menuNivel;
    private javax.swing.JMenuItem menuNivelAcademico;
    private javax.swing.JMenu menuPeriodos;
    private javax.swing.JMenu menuRubros;
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

    public JMenuItem getjMenuItemFacturaReporte() {
        return jMenuItemFacturaReporte;
    }

    public void setjMenuItemFacturaReporte(JMenuItem jMenuItemFacturaReporte) {
        this.jMenuItemFacturaReporte = jMenuItemFacturaReporte;
    }

    public JMenuItem getjMenuItemReporteCliente() {
        return jMenuItemReporteCliente;
    }

    public void setjMenuItemReporteCliente(JMenuItem jMenuItemReporteCliente) {
        this.jMenuItemReporteCliente = jMenuItemReporteCliente;
    }

    public JMenuItem getjMenuItemReporteProducto() {
        return jMenuItemReporteProducto;
    }

    public void setjMenuItemReporteProducto(JMenuItem jMenuItemReporteProducto) {
        this.jMenuItemReporteProducto = jMenuItemReporteProducto;
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

    public JMenuItem getjMenuItemDisenador() {
        return jMenuItemDisenador;
    }

    public void setjMenuItemDisenador(JMenuItem jMenuItemDisenador) {
        this.jMenuItemDisenador = jMenuItemDisenador;
    }

    public JMenuItem getjMenuCompra() {
        return jMenuCompra;
    }

    public void setjMenuCompra(JMenuItem jMenuCompra) {
        this.jMenuCompra = jMenuCompra;
    }

    public JMenuItem getjMenuItemAsociarProducto() {
        return jMenuItemAsociarProducto;
    }

    public void setjMenuItemAsociarProducto(JMenuItem jMenuItemAsociarProducto) {
        this.jMenuItemAsociarProducto = jMenuItemAsociarProducto;
    }

    public JMenuItem getMenuBodega() {
        return menuBodega;
    }

    public void setMenuBodega(JMenuItem menuBodega) {
        this.menuBodega = menuBodega;
    }

    public JMenuItem getjMenuItemIngresarInventario() {
        return jMenuItemIngresarInventario;
    }

    public void setjMenuItemIngresarInventario(JMenuItem jMenuItemIngresarInventario) {
        this.jMenuItemIngresarInventario = jMenuItemIngresarInventario;
    }

    public JMenuItem getjMenuItemKardex() {
        return jMenuItemKardex;
    }

    public void setjMenuItemKardex(JMenuItem jMenuItemKardex) {
        this.jMenuItemKardex = jMenuItemKardex;
    }

    public JMenuItem getjMenuItemInventarioEnsamble() {
        return jMenuItemInventarioEnsamble;
    }

    public void setjMenuItemInventarioEnsamble(JMenuItem jMenuItemInventarioEnsamble) {
        this.jMenuItemInventarioEnsamble = jMenuItemInventarioEnsamble;
    }

    public JMenuItem getMenuCatProducto() {
        return menuCatProducto;
    }

    public void setMenuCatProducto(JMenuItem menuCatProducto) {
        this.menuCatProducto = menuCatProducto;
    }

    public JMenuItem getMenuAula() {
        return menuAula;
    }

    public void setMenuAula(JMenuItem menuAula) {
        this.menuAula = menuAula;
    }

    public JMenuItem getMenuEstudiantes() {
        return menuEstudiantes;
    }

    public void setMenuEstudiantes(JMenuItem menuEstudiantes) {
        this.menuEstudiantes = menuEstudiantes;
    }

    public JMenuItem getMenuNivel() {
        return menuNivel;
    }

    public void setMenuNivel(JMenuItem menuNivel) {
        this.menuNivel = menuNivel;
    }

    public JMenuItem getMenuNivelAcademico() {
        return menuNivelAcademico;
    }

    public void setMenuNivelAcademico(JMenuItem menuNivelAcademico) {
        this.menuNivelAcademico = menuNivelAcademico;
    }
    
    
    
        
    
    
        
}