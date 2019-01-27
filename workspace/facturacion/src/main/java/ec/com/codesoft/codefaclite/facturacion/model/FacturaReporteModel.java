/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.report.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.views.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteFacturacionBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ReferidoBusquedaDialogo;
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
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import static ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha.*;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
import sun.nio.cs.ext.Big5;

/**
 *
 * @author CodesoftDesarrollo
 */
public class FacturaReporteModel extends FacturaReportePanel {

    protected Boolean filtrarReferidos;
    //protected Persona referido;
    
    private Persona persona;
    protected Persona referido;
    //Map<String, Object> parameters = new HashMap<String, Object>();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    private List<ReporteFacturaData> data;
    
    private Map<String,BigDecimal> mapTotales;

    //private List<Factura> datafact;
    //private List<NotaCredito> datafact2;
    //Date fechaInicio = null;
    //Date fechaFin = null;
    //String fechainicio = "";
    //String fechafin = "";

    public FacturaReporteModel() {
        valoresIniciales();
        initListener();
        super.validacionDatosIngresados=false;
    }

    private void initListener() {
        
        listenerBotones();
        listenerCombos();
        listenerChecks();
        
    }
    
    private void agregarValorTotal(String nombre,BigDecimal valor)
    {
        BigDecimal valorTmp=mapTotales.get(nombre);
        if(valorTmp==null)
        {
            mapTotales.put(nombre,valor);
        }
        else
        {
            valorTmp=valorTmp.add(valor);
            mapTotales.put(nombre,valorTmp);
        }
    }
    
    private void generarReporte()
    {
        try {
            
            Date fechaInicio=null;
            Date fechaFin =null;
            
            mapTotales=new HashMap<String,BigDecimal>();
            //igDecimal acum = BigDecimal.ZERO, acumdoce = BigDecimal.ZERO, acumiva = BigDecimal.ZERO, acumdesc = BigDecimal.ZERO;
            ComprobanteEntity.ComprobanteEnumEstado estadoFactura = (ComprobanteEntity.ComprobanteEnumEstado) getCmbEstado().getSelectedItem();
            String estadoStr = estadoFactura.getEstado();
            
            if (getDateFechaInicio().getDate() != null) {
                fechaInicio = new Date(getDateFechaInicio().getDate().getTime());
            }
            if (getDateFechaFin().getDate() != null) {
                fechaFin = new Date(getDateFechaFin().getDate().getTime());
            }
            


            FacturacionServiceIf fs = ServiceFactory.getFactory().getFacturacionServiceIf();
            List<Factura> datafact = fs.obtenerFacturasReporte(persona, fechaInicio, fechaFin, estadoFactura,filtrarReferidos,referido,getChkReporteAgrupadoReferido().isSelected());
            
            DocumentosConsultarEnum documentoConsultaEnum = (DocumentosConsultarEnum) getCmbDocumento().getSelectedItem();
            NotaCreditoServiceIf nc = ServiceFactory.getFactory().getNotaCreditoServiceIf();            
            List<NotaCredito> dataNotCre =null;
            
            if(documentoConsultaEnum.equals(DocumentosConsultarEnum.VENTAS))
            {
                dataNotCre=nc.obtenerNotasReporte(persona, null,null,ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO);
            }else
            {
                dataNotCre=nc.obtenerNotasReporte(persona, fechaInicio, fechaFin,estadoFactura);
            }
            
            data = new ArrayList<ReporteFacturaData>();

            
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
                                
                                agregarValorTotal("acum",factura.getSubtotalSinImpuestos().subtract(notaCredito.getSubtotalCero()));
                                //acum = acum.add(factura.getSubtotalSinImpuestos().subtract(notaCredito.getSubtotalCero()));
                                agregarValorTotal("acumdoce",factura.getSubtotalImpuestos().subtract(notaCredito.getSubtotalDoce()));
                                //acumdoce = acumdoce.add(factura.getSubtotalImpuestos().subtract(notaCredito.getSubtotalDoce()));
                                agregarValorTotal("acumiva",factura.getIva().subtract(notaCredito.getValorIvaDoce()));
                                //acumiva = acumiva.add(factura.getIva().subtract(notaCredito.getValorIvaDoce()));
                                agregarValorTotal("acumdesc",factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()));
                                //acumdesc = acumdesc.add(factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()));
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
                            agregarValorTotal("acum",factura.getSubtotalSinImpuestos());
                            //acum = acum.add(factura.getSubtotalSinImpuestos());
                            agregarValorTotal("acumdoce",factura.getSubtotalImpuestos());
                            //acumdoce = acumdoce.add(factura.getSubtotalImpuestos());
                            agregarValorTotal("acumiva",factura.getIva());
                            //acumiva = acumiva.add(factura.getIva());
                            BigDecimal descuentoSinImpuestos=(factura.getDescuentoSinImpuestos()!=null)?factura.getDescuentoSinImpuestos():BigDecimal.ZERO;
                            agregarValorTotal("acumdesc",factura.getDescuentoImpuestos().add(descuentoSinImpuestos));
                            //acumdesc = acumdesc.add(factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()));
                        }

                        BigDecimal valorComision=BigDecimal.ZERO;                                
                        if(factura.getReferido()!=null)
                        {
                            BigDecimal porcentajeComision=(factura.getReferido().getContactoClientePorcentaje()!=null)?factura.getReferido().getContactoClientePorcentaje():BigDecimal.ZERO;
                            
                            //Cuando los valores son distintos es porque aplica una nota de credito
                            if(factura.getTotal().compareTo(totalMenosNotaCredito)!=0)
                            {                                
                                BigDecimal porcentajeImpuestosOriginal=factura.getIva().divide(factura.getTotal(),8,BigDecimal.ROUND_HALF_UP); //Obtenego cual es la proporcion del iva original , por si afecta un nota de credito valor a calcular la comision con esta proporicion
                                valorComision=totalMenosNotaCredito.divide(porcentajeImpuestosOriginal.add(BigDecimal.ONE),2,BigDecimal.ROUND_HALF_UP);
                                valorComision=valorComision.multiply(porcentajeComision.divide(new BigDecimal("100"), 8, BigDecimal.ROUND_HALF_UP));
                                valorComision=valorComision.setScale(2,BigDecimal.ROUND_HALF_UP);
                            }
                            else
                            { //Este caso es cuando no aplica ninguna nota de credito
                                valorComision=factura.getTotal().subtract(factura.getIva());                            
                                valorComision=valorComision.multiply(porcentajeComision.divide(new BigDecimal("100"),8, BigDecimal.ROUND_HALF_UP));
                                valorComision=valorComision.setScale(8,BigDecimal.ROUND_HALF_UP);                            
                            }
                        }
                        
                        ReporteFacturaData reporteData=new ReporteFacturaData(
                                factura.getPreimpreso(),
                                dateFormat.format(factura.getFechaEmision()),
                                factura.getCliente().getIdentificacion(),
                                factura.getCliente().getRazonSocial(),
                                ((factura.getCliente().getNombreLegal())!=null)?factura.getCliente().getNombreLegal():"",
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
                                totalMenosNotaCredito.toString(),
                                (factura.getReferido()!=null)?factura.getReferido().getRazonSocial():"",
                                (factura.getReferido()!=null)?factura.getReferido().getIdentificacion():"",
                                (factura.getReferido()!=null)?factura.getReferido().getContactoClientePorcentaje().toString():"0",
                                valorComision.toString(),
                                factura.getClaveAcceso()
                                
                        );
                        
                        //UtilidadesFecha.formatoDiaMesAño(fechaFin);
                        reporteData.setFechaMaximaPago((factura.getFechaVencimiento()!=null)?UtilidadesFecha.formatoDiaMesAño(factura.getFechaVencimiento()):"");
                        reporteData.setVendedor((factura.getVendedor()!=null)?factura.getVendedor().getNombresCompletos():"");
                        
                        reporteData.mostrarReferido=filtrarReferidos; //Variables para saber si se debe mostrar las personas que le refieren
                        data.add(reporteData);


                    }

                    break;

                case NOTA_CREDITO:
                    for (NotaCredito nota : dataNotCre) {
                        Vector<String> fila = new Vector<String>();
                        ComprobanteEntity.ComprobanteEnumEstado estadoEnum = ComprobanteEntity.ComprobanteEnumEstado.getEnum(nota.getEstado());
                        
                        ReporteFacturaData reporteData=new ReporteFacturaData(
                                nota.getPreimpreso(),
                                dateFormat.format(nota.getFechaEmision()),
                                nota.getCliente().getIdentificacion(),
                                nota.getCliente().getRazonSocial(),
                                (nota.getCliente().getNombreLegal()!=null)?nota.getCliente().getNombreLegal():"",
                                (nota.getEstadoEnum()!=null)?nota.getEstadoEnum().getNombre():"Sin estado",
                                (nota.getTipoFacturacionEnum()!=null)?nota.getTipoFacturacionEnum().getNombre():"",
                                (nota.getCodigoDocumentoEnum()!=null)?nota.getCodigoDocumentoEnum().getNombre():"",
                                nota.getSubtotalDoce().toString(),
                                nota.getSubtotalCero().toString(),
                                "0", //Ver si es necesario calcular los descuentos en las notas de credito
                                nota.getValorIvaDoce().toString(),
                                nota.getTotal().toString(),
                                "0",
                                nota.getNumDocModificado(),
                                nota.getTotal().toString(),
                                (nota.getRazonSocial()!=null)?nota.getRazonSocial():"",                                
                                (nota.getIdentificacion()!=null)?nota.getIdentificacion():"",
                                "0",
                                "0",//TODO: Revisar porque en esta parte me late que no necesito calcular el iva
                                nota.getClaveAcceso()
                                
                        );
                        
                        reporteData.mostrarReferido=filtrarReferidos; //Variables para saber si se debe mostrar las personas que le refieren
                        data.add(reporteData);
                        
                        
                        agregarValorTotal("acum",nota.getSubtotalCero());
                        //acum = acum.add(nota.getSubtotalCero());
                        agregarValorTotal("acumdoce",nota.getSubtotalDoce());
                        //acumdoce = acumdoce.add(nota.getSubtotalDoce());
                        agregarValorTotal("acumiva",nota.getValorIvaDoce());
                        //acumiva = acumiva.add(nota.getValorIvaDoce());
                        agregarValorTotal("acumdesc",BigDecimal.ZERO); //todo: ver si agregar el descuento
                        //acumdesc = acumdesc.add(nota.getFactura().getDescuentoImpuestos().add(nota.getFactura().getDescuentoSinImpuestos()));

                        
                    }

                    break;

            }
            
            //if (tabla) 
            //{
                imprimirTabla();                
                
            //}
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaReporteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected InputStream getReporte()
    {
        return RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("reporte_documentos.jrxml");
    }
    
    protected void imprimirReporte()
    {
        Date fechaInicio = null;
        Date fechaFin = null;

        //BigDecimal acum = BigDecimal.ZERO, acumdoce = BigDecimal.ZERO, acumiva = BigDecimal.ZERO, acumdesc = BigDecimal.ZERO;
        ComprobanteEntity.ComprobanteEnumEstado estadoFactura = (ComprobanteEntity.ComprobanteEnumEstado) getCmbEstado().getSelectedItem();
        String estadoStr = estadoFactura.getEstado();

        if (getDateFechaInicio().getDate() != null) {
            fechaInicio = new Date(getDateFechaInicio().getDate().getTime());
        }
        if (getDateFechaFin().getDate() != null) {
            fechaFin = new Date(getDateFechaFin().getDate().getTime());
        }
        
        String estadoText = estadoFactura.getNombre();
        final InputStream path = getReporte();
        String cliente = "";
        if (persona == null) {
            cliente = "TODOS";
        } else {
            cliente = persona.getRazonSocial();
        }
        
        DocumentosConsultarEnum documentoConsultaEnum = (DocumentosConsultarEnum) getCmbDocumento().getSelectedItem();
        
        /**
         * Genera el reporte
         */

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("fechainicio", (fechaInicio != null) ? dateFormat.format(fechaInicio) : "");
        parameters.put("fechafin", (fechaFin != null) ? dateFormat.format(fechaFin) : "");
        parameters.put("tipodocumento", documentoConsultaEnum.toString());
        parameters.put("cliente", cliente);
        parameters.put("subtotal",mapTotales.get("acum").toString());
        parameters.put("subtotaliva",mapTotales.get("acumdoce").toString());
        parameters.put("valoriva",mapTotales.get("acumiva").toString());
        //BigDecimal total = acum.add(acumdoce).add(acumiva);
        BigDecimal total = mapTotales.get("acum").add(mapTotales.get("acumdoce").add(mapTotales.get("acumiva")));
        parameters.put("total", total.toString());
        
        //BigDecimal subtotal = acum.add(acumdoce);
        BigDecimal subtotal = mapTotales.get("acum").add(mapTotales.get("acumdoce"));
        parameters.put("totalsubtotales", subtotal.toString());
        parameters.put("descuentos",mapTotales.get("acumdesc").toString());
        parameters.put("estadofactura", estadoText);

        DialogoCodefac.dialogoReporteOpciones(new ReporteDialogListener() {

            @Override
            public void excel() {
                try {
                    Excel excel = new Excel();
                    Vector<String> titulosVector = crearCabezeraExcel();
                    String nombreCabeceras[] = titulosVector.toArray(new String[titulosVector.size()]); //Convertir en array
                    excel.gestionarIngresoInformacionExcel(nombreCabeceras, data);
                    excel.abrirDocumento();
                } catch (Exception exc) {
                    exc.printStackTrace();
                    DialogoCodefac.mensaje("Error", "El archivo Excel se encuentra abierto", DialogoCodefac.MENSAJE_INCORRECTO);
                }
            }

            @Override
            public void pdf() {
                ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Reporte Documentos ", OrientacionReporteEnum.HORIZONTAL);
            }
        });
    }
    
    protected void imprimirTabla()
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
            
            //Imprimir segun el tipo de consulta que estoy haciendo
            if(filtrarReferidos)
                fila.add(reporteFacturaData.getReferido());
            else                
                fila.add(reporteFacturaData.getNombreLegalCliente());
            
            
            fila.add(reporteFacturaData.getDocumento()); //Aqui debe ir el tipo de documento
            fila.add(reporteFacturaData.getEstadoFactura());
            fila.add(reporteFacturaData.getTipoEmision()); //Falta implementar
            fila.add(reporteFacturaData.getSubtotalDoceFactura());
            fila.add(reporteFacturaData.getSubtotalCeroFactura());
            fila.add(reporteFacturaData.getDescFactura());
            fila.add(reporteFacturaData.getIvaDoceFactura());
            fila.add(reporteFacturaData.getValorAfecta());
            fila.add(reporteFacturaData.getTotalFinal());
            modeloTablaFacturas.addRow(fila);
        }

        getTblDocumentos().setModel(modeloTablaFacturas);
        Map<Integer, Integer> mapTamanios = new HashMap<Integer, Integer>();
        mapTamanios.put(0, 130);
        UtilidadesTablas.definirTamanioColumnasPorMap(getTblDocumentos(), mapTamanios);
        
        getLblSubtotal0().setText(mapTotales.get("acum").toString());
        getLblSubtotal12().setText(mapTotales.get("acumdoce").toString());
        BigDecimal subtotal = mapTotales.get("acum").add(mapTotales.get("acumdoce"));
        getLblSubtotalSinImpuesto().setText(subtotal.toString());
        getLblTotalDescuento().setText(mapTotales.get("acumdesc").toString());
        getLblIva12().setText(mapTotales.get("acumiva").toString());
        BigDecimal total = mapTotales.get("acum").add(mapTotales.get("acumdoce")).add(mapTotales.get("acumiva"));
        getTxtValorTotal().setText(total.toString());


       
    }

    private NotaCredito verificarPorFactura(Factura factura,List<NotaCredito> notasCredito) {
        for (NotaCredito notaCredito : notasCredito) {
            if (notaCredito.getFactura()!=null && notaCredito.getFactura().equals(factura)) {
                return notaCredito;
            }
        }
        return null;
    }
    
    protected Vector<String>  crearCabezeraTabla()
    {
        Vector<String> titulo = new Vector<>();
        titulo.add("Preimpreso");
        titulo.add("Referencia");
        titulo.add("Fecha");
        titulo.add("Identificación");
        titulo.add("Razón social");
        
        //Si esta activo esta opcion envez del nombre legal pongo el nombre del referido TODO: Buscar alguna solucion mas elegante
        if(filtrarReferidos)
            titulo.add("Referido");
        else
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
    
    protected Vector<String>  crearCabezeraExcel()
    {
        Vector<String> titulos=crearCabezeraTabla();
        titulos.add(0,"Clave de Acceso");
        titulos.add(1,"Fecha Max Pago");
        titulos.add(2,"Vendedor");
        return titulos;
    }
    
    
    
    private DefaultTableModel construirModelTabla() {
        Vector<String> titulo = crearCabezeraTabla();        
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
        imprimirReporte();
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
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        
        filtrarReferidos=false;
        getDateFechaInicio().setDate(fechaInicioMes(hoy()));
        getDateFechaFin().setDate(hoy());

        getCmbEstado().removeAllItems();
        for (ComprobanteEntity.ComprobanteEnumEstado comprobanteEstado : ComprobanteEntity.ComprobanteEnumEstado.values()) {
            getCmbEstado().addItem(comprobanteEstado);
        }
        //getCmbEstado().addItem(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO);
        //getCmbEstado().addItem(FacturaEnumEstado.ANULADO_PARCIAL);
        //getCmbEstado().addItem(FacturaEnumEstado.ANULADO_TOTAL);
        //getCmbEstado().addItem(ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR);
        //getCmbEstado().addItem(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO);

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
        
        getChkTodosReferidos().setSelected(true);
        getBtnBuscarReferido().setEnabled(false);
        referido=null;
        
        ///Para el reporte de facturacion no me importa que sean visibles estos campos del referido
        getLblReferido().setVisible(false);
        getTxtReferido().setVisible(false);
        getBtnBuscarReferido().setVisible(false);
        getChkTodosReferidos().setVisible(false);
        getChkReporteAgrupadoReferido().setVisible(false);
    }

    protected void listenerBotones() {
        
       
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
                generarReporte(); //codigo que genera el reporte
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

    private void listenerCombos() {
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
    }

    protected void listenerChecks() {
        
        
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
