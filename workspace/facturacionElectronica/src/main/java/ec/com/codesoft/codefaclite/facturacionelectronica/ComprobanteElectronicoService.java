/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica;

import autorizacion.ws.sri.gob.ec.Autorizacion;
import com.sun.xml.bind.marshaller.DataWriter;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronicoLote;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionAdicional;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.lote.LoteComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.lote.LoteComprobanteCData;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito.NotaCreditoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.retencion.RetencionComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.JaxbCharacterEscapeHandler;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.UtilidadesComprobantes;
import ec.com.codesoft.codefaclite.facturacionelectronica.reporte.ComprobanteElectronicoReporte;
import ec.com.codesoft.codefaclite.facturacionelectronica.reporte.DetalleReporteData;
import ec.com.codesoft.codefaclite.facturacionelectronica.reporte.FacturaElectronicaReporte;
import ec.com.codesoft.codefaclite.facturacionelectronica.reporte.NotaCreditoReporte;
import ec.com.codesoft.codefaclite.facturacionelectronica.reporte.RetencionElectronicaReporte;
import ec.com.codesoft.codefaclite.ws.recepcion.Comprobante;
import ec.com.codesoft.codefaclite.ws.recepcion.Mensaje;
import ec.com.codesoft.ejemplo.utilidades.email.CorreoElectronico;
import ec.com.codesoft.ejemplo.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.ejemplo.utilidades.xml.UtilidadesXml;
import java.awt.Image;
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
import net.sf.jasperreports.engine.JasperReport;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;

/**
 *
 * @author Carlos
 */
public class ComprobanteElectronicoService implements Runnable {
    
    public static final String CARPETA_GENERADOS = "generados";
    public static final String CARPETA_FIRMADOS = "firmados";
    public static final String CARPETA_FIRMADOS_SIN_ENVIAR = "firmados_sin_enviar";
    public static final String CARPETA_ENVIADOS = "enviados";
    public static final String CARPETA_AUTORIZADOS = "autorizados";
    public static final String CARPETA_NO_AUTORIZADOS = "no_autorizados";
    public static final String CARPETA_RIDE = "ride";
    public static final String CARPETA_LOTE = "lote";
    
    public static final String CARPETA_CONFIGURACION = "configuracion";


    public static final String MODO_PRODUCCION = "producción";
    public static final String MODO_PRUEBAS = "pruebas";

    public static final Integer ETAPA_GENERAR = 1;
    public static final Integer ETAPA_PRE_VALIDAR = 2;
    public static final Integer ETAPA_FIRMAR = 3;
    public static final Integer ETAPA_RIDE = 4;
    public static final Integer ETAPA_ENVIO_COMPROBANTE =5;
    public static final Integer ETAPA_ENVIAR = 6;
    public static final Integer ETAPA_AUTORIZAR = 7;

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
     * Ruc de la empresa variable necesaria cuando se trabaja en lote
     */
    private String ruc;
    
    /**
     * Lista de las claves de acceso para trabajar en las siguientes etapas despues de generar el comprobante
     */
    private List<String> clavesAccesoLote;

    /**
     * Clave de acceso del comprobante
     */
    private String claveAcceso;

    private String uriRecepcion;
    private String uriAutorizacion;

    private ServicioSri servicioSri;

    private URL pathFacturaJasper;

    private URL pathNotaCreditoJasper;
    
    private URL pathRetencionJasper;
        
    private String pathParentJasper;
    public Image pathLogoImagen;

    private Map<String, Object> mapAdicionalReporte;
    private ListenerComprobanteElectronico escucha;
    private ListenerComprobanteElectronicoLote escuchaLote;
    
    private MetodosEnvioInterface metodoEnvioInterface;
    private List<String> correosElectronicos;

    private String footerMensajeCorreo;
    private Integer etapaLimiteProcesar;
    
    
    private JasperReport reporteInfoAdicional;
    private JasperReport reporteFormaPago;
    
    private boolean enviarPorLotes;
    
    private Integer secuencialLote;
    
    /**
     * Map que establece el diccionario de los codigos
     * y los nombres de las formas de pago para poder generar el ride
     * con los valores proporicinados.
     * @return 
     */
    private Map<String,String> mapCodeAndNameFormaPago;
    
    private Map<String,String> mapCodeAndNameTipoRetecion;
        
    private Map<String,String> mapCodeAndNameTipoDocumento;

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
    

    public void procesarComprobante() {
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
            
            if (etapaActual.equals(ETAPA_RIDE)) {
                generarRide();
                if (escucha != null) {
                    escucha.procesando(etapaActual, new ClaveAcceso(claveAcceso));
                }
                //generarRide();
                System.out.println("generarRide()");
                if (etapaLimiteProcesar <= ETAPA_RIDE) {
                    if (escucha != null) {
                        escucha.termino();
                    }
                    return;
                }
                etapaActual++;
            }
            
            if (etapaActual.equals(ETAPA_ENVIO_COMPROBANTE)) {
                //if(correosElectronicos!=null && correosElectronicos.size()>0)
                enviarComprobante();

                if (escucha != null) {
                    escucha.procesando(etapaActual, new ClaveAcceso(claveAcceso));
                }
                if (etapaLimiteProcesar <= ETAPA_ENVIO_COMPROBANTE) {
                    if (escucha != null) {
                        escucha.termino();
                    }
                    return;
                }
                etapaActual++;
                //generarRide();
                System.out.println("enviarCorreo()");
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


            if(escucha!=null)escucha.termino();
        } catch (ComprobanteElectronicoException cee) {
            if(escucha!=null)escucha.error(cee);
        }

    }
    
    private void procesarComprobanteLote()
    {
        try {
            
            if(escuchaLote!=null)escuchaLote.iniciado();
            
            if (etapaActual.equals(ETAPA_GENERAR)){
                List<ClaveAcceso> listaClaves=generarLote();
                if(escuchaLote!=null)escuchaLote.clavesGeneradas(listaClaves);
                
                if(escuchaLote!=null)escuchaLote.procesando(etapaActual);
                System.out.println("generar lote()");

                if (etapaLimiteProcesar<=ETAPA_GENERAR) {
                    if(escuchaLote!=null)escuchaLote.termino(null);
                    return;
                }

                etapaActual++;
            }

            if (etapaActual.equals(ETAPA_PRE_VALIDAR)) {
                preValidacion();
                if(escuchaLote!=null)escuchaLote.procesando(etapaActual);
                System.out.println("preValidacion lote()");
                if (etapaLimiteProcesar<=ETAPA_PRE_VALIDAR) {
                    if(escuchaLote!=null)escuchaLote.termino(null);
                    return;
                }
                etapaActual++;
            }

            //List<String> comprobantesFirmados=new ArrayList<String>();
            
            if (etapaActual.equals(ETAPA_FIRMAR)) {
                claveAcceso = obtenerClaveAccesoLote();
                firmarLote();
                if(escuchaLote!=null)escuchaLote.procesando(etapaActual);
                System.out.println("firmar lote()");
                if(etapaLimiteProcesar<=ETAPA_FIRMAR) {
                    if(escuchaLote!=null)escuchaLote.termino(null);
                    return;
                }
                etapaActual++;
            }
            else//Si no tiene que firmar los datos entonces tiene que obtener segun las claves de acceso
            {
                claveAcceso = obtenerClaveAccesoLote();
                List<String> comprobantesFirmados = new ArrayList<String>();
                for (String claveAcceso : clavesAccesoLote) {
                    String path = getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS, claveAcceso);
                    String firmaStr = UtilidadesXml.convertirDocumentToString(path);
                    //System.out.println(firmaStr);
                    firmaStr="<![CDATA["+firmaStr+"]]>";
                    comprobantesFirmados.add(firmaStr);
                }

                StringWriter stringWriter = generarXmlLote(comprobantesFirmados, ruc);
                ComprobantesElectronicosUtil.generarArchivoXml(stringWriter, getPathComprobante(CARPETA_LOTE));
                
            }
            
            
            
            if (etapaActual.equals(ETAPA_RIDE)) {
                generarRideLote();
                if (escuchaLote != null) {
                    escuchaLote.procesando(etapaActual);
                }
                //generarRide();
                System.out.println("generarRide()");
                if (etapaLimiteProcesar <= ETAPA_RIDE) {
                    if (escuchaLote != null) {
                        escuchaLote.termino(servicioSri.getAutorizacion());
                    }
                    return;
                }
                etapaActual++;
            }
            
            
            if (etapaActual.equals(ETAPA_ENVIO_COMPROBANTE)) {
                enviarComprobanteLoteCorreo();
                
                if(escuchaLote!=null)escuchaLote.procesando(etapaActual);
                if(etapaLimiteProcesar<=ETAPA_ENVIO_COMPROBANTE) {
                    if(escuchaLote!=null)escuchaLote.termino(servicioSri.getAutorizacion());
                    return;
                }
                //generarRide();
                etapaActual++;
                System.out.println("enviarCorreo()");
            }


            if (etapaActual.equals(ETAPA_ENVIAR)) {
                enviarSriLote();
                if(escuchaLote!=null)escuchaLote.procesando(etapaActual);
                System.out.println("enviarSri lote()");
                if(etapaLimiteProcesar<=ETAPA_ENVIAR) {
                    if(escuchaLote!=null)escuchaLote.termino(null);
                    return;
                }
                etapaActual++;
            }

            if (etapaActual.equals(ETAPA_AUTORIZAR)) {
                autorizarSriLote();
                if(escuchaLote!=null)escuchaLote.procesando(etapaActual);
                if(escuchaLote!=null)escuchaLote.datosAutorizados(servicioSri.getAutorizacion());
                System.out.println("autorizarSri lote()");
                if(etapaLimiteProcesar<=ETAPA_AUTORIZAR) {
                    if(escuchaLote!=null)escuchaLote.termino(servicioSri.getAutorizacion());
                    return;
                }
                etapaActual++;
            }

            if(escuchaLote!=null)escuchaLote.termino(servicioSri.getAutorizacion());
        } catch (ComprobanteElectronicoException cee) {
            if(escuchaLote!=null)escuchaLote.error();
        }
    
     }

    private void enviarComprobante() throws ComprobanteElectronicoException {
        //enviarComprobanteCorreo(this.claveAcceso);
        try {
            ClaveAcceso claveAcceso=new ClaveAcceso(this.claveAcceso);
            JAXBContext jaxbContext = JAXBContext.newInstance(claveAcceso.getClassTipoComprobante());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            //Map<String, String> mapComprobante = UtilidadesComprobantes.decodificarArchivoBase64Offline(getPathComprobante(CARPETA_AUTORIZADOS), null, null);
            String pathComprobanteFirmado=getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS, this.claveAcceso);
            File file=new File(pathComprobanteFirmado);

            //StringReader reader = new StringReader(mapComprobante.get("comprobante"));
            ComprobanteElectronico comprobante = (ComprobanteElectronico) jaxbUnmarshaller.unmarshal(file);

            String pathFile = getPathComprobante(CARPETA_RIDE, getNameRide(comprobante));
            Map<String,String> archivosPath=new HashMap<String,String>();
            archivosPath.put(claveAcceso.getTipoComprobante().getPrefijo()+"-"+comprobante.getInformacionTributaria().getPreimpreso()+".pdf",pathFile);
            archivosPath.put(comprobante.getInformacionTributaria().getPreimpreso()+".xml",getPathComprobante(CARPETA_FIRMADOS));
            
            try {
                String mensajeGenerado =getMensajeCorreo(claveAcceso.getTipoComprobante(),comprobante);
                
                List<String> correosElectronicosTemp=new ArrayList<String>();
                
                if(correosElectronicos!=null)
                {
                    //Agregado correos electronicos adicionales
                    correosElectronicosTemp.addAll(correosElectronicos);
                }
                
                if(comprobante.getInformacionAdicional()!=null)
                {
                    for (InformacionAdicional infoAdicional : comprobante.getInformacionAdicional()) {
                        if (infoAdicional.getNombre().indexOf("correo") >= 0) {
                            correosElectronicosTemp.add(infoAdicional.getValor());
                        }
                    }
                }
                
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
    
    private void enviarComprobanteLoteCorreo()
    {
        for (String claveAccesoComprobante : clavesAccesoLote) {
             //Autorizacion autorizacion = servicioSri.buscarAutorizacion(claveAccesoComprobante);

             //Enviar toso los comprobantes que existen porque segun el SRI ya son validos
            //if (autorizacion != null && autorizacion.getEstado().equals(ServicioSri.AUTORIZADO)) {
                 try {
                enviarComprobanteCorreo(claveAccesoComprobante);
            } catch (ComprobanteElectronicoException ex) {
                Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            }
            //}
        }
    }
    
    private void enviarComprobanteCorreo(String claveAccesoTemp) throws ComprobanteElectronicoException {
        try {
            ClaveAcceso claveAcceso=new ClaveAcceso(claveAccesoTemp);
            JAXBContext jaxbContext = JAXBContext.newInstance(claveAcceso.getClassTipoComprobante());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            //Map<String, String> mapComprobante = UtilidadesComprobantes.decodificarArchivoBase64Offline(getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS,claveAccesoTemp), null, null);
            //StringReader reader = new StringReader(mapComprobante.get("comprobante"));
            String pathComprobanteFirmado = getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS,claveAccesoTemp);
            File file = new File(pathComprobanteFirmado);
            
            ComprobanteElectronico comprobante = (ComprobanteElectronico) jaxbUnmarshaller.unmarshal(file);

            String pathFile = getPathComprobante(CARPETA_RIDE, getNameRide(comprobante));
            Map<String,String> archivosPath=new HashMap<String,String>();
            archivosPath.put(claveAcceso.getTipoComprobante().getPrefijo()+"-"+comprobante.getInformacionTributaria().getPreimpreso()+".pdf",pathFile);
            archivosPath.put(comprobante.getInformacionTributaria().getPreimpreso()+".xml",getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS,claveAccesoTemp));
            
            try {
                
                String mensajeGenerado =getMensajeCorreo(claveAcceso.getTipoComprobante(),comprobante);
                
                ComprobanteElectronico comprobanteElectronico=buscarComprobanteLote(claveAccesoTemp);
                List<String> correosElectronicosTemp=comprobanteElectronico.getCorreos();
                
                //Si la lista de correos adicionales  
                if(correosElectronicosTemp==null)correosElectronicosTemp=new ArrayList<String>();
                //Agregar correos adjuntos en el comprobante electronico
                if(comprobanteElectronico.getInformacionAdicional()!=null)
                {
                    for (InformacionAdicional infoAdicional : comprobanteElectronico.getInformacionAdicional()) {
                        if(infoAdicional.getNombre().equals("correo"))
                            correosElectronicosTemp.add(infoAdicional.getValor());                                           
                    }
                }
                
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
        generarRideIndividual(this.claveAcceso);

    }
    
    private void generarRideLote() throws ComprobanteElectronicoException {
        for (String claveAccesoComprobante : clavesAccesoLote) {
            generarRideIndividual(claveAccesoComprobante);
        }
    }
    
    private void generarRideIndividual(String claveAccesoTemp) throws ComprobanteElectronicoException
    {
        try {            
            //COPIAR RECURSOS//
            //InputStream reporteInfoAdicional = this.reporteInfoAdicional.openStream();
            //InputStream reporteFormaPago = this.reporteFormaPago.openStream();
            //InputStream pathLogoImagen = this.pathLogoImagen.openStream();

            //FIN COPIA ARCHIVOS
            
            //Map<String, String> mapComprobante = UtilidadesComprobantes.decodificarArchivoBase64Offline(getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS,claveAccesoTemp), null, null);
            ClaveAcceso claveAcceso=new ClaveAcceso(claveAccesoTemp);
            
            //StringReader reader = new StringReader(mapComprobante.get("comprobante"));
            
            
            String pathComprobanteFirmado=getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS, claveAccesoTemp);
            File file=new File(pathComprobanteFirmado);
            
            JAXBContext jaxbContext = JAXBContext.newInstance(claveAcceso.getClassTipoComprobante());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ComprobanteElectronico comprobante = (ComprobanteElectronico) jaxbUnmarshaller.unmarshal(file);

            ComprobanteElectronicoReporte reporte =getComprobanteReporte(comprobante);
            
            List<Object> informacionAdiciona = reporte.getDetalles();
            Map<String, Object> datosMap = reporte.getMapReporte();
            datosMap.put("SUBREPORT_DIR", pathParentJasper);
            datosMap.put("SUBREPORT_INFO_ADICIONAL", reporteInfoAdicional);
            datosMap.put("SUBREPORT_FORMA_PAGO", reporteFormaPago);
            datosMap.put("fecha_hora_autorizacion","");
            datosMap.put("estado","");

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
            {
                return new NotaCreditoReporte(comprobante);
            }
            else
            {
                if(comprobante.getClass().equals(RetencionComprobante.class))
                {
                    RetencionElectronicaReporte retencionReporte=new RetencionElectronicaReporte(comprobante);
                    retencionReporte.setMapCodeAndNameTipoDocumento(mapCodeAndNameTipoDocumento);
                    retencionReporte.setMapCodeAndNameTipoRetecion(mapCodeAndNameTipoRetecion);
                    return retencionReporte;
                }
                else
                {
                    System.out.println("no esta comparando clases");
                    return null;
                }
            }
    }

    private void preValidacion() {

    }

    public JasperPrint getPrintJasper() {
        try {
            //Map<String, String> mapComprobante = UtilidadesComprobantes.decodificarArchivoBase64Offline(getPathComprobante(CARPETA_AUTORIZADOS), null, null);
            ClaveAcceso claveAcceso=new ClaveAcceso(this.claveAcceso);
            JAXBContext jaxbContext = JAXBContext.newInstance(claveAcceso.getClassTipoComprobante());
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            //StringReader reader = new StringReader(mapComprobante.get("comprobante"));
            String pathComprobanteFirmado = getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS, this.claveAcceso);
            File file = new File(pathComprobanteFirmado);
            
            ComprobanteElectronico comprobante = (ComprobanteElectronico) jaxbUnmarshaller.unmarshal(file);
            ComprobanteElectronicoReporte reporte = getComprobanteReporte(comprobante);

            List<Object> informacionAdicional = reporte.getDetalles();
            
            //InputStream reporteInfoAdicional = this.reporteInfoAdicional.openStream();
            //InputStream reporteFormaPago = this.reporteFormaPago.openStream();
            //InputStream pathLogoImagen = this.pathLogoImagen.openStream();

            Map<String, Object> datosMap = reporte.getMapReporte();
            datosMap.put("SUBREPORT_DIR", pathParentJasper);
            datosMap.put("fecha_hora_autorizacion","");
            datosMap.put("estado","");
            
            datosMap.put("SUBREPORT_INFO_ADICIONAL", reporteInfoAdicional);
            datosMap.put("SUBREPORT_FORMA_PAGO", reporteFormaPago);
            datosMap.put("imagen_logo", pathLogoImagen);

            /**
             * Agregar datos adicionales como por ejemplo los datos del pide de
             * pagina
             */
            //datosMap.putAll(mapAdicionalReporte);
            datosMap.putAll(getMapCopyAdicionalReporte());

            //datosMap.put("imagen_logo",is);
            //datosMap.put("imagen_logo", UtilidadesComprobantes.getStreamByPath(pathLogoImagen));
            //datosMap.put("imagen_logo",pathLogoImagen.openStream());

            return UtilidadesComprobantes.generarReporteJasperPrint(getPathJasper(comprobante), datosMap, informacionAdicional);

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
            System.out.println("generando "+claveAcceso);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ComprobanteElectronicoException(e.getMessage(), "Generando XML", ComprobanteElectronicoException.ERROR_COMPROBANTE);
        }

    }
    
    /**
     * Genera todos los comprobantes y devuelve un map con las claves de acceso
     * @throws ComprobanteElectronicoException 
     */
    
    private List<ClaveAcceso> generarLote() throws ComprobanteElectronicoException{
         
        List<ClaveAcceso> listaClaves=new ArrayList<ClaveAcceso>();                    
        try {
                    
            //claveAcceso = obtenerClaveAccesoLote();            

            //Generar los xml individuales de los comprobantes para procesar en lote
            clavesAccesoLote=new ArrayList<String>();
            for (ComprobanteElectronico comprobante : comprobantesLote) {
                String claveAccesoComprobante=obtenerClaveAcceso(comprobante);
                listaClaves.add(new ClaveAcceso(claveAccesoComprobante));
                        
                comprobante.getInformacionTributaria().setClaveAcceso(claveAccesoComprobante);
                clavesAccesoLote.add(claveAccesoComprobante);
                //ruc=comprobante.getInformacionTributaria().getRuc();
                StringWriter stringWriter = generarXml(comprobante,claveAccesoComprobante);
                ComprobantesElectronicosUtil.generarArchivoXml(stringWriter, getPathComprobanteConClaveAcceso(CARPETA_GENERADOS,claveAccesoComprobante));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ComprobanteElectronicoException(e.getMessage(), "Generando XML", ComprobanteElectronicoException.ERROR_COMPROBANTE);
        }
        return listaClaves;
  
    }

    private void firmar() throws ComprobanteElectronicoException {
        /**
         * Firmando el documento
         */
        FirmaElectronica firmaElectronica = new FirmaElectronica(getPathFirma(), claveFirma);
        Document documentoFirmado = firmaElectronica.firmar(getPathComprobante(CARPETA_GENERADOS));
        if (documentoFirmado != null) {
            try {
                ComprobantesElectronicosUtil.generarArchivoXml(documentoFirmado, getPathComprobante(CARPETA_FIRMADOS_SIN_ENVIAR));
                ComprobantesElectronicosUtil.generarArchivoXml(documentoFirmado, getPathComprobante(CARPETA_FIRMADOS));
                ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobante(CARPETA_GENERADOS));
            } catch (Exception ex) {
                Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    private void obtenerFirmasPorLote()
    {
        for (String claveAccesoComprobante : clavesAccesoLote) {
            
        }
    }
    
    
    private void firmarLote() throws ComprobanteElectronicoException {
        FirmaElectronica firmaElectronica = new FirmaElectronica(getPathFirma(), claveFirma);
        
        //String ruc="";
        List<String> comprobantesFirmados=new ArrayList<String>();
        
        for (String claveAccesoComprobante : clavesAccesoLote) {
            //ruc=comprobanteElectronico.getInformacionTributaria().getRuc();
            String claveAccesoTemp=claveAccesoComprobante;
            Document documentoFirmado = firmaElectronica.firmar(getPathComprobanteConClaveAcceso(CARPETA_GENERADOS,claveAccesoTemp));
            
            if (documentoFirmado != null) {
                try {
                    ComprobantesElectronicosUtil.generarArchivoXml(documentoFirmado, getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS,claveAccesoTemp));
                    ComprobantesElectronicosUtil.generarArchivoXml(documentoFirmado, getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS_SIN_ENVIAR,claveAccesoTemp));
                    ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobanteConClaveAcceso(CARPETA_GENERADOS,claveAccesoTemp));

                    String loteComprobanteData=("<![CDATA["+UtilidadesTextos.documentToString(documentoFirmado)+"]]>");
                    
                    comprobantesFirmados.add(loteComprobanteData);
                } catch (Exception ex) {
                    Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        StringWriter stringWriter = generarXmlLote(comprobantesFirmados, ruc);
        ComprobantesElectronicosUtil.generarArchivoXml(stringWriter, getPathComprobante(CARPETA_LOTE));
        
        //return comprobantesFirmados;

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
                    
                    //Copiar el archivo al siguiente nivel firmado a los enviados
                    ComprobantesElectronicosUtil.copiarArchivoXml(getPathComprobante(CARPETA_FIRMADOS),getPathComprobante(CARPETA_ENVIADOS));
                    //Elimina la carpeta de los firmados para saber que ya fue enviado
                    ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobante(CARPETA_FIRMADOS_SIN_ENVIAR));
                    
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
            servicioSri.setUrlFile(getPathComprobante(CARPETA_LOTE));

            if (servicioSri.verificarConexionRecepcion()) {
                System.out.println("Existe conexion");
                if (servicioSri.enviar()) {
                    System.out.println("Documento enviados");
                    for (String claveAccesoComprobante : clavesAccesoLote) {
                        //Mover todos los archivos individuales
                        String claveAccesoTemp=claveAccesoComprobante;
                        ComprobantesElectronicosUtil.copiarArchivoXml(getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS,claveAccesoTemp),getPathComprobanteConClaveAcceso(CARPETA_ENVIADOS,claveAccesoTemp));
                        //ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS,claveAccesoTemp));
                        ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobanteConClaveAcceso(CARPETA_FIRMADOS_SIN_ENVIAR,claveAccesoTemp));
                    }                    
                    
                    //Mover el archivo que contiene todos los archivos en lote a la carpeta de enviados
                    //ComprobantesElectronicosUtil.copiarArchivoXml(getPathComprobante(CARPETA_FIRMADOS),getPathComprobante(CARPETA_ENVIADOS));
                    //ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobante(CARPETA_FIRMADOS));
                    
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
        servicioSri.setUrlFile(getPathComprobante(CARPETA_LOTE));
        
        /**
         * Recogiendo autorizacion SRI
         */
        if (servicioSri.autorizarLote(claveAcceso)) {
            
            for (String claveAccesoComprobante : clavesAccesoLote) {
                //Generar los archivos de cada comprobante autorizado
                Autorizacion autorizacion=servicioSri.buscarAutorizacion(claveAccesoComprobante);
                
                if(autorizacion!=null && autorizacion.getEstado().equals(ServicioSri.AUTORIZADO))
                {
                    String claveAccesoTemp=autorizacion.getNumeroAutorizacion();
                    String xmlAutorizado = servicioSri.castAutorizacionToString(autorizacion);
                    ComprobantesElectronicosUtil.generarArchivoXml(xmlAutorizado, getPathComprobanteConClaveAcceso(CARPETA_AUTORIZADOS,claveAccesoTemp));
                    ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobanteConClaveAcceso(CARPETA_ENVIADOS,claveAccesoTemp));
                }
                
            }
            
            //Si el archivo se autoriza correctamente se elimina el archivo del lote
            //ComprobantesElectronicosUtil.copiarArchivoXml(getPathComprobante(CARPETA_ENVIADOS), getPathComprobante(CARPETA_FIRMADOS));
            ComprobantesElectronicosUtil.eliminarArchivo(getPathComprobante(CARPETA_LOTE));

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

        //claveAcceso.add(compborgetTipoComprobante());
        claveAcceso.add(comprobante.getTipoDocumento());

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
        System.out.println(comprobante.getTipoDocumento());
        switch (comprobante.getTipoDocumento()) {
            case ComprobanteElectronico.FACTURA:
                return "01";

            case ComprobanteElectronico.NOTA_CREDITO:
                return "04";
                
            case ComprobanteElectronico.RETENCION:
                return ComprobanteEnum.COMPROBANTE_RETENCION.getCodigo();

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
                
            case ComprobanteElectronico.RETENCION:
                return "07";

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
            else
            {
                if(ComprobanteEnum.COMPROBANTE_RETENCION.getCodigo().equals(comprobante.getInformacionTributaria().getCodigoDocumento()))
                {
                    prefijo=ComprobanteEnum.COMPROBANTE_RETENCION.getPrefijo();
                }
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
    
    public void addActionListerComprobanteElectronicoLote(ListenerComprobanteElectronicoLote e) {
        this.escuchaLote = e;
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

    public URL getPathRetencionJasper() {
        return pathRetencionJasper;
    }

    public void setPathRetencionJasper(URL pathRetencionJasper) {
        this.pathRetencionJasper = pathRetencionJasper;
    }
    
    

    public void setMapCodeAndNameFormaPago(Map<String, String> mapCodeAndNameFormaPago) {
        this.mapCodeAndNameFormaPago = mapCodeAndNameFormaPago;
    }

    public void setReporteInfoAdicional(JasperReport reporteInfoAdicional) {
        this.reporteInfoAdicional = reporteInfoAdicional;
    }

    public void setReporteFormaPago(JasperReport reporteFormaPago) {
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
            } else if (ComprobanteEnum.COMPROBANTE_RETENCION.getCodigo().equals(clave.tipoComprobante))
            {
                path=pathRetencionJasper.openStream();
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

    public ServicioSri getServicioSri() {
        return servicioSri;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public List<String> getClavesAccesoLote() {
        return clavesAccesoLote;
    }

    public void setClavesAccesoLote(List<String> clavesAccesoLote) {
        this.clavesAccesoLote = clavesAccesoLote;
    }

    public Map<String, String> getMapCodeAndNameTipoRetecion() {
        return mapCodeAndNameTipoRetecion;
    }

    public void setMapCodeAndNameTipoRetecion(Map<String, String> mapCodeAndNameTipoRetecion) {
        this.mapCodeAndNameTipoRetecion = mapCodeAndNameTipoRetecion;
    }

    public Map<String, String> getMapCodeAndNameTipoDocumento() {
        return mapCodeAndNameTipoDocumento;
    }

    public void setMapCodeAndNameTipoDocumento(Map<String, String> mapCodeAndNameTipoDocumento) {
        this.mapCodeAndNameTipoDocumento = mapCodeAndNameTipoDocumento;
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
