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
    
    //TODO: Falta hacer 2 notas de credito para los calculos de los anulados    
    
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
    //private Map<String,BigDecimal> mapTotales;
    
    private List<ReporteFacturaData> data;

    private TotalSumatoria total;    //en este campo se van a calcular el valor total de todos los comprobantes     
    private TotalSumatoria totalAnulados; //en esta variable se van a calcular los totales de los anulados
    private TotalSumatoria totalNotasCredito; //en este campo se van a guardar los valores solo de las notas de credito
    
    
    
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
        //this.mapTotales = new HashMap<String,BigDecimal>();
        this.data = new ArrayList<ReporteFacturaData>();
    }
    
    private void buildMapTotales()
    {
        total=new TotalSumatoria();
        totalAnulados=new TotalSumatoria();
        totalNotasCredito=new TotalSumatoria();
        
        //return this.mapTotales;
    }
    
    public void generarReporte() {
        try {
            buildMapTotales();
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
                        
                        BigDecimal descuentoConImpuestos = (factura.getDescuentoImpuestos() != null) ? factura.getDescuentoImpuestos() : BigDecimal.ZERO;
                        BigDecimal descuentoSinImpuestos = (factura.getDescuentoSinImpuestos() != null) ? factura.getDescuentoSinImpuestos() : BigDecimal.ZERO;
                        
                                               
                        total.addSubtotalSinImpuesto(factura.getSubtotalSinImpuestos());
                        total.addSubtotalConImpuesto(factura.getSubtotalImpuestos());
                        total.addsValorImpuesto(factura.getIva());
                        total.addDescuentoconImpuesto(descuentoConImpuestos);
                        total.addDescuentoSinImpuesto(descuentoSinImpuestos);   
                        
                        //Sumar solo si esta buscando todos los comprobantes del Sri y esta eliminado en el Sri
                        if(estadoEnum.equals(estadoEnum.ELIMINADO_SRI) && estadoFactura.equals(estadoEnum.TODOS_SRI))
                        {
                            totalAnulados.addSubtotalSinImpuesto(factura.getSubtotalSinImpuestos());
                            totalAnulados.addSubtotalConImpuesto(factura.getSubtotalImpuestos());
                            totalAnulados.addsValorImpuesto(factura.getIva());
                            totalAnulados.addDescuentoconImpuesto(descuentoConImpuestos);
                            totalAnulados.addDescuentoSinImpuesto(descuentoSinImpuestos);                            
                        }
                                                

                        if (afectarNotaCredito) {                        
                            NotaCredito notaCredito = verificarPorFactura(factura, dataNotCre);
                            if (notaCredito != null) {
                                //Calculo de los valores cuando existe una nota de credito
                                preimpresoNotaCreditoAfecta = notaCredito.getPreimpreso();
                                totalNotaCredito = notaCredito.getTotal();
                                totalMenosNotaCredito = factura.getTotal().subtract(notaCredito.getTotal());
                                
                                

                                //Solo hacer la sumatoria de los valores de las notas de credito
                                //TODO: Ver si se puede optimizar para sumar todo y luego restar de las notas de credito
                                totalNotasCredito.addSubtotalSinImpuesto(notaCredito.getSubtotalCero());
                                totalNotasCredito.addSubtotalConImpuesto(notaCredito.getSubtotalDoce());
                                totalNotasCredito.addsValorImpuesto(notaCredito.getValorIvaDoce());
                                totalNotasCredito.addDescuentoconImpuesto(notaCredito.getDescuentoImpuestos());
                                totalNotasCredito.addDescuentoSinImpuesto(notaCredito.getDescuentoSinImpuestos());


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
                        
                                                //Sumar solo si esta buscando todos los comprobantes del Sri y esta eliminado en el Sri
                        if(estadoEnum.equals(estadoEnum.ELIMINADO_SRI) && estadoFactura.equals(estadoEnum.TODOS_SRI))
                        {
                            totalAnulados.addSubtotalSinImpuesto(nota.getSubtotalSinImpuestos());
                            totalAnulados.addSubtotalConImpuesto(nota.getSubtotalImpuestos());
                            totalAnulados.addsValorImpuesto(nota.getIva());
                            totalAnulados.addDescuentoconImpuesto(nota.getDescuentoImpuestos());
                            totalAnulados.addDescuentoSinImpuesto(nota.getDescuentoSinImpuestos());                            
                        }
                        
                        
                        totalNotasCredito.addSubtotalSinImpuesto(nota.getSubtotalCero());
                        totalNotasCredito.addSubtotalConImpuesto(nota.getSubtotalDoce());
                        totalNotasCredito.addsValorImpuesto(nota.getIva());
                        totalNotasCredito.addDescuentoconImpuesto(nota.getDescuentoImpuestos());
                        totalNotasCredito.addDescuentoSinImpuesto(nota.getDescuentoSinImpuestos());
                        //TODO: ver si agregar el descuento
                        //acumdesc = acumdesc.add(nota.getFactura().getDescuentoImpuestos().add(nota.getFactura().getDescuentoSinImpuestos()));
                        
                    }
                    
                    break;
                    
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorReporteFactura.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /*
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
    }*/
    
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

    /*
    public Map<String, BigDecimal> getMapTotales() {
        return mapTotales;
    }*/
    
    public Map<String,Object> mapParametrosReportePdf()
    {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("fechainicio", (fechaInicio != null) ? dateFormat.format(fechaInicio) : "");
        parameters.put("fechafin", (fechaFin != null) ? dateFormat.format(fechaFin) : "");
        parameters.put("tipodocumento", documentoConsultaEnum.toString());
        parameters.put("cliente", persona);
        parameters.put("estadofactura", estadoFactura.getNombre());
        parameters.put("afectaNotaCredito",(documentoConsultaEnum.equals(documentoConsultaEnum.NOTA_CREDITO))?false:afectarNotaCredito);//Si el documento es nota de credito siempre mando descativado
        
        if(estadoFactura.equals(ComprobanteEntity.ComprobanteEnumEstado.TODOS_SRI))
        {
            parameters.putAll(totalAnulados.buildMap(EtiquetaReporteEnum.ANULADOS));
        }
        
       if(documentoConsultaEnum.equals(documentoConsultaEnum.VENTAS))
       {
           TotalSumatoria totalSinNotasCredito=total.subtract(totalNotasCredito);
           TotalSumatoria totalSinNotasCreditoYAnulados=totalSinNotasCredito.subtract(totalAnulados);
           parameters.putAll(total.buildMap(EtiquetaReporteEnum.TOTAL));
           parameters.putAll(totalSinNotasCreditoYAnulados.buildMap(EtiquetaReporteEnum.NOTA_CREDITO));
           
       }
       else if(documentoConsultaEnum.equals(documentoConsultaEnum.NOTA_CREDITO))
       {
           TotalSumatoria totalSinAnulados=totalNotasCredito.subtract(totalAnulados);
           parameters.putAll(totalSinAnulados.buildMap(EtiquetaReporteEnum.TOTAL));
       }
        
                
        return parameters;
    }
    
    public TotalSumatoria totalSinNotaCredito()
    {
        return total.subtract(totalNotasCredito);
    }
    

    
    public class TotalSumatoria
    {
        private BigDecimal subtotalSinImpuesto;
        private BigDecimal subtotalConImpuesto;
        private BigDecimal descuentoSinImpuesto;
        private BigDecimal descuentoconImpuesto;
        private BigDecimal valorImpuesto; //Valor del Iva

        public TotalSumatoria() {
            this.subtotalSinImpuesto = BigDecimal.ZERO;
            this.subtotalConImpuesto = BigDecimal.ZERO;
            this.descuentoSinImpuesto = BigDecimal.ZERO;
            this.descuentoconImpuesto = BigDecimal.ZERO;
            this.valorImpuesto=BigDecimal.ZERO;
        }
        
        

        public TotalSumatoria(BigDecimal subtotalSinImpuesto, BigDecimal subtotalConImpuesto, BigDecimal descuentoSinImpuesto, BigDecimal descuentoconImpuesto) {
            this.subtotalSinImpuesto = subtotalSinImpuesto;
            this.subtotalConImpuesto = subtotalConImpuesto;
            this.descuentoSinImpuesto = descuentoSinImpuesto;
            this.descuentoconImpuesto = descuentoconImpuesto;
        }
        
        public TotalSumatoria add(TotalSumatoria total)
        {
            TotalSumatoria totalTmp=new TotalSumatoria();
            totalTmp.descuentoSinImpuesto=this.descuentoSinImpuesto.add(total.descuentoSinImpuesto);
            totalTmp.descuentoconImpuesto=this.descuentoconImpuesto.add(total.descuentoconImpuesto);
            totalTmp.subtotalConImpuesto=this.subtotalConImpuesto.add(total.subtotalConImpuesto);
            totalTmp.subtotalSinImpuesto=this.subtotalSinImpuesto.add(total.subtotalSinImpuesto);
            totalTmp.valorImpuesto=this.valorImpuesto.add(total.valorImpuesto);
            return totalTmp;
        }
        
        public TotalSumatoria subtract(TotalSumatoria total)
        {
            TotalSumatoria totalTmp=new TotalSumatoria();
            totalTmp.descuentoSinImpuesto=this.descuentoSinImpuesto.subtract(total.descuentoSinImpuesto);
            totalTmp.descuentoconImpuesto=this.descuentoconImpuesto.subtract(total.descuentoconImpuesto);
            totalTmp.subtotalConImpuesto=this.subtotalConImpuesto.subtract(total.subtotalConImpuesto);
            totalTmp.subtotalSinImpuesto=this.subtotalSinImpuesto.subtract(total.subtotalSinImpuesto);
            totalTmp.valorImpuesto=this.valorImpuesto.subtract(total.valorImpuesto);
            return totalTmp;
        }

        public BigDecimal getSubtotalSinImpuesto() {
            return subtotalSinImpuesto;
        }

        public BigDecimal getSubtotalConImpuesto() {
            return subtotalConImpuesto;
        }
        
        public BigDecimal getSubtotalSinImpuestoMenosDescuento() {
            return subtotalSinImpuesto.subtract(descuentoSinImpuesto);
        }

        public BigDecimal getSubtotalConImpuestoMenosDescuento() {
            return subtotalConImpuesto.subtract(descuentoconImpuesto);
        }

        public BigDecimal getDescuentoSinImpuesto() {
            return descuentoSinImpuesto;
        }

        public BigDecimal getDescuentoconImpuesto() {
            return descuentoconImpuesto;
        }

        public BigDecimal getValorImpuesto() {
            return valorImpuesto;
        }
        
        
              
        public BigDecimal obtenerSubtotal()
        {
            return getSubtotalConImpuestoMenosDescuento().add(getSubtotalSinImpuestoMenosDescuento());
        }
        
        public BigDecimal obtenerTotal()
        {
            return obtenerSubtotal().add(valorImpuesto);
        }
        
        public BigDecimal obtenerTotalDescuentos()
        {
            return descuentoSinImpuesto.add(descuentoconImpuesto);
        }

        public void addSubtotalSinImpuesto(BigDecimal subtotalSinImpuesto) {
            this.subtotalSinImpuesto=this.subtotalSinImpuesto.add(subtotalSinImpuesto);
        }

        public void addSubtotalConImpuesto(BigDecimal subtotalConImpuesto) {
            this.subtotalConImpuesto=this.subtotalConImpuesto.add(subtotalConImpuesto);
        }

        public void addDescuentoSinImpuesto(BigDecimal descuentoSinImpuesto) {
            this.descuentoSinImpuesto=this.descuentoSinImpuesto.add(descuentoSinImpuesto);
        }

        public void addDescuentoconImpuesto(BigDecimal descuentoconImpuesto) {
            this.descuentoconImpuesto=this.descuentoconImpuesto.add(descuentoconImpuesto);
        }

        public void addsValorImpuesto(BigDecimal valorImpuesto) {
            this.valorImpuesto=this.valorImpuesto.add(valorImpuesto);
        }
        
        public Map<String,Object> buildMap(EtiquetaReporteEnum nombreEnum)
        {
            Map<String,Object> parametros=new HashMap<String,Object>();
            parametros.put(nombreEnum.obtenerNombreEtiqueta("subtotal"),getSubtotalSinImpuestoMenosDescuento().toString());
            parametros.put(nombreEnum.obtenerNombreEtiqueta("subtotaliva"),getSubtotalConImpuestoMenosDescuento().toString());
            parametros.put(nombreEnum.obtenerNombreEtiqueta("valoriva"),valorImpuesto.toString());
            parametros.put(nombreEnum.obtenerNombreEtiqueta("total"),obtenerTotal().toString());
            parametros.put(nombreEnum.obtenerNombreEtiqueta("totalsubtotales"),obtenerSubtotal().toString());
            parametros.put(nombreEnum.obtenerNombreEtiqueta("descuentos"),obtenerTotalDescuentos().toString());
            
            return parametros;
        }
    
    }
    
    public enum EtiquetaReporteEnum {
        ANULADOS("Anulado"),
        NOTA_CREDITO("NC"),
        TOTAL("");

        private EtiquetaReporteEnum(String prefijo) {
            this.prefijo = prefijo;
        }

        private String prefijo;

        public String obtenerNombreEtiqueta(String nombre) {
            return nombre + prefijo;
        }

    };
    
    
}
