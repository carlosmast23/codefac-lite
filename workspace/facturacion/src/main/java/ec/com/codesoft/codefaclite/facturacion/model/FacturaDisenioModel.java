/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.facturacion.model.disenador.DrawCanvas;
import ec.com.codesoft.codefaclite.facturacion.model.disenador.DrawComponente;
import ec.com.codesoft.codefaclite.facturacion.model.disenador.DrawDocumento;
import ec.com.codesoft.codefaclite.facturacion.model.disenador.DrawSeccion;
import ec.com.codesoft.codefaclite.facturacion.model.disenador.LienzoDisenador;
import ec.com.codesoft.codefaclite.facturacion.model.disenador.ManagerReporteFacturaFisica;
import ec.com.codesoft.codefaclite.facturacion.model.disenador.RepaintInterface;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturaDisenioPanel;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturaDisenoPanel;
import ec.com.codesoft.codefaclite.facturacion.reportdata.DetalleFacturaFisicaData;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.BandaComprobante;
import ec.com.codesoft.codefaclite.servidor.entity.ComponenteComprobanteFisico;
import ec.com.codesoft.codefaclite.servidor.entity.ComprobanteFisicoDisenio;
import ec.com.codesoft.codefaclite.servidor.service.ComprobanteFisicoDisenioService;
import ec.com.codesoft.ejemplo.utilidades.texto.UtilidadesTextos;
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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Carlos
 */
public class FacturaDisenioModel extends FacturaDisenoPanel implements RepaintInterface {

    private FacturaDisenioModel facturaDisenioModel;
    private DrawCanvas canvas;
    private List<DrawComponente> componentesDraw;

    public FacturaDisenioModel() {
        this.repaintInterface = this;
        cargarDatos();
        facturaDisenioModel = this;

        cargarComboSeccion((ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem());
        cargarComboComponentes((BandaComprobante) getCmbSeccion().getSelectedItem());

        agregarListener();
        agregarListenerCamposTexto();
        cargarDatosSeleccion();

    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        cargarDocumentoGrafico((ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem());
        seleccionarComponenteActual();
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
        InputStream reporteOriginal = RecursoCodefac.JASPER_COMPROBANTES_FISICOS.getResourceInputStream("factura_fisica.jrxml");
        ManagerReporteFacturaFisica manager = new ManagerReporteFacturaFisica(reporteOriginal);
        ComprobanteFisicoDisenio documento = (ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem();
        manager.setearNuevosValores(documento);
        InputStream reporteNuevo = manager.generarNuevoDocumento();

        //String xmlStr= UtilidadesTextos.getStringFromInputStream(reporteNuevo);
        //System.out.println(xmlStr);
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("identificacion", "1724218951001");
        parametros.put("razonSocial", "Carlos alfonso sanchez coyago");

        List<DetalleFacturaFisicaData> detalles = new ArrayList<DetalleFacturaFisicaData>();
        DetalleFacturaFisicaData detalle = new DetalleFacturaFisicaData();
        detalle.setCantidad("1");
        detalle.setDescripcion("MOUSE OPTICO");
        detalle.setValorTotal("12");
        detalle.setValorUnitario("12");
        detalles.add(detalle);

        ReporteCodefac.generarReporteInternalFrame(reporteNuevo, parametros, detalles, panelPadre, "Muestra Previa");

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
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
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
                if (getCmbDocumento().getSelectedIndex() >= 0) {
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
                seleccionarComponenteActual();

            }
        });

        getBtnDown().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                componente.setY(componente.getY() + 5);
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
                ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                componente.setX(componente.getX() + 5);
                getjPanel1().repaint();
                cargarDatosSeleccion();
            }
        });

        getBtnIzquierda().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                componente.setX(componente.getX() - 5);
                getjPanel1().repaint();
                cargarDatosSeleccion();

            }
        });
    }

    private void seleccionarComponenteActual() {
        //Quitar la seleccion de todos los componentes
        quitarSeleccionComponentes();

        //Cargar el componente Grafico
        if (getCmbComponente().getSelectedIndex() >= 0) {
            ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
            DrawComponente draw = buscarComponente(componente);
            draw.setSeleccionado(true);
            getjPanel1().repaint();
        }
    }

    private void cargarDatosSeleccion() {
        if (getCmbDocumento().getSelectedIndex() >= 0) {
            ComprobanteFisicoDisenio documento = (ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem();
            getTxtAnchoDocumento().setValue(documento.getAncho());
            getTxtAltoDocumento().setValue(documento.getAlto());
        }

        if (getCmbSeccion().getSelectedIndex() >= 0) {
            BandaComprobante banda = (BandaComprobante) getCmbSeccion().getSelectedItem();
            getTxtAltoSeccion().setValue(banda.getAlto());
        }

        if (getCmbComponente().getSelectedIndex() >= 0) {
            ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
            getTxtX().setValue(componente.getX());
            getTxtY().setValue(componente.getY());
            getTxtAnchoComponente().setValue(componente.getAncho());
            getTxtAltoComponente().setValue(componente.getAlto());
            getCmbTamanioLetra().setValue(componente.getTamanioLetra());

            if (componente.getNegrita().equals("s")) {
                getChkNegrita().setSelected(true);
            } else {
                getChkNegrita().setSelected(false);
            }
            
            if (componente.getOculto().equals("s")) {
                getChkOculto().setSelected(true);
            } else {
                getChkOculto().setSelected(false);
            }

        }

    }

    /**
     * Quita la seccion de todos los componentes
     *
     * @param componente
     * @return
     */
    private void quitarSeleccionComponentes() {
        for (DrawComponente drawComponente : componentesDraw) {
            drawComponente.setSeleccionado(false);
        }
    }

    private DrawComponente buscarComponente(ComponenteComprobanteFisico componente) {
        for (DrawComponente drawComponente : componentesDraw) {
            if (drawComponente.getComponenteEntity().equals(componente)) {
                return drawComponente;
            }
        }
        return null;
    }

    private void cargarComboComponentes(BandaComprobante banda) {
        getCmbComponente().removeAllItems();
        for (ComponenteComprobanteFisico componente : banda.getComponentes()) {
            getCmbComponente().addItem(componente);
        }
    }

    private void cargarComboSeccion(ComprobanteFisicoDisenio documento) {
        getCmbSeccion().removeAllItems();
        for (BandaComprobante bandaComprobante : documento.getSecciones()) {
            getCmbSeccion().addItem(bandaComprobante);
        }
    }

    /**
     * Carga el documento seleccionado en el panel grafico
     *
     * @param documento
     */
    private void cargarDocumentoGrafico(ComprobanteFisicoDisenio documento) {
        DrawDocumento drawDocumento = new DrawDocumento(documento);
        //Cargar el componente Grafico
        this.componentesDraw = new ArrayList<DrawComponente>();
        for (BandaComprobante seccion : documento.getSecciones()) {
            DrawSeccion drawSeccion = new DrawSeccion(seccion);

            for (ComponenteComprobanteFisico componente : seccion.getComponentes()) {
                DrawComponente drawComponente = new DrawComponente(componente);
                drawSeccion.agregarComponente(drawComponente);
                componentesDraw.add(drawComponente);
            }
            drawDocumento.agregarSeccion(drawSeccion);
        }
        this.canvas = new DrawCanvas(drawDocumento);

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
        ComprobanteFisicoDisenioService servicio = new ComprobanteFisicoDisenioService();
        List<ComprobanteFisicoDisenio> documentos = servicio.obtenerTodos();
        for (ComprobanteFisicoDisenio documento : documentos) {
            getCmbDocumento().addItem(documento);
        }
    }

    @Override
    public void repaint(Graphics g) {
        Dimension dimension = getjPanel1().getSize();
        System.out.println(getjPanel1().getWidth() + "-" + getjPanel1().getHeight());
        canvas.dibujar(g, dimension);
    }

    private void agregarListenerCamposTexto() {

        getTxtAnchoDocumento().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ComprobanteFisicoDisenio compobante = (ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem();
                int numero = Integer.parseInt(getTxtAnchoDocumento().getValue().toString());
                compobante.setAncho(numero);
                getjPanel1().repaint();
            }
        });

        getTxtAltoDocumento().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ComprobanteFisicoDisenio compobante = (ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem();
                int numero = Integer.parseInt(getTxtAltoDocumento().getValue().toString());
                compobante.setAlto(numero);
                getjPanel1().repaint();
            }
        });

        getTxtX().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                int numero = Integer.parseInt(getTxtX().getValue().toString());
                componente.setX(numero);
                getjPanel1().repaint();

            }
        });

        getTxtY().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                int numero = Integer.parseInt(getTxtY().getValue().toString());
                componente.setY(numero);
                getjPanel1().repaint();
            }
        });

        getTxtAnchoComponente().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                int numero = Integer.parseInt(getTxtAnchoComponente().getValue().toString());
                componente.setAncho(numero);
                getjPanel1().repaint();
            }
        });

        getTxtAltoComponente().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                int numero = Integer.parseInt(getTxtAltoComponente().getValue().toString());
                componente.setAlto(numero);
                getjPanel1().repaint();
            }
        });

        getTxtAltoSeccion().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (getCmbSeccion().getSelectedIndex() >= 0) {
                    BandaComprobante banda = (BandaComprobante) getCmbSeccion().getSelectedItem();
                    banda.setAlto(Integer.parseInt(getTxtAltoSeccion().getValue().toString()));
                    getjPanel1().repaint();
                }
            }
        });

        getCmbTamanioLetra().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                int tamanio = Integer.parseInt(getCmbTamanioLetra().getValue().toString());
                componente.setTamanioLetra(tamanio);
                getjPanel1().repaint();
            }
        });

        getChkNegrita().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                if (getChkNegrita().isSelected()) {
                    componente.setNegrita("s");
                } else {
                    componente.setNegrita("n");
                }
                getjPanel1().repaint();
            }
        });
        
        getChkOculto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                if (getChkOculto().isSelected()) {
                    componente.setOculto("s");
                } else {
                    componente.setOculto("n");
                }
                getjPanel1().repaint();
            }
        });
    }

}
