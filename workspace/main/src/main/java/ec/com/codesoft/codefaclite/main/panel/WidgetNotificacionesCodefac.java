/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.panel;

import ec.com.codesoft.codefaclite.main.model.ObjetoEscritorioAbstract;
import javax.swing.JDesktopPane;

/**
 *
 * @author Carlos
 */
public abstract class WidgetNotificacionesCodefac extends ObjetoEscritorioAbstract {

    public WidgetNotificacionesCodefac(JDesktopPane parentPanel) {
        super(parentPanel);
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
        java.awt.GridBagConstraints gridBagConstraints;

        panelTitulo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        panelTitulo.setBackground(new java.awt.Color(40, 161, 207));
        panelTitulo.setForeground(new java.awt.Color(255, 255, 51));
        panelTitulo.setLayout(new javax.swing.BoxLayout(panelTitulo, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Notificaciones Codefac");
        panelTitulo.add(jLabel1);

        add(panelTitulo, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jButton1.setText("Ejemplo de widget");
        jPanel2.add(jButton1, new java.awt.GridBagConstraints());

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelTitulo;
    // End of variables declaration//GEN-END:variables
}
