/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
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

    //private List<Factura> datafact;
    //private List<NotaCredito> datafact2;
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
                generarReporte(true,false); //codigo que genera el reporte
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
    
    private void generarReporte(Boolean tabla,Boolean reporte)
    {
        try {
            
            BigDecimal acum = BigDecimal.ZERO, acumdoce = BigDecimal.ZERO, acumiva = BigDecimal.ZERO, acumdesc = BigDecimal.ZERO;
            ComprobanteEntity.ComprobanteEnumEstado estadoFactura = (ComprobanteEntity.ComprobanteEnumEstado) getCmbEstado().getSelectedItem();
            String estadoStr = estadoFactura.getEstado();
            
            if (getDateFechaInicio().getDate() != null) {
                fechaInicio = new Date(getDateFechaInicio().getDate().getTime());
            }
            if (getDateFechaFin().getDate() != null) {
                fechaFin = new Date(getDateFechaFin().getDate().getTime());
            }


            FacturacionServiceIf fs = ServiceFactory.getFactory().getFacturacionServiceIf();
            List<Factura> datafact = fs.obtenerFacturasReporte(persona, fechaInicio, fechaFin, estadoStr);
            NotaCreditoServiceIf nc = ServiceFactory.getFactory().getNotaCreditoServiceIf();
            List<NotaCredito> dataNotCre = nc.obtenerNotasReporte(persona, fechaInicio, fechaFin,estadoStr);
            
            List<ReporteFacturaData> data = new ArrayList<ReporteFacturaData>();

            DocumentosConsultarEnum documentoConsultaEnum = (DocumentosConsultarEnum) getCmbDocumento().getSelectedItem();

            switch (documentoConsultaEnum) {
                case VENTAS:
                    for (Factura factura : datafact) {
                        
                        BigDecimal totalMenosNotaCredito = BigDecimal.ZERO;
                        BigDecimal totalNotaCredito = BigDecimal.ZERO;
                        Boolean calcularTotalesSinNotasCredito=false;
                        String preimpresoNotaCreditoAfecta="";
                        String valorNotaCredito="";
                        
                        if (getChkAfectaNotaCredito().isSelected()) 
                        {
                            NotaCredito notaCredito = verificarPorFactura(factura, dataNotCre);
                            if (notaCredito != null) {
                                //Calculo de los valores cuando existe una nota de credito
                                preimpresoNotaCreditoAfecta=notaCredito.getPreimpreso();
                                totalNotaCredito=notaCredito.getTotal();
                                totalMenosNotaCredito = factura.getTotal().subtract(notaCredito.getTotal());
                                acum = acum.add(factura.getSubtotalSinImpuestos().subtract(notaCredito.getSubtotalCero()));
                                acumdoce = acumdoce.add(factura.getSubtotalImpuestos().subtract(notaCredito.getSubtotalDoce()));
                                acumiva = acumiva.add(factura.getIva().subtract(notaCredito.getValorIvaDoce()));
                                acumdesc = acumdesc.add(factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()));
                            } else {
                                //Calculo de los valores cuenado no existe nota de credito                                
                                calcularTotalesSinNotasCredito=true;
                            }
                        } else {                            
                            calcularTotalesSinNotasCredito=true;
                        }
                        
                        //Hacer el calculo normal sin notas de credito
                        if(calcularTotalesSinNotasCredito)
                        {
                            totalMenosNotaCredito = factura.getTotal();
                            acum = acum.add(factura.getSubtotalSinImpuestos());
                            acumdoce = acumdoce.add(factura.getSubtotalImpuestos());
                            acumiva = acumiva.add(factura.getIva());
                            acumdesc = acumdesc.add(factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()));
                        }

                        
                        data.add(new ReporteFacturaData(
                                factura.getPreimpreso(),
                                dateFormat.format(factura.getFechaEmision()),
                                factura.getCliente().getIdentificacion(),
                                factura.getCliente().getRazonSocial(),
                                factura.getCliente().getNombreLegal(),
                                (factura.getEstadoEnum()!=null)?factura.getEstadoEnum().getNombre():"Sin estado",
                                (factura.getTipoFacturacionEnum()!=null)?factura.getTipoFacturacionEnum().getNombre():"Sin definir",
                                (factura.getCodigoDocumentoEnum()!=null)?factura.getCodigoDocumentoEnum().getNombre():"",
                                factura.getSubtotalImpuestos().toString(),
                                factura.getSubtotalSinImpuestos().toString(),
                                factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()).toString(),
                                factura.getIva().toString(),
                                factura.getTotal().toString(),
                                totalNotaCredito.toString(),
                                preimpresoNotaCreditoAfecta,
                                totalMenosNotaCredito.toString()
                                
                        ));


                    }

                    break;

                case NOTA_CREDITO:
                    for (NotaCredito nota : dataNotCre) {
                        Vector<String> fila = new Vector<String>();
                        ComprobanteEntity.ComprobanteEnumEstado estadoEnum = ComprobanteEntity.ComprobanteEnumEstado.getEnum(nota.getEstado());
                        
                        data.add(new ReporteFacturaData(
                                nota.getPreimpreso(),
                                dateFormat.format(nota.getFechaEmision()),
                                nota.getCliente().getIdentificacion(),
                                nota.getCliente().getRazonSocial(),
                                nota.getCliente().getNombreLegal(),
                                (nota.getEstadoEnum()!=null)?nota.getEstadoEnum().getNombre():"Sin estado",
                                (nota.getTipoFacturacionEnum()!=null)?nota.getTipoFacturacionEnum().getNombre():"",
                                (nota.getCodigoDocumentoEnum()!=null)?nota.getCodigoDocumentoEnum().getNombre():"",
                                nota.getSubtotalDoce().toString(),
                                nota.getSubtotalCero().toString(),
                                nota.getFactura().getDescuentoImpuestos().add(nota.getFactura().getDescuentoSinImpuestos()).toString(),
                                nota.getValorIvaDoce().toString(),
                                nota.getTotal().toString(),
                                "0",
                                nota.getFactura().getPreimpreso(),
                                nota.getTotal().toString()
                        ));

                        acum = acum.add(nota.getSubtotalCero());
                        acumdoce = acumdoce.add(nota.getSubtotalDoce());
                        acumiva = acumiva.add(nota.getValorIvaDoce());
                        acumdesc = acumdesc.add(nota.getFactura().getDescuentoImpuestos().add(nota.getFactura().getDescuentoSinImpuestos()));

                        
                    }

                    break;

            }
            
            if (tabla) 
            {
                //Si genera en la tabla creo los datos de las filas
                DefaultTableModel modeloTablaFacturas = construirModelTabla();
                for (ReporteFacturaData reporteFacturaData : data) {
                        Vector<String> fila = new Vector<String>();
                        fila.add(reporteFacturaData.getNumeroFactura());
                        fila.add(reporteFacturaData.getReferencia());
                        fila.add(reporteFacturaData.getFechaFactura());
                        fila.add(reporteFacturaData.getIdentificacionCliente());
                        fila.add(reporteFacturaData.getRazonSocialCliente());
                        fila.add(reporteFacturaData.getNombreLegalCliente());
                        fila.add(reporteFacturaData.getDocumento()); //Aqui debe ir el tipo de documento
                        fila.add(reporteFacturaData.getEstadoFactura());
                        fila.add(reporteFacturaData.getTipoEmision()); //Falta implementar
                        fila.add(reporteFacturaData.getSubtotalDoceFactura());
                        fila.add(reporteFacturaData.getSubtotalCeroFactura());
                        fila.add(reporteFacturaData.getDescFactura());
                        fila.add(reporteFacturaData.getIvaDoceFactura());
                        fila.add(reporteFacturaData.getTotalFactura());
                        fila.add(reporteFacturaData.getValorAfecta());
                        fila.add(reporteFacturaData.getTotalFinal());                        
                        modeloTablaFacturas.addRow(fila);
                }
                
                getTblDocumentos().setModel(modeloTablaFacturas);
                getLblSubtotal0().setText(acum.toString());
                getLblSubtotal12().setText(acumdoce.toString());
                BigDecimal subtotal = acum.add(acumdoce);
                getLblSubtotalSinImpuesto().setText(subtotal.toString());
                getLblTotalDescuento().setText(acumdesc.toString());
                getLblIva12().setText(acumiva.toString());
                BigDecimal total = acum.add(acumdoce).add(acumiva);
                getTxtValorTotal().setText(total.toString());
            }
            
            //Si crea un reporte paso los datos al jasper
            if(reporte)
            {
                String estadoText = estadoFactura.getNombre();
                InputStream path = RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("reporte_documentos.jrxml");
                String cliente = "";
                if (persona == null) {
                    cliente = "TODOS";
                } else {
                    cliente = persona.getRazonSocial();
                }
                parameters.put("fechainicio", fechainicio);
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

                ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Reporte Documentos ",OrientacionReporteEnum.HORIZONTAL);
            
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private NotaCredito verificarPorFactura(Factura factura,List<NotaCredito> notasCredito) {
        for (NotaCredito notaCredito : notasCredito) {
            if (notaCredito.getFactura().equals(factura)) {
                return notaCredito;
            }
        }
        return null;
    }
    
    private DefaultTableModel construirModelTabla() {
        Vector<String> titulo = new Vector<>();
        titulo.add("Preimpreso");
        titulo.add("Referencia");
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
        titulo.add("Total 1");
        titulo.add("Valor Afecta");
        titulo.add("Total 2");
        
        DefaultTableModel modeloTablaFacturas = new DefaultTableModel(titulo, 0);
        return modeloTablaFacturas;
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
        generarReporte(false,true);        
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
