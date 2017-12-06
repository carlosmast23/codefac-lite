/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica;

import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionAdicional;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.UtilidadesComprobantes;
import ec.com.codesoft.codefaclite.facturacionelectronica.reporte.ComprobanteElectronicoReporte;
import ec.com.codesoft.codefaclite.facturacionelectronica.reporte.DetalleReporteData;
import ec.com.codesoft.codefaclite.facturacionelectronica.reporte.FacturaElectronicaReporte;
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
import java.io.StringReader;
import java.io.StringWriter;
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


    public static final String MODO_PRODUCCION = "produccion";
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
     * Clave de acceso del comprobante
     */
    private String claveAcceso;

    private String uriRecepcion;
    private String uriAutorizacion;

    private ServicioSri servicioSri;

    private String pathFacturaJasper;
    private String pathParentJasper;
    private BufferedImage logoImagen;
    public String pathLogoImagen;

    private Map<String, Object> mapAdicionalReporte;
    private ListenerComprobanteElectronico escucha;
    private MetodosEnvioInterface metodoEnvioInterface;
    private List<String> correosElectronicos;

    private String footerMensajeCorreo;
    private Integer etapaLimiteProcesar;

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

    public void procesar() {
        Thread thread = new Thread(this);
        thread.start();
    }

    private void procesarComprobante() {
        try {
            escucha.iniciado(comprobante);
            if (etapaActual == ETAPA_GENERAR) {
                generar();
                escucha.procesando(etapaActual);
                System.out.println("generar()");

                if (etapaLimiteProcesar<=ETAPA_GENERAR) {
                    escucha.termino();
                    return;
                }

                etapaActual++;
            }

            if (etapaActual == ETAPA_PRE_VALIDAR) {
                preValidacion();
                escucha.procesando(etapaActual);
                System.out.println("preValidacion()");
                if (etapaLimiteProcesar<=ETAPA_PRE_VALIDAR) {
                    escucha.termino();
                    return;
                }
                etapaActual++;
            }

            if (etapaActual == ETAPA_FIRMAR) {
                firmar();
                escucha.procesando(etapaActual);
                System.out.println("firmar()");
                if(etapaLimiteProcesar<=ETAPA_FIRMAR) {
                    escucha.termino();
                    return;
                }
                etapaActual++;
            }

            if (etapaActual == ETAPA_ENVIAR) {
                enviarSri();
                escucha.procesando(etapaActual);
                System.out.println("enviarSri()");
                if(etapaLimiteProcesar<=ETAPA_ENVIAR) {
                    escucha.termino();
                    return;
                }
                etapaActual++;
            }

            if (etapaActual == ETAPA_AUTORIZAR) {
                autorizarSri();
                escucha.procesando(etapaActual);
                System.out.println("autorizarSri()");
                if(etapaLimiteProcesar<=ETAPA_AUTORIZAR) {
                    escucha.termino();
                    return;
                }
                etapaActual++;
            }

            if (etapaActual == ETAPA_RIDE) {
                generarRide();
                escucha.procesando(etapaActual);
                //generarRide();
                System.out.println("generarRide()");
                if(etapaLimiteProcesar<=ETAPA_RIDE) {
                    escucha.termino();
                    return;
                }
                etapaActual++;
            }

            if (etapaActual == ETAPA_ENVIO_COMPROBANTE) {
                enviarComprobante();
                escucha.procesando(etapaActual);
                if(etapaLimiteProcesar<=ETAPA_ENVIO_COMPROBANTE) {
                    escucha.termino();
                    return;
                }
                //generarRide();
                System.out.println("enviarCorreo()");
            }

            escucha.termino();
        } catch (ComprobanteElectronicoException cee) {
            escucha.error(cee);
        }

    }

    private void enviarComprobante() throws ComprobanteElectronicoException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(FacturaComprobante.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Map<String, String> mapComprobante = UtilidadesComprobantes.decodificarArchivoBase64Offline(getPathComprobante(CARPETA_AUTORIZADOS), null, null);
            StringReader reader = new StringReader(mapComprobante.get("comprobante"));
            FacturaComprobante comprobante = (FacturaComprobante) jaxbUnmarshaller.unmarshal(reader);

            String pathFile = getPathComprobante(CARPETA_RIDE, getNameRide(comprobante));
            Map<String,String> archivosPath=new HashMap<String,String>();
            archivosPath.put(ComprobanteEnum.FACTURA.getPrefijo()+"-"+comprobante.getInformacionTributaria().getPreimpreso(),pathFile);
            archivosPath.put(comprobante.getInformacionTributaria().getPreimpreso(),getPathComprobante(CARPETA_AUTORIZADOS));
            
            try {
                String mensajeGenerado = "Estimado/a "
                        + "<b>" + comprobante.getInformacionFactura().getRazonSocialComprador() + "</b> ,<br><br>"
                        + "<b>" + comprobante.getInformacionTributaria().getNombreComercial() + "</b>"
                        + " le informa que su factura  electrónica " + comprobante.getInformacionTributaria().getPreimpreso() + " se generó correctamente. <br><br>";
                mensajeGenerado = "<p>" + mensajeGenerado + "</p>" + footerMensajeCorreo;
                
                metodoEnvioInterface.enviarCorreo(mensajeGenerado, "FACTURA:" + comprobante.getInformacionTributaria().getPreimpreso(), correosElectronicos, archivosPath);

                //metodoEnvioInterface.enviarCorreo(pathBase, pathBase, destinatorios, pathBase);
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

    private void generarRide() throws ComprobanteElectronicoException {
        try {
            Map<String, String> mapComprobante = UtilidadesComprobantes.decodificarArchivoBase64Offline(getPathComprobante(CARPETA_AUTORIZADOS), null, null);
            JAXBContext jaxbContext = JAXBContext.newInstance(FacturaComprobante.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(mapComprobante.get("comprobante"));
            FacturaComprobante comprobante = (FacturaComprobante) jaxbUnmarshaller.unmarshal(reader);
            FacturaElectronicaReporte reporte = new FacturaElectronicaReporte(comprobante);
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

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(logoImagen, "jpg", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());

            //datosMap.put("imagen_logo",is);
            datosMap.put("imagen_logo", UtilidadesComprobantes.getStreamByPath(pathLogoImagen));

            datosMap.put("pl_url_imgl", UtilidadesComprobantes.getStreamByPath(mapAdicionalReporte.get("pl_url_img1_url").toString()));
            datosMap.put("pl_img_facebook", UtilidadesComprobantes.getStreamByPath(mapAdicionalReporte.get("pl_img_facebook_url").toString()));
            datosMap.put("pl_img_whatsapp", UtilidadesComprobantes.getStreamByPath(mapAdicionalReporte.get("pl_img_whatsapp_url").toString()));
            datosMap.put("pl_img_telefono", UtilidadesComprobantes.getStreamByPath(mapAdicionalReporte.get("pl_img_telefono_url").toString()));
            datosMap.put("pl_img_logo_pie", UtilidadesComprobantes.getStreamByPath(mapAdicionalReporte.get("pl_img_logo_pie_url").toString()));

            UtilidadesComprobantes.generarReporteJasper(pathFacturaJasper, datosMap, informacionAdiciona, getPathComprobante(CARPETA_RIDE, getNameRide(comprobante)));

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

    private void preValidacion() {

    }

    public JasperPrint getPrintJasper() {
        try {
            Map<String, String> mapComprobante = UtilidadesComprobantes.decodificarArchivoBase64Offline(getPathComprobante(CARPETA_AUTORIZADOS), null, null);
            JAXBContext jaxbContext = JAXBContext.newInstance(FacturaComprobante.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(mapComprobante.get("comprobante"));
            FacturaComprobante comprobante = (FacturaComprobante) jaxbUnmarshaller.unmarshal(reader);
            FacturaElectronicaReporte reporte = new FacturaElectronicaReporte(comprobante);
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
            datosMap.put("imagen_logo", UtilidadesComprobantes.getStreamByPath(pathLogoImagen));

            return UtilidadesComprobantes.generarReporteJasperPrint(pathFacturaJasper, datosMap, informacionAdiciona);

        } catch (JAXBException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void generar() throws ComprobanteElectronicoException {
        try {
            claveAcceso = obtenerClaveAcceso();
            comprobante.getInformacionTributaria().setClaveAcceso(claveAcceso);
            StringWriter stringWriter = generarXml(comprobante);
            ComprobantesElectronicosUtil.generarArchivoXml(stringWriter, getPathComprobante(CARPETA_GENERADOS));
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

    public String obtenerClaveAcceso() {
        Vector<String> claveAcceso = new Vector<>();
        //SimpleDateFormat formateador = new SimpleDateFormat("ddmmyyyy");
        String fechaFormat = ComprobantesElectronicosUtil.formatSimpleDate(comprobante.getFechaEmision());
        claveAcceso.add(fechaFormat);

        claveAcceso.add(getTipoComprobante());

        //String identificacionFormat=UtilidadesTextos.llenarCarateresDerecha(comprobante.getIdentificacion(),12, "0");
        claveAcceso.add(comprobante.getIdentificacion());

        claveAcceso.add(getTipoCodigoAmbiente());

        /**
         * Valor por defecto serie que no se que se pone
         */
        claveAcceso.add("001001");

        /**
         * Secuendial del comprobante
         */
        String secuencialFormat = UtilidadesTextos.llenarCarateresIzquierda(comprobante.getInformacionTributaria().getSecuencial(), 9, "0");
        claveAcceso.add(secuencialFormat);

        /**
         * Codigo numerico , que paree que sirve cuando se procesan archivos en
         * bach y por defecto se pone con 0
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

    private StringWriter generarXml(ComprobanteElectronico comprobante) {
        try {
            JAXBContext contexto = JAXBContext.newInstance(comprobante.getClass());
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

    private String getPathComprobante(String carpeta) {
        //return pathBase+"/"+carpeta+"/"+claveAcceso+".xml";
        //14502017011724213151500010010010000000010000000011.xml
        return pathBase + "/" + carpeta + "/" + claveAcceso + ".xml";
    }

    private String getPathComprobante(String carpeta, String archivo) {
        return pathBase + "/" + carpeta + "/" + archivo;
    }

    private String getPathFirma() {
        return pathBase + "/" + CARPETA_CONFIGURACION + "/" + nombreFirma;
    }

    private String getNameRide(FacturaComprobante comprobante) {
        String prefijo = "";
        if (ComprobanteEnum.FACTURA.getCodigo().equals(comprobante.getInformacionTributaria().getCodigoDocumento())) {
            prefijo = ComprobanteEnum.FACTURA.getPrefijo();
        }
        comprobante.getTipoDocumento();
        return prefijo + "-" + comprobante.getInformacionTributaria().getPreimpreso() +"_"+claveAcceso+ ".pdf";
    }
/*
    public String getPathRide() {
        String nombreArchivo = getNameRide();
        return pathBase + "/" + CARPETA_RIDE + "/" + nombreArchivo;
    }
*/
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

    public String getPathFacturaJasper() {
        return pathFacturaJasper;
    }

    public void setPathFacturaJasper(String pathFacturaJasper) {
        this.pathFacturaJasper = pathFacturaJasper;
    }

    public String getPathParentJasper() {
        return pathParentJasper;
    }

    public void setPathParentJasper(String pathParentJasper) {
        this.pathParentJasper = pathParentJasper;
    }

    public void setLogoImagen(BufferedImage logoImagen) {
        this.logoImagen = logoImagen;
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

    
    
    

    @Override
    public void run() {
        procesarComprobante();
    }

}
