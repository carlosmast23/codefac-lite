/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobante.reporte;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NotaCreditoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public ControladorReporteFactura(Persona persona, Date fechaInicio, Date fechaFin, ComprobanteEntity.ComprobanteEnumEstado estadoFactura, Boolean filtrarReferidos, Persona referido, Boolean reporteAgrupado, Boolean afectarNotaCredito, DocumentosConsultarEnum documentoConsultaEnum, Map<String, BigDecimal> mapTotales, List<ReporteFacturaData> data) {
        this.persona = persona;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estadoFactura = estadoFactura;
        this.filtrarReferidos = filtrarReferidos;
        this.referido = referido;
        this.reporteAgrupado = reporteAgrupado;
        this.afectarNotaCredito = afectarNotaCredito;
        this.documentoConsultaEnum = documentoConsultaEnum;
        this.mapTotales = mapTotales;
        this.data = data;
    }
    
    
    
    public void generarReporte() {
        try {
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
                        
                        //if (getChkAfectaNotaCredito().isSelected()) {
                        if (afectarNotaCredito) {                        
                            NotaCredito notaCredito = verificarPorFactura(factura, dataNotCre);
                            if (notaCredito != null) {
                                //Calculo de los valores cuando existe una nota de credito
                                preimpresoNotaCreditoAfecta = notaCredito.getPreimpreso();
                                totalNotaCredito = notaCredito.getTotal();
                                totalMenosNotaCredito = factura.getTotal().subtract(notaCredito.getTotal());
                                
                                agregarValorTotal("acum", factura.getSubtotalSinImpuestos().subtract(notaCredito.getSubtotalCero()));
                                //acum = acum.add(factura.getSubtotalSinImpuestos().subtract(notaCredito.getSubtotalCero()));
                                agregarValorTotal("acumdoce", factura.getSubtotalImpuestos().subtract(notaCredito.getSubtotalDoce()));
                                //acumdoce = acumdoce.add(factura.getSubtotalImpuestos().subtract(notaCredito.getSubtotalDoce()));
                                agregarValorTotal("acumiva", factura.getIva().subtract(notaCredito.getValorIvaDoce()));
                                //acumiva = acumiva.add(factura.getIva().subtract(notaCredito.getValorIvaDoce()));
                                agregarValorTotal("acumdesc", factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()));
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
                            agregarValorTotal("acum", factura.getSubtotalSinImpuestos());
                            //acum = acum.add(factura.getSubtotalSinImpuestos());
                            agregarValorTotal("acumdoce", factura.getSubtotalImpuestos());
                            //acumdoce = acumdoce.add(factura.getSubtotalImpuestos());
                            agregarValorTotal("acumiva", factura.getIva());
                            //acumiva = acumiva.add(factura.getIva());
                            BigDecimal descuentoSinImpuestos = (factura.getDescuentoSinImpuestos() != null) ? factura.getDescuentoSinImpuestos() : BigDecimal.ZERO;
                            agregarValorTotal("acumdesc", factura.getDescuentoImpuestos().add(descuentoSinImpuestos));
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
                        
                        agregarValorTotal("acum", nota.getSubtotalCero());
                        //acum = acum.add(nota.getSubtotalCero());
                        agregarValorTotal("acumdoce", nota.getSubtotalDoce());
                        //acumdoce = acumdoce.add(nota.getSubtotalDoce());
                        agregarValorTotal("acumiva", nota.getValorIvaDoce());
                        //acumiva = acumiva.add(nota.getValorIvaDoce());
                        agregarValorTotal("acumdesc", BigDecimal.ZERO); //todo: ver si agregar el descuento
                        //acumdesc = acumdesc.add(nota.getFactura().getDescuentoImpuestos().add(nota.getFactura().getDescuentoSinImpuestos()));
                        
                    }
                    
                    break;
                    
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorReporteFactura.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void agregarValorTotal(String nombre, BigDecimal valor) {
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

    public List<ReporteFacturaData> getData() {
        return data;
    }

    
    
}
