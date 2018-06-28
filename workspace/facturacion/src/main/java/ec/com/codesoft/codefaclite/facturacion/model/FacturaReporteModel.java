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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoFacturacionEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NotaCreditoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import static ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URL;
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
        valoresIniciales();
        initListener();
    }

    private void initListener() {
        
        getCmbDocumento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DocumentosConsultarEnum documentoEnum=(DocumentosConsultarEnum) getCmbDocumento().getSelectedItem();
                
                //Si el documento es nota de credito desabilito el panel de control
                if(documentoEnum.equals(DocumentosConsultarEnum.NOTA_CREDITO))
                {
                    getPanelOpciones().setEnabled(false);
                    getChkAfectaNotaCredito().setEnabled(false);
                    getChkAfectaNotaDebito().setEnabled(false);
                }
                else
                {
                    getPanelOpciones().setEnabled(true);
                    getChkAfectaNotaCredito().setEnabled(true);
                    getChkAfectaNotaDebito().setEnabled(true);
                }
            }
        });
        
        //String texto= session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO).valor;
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
                try {
                    BigDecimal d = BigDecimal.ZERO;
                    BigDecimal acum = BigDecimal.ZERO, acumdoce = BigDecimal.ZERO, acumiva = BigDecimal.ZERO, acumdesc = BigDecimal.ZERO;
                    ComprobanteEntity.ComprobanteEnumEstado estadoFactura = (ComprobanteEntity.ComprobanteEnumEstado) getCmbEstado().getSelectedItem();
                    String estadoFact = estadoFactura.getEstado();
                    if (getDateFechaInicio().getDate() != null) {
                        fechaInicio = new Date(getDateFechaInicio().getDate().getTime());
                    }   if (getDateFechaFin().getDate() != null) {
                        fechaFin = new Date(getDateFechaFin().getDate().getTime());
                    }   System.out.println("ESTADO --------------->" + estadoFact);
                    Vector<String> titulo = new Vector<>();
                    titulo.add("Preimpreso");
                    titulo.add("Fecha");
                    titulo.add("Identificación");
                    titulo.add("Razón social");
                    titulo.add("Nombre legal");
                    titulo.add("Documento");
                    titulo.add("Estado");
                    titulo.add("Tipo");
                    titulo.add("Subtotal 12%");
                    titulo.add("Subtotal 0% ");
                    titulo.add("Descuentos");
                    titulo.add("IVA 12%");
                    titulo.add("Total");
                    modeloTablaFacturas = new DefaultTableModel(titulo, 0);
                    FacturacionServiceIf fs = ServiceFactory.getFactory().getFacturacionServiceIf();
                    datafact = fs.obtenerFacturasReporte(persona, fechaInicio, fechaFin, estadoFact);
                    NotaCreditoServiceIf nc = ServiceFactory.getFactory().getNotaCreditoServiceIf();
                    datafact2 = nc.obtenerNotasReporte(persona, fechaInicio, fechaFin);
                    
                    DocumentosConsultarEnum documentoConsultaEnum=(DocumentosConsultarEnum) getCmbDocumento().getSelectedItem();
                    
                    switch(documentoConsultaEnum)
                    {
                        case VENTAS:
                            for (Factura factura : datafact) {
                                Vector<String> fila = new Vector<String>();
                                ComprobanteEntity.ComprobanteEnumEstado ef = ComprobanteEntity.ComprobanteEnumEstado.getEnum(factura.getEstado());
                                DocumentoEnum tipoDocumentoEnum = DocumentoEnum.obtenerDocumentoPorCodigo(factura.getCodigoDocumento());
                                TipoFacturacionEnumEstado tf = TipoFacturacionEnumEstado.getEnumByEstado(factura.getTipoFacturacion());
                                fila.add(factura.getPreimpreso());
                                fila.add(dateFormat.format(factura.getFechaEmision()));
                                fila.add(factura.getCliente().getIdentificacion());
                                fila.add(factura.getCliente().getRazonSocial());
                                fila.add(factura.getCliente().getNombreLegal());
                                fila.add(tipoDocumentoEnum.getNombre());
                                fila.add(ef.getNombre());
                                fila.add(tf.getNombre());
                                fila.add(factura.getSubtotalImpuestos().toString());
                                fila.add(factura.getSubtotalSinImpuestos().toString());
                                fila.add(factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()).toString());
                                fila.add(factura.getIva().toString());

                                if (getChkAfectaNotaCredito().isSelected()) {
                                    NotaCredito notaCredito = verificarPorFactura(factura);
                                    if (notaCredito != null) {
                                        d = factura.getTotal().subtract(notaCredito.getTotal());
                                        acum = acum.add(factura.getSubtotalSinImpuestos().subtract(notaCredito.getSubtotalCero()));
                                        acumdoce = acumdoce.add(factura.getSubtotalImpuestos().subtract(notaCredito.getSubtotalDoce()));
                                        acumiva = acumiva.add(factura.getIva().subtract(notaCredito.getValorIvaDoce()));
                                        acumdesc = acumdesc.add(factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()));
                                    } else {
                                        d = factura.getTotal();
                                        acum = acum.add(factura.getSubtotalSinImpuestos());
                                        acumdoce = acumdoce.add(factura.getSubtotalImpuestos());
                                        acumiva = acumiva.add(factura.getIva());
                                        acumdesc = acumdesc.add(factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()));
                                    }
                                    fila.add(d.toString());
                                } else {
                                    fila.add(factura.getTotal().toString());
                                    acum = acum.add(factura.getSubtotalSinImpuestos());
                                    acumdoce = acumdoce.add(factura.getSubtotalImpuestos());
                                    acumiva = acumiva.add(factura.getIva());
                                    acumdesc = acumdesc.add(factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()));
                                }
                                modeloTablaFacturas.addRow(fila);
                            }

                            break;

                        case NOTA_CREDITO:
                            for (NotaCredito nota : datafact2) {
                                Vector<String> fila = new Vector<String>();
                                ComprobanteEntity.ComprobanteEnumEstado ef = ComprobanteEntity.ComprobanteEnumEstado.getEnum(nota.getEstado());

                                fila.add(nota.getPreimpreso());
                                fila.add(dateFormat.format(nota.getFechaEmision()));
                                fila.add(nota.getCliente().getIdentificacion());
                                fila.add(nota.getCliente().getRazonSocial());
                                fila.add(nota.getCliente().getNombreLegal());
                                fila.add((ef!=null)?ef.getNombre():""); //Todo: Verificar porque el estado de las notas de credito no se esta guardando
                                fila.add(nota.getSubtotalDoce().toString());
                                fila.add(nota.getSubtotalCero().toString());
                                fila.add(nota.getValorIvaDoce().toString());
                                fila.add(nota.getFactura().getDescuentoImpuestos().add(nota.getFactura().getDescuentoSinImpuestos()).toString());
                                fila.add(nota.getTotal().toString());

                                acum = acum.add(nota.getSubtotalCero());
                                acumdoce = acumdoce.add(nota.getSubtotalDoce());
                                acumiva = acumiva.add(nota.getValorIvaDoce());
                                acumdesc = acumdesc.add(nota.getFactura().getDescuentoImpuestos().add(nota.getFactura().getDescuentoSinImpuestos()));

                                modeloTablaFacturas.addRow(fila);
                            }

                            break;

                    }
                    
                    getTblDocumentos().setModel(modeloTablaFacturas);
                     
                    getTblDocumentos().setModel(modeloTablaFacturas);
                    getLblSubtotal0().setText(acum.toString());
                    getLblSubtotal12().setText(acumdoce.toString());
                    BigDecimal subtotal = acum.add(acumdoce);
                    getLblSubtotalSinImpuesto().setText(subtotal.toString());
                    getLblTotalDescuento().setText(acumdesc.toString());
                    getLblIva12().setText(acumiva.toString());
                    BigDecimal total = acum.add(acumdoce).add(acumiva);
                    getTxtValorTotal().setText(total.toString());
                } catch (RemoteException ex) {
                    Logger.getLogger(FacturaReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
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
        try {
            BigDecimal d = null;
            BigDecimal acum = BigDecimal.ZERO, acumdoce = BigDecimal.ZERO, acumiva = BigDecimal.ZERO, acumdesc = BigDecimal.ZERO;
            ComprobanteEntity.ComprobanteEnumEstado estadoFactura = (ComprobanteEntity.ComprobanteEnumEstado) getCmbEstado().getSelectedItem();
            String estadoFact = estadoFactura.getEstado();
            String estadoText = estadoFactura.getNombre();
            if (getDateFechaInicio().getDate() != null) {
                fechaInicio = new Date(getDateFechaInicio().getDate().getTime());
                fechainicio = dateFormat.format(getDateFechaInicio().getDate());
                
            }   if (getDateFechaFin().getDate() != null) {
                fechaFin = new Date(getDateFechaFin().getDate().getTime());
                fechafin = dateFormat.format(getDateFechaFin().getDate());
            }   
            InputStream path = RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("reporte_documentos.jrxml");
            FacturacionServiceIf fs = ServiceFactory.getFactory().getFacturacionServiceIf();
            datafact = fs.obtenerFacturasReporte(persona, fechaInicio, fechaFin, estadoFact);
            NotaCreditoServiceIf nc = ServiceFactory.getFactory().getNotaCreditoServiceIf();
            datafact2 = nc.obtenerNotasReporte(persona, fechaInicio, fechaFin);
            List<ReporteFacturaData> data = new ArrayList<ReporteFacturaData>();
            
            for (Factura factura : datafact) {
                if (getChkAfectaNotaCredito().isSelected()) {
                    NotaCredito notaCredito = verificarPorFactura(factura);
                    if (notaCredito != null) {
                        d = factura.getTotal().subtract(notaCredito.getTotal());
                        acum = acum.add(factura.getSubtotalSinImpuestos().subtract(notaCredito.getSubtotalCero()));
                        acumdoce = acumdoce.add(factura.getSubtotalImpuestos().subtract(notaCredito.getSubtotalDoce()));
                        acumiva = acumiva.add(factura.getIva().subtract(notaCredito.getValorIvaDoce()));
                        acumdesc = acumdesc.add(factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()));
                    } else {
                        d = factura.getTotal();
                        acum = acum.add(factura.getSubtotalSinImpuestos());
                        acumdoce = acumdoce.add(factura.getSubtotalImpuestos());
                        acumiva = acumiva.add(factura.getIva());
                        acumdesc = acumdesc.add(factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()));
                    }
                } else {
                    d = factura.getTotal();
                    acum = acum.add(factura.getSubtotalSinImpuestos());
                    acumdoce = acumdoce.add(factura.getSubtotalImpuestos());
                    acumiva = acumiva.add(factura.getIva());
                    acumdesc = acumdesc.add(factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()));
                }
                ComprobanteEntity.ComprobanteEnumEstado ef = ComprobanteEntity.ComprobanteEnumEstado.getEnum(factura.getEstado());

                data.add(new ReporteFacturaData(
                        factura.getPreimpreso(),
                        dateFormat.format(factura.getFechaEmision()),
                        factura.getCliente().getIdentificacion(),
                        factura.getCliente().getRazonSocial(),
                        factura.getCliente().getNombreLegal(),
                        ef.getNombre(),
                        factura.getSubtotalImpuestos().toString(),
                        factura.getSubtotalSinImpuestos().toString(),
                        factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()).toString(),
                        factura.getIva().toString(),
                        d.toString()
                ));
            }
            /* 
            else {
                for (NotaCredito nota : datafact2) {
                    acum = acum.add(nota.getSubtotalCero());
                    acumdoce = acumdoce.add(nota.getSubtotalDoce());
                    acumiva = acumiva.add(nota.getValorIvaDoce());
                    acumdesc = acumdesc.add(nota.getFactura().getDescuentoImpuestos().add(nota.getFactura().getDescuentoSinImpuestos()));
                    
                    ComprobanteEntity.ComprobanteEnumEstado ef = ComprobanteEntity.ComprobanteEnumEstado.getEnum(nota.getEstado());
                    data.add(new ReporteFacturaData(
                            nota.getPreimpreso(),
                            dateFormat.format(nota.getFechaEmision()),
                            nota.getCliente().getIdentificacion(),
                            nota.getCliente().getRazonSocial(),
                            nota.getCliente().getNombreLegal(),
                            ef.getNombre(),
                            nota.getSubtotalDoce().toString(),
                            nota.getSubtotalCero().toString(),
                            nota.getFactura().getDescuentoImpuestos().add(nota.getFactura().getDescuentoSinImpuestos()).toString(),
                            nota.getValorIvaDoce().toString(),
                            nota.getTotal().toString()
                    ));
                }
            }*/   
            
            String cliente = "";
            if (persona == null) {
                cliente = "TODOS";
            } else {
                cliente = persona.getRazonSocial();
            }   parameters.put("fechainicio", fechainicio);
            parameters.put("fechafin", fechafin);
            parameters.put("tipodocumento", String.valueOf("definir"));
            parameters.put("cliente", cliente);
            parameters.put("subtotal", acum.toString());
            parameters.put("subtotaliva", acumdoce.toString());
            parameters.put("valoriva", acumiva.toString());
            BigDecimal total = acum.add(acumdoce).add(acumiva);
            parameters.put("total", total.toString());
            BigDecimal subtotal = acum.add(acumdoce);
            parameters.put("totalsubtotales", subtotal.toString());
            parameters.put("descuentos", acumdesc.toString());
            parameters.put("estadofactura", estadoText);
            System.out.println(session.getUsuario().getClave());
            /*        data.add(new ReporteFacturaData("001-002-00001231"));
            data.add(new ReporteFacturaData("001-002-000012331"));
            */
            ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Reporte Documentos ");
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
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

//    @Override
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

    @Override
    public BuscarDialogoModel obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void valoresIniciales() {
        getDateFechaInicio().setDate(fechaInicioMes(hoy()));
        getDateFechaFin().setDate(hoy());

        getCmbEstado().addItem(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO);
        //getCmbEstado().addItem(FacturaEnumEstado.ANULADO_PARCIAL);
        //getCmbEstado().addItem(FacturaEnumEstado.ANULADO_TOTAL);
        getCmbEstado().addItem(ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR);
        getCmbEstado().addItem(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO);

        getChkTodos().setSelected(true);
        if (getChkTodos().isSelected()) {
            persona = null;
            getTxtCliente().setText("...");
            //getLblNombreCliente().setText("..");
            getBtnBuscarCliente().setEnabled(false);
        }
        
        //Agregar la lista de los elementos disponibles para buscar
        getCmbDocumento().removeAllItems();
        for (DocumentosConsultarEnum documentoEnum : DocumentosConsultarEnum.values()) {
            getCmbDocumento().addItem(documentoEnum);
        }
    }
    
    
    public enum DocumentosConsultarEnum
    {
        VENTAS("Ventas"),NOTA_CREDITO("Notas de Crédito");
        
        public String nombre;

        private DocumentosConsultarEnum(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public String toString() {
            return nombre;
        }
        
        
    }

}
