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
import com.thoughtworks.xstream.XStream;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import static ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil.archivoToByte;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.XStreamUtil;
import ec.com.codesoft.codefaclite.ws.recepcion.Mensaje;
import ec.com.codesoft.codefaclite.ws.recepcion.RecepcionComprobantesOffline;
import ec.com.codesoft.codefaclite.ws.recepcion.RecepcionComprobantesOfflineService;
import ec.com.codesoft.codefaclite.ws.recepcion.RespuestaSolicitud;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;

/**
 *
 * @author Carlos
 */
public class ServicioSri {
 
    private static final String namespaceURI="http://ec.gob.sri.ws.recepcion";
    private static final String namespaceAutorizarURI="http://ec.gob.sri.ws.autorizacion";
    private static final String localPort="RecepcionComprobantesOfflineService";
    private static final String localPortAutorizacion="AutorizacionComprobantesOfflineService";
    
    private static final Long TIEMPO_ESPERA_AUTORIZACION =400L;
    private static final Long INTENTOS_AUTORIZACION =400L; 
    
    //private String uri="https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
    private String uri_recepcion;
    private String uri_autorizacion;
    
    private RecepcionComprobantesOfflineService servicio;
    private AutorizacionComprobantesOfflineService servicioAutorizacion;
    
    private List<Mensaje> mensajes;
    private List<Autorizacion> autorizacion ;
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
    
    
    
            
    public boolean verificarConexionRecepcion() {
        try {
            URL url = new URL(uri_recepcion);
            QName qname = new QName(namespaceURI,localPort);
            servicio = new RecepcionComprobantesOfflineService(url, qname);
            System.out.println("si existe servicio con sri");
            return true;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("no existe servicio");
            return false;
        }
    }

    public boolean verificarConexionAutorizar()
    {
        try {
            URL url = new URL(uri_autorizacion);
            QName qname = new QName(namespaceAutorizarURI,localPortAutorizacion);
            servicioAutorizacion=new AutorizacionComprobantesOfflineService(url,qname);
            System.out.println("si existe servicio con sri autorizacion");
            return true;
        } catch (MalformedURLException ex) {
            Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("no existe servicio");
            return false;
        }
    }
    
    public Boolean enviar()
    {
        try {
            //File archivoXMLFirmado = new File("C:\\e-spirit-pruebas\\firmados\\2609201701179053733100110010020000072070000000011.xml");
            File archivoXMLFirmado = new File(urlFile);
            RecepcionComprobantesOffline port= servicio.getRecepcionComprobantesOfflinePort();
            RespuestaSolicitud respuestaSolicitud = port.validarComprobante(archivoToByte(archivoXMLFirmado));
            //System.out.println(respuestaSolicitud.getEstado()); //RECIBIDA DEVUELTA           
            if(respuestaSolicitud.getComprobantes().getComprobante().size()==0)
            {
                return true;
            }
            else
            {
                mensajes=respuestaSolicitud.getComprobantes().getComprobante().get(0).getMensajes().getMensaje();
                return false;
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean autorizar(String claveAcceso) throws ComprobanteElectronicoException
    {
       if(verificarConexionAutorizar())
       {
           
           for(int i=0;i<INTENTOS_AUTORIZACION;i++)
           {
               try {
                   AutorizacionComprobantesOffline port= servicioAutorizacion.getAutorizacionComprobantesOfflinePort();
                   RespuestaComprobante respuesta=port.autorizacionComprobante(claveAcceso);
                   autorizacion=respuesta.getAutorizaciones().getAutorizacion();
                   if(autorizacion.size()==0)
                   {
                        Thread.sleep(TIEMPO_ESPERA_AUTORIZACION);                        
                   }
                   else
                   {
                       if(autorizacion.get(0).getEstado().equals("AUTORIZADO"))
                       {
                            return true;
                       }
                       else
                       {
                           Autorizacion.Mensajes mensajes=autorizacion.get(0).getMensajes();
                           String mensajeError="";
                           for (autorizacion.ws.sri.gob.ec.Mensaje mensaje : mensajes.getMensaje()) {
                               System.out.println(mensaje.getIdentificador());
                               System.out.println(mensaje.getInformacionAdicional());
                               System.out.println(mensaje.getMensaje());
                               System.out.println(mensaje.getTipo());
                               mensajeError+=mensaje.getMensaje()+"\n"+mensaje.getInformacionAdicional();
                           }
                           throw new ComprobanteElectronicoException(mensajeError," Autorizando",ComprobanteElectronicoException.ERROR_COMPROBANTE);
                           //for
                       }
                   }
               } catch (InterruptedException ex) {
                   Logger.getLogger(ServicioSri.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
       }
       return false;
       
    }
    
    public String obtenerRespuestaAutorizacion() throws ComprobanteElectronicoException
    {
        try {
            Autorizacion item=autorizacion.get(0);
            item.setComprobante("<![CDATA[" + item.getComprobante() + "]]>");            
            XStream xstream = XStreamUtil.getRespuestaXStream();
            Writer writer = null;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            xstream.toXML(item, writer);
            String xmlAutorizacion = outputStream.toString("UTF-8");
            
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
    
    
    
}
