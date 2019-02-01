/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobante.reporte;

import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NotaCreditoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

/**
 *
 * @author Carlos
 */
public class ControladorReporteFactura {
    
    private static final String ACUMULADO="acum";
    private static final String ACUMULADO_DOCE="acumdoce";
    private static final String ACUMULADO_IVA="acumiva";
    private static final String ACUMULADO_DESCUENTO="acumdesc";
    
    private static final String ACUMULADO_ANULADO="acumAnulado";
    private static final String ACUMULADO_DOCE_ANULADO="acumdoceAnulado";
    private static final String ACUMULADO_IVA_ANULADO="acumivaAnulado";
    private static final String ACUMULADO_DESCUENTO_ANULADO="acumdescAnulado";
    
    
    private Persona persona;
    private Date fechaInicio;
    private Date fechaFin;
    private ComprobanteEntity.ComprobanteEnumEstado estadoFactura;
    private Boolean filtrarReferidos;
    private Persona referido;
    private Boolean reporteAgrupado;
    private Boolean afectarNotaCredito;
    private DocumentosConsultarEnum documentoConsultaEnum;
    
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Map<String,BigDecimal> mapTotales;
    
    private List<ReporteFacturaData> data;
    
    
    public ControladorReporteFactura(Persona persona, Date fechaInicio, Date fechaFin, ComprobanteEntity.ComprobanteEnumEstado estadoFactura, Boolean filtrarReferidos, Persona referido, Boolean reporteAgrupado, Boolean afectarNotaCredito, DocumentosConsultarEnum documentoConsultaEnum) {
        this.persona = persona;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estadoFactura = estadoFactura;
        this.filtrarReferidos = filtrarReferidos;
        this.referido = referido;
        this.reporteAgrupado = reporteAgrupado;
        this.afectarNotaCredito = afectarNotaCredito;
        this.documentoConsultaEnum = documentoConsultaEnum;
        this.mapTotales = new HashMap<String,BigDecimal>();
        this.data = new ArrayList<ReporteFacturaData>();
    }
    
    private Map<String,BigDecimal> buildMapTotales()
    {
        this.mapTotales = new HashMap<String,BigDecimal>();
        this.mapTotales.put(ACUMULADO, BigDecimal.ZERO);
        this.mapTotales.put(ACUMULADO_DOCE, BigDecimal.ZERO);
        this.mapTotales.put(ACUMULADO_IVA, BigDecimal.ZERO);
        this.mapTotales.put(ACUMULADO_DESCUENTO, BigDecimal.ZERO);        
        
        this.mapTotales.put(ACUMULADO_ANULADO, BigDecimal.ZERO);
        this.mapTotales.put(ACUMULADO_DOCE_ANULADO, BigDecimal.ZERO);
        this.mapTotales.put(ACUMULADO_IVA_ANULADO, BigDecimal.ZERO);
        this.mapTotales.put(ACUMULADO_DESCUENTO_ANULADO, BigDecimal.ZERO);
        return this.mapTotales;
    }
    
    public void generarReporte() {
        try {
            this.mapTotales=buildMapTotales();
            FacturacionServiceIf fs = ServiceFactory.getFactory().getFacturacionServiceIf();
            List<Factura> datafact = fs.obtenerFacturasReporte(persona, fechaInicio, fechaFin, estadoFactura, filtrarReferidos, referido, reporteAgrupado);
            
            //DocumentosConsultarEnum documentoConsultaEnum = (DocumentosConsultarEnum) getCmbDocumento().getSelectedItem();
            NotaCreditoServiceIf nc = ServiceFactory.getFactory().getNotaCreditoServiceIf();
            List<NotaCredito> dataNotCre = null;
            
            if (documentoConsultaEnum.equals(DocumentosConsultarEnum.VENTAS)) {
                dataNotCre = nc.obtenerNotasReporte(persona, null, null, ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO);
            } else {
                dataNotCre = nc.obtenerNotasReporte(persona, fechaInicio, fechaFin, estadoFactura);
            }
            
            data = new ArrayList<ReporteFacturaData>();
            
            switch (documentoConsultaEnum) {
                case VENTAS:
                    for (Factura factura : datafact) {
                        
                        BigDecimal totalMenosNotaCredito = BigDecimal.ZERO;
                        BigDecimal totalNotaCredito = BigDecimal.ZERO;
                        Boolean calcularTotalesSinNotasCredito = false;
                        String preimpresoNotaCreditoAfecta = "";
                        String valorNotaCredito = "";
                        ComprobanteEntity.ComprobanteEnumEstado estadoEnum= factura.getEstadoEnum();
                        
                        //if (getChkAfectaNotaCredito().isSelected()) {
                        if (afectarNotaCredito) {                        
                            NotaCredito notaCredito = verificarPorFactura(factura, dataNotCre);
                            if (notaCredito != null) {
                                //Calculo de los valores cuando existe una nota de credito
                                preimpresoNotaCreditoAfecta = notaCredito.getPreimpreso();
                                totalNotaCredito = notaCredito.getTotal();
                                totalMenosNotaCredito = factura.getTotal().subtract(notaCredito.getTotal());
                                
                                
                                agregarValorTotal(ACUMULADO, factura.getSubtotalSinImpuestos().subtract(notaCredito.getSubtotalCero()),estadoEnum);
                                //acum = acum.add(factura.getSubtotalSinImpuestos().subtract(notaCredito.getSubtotalCero()));
                                agregarValorTotal(ACUMULADO_DOCE, factura.getSubtotalImpuestos().subtract(notaCredito.getSubtotalDoce()),estadoEnum);
                                //acumdoce = acumdoce.add(factura.getSubtotalImpuestos().subtract(notaCredito.getSubtotalDoce()));
                                agregarValorTotal(ACUMULADO_IVA, factura.getIva().subtract(notaCredito.getValorIvaDoce()),estadoEnum);
                                //acumiva = acumiva.add(factura.getIva().subtract(notaCredito.getValorIvaDoce()));
                                agregarValorTotal(ACUMULADO_DESCUENTO, factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()),estadoEnum);
                                //acumdesc = acumdesc.add(factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()));
                            } else {
                                //Calculo de los valores cuenado no existe nota de credito
                                calcularTotalesSinNotasCredito = true;
                            }
                        } else {
                            calcularTotalesSinNotasCredito = true;
                        }
                        
                        //Hacer el calculo normal sin notas de credito
                        if (calcularTotalesSinNotasCredito) {
                            totalMenosNotaCredito = factura.getTotal();
                            //ComprobanteEntity.ComprobanteEnumEstado estadoEnum= factura.getEstadoEnum();
                            
                            agregarValorTotal(ACUMULADO, factura.getSubtotalSinImpuestos(),estadoEnum);
                            //acum = acum.add(factura.getSubtotalSinImpuestos());
                            agregarValorTotal(ACUMULADO_DOCE, factura.getSubtotalImpuestos(),estadoEnum);
                            //acumdoce = acumdoce.add(factura.getSubtotalImpuestos());
                            agregarValorTotal(ACUMULADO_IVA, factura.getIva(),estadoEnum);
                            //acumiva = acumiva.add(factura.getIva());
                            BigDecimal descuentoSinImpuestos = (factura.getDescuentoSinImpuestos() != null) ? factura.getDescuentoSinImpuestos() : BigDecimal.ZERO;
                            agregarValorTotal(ACUMULADO_DESCUENTO, factura.getDescuentoImpuestos().add(descuentoSinImpuestos),estadoEnum);
                            //acumdesc = acumdesc.add(factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()));
                        }
                        
                        BigDecimal valorComision = BigDecimal.ZERO;
                        if (factura.getReferido() != null) {
                            BigDecimal porcentajeComision = (factura.getReferido().getContactoClientePorcentaje() != null) ? factura.getReferido().getContactoClientePorcentaje() : BigDecimal.ZERO;
                            
                            //Cuando los valores son distintos es porque aplica una nota de credito
                            if (factura.getTotal().compareTo(totalMenosNotaCredito) != 0) {
                                BigDecimal porcentajeImpuestosOriginal = factura.getIva().divide(factura.getTotal(), 8, BigDecimal.ROUND_HALF_UP); //Obtenego cual es la proporcion del iva original , por si afecta un nota de credito valor a calcular la comision con esta proporicion
                                valorComision = totalMenosNotaCredito.divide(porcentajeImpuestosOriginal.add(BigDecimal.ONE), 2, BigDecimal.ROUND_HALF_UP);
                                valorComision = valorComision.multiply(porcentajeComision.divide(new BigDecimal("100"), 8, BigDecimal.ROUND_HALF_UP));
                                valorComision = valorComision.setScale(2, BigDecimal.ROUND_HALF_UP);
                            } else { //Este caso es cuando no aplica ninguna nota de credito
                                valorComision = factura.getTotal().subtract(factura.getIva());
                                valorComision = valorComision.multiply(porcentajeComision.divide(new BigDecimal("100"), 8, BigDecimal.ROUND_HALF_UP));
                                valorComision = valorComision.setScale(8, BigDecimal.ROUND_HALF_UP);
                            }
                        }
                        
                        ReporteFacturaData reporteData = new ReporteFacturaData(
                                factura.getPreimpreso(),
                                dateFormat.format(factura.getFechaEmision()),
                                factura.getCliente().getIdentificacion(),
                                factura.getCliente().getRazonSocial(),
                                ((factura.getCliente().getNombreLegal()) != null) ? factura.getCliente().getNombreLegal() : "",
                                (factura.getEstadoEnum() != null) ? factura.getEstadoEnum().getNombre() : "Sin estado",
                                (factura.getTipoFacturacionEnum() != null) ? factura.getTipoFacturacionEnum().getNombre() : "Sin definir",
                                (factura.getCodigoDocumentoEnum() != null) ? factura.getCodigoDocumentoEnum().getNombre() : "",
                                factura.getSubtotalImpuestos().toString(),
                                factura.getSubtotalSinImpuestos().toString(),
                                factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()).toString(),
                                factura.getIva().toString(),
                                factura.getTotal().toString(),
                                totalNotaCredito.toString(),
                                preimpresoNotaCreditoAfecta,
                                totalMenosNotaCredito.toString(),
                                (factura.getReferido() != null) ? factura.getReferido().getRazonSocial() : "",
                                (factura.getReferido() != null) ? factura.getReferido().getIdentificacion() : "",
                                (factura.getReferido() != null) ? factura.getReferido().getContactoClientePorcentaje().toString() : "0",
                                valorComision.toString(),
                                factura.getClaveAcceso()
                        );
                        
                        //UtilidadesFecha.formatoDiaMesAño(fechaFin);
                        reporteData.setFechaMaximaPago((factura.getFechaVencimiento() != null) ? UtilidadesFecha.formatoDiaMesAño(factura.getFechaVencimiento()) : "");
                        reporteData.setVendedor((factura.getVendedor() != null) ? factura.getVendedor().getNombresCompletos() : "");
                        
                        reporteData.mostrarReferido = filtrarReferidos; //Variables para saber si se debe mostrar las personas que le refieren
                        data.add(reporteData);
                        
                    }
                    
                    break;
                    
                case NOTA_CREDITO:
                    for (NotaCredito nota : dataNotCre) {
                        Vector<String> fila = new Vector<String>();
                        ComprobanteEntity.ComprobanteEnumEstado estadoEnum = ComprobanteEntity.ComprobanteEnumEstado.getEnum(nota.getEstado());
                        
                        ReporteFacturaData reporteData = new ReporteFacturaData(
                                nota.getPreimpreso(),
                                dateFormat.format(nota.getFechaEmision()),
                                nota.getCliente().getIdentificacion(),
                                nota.getCliente().getRazonSocial(),
                                (nota.getCliente().getNombreLegal() != null) ? nota.getCliente().getNombreLegal() : "",
                                (nota.getEstadoEnum() != null) ? nota.getEstadoEnum().getNombre() : "Sin estado",
                                (nota.getTipoFacturacionEnum() != null) ? nota.getTipoFacturacionEnum().getNombre() : "",
                                (nota.getCodigoDocumentoEnum() != null) ? nota.getCodigoDocumentoEnum().getNombre() : "",
                                nota.getSubtotalDoce().toString(),
                                nota.getSubtotalCero().toString(),
                                "0", //Ver si es necesario calcular los descuentos en las notas de credito
                                nota.getValorIvaDoce().toString(),
                                nota.getTotal().toString(),
                                "0",
                                nota.getNumDocModificado(),
                                nota.getTotal().toString(),
                                (nota.getRazonSocial() != null) ? nota.getRazonSocial() : "",
                                (nota.getIdentificacion() != null) ? nota.getIdentificacion() : "",
                                "0",
                                "0",//TODO: Revisar porque en esta parte me late que no necesito calcular el iva
                                nota.getClaveAcceso()
                        );
                        
                        reporteData.mostrarReferido = filtrarReferidos; //Variables para saber si se debe mostrar las personas que le refieren
                        data.add(reporteData);
                        
                        agregarValorTotal(ACUMULADO, nota.getSubtotalCero(),estadoEnum);
                        //acum = acum.add(nota.getSubtotalCero());
                        agregarValorTotal(ACUMULADO_DOCE, nota.getSubtotalDoce(),estadoEnum);
                        //acumdoce = acumdoce.add(nota.getSubtotalDoce());
                        agregarValorTotal(ACUMULADO_IVA, nota.getValorIvaDoce(),estadoEnum);
                        //acumiva = acumiva.add(nota.getValorIvaDoce());
                        agregarValorTotal(ACUMULADO_DESCUENTO, BigDecimal.ZERO,estadoEnum); //todo: ver si agregar el descuento
                        //acumdesc = acumdesc.add(nota.getFactura().getDescuentoImpuestos().add(nota.getFactura().getDescuentoSinImpuestos()));
                        
                    }
                    
                    break;
                    
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorReporteFactura.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void agregarValorTotal(String nombre, BigDecimal valor,ComprobanteEntity.ComprobanteEnumEstado estado)                
    {
        if(estadoFactura.equals(ComprobanteEntity.ComprobanteEnumEstado.TODOS_SRI))
        {
            if(estado.equals(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO_SRI))
            {
                nombre+="Anulado";
            }
        }
        
        BigDecimal valorTmp = mapTotales.get(nombre);
        if (valorTmp == null) {
            mapTotales.put(nombre, valor);
        } else {
            valorTmp = valorTmp.add(valor);
            mapTotales.put(nombre, valorTmp);
        }
    }
    
    private NotaCredito verificarPorFactura(Factura factura, List<NotaCredito> notasCredito) {
        for (NotaCredito notaCredito : notasCredito) {
            if (notaCredito.getFactura() != null && notaCredito.getFactura().equals(factura)) {
                return notaCredito;
            }
        }
        return null;
    }
    
    /**
     * TODO: ver si se puede unificar la forma de crear la cabecera con el de la pantalla de Factura Reporte
     * @return 
     */
    public Vector<String>  crearCabezeraTabla()
    {
        Vector<String> titulo = new Vector<>();
        titulo.add("Clave de Acceso");
        titulo.add("Fecha Max Pago");
        titulo.add("Vendedor");

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
        titulo.add("Valor Afecta");
        titulo.add("Total");
        return titulo;
    }
    
    public File obtenerArchivoReporteExcel()
    {
        try {
            Excel excel = new Excel();
            Vector<String> titulosVector = crearCabezeraTabla();
            String nombreCabeceras[] = titulosVector.toArray(new String[titulosVector.size()]); //Convertir en array
            excel.gestionarIngresoInformacionExcel(nombreCabeceras, data);
            return excel.obtenerArchivo();
        } catch (IOException ex) {
            Logger.getLogger(ControladorReporteFactura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ControladorReporteFactura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ControladorReporteFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private String obtenerTituloReportePdf()
    {
        String titulo = "Reporte ";
        if (documentoConsultaEnum.equals(DocumentosConsultarEnum.VENTAS)) {
            titulo += "Facturas";
        } else if (documentoConsultaEnum.equals(DocumentosConsultarEnum.NOTA_CREDITO)) {
            titulo += "Notas de Crédito";
        }
        return titulo;
    }
    
    public File obtenerArchivoReportePdf(InterfazComunicacionPanel panelPadre)
    {
        String titulo = obtenerTituloReportePdf();
        InputStream path=getReporte();
        //ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Reporte Documentos ", OrientacionReporteEnum.HORIZONTAL);
        String nombreArchivo=UtilidadesArchivos.generarNombreArchivoUnico("reporte","pdf");
        String pathGrabar="\\tmp\\"+nombreArchivo; //TODO: Camabiar por algun nombre en funcion de la fecha para que se unico y no genere problemas
        
        ReporteCodefac.generarReporteInternalFramePlantillaArchivo(path, mapParametrosReportePdf(), data, panelPadre,titulo, OrientacionReporteEnum.HORIZONTAL,FormatoHojaEnum.A4,pathGrabar);
        File file=new File(pathGrabar);
        if(file.exists())
        {
            return file;
        }
        return null;
    }
    
    
    public void obtenerReportePdf(InterfazComunicacionPanel panelPadre)
    {
        String titulo = obtenerTituloReportePdf();
        InputStream path=getReporte();
        //ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Reporte Documentos ", OrientacionReporteEnum.HORIZONTAL);
        //String nombreArchivo=UtilidadesArchivos.generarNombreArchivoUnico("reporte","pdf");
        //String pathGrabar="\\tmp\\"+nombreArchivo; //TODO: Camabiar por algun nombre en funcion de la fecha para que se unico y no genere problemas
        
        ReporteCodefac.generarReporteInternalFramePlantilla(path, mapParametrosReportePdf(), data, panelPadre,titulo, OrientacionReporteEnum.HORIZONTAL,FormatoHojaEnum.A4);
        
        
    }
    
    protected InputStream getReporte()
    {
        return RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("reporte_documentos.jrxml");
    }
   

    public List<ReporteFacturaData> getData() {
        return data;
    }

    public Map<String, BigDecimal> getMapTotales() {
        return mapTotales;
    }
    
    public Map<String,Object> mapParametrosReportePdf()
    {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("fechainicio", (fechaInicio != null) ? dateFormat.format(fechaInicio) : "");
        parameters.put("fechafin", (fechaFin != null) ? dateFormat.format(fechaFin) : "");
        parameters.put("tipodocumento", documentoConsultaEnum.toString());
        parameters.put("cliente", persona);
        parameters.put("subtotal", mapTotales.get(ACUMULADO).toString());
        parameters.put("subtotaliva", mapTotales.get(ACUMULADO_DOCE).toString());
        parameters.put("valoriva", mapTotales.get(ACUMULADO_IVA).toString());
        //BigDecimal total = acum.add(acumdoce).add(acumiva);
        BigDecimal total = mapTotales.get(ACUMULADO).add(mapTotales.get(ACUMULADO_DOCE).add(mapTotales.get(ACUMULADO_IVA)));
        parameters.put("total", total.toString());

        //BigDecimal subtotal = acum.add(acumdoce);
        BigDecimal subtotal = mapTotales.get(ACUMULADO).add(mapTotales.get(ACUMULADO_DOCE));
        parameters.put("totalsubtotales", subtotal.toString());
        parameters.put("descuentos", mapTotales.get(ACUMULADO_DESCUENTO).toString());
        parameters.put("estadofactura", estadoFactura.getNombre());
    
        
        
        
        ///AGREGAR LOS PARAMETROS CUANDO ES EL ESTADO TODOS SRI
        parameters.put("subtotalAnulado", mapTotales.get(ACUMULADO_ANULADO).toString());
        parameters.put("subtotalivaAnulado", mapTotales.get(ACUMULADO_DOCE_ANULADO).toString());
        parameters.put("valorivaAnulado", mapTotales.get(ACUMULADO_IVA_ANULADO).toString());
        BigDecimal totalAnulado = mapTotales.get(ACUMULADO_ANULADO).add(mapTotales.get(ACUMULADO_DOCE_ANULADO).add(mapTotales.get(ACUMULADO_IVA_ANULADO)));
        parameters.put("totalAnulado",totalAnulado.toString());
        BigDecimal subtotalAnulado = mapTotales.get(ACUMULADO_ANULADO).add(mapTotales.get(ACUMULADO_DOCE_ANULADO));
        parameters.put("totalsubtotalesAnulado",subtotalAnulado.toString());
        parameters.put("descuentosAnulado", mapTotales.get(ACUMULADO_DESCUENTO_ANULADO).toString());
                
        return parameters;
    }

    
    
}
