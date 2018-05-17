package ec.com.codesoft.codefaclite.compra.model;

import ec.com.codesoft.codefaclite.compra.panel.RetencionReportePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.crm.busqueda.ProveedorBusquedaDialogo;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RetencionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionIvaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionRentaServiceIf;
import static ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha.fechaInicioMes;
import static ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha.hoy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

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

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        getDateFechaInicio().setDate(fechaInicioMes(hoy()));
        getDateFechaFin().setDate(hoy());

        getChkTodos().setSelected(true);
        if (getChkTodos().isSelected()) {
            proveedor = null;
            getTxtProveedor().setText("...");
            getBtnBuscarProveedor().setEnabled(false);
        }

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

                    Vector<String> titulo = new Vector<>();
                    titulo.add("Proveedor");
                    titulo.add("Preimpreso");
                    titulo.add("Base imponble");
                    titulo.add("Prorcentaje");
                    titulo.add("Código");
                    titulo.add("valor");

                    DefaultTableModel modeloTablaRetenciones = new DefaultTableModel(titulo, 0);

                    RetencionServiceIf fs = ServiceFactory.getFactory().getRetencionServiceIf();
                    dataretencion = fs.obtenerRetencionesReporte(proveedor, fechaInicio, fechaFin, sriRetencionIva, sriRetencionRenta);
                    for (RetencionDetalle retencion : dataretencion) {
                        Vector<String> fila = new Vector<String>();
                        fila.add(retencion.getRetencion().getProveedor().getRazonSocial());
                        fila.add(retencion.getRetencion().getCompra().getPreimpreso());
                        fila.add(retencion.getBaseImponible().toString());
                        fila.add(retencion.getPorcentajeRetener().toString());
                        fila.add(retencion.getCodigoSri() + " - " + retencion.getCodigoRetencionSri());
                        fila.add(retencion.getValorRetenido().toString());

                        modeloTablaRetenciones.addRow(fila);

                    }
                    getTblRetenciones().setModel(modeloTablaRetenciones);

                    Vector<String> titulo2 = new Vector<>();
                    titulo2.add("Código");
                    titulo2.add("Valor");
                    DefaultTableModel modeloTablaRetIva = new DefaultTableModel(titulo2, 0);
                    List<Object[]> dataRetencionCodigo = fs.obtenerRetencionesCodigo(proveedor, fechaFin, fechaFin, sriRetencionIva, sriRetencionRenta,"C");
                    for (Object[] obj : dataRetencionCodigo) {
                        Vector<String> fila = new Vector<String>();
                        String r = (String) obj[0];
                        BigDecimal valor = (BigDecimal) obj[1];
                        fila.add(r);
                        fila.add(valor.toString());
                        modeloTablaRetIva.addRow(fila);
                        System.out.println("PRIMER CICLO "+r);
                    }
                    getTblRetIva().setModel(modeloTablaRetIva);

                    
                    Vector<String> titulo3 = new Vector<>();
                    titulo3.add("Código");
                    titulo3.add("Valor");
                    DefaultTableModel modeloTablaRetRenta = new DefaultTableModel(titulo3, 0);
                    List<Object[]> dataRetencionCodigoRenta = fs.obtenerRetencionesCodigo(proveedor, fechaFin, fechaFin, sriRetencionIva, sriRetencionRenta,"R");
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        return "Reporte Retenciones";
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

}
