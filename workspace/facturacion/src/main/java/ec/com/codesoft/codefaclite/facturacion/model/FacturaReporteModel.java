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
import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.entity.enumerados.FacturaEnumEstado;
import ec.com.codesoft.codefaclite.servidor.service.FacturacionService;
import ec.com.codesoft.codefaclite.test.TipoBusquedaEnum;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public FacturaReporteModel() {
        initListener();
    }

    private void initListener() {
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
                };
            }
        });

        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fechainicio = dateFormat.format(getDateFechaInicio().getDate());
                String fechafin = dateFormat.format(getDateFechaFin().getDate());
                TipoBusquedaEnum estadoSeleccionado = (TipoBusquedaEnum) getCmbTipo().getSelectedItem();

                Date sqlDate = new Date(getDateFechaInicio().getDate().getTime());
                Date sqlDate2 = new Date(getDateFechaFin().getDate().getTime());

//if(estadoSeleccionado.equals(TipoBusquedaEnum.TODOS))
                System.out.println("ESTA ES LA FECHA INICIO " + fechainicio);
                System.out.println("ESTA ES LA FECHA FIN " + fechafin);
                System.out.println("estado " + estadoSeleccionado.getTipo());

                Vector<String> titulo = new Vector<>();
                titulo.add("Preimpreso");
                titulo.add("Fecha");
                titulo.add("Cliente");
                titulo.add("valor");

                modeloTablaFacturas = new DefaultTableModel(titulo, 0);

                FacturacionService fs = new FacturacionService();
                List<Factura> datafact = fs.obtenerFacturasReporte(persona.getIdCliente(), estadoSeleccionado.getTipo(), sqlDate, sqlDate2);
                for (Factura factura : datafact) {
                    Vector<String> fila = new Vector<String>();
                    fila.add(factura.getPreimpreso());
                    fila.add(dateFormat.format(factura.getFechaFactura()));
                    fila.add(factura.getCliente().getRazonSocial());
                    fila.add(String.valueOf(factura.getTotal()));
                    modeloTablaFacturas.addRow(fila);
                }
                getTblDocumentos().setModel(modeloTablaFacturas);

            }
        });
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
        String fechainicio = dateFormat.format(getDateFechaInicio().getDate());
        String fechafin = dateFormat.format(getDateFechaFin().getDate());
        TipoBusquedaEnum estadoSeleccionado = (TipoBusquedaEnum) getCmbTipo().getSelectedItem();

        String path = RecursoCodefac.JASPER_FACTURACION.getResourcePath("reporte_documentos.jrxml");
        FacturacionService fs = new FacturacionService();

        List<ReporteFacturaData> data = new ArrayList<ReporteFacturaData>();

        Date sqlDate = new Date(getDateFechaInicio().getDate().getTime());
        Date sqlDate2 = new Date(getDateFechaFin().getDate().getTime());

        List<Factura> datafact = fs.obtenerFacturasReporte(persona.getIdCliente(), estadoSeleccionado.getTipo(), sqlDate, sqlDate2);
        for (Factura factura : datafact) {
            data.add(new ReporteFacturaData(factura.getPreimpreso(), dateFormat.format(factura.getFechaFactura()), factura.getCliente().getRazonSocial(), String.valueOf(factura.getTotal())));
        }

        parameters.put("fechainicio", fechainicio);
        parameters.put("fechafin", fechafin);

        // parameters.put("fechainicio", getDateFechaInicio().getDate());
        System.out.println(session.getUsuario().getClave());
        /* parameters.put("subreporte","C:\\Users\\Carlos\\Documents\\GitHub\\codefac-lite\\workspace\\recursos\\src\\main\\resources\\reportes\\crm\\");
         */
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

}
