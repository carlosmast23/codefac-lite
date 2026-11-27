/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobantes;

import ec.com.codesoft.codefaclite.controlador.panel.MonitorComprobantesPanel;
import ec.com.codesoft.codefaclite.controlador.core.swing.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.UIManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public class MonitorComprobanteModel extends MonitorComprobantesPanel {

    private static MonitorComprobanteModel monitorComprobanteModel;
    
    private GridLayout experimentLayout = new GridLayout(15, 2);
    private MonitorComprobanteListener listener;
    private static  Integer numeroVentanas=0;
    //private InterfazComunicacionPanel interfazPadre;

    //Tiempo que se espera, una vez que TODO termino bien (sin errores pendientes), antes de ocultar
    //el panel solo -ver notificarComprobanteTerminado()-. Si en ese lapso se agrega un comprobante
    //nuevo o alguno queda con error, se cancela el auto-ocultado.
    private static final int MILISEGUNDOS_ESPERA_AUTO_OCULTAR = 3000;
    private Timer timerAutoOcultar;

    public static MonitorComprobanteModel getInstance() {
        if (monitorComprobanteModel == null) {
            monitorComprobanteModel = new MonitorComprobanteModel();
        }
        UIManager.put("ProgressBar.selectionForeground", Color.black);
        return monitorComprobanteModel;
    }

    public MonitorComprobanteModel() {
        getjPanelComponentesCarga().setLayout(experimentLayout);
        agregarListener();
        

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
        getjPanelComponentesCarga().add(panelComprobante);

        setproperties(monitorComprobanteData);

        getjPanelComponentesCarga().revalidate();
        getjPanelComponentesCarga().repaint();
        
        monitorComprobanteData.getBtnCerrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getjPanelComponentesCarga().remove(monitorComprobanteData.getPosicion());
                numeroVentanas--;
                getjPanelComponentesCarga().revalidate();
                getjPanelComponentesCarga().repaint();
                System.out.println("eliminar la pantalla");
            }
        });

        return monitorComprobanteData;

    }


    public void addListener(MonitorComprobanteListener listener) {
        this.listener = listener;
    }

    private void setproperties(MonitorComprobanteData monitorComprobanteData) {
        monitorComprobanteData.getBtnCerrar().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("pequenos/cerrar-ico.png")));
        monitorComprobanteData.getBtnCerrar().setText("");
        monitorComprobanteData.getBtnCerrar().setToolTipText("Cerrar");
        monitorComprobanteData.getBtnCerrar().setSize(new Dimension(25,25));
        
        monitorComprobanteData.getBtnAbrir().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("pequenos/abrir-ico.png")));
        monitorComprobanteData.getBtnAbrir().setText("");
        monitorComprobanteData.getBtnAbrir().setToolTipText("Abrir");
        monitorComprobanteData.getBtnAbrir().setSize(new Dimension(1,1));
        
        monitorComprobanteData.getBtnReporte().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("pequenos/info-ico.png")));
        monitorComprobanteData.getBtnReporte().setText("");
        monitorComprobanteData.getBtnReporte().setToolTipText("Ver detalles");
        monitorComprobanteData.getBtnReporte().setPreferredSize(new Dimension(15,15));
        
        
        
    }

    @Override
    public void actualizar(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void agregarListener() {
        getBtnLimpiarTodo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarTodosDatos();
            }
        });
        
        getBtnQuitarTerminados().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarTerminados();
            }
        });
    }
    
    public void eliminarTerminados()
    {
        Component[] componentes = getjPanelComponentesCarga().getComponents();
        for (Component componente : componentes) {
            JPanel panel = (JPanel) componente;

            Component[] componentesMonitor = panel.getComponents();
            JProgressBar barraProgreso = (JProgressBar) componentesMonitor[0];

            if (barraProgreso.getPercentComplete() == 1d) {
                getjPanelComponentesCarga().remove(componente);
                getjPanelComponentesCarga().revalidate();
                getjPanelComponentesCarga().repaint();
            }

        }
    }
    
    public void eliminarTodosDatos()
    {
        getjPanelComponentesCarga().removeAll();
        getjPanelComponentesCarga().repaint();

    }

    /**
     * Avisa que un comprobante termino de procesarse (exitoso o con error) para evaluar si ya no
     * queda nada pendiente y el panel se puede ocultar solo -pedido del usuario: en pantallas de
     * baja resolucion este panel ocupa espacio fijo y antes se quedaba visible indefinidamente hasta
     * que alguien lo ocultaba a mano desde el toolbar principal-. Llamado desde
     * ClienteFacturaImplComprobante.termino(...) y ComprobanteRespuestaNoCallBack.terminado()
     * -los 2 unicos lugares que marcan un comprobante como terminado con exito-.
     */
    public void notificarComprobanteTerminado()
    {
        if (todosTerminadosSinError())
        {
            programarAutoOcultar();
        }
    }

    /**
     * true solo si HAY al menos una fila y TODAS estan en 100% con la barra en verde (exito). Un
     * panel vacio (ya cerrado/limpiado a mano) no dispara auto-ocultado -no hay nada que ocultar
     * "solo", esa decision ya la tomo el usuario-; cualquier fila en progreso o en color de error
     * (amarillo/naranja, ver ClienteFacturaImplComprobante) cancela el auto-ocultado.
     */
    private boolean todosTerminadosSinError()
    {
        Component[] componentes = getjPanelComponentesCarga().getComponents();
        if (componentes.length == 0)
        {
            return false;
        }
        for (Component componente : componentes)
        {
            JPanel panel = (JPanel) componente;
            Component[] componentesMonitor = panel.getComponents();
            JProgressBar barraProgreso = (JProgressBar) componentesMonitor[0];
            if (barraProgreso.getPercentComplete() != 1d || !Color.GREEN.equals(barraProgreso.getForeground()))
            {
                return false;
            }
        }
        return true;
    }

    private void programarAutoOcultar()
    {
        if (timerAutoOcultar != null && timerAutoOcultar.isRunning())
        {
            timerAutoOcultar.stop();
        }
        timerAutoOcultar = new Timer(MILISEGUNDOS_ESPERA_AUTO_OCULTAR, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Se re-verifica aqui (no solo al programar el timer) porque en esos 3 segundos pudo
                //haberse agregado un comprobante nuevo o haber quedado alguno con error.
                if (todosTerminadosSinError())
                {
                    ocultar();
                }
            }
        });
        timerAutoOcultar.setRepeats(false);
        timerAutoOcultar.start();
    }

    @Override
    public Object getPropertyByNombre(String nombre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



}
