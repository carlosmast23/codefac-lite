/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobantes;

import ec.com.codesoft.codefaclite.controlador.panel.MonitorComprobantePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazComunicacionPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public class MonitorComprobanteModel extends MonitorComprobantePanel{
    private static MonitorComprobanteModel monitorComprobanteModel;
    private GridLayout experimentLayout = new GridLayout(10,2);
    private MonitorComprobanteListener listener;
    private InterfazComunicacionPanel interfazPadre;
    
    
    public static MonitorComprobanteModel getInstance()
    {
        if(monitorComprobanteModel==null)
            monitorComprobanteModel=new MonitorComprobanteModel();
        
        return monitorComprobanteModel;
    }

    public MonitorComprobanteModel() {
        getjPanelComponentes().setLayout(experimentLayout);
        addListenerButtons();
    }
    
    public MonitorComprobanteData agregarComprobante()
    {
        MonitorComprobanteData monitorComprobanteData=new MonitorComprobanteData();
        JPanel panelComprobante = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        panelComprobante.setLayout(borderLayout);

        panelComprobante.add(monitorComprobanteData.getLblPreimpreso(), BorderLayout.LINE_START);

        panelComprobante.add(monitorComprobanteData.getBarraProgreso(), BorderLayout.CENTER);

        JPanel panelFinal = new JPanel();
        panelFinal.setLayout(new GridLayout(0, 3));


        panelFinal.add(monitorComprobanteData.getBtnReporte());
        panelFinal.add(monitorComprobanteData.getBtnAbrir());
        panelFinal.add(monitorComprobanteData.getBtnCerrar());

        panelComprobante.add(panelFinal, BorderLayout.LINE_END);
        getjPanelComponentes().add(panelComprobante);

        getjPanelComponentes().revalidate();
        getjPanelComponentes().repaint();
        
        
        return monitorComprobanteData;

    }
    
    public static void  mostrar()
    {
        getInstance().listener.mostrar();
    }

    private void addListenerButtons() {
        getBtnLimpiarTodo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                JPanel panelComprobante = new JPanel();
                BorderLayout borderLayout = new BorderLayout();
                panelComprobante.setLayout(borderLayout);
                        
                JLabel lblPreimpreso=new JLabel("001-002-01232132");
                panelComprobante.add(lblPreimpreso,BorderLayout.LINE_START);
                
                JProgressBar barraProgreso=new JProgressBar();
                panelComprobante.add(barraProgreso,BorderLayout.CENTER);
                
                JPanel panelFinal = new JPanel();
                panelFinal.setLayout(new GridLayout(0, 3));
                JButton btnReporte=new JButton("R");
                JButton btnAbrir=new JButton("A");
                JButton btnCerrar=new JButton("Xs");
                
                panelFinal.add(btnReporte);
                panelFinal.add(btnAbrir);
                panelFinal.add(btnCerrar);
                
                panelComprobante.add(panelFinal,BorderLayout.LINE_END);
                getjPanelComponentes().add(panelComprobante);
                
                getjPanelComponentes().revalidate();
                getjPanelComponentes().repaint();                
            }
        });
    }
    
    public void addListener(MonitorComprobanteListener listener)
    {
        this.listener=listener;
    }

    public InterfazComunicacionPanel getInterfazPadre() {
        return interfazPadre;
    }

    public void setInterfazPadre(InterfazComunicacionPanel interfazPadre) {
        this.interfazPadre = interfazPadre;
    }

    
    
    
}
