/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica;

import autorizacion.ws.sri.gob.ec.Autorizacion;
import autorizacion.ws.sri.gob.ec.AutorizacionComprobante;
import autorizacion.ws.sri.gob.ec.AutorizacionComprobantesOffline;
import autorizacion.ws.sri.gob.ec.AutorizacionComprobantesOfflineService;
import autorizacion.ws.sri.gob.ec.RespuestaComprobante;
import autorizacion.ws.sri.gob.ec.RespuestaLote;
import com.thoughtworks.xstream.XStream;
import static ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService.CARPETA_AUTORIZADOS;
import static ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService.CARPETA_ENVIADOS_SIN_RESPUESTA;
import static ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService.CARPETA_FIRMADOS_SIN_ENVIAR;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import static ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil.archivoToByte;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.UtilidadesComprobantes;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.XStreamUtil;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.ws.recepcion.Comprobante;
import ec.com.codesoft.codefaclite.ws.recepcion.Mensaje;
import ec.com.codesoft.codefaclite.ws.recepcion.RecepcionComprobantesOffline;
import ec.com.codesoft.codefaclite.ws.recepcion.RecepcionComprobantesOfflineService;
import ec.com.codesoft.codefaclite.ws.recepcion.RespuestaSolicitud;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;
import org.jfree.util.Log;

/**
 *
 * @author Carlos
 */
public class ServicioSri {
 
    private static final String namespaceURI="http://ec.gob.sri.ws.recepcion";
    private static final String namespaceAutorizarURI="http://ec.gob.sri.ws.autorizacion";
    private static final String localPort="RecepcionComprobantesOfflineService";
    private static final String localPortAutorizacion="AutorizacionComprobantesOfflineService";
    
    public static final String AUTORIZADO="AUTORIZADO";
    
    private static final Long TIEMPO_ESPERA_AUTORIZACION =1000L;
    /**
     * Numeros de intento para esperar que el sri me devuelva la consulta de autorizacion de un documentos
     */
    private static final Long INTENTOS_AUTORIZACION =15L; //Esperar maximo 15 segundos 
    
    private static final Long INTENTOS_AUTORIZACION_LOTE = 30L;
    
    //private String uri="https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
    private String uri_recepcion;
    private String uri_autorizacion;

    static {
        configurarSslSri();
    }

    /**
     * El SRI redirige sus endpoints HTTPS a IPs (ej: 181.113.227.222). El certificado
     * esta emitido para el dominio sri.gob.ec, no para la IP, por lo que Java rechaza
     * la conexion despues del redirect. Este verifier acepta la IP si el certificado
     * del servidor pertenece al dominio sri.gob.ec.
     */
    private static void configurarSslSri() {
        // Fuerza TLS 1.2: Java 8 negocia TLS 1.0 por defecto y el SRI puede rechazar la conexion
        try {
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        } catch (Exception e) {
            Logger.getLogger(ServicioSri.class.getName()).log(Level.WARNING, "No se pudo configurar TLSv1.2 para conexiones SRI", e);
        }

        final HostnameVerifier verifierOriginal = HttpsURLConnection.getDefaultHostnameVerifier();
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                if (hostname.matches("^\\d+\\.\\d+\\.\\d+\\.\\d+$")) {
                    try {
                        java.security.cert.Certificate[] certs = session.getPeerCertificates();
                        if (certs.length > 0 && certs[0] instanceof java.security.cert.X509Certificate) {
                            java.security.cert.X509Certificate x509 = (java.security.cert.X509Certificate) certs[0];
                            String cn = x509.getSubjectX500Principal().getName();
                            if (cn.contains("sri.gob.ec")) {
                                return true;
                            }
                        }
                    } catch (Exception e) {
                        Logger.getLogger(ServicioSri.class.getName()).log(Level.WARNING, "No se pudo verificar certificado SRI en IP: " + hostname, e);
                    }
                }
                return verifierOriginal.verify(hostname, session);
            }
        });
    }
    
    private RecepcionComprobantesOfflineService servicio;
    private AutorizacionComprobantesOfflineService servicioAutorizacion;
    
    private List<Mensaje> mensajes;
    private List<Autorizacion> autorizaciones ;
    
    /**
     * En esta lista se van a grabar los comprobantes que tuvieron problemas al enviar al SRI para que les autoricen
     */
    private List<Comprobante> comprobantesNoRecibidos;
    /**
     * El modo con el que va a trabajar el web service
     */
    private String modo;
    
    /**
     * Path donde se encuentra el archivo para enviar o recibir
     */
    private String urlFile;

    public ServicioSri() {
    }
    
    

    public ServicioSri(String uri_recepcion,String modo) {
        this.uri_recepcion = uri_recepcion;
        this.modo=modo;
    }
    
    public ServicioSri(String uri_recepcion) {
        this.uri_recepcion = uri_recepcion;
    }

    public String getUri_recepcion() {
        return uri_recepcion;
    }

    public void setUri_recepcion(String uri_recepcion) {
        this.uri_recepcion = uri_recepcion;
    }

    public String getUri_autorizacion() {
        return uri_autorizacion;
    }

    public void setUri_autorizacion(String uri_autorizacion) {
        this.uri_autorizacion = uri_autorizacion;
    }
    
    
    
            
    public boolean verificarConexionRecepcion() throws ComprobanteElectronicoException{
        
        //TODO: Poner esta constante en algun otro lugar
        final int numeroIntentos = 10;
        for (int i = 0; i < numeroIntentos; i++) 
        {
           
            //Pongo en esta parte porque al final genera error
            if(i>0)
            {
                try {                
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            try {

                URL url = new URL(uri_recepcion);
                QName qname = new QName(namespaceURI, localPort);
                servicio = new RecepcionComprobantesOfflineService(url, qname);
                System.out.println("si existe servicio con sri");
                return true;
            } catch (MalformedURLException ex) {
                
                ex.printStackTrace();
                Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("no existe servicio");
                throw new ComprobanteElectronicoException("Error de formato de la url" + "\n" + ex.getMessage(), "Verificar conexion recibir documentos SRI", ComprobanteElectronicoException.ERROR_COMPROBANTE);

            } catch (javax.xml.ws.WebServiceException wse) {
                Logger.getLogger(ServicioSri.class.getName()).log(Level.WARNING,"Intentando revisar conexión Sri, # intento: "+(i+1));
                //Si no llega al final no ejecuta la expecion
                if((i+1)<numeroIntentos)
                {
                    continue;
                }                
                wse.printStackTrace();
                System.out.println("No se puede acceder al web servicio");
                throw new ComprobanteElectronicoException("No existe conexion con el SRI para enviar documentos" + "\n" + wse.getMessage(), "Verificar conexion recibir documentos SRI", ComprobanteElectronicoException.ERROR_COMPROBANTE);

            }
           
        }
        return false;
    }

    public boolean verificarConexionAutorizar()
    {
        try {
            // Carga el WSDL desde el classpath para evitar el problema SSL del SRI
            // (el servidor SRI redirige a una IP cuyo certificado no tiene ese IP como SAN)
            URL wsdlUrl = ServicioSri.class.getClassLoader().getResource("META-INF/wsdl/AutorizacionComprobantesOffline.wsdl");
            if (wsdlUrl == null) {
                Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, "No se encontro el WSDL de autorizacion en el classpath");
                return false;
            }
            QName qname = new QName(namespaceAutorizarURI,localPortAutorizacion);
            servicioAutorizacion = new AutorizacionComprobantesOfflineService(wsdlUrl, qname);
            System.out.println("si existe servicio con sri autorizacion");
            return true;
        } catch(javax.xml.ws.WebServiceException ex)
        {
            Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error con el web service");
            return false;
        }
    }
    
    /**
     * Todo : Ver si en un futuro puedo usar el mismo metodo que enviar por lote
     * @return 
     */
    public Boolean enviar() throws ComprobanteElectronicoException
    {
        //TODO: Solucion temporal que aveces sale un error de conexion con el web service para intentar unas 3 veces antes de terminar
        final int INTENTO_MAXIMO=10;
        
        //TODO: Tener cuidado con estas 
        File archivoXMLFirmado = new File(urlFile);
        
        byte[] bytesEnviar=null;
        try {
            bytesEnviar= archivoToByte(archivoXMLFirmado);
        } catch (IOException ex) {
            Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
            throw new ComprobanteElectronicoException(ex.getMessage(), "Enviando Sri", ComprobanteElectronicoException.ERROR_COMPROBANTE);
        }
        
        for (int i = 0; i<INTENTO_MAXIMO;i++) 
        {
            try {
                if(i>0)Thread.sleep(1000); //evita que si la primera vez funciona lo haga rapido para las siguientes veces hago más lento
                mensajes = null;
                RecepcionComprobantesOffline port = servicio.getRecepcionComprobantesOfflinePort();
                RespuestaSolicitud respuestaSolicitud = port.validarComprobante(bytesEnviar);
                String estado = respuestaSolicitud.getEstado();
                
                if (respuestaSolicitud.getComprobantes() != null
                        && respuestaSolicitud.getComprobantes().getComprobante() != null
                        && !respuestaSolicitud.getComprobantes().getComprobante().isEmpty()
                        && respuestaSolicitud.getComprobantes().getComprobante().get(0).getMensajes() != null) {
                    mensajes = respuestaSolicitud.getComprobantes().getComprobante().get(0).getMensajes().getMensaje();
                }
                
                if ("RECIBIDA".equals(estado)) {
                    return true;
                }
                
                //todo: mejorar esta parte para tener clasificados mensajes y esos código del sri
                //nota: 70-CLAVE DE ACCESO EN PROCESAMIENTO -La clave de acceso 1902202601239005588100110011000000144760000000017  esta en procesamiento VALOR DEVUELTO POR EL PROCEDIMIENTO: SI
                if ("DEVUELTA".equals(estado)) {
                    String identificador = (mensajes != null && !mensajes.isEmpty()) ? mensajes.get(0).getIdentificador() : null;
                    String mensajeSri = (mensajes != null && !mensajes.isEmpty()) ? mensajes.get(0).getMensaje() : "";

                    if ("70".equals(identificador)) {
                        Logger.getLogger(ServicioSri.class.getName()).log(Level.WARNING, "CLAVE DE ACCESO EN PROCESAMIENTO, el Sri esta procesando y se esta demorando pero el procesa sigue en marcha de forma correcta");
                        return true;
                    } else if (mensajeSri != null && mensajeSri.toUpperCase().contains("CLAVE ACCESO REGISTRADA")) {
                        // El SRI ya tiene el comprobante de un envio anterior sin respuesta.
                        // No es rechazo: se continua a ETAPA_AUTORIZAR para consultar su estado.
                        Logger.getLogger(ServicioSri.class.getName()).log(Level.WARNING,
                            "SRI responde CLAVE ACCESO REGISTRADA (identificador={0}, info={1}). Se procede a consultar autorizacion.",
                            new Object[]{identificador, mensajes.get(0).getInformacionAdicional()});
                        return true;
                    } else {
                        //Si no es ninguno de los casos anteriores asumo que es un error del Sri
                        throw new ComprobanteElectronicoException(mensajes.get(0).getMensaje()+": "+mensajes.get(0).getInformacionAdicional(), "Rechazado Sri", ComprobanteElectronicoException.RECHAZADO,CARPETA_FIRMADOS_SIN_ENVIAR);
                    }

                }
                
                //Caso que no sea ninguno de los otros
                throw new ComprobanteElectronicoException(
                        "Estado no esperado: " + estado,
                        "Enviando Sri",
                        ComprobanteElectronicoException.ERROR_COMPROBANTE
                );

            }
            catch (ComprobanteElectronicoException ex) 
            {
                // Si el error ya fue controlado por la lógica del negocio, no reintentar
                throw ex;
            }
            catch (Exception ex) {
                //Ver que me salio, y finalmente volver intentar
                Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, "Error en intento " + (i + 1) + " de " + INTENTO_MAXIMO, ex);
                
            }
        }
        
        throw new ComprobanteElectronicoException("Intentos Agotados", "Enviando Sri", ComprobanteElectronicoException.ERROR_COMPROBANTE);
    } 
    
    
    public void enviarLote() throws ComprobanteElectronicoException
    {
        final int INTENTO_MAXIMO=10;
        for (int i = 0; ; ) {
            try {
                comprobantesNoRecibidos=new ArrayList<Comprobante>();
                //File archivoXMLFirmado = new File("C:\\CodefacRecursos\\comprobantes\\pruebas\\firmados\\0103201801172421895100110010010000000010000000011.xml");
                File archivoXMLFirmado = new File(urlFile);
                RecepcionComprobantesOffline port= servicio.getRecepcionComprobantesOfflinePort();
                RespuestaSolicitud respuestaSolicitud = port.validarComprobante(archivoToByte(archivoXMLFirmado));
                //System.out.println(respuestaSolicitud.getEstado()); //RECIBIDA DEVUELTA         
                comprobantesNoRecibidos=respuestaSolicitud.getComprobantes().getComprobante();
                for (Comprobante comprobantesNoRecibido : comprobantesNoRecibidos) {
                    
                    //Si en los comprobantes no recibidos viene el signo n/a significa que es un error global no del comprobante
                    if(comprobantesNoRecibido.getClaveAcceso().equals("N/A"))
                    {                        
                        for (Mensaje mensaje : comprobantesNoRecibido.getMensajes().getMensaje()) {
                            /*String mensajeError=
                                    "Identificador:"+mensaje.getIdentificador()+"\n"+
                                    "Info Adicional:"+mensaje.getInformacionAdicional()+"\n"+
                                    "Mensaje:"+mensaje.getMensaje()+"\n"+
                                    "Tipo:"+mensaje.getTipo();                            */
                            String mensajeError=UtilidadesComprobantes.castMensajeToString(mensaje);
                            throw new ComprobanteElectronicoException(mensajeError, "Enviando Sri Lote", ComprobanteElectronicoException.ERROR_COMPROBANTE);
                        }
                    }
                }
                
                break; //Si termina el proceso normal sale del ciclo repetitivo
                /*if(respuestaSolicitud.getComprobantes().getComprobante().size()==0)
                {
                    return true;
                }
                else
                {
                    mensajes=respuestaSolicitud.getComprobantes().getComprobante().get(0).getMensajes().getMensaje();
                    return false;
                }*/

            } catch (IOException ex) {
                Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
                
            } catch(Exception ex)
            {
                ex.printStackTrace();
                Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);            
                if(i==INTENTO_MAXIMO)
                {
                    throw new ComprobanteElectronicoException(ex.getMessage(), "Enviando Sri", ComprobanteElectronicoException.ERROR_COMPROBANTE);
                }
                else
                {
                    i++;
                }
                //throw new ComprobanteElectronicoException(ex.getMessage(),"Enviando Sri",ComprobanteElectronicoException.ERROR_COMPROBANTE);
            }
        }
       //comprobantesNoRecibidos return new ArrayList<Comprobante>();
    }    
   
    public boolean autorizar(String claveAcceso) throws ComprobanteElectronicoException
    {
       if(verificarConexionAutorizar())
       {
           AutorizacionComprobantesOffline port= servicioAutorizacion.getAutorizacionComprobantesOfflinePort();
           // Sobreescribe el endpoint para soportar modo prueba/produccion
           // (el WSDL local tiene la URL de produccion; hay que apuntar al correcto segun configuracion)
           ((BindingProvider) port).getRequestContext().put(
               BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
               uri_autorizacion.replace("?wsdl", ""));
           for(int i=0;i<INTENTOS_AUTORIZACION;i++)
           {
               
               try {
                   
                   RespuestaComprobante respuesta=port.autorizacionComprobante(claveAcceso);
                   autorizaciones=respuesta.getAutorizaciones().getAutorizacion();
                   if(autorizaciones.size()==0)
                   {
                        Thread.sleep(TIEMPO_ESPERA_AUTORIZACION);                        
                   }
                   else
                   {
                       if(autorizaciones.get(0).getEstado().equals(AUTORIZADO))
                       {
                           Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE,"Se autorizo en el intento: "+i);
                            return true;
                       }if(autorizaciones.get(0).getEstado().equals("EN PROCESO"))
                       {
                           //TODO: Si el mensaje es en proceso sigue esperando hasta que devuelva autorizado o error
                       }
                       else
                       {
                           Autorizacion.Mensajes mensajes=autorizaciones.get(0).getMensajes();
                           String mensajeError="";
                           for (autorizacion.ws.sri.gob.ec.Mensaje mensaje : mensajes.getMensaje()) {
                               System.out.println(mensaje.getIdentificador());
                               System.out.println(mensaje.getInformacionAdicional());
                               System.out.println(mensaje.getMensaje());
                               System.out.println(mensaje.getTipo());
                               mensajeError+=mensaje.getMensaje()+"\n"+mensaje.getInformacionAdicional();
                           }
                           
                           //Si el Sri lanza algun error coloco en la carpeta de rechazados
                           throw new ComprobanteElectronicoException(mensajeError," Autorizando",ComprobanteElectronicoException.RECHAZADO,ComprobanteElectronicoService.CARPETA_ENVIADOS_SIN_RESPUESTA);
                           
                       }
                   }
               } catch (ComprobanteElectronicoException ex) {
                   throw ex;
               } catch (InterruptedException ex) {
                   Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
                   throw new ComprobanteElectronicoException(ex.getMessage()," Autorizando",ComprobanteElectronicoException.ERROR_COMPROBANTE);
               } catch (Exception ex) {
                   // Error de red (Connection reset, timeout, etc.) - reintenta hasta agotar intentos
                   Logger.getLogger(ServicioSri.class.getName()).log(Level.WARNING, "Error de red autorizando (intento "+(i+1)+" de "+INTENTOS_AUTORIZACION+"): "+ex.getMessage());
                   if (i + 1 >= INTENTOS_AUTORIZACION) {
                       throw new ComprobanteElectronicoException(ex.getMessage()," Autorizando",ComprobanteElectronicoException.ERROR_COMPROBANTE);
                   }
                   try { Thread.sleep(TIEMPO_ESPERA_AUTORIZACION); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
               }
           }
           
           
           validacionPostComprobarAutorizacion(new ClaveAcceso(claveAcceso));
           
           //Si sale del bucle sin retornar asumo que excedio el tiempo de espera y lanzo una advertencia
           throw new ComprobanteElectronicoException("Se excedio el tiempo de espera para autorizar el documento , Por favor inténtelo mas tarde","Autorizando",ComprobanteElectronicoException.ERROR_COMPROBANTE);
       }
       else
       {
           throw new ComprobanteElectronicoException("El servicio para autorizar el comprobante no esta disponible","Autorizando",ComprobanteElectronicoException.ERROR_COMPROBANTE);
       }
       //return false;
       
    }
    
    private void validacionPostComprobarAutorizacion(ClaveAcceso claveAcceso) throws ComprobanteElectronicoException
    {
        //Si ha pasado más de 1 día esperando recibir respuesta lo clasifico como error para analizar el problema
        java.util.Date fechaEmisionTolerable=UtilidadesFecha.sumarDiasFecha(claveAcceso.fechaEmision, 1);
        if(UtilidadesFecha.compararFechaSinImportarHora(fechaEmisionTolerable,UtilidadesFecha.getFechaHoy())<0)
        {
            throw new ComprobanteElectronicoException("ERRROR TIEMPO AUTORIZACION: el sri no ha respondiendo en la etapa de autorización lo cual puede ser un error","Prevalidar",ComprobanteElectronicoException.RECHAZADO,CARPETA_ENVIADOS_SIN_RESPUESTA);
        }
    }
    
    public boolean autorizarLote(String claveAcceso) throws ComprobanteElectronicoException
    {
       if(verificarConexionAutorizar())
       {
           
           for(int i=0;i<INTENTOS_AUTORIZACION_LOTE;i++)
           {
               try {
                   AutorizacionComprobantesOffline port= servicioAutorizacion.getAutorizacionComprobantesOfflinePort();
                   ((BindingProvider) port).getRequestContext().put(
                       BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                       uri_autorizacion.replace("?wsdl", ""));
                   RespuestaLote respuesta=port.autorizacionComprobanteLote(claveAcceso);
                   autorizaciones=respuesta.getAutorizaciones().getAutorizacion();
                   if(autorizaciones.size()==0)
                   {
                        Thread.sleep(TIEMPO_ESPERA_AUTORIZACION);                        
                   }
                   else
                   {
                       Boolean esperarAutorizacion=false;
                       for (Autorizacion autorizacion : autorizaciones) {
                           if (autorizacion.getEstado().equals("AUTORIZADO") || autorizacion.getEstado().equals("NO AUTORIZADO")) 
                           {
                               //Si solo existe un unico registro y no se autoriza lanzo el error
                               if(autorizacion.getEstado().equals("NO AUTORIZADO") && autorizaciones.size()==1)
                               {
                                   String mensajeError=UtilidadesComprobantes.castMensajeAutorizadoToString(autorizacion.getMensajes());
                                   throw new ComprobanteElectronicoException(mensajeError,"Autorizando",ComprobanteElectronicoException.ERROR_COMPROBANTE);
                               }
                               break;
                           }
                           else if(autorizacion.getEstado().equals("EN PROCESO"))
                           {
                               esperarAutorizacion=true;
                           }
                       }
                       
                       
                       if(!esperarAutorizacion)
                       {
                            return true;
                       }
                       
 
                   }
               } catch (InterruptedException ex) {
                   Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
           
           //Si sale del bucle sin retornar asumo que excedio el tiempo de espera
           throw new ComprobanteElectronicoException("Se excedio el tiempo de espera para autorizar el documento , Por favor inténtelo mas tarde","Autorizando",ComprobanteElectronicoException.ERROR_COMPROBANTE);
           
       }
       else
       {
           throw new ComprobanteElectronicoException("No existe comunicación con el Sri para autorizar el comprobante","Autorizando",ComprobanteElectronicoException.ERROR_COMPROBANTE);
       }
       //return false;
       
    }
    
    /**
     * Busca entre los documentos autorizados alguno en especifico por la clave de acceso
     * @return 
     */
    public Autorizacion buscarAutorizacion(String claveAcceso)
    {
        for (Autorizacion autorizacion : autorizaciones) {
            if(autorizacion.getNumeroAutorizacion()!=null && autorizacion.getNumeroAutorizacion().equals(claveAcceso))
            {
                return autorizacion;
            }
        }
        return null;
    }
    
    /**
     * Obtiene la respuesta del comprobante individual y lo transforma en texto
     * @return
     * @throws ComprobanteElectronicoException 
     */
    public String obtenerRespuestaAutorizacion() throws ComprobanteElectronicoException
    {
        try {
            Autorizacion item=autorizaciones.get(0);
            //System.out.println(item.getFechaAutorizacion());
            item.setComprobante("<![CDATA[" + item.getComprobante() + "]]>");            
            XStream xstream = XStreamUtil.getRespuestaXStream();
            Writer writer = null;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xstream.toXML(item, writer);
            String xmlAutorizacion = outputStream.toString("UTF-8");
            
            /**
             * Solucion temporal para obtener la fecha con el tiempo 
             */
            int posicionInicial=xmlAutorizacion.indexOf("<fechaAutorizacion class=\"fechaAutorizacion\">");
            int posicionFinal=xmlAutorizacion.indexOf("</fechaAutorizacion>");
            xmlAutorizacion=xmlAutorizacion.substring(0,posicionInicial)+"<fechaAutorizacion>"+item.getFechaAutorizacion()+xmlAutorizacion.substring(posicionFinal,xmlAutorizacion.length());
            
            //System.out.println(xmlAutorizacion);
            
            if (item.getEstado().equals("AUTORIZADO")) {
                return xmlAutorizacion;
            }
            else
            {
                throw  new ComprobanteElectronicoException("Documeto no autorizado","Leyendo respuesta autorizado",ComprobanteElectronicoException.ERROR_COMPROBANTE);
                //implementar cuando esta autorizado
            }
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ComprobanteElectronicoException(ex.getMessage(),"Leyendo respuesta autorizado",ComprobanteElectronicoException.ERROR_COMPROBANTE);
        } catch (IOException ex) {
            Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ComprobanteElectronicoException(ex.getMessage(),"Leyendo respuesta autorizado",ComprobanteElectronicoException.ERROR_COMPROBANTE);
        }

    }
    
    public ComprobanteElectronico castComprobanteToAutorizacion(Autorizacion autorizacion)
    {
        //Si no existe nada grabado en el campo de autorizacion entonces devuelvo nulo
        if(autorizacion.getComprobante()==null)
            return null;
        
        if(autorizacion.getNumeroAutorizacion()==null || autorizacion.getNumeroAutorizacion().isEmpty())
        {
            return null;
        }
        
        try {
            ClaveAcceso claveAcceso=new ClaveAcceso(autorizacion.getNumeroAutorizacion());
            //JAXBContext jaxbContext = JAXBContext.newInstance(ComprobanteEnum.FACTURA.getClase());
            JAXBContext jaxbContext = JAXBContext.newInstance(claveAcceso.getTipoComprobante().getClase());
            String comprobanteStr=autorizacion.getComprobante();
            //Quitar etiquetas de cdata
            comprobanteStr=comprobanteStr.replaceAll("<!\\[CDATA\\[","");
            comprobanteStr=comprobanteStr.replaceAll("\\]\\]>","");
                    
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();      
            StringReader reader = new StringReader(comprobanteStr);
            
            ComprobanteElectronico comprobanteElectronico=(ComprobanteElectronico)jaxbUnmarshaller.unmarshal(reader);
            return comprobanteElectronico;
            
        } catch (JAXBException ex) {
            Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Cambiar el formato de autorizacion  a String
     * @param item
     * @return
     * @throws ComprobanteElectronicoException 
     */
    public String castAutorizacionToString(Autorizacion item) throws ComprobanteElectronicoException
    {
          try {
            item.setComprobante("<![CDATA[" + item.getComprobante() + "]]>");            
            XStream xstream = XStreamUtil.getRespuestaXStream();
            Writer writer = null;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xstream.toXML(item, writer);
            String xmlAutorizacion = outputStream.toString("UTF-8");
            
            /**
             * Solucion temporal para obtener la fecha con el tiempo 
             */
            int posicionInicial=xmlAutorizacion.indexOf("<fechaAutorizacion class=\"fechaAutorizacion\">");
            int posicionFinal=xmlAutorizacion.indexOf("</fechaAutorizacion>");
            xmlAutorizacion=xmlAutorizacion.substring(0,posicionInicial)+"<fechaAutorizacion>"+item.getFechaAutorizacion()+xmlAutorizacion.substring(posicionFinal,xmlAutorizacion.length());
            
            //System.out.println(xmlAutorizacion);
            
            if (item.getEstado().equals("AUTORIZADO")) {
                return xmlAutorizacion;
            }
            else
            {
                throw  new ComprobanteElectronicoException("Documeto no autorizado","Leyendo respuesta autorizado",ComprobanteElectronicoException.ERROR_COMPROBANTE);
                //implementar cuando esta autorizado
            }
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ComprobanteElectronicoException(ex.getMessage(),"Leyendo respuesta autorizado",ComprobanteElectronicoException.ERROR_COMPROBANTE);
        } catch (IOException ex) {
            Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
            throw  new ComprobanteElectronicoException(ex.getMessage(),"Leyendo respuesta autorizado",ComprobanteElectronicoException.ERROR_COMPROBANTE);
        }
         
        
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public String getUrlFile() {
        return urlFile;
    }

    public void setUrlFile(String urlFile) {
        this.urlFile = urlFile;
    }

    public List<Autorizacion> getAutorizacion() {
        return autorizaciones;
    }

    public List<Comprobante> getComprobantesNoRecibidos() {
        return comprobantesNoRecibidos;
    }
    
    
    
    
    
}
