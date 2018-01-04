/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.facturacion.busqueda.ClienteFacturacionBusqueda;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturaReportePanel;
import ec.com.codesoft.codefaclite.facturacion.reportdata.DataEjemploReporte;
import ec.com.codesoft.codefaclite.facturacion.reportdata.ReporteFacturaData;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.Factura;
import ec.com.codesoft.codefaclite.servidor.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidor.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.FacturaEnumEstado;
import ec.com.codesoft.codefaclite.servidor.service.FacturacionService;
import ec.com.codesoft.codefaclite.servidor.service.NotaCreditoService;
import ec.com.codesoft.codefaclite.test.TipoBusquedaEnum;
import static ec.com.codesoft.ejemplo.utilidades.fecha.UtilidadesFecha.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CodesoftDesarrollo
 */
public class FacturaReporteModel extends FacturaReportePanel {

    private Persona persona;
    Map<String, Object> parameters = new HashMap<String, Object>();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private DefaultTableModel modeloTablaFacturas;
    private List<Factura> datafact;
    private List<NotaCredito> datafact2;
    Date fechaInicio = null;
    Date fechaFin = null;
    String fechainicio = "";
    String fechafin = "";

    public FacturaReporteModel() {
        initListener();
    }

    private void initListener() {
        //String texto= session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO).valor;
        getDateFechaInicio().setDate(fechaInicioMes(hoy()));
        getDateFechaFin().setDate(hoy());

        getCmbTipo().addItem(TipoBusquedaEnum.TODOS);
        getCmbTipo().addItem(TipoBusquedaEnum.ANULADOS);
        getCmbTipo().addItem(TipoBusquedaEnum.FACTURAS);

        getChkTodos().setSelected(true);
        if (getChkTodos().isSelected()) {
            persona = null;
            getTxtCliente().setText("...");
            //getLblNombreCliente().setText("..");
            getBtnBuscarCliente().setEnabled(false);
        }

        getChkTodos().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    persona = null;
                    getTxtCliente().setText("...");
                    //getLblNombreCliente().setText("..");
                    getBtnBuscarCliente().setEnabled(false);
                } else {
                    getBtnBuscarCliente().setEnabled(true);
                }
            }
        });
        getBtnBuscarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteFacturacionBusqueda clienteBusquedaDialogo = new ClienteFacturacionBusqueda();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                persona = (Persona) buscarDialogoModel.getResultado();
                if (persona != null) {
                    setearValoresCliente();
                }
            }
        });

        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BigDecimal d = BigDecimal.ZERO;
                BigDecimal acum = BigDecimal.ZERO, acumdoce = BigDecimal.ZERO, acumiva = BigDecimal.ZERO;

                TipoBusquedaEnum estadoSeleccionado = (TipoBusquedaEnum) getCmbTipo().getSelectedItem();
                if (getDateFechaInicio().getDate() != null) {
                    fechaInicio = new Date(getDateFechaInicio().getDate().getTime());
                }
                if (getDateFechaFin().getDate() != null) {
                    fechaFin = new Date(getDateFechaFin().getDate().getTime());
                }

                Vector<String> titulo = new Vector<>();
                titulo.add("Preimpreso");
                titulo.add("Fecha");
                titulo.add("Identificación");
                titulo.add("Razón social");
                titulo.add("Nombre legal");
                titulo.add("Estado");
                titulo.add("Subtotal 12%");
                titulo.add("Subtotal 0% ");
                titulo.add("IVA 12%");
                titulo.add("Total");

                modeloTablaFacturas = new DefaultTableModel(titulo, 0);

                FacturacionService fs = new FacturacionService();
                datafact = fs.obtenerFacturasReporte(persona, fechaInicio, fechaFin);
                NotaCreditoService nc = new NotaCreditoService();
                datafact2 = nc.obtenerNotasReporte(persona, fechaInicio, fechaFin);

                if (estadoSeleccionado.getTipo() == "T" || estadoSeleccionado.getTipo() == "F") {
                    for (Factura factura : datafact) {
                        Vector<String> fila = new Vector<String>();
                        FacturaEnumEstado ef = FacturaEnumEstado.getEnum(factura.getEstado());
                        fila.add(factura.getPreimpreso());
                        fila.add(dateFormat.format(factura.getFechaFactura()));
                        fila.add(factura.getCliente().getIdentificacion());
                        fila.add(factura.getCliente().getRazonSocial());
                        fila.add(factura.getCliente().getNombreLegal());
                        fila.add(ef.getNombre());
                        fila.add(factura.getSubtotalImpuestos().toString());
                        fila.add(factura.getSubtotalSinImpuestos().toString());
                        fila.add(factura.getIva().toString());

                        if (estadoSeleccionado.getTipo() == "T") {
                            NotaCredito notaCredito = verificarPorFactura(factura);
                            if (notaCredito != null) {
                                d = factura.getTotal().subtract(notaCredito.getTotal());
                                acum = acum.add(factura.getSubtotalSinImpuestos().subtract(notaCredito.getSubtotalCero()));
                                acumdoce = acumdoce.add(factura.getSubtotalImpuestos().subtract(notaCredito.getSubtotalDoce()));
                                acumiva = acumiva.add(factura.getIva().subtract(notaCredito.getValorIvaDoce()));
                            } else {
                                d = factura.getTotal();
                                acum = acum.add(factura.getSubtotalSinImpuestos());
                                acumdoce = acumdoce.add(factura.getSubtotalImpuestos());
                                acumiva = acumiva.add(factura.getIva());
                            }
                            fila.add(d.toString());
                        } else {
                            fila.add(factura.getTotal().toString());
                            acum = acum.add(factura.getSubtotalSinImpuestos());
                            acumdoce = acumdoce.add(factura.getSubtotalImpuestos());
                            acumiva = acumiva.add(factura.getIva());
                        }
                        modeloTablaFacturas.addRow(fila);
                    }
                    getTblDocumentos().setModel(modeloTablaFacturas);
                } else {
                    for (NotaCredito factura : datafact2) {
                        Vector<String> fila = new Vector<String>();
                        FacturaEnumEstado ef = FacturaEnumEstado.getEnum(factura.getEstado());

                        fila.add(factura.getPreimpreso());
                        fila.add(dateFormat.format(factura.getFechaNotaCredito()));
                        fila.add(factura.getCliente().getIdentificacion());
                        fila.add(factura.getCliente().getRazonSocial());
                        fila.add(factura.getCliente().getNombreLegal());
                        fila.add(ef.getNombre());
                        fila.add(factura.getSubtotalDoce().toString());
                        fila.add(factura.getSubtotalCero().toString());
                        fila.add(factura.getValorIvaDoce().toString());
                        fila.add(factura.getTotal().toString());

                        acum = acum.add(factura.getSubtotalCero());
                        acumdoce = acumdoce.add(factura.getSubtotalDoce());
                        acumiva = acumiva.add(factura.getValorIvaDoce());

                        modeloTablaFacturas.addRow(fila);
                    }
                    getTblDocumentos().setModel(modeloTablaFacturas);
                }

                getLblSubtotal().setText(acum.toString());
                getLblSubtotalIva().setText(acumdoce.toString());
                getLblIva().setText(acumiva.toString());
                BigDecimal total = acum.add(acumdoce).add(acumiva);
                getLblTotal().setText(total.toString());
            }
        });
        /*
        getBtnLimpiarFecha().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDateFechaInicio().setDate(null);
                //getDateFechaFin().setDate(null);
            }
        });*/
        
        getBtnLimpiarFechaInicio().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDateFechaInicio().setDate(null);
            }
        });
        
        getBtnLimpiarFechaFin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDateFechaFin().setDate(null);
            }
        });
    }

    private NotaCredito verificarPorFactura(Factura factura) {
        for (NotaCredito notaCredito : datafact2) {
            if (notaCredito.getFactura().equals(factura)) {
                return notaCredito;
            }
        }
        return null;
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
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        BigDecimal d = null;
        BigDecimal acum = BigDecimal.ZERO, acumdoce = BigDecimal.ZERO, acumiva = BigDecimal.ZERO;

        TipoBusquedaEnum estadoSeleccionado = (TipoBusquedaEnum) getCmbTipo().getSelectedItem();
        if (getDateFechaInicio().getDate() != null) {
            fechaInicio = new Date(getDateFechaInicio().getDate().getTime());
            fechainicio = dateFormat.format(getDateFechaInicio().getDate());

        }
        if (getDateFechaFin().getDate() != null) {
            fechaFin = new Date(getDateFechaFin().getDate().getTime());
            fechafin = dateFormat.format(getDateFechaFin().getDate());
        }

        String path = RecursoCodefac.JASPER_FACTURACION.getResourcePath("reporte_documentos.jrxml");
        FacturacionService fs = new FacturacionService();
        datafact = fs.obtenerFacturasReporte(persona, fechaInicio, fechaFin);
        NotaCreditoService nc = new NotaCreditoService();
        datafact2 = nc.obtenerNotasReporte(persona, fechaInicio, fechaFin);

        List<ReporteFacturaData> data = new ArrayList<ReporteFacturaData>();
        if (estadoSeleccionado.getTipo() == "T" || estadoSeleccionado.getTipo() == "F") {
            for (Factura factura : datafact) {
                if (estadoSeleccionado.getTipo() == "T") {
                    NotaCredito notaCredito = verificarPorFactura(factura);
                    if (notaCredito != null) {
                        d = factura.getTotal().subtract(notaCredito.getTotal());
                        acum = acum.add(factura.getSubtotalSinImpuestos().subtract(notaCredito.getSubtotalCero()));
                        acumdoce = acumdoce.add(factura.getSubtotalImpuestos().subtract(notaCredito.getSubtotalDoce()));
                        acumiva = acumiva.add(factura.getIva().subtract(notaCredito.getValorIvaDoce()));
                    } else {
                        d = factura.getTotal();
                        acum = acum.add(factura.getSubtotalSinImpuestos());
                        acumdoce = acumdoce.add(factura.getSubtotalImpuestos());
                        acumiva = acumiva.add(factura.getIva());
                    }
                } else {
                    d = factura.getTotal();
                    acum = acum.add(factura.getSubtotalSinImpuestos());
                    acumdoce = acumdoce.add(factura.getSubtotalImpuestos());
                    acumiva = acumiva.add(factura.getIva());
                }
                FacturaEnumEstado ef = FacturaEnumEstado.getEnum(factura.getEstado());

                data.add(new ReporteFacturaData(
                        factura.getPreimpreso(),
                        dateFormat.format(factura.getFechaFactura()),
                        factura.getCliente().getIdentificacion(),
                        factura.getCliente().getRazonSocial(),
                        factura.getCliente().getNombreLegal(),
                        ef.getNombre(),
                        factura.getSubtotalImpuestos().toString(),
                        factura.getSubtotalSinImpuestos().toString(),
                        factura.getIva().toString(),
                        d.toString()
                ));
            }
        } else {
            for (NotaCredito factura : datafact2) {
                acum = acum.add(factura.getSubtotalCero());
                acumdoce = acumdoce.add(factura.getSubtotalDoce());
                acumiva = acumiva.add(factura.getValorIvaDoce());
                FacturaEnumEstado ef = FacturaEnumEstado.getEnum(factura.getEstado());
                data.add(new ReporteFacturaData(
                        factura.getPreimpreso(),
                        dateFormat.format(factura.getFechaNotaCredito()),
                        factura.getCliente().getIdentificacion(),
                        factura.getCliente().getRazonSocial(),
                        factura.getCliente().getNombreLegal(),
                        ef.getNombre(),
                        factura.getSubtotalDoce().toString(),
                        factura.getSubtotalCero().toString(),
                        factura.getValorIvaDoce().toString(),
                        factura.getTotal().toString()
                ));
            }
        }

        String cliente = "";
        if (persona == null) {
            cliente = "TODOS";
        } else {
            cliente = persona.getRazonSocial();
        }

        parameters.put("fechainicio", fechainicio);
        parameters.put("fechafin", fechafin);
        parameters.put("tipodocumento", String.valueOf(estadoSeleccionado));
        parameters.put("cliente", cliente);

        parameters.put("subtotal", acum.toString());
        parameters.put("subtotaliva", acumdoce.toString());
        parameters.put("valoriva", acumiva.toString());
        BigDecimal total = acum.add(acumdoce).add(acumiva);
        parameters.put("total", total.toString());

        System.out.println(session.getUsuario().getClave());
        /*        data.add(new ReporteFacturaData("001-002-00001231"));
        data.add(new ReporteFacturaData("001-002-000012331"));
         */
        ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Reporte Documentos ");
    }

    @Override
    public void actualizar() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
    }

    @Override
    public String getNombre() {
        return "Reporte Facturación";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    private void setearValoresCliente() {

        getTxtCliente().setText(persona.getIdentificacion());
        //getLblNombreCliente().setText(persona.getRazonSocial());

        //getLblDireccionCliente().setText(persona.getDireccion());
        //getLblTelefonoCliente().setText(persona.getTelefonoConvencional());  
        //datosAdicionales.put("email",persona.getCorreoElectronico());
        //factura.setCliente(persona);
        //Actualiza la tabla de los datos adicionales
        //cargarDatosAdicionales();
    }

    @Override
    public void iniciar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
