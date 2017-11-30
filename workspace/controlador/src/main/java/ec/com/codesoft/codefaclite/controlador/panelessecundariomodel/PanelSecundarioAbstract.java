/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.panelessecundariomodel;

/**
 *
 * @author Carlos
 */
public abstract class PanelSecundarioAbstract extends javax.swing.JPanel {
    public static final String PANEL_AYUDA="Ayuda";
    public static final String PANEL_MONITOR="Monitor";
    private PanelSecundarioListener listener;
    
    public abstract String getNombrePanel();
    
    public void addListenerPanelSecundario(PanelSecundarioListener listener)
    {
        this.listener=listener;
    }
    
    public void mostrar()
    {
        this.listener.mostrar();
    }
    

}
