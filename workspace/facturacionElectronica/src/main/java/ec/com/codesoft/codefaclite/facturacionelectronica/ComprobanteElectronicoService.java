/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica;

import autorizacion.ws.sri.gob.ec.Autorizacion;
import com.sun.xml.bind.marshaller.DataWriter;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionAdicional;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.lote.LoteComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.lote.LoteComprobanteCData;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito.NotaCreditoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.JaxbCharacterEscapeHandler;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.UtilidadesComprobantes;
import ec.com.codesoft.codefaclite.facturacionelectronica.reporte.ComprobanteElectronicoReporte;
import ec.com.codesoft.codefaclite.facturacionelectronica.reporte.DetalleReporteData;
import ec.com.codesoft.codefaclite.facturacionelectronica.reporte.FacturaElectronicaReporte;
import ec.com.codesoft.codefaclite.facturacionelectronica.reporte.NotaCreditoReporte;
import ec.com.codesoft.codefaclite.ws.recepcion.Comprobante;
import ec.com.codesoft.codefaclite.ws.recepcion.Mensaje;
import ec.com.codesoft.ejemplo.utilidades.email.CorreoElectronico;
import ec.com.codesoft.ejemplo.utilidades.texto.UtilidadesTextos;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;

/**
 *
 * @author Carlos
 */
public class ComprobanteElectronicoService implements Runnable {
    
    public static final String CARPETA_GENERADOS = "generados";
    public static final String CARPETA_FIRMADOS = "firmados";
    public static final String CARPETA_ENVIADOS = "enviados";
    public static final String CARPETA_AUTORIZADOS = "autorizados";
    public static final String CARPETA_NO_AUTORIZADOS = "no_autorizados";
    public static final String CARPETA_RIDE = "ride";
    
    public static final String CARPETA_CONFIGURACION = "configuracion";


    public static final String MODO_PRODUCCION = "producción";
    public static final String MODO_PRUEBAS = "pruebas";

    public static final Integer ETAPA_GENERAR = 1;
    public static final Integer ETAPA_PRE_VALIDAR = 2;
    public static final Integer ETAPA_FIRMAR = 3;
    public static final Integer ETAPA_ENVIAR = 4;
    public static final Integer ETAPA_AUTORIZAR = 5;
    public static final Integer ETAPA_RIDE = 6;
    public static final Integer ETAPA_ENVIO_COMPROBANTE = 7;

    public static final Integer CODIGO_SRI_MODO_PRUEBAS = 1;
    public static final Integer CODIGO_SRI_MODO_PRODUCCION = 2;

    /**
     * Etapa en la que se encuentra la facturacion
     */
    public Integer etapaActual;

    /**
     * Path base de los archivos de configuracion
     */
    private String pathBase;
    /**
     * Nombre del archivo del archivo de la firma electronica
     */
    private String nombreFirma;
    /**
     * Clave para la firma electronica
     */
    private String claveFirma;
    /**
     * Modo facturacion
     */
    private String modoFacturacion;

    /**
     * Comprobante electronica que se desea mandar a autorizar en el sri
     */
    private ComprobanteElectronico comprobante;
    
    /**
     * Conjunto de comprobante cuando se van a procesar por Lotes
     */
    private List<ComprobanteElectronico> comprobantesLote;

    /**
     * Clave de acceso del comprobante
     */
    private String claveAcceso;

    private String uriRecepcion;
    private String uriAutorizacion;

    private ServicioSri servicioSri;

    private URL pathFacturaJasper;

    private URL pathNotaCreditoJasper;
        
    private String pathParentJasper;
    public URL pathLogoImagen;

    private Map<String, Object> mapAdicionalReporte;
    private ListenerComprobanteElectronico escucha;
    private MetodosEnvioInterface metodoEnvioInterface;
    private List<String> correosElectronicos;

    private String footerMensajeCorreo;
    private Integer etapaLimiteProcesar;
    
    
    private URL reporteInfoAdicional;
    private URL reporteFormaPago;
    
    private boolean enviarPorLotes;
    
    private Integer secuencialLote;
    
    /**
     * Map que establece el diccionario de los codigos
     * y los nombres de las formas de pago para poder generar el ride
     * con los valores proporicinados.
     * @return 
     */
    private Map<String,String> mapCodeAndNameFormaPago;

    public ComprobanteElectronicoService() {
        this.etapaLimiteProcesar = 100;
        this.etapaActual = ETAPA_GENERAR;
    }

    public ComprobanteElectronicoService(String pathBase, String nombreFirma, String claveFirma, String modoFacturacion, ComprobanteElectronico comprobante) {
        this.pathBase = pathBase;
        this.nombreFirma = nombreFirma;
        this.claveFirma = claveFirma;
        this.modoFacturacion = modoFacturacion;
        this.comprobante = comprobante;
        this.etapaActual = ETAPA_GENERAR;
        this.etapaLimiteProcesar = 100;
    }

    public void procesar(Boolean enviarPorLotes) {
        this.enviarPorLotes=enviarPorLotes;
        Thread thread = new Thread(this);
        thread.start();
    }
    

    private void procesarComprobante() {
        try {
            if(escucha!=null)escucha.iniciado(comprobante);
            
            if (etapaActual.equals(ETAPA_GENERAR)){
                generar();
                if(escucha!=null)escucha.procesando(etapaActual,new ClaveAcceso(claveAcceso));
                System.out.println("generar()");

                if (etapaLimiteProcesar<=ETAPA_GENERAR) {
                    if(escucha!=null)escucha.termino();
                    return;
                }

                etapaActual++;
            }

            if (etapaActual.equals(ETAPA_PRE_VALIDAR)) {
                preValidacion();
                if(escucha!=null)escucha.procesando(etapaActual,new ClaveAcceso(claveAcceso));
                System.out.println("preValidacion()");
                if (etapaLimiteProcesar<=ETAPA_PRE_VALIDAR) {
                    if(escucha!=null)escucha.termino();
                    return;
                }
                etapaActual++;
            }

            if (etapaActual.equals(ETAPA_FIRMAR)) {
                firmar();
                if(escucha!=null)escucha.procesando(etapaActual,new ClaveAcceso(claveAcceso));
                System.out.println("firmar()");
                if(etapaLimiteProcesar<=ETAPA_FIRMAR) {
                    if(escucha!=null)escucha.termino();
                    return;
                }
                etapaActual++;
            }

            if (etapaActual.equals(ETAPA_ENVIAR)) {
                enviarSri();
                if(escucha!=null)escucha.procesando(etapaActual,new ClaveAcceso(claveAcceso));
                System.out.println("enviarSri()");
                if(etapaLimiteProcesar<=ETAPA_ENVIAR) {
                    if(escucha!=null)escucha.termino();
                    return;
                }
                etapaActual++;
            }

            if (etapaActual.equals(ETAPA_AUTORIZAR)) {
                autorizarSri();
                if(escucha!=null)escucha.procesando(etapaActual,new ClaveAcceso(claveAcceso));
                System.out.println("autorizarSri()");
                if(etapaLimiteProcesar<=ETAPA_AUTORIZAR) {
                    if(escucha!=null)escucha.termino();
                    return;
                }
                etapaActual++;
            }

            if (etapaActual.equals(ETAPA_RIDE)) {
                generarRide();
                if(escucha!=null)escucha.procesando(etapaActual,new ClaveAcceso(claveAcceso));
                //generarRide();
                System.out.println("generarRide()");
                if(etapaLimiteProcesar<=ETAPA_RIDE) {
                    if(escucha!=null)escucha.termino();
                    return;
                }
                etapaActual++;
            }

            if (etapaActual.equals(ETAPA_ENVIO_COMPROBANTE)) {
                if(correosElectronicos!=null && correosElectronicos.size()>0)
                    enviarComprobante();
                
                if(escucha!=null)escucha.procesando(etapaActual,new ClaveAcceso(claveAcceso));
                if(etapaLimiteProcesar<=ETAPA_ENVIO_COMPROBANTE) {
                    if(escucha!=null)escucha.termino();
                    return;
                }
                //generarRide();
                System.out.println("enviarCorreo()");
            }

            if(escucha!=null)escucha.termino();
        } catch (ComprobanteElectronicoException cee) {
            if(escucha!=null)escucha.error(cee);
        }

    }
    
    private void procesarComprobanteLote()
    {
        try {
            if(escucha!=null)escucha.iniciado(comprobante);
            
            if (etapaActual.equals(ETAPA_GENERAR)){
                generarLote();
                if(escucha!=null)escucha.procesando(etapaActual,new ClaveAcceso(claveAcceso));
                System.out.println("generar lote()");

                if (etapaLimiteProcesar<=ETAPA_GENERAR) {
                    if(escucha!=null)escucha.termino();
                    return;
                }

                etapaActual++;
            }

            if (etapaActual.equals(ETAPA_PRE_VALIDAR)) {
                preValidacion();
                if(escucha!=null)escucha.procesando(etapaActual,new ClaveAcceso(claveAcceso));
                System.out.println("preValidacion lote()");
                if (etapaLimiteProcesar<=ETAPA_PRE_VALIDAR) {
                    if(escucha!=null)escucha.termino();
                    return;
                }
                etapaActual++;
            }

            if (etapaActual.equals(ETAPA_FIRMAR)) {
                firmarLote();
                if(escucha!=null)escucha.procesando(etapaActual,new ClaveAcceso(claveAcceso));
                System.out.println("firmar lote()");
                if(etapaLimiteProcesar<=ETAPA_FIRMAR) {
                    if(escucha!=null)escucha.termino();
                    return;
                }
                etapaActual++;
            }

            if (etapaActual.equals(ETAPA_ENVIAR)) {
                enviarSriLote();
                if(escucha!=null)escucha.procesando(etapaActual,new ClaveAcceso(claveAcceso));
                System.out.println("enviarSri lote()");
                if(etapaLimiteProcesar<=ETAPA_ENVIAR) {
                    if(escucha!=null)escucha.termino();
                    return;
                }
                etapaActual++;
            }

            if (etapaActual.equals(ETAPA_AUTORIZAR)) {
                autorizarSriLote();
                if(escucha!=null)escucha.procesando(etapaActual,new ClaveAcceso(claveAcceso));
                System.out.println("autorizarSri lote()");
                if(etapaLimiteProcesar<=ETAPA_AUTORIZAR) {
                    if(escucha!=null)escucha.termino();
                    return;
                }
                etapaActual++;
            }

            if (etapaActual.equals(ETAPA_RIDE)) {
                generarRideLote();
                if(escucha!=null)escucha.procesando(etapaActual,new ClaveAcceso(claveAcceso));
                //generarRide();
                System.out.println("generarRide()");
                if(etapaLimiteProcesar<=ETAPA_RIDE) {
                    if(escucha!=null)escucha.termino();
                    return;
                }
                etapaActual++;
            }

            if (etapaActual.equals(ETAPA_ENVIO_COMPROBANTE)) {
                enviarComprobanteLoteCorreo();
                
                if(escucha!=null)escucha.procesando(etapaActual,new ClaveAcceso(claveAcceso));
                if(etapaLimiteProcesar<=ETAPA_ENVIO_COMPROBANTE) {
                    if(escucha!=null)escucha.termino();
                    return;
                }
                //generarRide();
                System.out.println("enviarCorreo()");
            }

            if(escucha!=null)escucha.termino();
        } catch (ComprobanteElectronicoException cee) {
            if(escucha!=null)escucha.error(cee);
        }
    
     }

    private void enviarComprobante() throws ComprobanteElectronicoException {
        try {
            ClaveAcceso claveAcceso=new ClaveAcceso(this.claveAcceso);
            JAXBContext jaxbContext = JAXBContext.newInstance(claveAcceso.getClassTipoComprobante());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Map<String, String> mapComprobante = UtilidadesComprobantes.decodificarArchivoBase64Offline(getPathComprobante(CARPETA_AUTORIZADOS), null, null);
            StringReader reader = new StringReader(mapComprobante.get("comprobante"));
            ComprobanteElectronico comprobante = (ComprobanteElectronico) jaxbUnmarshaller.unmarshal(reader);

            String pathFile = getPathComprobante(CARPETA_RIDE, getNameRide(comprobante));
            Map<String,String> archivosPath=new HashMap<String,String>();
            archivosPath.put(claveAcceso.getTipoComprobante().getPrefijo()+"-"+comprobante.getInformacionTributaria().getPreimpreso()+".pdf",pathFile);
            archivosPath.put(comprobante.getInformacionTributaria().getPreimpreso()+".xml",getPathComprobante(CARPETA_AUTORIZADOS));
            
            try {
                String mensajeGenerado =getMensajeCorreo(claveAcceso.getTipoComprobante(),comprobante);
                metodoEnvioInterface.enviarCorreo(mensajeGenerado, claveAcceso.getTipoComprobante().getNombre()+":" + comprobante.getInformacionTributaria().getPreimpreso(), correosElectronicos, archivosPath);
            } catch (Exception ex) {
                Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
                throw new ComprobanteElectronicoException("El comprobante se genero correctamente pero no se envio al cliente,\n Revise el correo y envie manualmente el RIDE", "Enviado correo", ComprobanteElectronicoException.ERROR_ENVIO_CLIENTE);
            }

        } catch (JAXBException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ComprobanteElectronicoException(ex.getMessage(), "Enviar comprobante", ComprobanteElectronicoException.ERROR_ENVIO_CLIENTE);
        } catch (Exception ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ComprobanteElectronicoException(ex.getMessage(), "Enviar comprobante", ComprobanteElectronicoException.ERROR_ENVIO_CLIENTE);
        }

    }
    
    private void enviarComprobanteLoteCorreo()
    {
        for (ComprobanteElectronico comprobanteElectronico : comprobantesLote) {
             Autorizacion autorizacion = servicioSri.buscarAutorizacion(comprobanteElectronico.getInformacionTributaria().getClaveAcceso());

             //Enviar solo los comprobantes que existen y que fueron autorizados
            if (autorizacion != null && autorizacion.getEstado().equals(ServicioSri.AUTORIZADO)) {
                 try {
                     enviarComprobanteCorreo(comprobanteElectronico.getInformacionTributaria().getClaveAcceso());
                 } catch (ComprobanteElectronicoException ex) {
                     Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
        }
    }
    
    private void enviarComprobanteCorreo(String claveAccesoTemp) throws ComprobanteElectronicoException {
        try {
            ClaveAcceso claveAcceso=new ClaveAcceso(claveAccesoTemp);
            JAXBContext jaxbContext = JAXBContext.newInstance(claveAcceso.getClassTipoComprobante());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Map<String, String> mapComprobante = UtilidadesComprobantes.decodificarArchivoBase64Offline(getPathComprobanteConClaveAcceso(CARPETA_AUTORIZADOS,claveAccesoTemp), null, null);
            StringReader reader = new StringReader(mapComprobante.get("comprobante"));
            ComprobanteElectronico comprobante = (ComprobanteElectronico) jaxbUnmarshaller.unmarshal(reader);

            String pathFile = getPathComprobante(CARPETA_RIDE, getNameRide(comprobante));
            Map<String,String> archivosPath=new HashMap<String,String>();
            archivosPath.put(claveAcceso.getTipoComprobante().getPrefijo()+"-"+comprobante.getInformacionTributaria().getPreimpreso()+".pdf",pathFile);
            archivosPath.put(comprobante.getInformacionTributaria().getPreimpreso()+".xml",getPathComprobanteConClaveAcceso(CARPETA_AUTORIZADOS,claveAccesoTemp));
            
            try {
                
                String mensajeGenerado =getMensajeCorreo(claveAcceso.getTipoComprobante(),comprobante);
                
                ComprobanteElectronico comprobanteElectronico=buscarComprobanteLote(claveAccesoTemp);
                List<String> correosElectronicosTemp=comprobanteElectronico.getCorreos();
                
                metodoEnvioInterface.enviarCorreo(mensajeGenerado, claveAcceso.getTipoComprobante().getNombre()+":" + comprobante.getInformacionTributaria().getPreimpreso(), correosElectronicosTemp, archivosPath);
            } catch (Exception ex) {
                Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
                throw new ComprobanteElectronicoException("El comprobante se genero correctamente pero no se envio al cliente,\n Revise el correo y envie manualmente el RIDE", "Enviado correo", ComprobanteElectronicoException.ERROR_ENVIO_CLIENTE);
            }

        } catch (JAXBException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ComprobanteElectronicoException(ex.getMessage(), "Enviar comprobante", ComprobanteElectronicoException.ERROR_ENVIO_CLIENTE);
        } catch (Exception ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ComprobanteElectronicoException(ex.getMessage(), "Enviar comprobante", ComprobanteElectronicoException.ERROR_ENVIO_CLIENTE);
        }

    }
    
    private String getMensajeCorreo(ComprobanteEnum clase,ComprobanteElectronico comprobante)
    {
        String mensajeGenerado = "Estimado/a ";
        if(clase.equals(ComprobanteEnum.FACTURA))
        {
            mensajeGenerado+= " "
                    + "<b>" + comprobante.getRazonSocialComprador() + "</b> ,<br><br>"
                    + "<b>" + comprobante.getInformacionTributaria().getNombreComercial() + "</b>"
                    + " le informa que su factura  electrónica " + comprobante.getInformacionTributaria().getPreimpreso() + " se generó correctamente. <br><br>";
            mensajeGenerado = "<p>" + mensajeGenerado + "</p>" + footerMensajeCorreo;
        }
        else
        {
            if(clase.equals(ComprobanteEnum.NOTA_CREDITO))
            {
                mensajeGenerado += " "
                        + "<b>" + comprobante.getRazonSocialComprador() + "</b> ,<br><br>"
                        + "<b>" + comprobante.getInformacionTributaria().getNombreComercial() + "</b>"
                        + " le informa que su nota de crédito " + comprobante.getInformacionTributaria().getPreimpreso() + " se generó correctamente. <br><br>";
                mensajeGenerado = "<p>" + mensajeGenerado + "</p>" + footerMensajeCorreo;
                
            }
            else
            {
                //Mensaje generico cuando no es ningun de los comprobantes registtados
                mensajeGenerado=" puede revisar el comprobante electronico como archivo adjunto";
            }
        }
        return mensajeGenerado;
        
    }

    private void generarRide() throws ComprobanteElectronicoException {
        try {
            Map<String, String> mapComprobante = UtilidadesComprobantes.decodificarArchivoBase64Offline(getPathComprobante(CARPETA_AUTORIZADOS), null, null);
            ClaveAcceso claveAcceso=new ClaveAcceso(this.claveAcceso);
            
            JAXBContext jaxbContext = JAXBContext.newInstance(claveAcceso.getClassTipoComprobante());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(mapComprobante.get("comprobante"));
            
            //FacturaComprobante comprobante = (FacturaComprobante) jaxbUnmarshaller.unmarshal(reader);
            //FacturaElectronicaReporte reporte = new FacturaElectronicaReporte(comprobante);
            
            ComprobanteElectronico comprobante = (ComprobanteElectronico) jaxbUnmarshaller.unmarshal(reader);
            //FacturaElectronicaReporte reporte = new FacturaElectronicaReporte(comprobante);
            ComprobanteElectronicoReporte reporte =getComprobanteReporte(comprobante);
            
            List<DetalleReporteData> informacionAdiciona = reporte.getDetalles();
            Map<String, Object> datosMap = reporte.getMapReporte();
            datosMap.put("SUBREPORT_DIR", pathParentJasper);
            datosMap.put("SUBREPORT_INFO_ADICIONAL", reporteInfoAdicional);
            datosMap.put("SUBREPORT_FORMA_PAGO", reporteFormaPago);
            datosMap.put("fecha_hora_autorizacion", mapComprobante.get("fechaAutorizacion"));
            datosMap.put("estado", mapComprobante.get("estado"));

            /**
             * Agregar datos adicionales como por ejemplo los datos del pide de
             * pagina
             */
            datosMap.putAll(mapAdicionalReporte);
            
            /*
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(logoImagen, "jpg", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());*/

            //datosMap.put("imagen_logo",is);
            //datosMap.put("imagen_logo", UtilidadesComprobantes.getStreamByPath(pathLogoImagen));
            datosMap.put("imagen_logo",pathLogoImagen);
            /*
            datosMap.put("pl_url_imgl", UtilidadesComprobantes.getStreamByPath(mapAdicionalReporte.get("pl_url_img1_url").toString()));
            datosMap.put("pl_img_facebook", UtilidadesComprobantes.getStreamByPath(mapAdicionalReporte.get("pl_img_facebook_url").toString()));
            datosMap.put("pl_img_whatsapp", UtilidadesComprobantes.getStreamByPath(mapAdicionalReporte.get("pl_img_whatsapp_url").toString()));
            datosMap.put("pl_img_telefono", UtilidadesComprobantes.getStreamByPath(mapAdicionalReporte.get("pl_img_telefono_url").toString()));
            datosMap.put("pl_img_logo_pie", UtilidadesComprobantes.getStreamByPath(mapAdicionalReporte.get("pl_img_logo_pie_url").toString()));
            */
            
            UtilidadesComprobantes.generarReporteJasper(getPathJasper(comprobante), datosMap, informacionAdiciona, getPathComprobante(CARPETA_RIDE, getNameRide(comprobante)));

        } catch (JAXBException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ComprobanteElectronicoException(ex.getMessage(), "Generando RIDE", ComprobanteElectronicoException.ERROR_COMPROBANTE);
        } catch (IOException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ComprobanteElectronicoException(ex.getMessage(), "Generando RIDE", ComprobanteElectronicoException.ERROR_COMPROBANTE);
        } catch (Exception ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ComprobanteElectronicoException(ex.getMessage(), "Generando RIDE", ComprobanteElectronicoException.ERROR_COMPROBANTE);
        }

    }
    
    private void generarRideLote() throws ComprobanteElectronicoException {
        for (ComprobanteElectronico comprobanteElectronico : comprobantesLote) {
            generarRideIndividual(comprobanteElectronico.getInformacionTributaria().getClaveAcceso());
        }
    }
    
    private void generarRideIndividual(String claveAccesoTemp) throws ComprobanteElectronicoException
    {
        try {            
            //COPIAR RECURSOS//
            InputStream reporteInfoAdicional = this.reporteInfoAdicional.openStream();
            InputStream reporteFormaPago = this.reporteFormaPago.openStream();
            InputStream pathLogoImagen = this.pathLogoImagen.openStream();

            //FIN COPIA ARCHIVOS
            
            Map<String, String> mapComprobante = UtilidadesComprobantes.decodificarArchivoBase64Offline(getPathComprobanteConClaveAcceso(CARPETA_AUTORIZADOS,claveAccesoTemp), null, null);
            ClaveAcceso claveAcceso=new ClaveAcceso(claveAccesoTemp);
            
            JAXBContext jaxbContext = JAXBContext.newInstance(claveAcceso.getClassTipoComprobante());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(mapComprobante.get("comprobante"));
            
            ComprobanteElectronico comprobante = (ComprobanteElectronico) jaxbUnmarshaller.unmarshal(reader);

            ComprobanteElectronicoReporte reporte =getComprobanteReporte(comprobante);
            
            List<DetalleReporteData> informacionAdiciona = reporte.getDetalles();
            Map<String, Object> datosMap = reporte.getMapReporte();
            datosMap.put("SUBREPORT_DIR", pathParentJasper);
            datosMap.put("SUBREPORT_INFO_ADICIONAL", reporteInfoAdicional);
            datosMap.put("SUBREPORT_FORMA_PAGO", reporteFormaPago);
            datosMap.put("fecha_hora_autorizacion", mapComprobante.get("fechaAutorizacion"));
            datosMap.put("estado", mapComprobante.get("estado"));

            /**
             * Agregar datos adicionales como por ejemplo los datos del pide de
             * pagina
             */
            //datosMap.putAll(mapAdicionalReporte);
            datosMap.putAll(getMapCopyAdicionalReporte());

            datosMap.put("imagen_logo",pathLogoImagen);
            
            UtilidadesComprobantes.generarReporteJasper(getPathJasper(comprobante), datosMap, informacionAdiciona, getPathComprobante(CARPETA_RIDE, getNameRide(comprobante)));
            
            //Resetear variables input stream para volver a usar
            //reporteInfoAdicional.close();
            //reporteFormaPago.close();
            //pathLogoImagen.close();
            

        } catch (JAXBException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ComprobanteElectronicoException(ex.getMessage(), "Generando RIDE", ComprobanteElectronicoException.ERROR_COMPROBANTE);
        } catch (IOException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ComprobanteElectronicoException(ex.getMessage(), "Generando RIDE", ComprobanteElectronicoException.ERROR_COMPROBANTE);
        } catch (Exception ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ComprobanteElectronicoException(ex.getMessage(), "Generando RIDE", ComprobanteElectronicoException.ERROR_COMPROBANTE);
        }
    }
    

    private Map<String, Object> getMapCopyAdicionalReporte()
    {
        Map<String,Object> mapCopy=new HashMap<String,Object>();
        for (Map.Entry<String, Object> entry : mapAdicionalReporte.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            
            if(value.getClass().equals(URL.class))
            {
                try {
                    URL url=(URL) value;
                    mapCopy.put(key,url.openStream());
                } catch (IOException ex) {
                    Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                mapCopy.put(key, value);
            }
        }
        return mapCopy;
    }
    
    private ComprobanteElectronicoReporte getComprobanteReporte(ComprobanteElectronico comprobante)
    {
        if(comprobante.getClass().equals(FacturaComprobante.class))
        {
            FacturaElectronicaReporte factElectReport=new FacturaElectronicaReporte(comprobante);
            factElectReport.setMapCodeAndNameFormaPago(mapCodeAndNameFormaPago);
            return factElectReport;
        }
        else
            if(comprobante.getClass().equals(NotaCreditoComprobante.class))
                return new NotaCreditoReporte(comprobante);
            else
            {
                System.out.println("no esta comparando clases");
                return null;
            }
    }

    private void preValidacion() {

    }

    public JasperPrint getPrintJasper() {
        try {
            Map<String, String> mapComprobante = UtilidadesComprobantes.decodificarArchivoBase64Offline(getPathComprobante(CARPETA_AUTORIZADOS), null, null);
            ClaveAcceso claveAcceso=new ClaveAcceso(this.claveAcceso);
            JAXBContext jaxbContext = JAXBContext.newInstance(claveAcceso.getClassTipoComprobante());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(mapComprobante.get("comprobante"));
            ComprobanteElectronico comprobante = (ComprobanteElectronico) jaxbUnmarshaller.unmarshal(reader);
            ComprobanteElectronicoReporte reporte = getComprobanteReporte(comprobante);
            List<DetalleReporteData> informacionAdiciona = reporte.getDetalles();

            Map<String, Object> datosMap = reporte.getMapReporte();
            datosMap.put("SUBREPORT_DIR", pathParentJasper);
            datosMap.put("fecha_hora_autorizacion", mapComprobante.get("fechaAutorizacion"));
            datosMap.put("estado", mapComprobante.get("estado"));

            /**
             * Agregar datos adicionales como por ejemplo los datos del pide de
             * pagina
             */
            datosMap.putAll(mapAdicionalReporte);

            //datosMap.put("imagen_logo",is);
            //datosMap.put("imagen_logo", UtilidadesComprobantes.getStreamByPath(pathLogoImagen));
            datosMap.put("imagen_logo",pathLogoImagen);

            return UtilidadesComprobantes.generarReporteJasperPrint(getPathJasper(comprobante), datosMap, informacionAdiciona);

        } catch (JAXBException ex) {
            ex.printStackTrace();
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void generar() throws ComprobanteElectronicoException {
        try {
            claveAcceso = obtenerClaveAcceso();
            comprobante.getInformacionTributaria().setClaveAcceso(claveAcceso);
            StringWriter stringWriter = generarXml(comprobante,claveAcceso);
            ComprobantesElectronicosUtil.generarArchivoXml(stringWriter, getPathComprobante(CARPETA_GENERADOS));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ComprobanteElectronicoException(e.getMessage(), "Generando XML", ComprobanteElectronicoException.ERROR_COMPROBANTE);
        }

    }
    
    private void generarLote() throws ComprobanteElectronicoException{
        try {
            claveAcceso = obtenerClaveAccesoLote();            
            //String ruc="";
            //Generar los xml individuales de los comprobantes para procesar en lote
            for (ComprobanteElectronico comprobante : comprobantesLote) {
                String claveAccesoComprobante=obtenerClaveAcceso(comprobante);
                comprobante.getInformacionTributaria().setClaveAcceso(claveAccesoComprobante);
                //ruc=comprobante.getInformacionTributaria().getRuc();
                StringWriter stringWriter = generarXml(comprobante,claveAccesoComprobante);
                ComprobantesElectronicosUtil.generarArchivoXml(stringWriter, getPathComprobanteConClaveAcceso(CARPETA_GENERADOS,claveAccesoComprobante));
            }
            //Genrar el xml del contenedor de los comprobantes para procesar individual
            //StringWriter stringWriter = generarXmlLote(comprobantesLote,ruc);
            //ComprobantesElectronicosUtil.generarArchivoXml(stringWriter, getPathComprobante(CARPETA_GENERADOS));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ComprobanteElectronicoException(e.getMessage(), "Generando XML", ComprobanteElectronicoException.ERROR_COMPROBANTE);
        }
  
    }

    private void firmar() throws ComprobanteElectronicoException {
        /**
         * Firmando el documento
         */
        FirmaElectronica firmaElectronica = new FirmaElectronica(getPathFirma(), claveFirma);
        Document documentoFirmado = firmaElectronica.firmar(getPathComprobante(CARPETA_GENERADOS));
        if (documentoFirmado != null) {
            try {
                ComprobantesElectronicosUtil.generarArchivoXml(documentoFirmado, getPathComprobante(CARPETA_FIRMADOS));
                ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobante(CARPETA_GENERADOS));
            } catch (Exception ex) {
                Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    private void firmarLote() throws ComprobanteElectronicoException {
        FirmaElectronica firmaElectronica = new FirmaElectronica(getPathFirma(), claveFirma);
        
        String ruc="";
        List<String> comprobantesFirmados=new ArrayList<String>();
        
        for (ComprobanteElectronico comprobanteElectronico : comprobantesLote) {
            ruc=comprobanteElectronico.getInformacionTributaria().getRuc();
            String claveAccesoTemp=comprobanteElectronico.getInformacionTributaria().getClaveAcceso();
            Document documentoFirmado = firmaElectronica.firmar(getPathComprobanteConClaveAcceso(CARPETA_GENERADOS,claveAccesoTemp));
            
            if (documentoFirmado != null) {
                try {
                    ComprobantesElectronicosUtil.generarArchivoXml(documentoFirmado, getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS,claveAccesoTemp));
                    ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobanteConClaveAcceso(CARPETA_GENERADOS,claveAccesoTemp));
                    
                    //LoteComprobanteCData loteComprobanteData=new LoteComprobanteCData();
                    String loteComprobanteData=("<![CDATA["+UtilidadesTextos.documentToString(documentoFirmado)+"]]>");
                    //String documentoStr=UtilidadesTextos.documentToString(documentoFirmado);
                    //loteComprobanteData.setcData(documentoStr);
                    
                    comprobantesFirmados.add(loteComprobanteData);
                } catch (Exception ex) {
                    Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        //Agregar el comprobante firmado a la lista de comprobante en loteas
        StringWriter stringWriter = generarXmlLote(comprobantesFirmados,ruc);
        ComprobantesElectronicosUtil.generarArchivoXml(stringWriter, getPathComprobante(CARPETA_FIRMADOS)); 

    }

    private void enviarSri() throws ComprobanteElectronicoException {
        try {
            servicioSri = new ServicioSri();
            servicioSri.setUri_autorizacion(uriAutorizacion);
            servicioSri.setUri_recepcion(uriRecepcion);
            servicioSri.setUrlFile(getPathComprobante(CARPETA_FIRMADOS));

            if (servicioSri.verificarConexionRecepcion()) {
                System.out.println("Existe conexion");
                if (servicioSri.enviar()) {
                    
                    System.out.println("Documento enviados");
                    ComprobantesElectronicosUtil.copiarArchivoXml(getPathComprobante(CARPETA_FIRMADOS),getPathComprobante(CARPETA_ENVIADOS));
                    ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobante(CARPETA_FIRMADOS));
                    
                } else {
                    String mensajeError = "";
                    for (Mensaje mensaje : servicioSri.getMensajes()) {
                        System.out.println(mensaje.getIdentificador() + "-" + mensaje.getMensaje() + "-" + mensaje.getInformacionAdicional());
                        mensajeError += mensaje.getMensaje() + "\n" + mensaje.getInformacionAdicional();
                    }
                    throw new ComprobanteElectronicoException(mensajeError, "Enviar comprobante", ComprobanteElectronicoException.ERROR_COMPROBANTE);
                }
            }
        } catch (ComprobanteElectronicoException cee) {
            cee.printStackTrace();
            throw new ComprobanteElectronicoException(cee);
        }
    }
    
    private void enviarSriLote() throws ComprobanteElectronicoException {
        try {
            servicioSri = new ServicioSri();
            servicioSri.setUri_autorizacion(uriAutorizacion);
            servicioSri.setUri_recepcion(uriRecepcion);
            servicioSri.setUrlFile(getPathComprobante(CARPETA_FIRMADOS));

            if (servicioSri.verificarConexionRecepcion()) {
                System.out.println("Existe conexion");
                if (servicioSri.enviar()) {
                    System.out.println("Documento enviados");
                    for (ComprobanteElectronico comprobanteElectronico : comprobantesLote) {
                        //Mover todos los archivos individuales
                        String claveAccesoTemp=comprobanteElectronico.getInformacionTributaria().getClaveAcceso();
                        ComprobantesElectronicosUtil.copiarArchivoXml(getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS,claveAccesoTemp),getPathComprobanteConClaveAcceso(CARPETA_ENVIADOS,claveAccesoTemp));
                        ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS,claveAccesoTemp));
                    }                    
                    
                    //Mover el archivo que contiene todos los archivos en lote a la carpeta de enviados
                    ComprobantesElectronicosUtil.copiarArchivoXml(getPathComprobante(CARPETA_FIRMADOS),getPathComprobante(CARPETA_ENVIADOS));
                    ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobante(CARPETA_FIRMADOS));
                    
                } else {
                    String mensajeError = "";
                    for (Mensaje mensaje : servicioSri.getMensajes()) {
                        System.out.println(mensaje.getIdentificador() + "-" + mensaje.getMensaje() + "-" + mensaje.getInformacionAdicional());
                        mensajeError += mensaje.getMensaje() + "\n" + mensaje.getInformacionAdicional();
                    }
                    throw new ComprobanteElectronicoException(mensajeError, "Enviar comprobante", ComprobanteElectronicoException.ERROR_COMPROBANTE);
                }
            }
        } catch (ComprobanteElectronicoException cee) {
            cee.printStackTrace();
            throw new ComprobanteElectronicoException(cee);
        }
    }

    private void autorizarSri() throws ComprobanteElectronicoException {
        servicioSri = new ServicioSri();
        servicioSri.setUri_autorizacion(uriAutorizacion);
        servicioSri.setUri_recepcion(uriRecepcion);
        servicioSri.setUrlFile(getPathComprobante(CARPETA_FIRMADOS));
        /**
         * Recogiendo autorizacion SRI
         */
        if (servicioSri.autorizar(claveAcceso)) {
            String xmlAutorizado = servicioSri.obtenerRespuestaAutorizacion();
            ComprobantesElectronicosUtil.generarArchivoXml(xmlAutorizado, getPathComprobante(CARPETA_AUTORIZADOS));
            ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobante(CARPETA_ENVIADOS));
        }

    }
    
    private void autorizarSriLote() throws ComprobanteElectronicoException {
        servicioSri = new ServicioSri();
        servicioSri.setUri_autorizacion(uriAutorizacion);
        servicioSri.setUri_recepcion(uriRecepcion);
        servicioSri.setUrlFile(getPathComprobante(CARPETA_FIRMADOS));
        
        /**
         * Recogiendo autorizacion SRI
         */
        if (servicioSri.autorizarLote(claveAcceso)) {
            
            for (ComprobanteElectronico comprobanteLote : comprobantesLote) {
                //Generar los archivos de cada comprobante autorizado
                Autorizacion autorizacion=servicioSri.buscarAutorizacion(comprobanteLote.getInformacionTributaria().getClaveAcceso());
                
                if(autorizacion!=null && autorizacion.getEstado().equals(ServicioSri.AUTORIZADO))
                {
                    String claveAccesoTemp=autorizacion.getNumeroAutorizacion();
                    String xmlAutorizado = servicioSri.castAutorizacionToString(autorizacion);
                    ComprobantesElectronicosUtil.generarArchivoXml(xmlAutorizado, getPathComprobanteConClaveAcceso(CARPETA_AUTORIZADOS,claveAccesoTemp));
                    ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobanteConClaveAcceso(CARPETA_ENVIADOS,claveAccesoTemp));
                }
                
            }
            
            ComprobantesElectronicosUtil.copiarArchivoXml(getPathComprobante(CARPETA_ENVIADOS), getPathComprobante(CARPETA_FIRMADOS));
            ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobante(CARPETA_ENVIADOS));

            //String xmlAutorizado = servicioSri.obtenerRespuestaAutorizacion();
            //ComprobantesElectronicosUtil.generarArchivoXml(xmlAutorizado, getPathComprobanteConClaveAcceso(CARPETA_AUTORIZADOS,claveAcceso));
            //ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobanteConClaveAcceso(CARPETA_ENVIADOS,claveAcceso));
        }

    }

    private KeyStore obtenerAlmacenFirma(String rutaAlmacenCertificado, String passwordAlmacenCertificado) {
        try {
            KeyStore clave = null;
            clave = KeyStore.getInstance("PKCS12");
            FileInputStream file = new FileInputStream(rutaAlmacenCertificado);
            clave.load(new FileInputStream(rutaAlmacenCertificado),
                    passwordAlmacenCertificado.toCharArray());
            return clave;
        } catch (KeyStoreException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String obtenerClaveAccesoLote()
    {
        Vector<String> claveAcceso = new Vector<>();
        
        //Fecha cuando se va a enviar los archivos por lote
        claveAcceso.add("01032018");
        
        //Tipo de comprobante (por defecto le dejo 01 que corresponde a factura)
        claveAcceso.add("01");
        
        //Identificacion de ruc mia supongo
        claveAcceso.add("1724218951001");
        
        //Tipo de ambiente de la facturacion
        claveAcceso.add("1"); //por defecto en pruebas
        
        //Numero de serie , asumo que es un valor que se genera automaticamente 
        claveAcceso.add("001001");
        
        //Numero de secuencial que supongo que es el secuencial inicial desde el que sea va a empezar a facturar
        claveAcceso.add(UtilidadesTextos.llenarCarateresIzquierda(secuencialLote+"",9,"0"));
        
        
        //Código Numérico que por defecto mando este valor
        claveAcceso.add("00000000");
        
        //Tipo de emision
        claveAcceso.add(ComprobanteElectronico.MODO_FACTURACION_NORMAL);
        
        //Digito verificador
        String digito = UtilidadesTextos.calcularModulo11(UtilidadesTextos.castVectorToString(claveAcceso));
        claveAcceso.add(digito);
        
        
        return UtilidadesTextos.castVectorToString(claveAcceso);
    }

    public String obtenerClaveAcceso() 
    {
        Vector<String> claveAcceso = new Vector<>();
        //SimpleDateFormat formateador = new SimpleDateFormat("ddmmyyyy");
        String fechaFormat = ComprobantesElectronicosUtil.formatSimpleDate(comprobante.getFechaEmision());
        claveAcceso.add(fechaFormat);

        claveAcceso.add(getTipoComprobante());

        //String identificacionFormat=UtilidadesTextos.llenarCarateresDerecha(comprobante.getIdentificacion(),12, "0");
        claveAcceso.add(comprobante.getIdentificacion());

        claveAcceso.add(getTipoCodigoAmbiente());

        /**
         * Establecimiento y punto de emision
         */
        String establecimiento= UtilidadesTextos.llenarCarateresIzquierda(comprobante.getInformacionTributaria().getEstablecimiento(),3,"0");
        String puntoEmision= UtilidadesTextos.llenarCarateresIzquierda(comprobante.getInformacionTributaria().getPuntoEmision(),3,"0");
        claveAcceso.add(establecimiento+puntoEmision);

        /**
         * Secuendial del comprobante
         */
        String secuencialFormat = UtilidadesTextos.llenarCarateresIzquierda(comprobante.getInformacionTributaria().getSecuencial(), 9, "0");
        claveAcceso.add(secuencialFormat);

        /**
         * Codigo numerico , que paree que sirve cuando se procesan archivos en
         * lote y por defecto se pone con 0
         */
        claveAcceso.add("00000000");

        /**
         * Clave del tipo de emision, para el metodo offline solo existe el modo
         * 1 que significa modo normal antes existia el modo contingencia
         */
        claveAcceso.add(ComprobanteElectronico.MODO_FACTURACION_NORMAL);

        /**
         * Digito verificador
         */
        String digito = UtilidadesTextos.calcularModulo11(UtilidadesTextos.castVectorToString(claveAcceso));
        claveAcceso.add(digito);

        return UtilidadesTextos.castVectorToString(claveAcceso);
    }
    
    public String obtenerClaveAcceso(ComprobanteElectronico comprobante) 
    {
        Vector<String> claveAcceso = new Vector<>();
        //SimpleDateFormat formateador = new SimpleDateFormat("ddmmyyyy");
        String fechaFormat = ComprobantesElectronicosUtil.formatSimpleDate(comprobante.getFechaEmision());
        claveAcceso.add(fechaFormat);

        claveAcceso.add(getTipoComprobante(comprobante));

        //String identificacionFormat=UtilidadesTextos.llenarCarateresDerecha(comprobante.getIdentificacion(),12, "0");
        claveAcceso.add(comprobante.getIdentificacion());

        claveAcceso.add(getTipoCodigoAmbiente());

        /**
         * Establecimiento y punto de emision
         */
        String establecimiento= UtilidadesTextos.llenarCarateresIzquierda(comprobante.getInformacionTributaria().getEstablecimiento(),3,"0");
        String puntoEmision= UtilidadesTextos.llenarCarateresIzquierda(comprobante.getInformacionTributaria().getPuntoEmision(),3,"0");
        claveAcceso.add(establecimiento+puntoEmision);

        /**
         * Secuendial del comprobante
         */
        String secuencialFormat = UtilidadesTextos.llenarCarateresIzquierda(comprobante.getInformacionTributaria().getSecuencial(), 9, "0");
        claveAcceso.add(secuencialFormat);

        /**
         * Codigo numerico , que paree que sirve cuando se procesan archivos en
         * lote y por defecto se pone con 0
         */
        claveAcceso.add("00000000");

        /**
         * Clave del tipo de emision, para el metodo offline solo existe el modo
         * 1 que significa modo normal antes existia el modo contingencia
         */
        claveAcceso.add(ComprobanteElectronico.MODO_FACTURACION_NORMAL);

        /**
         * Digito verificador
         */
        String digito = UtilidadesTextos.calcularModulo11(UtilidadesTextos.castVectorToString(claveAcceso));
        claveAcceso.add(digito);

        return UtilidadesTextos.castVectorToString(claveAcceso);
    }

    private String getTipoCodigoAmbiente() {
        switch (this.modoFacturacion) {
            case ComprobanteElectronicoService.MODO_PRODUCCION:
                return "2";

            case ComprobanteElectronicoService.MODO_PRUEBAS:
                return "1";

            default:
                return "00";
        }
    }

    private String getTipoComprobante() {
        switch (comprobante.getTipoDocumento()) {
            case ComprobanteElectronico.FACTURA:
                return "01";

            case ComprobanteElectronico.NOTA_CREDITO:
                return "04";

            default:
                return "00";
        }
    }
    
    private String getTipoComprobante(ComprobanteElectronico comprobante) {
        switch (comprobante.getTipoDocumento()) {
            case ComprobanteElectronico.FACTURA:
                return "01";

            case ComprobanteElectronico.NOTA_CREDITO:
                return "04";

            default:
                return "00";
        }
    }

    private StringWriter generarXml(ComprobanteElectronico comprobante,String claveAccesoStr) {
        try {
            ClaveAcceso claveAcceso=new ClaveAcceso(claveAccesoStr);
            JAXBContext contexto = JAXBContext.newInstance(claveAcceso.getClassTipoComprobante());
            Marshaller marshaller = contexto.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "libro.xsd");
            StringWriter sw = new StringWriter();
            //marshaller.marshal(libro, System.out);
            marshaller.marshal(comprobante, sw);
            return sw;
        } catch (JAXBException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    private StringWriter generarXmlLote(List<String> comprobantesFirmados,String ruc) {
        try {
            ClaveAcceso claveAcceso=new ClaveAcceso(this.claveAcceso);
            JAXBContext contexto = JAXBContext.newInstance(LoteComprobante.class);
            Marshaller marshaller = contexto.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
          
             LoteComprobante loteComprobante=new LoteComprobante();
             loteComprobante.setRuc(ruc);
             loteComprobante.setClaveAcceso(this.claveAcceso);
             loteComprobante.setComprobantes(comprobantesFirmados);
             
            StringWriter sw = new StringWriter();
            PrintWriter printWriter = new PrintWriter(sw);
            DataWriter dataWriter = new DataWriter(printWriter, "UTF-8", new JaxbCharacterEscapeHandler());
            
            
            marshaller.marshal(loteComprobante, dataWriter);
            return sw;
        } catch (JAXBException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private String getPathComprobante(String carpeta) {
        return pathBase + "/comprobantes/"+modoFacturacion+"/" + carpeta + "/" + claveAcceso + ".xml";
    }
    
    private String getPathComprobanteConClaveAcceso(String carpeta,String claveAcceso) {
        return pathBase + "/comprobantes/"+modoFacturacion+"/" + carpeta + "/" + claveAcceso + ".xml";
    }

    private String getPathComprobante(String carpeta, String archivo) {
        return pathBase + "/comprobantes/"+modoFacturacion+"/" + carpeta + "/" + archivo;
    }

    private String getPathFirma() {
        return pathBase + "/" + CARPETA_CONFIGURACION + "/" + nombreFirma;
    }

    private String getNameRide(ComprobanteElectronico comprobante) {
        String prefijo = "";
        if (ComprobanteEnum.FACTURA.getCodigo().equals(comprobante.getInformacionTributaria().getCodigoDocumento())) {
            prefijo = ComprobanteEnum.FACTURA.getPrefijo();
        }
        else
        {
            if (ComprobanteEnum.NOTA_CREDITO.getCodigo().equals(comprobante.getInformacionTributaria().getCodigoDocumento())) {
                prefijo = ComprobanteEnum.NOTA_CREDITO.getPrefijo();
            }
        }
        comprobante.getTipoDocumento();
        return prefijo + "-" + comprobante.getInformacionTributaria().getPreimpreso() +"_"+claveAcceso+ ".pdf";
    }

    public void setUriRecepcion(String uriRecepcion) {
        this.uriRecepcion = uriRecepcion;
    }

    public void setUriAutorizacion(String uriAutorizacion) {
        this.uriAutorizacion = uriAutorizacion;
    }

    public String getModoFacturacion() {
        return modoFacturacion;
    }

    public void setModoFacturacion(String modoFacturacion) {
        this.modoFacturacion = modoFacturacion;
    }

    public URL getPathFacturaJasper() {
        return pathFacturaJasper;
    }

    public void setPathFacturaJasper(URL pathFacturaJasper) {
        this.pathFacturaJasper = pathFacturaJasper;
    }

    public String getPathParentJasper() {
        return pathParentJasper;
    }

    public void setPathParentJasper(String pathParentJasper) {
        this.pathParentJasper = pathParentJasper;
    }

    
    public Map<String, Object> getMapAdicionalReporte() {
        return mapAdicionalReporte;
    }

    public void setMapAdicionalReporte(Map<String, Object> mapAdicionalReporte) {
        this.mapAdicionalReporte = mapAdicionalReporte;
    }

    public void addActionListerComprobanteElectronico(ListenerComprobanteElectronico e) {
        this.escucha = e;
    }

    public Integer getEtapaActual() {
        return etapaActual;
    }

    public String getPathBase() {
        return pathBase;
    }

    public String getNombreFirma() {
        return nombreFirma;
    }

    public String getClaveFirma() {
        return claveFirma;
    }

    public ComprobanteElectronico getComprobante() {
        return comprobante;
    }

    public void setEtapaActual(Integer etapaActual) {
        this.etapaActual = etapaActual;
    }

    public void setPathBase(String pathBase) {
        this.pathBase = pathBase;
    }

    public void setNombreFirma(String nombreFirma) {
        this.nombreFirma = nombreFirma;
    }

    public void setClaveFirma(String claveFirma) {
        this.claveFirma = claveFirma;
    }

    public void setComprobante(ComprobanteElectronico comprobante) {
        this.comprobante = comprobante;
    }

    public MetodosEnvioInterface getMetodoEnvioInterface() {
        return metodoEnvioInterface;
    }

    public void setMetodoEnvioInterface(MetodosEnvioInterface metodoEnvioInterface) {
        this.metodoEnvioInterface = metodoEnvioInterface;
    }

    public void setCorreosElectronicos(List<String> correosElectronicos) {
        this.correosElectronicos = correosElectronicos;
    }

    public String getFooterMensajeCorreo() {
        return footerMensajeCorreo;
    }

    public void setFooterMensajeCorreo(String footerMensajeCorreo) {
        this.footerMensajeCorreo = footerMensajeCorreo;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public Integer getEtapaLimiteProcesar() {
        return etapaLimiteProcesar;
    }

    public void setEtapaLimiteProcesar(Integer etapaLimiteProcesar) {
        this.etapaLimiteProcesar = etapaLimiteProcesar;
    }

    public URL getPathNotaCreditoJasper() {
        return pathNotaCreditoJasper;
    }

    public void setPathNotaCreditoJasper(URL pathNotaCreditoJasper) {
        this.pathNotaCreditoJasper = pathNotaCreditoJasper;
    }

    public void setMapCodeAndNameFormaPago(Map<String, String> mapCodeAndNameFormaPago) {
        this.mapCodeAndNameFormaPago = mapCodeAndNameFormaPago;
    }

    public void setReporteInfoAdicional(URL reporteInfoAdicional) {
        this.reporteInfoAdicional = reporteInfoAdicional;
    }

    public void setReporteFormaPago(URL reporteFormaPago) {
        this.reporteFormaPago = reporteFormaPago;
    }

    public List<ComprobanteElectronico> getComprobantesLote() {
        return comprobantesLote;
    }

    public void setComprobantesLote(List<ComprobanteElectronico> comprobantesLote) {
        this.comprobantesLote = comprobantesLote;
    }
    
    private ComprobanteElectronico buscarComprobanteLote(String clave)
    {
        for (ComprobanteElectronico comprobanteElectronico : comprobantesLote) {
            if(comprobanteElectronico.getInformacionTributaria().getClaveAcceso().equals(clave))
            {
                return comprobanteElectronico;
            }
        }
        return null;
    }

    public InputStream getPathJasper(ComprobanteElectronico comprobanteElectronico)
    {
        InputStream path = null;
        try {
            ClaveAcceso clave = new ClaveAcceso(claveAcceso);
            if (ComprobanteEnum.FACTURA.getCodigo().equals(clave.tipoComprobante)) {
                path = pathFacturaJasper.openStream();

            } else if (ComprobanteEnum.NOTA_CREDITO.getCodigo().equals(clave.tipoComprobante)) {
                path = pathNotaCreditoJasper.openStream();
            }
            //comprobante.getTipoDocumento();
            //return path + "-" + comprobante.getInformacionTributaria().getPreimpreso() +"_"+claveAcceso+ ".pdf";
        } catch (IOException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return path;
    }

    public void setSecuencialLote(Integer secuencialLote) {
        this.secuencialLote = secuencialLote;
    }
    
    
    

    @Override
    public void run() {
        if(enviarPorLotes)
        {
            procesarComprobanteLote();
        }
        else
        {        
            procesarComprobante();
        }
    }

}
