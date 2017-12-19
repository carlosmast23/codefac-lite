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

    public FacturaReporteModel() {
        initListener();
    }

    private void initListener() {
        //String texto= session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO).valor;
        getDateFechaInicio().setDate(hoy());
        getDateFechaFin().setDate(fechaFinMes(hoy()));

        getCmbTipo().addItem(TipoBusquedaEnum.TODOS);
        getCmbTipo().addItem(TipoBusquedaEnum.ANULADOS);
        getCmbTipo().addItem(TipoBusquedaEnum.FACTURAS);

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
                Date sqlDate = new Date(getDateFechaInicio().getDate().getTime());
                Date sqlDate2 = new Date(getDateFechaFin().getDate().getTime());

                Vector<String> titulo = new Vector<>();
                titulo.add("Preimpreso");
                titulo.add("Fecha");
                titulo.add("Cliente");
                titulo.add("Valor");

                modeloTablaFacturas = new DefaultTableModel(titulo, 0);

                FacturacionService fs = new FacturacionService();
                datafact = fs.obtenerFacturasReporte(persona, sqlDate, sqlDate2);
                NotaCreditoService nc = new NotaCreditoService();
                datafact2 = nc.obtenerNotasReporte(persona, sqlDate, sqlDate2);
                if (estadoSeleccionado.getTipo() == "T" || estadoSeleccionado.getTipo() == "F") {
                    for (Factura factura : datafact) {
                        Vector<String> fila = new Vector<String>();
                        fila.add(factura.getPreimpreso());
                        fila.add(dateFormat.format(factura.getFechaFactura()));
                        fila.add(factura.getCliente().getRazonSocial());
                        if (estadoSeleccionado.getTipo() == "T") {
                            NotaCredito notaCredito = verificarPorFactura(factura);
                            if (notaCredito != null) {
                                d = factura.getTotal().subtract(notaCredito.getTotal());
                                acum = acum.add(factura.getSubtotalCero().subtract(notaCredito.getSubtotalCero()));
                                acumdoce = acumdoce.add(factura.getSubtotalDoce().subtract(notaCredito.getSubtotalDoce()));
                                acumiva = acumiva.add(factura.getValorIvaDoce().subtract(notaCredito.getValorIvaDoce()));
                            } else {
                                d = factura.getTotal();
                                acum = acum.add(factura.getSubtotalCero());
                                acumdoce = acumdoce.add(factura.getSubtotalDoce());
                                acumiva = acumiva.add(factura.getValorIvaDoce());
                            }
                            fila.add(String.valueOf(d));
                        } else {
                            fila.add(String.valueOf(factura.getTotal()));
                            acum = acum.add(factura.getSubtotalCero());
                            acumdoce = acumdoce.add(factura.getSubtotalDoce());
                            acumiva = acumiva.add(factura.getValorIvaDoce());
                        }
                        modeloTablaFacturas.addRow(fila);
                    }
                    getTblDocumentos().setModel(modeloTablaFacturas);
                } else {
                    for (NotaCredito factura : datafact2) {
                        Vector<String> fila = new Vector<String>();
                        fila.add(factura.getPreimpreso());
                        fila.add(dateFormat.format(factura.getFechaNotaCredito()));
                        fila.add(factura.getCliente().getRazonSocial());
                        fila.add(String.valueOf(factura.getTotal()));

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

        String fechainicio = dateFormat.format(getDateFechaInicio().getDate());
        String fechafin = dateFormat.format(getDateFechaFin().getDate());
        TipoBusquedaEnum estadoSeleccionado = (TipoBusquedaEnum) getCmbTipo().getSelectedItem();
        Date sqlDate = new Date(getDateFechaInicio().getDate().getTime());
        Date sqlDate2 = new Date(getDateFechaFin().getDate().getTime());

        String path = RecursoCodefac.JASPER_FACTURACION.getResourcePath("reporte_documentos.jrxml");
        FacturacionService fs = new FacturacionService();
        datafact = fs.obtenerFacturasReporte(persona, sqlDate, sqlDate2);
        NotaCreditoService nc = new NotaCreditoService();
        datafact2 = nc.obtenerNotasReporte(persona, sqlDate, sqlDate2);
        List<ReporteFacturaData> data = new ArrayList<ReporteFacturaData>();
        if (estadoSeleccionado.getTipo() == "T" || estadoSeleccionado.getTipo() == "F") {
            for (Factura factura : datafact) {
                if (estadoSeleccionado.getTipo() == "T") {

                    NotaCredito notaCredito = verificarPorFactura(factura);
                    if (notaCredito != null) {
                        d = factura.getTotal().subtract(notaCredito.getTotal());
                        acum = acum.add(factura.getSubtotalCero().subtract(notaCredito.getSubtotalCero()));
                        acumdoce = acumdoce.add(factura.getSubtotalDoce().subtract(notaCredito.getSubtotalDoce()));
                        acumiva = acumiva.add(factura.getValorIvaDoce().subtract(notaCredito.getValorIvaDoce()));
                    } else {
                        d = factura.getTotal();
                        acum = acum.add(factura.getSubtotalCero());
                        acumdoce = acumdoce.add(factura.getSubtotalDoce());
                        acumiva = acumiva.add(factura.getValorIvaDoce());
                    }
                } else {
                    d = factura.getTotal();
                    acum = acum.add(factura.getSubtotalCero());
                    acumdoce = acumdoce.add(factura.getSubtotalDoce());
                    acumiva = acumiva.add(factura.getValorIvaDoce());
                }
                data.add(new ReporteFacturaData(factura.getPreimpreso(), dateFormat.format(factura.getFechaFactura()), factura.getCliente().getRazonSocial(), String.valueOf(d)));
            }
        } else {
            for (NotaCredito factura : datafact2) {
                acum = acum.add(factura.getSubtotalCero());
                acumdoce = acumdoce.add(factura.getSubtotalDoce());
                acumiva = acumiva.add(factura.getValorIvaDoce());

                data.add(new ReporteFacturaData(factura.getPreimpreso(), dateFormat.format(factura.getFechaNotaCredito()), factura.getCliente().getRazonSocial(), String.valueOf(factura.getTotal())));
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
        getLblNombreCliente().setText(persona.getRazonSocial());

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

}
