/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.facturacion.model.disenador.DrawCanvas;
import ec.com.codesoft.codefaclite.facturacion.model.disenador.DrawComponente;
import ec.com.codesoft.codefaclite.facturacion.model.disenador.DrawDocumento;
import ec.com.codesoft.codefaclite.facturacion.model.disenador.DrawSeccion;
import ec.com.codesoft.codefaclite.facturacion.model.disenador.LienzoDisenador;
import ec.com.codesoft.codefaclite.facturacion.model.disenador.RepaintInterface;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturaDisenioPanel;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturaDisenoPanel;
import ec.com.codesoft.codefaclite.servidor.entity.BandaComprobante;
import ec.com.codesoft.codefaclite.servidor.entity.ComponenteComprobanteFisico;
import ec.com.codesoft.codefaclite.servidor.entity.ComprobanteFisicoDisenio;
import ec.com.codesoft.codefaclite.servidor.service.ComprobanteFisicoDisenioService;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;
import javax.swing.SpringLayout;

/**
 *
 * @author Carlos
 */
public class FacturaDisenioModel extends FacturaDisenoPanel implements RepaintInterface{

    private FacturaDisenioModel facturaDisenioModel;
    private DrawCanvas canvas;
    
    public FacturaDisenioModel() {
        this.repaintInterface=this;
        cargarDatos();
        facturaDisenioModel=this;
        
        cargarComboSeccion((ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem());
        cargarComboComponentes((BandaComprobante) getCmbSeccion().getSelectedItem());
        
        
        agregarListener();
        agregarListenerCamposTexto();
        cargarDatosSeleccion();
        
    }
    


    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        //cargarElementosBaseDatos();
       /*
        getTxtY().requestFocusInWindow();
                facturaDisenioModel.revalidate();
        facturaDisenioModel.repaint();
        lienzo.repaint();*/
        cargarDocumentoGrafico((ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem());

    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        //RecursoCodefac.JASPER_CRM.getResourceURL("reporteEjemplo.jrxml");
       // panelPadre.crearReportePantalla(jasperPrint, title);
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void agregarListener() {
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                //cargarDocumentoGrafico((ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem());
                getjPanel1().revalidate();
                getjPanel1().repaint();
            }
            
        });
        
        getCmbDocumento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getCmbDocumento().getSelectedIndex()>=0)
                {
                    cargarComboSeccion((ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem());
                    cargarDocumentoGrafico((ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem());
                    cargarDatosSeleccion();
                }
            }
        });
        
         
        getCmbSeccion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                cargarComboComponentes((BandaComprobante) getCmbSeccion().getSelectedItem());
                cargarDatosSeleccion();
            }
        });
        
        getCmbComponente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosSeleccion();
            }
        });
        
        
        getBtnDown().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                componente.setY(componente.getY() +5);
                getjPanel1().repaint();
                cargarDatosSeleccion();
            }
        });
        
        getBtnArriba().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                componente.setY(componente.getY() - 5);
                getjPanel1().repaint();
                cargarDatosSeleccion();
            }
        });
        
        getBtnDerecha().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComponenteComprobanteFisico componente= (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                componente.setX(componente.getX()+5);
                getjPanel1().repaint();
                cargarDatosSeleccion();
            }
        });
        
        getBtnIzquierda().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComponenteComprobanteFisico componente= (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                componente.setX(componente.getX()-5);
                getjPanel1().repaint();
                cargarDatosSeleccion();

            }
        });
    }
    
    private void cargarDatosSeleccion()
    {
        if(getCmbDocumento().getSelectedIndex()>=0)
        {
            ComprobanteFisicoDisenio documento = (ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem();
            getTxtAnchoDocumento().setText(documento.getAncho() + "");
            getTxtAltoDocumento().setText(documento.getAlto() + "");
        }

        
        if(getCmbSeccion().getSelectedIndex()>=0)
        {
            BandaComprobante banda = (BandaComprobante) getCmbSeccion().getSelectedItem();
            getTxtAltoSeccion().setText(banda.getAlto() + "");
        }
        
        if(getCmbComponente().getSelectedIndex()>=0)
        {
            ComponenteComprobanteFisico componente=(ComponenteComprobanteFisico)getCmbComponente().getSelectedItem();
            getTxtX().setText(componente.getX()+"");
            getTxtY().setText(componente.getY()+"");
        }
    
    }
    
    private void cargarComboComponentes(BandaComprobante banda)
    {
        getCmbComponente().removeAllItems();
        for (ComponenteComprobanteFisico componente : banda.getComponentes()) {
            getCmbComponente().addItem(componente);
        }
    }
    
    private void cargarComboSeccion(ComprobanteFisicoDisenio documento)
    {
        getCmbSeccion().removeAllItems();
        for (BandaComprobante bandaComprobante : documento.getSecciones()) {
            getCmbSeccion().addItem(bandaComprobante);
        }
    }
    
    /**
     * Carga el documento seleccionado en el panel grafico
     * @param documento 
     */
    private void cargarDocumentoGrafico(ComprobanteFisicoDisenio documento)
    {
        DrawDocumento drawDocumento=new DrawDocumento(documento);
        for (BandaComprobante seccion : documento.getSecciones()) {
            DrawSeccion drawSeccion=new DrawSeccion(seccion);
            
            for (ComponenteComprobanteFisico componente : seccion.getComponentes()) {
                DrawComponente drawComponente=new DrawComponente(componente);
                drawSeccion.agregarComponente(drawComponente);
            }
            drawDocumento.agregarSeccion(drawSeccion);            
        }
        this.canvas=new DrawCanvas(drawDocumento);
        //Graphics g= getjPanel1().getGraphics();

        //this.canvas.dibujar(g);

        
    }
/*
    private void iniciarLienzo() {

        this.canvas=new DrawCanvas(null);
        SpringLayout layout = new SpringLayout();
        LienzoDisenador lienzo = new LienzoDisenador(this.canvas);
        lienzo.setLayout(layout);
        getjScrollPane1().setViewportView(lienzo);
    }*/

    private void cargarDatos() {
        getCmbDocumento().removeAllItems();
        ComprobanteFisicoDisenioService servicio=new ComprobanteFisicoDisenioService();
        List<ComprobanteFisicoDisenio> documentos=servicio.obtenerTodos();
        for (ComprobanteFisicoDisenio documento : documentos) {
            getCmbDocumento().addItem(documento);
        }
    }

    @Override
    public void repaint(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0,0,2000,2000);
        System.out.println("repintando toda la pantalla ...");
        //cargarDocumentoGrafico((ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem());
                
        Dimension dimension= getjPanel1().getSize();
        Rectangle rectangle=getjPanel1().getBounds();
        System.out.println(getjPanel1().getWidth()+"-"+getjPanel1().getHeight());
        canvas.dibujar(g,dimension);
        //facturaDisenioModel.repaint();
    }

    private void agregarListenerCamposTexto() {
        getTxtAltoSeccion().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                if (getCmbSeccion().getSelectedIndex() >= 0) {
                    BandaComprobante banda = (BandaComprobante) getCmbSeccion().getSelectedItem();
                    banda.setAlto(Integer.parseInt(getTxtAltoSeccion().getText()));
                    getjPanel1().repaint();
                }
            }
        });
    }
    
}

