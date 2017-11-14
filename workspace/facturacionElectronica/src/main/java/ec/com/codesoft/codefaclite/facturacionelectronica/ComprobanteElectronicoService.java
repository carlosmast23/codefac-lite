/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.ws.recepcion.Mensaje;
import ec.com.codesoft.ejemplo.utilidades.texto.UtilidadesTextos;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.w3c.dom.Document;

/**
 *
 * @author Carlos
 */
public class ComprobanteElectronicoService {
    
    public static final String CARPETA_GENERADOS="generados";
    public static final String CARPETA_FIRMADOS="firmados";
    public static final String CARPETA_ENVIADOS="enviados";
    public static final String CARPETA_AUTORIZADOS="autorizados";
    public static final String CARPETA_NO_AUTORIZADOS="no_autorizados";
    public static final String CARPETA_CONFIGURACION="configuracion";
    public static final String CARPETA_RIDE="ride";
    
    public static final String MODO_PRODUCCION="produccion";
    public static final String MODO_PRUEBAS="pruebas";
    public static final String MODO_FACTURACION_NORMAL="1";
    
    public static final Integer ETAPA_GENERAR=1;
    public static final Integer ETAPA_PRE_VALIDAR=2;
    public static final Integer ETAPA_FIRMAR=3;
    public static final Integer ETAPA_ENVIAR=4;
    public static final Integer ETAPA_AUTORIZAR=5;
    public static final Integer ETAPA_RIDE=6;
    
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
    
    private ServicioSri servicioSri;

    public ComprobanteElectronicoService(String pathBase, String nombreFirma, String claveFirma, String modoFacturacion, ComprobanteElectronico comprobante) {
        this.pathBase = pathBase;
        this.nombreFirma = nombreFirma;
        this.claveFirma = claveFirma;
        this.modoFacturacion = modoFacturacion;
        this.comprobante = comprobante;
    }
    
    public void procesarComprobante()
    {
        if(etapaActual==ETAPA_GENERAR)
        {
            generar();
        }
        
        if(etapaActual==ETAPA_PRE_VALIDAR)        
        {
            preValidacion();
        }
        
        if(etapaActual==ETAPA_FIRMAR)   
        {
            firmar();
        }
        
        if(etapaActual==ETAPA_ENVIAR)        
        {
            enviarSri();
        }
        
        if(etapaActual==ETAPA_ENVIAR)  
        {
            autorizarSri();
        }
        
        if(etapaActual==ETAPA_RIDE)  
        {
            generarRide();
        }
        
    }
    
    private void generarRide()
    {
    
    }
    
    private void preValidacion()
    {
    
    }
    
    private void generar()
    {
        StringWriter stringWriter= generarXml(comprobante);
        claveAcceso=obtenerClaveAcceso();
        ComprobantesElectronicosUtil.generarArchivoXml(stringWriter,getPathComprobante(CARPETA_GENERADOS));
  
    }
    
    private void firmar()
    {
                /**
         * Firmando el documento
         */
        FirmaElectronica firmaElectronica=new FirmaElectronica(getPathFirma(),claveFirma);
        Document documentoFirmado=firmaElectronica.firmar(getPathComprobante(CARPETA_GENERADOS));
        if(documentoFirmado!=null)
        {            
            try {
                ComprobantesElectronicosUtil.generarArchivoXml(documentoFirmado, getPathComprobante(CARPETA_FIRMADOS));
            } catch (Exception ex) {
                Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    private void enviarSri()
    {
        servicioSri=new ServicioSri("","");
        if(servicioSri.verificarConexionRecepcion())
        {
            System.out.println("Existe conexion");
            if(servicioSri.enviar())
            {
                System.out.println("Documento enviados");
            }
            else
            {
                for (Mensaje mensaje : servicioSri.getMensajes()) {
                    System.out.println(mensaje.getIdentificador()+"-"+mensaje.getMensaje()+"-"+mensaje.getInformacionAdicional());
                }
            }
        }
    }
    
    private void autorizarSri()
    {
        servicioSri=new ServicioSri("");
         /**
         * Recogiendo autorizacion SRI
         */
        if(servicioSri.autorizar("ejemplo"))
        {
            String xmlAutorizado=servicioSri.obtenerRespuestaAutorizacion();
            ComprobantesElectronicosUtil.generarArchivoXml(xmlAutorizado,getPathComprobante(CARPETA_AUTORIZADOS));
        }
    
    }
    
    private KeyStore obtenerAlmacenFirma(String rutaAlmacenCertificado,String passwordAlmacenCertificado)
    {
        try {
            KeyStore clave=null;
            clave=KeyStore.getInstance("PKCS12");
            FileInputStream file=new FileInputStream(rutaAlmacenCertificado);
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
        
    public String obtenerClaveAcceso()
    {
        Vector<String> claveAcceso=new Vector<>();
        SimpleDateFormat formateador = new SimpleDateFormat("ddmmyyyy");
        claveAcceso.add(formateador.format(comprobante.getFechaEmision()));
             
        claveAcceso.add(getTipoComprobante());
        
        String identificacionFormat=UtilidadesTextos.llenarCarateresDerecha(comprobante.getIdentificacion(),13, "0");
        claveAcceso.add(identificacionFormat);
        
        claveAcceso.add(getTipoCodigoAmbiente());
        
        /**
         * Valor por defecto serie que no se que se pone
         */
        claveAcceso.add("001001");
        
        /**
         * Secuendial del comprobante
         */
        String secuencialFormat=UtilidadesTextos.llenarCarateresIzquierda(comprobante.getInformacionTributaria().getSecuencial(),9, "0");
        claveAcceso.add(secuencialFormat);
        
        /**
         * Codigo numerico , que paree que sirve cuando se procesan archivos en bach
         * y por defecto se pone con 0
         */
        claveAcceso.add("00000000");
        
        /**
         * Clave del tipo de emision, para el metodo offline solo existe el modo 1
         * que significa modo normal antes existia el modo contingencia
         */
        claveAcceso.add(MODO_FACTURACION_NORMAL);
        
                
        
        /**
         * Digito verificador
         */
        String digito=UtilidadesTextos.calcularModulo11(UtilidadesTextos.castVectorToString(claveAcceso));
        claveAcceso.add(digito);
        
        return UtilidadesTextos.castVectorToString(claveAcceso);
    }
    
    private String getTipoCodigoAmbiente()
    {
        switch (this.modoFacturacion) {
            case ComprobanteElectronicoService.MODO_PRODUCCION:
                return "02";

            case ComprobanteElectronicoService.MODO_PRUEBAS:
                return "01";
                
            default: 
                return "00";
        }
    }
    
    private String getTipoComprobante()
    {
        switch (comprobante.getTipoDocumento()) {
            case ComprobanteElectronico.FACTURA:
                return "01";

            case ComprobanteElectronico.NOTA_CREDITO:
                return "04";
                
            default: 
                return "00";
        }
    }
    
    private StringWriter generarXml(ComprobanteElectronico comprobante)
    {
        try {
            JAXBContext contexto = JAXBContext.newInstance(comprobante.getClass());            
            Marshaller marshaller = contexto.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
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
    
    private String getPathComprobante(String carpeta)            
    {
        //return pathBase+"/"+carpeta+"/"+claveAcceso+".xml";
        //14502017011724213151500010010010000000010000000011.xml
        return pathBase+"/"+carpeta+"/14502017011724213151500010010010000000010000000011.xml";
    }
    
    private String getPathFirma()            
    {
        return pathBase+"/"+CARPETA_CONFIGURACION+"/"+nombreFirma;
    }
    
    
    
}
