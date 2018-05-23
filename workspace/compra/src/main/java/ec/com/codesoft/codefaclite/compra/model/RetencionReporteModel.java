package ec.com.codesoft.codefaclite.compra.model;

import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.compra.panel.RetencionReportePanel;
import ec.com.codesoft.codefaclite.compra.reportdata.ReporteRetencionesData;
import ec.com.codesoft.codefaclite.compra.reportdata.ValorRetencionesData;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.crm.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RetencionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionIvaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionRentaServiceIf;
import static ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha.fechaInicioMes;
import static ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha.formatDate;
import static ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha.hoy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.Boolean.FALSE;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author CodesoftDesarrollo
 */
public class RetencionReporteModel extends RetencionReportePanel {

    private Persona proveedor = null;
    SriRetencionIva sriRetencionIva = null;
    SriRetencionRenta sriRetencionRenta = null;

    Map<String, Object> parameters = new HashMap<String, Object>();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private List<RetencionDetalle> dataretencion;
    Date fechaInicio = null;
    Date fechaFin = null;
    String fechainicio = "";
    String fechafin = "";
    boolean banderaTipo = false;

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        getCmbTipo().addItem("IVA");
        getCmbTipo().addItem("RENTA");

        getDateFechaInicio().setDate(fechaInicioMes(hoy()));
        getDateFechaFin().setDate(hoy());

        getChkTodos().setSelected(true);
        if (getChkTodos().isSelected()) {
            proveedor = null;
            getTxtProveedor().setText("...");
            getBtnBuscarProveedor().setEnabled(false);
        }

        getChkTodos().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    proveedor = null;
                    getTxtProveedor().setText("...");
                    getBtnBuscarProveedor().setEnabled(false);
                } else {
                    getBtnBuscarProveedor().setEnabled(true);
                }
            }
        });

        getChkTodosIva().setSelected(true);
        if (getChkTodosIva().isSelected()) {
            sriRetencionIva = null;
            getCmbRetencionIva().setEnabled(false);
        }

        getChkTodosIva().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    sriRetencionIva = null;
                    getCmbRetencionIva().setEnabled(false);
                } else {
                    getCmbRetencionIva().setEnabled(true);
                }
            }
        });

        getChkTodosRenta().setSelected(true);
        if (getChkTodosRenta().isSelected()) {
            sriRetencionRenta = null;
            getCmbRetencionRenta().setEnabled(false);
        }

        getChkTodosRenta().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    sriRetencionRenta = null;
                    getCmbRetencionRenta().setEnabled(false);
                } else {
                    getCmbRetencionRenta().setEnabled(true);
                }
            }
        });

        getChkTodosTipo().setSelected(true);
        if (getChkTodosTipo().isSelected()) {
            banderaTipo = true;
            getCmbTipo().setEnabled(false);
        }

        getChkTodosTipo().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    banderaTipo = true;
                    getCmbTipo().setEnabled(false);
                } else {
                    banderaTipo = false;
                    getCmbTipo().setEnabled(true);
                }
            }
        });

        //Agregar los tipos de retencion Iva
        getCmbRetencionIva().removeAllItems();
        SriRetencionIvaServiceIf sriRetencionIvaService = ServiceFactory.getFactory().getSriRetencionIvaServiceIf();
        try {
            List<SriRetencionIva> tipoRetencionesIva = sriRetencionIvaService.obtenerTodos();
            for (SriRetencionIva tipoRetencione : tipoRetencionesIva) {
                getCmbRetencionIva().addItem(tipoRetencione);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Agregar los tipos de retencion Renta
        getCmbRetencionRenta().removeAllItems();
        SriRetencionRentaServiceIf sriRetencionRentaService = ServiceFactory.getFactory().getSriRetencionRentaServiceIf();
        try {
            List<SriRetencionRenta> tipoRetencionesRenta = sriRetencionRentaService.obtenerTodos();
            for (SriRetencionRenta sriRetencionRenta : tipoRetencionesRenta) {
                getCmbRetencionRenta().addItem(sriRetencionRenta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        getBtnBuscarProveedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProveedorBusquedaDialogo proveedorBusquedaDialogo = new ProveedorBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(proveedorBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                proveedor = (Persona) buscarDialogoModel.getResultado();
                if (proveedor != null) {
                    String identificacion = proveedor.getIdentificacion();
                    String nombre = proveedor.getRazonSocial();
                    getTxtProveedor().setText(identificacion + " - " + nombre);
                }
            }
        });

        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (getDateFechaInicio().getDate() != null) {
                        fechaInicio = new Date(getDateFechaInicio().getDate().getTime());
                    }
                    if (getDateFechaFin().getDate() != null) {
                        fechaFin = new Date(getDateFechaFin().getDate().getTime());
                    }
                    sriRetencionIva = (SriRetencionIva) getCmbRetencionIva().getSelectedItem();
                    sriRetencionRenta = (SriRetencionRenta) getCmbRetencionRenta().getSelectedItem();

                    String tipo = "";
                    if (banderaTipo == true) {
                        tipo = null;
                    } else {
                        tipo = getCmbTipo().getSelectedItem().toString();
                    }

                    Vector<String> titulo = new Vector<>();
                    titulo.add("Proveedor");
                    titulo.add("Preimpreso");
                    titulo.add("Base imponble");
                    titulo.add("Porcentaje");
                    titulo.add("Código");
                    titulo.add("valor");

                    DefaultTableModel modeloTablaRetenciones = new DefaultTableModel(titulo, 0);

                    RetencionServiceIf fs = ServiceFactory.getFactory().getRetencionServiceIf();
                    dataretencion = fs.obtenerRetencionesReporte(proveedor, fechaInicio, fechaFin, sriRetencionIva, sriRetencionRenta, tipo);
                    for (RetencionDetalle retencion : dataretencion) {
                        Vector<String> fila = new Vector<String>();
                        fila.add(retencion.getRetencion().getProveedor().getRazonSocial());
                        fila.add(retencion.getRetencion().getCompra().getPreimpreso());
                        fila.add(retencion.getBaseImponible().toString());
                        fila.add(retencion.getPorcentajeRetener().toString() + " %");
                        fila.add(retencion.getCodigoRetencionSri());
                        fila.add(retencion.getValorRetenido().toString());

                        modeloTablaRetenciones.addRow(fila);

                    }
                    getTblRetenciones().setModel(modeloTablaRetenciones);

                    Vector<String> titulo2 = new Vector<>();
                    titulo2.add("Código");
                    titulo2.add("Valor");
                    DefaultTableModel modeloTablaRetIva = new DefaultTableModel(titulo2, 0);
                    List<Object[]> dataRetencionCodigo = fs.obtenerRetencionesCodigo(proveedor, fechaInicio, fechaFin, sriRetencionIva, sriRetencionRenta, "2");
                    for (Object[] obj : dataRetencionCodigo) {
                        Vector<String> fila = new Vector<String>();
                        String r = (String) obj[0];
                        BigDecimal valor = (BigDecimal) obj[1];
                        fila.add(r);
                        fila.add(valor.toString());
                        modeloTablaRetIva.addRow(fila);
                    }
                    getTblRetIva().setModel(modeloTablaRetIva);

                    Vector<String> titulo3 = new Vector<>();
                    titulo3.add("Código");
                    titulo3.add("Valor");
                    DefaultTableModel modeloTablaRetRenta = new DefaultTableModel(titulo3, 0);
                    List<Object[]> dataRetencionCodigoRenta = fs.obtenerRetencionesCodigo(proveedor, fechaInicio, fechaFin, sriRetencionIva, sriRetencionRenta, "1");
                    for (Object[] obj : dataRetencionCodigoRenta) {
                        Vector<String> fila = new Vector<String>();
                        String r = (String) obj[0];
                        BigDecimal valor = (BigDecimal) obj[1];
                        fila.add(r);
                        fila.add(valor.toString());
                        modeloTablaRetRenta.addRow(fila);
                    }
                    getTblRetRenta().setModel(modeloTablaRetRenta);

                } catch (RemoteException ex) {
                    Logger.getLogger(RetencionReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
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

        try {
            if (getDateFechaInicio().getDate() != null) {
                fechaInicio = new Date(getDateFechaInicio().getDate().getTime());
            }
            if (getDateFechaFin().getDate() != null) {
                fechaFin = new Date(getDateFechaFin().getDate().getTime());
            }
            sriRetencionIva = (SriRetencionIva) getCmbRetencionIva().getSelectedItem();
            sriRetencionRenta = (SriRetencionRenta) getCmbRetencionRenta().getSelectedItem();
            String tipo = "";
            if (banderaTipo == true) {
                tipo = null;
            } else {
                tipo = getCmbTipo().getSelectedItem().toString();
            }

            InputStream path = RecursoCodefac.JASPER_COMPRA.getResourceInputStream("reporte_retenciones.jrxml");
            RetencionServiceIf fs = ServiceFactory.getFactory().getRetencionServiceIf();
            dataretencion = fs.obtenerRetencionesReporte(proveedor, fechaInicio, fechaFin, sriRetencionIva, sriRetencionRenta, tipo);
            List<ReporteRetencionesData> data = new ArrayList<ReporteRetencionesData>();
            for (RetencionDetalle retencion : dataretencion) {
                data.add(new ReporteRetencionesData(
                        retencion.getRetencion().getCompra().getPreimpreso(),
                        retencion.getBaseImponible().toString(),
                        retencion.getPorcentajeRetener().toString()+" %",
                        retencion.getCodigoRetencionSri(),
                        retencion.getValorRetenido().toString()
                ));
            }

            List<Object[]> dataRetencionCodigo = fs.obtenerRetencionesCodigo(proveedor, fechaInicio, fechaFin, sriRetencionIva, sriRetencionRenta, "2");
            List<ValorRetencionesData> datavc = new ArrayList<ValorRetencionesData>();
            for (Object[] obj : dataRetencionCodigo) {
                String r = (String) obj[0];
                BigDecimal valor = (BigDecimal) obj[1];
                datavc.add(new ValorRetencionesData(
                        r, valor.toString()
                ));
            }

            List<Object[]> dataRetencionCodigoRenta = fs.obtenerRetencionesCodigo(proveedor, fechaInicio, fechaFin, sriRetencionIva, sriRetencionRenta, "1");
            List<ValorRetencionesData> datav = new ArrayList<ValorRetencionesData>();
            for (Object[] obj : dataRetencionCodigoRenta) {
                String r = (String) obj[0];
                BigDecimal valor = (BigDecimal) obj[1];
                datav.add(new ValorRetencionesData(
                        r, valor.toString()
                ));
            }

            RecursosServiceIf service = ServiceFactory.getFactory().getRecursosServiceIf();
            InputStream pathSubReporte = RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER_COMPRA, "subreporte_retencion.jrxml"));
            JasperReport reportPiePagina = JasperCompileManager.compileReport(pathSubReporte);
            RecursosServiceIf service2 = ServiceFactory.getFactory().getRecursosServiceIf();
            InputStream pathSubReporte2 = RemoteInputStreamClient.wrap(service2.getResourceInputStream(RecursoCodefac.JASPER_COMPRA, "subreporte_retencion_renta.jrxml"));
            JasperReport reportPiePagina2 = JasperCompileManager.compileReport(pathSubReporte2);

            parameters.put("SUBREPORTE_RUTA", reportPiePagina);
            parameters.put("SUBREPORTE_RUTA_RENTA", reportPiePagina2);

            parameters.put("fechainicio", formatDate(fechaInicio, "yyyy-MM-dd"));
            parameters.put("fechafin", formatDate(fechaFin, "yyyy-MM-dd"));
            parameters.put("listaIva", datavc);
            parameters.put("listaRenta", datav);
            ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Reporte de retenciones");

        } catch (RemoteException ex) {
            Logger.getLogger(RetencionReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RetencionReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(RetencionReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        }

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

//    @Override
    public String getNombre() {
        return "Reporte Retenciones";
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

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
