/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.panel;

import ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.PanelSecundarioAbstract;

/**
 *
 * @author Carlos
 */
public abstract class AyudaCodefacPanel extends PanelSecundarioAbstract{

    /**
     * Creates new form AyudaCodefacPanel
     */
    public AyudaCodefacPanel() {
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

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public String getNombrePanel() {
        return PANEL_AYUDA;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
