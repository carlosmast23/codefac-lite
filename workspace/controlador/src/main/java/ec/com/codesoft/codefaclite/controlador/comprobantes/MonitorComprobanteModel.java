/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobantes;

import com.sun.org.apache.bcel.internal.generic.D2F;
import ec.com.codesoft.codefaclite.controlador.panelsecundario.MonitorComprobantePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public class MonitorComprobanteModel extends MonitorComprobantePanel {

    private static MonitorComprobanteModel monitorComprobanteModel;
    private GridLayout experimentLayout = new GridLayout(15, 2);
    private MonitorComprobanteListener listener;
    private static  Integer numeroVentanas=0;
    //private InterfazComunicacionPanel interfazPadre;

    public static MonitorComprobanteModel getInstance() {
        if (monitorComprobanteModel == null) {
            monitorComprobanteModel = new MonitorComprobanteModel();
        }
        UIManager.put("ProgressBar.selectionForeground", Color.black);
        return monitorComprobanteModel;
    }

    public MonitorComprobanteModel() {
        getjPanelComponentes().setLayout(experimentLayout);

    }

    public MonitorComprobanteData agregarComprobante() {
        MonitorComprobanteData monitorComprobanteData = new MonitorComprobanteData(numeroVentanas++);
        JPanel panelComprobante = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        panelComprobante.setLayout(borderLayout);

        //panelComprobante.add(monitorComprobanteData.getLblPreimpreso(), BorderLayout.LINE_START);

        panelComprobante.add(monitorComprobanteData.getBarraProgreso(), BorderLayout.CENTER);

        JPanel panelFinal = new JPanel();
        panelFinal.setLayout(new GridLayout(0, 3));

        panelFinal.add(monitorComprobanteData.getBtnReporte());
        panelFinal.add(monitorComprobanteData.getBtnAbrir());
        panelFinal.add(monitorComprobanteData.getBtnCerrar());

        panelComprobante.add(panelFinal, BorderLayout.LINE_END);
        getjPanelComponentes().add(panelComprobante);

        setproperties(monitorComprobanteData);

        getjPanelComponentes().revalidate();
        getjPanelComponentes().repaint();
        
        monitorComprobanteData.getBtnCerrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getjPanelComponentes().remove(monitorComprobanteData.getPosicion());
                numeroVentanas--;
                getjPanelComponentes().revalidate();
                getjPanelComponentes().repaint();
                System.out.println("eliminar la pantalla");
            }
        });

        return monitorComprobanteData;

    }


    public void addListener(MonitorComprobanteListener listener) {
        this.listener = listener;
    }

    private void setproperties(MonitorComprobanteData monitorComprobanteData) {
        monitorComprobanteData.getBtnCerrar().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourcePath("pequenos/cerrar-ico.png")));
        monitorComprobanteData.getBtnCerrar().setText("");
        monitorComprobanteData.getBtnCerrar().setToolTipText("Cerrar");
        monitorComprobanteData.getBtnCerrar().setSize(new Dimension(25,25));
        
        monitorComprobanteData.getBtnAbrir().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourcePath("pequenos/abrir-ico.png")));
        monitorComprobanteData.getBtnAbrir().setText("");
        monitorComprobanteData.getBtnAbrir().setToolTipText("Abrir");
        monitorComprobanteData.getBtnAbrir().setSize(new Dimension(1,1));
        
        monitorComprobanteData.getBtnReporte().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourcePath("pequenos/info-ico.png")));
        monitorComprobanteData.getBtnReporte().setText("");
        monitorComprobanteData.getBtnReporte().setToolTipText("Ver detalles");
        monitorComprobanteData.getBtnReporte().setPreferredSize(new Dimension(15,15));
        
        
        
    }

}
